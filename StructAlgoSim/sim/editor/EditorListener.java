package sim.editor;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.KeyEventDispatcher;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import sim.editor.EditorGui.EditorPanel;
import sim.editor.EditorInfo.InfoType;
import sim.functions.*;
import sim.gui.elements.GuiElement;
import sim.structures.*;

/**
 * The listener and action handling class for the {@link EditorGui}. 
 * Handles everything that is going on in the editor and keeps track of elements that are active and the links between them.
 *
 */
public class EditorListener implements ActionListener, MouseMotionListener, MouseListener, KeyEventDispatcher {
	// Class variables //
	protected 	ElementType 		type = ElementType.NONE;
	private 	EditorGui 			gui;
	private 	Vector<Object> 		elements = new Vector<Object>();
	private 	Vector<GuiElement> 	guiElements = new Vector<GuiElement>();
	private 	Object 				startElement;
	private 	Object 				endElement;
	protected 	GlassPanel 			panel = new GlassPanel();
	protected 	Vector<Link> 		linkys = new Vector<Link>();
	private 	Link 				link;
	private Point resizeStartPoint = new Point();
	private Point resizeEndPoint = new Point();
	private Point resizeTempPoint = new Point();
	private int resizeElementIndex = -1;

	// Class Methods //
	/**
	 * Class constructor. An instance of EditorGui is required as these two classes communicate about events and graphical components
	 * @param gui
	 */
	public EditorListener(EditorGui gui){
		this.gui = gui;
	}

	/**
	 * Adds a JComponent to the active instance of EditorGui's editor panel.
	 * @param element
	 */
	private void addElementAtPosition(JComponent element){
		if(element!=null)
			gui.editorPanel.add(element);
	}

	/**
	 * A method to get the actual instance of a class from the class enum {@link ElementType}
	 * This method also adds the given type's class instance and graphical instance in the two internal class variables, {@link elements} and {@link guiElements}
	 * @param type
	 * @param bounds
	 * @return The graphical component of the type given.
	 */
	private JComponent getComponentFromEnum(ElementType type, Rectangle bounds) {
		int index;
		switch(type){
		case ADD:
			bounds.width 	= bounds.width < 80 ? 80 : bounds.width;
			bounds.height 	= bounds.height < 30 ? 30 : bounds.height;
			Add addElement = new Add(bounds);
			elements.add(addElement);
			index = elements.lastIndexOf(addElement);
			guiElements.add(index,addElement.getGuiElement());
			return addElement.getGuiElement();
		case ARRAY:
			JPanel p = new JPanel(new BorderLayout());
			p.add(new JLabel("Array dimension and size"),BorderLayout.NORTH);

			ButtonGroup bg1 = new ButtonGroup();
			JRadioButton dim1 = new JRadioButton("1");
			dim1.setActionCommand("1");
			JRadioButton dim2 = new JRadioButton("2");
			dim2.setActionCommand("2");
			bg1.add(dim1);
			bg1.add(dim2);
			JPanel dimPanel = new JPanel(new GridLayout(2,3));
			dimPanel.add(new JLabel("Dimension:"));
			dimPanel.add(dim1);
			dimPanel.add(dim2);
			dimPanel.add(new JLabel("Size"));
			JPanel tf1Panel = new JPanel();
			JTextField tf1 = new JTextField("12");
			tf1.setToolTipText("1st dimension");
			tf1Panel.add(new JLabel("Rows:"));
			tf1Panel.add(tf1);
			dimPanel.add(tf1Panel);
			JPanel tf2Panel = new JPanel();
			JTextField tf2 = new JTextField("12");
			tf2.setToolTipText("2nd dimension");
			tf2Panel.add(new JLabel("Columns:"));
			tf2Panel.add(tf2);
			dimPanel.add(tf2Panel);
			p.add(dimPanel,BorderLayout.CENTER);
			JOptionPane.showMessageDialog(gui, p);
			int y = 6;
			int x = 1;

			try{
				y = Integer.parseInt(tf1.getText());
				x = Integer.parseInt(tf2.getText());
			}catch(NumberFormatException nfe){
				y = 6;
				x = 6;
			}

			Array arrayElement;
			bounds.width 	= bounds.width < 80 ? (x < 3 ? 80 * x : 160) : bounds.width;
			bounds.height 	= bounds.height < 180 ? 180 : bounds.height;
			if(bg1.getSelection().getActionCommand().equals("2")){
				arrayElement = new Array(bounds,y,x);
			}else{
				arrayElement = new Array(bounds,y);
			}

			elements.add(arrayElement);
			index = elements.lastIndexOf(arrayElement);
			guiElements.add(index,arrayElement.getGuiElement());
			return arrayElement.getGuiElement();
		case INSERT:
			bounds.width 	= bounds.width < 150 ? 150 : bounds.width;
			bounds.height 	= bounds.height < 30 ? 30 : bounds.height;
			Insert insertElement = new Insert(bounds, true);
			elements.add(insertElement);
			index = elements.lastIndexOf(insertElement);
			guiElements.add(index,insertElement.getGuiElement());
			return insertElement.getGuiElement();
		case LIST:
			bounds.width 	= bounds.width < 290 ? 290 : bounds.width;
			bounds.height 	= bounds.height < 140 ? 140 : bounds.height;
			LinkedList listElement = new LinkedList(bounds);
			elements.add(listElement);
			index = elements.lastIndexOf(listElement);
			guiElements.add(index,listElement.getGuiElement());
			return listElement.getGuiElement();
		case POP:
			bounds.width 	= bounds.width < 80 ? 80 : bounds.width;
			bounds.height 	= bounds.height < 30 ? 30 : bounds.height;
			Pop popElement = new Pop(bounds);
			elements.add(popElement);
			index = elements.lastIndexOf(popElement);
			guiElements.add(index,popElement.getGuiElement());
			return popElement.getGuiElement();
		case PUSH:
			bounds.width 	= bounds.width < 80 ? 80 : bounds.width;
			bounds.height 	= bounds.height < 30 ? 30 : bounds.height;
			Push pushElement = new Push(bounds);
			elements.add(pushElement);
			index = elements.lastIndexOf(pushElement);
			guiElements.add(pushElement.getGuiElement());
			return pushElement.getGuiElement();
		case REMOVE:
			bounds.width 	= bounds.width < 120 ? 120 : bounds.width;
			bounds.height 	= bounds.height < 30 ? 30 : bounds.height;
			Remove removeElement = new Remove(bounds);
			elements.add(removeElement);
			index = elements.lastIndexOf(removeElement);
			guiElements.add(index,removeElement.getGuiElement());
			return removeElement.getGuiElement();
		case STACK:
			bounds.width 	= bounds.width < 80 ? 80 : bounds.width;
			bounds.height 	= bounds.height < 150 ? 150 : bounds.height;
			Stack stackElement = new Stack(bounds);
			elements.add(stackElement);
			index = elements.lastIndexOf(stackElement);
			guiElements.add(index,stackElement.getGuiElement());
			return stackElement.getGuiElement();
		case VARIABLE:
			//			e = b.getActionCommand().equals("1") ? true : false;
			bounds.width 	= bounds.width < 80 ? 80 : bounds.width;
			bounds.height 	= bounds.height < 30 ? 30 : bounds.height;
			Variable variableElement = new Variable(bounds,"",true);
			elements.add(variableElement);
			index = elements.lastIndexOf(variableElement);
			guiElements.add(index,variableElement.getGuiElement());
			return variableElement.getGuiElement();
		case TREE:
			bounds.width	= bounds.width < 500 ? 500 : bounds.width;
			bounds.height	= bounds.height < 250 ? 250 : bounds.height;
			Tree treeElement = new Tree(bounds,false);
			elements.add(treeElement);
			index = elements.lastIndexOf(treeElement);
			guiElements.add(index,treeElement.getGuiElement());
			return treeElement.getGuiElement();
		case HEAP:
			bounds.width	= bounds.width < 400 ? 400 : bounds.width;
			bounds.height	= bounds.height < 400 ? 400 : bounds.height;
			Heap heapElement = new Heap(bounds,true);
			elements.add(heapElement);
			index = elements.lastIndexOf(heapElement);
			guiElements.add(index,heapElement.getGuiElement());
			return heapElement.getGuiElement();
		case QUEUE:
			bounds.width	= bounds.width < 350 ? 350 : bounds.width;
			bounds.height	= bounds.height < 90 ? 90 : bounds.height;
			Queue queueElement = new Queue(bounds);
			elements.add(queueElement);
			index = elements.lastIndexOf(queueElement);
			guiElements.add(index,queueElement.getGuiElement());
			return queueElement.getGuiElement();
		case SET:
			bounds.width	= bounds.width < 120 ? 120 : bounds.width;
			bounds.height	= bounds.height < 30 ? 30 : bounds.height;
			Set setElement 	= new Set(bounds,false);
			elements.add(setElement);
			index 			= elements.lastIndexOf(setElement);
			guiElements.add(index,setElement.getGuiElement());
			return setElement.getGuiElement();
		case GET:
			bounds.width	= bounds.width < 120 ? 120 : bounds.width;
			bounds.height	= bounds.height < 30 ? 30 : bounds.height;
			Get getElement 	= new Get(bounds, false);

			elements.add(getElement);
			index			= elements.lastIndexOf(getElement);
			guiElements.add(index,getElement.getGuiElement());
			return getElement.getGuiElement();
		}
		return null;
	}

	/**
	 * Clears all objects in the editor panel and removes them from {@link EditorListener}'s internal pointers.
	 */
	public void clearEditor(){
		linkys.clear();
		elements.clear();
		guiElements.clear();
		gui.editorPanel.removeAll();
		gui.repaint();
		gui.validate();
	}

	/**
	 * Checks whether element1 and element2 fits together in the editor gui. E.g. an instance of {@link Add} can't be linked with another instance of {@link Add}
	 * @param element1
	 * @param element2
	 * @return Returns true if the two items can be linked
	 */
	private boolean checkCompatibility(Object element1, Object element2){

		if(element1 instanceof Add){
			if(element2 instanceof Variable){
				return true;
			}else if(element2 instanceof LinkedList){
				return true;
			}else if(element2 instanceof Tree){
				return true;
			}else if(element2 instanceof Queue){
				return true;
			}
		}else if(element1 instanceof Get){
			if(element2 instanceof Variable){
				return true;
			}else if(element2 instanceof LinkedList){
				return true;
			}else if(element2 instanceof Tree){
				return true;
			}else if(element2 instanceof Array){
				return true;
			}
		}
		else if(element1 instanceof Set){
			if(element2 instanceof Variable){
				return true;
			}else if(element2 instanceof LinkedList){
				return true;
			}else if(element2 instanceof Tree){
				return true;
			}else if(element2 instanceof Array){
				return true;
			}
		}
		else if(element1 instanceof Variable){
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
			}else if(element2 instanceof Get){
				return true;
			}else if(element2 instanceof Set){
				return true;
			}
		}else if(element1 instanceof Stack){
			if(element2 instanceof Push){
				return true;
			}else if(element2 instanceof Pop){
				return true;
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
			}
		}else if(element1 instanceof LinkedList){
			if(element2 instanceof Remove){
				return true;
			}else if(element2 instanceof Insert){
				return true;
			}else if(element2 instanceof Get){
				return true;
			}else if(element2 instanceof Set){
				return true;
			}else if(element2 instanceof Add){
				return true;
			}
		}else if(element1 instanceof Remove){
			if(element2 instanceof Variable){
				return true;
			}else if(element2 instanceof LinkedList){
				return true;
			}else if(element2 instanceof Tree){
				return true;
			}else if(element2 instanceof Queue){
				return true;
			}
		}else if(element1 instanceof Array){
			if(element2 instanceof Get){
				return true;
			}else if(element2 instanceof Set){
				return true;
			}else if(element2 instanceof Remove){
				return true;
			}else if(element2 instanceof Insert){
				return true;
			}
		}else if(element1 instanceof Tree){
			if(element2 instanceof Get){
				return true;
			}else if(element2 instanceof Set){
				return true;
			}else if(element2 instanceof Remove){
				return true;
			}else if(element2 instanceof Insert){
				return true;
			}else if(element2 instanceof Add){
				return true;
			}
		}else if(element1 instanceof Insert){
			if(element2 instanceof Tree){
				return true;
			}else if(element2 instanceof LinkedList){
				return true;
			}else if(element2 instanceof Variable){
				return true;
			}
		}else if(element1 instanceof Queue){
			if(element2 instanceof Add){
				return true;
			}else if(element2 instanceof Remove){
				return true;
			}
		}
		return false;
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
				for(int i = 0;i < linkys.size(); i++){
					if(linkys.get(i).from.equals(elements.get(elementIndex)) || linkys.get(i).to.equals(elements.get(elementIndex))){
						linkys.remove(i);
					}
				}
				guiElements.remove(elementIndex);
				elements.remove(elementIndex);

				panel.r = null;
				gui.editorPanel.validate();
			}
			return;
		}
		if(type != ElementType.LINK){
			JComponent c = getComponentFromEnum(type, new Rectangle(e.getX(),e.getY(),10,10));
			addElementAtPosition(c);
			gui.editorPanel.repaint();
			gui.editorPanel.validate();
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
		resizeStartPoint.x = x;
		resizeStartPoint.y = y;
		resizeTempPoint.x = x;
		resizeTempPoint.y = y;
		if(type == ElementType.LINK){
			for(int i=0;i<guiElements.size();i++){
				if(guiElements.get(i).getBounds().contains(x, y)){
					link = new Link();
					link.from = elements.get(i);
					link.fromGui = guiElements.get(i);
					startElement = elements.get(i);
					break;
				}
			}
			panel.p1 = new Point(e.getX(),e.getY());
		}else if(type == ElementType.RESIZE){
			for(int i = 0;i < guiElements.size();i++){
				if(guiElements.get(i).getBounds().contains(x, y)){
					resizeElementIndex = i;
				}
			}
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

					if(checkCompatibility(startElement,endElement)){
						link.to = endElement;
						link.toGui = guiElements.get(i);
						link.getDirection();
						linkys.add(link);
						link = null;
					}

					if(startElement instanceof Add){
						if(endElement instanceof Variable){
							// Show dialog to choose what varaible this should be
							Object[] options = {"Input", "Index", "Cancel"};
							int sel = JOptionPane.showOptionDialog(gui, "What type of variable is this?", "Type of variable", JOptionPane.YES_NO_CANCEL_OPTION	, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
							if(sel == 0){
								((Add) startElement).setSourceVariable((Variable) endElement);
							}else if(sel == 1){
								((Add) startElement).setIndexVariable((Variable) endElement);
							}
						}else if(endElement instanceof LinkedList){
							((Add) startElement).setTarget(endElement);
						}else if(endElement instanceof Tree){
							((Add) startElement).setTarget(endElement);
						}else if(endElement instanceof Queue){
							((Add) startElement).setTarget(endElement);
						}
					}else if(startElement instanceof Variable){
						if(endElement instanceof Add){
							Object[] options = {"Input", "Index", "Cancel"};
							int sel = JOptionPane.showOptionDialog(((Add) endElement).getGuiElement(), "What type of variable is this?", "Type of variable", JOptionPane.YES_NO_CANCEL_OPTION	, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
							if(sel == 0){
								((Add) endElement).setSourceVariable((Variable) startElement);
							}else if(sel == 1){
								((Add) endElement).setIndexVariable((Variable) startElement);
							}
						}else if(endElement instanceof Remove){
							Object[] options = {"Output", "Index", "Cancel"};
							int sel = JOptionPane.showOptionDialog(((Remove) endElement).getGuiElement(), "What type of variable is this?", "Type of variable", JOptionPane.YES_NO_CANCEL_OPTION	, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
							if(sel == 0){
								((Remove) endElement).setSourceVariable((Variable) startElement);
							}else if(sel == 1){
								((Remove) endElement).setIndexVariable((Variable) startElement);
							}
						}else if(endElement instanceof Insert){
							Object[] options = {"Input", "Index", "Cancel"};
							int sel = JOptionPane.showOptionDialog(((Insert) endElement).getGuiElement(), "What type of variable is this?", "Type of variable", JOptionPane.YES_NO_CANCEL_OPTION	, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
							if(sel == 0){
								((Insert) endElement).setSourceVariable((Variable) startElement);
							}else if(sel == 1){
								((Insert) endElement).setIndexVariable((Variable) startElement);
							}
						}else if(endElement instanceof Push){
							((Push) endElement).setSourceVariable((Variable) startElement);
						}else if(endElement instanceof Pop){
							((Pop) endElement).setSourceVariable((Variable) startElement);
						}else if(endElement instanceof Get){

							Object[] options = {"Input", "Output", "Index"};
							Object sel = JOptionPane.showInputDialog(((Get) endElement).getGuiElement(), "What type of variable is this?", "Type of variable", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
							if(sel== null) return;

							int getChar = -1;
							if(sel.equals("Input")){
								((Get) endElement).setSource((Variable) startElement);

								getChar = JOptionPane.showConfirmDialog(((Get) endElement).getGuiElement(), "Should the function get one character at a time?", "Get function",JOptionPane.YES_NO_CANCEL_OPTION);

							}else if(sel.equals("Output")){
								((Get) endElement).setTarget(startElement);
								getChar = JOptionPane.showConfirmDialog(((Get) endElement).getGuiElement(), "Should the function set one character at a time?", "Get function",JOptionPane.YES_NO_CANCEL_OPTION);

							}else if(sel.equals("Index")){
								((Get) endElement).setIndexVariable((Variable) startElement);
							}else{
								return;
							}
							if(getChar == 0){
								((Get) endElement).setSingleChar(true);
							}else if(getChar == 1){
								((Get) endElement).setSingleChar(false);
							}
						}else if(endElement instanceof Set){

							int setChar = -1;

							Object[] options = {"Input", "Index", "Cancel"};
							int sel = JOptionPane.showOptionDialog(((Set) endElement).getGuiElement(), "What type of variable is this?", "Type of variable", JOptionPane.YES_NO_CANCEL_OPTION	, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

							
							if(sel == 0){
								((Set) endElement).setSourceVariable((Variable) startElement);
								setChar = JOptionPane.showConfirmDialog(((Set) endElement).getGuiElement(), "Should the function set one character at a time?", "Get function",JOptionPane.YES_NO_CANCEL_OPTION);

							}else if(sel == 1){
								((Set) endElement).setIndexVariable((Variable) startElement);
							}else{
								return;
							}
							if(setChar == 0){
								((Set) endElement).setSingleChar(true);
							}else if(setChar == 1){
								((Set) endElement).setSingleChar(false);
							}
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
						if(endElement instanceof Set){
							((Set) endElement).setTarget(startElement);
						}else if(endElement instanceof Get){
							((Get) endElement).setTarget(startElement);
						}else if(endElement instanceof Remove){
							((Remove) endElement).setTarget(startElement);
						}else if(endElement instanceof Insert){
							((Insert) endElement).setTarget(startElement);
						}else if(endElement instanceof Add){
							((Add) endElement).setTarget(startElement);
						}
					}else if(startElement instanceof Remove){
						if(endElement instanceof Variable){
							Object[] options = {"Ouput", "Index", "Cancel"};
							int sel = JOptionPane.showOptionDialog(((Variable) endElement).getGuiElement(), "What type of variable is this?", "Type of variable", JOptionPane.YES_NO_CANCEL_OPTION	, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
							if(sel == 0){
								((Remove) startElement).setSourceVariable((Variable) endElement);
							}else if(sel == 1){
								((Remove) startElement).setIndexVariable((Variable) endElement);
							}
						}else if(endElement instanceof LinkedList){
							((Remove) startElement).setTarget(endElement);
						}else if(endElement instanceof Tree){
							((Remove) startElement).setTarget(endElement);
						}else if(endElement instanceof Queue){
							((Remove) startElement).setTarget(endElement);
						}
					}else if(startElement instanceof Get){
						if(endElement instanceof Variable){

							int getChar = -1;


							Object[] options = {"Input", "Output", "Index"};
							Object sel = JOptionPane.showInputDialog(((Variable) endElement).getGuiElement(), "What type of variable is this?", "Type of variable", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
							if(sel== null) return;
							if(sel.equals("Input")){
								((Get) startElement).setSource((Variable) endElement);
								getChar= JOptionPane.showConfirmDialog(((Variable) endElement).getGuiElement(), "Should the function get one character at a time?", "Get function",JOptionPane.YES_NO_CANCEL_OPTION);

							}else if(sel.equals("Output")){
								((Get) startElement).setTarget(endElement);
								getChar= JOptionPane.showConfirmDialog(((Variable) endElement).getGuiElement(), "Should the function set one character at a time?", "Get function",JOptionPane.YES_NO_CANCEL_OPTION);

							}else if(sel.equals("Index")){
								((Get) startElement).setIndexVariable((Variable) endElement);
							}else{
								return;
							}							
							if(getChar == 0){
								((Get) startElement).setSingleChar(true);
							}else if(getChar == 1){
								((Get) startElement).setSingleChar(false);
							}
						}else if(endElement instanceof LinkedList){
							((Get) startElement).setSource(endElement);
						}else if(endElement instanceof Tree){
							((Get) startElement).setSource(endElement);
						}else if(endElement instanceof Array){
							((Get) startElement).setSource(endElement);
						}
					}else if(startElement instanceof Set){
						if(endElement instanceof Variable){

							

							int setChar 	= JOptionPane.showConfirmDialog(((Variable) endElement).getGuiElement(), "Should the function get one character at a time?", "Get function",JOptionPane.YES_NO_CANCEL_OPTION);
							
							Object[] options = {"Input", "Index", "Cancel"};
							int sel = JOptionPane.showOptionDialog(((Variable) endElement).getGuiElement(), "What type of variable is this?", "Type of variable", JOptionPane.YES_NO_CANCEL_OPTION	, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
							if(sel == 0){
								((Set) startElement).setSourceVariable((Variable) endElement);
							}else if(sel == 1){
								((Set) startElement).setIndexVariable((Variable) endElement);
							}
							if(setChar == 0){
								((Set) startElement).setSingleChar(true);
							}else if(setChar == 1){
								((Set) startElement).setSingleChar(false);
							}
						}else if(endElement instanceof LinkedList){
							((Set) startElement).setTarget(endElement);
						}else if(endElement instanceof Tree){
							((Set) startElement).setTarget(endElement);
						}else if(endElement instanceof Queue){
							((Set) startElement).setTarget(endElement);
						}else if(endElement instanceof Array){
							((Set) startElement).setTarget(endElement);
						}
					}else if(startElement instanceof Array){
						if(endElement instanceof Set){
							((Set) endElement).setTarget(startElement);
						}else if(endElement instanceof Get){
							((Get) endElement).setTarget(startElement);
						}else if(endElement instanceof Insert){
							((Insert) endElement).setTarget(startElement);
						}
					}else if(startElement instanceof Tree){
						if(endElement instanceof Set){
							((Set) endElement).setTarget(startElement);
						}else if(endElement instanceof Get){
							((Get) endElement).setTarget(startElement);
						}else if(endElement instanceof Insert){
							((Insert) endElement).setTarget(startElement);
						}else if(endElement instanceof Add){
							((Add) endElement).setTarget(startElement);
						}else if(endElement instanceof Remove){
							((Remove) endElement).setTarget(startElement);
						}
					}else if(startElement instanceof Insert){
						if(endElement instanceof Tree){
							((Insert) startElement).setTarget(endElement);
						}else if(endElement instanceof LinkedList){
							((Insert) startElement).setTarget(endElement);
						}else if(endElement instanceof Variable){
							Object[] options = {"Input", "Index", "Cancel"};
							int sel = JOptionPane.showOptionDialog(((Variable) endElement).getGuiElement(), "What type of variable is this?", "Type of variable", JOptionPane.YES_NO_CANCEL_OPTION	, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
							if(sel == 0){
								((Insert) startElement).setSourceVariable((Variable) endElement);
							}else if(sel == 1){
								((Insert) startElement).setIndexVariable((Variable) endElement);
							}
						}
					}else if(startElement instanceof Queue){
						if(endElement instanceof Add){
							((Add) endElement).setTarget(startElement);
						}else if(endElement instanceof Remove){
							((Remove) endElement).setTarget(startElement);
						}
					}
				}
			}
		}else if(type == ElementType.RESIZE && resizeElementIndex != -1){
			resizeEndPoint = new Point(x,y);
			int oldWidth = guiElements.get(resizeElementIndex).getWidth();
			int newWidth = oldWidth + (resizeEndPoint.x - resizeStartPoint.x);
			int oldHeight = guiElements.get(resizeElementIndex).getHeight();
			int oldX = guiElements.get(resizeElementIndex).getX();
			int oldY = guiElements.get(resizeElementIndex).getY();
			int newHeight = oldHeight + (resizeEndPoint.y - resizeStartPoint.y);
			newWidth = newWidth > 10 ? newWidth : 10;
			newHeight = newHeight > 10 ? newHeight : 10;

			guiElements.get(resizeElementIndex).setBounds(oldX, oldY, newWidth, newHeight);
			resizeElementIndex = -1;
			panel.repaint();
			panel.validate();
			gui.validate();
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
					panel.c = checkCompatibility(startElement, elements.get(i)) ? Color.green : Color.red;
					panel.r = new Rectangle(guiElements.get(i).getBounds());
					break;
				}else{
					panel.r = null;
					panel.c = Color.green;
				}
			}
			panel.p2 = new Point(e.getX(),e.getY());
			panel.repaint();
		}else if(type == ElementType.RESIZE && resizeElementIndex != -1){
			resizeEndPoint.x = x;
			resizeEndPoint.y = y;
			panel.r.width = (panel.r.width + (resizeEndPoint.x - resizeTempPoint.x)) > 10 ? (panel.r.width + (resizeEndPoint.x - resizeTempPoint.x)) : 10;
			panel.r.height = (panel.r.height + (resizeEndPoint.y - resizeTempPoint.y)) > 10 ? (panel.r.height + (resizeEndPoint.y - resizeTempPoint.y)) : 10;
			panel.repaint();
			resizeTempPoint.x = x;
			resizeTempPoint.y = y;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		gui.mouseCoords.setText("X: "+x+" Y: "+y);
		gui.validate();

		if(type == ElementType.LINK){
			for(int i=0;i<guiElements.size();i++){
				if(guiElements.get(i).getBounds().contains(x, y)){
					panel.r = new Rectangle(guiElements.get(i).getBounds());
					if(startElement != null)
						panel.c = checkCompatibility(startElement, elements.get(i)) ? Color.GREEN : Color.RED;
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
					panel.c = Color.red;
					panel.r = new Rectangle(guiElements.get(i).getBounds());
					panel.repaint();
					break;
				}else{
					panel.r = null;
					panel.c = Color.green;
					panel.repaint();
				}
			}
		}else if(type == ElementType.RESIZE){
			for(int i=0;i<guiElements.size();i++){
				if(guiElements.get(i).getBounds().contains(x,y)){
					panel.c = Color.orange;
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

	@SuppressWarnings("unused")
	@Override
	public void actionPerformed(ActionEvent e) {
		gui.editorPanel.remove(panel);
		panel.removeMouseListener(this);
		panel.removeMouseMotionListener(this);

		gui.validate();

		switch(Integer.parseInt(e.getActionCommand())){
		case 1:
			type = ElementType.STACK;
			gui.eInfo.setInfoType(InfoType.STACK);

			break;
		case 2:
			type = ElementType.ARRAY;
			gui.eInfo.setInfoType(InfoType.ARRAY);


			break;
		case 3:
			type = ElementType.LIST;
			gui.eInfo.setInfoType(InfoType.LINKEDLIST);
			break;
		case 4:
			type = ElementType.ADD;
			gui.eInfo.setInfoType(InfoType.ADD);
			break;
		case 5:
			type = ElementType.REMOVE;
			gui.eInfo.setInfoType(InfoType.REMOVE);
			break;
		case 6:
			type = ElementType.INSERT;
			gui.eInfo.setInfoType(InfoType.INSERT);
			break;
		case 7:
			type = ElementType.PUSH;
			gui.eInfo.setInfoType(InfoType.PUSH);
			break;
		case 8:
			type = ElementType.POP;
			gui.eInfo.setInfoType(InfoType.POP);
			break;
		case 9:
			type = ElementType.VARIABLE;
			gui.eInfo.setInfoType(InfoType.VARIABLE);
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
			type = ElementType.DELETE;

			panel.setOpaque(false);
			panel.setSize(gui.editorPanel.getSize());
			panel.addMouseListener(this);
			panel.addMouseMotionListener(this);

			gui.editorPanel.add(panel);
			gui.editorPanel.setComponentZOrder(panel, 0);
			gui.editorPanel.validate();
			break;
		case 13:
			type = ElementType.TREE;
			gui.eInfo.setInfoType(InfoType.TREE);
			break;
		case 14:
			type = ElementType.HEAP;
			gui.eInfo.setInfoType(InfoType.HEAP);
			break;
		case 15:
			type = ElementType.QUEUE;
			gui.eInfo.setInfoType(InfoType.QUEUE);
			break;
		case 16:
			gui.editorPanel.grid = ((JCheckBox) e.getSource()).isSelected() ? true : false;
			gui.editorPanel.repaint();
			panel.repaint();
			gui.validate();
			break;
		case 17:
			// ANIMATION WINDOW
			new EditorPlayer(gui.editorPanel);
			break;
		case 18:
			// SAVE
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("AlgoSim files", "ags");
			fc.setFileFilter(filter);
			int dir = fc.showSaveDialog(gui);
			if(dir == JFileChooser.APPROVE_OPTION){
				File f = fc.getSelectedFile();
				String path = f.getPath();
				path = path.endsWith(".ags") ? path : path + ".ags";

				try{
					FileWriter fio = new FileWriter(path);
					BufferedWriter out = new BufferedWriter(fio);
					for(int i = 0;i < elements.size();i++){
						Object element = elements.get(i);
						GuiElement guiElement = guiElements.get(i);
						out.write(i+";"+guiElement.getX()+":"+guiElement.getY()+":"+guiElement.getWidth()+":"+guiElement.getHeight());
						out.write(";"+elements.get(i).getClass().getSimpleName()+";");

						if(element instanceof Stack){
							out.write("null");
						}else if(element instanceof Array){
							out.write("null");
						}else if(element instanceof Add){
							String t = Integer.toString(elements.indexOf(((Add) element).getTarget()));
							String sv = Integer.toString(elements.indexOf(((Add) element).getSourceVariable()));
							String iv = Integer.toString(elements.indexOf(((Add) element).getIndexVariable()));

							out.write(t+":"+sv+":"+iv);
							out.flush();
						}else if(element instanceof Remove){
							String t = Integer.toString(elements.indexOf(((Remove) element).getTarget()));
							String sv = Integer.toString(elements.indexOf(((Remove) element).getSourceVariable()));
							String iv = Integer.toString(elements.indexOf(((Remove) element).getIndexVariable()));

							out.write(t+":"+sv+":"+iv);
							out.flush();
						}else if(element instanceof Insert){
							String t = Integer.toString(elements.indexOf(((Insert) element).getTarget()));
							String sv = Integer.toString(elements.indexOf(((Insert) element).getSourceVariable()));
							String iv = Integer.toString(elements.indexOf(((Insert) element).getIndexVariable()));

							out.write(t+":"+sv+":"+iv);
							out.flush();
						}else if(element instanceof Push){
							String t = Integer.toString(elements.indexOf(((Push) element).getTarget()));
							String sv = Integer.toString(elements.indexOf(((Push) element).getSourceVariable()));

							out.write(t+":"+sv);
							out.flush();
						}else if(element instanceof Pop){
							String t = Integer.toString(elements.indexOf(((Pop) element).getTarget()));
							String sv = Integer.toString(elements.indexOf(((Pop) element).getSourceVariable()));

							out.write(t+":"+sv);
						}else if(element instanceof Variable){
							String edit = ((Variable) element).isEditable ? "1" : "0";

							out.write(edit);
							out.flush();
						}else if(element instanceof LinkedList){
							out.write("null");
						}else if(element instanceof Tree){
							out.write("null");
						}else if(element instanceof Heap){
							out.write("null");
						}else if(element instanceof Get){
							String t = Integer.toString(elements.indexOf(((Get) element).getTarget()));
							String sv = Integer.toString(elements.indexOf(((Get) element).getSource()));
							String iv = Integer.toString(elements.indexOf(((Get) element).getIndexVariable()));

							out.write(t+":"+sv+":"+iv);
						}else if(element instanceof Set){
							String t = Integer.toString(elements.indexOf(((Set) element).getTarget()));
							String sv = Integer.toString(elements.indexOf(((Set) element).getSourceVariable()));
							String iv = Integer.toString(elements.indexOf(((Set) element).getIndexVariable()));

							out.write(t+":"+sv+":"+iv);
						}
						out.flush();

						out.write(System.getProperty("line.separator"));
					}
					out.close();
				} catch (Exception e1){
					e1.printStackTrace();
				}
			}
			break;
		case 19:
			// LOAD
			clearEditor();
			JFileChooser load = new JFileChooser();
			FileNameExtensionFilter openFilter = new FileNameExtensionFilter("AlgoSim files", "ags");
			load.setMultiSelectionEnabled(false);
			load.setFileFilter(openFilter);
			int l = load.showOpenDialog(gui);
			if(l == JFileChooser.APPROVE_OPTION){
				File openFile = load.getSelectedFile();
				try{
					FileReader fr = new FileReader(openFile);
					BufferedReader r = new BufferedReader(fr);

					int numLines = 0;
					while(r.readLine() != null)
						numLines++;
					r.close();

					fr = new FileReader(openFile);
					r = new BufferedReader(fr);
					String line = r.readLine();
					int currentLine = 0;
					int[][] link = new int[numLines][3];

					while(line != null){
						String[] s = line.split(";");
						int id = -1;
						Rectangle bounds = new Rectangle();
						for(int i = 0;i<s.length;i++){
							switch(i){
							case 0:
								// Sets the id
								id = Integer.parseInt(s[i]);
								break;
							case 1:
								// Sets the bounds of the object
								String[] boundStrings = s[i].split(":");
								bounds = new Rectangle(
										Integer.parseInt(boundStrings[0]),
										Integer.parseInt(boundStrings[1]),
										Integer.parseInt(boundStrings[2]),
										Integer.parseInt(boundStrings[3])
								);
								break;
							case 2:
								// Sets the object
								if(s[i].equals("Stack")){
									JComponent c = getComponentFromEnum(ElementType.STACK, bounds);
									addElementAtPosition(c);
								}else if(s[i].equals("Array")){
									JComponent c = getComponentFromEnum(ElementType.ARRAY, bounds);
									addElementAtPosition(c);
								}else if(s[i].equals("LinkedList")){
									JComponent c = getComponentFromEnum(ElementType.LIST, bounds);
									addElementAtPosition(c);
								}else if(s[i].equals("Tree")){
									JComponent c = getComponentFromEnum(ElementType.TREE, bounds);
									addElementAtPosition(c);
								}else if(s[i].equals("Heap")){
									JComponent c = getComponentFromEnum(ElementType.HEAP, bounds);
									addElementAtPosition(c);
								}else if(s[i].equals("Queue")){
									JComponent c = getComponentFromEnum(ElementType.QUEUE, bounds);
									addElementAtPosition(c);
								}else if(s[i].equals("Variable")){
									JComponent c = getComponentFromEnum(ElementType.VARIABLE, bounds);
									addElementAtPosition(c);
								}else if(s[i].equals("Add")){
									JComponent c = getComponentFromEnum(ElementType.ADD, bounds);
									addElementAtPosition(c);
								}else if(s[i].equals("Remove")){
									JComponent c = getComponentFromEnum(ElementType.REMOVE, bounds);
									addElementAtPosition(c);
								}else if(s[i].equals("Insert")){
									JComponent c = getComponentFromEnum(ElementType.INSERT, bounds);
									addElementAtPosition(c);
								}else if(s[i].equals("Push")){
									JComponent c = getComponentFromEnum(ElementType.PUSH, bounds);
									addElementAtPosition(c);
								}else if(s[i].equals("Pop")){
									JComponent c = getComponentFromEnum(ElementType.POP, bounds);
									addElementAtPosition(c);
								}else if(s[i].equals("Get")){
									JComponent c = getComponentFromEnum(ElementType.GET, bounds);
									addElementAtPosition(c);
								}else if(s[i].equals("Set")){
									JComponent c = getComponentFromEnum(ElementType.SET, bounds);
									addElementAtPosition(c);
								}

								break;
							case 3:
								// Attributes
								String[] attributes = s[i].split(":");
								for(int j = 0; j < attributes.length;j++){
									if(attributes[j].equals("null"))
										attributes[j] = "-1";
									link[currentLine][j] = Integer.parseInt(attributes[j]);
								}
								break;
							}
						}
						line = r.readLine();
						currentLine++;
					}

					// Handling the links between the objects in the gui.
					for(int i = 0;i < link.length;i++){
						Object element = elements.get(i);
						Link li = new Link();
						li.from = element;
						li.fromGui = guiElements.get(i);
						for(int j = 0;j < link[i].length;j++){

							if(link[i][j] != -1){
								if(!elements.get(link[i][j]).equals(element) && checkCompatibility(element,elements.get(link[i][j])) && !(element instanceof Variable)){
									li.to = elements.get(link[i][j]);
									li.toGui = guiElements.get(link[i][j]);
									li.getDirection();
									linkys.add(li);
									li = new Link();
									li.from = element;
									li.fromGui = guiElements.get(i);
								}

								if(element instanceof Push){
									switch(j){
									case 0:
										((Push) element).setTarget(elements.get(link[i][j]));
										break;
									case 1:
										((Push) element).setSourceVariable((Variable) elements.get(link[i][j]));
										break;
									}
								}else if(element instanceof Remove){
									switch(j){
									case 0:
										((Remove) element).setTarget(elements.get(link[i][j]));
										break;
									case 1:
										((Remove) element).setSourceVariable((Variable) elements.get(link[i][j]));
										break;
									case 2:
										((Remove) element).setIndexVariable((Variable) elements.get(link[i][j]));
										break;
									}
								}else if(element instanceof Insert){
									switch(j){
									case 0:
										((Insert) element).setTarget(elements.get(link[i][j]));
										break;
									case 1:
										((Insert) element).setSourceVariable((Variable) elements.get(link[i][j]));
										break;
									case 2:
										((Insert) element).setIndexVariable((Variable) elements.get(link[i][j]));
									}
								}else if(element instanceof Add){
									switch(j){
									case 0:
										((Add) element).setTarget(elements.get(link[i][j]));
										break;
									case 1:
										((Add) element).setSourceVariable((Variable) elements.get(link[i][j]));
										break;
									case 2:
										((Add) element).setIndexVariable((Variable) elements.get(link[i][j]));
									}
								}else if(element instanceof Pop){
									switch(j){
									case 0:
										((Pop) element).setTarget(elements.get(link[i][j]));
										break;
									case 1:
										((Pop) element).setSourceVariable((Variable) elements.get(link[i][j]));
										break;
									}
								}else if(element instanceof Get){
									switch(j){
									case 0:
										((Get) element).setTarget(elements.get(link[i][j]));
										break;
									case 1:
										((Get) element).setSource(elements.get(link[i][j]));
										break;
									case 2:
										((Get) element).setIndexVariable((Variable) elements.get(link[i][j]));
										break;
									}
								}else if(element instanceof Set){
									switch(j){
									case 0:
										((Set) element).setTarget(elements.get(link[i][j]));
										break;
									case 1:
										((Set) element).setSourceVariable((Variable) elements.get(link[i][j]));
										break;
									case 2:
										((Set) element).setIndexVariable((Variable) elements.get(link[i][j]));
										break;
									}
								}
							}
						}
					}
				}catch(Exception e1){
					e1.printStackTrace();
				}

			}
			break;
		case 20:
			//SET
			type = ElementType.SET;
//			gui.optionsPanel.setOptionsType(ElementType.SET);
			break;
		case 21:
			//GET
			type = ElementType.GET;
//			gui.optionsPanel.setOptionsType(ElementType.GET);
			break;
		case 22:
			//RESIZE
			JPanel resizePanel = new JPanel();
			JOptionPane.showInputDialog(gui, resizePanel);
			break;
		case 23:
			// NEW FILE
			clearEditor();
			break;
		case 24:
			// RESIZE MODE
			type = ElementType.RESIZE;

			panel.setOpaque(false);
			panel.setSize(gui.editorPanel.getSize());
			panel.addMouseListener(this);
			panel.addMouseMotionListener(this);

			gui.editorPanel.add(panel);
			gui.editorPanel.setComponentZOrder(panel, 0);
			gui.editorPanel.validate();
			break;
		}
//		gui.optionsPanel.setOptionsType(type);
		gui.editorPanel.revalidate();
		gui.repaint();
		gui.validate();
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		if(e.getID() == KeyEvent.KEY_PRESSED){

		}else if(e.getID() == KeyEvent.KEY_RELEASED){

		}else if(e.getID() == KeyEvent.KEY_TYPED){

		}
		return false;
	}

	/**
	 * An inner class of {@link EditorListener} that adds the ability to layer a "glass pane" on top of the current editor panel.
	 * This makes it easier to add mouselistener for item linking and deletion as you don't have to add a mouselistener to every single
	 * item on the editor panel.
	 * 
	 */
	@SuppressWarnings("serial") 
	class GlassPanel extends JPanel{
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
		}
	}

	/**
	 * An inner class of {@link EditorListener} that contains information about the links between objects in the editor panel.
	 * Instances of this class are added to the internal class variable {@link linkys} which is used for the drawing of links in the {@link EditorPanel}.
	 *
	 */
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

				if(checkCompatibility(from, to))
					direction = checkCompatibility(to, from) ? LinkDirection.LEFT_RIGHT : LinkDirection.RIGHT;
			}else if(fromX > toX+toWidth){ // 1st element is to the right of 2nd element
				int middleOfElements = fromX - ((fromX - (toX + toWidth)) / 2);
				p1 = new Point(toX+toWidth,toY + (toHeight / 2));
				p2 = new Point(middleOfElements,p1.y);
				p3 = new Point(middleOfElements,fromY + (fromHeight / 2));
				p4 = new Point(fromX,p3.y);

				if(checkCompatibility(from, to))
					direction = checkCompatibility(to, from) ? LinkDirection.LEFT_RIGHT : LinkDirection.LEFT;
			}else{ // The elements are above or below each other
				if(fromY+fromHeight < toY){ // 1st element is above 2nd element
					int middleOfElements = toY - ((toY - (fromY + fromHeight)) / 2);
					p1 = new Point(fromX + (fromWidth / 2),fromY+fromHeight);
					p2 = new Point(p1.x,middleOfElements);
					p3 = new Point(toX + (toWidth / 2), middleOfElements);
					p4 = new Point(p3.x,toY);

					if(checkCompatibility(from, to))
						direction = checkCompatibility(to, from) ? LinkDirection.UP_DOWN : LinkDirection.DOWN;
				}else if(fromY > toY+toHeight){ // 1st element is below 2nd element
					int middleOfElements = fromY - ((fromY - (toY + toHeight)) / 2);
					p1 = new Point(toX + (toWidth / 2), toY+toHeight);
					p2 = new Point(p1.x,middleOfElements);
					p3 = new Point(fromX + (fromWidth / 2), middleOfElements);
					p4 = new Point(p3.x,fromY);

					if(checkCompatibility(from, to))
						direction = checkCompatibility(to, from) ? LinkDirection.UP_DOWN : LinkDirection.UP;
				}
			}
		}
	}

	public enum ElementType{
		STACK,ARRAY,LIST,ADD,REMOVE,INSERT,PUSH,POP,VARIABLE,LINK,SELECT,GET, SET,DELETE,TREE,HEAP,QUEUE,NONE,RESIZE
	}

	protected enum LinkDirection{
		UP,DOWN,LEFT,RIGHT,LEFT_RIGHT,UP_DOWN
	}
}
