package sim.gui.elements;

import java.awt.Rectangle;

import javax.swing.JButton;


/**
 * The graphical part of any function type object such as {@link sim.functions.Pop} and {@link sim.functions.Push}
 * @author 
 */
@SuppressWarnings("serial")
public class GuiFunction extends GuiElement{
// Class variables //
	private JButton b;
	
// Getters and setters //
	public JButton getButton(){
		return b;
	}
	
// Class constructor //
	/**
	 * Creates and adds a button to itself using the function name provided in {@param s}
	 * @param bounds - The size and placement of the graphical element
	 * @param s		 - The button text and function name
	 */
	public GuiFunction(Rectangle bounds,String s){
		super();
		b = new JButton(s);
		add(b);
		setBounds(bounds);
	}
}