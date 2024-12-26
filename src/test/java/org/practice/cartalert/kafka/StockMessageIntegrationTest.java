package org.practice.cartalert.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.practice.cartalert.entity.Product;
import org.practice.cartalert.entity.Stock;
import org.practice.cartalert.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@SpringBootTest
@EmbeddedKafka(
        partitions = 1,
        brokerProperties = {
                "listeners=PLAINTEXT://localhost:9092",
                "port=9092"
        },
        topics = {"stock-events"}
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext
class StockMessageIntegrationTest {
    @Autowired
    private StockProducer stockProducer;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafka;

    @SpyBean
    private StockConsumer stockConsumer;

    private Consumer<String, Object> consumer;

    @BeforeAll
    void setUp() {
        Map<String, Object> configs = new HashMap<>(
                KafkaTestUtils.consumerProps("test-group", "true", embeddedKafka)
        );
        configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        consumer = new DefaultKafkaConsumerFactory<>(configs,
                new StringDeserializer(),
                new JsonDeserializer<>(Object.class)
        ).createConsumer();

        embeddedKafka.consumeFromAllEmbeddedTopics(consumer);
    }

    @AfterAll
    void tearDown() {
        if (consumer != null) {
            consumer.close();
        }
    }

    @Test
    void testStockMessageFlow() throws Exception {
        // Given
        Stock stock = createAndSaveStock();
        StockMessage message = StockMessage.builder()
                .stockId(stock.getId())
                .quantity(10)
                .operation("INCREASE")
                .reason("Test")
                .timestamp(LocalDateTime.now())
                .build();

        // When
        SendResult<String, Object> sendResult = stockProducer
                .sendStockMessage(message)
                .get(10, TimeUnit.SECONDS);

        // Then
        assertNotNull(sendResult);

        // Mockito.verify() 대신 Awaitility 사용
        await()
                .atMost(5, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    verify(stockConsumer)
                            .consume(
                                    argThat(msg -> msg.getStockId().equals(stock.getId())),
                                    anyInt(),
                                    anyLong(),
                                    any(Acknowledgment.class)
                            );
                });

        // 재고 업데이트 확인
        await()
                .atMost(5, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    Stock updatedStock = stockRepository.findById(stock.getId())
                            .orElseThrow();
                    assertEquals(110, updatedStock.getQuantity());
                });
    }

    private Stock createAndSaveStock() {
        Stock stock = Stock.builder()
                .quantity(100)
                .productId(1L)
                .safetyStock(100)
                .build();
        return stockRepository.save(stock);
    }
}