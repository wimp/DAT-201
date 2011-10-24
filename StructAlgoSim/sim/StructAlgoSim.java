package sim;

import java.awt.Rectangle;

import javax.swing.JFrame;

import sim.editor.EditorInfo;
import sim.editor.EditorInfo.InfoPanel;
import sim.functions.Add;
import sim.functions.Pop;
import sim.functions.Push;
import sim.functions.Remove;
import sim.structures.LinkedList;
import sim.structures.Queue;
import sim.structures.Stack;
import sim.structures.Tree;
import sim.structures.Variable;

public class StructAlgoSim {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	//	RailwayPostfix rsim = new RailwayPostfix();
	//	new EditorGui();
		

		JFrame frame = new JFrame();
		frame.setTitle("StructAlgoSim 0.1");
		frame.setSize(800,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		//Tree t = new Tree(new Rectangle(100,100, 650,300),false);
		
		Stack s = new Stack(new Rectangle(100,100,400, 300));

		EditorInfo info = new EditorInfo(new Rectangle(0,0,400,200));
		frame.add(info);
		Queue q = new Queue(new Rectangle(300,100,300,100));
		Tree t = new Tree(new Rectangle(100,100, 400, 400), false);
		LinkedList l = new LinkedList(new Rectangle(100, 400, 300, 200));
		Variable v = new Variable(new Rectangle(100, 10, 100, 25), "", true);
		Push push = new Push(new Rectangle(50,50,75,50), v, s);
		Pop pop = new Pop(new Rectangle(125,50,75,50), s, v);
		Add a = new Add(new Rectangle(300,10, 75,75),q, v);

		Remove r = new Remove(new Rectangle(375,10, 75,75),q, v);
//		frame.add(s.getGuiElement());
	
//		frame.add(push.getGuiElement());
//		frame.add(pop.getGuiElement());
//		frame.add(l.getGuiElement());
//		frame.add(v.getGuiElement());
//		frame.add(a.getGuiElement());
//		frame.add(q.getGuiElement());
//		frame.add(r.getGuiElement());
//		frame.add(t.getGuiElement());
		frame.validate();
		frame.setVisible(true);
		//frame.repaint();
	}
}
