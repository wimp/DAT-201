package sim.functions;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sim.functions.MoveChar.Direction;
import sim.gui.elements.GuiElement;
import sim.gui.elements.GuiFunction;
import sim.structures.Array;
import sim.structures.LinkedList;
import sim.structures.Tree;
import sim.structures.Variable;

public class Set implements ActionListener{
	Variable v;
	Variable i;
	Object l;
	
	GuiFunction gui;
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
	
	public Set(Rectangle bounds){
	//TODO add direction here
		gui = new GuiFunction(bounds,"Get");
		gui.getButton().addActionListener(this);
		this.v = null;
		this.l = null;
	}
	/**
	 * Constructor.
	 * 
	 * @param bounds = the dimensions of the graphical element
	 * @param dir = direction of arrow
	 * @param input = input var
	 * @param output = output var
	 */
	public Set(Rectangle bounds, Array l, Variable v, Variable i) {
		gui = new GuiFunction(bounds,"Get");
		gui.getButton().addActionListener(this);
		this.l=l;
		this.v=v;
	}
	public Set(Rectangle bounds, LinkedList l, Variable v, Variable i) {
		gui = new GuiFunction(bounds,"Get");
		gui.getButton().addActionListener(this);
		this.l=l;
		this.v=v;
	}
	public Set(Rectangle bounds, Tree l, Variable v, Variable i) {
		gui = new GuiFunction(bounds,"Get");
		gui.getButton().addActionListener(this);
		this.l=l;
		this.i=i;
		this.v=v;
	}
	/**
	 * Will remove the first char from the input string and append it to the output string. 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(i != null && v != null){
			
		}
	}
}
