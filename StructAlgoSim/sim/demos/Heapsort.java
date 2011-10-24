package sim.demos;

import java.awt.Rectangle;

import javax.swing.JFrame;

import sim.functions.Remove;
import sim.structures.Heap;
import sim.structures.Stack;
import sim.structures.Variable;

public class Heapsort {
	public Heapsort(){
	JFrame frame = new JFrame();
	frame.setTitle("StructAlgoSim 0.1 - Heapsort");
	frame.setSize(700,600);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setLayout(null);
	
	
	Heap h = new Heap(new Rectangle(200,100, 475, 300), true);
	h.addBreadthFirst("6");
	h.addBreadthFirst("13");
	h.addBreadthFirst("4");
	h.addBreadthFirst("5");
	h.addBreadthFirst("9");
	h.addBreadthFirst("2");
	h.addBreadthFirst("1");
	h.addBreadthFirst("12");
	h.addBreadthFirst("7");
	h.addBreadthFirst("8");
	h.addBreadthFirst("11");
	h.addBreadthFirst("10");
	
	Variable v = new Variable(new Rectangle(100,100, 70, 25), "output", true);
	Variable i = new Variable(new Rectangle(400,100, 70, 25), "0", false);
	Remove r = new Remove(new Rectangle(0, 100, 75,75),h, v, i);
	Stack s = new Stack(new Rectangle(100, 100, 100, 200));	
	
	frame.add(h.		getGuiElement());
	frame.add(v.		getGuiElement());
	frame.add(s.		getGuiElement());
	frame.validate();
	frame.setVisible(true);
}
}
