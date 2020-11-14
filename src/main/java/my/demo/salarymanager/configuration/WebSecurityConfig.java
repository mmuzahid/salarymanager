package my.demo.salarymanager.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	@Qualifier("databaseUserDetailsService")
	private UserDetailsService databaseUserDetailsService;
	
	@Autowired
	@Qualifier("databaseUserAuthenticationProvider")
	private AuthenticationProvider databaseUserAuthenticationProvider;
	
	@Autowired
	@Qualifier("externalApiUserAuthenticationProvider")
    private AuthenticationProvider externalApiUserAuthenticationProvider;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*http        
	        .formLogin().loginPage("/login")                    
	        	.and()
	        .httpBasic()
	        	.and()
	        .authorizeRequests()
	            .antMatchers(HttpMethod.GET, "/salarymanager/**").hasRole("USER")
	            .antMatchers(HttpMethod.POST, "/salarymanager/**").hasRole("ADMIN")
	            .antMatchers(HttpMethod.PUT, "/salarymanager/**").hasRole("ADMIN")
	            .antMatchers(HttpMethod.PATCH, "/salarymanager/**").hasRole("ADMIN")
	            .antMatchers(HttpMethod.DELETE, "/salarymanager/**").hasRole("ADMIN")
       ;*/
		
	}

	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	
		auth.authenticationProvider(databaseUserAuthenticationProvider);
		auth.authenticationProvider(externalApiUserAuthenticationProvider);
		auth.inMemoryAuthentication()
		  .withUser("user").password(passwordEncoder.encode("password")).roles("USER")
		  .and()
		  .withUser("admin").password(passwordEncoder.encode("password")).roles("USER",
		  "ADMIN");
		 
    }
	
	
	@Bean(name="databaseUserAuthenticationProvider")
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(); 
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
		daoAuthenticationProvider.setUserDetailsService(databaseUserDetailsService);
		
		return daoAuthenticationProvider;
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	 
	
}