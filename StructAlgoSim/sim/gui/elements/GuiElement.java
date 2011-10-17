package sim.gui.elements;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("serial")
public abstract class GuiElement extends JPanel{
// Class variables //
	Timer animation;
	public Timer getAnimation() {
		return animation;
	}
	public void startAnimation() {
		if(animation != null)
		animation.start();
	}
	int frame;
	final int MAXFRAME = 6;
// Class constructor //
	/**
	 * Class constructor - sets the instance of the class to visible.
	 */
	public GuiElement(){
		setLayout(new GridLayout(1,1));
		setVisible(true);
	}
}
