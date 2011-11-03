package sim.gui.elements;

import java.awt.Rectangle;

import javax.swing.JButton;


/**
 * The graphical part of any function type object such as {@link sim.functions.Pop} and {@link sim.functions.Push}
 */
@SuppressWarnings("serial")
public class GuiFunction extends GuiElement{
	private JButton b;
	
	public JButton getButton(){
		return b;
	}
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