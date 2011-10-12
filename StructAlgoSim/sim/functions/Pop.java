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
	 * @param s 		- The structure on which to perform the pop()-action
	 * @param v 		- The output variable
	 */
	public Pop(Rectangle bounds, Stack s, Variable v) {
		gui = new GuiFunction(bounds,"Pop");
		gui.getButton().addActionListener(this);
		this.s=s;
		this.v=v;
	}

// Action Listener implementation //
	@Override
	public void actionPerformed(ActionEvent e) {
		v.setValue((String)s.pop());
	}
}
