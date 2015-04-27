package dininghalls.model;

public class DiningHall extends AbstractModelObject {

	private String hallName = "";
	private String hallLocation = "";
	private String hallAdmin = "";
	private String hallType = "";
	
	public DiningHall(){
		
	}
	
	public DiningHall(String name, String type, String location, String admin){
		this.hallName = name;
		this.hallType = type;
		this.hallLocation = location;
		this.hallAdmin = admin;
	}
	
	public String getName() {
		return this.hallName;
	}
	
	public void setName(String name){
		String oldName = this.hallName;
		this.hallName = name;
		firePropertyChange("dining hall", oldName, this.hallName);
	}
	
	public String getLocation() {
		return this.hallLocation;
	}
	
	public void setLocation(String location){
		String oldLocation = this.hallLocation;
		this.hallLocation = location;
		firePropertyChange("location", oldLocation, this.hallLocation);
	}
	
	public String getType() {
		return this.hallType;
	}
	
	public void setType(String type){
		String oldType = this.hallType;
		this.hallType = type;
		firePropertyChange("type", oldType, this.hallType);
	}
	
	public String getAdmin() {
		return this.hallAdmin;
	}
	
	public void setAdmin(String admin){
		String oldAdmin = this.hallAdmin;
		this.hallAdmin = admin;
		firePropertyChange("admin", oldAdmin, this.hallAdmin);
	}
}
