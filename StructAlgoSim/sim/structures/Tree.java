package sim.structures;

import java.awt.Rectangle;
import java.util.Vector;

import sim.gui.elements.GuiTree;


public class Tree {
	public class TreeNode{
		private TreeNode parent;
		public TreeNode getParent() {
			return parent;
		}
		public void setParent(TreeNode parent) {
			this.parent = parent;
		}
		private Vector<TreeNode> children;
		private boolean leaf;
		private int depth;
		private Object value;
		
		public boolean isLeaf() {
			return leaf;
		}
		public void setLeaf(boolean leaf) {
			this.leaf = leaf;
		}
		public int getDepth() {
			return depth;
		}
		public void setDepth(int depth) {
			this.depth = depth;
		}	
		public Vector<TreeNode> getChildren(){
			return children;
		}
		public void setChildren(Vector<TreeNode> children) {
			this.children = children;
		}
		public void setValue(Object value) {
			this.value = value;
		}
		public Object getValue(){
			return value;
		}
		public void insert(Object value){
			addSubTree(new TreeNode(value, this));
		}
		public void addSubTree(TreeNode root){
			children.add(root);	
		}
		public TreeNode(Object value, TreeNode parent){
			this.value = value;
			if(parent != null) this.depth = parent.getDepth()+1;
			else this.depth = 0;
			this.parent = parent;
			children = new Vector<TreeNode>();
		}
	}
	/**
	 * @param args
	 */
	GuiTree gui;
	int maxDepth;
	TreeNode root; 
	
	public TreeNode getRoot() {
		return root;
	}
	public void setRoot(TreeNode root) {
		this.root = root;
	}
	public GuiTree getGuiElement() {
		return gui;
	}
	public Tree(Rectangle bounds,boolean animated){
		root = new TreeNode("root", null);
		TreeNode lol0 = new TreeNode("1", root);
		lol0.insert("11");		
		lol0.insert("12");
		lol0.insert("13");
		TreeNode lol1 = new TreeNode("2", root);
		lol1.insert("21");		
		lol1.insert("22");
		lol1.insert("23");
		TreeNode lol2 = new TreeNode("3", root);
		lol2.insert("31");		
		lol2.insert("32");
		lol2.insert("33");
		root.addSubTree(lol0);
		root.addSubTree(lol1);
		root.addSubTree(lol2);
		
		gui = new GuiTree(bounds, this, animated);
	}
	public int findMaxCluster(TreeNode t, int max){
		
		if(t.getChildren().size()>max) max = t.getChildren().size();

		for(Tree.TreeNode q : t.getChildren())
		{
			return findMaxCluster(q, max);
		}
		return max;
	}
	public int getHeight(TreeNode t){
		return 0;
	}
	public int findMaxDepth(TreeNode t, int max){
		if(t.getDepth()>max) max = t.getDepth();
		for(Tree.TreeNode q : t.getChildren())
		{
			return findMaxDepth(q, max);
		}
		return max;
	}
}
