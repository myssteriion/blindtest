package com.myssteriion.blindtest;

import com.myssteriion.utils.BeanFactory;
import com.myssteriion.utils.rest.RestUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * The type Blindtest application.
 */
@SpringBootApplication
@Import( {BeanFactory.class, RestUtils.class} )
@PropertySource("${SPRING_CONFIG_LOCATION}/application.properties")
public class BlindtestApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BlindtestApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(BlindtestApplication.class, args);
	}

}
