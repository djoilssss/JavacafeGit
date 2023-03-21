package kr.co.javacafe.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.javacafe.dto.AdminDTO;
import kr.co.javacafe.dto.AdminJoinDTO;
import kr.co.javacafe.dto.PageRequestDTO;
import kr.co.javacafe.dto.SalesDTO;
import kr.co.javacafe.service.AdminService;
import kr.co.javacafe.service.AdminServiceImpl;
import kr.co.javacafe.service.InvenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/admin")
@Log4j2
@RequiredArgsConstructor
public class AdminController {
	
	private final AdminService adminService;
 
	
	@GetMapping("/modify")
	public void modifyGET(String id, Model model){
		AdminJoinDTO adminJoinDTO=adminService.readOne(id);
		model.addAttribute("dto",adminJoinDTO);
		
	}
	
	//정보수정
	@PostMapping("/modify")
	public String modifyPOST(@Valid AdminJoinDTO adminJoinDTO,
						BindingResult bindingResult,
						RedirectAttributes redirectAttributes,
						HttpServletRequest request) {
		log.info("Admin modify========================" + adminJoinDTO);
		if(bindingResult.hasErrors()) { //에러발생시
			log.info("Admin modify ==========errors");
			redirectAttributes.addFlashAttribute("errors",bindingResult.getAllErrors());
			redirectAttributes.addAttribute("id",adminJoinDTO.getId());
			return "redirect:/";
		}
		
		adminService.modify(adminJoinDTO);
		redirectAttributes.addFlashAttribute("result1","modified");
		redirectAttributes.addAttribute("id",adminJoinDTO.getId());
		return "redirect:/";
	}
	
	//관리자 아이디 등록하기
		@GetMapping("/join")
		public void joinGET() {
			log.info("join get.....");
		}
		
		@PostMapping("/join")
		public String joinPOST(AdminJoinDTO joinDTO,RedirectAttributes redirectAttributes) {
			log.info("join post/......");
			log.info(joinDTO);
			
			try {
				adminService.join(joinDTO);
			}catch (AdminServiceImpl.MidExistException e) {
				redirectAttributes.addFlashAttribute("error","id");
				 
				return "redirect:/";
			} 
			redirectAttributes.addFlashAttribute("regresult","success");
			return "redirect:/";
		}
		
		
	
	//삭제 처리
	@PostMapping("/remove")
	public String removePOST(String id, RedirectAttributes redirectAttributes) {
		log.info("remove post================== " + id);
		adminService.remove(id);
		redirectAttributes.addFlashAttribute("result3","removed");
		return "redirect:/login";
	}
	 
}
