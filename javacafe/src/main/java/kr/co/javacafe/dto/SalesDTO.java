package kr.co.javacafe.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SalesDTO {

	private Long sno;
	
	@NotEmpty
	private String year;
	
	@NotEmpty
	private String month;
	
	@NotNull
	private Long sales;
	
}
