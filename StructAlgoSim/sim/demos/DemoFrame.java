package sim.demos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import sim.functions.Add;
import sim.functions.Get;
import sim.functions.Insert;
import sim.functions.Pop;
import sim.functions.Push;
import sim.functions.Remove;
import sim.functions.Set;
import sim.gui.elements.GuiElement;
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
	Graphics g;

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

		panel = new DemoPanel();
		frame.add(panel);
	}
	
	class DemoPanel extends JPanel{
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.gray);
			
			System.out.println("Drawing links");
			for(int i = 0; i<links.size();i++){
				System.out.println("Drawing "+links.get(i).p1.x);
				g2d.drawLine(links.get(i).p1.x, links.get(i).p1.y, links.get(i).p2.x, links.get(i).p2.y);
				g2d.drawLine(links.get(i).p2.x, links.get(i).p2.y, links.get(i).p3.x, links.get(i).p3.y);
				g2d.drawLine(links.get(i).p3.x, links.get(i).p3.y, links.get(i).p4.x, links.get(i).p4.y);
			}
		}
	}

	ArrayList<GuiElement> guiElements = new ArrayList<GuiElement>();
	ArrayList<Object> elements = new ArrayList<Object>();

	/**
	 * Internal element adding handler
	 * @param guiElement
	 * @param element2 
	 */
	private void addElement(GuiElement guiElement, Object element){
//		panel.add(guiElement);
		guiElements.add(guiElement);
		elements.add(element);
	}

	/**
	 * Adds elements to working frame and stores them for later link processing
	 * @param element
	 */
	public void add(Object element){
		if(element instanceof Array)
			addElement(((Array) element).getGuiElement(), element);
		else if(element instanceof Heap)
			addElement(((Heap) element).getGuiElement(), element);
		else if(element instanceof InfoPanel)
			addElement(((InfoPanel) element).getGuiElement(), element);
		else if(element instanceof LinkedList)
			addElement(((LinkedList) element).getGuiElement(), element);
		else if(element instanceof Queue)
			addElement(((Queue) element).getGuiElement(), element);
		else if(element instanceof Stack)
			addElement(((Stack) element).getGuiElement(), element);
		else if(element instanceof Tree)
			addElement(((Tree) element).getGuiElement(), element);
		else if(element instanceof Variable)
			addElement(((Variable) element).getGuiElement(), element);
		else if(element instanceof Add)
			addElement(((Add) element).getGuiElement(), element);
		else if(element instanceof Get)
			addElement(((Get) element).getGuiElement(), element);
		else if(element instanceof Insert)
			addElement(((Insert) element).getGuiElement(), element);
		else if(element instanceof Pop)
			addElement(((Pop) element).getGuiElement(), element);
		else if(element instanceof Push)
			addElement(((Push) element).getGuiElement(), element);
		else if(element instanceof Remove)
			addElement(((Remove) element).getGuiElement(), element);
		else if(element instanceof Set)
			addElement(((Set) element).getGuiElement(), element);
	}

	ArrayList<Link> links = new ArrayList<Link>();
	private DemoPanel panel;

	/** 
	 * Creates all the links from the elements added
	 */
	private void createLinks(){
		for(int i = 0; i<elements.size();i++){
			//Determine links
			Object element = elements.get(i);
			ArrayList<Object> subElements = getSubElements(element);

			ArrayList<Link> links = new ArrayList<Link>();
			for(int j = 0; j<subElements.size();j++){
				Link link = new Link();
				link.from = element;
				link.to = subElements.get(j);
				link.fromGui = getGuiElement(element);
				link.toGui = getGuiElement(subElements.get(j));

//				System.out.println("Calculating link from "+link.from+" to "+link.to);
				link.calculate();
				links.add(link);
			}
		}
	}


	/**
	 * Scans through instanceof checks to get the right gui element
	 * @param element
	 * @return
	 */
	private GuiElement getGuiElement(Object element){
		if(element instanceof Array)
			return(((Array) element).getGuiElement());
		else if(element instanceof Heap)
			return(((Heap) element).getGuiElement());
		else if(element instanceof InfoPanel)
			return(((InfoPanel) element).getGuiElement());
		else if(element instanceof LinkedList)
			return(((LinkedList) element).getGuiElement());
		else if(element instanceof Queue)
			return(((Queue) element).getGuiElement());
		else if(element instanceof Stack)
			return(((Stack) element).getGuiElement());
		else if(element instanceof Tree)
			return(((Tree) element).getGuiElement());
		else if(element instanceof Variable)
			return(((Variable) element).getGuiElement());
		else if(element instanceof Add)
			return(((Add) element).getGuiElement());
		else if(element instanceof Get)
			return(((Get) element).getGuiElement());
		else if(element instanceof Insert)
			return(((Insert) element).getGuiElement());
		else if(element instanceof Pop)
			return(((Pop) element).getGuiElement());
		else if(element instanceof Push)
			return(((Push) element).getGuiElement());
		else if(element instanceof Remove)
			return(((Remove) element).getGuiElement());
		else if(element instanceof Set)
			return(((Set) element).getGuiElement());
		else 
			return null;
	}
	private ArrayList<Object> getSubElements(Object element) {
		ArrayList<Object> subElements = new ArrayList<Object>();
		if(element instanceof Add){
			subElements.add(((Add)element).getIndexVariable());
			subElements.add(((Add)element).getSourceVariable());
			subElements.add(((Add)element).getTarget());
		}
		else if(element instanceof Get){
			if(((Get)element).getIndexVariable() != null) //To prevent nullpointerexception for certain Get instances without index variable
				subElements.add(((Get)element).getIndexVariable());
			subElements.add(((Get)element).getSource());
			subElements.add(((Get)element).getTarget());
		}
		else if(element instanceof Insert){
			subElements.add(((Insert)element).getIndexVariable());
			subElements.add(((Insert)element).getSourceVariable());
			subElements.add(((Insert)element).getTarget());
		}
		else if(element instanceof Pop){
			subElements.add(((Pop)element).getTargetVariable());
			subElements.add(((Pop)element).getSource());
		}
		else if(element instanceof Push){
			subElements.add(((Push)element).getSourceVariable());
			subElements.add(((Push)element).getTarget());
		}
		else if(element instanceof Remove){
			subElements.add(((Remove)element).getIndexVariable());
			subElements.add(((Remove)element).getTargetVariable());
			subElements.add(((Remove)element).getSource());
		}
		else if(element instanceof Set){
			subElements.add(((Set)element).getIndexVariable());
			subElements.add(((Set)element).getSourceVariable());
			subElements.add(((Set)element).getTarget());
		}
		return subElements;
	}

	class Link{
		protected GuiElement 	fromGui;
		protected GuiElement 	toGui;
		protected Object	 	from;
		protected Object	 	to;
		protected Point			p1;
		protected Point			p2;
		protected Point			p3;
		protected Point			p4;

		void calculate(){
			int fromX 		= fromGui.getX();
			int fromY 		= fromGui.getY();
			int fromWidth 	= fromGui.getWidth();
			int fromHeight 	= fromGui.getHeight();

			int toX			= toGui.getX();
			int toY			= toGui.getY();
			int toWidth 	= toGui.getWidth();
			int toHeight 	= toGui.getHeight();

			if(fromX+fromWidth < toX){ // 1st element is to the left of 2nd element
				int middleOfElements = toX - ((toX - (fromX + fromWidth)) / 2);
				p1 = new Point(fromX+fromWidth,fromY + (fromHeight / 2));
				p2 = new Point(middleOfElements,p1.y);
				p3 = new Point(middleOfElements,toY + (toHeight / 2));
				p4 = new Point(toX,p3.y);


			}else if(fromX > toX+toWidth){ // 1st element is to the right of 2nd element
				int middleOfElements = fromX - ((fromX - (toX + toWidth)) / 2);
				p1 = new Point(toX+toWidth,toY + (toHeight / 2));
				p2 = new Point(middleOfElements,p1.y);
				p3 = new Point(middleOfElements,fromY + (fromHeight / 2));
				p4 = new Point(fromX,p3.y);


			}else{ // The elements are above or below each other
				if(fromY+fromHeight < toY){ // 1st element is above 2nd element
					int middleOfElements = toY - ((toY - (fromY + fromHeight)) / 2);
					p1 = new Point(fromX + (fromWidth / 2),fromY+fromHeight);
					p2 = new Point(p1.x,middleOfElements);
					p3 = new Point(toX + (toWidth / 2), middleOfElements);
					p4 = new Point(p3.x,toY);


				}else if(fromY > toY+toHeight){ // 1st element is below 2nd element
					int middleOfElements = fromY - ((fromY - (toY + toHeight)) / 2);
					p1 = new Point(toX + (toWidth / 2), toY+toHeight);
					p2 = new Point(p1.x,middleOfElements);
					p3 = new Point(fromX + (fromWidth / 2), middleOfElements);
					p4 = new Point(p3.x,fromY);
				}
			}
		}
	}

	public void validate(){
		createLinks();
		frame.validate();
		System.out.println("Validating panel");
		panel.validate();
		panel.repaint();
	}
}
