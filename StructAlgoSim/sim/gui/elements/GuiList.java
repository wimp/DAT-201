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
		listScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		listScroller.setPreferredSize(new Dimension(getWidth(), getHeight()));
		this.add(listScroller);
	}
	private class ListPanel extends JPanel implements Scrollable{
		@Override 
		public void paintComponent(Graphics g){
			Graphics2D g2d = (Graphics2D)g;
			setPreferredSize(new Dimension(drawNodeWidth*data.size()*2, getHeight()));
//			g2d.drawRect(drawNodeWidth, listPanel.getHeight()/4, drawNodeWidth, drawNodeHeight);
			for(LinkedList.Node n : data){
				if(n == null) continue;
				
				int indexOfNode = data.indexOf(n)+1;
				int indexOfNext = data.indexOf(n.getNext())+1;
				int indexOfPrev = data.indexOf(n.getPrevious())+1;
				
				g2d.drawOval((2*indexOfNode)*drawNodeWidth, listPanel.getHeight()/4, drawNodeWidth, drawNodeHeight);
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
					
					int[] linkY = 
					{ 
							listPanel.getHeight()/4+drawNodeHeight,
							(int)(listPanel.getHeight()/4+f*drawNodeHeight),	
							(int)(listPanel.getHeight()/4+f*drawNodeHeight),	
							listPanel.getHeight()/4+drawNodeHeight/2,	
							listPanel.getHeight()/4+drawNodeHeight/2	
					};
					int[] arrowX = 
					{ 
							(int)((2*indexOfNext)*drawNodeWidth-drawNodeWidth*(1/4.0)),
							(int)((2*indexOfNext)*drawNodeWidth),
							(int)((2*indexOfNext)*drawNodeWidth-drawNodeWidth*(1/4.0)),
							
					};
					int[] arrowY = 
					{ 
							(int)(listPanel.getHeight()/4+(1/4.0)*drawNodeHeight),	
							listPanel.getHeight()/4+drawNodeHeight/2,
							(int)(listPanel.getHeight()/4+(3/4.0)*drawNodeHeight),	
					};
					g2d.drawPolyline(linkX, linkY, linkX.length);
					g2d.fillPolygon(arrowX, arrowY, arrowX.length);
				}
				if(n.getPrevious() != null){
					int[] linkX = 
					{ 
							(2*(indexOfNode))*drawNodeWidth+drawNodeWidth/2,
							(2*(indexOfNode))*drawNodeWidth+drawNodeWidth/2,
							(int)((2*indexOfPrev)*drawNodeWidth+drawNodeWidth*(11/8.0)),
							(int)((2*indexOfPrev)*drawNodeWidth+drawNodeWidth*(11/8.0)),
							(2*indexOfPrev+1)*drawNodeWidth					
					};
					
					float f = ((indexOfNode-indexOfPrev) > 0) ? 1/4f : 2/4f;
					
					int[] linkY = 
					{ 	
							listPanel.getHeight()/4,
							(int)(listPanel.getHeight()/4-f*drawNodeHeight),
							(int)(listPanel.getHeight()/4-f*drawNodeHeight),							
							listPanel.getHeight()/4+drawNodeHeight/2,
							listPanel.getHeight()/4+drawNodeHeight/2,
							listPanel.getHeight()/4+drawNodeHeight/2
					};
					int[] arrowX = 
					{ 
							(int)((2*indexOfPrev)*drawNodeWidth+drawNodeWidth*(5/4.0)),
							(int)((2*indexOfPrev)*drawNodeWidth+drawNodeWidth),
							(int)((2*indexOfPrev)*drawNodeWidth+drawNodeWidth*(5/4.0))	
					};
					int[] arrowY = 
					{ 
							(int)(listPanel.getHeight()/4+(1/4.0)*drawNodeHeight),	
							listPanel.getHeight()/4+drawNodeHeight/2,
							(int)(listPanel.getHeight()/4+(3/4.0)*drawNodeHeight)	
					};
					g2d.drawPolyline(linkX, linkY, linkX.length);
					g2d.fillPolygon(arrowX, arrowY, arrowX.length);
				}
				g2d.drawString((String)n.getValue(), (2*indexOfNode)*drawNodeWidth,listPanel.getHeight()/4+drawNodeHeight/2);
			}
		}

		@Override
		public Dimension getPreferredScrollableViewportSize() {
			
			return this.getPreferredSize();
		}

		@Override
		public int getScrollableBlockIncrement(Rectangle arg0, int arg1,
				int arg2) {
			// TODO Auto-generated method stub
			return 1;
		}

		@Override
		public boolean getScrollableTracksViewportHeight() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean getScrollableTracksViewportWidth() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public int getScrollableUnitIncrement(Rectangle arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			return 1;
		}	
	}
}
