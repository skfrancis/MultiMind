package tools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class DocumentEditor extends JTextPane {

	private static final long serialVersionUID = 1519496210812762809L;
	
	private StyledDocument document;
	private MutableAttributeSet attributes;
	
	public DocumentEditor(){
		attributes = new SimpleAttributeSet();
		StyleConstants.setForeground(attributes, ColorScheme.getEditorForeground());
		this.setCharacterAttributes(attributes, true);
		document = this.getStyledDocument();
		this.setDocument(document);
		this.addCaretListener(new DocumentCaretListener());
		this.setCaretPosition(0);
	}
		
	public void loadDocument(String fileName){
		// TODO Check to see if file exists before trying to load
		try {
			FileInputStream rawBytes = new FileInputStream(fileName);
			ObjectInputStream inFile = new ObjectInputStream(rawBytes);
			document = (StyledDocument) inFile.readObject();
			inFile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setDocument(document);
	}
	
	public void saveDocument(String fileName){
		// TODO Check to see if file exists before trying to save
		try {
			FileOutputStream bytesToDisk = new FileOutputStream(fileName);
			ObjectOutputStream outFile = new ObjectOutputStream(bytesToDisk);
			outFile.writeObject(document);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private class DocumentCaretListener implements CaretListener {
		@Override
		public void caretUpdate(CaretEvent event) {
			// TODO Auto-generated method stub
			// Need to set up editor colors 
			StyleConstants.setForeground(attributes, ColorScheme.getDocForeground());
			StyleConstants.setBackground(attributes, ColorScheme.getDocBackground());
			setCharacterAttributes(attributes, true);
		}
	}
}
