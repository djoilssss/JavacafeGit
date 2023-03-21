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
public class ShopStateDTO {

	private Long ssno;
	private String sid;
	private String cphone;
	private Long ssstate;
	private Long sstotal;
	private Long sstoday;
	private LocalDateTime regDate;
	private LocalDateTime modDate;		
}
