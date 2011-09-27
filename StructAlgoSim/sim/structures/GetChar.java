package sim.structures;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sim.gui.elements.GuiElement;
import sim.gui.elements.GuiFunction;

public class GetChar implements ActionListener {
	Variable input;
	Variable output;
	GuiFunction gui;
	private FetchType type;
	private boolean removeAfterFetch;
	public GuiElement getGuiElement(){
		return gui;
	}
	public GetChar(int x, int y, int w, int h, Direction dir, FetchType type, boolean removeAfterFetch,Variable input, Variable output){
		//TODO add direction here
		gui = new GuiFunction(x,y,w,h,"<-");
		gui.getButton().addActionListener(this);
		this.input = input;
		this.output = output;
		this.type = type;
		this.removeAfterFetch = removeAfterFetch;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String val = input.getValue();
		if(type == FetchType.FIRST){
			output.setValue(val.substring(0,1));
			if(removeAfterFetch){
				input.setValue(val.substring(1));
			}
		}
		else{
			output.setValue(val.substring(val.length()-1));
			if(removeAfterFetch){
				input.setValue(val.substring(0,val.length()-2));
			}
		}
		
		
	}

	public enum Direction{
		UP,DOWN,LEFT,RIGHT;
	}
	public enum FetchType{
		FIRST,LAST;
	}
}
