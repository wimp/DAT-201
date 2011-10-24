package sim.editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import sim.gui.elements.GuiElement;
import sim.gui.elements.GuiSettings;
import sim.structures.Array;
import sim.structures.LinkedList;
import sim.structures.Stack;

public class EditorInfo extends JPanel{
	InfoPanel panel;
	InfoType infotype;

	public EditorInfo(Rectangle bounds){
		panel = new InfoPanel(InfoType.NONE,bounds);
		panel.changeInfo(InfoType.LINKEDLIST);
		setBounds(bounds);
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
		Rectangle bounds; 
		
		public InfoPanel(InfoType infotype, Rectangle bounds){
			super(new BorderLayout());
			setPreferredSize(new Dimension(bounds.width, bounds.height));
			info = infotype;
		}
		public void changeInfo(InfoType infotype){
			removeAll();
			drawComp(infotype);
		}
		private void drawComp(InfoType info){
			JTextArea text = new JTextArea();
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
						"The stack is wicked cool, yo!," +
						"The stack is wicked cool, yo!"
					;
				text.setText(t);
				break;
			case ARRAY:
				Array a = new Array(new Rectangle(0,0, 100, 100), 5);
				a.insertAt("value 1", 1);
				a.insertAt("value 2", 0);
				a.insertAt("value 3", 4);
				add(a.getGuiElement());
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
				ll.addFirst("value 2");
				
				add(ll.getGuiElement(), BorderLayout.WEST);
				String at = 
						"The linkedlist is wicked cool, yo!"
					;
				text.setText(at);
				
				break;
			case QUEUE:
				break;
			case TREE:
				break;
			case VARIABLE:
				break;
			}
		}
	}
}
