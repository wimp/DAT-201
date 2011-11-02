package sim.gui.elements;

import java.awt.Color;

public final class GuiSettings {
	
	//Global
	public static boolean isAnimated = true;
	//LinkedList
	public static final Color LISTNODECOLOR 		= Color.CYAN;
	public static final Color LISTADDEDCOLOR 		= Color.GREEN;
	public static final Color LISTREMOVEDCOLOR 		= Color.RED;
	public static final Color LISTHEADCOLOR 		= Color.YELLOW;
	public static final Color LISTLASTCOLOR 		= Color.MAGENTA;
	public static final int LISTNODEWIDTH 			= 30;
	public static final int LISTNODEHEIGHT 			= 30;	
	//Tree
	public static final Color TREENODECOLOR 		= LISTNODECOLOR;
	public static final Color TREEADDEDCOLOR 		= LISTADDEDCOLOR;
	public static final Color TREEREMOVEDCOLOR 		= Color.LIGHT_GRAY;
	public static final Color TREEADDPATHCOLOR 		= LISTHEADCOLOR;
	public static final Color TREEREMOVEPATHCOLOR 	= LISTREMOVEDCOLOR;
	public static final Color TREEROOTCOLOR 		= LISTHEADCOLOR;
	public static final Color TREELEAFCOLOR 		= LISTLASTCOLOR;
	public static final Color TREECONTENTCOLOR 		= Color.LIGHT_GRAY;
	public static final int TREENODEWIDTH 			= LISTNODEWIDTH;
	public static final int TREENODEHEIGHT 			= LISTNODEHEIGHT;
	
	//Array
	public static final Color ARRAYELEMENTCOLOR 	= LISTHEADCOLOR;
	public static final Color ARRAYEMPTYCOLOR 		= LISTNODECOLOR;
	public static final Color ARRAYCHANGEDCOLOR 	= LISTADDEDCOLOR;
	public static final int ARRAYELEMENTHEIGHT 		= LISTNODEHEIGHT;
	
	//Stack
	public static final Color STACKTOPCOLOR 		= LISTHEADCOLOR;
	public static final Color STACKADDEDCOLOR 		= LISTADDEDCOLOR;
	public static final Color STACKELEMENTCOLOR 	= LISTNODECOLOR;
	public static final int STACKELEMENTWIDTH 		= 50;
	public static final int STACKELEMENTHEIGHT 		= LISTNODEHEIGHT;
	
	//Queue
	public static final Color QUEUETOPCOLOR = Color.GREEN;
	public static final Color QUEUEENDCOLOR = Color.RED;
	public static final Color QUEUEELEMENTCOLOR = Color.YELLOW;
	public static final int QUEUEELEMENTWIDTH = STACKELEMENTHEIGHT;
	public static final int QUEUEELEMENTHEIGHT = STACKELEMENTWIDTH;
	
}
