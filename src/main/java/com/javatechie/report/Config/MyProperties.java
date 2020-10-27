package com.javatechie.report.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class MyProperties {

    @Autowired
    private Environment evn;

    public String getConfigValue(String configKey) {
        return evn.getProperty(configKey);
    }
}
