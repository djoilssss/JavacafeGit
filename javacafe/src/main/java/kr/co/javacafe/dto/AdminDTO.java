package kr.co.javacafe.dto;

import java.util.Collection;

import javax.validation.constraints.NotEmpty;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter 
@ToString
public class AdminDTO extends User{
	
 
	@NotEmpty
	private String id;
	
	@NotEmpty
	private String pw;
 
	
	
	public AdminDTO(String username, String password,Collection<? extends GrantedAuthority> authorities) {
		super(username, password,authorities);
		this.id = username;
		this.pw = password;
		
	}
}
