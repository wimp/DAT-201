package sim;

import java.awt.Rectangle;

import javax.swing.JFrame;

import sim.functions.Add;
import sim.functions.Insert;
import sim.functions.Pop;
import sim.functions.Push;
import sim.functions.Remove;
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

		Variable v = new Variable(new Rectangle(100,50,100,20),"win", true);
		LinkedList list = new LinkedList(new Rectangle(200,300,500,100), true,true,false);
		Add add = new Add(new Rectangle(200,200, 75,50), list, v);
		Stack s = new Stack(new Rectangle(100,300, 100, 100));
		Variable i = new Variable(new Rectangle(100,50,100,20),"1",true);
		Variable r = new Variable(new Rectangle(400,50,100,20)," ",false);
		Remove rm = new Remove(new Rectangle(400,200,120,50),list,r,i);
		Array a = new Array(new Rectangle(100,100,400,100),8);
		Insert ib = new Insert(new Rectangle(290,200,120,50),a,v,i);
		frame.add(v.getGuiElement());
		frame.add(i.getGuiElement());
		frame.add(list.getGuiElement());
		frame.add(rm.getGuiElement());
		frame.add(r.getGuiElement());
		frame.add(add.getGuiElement());
		frame.add(ib.getGuiElement());
		frame.validate();
		frame.setVisible(true);
	}
}
