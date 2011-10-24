package sim.structures;

import java.awt.Rectangle;
import java.util.Vector;

import sim.gui.elements.GuiTree;


public class Tree {

	/**
	 * @param args
	 */
	public enum Traversal{
		PREORDER,
		INORDER,
		POSTORDER
	}
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
		
		addBreadthFirst("1");
		addBreadthFirst("2");
		addBreadthFirst("3");
		addBreadthFirst("4");
		addBreadthFirst("5");
		addBreadthFirst("6");
		addBreadthFirst("7");
		addBreadthFirst("8");
		addBreadthFirst("9");
		addBreadthFirst("10");
		addBreadthFirst("11");
		addBreadthFirst("12");
		addBreadthFirst("13");		
		addBreadthFirst("14");
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
	// Get element methods
	private int currentIndex = 0;
	private TreeNode currentNode = null;
	private Traversal traversal = Traversal.INORDER;
	public Traversal getTraversal() {
		return traversal;
	}
	public void setTraversal(Traversal traversal) {
		this.traversal = traversal;
	}
	public void insertAt(int index,Object value){
		if(elementAt(index).getChildren().size()!=maxCluster){
			elementAt(index).getChildren().add(new TreeNode(value, elementAt(index)));
		}
	}
	public TreeNode elementAt(int index){
		currentNode = null;
		currentIndex = 0;
		switch(traversal){
		case INORDER:
			inOrderElementAt(root, index);
		break;
		case PREORDER:
			preOrderElementAt(root, index);
		break;
		case POSTORDER:
			postOrderElementAt(root, index);
		break;
		}
		return currentNode;
	}
	private void inOrderElementAt(TreeNode n, int index){
		for(int i= 0; i<n.getChildren().size()-1; i++){
					inOrderElementAt(n.getChildren().elementAt(i), index);
		}		
		currentIndex++;
		if(index == currentIndex) currentNode = n;
		
		if(n.getChildren().size()>0)
			inOrderElementAt(n.getChildren().elementAt(n.getChildren().size()-1), index);
	}
	private void preOrderElementAt(TreeNode n, int index){
		currentIndex++;
		if(index == currentIndex) currentNode = n;
		
		for(int i= 0; i<n.getChildren().size()-1; i++){
					preOrderElementAt(n.getChildren().elementAt(i), index);
		}		
		if(n.getChildren().size()>0)
			preOrderElementAt(n.getChildren().elementAt(n.getChildren().size()-1), index);
	}
	private void postOrderElementAt(TreeNode n, int index){

		for(int i= 0; i<n.getChildren().size()-1; i++){
					postOrderElementAt(n.getChildren().elementAt(i), index);
		}		
		if(n.getChildren().size()>0)
			postOrderElementAt(n.getChildren().elementAt(n.getChildren().size()-1), index);
		
		currentIndex++;
		if(index == currentIndex) currentNode = n;
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
		private boolean added;
		private boolean removed;
		public boolean isAdded() {
			return added;
		}
		public void setAdded(boolean added) {
			this.added = added;
		}
		public boolean isRemoved() {
			return removed;
		}
		public void setRemoved(boolean removed) {
			this.removed = removed;
		}
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
		public int getDegree(){
			return children.size();
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
			TreeNode n = new TreeNode(value, this);
			n.setAdded(true);
			addSubTree(n);
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