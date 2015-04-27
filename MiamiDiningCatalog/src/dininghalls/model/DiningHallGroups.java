package dininghalls.model;


import java.util.ArrayList;
import java.util.List;

public class DiningHallGroups extends AbstractModelObject {
	private final List hallGroups = new ArrayList();
	
	public void addGroup(DiningHallGroup group){
		hallGroups.add(group);
		firePropertyChange("groups", null, hallGroups);
	}
	
	public void removeGroup(DiningHallGroup group){
		hallGroups.remove(group);
		firePropertyChange("groups", null, hallGroups);
	}
	
	public List getGroups() {
		return hallGroups;
	}
	
}
