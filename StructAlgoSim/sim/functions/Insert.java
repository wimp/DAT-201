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
public class Insert implements ActionListener {
// Class variables //
	Object l;
	Variable v;
	Variable i;
	GuiFunction gui;
	boolean insertAfterElement;
	String buttonText = "Insert Before";
	
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

	public Variable getIndexVariable() {
		return i;
	}

	public void setIndexVariable(Variable i) {
		this.i = i;
	}

	// Class constructor //
	public Insert(Rectangle bounds, boolean insertAfterElement) {
		this.l = null;
		this.v = null;
		this.i = null;
		this.insertAfterElement = insertAfterElement;
		buttonText = insertAfterElement ? "Insert Before" : "Insert After";
		gui = new GuiFunction(bounds,buttonText);
		gui.getButton().addActionListener(this);
	}
	/**
	 * The class constructor. Initializes the graphical element - {@link GuiFunction}
	 * @param bounds 			- The size and placement of the graphical element
	 * @param l 				- The structure on which to perform the add()-action
	 * @param v 				- The output variable
	 * @param i					- The input variable (The index of array element that is to be "added before")
	 * @param insertAfterElement- Whether the element at i should be added after (true) or before (false).
	 */
	public Insert(Rectangle bounds, LinkedList l, Variable v, Variable i, boolean insertAfterElement) {
		this.l = l;
		this.v = v;
		this.i = i;
		this.insertAfterElement = insertAfterElement;
		buttonText = insertAfterElement ? "Insert After" : "Insert Before";
		gui = new GuiFunction(bounds,buttonText);
		gui.getButton().addActionListener(this);
	}
	public Insert(Rectangle bounds, Array l, Variable v, Variable i, boolean insertAfterElement) {
		this.l = l;
		this.v = v;
		this.i = i;
		this.insertAfterElement = insertAfterElement;
		buttonText = insertAfterElement ? "Insert After" : "Insert Before";
		gui = new GuiFunction(bounds,buttonText);
		gui.getButton().addActionListener(this);
	}
	public Insert(Rectangle bounds, Tree l, Variable v, Variable i, boolean insertAfterElement) {
		this.l = l;
		this.v = v;
		this.i = i;
		this.insertAfterElement = insertAfterElement;
		buttonText = insertAfterElement ? "Insert After" : "Insert Before";
		gui = new GuiFunction(bounds,buttonText);
		gui.getButton().addActionListener(this);
	}
	
// Action Listener implementation //
	@Override
	public void actionPerformed(ActionEvent e) {
		if(l instanceof Array){
				((Array) l).insertAt(v.getValue(),Integer.parseInt(i.getValue()));
		}else if(l instanceof LinkedList){
			if(insertAfterElement)
				((LinkedList) l).insertAt(Integer.parseInt(i.getValue()+1),v.getValue());
			else
				((LinkedList) l).insertAt(Integer.parseInt(i.getValue()),v.getValue());
		}else if(l instanceof Tree){
				((Tree) l).insertAt(Integer.parseInt(i.getValue()),v.getValue());
		}
		//l.addFirst(v.getValue());
		

	}
}
