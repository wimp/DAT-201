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
		int drawNodeWidth = 10;
		int drawNodeHeight = 10;
		int maxCluster;
		public TreePanel(){
			
		}
		public int drawTree(Graphics2D g2d ,Tree.TreeNode tree){
			int prevCluster;
			int indent;
			for(Tree.TreeNode q : tree.getChildren())
			{
				if(q.getChildren().size()>0) prevCluster = drawTree(g2d , q);
				int relativeNodePos = (tree.getChildren().indexOf(q)*drawNodeWidth*2)+maxCluster*tree.getChildren().indexOf(q);
				int levelOffset = tree.getParent()== null ? 0 : tree.getParent().getChildren().indexOf(tree)*drawNodeWidth;
				g2d.drawOval(relativeNodePos + levelOffset, drawNodeHeight * 3* q.getDepth(),
						drawNodeWidth, drawNodeHeight);
				//g2d.drawString((String)q.getValue(), (q.getChildren().size())*drawNodeWidth+drawNodeWidth, drawNodeHeight*q.getDepth()+drawNodeWidth);
				System.out.println(q.getParent().getValue().toString()+" - "+tree.getChildren().indexOf(q)+": " +q.getValue().toString());
			}
			return 0;
		}
		public int findMaxCluster(Tree.TreeNode t, int max){
			if(t.getChildren().size()>max){
				max = (t.getDepth()+1)*drawNodeWidth;
			}
			for(Tree.TreeNode q : t.getChildren())
			{
				max = findMaxCluster(q, max);
			}
			return max;
		}
		@Override 
		public void paintComponent(Graphics g){
			Graphics2D g2d = (Graphics2D)g;
			maxCluster = root.getChildren().size();
			maxCluster = findMaxCluster(root, maxCluster);
			System.out.println("Maxcluster: "+maxCluster);
			drawTree(g2d, root);
			this.validate();

		}
	}
}
