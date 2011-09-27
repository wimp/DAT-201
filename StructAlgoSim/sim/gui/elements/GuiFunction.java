package sim.gui.elements;

import java.awt.Rectangle;

import javax.swing.JButton;



public class GuiFunction extends GuiElement{
// Class variables //
	private static final long serialVersionUID = 1L;
	JButton b;
	
// Class constructor //
	public GuiFunction(Rectangle bounds,String s){
		super();
		b = new JButton(s);
		add(b);
		setBounds(bounds);
	}
	public JButton getButton(){
		return b;
	}
}