package sim;

import java.awt.Rectangle;

import javax.swing.JFrame;

import sim.structures.LinkedList;
import sim.structures.Tree;

public class StructAlgoSim {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		RailwayPostfix rsim = new RailwayPostfix();
		//EditorGui gui = new EditorGui();
		

		JFrame frame = new JFrame();
		frame.setTitle("StructAlgoSim 0.1");
		frame.setSize(700,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		
		LinkedList l = new LinkedList(new Rectangle(100,400, 300,100), true, true);
		Tree t = new Tree(new Rectangle(100,100, 500,500),false);
		frame.add(t.getGuiElement());
		frame.add(l.getGuiElement());
		frame.validate();
		frame.setVisible(true);
	}
}
