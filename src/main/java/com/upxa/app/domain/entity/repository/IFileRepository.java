package com.upxa.app.domain.entity.repository;

import com.upxa.app.domain.entity.File;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface IFileRepository extends MongoRepository<File, String> {

}
