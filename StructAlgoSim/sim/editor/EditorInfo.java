package sim.editor;

import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import sim.gui.elements.GuiElement;
import sim.structures.Array;
import sim.structures.Stack;

public class EditorInfo extends JPanel{
	InfoPanel panel;
	InfoType infotype;

	public EditorInfo(Rectangle bounds){
		panel = new InfoPanel(InfoType.NONE);
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
		
		public InfoPanel(InfoType	infotype){
			super();
			info = infotype;
			info = InfoType.ARRAY;
			drawComp(info);
			setBounds(new Rectangle(0,0,200, 400));
		}
		public void changeInfo(InfoType infotype){
			removeAll();
			drawComp(infotype);
		}
		private void drawComp(InfoType info){
			int asd = 1;
			switch(info){
			case STACK:
				Stack s = new Stack(new Rectangle(0,0, 100, 100));
				s.push("value 1");
				s.push("value 2");
				s.push("value 3");
				s.push("value 4");
				add(s.getGuiElement());
				JTextArea stext = new JTextArea();
				String st = 
						"The stack is wicked cool, yo!"
					;
				stext.setText(st);
				add(stext);
				break;
			case ARRAY:
				Array a = new Array(new Rectangle(0,0, 100, 100), 5);
				a.insertAt("value 1", 1);
				a.insertAt("value 2", 0);
				a.insertAt("value 3", 4);
				add(a.getGuiElement());
				JTextArea atext = new JTextArea();
				String at = 
						"The stack is wicked cool, yo!"
					;
				atext.setText(at);
				add(atext);
				break;
			case HEAP:
				break;
			case LINKEDLIST:
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
