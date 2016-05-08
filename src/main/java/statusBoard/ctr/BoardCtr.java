package statusBoard.ctr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import statusBoard.model.PersonRepo;
import util.PrintAction;
import util.Printer;

@Controller
@RequestMapping(value = "/ioboard")
public class BoardCtr {

	@Autowired
	private Printer printer;

	@Autowired
	private PersonRepo personRepo;


//	@RequestMapping(value = "/api/persons/create", produces = {
//			MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
//	public @ResponseBody Person createPerson(@RequestBody Person person) {
//		print("Creating a new person:  " + person.toString());
//		if (personRepo.createPerson(person)) {
//			print("Success");
//		} else {
//			print("Failed");
//		}
//		return person;
//	}
//
//	@RequestMapping(value = "/api/persons/{name}", produces = {
//			MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET)
//	public ResponseEntity<Person> readPerson(@PathVariable String name) {
//		return new ResponseEntity<Person>(personRepo.findPersonByName(name), HttpStatus.OK);
//	}
//
//	@RequestMapping(value = "/api/persons/{name}/update", method = RequestMethod.POST)
//	public @ResponseBody ResponseEntity<Person> updatePerson(@PathVariable String name, @RequestBody Person person) {
//		return new ResponseEntity<Person>(personRepo.updatePerson(person), HttpStatus.OK);
//	}
//
//	@RequestMapping(value = "/api/persons/{name}/delete", method = RequestMethod.POST)
//	public @ResponseBody ResponseEntity<Person> deletePerson(@PathVariable String name) { // ("name")
//		if (personRepo.deletePerson(personRepo.findPersonByName(name)))
//			return new ResponseEntity<Person>(new Person(), HttpStatus.OK);
//		return new ResponseEntity<Person>(new Person(), HttpStatus.NOT_FOUND);
//	}
//
//	@RequestMapping(value = "/api/persons/all", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET)
//	public ResponseEntity<List<Person>> getAllPersons() {
//		personRepo.refresh();
//		print(personRepo.toString());
//		List<Person> obj = personRepo.get();
//		return new ResponseEntity<List<Person>>(obj, HttpStatus.OK);
//	}
//	
//
//	@RequestMapping(value = "/save", method = RequestMethod.POST)
//	public ResponseEntity<List<Person>> update(@RequestBody List<Person> persons) {
//		// if (persons != null) {
//		personRepo.set(persons);
//		personRepo.save();
//		// }
//		return new ResponseEntity<List<Person>>(persons, HttpStatus.OK);
//	}

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

	@RequestMapping(value = { "/", "/welcome**" }, method = RequestMethod.GET)
	public ModelAndView defaultPage() {

		ModelAndView model = new ModelAndView();
		model.addObject("title", "KM's In and Out Board");
		model.addObject("message", "This is default page!");
		model.addObject("persons", personRepo.get());
		model.setViewName("index");
		return model;

	}

}
