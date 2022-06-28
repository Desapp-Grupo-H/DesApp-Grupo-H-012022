package ar.edu.unq.desapp.grupoh.backenddesappapi;

import ar.edu.unq.desapp.grupoh.backenddesappapi.webservice.aspects.LogExecutionTimeAspectAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication
@EnableSwagger2
public class BackendDesappApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendDesappApiApplication.class, args);
	}

	@Bean
	JedisPool jedisPool(){
		return new JedisPool( new JedisPoolConfig(), "localhost", 6379 );
	}
}
