package DAO;

public abstract class DAOFactory {
	
	public static UserDAORelational getUserDAO(){
		return new UserDAORelational();
	}

	public static AppDAORelational getAppDAO(){
		return new AppDAORelational();
	}
	
	public static DevelopersDAORelational getDevelopersDAO(){
		return new DevelopersDAORelational();
	}
}
