package com.iftm.client.repositories;



import java.time.Instant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iftm.client.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
	
	
	@Query("SELECT obj FROM Client obj WHERE " + "LOWER(obj.name) LIKE LOWER(CONCAT('%', ?1,'%'))")
	Page<Client> findClientByName(String name, Pageable pageable);
	
	
	@Query("SELECT obj FROM Client obj WHERE " + "obj.birthDate >= :birthDate")
	Page<Client> findClientByBirthDate(Instant birthDate, Pageable pageable);
	
	
}
