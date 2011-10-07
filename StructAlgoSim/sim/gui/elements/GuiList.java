package sim.gui.elements;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import sim.structures.LinkedList;

/**
 * The graphical element of the {@link sim.structures.LinkedList} LinkedList class
 */
@SuppressWarnings("serial")
public class GuiList extends GuiElement {
// Class variables //
	private Vector<LinkedList.Node> data;
	private ListPanel listPanel;
	private final int drawNodeWidth = 20;
	private final int drawNodeHeight = 20;
	
// Getters and setters //
	public void setData(Vector<LinkedList.Node> data){
		
	}
	public GuiList(Rectangle bounds,Vector<LinkedList.Node> data){
		super();
		this.data = data;
		setBounds(bounds);
		initGraphics(data);
	}
	private void initGraphics(Vector<LinkedList.Node> data){
		listPanel = new ListPanel();
		listPanel.setPreferredSize(new Dimension(getWidth(), getHeight()));
		JScrollPane listScroller = new JScrollPane(listPanel);
		listScroller.setPreferredSize(new Dimension(getWidth(), getHeight()));
		this.add(listScroller);
	}
	private class ListPanel extends JPanel{
		@Override 
		public void paintComponent(Graphics g){
			Graphics2D g2d = (Graphics2D)g;
			for(int i = 1; i<data.size(); i++){
				LinkedList.Node n = data.elementAt(i);
				g2d.drawOval((2*i)*drawNodeWidth, listPanel.getHeight()/2, drawNodeWidth, drawNodeWidth);
				if(n.getNext()!=null){
					g2d.drawLine((2*i)*drawNodeWidth+drawNodeWidth/2,listPanel.getHeight()/2+drawNodeHeight,
							(2*(i+1))*drawNodeWidth-drawNodeWidth/2,listPanel.getHeight()/2+(3/2)*drawNodeHeight);
					g2d.drawLine((2*(i+1))*drawNodeWidth-drawNodeWidth/2,listPanel.getHeight()/2+(3/2)*drawNodeHeight,
							(2*(i+1))*drawNodeWidth-drawNodeWidth/2,listPanel.getHeight()/2+drawNodeHeight);
							
				}
				g2d.drawString((String)data.elementAt(i).getValue(), (2*i)*drawNodeWidth,listPanel.getHeight()/2);
			}
		}	
	}
}
