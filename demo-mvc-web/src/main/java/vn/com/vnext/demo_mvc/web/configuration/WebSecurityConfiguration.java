/**
 * WebSecurityConfiguration.java
 *
 * @copyright  Copyright © 2019 VNext Software
 * @author quannl
 * @package vn.com.vnext.demo_mvc.configuration
 * @version 1.0.0
 */
package vn.com.vnext.demo_mvc.web.configuration;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import vn.com.vnext.demo_mvc.web.filter.CorsFilterOncePerRequestFilter;
import vn.com.vnext.demo_mvc.web.filter.DemoMVCAuthenticationFilter;

/**
 * WebSecurityConfiguration
 *
 * @author quannl
 * @access public
 * @package vn.com.vnext.demo_mvc.configuration
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Get the current user's information (email and password)
    @Override
    protected void configure(AuthenticationManagerBuilder authentication) throws Exception {
        authentication.userDetailsService(userDetailService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new CorsFilterOncePerRequestFilter(), ChannelProcessingFilter.class);
        http.cors().configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.addAllowedOrigin("*");
            config.setAllowCredentials(true);
            return config;
        });

        http.csrf().disable().authorizeRequests()
                .antMatchers("/").permitAll()
                //For the resource /getToken, we don't check the JWT token
                .antMatchers("/login", "/resources/**").permitAll()
                .and().authorizeRequests()
                .antMatchers(HttpMethod.GET, "/v2/api-docs", "/swagger-resources/configuration/ui", "/swagger-resources",
                        "/swagger-resources/configuration/security", "/swagger-ui.html", "/webjars/**").permitAll()
                .and().authorizeRequests().anyRequest().authenticated()
                /*.and().formLogin().loginPage("/login").usernameParameter("username").passwordParameter("password").permitAll()
                .loginProcessingUrl("/doLogin")
                .successForwardUrl("/postLogin")
                .failureUrl("/loginFailed")*/
                .and()
                .logout().logoutUrl("/doLogout").logoutSuccessUrl("/login").permitAll()
                //This is an web service (API), so we set the session into STATELESS mode
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and().addFilterBefore(new DemoMVCAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                /*.and()
                // We filter the api/login requests by checking the request's body(email and password)
                .addFilterBefore(new JWTLoginFilter("/getToken", authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class)
                // And filter other requests to check the presence of JWT in header
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                //Add exception handler for filter
                .addFilterBefore(new ExceptionHandlerFilter(), JWTAuthenticationFilter.class)*/;

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/public/**");
        web.ignoring().antMatchers("api/v2/api-docs", "/v2/api-docs", "/configuration/ui", "/swagger-resources",
                "/configuration/security", "/swagger-ui.html", "/webjars/**");
    }
}
