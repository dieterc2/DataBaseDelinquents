package DAO;

public interface UserDAO {
	
	public void insert(String query);
	public void select(String query);
	public void update(String query);
	public void delete(String query);
}
