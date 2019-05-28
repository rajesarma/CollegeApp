package in.education.college.common.configuration;

//@Configuration
//@Transactional
//@EnableJpaRepositories(basePackages = "in.education.college")
//@EnableTransactionManagement
//@EntityScan("in.education.college.user")
//@PropertySource("classpath:application.properties")
public class MySqlConfig {

	/*@Autowired
	DataSource dataSource;*/

	/*@Bean
	@Primary
	@ConfigurationProperties("spring.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().type(DriverManagerDataSource.class).build();
	}*/

	/*@Bean(name = "sessionFactory")
	public SessionFactory getSessionFactory() {
		LocalSessionFactoryBuilder sessionBuilder =
				new LocalSessionFactoryBuilder(dataSource);
		sessionBuilder.scanPackages("in.education.college");
		return sessionBuilder.buildSessionFactory();
	}

	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(getSessionFactory());
		return transactionManager;
	}*/

	/*@Bean
	public EntityManagerFactory entityManagerFactory() {

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//		vendorAdapter.setGenerateDdl(true);
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("in.education.college");
		factory.setDataSource(dataSource);
		factory.afterPropertiesSet();

		return factory.getObject();
	}

	@Bean
	public PlatformTransactionManager transactionManager() {

		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory());
		return txManager;
	}*/


	// Configuring two data bases, specified its properties in yml file

	/*@Bean
	@Primary
	@ConfigurationProperties("app.datasource.first")
	public DataSourceProperties dataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@Primary
	@ConfigurationProperties("app.datasource.first")
	public DataSource dataSource(DataSourceProperties dataSourceProperties) {
		//return DataSourceBuilder.create().build();
		// Hikari Datasource as default
		// return dataSourceProperties.initializeDataSourceBuilder().build();
		return dataSourceProperties.initializeDataSourceBuilder().type(DriverManagerDataSource.class).build();
	}*/

	// Configuring second Datasource
	/*@Bean
	@ConfigurationProperties("app.datasource.second")
	public DataSourceProperties secondDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@ConfigurationProperties("app.datasource.second.configuration")
	public BasicDataSource secondDataSource() {
		return secondDataSourceProperties().initializeDataSourceBuilder()
				.type(BasicDataSource.class).build();
	}*/


	/*For Configuring Database manually*/

	/*@Value("${mysql.driver}")
	private String driver;

	@Value("${mysql.url}")
	private String url;

	@Value("${mysql.username}")
	private String userName;

	@Value("${mysql.password}")
	private String password;

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driver);
		dataSource.setUrl(url);
		dataSource.setUsername(userName);
		dataSource.setPassword(password);

		return dataSource;
	}*/
	/*For Configuring Database manually*/



	/*For Configuring Hibernate*/

	/*@Value("${hibernate.dialect}")
	private String HIBERNATE_DIALECT;

	@Value("${hibernate.show_sql}")
	private String HIBERNATE_SHOW_SQL;

	@Value("${hibernate.hbm2ddl.auto}")
	private String HIBERNATE_HBM2DDL_AUTO;

	@Value("${entitymanager.packagesToScan}")
	private String ENTITYMANAGER_PACKAGES_TO_SCAN;

	@Bean
	public LocalSessionFactoryBean sessionFactory()
	{
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource());
		sessionFactoryBean.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);

		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.dialect", HIBERNATE_DIALECT);
		hibernateProperties.put("hibernate.show_sql", HIBERNATE_SHOW_SQL);
		//hibernateProperties.put("hibernate.hbm2ddl.auto", HIBERNATE_HBM2DDL_AUTO);
		hibernateProperties.put("hibernate.hbm2ddl.auto", "none");

		hibernateProperties.put("hibernate.id.new_generator_mappings","false");

		sessionFactoryBean.setHibernateProperties(hibernateProperties);

		return sessionFactoryBean;
	}

	@Bean
	public HibernateTransactionManager transactionManager()
	{
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}*/

	/*For Configuring Hibernate*/
}



/*@Bean
	public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
		final DataSourceInitializer initializer = new DataSourceInitializer();
		initializer.setDataSource(dataSource);
		return initializer;
	}*/