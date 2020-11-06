package tools;

import java.awt.Color;

public class ColorScheme {
	private static Color editorBackground;
	private static Color editorForeground;
	private static Color panelBackground;
	private static Color panelForeground;
	private static Color docBackground;
	private static Color docForeground;
	
	/**
	 * This class is only created once when the program is started.
	 * Once it has been created it can be called at any time during the course
	 * of the program cycle.  
	 */
	private ColorScheme(){} 
	
	/**
	 * This method will initialize the program's default color scheme.
	 */
	public static void initColor() {
		editorBackground = Color.WHITE;
		editorForeground = Color.BLACK;
		panelForeground = Color.WHITE;
		panelBackground = Color.BLACK;
		docBackground = panelBackground;
		docForeground = panelForeground;
	}	
	
	/**
	 * This method will get the program panel foreground color for usage.
	 * @return
	 */
	public static Color getPanelForeground(){
		return panelForeground;
	}

	/**
	 * This method will get the program panel background color for usage.
	 * @return
	 */
	public static Color getPanelBackground(){
		return panelBackground;
	}
	
	/**
	 * This method will get the basic editor window foreground color for usage.
	 * @return
	 */
	public static Color getEditorForeground(){
		return editorForeground;
	}
	
	/**
	 * This method will get the basic editor window background color for usage.
	 * @return
	 */
	public static Color getEditorBackground(){
		return editorBackground;
	}
	
	/**
	 * This method will get the modifiable documents foreground color for usage.
	 * @return
	 */
	public static Color getDocForeground(){
		return docForeground;
	}
	
	/**
	 * This method will get the modifiable documents background color for usage.
	 * @return
	 */
	public static Color getDocBackground(){
		return docBackground;
	}
	
	/**
	 * This method will set the modifiable documents foreground color and the
	 * program panel foreground color to the RGB color scheme passed in.
	 * @param red
	 * @param green
	 * @param blue
	 */
	public static void setForeground(int red, int green, int blue){
		docForeground = new Color(red, green, blue);
		panelForeground = docForeground;
	}
		
	/**
	 * This method will set the modifiable documents background color and the
	 * program panel background color to the RGB color scheme passed in.  
	 * @param red
	 * @param green
	 * @param blue
	 */
	public static void setBackground(int red, int green, int blue) {
		docBackground = new Color(red, green, blue);
		panelBackground = docBackground;
	}

}
