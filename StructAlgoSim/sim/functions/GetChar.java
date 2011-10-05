package sim.functions;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sim.gui.elements.GuiElement;
import sim.gui.elements.GuiFunction;
import sim.structures.Variable;

public class GetChar implements ActionListener {
	Variable input;
	Variable output;
	GuiFunction gui;
	private boolean removeAfterFetch;
	private boolean append;
	public GuiElement getGuiElement(){
		return gui;
	}
	/**
	 * @author Rune B. Kalleberg
	 * @param x = x Pos
	 * @param y = y Pos
	 * @param w = width
	 * @param h = height
	 * @param dir = direction of arrow
	 * @param append = append output (true) or replace output(false)
	 * @param removeAfterFetch = remove character from input after fetch
	 * @param input = input var
	 * @param output = output var
	 */
	public GetChar(Rectangle bounds, Direction dir, boolean append, boolean removeAfterFetch,Variable input, Variable output){
	//TODO add direction here
		gui = new GuiFunction(bounds,"<-");
		gui.getButton().addActionListener(this);
		this.input = input;
		this.output = output;
		this.removeAfterFetch = removeAfterFetch;
		this.append = append;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String val = input.getValue();
			if(append)
				output.setValue(output.getValue() + val.substring(0,1));
			else
				output.setValue(val.substring(0,1));
			if(removeAfterFetch){
				input.setValue(val.substring(1));
			}
	}

	public enum Direction{
		UP,DOWN,LEFT,RIGHT;
	}
}
