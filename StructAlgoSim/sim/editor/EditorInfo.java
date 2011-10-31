package sim.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import sim.functions.Add;
import sim.gui.elements.GuiElement;
import sim.gui.elements.GuiSettings;
import sim.structures.Array;
import sim.structures.LinkedList;
import sim.structures.Queue;
import sim.structures.Stack;
import sim.structures.Tree;
import sim.structures.Variable;

@SuppressWarnings("serial")
public class EditorInfo extends JPanel{
	InfoPanel panel;
	InfoType infotype;

	public EditorInfo(){
		panel = new InfoPanel();
		panel.changeInfo(InfoType.LINKEDLIST);
		setLayout(new GridLayout(1,1));
		add(panel);
	}
	public void setInfoType(InfoType infotype){
		this.infotype = infotype;
		this.panel.changeInfo(infotype);
		validate();
	}
	public static enum InfoType{
		//Structures
		STACK,
		ARRAY,
		HEAP,
		LINKEDLIST,
		QUEUE,
		TREE,
		VARIABLE,
		//Functions
		ADD,
		INSERT,
		POP,
		PUSH,
		REMOVE,
		GET,
		SET,
		//Default
		NONE	
	}
	public class InfoPanel extends JPanel{
		GuiElement type;
		InfoType info;
		
		public InfoPanel(){
			super(new GridLayout(1,2));
			info = InfoType.NONE;
		}
		public void changeInfo(InfoType infotype){
			removeAll();
			drawComp(infotype);
		}
		private void drawComp(InfoType info){
			JTextArea text = new JTextArea();
			text.setEditable(false);
			text.setBorder(BorderFactory.createLineBorder(Color.BLACK));

			JScrollPane textPane = new JScrollPane(text);
			add(textPane);
			text.setLineWrap(true);
			text.setWrapStyleWord(true);
			textPane.setPreferredSize(new Dimension(getWidth()-150,getHeight()));
			
			String t;
			
			switch(info){
			case STACK:
				Stack s = new Stack(new Rectangle(0,0,(int)(GuiSettings.STACKELEMENTWIDTH*(3/2.0)), 150));
				s.push("value 1");
				s.push("value 2");
				s.push("value 3");
				s.push("value 4");
				add(s.getGuiElement());

				t = 
						"Structure information:\n" +
						"The elements in a stack can only be accessed from one end, the top.\n" +
						"The methods to add or remove elements are Push and Pop, respectively.\n" +
						"\n" +
						"Editor use:\n" +
						"Place in the desired position and link with a Pop and/or a Push function."
					;
				text.setText(t);
				break;
			case ARRAY:
				Array a = new Array(new Rectangle(0,0, 100, 100), 5, 2);
				a.insertAt("value 1", 0, 1);
				a.insertAt("value 2", 1, 1);
				a.insertAt("value 3", 2);
				a.insertAt("yoyoyo", 4, 1);
				add(a.getGuiElement(), BorderLayout.WEST);
				t = 
						"Structure information:\n" +
						"An array has a fixed size and a fixed amount of dimensions. To access the data in an array, one simply points" +
						"to the indices of the position in a given dimension. Arrays are good for random access, but the fact that they have" +
						"a fixed size implements a few problem if the data one wants to store has variable size as that would require a " +
						"remake of the array with new sizes.\n" +
						"\n" +
						"Editor use:\n" +
						"Place in the desired position, then select one- or two-dimensional as well as size of the two dimensions." +
						"The array can be linked with Set and Get. If you want to delete an element, simply set the value blank."
					;
				text.setText(t);
				break;
			case HEAP:
				break;
			case LINKEDLIST:
				LinkedList ll = new LinkedList(new Rectangle(0,0,100, 75));
				ll.addFirst("value 1");
				ll.addFirst("value 2");
				
				add(ll.getGuiElement(), BorderLayout.WEST);
				t = 
						"Structure information:\n" +
						"A linked list consist of a number of elements that are connected to each other, either singularily or doubly." +
						"In the first case every element always links to their next neighbor in the list, while in the second case" +
						"the elements will point the link to both their next and to their previous neighbor. Linked lists can also" +
						"be circular. In this case the last element points to the first element in the list, and if the list is doubly" +
						"linked, the first element will point to the last element in the list.\n" +
						"\n" +
						"Editor use:\n" +
						"Place in the desired position and use the controls on the element to choose between single or doubly linked as well" +
						"as circular or non-circular. The linked list can be linked with Insert, Add and Remove"
					;
				text.setText(t);
				
				break;
			case QUEUE:
				Queue q = new Queue(new Rectangle(0,0,100, 75));
				q.add("value 1");
				q.add("value 2");
				q.add("value 3");
				
				add(q.getGuiElement(), BorderLayout.WEST);
				t = 
						"Structure information:\n" +
						"A queue is a special case of a stack that only allows items to be added at the top and removed at the bottom." +
						"This comes to use when you want to sort items from old to new for example, as you only can get the oldest item" +
						"out of the queue at any time.\n" +
						"\n" +
						"Editor use:\n" +
						"Place in desired position and link with Add and Remove to, believe it or not, add or remove items from the queue."
					;
				text.setText(t);
				
				break;
			case TREE:
				Tree tr = new Tree(new Rectangle(0,0,100, 75), true);
				tr.addBreadthFirst("value 1");
				tr.addBreadthFirst("value 2");
				tr.addBreadthFirst("value 3");
				
				add(tr.getGuiElement(), BorderLayout.WEST);
				t = 
						"Structure information:\n" +
						"A tree consists of a root with one or more sub-elements that further can be parents to new" +
						"sub-elements. In a binary tree the parents can only have two sub-elements, while in an N-ary tree the parents" +
						"can have N number of sub-elements. I many ways the tree is a special case of a linked list, but diverges from" +
						"the linked list in that it is, in some cases, much more efficient to traverse and operate on.\n"+
						"\n" +
						"Editor use:\n" +
						"Place in desired position and use the controls on the element to change how the tree is built. Can be linked with" +
						"Add, Remove, Insert, Get or Set"
					;
				text.setText(t);
				
				break;
			case VARIABLE:
				Variable v = new Variable(new Rectangle(50,50,50,100), "Variable value", true);
				
				add(v.getGuiElement());
				t= 
						"Structure information:\n" +
						"A variable is used to pass values to and from functions and structures.\n" +
						"\n" +
						"Editor use:\n" +
						"Place in desired position after choosing whether it should be editable or not. Can be linked with all" +
						"the functions. Read information about specific use when selecting a function."
						;
				text.setText(t);
				break;
			case ADD:
				Add add = new Add(new Rectangle(50,50,80,30));
				add(add.getGuiElement());
				t = 
						"Function information:\n" +
						"Add adds an element to a given index or at the default end- or starting point of a structure.\n" +
						"\n" +
						"Editor use:\n" +
						"Place at desired position and link with a variable to be able to select whether that variable should be" +
						"the index at which to add the value, or if it should be the value to add. If Add is only linked to a value-" +
						"variable it attempts to add the value given at the end or beginning of the structure it is linked with. Can be linked" +
						"with: Queue, Linked List, Tree, Heap or Variable."
						;
				text.setText(t);
				break;
			
			}
		}
	}
}
