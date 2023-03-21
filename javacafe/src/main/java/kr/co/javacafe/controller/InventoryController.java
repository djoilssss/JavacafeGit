package kr.co.javacafe.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import kr.co.javacafe.dto.InventoryDTO;
import kr.co.javacafe.dto.PageRequestDTO;
import kr.co.javacafe.dto.PageResponseDTO;
import kr.co.javacafe.service.InvenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/inven")
@Log4j2
@RequiredArgsConstructor
public class InventoryController {

	
	private final InvenService invenService;
	
	
	
	
	
	//검색 및 list 메소드
	@GetMapping("/list")
	public void list(PageRequestDTO pageRequestDTO, Model model) {
		 
		PageResponseDTO<InventoryDTO> responseDTO = invenService.list(pageRequestDTO);
				
		log.info(responseDTO);
				 
		model.addAttribute("dto",responseDTO);
		//pageRequestDTO와 pageResponseDTO객체가 view로 전달됨
				
	}			
	
	//게시글 등록하기 get-post
		@GetMapping("/register")
		public void registerGET() {
			
		}
		
		@PostMapping("/register")
		public String registerPOST(@Valid InventoryDTO inventoryDTO, BindingResult bindingResult, 
				RedirectAttributes redirectAttributes, HttpServletRequest request) {
			log.info("inven POST register==============");
			
			if(bindingResult.hasErrors()) { //오류 발생할경우
				log.info("has errors=================");
				redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
				//error가 발생할경우 error라는 이름으로 RedirectAtrributes에 추가해서 전송한다 라는 코드
				
				return "redirect:/inven/register";
			}
			
			//정상적으로 생성될경우
			log.info(inventoryDTO);
			long ino = invenService.register(inventoryDTO, request);
			redirectAttributes.addFlashAttribute("result1",ino);
			return "redirect:/inven/list";
		}
		
		//게시글 상세보기(조회) + 수정
		@PreAuthorize("isAuthenticated()")
		@GetMapping({"/read","/modify"})
		public void read(long ino, PageRequestDTO pageRequestDTO, Model model) {
			
			//게시글 상세보기
			InventoryDTO inventoryDTO = invenService.readOne(ino);
			log.info(inventoryDTO);
			model.addAttribute("dto",inventoryDTO);
		}
		
		//게시글 수정 post
		@PostMapping("/modify")
		public String modifyPOST(PageRequestDTO pageRequestDTO,
							@Valid InventoryDTO inventoryDTO,
							BindingResult bindingResult,
							RedirectAttributes redirectAttributes,
							HttpServletRequest request) {
			log.info("inventory modify post================>" + inventoryDTO);
			
			//오류발생시
			if(bindingResult.hasErrors()) {
				log.info("has errors===============");
				String link = pageRequestDTO.getLink(); //url고정
				//에러발생시 모든 에러는 errors라는 이름으로 수정페이지로 이동시킴
				
				redirectAttributes.addFlashAttribute("errors",bindingResult.getAllErrors());
				redirectAttributes.addAttribute("ino", inventoryDTO.getIno());
				
				//link를 통해 기존의 모든 조건(url)을 붙여서 redirect
				return "redirect:/inven/modify?"+link;
				
			} //errors if end
				
				
			//수정메소드
			invenService.modify(inventoryDTO, request);
			redirectAttributes.addFlashAttribute("result2", "modified");
			redirectAttributes.addAttribute("ino",inventoryDTO.getIno());
			
			return "redirect:/inven/read";
		}
		
		
		//삭제 처리
		@PostMapping("/remove")
		public String removePOST(long ino, RedirectAttributes redirectAttributes) {
			log.info("remove post================== " + ino);
			invenService.remove(ino);
			redirectAttributes.addFlashAttribute("result3","removed");
			return "redirect:/inven/list";
		}
	
}
