package kr.co.javacafe.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.javacafe.dto.BoardDTO;
import kr.co.javacafe.dto.CustomerDTO;
import kr.co.javacafe.dto.PageRequestDTO;
import kr.co.javacafe.dto.PageResponseDTO;
import kr.co.javacafe.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/customer")
@Log4j2
@RequiredArgsConstructor
public class CustomerController {
	
	private final CustomerService customerService;
	
	
//	고객정보 전체 리스트 조회
	@GetMapping("/list")
	public void list(PageRequestDTO pageRequestDTO,Model model) {
		PageResponseDTO<CustomerDTO> responseDTO = customerService.list(pageRequestDTO);
		log.info(responseDTO);
		
		model.addAttribute("responseDTO", responseDTO);
	}
//	고객정보 전체 리스트 조회 (포인트 적립용)
	@GetMapping("/list2")
	public void list2(PageRequestDTO pageRequestDTO,Model model) {
		PageResponseDTO<CustomerDTO> responseDTO = customerService.list(pageRequestDTO);
		log.info(responseDTO);
		
		model.addAttribute("responseDTO", responseDTO);
	}
//	고객정보 전체 리스트 조회 (포인트 차감용)
	@GetMapping("/list3")
	public void list3(PageRequestDTO pageRequestDTO,Model model) {
		PageResponseDTO<CustomerDTO> responseDTO = customerService.list(pageRequestDTO);
		log.info(responseDTO);
		
		model.addAttribute("responseDTO", responseDTO);
	}
	
//	고객 정보 추가폼으로 이동
	@GetMapping("/register")
	public void registerGET(){
	
	}
	
//	고객 정보 추가
	@PostMapping("/register")
    public String registerPost(@Valid CustomerDTO customerDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        log.info("Customer POST register.......");

        if(bindingResult.hasErrors()) {
            log.info("has errors.......");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors() );
            return "redirect:/customer/register";
        }

        log.info(customerDTO);

        Long cno  = customerService.register(customerDTO);

        redirectAttributes.addFlashAttribute("result", cno);

        return "redirect:/customer/list";
    }
	
//	고객 정보 선택 조회 , 수정
	@GetMapping({"/read", "/modify"})
	public void read(Long cno, PageRequestDTO pageRequestDTO,Model model) {
		CustomerDTO customerDTO = customerService.readOne(cno);
		
		log.info(customerDTO);
		
		model.addAttribute("dto", customerDTO);
	}
//	고객 정보 수정
	@PostMapping("/modify")
	public String modify(PageRequestDTO pageRequestDTO,
                          @Valid CustomerDTO customerDTO,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {
		 log.info("Customer modify post......." + customerDTO);

	        if(bindingResult.hasErrors()) {
	            log.info("has errors.......");

	            String link = pageRequestDTO.getLink();

	            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors() );

	            redirectAttributes.addAttribute("cno", customerDTO.getCno());
		return "redirect:/customer/modify?"+link;
	}
	        customerService.modify(customerDTO);

	        redirectAttributes.addFlashAttribute("result", "modified");

	        redirectAttributes.addAttribute("cno", customerDTO.getCno());

	        return "redirect:/customer/read";
	    }

//	고객 정보 삭제
	@PostMapping("/remove")
    public String remove(Long cno, RedirectAttributes redirectAttributes) {

        log.info("remove post.. " + cno);

        customerService.remove(cno);

        redirectAttributes.addFlashAttribute("result", "removed");

        return "redirect:/customer/list";

    }
	
	
	
//	고객 정보 선택 조회2 , 포인트적립( 수정 )
	@GetMapping({"/read2", "/modify2"})
	public void read2(Long cno, PageRequestDTO pageRequestDTO,Model model) {
		CustomerDTO customerDTO = customerService.readOne(cno);
		
		log.info(customerDTO);
		
		model.addAttribute("dto", customerDTO);
	}
//	고객 정보 선택 조회2 , 포인트차감( 수정 )
	@GetMapping({"/read3", "/modify3"})
	public void read3(Long cno, PageRequestDTO pageRequestDTO,Model model) {
		CustomerDTO customerDTO = customerService.readOne(cno);
		
		log.info(customerDTO);
		
		model.addAttribute("dto", customerDTO);
	}
//	고객 포인트 적립 ( 수정 )

	@PostMapping("/modify2")
	public String modify2(PageRequestDTO pageRequestDTO,
                          @Valid CustomerDTO customerDTO,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {
		 log.info("Customer modify2 post......." + customerDTO);

	        if(bindingResult.hasErrors()) {
	            log.info("has errors.......");

	            String link = pageRequestDTO.getLink();

	            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors() );

	            redirectAttributes.addAttribute("cno", customerDTO.getCno());
		return "redirect:/customer/modify2?"+link;
	}
	        customerService.modify2(customerDTO);

	        redirectAttributes.addFlashAttribute("result", "modified");

	        redirectAttributes.addAttribute("cno", customerDTO.getCno());

	        return "redirect:/customer/read";
	    }
	
//	고객 포인트 차감 ( 수정 )

	@PostMapping("/modify3")
	public String modify3(PageRequestDTO pageRequestDTO,
                          @Valid CustomerDTO customerDTO,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {
		 log.info("Customer modify3 post......." + customerDTO);

	        if(bindingResult.hasErrors()) {
	            log.info("has errors.......");

	            String link = pageRequestDTO.getLink();

	            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors() );

	            redirectAttributes.addAttribute("cno", customerDTO.getCno());
		return "redirect:/customer/modify3?"+link;
	}
	        customerService.modify3(customerDTO);

	        redirectAttributes.addFlashAttribute("result", "modified");

	        redirectAttributes.addAttribute("cno", customerDTO.getCno());

	        return "redirect:/customer/read";
	    }

	
	

}
