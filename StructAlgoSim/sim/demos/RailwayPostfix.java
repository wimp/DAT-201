package sim.demos;

import javax.swing.JFrame;

import sim.functions.Pop;
import sim.functions.Push;
import sim.structures.GetChar;
import sim.structures.GetChar.Direction;
import sim.structures.Stack;
import sim.structures.Variable;

public class RailwayPostfix {
	public RailwayPostfix(){
		JFrame frame = new JFrame();
		frame.setTitle("StructAlgoSim 0.1 - infix to postfix using railway algorithm");
		frame.setSize(700,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		Stack s = new Stack(300,200, 70, 200);
		Variable v = new Variable(280,170,100,100,"#",false);
		Variable infix = new Variable(500,170,200,100,"2+2",true);
		GetChar moveInfix = new GetChar(450,170,100,100,Direction.LEFT, false,true,infix,v);
		
		Variable postfix = new Variable(50,170,200,100,"                               ",true);
		GetChar movePostfix = new GetChar(200,170,100,100,Direction.LEFT, true,true,v,postfix);
		
		Pop pop = new Pop(200,200,100,50,s, v);
		Push push = new Push(200,250,100,50,s, v);
		
		frame.add(pop.			getGuiElement());
		frame.add(push.			getGuiElement());
		frame.add(v.			getGuiElement());
		frame.add(s.			getGuiElement());
		frame.add(infix.		getGuiElement());
		frame.add(postfix.		getGuiElement());
		frame.add(moveInfix.	getGuiElement());
		frame.add(movePostfix.	getGuiElement());
		frame.validate();
		frame.setVisible(true);
	}
}
