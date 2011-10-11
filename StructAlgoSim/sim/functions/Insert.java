package sim.functions;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sim.gui.elements.GuiElement;
import sim.gui.elements.GuiFunction;
import sim.structures.Array;
import sim.structures.LinkedList;
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
	
// Getters and setters //
	public GuiElement getGuiElement(){
		return gui;
	}
	
// Class constructor //
	/**
	 * The class constructor. Initializes the graphical element - {@link GuiFunction}
	 * @param bounds 			- The size and placement of the graphical element
	 * @param l 				- The structure on which to perform the add()-action
	 * @param v 				- The output variable
	 * @param i					- The input variable (The index of array element that is to be "added before")
	 * @param insertAfterElement- Whether the element at i should be added after (true) or before (false).
	 */
	public Insert(Rectangle bounds, Object l, Variable v, Variable i) {
		this(bounds,l,v,i,false);
	}
	
	public Insert(Rectangle bounds, Object l, Variable v, Variable i, boolean insertAfterElement) {
		gui = new GuiFunction(bounds,"Insert Before");
		gui.getButton().addActionListener(this);
		this.l = l;
		this.v = v;
		this.i = i;
		this.insertAfterElement = insertAfterElement;
	}
	
// Action Listener implementation //
	@Override
	public void actionPerformed(ActionEvent e) {
		if(l instanceof Array){
			if(insertAfterElement)
				((Array) l).insertAfter(v.getValue(),Integer.parseInt(i.getValue()));
			else
				((Array) l).insertBefore(v.getValue(),Integer.parseInt(i.getValue()));
		}else if(l instanceof LinkedList){
			if(insertAfterElement)
				((LinkedList) l).insertAfterElement(((LinkedList) l).elementAt(Integer.parseInt(i.getValue())),v.getValue());
			else
				((LinkedList) l).insertBeforeElement(((LinkedList) l).elementAt(Integer.parseInt(i.getValue())),v.getValue());
		}
		//l.addFirst(v.getValue());
		

	}
}
