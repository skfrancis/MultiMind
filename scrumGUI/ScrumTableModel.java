package scrumGUI;

import java.sql.*;
import javax.swing.event.*;
import javax.swing.table.*;

/**
 * This class takes a JDBC ResultSet object and implements the TableModel
 * interface in terms of it so that a Swing JTable component can display the
 * contents of the ResultSet.  Note that it requires a scrollable JDBC 2.0 
 * ResultSet.  Also note that it provides read-only access to the results
 **/
public class ScrumTableModel implements TableModel {
	private ResultSet results;
	private ResultSetMetaData metaData;
	private int numColumns, numRows;
	
	
	/**
     * This constructor creates a TableModel from a ResultSet.
     **/
	public ScrumTableModel(ResultSet results){
		this.results = results;
		try {
			metaData = this.results.getMetaData();
			numColumns = metaData.getColumnCount();
			this.results.last();
			numRows = this.results.getRow();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * These two TableModel methods return the size of the table
	 **/
	public int getColumnCount() {
		return numColumns;
	}
	public int getRowCount() {
		return numRows;
	}
	
	/**
	 * This TableModel method specifies the data type for each column.  
	 * We could map SQL types to Java types, but instead we are just
	 * converting all the returned data to strings.
	 **/
	public Class<String> getColumnClass(int columnIndex) {
		return String.class;
	}
	
	/**
     * This method returns the value of each column name of the table.
     * We use strings in this case.  If anything goes wrong, we return
     * the exception as a string, so it will be displayed in the table.
     * Note that SQL column numbers start at 1, but TableModel column
     * numbers start at 0.
     **/
	public String getColumnName(int columnIndex) {
		try {
			return metaData.getColumnLabel(columnIndex + 1);
		} catch (SQLException e) {
			return e.toString();
		}
	}
	
	/**
     * This is the key method of the TableModel: it returns the value at each cell
     * of the table.  We use strings in this case.  If anything goes wrong, we
     * return the exception as a string, so it will be displayed in the table.
     * Note that SQL row and column numbers start at 1, but TableModel column
     * numbers start at 0.
     **/
	public Object getValueAt(int rowIndex, int columnIndex) {
		try {
			results.absolute(rowIndex + 1);
			Object value = results.getObject(columnIndex + 1); 
		    if (value == null){
		    	return null;       
		    }
		    else return value.toString();
		} catch (SQLException e) {
			return e.toString();
		}
	}
	
	/**
	 * The table cells can not be modified by the user as they are set when
	 * the Scrum Report Document was created therefore these following methods
	 * do not need to be implemented for usage.
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex) {return false;}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {}
	
	public void addTableModelListener(TableModelListener l) {}
	
	public void removeTableModelListener(TableModelListener l) {}

	

}
