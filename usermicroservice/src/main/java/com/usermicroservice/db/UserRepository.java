package com.usermicroservice.db;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usermicroservice.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
	
	Optional<User> findByEmail(String email);
	boolean existsByEmail(String email);
	List<User> findByRole(String role);
	boolean existsByStatus(String status);
}
