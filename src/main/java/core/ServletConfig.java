package core;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.dialect.IDialect;
//import org.thymeleaf.extras.springsecurity3.dialect.SpringSecurityDialect;
import org.thymeleaf.spring3.SpringTemplateEngine;
import org.thymeleaf.spring3.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@Configuration
public class ServletConfig extends WebMvcConfigurerAdapter 
{
	
	/*Trying to load messages to my views*/
//	@Bean
//    public ReloadableResourceBundleMessageSource messageSource(){
//        ReloadableResourceBundleMessageSource messageSource=new ReloadableResourceBundleMessageSource();
////        String[] resources= {};
////        messageSource.setBasenames(resources)
//        messageSource.setBasenames("classpath:/resources/messages/messages");
//        return messageSource;
//    }
	
	@Bean
    public ResourceBundleMessageSource messageSource() {
    	ResourceBundleMessageSource source = new ResourceBundleMessageSource();
    	source.setBasename("msg/messages");
    	source.setUseCodeAsDefaultMessage(true);
    	return source;
    }

	@Bean
	public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations(
				"/resources/");
	}

	/* Alternative viewResolver code */
	// @Bean
	// public InternalResourceViewResolver viewResolver() {
	// InternalResourceViewResolver viewResolver = new
	// InternalResourceViewResolver();
	// viewResolver.setViewClass(JstlView.class);
	// viewResolver.setPrefix("/WEB-INF/pages/");
	// viewResolver.setSuffix(".jsp");
	// return viewResolver;
	// }

	@Bean
	public ServletContextTemplateResolver templateResolver() {
		ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".html");
		// NB, selecting HTML5 as the template mode.
		resolver.setTemplateMode("HTML5");
		resolver.setCacheable(false);
		return resolver;

	}

	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		// engine.setAdditionalDialects((Set<IDialect>) additionalDialect());
		engine.setTemplateResolver(templateResolver());
        engine.setMessageSource(messageSource());
		Set<IDialect> additionalDialects = new HashSet<>();
//		additionalDialects.add(new SpringSecurityDialect());
		engine.setAdditionalDialects(additionalDialects);
		return engine;
	}

//	/* Added this method inorder to access the Thymeleaf:TabLibs. */
//	 public SpringSecurityDialect additionalDialect() {
//	 SpringSecurityDialect dialect = new SpringSecurityDialect();
//	 // org.thymeleaf.extras.springsecurity3.dialect.SpringSecurityDialect
//	 return dialect;
//	 }

	@Bean
	public ViewResolver viewResolver() {

		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine());
		viewResolver.setOrder(1);
		viewResolver.setViewNames(new String[] { "*" });
		viewResolver.setCache(false);
		return viewResolver;
	}

}
