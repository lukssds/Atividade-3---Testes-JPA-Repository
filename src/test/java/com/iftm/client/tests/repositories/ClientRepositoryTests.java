package com.iftm.client.tests.repositories;

import java.time.Instant;
import java.time.ZoneId;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.iftm.client.dto.ClientDTO;
import com.iftm.client.entities.Client;
import com.iftm.client.repositories.ClientRepository;
import com.iftm.client.tests.factory.ClientFactory;


@DataJpaTest
public class ClientRepositoryTests {
	
	@Autowired
	private ClientRepository repository;
	
	
	private long countTotalClients;
	private String existingName;
	private String nonExistingName;
	private String caseSensitiveName;
	private String emptyName;
	private String nonExistingCpf;
	private Instant date;
	private Instant nonExistingDate;

	
	@BeforeEach
	void setUp() throws Exception {
		
		countTotalClients = 12L;
		existingName= "Conceição Evaristo";
		caseSensitiveName= "cOnceIçÃo evARiSto";
		emptyName="";
		nonExistingName = "lucas Samuel";
		nonExistingCpf= "12975854154";
		date = Instant.parse("1930-09-20T08:00:00Z");
		nonExistingDate =Instant.parse("2021-09-20T08:00:00Z");
		}
	
	// Testando o find para nome existente
	@Test
	public void findNameShouldFindTheObjectNameWhenNameExists() {
		
		PageRequest pageRequest = PageRequest.of(0, 10);
		Page<Client> result = repository.findClientByName(existingName,pageRequest);
		
		Assertions.assertFalse(result.isEmpty());
	}
	
	// Testando o find para nome existente ignorando case
	@Test
	public void findNameShouldFindTheClientNameIgnoringTheCaseSensitiveWhenNameExists() {
		
		PageRequest pageRequest = PageRequest.of(0, 10);
		Page<Client> result = repository.findClientByName(caseSensitiveName,pageRequest);
		
		Assertions.assertFalse(result.isEmpty());
	}
	
	//Testando o find para nome vazio e verificando se retorna todos os clientes
	@Test
	public void findNameShouldFindAllTheClientsNamesIfThePasedNameIsEmpty() {
		
		PageRequest pageRequest = PageRequest.of(0, 10);
		Page<Client> result = repository.findClientByName(emptyName,pageRequest);
		
		Assertions.assertEquals(result.getTotalElements(),countTotalClients);
	}
		
	//Testando o find para data de nascimento maior que determinada data de	referência
	@Test
	public void findByDateOfBirthShouldFindAllClientsWithTheDateOfBirthGreaterThanTheReferenceDate() { 
				
		PageRequest pageRequest = PageRequest.of(0, 15);
		
		Page<Client> result = repository.findClientByBirthDate(date,pageRequest);
		
		Assertions.assertTrue(result.getTotalElements() > 0);
	}
	
	//Testando o update de um cliente
	@Test
	public void updateShouldChangeClientData() { 
		
		Optional<Client> cliente = repository.findById(1L);
		
		cliente.get().setName(nonExistingName);
		cliente.get().setCpf(nonExistingCpf);
		
		
		Assertions.assertTrue(cliente.get().getName().equals(nonExistingName));
		Assertions.assertTrue(cliente.get().getCpf().equals(nonExistingCpf));	
		Assertions.assertTrue(cliente.get().getId().equals(1L));
	}
	
	//Testando se o Update lança exceção quando o Id não é encontrado
	@Test
	public void updateShouldThrowExeptionWhenIdDoNotExists() { 
		
		Optional<Client> cliente = repository.findById(15L);
				
		Assertions.assertThrows(NoSuchElementException.class, ()->{
			cliente.get().getId().equals(25L); 			
		});
}
	
} 