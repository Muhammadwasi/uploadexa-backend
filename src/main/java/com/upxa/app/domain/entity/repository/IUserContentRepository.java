package com.upxa.app.domain.entity.repository;

import com.upxa.app.domain.entity.UserContent;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface IUserContentRepository extends MongoRepository<UserContent, String> {

}
