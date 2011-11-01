package sim.structures;

import java.awt.Rectangle;
import java.util.Vector;

import sim.gui.elements.GuiTree;


public class Tree {

	public enum Traversal{
		PREORDER,
		INORDER,
		POSTORDER,
		BREADTHFIRST
	}
	private int maxCluster = 2;
	private TreeNode root; 
	private GuiTree gui;

	private Traversal traversal = Traversal.INORDER;

	//GETTERS AND SETTERS
	public Traversal getTraversal() {
		return traversal;
	}
	public void setTraversal(Traversal traversal) {
		this.traversal = traversal;
		setIndices();
	}
	public int getMaxDepth() {
		return findMaxDepth(root, 0);
	}
	public int getHeight(TreeNode t){
		if(t==null) return 0;
		return findMaxDepth(t, 0)-t.getDepth();
	}
	public int getMaxCluster() {
		return maxCluster;
	}
	public void setMaxCluster(int maxCluster) {
		this.maxCluster = maxCluster;
		rebuildTree();
	}
	public TreeNode getRoot() {
		return root;
	}	
	public void setRoot(TreeNode root) {
		this.root = root;
	}
	public String getValueAt(int index){
		String s = (String)elementAt(index).getValue();
		return s;
	}
	public void setValueAt(int index, Object value){
		TreeNode n = elementAt(index);
		if(n!=null)
			n.setValue(value);

		gui.repaint();
	}
	public GuiTree getGuiElement() {
		return gui;
	}
	public void setGuiElement(GuiTree gui) {
		this.gui = gui;
	}
	//CONSTRUCTORS
	public Tree(Rectangle bounds,boolean animated){
		root = null;	
		gui = new GuiTree(bounds, this, animated);
	}
	protected Tree(){
		root = null;	
	}
	//TREE METHODS
	/**
	 * Rebuilds the tree by adding breadth-first.
	 * @param values The values to build the tree from.
	 */
	public void rebuildTree(){
		if(root == null) return;
		Vector<TreeNode> nodes = getAllNodes(new Vector<TreeNode>(), root);
		setRoot(new TreeNode(nodes.remove(0).getValue().toString(), null));
		for(TreeNode n : nodes)
			addBreadthFirst(n.getValue().toString());

				setIndices();
	}
	public void swapNodes(TreeNode a, TreeNode b){
		Object o = a.getValue().toString();
		a.setValue(b.getValue().toString());
		b.setValue(o);
	}
	private void setIndices(){
		Vector<TreeNode> nodes = getAllNodes(new Vector<TreeNode>(), root);
		if(nodes != null){
			int size = nodes.size();
			for(int i= 0; i<size; i++){
				if(elementAt(i)!=null)
					elementAt(i).setCurrentIndex(i);
			}
		}
	}
	private int findMaxDepth(TreeNode t, int max){
		if(t==null) return max;
		if(t.getDepth()>max) max = t.getDepth();
		for(Tree.TreeNode q : t.getChildren())
		{
			max = findMaxDepth(q, max);
		}
		return max;
	}
	//ADD AND INSERT METHODS
	public void addBreadthFirst(String value){
		Vector<TreeNode> nodeQueue = new Vector<TreeNode>();
		TreeNode n = root;
		if(n == null){
			root = new TreeNode(value, null);
			gui.repaint();
			return;
		}
		nodeQueue = getAllNodes(nodeQueue,root);
		for(TreeNode node : nodeQueue){
			if(node.getChildren().size()<maxCluster){
				addChildAt(node.getCurrentIndex(), value);
				break;
			}
		}
		setIndices();
		
		gui.startAnimation();
		gui.repaint();
		}
	public void addChildAt(int index, Object value){
		TreeNode n = root;
		if(n == null){
			root = new TreeNode(value, null);
			gui.repaint();
			return;
		}
		TreeNode element = elementAt(index);
		if(element == null) return;
		TreeNode newnode = new TreeNode(value, element);
		if(element.getChildren().size()<maxCluster){
			element.getChildren().add(newnode);
		}
		setIndices();
		newnode.setAdded(true);
		gui.setChanged(newnode);
		Vector<TreeNode> changePath = new Vector<TreeNode>();
		changePath = getAllNodes(changePath, root);
		TreeNode start =changePath.firstElement();
		
		while(start !=newnode){
			changePath.remove(start);
			start = changePath.firstElement();
		}
		gui.setPathToChanged(changePath);
		gui.startAnimation();
		gui.repaint();
	}
	public String removeAt(int index){

		setIndices();
		TreeNode element = elementAt(index);

		if(element!= null){
			String s = element.getValue().toString();

			if(element.getChildren().size()==1){
				TreeNode child = element.getChildren().firstElement();
				if(element.getParent()!=null){
					element.getParent().getChildren().set(element.getParent().getChildren().indexOf(element),child);
					child.setParent(element.getParent());
				}
				else{
					child.setParent(null);
					setRoot(child);
				}
			}
			else if(element.getChildren().size()>1){
				Traversal t = traversal;
				traversal = Traversal.INORDER;
				TreeNode n = element.getChildren().firstElement();
				while(n.getChildren().size()>0){
					n = n.getChildren().firstElement();
				}
				element.setValue(n.getValue().toString());
				n.getParent().getChildren().remove(n);
				traversal = t;
			}
			else{
				if(element.getParent()!= null)
					element.getParent().getChildren().remove(element);
				else
					setRoot(null);
			}
			setIndices();
			gui.repaint();
			return s;
		}
		else return null;

	}
	public void insertAt(int index,Object value, boolean after){

		setIndices();

		TreeNode n = root;
		if(n == null){
			root = new TreeNode(value, null);
			gui.repaint();
			return;
		}
		TreeNode element = elementAt(index);
		if(element == null) return;
		TreeNode newnode = null;
		if(after){
			newnode = new TreeNode(value, element);

			if(element.getChildren().size()==maxCluster) return;
			else element.getChildren().add(newnode);
		}
		else{
			newnode = new TreeNode(value, element.getParent());
			if(element == root) 
				setRoot(newnode);
			else if(element.getParent() != null){
				element.getParent().getChildren().set(element.getParent().getChildren().indexOf(element), newnode);
			}
			element.setParent(newnode);
			newnode.getChildren().add(element);
		}
		setIndices();
		gui.setChanged(newnode);
		gui.startAnimation();
		gui.repaint();
	}

	// GET METHODS
	private int currentIndex = 0;
	private TreeNode currentNode = null;

	public Vector<TreeNode> getAllNodes(Vector<TreeNode> nodes, TreeNode n){
		if(n==null) return null;
		switch(traversal){
		case INORDER:
			if(n.getChildren().size()>0)
				getAllNodes(nodes, n.getChildren().get(0));

			nodes.add(n);

			if(n.getChildren().size()>1)
				getAllNodes(nodes, n.getChildren().get(1));
			break;
		case PREORDER:
			nodes.add(n);

			for(TreeNode t : n.getChildren())
				getAllNodes(nodes, t);
					break;
		case POSTORDER:
			for(TreeNode t : n.getChildren())
				getAllNodes(nodes, t);

					nodes.add(n);
		case BREADTHFIRST:
			nodes.add(n);
			Vector<TreeNode> tobeadded = new Vector<TreeNode>();
			tobeadded.addAll(n.getChildren());
			while(tobeadded.size()>0){
				nodes.add(tobeadded.firstElement());
				tobeadded.addAll(tobeadded.firstElement().getChildren());
				tobeadded.remove(tobeadded.firstElement());
			}
			break;
		}
		return nodes;
	}
	public TreeNode elementAt(int index){
		currentNode = null;
		currentIndex = 0;
		if(root != null)
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
			case BREADTHFIRST:
				breadthFirstElementAt(index);
				break;
			}
		return currentNode;
	}
	private void breadthFirstElementAt(int index){
		if(getRoot()==null) return;
		currentNode = getRoot();
		Vector<TreeNode> added = new Vector<TreeNode>();
		Vector<TreeNode> tobeadded = new Vector<TreeNode>();
		added.add(currentNode);
		tobeadded.addAll(currentNode.getChildren());
		while(tobeadded.size()>0){
			added.add(tobeadded.firstElement());
			tobeadded.addAll(tobeadded.firstElement().getChildren());
			tobeadded.remove(tobeadded.firstElement());
		}
		currentNode = added.elementAt(index);
	}
	private void inOrderElementAt(TreeNode n, int index){
		if(n.getChildren().size()>0)
			inOrderElementAt(n.getChildren().elementAt(0), index);		

		if(index == currentIndex) currentNode = n;
		currentIndex++;
		if(n.getChildren().size()>1)
			inOrderElementAt(n.getChildren().elementAt(1), index);
	}
	private void preOrderElementAt(TreeNode n, int index){

		if(index == currentIndex) currentNode = n;
		currentIndex++;
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


		if(index == currentIndex) currentNode = n;
		currentIndex++;
	}
	public class TreeNode{
		private TreeNode parent;		
		private Vector<TreeNode> children;
		private Object value;
		private int currentIndex;
		private boolean added;
		private boolean removed;
		public boolean isAdded() {
			return added;
		}
		public void setAdded(boolean added) {
			this.added = added;
		}
		public int getCurrentIndex() {
			return currentIndex;
		}
		public void setCurrentIndex(int currentIndex) {
			this.currentIndex = currentIndex;
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