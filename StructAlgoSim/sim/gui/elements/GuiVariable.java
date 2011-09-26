package sim.gui.elements;

import javax.swing.JTextField;

public class GuiVariable extends GuiElement{
	private String value;
	private JTextField t;

	public GuiVariable(String value, boolean editable){
		super();
		this.value = value;
		t = new JTextField(value);
		t.setEditable(editable);
		add(t);
	}
	public void setValue(String value){
		this.value = value;
		t.setText(value);
	}
	public String getValue(){
		return value;
	}
}
