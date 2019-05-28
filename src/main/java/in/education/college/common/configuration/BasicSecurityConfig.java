package in.education.college.common.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/*@Configuration
@EnableWebSecurity*/
//@EnableGlobalMethodSecurity(securedEnabled=true)
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter {

	/*BasicSecurityConfig(){}*/

	/*@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}*/

	private UserDetailsService userDetailsService;

	@Autowired
	public BasicSecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}


	/*@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
	}*/

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService()).passwordEncoder(new BCryptPasswordEncoder(11));
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().hasAnyRole("ADMIN", "USER")
				.and()
				.formLogin()
				.and()
				.logout().permitAll().logoutSuccessUrl("/login")
				.and()
				.csrf().disable();
	}


	/*@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.withDefaultSchema()
			.withUser("admin")
			.password("guest")
			.roles("admin");

//			.and()
//			.withUser("admin").password("password").roles("USER", "ADMIN");
	}*/


	/*@Bean
	public UserDetailsService userDetailsService() {
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(User.withDefaultPasswordEncoder()
		.username("ad").password("guest").roles("ADMIN").build()
		);
		return manager;
	}*/



	/*@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth
			.inMemoryAuthentication()
			.withUser("user")
			.password("password")
			.roles("USER")
			.and()
			.withUser("admin")
			.password("admin")
			.roles("USER","ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		.anyRequest()
		.authenticated()
		.and()
		.httpBasic();
	}*/

	/*
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
				.withUser("admin").password("admin").roles(ADMIN.toString())
				.and()
				.withUser("guest").password("guest").roles(GUEST.toString());
	}*/
}

	/*@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/api/v1/signup", "/favicon.ico");
	}*/

//.csrf().ignoringAntMatchers("/contact-email")

/*http
		.authorizeRequests()
		.antMatchers("/api/v1/signup/**").permitAll()
		.anyRequest().authenticated()*/

/*
<http pattern="/resources/**" security="none"/>*/

/*
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/web/admin/**").hasAnyRole(ADMIN.toString(), GUEST.toString())
				.anyRequest().permitAll()
				.and()
				.formLogin().loginPage("/web/login").permitAll()
				.and()
				.csrf().ignoringAntMatchers("/contact-email")
				.and()
				.logout().logoutUrl("/web/logout").logoutSuccessUrl("/web/").permitAll();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
				.withUser("admin").password("admin").roles(ADMIN.toString())
				.and()
				.withUser("guest").password("guest").roles(GUEST.toString());
	}

}*/


/*<security:intercept-url pattern="/home" access="isAnonymous()"/>
<security:intercept-url pattern="/error*" access="isAnonymous()"/>
<security:intercept-url pattern="/main" access="isAuthenticated()"/>
<security:intercept-url pattern="/css/**" access="permitAll" />
<security:intercept-url pattern="/js/**" access="permitAll" />
<security:intercept-url pattern="/fonts/**" access="permitAll" />
<security:intercept-url pattern="/images/**" access="permitAll" />*/
