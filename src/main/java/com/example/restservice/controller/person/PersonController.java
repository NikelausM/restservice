package com.example.restservice.controller.person;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.example.restservice.model.person.Person;
import com.example.restservice.util.Utility;

@RestController
public class PersonController {

	private static final String PATH_PREFIX = "/persons";
	private static final String PERSONS_JSON_FILENAME = "/static/json/person/obj.json";
	private static final String NEW_FORM_FILENAME = "/static/html/person/new.html";
	private static final String EDIT_FORM_FILENAME = "/static/html/person/new.html";
	private static final TypeReference<List<Person>> LIST_PERSON_TYPEREFERENCE = new TypeReference<List<Person>>() {
	};

	/**
	 * Index method.
	 * 
	 * Displays all persons.
	 * 
	 * @return
	 */
	@GetMapping(PATH_PREFIX)
	public List<Person> indexGet() {
		List<Person> persons = Utility.readFileToList(PERSONS_JSON_FILENAME, LIST_PERSON_TYPEREFERENCE);
		return persons;
	}

	/**
	 * New method.
	 * 
	 * Shows new form for new person.
	 * 
	 * @return
	 */
	@GetMapping(PATH_PREFIX + "/new")
	public ResponseEntity<String> newGet() {
		String form = Utility.getHtml(NEW_FORM_FILENAME, this);
		ResponseEntity<String> response = ResponseEntity.ok(form);
		return response;
	}

	/**
	 * Create method.
	 * 
	 * Creates a new person.
	 * 
	 * @return
	 */
	@PostMapping(PATH_PREFIX)
	public ResponseEntity<String> createPost(@ModelAttribute Person person) {
		if (person.getId() == 0 || person.getName().equals(null) || person.getAge() == 0) {
			return new ResponseEntity<String>("Invalid data provided, please try again.", HttpStatus.BAD_REQUEST);
		}
		List<Person> persons = Utility.readFileToList(PERSONS_JSON_FILENAME, LIST_PERSON_TYPEREFERENCE);
		ResponseEntity<String> response = new ResponseEntity<String>("Person successfully created.",
				HttpStatus.CREATED);
		for (int i = 0; i < persons.size(); i++) {
			if (persons.get(i).getId() == person.getId()) {
				response = new ResponseEntity<String>("Person already exists.", HttpStatus.BAD_REQUEST);
			}
		}
		return response;
	}

	/**
	 * Show method.
	 * 
	 * Shows one specified person.
	 * 
	 * @return
	 */
	@GetMapping(PATH_PREFIX + "/{id}")
	public ResponseEntity<String> showGet(@PathVariable Long id) {
		List<Person> persons = Utility.readFileToList(PERSONS_JSON_FILENAME, LIST_PERSON_TYPEREFERENCE);
		Person person = null;
		ResponseEntity<String> response = new ResponseEntity<String>("Person not found.", HttpStatus.NOT_FOUND);
		for (int i = 0; i < persons.size(); i++) {
			if (persons.get(i).getId() == id) {
				person = persons.get(i);
				response = ResponseEntity.ok(person.toString());
			}
		}
		return response;
	}

	/**
	 * Edit method.
	 * 
	 * Shows edit form for a specified person.
	 * 
	 * @return
	 */
	@GetMapping(PATH_PREFIX + "/{id}/edit")
	public ResponseEntity<String> editGet(@PathVariable Long id) {
		List<Person> persons = Utility.readFileToList(PERSONS_JSON_FILENAME, LIST_PERSON_TYPEREFERENCE);
		ResponseEntity<String> response = new ResponseEntity<String>("Person not found.", HttpStatus.NOT_FOUND);
		for (int i = 0; i < persons.size(); i++) {
			if (persons.get(i).getId() == id) {
				String form = Utility.getHtml(EDIT_FORM_FILENAME, this);
				response = ResponseEntity.ok(form);
			}
		}
		return response;
	}

	/**
	 * Update method.
	 * 
	 * Updates a specified person.
	 * 
	 * @return
	 */
	@PutMapping(PATH_PREFIX + "/{id}")
	public ResponseEntity<String> updatePut(@PathVariable Long id) {
		List<Person> persons = Utility.readFileToList(PERSONS_JSON_FILENAME, LIST_PERSON_TYPEREFERENCE);
		ResponseEntity<String> response = new ResponseEntity<String>("Person not found.", HttpStatus.NOT_FOUND);
		for (int i = 0; i < persons.size(); i++) {
			if (persons.get(i).getId() == id) {
				response = ResponseEntity.ok("Person successfully updated.");
			}
		}
		return response;
	}

	/**
	 * Destroy method.
	 * 
	 * Deletes a specified person.
	 * 
	 * @return
	 */
	@DeleteMapping(PATH_PREFIX + "/{id}")
	public ResponseEntity<String> destroyDelete(@PathVariable Long id) {
		List<Person> persons = Utility.readFileToList(PERSONS_JSON_FILENAME, LIST_PERSON_TYPEREFERENCE);
		ResponseEntity<String> response = new ResponseEntity<String>("Person not found.", HttpStatus.NOT_FOUND);
		for (int i = 0; i < persons.size(); i++) {
			if (persons.get(i).getId() == id) {
				response = ResponseEntity.ok("Person successfully deleted.");
			}
		}
		return response;
	}

}