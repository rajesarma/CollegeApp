package in.education.college.common.configuration;

public class WebSecurityConfigBkp { //extends WebSecurityConfigurerAdapter

	/*@Override
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
