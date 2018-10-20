package io.pivotal.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import io.pivotal.demo.domain.Contact;

public interface ContactRepository extends PagingAndSortingRepository<Contact, Long> {
	
	List<Contact> findAll();

	List<Contact> findByFirstNameOrLastName(@Param("firstName") String firstName, @Param("lastName") String lastName); 
}
