package com.iftm.client.tests.factory;

import java.time.Instant;

import com.iftm.client.dto.ClientDTO;
import com.iftm.client.entities.Client;

public class ClientFactory {
	
	public static Client createClient() {
		return new Client(1L, "Pablo Alberto", "10510824592", 2000.0, Instant.parse("1958-09-20T08:00:00Z"), 1);
	}
	public static Client createEmptyClient(Long id, String name, String cpf, Double income, Instant birthDate,Integer children) {
		return new Client(id, name, cpf, income, birthDate, children);
	}
	public static ClientDTO createClientDTO() {
		return new ClientDTO(createClient());
		
	}
	
}
