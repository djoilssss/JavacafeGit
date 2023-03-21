package kr.co.javacafe.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.javacafe.dto.PageRequestDTO;
import kr.co.javacafe.dto.PageResponseDTO;
import kr.co.javacafe.dto.ShopJoinDTO;
import kr.co.javacafe.dto.ShopStateDTO;
import kr.co.javacafe.service.ShopService;
import kr.co.javacafe.service.ShopStateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/shop")
@Log4j2
@RequiredArgsConstructor
public class ShopController {
	
	private final ShopService shopService;
	private final ShopStateService shopStateService;

	@GetMapping("/order")
	public void orderGET() {	
	}	
	@GetMapping("/list")
	public void listGET(PageRequestDTO pageRequestDTO, Model model) {
        PageResponseDTO<ShopStateDTO> responseDTO = shopStateService.list(pageRequestDTO);
        log.info(responseDTO);
        model.addAttribute("responseDTO", responseDTO);		
	}
	
	@GetMapping("/read")
	public void readGET(Long ssno, String sid, Model model){
		log.info("sid : "+sid);
		// List<ShopDTO> dtolist = shopService.getByKid(sid);
		model.addAttribute("dto", shopStateService.readOne(ssno));
		model.addAttribute("dtolist", shopService.getByKidJoin(sid));
		
	}
}