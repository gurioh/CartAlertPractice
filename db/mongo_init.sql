use cart_alert

db.products.insertMany([
    {
        name: "Wireless Mouse",
        description: "Ergonomic wireless mouse with USB receiver",
        price: 25.99,
        stock: 150,
        category: "Electronics",
        createdAt: new Date(),
        updatedAt: new Date()
    },
    {
        name: "Wireless Keyboard",
        description: "Compact wireless keyboard with Bluetooth",
        price: 49.99,
        stock: 100,
        category: "Electronics",
        createdAt: new Date(),
        updatedAt: new Date()
    },
    {
        name: "Gaming Chair",
        description: "Comfortable gaming chair with adjustable height",
        price: 299.99,
        stock: 30,
        category: "Furniture",
        createdAt: new Date(),
        updatedAt: new Date()
    }
])