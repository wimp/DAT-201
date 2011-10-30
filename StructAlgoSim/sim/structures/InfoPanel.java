package sim.structures;

import java.awt.Rectangle;

import javax.swing.JTextArea;

import sim.gui.elements.GuiElement;
import sim.gui.elements.GuiInfo;

public class InfoPanel {
	GuiInfo gui;
	
	public GuiInfo getGuiElement() {
		return gui;
	}

	public void setGuiElement(GuiInfo gui) {
		this.gui = gui;
	}

	public InfoPanel(Rectangle bounds, String text, boolean editable){
		gui = new GuiInfo(bounds, text);
		gui.getTextArea().setEditable(editable);
	}
}
