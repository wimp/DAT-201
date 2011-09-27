package sim.gui.elements;

import java.awt.Dimension;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class GuiStack extends GuiElement {
	
	JList list;
	public void setData(Object[] data){
		list.setListData(data);
	}
	public GuiStack(int x, int y, int w, int h,Object[] data){
		super();
		setBounds(x,y,w,h);
		initGraphics(data);
	}
	
	private void initGraphics(Object[] data){
		list = new JList(data); //data has type Object[]
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(getWidth(), getHeight()));
		this.add(listScroller);
	}
}
