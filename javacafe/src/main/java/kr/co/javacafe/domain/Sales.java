package kr.co.javacafe.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
public class Sales{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sno;
	
	@Column(length = 100, nullable = false)
	private String year;
	
	@Column(length = 100, nullable = false)
	private String month;
	
	@Column(length = 2000, nullable = false)
	private Long sales;
	
	public void change(String year, String month, Long sales) {
		this.year = year;
		this.month = month;
		this.sales = sales;
		 
	}
	
}
