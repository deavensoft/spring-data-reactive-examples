package com.example.reactivedataaccess.configuration;

import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(EmbeddedMongoAutoConfiguration.class)
public class MongoConfig {

}
