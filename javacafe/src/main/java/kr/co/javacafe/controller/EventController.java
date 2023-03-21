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
import kr.co.javacafe.dto.EventDTO;
import kr.co.javacafe.dto.PageRequestDTO;
import kr.co.javacafe.dto.PageResponseDTO;
import kr.co.javacafe.service.EventService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/event")
@Log4j2
@RequiredArgsConstructor
public class EventController {

	private final EventService eventService;
	
	// 게시판 리스트
	@GetMapping("/list")
	public void list(PageRequestDTO pageRequestDTO, Model model) {
	
		PageResponseDTO<EventDTO> responseDTO = eventService.list(pageRequestDTO);
		
		log.info(responseDTO);
		
		model.addAttribute("responseDTO", responseDTO);
	}
	
	// 게시글 작성
	@GetMapping("/register")
	public void registerGET(Long eno, Model model) {
		if(eno == null) {
			log.info("<Event Controller> event register GET");
			EventDTO eventDTO = EventDTO.builder()
					.eno(0L)
					.etitle("")
					.econtent("")
					.ewriter("")
					.eimg("")
					.build();
			model.addAttribute("dto", eventDTO);
		}else {
			log.info("<Event Controller> event modify GET");
			EventDTO eventDTO = eventService.readOne(eno);
			log.info(eventDTO);
			model.addAttribute("dto", eventDTO);
		}
	}
	
	// 게시글 작성
	@PostMapping("/register")
	public String registerPost(@Valid EventDTO eventDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		log.info("event POST register.....");
		if(bindingResult.hasErrors()) {
			log.info("errors.....");
			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			return "redirect:/event/register";
		}
		
		log.info(eventDTO);
		
		Long eno = eventService.register(eventDTO, request);
		
		redirectAttributes.addFlashAttribute("result", eno);
		
		return "redirect:/event/list";
	}
	
	// 상세보기 & (수정&삭제 화면보기)
	@GetMapping({"/read", "/modify"})
	public void read(Long eno, PageRequestDTO pageRequestDTO, Model model) {
		EventDTO eventDTO = eventService.readOne(eno);
		log.info(eventDTO);
		model.addAttribute("dto", eventDTO);
	}
	
	// 	 수정 처리 
	// - 수정 후 조회 화면으로 이동했을 때 검색했던 조건들이 해당하지 않을 수도 있다. 
	// - 예를 들어 제목을 수정하면 기존에는 검색 조건에 맞았을 수 있지만, 수정 후에는 검색 조건에 맞지 않을 수도 있다.
	//   안전하게 구현하려면 수정 후에는 검색 조건 없이 단순 조회 화면으로 이동하도록 구현한다.
	// - 수정 작업 또한 @valid를 이용하고 문제가 있다면 다시 수정화면으로 돌려 보낼 필요가 있다.
	@PostMapping("/modify")
	public String modify(PageRequestDTO pageRequestDTO, @Valid EventDTO eventDTO, 
			BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		log.info("event modify post....." + eventDTO);
		if(bindingResult.hasErrors()) {
			String link = pageRequestDTO.getLink();
			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			redirectAttributes.addAttribute("eno", eventDTO.getEno());
			return "redirect:/event/modify?"+link;
		}
		eventService.modify(eventDTO, request);
		redirectAttributes.addAttribute("eno", eventDTO.getEno());
		return "redirect:/event/read";
	}
	
	// 삭제 처리 후에는 목록으로 이동하도록 구성함.
	@PostMapping("/remove")
	public String remove(Long eno, RedirectAttributes redirectAttributes) {
		log.info("remove post..." + eno);
		eventService.remove(eno);
		redirectAttributes.addFlashAttribute("result", "removed");
		return "redirect:/event/list";
	}
	
} // end Controller
