package sim.gui.elements;

import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JPanel;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("serial")
public abstract class GuiElement extends JPanel {
// Class variables //

// Class constructor //
	/**
	 * Class constructor - sets the instance of the class to visible.
	 */
	public GuiElement(){
		setLayout(new GridLayout(1,1));
		setVisible(true);
	}
//	@Override 
//	public void paintComponent(Graphics g){
//		super.paintComponent(g);
//	}
}
