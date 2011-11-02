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
 * @author Rune B. Kalleberg
 *
 */
public class DemoFrame extends JFrame {

	DemoFrame(String title){

		setTitle("StructAlgoSim 0.1 - "+title);
		setSize(800,550);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setVisible(true);
	}
	
	public void add(Object element){
		if(element instanceof Array)
			super.add(((Array) element).getGuiElement());
		else if(element instanceof Heap)
			super.add(((Heap) element).getGuiElement());
		else if(element instanceof InfoPanel)
			super.add(((InfoPanel) element).getGuiElement());
		else if(element instanceof LinkedList)
			super.add(((LinkedList) element).getGuiElement());
		else if(element instanceof Queue)
			super.add(((Queue) element).getGuiElement());
		else if(element instanceof Stack)
			super.add(((Stack) element).getGuiElement());
		else if(element instanceof Tree)
			super.add(((Tree) element).getGuiElement());
		else if(element instanceof Variable)
			super.add(((Variable) element).getGuiElement());
		else if(element instanceof Add)
			super.add(((Add) element).getGuiElement());
		else if(element instanceof Get)
			super.add(((Get) element).getGuiElement());
		else if(element instanceof Insert)
			super.add(((Insert) element).getGuiElement());
		else if(element instanceof Pop)
			super.add(((Push) element).getGuiElement());
		else if(element instanceof Push)
			super.add(((Push) element).getGuiElement());
		else if(element instanceof Remove)
			super.add(((Remove) element).getGuiElement());
		else if(element instanceof Set)
			super.add(((Set) element).getGuiElement());
	}
}
