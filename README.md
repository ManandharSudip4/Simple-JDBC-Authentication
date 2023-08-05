# Spring Security with JDBC (H2 Database)

### Brief

    So i previously configured authentication on spring boot using
    In-Memory Authentication (Without using any database).

    So, here i tried to use database and used the credentials stored
    in that database to access my APIs.

    So, i was refering to some 3 years old video and a official documentation.
    And faced some errors because of deprecated methods and ways.

    Had to follow some community notes to solve the problem.

    Before i explain further, following code blocks is what i was following (from 3 y/o video).

```java
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    
    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
            .dataSource(dataSource)
            .withDefaultSchema()
            .withUser(
                User.withUsername("user")
                    .password("password")
                    .roles("USER")
            );
    }
}

```

    But a lot of this have been deprecated from this snippet and had to solve the issues one by one.

    You can see the working code blocks in [security/SecurityConfiguration].

#### Community Links i followed to solve this issues
- [JDBC Authentication](https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/jdbc.html#servlet-authentication-jdbc-schema)
- [MVCMatchBuilder issue](https://github.com/spring-projects/spring-security/issues/13568)



## Further More
    So i commented the some lines inside UserDetailsManager Bean which creates the new user
    and created the user from the SQL file located at [resources/db/schema.sql]

    This is how we do when we want to create users using SQL.

    Also, specified script[schema.sql] to be used to create a table and insert the user instead 
    of creating default tables and inserting users from USerDetailsManager.

    This is how we customize the table we want to create.
    