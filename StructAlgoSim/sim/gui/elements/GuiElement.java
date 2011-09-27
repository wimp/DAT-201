package sim.gui.elements;

import java.awt.FlowLayout;

import javax.swing.JPanel;

public abstract class GuiElement extends JPanel {
	
	public GuiElement(){
		setLayout(new FlowLayout());
		setOpaque(false);
		setVisible(true);
	}
}
