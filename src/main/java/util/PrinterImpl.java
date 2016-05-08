package util;

public class PrinterImpl implements Printer {

	private String debug;
	
	@Override
	public void print(PrintAction action, Object obj) {
		// TODO Auto-generated method stub

		switch(action) {
		case CONSOLE: {
			print(PrintAction.PRINT,obj+"");
			break;
		}
		case ERROR: {
			print(PrintAction.PRINT,obj+"");
			break;
		}
		case PRINT: {
			System.out.println(obj+"");
			break;
		}
		case DEBUG: {
			if(this.debug.equals("true"))
				print(PrintAction.PRINT,obj);
			break;
		}
		case LOG: {
			print(PrintAction.PRINT,obj+"");
			break;
		}
		case TEST: {
			print(PrintAction.PRINT,obj+"");
			break;
		}
		default:
			break;
		}
	}

	@Override
	public void setDebug(String debug) {
		this.debug = debug;
	}

	@Override
	public String debugStatus() {
		return this.debug;
	}

}
