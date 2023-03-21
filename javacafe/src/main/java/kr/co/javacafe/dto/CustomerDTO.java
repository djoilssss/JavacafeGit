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
public class CustomerDTO {
	
//	고객 번호
	private Long cno;
//	고객 이름
	private String cname;
//	고객 전화 번호
	private String cphone;
//	고객 포인트
	private Long cpoint;

	
//	고객 가입 일자는 BaseEntity에서 가져다 사용합니다.
	private LocalDateTime regDate;
	
	private LocalDateTime modDate;

}
