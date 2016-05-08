package statusBoard.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import util.PrintAction;
import util.Printer;
import util.Rdr;
import util.Writer;

public class PersonRepo {

	private Printer printer;
	private Writer writer;
	private String fileName;
	private File mainFile;
	private Rdr rdr;
	private List<Person> persons = new ArrayList<>();
	private ObjectMapper objectMapper = new ObjectMapper();
	private int newId = 0;

	public Person findPersonByName(String name) {
		Person person = null;
		for (Person per : persons) {
			// print(per.getName());
			if (per.getName().equals(name)) {
				person = per;
				break;
			}
		}
		return person;
	}

	public Person findPersonById(int id) {
		Person person = null;
		for (Person per : persons) {
			// print(per.getName());
			if (per.getId() == id) {
				person = per;
				break;
			}
		}
		return person;
	}

	public boolean personExists(Person person) {
		if (findPersonById(person.getId()) != null) {
			print("ID found: " + findPersonById(person.getId()).toString());
			return true;
		} else if (findPersonByName(person.getName()) != null) {
			print("Name found: " + findPersonByName(person.getName()).toString());
			return true;
		}
		for (Person curPerson : persons) {
			if (curPerson.equals(person)) {
				print("Person found: " + curPerson.getName());
				return true;
			}
		}

		return false;
	}

	public boolean createPerson(Person person) {
		person.setId(newId);// persons.size()
		print("method: createPerson, result: " + person);
		if (personExists(person)) {
			print("Person Existins");
			return false;
		} else {
			print("Person is unique: " + person.getId());
			persons.add(person);
			save();
			return true;
		}
	}

	public Person updatePerson(Person person) {
		if (personExists(person)) {
			for (Person curPerson : persons) {
				if (curPerson.equals(person)) {
					persons.set(persons.indexOf(curPerson), person);
					save();
					return person;
				}
			}
			return null;
		}
		return null;
	}

	public boolean deletePerson(Person person) {
		if (personExists(person)) {
			persons.remove(person);
			save();
			return true;
		} else {
			return false;
		}
	}

	public String toString() {
		StringBuilder outPut = new StringBuilder("[");
		for (int i = 0; i < persons.size(); i++) {
			outPut.append(persons.get(i).toString() + "\n");
			if (i != persons.size() - 1)
				outPut.append(",");
		}
		outPut.append("]");
		return outPut.toString();
	}

	public void save() {
		try {
			objectMapper.writeValue(mainFile, persons);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// writer.write(fileName, str, false);
	}

	public List<Person> get() {
		return persons;
	}

	public void set(List<Person> persons) {
		this.persons = persons;
	}

	public void initIT() {
		refresh();
		print(this.toString());
		// save(persons.toString());
	}
	
	private void updateId() {
		for (Person p : persons) {
			newId = (p.getId() >= newId) ? p.getId() + 1 : newId;
		}
	}

	public void refresh() {
		print("Loading people...");
		try {
			persons = objectMapper.readValue(rdr.read(fileName),
					objectMapper.getTypeFactory().constructCollectionType(List.class, Person.class));
			updateId();
			print("Load Successful. Next id is " + newId);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// return persons;
	}

	public void save(String str) {
		print("Saving people...");
		writer.write(fileName, str, false);
		print("Save Successfull.");
	}

	public void print(Object obj) {
		printer.print(PrintAction.DEBUG, obj);
	}

	public void setPrinter(Printer printer) {
		this.printer = printer;
	}

	public void setRdr(Rdr rdr) {
		this.rdr = rdr;
	}

	public void setWriter(Writer writer) {
		this.writer = writer;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
		mainFile = new File(this.fileName);
	}
}
