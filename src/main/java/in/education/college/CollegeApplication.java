package in.education.college;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CollegeApplication {	//extends SpringBootServletInitializer

	private static final Logger log = LoggerFactory.getLogger(CollegeApplication.class);

//	private static final Logger LOGGER  = LoggerFactory.getLogger( "ROLLING-FILE" );

	/*
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(StudentApplication.class);
	}*/

	public static void main(String[] args) {
		SpringApplication.run(CollegeApplication.class, args);

		log.warn("Application Started.");

		/*for ( int i = 1; i <= 52; i++ ) {
			LOGGER.info( "write log" );

			try {
				Thread.sleep( 1000L );
			} catch ( final InterruptedException e ) {
				LOGGER.error( "an error occurred", e );
			}
		}*/
	}

	/*@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			System.out.println("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}
		};
	}*/

}
