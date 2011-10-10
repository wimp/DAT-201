package sim;

import java.awt.Rectangle;

import javax.swing.JFrame;

import sim.functions.Add;
import sim.functions.InsertBefore;
import sim.functions.Pop;
import sim.functions.Push;
import sim.structures.Array;
import sim.structures.LinkedList;
import sim.structures.Stack;
import sim.structures.Variable;

public class StructAlgoSim {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		RailwayPostfix rsim = new RailwayPostfix();
		
		JFrame frame = new JFrame();
		frame.setTitle("StructAlgoSim 0.1");
		frame.setSize(700,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);

		Stack s = new Stack(new Rectangle(100,300, 100, 100));
		Variable v = new Variable(new Rectangle(50,10,100,20),"win", true);
		Variable i = new Variable(new Rectangle(100,50,100,20),"1",true);
		Variable r = new Variable(new Rectangle(400,50,100,20)," ",false);
		Pop pop = new Pop(new Rectangle(100,200,100,25),s, v);
		Push push = new Push(new Rectangle(200,200,100,25), v, s);
		LinkedList list = new LinkedList(new Rectangle(20,20,400,100),true, false);
		Array a = new Array(new Rectangle(100,100,400,100),8);
		Add add = new Add(new Rectangle(200,200, 70,50), a, v);
		InsertBefore ib = new InsertBefore(new Rectangle(290,200,120,50),a,v,i);
		
		frame.add(v.getGuiElement());
		frame.add(i.getGuiElement());
		frame.add(a.getGuiElement());
//		frame.add(list.getGuiElement());
		frame.add(add.getGuiElement());
		frame.add(ib.getGuiElement());
		frame.validate();
		frame.setVisible(true);
	}

}
