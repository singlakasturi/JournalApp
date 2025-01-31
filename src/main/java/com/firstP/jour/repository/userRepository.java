package com.firstP.jour.repository;

import com.firstP.jour.entity.users;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

public interface userRepository extends MongoRepository<users, ObjectId> {

    users findByUserName(String userName);

    void deleteByUserName(String userName);
}
