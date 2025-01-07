package org.practice.cartalert.repository;

import org.practice.cartalert.repository.entity.UserAuthToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthTokensRepository extends JpaRepository<UserAuthToken, Long> {

    void deleteByUserId(Long userId);


}
