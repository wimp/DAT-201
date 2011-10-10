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
		Variable si = new Variable(new Rectangle(390,170,60,50),"#",false);
		Variable so = new Variable(new Rectangle(275,170,60,50),"",false);

		Variable infix = new Variable(new Rectangle(500,170,200,50),"a+b*c-e",true);
		
		MoveChar moveInfix = new MoveChar(new Rectangle(450,170,50,50),Direction.LEFT,infix,si);
		MoveChar moveSiSo = new MoveChar(new Rectangle(335,170,55,50),Direction.LEFT,si,so);
		
		Variable postfix = new Variable(new Rectangle(25,170,200,50),"                               ",true);
		MoveChar movePostfix = new MoveChar(new Rectangle(225,170,50,50),Direction.LEFT,so,postfix);
		
		Pop popSi = new Pop(new Rectangle(405,300,70,25),s, si);
		Push pushSi = new Push(new Rectangle(405,350,70,25),si, s);
		Pop popSo = new Pop(new Rectangle(225,300,70,25),s, so);
		Push pushSo = new Push(new Rectangle(225,350,70,25),so, s);
		
		frame.add(popSi.			getGuiElement());
		frame.add(pushSi.			getGuiElement());
		frame.add(popSo.			getGuiElement());
		frame.add(pushSo.			getGuiElement());
		frame.add(moveSiSo.         getGuiElement());
		frame.add(si.			getGuiElement());
		frame.add(so.			getGuiElement());
		frame.add(s.			getGuiElement());
		frame.add(infix.		getGuiElement());
		frame.add(postfix.		getGuiElement());
		frame.add(moveInfix.	getGuiElement());
		frame.add(movePostfix.	getGuiElement());
		frame.validate();
		frame.setVisible(true);
	}
}
