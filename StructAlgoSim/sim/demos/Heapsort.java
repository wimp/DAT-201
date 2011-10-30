package sim.demos;

import java.awt.Rectangle;

import javax.swing.JFrame;

import sim.functions.Insert;
import sim.functions.Push;
import sim.functions.Remove;
import sim.structures.Heap;
import sim.structures.InfoPanel;
import sim.structures.Stack;
import sim.structures.Tree;
import sim.structures.Tree.Traversal;
import sim.structures.Variable;

public class Heapsort {
	public static void main(String[] args) {
		
		new Heapsort();
		
	}
	
	public Heapsort(){
	JFrame frame = new JFrame();
	frame.setTitle("StructAlgoSim 0.1 - Heapsort");
	frame.setSize(700,600);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setLayout(null);
	
	Heap h = new Heap(new Rectangle(200,100, 475, 500), true);
	h.setTraversal(Traversal.BREADTHFIRST);
//	h.addBreadthFirst("6");
//	h.addBreadthFirst("13");
//	h.addBreadthFirst("4");
//	h.addBreadthFirst("5");
//	h.addBreadthFirst("9");
//	h.addBreadthFirst("2");
//	h.addBreadthFirst("1");
//	h.addBreadthFirst("12");
//	h.addBreadthFirst("7");
//	h.addBreadthFirst("8");
//	h.addBreadthFirst("11");
//	h.addBreadthFirst("10");

	h.addBreadthFirst("Rune");
	h.addBreadthFirst("Jan-Vidar");
	h.addBreadthFirst("Gandalf");
	h.addBreadthFirst("Frodo");
	h.addBreadthFirst("Red Bull");
	h.addBreadthFirst("Lommebok");
	h.addBreadthFirst("Orc");
	h.addBreadthFirst("Uruk-hai");
	h.addBreadthFirst("Extra");
	h.addBreadthFirst("Mus");
	h.addBreadthFirst("Imsdal");
	h.addBreadthFirst("Rihanna");
	
	//Tree t = new Tree(new Rectangle(200,100, 475, 300), true);
	Variable v = new Variable(new Rectangle(25,10, 70, 25), "output", true);
	Variable i = new Variable(new Rectangle(300,10, 70, 25), "0", false);
	Remove r = new Remove(new Rectangle(95, 10, 75,75), h, v, i);
	Stack s = new Stack(new Rectangle(100, 100, 100, 200));	
	Push p = new Push(new Rectangle(10, 100, 75,75), v, s);
	
	String info = 
			"This is a demonstration of the heapsort algorithm." +
			"It uses a heap which is built as either a max or a min-heap. " +
			"A max heap satisfies the condition of each node having only " +
			"smaller or equal nodes in its subtree. For a min-heap each node " +
			"has only larger values as its descendants." +
			System.getProperty("line.separator") +
			"This property assures that the root will always be either the largest or the smallest value in the heap." +
			" Heapsort uses this property to sort the elements by removing the root and placing it in in a queue, rearranging the heap and repeating this " +
			"until the heap is empty. This will result in a sorted queue." +
			System.getProperty("line.separator")+
			"";
	
	InfoPanel in = new InfoPanel(new Rectangle(10, 300, 150,200),info);
	
	frame.add(v.		getGuiElement());
	frame.add(h.		getGuiElement());
	frame.add(s.		getGuiElement());
	frame.add(p.		getGuiElement());
	frame.add(r.		getGuiElement());
	frame.add(in.		getGuiElement());
	frame.validate();
	frame.setVisible(true);
}
}
