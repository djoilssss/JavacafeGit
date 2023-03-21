package kr.co.javacafe.controller.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.co.javacafe.dto.CustomerDTO;
import kr.co.javacafe.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/customerrest")
@Log4j2
@RequiredArgsConstructor
public class CustomerRestController {

	private final CustomerService customerService;

	@ApiOperation(value = "Get CustomerDTO By cphone", notes = "GET방식으로 핸드폰 번호를 통해 회원 정보 조회")
	@GetMapping(value = "/phone/{cphone}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomerDTO getCustomerDTOByCphone(@PathVariable("cphone") String cphone) {
		return customerService.getFirstByCphone(cphone);
	}

	// 적립
	@ApiOperation(value = "Customer Modify", notes = "PUT방식으로 포인트 적립")
	@PutMapping(value = "/{cno}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Long> modfiy(@PathVariable("cno") Long cno, @RequestBody CustomerDTO customerDTO){
		customerDTO.setCno(cno);
		customerService.modify2(customerDTO);
		Map<String, Long> resultMap = new HashMap<>();
		resultMap.put("cno", cno);
		return resultMap;
	}
}
