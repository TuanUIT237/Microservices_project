package com.tuan.notificationservice.repository;

import com.tuan.notificationservice.entity.MessageGlobal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageGlobalRepository extends MongoRepository<MessageGlobal, String> {
}
