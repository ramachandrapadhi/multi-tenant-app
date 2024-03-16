package com.learningtech.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.ResourceUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class MultitenantConfiguration {
//	Logger logger = LoggerFactory.getLogger(MultitenantConfiguration.class);

	@Value("${defaultTenant}")
	private String defaultTenant;

	@SuppressWarnings("rawtypes")
	@Bean
	@ConfigurationProperties(prefix = "tenants")
	DataSource dataSource() throws IOException {

//    	Reading one by one file 
//		File file11 = ResourceUtils.getFile("classpath:allTenants/finance.properties");
//		File file12 = ResourceUtils.getFile("classpath:allTenants/techinical.properties");
//		File[] files = { file11, file12 };

//    	Reading folder with multiple approach 
//		final String absolutePath = "C:/Users/ramap/git/multi-tenant-app/multi-tenant-proj/src/main/resources/allTenants";
//		File[] fileList1 = Paths.get(absolutePath).toFile().listFiles();
//		File[] fileList2 = new File(absolutePath).listFiles();

//		 using PathMatchingResourcePatternResolver read all file dynamically 
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] listOfFile = resolver.getResources("allTenants/*");

		Map<Object, Object> resolvedDataSources = new HashMap<>();

		if (listOfFile == null ) {
			log.error("Properties file not found inside allTenants");
			throw new IOException("Properties file not found");
		}
		for (Resource propertyFile : listOfFile) {
			Properties tenantProperties = new Properties();
			DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();

			try {
				tenantProperties.load(new FileInputStream(propertyFile.getFile()));
				String tenantId = tenantProperties.getProperty("name");

				dataSourceBuilder.driverClassName(tenantProperties.getProperty("datasource.driver-class-name"));
				dataSourceBuilder.username(tenantProperties.getProperty("datasource.username"));
				dataSourceBuilder.password(tenantProperties.getProperty("datasource.password"));
				dataSourceBuilder.url(tenantProperties.getProperty("datasource.url"));
				resolvedDataSources.put(tenantId, dataSourceBuilder.build());
			} catch (IOException exp) {
				throw new RuntimeException("Problem in tenant datasource:" + exp);
			}
		}
		
//        for (File propertyFile : files) {
//        	Properties tenantProperties = new Properties();
//        	DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//        	
//        	try {
//        		tenantProperties.load(new FileInputStream(propertyFile));
//        		String tenantId = tenantProperties.getProperty("name");
//        		
//        		dataSourceBuilder.driverClassName(tenantProperties.getProperty("datasource.driver-class-name"));
//        		dataSourceBuilder.username(tenantProperties.getProperty("datasource.username"));
//        		dataSourceBuilder.password(tenantProperties.getProperty("datasource.password"));
//        		dataSourceBuilder.url(tenantProperties.getProperty("datasource.url"));
//        		resolvedDataSources.put(tenantId, dataSourceBuilder.build());
//        	} catch (IOException exp) {
//        		throw new RuntimeException("Problem in tenant datasource:" + exp);
//        	}
//        }

		AbstractRoutingDataSource dataSource = new MultitenantDataSource();
		dataSource.setDefaultTargetDataSource(resolvedDataSources.get(defaultTenant));
		dataSource.setTargetDataSources(resolvedDataSources);

		dataSource.afterPropertiesSet();
		return dataSource;
	}

}