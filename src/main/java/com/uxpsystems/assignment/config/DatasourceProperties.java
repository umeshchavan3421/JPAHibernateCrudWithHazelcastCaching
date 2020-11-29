package com.uxpsystems.assignment.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import javax.validation.constraints.NotNull;

import lombok.Data;


/**
 * DataSource property mapping from external datasource.property file.
 * 
 * @author Umesh.Chavan
 *
 */

@Data
@Configuration
@PropertySource("classpath:datasource.properties")
@ConfigurationProperties(ignoreUnknownFields = false, prefix = "datasource")
public class DatasourceProperties {

	@NotNull
	private String username;

	@NotNull
	private String password;

	@NotNull
	private String driverClassName;

	@NotNull
	private String url;

}
