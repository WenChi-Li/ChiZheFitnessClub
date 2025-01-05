package tw.mygym.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;

//@Configuration
public class SecurityConfigTest {

//	 @Bean
//	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//	        http
//	            .authorizeHttpRequests()
//	                .requestMatchers("/admin/**").hasRole("ADMIN")  // 限制 /admin/ 路徑只有 ADMIN 角色可以訪問
//	                .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")  // 限制 /user/ 路徑，USER 或 ADMIN 都可以訪問
//	                .requestMatchers("http://localhost:8080/createUser").permitAll()
//	                .anyRequest().authenticated()  // 其他所有請求都需要身份驗證
//	                .and()
////	                .formLogin().disable();
//	            .formLogin()  // 啟用預設的表單登入功能
//	                .and()
//	            .logout();  // 啟用預設的登出功能
//	        return http.build();  // 返回 SecurityFilterChain 配置
//	    }
//
//	    @Bean
//	    public PasswordEncoder passwordEncoder() {
//	        return new BCryptPasswordEncoder();  // 使用 BCryptPasswordEncoder 進行密碼加密
//	    }
//
//	    @Bean
//	    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//	        return http.getSharedObject(AuthenticationManagerBuilder.class)
//	                .inMemoryAuthentication() // 配置 In-Memory Authentication
//	                    .withUser("admin").password(passwordEncoder().encode("admin123")).roles("ADMIN") // 建立用戶 admin
//	                    .and()
//	                    .withUser("user").password(passwordEncoder().encode("user123")).roles("USER") // 建立用戶 user
//	                    .and()
//	                .and()
//	                .build(); // 返回 AuthenticationManager
//	    }
}
