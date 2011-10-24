package sim.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import sim.gui.elements.GuiElement;
import sim.gui.elements.GuiSettings;
import sim.structures.Array;
import sim.structures.LinkedList;
import sim.structures.Queue;
import sim.structures.Stack;
import sim.structures.Tree;

public class EditorInfo extends JPanel{
	InfoPanel panel;
	InfoType infotype;

	public EditorInfo(){
		panel = new InfoPanel();
		panel.changeInfo(InfoType.TREE);
		add(panel);
	}
	public void setInfoType(InfoType infotype){
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
		MOVECHAR,
		POP,
		PUSH,
		REMOVE,
		//Default
		NONE	
	}
	public class InfoPanel extends JPanel{
		GuiElement type;
		InfoType info;
		
		public InfoPanel(){
			super(new BorderLayout());
			info = InfoType.NONE;
		}
		public void changeInfo(InfoType infotype){
			removeAll();
			drawComp(infotype);
		}
		private void drawComp(InfoType info){
			JTextArea text = new JTextArea();
			text.setBorder(BorderFactory.createLineBorder(Color.BLACK));

			JScrollPane textPane = new JScrollPane(text);
			textPane.setPreferredSize(new Dimension(getWidth()/2, getHeight()));
			add(textPane, BorderLayout.EAST);
			text.setLineWrap(true);
			add(text);
			
			String t;
			
			switch(info){
			case STACK:
				Stack s = new Stack(new Rectangle(0,0,(int)(GuiSettings.STACKELEMENTWIDTH*(3/2.0)), 200));
				s.push("value 1");
				s.push("value 2");
				s.push("value 3");
				s.push("value 4");
				add(s.getGuiElement(), BorderLayout.WEST);

				t = 
						"The stack is wicked cool, yo!"
					;
				text.setText(t);
				break;
			case ARRAY:
				Array a = new Array(new Rectangle(0,0, 100, 100), 5);
				a.insertAt("value 1", 1);
				a.insertAt("value 2", 0);
				a.insertAt("value 3", 4);
				add(a.getGuiElement(), BorderLayout.WEST);
				t = 
						"The array is wicked cool, yo!"
					;
				text.setText(t);
				add(text);
				break;
			case HEAP:
				break;
			case LINKEDLIST:
				LinkedList ll = new LinkedList(new Rectangle(0,0,100, 75));
				ll.addFirst("value 1");
				ll.addFirst("value 2");
				ll.addFirst("value 3");
				
				add(ll.getGuiElement(), BorderLayout.WEST);
				t = 
						"The linkedlist is wicked cool, yo!"
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
						"The queue is wicked cool, yo!"
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
						"The queue is wicked cool, yo!"
					;
				text.setText(t);
				
				break;
			case VARIABLE:
				break;
			}
		}
	}
}
