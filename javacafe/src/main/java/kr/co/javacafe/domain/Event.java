package kr.co.javacafe.domain;

import lombok.*;
import javax.persistence.*;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Event extends BaseEntity {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long eno;
	
	@Column(length = 500, nullable = false) // 칼럼의 길이와 null 허용 여부
	private String etitle;
	
	@Column(length = 2000, nullable = false)
	private String econtent;
	
	@Column(length = 200, nullable = true)
	private String eimg;
	
	@Column(length = 50, nullable = false)
	private String ewriter;
	
	// Event에서 제목 & 내용 수정이 가능하므로 이에 맞도록 change 메소드 추가
	public void change(String etitle, String econtent, String eimg) {
		this.etitle = etitle;
		this.econtent = econtent;
		this.eimg = eimg;
	}
	
}
