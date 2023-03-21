package kr.co.javacafe.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import kr.co.javacafe.security.CustomUserDetailsService;
import kr.co.javacafe.security.handler.Custom403Handler;

import javax.sql.DataSource;

@Log4j2
@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomSeurityConfig {
	
	private final DataSource dataSource;
	private final CustomUserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		log.info("-----------configure-------------------");
		
		 http.formLogin().loginPage("/login"); // 로그인페이지
		 http.csrf().disable().authorizeRequests() //csrf토큰 비활성화
		 .antMatchers("/", //권한이 필요한 페이지 지정
				 "/inven/**",
				 "/staff/**",
				 "/sales/**",
				 "/recipe/**",
				 "/customer/**",
				 "/fboard/**",
				 "/event/**",
				 "/admin/**",
				 "/join").authenticated()
		 
		 .antMatchers("/", 
				 "/inven/**",
				 "/staff/**",
				 "/sales/**",
				 "/recipe/**",
				 "/customer/**",
				 "/fboard/**",
				 "/event/**",
				 "/admin/**",
				 "/join").hasRole("ADMIN"); //AdminRole = ADMIN일 경우에 허락
		 
		 
		 http.rememberMe() //자동로그인 기능
	     		.key("12345678")
	            .tokenRepository(persistentTokenRepository())
	            .userDetailsService(userDetailsService)
	            .tokenValiditySeconds(60*60*24*30);
		 http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
		 
		 http.logout() //로그아웃 기능넣기
		 	.logoutUrl("/logout") //로그아웃 처리url default : /logout, 원칙적으로 post만 지원
		 	.logoutSuccessUrl("/login") //로그아웃 성공시 이동할곳
		 	.deleteCookies("JSESSIONID","remember-me"); //쿠키/자동로그인쿠키 지우기
		 	// .addLogoutHandler()
		 	// .logoutSuccessHandler() 
		 
		return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		log.info("---------------------web configure--------------------");
		return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
	    repo.setDataSource(dataSource);
	    return repo;
	}
	
	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new Custom403Handler();
	}
	
	   
	  
	   
	   
}
