package sim.functions;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sim.gui.elements.GuiElement;
import sim.gui.elements.GuiFunction;
import sim.structures.Stack;
import sim.structures.Variable;

/**
 * Pop - Instances of this class is used with structures such as stack to fetch the top element. (LIFO)
 * @author 
 */
public class Pop implements ActionListener {
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
	public Pop(Rectangle bounds) {
		gui = new GuiFunction(bounds,"Pop");
		gui.getButton().addActionListener(this);
		this.l=null;
		this.v=null;
	}
	/**
	 * The class constructor. Initializes the graphical element - {@link GuiFunction}
	 * @param bounds 	- The size and placement of the graphical element
	 * @param s 		- The structure on which to perform the pop()-action
	 * @param v 		- The output variable
	 */
	public Pop(Rectangle bounds, Stack l, Variable v) {
		gui = new GuiFunction(bounds,"Pop");
		gui.getButton().addActionListener(this);
		this.l=l;
		this.v=v;
	}

// Action Listener implementation //
	@Override
	public void actionPerformed(ActionEvent e) {
		if(l instanceof Stack){
			if(v != null)
				v.setValue((String)((Stack)l).pop());
			else
				((Stack)l).pop();
		}
	}
}
