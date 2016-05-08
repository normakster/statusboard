package statusBoard.ctr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import statusBoard.model.Person;
import statusBoard.model.PersonRepo;
import util.PrintAction;
import util.Printer;


//@Import(RepositoryRestMvcConfiguration.class)
@RestController
@RequestMapping(value = "/api")
public class RestCtr {

	@Autowired
	private Printer printer;

	@Autowired
	private PersonRepo personRepo;

	@RequestMapping(value = "/persons/create", produces = {
			MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody Person createPerson(@RequestBody Person person) {
		print("Creating a new person:  " + person.toString());
		if (personRepo.createPerson(person)) {
			print("Success");
		} else {
			print("Failed");
		}
		return person;
	}

	@RequestMapping(value = "/persons/{name}", produces = {
			MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET)
	public ResponseEntity<Person> readPersonByName(@PathVariable String name) {
		return new ResponseEntity<Person>(personRepo.findPersonByName(name), HttpStatus.OK);
	}

	@RequestMapping(value = "/persons/{pid}", produces = {
			MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET)
	public ResponseEntity<Person> readPersonById(@PathVariable int id) {
		return new ResponseEntity<Person>(personRepo.findPersonById(id), HttpStatus.OK);
	}

	@RequestMapping(value = "/persons/{name}/update", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<Person> updatePerson(@PathVariable String name, @RequestBody Person person) {
		return new ResponseEntity<Person>(personRepo.updatePerson(person), HttpStatus.OK);
	}

	@RequestMapping(value = "/persons/{name}/delete", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<Person> deletePerson(@PathVariable String name) { // ("name")
		if (personRepo.deletePerson(personRepo.findPersonByName(name)))
			return new ResponseEntity<Person>(new Person(), HttpStatus.OK);
		return new ResponseEntity<Person>(new Person(), HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/persons/all", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET)
	public ResponseEntity<List<Person>> getAllPersons() {
		personRepo.refresh();
		print(personRepo.toString());
		List<Person> obj = personRepo.get();
		return new ResponseEntity<List<Person>>(obj, HttpStatus.OK);
	}

	public void setPersonRepo(PersonRepo personRepo) {
		this.personRepo = personRepo;
	}

	// @Override
	public void print(Object obj) {
		printer.print(PrintAction.DEBUG, obj);
	}

	// @Override
	public void setPrinter(Printer printer) {
		this.printer = printer;
	}
}
