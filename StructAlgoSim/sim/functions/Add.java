package sim.functions;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sim.gui.elements.GuiElement;
import sim.gui.elements.GuiFunction;
import sim.structures.Array;
import sim.structures.LinkedList;
import sim.structures.Tree;
import sim.structures.Variable;

/**
 * Add - Instances of this class is used with structures such as linked-list or array to add an element to the end. (LIFO)
 * @author 
 */
public class Add implements ActionListener {
// Class variables //
	Object l;
	Variable v;
	GuiFunction gui;
	
// Getters and setters //
	public GuiElement getGuiElement(){
		return gui;
	}
	
	public Object getTarget() {
		return l;
	}

	public void setTarget(Object l) {
		this.l = l;
	}

	public Variable getSourceVariable() {
		return v;
	}

	public void setSourceVariable(Variable v) {
		this.v = v;
	}

	
// Class constructor //
	/**
	 * The class constructor. Initializes the graphical element - {@link GuiFunction}
	 * @param bounds 	- The size and placement of the graphical element
	 * @param l 		- The structure on which to perform the add()-action
	 * @param v 		- The output variable
	 */
	public Add(Rectangle bounds, Object l, Variable v) {
		gui = new GuiFunction(bounds,"Add");
		gui.getButton().addActionListener(this);
		this.l=l;
		this.v=v;
	}
	
// Action Listener implementation //
	@Override
	public void actionPerformed(ActionEvent e) {
		if(l instanceof Array){
			((Array) l).addItem(v.getValue());
		}else if(l instanceof LinkedList){
			((LinkedList) l).addLast(v.getValue());
		}else if(l instanceof Tree){
		((Tree) l).addBreadthFirst(v.getValue());
		}
		//l.addFirst(v.getValue());
		

	}
}
