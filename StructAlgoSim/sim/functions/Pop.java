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
 * Pop - Instances of this class is used with structures such as stack to fetch the top element. (LIFO)
 */
public class Pop implements ActionListener  , GraphicalStructure{
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
	 * Gets the source object
	 * @return The functions Source Object
	 */
	public Object getSource() {
		return l;
	}
	/**
	 * Sets the source object
	 * @param l - Source Object
	 */
	public void setSource(Object l) {
		this.l = l;
	}
	/**
	 * Gets the target {@link Variable}
	 * @return Target {@link Variable}
	 */
	public Variable getTargetVariable() {
		return v;
	}

	/**
	 * Sets the target {@link Variable}
	 * @param v - Target {@link Variable}
	 */
	public void setTargetVariable(Variable v) {
		this.v = v;
	}


	// Class constructor //
	/**
	 * The class constructor. Initializes the graphical element - {@link GuiFunction}
	 * @param bounds 	- The size and placement of the graphical element
	 */
	public Pop(Rectangle bounds) {
		gui = new GuiFunction(bounds,"Pop");
		gui.getButton().addActionListener(this);
		this.l=null;
		this.v=null;
	}
	/**
	 * The class constructor. Initializes the graphical element - {@link GuiFunction}
	 * @param bounds 	- The size and placement of the graphical element
	 * @param l 		- The structure on which to perform the pop()-action
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
