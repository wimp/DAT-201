package sim.gui.elements;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import sim.structures.Tree;

public class GuiTree extends GuiElement{
	Tree tree;
	TreePanel treePanel;
	
	public GuiTree(Rectangle bounds,Tree tree, boolean animated){
		super();
		
		if(animated) animation = new Timer(750,this);
		else 
			animation = new Timer(0, this);
		this.tree = tree;

		setBounds(bounds);			
		initGraphics();
	}
	private void initGraphics(){
		treePanel = new TreePanel();
		treePanel.setPreferredSize(new Dimension(getWidth(), getHeight()));
		JScrollPane listScroller = new JScrollPane(treePanel);
		listScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		listScroller.setPreferredSize(new Dimension(getWidth(), getHeight()));
		this.add(listScroller);
	}
	private class TreePanel extends JPanel{
		int drawNodeWidth = 20;
		int drawNodeHeight = 20;
		int maxCluster;
		int maxDepth; 
		
		public TreePanel(){
		}
		private int getIndent(Tree.TreeNode n){
			int indent = 0;
			for(int i =0; i<=(maxDepth)-n.getDepth()+1; i++){
					indent= (int)Math.pow(maxCluster, i);
				}

			return indent;
		}
		private int getIndex(Tree.TreeNode n){
			int index =0;
					while(n!=null){
						if(n.getParent()!=null )
						index += n.getParent().getChildren().indexOf(n)*getIndent(n);
						n = n.getParent();
					}
			return index;
		}
		public void drawTree(Graphics2D g2d ,Tree.TreeNode tree){
			for(Tree.TreeNode q : tree.getChildren())
			{
				if(q.getChildren().size()>0) drawTree(g2d , q);
					int index = getIndex(q)+tree.getChildren().indexOf(q);
					System.out.println(q.getValue().toString() + "- index : "+index);
					int x = index*drawNodeWidth;
					
						g2d.drawOval(x, drawNodeHeight * 1* q.getDepth(),
						drawNodeWidth, drawNodeHeight);
				g2d.drawString((String)q.getValue(), x, drawNodeHeight*1*q.getDepth());
			}
		}
		@Override 
		public void paintComponent(Graphics g){
			Graphics2D g2d = (Graphics2D)g;
			g2d.clearRect(0, 0, getWidth(), getHeight());
			maxDepth = tree.findMaxDepth(tree.getRoot(), 0);
			maxCluster = tree.findMaxCluster(tree.getRoot(), 0);
			setPreferredSize(new Dimension((int)(drawNodeWidth*Math.pow(maxCluster, maxDepth+1)), drawNodeHeight*2*maxDepth));
			System.out.println("Maxcluster: "+maxCluster);
			System.out.println("Maxdepth: "+maxDepth);
			drawTree(g2d, tree.getRoot());
			revalidate();
		}
	}
}
