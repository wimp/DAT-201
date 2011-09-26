package sim.gui.elements;

import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import sim.structures.Stack;

public class GuiStack extends GuiElement {
	
	Stack s;
	
	public GuiStack(){
		super();
		
		s = new Stack();
		s.push("Hello");
		s.push("Hello1");
		s.push("Hello2");
		draw();
	}
	
	private void draw(){
		Object[] data = s.toArray();
		JList list = new JList(data); //data has type Object[]
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(250, 80));
		this.add(listScroller);
	}
}
