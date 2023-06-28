package com.tsti.repository;

import com.tsti.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long>  {

    Optional<Client> findByDocument(Long document);
}
