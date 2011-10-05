package sim.gui.elements;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import sim.structures.SingleLinkedList;

/**
 * The graphical element of the {@link sim.structures.SingleLinkedList} SingleLinkedList and {@link sim.structures.SingleLinkedList} DoubleLinkedList class
 */
@SuppressWarnings("serial")
public class GuiList extends GuiElement {
// Class variables //
	private Vector<SingleLinkedList.Node> data;
	private JPanel listPanel;
	private final int drawNodeWidth = 20;
	private final int drawNodeHeight = 20;
	
// Getters and setters //
	public void setData(Vector<SingleLinkedList.Node> data){
		
	}
	public GuiList(Rectangle bounds,Vector<SingleLinkedList.Node> data){
		super();
		setBounds(bounds);
		initGraphics(data);
	}
	@Override 
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		
		for(int i = 0; i<data.size(); i++){
			//g2d.fillOval(i*, listPanel.getHeight()/2, width, height)
		}
	}
	private void initGraphics(Vector<SingleLinkedList.Node> data){

		listPanel = new JPanel();
		listPanel.setPreferredSize(new Dimension(getWidth(), getHeight()));
		
		JScrollPane listScroller = new JScrollPane(listPanel);
		listScroller.setPreferredSize(new Dimension(getWidth(), getHeight()));
		this.add(listScroller);
	}
}
