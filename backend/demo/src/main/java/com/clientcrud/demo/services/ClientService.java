package com.clientcrud.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clientcrud.demo.dto.ClientDTO;
import com.clientcrud.demo.entities.Client;
import com.clientcrud.demo.repositories.ClientRepository;
import com.clientcrud.demo.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;


    @Transactional(readOnly = true)
    public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
        Page<Client> list = repository.findAll(pageRequest);

        return list.map(x -> new ClientDTO(x));
    }


    public ClientDTO findyById(Long id) {

        Optional<Client> obj = repository.findById(id);
        Client entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new ClientDTO(entity);   



    }

    @Transactional
    public ClientDTO insert(ClientDTO dto) {

        Client entity = new Client();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new ClientDTO(entity);

    }
    
    
        public ClientDTO update(Long id, ClientDTO dto) {
            try{

                Client entity = repository.getReferenceById(id);
                copyDtoToEntity(dto, entity);
                entity = repository.save(entity);
                return new ClientDTO(entity);

            }
            catch(EntityNotFoundException e){
                throw new ResourceNotFoundException("Id not found" + id);
            }
            
        }
        
        public void delete(Long id) {
            try{

                repository.deleteById(id);

            }
            catch(EmptyResultDataAccessException e){
                throw new ResourceNotFoundException("Id not found" + id);
            }
            catch(DataIntegrityViolationException e){
                throw new ResourceNotFoundException("Integrity violation");
            }



        }
        
























    private void copyDtoToEntity(ClientDTO dto, Client entity) {

        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setIncome(dto.getIncome());
        entity.setBirthDate(dto.getBirthDate());
        entity.setChildren(dto.getChildren());

    }


    
}
