package sim.gui.elements;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

@SuppressWarnings("serial")
public class GuiArray extends GuiElement {
	private Vector<Object> data;
	JList list;
	
	public void setData(Vector<Object> data){
		list.setListData(data);
	}
	
	public int getListElementIndex(){
		return list.getSelectedIndex();
	}
	
	public GuiArray(Rectangle bounds,Vector<Object> data){
		super();
		this.data = data;
		setBounds(bounds);
		initGraphics();
	}
	
	public void initGraphics(){
		list = new JList(data);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(getWidth(), getHeight()));
		this.add(listScroller);
	}
}
