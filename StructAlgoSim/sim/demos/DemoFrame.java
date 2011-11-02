package sim.demos;

import javax.swing.JFrame;
import sim.gui.elements.GuiElement.GraphicalStructure;
/**
 * DemoFrame - All programatical demos should have an instance of DemoFrame instead of JFrame for similar appearance plus easier adding. Using DemoFrame lets you add the element instead of the GuiElement to the frame. Add each element as you would add a JLabel or JButton to a JFrame.
 */
public class DemoFrame {
	JFrame frame;
	
	/**
	 * Constructor
	 * @param title - Appending Demo-title. "StructAlgoSim Version - "+title
	 */
	DemoFrame(String title){
		frame = new JFrame();
		frame.setTitle("StructAlgoSim 0.1 - "+title);
		frame.setSize(800,550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setVisible(true);
	}

	public void add(Object element){
		if(element instanceof GraphicalStructure)
			((GraphicalStructure)element).getGuiElement();
	}

	public void validate(){
		frame.validate();
	}
}
