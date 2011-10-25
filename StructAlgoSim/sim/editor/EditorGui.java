package sim.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import sim.editor.EditorListener.ElementType;
import sim.editor.EditorListener.Link;

@SuppressWarnings("serial")
public class EditorGui extends JFrame {
	EditorListener el = new EditorListener(this);
	EditorPanel editorPanel;
	JLabel mouseCoords;
	OptionsPanel optionsPanel = new OptionsPanel();
	protected EditorInfo eInfo;
	
	public EditorGui(){
		// General initialization //
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 600);
		setLayout(new BorderLayout());
		JPanel topPanel 	= new JPanel();
		JPanel westPanel	= new JPanel();
		JPanel eastPanel	= new JPanel();
		JPanel bottomPanel	= new JPanel();
		
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(el);
		
		editorPanel = new EditorPanel();
		editorPanel.addMouseListener(el);
		editorPanel.addMouseMotionListener(el);
		editorPanel.setBackground(Color.lightGray);
		editorPanel.setLayout(null);
		mouseCoords = new JLabel("X:   Y:");
		eInfo = new EditorInfo();
		JSeparator s = new JSeparator();
		optionsPanel.setLayout(new GridLayout(2,1));
		
		// Init. items that should be in the top panel //
		ButtonGroup bg			= new ButtonGroup();
		JToggleButton stack 	= new JToggleButton("Stack");
		JToggleButton array 	= new JToggleButton("Array");
		JToggleButton list		= new JToggleButton("Linked List");
		JToggleButton add		= new JToggleButton("Add");
		JToggleButton remove 	= new JToggleButton("Remove");
		JToggleButton insert	= new JToggleButton("Insert");
		JToggleButton push		= new JToggleButton("Push");
		JToggleButton pop		= new JToggleButton("Pop");
		JToggleButton variable	= new JToggleButton("Variable");
		JToggleButton link		= new JToggleButton("Link");
		JToggleButton select	= new JToggleButton("Select and Push");
		JToggleButton moveChar	= new JToggleButton("Move Char");
		JToggleButton delete	= new JToggleButton("Delete Element");
		JToggleButton tree		= new JToggleButton("Tree");
		JToggleButton heap		= new JToggleButton("Heap");
		JToggleButton queue		= new JToggleButton("Queue");
		JCheckBox grid			= new JCheckBox("Grid");
		JButton animation		= new JButton("Animation");
		
		// Add actionlisteners and set action commands //
		stack.addActionListener(el);
		stack.setActionCommand("1");
		array.addActionListener(el);
		array.setActionCommand("2");
		list.addActionListener(el);
		list.setActionCommand("3");
		add.addActionListener(el);
		add.setActionCommand("4");
		remove.addActionListener(el);
		remove.setActionCommand("5");
		insert.addActionListener(el);
		insert.setActionCommand("6");
		push.addActionListener(el);
		push.setActionCommand("7");
		pop.addActionListener(el);
		pop.setActionCommand("8");
		variable.addActionListener(el);
		variable.setActionCommand("9");
		link.addActionListener(el);
		link.setActionCommand("10");
		select.addActionListener(el);
		select.setActionCommand("11");
		moveChar.addActionListener(el);
		moveChar.setActionCommand("12");
		delete.addActionListener(el);
		delete.setActionCommand("13");
		tree.addActionListener(el);
		tree.setActionCommand("14");
		heap.addActionListener(el);
		heap.setActionCommand("15");
		queue.addActionListener(el);
		queue.setActionCommand("16");
		grid.addActionListener(el);
		grid.setActionCommand("17");
		animation.addActionListener(el);
		animation.setActionCommand("18");
		
		// Add toggle buttons to the button group //
		bg.add(stack);
		bg.add(array);
		bg.add(list);
		bg.add(add);
		bg.add(remove);
		bg.add(insert);
		bg.add(push);
		bg.add(pop);
		bg.add(variable);
		bg.add(moveChar);
		bg.add(link);
		bg.add(select);
		bg.add(delete);
		bg.add(tree);
		bg.add(heap);
		bg.add(queue);
		
		// Add elements to the west panel (data structures and variables) //
		westPanel.setLayout(new GridLayout(10,1));
		westPanel.add(stack);
		westPanel.add(array);
		westPanel.add(list);
		westPanel.add(tree);
		westPanel.add(heap);
		westPanel.add(queue);
		westPanel.add(new JSeparator());
		westPanel.add(variable);
		westPanel.add(new JSeparator());
		westPanel.add(optionsPanel);
		
		// Add elements to the east panel (functions etc.) //
		eastPanel.setLayout(new GridLayout(10,1));
		eastPanel.add(add);
		eastPanel.add(remove);
		eastPanel.add(insert);
		eastPanel.add(push);
		eastPanel.add(pop);
		eastPanel.add(moveChar);
		eastPanel.add(s);
		eastPanel.add(s);
		eastPanel.add(s);
		
		// Add elements to the top panel //
		topPanel.setLayout(new BorderLayout());
		
		JPanel centerOfTop = new JPanel();
		centerOfTop.setLayout(new GridLayout(1,3));
			centerOfTop.add(link);
			centerOfTop.add(select);
			centerOfTop.add(delete);
		
		JPanel leftOnTop = new JPanel();
			leftOnTop.setPreferredSize(new Dimension(250,30));
			leftOnTop.setLayout(new GridLayout(1,2));
			leftOnTop.add(mouseCoords);
			leftOnTop.add(grid);
		JPanel rightOnTop = new JPanel(new GridLayout(1,3));
			rightOnTop.setPreferredSize(new Dimension(250,30));
			rightOnTop.add(new JLabel(""));
			rightOnTop.add(animation);
			rightOnTop.add(new JLabel(""));
		topPanel.add(centerOfTop,BorderLayout.CENTER);
		topPanel.add(leftOnTop,BorderLayout.WEST);
		topPanel.add(rightOnTop,BorderLayout.EAST);
		
		// Add elements to the bottom panel //
		bottomPanel.add(eInfo);
		
		// Add elements to the main frame in the gridLayout //
		add(eastPanel,BorderLayout.EAST);
		add(westPanel,BorderLayout.WEST);
		add(topPanel,BorderLayout.NORTH);
		add(bottomPanel,BorderLayout.SOUTH);
		add(editorPanel,BorderLayout.CENTER);
		
		eInfo.setBackground(Color.green);
		eInfo.setPreferredSize(new Dimension(700,200));
		
		validate();
		setVisible(true);
	}
	
	protected class EditorPanel extends JPanel{
		public boolean grid;
		protected void paintComponent(Graphics g){
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.gray);
			
			if(grid){
				for(int i = 20; i < this.getWidth();i+=20){
					g2d.drawLine(i, 0, i, this.getHeight());
				}
				for(int i = 20; i < this.getHeight();i+=20){
					g2d.drawLine(0, i, this.getWidth(), i);
				}
			}
			
			g2d.setColor(Color.black);
			for(int i = 0;i<el.linkys.size();i++){
				Link l = el.linkys.get(i);
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
		}
	}

	protected class OptionsPanel extends JPanel{
		JTextField 	textOption 	= new JTextField();
		ButtonGroup groupOption = new ButtonGroup();
		JCheckBox 	check1		= new JCheckBox();
		JCheckBox 	check2		= new JCheckBox();
		
		public OptionsPanel(){
			
		}
		
		public void setOptionsType(ElementType type){
			removeAll();

			// Remove all buttons from the button group //
			groupOption = null;
			groupOption = new ButtonGroup();
			
			switch(type){
			case ARRAY:
				setLayout(new GridLayout(2,1));
				add(new JLabel("Number of Elements"));
				add(textOption);
				break;
			case VARIABLE:
				setLayout(new GridLayout(3,1));
				add(new JLabel("Editable"));
				JRadioButton selTrue = new JRadioButton("Yes");
				selTrue.setActionCommand("1");
				JRadioButton selFalse = new JRadioButton("No");
				selFalse.setActionCommand("0");
				selTrue.setSelected(true);
				groupOption.add(selTrue);
				groupOption.add(selFalse);
				add(selTrue);
				add(selFalse);
				break;
			case INSERT:
				setLayout(new GridLayout(3,1));
				add(new JLabel("Where to insert"));
				JRadioButton selBefore 	= new JRadioButton("Before");
				selBefore.setActionCommand("0");
				JRadioButton selAfter	= new JRadioButton("After");
				selAfter.setActionCommand("1");
				selBefore.setSelected(true);
				groupOption.add(selBefore);
				groupOption.add(selAfter);
				add(selBefore);
				add(selAfter);
				break;
			default:
				
				break;	
			}
			validate();
		}
	}
}
