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
import sim.structures.Tree;
import sim.structures.Variable;

public class Set implements ActionListener , GraphicalStructure{
	private Variable source;
	private Variable i;
	private Object target;
	private boolean singleChar;
	private boolean overWrite;

	private GuiFunction gui;
	
	public GuiElement getGuiElement(){
		return gui;
	}
	public Object getTarget() {
		return target;
	}
	public void setTarget(Object l) {
		this.target = l;
	}
	public boolean isOverWrite() {
		return overWrite;
	}
	public void setOverWrite(boolean overWrite) {
		this.overWrite = overWrite;
	}
	public Variable getSourceVariable() {
		return source;
	}
	public void setSourceVariable(Variable v) {
		this.source = v;
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
	public void setSingleChar(boolean singleChar) {
		this.singleChar = singleChar;
	}
	public Set(Rectangle bounds){
		//TODO add direction here
		gui = new GuiFunction(bounds,"Set");
		gui.getButton().addActionListener(this);
		this.source = null;
		this.target = null;
	}
	/**
	 * Constructor
	 * @param bounds
	 * @param l - Source {@link Variable}
	 * @param o - Target {@link Variable}
	 * @param singleChar
	 */
	public Set(Rectangle bounds, Variable l,Variable o, boolean singleChar, boolean overWrite) {
		gui = new GuiFunction(bounds,"Set");
		gui.getButton().addActionListener(this);
		this.source=l;
		this.target=o;
		this.singleChar=singleChar;
		this.overWrite = overWrite;
	}
	/**
	 * Constructor.
	 */
	public Set(Rectangle bounds, Array l, Variable v, Variable i, boolean singleChar, boolean overWrite) {
		gui = new GuiFunction(bounds,"Set");
		gui.getButton().addActionListener(this);
		this.target=l;
		this.source=v;
		this.singleChar=singleChar;
		this.overWrite=overWrite;
	}
	public Set(Rectangle bounds, LinkedList l, Variable v, Variable i, boolean singleChar, boolean overWrite) {
		gui = new GuiFunction(bounds,"Set");
		gui.getButton().addActionListener(this);
		this.target=l;
		this.source=v;
		this.singleChar=singleChar;
		this.overWrite=overWrite;
	}
	public Set(Rectangle bounds, Tree l, Variable v, Variable i, boolean singleChar, boolean overWrite) {
		gui = new GuiFunction(bounds,"Set");
		gui.getButton().addActionListener(this);
		this.target=l;
		this.i=i;
		this.source=v;
		this.singleChar=singleChar;
		this.overWrite=overWrite;
	}
	/**
	 * Will remove the first char from the input string and append it to the output string. 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(source != null){
			if(target instanceof Variable){
				if(singleChar){
					String val = source.getValue();

					if(val.length()>0){
					String ch = val.substring(0, 1);
					source.setValue(val.substring(1));
					String tarVal = ((Variable) target).getValue();
					if(!overWrite)tarVal += ch;
					else tarVal = ch;
					
					((Variable) target).setValue(tarVal);
					}
				}
				else {
					source.setValue(((Variable) target).getValue());
				}
			}else if(i != null && target instanceof Tree){

				try{
						int index = Integer.parseInt(i.getValue());
						((Tree)target).setValueAt(index, source.getValue());
						
				}catch(NumberFormatException nfe){
					JOptionPane.showConfirmDialog(gui, "Illegal character: you can only enter numbers.");
				}
			}else if(i != null && target instanceof LinkedList){

				try{
					int index = Integer.parseInt(i.getValue());
					if(index==0) return;
					String s = source.getValue();
					if(s!=null)
						((LinkedList)target).setValueAt(index,s);

				}catch(NumberFormatException nfe){
					JOptionPane.showConfirmDialog(gui, "Illegal character: you can only enter numbers.");
				}
			}
			else if(i != null && target instanceof Array){
					if(i.getValue().indexOf(",") > 0){
						String[] index = i.getValue().split(",");
						try{
							int indexY = Integer.parseInt(index[0]);
							int indexX = Integer.parseInt(index[1]);

							if(((Array) target).getDimensions() == 2){
								((Array) target).setValueAt(source.getValue(), indexY, indexX);
							}else{
								((Array) target).setValueAt(source.getValue(), indexY);
							}
						}catch(Exception nfe){
							JOptionPane.showMessageDialog(gui, "Illegal character: you can only enter numbers separated by a comma (,)");
						}
					}else{
						try{
							int indexY = Integer.parseInt(i.getValue());
							((Array) target).setValueAt(source.getValue(), indexY);
						}catch(Exception nfe){
							JOptionPane.showMessageDialog(gui, "Illegal character: you can only enter numbers separated by a comma (,)");
						}
					}
				}
		}
	}
}
