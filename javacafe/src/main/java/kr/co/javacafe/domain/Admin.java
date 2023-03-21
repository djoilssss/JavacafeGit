package kr.co.javacafe.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

 

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "roleSet")
public class Admin {
	
	
	
	@Id
	@Column(length = 100, nullable = false)
	private String id;
	
	@Column(length = 100, nullable = false)
	private String pw;
	
	@ElementCollection(fetch = FetchType.LAZY)
	@Builder.Default
	private Set<AdminRole> roleSet = new HashSet<>();
	
	
	public void change(String pw) {
		this.pw = pw;
	}
	
	public void addRole(AdminRole adminRole) {
		this.roleSet.add(adminRole);
	}
	
	public void clearRoles() {
		this.roleSet.clear();
	}
}
