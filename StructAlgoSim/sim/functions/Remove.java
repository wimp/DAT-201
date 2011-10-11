package sim.functions;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sim.gui.elements.GuiElement;
import sim.gui.elements.GuiFunction;
import sim.structures.Array;
import sim.structures.LinkedList;
import sim.structures.Variable;

/**
 * Remove - Instances of this class is used with structures such as linked-list or array to remove an element at the index i.
 */
public class Remove implements ActionListener {
// Class variables //
	Object l;
	Variable v;
	Variable i;
	GuiFunction gui;
	
// Getters and setters //
	public GuiElement getGuiElement(){
		return gui;
	}
	
	public Object getL() {
		return l;
	}

	public void setL(Object l) {
		this.l = l;
	}

	public Variable getV() {
		return v;
	}

	public void setV(Variable v) {
		this.v = v;
	}

	public Variable getI() {
		return i;
	}

	public void setI(Variable i) {
		this.i = i;
	}

	// Class constructor //
	/**
	 * The class constructor. Initializes the graphical element - {@link GuiFunction}
	 * @param bounds 	- The size and placement of the graphical element
	 * @param l 		- The structure on which to perform the remove()-action
	 * @param v 		- The output variable
	 * @param i			- The input variable. The index to be removed from the list/array
	 */
	public Remove(Rectangle bounds, Object l, Variable v, Variable i) {
		gui = new GuiFunction(bounds,"Remove");
		gui.getButton().addActionListener(this);
		this.l=l;
		this.v=v;
		this.i=i == null ? new Variable(new Rectangle(0,0,0,0),"0",false) : i;
	}
	
// Action Listener implementation //
	@Override
	public void actionPerformed(ActionEvent e) {
		if(l instanceof Array){
			String s = ((Array) l).removeItem(Integer.parseInt(i.getValue())).toString();
			v.setValue(s);
		}else if(l instanceof LinkedList){
			Object o = ((LinkedList) l).removeElementAt(Integer.parseInt(i.getValue()));
			if(o!=null){
				String s = o.toString();
				v.setValue(s);
			}
		}
	}
}
