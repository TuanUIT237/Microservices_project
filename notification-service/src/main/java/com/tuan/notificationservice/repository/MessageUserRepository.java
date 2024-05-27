package com.tuan.notificationservice.repository;

import com.tuan.notificationservice.entity.MessageUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageUserRepository extends MongoRepository<MessageUser, String> {
}
