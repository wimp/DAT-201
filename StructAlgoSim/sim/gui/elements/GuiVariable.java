package sim.gui.elements;

import javax.swing.JTextField;

public class GuiVariable extends GuiElement{
	private JTextField t;

	public GuiVariable(int x, int y, int w, int h,String value, boolean editable){
		super();
		t = new JTextField(value);
		t.setEditable(editable);
		add(t);
		setBounds(x,y,w,h);
	}
	public void setValue(String value){
		t.setText(value);
	}
	public String getValue(){
		return t.getText();
	}
}
