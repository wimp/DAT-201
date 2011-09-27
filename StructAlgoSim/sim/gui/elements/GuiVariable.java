package sim.gui.elements;

import java.awt.Rectangle;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class GuiVariable extends GuiElement{
	private JTextField t;

	public GuiVariable(Rectangle bounds,String value, boolean editable){
		super();
		t = new JTextField(value);
		t.setEditable(editable);
		add(t);
		setBounds(bounds);
	}
	public void setValue(String value){
		t.setText(value);
	}
	public String getValue(){
		return t.getText();
	}
}
