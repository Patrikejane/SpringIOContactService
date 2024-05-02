package com.loollablk.contactservice.repository;

import com.loollablk.contactservice.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author sskma
 * @Created 02/05/2024 - 2:18 AM
 * @project contactservice
 */

@Repository
public interface ContactRepository extends JpaRepository<Contact,String> {

    Optional<Contact> findById(String id);
}
