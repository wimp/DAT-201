package sim.functions;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sim.gui.elements.GuiElement;
import sim.gui.elements.GuiFunction;
import sim.structures.Variable;

/**
 * The MoveChar class is used to create objects that gets(removes) 
 * the first char from a string-type input variable and puts (appends) 
 * it into another string-type variable.
 */
public class MoveChar implements ActionListener {
	Variable input;
	Variable output;
	GuiFunction gui;
	public GuiElement getGuiElement(){
		return gui;
	}
	public Variable getInput() {
		return input;
	}
	public void setInput(Variable input) {
		this.input = input;
	}
	public Variable getOutput() {
		return output;
	}
	public void setOutput(Variable output) {
		this.output = output;
	}
	public MoveChar(Rectangle bounds, Direction dir){
	//TODO add direction here
		gui = new GuiFunction(bounds,"<-");
		gui.getButton().addActionListener(this);
		this.input = null;
		this.output = null;
	}
	/**
	 * Constructor.
	 * 
	 * @param bounds = the dimensions of the graphical element
	 * @param dir = direction of arrow
	 * @param input = input var
	 * @param output = output var
	 */
	public MoveChar(Rectangle bounds, Direction dir,Variable input, Variable output){
	//TODO add direction here
		gui = new GuiFunction(bounds,"<-");
		gui.getButton().addActionListener(this);
		this.input = input;
		this.output = output;
	}
	/**
	 * Will remove the first char from the input string and append it to the output string. 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(input != null && output != null){
			String val = input.getValue();
			if(val.length() > 0){
				output.setValue(output.getValue() + val.substring(0,1));
				input.setValue(val.substring(1));
			}
		}
	}
	/**
	 * The direction enum is used to specify the direction the arrow will point in the
	 * GUIelement.
	 */
	public enum Direction{
		UP,DOWN,LEFT,RIGHT;
	}
}
