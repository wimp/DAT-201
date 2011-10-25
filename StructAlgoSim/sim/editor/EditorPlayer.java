package sim.editor;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class EditorPlayer implements ActionListener, ChangeListener {

	static final int FPS_MIN = 0;
	static final int FPS_MAX = 4;
	static final int FPS_INIT = 2;    //initial frames per second

	private int framesPerSecond;
	private boolean recording;
	JPanel editorPanel;
	JPanel drawPanel;
	JFrame playerFrame;
	JButton record;
	JButton play;
	JButton stop;
	JSlider fps;
	
	Vector<BufferedImage> images;
	Timer animTimer;
	
	public EditorPlayer(JPanel editorPanel){
		this.editorPanel = editorPanel;
		
		playerFrame = new JFrame("Animation");
		if(editorPanel != null){
			if(editorPanel.getWidth()>0 && editorPanel.getHeight()>0){
		playerFrame.setSize(editorPanel.getWidth(), editorPanel.getHeight());
			}	
			else
				playerFrame.setSize(500, 500);
		}
		else
			playerFrame.setSize(500, 500);
			
		JPanel buttonPanel = new JPanel(new GridLayout(2,3));
		buttonPanel.setPreferredSize(new Dimension(playerFrame.getWidth(), playerFrame.getHeight()/3));

		fps = new JSlider(JSlider.HORIZONTAL,
                FPS_MIN, FPS_MAX, FPS_INIT);
		fps.addChangeListener(this);
		fps.setMajorTickSpacing(1);
		fps.setMinorTickSpacing(1);
		fps.setPaintTicks(true);
		fps.setPaintLabels(true);

		record			= new JButton("Record");
		play			= new JButton("Play");
		stop 			= new JButton("Stop");
		
		record.addActionListener(this);
		record.setActionCommand("record");
		play.addActionListener(this);
		play.setActionCommand("play");
		stop.addActionListener(this);
		stop.setActionCommand("stop");

		buttonPanel.add(record);
		buttonPanel.add(play);
		buttonPanel.add(stop);
		buttonPanel.add(new JLabel("Frames per second:"));		
		buttonPanel.add(fps);

		playerFrame.add(buttonPanel);
		playerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		playerFrame.setVisible(true);
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == record){
			images = new Vector<BufferedImage>();
			animTimer = new Timer(1000/framesPerSecond, this);
			animTimer.start();
			recording = true;
		}
		else if(arg0.getSource() == play){
			
		}
		else if(arg0.getSource() == stop){
			animTimer.stop();
			recording = false;
		}
		else if(arg0.getSource()== animTimer){
			BufferedImage bi = new BufferedImage((int)editorPanel.getSize().getWidth(), (int)editorPanel.getSize().getHeight(), BufferedImage.TYPE_INT_ARGB);
			editorPanel.paintAll(bi.getGraphics());
			images.add(bi);
		}
	}
	@Override
	public void stateChanged(ChangeEvent arg0) {
		framesPerSecond = fps.getValue();
		
	}
}
