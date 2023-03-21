package kr.co.javacafe.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import kr.co.javacafe.domain.Admin;
import kr.co.javacafe.domain.AdminRole;
import kr.co.javacafe.domain.Sales;
import kr.co.javacafe.dto.AdminDTO;
import kr.co.javacafe.dto.AdminJoinDTO;
 
import kr.co.javacafe.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
	
	private final ModelMapper modelMapper;
	private final LoginRepository loginRepository;
	private final PasswordEncoder passwordEncoder;
	
	
	
	@Override
	public void join(AdminJoinDTO joinDTO) throws MidExistException{
		String id = joinDTO.getId();
		boolean exist = loginRepository.existsById(id);
		if(exist) {
			throw new MidExistException();
		}
		
		Admin admin = modelMapper.map(joinDTO,Admin.class);
		admin.change(passwordEncoder.encode(joinDTO.getPw()));
		admin.addRole(AdminRole.ADMIN);
		log.info("==========================");
		log.info(admin);
		log.info(admin.getRoleSet());
		
		loginRepository.save(admin);
		
	}


	//삭제
	@Override
	public void remove(String id) {
	 loginRepository.deleteById(id);
		
	}


	//조회
	@Override
	public AdminJoinDTO readOne(String id) {
		Optional<Admin> result = loginRepository.findById(id);
		Admin admin = result.orElseThrow();
		AdminJoinDTO adminJoinDTO = modelMapper.map(admin, AdminJoinDTO.class);
		 
		return adminJoinDTO;
	}

	//수정
	@Override
	public void modify(AdminJoinDTO adminJoinDTO) {
		Optional<Admin> result = loginRepository.findById(adminJoinDTO.getId());
		Admin admin = result.orElseThrow();
		
		admin.change(passwordEncoder.encode(adminJoinDTO.getPw()));
		loginRepository.save(admin);
	}



  
	
}
