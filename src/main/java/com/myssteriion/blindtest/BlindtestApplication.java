package com.myssteriion.blindtest;

import com.myssteriion.utils.cipher.StringCipher;
import com.myssteriion.utils.rest.RestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * The Blindtest application.
 */
@SpringBootApplication
@Import( {RestUtils.class} )
@ComponentScan( basePackages = {"com.myssteriion.utils"} )
@PropertySource("${SPRING_CONFIG_LOCATION}/musics-blindtest/application.properties")
public class BlindtestApplication extends SpringBootServletInitializer {
    
    @Value("${cipher.algorithm}")
    private String cipherAlgorithm;
    
    @Value("${cipher.key_algorithm}")
    private String cipherKeyAlgorithm;
    
    @Value("${cipher.secret_key}")
    private String cipherSecretKey;
    
    
    
    /**
     * The stringCipher bean.
     *
     * @return the stringCipher
     */
    @Bean
    public StringCipher stringCipher() {
        return new StringCipher(cipherAlgorithm, cipherKeyAlgorithm, cipherSecretKey);
    }
    
    
    
    public static void main(String[] args) {
        SpringApplication.run(BlindtestApplication.class, args);
    }
    
}
