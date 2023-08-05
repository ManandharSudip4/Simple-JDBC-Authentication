package com.mstech.springsecurityjdbc.security;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  private static final String myAdmin = "admin";
  private static final String roleA = "ADMIN";

  @Bean
  DataSource dataSource() {
    return new EmbeddedDatabaseBuilder()
      .setType(EmbeddedDatabaseType.H2)
      // .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
      .addScript("db/schema.sql")
      .build();
  }

  // Authentication/ Creating a user 
  // This bean can be modified(commennted) like below if we have a user creating query inside the SQL file.
  @Bean
  UserDetailsManager users(DataSource dataSource) {
    // UserDetails user = User
    //   .builder()
    //   .username("user")
    //   .password("user")
    //   .roles("USER")
    //   .build();
    // UserDetails admin = User
    //   .builder()
    //   .username(myAdmin)
    //   .password(myAdmin)
    //   .roles("USER", roleA)
    //   .build();
    JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
    // users.createUser(user);
    // users.createUser(admin);
    return users;
  }

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }

  //   Authorization
  @Bean
  public SecurityFilterChain securityFilterChain(
    HttpSecurity http,
    HandlerMappingIntrospector introspector
  ) throws Exception {
    MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(
      introspector
    );
    http
      .authorizeHttpRequests(authorize ->
        authorize
          .requestMatchers(mvcMatcherBuilder.pattern("/admin"))
          .hasRole(roleA)
          .requestMatchers(mvcMatcherBuilder.pattern("/user"))
          .hasAnyRole(roleA, "USER")
          .requestMatchers(mvcMatcherBuilder.pattern("/"))
          .permitAll()
      )
      .formLogin(Customizer.withDefaults());
    return http.build();
  }
}
