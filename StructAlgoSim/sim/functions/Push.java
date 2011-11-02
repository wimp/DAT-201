package sim.functions;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sim.gui.elements.GuiElement;
import sim.gui.elements.GuiFunction;
import sim.structures.Stack;
import sim.structures.Variable;

/**
 * Push - Instances of this class is used with structures such as stack to add an element to the top. (LIFO)
 */
public class Push implements ActionListener{
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
	public Push(Rectangle bounds){
		gui = new GuiFunction(bounds,"Push");
		gui.getButton().addActionListener(this);
		this.l = null;
		this.v = null;
	}
	/**
	 * The class constructor. Initializes the graphical element - {@link GuiFunction}
	 * @param bounds 	- The size and placement of the graphical element
	 * @param v 		- The input variable.
	 * @param l 		- The structure on which to perform the push()-action 
	 */
	public Push(Rectangle bounds, Variable v, Stack l){
		gui = new GuiFunction(bounds,"Push");
		gui.getButton().addActionListener(this);
		this.l = l;
		this.v = v;
	}
	
// Implementation of the actionListener //
	@Override
	public void actionPerformed(ActionEvent e) {
		if(l instanceof Stack && v != null){
			((Stack)l).push((Object)v.getValue());
		}
	}
}
