package sim.gui.elements;

import javax.swing.JPanel;

public abstract class GuiElement extends JPanel {
	
	public GuiElement(){
		setSize(100, 300);
		setVisible(true);
	}
	
}
