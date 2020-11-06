package database;

import java.sql.*;

import application.Configuration;

public class LifeStream {
	/**
	 * These Strings are used for setting up the database connection
	 * dbDriver is the java driver used to load its database drivers
	 * dbCommand is the command for connecting to the database server
	 * dataBase is the actual name of the database stored on the server 
	 */
	private static final String dbDriver = "com.mysql.jdbc.Driver";
	private static final String dbCommand = "jdbc:mysql://";
	
	/**
	 * Database server information setting variables
	 */
	private static String url;
	private static String dbHost;
	private static String userName;
	private static String passWord;
	private static Statement statement = null;
	private static Connection dbConnection = null;

	
	private LifeStream () {}
	
	/**
	 * This method populates the database parameters settings
	 * for the LifeStream database connection.
	 * @param userName
	 * @param passWord
	 * @param dbPort
	 * @param dbHost
	 * @param dataBase
	 */
	public static boolean initSettings(){
		try {
			Class.forName(dbDriver);
		}
		catch (ClassNotFoundException e) {
            System.err.println("*** Class Not Found Exception: Error loading mySQL JDBC driver.");
            System.exit(-1);
		}
		return loadConfig();
	}
	
	/**
	 * This method will make a connection to the database for usage
	 * @throws SQLException
	 */
	public static void openConnection() throws SQLException{
		if(dbConnection != null){
			dbConnection.close();
		}
        dbConnection = DriverManager.getConnection (url, userName, passWord);
	}
	
	public static Connection getConnection(){
		return dbConnection;
	}
	
	/**
	 * This method will commit any changes and then close the database
	 * connection.
	 * @throws SQLException
	 */
	public static void closeConnection () throws SQLException {
		// dbConnection.commit();
		dbConnection.close();
	}
	
	
	/**
	 * This method will execute a query on the database
	 * using the passed in String query. Read only is used to create a result set
	 * that is scrollable and read only (Used in conjunction with Scrum Reports).
	 * openConnection must be called before executing this method
	 * @param query
	 * @return
	 * @throws SQLException 
	 */
	public static ResultSet getQuery(String query, Boolean readOnly) throws SQLException{
		ResultSet results = null;
		
		statement = null;
		if(readOnly) {
			statement = dbConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		} else {
			statement = dbConnection.createStatement();
		}
		results = statement.executeQuery(query);
		return results;
	}
	
	/**
	 * This method will execute a query on the database
	 * using the passed in String query.  This is used for
	 * inserting data into the database.  The method openConnection
	 * must be called before executing this method.
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	public static boolean setQuery(String query) throws SQLException{
		statement = null;
		statement = dbConnection.createStatement();
		return statement.execute(query);
	}
	
	
	/**
	 * This method closes out the connection to the database.
	 * @throws SQLException
	 */
	public static void closeQuery() throws SQLException{
		statement.close();
	}
	
	private static boolean loadConfig() {
		dbHost = Configuration.getHost();
		String dbPort = Configuration.getPort();
		String dataBase = Configuration.getDatabase();
		userName = Configuration.getUser();
		passWord = Configuration.getPassword();
		LifeStream.url = dbCommand + dbHost + ":" + dbPort;
		try {
			if(checkDataBase(dataBase)) {
				LifeStream.url = LifeStream.url + "/" + dataBase;
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Could not load database configuration");
		return false;
	}
		
	private static boolean checkDataBase (String dataBase) throws SQLException {
		Connection dbConnection;
		ResultSet results;
		DatabaseMetaData metaData;
        
		dbConnection = DriverManager.getConnection (url, userName, passWord);
        metaData = dbConnection.getMetaData();
        results = metaData.getCatalogs();
        while (results.next()) {
            if(dataBase.compareTo(results.getString("TABLE_CAT")) == 0) {
            	dbConnection.close();
            	return true;
            }
        }
       	dbConnection.close();
       	return false;
	}
}
