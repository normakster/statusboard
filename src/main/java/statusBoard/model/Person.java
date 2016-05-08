package statusBoard.model;

public class Person {
	
	private int id;
	private String name;
	private String crntStatus;
	private String eta;
	private String location;
	private String description;
	private boolean editStatus = false;
	
	public Person() {
		
	}
	
	public Person(int id, String name, String crntStatus, String eta, String location, boolean editStatus) {
		this.id = id; 
		this.name = name;
		this.crntStatus = crntStatus;
		this.eta = eta;
		this.location = location;
		this.editStatus = editStatus;
	}
	
	@Override
	public boolean equals(Object otherObj) {
		if(!otherObj.getClass().equals(this.getClass())) {
			return false;
		}
//		return ((Person)otherObj).getId().equals(this.getId());
		return ((Person)otherObj).getName().equals(this.getName());
	}
	
	@Override
	public String toString() {
		return "{"
				+ "\"id\":\""+id+","
				+ "\"name\":\""+name+","
				+"\"eta\":"+eta+","
				+"\"location\":"+location+","
				+"\"crntStatus\":"+crntStatus+","
				+"\"description\":"+description
				+"}";
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCrntStatus() {
		return crntStatus;
	}
	public void setCrntStatus(String crntStatus) {
		this.crntStatus = crntStatus;
	}
	public String getEta() {
		return eta;
	}
	public void setEta(String eta) {
		this.eta = eta;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isEditStatus() {
		return editStatus;
	}
	public void setEditStatus(boolean editStatus) {
		this.editStatus = editStatus;
	}

}
