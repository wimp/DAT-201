package sim.functions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import sim.gui.elements.GuiElement;


public abstract class Function extends GuiElement implements ActionListener{

	final int WIDTH = 50;
	final int HEIGHT = 50;
	
	JButton b;
	public Function(String s){
		super();
		b = new JButton(s);
		b.addActionListener(this);
		setSize(WIDTH, HEIGHT);
<<<<<<< HEAD
		add(b);
=======
<<<<<<< HEAD
	}
	public void actionPerformed(ActionEvent e){
		
=======
		add(b);
>>>>>>> origin/master
>>>>>>> b35b16214d42b0d7fdf72e6c8907fd0766841590
	}
}
