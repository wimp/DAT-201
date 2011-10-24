package sim.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

@SuppressWarnings("serial")
public class EditorGui extends JFrame {
	EditorListener el = new EditorListener(this);
	JPanel editorPanel;
	
	public EditorGui(){
		// General initialization //
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 400);
		setLayout(new BorderLayout());
		JPanel topPanel 	= new JPanel();
		JPanel westPanel	= new JPanel();
		JPanel eastPanel	= new JPanel();
		JPanel bottomPanel	= new JPanel();
		editorPanel = new JPanel();
		editorPanel.addMouseListener(el);
		editorPanel.setBackground(Color.cyan);
		editorPanel.setLayout(null);
		
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
		JToggleButton tree		= new JToggleButton("Tre");
		
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
		
		// Add elements to the west panel (data structures and variables) //
		westPanel.setLayout(new GridLayout(5,1));
		westPanel.add(stack);
		westPanel.add(array);
		westPanel.add(list);
		westPanel.add(variable);
		westPanel.add(tree);
		
		// Add elements to the east panel (functions etc.) //
		eastPanel.setLayout(new GridLayout(6,1));
		eastPanel.add(add);
		eastPanel.add(remove);
		eastPanel.add(insert);
		eastPanel.add(push);
		eastPanel.add(pop);
		eastPanel.add(moveChar);
		
		// Add elements to the top panel //
		topPanel.setLayout(new BorderLayout());
		
		JPanel centerOfTop = new JPanel();
		centerOfTop.setLayout(new GridLayout(1,3));
			centerOfTop.add(link);
			centerOfTop.add(select);
			centerOfTop.add(delete);
		
		JPanel leftOnTop = new JPanel();
			leftOnTop.setPreferredSize(new Dimension(250,30));
		JPanel rightOnTop = new JPanel();
			rightOnTop.setPreferredSize(new Dimension(250,30));
		
		topPanel.add(centerOfTop,BorderLayout.CENTER);
		topPanel.add(leftOnTop,BorderLayout.WEST);
		topPanel.add(rightOnTop,BorderLayout.EAST);
		
		// Add elements to the main frame in the gridLayout //
		add(eastPanel,BorderLayout.EAST);
		add(westPanel,BorderLayout.WEST);
		add(topPanel,BorderLayout.NORTH);
		add(bottomPanel,BorderLayout.SOUTH);
		add(editorPanel,BorderLayout.CENTER);
		
		validate();
		setVisible(true);
	}
}
