package reportGUI;

import java.io.IOException;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class ReportViewer extends JPanel {

	private static final long serialVersionUID = -8221098925132110508L;
	
	private JEditorPane reportPane;
	private JScrollPane scrollPane;
	private String reportURL;
	
	
	public ReportViewer() {
		reportURL = "http://statsvn.org/statsvn-html/developers.html";
		try {
			reportPane = new JEditorPane(reportURL);
			reportPane.setEditable(false);
			scrollPane = new JScrollPane(reportPane);
			add(scrollPane);
			setVisible(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
