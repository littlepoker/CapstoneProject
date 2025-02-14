package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
	@Query(value = "from Token te inner join Users ue on te.userData.id = ue.id where ue.id = :userId and (te.valid = true)")
	List<Token> findAllValidTokenByUsers(int userId);

	Optional<Token> findByToken(String jwtToken);
	
	@Query (value = "delete from Token te where te.userData.id = :userId")
	void deleteByUserId(int userId);
}
