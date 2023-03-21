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
public class RecipeStockDTO {
	
	private Long rsno;
	
	@NotNull
	private Long rno;
	
	@NotNull
	private Long ino;
	
	@NotEmpty
	private String rsnumber;
	
	private LocalDateTime regDate, modDate;
}
