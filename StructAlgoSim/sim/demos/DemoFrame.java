package sim.demos;

import javax.swing.JFrame;

import sim.functions.Add;
import sim.functions.Get;
import sim.functions.Insert;
import sim.functions.Pop;
import sim.functions.Push;
import sim.functions.Remove;
import sim.functions.Set;
import sim.structures.Array;
import sim.structures.Heap;
import sim.structures.InfoPanel;
import sim.structures.LinkedList;
import sim.structures.Queue;
import sim.structures.Stack;
import sim.structures.Tree;
import sim.structures.Variable;
/**
 * DemoFrame - All programmatical demos should have an instance of DemoFrame instead of JFrame for similar appearance plus easier adding. Using DemoFrame lets you add the element instead of the GuiElement to the frame. Add each element as you would add a JLabel or JButton to a JFrame.
 */
public class DemoFrame {
	JFrame frame;
	DemoFrame(String title){
		frame = new JFrame();
		frame.setTitle("StructAlgoSim 0.1 - "+title);
		frame.setSize(800,550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setVisible(true);
	}

	public void add(Object element){
		if(element instanceof Array)
			frame.add(((Array) element).getGuiElement());
		else if(element instanceof Heap)
			frame.add(((Heap) element).getGuiElement());
		else if(element instanceof InfoPanel)
			frame.add(((InfoPanel) element).getGuiElement());
		else if(element instanceof LinkedList)
			frame.add(((LinkedList) element).getGuiElement());
		else if(element instanceof Queue)
			frame.add(((Queue) element).getGuiElement());
		else if(element instanceof Stack)
			frame.add(((Stack) element).getGuiElement());
		else if(element instanceof Tree)
			frame.add(((Tree) element).getGuiElement());
		else if(element instanceof Variable)
			frame.add(((Variable) element).getGuiElement());
		else if(element instanceof Add)
			frame.add(((Add) element).getGuiElement());
		else if(element instanceof Get)
			frame.add(((Get) element).getGuiElement());
		else if(element instanceof Insert)
			frame.add(((Insert) element).getGuiElement());
		else if(element instanceof Pop)
			frame.add(((Pop) element).getGuiElement());
		else if(element instanceof Push)
			frame.add(((Push) element).getGuiElement());
		else if(element instanceof Remove)
			frame.add(((Remove) element).getGuiElement());
		else if(element instanceof Set)
			frame.add(((Set) element).getGuiElement());
	}

	public void validate(){
		frame.validate();
	}
}
