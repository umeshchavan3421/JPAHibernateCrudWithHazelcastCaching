package com.uxpsystems.assignment.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

/**
 * Hibernate property mapping from external datasource.properties file.
 * 
 * @author Umesh.Chavan
 *
 */
@Data
@Configuration
@PropertySource("classpath:datasource.properties")
@ConfigurationProperties(ignoreUnknownFields = false, prefix = "hibernate")
public class HibernateProperties {

    private String dialect;

    private String hbm2ddlAuto;

    private String showSql;

    private String formatSql;

    //private String cacheUseSecondLevelCache;

    //private String cacheRegionFactoryClass;

    
}
