package sim.functions;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import sim.gui.elements.GuiElement;
import sim.gui.elements.GuiElement.GraphicalStructure;
import sim.gui.elements.GuiFunction;
import sim.structures.Array;
import sim.structures.LinkedList;
import sim.structures.Queue;
import sim.structures.Tree;
import sim.structures.Variable;

/**
 * Add - Instances of this class is used with structures such as linked-list or array to add an element to the end. (LIFO)
 */
public class Add implements ActionListener , GraphicalStructure{
	// Class variables //
	private Object o;
	private Variable v;
	private Variable i;
	private GuiFunction gui;

	// Getters and setters //
	/**
	 * Returns the GuiElement component for adding to JFrame/JPanel
	 * @return {@link GuiElement}
	 */
	public GuiElement getGuiElement(){
		return gui;
	}

	/**
	 * Getter for the target.
	 * @return current target object where the input variable should be added
	 */
	public Object getTarget() {
		return o;
	}
	/**
	 * Sets the current target object. This is the object the input object will be added to.
	 * @param l - Target object
	 */
	public void setTarget(Object l) {
		this.o = l;
	}

	/**
	 * Gets source variable
	 * @return The source {@link Variable} from which data to be added is retrieved
	 */
	public Variable getSourceVariable() {
		return v;
	}

	/**
	 * Set source {@link Variable} from which data to be added is retrieved
	 * @param v - Source {@link Variable}
	 */
	public void setSourceVariable(Variable v) {
		this.v = v;
	}
	
	/**
	 * Get index {@link Variable} from which the index number is retrieved. The index is later used for positioning the addition to the target object.
	 * @return Index {@link Variable}
	 */
	public Variable getIndexVariable() {
		return i;
	}
	/**
	 * Set index {@link Variable} from which the index number is retrieved. The index is later used for positioning the addition to the target object.
	 * @param i - Index {@link Variable}
	 */
	public void setIndexVariable(Variable i) {
		this.i = i;
	}


	// Class constructor //
	/**
	 * The class constructor. Initializes the graphical element - {@link GuiFunction}
	 * @param bounds 	- The size and placement of the graphical element
	 */
	public Add(Rectangle bounds) {
		gui = new GuiFunction(bounds,"Add");
		gui.getButton().addActionListener(this);
		this.o=null;
		this.v=null;
	}	
	/**
	 * The class constructor. Initializes the graphical element - {@link GuiFunction}
	 * @param bounds 	- The size and placement of the graphical element
	 * @param l 		- The {@link LinkedList} on which to perform the add()-action
	 * @param v 		- The output variable
	 */
	public Add(Rectangle bounds, LinkedList l, Variable v) {
		gui = new GuiFunction(bounds,"Add");
		gui.getButton().addActionListener(this);
		this.o=l;
		this.v=v;
	}
	/**
	 * The class constructor. Initializes the graphical element - {@link GuiFunction}
	 * @param bounds 	- The size and placement of the graphical element
	 * @param l 		- The {@link Tree} on which to perform the add()-action
	 * @param v 		- The output variable
	 * @param i 		- The input variable
	 */
	public Add(Rectangle bounds, Tree l, Variable v, Variable i) {
		gui = new GuiFunction(bounds,"Add");
		gui.getButton().addActionListener(this);
		this.o=l;
		this.i=i;
		this.v=v;
	}
	/**
	 * The class constructor. Initializes the graphical element - {@link GuiFunction}
	 * @param bounds 	- The size and placement of the graphical element
	 * @param l 		- The {@link Queue} on which to perform the add()-action
	 * @param v 		- The output variable
	 */
	public Add(Rectangle bounds, Queue l, Variable v) {
		gui = new GuiFunction(bounds,"Add");
		gui.getButton().addActionListener(this);
		this.o=l;
		this.v=v;
	}
	// Action Listener implementation //
	@Override
	public void actionPerformed(ActionEvent e) {
		if(v != null){
			if(o instanceof LinkedList){
				((LinkedList) o).addLast(v.getValue());
			}else if(o instanceof Tree){
				if(i!=null)
					try{
						((Tree) o).addChildAt(Integer.parseInt(i.getValue()),v.getValue());
					}
				catch(NumberFormatException n){
				}
				else JOptionPane.showMessageDialog(null,
						"You need to connect an index variable to add to a tree.",
						"Warning",
						JOptionPane.WARNING_MESSAGE);
			}else if(o instanceof Queue){
				((Queue) o).add(v.getValue());
			}else if(o instanceof Array){
				((Array) o).setValueAt(v.getValue(), Integer.parseInt(i.getValue()));
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
