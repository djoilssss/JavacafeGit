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


import kr.co.javacafe.dto.PageRequestDTO;
import kr.co.javacafe.dto.PageResponseDTO;
import kr.co.javacafe.dto.SalesDTO;
import kr.co.javacafe.service.SalesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/sales")
@Log4j2
@RequiredArgsConstructor
public class SalesController {
		
		private final SalesService salesService;
		
		
		//검색 및 list 메소드
		@GetMapping("/list")
		public void list(PageRequestDTO pageRequestDTO, Model model) {
			 
			PageResponseDTO<SalesDTO> responseDTO = salesService.list(pageRequestDTO);
					
			log.info(responseDTO);
					 
			model.addAttribute("dto",responseDTO);
			//pageRequestDTO와 pageResponseDTO객체가 view로 전달됨
					
		}		
		
		//게시글 등록하기 get-post
				@GetMapping("/register")
				public void registerGET() {
					
				}
				
				@PostMapping("/register")
				public String registerPOST(@Valid SalesDTO salesDTO, BindingResult bindingResult, 
						RedirectAttributes redirectAttributes, HttpServletRequest request) {
					log.info("sales POST register==============");
					
					if(bindingResult.hasErrors()) { //오류 발생할경우
						log.info("has errors=================");
						redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
						//error가 발생할경우 error라는 이름으로 RedirectAtrributes에 추가해서 전송한다 라는 코드
						
						return "redirect:/sales/register";
					}
					
					//정상적으로 생성될경우
					log.info(salesDTO);
					long sno = salesService.register(salesDTO);
					redirectAttributes.addFlashAttribute("result1",sno);
					return "redirect:/sales/list";
				}
				
				//게시글 상세보기(조회) + 수정
				@GetMapping({"/read","/modify"})
				public void read(long sno, PageRequestDTO pageRequestDTO, Model model) {
					
					//게시글 상세보기
					SalesDTO salesDTO = salesService.readOne(sno);
					log.info(salesDTO);
					model.addAttribute("dto",salesDTO);
				}
				
				//게시글 수정 post
				@PostMapping("/modify")
				public String modifyPOST(PageRequestDTO pageRequestDTO,
									@Valid SalesDTO salesDTO,
									BindingResult bindingResult,
									RedirectAttributes redirectAttributes,
									HttpServletRequest request) {
					log.info("salse modify post================>" + salesDTO);
					
					//오류발생시
					if(bindingResult.hasErrors()) {
						log.info("has errors===============");
						String link = pageRequestDTO.getLink(); //url고정
						//에러발생시 모든 에러는 errors라는 이름으로 수정페이지로 이동시킴
						
						redirectAttributes.addFlashAttribute("errors",bindingResult.getAllErrors());
						redirectAttributes.addAttribute("sno", salesDTO.getSno());
						
						//link를 통해 기존의 모든 조건(url)을 붙여서 redirect
						return "redirect:/sales/modify?"+link;
						
					} //errors if end
						
						
					//수정메소드
					salesService.modify(salesDTO);
					redirectAttributes.addFlashAttribute("result2", "modified");
					redirectAttributes.addAttribute("sno",salesDTO.getSno());
					
					return "redirect:/sales/read";
				}
				
				
				//삭제 처리
				@PostMapping("/remove")
				public String removePOST(long sno, RedirectAttributes redirectAttributes) {
					log.info("remove post================== " + sno);
					salesService.remove(sno);
					redirectAttributes.addFlashAttribute("result3","removed");
					return "redirect:/sales/list";
				}
}
