package sim.functions;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import sim.gui.elements.GuiElement;
import sim.gui.elements.GuiElement.GraphicalStructure;
import sim.gui.elements.GuiFunction;
import sim.structures.Array;
import sim.structures.Heap;
import sim.structures.LinkedList;
import sim.structures.Tree;
import sim.structures.Variable;

/**
 * Insert - Instances of this class is used with structures such as {@link Tree}, {@link Heap} or {@link LinkedList}
 * It has a source object, an index object and a toggle that decides if it inserts before or after the given index.
 */
public class Insert implements ActionListener , GraphicalStructure {
	// Class variables //
	Object l;
	Variable v;
	Variable i;
	GuiFunction gui;
	boolean insertAfterElement;
	String buttonText = "Insert Before";

	// Getters and setters //
	/**
	 * Gets the {@link GuiElement}
	 * @return The function's {@link GuiElement}
	 */
	public GuiElement getGuiElement(){
		return gui;
	}
	
	/** 
	 * Gets the target Object
	 * @return Target Object
	 */
	public Object getTarget() {
		return l;
	}
	/**
	 * Sets the target object
	 * @param l - Target Object
	 */
	public void setTarget(Object l) {
		this.l = l;
	}
	
	/**
	 * Gets the source {@link Variable}
	 * @return Source Variable
	 */
	public Variable getSourceVariable() {
		return v;
	}
	/**
	 * Sets the source {@link Variable}
	 * @param v - Source Variable
	 */
	public void setSourceVariable(Variable v) {
		this.v = v;
	}
	
	/**
	 * Gets the indes {@link Variable}
	 * @return Index Variable
	 */
	public Variable getIndexVariable() {
		return i;
	}
	/**
	 * Sets the index {@link Variable}
	 * @param i - Index Variable
	 */
	public void setIndexVariable(Variable i) {
		this.i = i;
	}
	
	public boolean getInsertAfterElement(){
		return insertAfterElement;
	}
	
	public void setInsertAfterElement(boolean insertAfterElement){
		this.insertAfterElement = insertAfterElement;
	}

	// Class constructor //
	/**
	 * 
	 * @param bounds - Places the GuiElement
	 * @param insertAfterElement - Decides if the insert happens before or after the given index. 
	 */
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
	 * Constructor
	 * @param bounds - Places the GuiElement
	 * @param l - The target {@link LinkedList}
	 * @param v - The source {@link Variable}
	 * @param i - The index {@link Variable}
	 * @param insertAfterElement - Boolean flag that decides insert position relative to index variable
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
	
	/**
	 * Constructor
	 * @param bounds - Places the GuiElement
	 * @param l - The target {@link Array}
	 * @param v - The source {@link Variable}
	 * @param i - The index {@link Variable}
	 * @param insertAfterElement - Boolean flag that decides insert position relative to index variable
	 */
	public Insert(Rectangle bounds, Array l, Variable v, Variable i, boolean insertAfterElement) {
		this.l = l;
		this.v = v;
		this.i = i;
		this.insertAfterElement = insertAfterElement;
		buttonText = insertAfterElement ? "Insert After" : "Insert Before";
		gui = new GuiFunction(bounds,buttonText);
		gui.getButton().addActionListener(this);
	}
	/**
	 * Constructor
	 * @param bounds - Places the GuiElement
	 * @param l - The target {@link Tree}
	 * @param v - The source {@link Variable}
	 * @param i - The index {@link Variable}
	 * @param insertAfterElement - Boolean flag that decides insert position relative to index variable
	 */
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
