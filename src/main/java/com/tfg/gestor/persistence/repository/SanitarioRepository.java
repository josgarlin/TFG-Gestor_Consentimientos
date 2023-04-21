package com.tfg.gestor.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tfg.gestor.persistence.entity.Sanitario;

public interface SanitarioRepository extends JpaRepository<Sanitario, String> {
	
	public Sanitario findByUsernameAndPassword(String username, String password);
	
	@Modifying
	@Query(value = "UPDATE SANITARIO SET STATUS=0 WHERE DNI=:dni", nativeQuery = true)
	public void markInside(@Param("dni") String dni);
}
