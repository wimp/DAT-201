package sim.gui.elements;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class GuiInfo extends GuiElement {
	//Class variables//
	private JTextArea text;
	//Getters and setters//
	public JTextArea getTextArea() {
		return text;
	}
	public void setTextArea(JTextArea text) {
		this.text = text;
	}
	//Class constructor//
	public GuiInfo(Rectangle bounds, String text){
		this.setBounds(bounds);
		this.text = new JTextArea(text);
		this.text.setLineWrap(true);
		this.text.setWrapStyleWord( true );
		JScrollPane textScroller = new JScrollPane(this.text);
		add(textScroller);
	}
	//Class methods//
	@Override
	public void actionPerformed(ActionEvent e) {

	}
	@Override
	public void startAnimation() {
	}
	@Override
	public void stopAnimation() {

	}
}