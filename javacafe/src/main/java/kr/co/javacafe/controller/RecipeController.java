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
import kr.co.javacafe.dto.RecipeDTO;
import kr.co.javacafe.service.InvenService;
import kr.co.javacafe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/recipe")
@Log4j2
@RequiredArgsConstructor
public class RecipeController {
	
	private final RecipeService recipeService;
	private final InvenService invenService;

	@GetMapping("/list")
	public void list(PageRequestDTO pageRequestDTO, Model model) {
		log.info(pageRequestDTO);
		PageResponseDTO<RecipeDTO> responseDTO = recipeService.list(pageRequestDTO);
		log.info(responseDTO);
		model.addAttribute("responseDTO", responseDTO);
	}
	
	@GetMapping("/register")
	public void registerGET(Long rno, Model model) {
		if(rno == null) {
			log.info("<Recipe Controller> recipe register GET");
			RecipeDTO recipeDTO = RecipeDTO.builder()
					.rno(0L)
					.rcate("0")
					.rname("")
					.reng("")
					.rdesc("")
					.rtext("")
					.rcost(0)
					.rprice(0)
					.rkcal(0)
					.rimg("")
					.rstate(0)
					.build();
			model.addAttribute("dto", recipeDTO);
		}else {
			log.info("<Recipe Controller> recipe modify GET");
			RecipeDTO recipeDTO = recipeService.readOne(rno);
			log.info(recipeDTO);
			model.addAttribute("dto", recipeDTO);
			
		}
	}
	
	@PostMapping("/register")
	public String registerPost(@Valid RecipeDTO recipeDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		log.info("<Recipe Controller> recipe register POST");
		if(bindingResult.hasErrors()) {
			log.info("has errors......");
			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			return "redirect:/recipe/register";
		}
		log.info(recipeDTO);
		Long rno = recipeService.register(recipeDTO, request);
		redirectAttributes.addFlashAttribute("result", rno);
		return "redirect:/recipe/list";
	}
	
	@PostMapping("/modify")
	public String modify(PageRequestDTO pageRequestDTO, @Valid RecipeDTO recipeDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		log.info("<Recipe Controller> modify POST");
		log.info(recipeDTO);
		if(bindingResult.hasErrors()) {
			log.info("has errors!");
			String link = pageRequestDTO.getLink();
			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			redirectAttributes.addAttribute("rno", recipeDTO.getRno());
			return "redirect:/recipe/register?"+link;
			
		}
		recipeService.modify(recipeDTO, request);
		redirectAttributes.addFlashAttribute("result","modified");
		redirectAttributes.addAttribute("rno", recipeDTO.getRno());
		return "redirect:/recipe/read";
	}
	
	@GetMapping("/read")
	public void read(Long rno, PageRequestDTO pageRequestDTO, Model model) {
		RecipeDTO recipeDTO = recipeService.readOne(rno);
		log.info(recipeDTO);
		model.addAttribute("dto", recipeDTO);
		// 개행 문자 넘기기
		String nlString = System.getProperty("line.separator").toString();
		model.addAttribute("nlString", nlString);
		model.addAttribute("invenlist", invenService.getAll());
	}
	
	@PostMapping("/remove")
	public String remove(Long rno, RedirectAttributes redirectAttributes) {
		log.info("remove record : "+rno);
		recipeService.remove(rno);
		redirectAttributes.addFlashAttribute("result", "removed");
		return "redirect:/recipe/list";
		
	}
	

}
