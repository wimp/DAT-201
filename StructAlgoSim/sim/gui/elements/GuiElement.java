package sim.gui.elements;

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
		setVisible(true);
	}
}
