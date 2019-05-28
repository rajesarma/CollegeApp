package in.education.college.common.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Configuration
@PropertySource({"classpath:labels.properties","classpath:validation_messages" +
		".properties"})
public class WebConfig implements WebMvcConfigurer  {

//	@Autowired
//	RequestHeaderInterceptor requestHeaderInterceptor;

	//	The LocaleResolver comes from a very smart interface definitions which makes use of not one but four different techniques to determine current locale, this is:
//	1. Current session has locale information, 2. Cookies being exchanged over the
// browser, 3. The Accept-Language header being exchanged in request scope, 4. A fixed value

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
		//CookieLocaleResolver resolver= new CookieLocaleResolver();
		sessionLocaleResolver.setDefaultLocale(Locale.US);

		return sessionLocaleResolver;
	}

//	LocaleChangeInterceptor
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");

		return localeChangeInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//registry.addInterceptor(localeChangeInterceptor()).addPathPatterns("/*");

//		registry.addInterceptor(requestHeaderInterceptor);
		registry.addInterceptor(localeChangeInterceptor());
	}


	// Configured in yml file

	//For Messages
	/*@Bean
	public MessageSource messageSource() {
		//Can load message sources if changed externally during runtime
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
//		messageSource.setBasename("classpath:validation_messages");	// Single Properties
		messageSource.setBasenames("classpath:labels", "classpath:validation_messages"); // Multiple Properties files
		messageSource.setDefaultEncoding("UTF-8");
//		messageSource.setCacheSeconds(10); //reload messages every 10 seconds
		return messageSource;
	}

	@Bean
	public LocalValidatorFactoryBean validator(MessageSource messageSource) {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource);
		return bean;
	}*/


	/* This can be used if not mentioned in properties file
	@Bean
	public ViewResolver getViewResolver(){
	    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
	    resolver.setPrefix("/WEB-INF/view/");
	    resolver.setSuffix(".jsp");
	    
	    // resolver.setPrefix("classpath:/static/");
		// resolver.setSuffix(".html");
	    
	    resolver.setViewClass(JstlView.class);
	    return resolver;
	}*/

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//WebMvcConfigurer.super.addResourceHandlers(registry);
		
		// Register resource handler for CSS and JS
		registry.addResourceHandler("/resources/**")
				.addResourceLocations("classpath:/static/")
				.setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());

		registry.addResourceHandler("/resources/**")
				.addResourceLocations("/resources/");

		/*registry.addInterceptor(localeChangeInterceptor())
				.excludePathPatterns("/js/**", "/css/**", "/images/**", "/webjars/**");*/

		// Register resource handler for images
		registry.addResourceHandler("/images/**")
				.addResourceLocations("classpath:/static/images/")
				.setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());

		registry
				.addResourceHandler("/webjars/**")
				.addResourceLocations("/webjars/");

		/*registry.addResourceHandler("/jquery/**") //
				.addResourceLocations("classpath:/META-INF/resources/webjars/jquery/3.3.1-1/");
		registry.addResourceHandler("/popper/**") //
				.addResourceLocations("classpath:/META-INF/resources/webjars/popper.js/1.14.1/umd/");
		registry.addResourceHandler("/bootstrap/**") //
				.addResourceLocations("classpath:/META-INF/resources/webjars/bootstrap/4.1.1/");*/

	}

	/*@Bean
	public FilterRegistrationBean<RequestResponseLoggingFilter> loggingFilter(){
		FilterRegistrationBean<RequestResponseLoggingFilter> registrationBean
				= new FilterRegistrationBean<>();

		registrationBean.setFilter(new RequestResponseLoggingFilter());
		registrationBean.addUrlPatterns("/users/*");

		return registrationBean;
	}*/


	/*@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("validation_messages");
		return messageSource;
	}*/


}
