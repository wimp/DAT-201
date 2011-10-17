package sim;

import java.awt.Rectangle;

import javax.swing.JFrame;

import sim.editor.EditorGui;
import sim.functions.Add;
import sim.functions.Push;
import sim.structures.LinkedList;
import sim.structures.Stack;
import sim.structures.Tree;
import sim.structures.Variable;

public class StructAlgoSim {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	//	RailwayPostfix rsim = new RailwayPostfix();
		//EditorGui gui = new EditorGui();
		

		JFrame frame = new JFrame();
		frame.setTitle("StructAlgoSim 0.1");
		frame.setSize(800,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		//Tree t = new Tree(new Rectangle(100,100, 650,300),false);
		Stack s = new Stack(new Rectangle(100,100,200, 300));
		LinkedList l = new LinkedList(new Rectangle(100, 400, 300, 200));
		Variable v = new Variable(new Rectangle(100, 10, 100, 25), "", true);
		Push p = new Push(new Rectangle(50,50,75,50), v, s);
		Add a = new Add(new Rectangle(10,10, 90,90),l, v);
		frame.add(s.getGuiElement());
		frame.add(p.getGuiElement());
		frame.add(l.getGuiElement());
		frame.add(v.getGuiElement());
		frame.add(a.getGuiElement());
		frame.validate();
		frame.setVisible(true);
	}
}
