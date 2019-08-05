package in.education.college.common.filter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

public class CorsDevEnvironmentFilter extends CorsFilter {

	public CorsDevEnvironmentFilter() {
		super(configurationSource());
	}

	private static UrlBasedCorsConfigurationSource configurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowCredentials(true);
		configuration.addAllowedOrigin("localhost:8040");
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
