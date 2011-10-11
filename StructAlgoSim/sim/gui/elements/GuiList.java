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
	private Vector<Link> links;
	private ListPanel listPanel;
	private final int drawNodeWidth = 30;
	private final int drawNodeHeight = 30;

// Getters and setters //
	public void setData(Vector<LinkedList.Node> data){
		this.data = data;
	}
	public GuiList(Rectangle bounds,Vector<LinkedList.Node> data, boolean doublyLinked, boolean circular, boolean animated){
		super();
		if(animated) animation = new Timer(750,this);
		else 
			animation = new Timer(0, this);
		this.data = data;

		setBounds(bounds);		
		listPanel = new ListPanel(circular, doublyLinked);		
		links = new Vector<Link>();
		updateLinks();
		initGraphics(data);

	}
	public void updateLinks(){
		links.removeAllElements();
		for(Node n : data){
			if(listPanel.isCircular() || data.indexOf(n)-data.indexOf(n.getNext()) <0)
			{
				if(data.indexOf(n)-data.indexOf(n.getNext()) >0)
				{
				links.add(new Link(n,n.getNext(), 1, true));
				}
				else 
				links.add(new Link(n,n.getNext(), 1, false));
			}
			if(listPanel.isDoublyLinked() && (listPanel.isCircular() || data.indexOf(n)-data.indexOf(n.getPrevious()) >1))
				if(data.indexOf(n)-data.indexOf(n.getPrevious()) <1)
				{
				links.add(new Link(n,n.getPrevious(), -1, true));
				}
				else 
				links.add(new Link(n,n.getPrevious(), -1, false));
		}
	}
	private void initGraphics(Vector<LinkedList.Node> data){
		listPanel.setPreferredSize(new Dimension(getWidth(), getHeight()));
		JScrollPane listScroller = new JScrollPane(listPanel);
		listScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		listScroller.setPreferredSize(new Dimension(getWidth(), getHeight()));
		this.add(listScroller);
	}
	private class Link{
		Node a;
		Node b;
		int direction;
		boolean circular;
		
		Color c = Color.BLACK;
		public Link(Node a, Node b, int direction, boolean circular){
			this.a = a;
			this.b = b;
			this.direction = direction;
			this.circular = circular;
		}
		public void drawLink(Graphics2D g2d){
			g2d.setColor(c);
			int indexOfStart = data.indexOf(a)+1;
			int indexOfEnd = data.indexOf(b)+1;

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
			float d = circular ? direction*(1/8.0f) : 0f;		
			
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
	}
	private class ListPanel extends JPanel{
		private boolean circular;
		private boolean doublyLinked;
		
		public ListPanel(boolean circular, boolean doublyLinked){
			this.circular = circular;
			this.doublyLinked = doublyLinked;
		}		
		public boolean isCircular() {
			return circular;
		}
		public boolean isDoublyLinked() {
			return doublyLinked;
		}
		private void drawNode(Graphics2D g2d, Node n){
			int indexOfNode = data.indexOf(n)+1;
			
			g2d.drawOval((2*indexOfNode)*drawNodeWidth, getHeight()/4, drawNodeWidth, drawNodeHeight);
			g2d.drawString((String)n.getValue(), (2*indexOfNode)*drawNodeWidth,getHeight()/4+drawNodeHeight/2);
		}
		private void addAnimation(Node n){
			switch(frame){
			case 1:
				 if(data.size()==3 && circular) links.remove(2);
				 else for(int i = 0; i<links.size(); i++){
					 if(links.get(i).b == n.getPrevious() && links.get(i).a == n.getNext()){
						 links.remove(links.get(i));
						 i--;
						 break;
					 }
				 }
				 break;
			case 2:
				if(data.size()==3 && circular) links.remove(1);
				else for(int i = 0; i<links.size(); i++){
					 if(links.get(i).b == n.getNext() && links.get(i).a == n.getPrevious()){
						 links.remove(links.get(i));
						 i--;
						 break;
					 }
				 }
				 break;
			case 3:
				if(listPanel.isCircular() || data.indexOf(n)-data.indexOf(n.getNext()) <0)
				{
					if(data.indexOf(n)-data.indexOf(n.getNext()) >0)
					{
					Link l = new Link(n,n.getNext(), 1, true);
					l.c = Color.GREEN;
					links.add(l);
					}
					else {
					Link l = new Link(n,n.getNext(), 1, true);
					l.c = Color.GREEN;
					links.add(l);
				}
				}
				break;
			case 4:
				if(listPanel.isDoublyLinked() && (listPanel.isCircular() || data.indexOf(n)-data.indexOf(n.getPrevious()) >1))
					if(data.indexOf(n)-data.indexOf(n.getPrevious()) <1)
					{
					Link l = new Link(n,n.getPrevious(), -1, true);
					l.c = Color.GREEN;
					links.add(l);
					}
					else 
					{
						Link l = new Link(n,n.getPrevious(), -1, false);
						l.c = Color.GREEN;
						links.add(l);
					}
				break;
			case 5:
				if(listPanel.isCircular() || data.indexOf(n.getNext())-data.indexOf(n) <0)
				{
					if(data.indexOf(n.getNext())-data.indexOf(n) >0)
					{
						Link l =new Link(n.getNext(),n, -1, false);
						l.c = Color.GREEN;
						links.add(l);
					}
					else {
						Link l =new Link(n.getNext(),n, -1,true);
						l.c = Color.GREEN;
						links.add(l);
					}
					}
				break;
			case 6:
				if(listPanel.isDoublyLinked() && (listPanel.isCircular() || data.indexOf(n.getPrevious())-data.indexOf(n) >1))
					if(data.indexOf(n.getPrevious())-data.indexOf(n) <1)
					{
						Link l =new Link(n.getPrevious(),n, 1, false);
						l.c = Color.GREEN;
						links.add(l);
					}
					else {
						Link l = new Link(n.getPrevious(),n, 1, true);
						l.c = Color.GREEN;
						links.add(l);
					}
				break;
			default:
			break;
			}
		}
		private void removeAnimation(Node n){
			switch(frame){
			case 1:
				 for(int i = 0; i<links.size(); i++){
					 if(links.get(i).b == n.getPrevious() && links.get(i).a == n){
						 links.remove(links.get(i));
						 i--;
						 break;
					 }
				 }
				 break;
			case 2:
				 for(int i = 0; i<links.size(); i++){
					 if(links.get(i).b == n.getNext() && links.get(i).a == n){
						 links.remove(links.get(i));
						 i--;
						 break;
					 }
				 }
				 break;
			case 3:
				 for(int i = 0; i<links.size(); i++){
					 if(links.get(i).a == n.getNext() && links.get(i).b == n){
						 links.remove(links.get(i));
						 i--;
						 break;
					 }
				 }
				break;
			case 4:
				 for(int i = 0; i<links.size(); i++){
					 if(links.get(i).a == n.getPrevious() && links.get(i).b == n){
						 links.remove(links.get(i));
						 i--;
						 break;
					 }
				 }
				break;
			case 5:
				if(listPanel.isCircular() || data.indexOf(n.getNext())-data.indexOf(n.getPrevious()) <0)
				{
					if(data.indexOf(n.getNext())-data.indexOf(n.getPrevious()) >0)
					{
						Link l = new Link(n.getNext(),n.getPrevious(), -1, false);
						l.c = Color.GREEN;
						links.add(l);
					}
					else {
						Link l = new Link(n.getNext(),n.getPrevious(), -1,true);
						l.c = Color.GREEN;
						links.add(l);
					}
				}
				break;
			case 6:
				if(listPanel.isDoublyLinked() && (listPanel.isCircular() || data.indexOf(n.getPrevious())-data.indexOf(n.getNext()) >1))
					if(data.indexOf(n.getPrevious())-data.indexOf(n.getNext()) <1)
					{
						Link l = new Link(n.getPrevious(),n.getNext(), 1, false);
						l.c = Color.GREEN;
						links.add(l);
					}
					else {
						Link l = new Link(n.getPrevious(),n.getNext(), 1, true);
						l.c = Color.GREEN;
						links.add(l);
					}
				break;
			default:
		break;
			}
		}
		@Override 
		public void paintComponent(Graphics g){
			Graphics2D g2d = (Graphics2D)g;
			setPreferredSize(new Dimension(drawNodeWidth*data.size()*2, getHeight()));
			
			for(Node n : data){
			drawNode(g2d, n);
			if(n.isAdded()) addAnimation(n);
			if(n.isRemoved()) removeAnimation(n);
			}
			
			for(Link l : links){
					l.drawLink(g2d);
				}
			listPanel.validate();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==animation){
			frame++;
			System.out.println(frame);
			if(frame > MAXFRAME){
				for(int i =0; i< data.size(); i++){
					if(data.get(i).isAdded()){
						data.get(i).setAdded(false);
						updateLinks();
					}
					if(data.get(i).isRemoved()){
						data.get(i).getNext().setPrevious(data.get(i).getPrevious());
						data.get(i).getPrevious().setNext(data.get(i).getNext());
						data.remove(data.get(i));
						
						i--;
						updateLinks();
					}
				}
				frame = 0;
				animation.stop();
				repaint();
			}
			repaint();
		}
	}
}