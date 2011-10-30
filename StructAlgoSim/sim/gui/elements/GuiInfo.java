package sim.gui.elements;

import java.awt.Rectangle;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class GuiInfo extends GuiElement {
	
	JTextArea text;
	
	public JTextArea getTextArea() {
		return text;
	}
	public void setTextArea(JTextArea text) {
		this.text = text;
	}

	public GuiInfo(Rectangle bounds, String text){
		this.setBounds(bounds);
		this.text = new JTextArea(text);
		this.text.setLineWrap(true);
		this.text.setWrapStyleWord( true );
		JScrollPane textScroller = new JScrollPane(this.text);
		add(textScroller);
	}
}
