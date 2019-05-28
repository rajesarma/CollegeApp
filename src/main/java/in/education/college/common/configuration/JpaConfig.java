package in.education.college.common.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

//@Configuration
//@EnableJpaRepositories(basePackages = "in.education.college")
//@EnableTransactionManagement
public class JpaConfig {

	private DataSource dataSource;

	@Autowired
	JpaConfig(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() {

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//		vendorAdapter.setGenerateDdl(true);

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("edu.sjsu");
		factory.setDataSource(dataSource);
		factory.afterPropertiesSet();

		return factory.getObject();
	}

	@Bean
	public PlatformTransactionManager transactionManager() {

		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory());
		return txManager;
	}



	/*@Bean("entityManagerFactoryBean")
	public LocalSessionFactoryBean sessionFactory()
	{
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
//		sessionFactoryBean.setDataSource(dataSource());

		*//*Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.dialect", HIBERNATE_DIALECT);
		hibernateProperties.put("hibernate.show_sql", HIBERNATE_SHOW_SQL);
		//hibernateProperties.put("hibernate.hbm2ddl.auto", HIBERNATE_HBM2DDL_AUTO);
		hibernateProperties.put("hibernate.hbm2ddl.auto", "none");

		hibernateProperties.put("hibernate.id.new_generator_mappings","false");

		sessionFactoryBean.setHibernateProperties(hibernateProperties);*//*

		return sessionFactoryBean;
	}*/

}