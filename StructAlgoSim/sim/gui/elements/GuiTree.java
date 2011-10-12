package sim.gui.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import sim.structures.Tree;

public class GuiTree extends GuiElement{
	Tree.TreeNode root;
	TreePanel treePanel;
	
	public GuiTree(Rectangle bounds,Tree.TreeNode root, boolean animated){
		super();
		
		if(animated) animation = new Timer(750,this);
		else 
			animation = new Timer(0, this);
		this.root = root;

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
		int drawNodeWidth = 40;
		int drawNodeHeight = 40;
		int clusterWidth;
		int maxCluster;
		int maxDepth; 
		
		public TreePanel(){
		}
		public void drawTree(Graphics2D g2d ,Tree.TreeNode tree){
			for(Tree.TreeNode q : tree.getChildren())
			{
				if(q.getChildren().size()>0) drawTree(g2d , q);
				int relativeNodePos = tree.getParent()==null ? getWidth()/2 :
					getWidth()/2+(((int)(Math.pow(maxCluster,q.getDepth()+1))*drawNodeWidth)/(int)(Math.pow(maxCluster,q.getDepth()+1))*(tree.getChildren().indexOf(q))
						+(tree.getParent().getChildren().indexOf(tree))*clusterWidth)
						;
				g2d.drawOval(relativeNodePos, drawNodeHeight * 3* q.getDepth(),
						drawNodeWidth, drawNodeHeight);
				g2d.drawString((String)q.getValue(), relativeNodePos, drawNodeHeight*3*q.getDepth());
				System.out.println(q.getParent().getValue().toString()+" - "+tree.getChildren().indexOf(q)+": " +q.getValue().toString());
			}
		}
		public int findMaxCluster(Tree.TreeNode t, int max){
			if(t.getChildren().size()>max){
				max = t.getChildren().size();
			}
			for(Tree.TreeNode q : t.getChildren())
			{
				max = findMaxCluster(q, max);
			}
			return max;
		}
		public int findMaxDepth(Tree.TreeNode t, int max){
			if(t.getDepth()>max){
				max = t.getDepth();
			}
			for(Tree.TreeNode q : t.getChildren())
			{
				max = findMaxDepth(q, max);
			}
			return max;
		}
		@Override 
		public void paintComponent(Graphics g){
			Graphics2D g2d = (Graphics2D)g;
			maxCluster = root.getChildren().size();
			maxDepth = findMaxDepth(root,0);
			maxCluster = findMaxCluster(root, maxCluster);
			clusterWidth = maxCluster*drawNodeWidth;
			setPreferredSize(new Dimension((int)(drawNodeWidth*Math.pow(maxCluster, maxDepth)), drawNodeHeight*2*maxDepth));
			System.out.println("Maxcluster: "+maxCluster);
			System.out.println("Maxdepth: "+maxDepth);
			drawTree(g2d, root);
			treePanel.validate();

		}
	}
}
