package com.projeto.grupo10.vacineja;

import com.projeto.grupo10.vacineja.filtros.TokenFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VacinejaApplication {

	@Bean
	public FilterRegistrationBean<TokenFilter> filterJwt() {
		FilterRegistrationBean<TokenFilter> filterRB = new FilterRegistrationBean<TokenFilter>();
		filterRB.setFilter(new TokenFilter());
		filterRB.addUrlPatterns("/api/teste");
		return filterRB;
	}

	public static void main(String[] args) {
		SpringApplication.run(VacinejaApplication.class, args);
	}

}
