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
 * This object retrieves either a single character or the entire string from a source object (or index within the source object) and passes it to a target object. 
 * The Source object can be a {@link Variable}, {@link LinkedList}, {@link Array} or {@link Tree}
 * The target can only be a {@link Variable}
 */
public class Get implements ActionListener{

	Variable i;
	Variable target;
	Object source;
	boolean singleChar;
	boolean overWrite;

	GuiFunction gui;
	/**
	 * Returns the GuiElement component for adding to JFrame/JPanel
	 * @return
	 */
	public GuiElement getGuiElement(){
		return gui;
	}
	/**
	 * Gets target variable
	 * @return {@link Variable} target
	 */
	public Variable getTarget() {
		return target;
	}
	/**
	 * Sets target variable
	 * @param l - {@link Variable} target
	 */
	public void setTarget(Variable l) {
		this.target = l;
	}
	
	public boolean isOverWrite() {
		return overWrite;
	}
	public void setOverWrite(boolean overWrite) {
		this.overWrite = overWrite;
	}
	/**
	 * Gets source variable
	 * @return {@link Variable} source
	 */
	public Object getSource() {
		return source;
	}
	/**
	 * Sets source variable
	 * @param l - {@link Variable} source
	 */
	public void setSource(Object l) {
		this.source= l;
	}

	/**
	 * Gets index variable
	 * @return {@link Variable} index
	 */
	public Variable getIndexVariable() {
		return i;
	}

	/**
	 * Sets index variable
	 * @param {@link Variable} index
	 */
	public void setIndexVariable(Variable i) {
		this.i = i;
	}

	/**
	 * Gets whether or not the get should retrieve a single character or not
	 * @return boolean
	 */
	public boolean getSingleChar(){
		return singleChar;
	}
	/**
	 * Sets whether or not the get should retrieve a single character or not
	 * @param singleChar
	 */
	public void setSingleChar(boolean singleChar, boolean overWrite){
		this.singleChar = singleChar;
		this.overWrite = overWrite;
	}
	/**
	 * Constructor
	 * @param bounds
	 * @param singleChar - Controls if the Get should retrieve only one character or all.
	 */
	public Get(Rectangle bounds, boolean singleChar, boolean overWrite){

		gui = new GuiFunction(bounds,"Get");
		gui.getButton().addActionListener(this);
		this.target = null;
		this.source = null;
		this.singleChar=singleChar;
		this.overWrite = overWrite;
	}
	/**
	 * Constructor
	 * @param bounds
	 * @param l - Source {@link Variable}
	 * @param o - Target {@link Variable}
	 * @param i - Index {@link Variable}
	 * @param singleChar
	 */
	public Get(Rectangle bounds, Variable l,Variable o, Variable i, boolean singleChar, boolean overWrite) {
		gui = new GuiFunction(bounds,"Get");
		gui.getButton().addActionListener(this);
		this.source=l;
		this.i=i;
		this.target=o;
		this.singleChar=singleChar;
		this.overWrite = overWrite;
	}
	/**
	 * Constructor
	 * @param bounds
	 * @param l - Source {@link Array}
	 * @param o - Target {@link Variable}
	 * @param singleChar
	 */
	public Get(Rectangle bounds, Array l,Variable o, boolean singleChar, boolean overWrite) {
		gui = new GuiFunction(bounds,"Get");
		gui.getButton().addActionListener(this);
		this.source=l;
		this.target=o;
		this.singleChar=singleChar;
		this.overWrite = overWrite;
	}
	/**
	 * Constructor
	 * @param bounds
	 * @param l - Source {@link LinkedList}
	 * @param o - Target {@link Variable}
	 * @param i - Index {@link Variable}
	 * @param singleChar
	 */
	public Get(Rectangle bounds, LinkedList l,Variable o, Variable i, boolean singleChar, boolean overWrite) {
		gui = new GuiFunction(bounds,"Get");
		gui.getButton().addActionListener(this);
		this.source=l;
		this.target=o;
		this.i = i;
		this.singleChar=singleChar;
		this.overWrite = overWrite;
	}
	/**
	 * Constructor
	 * @param bounds
	 * @param l - Source {@link Tree}
	 * @param o - Target {@link Variable}
	 * @param i - Index {@link Variable}
	 * @param singleChar
	 */
	public Get(Rectangle bounds, Tree l,Variable o, Variable i, boolean singleChar, boolean overWrite) {
		gui = new GuiFunction(bounds,"Get");
		gui.getButton().addActionListener(this);
		this.source=l;
		this.i=i;
		this.target=o;
		this.singleChar=singleChar;
		this.overWrite = overWrite;
	}
	/**
	 * Will remove the first char from the input string and append it to the output string. 
	 */
	@Override

	public void actionPerformed(ActionEvent e) {
		if(target != null){
			if(source instanceof Variable){
				if(singleChar){
					String val = ((Variable)source).getValue();

					if(val.length()>0){
						String ch = val.substring(0, 1);
						((Variable)source).setValue(val.substring(1));
						String tarVal = ((Variable) target).getValue();
						
						if(overWrite)tarVal += ch;
						else tarVal = ch;
						
						((Variable) target).setValue(tarVal);
					}
				}
				else {
					((Variable) target).setValue(((Variable)source).getValue());
				}
			}else if(i != null && source instanceof Tree){
				try{
					int index = Integer.parseInt(i.getValue());
					String s = ((Tree)source).getValueAt(index);
					if(s!=null)
						target.setValue(s);
				}catch(NumberFormatException nfe){
					JOptionPane.showConfirmDialog(gui, "Illegal character: you can only enter numbers.");
				}
			}else if(i != null && source instanceof LinkedList){

				try{
					int index = Integer.parseInt(i.getValue());
					String s = ((LinkedList)source).getValueAt(index);
					if(s!=null)
						target.setValue(s);

				}catch(NumberFormatException nfe){
					JOptionPane.showConfirmDialog(gui, "Illegal character: you can only enter numbers.");
				}
			}else if(i != null && source instanceof Array){
				if(i.getValue().indexOf(",") > 0){
					String[] index = i.getValue().split(",");
					try{
						int indexY = Integer.parseInt(index[0]);
						int indexX = Integer.parseInt(index[1]);

						if(((Array) source).getDimensions() == 2){
							target.setValue((String) ((Array) source).getValueAt(indexY,indexX));
						}else{
							target.setValue((String) ((Array) source).getValueAt(indexY));
						}
					}catch(Exception nfe){
						JOptionPane.showConfirmDialog(gui, "Illegal character: you can only enter numbers separated by a comma (,)");
					}
				}else{
					try{
						int indexY = Integer.parseInt(i.getValue());
						target.setValue((String) ((Array) source).getValueAt(indexY));
					}catch(Exception nfe){
						JOptionPane.showConfirmDialog(gui, "Illegal character: you can only enter numbers separated by a comma (,)");
					}
				}
			}
		}
	}
}

