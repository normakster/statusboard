package util;

public enum PrintAction { 
	DEBUG("debug"),
	ERROR("error"),
	LOG("log"),
	CONSOLE("console"),
	PRINT("print"),
	TEST("test");
	
	private String value;
	
	private PrintAction(String value) {
		this.value = value;
	}
	
	public String value() {
		return this.value;
	}

}
