package com.remainder.service;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.remainder.service.filters.JwtFilter;


@ComponentScan(basePackages = "com.remainder.service")
@SpringBootApplication
public class RemainderServiceAppApplication {
	
	private static final Logger log = LoggerFactory.getLogger(RemainderServiceAppApplication.class);
	
	@Value("${application.secret}")
	private String secret;

	@Value("${session.duration.hours:1}")
	private int hours;

	@Value("${session.duration.minutes:0}")
	private int minutes;

	@Value("${session.threshold:20}")
	private int threshold;
	
	@Bean
	public FilterRegistrationBean<JwtFilter> jwtFilter() {
		final FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<JwtFilter>();
		JwtFilter filter = new JwtFilter(secret, hours, minutes, threshold);
		filter.sessionSettings(hours, minutes, threshold);

		registrationBean.setFilter(filter);
		registrationBean.addUrlPatterns("/api/remainder/*");
		registrationBean.addUrlPatterns("/login/ingresa");
		registrationBean.addUrlPatterns("/check/jwt");

		log.debug("filter jwtFilter added");
		return registrationBean;
		
	}
	
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("languages/messages");
		messageSource.setDefaultEncoding("utf-8");
		return messageSource;
	}
	
	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT-5"));
	}

	public static void main(String[] args) {
		SpringApplication.run(RemainderServiceAppApplication.class, args);
	}

}
