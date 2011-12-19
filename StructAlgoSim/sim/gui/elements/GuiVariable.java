package sim.gui.elements;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class GuiVariable extends GuiElement{
	private JTextField t;

	public GuiVariable(Rectangle bounds,String value, boolean editable){
		super();
		t = new JTextField(value);
		t.setEditable(editable);
		t.setBounds(0,0,bounds.width,bounds.height);
		setBounds(bounds);
		add(t);
		validate();
	}
	public void setValue(String value){
		t.setText(value);
	}
	public String getValue(){
		return t.getText();
	}
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
