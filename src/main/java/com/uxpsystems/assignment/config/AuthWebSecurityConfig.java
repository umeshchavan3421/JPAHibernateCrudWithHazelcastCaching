package com.uxpsystems.assignment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Configuration class for Spring Authentication and Authorization Security. It
 * configures In-Memory Authentication
 * 
 * @author Umesh.Chavan
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthWebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomBasicAuthenticationEntryPoint authenticationEntryPoint;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user1")
				.password("$2a$10$a3XpDRSC8dLqKWp.Pg3ioefHaTNdCMDlZjWJqw3RlSGrj5c1ajhzi").authorities("ROLE_USER").and()
				.withUser("admin").password("$2a$10$QAEsyl8gPIHIp6HnHcAPzej3dOUUsVU8L50ErKzqgt.5jTlnwnXji")
				.authorities("ROLE_ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().anyRequest().authenticated()
				.and().httpBasic().authenticationEntryPoint(authenticationEntryPoint);
		http.headers().frameOptions().disable();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
