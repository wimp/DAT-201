package sim.gui.elements;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.Timer;

import sim.structures.Heap;
import sim.structures.Heap.CompareKey;
import sim.structures.Tree;

@SuppressWarnings("serial")
public class GuiTree extends GuiElement implements ActionListener{
	boolean isheap;
	Tree tree;
	TreePanel treePanel;
	
	//TREE
	JTextField nval;
	ButtonGroup bg;
	JRadioButton nary;
	JRadioButton bin;
	ButtonGroup trg;
	JRadioButton inorder;
	JRadioButton preorder;
	JRadioButton postorder;
	
	
	//HEAP
	ButtonGroup bg1;
	JRadioButton min;
	JRadioButton max;
	JRadioButton alpha;
	JRadioButton num;
	JRadioButton strlen;
	
	public GuiTree(Rectangle bounds,Heap heap, boolean animated){
		super();
		isheap = true;
		if(animated) animation = new Timer(750,this);
		else 
			animation = new Timer(0, this);
		this.tree = heap;
		setLayout(new BorderLayout());
		setBounds(bounds);			
		initHeapGraphics();
	}
	public GuiTree(Rectangle bounds,Tree tree, boolean animated){
		super();
		isheap = false;
		if(animated) animation = new Timer(750,this);
		else 
			animation = new Timer(0, this);
		this.tree = tree;
		setLayout(new BorderLayout());
		setBounds(bounds);			
		initTreeGraphics();
	}
	private void initTreeGraphics(){
		treePanel = new TreePanel();
		treePanel.setPreferredSize(new Dimension(getWidth(), getHeight()));
		JScrollPane listScroller = new JScrollPane(treePanel);
		listScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		listScroller.setPreferredSize(new Dimension(getWidth(), getHeight()));
		JPanel check = new JPanel(new GridLayout(1,8));
		nval = new JTextField("N-value");
		nval.setEditable(false);
		nval.addActionListener(this);
		nary = new JRadioButton("N-ary");
		nary.addActionListener(this);
		bin = new JRadioButton("Binary");
		bin.setSelected(true);
		bin.addActionListener(this);
		bg = new ButtonGroup();
		bg.add(nary);
		bg.add(bin);
		check.add(bin);
		check.add(nary);
		check.add(nval);

		preorder = new JRadioButton("PreOrder");
		preorder.addActionListener(this);
		inorder = new JRadioButton("InOrder");
		inorder.addActionListener(this);
		postorder = new JRadioButton("PostOrder");
		postorder.addActionListener(this);
		trg = new ButtonGroup();
		trg.add(preorder);
		trg.add(inorder);
		trg.add(postorder);
		
		check.add(preorder);
		check.add(inorder);
		check.add(postorder);
		
		check.add(new JLabel("      "));
		
		this.add(check, BorderLayout.NORTH);
		this.add(listScroller, BorderLayout.CENTER);
	}
	private void initHeapGraphics(){
		treePanel = new TreePanel();
		treePanel.setPreferredSize(new Dimension(getWidth(), getHeight()));
		JScrollPane listScroller = new JScrollPane(treePanel);
		listScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		listScroller.setPreferredSize(new Dimension(getWidth(), getHeight()));
		JPanel check = new JPanel(new GridLayout(1,8));
		min = new JRadioButton("Min");
		min.setSelected(true);
		min.addActionListener(this);
		max = new JRadioButton("Max");
		max.addActionListener(this);
		bg = new ButtonGroup();
		bg.add(min);
		bg.add(max);
		check.add(min);
		check.add(max);
		
		alpha = new JRadioButton("Alphabetical");
		alpha.addActionListener(this);
		num = new JRadioButton("Numerical");
		num.addActionListener(this);
		strlen = new JRadioButton("Length");
		strlen.addActionListener(this);
		bg1 = new ButtonGroup();
		bg1.add(alpha);
		bg1.add(num);
		bg1.add(strlen);
		
		check.add(alpha);
		check.add(num);
		check.add(strlen);
		
		check.add(new JLabel("      "));
		check.add(new JLabel("      "));
		check.add(new JLabel("      "));
		
		this.add(check, BorderLayout.NORTH);
		this.add(listScroller, BorderLayout.CENTER);
	}
	private class TreePanel extends JPanel{
		int drawNodeWidth = 20;
		int drawNodeHeight = 20;

		int[] indent;
		
		public TreePanel(){
		}
		private int getIndent(Tree.TreeNode n){
			int indent = 0;
			for(int i =0; i<=(tree.getMaxDepth())-n.getDepth(); i++){
					indent= (int)Math.pow(tree.getMaxCluster(), i);
				}

			return indent;
		}
		private int getOffset(Tree.TreeNode n){
			int[] index = new int[tree.getMaxDepth()];
			float in = getIndent(n)/2.0f;
					while(n!=null){
						indent[n.getDepth()] = getIndent(n);
						if(n.getParent()!=null ){
						index[n.getDepth()-1] = n.getParent().getChildren().indexOf(n);
						}
						n = n.getParent();
					}
					for(int i=0; i<index.length; i++)
						in+=index[i]*indent[i+1];
			return (int)(in*drawNodeWidth);
		}
		public void drawTree(Graphics2D g2d ,Tree.TreeNode tree){
			for(Tree.TreeNode q : tree.getChildren())
			{
				if(q.getChildren().size()>0) drawTree(g2d , q);
					drawNode(g2d, q, getOffset(q));
			}
		}
		private void drawNode(Graphics2D g2d, Tree.TreeNode n, int offset){
			Color c = g2d.getColor();
			g2d.setColor(n.isLeaf() ? GuiSettings.TREENODECOLOR : GuiSettings.TREELEAFCOLOR);
			g2d.fillOval(offset, drawNodeHeight *2* n.getDepth()+drawNodeHeight,
					drawNodeWidth, drawNodeHeight);
			g2d.setColor(c);			
			g2d.drawOval(offset, drawNodeHeight *2* n.getDepth()+drawNodeHeight,
					drawNodeWidth, drawNodeHeight);
			g2d.drawString((String)n.getValue(), offset, drawNodeHeight*2*n.getDepth()+drawNodeHeight);
		
		}
		@Override 
		public void paintComponent(Graphics g){
			Graphics2D g2d = (Graphics2D)g;
			g2d.clearRect(0, 0, getWidth(), getHeight());
			
			indent = new int[tree.getMaxDepth()+1];
			setPreferredSize(new Dimension((int)(drawNodeWidth*Math.pow(tree.getMaxCluster(), tree.getMaxDepth())), drawNodeHeight*2*tree.getMaxDepth()));
			
			drawNode(g2d, tree.getRoot(), getOffset(tree.getRoot()));
			drawTree(g2d, tree.getRoot());
			
			revalidate();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==nary){
			nval.setText("");
			nval.setEditable(true);
			
		}
		else if(e.getSource() == nval){
			int mc;
			try{
				mc = Integer.parseInt(nval.getText());

				tree.setMaxCluster(mc);
			}
			catch(NumberFormatException nfe){
				
			}
		}
		else if(e.getSource()==bin){
			nval.setText("N-value");
			nval.setEditable(false);
			tree.setMaxCluster(2);
		}
		else if(e.getSource() == max){
			if(isheap) ((Heap)tree).maxHeapifyTree(tree.getRoot());
		}
		else if(e.getSource() == min){
			if(isheap) ((Heap)tree).minHeapifyTree(tree.getRoot());			
		}
		else if(e.getSource() == alpha){
			if(isheap) ((Heap)tree).setSortKey(CompareKey.ALPHABETICAL);
		}
		else if(e.getSource() == num){
			if(isheap) ((Heap)tree).setSortKey(CompareKey.NUMERICAL);		
		}
		else if(e.getSource() == strlen){
			if(isheap) ((Heap)tree).setSortKey(CompareKey.STRLEN);		
		}
		repaint();
		validate();
	}
}
