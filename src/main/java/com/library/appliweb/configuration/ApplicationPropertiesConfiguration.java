package com.library.appliweb.configuration;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("appliweb")
@Data
public class ApplicationPropertiesConfiguration {

    private int livresParPage;
}
