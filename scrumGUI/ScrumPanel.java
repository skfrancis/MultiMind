package scrumGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;
import database.LifeStream;
import tools.ColorScheme;
import tools.SessionTracker;

public class ScrumPanel extends JPanel {
	
	private static final long serialVersionUID = -1297126204342873465L;
	private JPanel scrumPanel;
	private JPanel buttonsPanel;
	private JTable scrumTable;
	private JTextArea scrumTextArea;
	private JSplitPane splitPane;
	private JButton addButton;
	private JButton editButton;
	
	/**
	 * Constructor builds the Scrum Report Panel
	 * adding all the components for usage.
	 */
	public ScrumPanel() {
		super(false);
		scrumPanel = buildScrumPanel();
		buttonsPanel = buildButtonPanel();
        this.setLayout(new BorderLayout());
        this.add(scrumPanel,BorderLayout.CENTER);
        this.add(buttonsPanel,BorderLayout.SOUTH);
	}

	/**
	 * This method will build the Scrum Report message table containing all
	 * messages stored in the database.  It has a table view and a
	 * textual area to show the actual report message and work done by the user.
	 */
	private JPanel buildScrumPanel() {
		JPanel panel = new JPanel();
		panel.setBackground(ColorScheme.getPanelBackground());
		scrumTable = populateTable();
		scrumTable.getSelectionModel().addListSelectionListener(activateTableListener());
		scrumTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrumTextArea = new JTextArea();
		scrumTextArea.setLineWrap(true);
		scrumTextArea.setWrapStyleWord(true);
		scrumTextArea.setBackground(ColorScheme.getEditorBackground());
		scrumTextArea.setForeground(ColorScheme.getEditorForeground());
		scrumTextArea.setEditable(false);
		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(scrumTable),new JScrollPane(scrumTextArea));
		panel.setLayout(new BorderLayout());
		panel.add(splitPane, BorderLayout.CENTER);
		splitPane.setDividerLocation(300);
		return panel;
	}
	
	/**
	 * This method builds the Scrum report buttons for usage.
	 */
	private JPanel buildButtonPanel() {
		JPanel panel = new JPanel();
		panel.setBackground(ColorScheme.getPanelBackground());
		addButton = new JButton(new ImageIcon("images/addscrum.png"));
		addButton.setToolTipText("Add Scrum Report");
		addButton.setBackground(ColorScheme.getPanelBackground());
		addButton.setForeground(ColorScheme.getPanelForeground());
		addButton.setFocusable(false);
		addButton.setBorderPainted(false);
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				AddReport addFrame = new AddReport();
				addButton.setEnabled(false);
				addFrame.addWindowListener(new WindowListener() {
					public void windowActivated(WindowEvent arg0) {}
					@Override
					public void windowClosed(WindowEvent arg0) {
						if(SessionTracker.isScrumReportNeeded()){
							addButton.setEnabled(true);
						}
						scrumTable = populateTable();
						splitPane.setTopComponent(new JScrollPane(scrumTable));
						scrumTable.getSelectionModel().addListSelectionListener(activateTableListener());
						scrumTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					}
					public void windowClosing(WindowEvent arg0) {}
					public void windowDeactivated(WindowEvent arg0) {}
					public void windowDeiconified(WindowEvent arg0) {}
					public void windowIconified(WindowEvent arg0) {}
					public void windowOpened(WindowEvent arg0) {}
            	});
				addFrame.setVisible(true);
			}
			
		});
		editButton = new JButton(new ImageIcon("images/editscrum.png"));
		editButton.setToolTipText("Edit Scrum Report");
		editButton.setBackground(ColorScheme.getPanelBackground());
		editButton.setForeground(ColorScheme.getPanelForeground());
		editButton.setBorderPainted(false);
		editButton.setEnabled(false);
		editButton.setFocusable(false);
		editButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				EditReport editFrame = new EditReport((String) scrumTable.getValueAt(scrumTable.getSelectedRow(),0));
				editButton.setEnabled(false);
				addButton.setEnabled(false);
				editFrame.addWindowListener(new WindowListener() {
					public void windowActivated(WindowEvent arg0) {}
					@Override
					public void windowClosed(WindowEvent arg0) {
						scrumTable = populateTable();
						splitPane.setTopComponent(new JScrollPane(scrumTable));
						scrumTable.getSelectionModel().addListSelectionListener(activateTableListener());
						scrumTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						scrumTextArea.setText("");
						if(SessionTracker.isScrumReportNeeded()){
							addButton.setEnabled(true);
						}
					}
					public void windowClosing(WindowEvent arg0) {}
					public void windowDeactivated(WindowEvent arg0) {}
					public void windowDeiconified(WindowEvent arg0) {}
					public void windowIconified(WindowEvent arg0) {}
					public void windowOpened(WindowEvent arg0) {}
            	});
				editFrame.setVisible(true);
			}
		});
		panel.add(addButton);
		panel.add(editButton);
		
		return panel;
	}
	
	/**
	 * This method will populate the Scrum Table with the database entries.
	 * @return
	 */
	private JTable populateTable() {
		JTable table = new JTable();
		ResultSet results;
	
		String query = "SELECT reportID as \"Report ID\", users.Name as \"User\"," +
					   "CP.Name as \"Composite Personae\", date as \"Date Last Modified\" " +
					   "FROM CP, users, reports WHERE reports.userID = users.userID AND " +
					   "users.CPID = CP.CPID ORDER BY reportID";
		
		table.setCellSelectionEnabled(false);
		try {
			results = LifeStream.getQuery(query, true);
			table = new JTable(){
				private static final long serialVersionUID = -5162317842411459624L;

				public Component prepareRenderer (TableCellRenderer renderer,int Index_row, int Index_col) {
					Component component = super.prepareRenderer(renderer, Index_row, Index_col);
					//even index, selected or not selected
					if (isRowSelected(Index_row)) {
						component.setBackground(new Color(150,200,255));
					} 
					else  if (Index_row % 2 == 0 ){
						component.setBackground(new Color (230,230,230));
					} else {
						component.setBackground(Color.white);
					}
					return component;
				}
			};
			table.setModel(new ScrumTableModel(results));
			results.last();
			return table;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return table;
	}
		
	/**
	 * This method sets up the listener for the Scrum table
	 * to display the current report selected in the text area
	 * below the message list.
	 * @return
	 */
	private ListSelectionListener activateTableListener(){
		ListSelectionListener listener = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				int userID;
				String row = (String) scrumTable.getValueAt(scrumTable.getSelectedRow(),0);
				ResultSet results;
				String query = "SELECT userID, text FROM reports WHERE reportID =" + row; 
				try {
					results = LifeStream.getQuery(query, false);
					results.next();
					userID = Integer.parseInt(results.getString(1));
					if(userID == SessionTracker.getUserID()){
						editButton.setEnabled(true);
					} else {
						editButton.setEnabled(false);
					}
					scrumTextArea.setText(results.getString(2));
				} catch (SQLException exception) {
					exception.printStackTrace();
				}
			}
		};
		return listener;
	}
}
