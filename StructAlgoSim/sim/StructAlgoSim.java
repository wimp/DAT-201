package sim;

import javax.swing.JFrame;

import sim.gui.elements.GuiStack;

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
		
		frame.add(new GuiStack());
		frame.validate();
	}

}
