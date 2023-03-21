package kr.co.javacafe.repository;

import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import groovy.transform.AutoClone;
import kr.co.javacafe.domain.Admin;
import kr.co.javacafe.domain.AdminRole;
import kr.co.javacafe.domain.Customer;
import kr.co.javacafe.domain.Inventory;
import kr.co.javacafe.dto.AdminDTO;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class AdminRepositoryTest {
	
	@Autowired
	private LoginRepository loginRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Test //등록 테스트
	public void insert() {
		IntStream.rangeClosed(1,100).forEach(i-> {
			Admin admin = Admin.builder()
					.id("id"+i)
					.pw(passwordEncoder.encode("12345"))
					.build();
			//Role 존재시 
			//admin.addRole(AdminRole.USER);
			
			 //if ( i > 90){
			 //admin.addRole(AdminRole.ADMIN);
			 //} //이런식으로 if문사용가능
			
			loginRepository.save(admin);
		});
	}
	
	//중복아이디찾기
	@Test
	public void Read() {
		Optional<Admin> result = loginRepository.getWithRoles("id100");
		Admin admin = result.orElseThrow();
		log.info(admin);
		log.info(admin.getRoleSet());
		admin.getRoleSet().forEach(adminRole -> log.info(adminRole.name()));
	}
	
	@Test
	public void read1() {
		String id = "abc";
		Optional<Admin> result = loginRepository.findById(id);
		Admin admin = result.orElseThrow();
		
		System.out.println("아이디 "+admin);
	}
	
	
	@Test
	public void remove() {
		String id ="abc";
		loginRepository.deleteById(id);
		log.info("삭제된 아이디 " + id);
	}
	
	@Test
	public void modify() {
		String id="abc";
		Optional<Admin> result = loginRepository.findById(id);
		Admin admin = result.orElseThrow();
		admin.change(passwordEncoder.encode("1111"));
		loginRepository.save(admin);
	}
	 
}
