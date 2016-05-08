package core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import statusBoard.ctr.BoardCtr;
import statusBoard.ctr.RestCtr;
import statusBoard.model.PersonRepo;
import util.*;

@Configuration
public class StatusBoardConfig {
	
	@Bean
	public Printer printer() {
		Printer bean = new PrinterImpl();
		bean.setDebug("true");
		return bean;
	}
	
	@Bean
	public Writer writer() {
		Writer bean = new Writer();
		bean.setPrinter(printer());
		return bean;
	}
	
	@Bean
	public Rdr rdr() {
		Rdr bean = new Rdr();
		return bean;
	}
	
	@Bean
	public PersonRepo personRepo() {
		PersonRepo bean = new PersonRepo();
		bean.setPrinter(printer());
		bean.setRdr(rdr());
		bean.setWriter(writer());
//		bean.setFileName("//home//normak//Desktop//temp1.txt");
		bean.setFileName("/opt/web/embedded/temp1.txt");
		bean.initIT();
		return bean;
	}
	
	@Bean
	public BoardCtr boardCtr() {
		BoardCtr bean = new BoardCtr();
		bean.setPrinter(printer());
		bean.setPersonRepo(personRepo());
		return bean;
	}
	
	@Bean
	public RestCtr restCtr() {
		RestCtr bean = new RestCtr();
		bean.setPrinter(printer());
		bean.setPersonRepo(personRepo());
		return bean;
	}

}
