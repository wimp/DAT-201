package sim.editor;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JPanel;

import sim.functions.*;
import sim.functions.MoveChar.Direction;
import sim.gui.elements.GuiElement;
import sim.structures.*;

/**
 * The listener and action handling class for the editor gui. Handles everything that is going on in the editor.
 *
 */
public class EditorListener implements ActionListener, MouseMotionListener, MouseListener {
// Class variables //
	ElementType type;
	EditorGui gui;
	Vector<Object> elements = new Vector<Object>();
	Vector<GuiElement> guiElements = new Vector<GuiElement>();
	private Object startElement;
	private Object endElement;
	private GuiElement startGuiElement;
	private GlassPanel panel = new GlassPanel();
	private Vector<Point> links = new Vector<Point>();
	private Vector<Link> linkys = new Vector<Link>();
	private Link link;

// Class Methods //
	public EditorListener(EditorGui gui){
		this.gui = gui;
	}
	
	public void addElementAtPosition(JComponent element){
		gui.editorPanel.add(element);
	}
	
	private JComponent getComponentFromEnum(ElementType type, Rectangle bounds) {
		int index;
		switch(type){
		case ADD:
			bounds.width 	= bounds.width < 70 ? 70 : bounds.width;
			bounds.height 	= bounds.height < 30 ? 30 : bounds.height;
			Add addElement = new Add(bounds);
			elements.add(addElement);
			index = elements.lastIndexOf(addElement);
			guiElements.add(index,addElement.getGuiElement());
			return addElement.getGuiElement();
		case ARRAY:
			bounds.width 	= bounds.width < 70 ? 70 : bounds.width;
			bounds.height 	= bounds.height < 150 ? 150 : bounds.height;
			Array arrayElement = new Array(bounds,8);
			elements.add(arrayElement);
			index = elements.lastIndexOf(arrayElement);
			guiElements.add(index,arrayElement.getGuiElement());
			return arrayElement.getGuiElement();
		case INSERT:
			bounds.width 	= bounds.width < 150 ? 150 : bounds.width;
			bounds.height 	= bounds.height < 30 ? 30 : bounds.height;
			Insert insertElement = new Insert(bounds, false);
			elements.add(insertElement);
			index = elements.lastIndexOf(insertElement);
			guiElements.add(index,insertElement.getGuiElement());
			return insertElement.getGuiElement();
		case LIST:
			bounds.width 	= bounds.width < 200 ? 200 : bounds.width;
			bounds.height 	= bounds.height < 75 ? 75 : bounds.height;
			LinkedList listElement = new LinkedList(bounds);
			elements.add(listElement);
			index = elements.lastIndexOf(listElement);
			guiElements.add(index,listElement.getGuiElement());
			return listElement.getGuiElement();
		case POP:
			bounds.width 	= bounds.width < 70 ? 70 : bounds.width;
			bounds.height 	= bounds.height < 30 ? 30 : bounds.height;
			Pop popElement = new Pop(bounds);
			elements.add(popElement);
			index = elements.lastIndexOf(popElement);
			guiElements.add(index,popElement.getGuiElement());
			return popElement.getGuiElement();
		case PUSH:
			bounds.width 	= bounds.width < 70 ? 70 : bounds.width;
			bounds.height 	= bounds.height < 30 ? 30 : bounds.height;
			Push pushElement = new Push(bounds);
			elements.add(pushElement);
			index = elements.lastIndexOf(pushElement);
			guiElements.add(pushElement.getGuiElement());
			return pushElement.getGuiElement();
		case REMOVE:
			bounds.width 	= bounds.width < 100 ? 100 : bounds.width;
			bounds.height 	= bounds.height < 30 ? 30 : bounds.height;
			Remove removeElement = new Remove(bounds);
			elements.add(removeElement);
			index = elements.lastIndexOf(removeElement);
			guiElements.add(index,removeElement.getGuiElement());
			return removeElement.getGuiElement();
		case STACK:
			bounds.width 	= bounds.width < 70 ? 70 : bounds.width;
			bounds.height 	= bounds.height < 150 ? 150 : bounds.height;
			Stack stackElement = new Stack(bounds);
			elements.add(stackElement);
			index = elements.lastIndexOf(stackElement);
			guiElements.add(index,stackElement.getGuiElement());
			return stackElement.getGuiElement();
		case VARIABLE:
			bounds.width 	= bounds.width < 50 ? 50 : bounds.width;
			bounds.height 	= bounds.height < 30 ? 30 : bounds.height;
			Variable variableElement = new Variable(bounds,"test",true);
			elements.add(variableElement);
			index = elements.lastIndexOf(variableElement);
			guiElements.add(index,variableElement.getGuiElement());
			return variableElement.getGuiElement();
		case MOVECHAR:
			bounds.width	= bounds.width < 70 ? 70 : bounds.width;
			bounds.height	= bounds.height < 30 ? 30: bounds.height;
			MoveChar moveCharElement = new MoveChar(bounds,Direction.LEFT);
			elements.add(moveCharElement);
			index = elements.lastIndexOf(moveCharElement);
			guiElements.add(index,moveCharElement.getGuiElement());
			return moveCharElement.getGuiElement();
		case TREE:
			bounds.width	= bounds.width < 500 ? 500 : bounds.width;
			bounds.height	= bounds.height < 250 ? 250 : bounds.height;
			Tree treeElement = new Tree(bounds,false);
			elements.add(treeElement);
			index = elements.lastIndexOf(treeElement);
			guiElements.add(index,treeElement.getGuiElement());
			return treeElement.getGuiElement();
		}
		return null;
	}
	
	private boolean checkCompatability(Object element1, Object element2){
		
		if(element1 instanceof Add){
			if(element2 instanceof Variable){
				return true;
			}else if(element2 instanceof LinkedList){
				return true;
			}else if(element2 instanceof Tree){
				return true;
			}else{
				return false;
			}
		}else if(element1 instanceof Variable){
			if(element2 instanceof Add){
				return true;
			}else if(element2 instanceof Remove){
				return true;
			}else if(element2 instanceof Insert){
				return true;
			}else if(element2 instanceof Push){
				return true;
			}else if(element2 instanceof Pop){
				return true;
			}else if(element2 instanceof MoveChar){
				return true;
			}else{
				return false;
			}
		}else if(element1 instanceof Stack){
			if(element2 instanceof Push){
				return true;
			}else if(element2 instanceof Pop){
				return true;
			}else{
				return false;
			}
		}else if(element1 instanceof Push){
			if(element2 instanceof Stack){
				return true;
			}else if(element2 instanceof Variable){
				return true;
			}
		}else if(element1 instanceof Pop){
			if(element2 instanceof Stack){
				return true;
			}else if(element2 instanceof Variable){
				return true;
			}else{
				return false;
			}
		}else if(element1 instanceof LinkedList){
			if(element2 instanceof Remove){
				return true;
			}else if(element2 instanceof Insert){
				return true;
			}else if(element2 instanceof Add){
				return true;
			}else{
				return false;
			}
		}else if(element1 instanceof Remove){
			if(element2 instanceof Variable){
				return true;
			}else if(element2 instanceof Array){
				return true;
			}else if(element2 instanceof LinkedList){
				return true;
			}else if(element2 instanceof Tree){
				return true;
			}else{
				return false;
			}
		}else if(element1 instanceof Array){
			if(element2 instanceof Add){
				return true;
			}else if(element2 instanceof Remove){
				return true;
			}else if(element2 instanceof Insert){
				return true;
			}else{
				return false;
			}
		}else if(element1 instanceof MoveChar){
			if(element2 instanceof Variable){
				return true;
			}else{
				return false;
			}
		}else if(element1 instanceof Tree){
			if(element2 instanceof Insert){
				return true;
			}else if(element2 instanceof Add){
				return true;
			}else{
				return false;
			}
		}else if(element1 instanceof Insert){
			if(element2 instanceof Tree){
				return true;
			}else if(element2 instanceof LinkedList){
				return true;
			}else if(element2 instanceof Variable){
				return true;
			}else if(element2 instanceof Array){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
	
	private void addLinks(GuiElement element1, GuiElement element2){
		int el1MiddleY 			= element1.getY() + (element1.getHeight() / 2);
		int el2MiddleY 			= element2.getY() + (element2.getHeight() / 2);
		int middleOfElementsX 	= element2.getX() - ((element2.getX() - (element1.getX() + element1.getWidth())) / 2);
		int middleOfElementsY	= element2.getY() - ((element2.getY() - (element1.getY() + element1.getHeight())) / 2);
		int middleOfElementsY2	= element1.getY() - ((element1.getY() - (element2.getY() + element2.getHeight())) / 2);
		
		if(element1.getX()+element1.getWidth() < element2.getX()){
			Point p1 = new Point(element1.getX()+element1.getWidth(),el1MiddleY);
			Point p2 = new Point(middleOfElementsX,p1.y);
			Point p3 = new Point(p2.x,el2MiddleY);
			Point p4 = new Point(element2.getX(),p3.y);
			
			links.add(p1);
			links.add(p2);
			links.add(p3);
			links.add(p4);
		}else if(element1.getX() > element2.getX()+element2.getWidth()){
			Point p1 = new Point(element2.getX()+element2.getWidth(), el2MiddleY);
			Point p2 = new Point(middleOfElementsX,p1.y);
			Point p3 = new Point(p2.x,el1MiddleY);
			Point p4 = new Point(element1.getX(),p3.y);
			
			links.add(p1);
			links.add(p2);
			links.add(p3);
			links.add(p4);
		}else if((element1.getX() <= element2.getX() || element1.getX()+element1.getWidth() >= element2.getX()+element2.getWidth()) || (element2.getX() <= element1.getX() || element2.getX()+element2.getWidth() >= element1.getX()+element1.getWidth())){
			if(element1.getY()+element1.getHeight() < element2.getY()){
				Point p1 = new Point(element1.getX() + (element1.getWidth() / 2),element1.getY()+element1.getHeight());
				Point p2 = new Point(p1.x,middleOfElementsY);
				Point p3 = new Point(element2.getX() + (element2.getWidth() / 2),p2.y);
				Point p4 = new Point(p3.x,element2.getY());
				
				links.add(p1);
				links.add(p2);
				links.add(p3);
				links.add(p4);
			}else if(element2.getY()+element2.getHeight() < element1.getY()){
				Point p1 = new Point(element2.getX() + (element2.getWidth() / 2),element2.getY()+element2.getHeight());
				Point p2 = new Point(p1.x,middleOfElementsY2);
				Point p3 = new Point(element1.getX() + (element1.getWidth() / 2),p2.y);
				Point p4 = new Point(p3.x,element1.getY());
				
				links.add(p1);
				links.add(p2);
				links.add(p3);
				links.add(p4);
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		int elementIndex = -1;
		
		if(type == ElementType.SELECT) return;
		if(type == ElementType.DELETE){
			for(int i=0;i<guiElements.size();i++){
				if(guiElements.get(i).getBounds().contains(x, y)){
					elementIndex = i;
					
					break;
				}
			}
			
			if(elementIndex != -1){
				gui.editorPanel.remove(guiElements.get(elementIndex));
				guiElements.remove(elementIndex);
				elements.remove(elementIndex);
				panel.r = null;
				gui.editorPanel.validate();
			}
			return;
		}
		if(type != ElementType.LINK){
			JComponent c = getComponentFromEnum(type, new Rectangle(e.getX(),e.getY(),10,10));
			for(Component co : c.getComponents()){
				co.addMouseListener(this);
			}
			addElementAtPosition(c);
		}
		gui.validate();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		if(type == ElementType.LINK){
			for(int i=0;i<guiElements.size();i++){
				if(guiElements.get(i).getBounds().contains(x, y)){
					link = new Link();
					link.from = elements.get(i);
					link.fromGui = guiElements.get(i);
					
					startElement = elements.get(i);
					startGuiElement = guiElements.get(i);
					break;
				}
			}
			panel.p1 = new Point(e.getX(),e.getY());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		panel.p1 = null;
		panel.p2 = null;
		
		if(type == ElementType.LINK){
			for(int i=0;i<guiElements.size();i++){
				if(guiElements.get(i).getBounds().contains(x, y)){
					endElement = elements.get(i);
					
					if(checkCompatability(startElement,endElement)){
						addLinks(startGuiElement, guiElements.get(i));
						link.to = endElement;
						link.toGui = guiElements.get(i);
						link.getDirection();
						linkys.add(link);
						link = null;
					}
					
					if(startElement instanceof Add){
						if(endElement instanceof Array){
							((Add) startElement).setTarget(endElement);
						}else if(endElement instanceof Variable){
							((Add) startElement).setSourceVariable((Variable) endElement);
						}else if(endElement instanceof LinkedList){
							((Add) startElement).setTarget(endElement);
						}else if(endElement instanceof Tree){
							((Add) startElement).setTarget(endElement);
						}
					}else if(startElement instanceof Variable){
						if(endElement instanceof Add){
							((Add) endElement).setSourceVariable((Variable) startElement);
						}else if(endElement instanceof Remove){
							((Remove) endElement).setIndexVariable((Variable) startElement);
						}else if(endElement instanceof Insert){
							((Insert) endElement).setIndexVariable((Variable) startElement);
						}else if(endElement instanceof Push){
							((Push) endElement).setSourceVariable((Variable) startElement);
						}else if(endElement instanceof Pop){
							((Pop) endElement).setSourceVariable((Variable) startElement);
						}else if(endElement instanceof MoveChar){
							((MoveChar) endElement).setInput((Variable) startElement);
						}
					}else if(startElement instanceof Stack){
						if(endElement instanceof Push){
							((Push) endElement).setTarget(startElement);
						}else if(endElement instanceof Pop){
							((Pop) endElement).setTarget(startElement);
						}
					}else if(startElement instanceof Push){
						if(endElement instanceof Stack){
							((Push) startElement).setTarget(endElement);
						}else if(endElement instanceof Variable){
							((Push) startElement).setSourceVariable((Variable) endElement);
						}
					}else if(startElement instanceof Pop){
						if(endElement instanceof Stack){
							((Pop) startElement).setTarget(endElement);
						}else if(endElement instanceof Variable){
							((Pop) startElement).setSourceVariable((Variable) endElement);
						}
					}else if(startElement instanceof LinkedList){
						if(endElement instanceof Remove){
							((Remove) endElement).setTarget(startElement);
						}else if(endElement instanceof Insert){
							((Insert) endElement).setTarget(startElement);
						}else if(endElement instanceof Add){
							((Add) endElement).setTarget(startElement);
						}
					}else if(startElement instanceof Remove){
						if(endElement instanceof Variable){
							((Remove) startElement).setSourceVariable((Variable) endElement);
						}else if(endElement instanceof Array){
							((Remove) startElement).setTarget(endElement);
						}else if(endElement instanceof LinkedList){
							((Remove) startElement).setTarget(endElement);
						}else if(endElement instanceof Tree){
							((Remove) startElement).setTarget(endElement);
						}
					}else if(startElement instanceof Array){
						if(endElement instanceof Add){
							((Add) endElement).setTarget(startElement);
						}else if(endElement instanceof Remove){
							((Remove) endElement).setTarget(startElement);
						}else if(endElement instanceof Insert){
							((Insert) endElement).setTarget(startElement);
						}
					}else if(startElement instanceof MoveChar){
						if(endElement instanceof Variable){
							((MoveChar) startElement).setOutput((Variable) endElement);
						}
					}else if(startElement instanceof Tree){
						if(endElement instanceof Insert){
							((Insert) endElement).setTarget(startElement);
						}else if(endElement instanceof Add){
							((Add) endElement).setTarget(startElement);
						}
					}else if(startElement instanceof Insert){
						if(endElement instanceof Tree){
							((Insert) startElement).setTarget(endElement);
						}else if(endElement instanceof LinkedList){
							((Insert) startElement).setTarget(endElement);
						}else if(endElement instanceof Variable){
							((Insert) startElement).setSourceVariable((Variable) endElement);
						}else if(endElement instanceof Array){
							((Insert) startElement).setTarget(endElement);
						}
					}
					break;
				}
			}
		}
		panel.repaint();
		
		startElement = null;
		endElement	 = null;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		if(type == ElementType.LINK){
			for(int i=0;i<guiElements.size();i++){
				if(guiElements.get(i).getBounds().contains(x, y)){
					panel.c = checkCompatability(startElement, elements.get(i)) ? Color.green : Color.red;
					panel.r = new Rectangle(guiElements.get(i).getBounds());
					break;
				}else{
					panel.r = null;
					panel.c = Color.green;
				}
			}
			panel.p2 = new Point(e.getX(),e.getY());
			panel.repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		if(type == ElementType.LINK){
			for(int i=0;i<guiElements.size();i++){
				if(guiElements.get(i).getBounds().contains(x, y)){
					panel.r = new Rectangle(guiElements.get(i).getBounds());
					if(startElement != null)
						panel.c = checkCompatability(startElement, elements.get(i)) ? Color.GREEN : Color.RED;
					panel.repaint();
					break;
				}else{
					panel.r = null;
					panel.c = Color.GREEN;
					panel.repaint();
				}
			}
		}else if(type == ElementType.DELETE){
			for(int i=0;i<guiElements.size();i++){
				if(guiElements.get(i).getBounds().contains(x,y)){
					panel.r = new Rectangle(guiElements.get(i).getBounds());
					panel.repaint();
					break;
				}else{
					panel.r = null;
					panel.c = Color.green;
					panel.repaint();
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gui.editorPanel.remove(panel);
		panel.removeMouseListener(this);
		panel.removeMouseMotionListener(this);
		gui.validate();
		switch(Integer.parseInt(e.getActionCommand())){
		case 1:
			type = ElementType.STACK;
			break;
		case 2:
			type = ElementType.ARRAY;
			break;
		case 3:
			type = ElementType.LIST;
			break;
		case 4:
			type = ElementType.ADD;
			break;
		case 5:
			type = ElementType.REMOVE;
			break;
		case 6:
			type = ElementType.INSERT;
			break;
		case 7:
			type = ElementType.PUSH;
			break;
		case 8:
			type = ElementType.POP;
			break;
		case 9:
			type = ElementType.VARIABLE;
			break;
		case 10:
			type = ElementType.LINK;
			
			panel.setOpaque(false);
			panel.setSize(gui.editorPanel.getSize());
			panel.addMouseListener(this);
			panel.addMouseMotionListener(this);
			
			gui.editorPanel.add(panel);
			gui.editorPanel.setComponentZOrder(panel, 0);
			gui.editorPanel.validate();
			break;
		case 11:
			type = ElementType.SELECT;
			break;
		case 12:
			type = ElementType.MOVECHAR;
			break;
		case 13:
			type = ElementType.DELETE;
			
			panel.setOpaque(false);
			panel.setSize(gui.editorPanel.getSize());
			panel.addMouseListener(this);
			panel.addMouseMotionListener(this);
			
			gui.editorPanel.add(panel);
			gui.editorPanel.setComponentZOrder(panel, 0);
			gui.editorPanel.validate();
			break;
		case 14:
			type = ElementType.TREE;
			break;
		}
		gui.editorPanel.validate();
	}
	
	@SuppressWarnings("serial")
	private class GlassPanel extends JPanel{
		protected Rectangle r;
		protected Point p1, p2;
		protected Color c = Color.green;
		AlphaComposite a = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
		
		@Override
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			
			if(r != null){
				g2d.setColor(Color.yellow);
				g2d.drawRect(r.x-2, r.y-2, r.width+3, r.height+3);
				Composite temp = g2d.getComposite();
				g2d.setComposite(a);
				g2d.setColor(c);
				g2d.fillRect(r.x, r.y, r.width, r.height);
				g2d.setComposite(temp);
			}
			
			if(p1 != null && p2 != null){
				g2d.setColor(c);
				g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
			}
			
			if(links.size() >=4){
				g2d.setColor(Color.black);
				/*for(int i = 0;i < links.size();i+=4){
					if(links.size() >= i+3){
						g2d.drawLine(links.get(i).x, links.get(i).y, links.get(i+1).x, links.get(i+1).y);
						g2d.drawLine(links.get(i+1).x, links.get(i+1).y, links.get(i+2).x, links.get(i+2).y);
						g2d.drawLine(links.get(i+2).x, links.get(i+2).y, links.get(i+3).x, links.get(i+3).y);
						
						g2d.fillOval(links.get(i).x-4, links.get(i).y-4, 8, 8);
						g2d.fillOval(links.get(i+3).x-4, links.get(i+3).y-4, 8, 8);
					}
				}*/
				
				for(int i = 0;i<linkys.size();i++){
					Link l = linkys.get(i);
					g2d.drawLine(l.p1.x, l.p1.y, l.p2.x, l.p2.y);
					g2d.drawLine(l.p2.x, l.p2.y, l.p3.x, l.p3.y);
					g2d.drawLine(l.p3.x, l.p3.y, l.p4.x, l.p4.y);
					
					switch(l.direction){
					case LEFT:
						break;
					case RIGHT:
						break;
					case DOWN:
						break;
					case UP:
						break;
					case LEFT_RIGHT:
						break;
					case UP_DOWN:
						break;
					}
				}
				g2d.setColor(c);
			}
		}
	}
	
	class Link{
		protected GuiElement 	fromGui;
		protected GuiElement 	toGui;
		protected Object	 	from;
		protected Object	 	to;
		protected LinkDirection direction;
		protected Point			p1;
		protected Point			p2;
		protected Point			p3;
		protected Point			p4;
		
		void getDirection(){
			int fromX 		= fromGui.getX();
			int fromY 		= fromGui.getY();
			int fromWidth 	= fromGui.getWidth();
			int fromHeight 	= fromGui.getHeight();
			
			int toX			= toGui.getX();
			int toY			= toGui.getY();
			int toWidth 	= toGui.getWidth();
			int toHeight 	= toGui.getHeight();
			
			if(fromX+fromWidth < toX){ // 1st element is to the left of 2nd element
				int middleOfElements = toX - ((toX - (fromX + fromWidth)) / 2);
				p1 = new Point(fromX+fromWidth,fromY + (fromHeight / 2));
				p2 = new Point(middleOfElements,p1.y);
				p3 = new Point(middleOfElements,toY + (toHeight / 2));
				p4 = new Point(toX,p3.y);
				
				if(checkCompatability(from, to))
					direction = checkCompatability(to, from) ? LinkDirection.LEFT_RIGHT : LinkDirection.RIGHT;
			}else if(fromX > toX+toWidth){ // 1st element is to the right of 2nd element
				int middleOfElements = fromX - ((fromX - (toX + toWidth)) / 2);
				p1 = new Point(toX+toWidth,toY + (toHeight / 2));
				p2 = new Point(middleOfElements,p1.y);
				p3 = new Point(middleOfElements,fromY + (fromHeight / 2));
				p4 = new Point(fromX,p3.y);
				
				if(checkCompatability(from, to))
					direction = checkCompatability(to, from) ? LinkDirection.LEFT_RIGHT : LinkDirection.LEFT;
			}else{ // The elements are above or below each other
				if(fromY+fromHeight < toY){ // 1st element is above 2nd element
					int middleOfElements = toY - ((toY - (fromY + fromHeight)) / 2);
					p1 = new Point(fromX + (fromWidth / 2),fromY+fromHeight);
					p2 = new Point(p1.x,middleOfElements);
					p3 = new Point(toX + (toWidth / 2), middleOfElements);
					p4 = new Point(p3.x,toY);
					
					if(checkCompatability(from, to))
						direction = checkCompatability(to, from) ? LinkDirection.UP_DOWN : LinkDirection.DOWN;
				}else if(fromY > toY+toHeight){ // 1st element is below 2nd element
					int middleOfElements = fromY - ((fromY - (toY + toHeight)) / 2);
					p1 = new Point(toX + (toWidth / 2), toY+toHeight);
					p2 = new Point(p1.x,middleOfElements);
					p3 = new Point(fromX + (fromWidth / 2), middleOfElements);
					p4 = new Point(p3.x,fromY);
					
					if(checkCompatability(from, to))
						direction = checkCompatability(to, from) ? LinkDirection.UP_DOWN : LinkDirection.UP;
				}
			}
		}
	}
	
	public enum ElementType{
		STACK,ARRAY,LIST,ADD,REMOVE,INSERT,PUSH,POP,VARIABLE,LINK,SELECT,MOVECHAR,DELETE,TREE
	}
	
	private enum LinkDirection{
		UP,DOWN,LEFT,RIGHT,LEFT_RIGHT,UP_DOWN
	}
}