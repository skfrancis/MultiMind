package mainGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;

import projectGUI.ProjectPanel;

import database.LifeStream;
import discussionGUI.DiscussionPanel;

import reportGUI.ReportViewer;
import scrumGUI.ScrumPanel;
import tools.ColorScheme;
import tools.SessionTracker;

public class ProjectCenter extends JFrame {

	private static final long serialVersionUID = -1778186852272145925L;
	
	/**
	 * When adding a new tab, name component based off what its function is
	 * and increment the total panel count
	 */
	private int totalPanels = 4;
	private JComponent projectPanel;
	private JComponent reportsPanel;
	private JComponent scrumPanel;
	private JComponent discussionPanel;
		
	/**
	 * Create the frame.
	 */
	public ProjectCenter() {
		setTitle("Project Center");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(new Dimension(800, 800));
		Point middle = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		setLocation(new Point((int)middle.getX() - 400, (int)middle.getY() - 400));
		setJMenuBar(buildMenu());
		setBackground(ColorScheme.getPanelBackground());
		setForeground(ColorScheme.getPanelForeground());
		addWindowListener(new WindowAdapter() {
        	public void windowClosing(WindowEvent e) {
        		// TODO: Need to check for Scrum report
        	}
        });
		setContentPane(mainPanel());
	}
		
	private JMenuBar buildMenu() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(ColorScheme.getPanelBackground());
		
		JMenu userMenu = new JMenu("User");
		userMenu.setForeground(ColorScheme.getPanelForeground());
		
		JMenuItem logOff = new JMenuItem("Log off");
		logOff.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(endSession()){
					dispose();
				}
			}
		});
		
		JMenuItem exitAction = new JMenuItem("Exit");
		exitAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(endSession()) {
					try {
						LifeStream.closeConnection();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                System.exit(0);
				}
			}
		});
		
		userMenu.add(logOff);
		userMenu.add(exitAction);
				
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setForeground(ColorScheme.getPanelForeground());
		JMenuItem about = new JMenuItem("About");
		helpMenu.add(about);
			
		menuBar.add(userMenu);
		menuBar.add(helpMenu);
			
		return menuBar;
	}
	
	private JPanel mainPanel() {
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainPanel.setLayout(new BorderLayout(0, 0));
		mainPanel.setBackground(ColorScheme.getPanelBackground());
		
		JTabbedPane tabbedPane = new JTabbedPane();
		
		tabbedPane.setBackground(ColorScheme.getPanelBackground());
		tabbedPane.setForeground(ColorScheme.getPanelForeground());
		
		projectPanel = new ProjectPanel();
		projectPanel.setBorder(null);
		tabbedPane.addTab(null, new ImageIcon("images/projects.png"), projectPanel, "Projects");
		
		reportsPanel = new ReportViewer();
		reportsPanel.setBorder(null);
		tabbedPane.addTab(null, new ImageIcon("images/reports.png"), reportsPanel, "Reports");
		
		scrumPanel = new ScrumPanel();
		scrumPanel.setBorder(null);
		tabbedPane.addTab(null, new ImageIcon("images/scrumreport.png"), scrumPanel, "Scrum Reports");
         
		discussionPanel = new DiscussionPanel();
		discussionPanel.setBorder(null);
        tabbedPane.addTab(null, new ImageIcon("images/discussion.png"), discussionPanel, "Discussions");
		
		for(int index = 0; index < totalPanels; index++){
			tabbedPane.setBackgroundAt(index, ColorScheme.getPanelBackground());
    		tabbedPane.setForegroundAt(index, ColorScheme.getPanelForeground());
		}
		
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    	mainPanel.add(tabbedPane);
		return mainPanel;
	}
	
	private boolean endSession() {
		if(SessionTracker.changeCount() > 0 && SessionTracker.isScrumReportNeeded()) {
			JOptionPane.showMessageDialog(new JFrame(),
					"Must complete a scrum report before exiting application.",
				    "Scrum Report Not Completed",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		} else {
			SessionTracker.SessionOver();
			return true;
		}
	}
}
