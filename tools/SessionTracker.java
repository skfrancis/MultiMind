package tools;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import database.LifeStream;

/**
 * Used to keep track of the time a user logs in and out of the program.
 * Also keeps track of any notable actions performed by the user so that
 * the user will be compelled to fill out a scrum report describing what they did.
 * 
 * Should be called in the form SessionTracker.Init() to initialize, do not attempt to instantiate it.
 */

public class SessionTracker {
	
	private static final String DATE_FORMAT_NOW = "HH:mm:ss MM-dd-yyyy";
	private static SimpleDateFormat sdf;
	private static String signIn;
	private static String signOut;
	private static boolean scrumReportComplete;
	private static int updateNoteCounter;
	private static int commitCounter;
	private static int postCounter;
	private static int currentUser;
	
	private SessionTracker(){}
	
	public static void  Init(int userID){
		sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		signIn = sdf.format(Calendar.getInstance().getTime());
		updateNoteCounter = 0;
		commitCounter = 0;
		postCounter = 0;
		scrumReportComplete = false;
		currentUser = userID;
	}
	
	public static int getUserID() {
		return currentUser;
	}
	
	public static void SessionOver(){
		if(scrumReportComplete){
			signOut = sdf.format(Calendar.getInstance().getTime());
			String query = "insert into session (userID, intime, outtime, updates, commits, posts) values (" + currentUser +
				", \"" + signIn + "\", \"" + signOut + "\"," + updateNoteCounter + "," + commitCounter + "," + postCounter + ")";
			try {
	//			System.out.println(query);
				LifeStream.setQuery(query);
				LifeStream.closeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			// scrum report has not been filed so do not close
		}
	}
	
	/**
	 * Called in various places throughout the program to note if the user does anything
	 */
	
	public static void updateIncrement(){
		updateNoteCounter++;
	}
	
	public static void commitIncrement(){
		commitCounter++;
	}
	
	public static void postIncrement(){
		postCounter++;
	}
	
	/**
	 * Should be called by scrum report creator to link the report with the session.
	 * Program should not be closed if scrumReport remains false
	 * @param ID
	 */
	public static void scrumReportComplete(){
		scrumReportComplete = true;
	}
	
	public static boolean isScrumReportNeeded(){
		return !scrumReportComplete;
	}
	
	public static int changeCount(){
		return updateNoteCounter + postCounter + commitCounter;
	}
	
	public static String getSignInTime(){
		return signIn;
	}

}
