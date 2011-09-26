package sim;

import java.util.Stack;

public class GuiStack extends GuiElement {
	
	Stack s;
	
	GuiStack(){
		super();
		
		s = new Stack();
		s.push("Hello");
		s.push("Hello1");
		s.push("Hello2");
	}
	
	private void draw(){
		
	}
}
