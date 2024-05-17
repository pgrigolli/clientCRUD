package com.clientcrud.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clientcrud.demo.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{
    
}
