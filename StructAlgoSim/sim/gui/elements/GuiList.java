package sim.gui.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import sim.structures.LinkedList;
import sim.structures.LinkedList.Node;

/**
 * The graphical element of the {@link sim.structures.LinkedList} LinkedList class
 */
@SuppressWarnings("serial")
public class GuiList extends GuiElement implements ActionListener{
// Class variables //
	private Vector<LinkedList.Node> data;
	private ListPanel listPanel;
	private final int drawNodeWidth = 30;
	private final int drawNodeHeight = 30;

// Getters and setters //
	public void setData(Vector<LinkedList.Node> data){
		this.data = data;
	}
	public GuiList(Rectangle bounds,Vector<LinkedList.Node> data, boolean doublyLinked, boolean circular){
		super();
		animation = new Timer(1000,this);
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
		private int direction;
		
		public ListPanel(boolean circular, boolean doublyLinked){
			this.circular = circular;
			this.doublyLinked = doublyLinked;
		}
		private void drawLink(Graphics2D g2d, Node start, Node end){
			
			int indexOfStart = data.indexOf(start)+1;
			int indexOfEnd = data.indexOf(end)+1;
			
			if(indexOfStart == data.size() && indexOfEnd == 1){
				if(!circular) return;
			}
			if(indexOfStart == 1 && indexOfEnd == data.size()){
				if(!circular) return;
			}

			float f = (direction < 0) ? 11/8f : -3/8f;
			
			int[] linkX = 
				{ 
						(2*(indexOfStart))*drawNodeWidth+drawNodeWidth/2,
						(2*(indexOfStart))*drawNodeWidth+drawNodeWidth/2,
						(int)((2*indexOfEnd)*drawNodeWidth+drawNodeWidth*f),
						(int)((2*indexOfEnd)*drawNodeWidth+drawNodeWidth*f),
						(int)((2*indexOfEnd)*drawNodeWidth+drawNodeWidth*f+drawNodeWidth*direction*(3/8.0))
				};

			f = (direction < 0) ? -1f : 1f;
			float d = (Math.abs(indexOfStart-indexOfEnd) > 1) || (data.indexOf(start)== data.size()-1 && data.size()==2)  ? direction*(1/8.0f) : 0f;		
			
			int[] linkY = 
				{ 
						(getHeight()/4+drawNodeHeight/2)+(direction*(drawNodeHeight/2)),
						(int)((getHeight()/4+drawNodeHeight/2)+f*drawNodeHeight+d*drawNodeHeight),	
						(int)((getHeight()/4+drawNodeHeight/2)+f*drawNodeHeight+d*drawNodeHeight),	
						(getHeight()/4)+(drawNodeHeight/2),	
						(getHeight()/4+(drawNodeHeight/2))	
			};
			
			f = (direction < 0) ? 5/4f : -1/4f;
				
			int[] arrowX = 
				{ 
						(int)((2*indexOfEnd)*drawNodeWidth+(drawNodeWidth*f)),
						(int)((direction > 0) ? 2*indexOfEnd*drawNodeWidth : 2*indexOfEnd*drawNodeWidth+drawNodeWidth),
						(int)((2*indexOfEnd)*drawNodeWidth+(drawNodeWidth*f))
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
		private void drawNode(Graphics2D g2d, Node n){
			int indexOfNode = data.indexOf(n)+1;
			
			g2d.drawOval((2*indexOfNode)*drawNodeWidth, getHeight()/4, drawNodeWidth, drawNodeHeight);
			g2d.drawString((String)n.getValue(), (2*indexOfNode)*drawNodeWidth,getHeight()/4+drawNodeHeight/2);
		}
		@Override 
		public void paintComponent(Graphics g){
			Graphics2D g2d = (Graphics2D)g;
			setPreferredSize(new Dimension(drawNodeWidth*data.size()*2, getHeight()));
			
			for(LinkedList.Node n : data){

				if(n == null) continue;
				
				if(n.isAnimated()){
					Color c = g2d.getColor();
					switch(frame){
					default:
					case 4:
						g2d.setColor(Color.RED);
						direction = -1;						
						drawLink(g2d, n.getNext(), n);						
					case 3:
						g2d.setColor(Color.GREEN);
						direction = -1;	
						drawLink(g2d, n, n.getPrevious());						
					case 2:
						g2d.setColor(Color.RED);
						direction = 1;	
						drawLink(g2d, n, n.getNext());
						
					case 1:
						g2d.setColor(Color.GREEN);
						direction = 1;	
						drawLink(g2d, n.getPrevious(), n);						
					case 0:

						g2d.setColor(c);
					break;
					}
				}
				drawNode(g2d, n);
				if(n.getNext() != null && !n.isAnimated())
				{
				if(!n.getNext().isAnimated()){
						direction = 1;
						drawLink(g2d, n, n.getNext());
					}
				}
				if(doublyLinked && n.getPrevious() != null && !n.isAnimated())
				{
				 if(!n.getPrevious().isAnimated()){
					direction = -1;	
					drawLink(g2d, n,n.getPrevious());
				}
				}
				}
			listPanel.validate();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==animation){
			frame++;
			if(frame > MAXFRAME){
				for(Node n : data)
					if(n.isAnimated())
						n.setAnimated(false);
				frame = 0;
				animation.stop();
				repaint();
			}
			repaint();
		}
	}
}