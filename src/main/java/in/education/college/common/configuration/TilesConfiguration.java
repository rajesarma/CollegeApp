package in.education.college.common.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

@Configuration
public class TilesConfiguration {

	// Using org.springframework.web.servlet.view.tiles3 not ties2
	// jstl is used by Spring's tiles3 integration

	@Bean
	public ViewResolver tilesViewResolver() {

		// Need to register view class as TilesView.class
		UrlBasedViewResolver tilesViewResolver = new UrlBasedViewResolver();
		tilesViewResolver.setViewClass(TilesView.class);

		return  tilesViewResolver;
	}

	@Bean
	public TilesConfigurer tilesConfigurer() {

		// Configure the location of tiles configuration file
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		String[] definitions = {"WEB-INF/tiles.xml"};
		tilesConfigurer.setDefinitions(definitions);

		return  tilesConfigurer;
	}
}
