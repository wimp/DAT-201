package sim.editor;

import java.awt.BorderLayout;
import java.awt.Color;
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
		
		// Add elements to the top panel //
		topPanel.setLayout(new GridLayout(2,1));
		topPanel.add(stack);
		topPanel.add(array);
		topPanel.add(list);
		topPanel.add(add);
		topPanel.add(remove);
		topPanel.add(insert);
		topPanel.add(push);
		topPanel.add(pop);
		topPanel.add(variable);
		topPanel.add(moveChar);
		topPanel.add(link);
		topPanel.add(select);
		topPanel.add(delete);
		
		// Add elements to the main frame in the gridLayout //
		add(topPanel,BorderLayout.NORTH);
		add(editorPanel,BorderLayout.CENTER);
		
		validate();
		setVisible(true);
	}
}
