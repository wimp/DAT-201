package sim.gui.elements;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import sim.structures.Stack;

public class GuiStack extends GuiElement {
	
	JList list;
	public void setData(Object[] data){
		list.setListData(data);
	}
	public GuiStack(Object[] data){
		super();
		initGraphics(data);
	}
	
	private void initGraphics(Object[] data){
		list = new JList(data); //data has type Object[]
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(250, 80));
		this.add(listScroller);
	}
	@Override
	public JComponent getGuiElement() {
		return list;
	}
}
