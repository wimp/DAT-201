package sim.functions;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import sim.gui.elements.GuiElement;
import sim.gui.elements.GuiFunction;
import sim.structures.Array;
import sim.structures.LinkedList;
import sim.structures.Tree;
import sim.structures.Variable;

/**
 * Add - Instances of this class is used with structures such as linked-list or array to add an element to the end. (LIFO)
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
		buttonText = insertAfterElement ? "Insert After" : "Insert Before";
		gui = new GuiFunction(bounds,buttonText);
		gui.getButton().addActionListener(this);
	}
	/**
	 * The class constructor. Initializes the graphical element - {@link GuiFunction}
	 *
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
		if(v!=null){
			if(i!=null){
				if(l instanceof LinkedList){
					try{
						if(insertAfterElement)
							((LinkedList) l).insertAt(Integer.parseInt(i.getValue()),v.getValue(), true);
						else
							((LinkedList) l).insertAt(Integer.parseInt(i.getValue()),v.getValue(), false);
					}
					catch(NumberFormatException n){
					}
				}else if(l instanceof Tree){
					try{
						((Tree) l).insertAt(Integer.parseInt(i.getValue()),v.getValue(), insertAfterElement);
					}
					catch(NumberFormatException n){
					}
				}
			}
			else {
				JOptionPane.showMessageDialog(null,
						"You need to connect an index variable to insert.",
						"Warning",
						JOptionPane.WARNING_MESSAGE);
			}
		}
		else{
			JOptionPane.showMessageDialog(null,
					"You need to connect a varible.",
					"Warning",
					JOptionPane.WARNING_MESSAGE);
		}
	}
	//l.addFirst(v.getValue());
}
