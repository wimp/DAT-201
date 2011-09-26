package sim.gui.elements;

import javax.swing.JTextField;

public class GuiVariable extends GuiElement{
	private JTextField t;

	public GuiVariable(String value, boolean editable){
		super();
		t = new JTextField(value);
		t.setEditable(editable);
		add(t);
	}
	public void setValue(String value){
		t.setText(value);
	}
}
