package sim.gui.elements;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

/**
 * The graphical element of the {@link sim.structures.Stack} stack class
 */
@SuppressWarnings("serial")
public class GuiStack extends GuiElement {
// Class variables //
	JList list;

// Getters and setters //
	public void setData(Object[] data){
		list.setListData(data);
	}
	public GuiStack(Rectangle bounds,Object[] data){
		super();
		setBounds(bounds);
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
