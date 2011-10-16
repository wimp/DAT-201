package sim.structures;

import java.awt.Rectangle;
import java.util.Vector;

import sim.gui.elements.GuiTree;


public class Tree {

	/**
	 * @param args
	 */
	GuiTree gui;
	public int getMaxDepth() {
		return findMaxDepth(root, 0);
	}
	public int getMaxCluster() {
		return maxCluster;
	}
	public void setMaxCluster(int maxCluster) {
		this.maxCluster = maxCluster;
		rebuildTree();
	}
	int maxCluster = 2;
	
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
		gui = new GuiTree(bounds, this, animated);
	}
	protected Tree(){
		root = new TreeNode("root", null);	
	}
	public void rebuildTree(){
		Vector<TreeNode> nodes = getAllNodes(new Vector<TreeNode>(), root);
		nodes.remove(root);
		root = new TreeNode("root", null);
		//nodes.remove(root);
		for(TreeNode n : nodes)
			addBreadthFirst(n.getValue().toString());
	}
	public Vector<TreeNode> getAllNodes(Vector<TreeNode> nodes, TreeNode n){
		nodes.add(n);
		for(TreeNode t : n.getChildren())
			getAllNodes(nodes, t);
		return nodes;
	}
	
	public void swapNodes(TreeNode a, TreeNode b){
		Object o = a.getValue().toString();
		a.setValue(b.getValue().toString());
		b.setValue(o);
	}
	public void addBreadthFirst(String value){
		breadthFirstInsert().insert(value);
		gui.repaint();
	}
	private TreeNode depthFirstInsert(){
		Vector<TreeNode> nodeQueue = new Vector<TreeNode>();
		TreeNode n = root;
		nodeQueue.addAll(n.getChildren());
		while(n.getChildren().size()==maxCluster && nodeQueue.size()>0){
			nodeQueue.addAll(n.getChildren());
			n = nodeQueue.remove(nodeQueue.size()-1);
		}
		return n;
	}
	private TreeNode breadthFirstInsert(){
		Vector<TreeNode> nodeQueue = new Vector<TreeNode>();
		TreeNode n = root;
		nodeQueue.addAll(n.getChildren());
		while(n.getChildren().size()==maxCluster && nodeQueue.size()>0){
			nodeQueue.addAll(n.getChildren());
			n = nodeQueue.remove(0);
		}
		return n;
	}
	public int findMaxCluster(TreeNode t, int max){
		
		if(t.getChildren().size()>max) max = t.getChildren().size();
		for(Tree.TreeNode q : t.getChildren())
		{
			max = findMaxCluster(q, max);
		}
		return max;
	}
	public int getHeight(TreeNode t){
		if(t==null) return 0;
		return findMaxDepth(t, 0)-t.getDepth();
	}
	public int findMaxDepth(TreeNode t, int max){
		if(t==null) return max;
		if(t.getDepth()>max) max = t.getDepth();
		for(Tree.TreeNode q : t.getChildren())
		{
			max = findMaxDepth(q, max);
		}
		return max;
	}
	public class TreeNode{
		private TreeNode parent;
		public TreeNode getParent() {
			return parent;
		}
		public void setParent(TreeNode parent) {
			this.parent = parent;
		}
		private Vector<TreeNode> children;
		private Object value;
		
		public boolean isLeaf() {
			return !(children.size()>0);
		}
		public int getDepth() {
			if(getParent() == null) return 0;
			TreeNode n = getParent();
			int depth = 0;
			while(n!=null){
				n =	n.getParent();
				depth++;
			}
			return depth;
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
			this.parent = parent;
			children = new Vector<TreeNode>();
		}
	}
}
