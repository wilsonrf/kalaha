package com.wilsonfranca.kalaha.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = {"com.wilsonfranca.kalaha"})
@EnableMongoAuditing
public class MongoRepositoryConfiguration {
}
