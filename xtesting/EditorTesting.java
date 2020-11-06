package xtesting;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;

import java.awt.*;              //for layout managers and more
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class EditorTesting extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8361484535199829042L;
	/**
	 * 
	 */
	
    private JTextPane textPane;
    MutableAttributeSet attr;
    private JPanel buttonPane;
    private JButton saveButton;
    private AbstractDocument doc;
    private String fileName = "testdoc.mmd";

    public EditorTesting() {
    	
    	this.setLayout(new BorderLayout());
         //Create a text pane.
     	textPane = createTextPane();
     	attr = new SimpleAttributeSet();
     	StyleConstants.setForeground(attr, Color.DARK_GRAY);
		textPane.setCharacterAttributes(attr, true);
     	textPane.addCaretListener(new MyCaretListener());
     	saveButton = new JButton("Save");
     	saveButton.addActionListener(new SaveActionListener());
     	buttonPane = new JPanel();
     	buttonPane.add(saveButton);
     	
     	JMenu styleMenu = createStyleMenu();
        JMenuBar mb = new JMenuBar();

        mb.add(styleMenu);
        setJMenuBar(mb);
     	
        JScrollPane paneScrollPane = new JScrollPane(textPane);
        paneScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        paneScrollPane.setPreferredSize(new Dimension(500, 500));
        paneScrollPane.setMinimumSize(new Dimension(10, 10));
        getContentPane().add(paneScrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
   }
    
    protected JMenu createStyleMenu() {
        JMenu menu = new JMenu("Colors");

        menu.add(new StyledEditorKit.ForegroundAction("Red", Color.red));
        menu.add(new StyledEditorKit.ForegroundAction("Green", Color.green));
        menu.add(new StyledEditorKit.ForegroundAction("Blue", Color.blue));
        menu.add(new StyledEditorKit.ForegroundAction("Black", Color.black));
        return menu;
    } 

    private JTextPane createTextPane() {
    	JTextPane textPane = new JTextPane();
      

        // Original Document Testing used when file was created for reference purposes
		        
        doc = new DefaultStyledDocument();
        Style regular = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
        ((DefaultStyledDocument) doc).addStyle("default", regular);
        ((DefaultStyledDocument) doc).setLogicalStyle(0, regular);
        StyleConstants.setForeground(regular, Color.BLUE);

    	StyledDocument styledDoc = textPane.getStyledDocument();
        if (styledDoc instanceof AbstractDocument) {
            doc = (AbstractDocument)styledDoc;
        } else {
            System.err.println("Text pane's document isn't an AbstractDocument!");
            System.exit(-1);
        }

        loadDoc();
        
        doc.addDocumentListener(new MyDocumentListener());  // adds listeners for the document 
        textPane.setStyledDocument((StyledDocument)doc);	// adds the created document to the JTextPane
        textPane.setCaretPosition(0);						// Places the cursor at the beginning of the document
        return textPane;
   }
   
   private void loadDoc(){
	   	try {
	   		FileInputStream rawBytes = new FileInputStream(fileName);
	   		ObjectInputStream inFile = new ObjectInputStream(rawBytes);
	   		doc = (AbstractDocument) inFile.readObject();
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
  }
   
   private class SaveActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			FileOutputStream bytesToDisk = new FileOutputStream(fileName);
			ObjectOutputStream outFile = new ObjectOutputStream(bytesToDisk);
			outFile.writeObject(doc);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	   
   }
   
   private class MyCaretListener implements CaretListener{

	@Override
	public void caretUpdate(CaretEvent arg0) {
	//	System.out.println("Caret Changed\n");
		StyleConstants.setForeground(attr, Color.DARK_GRAY);
		textPane.setCharacterAttributes(attr, true);
	}
   }
   
   private class MyDocumentListener implements DocumentListener {

	@Override
	public void changedUpdate(DocumentEvent arg0) {
//		System.out.println("Document Changed\n");
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
//		System.out.println("Document inserted");
		
	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
//		System.out.println("Document removed");
		
	}
   }

   /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        
        EditorTesting frame = new EditorTesting();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                 //Turn off metal's use of bold fonts
		UIManager.put("swing.boldMetal", Boolean.FALSE);
		createAndShowGUI();
            }
        });
    }
}
