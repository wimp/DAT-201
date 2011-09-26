package sim;

import java.awt.FlowLayout;

import javax.swing.JFrame;

import sim.functions.Pop;
import sim.functions.Push;
import sim.gui.elements.GuiStack;
import sim.gui.elements.GuiVariable;

public class StructAlgoSim {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setTitle("StructAlgoSim 0.1");
		frame.setSize(700,600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new FlowLayout());
		GuiStack s = new GuiStack();
		GuiVariable v = new GuiVariable("win",false);
		Pop p = new Pop(s, v);
		frame.add(p);
		frame.add(v);
		frame.add(s);
		frame.validate();
	}

}
