package com.example.case_module4.configuration.security;

import com.example.case_module4.configuration.CustomAccessDeniedHandler;
import com.example.case_module4.configuration.JwtAuthenticationFilter;
import com.example.case_module4.configuration.RestAuthenticationEntryPoint;
import com.example.case_module4.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.case_module4.model.constant.RoleName.ROLE_ADMIN;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private IUserService userService;

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public RestAuthenticationEntryPoint restServicesEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/**");
        http.httpBasic().authenticationEntryPoint(restServicesEntryPoint());
        http.authorizeRequests()
                .antMatchers("/api/login", "/api/register").permitAll()
                .antMatchers(HttpMethod.GET, "/api/cities").permitAll()
                .antMatchers(HttpMethod.GET, "/api/categories").permitAll()
                .antMatchers(HttpMethod.GET, "/api/rooms").permitAll()
        ;
        http.antMatcher("/api/**").httpBasic()
                .authenticationEntryPoint(restServicesEntryPoint()).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()

//                .antMatchers(HttpMethod.GET, "/api/**")
//                .access("hasRole('ROLE_ADMIN')")
//                .antMatchers(HttpMethod.POST, "/api/**")
//                .access("hasRole('ROLE_ADMIN')")
//                .antMatchers(HttpMethod.PUT, "/api/**")
//                .access("hasRole('ROLE_ADMIN')")
//                .antMatchers(HttpMethod.DELETE, "/api/**")
//                .access("hasRole('ROLE_ADMIN')")

                .antMatchers(HttpMethod.GET, "/api/rooms/**")
                .access("hasRole('ROLE_USER') or hasRole('ROLE_SELLER') or hasRole('ROLE_ADMIN')")
                .antMatchers(HttpMethod.POST, "/api/rooms**")
                .access("hasRole('ROLE_SELLER')")
                .antMatchers(HttpMethod.PUT, "/api/rooms**")
                .access("hasRole('ROLE_SELLER')")
                .antMatchers(HttpMethod.DELETE, "/api/rooms**")
                .access("hasRole('ROLE_SELLER')")

                .and()
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());
    }
}
