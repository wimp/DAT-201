package sim.functions;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sim.gui.elements.GuiElement;
import sim.gui.elements.GuiElement.GraphicalStructure;
import sim.gui.elements.GuiFunction;
import sim.structures.LinkedList;
import sim.structures.Queue;
import sim.structures.Tree;
import sim.structures.Variable;

/**
 * Remove - Instances of this class is used with structures such as linked-list or array to remove an element at the index i.
 */
public class Remove implements ActionListener , GraphicalStructure {
	// Class variables //
	Object l;
	Variable v;
	Variable i;
	GuiFunction gui;

	// Getters and setters //
	/**
	 * Gets the functions Graphical component
	 * @return The functions {@link GuiElement}
	 */
	public GuiElement getGuiElement(){
		return gui;
	}

	/**
	 * Gets the source object
	 * @return The source object
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
	 * @return The target {@link Variable}
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
	/**
	 * Gets the index {@link Variable}
	 * @return i - Index {@link Variable}
	 */
	public Variable getIndexVariable() {
		return i;
	}
	/**
	 * Sets the index {@link Variable}
	 * @param i - Index {@link Variable}
	 */
	public void setIndexVariable(Variable i) {
		this.i = i;
	}

	// Class constructor //
	/**
	 * Constructor
	 * @param bounds - For Gui positioning
	 */
	public Remove(Rectangle bounds) {
		gui = new GuiFunction(bounds,"Remove");
		gui.getButton().addActionListener(this);
		this.l=null;
		this.v=null;
		this.i= null;
	}

	/**
	 * Constructor for Queue target
	 * @param bounds - For Gui positioning
	 * @param l - The target {@link Queue}
	 * @param v - The source {@link Variable}
	 */
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
	 * @param i			- The index variable. The index to be removed from the list/array
	 */
	public Remove(Rectangle bounds, LinkedList l, Variable v, Variable i) {
		gui = new GuiFunction(bounds,"Remove");
		gui.getButton().addActionListener(this);
		this.l=l;
		this.v=v;
		this.i= i == null ? new Variable(new Rectangle(0,0,0,0),"0",false) : i;
	}
	/**
	 * The class constructor. Initializes the graphical element - {@link GuiFunction}
	 * @param bounds 	- The size and placement of the graphical element
	 * @param l 		- The structure on which to perform the remove()-action
	 * @param v 		- The output variable
	 * @param i			- The index variable. The index to be removed from the list/array
	 */
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
