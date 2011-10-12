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
 * @author 
 */
public class Push implements ActionListener{
// Class variables //
	Stack s;
	Variable v;
	GuiFunction gui;

// Getters and setters //
	public GuiElement getGuiElement(){
		return gui;
	}
	
	public Stack getS() {
		return s;
	}

	public void setS(Stack s) {
		this.s = s;
	}

	public Variable getV() {
		return v;
	}

	public void setV(Variable v) {
		this.v = v;
	}

// Class constructor //
	/**
	 * The class constructor. Initializes the graphical element - {@link GuiFunction}
	 * @param bounds 	- The size and placement of the graphical element
	 * @param v 		- The input variable.
	 * @param s 		- The structure on which to perform the push()-action 
	 */
	public Push(Rectangle bounds, Variable v, Stack s){
		gui = new GuiFunction(bounds,"Push");
		gui.getButton().addActionListener(this);
		this.s = s;
		this.v = v;
	}
	
// Implementation of the actionListener //
	@Override
	public void actionPerformed(ActionEvent e) {
		s.push((Object)v.getValue());
		v.setValue("");
	}
}
