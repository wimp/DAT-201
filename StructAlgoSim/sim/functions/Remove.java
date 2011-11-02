package sim.functions;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sim.gui.elements.GuiElement;
import sim.gui.elements.GuiFunction;
import sim.structures.LinkedList;
import sim.structures.Queue;
import sim.structures.Tree;
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
	public Object getSource() {
		return l;
	}
	public void setSource(Object l) {
		this.l = l;
	}
	public Variable getTargetVariable() {
		return v;
	}
	public void setTargetVariable(Variable v) {
		this.v = v;
	}
	public Variable getIndexVariable() {
		return i;
	}

	public void setIndexVariable(Variable i) {
		this.i = i;
	}

	// Class constructor //
	public Remove(Rectangle bounds) {
		gui = new GuiFunction(bounds,"Remove");
		gui.getButton().addActionListener(this);
		this.l=null;
		this.v=null;
		this.i= null;
	}
	

	public Remove(Rectangle bounds, Queue l, Variable v) {
		gui = new GuiFunction(bounds,"Remove");
		gui.getButton().addActionListener(this);
		this.l = l;
		this.v = v;
		this.i = null;
	}	
	/**
	 * The class constructor. Initializes the graphical element - {@link GuiFunction}
	 * @param bounds 	- The size and placement of the graphical element
	 * @param l 		- The structure on which to perform the remove()-action
	 * @param v 		- The output variable
	 * @param i			- The input variable. The index to be removed from the list/array
	 */
	public Remove(Rectangle bounds, LinkedList l, Variable v, Variable i) {
		gui = new GuiFunction(bounds,"Remove");
		gui.getButton().addActionListener(this);
		this.l=l;
		this.v=v;
		this.i= i == null ? new Variable(new Rectangle(0,0,0,0),"0",false) : i;
	}
	public Remove(Rectangle bounds, Tree l, Variable v, Variable i) {
		gui = new GuiFunction(bounds,"Remove");
		gui.getButton().addActionListener(this);
		this.l=l;
		this.v=v;
		this.i= i == null ? new Variable(new Rectangle(0,0,0,0),"0",false) : i;
	}
	
// Action Listener implementation //
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = null;
		if(l instanceof Queue){
		o = ((Queue) l).remove();
		}
		if(o!=null){
			String s = o.toString();
			if(v!=null)
			v.setValue(s);
		}
		int index = 0;
		
		if(i!=null){
		try{
			index = Integer.parseInt(i.getValue());
		}
		catch(NumberFormatException nf){
			return;
		}
		if(l instanceof LinkedList){
			o = ((LinkedList) l).removeElementAt(index);
			if(o!=null){
				String s = o.toString();
				v.setValue(s);
			}
		}else if(l instanceof Tree){
			o = ((Tree) l).removeAt(index);
			if(o!=null){
				String s = o.toString();
				if(v!=null)
				v.setValue(s);
			}
		}
	}
	}
}
