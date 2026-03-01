package com.ironfit.backendmongo.test;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestRepo extends MongoRepository<TestDoc, String> {

}
