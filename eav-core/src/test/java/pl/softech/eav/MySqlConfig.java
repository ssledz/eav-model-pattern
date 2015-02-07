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
package pl.softech.eav;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import liquibase.integration.spring.SpringLiquibase;
import liquibase.logging.LogFactory;
import liquibase.logging.LogLevel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import pl.softech.eav.domain.ComputerModelInitializationService;
import pl.softech.eav.domain.PersonModelInitializationService;
import pl.softech.eav.domain.attribute.AttributeRepository;
import pl.softech.eav.domain.attribute.DataTypeSerialisationService;
import pl.softech.eav.domain.dictionary.DictionaryEntryRepository;
import pl.softech.eav.domain.frame.FrameFactory;
import pl.softech.eav.domain.relation.RelationConfigurationRepository;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
@Configuration
@EnableJpaRepositories("pl.softech.eav.domain")
public class MySqlConfig {

	@Bean
	public DataSource dataSource() {

		final ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setJdbcUrl("jdbc:mysql://localhost:3306/sample?createDatabaseIfNotExist=true&sessionVariables=sql_mode=NO_BACKSLASH_ESCAPES&useUnicode=yes&characterEncoding=UTF-8&characterSetResults=utf8&connectionCollation=utf8_unicode_ci");
		cpds.setUser("test");
		cpds.setPassword("test");
		return cpds;
	}

	@Bean
	public SpringLiquibase liquibase(final DataSource ds) {
		LogFactory.getInstance().setDefaultLoggingLevel(LogLevel.DEBUG);
		final SpringLiquibase lb = new SpringLiquibase();
		lb.setDataSource(ds);
		lb.setChangeLog("pl/softech/eav/db.changelog.xml");
		return lb;
	} 
	
	@Bean
	public FrameFactory frameFactory(final AttributeRepository attributeRepository,
			final RelationConfigurationRepository relationConfigurationRepository) {
		return new FrameFactory(attributeRepository, relationConfigurationRepository);
	}

	@Bean
	public DataTypeSerialisationService dataTypeSerialisationService(final DictionaryEntryRepository dictionaryEntryRepository) {
		return new DataTypeSerialisationService(dictionaryEntryRepository);
	}

	@Bean
	public ComputerModelInitializationService computerModelInitializationService() {
		return new ComputerModelInitializationService();
	}

	@Bean
	public PersonModelInitializationService personModelInitializationService() {
		return new PersonModelInitializationService();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(final DataSource ds) {
		final LocalContainerEntityManagerFactoryBean lemfb = new LocalContainerEntityManagerFactoryBean();
		lemfb.setDataSource(ds);
		lemfb.setJpaVendorAdapter(jpaVendorAdapter());
		lemfb.setPackagesToScan("pl.softech.eav.domain");
		final Properties jpaProperties = new Properties();
		jpaProperties.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
		jpaProperties.setProperty("hibernate.cache.use_second_level_cache", "true");
		jpaProperties.setProperty("hibernate.show_sql", "true");
		jpaProperties.setProperty("hibernate.format_sql", "true");
		lemfb.setJpaProperties(jpaProperties);
		return lemfb;
	}

	@Bean
	public JpaTransactionManager transactionManager(final EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		final HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setDatabase(Database.MYSQL);
//		jpaVendorAdapter.setGenerateDdl(true);
		return jpaVendorAdapter;
	}

}
