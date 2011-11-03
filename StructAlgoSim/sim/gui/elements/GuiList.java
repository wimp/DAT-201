package sim.gui.elements;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import sim.structures.LinkedList.Node;

/**
 * The graphical element of the {@link sim.structures.LinkedList} LinkedList
 * class
 */
@SuppressWarnings("serial")
public class GuiList extends GuiElement implements ActionListener, ItemListener, MouseMotionListener {
	// Class variables //
	private int height;

	private Point mousePos = new Point(0,0);
	private Node selected = null;
	private Node removed = null;
	private Vector<Node> data;
	private Vector<Link> links;
	private ListPanel listPanel;

	private boolean show;
	
	public ListPanel getListPanel() {
		return listPanel;
	}

	private JCheckBox circular;
	private JCheckBox doublyLinked;
	private JCheckBox showValues;
	private JScrollPane listScroller;
	private final int drawNodeWidth = GuiSettings.LISTNODEWIDTH;
	private final int drawNodeHeight = GuiSettings.LISTNODEHEIGHT;

	// Getters and setters //
	public void setData(Vector<Node> data) {
		this.data = data;
	}

	public Node getRemoved() {
		return removed;
	}

	public void setRemoved(Node removed) {
		this.removed = removed;
	}
	public boolean isCircular() {
		return listPanel==null ? false : listPanel.isCircular();
	}

	public void setCircular(boolean circular) {
		if(this.circular!=null) 	this.circular.setSelected(circular);
		if(listPanel != null) listPanel.setCircular(circular);	
		}

	public boolean isDoublyLinked() {
		
		return listPanel==null ? false : listPanel.isDoublyLinked();
	}

	public void setDoublyLinked(boolean doublyLinked) {
		if(this.doublyLinked!=null) this.doublyLinked.setSelected(doublyLinked);
		listPanel.setDoublyLinked(doublyLinked);
	}
	public GuiList(Rectangle bounds, Vector<Node> data, boolean animated) {
		super();
		if (animated)
			animation = new Timer(500, this);
		else
			animation = new Timer(0, this);
		this.data = data;
		this.setLayout(new BorderLayout());
		setBounds(bounds);
		height = drawNodeHeight * 4;
		listPanel = new ListPanel(false, false);
		listPanel.addMouseMotionListener(this);
		links = new Vector<Link>();
		updateLinks();
		initGraphics();
	}

	@Override
	public void startAnimation() {
		super.startAnimation();
	}
	public void stopAnimation() {
		animation.stop();
		
		frame = 0;
		if (removed != null) {

			removed.getNext().setPrevious(removed.getPrevious());
			removed.getPrevious().setNext(removed.getNext());
			for (int i = 0; i < data.size(); i++)
				if (data.elementAt(i) == null) {
					data.remove(i);
					i--;
				}
			for(Node node : data){
				if(node!=null)
				node.setIndex(data.indexOf(node));
			}
			removed = null;
		}
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).isAdded()) {
				data.get(i).setAdded(false);
			}
		}
		updateLinks();
	}

	private void initGraphics() {
		listPanel.setPreferredSize(new Dimension(getWidth(), height));
		listScroller = new JScrollPane(listPanel);
		listScroller
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		listScroller
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		listScroller.setPreferredSize(new Dimension(getWidth(), getHeight()));

		circular = new JCheckBox("Circular");
		circular.addItemListener(this);
		doublyLinked = new JCheckBox("Doublylinked");
		doublyLinked.addItemListener(this);
		showValues = new JCheckBox("Show values");
		showValues.addItemListener(this);
		
		JPanel check = new JPanel(new BorderLayout());
		check.add(circular, BorderLayout.WEST);
		check.add(doublyLinked, BorderLayout.CENTER);
		check.add(showValues, BorderLayout.EAST);
		this.add(check, BorderLayout.NORTH);
		this.add(listScroller, BorderLayout.CENTER);
	}

	public void updateLinks() {
		links.removeAllElements();
		for (Node n : data) {
			if (listPanel.isCircular()
					|| (n.getIndex() - n.getNext().getIndex()) < 0) {
				if (n.getIndex() - n.getNext().getIndex() > 0) {
					links.add(new Link(n, n.getNext(), 1, true));
				} else
					links.add(new Link(n, n.getNext(), 1, false));
			}
			if (listPanel.isDoublyLinked()
					&& (listPanel.isCircular() || n.getIndex()
							- n.getPrevious().getIndex() >= 1))
				if (n.getIndex() - n.getPrevious().getIndex() < 1) {
					links.add(new Link(n, n.getPrevious(), -1, true));
				} else
					links.add(new Link(n, n.getPrevious(), -1, false));
		}
	}

	private class Link {
		private Node a;
		private Node b;
		private int direction;
		private boolean circular;

		private Color c = Color.BLACK;

		public Link(Node a, Node b, int direction, boolean circular) {
			this.a = a;
			this.b = b;
			this.direction = direction;
			this.circular = circular;
		}

		public void drawLink(Graphics2D g2d) {
			Color temp = g2d.getColor();
			g2d.setColor(c);
			int indexOfStart = a.getIndex() + 1;
			int indexOfEnd = b.getIndex() + 1;

			float f = (direction < 0) ? 11 / 8f : -3 / 8f;

			int[] linkX = {
					(2 * (indexOfStart)) * drawNodeWidth + drawNodeWidth / 2,
					(2 * (indexOfStart)) * drawNodeWidth + drawNodeWidth / 2,
					(int) ((2 * indexOfEnd) * drawNodeWidth + drawNodeWidth * f),
					(int) ((2 * indexOfEnd) * drawNodeWidth + drawNodeWidth * f),
					(int) ((2 * indexOfEnd) * drawNodeWidth + drawNodeWidth * f + drawNodeWidth
							* direction * (3 / 8.0)) };

			f = (direction < 0) ? -1f : 1f;
			float d = circular ? direction * (1 / 8.0f) : 0f;

			int[] linkY = {
					(height / 2) + (direction * (drawNodeHeight / 2)),
					(int) ((height / 2) + f * drawNodeHeight + d
							* drawNodeHeight),
					(int) ((height / 2) + f * drawNodeHeight + d
							* drawNodeHeight), height / 2, height / 2 };

			f = (direction < 0) ? 5 / 4f : -1 / 4f;

			int[] arrowX = {
					(int) ((2 * indexOfEnd) * drawNodeWidth + (drawNodeWidth * f)),
					(int) ((direction > 0) ? 2 * indexOfEnd * drawNodeWidth : 2
							* indexOfEnd * drawNodeWidth + drawNodeWidth),
					(int) ((2 * indexOfEnd) * drawNodeWidth + (drawNodeWidth * f)) };
			int[] arrowY = { (int) (height / 2 - (1 / 4.0) * drawNodeHeight),
					height / 2,
					(int) (height / 2 + (1 / 4.0) * drawNodeHeight), };
			g2d.drawPolyline(linkX, linkY, linkX.length);
			g2d.fillPolygon(arrowX, arrowY, arrowX.length);
			
			g2d.setColor(temp);
		}
	}

	private class ListPanel extends JPanel {
		private boolean circular;
		private boolean doublyLinked;
		
		public ListPanel(boolean circular, boolean doublyLinked) {
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

		private void drawNode(Graphics2D g2d, Node n) {
			int indexOfNode = n.getIndex() + 1;
			
			if(mousePos.x > (2 * indexOfNode) * drawNodeWidth && 
					mousePos.x < (2 * indexOfNode) * drawNodeWidth+drawNodeWidth && 
					mousePos.y > height / 2- drawNodeHeight / 2 && 
					mousePos.y < height / 2+ drawNodeHeight / 2){
				selected = n;
			}
			Color c = g2d.getColor();
			if(n!=selected)
			if(show){
				int v=g2d.getFontMetrics(getFont()).charsWidth(n.getValue().toString().toCharArray(), 0, n.getValue().toString().toCharArray().length)+20;
				if(v<drawNodeWidth) v = drawNodeWidth;
				g2d.setColor(GuiSettings.LISTCONTENTCOLOR);


				g2d.fillRoundRect((2 * indexOfNode) * drawNodeWidth, height / 2
						- drawNodeHeight / 2, v, drawNodeHeight, 5, 5);
				g2d.setColor(c);
				g2d.drawRoundRect((2 * indexOfNode) * drawNodeWidth, height / 2
						- drawNodeHeight / 2, v, drawNodeHeight, 5, 5);
				g2d.drawString(n.getValue().toString(),(2 * indexOfNode-1) * drawNodeWidth+drawNodeWidth/6, height / 2
						+ drawNodeHeight / 6);
			
			}
			else{
				if (n.isAdded())
					g2d.setColor(GuiSettings.LISTADDEDCOLOR);
				else if (n==removed)
					g2d.setColor(GuiSettings.LISTREMOVEDCOLOR);
				else if (n.getNext().getIndex() < n.getIndex()
						|| n.getNext() == n)
					g2d.setColor(GuiSettings.LISTLASTCOLOR);
				else if (n.getIndex() == 0)
					g2d.setColor(GuiSettings.LISTHEADCOLOR);
				else
					g2d.setColor(GuiSettings.LISTNODECOLOR);
				
				g2d.fillOval((2 * indexOfNode) * drawNodeWidth, height / 2
						- drawNodeHeight / 2, drawNodeWidth, drawNodeHeight);
				g2d.setColor(c);
				g2d.drawOval((2 * indexOfNode) * drawNodeWidth, height / 2
						- drawNodeHeight / 2, drawNodeWidth, drawNodeHeight);
				g2d.drawString(((Integer)(indexOfNode)).toString(), (2 * indexOfNode)
						* drawNodeWidth+drawNodeWidth/3, height / 2+drawNodeHeight/8);
			}
			g2d.setColor(c);
		}

		private void addAnimation(Node n) {
			switch (frame) {
			case 1:
				break;
			case 2:
				if (data.size() == 3 && listPanel.isCircular()
						&& links.size() > 2)
					links.remove(2);
				else
					for (int i = 0; i < links.size(); i++) {
						if (links.get(i).b == n.getPrevious()
								&& links.get(i).a == n.getNext()) {
							links.remove(links.get(i));
							i--;
							break;
						}
					}
				break;
			case 3:
				if (data.size() == 3 && listPanel.isCircular()
						&& links.size() > 1)
					links.remove(1);
				else
					for (int i = 0; i < links.size(); i++) {
						if (links.get(i).b == n.getNext()
								&& links.get(i).a == n.getPrevious()) {
							links.remove(links.get(i));
							i--;
							break;
						}
					}
				break;
			case 4:
				if (listPanel.isCircular()
						|| n.getIndex() - n.getNext().getIndex() < 0) {
					if (n.getIndex() - n.getNext().getIndex() > 0) {
						Link l = new Link(n, n.getNext(), 1, true);
						l.c = Color.GREEN;
						links.add(l);
					} else {
						Link l = new Link(n, n.getNext(), 1, true);
						l.c = Color.GREEN;
						links.add(l);
					}
					if (!listPanel.isDoublyLinked())
						frame++;
				}
				break;
			case 5:
				if (listPanel.isDoublyLinked()
						&& (listPanel.isCircular() || n.getIndex()
								- n.getPrevious().getIndex() >= 1))
					if (n.getIndex() - n.getPrevious().getIndex() >= 1) {
						Link l = new Link(n, n.getPrevious(), -1, false);
						l.c = Color.GREEN;
						links.add(l);
					} else {
						Link l = new Link(n, n.getPrevious(), -1, false);
						l.c = Color.GREEN;
						links.add(l);
					}
				break;
			case 6:
				if (listPanel.isCircular()) {
					if (n.getNext().getIndex() - n.getIndex() > 0
							&& listPanel.isDoublyLinked()) {
						Link l = new Link(n.getNext(), n, -1, false);
						l.c = Color.GREEN;
						links.add(l);
					} else if (data.size() != 2 && listPanel.isDoublyLinked()) {
						Link l = new Link(n.getNext(), n, -1, true);
						l.c = Color.GREEN;
						links.add(l);
					} else {
						Link l = new Link(n.getPrevious(), n, 1, false);
						l.c = Color.GREEN;
						links.add(l);
					}

				} else {
					Link l = new Link(n.getPrevious(), n, 1, false);
					l.c = Color.GREEN;
					links.add(l);
				}
				break;
			case 7:
				if (listPanel.isDoublyLinked()
						&& (listPanel.isCircular() || n.getPrevious().getIndex() - n.getIndex() > 1)) {
					if (n.getPrevious().getIndex() - n.getIndex() < 1) {
						Link l = new Link(n.getPrevious(), n, 1, false);
						l.c = Color.GREEN;
						links.add(l);
					} else {
						Link l = new Link(n.getPrevious(), n, 1, true);
						l.c = Color.GREEN;
						links.add(l);
					}
				}
				break;
			default:
				break;
			}
		}

		private void removeAnimation(Node n) {
			switch (frame) {
			case 1:
				for (int i = 0; i < links.size(); i++) {
					if (links.get(i).b == n.getPrevious()
							&& links.get(i).a == n) {
						links.remove(links.get(i));
						i--;
						break;
					}
				}
				break;
			case 2:
				for (int i = 0; i < links.size(); i++) {
					if (links.get(i).b == n.getNext() && links.get(i).a == n) {
						links.remove(links.get(i));
						i--;
						break;
					}
				}
				break;
			case 3:
				for (int i = 0; i < links.size(); i++) {
					if (links.get(i).a == n.getNext() && links.get(i).b == n) {
						links.remove(links.get(i));
						i--;
						break;
					}
				}
				break;
			case 4:
				for (int i = 0; i < links.size(); i++) {
					if (links.get(i).a == n.getPrevious()
							&& links.get(i).b == n) {
						links.remove(links.get(i));
						i--;
						break;
					}
				}
				break;
			case 5:
				if (listPanel.isDoublyLinked() && (listPanel.isCircular()|| n.getNext().getIndex()
								- n.getPrevious().getIndex() < 0)) {
					if (n.getNext().getIndex()
							- n.getPrevious().getIndex() > 0) {
						Link l = new Link(n.getNext(), n.getPrevious(), -1,
								false);
						l.c = Color.GREEN;
						links.add(l);
					} else {
						Link l = new Link(n.getNext(), n.getPrevious(), -1,
								true);
						l.c = Color.GREEN;
						links.add(l);
					}
				}
				break;
			case 6:
				if (listPanel.isDoublyLinked()
						&& (listPanel.isCircular() || 
								n.getPrevious().getIndex() - n.getNext().getIndex() > 1))
					if (n.getPrevious().getIndex()
							- n.getNext().getIndex() < 1) {
						Link l = new Link(n.getPrevious(), n.getNext(), 1,
								false);
						l.c = Color.GREEN;
						links.add(l);
					} else {
						Link l = new Link(n.getPrevious(), n.getNext(), -1,
								true);
						l.c = Color.GREEN;
						links.add(l);
					}
				else {
					Link l = new Link(n.getPrevious(), n.getNext(), 1, false);
					l.c = Color.GREEN;
					links.add(l);
				}
				break;
			default:
				break;
			}
		}

		@Override
		public void paintComponent(Graphics g) {
			if(!GuiSettings.isAnimated && animation.isRunning()){
				stopAnimation();
			}
			Graphics2D g2d = (Graphics2D) g;
			g2d.clearRect(0, 0, getWidth(), getHeight());
			setPreferredSize(new Dimension(drawNodeWidth * (data.size() + 1)
					* 2, getHeight()));
			for (Link l : links) {
				l.drawLink(g2d);
			}
			for (Node n : data) {
				if (n == null)
					continue;
				drawNode(g2d, n);
				if (n.isAdded())
					addAnimation(n);
			}
			Color c = g2d.getColor();
			if(selected!= null)
			if(!show){
				int v=g2d.getFontMetrics(getFont()).charsWidth(selected.getValue().toString().toCharArray(), 0, selected.getValue().toString().toCharArray().length)+20;
				if(v<drawNodeWidth) v = drawNodeWidth;
				g2d.setColor(GuiSettings.LISTCONTENTCOLOR);


				g2d.fillRoundRect((2 * (selected.getIndex() + 1)) * drawNodeWidth, height / 2
						- drawNodeHeight / 2, v, drawNodeHeight, 5, 5);
				g2d.setColor(c);
				g2d.drawRoundRect((2 *(selected.getIndex() + 1)) * drawNodeWidth, height / 2
						- drawNodeHeight / 2, v, drawNodeHeight, 5, 5);
				g2d.drawString(selected.getValue().toString(),(2 * (selected.getIndex() + 1)) * drawNodeWidth+drawNodeWidth/6, height / 2
						+ drawNodeHeight / 6);
				selected = null;
			}
			else{
				if (selected.isAdded())
					g2d.setColor(GuiSettings.LISTADDEDCOLOR);
				else if (selected==removed)
					g2d.setColor(GuiSettings.LISTREMOVEDCOLOR);
				else if (selected.getNext().getIndex() < selected.getIndex()
						|| selected.getNext() == selected)
					g2d.setColor(GuiSettings.LISTLASTCOLOR);
				else if (selected.getIndex() == 0)
					g2d.setColor(GuiSettings.LISTHEADCOLOR);
				else
					g2d.setColor(GuiSettings.LISTNODECOLOR);
				
				g2d.fillOval((2 * (selected.getIndex() + 1)) * drawNodeWidth, height / 2
						- drawNodeHeight / 2, drawNodeWidth, drawNodeHeight);
				g2d.setColor(c);
				g2d.drawOval((2 * (selected.getIndex() + 1)) * drawNodeWidth, height / 2
						- drawNodeHeight / 2, drawNodeWidth, drawNodeHeight);
				g2d.drawString(((Integer)((selected.getIndex() + 1))).toString(), (2 * (selected.getIndex() + 1))
						* drawNodeWidth+drawNodeWidth/3, height / 2+drawNodeHeight/8);
				selected = null;
			}
			g2d.setColor(c);
			if (removed != null) {
				removeAnimation(removed);
				drawNode(g2d, removed);
			}
			
			listPanel.revalidate();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == animation) {
			frame++;
			if (frame > getMaxFrame()) {
				stopAnimation();

				frame = 0;
			}
			repaint();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == circular) {
			listPanel.setCircular(!listPanel.isCircular());
		} else if (e.getSource() == doublyLinked) {
			listPanel.setDoublyLinked(!listPanel.isDoublyLinked());
		}	else if(e.getSource() == showValues){
			show = !show;
		}
		stopAnimation();
		updateLinks();
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mousePos.x = e.getX();
		mousePos.y = e.getY();
		repaint();
	}
}