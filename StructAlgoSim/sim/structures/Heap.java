package sim.structures;

import java.awt.Rectangle;
import java.util.Vector;

import sim.gui.elements.GuiTree;
import sim.structures.Tree.TreeNode;

public class Heap extends Tree{
	
	public enum CompareKey{
		ALPHABETICAL,
		NUMERICAL,
		STRLEN
	}
	private CompareKey key = CompareKey.NUMERICAL;
	
	public CompareKey getSortKey() {
		return key;
	}
	public void setSortKey(CompareKey key) {
		this.key = key;
	}
	public Heap(Rectangle bounds, boolean animated) {
		super();
		maxCluster = 2;
		gui = new GuiTree(bounds, this, animated);
		
		Vector<String> v = new Vector<String>();
		
		v.add("1");
		v.add("2");
		v.add("6");
		v.add("8");
		v.add("3");
		v.add("4");
		v.add("1");
		v.add("2");
		v.add("6");
		v.add("8");
		v.add("3");
		v.add("4");
		buildTree(v);
		
		maxHeapifyTree(root);
		for(TreeNode n: heapSort()){
			if(n!=null)
			System.out.println(n.getValue().toString());
		}
		gui.repaint();
	}
	public Vector<TreeNode> heapSort(){
		Vector<TreeNode> sorted = new Vector<TreeNode>();
		while(root.getChildren().size()>0){
			maxHeapifyTree(root);
			
			sorted.add(root);
			
			Vector<String> values = new Vector<String>();
			for(TreeNode t : getAllNodes(new Vector<TreeNode>() , root)){
				if(t!=null && t!=root)
				values.add(t.getValue().toString());
			}
			buildTree(values);
		}
		sorted.add(root);
		Vector<String> values = new Vector<String>();
		for(TreeNode n: sorted) 
			values.add(n.getValue().toString());
		
		buildTree(values);
		return sorted;
	}
	public void buildTree(Vector<String> values){
		if(values.size()==0) return;
		root = new TreeNode(values.remove(0), null);
		for(String s : values)
			addBreadthFirst(s);
	}
	public void minHeapifyTree(TreeNode t){
		reArrangeHeap(t,false);
		for(TreeNode n : t.getChildren())
			minHeapifyTree(n);
	}
	public void maxHeapifyTree(TreeNode t){
		reArrangeHeap(t, true);
		for(TreeNode n : t.getChildren()){
			maxHeapifyTree(n);
		}
	}
	private void reArrangeHeap(TreeNode n, boolean max){
		boolean swap = true;
		while(swap && n.getParent()!=null){
			TreeNode parent = n.getParent();
			String a = n.getValue().toString();
			String b = parent.getValue().toString();
			
			switch(key){
			case ALPHABETICAL:
				for(int i = 0; i<a.length(); i++){
					if(b.length()-1<i) break;
					
					if(max){
						if((int)a.charAt(i)<=(int)b.charAt(i)){
							swap = false;
						}
						else if((int)a.charAt(i)>(int)b.charAt(i)) break;
					}
					else {
						if((int)a.charAt(i)>=(int)b.charAt(i)){
							swap = false;
						}
						else if((int)a.charAt(i)<(int)b.charAt(i)) break;
					}
				}
			break;
			case NUMERICAL:
				try{
					if(!max){
						if(Integer.parseInt(a)>=Integer.parseInt(b)) swap = false;
					}
					else{ 
						if(Integer.parseInt(a)<=Integer.parseInt(b)) swap = false;
					}
				}
				catch(NumberFormatException e){
					swap = false;
				}
			break;
			case STRLEN:
				if(!max){
					if(a.length()>=b.length()) swap = false;
				}
				else{ 
					if(a.length()<=b.length()) swap = false;
				}
			break;
			}
			
			if(swap) swapNodes(n, parent);
			
			n = parent;
		}
	}
}
