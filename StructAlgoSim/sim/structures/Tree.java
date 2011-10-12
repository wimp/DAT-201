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
	
	public GuiTree getGuiElement() {
		return gui;
	}
	public Tree(Rectangle bounds,boolean animated){
		TreeNode root = new TreeNode("root", null);
		TreeNode lol = new TreeNode("1", root);
		lol.insert("1.1");
		TreeNode fu = new TreeNode("1.2", lol);
		fu.insert("1.2.1");		
		fu.insert("1.2.1");
		fu.insert("1.2.2");
		lol.addSubTree(fu);
		TreeNode fu1 = new TreeNode("1.3", lol);
		fu1.insert("1.3.1");		
		fu1.insert("1.3.1");
		fu1.insert("1.3.2");
		lol.addSubTree(fu1);
		TreeNode lol1 = new TreeNode("1.3", root);
		lol1.insert("1.3.1");		
		lol1.insert("1.3.1");
		lol1.insert("1.3.2");
		root.addSubTree(lol1);
		root.addSubTree(lol);
		gui = new GuiTree(bounds, root, animated);
	}

}
