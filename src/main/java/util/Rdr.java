package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Rdr {

	public String read(String fileName) {
		String obj = "";//"{\"name\":\"Kameron\"}";

		try {
			FileReader file = new FileReader(fileName);
			BufferedReader reader = new BufferedReader(file);
			if (reader.ready()) {
//				printer.print(PrintAction.DEBUG, "\n\n\n\n" + "Reader ready"
//						+ "\n\n\n\n");
				String everything = "";
				String newLine = reader.readLine();
				while (newLine != null) {
					everything += newLine;
					newLine = reader.readLine();
				}
				obj = everything;
//				printer.print(PrintAction.DEBUG, "\n\n\n\n" + everything
//						+ "\n\n\n\n");
			}
			// reader.flush();
			reader.close();
			return obj;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return obj;
	}
}
