package kr.co.javacafe.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Customer extends BaseEntity{
	
//	고객 번호
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cno;
	
//	고객 이름
	@Column(length = 200, nullable = false)
	private String cname;
	
//	고객 전화 번호
	@Column(length = 200, nullable = true)
	private String cphone;

//	고객 포인트
	@Column(length = 200)
	@ColumnDefault("0") //default 0
	private Long cpoint;
	
//	고객 가입 일자는 BaseEntity에서 가져다 사용합니다.

	
	public void change(String cname,String cphone,Long cpoint) {
		this.cname = cname;
		this.cphone = cphone;
		this.cpoint = cpoint;
		
	}
	public void change2(Long cpoint) {
		
		this.cpoint += cpoint;
		
	}
	
	public void change3(Long cpoint) {
		
		this.cpoint = cpoint;
	}
	


}
