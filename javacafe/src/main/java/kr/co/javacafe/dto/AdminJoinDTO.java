package kr.co.javacafe.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminJoinDTO {
	
 
	private String id;
	private String pw;
}
