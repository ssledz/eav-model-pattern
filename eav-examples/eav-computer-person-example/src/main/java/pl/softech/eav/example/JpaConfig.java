/*
 * Copyright 2013 Sławomir Śledź <slawomir.sledz@sof-tech.pl>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.softech.eav.example;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.2
 */
@Configuration
@PropertySources(value = {@PropertySource("classpath:jpa.properties")})
public class JpaConfig {
	
	@Bean
	   public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
	      return new PropertySourcesPlaceholderConfigurer();
	   }
	
	@Bean
	public DataSource dataSource() {

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setJdbcUrl("jdbc:mysql://localhost:3306/sample?createDatabaseIfNotExist=true&sessionVariables=sql_mode=NO_BACKSLASH_ESCAPES&useUnicode=yes&characterEncoding=UTF-8&characterSetResults=utf8&connectionCollation=utf8_unicode_ci");
		cpds.setUser("test");
		cpds.setPassword("test");
		return cpds;
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter(@Value("${hibernate.generate_ddl}") boolean generateDDl) {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setDatabase(Database.MYSQL);
		jpaVendorAdapter.setGenerateDdl(generateDDl);
		return jpaVendorAdapter;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(JpaVendorAdapter jpaVendorAdapter, DataSource ds) {
		LocalContainerEntityManagerFactoryBean lemfb = new LocalContainerEntityManagerFactoryBean();
		lemfb.setDataSource(ds);
		lemfb.setJpaVendorAdapter(jpaVendorAdapter);
		lemfb.setPackagesToScan("pl.softech.eav.domain");
		Properties jpaProperties = new Properties();
		jpaProperties.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
		jpaProperties.setProperty("hibernate.cache.use_second_level_cache", "true");
		jpaProperties.setProperty("hibernate.show_sql", "true");
		jpaProperties.setProperty("hibernate.format_sql", "true");
		lemfb.setJpaProperties(jpaProperties);
		return lemfb;
	}

	@Bean(name="transactionManager")
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}

}
