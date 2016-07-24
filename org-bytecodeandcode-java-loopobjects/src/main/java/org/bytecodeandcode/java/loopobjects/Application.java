package org.bytecodeandcode.java.loopobjects;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.gs1ca.metadata.attributetype.AttributeMetadataPool;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@SpringBootApplication
@EnableJpaRepositories(basePackages= {"org.gs1ca.eccnet.domain.dao", "org.gs1ca.eccnet.location.dao", "org.gs1ca.metadata.dao"})
@ComponentScan(basePackages = { "org.gs1ca.eccnet.domain.service", "org.gs1ca.metadata.service" })
public class Application {

	@Bean
	public AttributeMetadataPool attributeMetadataPool() {
		return AttributeMetadataPool.getInstance();
	}

	@Bean
	public DataSource dataSource() {
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setUrl(
				"jdbc:jtds:sqlserver://LRD-DDECCNet01;user=eccnetuser;password=EccnetUs3r!;databaseName=EEV3_NextGen");

		return basicDataSource;
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() {

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("org.gs1ca.eccnet.domain.domain", "org.gs1ca.eccnet.location.domain", "org.gs1ca.metadata");
		factory.setDataSource(dataSource());
		factory.afterPropertiesSet();

		return factory.getObject();
	}
}
