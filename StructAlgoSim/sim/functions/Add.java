package sim.functions;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import sim.gui.elements.GuiElement;
import sim.gui.elements.GuiFunction;
import sim.structures.Array;
import sim.structures.LinkedList;
import sim.structures.Queue;
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
	Variable i;
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
	public Variable getIndexVariable() {
		return i;
	}

	public void setIndexVariable(Variable i) {
		this.i = i;
	}


	// Class constructor //

	public Add(Rectangle bounds) {
		gui = new GuiFunction(bounds,"Add");
		gui.getButton().addActionListener(this);
		this.l=null;
		this.v=null;
	}	
	/**
	 * The class constructor. Initializes the graphical element - {@link GuiFunction}
	 * @param bounds 	- The size and placement of the graphical element
	 * @param l 		- The structure on which to perform the add()-action
	 * @param v 		- The output variable
	 */
	public Add(Rectangle bounds, Array l, Variable v) {
		gui = new GuiFunction(bounds,"Add");
		gui.getButton().addActionListener(this);
		this.l=l;
		this.v=v;
	}
	public Add(Rectangle bounds, LinkedList l, Variable v) {
		gui = new GuiFunction(bounds,"Add");
		gui.getButton().addActionListener(this);
		this.l=l;
		this.v=v;
	}
	public Add(Rectangle bounds, Tree l, Variable v, Variable i) {
		gui = new GuiFunction(bounds,"Add");
		gui.getButton().addActionListener(this);
		this.l=l;
		this.i=i;
		this.v=v;
	}
	public Add(Rectangle bounds, Queue l, Variable v) {
		gui = new GuiFunction(bounds,"Add");
		gui.getButton().addActionListener(this);
		this.l=l;
		this.v=v;
	}
	// Action Listener implementation //
	@Override
	public void actionPerformed(ActionEvent e) {
		if(v != null){
			if(l instanceof LinkedList){
				((LinkedList) l).addLast(v.getValue());
			}else if(l instanceof Tree){
				if(i!=null)
					try{
						((Tree) l).addChildAt(Integer.parseInt(i.getValue()),v.getValue());
					}
				catch(NumberFormatException n){
				}
				else JOptionPane.showMessageDialog(null,
						"You need to connect an index variable to add to a tree.",
						"Warning",
						JOptionPane.WARNING_MESSAGE);
			}else if(l instanceof Queue){
				((Queue) l).add(v.getValue());
			}else if(l instanceof Array){
				((Array) l).insertAt(v.getValue(), Integer.parseInt(i.getValue()));
			}
		}
		else{
			JOptionPane.showMessageDialog(null,
					"You need to connect a varible.",
					"Warning",
					JOptionPane.WARNING_MESSAGE);
		}
		//l.addFirst(v.getValue());
	}
}
