package sim.structures;

import java.awt.Rectangle;

import sim.gui.elements.GuiInfo;

/**
 * Informationpanel using a JTextArea. Useful for displaying demo-usage info and various algorithm explanations
 *
 */
public class InfoPanel {
	GuiInfo gui;
	
	/**
	 * Returns the GuiElement component for adding to JFrame/JPanel
	 * @return
	 */
	public GuiInfo getGuiElement() {
		return gui;
	}
	
	/**
	 * Sets the GuiElement
	 * @param gui
	 */
	public void setGuiElement(GuiInfo gui) {
		this.gui = gui;
	}
	
	/**
	 * Sets whether or not the textarea is editable or not
	 * @param editable
	 */
	public void setEditable(boolean editable){
			gui.getTextArea().setEditable(editable);
	}
	/**
	 * Gets whether or not the textarea is editable or not
	 * @return Editable boolean
	 */
	public boolean getEditable(){
		return gui.getTextArea().isEditable();
}
	/**
	 * Constructor
	 * @param bounds - Rectangle containing boundary arguments x,y,width,height
	 */
	public InfoPanel(Rectangle bounds){
		gui = new GuiInfo(bounds, "");
	}
	/**
	 * Gets text within the textarea
	 * @return String containing all text within the InfoPanel
	 */
	public String getValue(){
		return gui.getTextArea().getText();
	}
	/**
	 * Updates the text contents of the textarea
	 * @param value
	 */
	public void setValue(String value){
		gui.getTextArea().setText(value);
	}
	
	/**
	 * Constructor. Used for programmatic instantiation of the object
	 * @param bounds
	 * @param text
	 * @param editable
	 */
	public InfoPanel(Rectangle bounds, String text, boolean editable){
		gui = new GuiInfo(bounds, text);
		gui.getTextArea().setEditable(editable);
	}
}
