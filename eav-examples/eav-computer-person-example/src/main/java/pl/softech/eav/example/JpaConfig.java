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
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

/**
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.2
 */
@Configuration
public class JpaConfig {

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(JpaVendorAdapter jpaVendorAdapter, DataSource ds,
			@Value("${hibernate.show_sql}") String showSql, @Value("${hibernate.format_sql}") String formatSql,
			@Value("${hibernate.cache.use_second_level_cache}") String useSecondLevelCache) {
		LocalContainerEntityManagerFactoryBean lemfb = new LocalContainerEntityManagerFactoryBean();
		lemfb.setDataSource(ds);
		lemfb.setJpaVendorAdapter(jpaVendorAdapter);
		lemfb.setPackagesToScan("pl.softech.eav.domain");
		Properties jpaProperties = new Properties();
		jpaProperties.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
		jpaProperties.setProperty("hibernate.cache.use_second_level_cache", useSecondLevelCache);
		jpaProperties.setProperty("hibernate.show_sql", showSql);
		jpaProperties.setProperty("hibernate.format_sql", formatSql);
		/* See https://hibernate.atlassian.net/browse/HHH-8796 */
		jpaProperties.setProperty("hibernate.schema_update.unique_constraint_strategy", "RECREATE_QUIETLY");
		lemfb.setJpaProperties(jpaProperties);
		return lemfb;
	}

	@Bean(name = "transactionManager")
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}

}
