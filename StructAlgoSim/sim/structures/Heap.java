package sim.structures;

import java.awt.Rectangle;
import java.util.Vector;

import sim.gui.elements.GuiTree;
/**
 * A class representing an heap. Its visual component is the GuiTree.
 */
public class Heap extends Tree{

	public enum CompareKey{
		ALPHABETICAL,
		NUMERICAL,
		STRLEN
	}
	private CompareKey key = CompareKey.NUMERICAL;

	private boolean max = true;
	public boolean isMax() {
		return max;
	}
	public void setMax(boolean max) {
		this.max = max;
	}
	public CompareKey getSortKey() {
		return key;
	}
	public void setSortKey(CompareKey key) {
		this.key = key;
	}
	/**
	 * Constructor.
	 * @param bounds The size of the graphical element.
	 * @param animated Determines whether insertion and deletion will be animated.
	 */
	public Heap(Rectangle bounds, boolean animated) {
		super();
		setMaxCluster(2);
		setGuiElement(new GuiTree(bounds, this, animated));
	}
	public String removeRoot(){
		if(getRoot() != null){
			String value = getRoot().getValue().toString();

			Vector<String> values = new Vector<String>();
			for(TreeNode t : getAllNodes(new Vector<TreeNode>() , getRoot())){
				if(t!=null && t!=getRoot())
					values.add(t.getValue().toString());
			}
			setRoot(null);
			rebuildTree();

			return value;
		}
		return null;
	}
	@Override 
	public String removeAt(int index){
		String n = super.removeAt(index);
		if(max) maxHeapifyTree(getRoot());
		else minHeapifyTree(getRoot());
		return n;
	}
	/**
	 * Calls reArrangeHeap to make a MinHeap.
	 * @param t The root node of the tree to be heapified.
	 */
	public void minHeapifyTree(TreeNode t){
		if(t == null) return;
		reArrangeHeap(t,false);
		for(TreeNode n : t.getChildren())
			minHeapifyTree(n);
	}
	/**
	 * Calls reArrangeHeap to make a MaxHeap.
	 * @param t The root node of the tree to be heapified.
	 */
	public void maxHeapifyTree(TreeNode t){
		if(t == null) return;
		reArrangeHeap(t, true);
		for(TreeNode n : t.getChildren()){
			maxHeapifyTree(n);
		}
	}

	/**
	 * Swaps nodes until the specified conditions are satisfied, 
	 * this results in either a max-heap or a min-heap sorted by
	 * the CompareKey class variable key.
	 * 
	 * @param n The root node of the tree to be heapified.
	 * @param max True will result in a max-heap, false in a min-heap.
	 */
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
