package io.pivotal.demo.repository.integration;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import io.pivotal.demo.ContactDataServiceApplication;
import io.pivotal.demo.domain.Contact;
import io.pivotal.demo.repository.ContactRepository;
import junit.framework.TestCase;

/**
 * Integration tests for the <code>ContactRepository</code>
 * JPA repository interface.
 * 
 * The <code>SpringApplicationConfiguration</code> annotation
 * ensures that the embedded database is started and configured
 * for the integration tests.
 * 
 * Most of the methods tested (<code>findOne</code>, <code>save</code>),
 * are provided by the base JpaRepository class.
 * 
 * @author Jignesh Sheth
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ContactDataServiceApplication.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@WebAppConfiguration
public class ContactRepositoryTests {

	//The repository to test.
	@Autowired
	ContactRepository contactRepo;
	
	/**
	 * Tests the repository's findAll method, by asserting that
	 * there is more than 0 replies returned.
	 */
	@Test
	public void testFindAll() {
		Iterable<Contact> contacts = contactRepo.findAll();
		TestCase.assertNotNull(
				"Find all should return at least 1 result.",
				contacts.iterator().next());
	}

	/**
	 * Tests the repository's findByFirstNameOrLastName method, by
	 * getting the first contact from findAll, and then 
	 * using that contact's firstName to call and assert the 
	 * findByFirstNameOrLastName method's results.
	 */
	@Test
	public void testfindByFirstNameOrLastName() {
		
		Contact firstContact = contactRepo.findAll().iterator().next();
		List<Contact> resultOfFindByFirstNameOrLastName = contactRepo.findByFirstNameOrLastName(firstContact.getFirstName(),firstContact.getLastName());
		TestCase.assertEquals(
				firstContact.getFirstName(), 
				resultOfFindByFirstNameOrLastName.get(0).getFirstName());
		
	}

	/**
	 * Tests the repository's save method, by
	 * creating a new Contact object, saving it,
	 * fetching it back from the repository, and
	 * asserting that it was fetched properly.
	 */
	@Test
	public void testSaveNew() {
		
		final String title = "title";
		final String firstName = "firstName";
		final String lastName = "lastName";
		final String email = "email";
		final String phone = "phone";
		Contact newContact = new Contact(title, firstName, lastName, email, phone);
		newContact = contactRepo.save(newContact);
		Contact savedContact = 
				contactRepo.findOne(newContact.getId());
		TestCase.assertEquals(
				title, savedContact.getTitle());
		TestCase.assertEquals(
				firstName, savedContact.getFirstName());
		TestCase.assertEquals(
				lastName, savedContact.getLastName());
		TestCase.assertEquals(
				email, savedContact.getEmail());
		TestCase.assertEquals(
				phone, savedContact.getPhone());
	}

	/**
	 * Tests the repository's update method, by
	 * creating a new Contact object, saving it,
	 * fetching it back from the repository, and 
	 * updating the attributes and asserting
	 * that it was fetched properly.
	 */
	@Test
	public void testUpdateExisting() {
		
		final String title = "title";
		final String firstName = "firstName";
		final String lastName = "lastName";
		final String email = "email";
		final String phone = "phone";
		Contact newContact = new Contact(title, firstName, lastName, email, phone);
		newContact = contactRepo.save(newContact);
		Contact updatedContact = newContact;
		updatedContact.setTitle(title+"_updated");
		updatedContact.setFirstName(firstName+"_updated");
		updatedContact.setLastName(lastName+"_updated");
		updatedContact.setEmail(email+"_updated");
		updatedContact.setPhone(phone+"_updated");
		updatedContact = contactRepo.save(updatedContact);
		Contact savedContact = 
				contactRepo.findOne(updatedContact.getId());
		TestCase.assertEquals(
				title+"_updated", savedContact.getTitle());
		TestCase.assertEquals(
				firstName+"_updated", savedContact.getFirstName());
		TestCase.assertEquals(
				lastName+"_updated", savedContact.getLastName());
		TestCase.assertEquals(
				email+"_updated", savedContact.getEmail());
		TestCase.assertEquals(
				phone+"_updated", savedContact.getPhone());
	}

	/**
	 * Tests the repository's delete method, by
	 * creating a new Contact object, saving it 
	 * and then deleting it and then trying to
	 * fetch it back from the repository, and
	 * asserting that it was not available.
	 */
	@Test
	public void testDelete() {
		
		final String title = "title";
		final String firstName = "firstName";
		final String lastName = "lastName";
		final String email = "email";
		final String phone = "phone";
		Contact newContact = new Contact(title, firstName, lastName, email, phone);
		newContact = contactRepo.save(newContact);
		Long contactID = newContact.getId();
		contactRepo.delete(contactID);
		Contact savedContact = 
				contactRepo.findOne(contactID);
		TestCase.assertNull(savedContact);
	}
}
