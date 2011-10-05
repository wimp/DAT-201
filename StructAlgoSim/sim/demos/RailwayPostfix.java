package sim.demos;

import java.awt.Rectangle;

import javax.swing.JFrame;

import sim.functions.MoveChar;
import sim.functions.Pop;
import sim.functions.Push;
import sim.functions.MoveChar.Direction;
import sim.structures.Stack;
import sim.structures.Variable;

public class RailwayPostfix {
	public RailwayPostfix(){
		JFrame frame = new JFrame();
		frame.setTitle("StructAlgoSim 0.1 - infix to postfix using railway algorithm");
		frame.setSize(700,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		Stack s = new Stack(new Rectangle(325,300, 70, 200));
		Variable v = new Variable(new Rectangle(335,170,70,50),"#",false);
		Variable infix = new Variable(new Rectangle(500,170,200,50),"a+b*c-e",true);
		MoveChar moveInfix = new MoveChar(new Rectangle(450,170,50,50),Direction.LEFT,infix,v);
		
		Variable postfix = new Variable(new Rectangle(25,170,200,50),"                               ",true);
		MoveChar movePostfix = new MoveChar(new Rectangle(225,170,50,50),Direction.LEFT,v,postfix);
		
		Pop pop = new Pop(new Rectangle(225,300,70,25),s, v);
		Push push = new Push(new Rectangle(225,350,70,25),v, s);
		
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
