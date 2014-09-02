package pl.softech.learning;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import pl.softech.learning.domain.dictionary.DictionaryEntryRepository;
import pl.softech.learning.domain.eav.AttributeRepository;
import pl.softech.learning.domain.eav.ComputerModelInitializationService;
import pl.softech.learning.domain.eav.DataTypeSerialisationService;
import pl.softech.learning.domain.eav.PersonModelInitializationService;
import pl.softech.learning.domain.eav.frame.FrameFactory;

@Configuration
@EnableJpaRepositories("pl.softech.learning.domain")
public class HSqlConfig {

	@Bean
	public FrameFactory frameFactory(AttributeRepository attributeRepository) {
		return new FrameFactory(attributeRepository);
	}
	
	@Bean
	public DataTypeSerialisationService dataTypeSerialisationService(DictionaryEntryRepository dictionaryEntryRepository) {
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
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL).build();
	}

	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setDatabase(Database.HSQL);
		jpaVendorAdapter.setGenerateDdl(true);
		return jpaVendorAdapter;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean lemfb = new LocalContainerEntityManagerFactoryBean();
		lemfb.setDataSource(dataSource());
		lemfb.setJpaVendorAdapter(jpaVendorAdapter());
		lemfb.setPackagesToScan("pl.softech.learning.domain");
		lemfb.setMappingResources("named-queries.xml");
		return lemfb;
	}

}
