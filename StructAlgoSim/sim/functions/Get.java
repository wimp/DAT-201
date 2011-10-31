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

public class Get implements ActionListener{
	Variable v;
	Variable i;
	Object l;
	boolean singleChar;
	
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
	public boolean getSingleChar(){
		return singleChar;
	}
	public void setSingleChar(boolean singleChar){
		this.singleChar = singleChar;
	}
	
	public Get(Rectangle bounds, boolean singleChar){
	//TODO add direction here
		gui = new GuiFunction(bounds,"Get");
		gui.getButton().addActionListener(this);
		this.v = null;
		this.l = null;
		this.singleChar=singleChar;
	}
	/**
	 * Constructor.
	 * 
	 * @param bounds = the dimensions of the graphical element
	 * @param dir = direction of arrow
	 * @param input = input var
	 * @param output = output var
	 */
	public Get(Rectangle bounds, Array l, Variable v, boolean singleChar) {
		gui = new GuiFunction(bounds,"Get");
		gui.getButton().addActionListener(this);
		this.l=l;
		this.v=v;
		this.singleChar=singleChar;
	}
	public Get(Rectangle bounds, LinkedList l, Variable v, boolean singleChar) {
		gui = new GuiFunction(bounds,"Get");
		gui.getButton().addActionListener(this);
		this.l=l;
		this.v=v;
		this.singleChar=singleChar;
	}
	public Get(Rectangle bounds, Tree l, Variable v, Variable i, boolean singleChar) {
		gui = new GuiFunction(bounds,"Get");
		gui.getButton().addActionListener(this);
		this.l=l;
		this.i=i;
		this.v=v;
		this.singleChar=singleChar;
	}
	/**
	 * Will remove the first char from the input string and append it to the output string. 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(i != null && v != null){
			if(l instanceof Variable){
				if(singleChar){
				String val = v.getValue();
				String ch = val.substring(0, 1);
				v.setValue(val.substring(1));
				String tarVal = ((Variable) l).getValue();
				tarVal += ch;
				((Variable) l).setValue(tarVal);
				}
				else {
					v.setValue(((Variable) l).getValue());
				}
			}else if(l instanceof Array){
				if(i.getValue().indexOf(",") > 0){
					String[] index = i.getValue().split(",");
					try{
						int indexY = Integer.parseInt(index[0]);
						int indexX = Integer.parseInt(index[1]);
						
						if(((Array) l).getDimensions() == 2){
							v.setValue((String) ((Array) l).valueAt(indexY,indexX));
						}else{
							v.setValue((String) ((Array) l).valueAt(indexY));
						}
					}catch(Exception nfe){
						JOptionPane.showConfirmDialog(gui, "Illegal character: you can only enter numbers separated by a comma (,)");
					}
				}else{
					try{
						int indexY = Integer.parseInt(i.getValue());
						v.setValue((String) ((Array) l).valueAt(indexY));
					}catch(Exception nfe){
						JOptionPane.showConfirmDialog(gui, "Illegal character: you can only enter numbers separated by a comma (,)");
					}
				}
			}
		}
	}
}

