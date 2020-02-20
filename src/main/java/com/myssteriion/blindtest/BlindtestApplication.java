package com.myssteriion.blindtest;

import com.myssteriion.utils.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;

/**
 * The type Blindtest application.
 */
@SpringBootApplication
@PropertySource("${SPRING_CONFIG_LOCATION}/application.properties")
public class BlindtestApplication extends SpringBootServletInitializer {

	@Autowired
    public BlindtestApplication(ApplicationContext applicationContext) {
		new BeanFactory().setApplicationContext(applicationContext);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BlindtestApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(BlindtestApplication.class, args);
	}

}
