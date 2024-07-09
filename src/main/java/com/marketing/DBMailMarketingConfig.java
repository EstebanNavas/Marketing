package com.marketing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "DBMailMarketingEntityManagerFactory", transactionManagerRef = "DBMailMarketingTransactionManager", 
basePackages = { "com.marketing.Repository.DBMailMarketing"})
public class DBMailMarketingConfig {

	@Autowired
	private Environment env;
	
	@Bean(name = "DataSource")
	public DataSource DBMailMarketingDatasource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(env.getProperty("validate.datasource.url"));
		dataSource.setUsername(env.getProperty("validate.datasource.username"));
		dataSource.setPassword(env.getProperty("validate.datasource.password"));
		dataSource.setDriverClassName(env.getProperty("validate.datasource.driver-class-name"));
		
		return dataSource;
	}
	
	
	@Bean(name = "DBMailMarketingEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(DBMailMarketingDatasource());
		em.setPackagesToScan("com.marketing.Model.DBMailMarketing");
		
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		
		Map<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", env.getProperty("validate.jpa.hibernate.ddl-auto"));
		properties.put("hibernate.show-sql", env.getProperty("validate.jpa.show-sql"));
		properties.put("hibernate.dialect", env.getProperty("validate.jpa.database-platform"));
		
		em.setJpaPropertyMap(properties);
		
		return em;
		
	}
	
	
	@Bean(name = "DBMailMarketingTransactionManager")
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		
		return transactionManager;
	}
}
