package tw.mygym.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.http.HttpServletResponse;



@Configuration
@EnableWebSecurity
public class SecurityConfig {	
	
//	做測試用，全部開放沒有登入
//	@Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable() // 禁用 CSRF（僅開發環境使用，生產環境謹慎）
//            .authorizeHttpRequests(auth -> auth
//                .anyRequest().permitAll() // 允許所有請求
//            );
//        return http.build();
//    }
	
	
	// 公開訪問路徑
	  private static final String[] PUBLIC_PATHS = {
		        "/login", "/register", "/createUser",
		        "/updateUser", "/checkDuplicateUser","/addTrainingRecord",
		        "/css/**", "/js/**"
		    };
//	// 需要身份驗證的路徑
//	  private static final String[] AUTHENTICATED_PATHS = {
//			  "/updateUser", "/deleteUser/**", "/user/**"
//		    };
	  
	 @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		 http
         .csrf().disable() // 禁用 CSRF（僅限開發環境，生產環境需啟用）
         .authorizeHttpRequests(auth -> auth
        	 .requestMatchers(PUBLIC_PATHS).permitAll() // 登入頁面允許訪問
//        	 .requestMatchers(AUTHENTICATED_PATHS).authenticated() // 需要登入的路徑
             .anyRequest().authenticated() // 其他頁面需登入
         )
         .formLogin(form -> form
             .loginPage("/login.html") // 自訂登入頁面
             .loginProcessingUrl("/login") // 表單提交的 URL
             
             .defaultSuccessUrl("/home.html", true) // 登入成功後跳轉
             .failureHandler((request, response, exception) -> {
                 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                 response.setContentType("application/json;charset=UTF-8");
                 response.getWriter().write("{\"error\": \"登入失敗，帳號或密碼錯誤\"}");
             })
             
             .permitAll()
         )
         .logout(logout -> logout
             .logoutUrl("/logout") // 登出請求的 URL
             .logoutSuccessUrl("/login.html") // 登出成功後跳轉
             .invalidateHttpSession(true) // 使 session 無效
             .deleteCookies("JSESSIONID") // 刪除 cookies
         );
	        return http.build();
	    }
	 
	 @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	        return authenticationConfiguration.getAuthenticationManager();
	    }
	 
	
//	使用BCry加密
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
