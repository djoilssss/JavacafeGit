package kr.co.javacafe.security;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.javacafe.domain.Admin;
import kr.co.javacafe.domain.AdminRole;
import kr.co.javacafe.dto.AdminDTO;
 
import kr.co.javacafe.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{

	private PasswordEncoder passwordEncoder;
	private final LoginRepository loginRepository;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("loadUserByusername:  " + username);
//		UserDetails userDetails = User.builder().username("user1")
//		.password("1111")
//		.password(passwordEncoder.encode("1111"))
//		.authorities("ROLE_USER")
//		.build();
		
		Optional<Admin> result = loginRepository.getWithRoles(username);		
		
		if(result.isEmpty()) { //해당 아이디를 가진 사용자가 없다면
			throw new UsernameNotFoundException("username not found....");
		}
			
		Admin admin = result.get();
		AdminDTO adminDTO = new AdminDTO(
							admin.getId(),
							admin.getPw(),
							admin.getRoleSet().stream().map(adminRole -> 
							new SimpleGrantedAuthority("ROLE_"+adminRole.name()))
							.collect(Collectors.toList())
							);
		log.info("AdminDTO---------------");
		log.info(adminDTO);
		return adminDTO;
	}

	
}
