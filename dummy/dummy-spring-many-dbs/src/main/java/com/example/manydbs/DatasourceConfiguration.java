package com.example.manydbs;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfiguration {

	@Bean
	@ConfigurationProperties("spring.datasource.bank-catalogue")
	public DataSourceProperties bankCatalogueDataSourceProperties(){
		return new DataSourceProperties();
	}

	@Bean
	public DataSource bankCatalogueDataSource(){
		return bankCatalogueDataSourceProperties()
				.initializeDataSourceBuilder()
				.build();
	}





}
