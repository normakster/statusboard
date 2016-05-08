package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class Writer {

	private Printer printer;

	public void writeJSON(String dir, String name, String fType, JSONObject obj) {
		try (FileWriter writer = new FileWriter(this.createFile(dir, name,
				fType).getAbsoluteFile(), false)) {
			writer.write("" + obj);
			printer.print(PrintAction.DEBUG, obj);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException j) {
			j.printStackTrace();
		} finally {
			// writer.flush();
			// writer.close();
		}
	}

	public void write(String dir, String name, String fType, String str,
			Boolean append) {
		write(setFilename(dir, name, fType),str,append);
	}

	public void write(String fileName, String str, Boolean append) {
		try (FileWriter writer = new FileWriter(this.createFile(fileName).getAbsoluteFile(), append)) {
			writer.write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void write(String dir, String name, String fType,
			ArrayList<String> pArray, Boolean append) {
		this.write(setFilename(dir, name, fType), pArray, append);
	}

	public void write(String fileName, ArrayList<String> pArray, Boolean append) {
		// Build output data
		String outputData = "";
		for (String print : pArray) {
			outputData = outputData + "\r\n"
			// "*split*"
					+ print;
		}

		try (FileWriter writer = new FileWriter(this.createFile(fileName)
				.getAbsoluteFile(), append)) {
			writer.write(outputData
			// .replace("*split*", "\r\n")
			);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public File createFile(String dir, String name, String fType) {
		return createFile(setFilename(dir, name, fType));
	}

	public File createFile(String fileName) {
		// Establish file
		File file = new File(fileName);
		try {
			file.createNewFile();
			return file;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	public String setFilename(String dir, String name, String fType) {
		// Set Defaults
		if (fType == "" || fType == null)
			fType = ".txt";
		if (dir == "" || dir == null)
			dir = "C:\\Users\\Kameron\\Desktop\\";
		if (name == "" || name == null)
			name = "temp";
		return dir + name + fType;
	}

	public void setPrinter(Printer printer) {
		this.printer = printer;
	}
}
