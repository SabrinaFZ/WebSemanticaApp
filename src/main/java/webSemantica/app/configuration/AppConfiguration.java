package webSemantica.app.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

@Configuration
public class AppConfiguration {
	
	 @Autowired
	 private SpringTemplateEngine templateEngine;

	
	@Bean
	public ThymeleafViewResolver thymeleafViewResolver() {
	    ThymeleafViewResolver resolver = new ThymeleafViewResolver();
	    resolver.setTemplateEngine(this.templateEngine);
	    resolver.setCharacterEncoding("UTF-8");
	    return resolver;
	}

}
