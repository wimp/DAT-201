package sim.gui.elements;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Scrollable;

import sim.structures.LinkedList;

/**
 * The graphical element of the {@link sim.structures.LinkedList} LinkedList class
 */
@SuppressWarnings("serial")
public class GuiList extends GuiElement {
// Class variables //
	private Vector<LinkedList.Node> data;
	private ListPanel listPanel;
	private final int drawNodeWidth = 30;
	private final int drawNodeHeight = 30;
	
// Getters and setters //
	public void setData(Vector<LinkedList.Node> data){
		
	}
	public GuiList(Rectangle bounds,Vector<LinkedList.Node> data, boolean circular, boolean doublyLinked){
		super();
		this.data = data;
		setBounds(bounds);		
		listPanel = new ListPanel(circular, doublyLinked);
		initGraphics(data);

	}
	private void initGraphics(Vector<LinkedList.Node> data){
		listPanel.setPreferredSize(new Dimension(getWidth(), getHeight()));
		JScrollPane listScroller = new JScrollPane(listPanel);
		listScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		listScroller.setPreferredSize(new Dimension(getWidth(), getHeight()));
		this.add(listScroller);
	}
	private class ListPanel extends JPanel{
		private boolean circular;
		private boolean doublyLinked;
		
		public ListPanel(boolean circular, boolean doublyLinked){
			this.circular = circular;
			this.doublyLinked = doublyLinked;
		}
		@Override 
		public void paintComponent(Graphics g){
			Graphics2D g2d = (Graphics2D)g;
			setPreferredSize(new Dimension(drawNodeWidth*data.size()*2, getHeight()));
			
			for(LinkedList.Node n : data){
				if(n == null) continue;
				
				int indexOfNode = data.indexOf(n)+1;
				int indexOfNext = data.indexOf(n.getNext())+1;
				int indexOfPrev = data.indexOf(n.getPrevious())+1;
				
				g2d.drawOval((2*indexOfNode)*drawNodeWidth, getHeight()/4, drawNodeWidth, drawNodeHeight);
				if(n.getNext()!=null){
					int[] linkX = 
					{ 
							(2*(indexOfNode))*drawNodeWidth+drawNodeWidth/2,
							(2*(indexOfNode))*drawNodeWidth+drawNodeWidth/2,
							(int)((2*indexOfNext)*drawNodeWidth-drawNodeWidth*(3/8.0)),
							(int)((2*indexOfNext)*drawNodeWidth-drawNodeWidth*(3/8.0)),
							(2*indexOfNext)*drawNodeWidth
					};

					float f = ((indexOfNode-indexOfNext) < 0) ? 5/4f : 6/4f;
					if(indexOfNode-indexOfNext < 0 || (indexOfNode-indexOfNext >= 0 && circular)){
					int[] linkY = 
					{ 
							getHeight()/4+drawNodeHeight,
							(int)(getHeight()/4+f*drawNodeHeight),	
							(int)(getHeight()/4+f*drawNodeHeight),	
							getHeight()/4+drawNodeHeight/2,	
							getHeight()/4+drawNodeHeight/2	
					};
					int[] arrowX = 
					{ 
							(int)((2*indexOfNext)*drawNodeWidth-drawNodeWidth*(1/4.0)),
							(int)((2*indexOfNext)*drawNodeWidth),
							(int)((2*indexOfNext)*drawNodeWidth-drawNodeWidth*(1/4.0)),
							
					};
					int[] arrowY = 
					{ 
							(int)(getHeight()/4+(1/4.0)*drawNodeHeight),	
							getHeight()/4+drawNodeHeight/2,
							(int)(getHeight()/4+(3/4.0)*drawNodeHeight),	
					};
					g2d.drawPolyline(linkX, linkY, linkX.length);
					g2d.fillPolygon(arrowX, arrowY, arrowX.length);
					}
				}
				if(doublyLinked && n.getPrevious() != null){
					int[] linkX = 
					{ 
							(2*(indexOfNode))*drawNodeWidth+drawNodeWidth/2,
							(2*(indexOfNode))*drawNodeWidth+drawNodeWidth/2,
							(int)((2*indexOfPrev)*drawNodeWidth+drawNodeWidth*(11/8.0)),
							(int)((2*indexOfPrev)*drawNodeWidth+drawNodeWidth*(11/8.0)),
							(2*indexOfPrev+1)*drawNodeWidth					
					};
					
					float f = ((indexOfNode-indexOfPrev) > 0) ? 1/4f : 2/4f;
					if(indexOfNode-indexOfPrev > 0 || (indexOfNode-indexOfPrev <= 0 && circular)){
					int[] linkY = 
					{ 	
							getHeight()/4,
							(int)(getHeight()/4-f*drawNodeHeight),
							(int)(getHeight()/4-f*drawNodeHeight),							
							getHeight()/4+drawNodeHeight/2,
							getHeight()/4+drawNodeHeight/2,
							getHeight()/4+drawNodeHeight/2
					};
					int[] arrowX = 
					{ 
							(int)((2*indexOfPrev)*drawNodeWidth+drawNodeWidth*(5/4.0)),
							(int)((2*indexOfPrev)*drawNodeWidth+drawNodeWidth),
							(int)((2*indexOfPrev)*drawNodeWidth+drawNodeWidth*(5/4.0))	
					};
					int[] arrowY = 
					{ 
							(int)(getHeight()/4+(1/4.0)*drawNodeHeight),	
							getHeight()/4+drawNodeHeight/2,
							(int)(getHeight()/4+(3/4.0)*drawNodeHeight)	
					};
					g2d.drawPolyline(linkX, linkY, linkX.length);
					g2d.fillPolygon(arrowX, arrowY, arrowX.length);
				}
				}
				g2d.drawString((String)n.getValue(), (2*indexOfNode)*drawNodeWidth,getHeight()/4+drawNodeHeight/2);
			}
			listPanel.revalidate();
		}
	}
}
