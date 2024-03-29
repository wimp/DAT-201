package sim.editor;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Cursor;
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
import java.io.File;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
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
import sim.functions.Add;
import sim.functions.Get;
import sim.functions.Insert;
import sim.functions.Pop;
import sim.functions.Push;
import sim.functions.Remove;
import sim.functions.Set;
import sim.gui.elements.GuiElement;
import sim.gui.elements.GuiSettings;
import sim.structures.Array;
import sim.structures.Heap;
import sim.structures.Info;
import sim.structures.LinkedList;
import sim.structures.Queue;
import sim.structures.Stack;
import sim.structures.Tree;
import sim.structures.Variable;

/**
 * The listener and action handling class for the {@link EditorGui}. 
 * Handles everything that is going on in the editor and keeps track of elements that are active and the links between them.
 *
 */
public class EditorListener implements ActionListener, MouseMotionListener, MouseListener, KeyEventDispatcher {
// Class variables //
	protected ElementType 			type 			= ElementType.NONE;
	protected GlassPanel 			panel 			= new GlassPanel();
	protected Vector<Link> 			links 			= new Vector<Link>();
	protected boolean 				showDialogOnAdd = true;
	protected Vector<Object> 		elements 		= new Vector<Object>();
	protected Vector<GuiElement> 	guiElements 	= new Vector<GuiElement>();
	
	private Point 		resizeStartPoint 	= new Point();
	private Point 		resizeEndPoint 		= new Point();
	private Point 		resizeTempPoint 	= new Point();
	private Point 		moveEndPoint 		= new Point();
	private int 		moveElementIndex 	= -1;
	private int 		resizeElementIndex 	= -1;
	private int 		moveDifferX;
	private int 		moveDifferY;
	private EditorGui 	gui;
	private Object 		startElement;
	private Object 		endElement;
	private Link 		link;
	
	
	

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
	protected void addElementAtPosition(JComponent element){
		if(element!=null)
			gui.editorPanel.add(element);
	}

	/**
	 * A method to get the actual instance of a class from the class enum {@link ElementType}.
	 * This method also adds the given type's class instance and graphical instance in the two internal class variables, {@link elements} and {@link guiElements}
	 * @param type
	 * @param bounds
	 * @return The graphical component of the type given.
	 */
	protected JComponent getComponentFromEnum(ElementType type, Rectangle bounds) {
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
			Object[] options = {"Insert BEFORE given index","Insert AFTER given index"};
			bounds.width 	= bounds.width < 150 ? 150 : bounds.width;
			bounds.height 	= bounds.height < 30 ? 30 : bounds.height;
			Insert insertElement;
			if(showDialogOnAdd){
				Object sel = JOptionPane.showInputDialog(gui, "Where should this function insert values?", "How to insert", JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
				sel = sel == null ? options[0] : sel;
				insertElement = new Insert(bounds, sel.equals(options[0]) ? false : true);
			}else{
				insertElement = new Insert(bounds, false);
			}
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
			bounds.width 	= bounds.width < 30 ? 30 : bounds.width;
			bounds.height 	= bounds.height < 30 ? 30 : bounds.height;
			Variable variableElement = new Variable(bounds,"",true);
			elements.add(variableElement);
			index = elements.lastIndexOf(variableElement);
			guiElements.add(index,variableElement.getGuiElement());
			return variableElement.getGuiElement();
		case TREE:
			bounds.width	= bounds.width < 500 ? 500 : bounds.width;
			bounds.height	= bounds.height < 250 ? 250 : bounds.height;
			Tree treeElement = new Tree(bounds);
			elements.add(treeElement);
			index = elements.lastIndexOf(treeElement);
			guiElements.add(index,treeElement.getGuiElement());
			return treeElement.getGuiElement();
		case HEAP:
			bounds.width	= bounds.width < 400 ? 400 : bounds.width;
			bounds.height	= bounds.height < 400 ? 400 : bounds.height;
			Heap heapElement = new Heap(bounds);
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
			Set setElement 	= new Set(bounds);
			elements.add(setElement);
			index 			= elements.lastIndexOf(setElement);
			guiElements.add(index,setElement.getGuiElement());
			return setElement.getGuiElement();
		case GET:
			bounds.width	= bounds.width < 120 ? 120 : bounds.width;
			bounds.height	= bounds.height < 30 ? 30 : bounds.height;
			Get getElement 	= new Get(bounds);

			elements.add(getElement);
			index			= elements.lastIndexOf(getElement);
			guiElements.add(index,getElement.getGuiElement());
			return getElement.getGuiElement();
		case TEXT:
			bounds.width	= bounds.width < 120 ? 120 : bounds.width;
			bounds.height	= bounds.height < 100 ? 100 : bounds.height;
			Info textElement = new Info(bounds);
			
			elements.add(textElement);
			index 			= elements.lastIndexOf(textElement);
			guiElements.add(index,textElement.getGuiElement());
			return textElement.getGuiElement();
		}
		return null;
	}

	/**
	 * Refreshes the links between elements in cases where for example one of the elements has been removed.
	 * @param links
	 */
	private void reLinkElements(Vector<Link> links){
		for(Link l : links){
			l.makeLink();
		}
	}
	
	/**
	 * Clears all objects in the editor panel and removes them from {@link EditorListener}'s internal pointers.
	 */
	public void clearEditor(){
		links.clear();
		elements.clear();
		guiElements.clear();
		gui.editorPanel.removeAll();
		gui.repaint();
		gui.validate();
	}

	/**
	 * Checks whether elementFrom and elementTo fits together in the editor gui. E.g. an instance of {@link Add} can't be linked with another instance of {@link Add}
	 * @param elementFrom
	 * @param elementTo
	 * @return Returns true if the two items can be linked
	 */
	boolean checkCompatibility(Object elementFrom, Object elementTo){
		if(elementFrom instanceof Add){
			if(elementTo instanceof Variable){
				return true;
			}else if(elementTo instanceof LinkedList){
				return true;
			}else if(elementTo instanceof Tree){
				return true;
			}else if(elementTo instanceof Queue){
				return true;
			}
		}else if(elementFrom instanceof Get){
			if(elementTo instanceof Variable){
				return true;
			}else if(elementTo instanceof LinkedList){
				return true;
			}else if(elementTo instanceof Tree){
				return true;
			}else if(elementTo instanceof Array){
				return true;
			}
		}
		else if(elementFrom instanceof Set){
			if(elementTo instanceof Variable){
				return true;
			}else if(elementTo instanceof LinkedList){
				return true;
			}else if(elementTo instanceof Tree){
				return true;
			}else if(elementTo instanceof Array){
				return true;
			}
		}
		else if(elementFrom instanceof Variable){
			if(elementTo instanceof Add){
				return true;
			}else if(elementTo instanceof Remove){
				return true;
			}else if(elementTo instanceof Insert){
				return true;
			}else if(elementTo instanceof Push){
				return true;
			}else if(elementTo instanceof Pop){
				return true;
			}else if(elementTo instanceof Get){
				return true;
			}else if(elementTo instanceof Set){
				return true;
			}
		}else if(elementFrom instanceof Stack){
			if(elementTo instanceof Push){
				return true;
			}else if(elementTo instanceof Pop){
				return true;
			}
		}else if(elementFrom instanceof Push){
			if(elementTo instanceof Stack){
				return true;
			}else if(elementTo instanceof Variable){
				return true;
			}
		}else if(elementFrom instanceof Pop){
			if(elementTo instanceof Stack){
				return true;
			}else if(elementTo instanceof Variable){
				return true;
			}
		}else if(elementFrom instanceof LinkedList){
			if(elementTo instanceof Remove){
				return true;
			}else if(elementTo instanceof Insert){
				return true;
			}else if(elementTo instanceof Get){
				return true;
			}else if(elementTo instanceof Set){
				return true;
			}else if(elementTo instanceof Add){
				return true;
			}
		}else if(elementFrom instanceof Remove){
			if(elementTo instanceof Variable){
				return true;
			}else if(elementTo instanceof LinkedList){
				return true;
			}else if(elementTo instanceof Tree){
				return true;
			}else if(elementTo instanceof Queue){
				return true;
			}
		}else if(elementFrom instanceof Array){
			if(elementTo instanceof Get){
				return true;
			}else if(elementTo instanceof Set){
				return true;
			}else if(elementTo instanceof Remove){
				return true;
			}else if(elementTo instanceof Insert){
				return true;
			}
		}else if(elementFrom instanceof Tree){
			if(elementTo instanceof Get){
				return true;
			}else if(elementTo instanceof Set){
				return true;
			}else if(elementTo instanceof Remove){
				return true;
			}else if(elementTo instanceof Insert){
				return true;
			}else if(elementTo instanceof Add){
				return true;
			}
		}else if(elementFrom instanceof Insert){
			if(elementTo instanceof Tree){
				return true;
			}else if(elementTo instanceof LinkedList){
				return true;
			}else if(elementTo instanceof Variable){
				return true;
			}
		}else if(elementFrom instanceof Queue){
			if(elementTo instanceof Add){
				return true;
			}else if(elementTo instanceof Remove){
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

		if(type == ElementType.SELECT) 
			return;
		
		if(type == ElementType.DELETE){
			for(int i=0;i<guiElements.size();i++){
				if(guiElements.get(i).getBounds().contains(x, y)){
					elementIndex = i;
					break;
				}
			}

			if(elementIndex != -1){
				gui.editorPanel.remove(guiElements.get(elementIndex));
				for(int i = 0;i < links.size(); i++){
					if(links.get(i).from==elements.get(elementIndex) || links.get(i).to==elements.get(elementIndex)){
						links.remove(i);
						i--;
					}
				}
				guiElements.remove(elementIndex);
				elements.remove(elementIndex);

				panel.r = null;
				reLinkElements(links);
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
			resizeStartPoint.x = x;
			resizeStartPoint.y = y;
			resizeTempPoint.x = x;
			resizeTempPoint.y = y;
			
			for(int i = 0;i < guiElements.size();i++){
				if(guiElements.get(i).getBounds().contains(x, y)){
					resizeElementIndex = i;
				}
			}
		}else if(type == ElementType.MOVE){
			for(int i = 0;i < guiElements.size();i++){
				if(guiElements.get(i).getBounds().contains(x, y)){
					moveElementIndex = i;
					moveDifferX = guiElements.get(i).getX() - x;
					moveDifferY = guiElements.get(i).getY() - y;
					moveEndPoint.x = guiElements.get(i).getX() + (moveDifferX);
					moveEndPoint.y = guiElements.get(i).getY() + (moveDifferY);
					panel.r.x = moveEndPoint.x;
					panel.r.y = moveEndPoint.y;
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
						link.makeLink();
						links.add(link);
						link = null;
					}

				// startElement
					if(startElement instanceof Add){
						if(endElement instanceof Variable){
							// Show dialog to choose what varaible this should be
							Object[] options = {"Input", "Index"};
							Object sel = JOptionPane.showInputDialog(((Variable) endElement).getGuiElement(), "What type of variable is this?", "Type of variable", JOptionPane.INFORMATION_MESSAGE	, null, options, options[0]);
							if(sel == null) return;
							if(sel.equals("Input")){
								((Add) startElement).setSourceVariable((Variable) endElement);
							}else if(sel.equals("Index")){
								((Add) startElement).setIndexVariable((Variable) endElement);
							}
						}else if(endElement instanceof LinkedList){
							((Add) startElement).setTarget(endElement);
						}else if(endElement instanceof Tree){
							((Add) startElement).setTarget(endElement);
						}else if(endElement instanceof Queue){
							((Add) startElement).setTarget(endElement);
						}
				// startElement
					}else if(startElement instanceof Variable){
						if(endElement instanceof Add){
							Object[] options = {"Input", "Index"};
							Object sel = JOptionPane.showInputDialog(((Add) endElement).getGuiElement(), "What type of variable is this?", "Type of variable", JOptionPane.INFORMATION_MESSAGE	, null, options, options[0]);
							if(sel == null) return;
							if(sel.equals("Input")){
								((Add) endElement).setSourceVariable((Variable) startElement);
							}else if(sel.equals("Index")){
								((Add) endElement).setIndexVariable((Variable) startElement);
							}
						}else if(endElement instanceof Remove){
							Object[] options = {"Output", "Index"};
							Object sel = JOptionPane.showInputDialog(((Remove) endElement).getGuiElement(), "What type of variable is this?", "Type of variable", JOptionPane.INFORMATION_MESSAGE	, null, options, options[0]);
							if(sel == null) return;
							if(sel.equals("Output")){
								((Remove) endElement).setTargetVariable((Variable) startElement);
							}else if(sel.equals("Index")){
								((Remove) endElement).setIndexVariable((Variable) startElement);
							}
						}else if(endElement instanceof Insert){
							Object[] options = {"Input", "Index"};
							Object sel = JOptionPane.showInputDialog(((Insert) endElement).getGuiElement(), "What type of variable is this?", "Type of variable", JOptionPane.INFORMATION_MESSAGE	, null, options, options[0]);
							if(sel == null) return;
							if(sel.equals("Input")){
								((Insert) endElement).setSourceVariable((Variable) startElement);
							}else if(sel.equals("Index")){
								((Insert) endElement).setIndexVariable((Variable) startElement);
							}
						}else if(endElement instanceof Push){
							((Push) endElement).setSourceVariable((Variable) startElement);
						}else if(endElement instanceof Pop){
							((Pop) endElement).setTargetVariable((Variable) startElement);
						}else if(endElement instanceof Get){
							int getChar = -1;
							Object[] options = {"Input", "Output", "Index"};
							Object sel = JOptionPane.showInputDialog(((Get) endElement).getGuiElement(), "What type of variable is this?", "Type of variable", JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
							if(sel== null) return;

							if(sel.equals(options[0])){
								((Get) endElement).setSource((Variable) startElement);
								Object[] opt = {"Read a single character at a time","Read the entire value"};
								Object read = JOptionPane.showInputDialog(((Get) endElement).getGuiElement(), "How would you like to read this variable?", "Type of variable",JOptionPane.INFORMATION_MESSAGE,null,opt,opt[1]);

								if(read != null){
									if(read.equals(opt[0]))
										getChar = 1;
									else if(read.equals(opt[1]))
										getChar = 0;
									else
										getChar = 0;
								}else
									getChar = 0;
								((Get) endElement).setSingleChar(getChar < 1 ? false : true);

							}else if(sel.equals(options[1])){
								((Get) endElement).setTarget((Variable) startElement);
							}else if(sel.equals(options[2])){
								((Get) endElement).setIndexVariable((Variable) startElement);
							}else{
								return;
							}

						}else if(endElement instanceof Set){
							int getChar = -1;
							Object[] options = {"Input", "Output", "Index"};
							Object sel = JOptionPane.showInputDialog(((Set) endElement).getGuiElement(), "What type of variable is this?", "Type of variable", JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
							if(sel== null) return;

							if(sel.equals(options[0])){
								((Set) endElement).setSourceVariable((Variable) startElement);
								Object[] opt = {"Read a single character at a time","Read the entire value"};
								Object read = JOptionPane.showInputDialog(((Set) endElement).getGuiElement(), "How would you like to read this variable?", "Type of variable",JOptionPane.INFORMATION_MESSAGE,null,opt,opt[1]);

								if(read != null){
									if(read.equals(opt[0]))
										getChar = 1;
									else if(read.equals(opt[1]))
										getChar = 0;
									else
										getChar = 0;
								}else
									getChar = 0;


								((Set) endElement).setSingleChar(getChar < 1 ? false : true);
							}else if(sel.equals(options[1])){
								((Set) endElement).setTarget((Variable) startElement);
							}else if(sel.equals(options[2])){
								((Set) endElement).setIndexVariable((Variable) startElement);
							}else{
								return;
							}
						}
				// startElement
					}else if(startElement instanceof Stack){
						if(endElement instanceof Push){
							((Push) endElement).setTarget(startElement);
						}else if(endElement instanceof Pop){
							((Pop) endElement).setSource(startElement);
						}
				// startElement
					}else if(startElement instanceof Push){
						if(endElement instanceof Stack){
							((Push) startElement).setTarget(endElement);
						}else if(endElement instanceof Variable){
							((Push) startElement).setSourceVariable((Variable) endElement);
						}
				// startElement
					}else if(startElement instanceof Pop){
						if(endElement instanceof Stack){
							((Pop) startElement).setSource(endElement);
						}else if(endElement instanceof Variable){
							((Pop) startElement).setTargetVariable((Variable) endElement);
						}
				// startElement
					}else if(startElement instanceof LinkedList){
						if(endElement instanceof Set){
							((Set) endElement).setTarget(startElement);
						}else if(endElement instanceof Get){
							((Get) endElement).setSource(startElement);
						}else if(endElement instanceof Remove){
							((Remove) endElement).setSource(startElement);
						}else if(endElement instanceof Insert){
							((Insert) endElement).setTarget(startElement);
						}else if(endElement instanceof Add){
							((Add) endElement).setTarget(startElement);
						}
				// startElement
					}else if(startElement instanceof Remove){
						if(endElement instanceof Variable){
							Object[] options = {"Ouput", "Index"};
							Object sel = JOptionPane.showInputDialog(((Variable) endElement).getGuiElement(), "What type of variable is this?", "Type of variable", JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
							
							if(sel == null) return;
							if(sel.equals(options[0])){
								((Remove) startElement).setTargetVariable((Variable) endElement);
							}else if(sel.equals(options[1])){
								((Remove) startElement).setIndexVariable((Variable) endElement);
							}
						}else if(endElement instanceof LinkedList){
							((Remove) startElement).setSource(endElement);
						}else if(endElement instanceof Tree){
							((Remove) startElement).setSource(endElement);
						}else if(endElement instanceof Queue){
							((Remove) startElement).setSource(endElement);
						}
				// startElement
					}else if(startElement instanceof Get){
						if(endElement instanceof Variable){
							int getChar = -1;
							Object[] options = {"Input", "Output", "Index"};
							Object sel = JOptionPane.showInputDialog(((Variable) endElement).getGuiElement(), "What type of variable is this?", "Type of variable", JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
							if(sel== null) return;
							if(sel.equals(options[0])){
								((Get) startElement).setSource((Variable) endElement);
								Object[] opt = {"Read a single character at a time","Read the entire value"};
								Object read  = JOptionPane.showInputDialog(((Variable) endElement).getGuiElement(), "How would you like to read this variable?", "How to read variable", JOptionPane.INFORMATION_MESSAGE, null, opt, opt[1]);
								if(read != null){
									if(read.equals(opt[0])){
										getChar = 1;
									}else if(read.equals(opt[1])){
										getChar = 0;
									}else
										getChar = 0;
								}else
									getChar = 0;
								((Get) startElement).setSingleChar(getChar < 1 ? false : true);
							}else if(sel.equals(options[1])){
								((Get) startElement).setTarget((Variable) endElement);
							}else if(sel.equals(options[2])){
								((Get) startElement).setIndexVariable((Variable) endElement);
							}else{
								return;
							}

						}else if(endElement instanceof LinkedList){
							((Get) startElement).setSource(endElement);
						}else if(endElement instanceof Tree){
							((Get) startElement).setSource(endElement);
						}else if(endElement instanceof Array){
							((Get) startElement).setSource(endElement);
						}
				// startElement
					}else if(startElement instanceof Set){
						if(endElement instanceof Variable){													
							int setChar = -1;
							Object[] options = {"Input", "Output", "Index"};
							Object sel = JOptionPane.showInputDialog(((Variable) endElement).getGuiElement(), "What type of variable is this?", "Type of variable", JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

							if(sel == null) return;

							if(sel.equals("Input")){
								((Set) startElement).setSourceVariable((Variable) endElement);
								Object[] opt = {"Read a single character at a time","Read the entire value"};
								Object read = JOptionPane.showInputDialog(((Variable) endElement).getGuiElement(),"How would you like to read this variable?","How to read variable",JOptionPane.INFORMATION_MESSAGE,null,opt,opt[1]);
								if(read != null){
									if(read.equals(opt[0])){
										setChar = 1;
									}else if(read.equals(opt[1])){
										setChar = 0;
									}
								}else
									setChar = 0;
								((Set) startElement).setSingleChar(setChar < 1 ? false : true);
							}else if(sel.equals("Output")){
								((Set) startElement).setTarget(endElement);
								setChar = 1;
							}else if(sel.equals(options[2])){
								((Set) startElement).setIndexVariable((Variable) endElement);
								setChar = 0;
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
				// startElement
					}else if(startElement instanceof Array){
						if(endElement instanceof Set){
							((Set) endElement).setTarget(startElement);
						}else if(endElement instanceof Get){
							((Get) endElement).setSource(startElement);
						}else if(endElement instanceof Insert){
							((Insert) endElement).setTarget(startElement);
						}
				// startElement
					}else if(startElement instanceof Tree){
						if(endElement instanceof Set){
							((Set) endElement).setTarget(startElement);
						}else if(endElement instanceof Get){
							((Get) endElement).setSource(startElement);
						}else if(endElement instanceof Insert){
							((Insert) endElement).setTarget(startElement);
						}else if(endElement instanceof Add){
							((Add) endElement).setTarget(startElement);
						}else if(endElement instanceof Remove){
							((Remove) endElement).setSource(startElement);
						}
				// startElement
					}else if(startElement instanceof Insert){
						if(endElement instanceof Tree){
							((Insert) startElement).setTarget(endElement);
						}else if(endElement instanceof LinkedList){
							((Insert) startElement).setTarget(endElement);
						}else if(endElement instanceof Variable){
							Object[] options = {"Input", "Index"};
							Object sel = JOptionPane.showInputDialog(((Variable) endElement).getGuiElement(), 
									"What type of variable is this?", "Type of variable", JOptionPane.INFORMATION_MESSAGE	, 
									null, options, options[0]);
							
							if(sel == null) return;
							if(sel.equals(options[0])){
								((Insert) startElement).setSourceVariable((Variable) endElement);
							}else if(sel.equals(options[1])){
								((Insert) startElement).setIndexVariable((Variable) endElement);
							}
						}
				// startElement
					}else if(startElement instanceof Queue){
						if(endElement instanceof Add){
							((Add) endElement).setTarget(startElement);
						}else if(endElement instanceof Remove){
							((Remove) endElement).setSource(startElement);
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
			reLinkElements(links);
			panel.repaint();
			panel.validate();
			gui.validate();
		}else if(type == ElementType.MOVE && moveElementIndex != -1){
			moveEndPoint.x = (int) (x + (moveDifferX));
			moveEndPoint.y = (int) (y + (moveDifferY));
			int width = guiElements.get(moveElementIndex).getWidth();
			int height = guiElements.get(moveElementIndex).getHeight();
			guiElements.get(moveElementIndex).setBounds(moveEndPoint.x,moveEndPoint.y,width,height);
			moveElementIndex = -1;
			moveEndPoint.x = x;
			moveEndPoint.y = y;
			reLinkElements(links);
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
		}else if(type == ElementType.MOVE && moveElementIndex != -1){
			moveEndPoint.x = (int) (x + (moveDifferX));
			moveEndPoint.y = (int) (y + (moveDifferY));
			panel.r.x = moveEndPoint.x;
			panel.r.y = moveEndPoint.y;
			panel.repaint();
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
					gui.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
					gui.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					panel.repaint();
				}
			}
		}else if(type == ElementType.RESIZE){
			for(int i=0;i<guiElements.size();i++){
				if(guiElements.get(i).getBounds().contains(x,y)){
					panel.c = Color.orange;
					panel.r = new Rectangle(guiElements.get(i).getBounds());
					panel.repaint();
					gui.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
					break;
				}else{
					panel.r = null;
					panel.c = Color.green;
					gui.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					panel.repaint();
				}
			}
		}else if(type == ElementType.MOVE){
			for(int i=0;i<guiElements.size();i++){
				if(guiElements.get(i).getBounds().contains(x,y)){
					panel.c = Color.blue;
					panel.r = new Rectangle(guiElements.get(i).getBounds());
					panel.repaint();
					gui.setCursor(new Cursor(Cursor.MOVE_CURSOR));
					break;
				}else{
					panel.r = null;
					panel.c = Color.green;
					gui.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					panel.repaint();
				}
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		gui.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		gui.editorPanel.remove(panel);
		panel.removeMouseListener(this);
		panel.removeMouseMotionListener(this);

		gui.validate();
		
		// Converting integers from EditorGui to enums.
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
			gui.editorPanel.grid = ((JCheckBoxMenuItem) e.getSource()).isSelected() ? true : false;
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
				EditorFileHandling.saveFile(path, elements, guiElements);
			}
			// END OF SAVE ROUTINE
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
				EditorFileHandling.loadFile(openFile, this);
			}
			// END OF LOAD ROUTINE
			break;
		case 20:
			//SET
			type = ElementType.SET;
			gui.eInfo.setInfoType(InfoType.SET);
			break;
		case 21:
			//GET
			type = ElementType.GET;
			gui.eInfo.setInfoType(InfoType.GET);
			break;
		case 22:
			//RESIZE VIEW
			JPanel resizePanel = new JPanel(new BorderLayout());

			JTextField hText = new JTextField(Integer.toString(gui.editorPanel.getHeight()));
			JTextField wText = new JTextField(Integer.toString(gui.editorPanel.getWidth()));
			resizePanel.add(new JLabel("Please select the new height and width:"),BorderLayout.NORTH);
			JPanel gridPanel = new JPanel(new GridLayout(2,2));
			gridPanel.add(new JLabel("Width:"));
			gridPanel.add(wText);
			gridPanel.add(new JLabel("Height:"));
			gridPanel.add(hText);
			resizePanel.add(gridPanel,BorderLayout.CENTER);

			JOptionPane.showMessageDialog(gui, resizePanel);
			try{
				int w = Integer.parseInt(wText.getText());
				int h = Integer.parseInt(hText.getText());
				gui.editorPanel.setPreferredSize(new Dimension(w,h));
			}catch(Exception nfe){

			}
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
		case 25:
			// MOVE MODE
			type = ElementType.MOVE;
			
			panel.setOpaque(false);
			panel.setSize(gui.editorPanel.getSize());
			panel.addMouseListener(this);
			panel.addMouseMotionListener(this);

			gui.editorPanel.add(panel);
			gui.editorPanel.setComponentZOrder(panel, 0);
			gui.editorPanel.validate();
			break;
		case 26:
			// ANIMATE SELECTION //
			boolean animated = ((JCheckBoxMenuItem) e.getSource()).isSelected();
			GuiSettings.isAnimated = animated;
			break;
		case 27:
			// TEXT PANEL //
			type = ElementType.TEXT;
			gui.eInfo.setInfoType(InfoType.INFO);
			break;
		}
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

// Nested Classes and Interfaces //
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
	public class Link{
		protected GuiElement 	fromGui;
		protected GuiElement 	toGui;
		protected Object	 	from;
		protected Object	 	to;
		protected LinkDirection direction;
		protected Point			p1;
		protected Point			p2;
		protected Point			p3;
		protected Point			p4;

		void makeLink(){
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

// Class Enums //
	public enum ElementType{
		STACK,ARRAY,LIST,ADD,REMOVE,INSERT,PUSH,POP,VARIABLE,LINK,SELECT,GET, SET,DELETE,TREE,HEAP,QUEUE,NONE,RESIZE,MOVE,TEXT
	}

	protected enum LinkDirection{
		UP,DOWN,LEFT,RIGHT,LEFT_RIGHT,UP_DOWN
	}
}
