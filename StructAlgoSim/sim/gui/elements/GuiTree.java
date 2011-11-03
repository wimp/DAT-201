package sim.gui.elements;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.Timer;

import sim.structures.Heap;
import sim.structures.Heap.CompareKey;
import sim.structures.Tree;
import sim.structures.Tree.Traversal;
import sim.structures.Tree.TreeNode;

@SuppressWarnings("serial")
public class GuiTree extends GuiElement implements ActionListener, MouseMotionListener{
	private boolean isheap;
	private Point mousePos = new Point(0,0);
	private boolean show;

	private Tree tree;
	private TreePanel treePanel;

	private TreeNode changed;
	public TreeNode getChanged() {
		return changed;
	}	

	public void setChanged(TreeNode changed) {
		this.changed = changed;
	}
	private Vector<TreeNode> pathToChanged;

	public Vector<TreeNode> getPathToChanged() {
		return pathToChanged;
	}
	public void setPathToChanged(Vector<TreeNode> pathToChanged) {
		this.pathToChanged = pathToChanged;
	}	
	public void showValues(boolean b){
		show = b;
	}
	//TREE
	private JTextField nval;
	private ButtonGroup bg;
	private JRadioButton nary;
	private JRadioButton bin;
	private ButtonGroup trg;
	private JRadioButton inorder;
	private JRadioButton preorder;
	private JRadioButton postorder;
	private JRadioButton breadthfirst;
	private JCheckBox showvalue;

	//HEAP
	private ButtonGroup bg1;
	private JRadioButton min;
	private JRadioButton max;
	private JRadioButton alpha;
	private JRadioButton num;
	private JRadioButton strlen;

	public GuiTree(Rectangle bounds,Heap heap){
		super();
		isheap = true;
		
		animation = new Timer(750,this);
		
		this.tree = heap;
		setLayout(new BorderLayout());
		setBounds(bounds);			
		initHeapGraphics();
	}
	public GuiTree(Rectangle bounds,Tree tree){
		super();
		isheap = false;
		
		animation = new Timer(750,this);
		
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
		treePanel.addMouseMotionListener(this);

		JPanel check = new JPanel(new GridLayout(3,5));
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

		check.add(new JLabel("Type:"));
		check.add(bin);
		check.add(nary);
		check.add(nval);
		check.add(new JLabel(""));

		preorder = new JRadioButton("PreOrder");
		preorder.addActionListener(this);
		inorder = new JRadioButton("InOrder");
		inorder.addActionListener(this);
		postorder = new JRadioButton("PostOrder");
		postorder.addActionListener(this);
		breadthfirst = new JRadioButton("BreadthFirst");
		breadthfirst.addActionListener(this);
		trg = new ButtonGroup();
		trg.add(preorder);
		trg.add(inorder);
		trg.add(postorder);
		trg.add(breadthfirst);

		check.add(new JLabel("Traversal:"));
		check.add(preorder);
		check.add(inorder);
		check.add(postorder);
		check.add(breadthfirst);

		showvalue = new JCheckBox("Show Values");
		showvalue.addActionListener(this);

		check.add(showvalue);
		check.add(new JLabel(""));
		check.add(new JLabel(""));
		check.add(new JLabel(""));

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
		listScroller.addMouseMotionListener(this);

		JPanel check = new JPanel(new GridLayout(4,5));
		min = new JRadioButton("Min");
		min.setSelected(true);
		min.addActionListener(this);
		max = new JRadioButton("Max");
		max.addActionListener(this);
		bg = new ButtonGroup();
		bg.add(min);
		bg.add(max);

		check.add(new JLabel("Type:"));
		check.add(min);
		check.add(max);
		check.add(new JLabel(""));
		check.add(new JLabel(""));

		preorder = new JRadioButton("PreOrder");
		preorder.addActionListener(this);
		inorder = new JRadioButton("InOrder");
		inorder.addActionListener(this);
		postorder = new JRadioButton("PostOrder");
		postorder.addActionListener(this);
		breadthfirst = new JRadioButton("BreadthFirst");
		breadthfirst.addActionListener(this);
		trg = new ButtonGroup();
		trg.add(preorder);
		trg.add(inorder);
		trg.add(postorder);
		trg.add(breadthfirst);

		check.add(new JLabel("Traversal:"));
		check.add(preorder);
		check.add(inorder);
		check.add(postorder);
		check.add(breadthfirst);

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

		check.add(new JLabel("Sorting key:"));
		check.add(alpha);
		check.add(num);
		check.add(strlen);
		check.add(new JLabel(""));

		showvalue = new JCheckBox("Show Values");
		showvalue.addActionListener(this);

		check.add(showvalue);
		check.add(new JLabel(""));
		check.add(new JLabel(""));
		check.add(new JLabel(""));
		check.add(new JLabel(""));

		this.add(check, BorderLayout.NORTH);
		this.add(listScroller, BorderLayout.CENTER);
	}
	private class TreePanel extends JPanel{
		int drawNodeWidth = GuiSettings.TREENODEWIDTH;
		int drawNodeHeight = GuiSettings.TREENODEHEIGHT;
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
			if(tree == null) return;
			for(Tree.TreeNode q : tree.getChildren())
			{
				if(q!=null){
					if(q.getNumberOfChildren()>0) 
						drawTree(g2d , q);
					drawLink(g2d, q, q.getParent());
					drawNode(g2d, q, getOffset(q));
				}
			}
		}
		private void drawLink(Graphics2D g2d, Tree.TreeNode n, Tree.TreeNode m){

			if(m!=null && n!=null)			
			{	
				
				Color c = g2d.getColor();
				if(pathToChanged !=null && changed !=null){
				if((pathToChanged.contains(n) || pathToChanged.contains(m))){
					if((pathToChanged.contains(n)&& pathToChanged.contains(m)))
						if(changed.isAdded())
							g2d.setColor(GuiSettings.TREEADDEDCOLOR);
						else
							g2d.setColor(GuiSettings.TREEREMOVEPATHCOLOR);
					else if((pathToChanged.contains(n) && m==changed))
						if(changed.isAdded())
							g2d.setColor(GuiSettings.TREEADDEDCOLOR);
						else
							g2d.setColor(GuiSettings.TREEREMOVEPATHCOLOR);
					else if((pathToChanged.contains(m) && n==changed))
						if(changed.isAdded())
							g2d.setColor(GuiSettings.TREEADDEDCOLOR);
						else
							g2d.setColor(GuiSettings.TREEREMOVEPATHCOLOR);
				}
				}
				g2d.drawLine(getOffset(n)+drawNodeHeight/2, drawNodeHeight *2* n.getDepth()+drawNodeHeight,
						getOffset(m)+drawNodeHeight/2,  drawNodeHeight *2* m.getDepth()+drawNodeHeight+drawNodeHeight/2);
				
				g2d.setColor(c);
			}
		}
		private void drawNode(Graphics2D g2d, Tree.TreeNode n, int offset){
			Color c = g2d.getColor();

			if(mousePos.x > offset && mousePos.x < offset+drawNodeWidth && 
					mousePos.y > drawNodeHeight *2* n.getDepth()+drawNodeHeight && 
					mousePos.y < drawNodeHeight *2* n.getDepth()+drawNodeHeight*2){
				selected = n;
			}
			if(n!=selected){
				if(show){
					int v=g2d.getFontMetrics(getFont()).charsWidth(n.getValue().toString().toCharArray(), 0, n.getValue().toString().toCharArray().length)+20;
					if(v<drawNodeWidth) v = drawNodeWidth;
					g2d.setColor(GuiSettings.TREECONTENTCOLOR);
					if(n!=null && pathToChanged !=null)
						if(n==changed){
							if(changed.isAdded())
								g2d.setColor(GuiSettings.TREEADDEDCOLOR);
							else
								g2d.setColor(GuiSettings.TREEREMOVEDCOLOR);
						}
						else if(frame <pathToChanged.size() && n.getCurrentIndex()<=frame){						
							if(changed.isAdded())
								g2d.setColor(GuiSettings.TREEADDPATHCOLOR);
							else
								g2d.setColor(GuiSettings.TREEREMOVEPATHCOLOR);
						}


					g2d.fillRoundRect(offset, drawNodeHeight *2* n.getDepth()+drawNodeHeight, v, drawNodeHeight, 5, 5);
					g2d.setColor(c);
					g2d.drawRoundRect(offset, drawNodeHeight *2* n.getDepth()+drawNodeHeight, v, drawNodeHeight, 5, 5);
					g2d.drawString(n.getValue().toString(),offset+10, drawNodeHeight *2* n.getDepth()+drawNodeHeight+drawNodeHeight/2);
				}
				else{

					g2d.setColor(!n.isLeaf() ? GuiSettings.TREENODECOLOR : GuiSettings.TREELEAFCOLOR);

					if(n!=null && pathToChanged !=null){
						if(n==changed){
							if(changed.isAdded())
								g2d.setColor(GuiSettings.TREEADDEDCOLOR);
							else
								g2d.setColor(GuiSettings.TREEREMOVEDCOLOR);
						}
						else if(frame <pathToChanged.size() && n.getCurrentIndex()<=frame){						
							if(changed.isAdded())
								g2d.setColor(GuiSettings.TREEADDPATHCOLOR);
							else
								g2d.setColor(GuiSettings.TREEREMOVEPATHCOLOR);
						}
					}
					g2d.fillOval(offset, drawNodeHeight *2* n.getDepth()+drawNodeHeight,
							drawNodeWidth, drawNodeHeight);
					g2d.setColor(c);			
					g2d.drawOval(offset, drawNodeHeight *2* n.getDepth()+drawNodeHeight,
							drawNodeWidth, drawNodeHeight);
					g2d.drawString(new Integer(n.getCurrentIndex()).toString(), offset+(drawNodeWidth/3), drawNodeHeight*2*n.getDepth()+drawNodeHeight+(drawNodeHeight/2));

				}
			}
		}
		Tree.TreeNode selected;
		@Override 
		public void paintComponent(Graphics g){
			if(!GuiSettings.isAnimated && animation.isRunning())
				stopAnimation();
			
			Graphics2D g2d = (Graphics2D)g;
			g2d.clearRect(0, 0, getWidth(), getHeight());

			indent = new int[tree.getMaxDepth()+1];
			setPreferredSize(new Dimension((int)(drawNodeWidth*Math.pow(tree.getMaxCluster(), tree.getMaxDepth())), drawNodeHeight*3*tree.getMaxDepth()));

			drawTree(g2d, tree.getRoot());
			if(tree.getRoot() != null)
				drawNode(g2d, tree.getRoot(), getOffset(tree.getRoot()));	

			if(selected != null){
				Color c = g2d.getColor();
				if(!show){
					int v=g2d.getFontMetrics(getFont()).charsWidth(selected.getValue().toString().toCharArray(), 0, selected.getValue().toString().toCharArray().length)+20;
					if(v<drawNodeWidth) v = drawNodeWidth;
					g2d.setColor(GuiSettings.TREECONTENTCOLOR);
					g2d.fillRoundRect(getOffset(selected), drawNodeHeight *2* selected.getDepth()+drawNodeHeight, v, drawNodeHeight, 5, 5);
					g2d.setColor(c);
					g2d.drawRoundRect(getOffset(selected), drawNodeHeight *2* selected.getDepth()+drawNodeHeight, v, drawNodeHeight, 5, 5);
					g2d.drawString(selected.getValue().toString(),getOffset(selected)+10, drawNodeHeight *2* selected.getDepth()+drawNodeHeight+drawNodeHeight/2);
					selected = null;
				}
				else{
					g2d.setColor(selected.isLeaf() ? GuiSettings.TREENODECOLOR : GuiSettings.TREELEAFCOLOR);
					g2d.fillOval(getOffset(selected), drawNodeHeight *2* selected.getDepth()+drawNodeHeight,
							drawNodeWidth, drawNodeHeight);
					g2d.setColor(c);			
					g2d.drawOval(getOffset(selected), drawNodeHeight *2* selected.getDepth()+drawNodeHeight,
							drawNodeWidth, drawNodeHeight);
					g2d.drawString(new Integer(selected.getCurrentIndex()).toString(), getOffset(selected)+(drawNodeWidth/3), drawNodeHeight*2*selected.getDepth()+drawNodeHeight+(drawNodeHeight/2));
					selected = null;	
				}
			}
			revalidate();
		}
	}
	public void stopAnimation(){
		animation.stop();
		if(changed!= null && changed.isRemoved()){
			changed.setRemoved(false);

			if(changed.getNumberOfChildren()==1){
				TreeNode child = changed.getChild(0);
				if(child!=null && changed.getParent()!=null){
					changed.getParent().getChildren().set(changed.getParent().getChildren().indexOf(changed),child);
					child.setParent(changed.getParent());
				}
				else{
					child.setParent(null);
					tree.setRoot(child);
				}
			}
			else if(changed.getNumberOfChildren()>1){
				Traversal t = tree.getTraversal();
				tree.setTraversal(Traversal.INORDER);
				TreeNode n = changed.getChild(0);
				while(n.getNumberOfChildren()>0){
					n = n.getChild(0);
				}
				changed.setValue(n.getValue().toString());
				n.getParent().removeChild(n);
				tree.setTraversal(t);
			}
			else{
				if(changed.getParent()!= null)
					changed.getParent().removeChild(changed);
				else
					tree.setRoot(null);
			}
			tree.setIndices();
		}
		else if(changed!=null)changed.setAdded(false);

		if(isheap) ((Heap)tree).heapify();
		changed = null;
		pathToChanged = null;
		frame = 0;
	}
	public void startAnimation(){
		super.startAnimation();
		if(pathToChanged!=null){
			setMaxFrame(pathToChanged.size()+1);
			animation.setDelay((500*pathToChanged.size())/getMaxFrame());
		}

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == animation){
			frame++;
			if(frame>getMaxFrame()){
				stopAnimation();
			}
		}
		if(e.getSource()== preorder){
			tree.setTraversal(Traversal.PREORDER);
		}
		else if(e.getSource()== inorder){
			tree.setTraversal(Traversal.INORDER);			
		}
		else if(e.getSource()==postorder){
			tree.setTraversal(Traversal.POSTORDER);
		}
		else if(e.getSource()==breadthfirst){
			tree.setTraversal(Traversal.BREADTHFIRST);
		}
		else if(e.getSource() == showvalue)
			show=!show;

		if(!isheap)
			if(e.getSource()==nary){
				nval.setText("");
				nval.setEditable(true);

			}
			else if(e.getSource() == nval){
				int mc;
				try{
					mc = Integer.parseInt(nval.getText());
					if(mc!=2){
						inorder.setEnabled(false);
						if(tree.getTraversal() == Traversal.INORDER) 
							tree.setTraversal(Traversal.PREORDER);
						preorder.setSelected(true);
					}
					else{
						inorder.setEnabled(true);
					}

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
		if(isheap) 
			if(e.getSource() == max){
				if(tree.getRoot() !=null)
				{
					((Heap)tree).setMax(true);
				}
			}
			else if(e.getSource() == min){
				if(tree.getRoot() !=null){
					((Heap)tree).setMax(false);
				}
			}
			else if(e.getSource() == alpha){
				((Heap)tree).setSortKey(CompareKey.ALPHABETICAL);
			}
			else if(e.getSource() == num){
				((Heap)tree).setSortKey(CompareKey.NUMERICAL);		
			}
			else if(e.getSource() == strlen){
				((Heap)tree).setSortKey(CompareKey.STRLEN);		
			}
		repaint();
		validate();
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
