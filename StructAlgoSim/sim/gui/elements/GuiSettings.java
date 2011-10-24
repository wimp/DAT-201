package sim.gui.elements;

import java.awt.Color;

public final class GuiSettings {
	
	//LinkedList
	public static final Color LISTNODECOLOR = Color.CYAN;
	public static final Color LISTADDEDCOLOR = Color.GREEN;
	public static final Color LISTREMOVEDCOLOR = Color.GREEN;
	public static final Color LISTHEADCOLOR = Color.YELLOW;
	public static final Color LISTLASTCOLOR = Color.MAGENTA;
	public static final int LISTNODEWIDTH = 30;
	public static final int LISTNODEHEIGHT = 30;	
	//Tree
	public static final Color TREENODECOLOR = LISTNODECOLOR;
	public static final Color TREEADDEDCOLOR = LISTADDEDCOLOR;
	public static final Color TREEREMOVEDCOLOR = LISTREMOVEDCOLOR;
	public static final Color TREEROOTCOLOR = LISTHEADCOLOR;
	public static final Color TREELEAFCOLOR = LISTLASTCOLOR;	
	public static final int TREENODEWIDTH = LISTNODEWIDTH;
	public static final int TREENODEHEIGHT = LISTNODEHEIGHT;
	
	//Array
	
	//Stack
	public static final Color STACKTOPCOLOR = LISTHEADCOLOR;
	public static final Color STACKELEMENTCOLOR = LISTNODECOLOR;
	public static final int STACKELEMENTWIDTH = 50;
	public static final int STACKELEMENTHEIGHT = LISTNODEHEIGHT;
	
	//Queue
	public static final Color QUEUETOPCOLOR = LISTHEADCOLOR;
	public static final Color QUEUEENDCOLOR = LISTHEADCOLOR;
	public static final Color QUEUEELEMENTCOLOR = LISTNODECOLOR;
	public static final int QUEUEELEMENTWIDTH = STACKELEMENTHEIGHT;
	public static final int QUEUEELEMENTHEIGHT = STACKELEMENTWIDTH;
	
}
