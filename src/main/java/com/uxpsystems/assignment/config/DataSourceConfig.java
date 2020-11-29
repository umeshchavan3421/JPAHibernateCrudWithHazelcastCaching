package com.uxpsystems.assignment.config;

import java.sql.SQLException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 
 * Data source configurations used for database connections and transactions
 * @author Umesh.Chavan
 *
 */
@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "jpaTransactionManager", basePackages = {
		"com.uxpsystems.assignment.dao" })
@PropertySource("application.properties")
@EnableTransactionManagement
public class DataSourceConfig {

	//private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceConfig.class);

	@Autowired
	DatasourceProperties datasourceProperties;

	@Autowired
	HibernateProperties hibernateProperties;

	/**
	 * Configures datasource object using properties mentioned in
	 * datasource.properties file
	 * 
	 * @return DataSource
	 * 
	 * @throws SQLException
	 */
	@Bean(name = "datasource")
	DataSource datasource() throws SQLException {
		//LOGGER.info("DataSource configuration initialized.");
		DriverManagerDataSource datasource = new DriverManagerDataSource();
		datasource.setDriverClassName(datasourceProperties.getDriverClassName());
		datasource.setUrl(datasourceProperties.getUrl());
		datasource.setUsername(datasourceProperties.getUsername());
		datasource.setPassword(datasourceProperties.getPassword());

		return datasource;
	}

	/**
	 * Configure Entity manager with hibernate properties mentioned in
	 * datasaource.properties file. It is used by repository to communicate all the
	 * DB related transactions.
	 * 
	 * @param datasource
	 * 
	 * @return LocalContainerEntityManagerFactoryBean
	 */
	@Bean(name = "entityManagerFactory")
	LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("datasource") DataSource datasource) {
		//LOGGER.info("Entity Manger Factory configuration initialized.");

		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(datasource);
		entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactory.setPackagesToScan("com.uxpsystems.assignment.model");

		Properties jpaProperties = new Properties();
		// Configures the used database dialect. This allows Hibernate to create
		// SQL
		// that is optimized for the used database.
		jpaProperties.put("hibernate.dialect", hibernateProperties.getDialect());

		// Specifies the action that is invoked to the database when the
		// Hibernate
		// SessionFactory is created or closed.
		jpaProperties.put("hibernate.hbm2ddl.auto", hibernateProperties.getHbm2ddlAuto());

		// If the value of this property is true, Hibernate writes all SQL
		// statements to the console.
		jpaProperties.put("hibernate.show_sql", hibernateProperties.getShowSql());

		// If the value of this property is true, Hibernate will use prettyprint
		// when it writes SQL to the console.
		jpaProperties.put("hibernate.format_sql", hibernateProperties.getFormatSql());

		// TODO: Need to check Hibernate second level cache configuration
		// If the value of this property is true, Hibernate will use hibernet
		// second level cache
		//jpaProperties.put("hibernate.cache.use_second_level_cache", hibernateProperties.getCacheUseSecondLevelCache());

		// Specify the hibernate region factory class used for second level
		// cache
		// jpaProperties.put("hibernate.cache.region.factory_class",
		// "com.hazelcast.hibernate.HazelcastCacheRegionFactory");
		//jpaProperties.put("hibernate.cache.region.factory_class", hibernateProperties.getCacheRegionFactoryClass());

		entityManagerFactory.setJpaProperties(jpaProperties);

		return entityManagerFactory;
	}

	/**
	 * Configures JPA Transaction manager used to managed all the DB transaction
	 * performed by Entity manager.
	 * 
	 * @param entityManagerFactory
	 * 
	 * @return JpaTransactionManager
	 */
	@Bean(name = "jpaTransactionManager")
	JpaTransactionManager jpaTransactionManager(
			@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);

		return transactionManager;
	}

}
