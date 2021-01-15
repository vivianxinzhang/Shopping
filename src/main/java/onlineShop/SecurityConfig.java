package onlineShop;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    // authentication
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin()        // 使用form authentication
                .loginPage("/login");   // 使用自己的 login 页面
        http
                .authorizeRequests()    // *表示任意字母 ** 可以匹配多个level  /a/b
                .antMatchers("/cart/**").hasAuthority("ROLE_USER")  // admin 没有购物车
                .antMatchers("/get*/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .antMatchers("/admin*/**").hasAuthority("ROLE_ADMIN")   // admin 开头的 url 需要 admin 权限才能访问
                .anyRequest().permitAll();  // 其它操作任何权限都可以访问
    }

    // inMemoryAuthentication  方便测试
    // jdbcAuthentication   实际上线使用这种
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 在memory中(JVM) authenticate下面这个用户 权限为admin
        auth
                .inMemoryAuthentication().withUser("123@gmail.com").password("123").authorities("ROLE_ADMIN");

        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT emailId, password, enabled FROM users WHERE emailId=?")   // sql 语句
                .authoritiesByUsernameQuery("SELECT emailId, authorities FROM authorities WHERE emailId=?");
    }

    // 这里是说不加密 明文匹配  框架要求规定好加密方式
    @SuppressWarnings("deprecation")
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance(); // 加密操作
    }
}
