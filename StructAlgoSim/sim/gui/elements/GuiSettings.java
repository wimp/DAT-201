package sim.gui.elements;

import java.awt.Color;

public final class GuiSettings {
	
	//LinkedList
	static final Color LISTNODECOLOR = Color.CYAN;
	static final Color LISTADDEDCOLOR = Color.GREEN;
	static final Color LISTREMOVEDCOLOR = Color.GREEN;
	static final Color LISTHEADCOLOR = Color.YELLOW;
	static final Color LISTLASTCOLOR = Color.MAGENTA;
	static final int LISTNODEWIDTH = 20;
	static final int LISTNODEHEIGHT = 20;	
	//Tree
	static final Color TREENODECOLOR = LISTNODECOLOR;
	static final Color TREEADDEDCOLOR = LISTADDEDCOLOR;
	static final Color TREEREMOVEDCOLOR = LISTREMOVEDCOLOR;
	static final Color TREEROOTCOLOR = LISTHEADCOLOR;
	static final Color TREELEAFCOLOR = LISTLASTCOLOR;	
	static final int TREENODEWIDTH = LISTNODEWIDTH;
	static final int TREENODEHEIGHT = LISTNODEHEIGHT;
	
	//Array
	
	//Stack
	static final Color STACKTOPCOLOR = LISTHEADCOLOR;
	static final Color STACKELEMENTCOLOR = LISTNODECOLOR;
	static final int STACKELEMENTWIDTH = 50;
	static final int STACKELEMENTHEIGHT = LISTNODEHEIGHT;
	
	//Queue
	static final Color QUEUETOPCOLOR = LISTHEADCOLOR;
	static final Color QUEUEENDCOLOR = LISTHEADCOLOR;
	static final Color QUEUEELEMENTCOLOR = LISTNODECOLOR;
	static final int QUEUEELEMENTWIDTH = STACKELEMENTHEIGHT;
	static final int QUEUEELEMENTHEIGHT = STACKELEMENTWIDTH;
	
}
