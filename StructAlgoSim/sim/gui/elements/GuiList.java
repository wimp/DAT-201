package sim.gui.elements;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import sim.structures.elements.Node;

/**
 * The graphical element of the {@link sim.structures.LinkedList} LinkedList class
 */
@SuppressWarnings("serial")
public class GuiList extends GuiElement implements ActionListener, ItemListener{
// Class variables //
	private int height;
	private Vector<Node> data;
	private Vector<Link> links;
	private ListPanel listPanel;
	private JCheckBox circular;
	private JCheckBox doublyLinked;
	private JScrollPane listScroller;
	private final int drawNodeWidth = GuiSettings.LISTNODEWIDTH;
	private final int drawNodeHeight = GuiSettings.LISTNODEHEIGHT;

// Getters and setters //
	public void setData(Vector<Node> data){
		this.data = data;
	}
	public GuiList(Rectangle bounds,Vector<Node> data, boolean animated){
		super();
		if(animated) animation = new Timer(750,this);
		else 
			animation = new Timer(0, this);
		this.data = data;
		this.setLayout(new BorderLayout());
		setBounds(bounds);		
		height = bounds.height;
		listPanel = new ListPanel(false, false);		
		links = new Vector<Link>();
		updateLinks();
		initGraphics();
	}	
	private void initGraphics(){
		listPanel.setPreferredSize(new Dimension(getWidth(), height));
		listScroller = new JScrollPane(listPanel);
		listScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		listScroller.setPreferredSize(new Dimension(getWidth(), getHeight()));
		
		circular = new JCheckBox("Circular");
		circular.addItemListener(this);
		doublyLinked = new JCheckBox("Doublylinked");
		doublyLinked.addItemListener(this);
		JPanel check = new JPanel(new BorderLayout());
		check.add(circular, BorderLayout.WEST);
		check.add(doublyLinked, BorderLayout.EAST);
		this.add(check, BorderLayout.NORTH);
		this.add(listScroller, BorderLayout.CENTER);
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
			if(listPanel.isDoublyLinked() && (listPanel.isCircular() || data.indexOf(n)-data.indexOf(n.getPrevious()) >=1))
				if(data.indexOf(n)-data.indexOf(n.getPrevious()) <1)
				{
					links.add(new Link(n,n.getPrevious(), -1, true));
				}
				else 
					links.add(new Link(n,n.getPrevious(), -1, false));
		}
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
						( height/2+drawNodeHeight/2)+(direction*(drawNodeHeight/2)),
						(int)(( height/2+drawNodeHeight/2)+f*drawNodeHeight+d*drawNodeHeight),	
						(int)(( height/2+drawNodeHeight/2)+f*drawNodeHeight+d*drawNodeHeight),	
						( height/2)+(drawNodeHeight/2),	
						( height/2+(drawNodeHeight/2))	
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
						(int)( height/2+(1/4.0)*drawNodeHeight),	
						 height/2+drawNodeHeight/2,
						(int)( height/2+(3/4.0)*drawNodeHeight),	
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
		public void setCircular(boolean circular) {
			this.circular = circular;
		}

		public boolean isDoublyLinked() {
			return doublyLinked;
		}		
		public void setDoublyLinked(boolean doublyLinked) {
			this.doublyLinked = doublyLinked;
		}
		private void drawNode(Graphics2D g2d, Node n){
			int indexOfNode = data.indexOf(n)+1;
			Color c = g2d.getColor();
			if(n.isAdded()) g2d.setColor(GuiSettings.LISTADDEDCOLOR);
			else if(n.isRemoved()) g2d.setColor(GuiSettings.LISTREMOVEDCOLOR);
			else if(data.indexOf(n.getNext())<data.indexOf(n) || n.getNext() == n) g2d.setColor(GuiSettings.LISTLASTCOLOR);
			else if(data.indexOf(n)==0) g2d.setColor(GuiSettings.LISTHEADCOLOR);
			else g2d.setColor(GuiSettings.LISTNODECOLOR);
			
			g2d.fillOval((2*indexOfNode)*drawNodeWidth,  height/2, drawNodeWidth, drawNodeHeight);
			g2d.setColor(c);
			g2d.drawOval((2*indexOfNode)*drawNodeWidth,  height/2, drawNodeWidth, drawNodeHeight);
			g2d.drawString((String)n.getValue(), (2*indexOfNode)*drawNodeWidth, height/2+drawNodeHeight/2);
			g2d.setColor(c);
		}
		private void addAnimation(Node n){
			switch(frame){
			case 1:
				 if(data.size()==3 && listPanel.isCircular()&& links.size()>2) links.remove(2);
				 else for(int i = 0; i<links.size(); i++){
					 if(links.get(i).b == n.getPrevious() && links.get(i).a == n.getNext()){
						 links.remove(links.get(i));
						 i--;
						 break;
					 }
				 }
				 break;
			case 2:
				if(data.size()==3 && listPanel.isCircular() && links.size()>1) links.remove(1);
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
					else if(data.size()!=2){
						Link l =new Link(n.getNext(),n, -1,true);
						l.c = Color.GREEN;
						links.add(l);
					}
					else {
						Link l =new Link(n.getNext(),n, 1,false);
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
			g2d.clearRect(0, 0, getWidth(), getHeight());
			setPreferredSize(new Dimension(drawNodeWidth*(data.size()+1)*2, getHeight()));
			
			for(Node n : data){
				drawNode(g2d, n);
				if(n.isAdded()) addAnimation(n);
				if(n.isRemoved()) removeAnimation(n);
			}
			for(Link l : links){
				l.drawLink(g2d);
			}
			listPanel.revalidate();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==animation){
			frame++;
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
	@Override
	public void itemStateChanged(ItemEvent e){
		if(e.getSource() == circular){
			listPanel.setCircular(!listPanel.isCircular());
		}
		else if(e.getSource() == doublyLinked){
			listPanel.setDoublyLinked(!listPanel.isDoublyLinked());
		}
		updateLinks();
		repaint();
	}
}