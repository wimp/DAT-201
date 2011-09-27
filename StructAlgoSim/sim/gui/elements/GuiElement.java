package sim.gui.elements;

import java.awt.FlowLayout;
import javax.swing.JPanel;

/**
 * 
 * @author 
 *
 */
public abstract class GuiElement extends JPanel {
// Class variables //
	private static final long serialVersionUID = 1L; // Auto generated default serial for the JPanel extension

// Class constructor //
	/**
	 * Class constructor - sets the instance of the class visible.
	 */
	public GuiElement(){
		setLayout(new FlowLayout());
		setOpaque(false);
		setVisible(true);
	}
}
