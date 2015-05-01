package dininghalls.model;

import java.util.ArrayList;
import java.util.List;

public class DiningHallGroup extends AbstractModelObject {
	private final List halls = new ArrayList();
	private String hallsName;
	
	public DiningHallGroup(){
		
	}
	
	public DiningHallGroup(String name){
		hallsName = name;
	}
	
	public String getName() {
		return hallsName;
	}
	
	public void setName(String name) {
		String oldName = hallsName;
		hallsName = name;
		firePropertyChange("halls", oldName, hallsName);
	}
	
	public void addHall(DiningHall name){
		halls.add(name);
		firePropertyChange("dining hall", null, halls);
	}
	
	public void removeHall(DiningHall name){
		halls.remove(name);
		firePropertyChange("dining hall", null, halls);
	}
	
	public List<DiningHall> getHalls() {
		return halls;
	}
	
}
