package sim.structures;


import sim.gui.elements.GuiElement;
import sim.gui.elements.GuiQueue;
import sim.structures.elements.Node;

public class Queue {
	Node start;
	Node end;
	GuiQueue gui;
	public GuiElement getGuiElement(){
		return gui;
	}
	public Queue(){
		start = new Node("foran");
		end = new Node("bak");
		gui = new GuiQueue(start, end);
	}
	
	public void add(Object o){
		Node n = new Node(o);
		n.setPrevious(end);
		end.getNext().setPrevious(n);
		end.setNext(n);
	}
	public Object remove(){
		if(start.getPrevious()!= end){
		start.getPrevious().getPrevious().setNext(start);
		Node n = start.getPrevious();
		start.setPrevious(start.getPrevious().getPrevious());
		return n.getValue().toString();
		}
		else return null;
	}
	
}
