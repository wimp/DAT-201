package sim.gui.elements;

import javax.swing.JTextField;

public class Variable {
	private Object obj;

	public Variable(Object obj, boolean editable){
		this.obj = obj;
		
		JTextField t = new JTextField(obj.toString());
		t.setEditable(editable);
	}
}
