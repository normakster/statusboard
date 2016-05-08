package util;

public interface Printer {
	
	public void print(PrintAction action, Object obj); 
	public void setDebug(String debug);
	public String debugStatus();

}
