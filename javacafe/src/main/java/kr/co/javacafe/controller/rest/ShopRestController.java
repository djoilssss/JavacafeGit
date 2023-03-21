package kr.co.javacafe.controller.rest;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.co.javacafe.dto.ShopDTO;
import kr.co.javacafe.dto.ShopStateDTO;
import kr.co.javacafe.service.ShopService;
import kr.co.javacafe.service.ShopStateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/shoprest")
@Log4j2
@RequiredArgsConstructor
public class ShopRestController {

	private final ShopService shopService;
	private final ShopStateService shopStateService;
	
	// 주문 낱개 등록
	@ApiOperation(value = "Shop Register", notes = "POST 방식으로 레코드 등록")
	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Long> register(@Valid @RequestBody ShopDTO shopDTO, BindingResult bindingResult)throws BindException{
		log.info(shopDTO);
		if(bindingResult.hasErrors()) {
			throw new BindException(bindingResult);
		}
		Map<String, Long> resultMap = new HashMap<>();
		Long sno = shopService.register(shopDTO);
		resultMap.put("sno", sno);
		return resultMap;
	}
	
	// 주문 상태 등록
	@ApiOperation(value = "Shop State Register", notes = "POST 방식으로 레코드 등록")
	@PostMapping(value = "/state/", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Long> register(@Valid @RequestBody ShopStateDTO shopStateDTO, BindingResult bindingResult)throws BindException{
		log.info(shopStateDTO);
		if(bindingResult.hasErrors()) {
			throw new BindException(bindingResult);
		}
		Map<String, Long> resultMap = new HashMap<>();
		Long ssno = shopStateService.register(shopStateDTO);
		resultMap.put("ssno", ssno);
		resultMap.put("sstoday", shopStateDTO.getSstoday());
		return resultMap;
	}
	
	// 대기번호 출력
	@ApiOperation(value = "GetTodayOrder ShopState", notes = "GET 방식으로 주문 순서 조회")
	@GetMapping(value = "/today/{today}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Long> todayGET(@PathVariable("today") String today){
		log.info("today : "+today);
		Map<String, Long> resultMap = new HashMap<>();
		Long todayorder = shopStateService.countTodayOrder(today);
		resultMap.put("todayorder", todayorder);
		return resultMap;
	}
	
	// 주문 상태에 회원 전화번호 등록
	@ApiOperation(value = "Modify ShopState", notes = "PUT방식으로 회원 전화번호 등록")
	@PutMapping(value = "/{ssno}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Long> remove(@PathVariable("ssno") Long ssno, @RequestBody ShopStateDTO shopStateDTO){
		shopStateDTO.setSsno(ssno);
		shopStateService.modify(shopStateDTO);
		Map<String, Long> resultMap = new HashMap<>();
		resultMap.put("ssno", ssno);
		return resultMap;
	}	
}
