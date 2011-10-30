package sim.structures;

import java.awt.Rectangle;

import sim.gui.elements.GuiElement;
import sim.gui.elements.GuiInfo;

public class InfoPanel {
	GuiElement gui;
	
	public GuiElement getGuiElement() {
		return gui;
	}

	public void setGuiElement(GuiElement gui) {
		this.gui = gui;
	}

	public InfoPanel(Rectangle bounds, String text){
		gui = new GuiInfo(bounds, text);
	}
}
