package sim.editor;

import java.awt.Component;
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
import sim.gui.elements.GuiElement;
import sim.structures.*;

public class EditorListener implements ActionListener, MouseMotionListener, MouseListener {
	ElementType type;
	EditorGui gui;
	Vector<Object> elements = new Vector<Object>();
	Vector<GuiElement> guiElements = new Vector<GuiElement>();
	private Object startElement;
	private Object endElement;
	private JPanel panel = new JPanel();
	
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
			Add addElement = new Add(bounds,null,null);
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
			Insert insertElement = new Insert(bounds,null,null,null);
			elements.add(insertElement);
			index = elements.lastIndexOf(insertElement);
			guiElements.add(index,insertElement.getGuiElement());
			return insertElement.getGuiElement();
		case LIST:
			bounds.width 	= bounds.width < 200 ? 200 : bounds.width;
			bounds.height 	= bounds.height < 75 ? 75 : bounds.height;
			LinkedList listElement = new LinkedList(bounds,true,true);
			elements.add(listElement);
			index = elements.lastIndexOf(listElement);
			guiElements.add(index,listElement.getGuiElement());
			return listElement.getGuiElement();
		case POP:
			bounds.width 	= bounds.width < 70 ? 70 : bounds.width;
			bounds.height 	= bounds.height < 30 ? 30 : bounds.height;
			Pop popElement = new Pop(bounds,null,null);
			elements.add(popElement);
			index = elements.lastIndexOf(popElement);
			guiElements.add(index,popElement.getGuiElement());
			return popElement.getGuiElement();
		case PUSH:
			bounds.width 	= bounds.width < 70 ? 70 : bounds.width;
			bounds.height 	= bounds.height < 30 ? 30 : bounds.height;
			Push pushElement = new Push(bounds,null,null);
			elements.add(pushElement);
			index = elements.lastIndexOf(pushElement);
			guiElements.add(pushElement.getGuiElement());
			return pushElement.getGuiElement();
		case REMOVE:
			bounds.width 	= bounds.width < 100 ? 100 : bounds.width;
			bounds.height 	= bounds.height < 30 ? 30 : bounds.height;
			Remove removeElement = new Remove(bounds, null, null, null);
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
		}
		return null;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(type == ElementType.SELECT) return;
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
					startElement = elements.get(i);
					break;
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		if(type == ElementType.LINK){
			for(int i=0;i<guiElements.size();i++){
				if(guiElements.get(i).getBounds().contains(x, y)){
					endElement = elements.get(i);
					if(startElement instanceof Add){
						if(endElement instanceof Array){
							((Add) startElement).setL(endElement);
						}else if(endElement instanceof Variable){
							((Add) startElement).setV((Variable) endElement);
						}else if(endElement instanceof LinkedList){
							((Add) startElement).setL(endElement);
						}
					}else if(startElement instanceof Variable){
						if(endElement instanceof Add){
							((Add) endElement).setV((Variable) startElement);
						}else if(endElement instanceof Remove){
							((Remove) endElement).setI((Variable) startElement);
						}
					}
					break;
				}
			}
		}
		
		startElement = null;
		endElement	 = null;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gui.editorPanel.remove(panel);
		panel.removeMouseListener(this);
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
			
			gui.editorPanel.add(panel);
			gui.editorPanel.setComponentZOrder(panel, 0);
			gui.editorPanel.validate();
			break;
		case 11:
			type = ElementType.SELECT;
			break;
		}
		gui.editorPanel.validate();
	}
	
	public enum ElementType{
		STACK,ARRAY,LIST,ADD,REMOVE,INSERT,PUSH,POP,VARIABLE,LINK,SELECT
	}
}
