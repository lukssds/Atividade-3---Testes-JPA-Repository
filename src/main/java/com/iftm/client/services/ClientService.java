package com.iftm.client.services;

import java.time.Instant;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iftm.client.dto.ClientDTO;
import com.iftm.client.entities.Client;
import com.iftm.client.repositories.ClientRepository;
import com.iftm.client.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository repository;
	
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
		Page<Client> list =  repository.findAll(pageRequest);
		return list.map(x -> new ClientDTO(x));
	}
	

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> obj = repository.findById(id);
		Client entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new ClientDTO(entity);
	}
	
	@Transactional
	public Page <ClientDTO> findByName(String name, PageRequest pageRequest) {
		Page<Client> obj = repository.findClientByName(name, pageRequest);
		return obj.map(x -> new ClientDTO(x));		
	}
	
	@Transactional
	public Page <ClientDTO> findByBirthDate(Instant date, PageRequest pageRequest) {
		Page<Client> obj = repository.findClientByBirthDate(date, pageRequest);
		return obj.map(x -> new ClientDTO(x));		
	}
	
	@Transactional
	public ClientDTO insert(ClientDTO dto) {
		Client entity = dto.toEntity();
		entity = repository.save(entity);
		return new ClientDTO(entity);
	}
	
	@Transactional
	public ClientDTO update(Long id, ClientDTO dto) {
		try {
			Client entity = repository.getOne(id);
			updateData(entity, dto);
			entity = repository.save(entity);
			return new ClientDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}
	

	private void updateData(Client entity, ClientDTO dto) {
		entity.setName(dto.getName());
		entity.setCpf(dto.getCpf());
		entity.setIncome(dto.getIncome());
		entity.setBirthDate(dto.getBirthDate());
		entity.setChildren(dto.getChildren());
	}

}
