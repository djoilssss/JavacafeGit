package kr.co.javacafe.repository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import kr.co.javacafe.domain.Admin;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Admin, String>{
	
	//중복아이디 찾기
	@EntityGraph(attributePaths = "roleSet")
	@Query("select m from Admin m where m.id =:id")
	Optional<Admin> getWithRoles(@Param("id") String id);
}
