package kr.co.javacafe.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class Custom403Handler implements AccessDeniedHandler {
	
	@Override	
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {

        log.info("--------ACCESS DENIED--------------");

        response.setStatus(HttpStatus.FORBIDDEN.value());

        //일반 request
            response.sendRedirect("/login?error=ACCESS_DENIED");
            log.info("AccessDeniedException  :  "+accessDeniedException);
            


	}
	

}
