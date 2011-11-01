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
	
	Variable i;
	Object source;
	Object target;
	boolean singleChar;

	GuiFunction gui;
	public GuiElement getGuiElement(){
		return gui;
	}
	public Object getTarget() {
		return target;
	}
	public void setTarget(Object l) {
		this.target = l;
	}
	public Object getSource() {
		return source;
	}
	public void setSource(Object o) {
		this.source= o;
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
		this.source = null;
		this.target = null;
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
	public Get(Rectangle bounds, Array l,Object o, boolean singleChar) {
		gui = new GuiFunction(bounds,"Get");
		gui.getButton().addActionListener(this);
		this.target=l;
		this.source=o;
		this.singleChar=singleChar;
	}
	public Get(Rectangle bounds, LinkedList l,Object o, boolean singleChar) {
		gui = new GuiFunction(bounds,"Get");
		gui.getButton().addActionListener(this);
		this.target=l;
		this.source=o;
		this.singleChar=singleChar;
	}
	public Get(Rectangle bounds, Tree l,Object o, Variable i, boolean singleChar) {
		gui = new GuiFunction(bounds,"Get");
		gui.getButton().addActionListener(this);
		this.target=l;
		this.i=i;
		this.source=o;
		this.singleChar=singleChar;
	}
	/**
	 * Will remove the first char from the input string and append it to the output string. 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(i != null && source != null){
			if(target instanceof Variable && source instanceof Variable){
				if(singleChar){
					String val = ((Variable)source).getValue();
					String ch = val.substring(0, 1);
					((Variable)source).setValue(val.substring(1));
					String tarVal = ((Variable) target).getValue();
					tarVal += ch;
					((Variable) target).setValue(tarVal);
				}
				else {
					((Variable) target).setValue(((Variable)source).getValue());
				}
			}else if(source instanceof Tree && target instanceof Variable){
				try{
						int index = Integer.parseInt(i.getValue());
						String s = ((Tree)source).getValueAt(index);
						if(s!=null)
							((Variable)target).setValue(s);
				}catch(NumberFormatException nfe){
					JOptionPane.showConfirmDialog(gui, "Illegal character: you can only enter numbers.");
				}
			}else if(source instanceof LinkedList && target instanceof Variable){

				try{
					int index = Integer.parseInt(i.getValue());
					String s = ((LinkedList)source).getValueAt(index);
					if(s!=null)
						((Variable)target).setValue(s);

				}catch(NumberFormatException nfe){
					JOptionPane.showConfirmDialog(gui, "Illegal character: you can only enter numbers.");
				}
			}else if(source instanceof Array && target instanceof Variable){
				if(i.getValue().indexOf(",") > 0){
					String[] index = i.getValue().split(",");
					try{
						int indexY = Integer.parseInt(index[0]);
						int indexX = Integer.parseInt(index[1]);

						if(((Array) source).getDimensions() == 2){
							((Variable)target).setValue((String) ((Array) source).getValueAt(indexY,indexX));
						}else{
							((Variable)target).setValue((String) ((Array) source).getValueAt(indexY));
						}
					}catch(Exception nfe){
						JOptionPane.showConfirmDialog(gui, "Illegal character: you can only enter numbers separated by a comma (,)");
					}
				}else{
					try{
						int indexY = Integer.parseInt(i.getValue());
						((Variable)target).setValue((String) ((Array) source).getValueAt(indexY));
					}catch(Exception nfe){
						JOptionPane.showConfirmDialog(gui, "Illegal character: you can only enter numbers separated by a comma (,)");
					}
				}
			}
		}
	}
}

