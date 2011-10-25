package sim.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class EditorPlayer implements ActionListener {
	JPanel editorPanel;
	JFrame playerFrame;
	JButton record			= new JButton("Record");
	JButton play			= new JButton("Play");
	JButton stop 			= new JButton("Stop");
	public EditorPlayer(JPanel editorPanel){
		this.editorPanel = editorPanel;
		playerFrame = new JFrame("Animation");
		playerFrame.setSize(editorPanel.getWidth(), editorPanel.getHeight());
		

		record			= new JButton("Record");
		play			= new JButton("Play");
		stop 			= new JButton("Stop");
		record.addActionListener(this);
		record.setActionCommand("record");
		play.addActionListener(this);
		play.setActionCommand("play");
		stop.addActionListener(this);
		stop.setActionCommand("stop");
		
		playerFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		playerFrame.setVisible(true);
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}
}
