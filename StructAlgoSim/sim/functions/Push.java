package sim.functions;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sim.gui.elements.GuiElement;
import sim.gui.elements.GuiElement.GraphicalStructure;
import sim.gui.elements.GuiFunction;
import sim.structures.Stack;
import sim.structures.Variable;

/**
 * Push - Instances of this class is used with structures such as stack to add an element to the top. (LIFO)
 */
public class Push implements ActionListener , GraphicalStructure{
// Class variables //
	private Object l;
	private Variable v;
	private GuiFunction gui;

// Getters and setters //
	/**
	 * Gets the functions {@link GuiElement}
	 * @return {@link GuiElement}
	 */
	public GuiElement getGuiElement(){
		return gui;
	}
	
	/**
	 * Gets the target object to push to
	 * @return Target Object
	 */
	public Object getTarget() {
		return l;
	}

	/**
	 * Sets the object to push to
	 * @param l - Target object
	 */
	public void setTarget(Object l) {
		this.l = l;
	}
	/**
	 * Gets the source {@link Variable} 
	 * @return Source {@link Variable} 
	 */
	public Variable getSourceVariable() {
		return v;
	}
	/**
	 * Sets the source {@link Variable} 
	 * @param v - Source {@link Variable} 
	 */
	public void setSourceVariable(Variable v) {
		this.v = v;
	}

// Class constructor //
	/**
	 * The class constructor. Initializes the graphical element - {@link GuiFunction}
	 * @param bounds 	- The size and placement of the graphical element
	 */
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
