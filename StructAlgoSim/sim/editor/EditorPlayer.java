package sim.editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
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

	static final int FPS_MIN = 1;
	static final int FPS_MAX = 4;
	static final int FPS_INIT = 2;    //initial frames per second
	static final int MAX_FRAMES = 1000;
	private int framesPerSecond;
	private boolean recording;
	JPanel editorPanel;
	AnimationPanel drawPanel;
	JFrame playerFrame;
	JButton record;
	JButton play;
	JButton stop;
	JButton pause;
	JButton stepback;
	JButton stepforward;
	JSlider fps;
	JLabel recordInfo;
	
	Timer animTimer;
	
	public EditorPlayer(JPanel editorPanel){
		this.editorPanel = editorPanel;
		
		framesPerSecond = FPS_INIT;
		playerFrame = new JFrame("Animation");
		playerFrame.setLayout(new BorderLayout());
		if(editorPanel != null){
			if(editorPanel.getWidth()>0 && editorPanel.getHeight()>0){
		playerFrame.setSize(editorPanel.getWidth(), editorPanel.getHeight());
			}	
			else
				playerFrame.setSize(500, 700);
		}
		else
			playerFrame.setSize(500, 500);
			
		JPanel buttonPanel = new JPanel(new GridLayout(2,6));
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
		pause			= new JButton("Pause");
		stepback		= new JButton("<-Step");
		stepforward		= new JButton("Step->");
		
		recordInfo = new JLabel("Not recording");
		
		record.addActionListener(this);
		play.addActionListener(this);
		stop.addActionListener(this);
		pause.addActionListener(this);
		stepback.addActionListener(this);
		stepforward.addActionListener(this);

		buttonPanel.add(record);
		buttonPanel.add(play);
		buttonPanel.add(stepback);
		buttonPanel.add(pause);
		buttonPanel.add(stepforward);
		buttonPanel.add(stop);
		buttonPanel.add(new JLabel(""));	
		buttonPanel.add(new JLabel("Frames per second:"));		
		buttonPanel.add(fps);
		buttonPanel.add(recordInfo);
		buttonPanel.add(new JLabel(""));
		buttonPanel.add(new JLabel(""));

		playerFrame.add(buttonPanel, BorderLayout.NORTH);
		
		drawPanel = new AnimationPanel();
		
		playerFrame.add(drawPanel, BorderLayout.CENTER);
		
		playerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		playerFrame.setVisible(true);
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == record){
			drawPanel.setImages(new Vector<BufferedImage>());
			animTimer = new Timer(1000/framesPerSecond, this);
			animTimer.start();
			recording = true;
		}
		else if(arg0.getSource() == play){
			if(animTimer == null){
				drawPanel.startAnimation();
				animTimer = new Timer(1000/framesPerSecond, this);
			}
			animTimer.start();
		}
		else if(arg0.getSource() == pause){
			if(animTimer != null)
			if(animTimer.isRunning()) animTimer.stop();

			recordInfo.setText("PAUSED!  Frame #"+ drawPanel.getCurrentIndex());
		}
		else if(arg0.getSource() == stepback){
			drawPanel.stepBack();
			if(animTimer != null)
			if(animTimer.isRunning()) animTimer.stop();

			recordInfo.setText("PAUSED!  Frame #"+ drawPanel.getCurrentIndex());

		}
		else if(arg0.getSource() == stepforward){
			drawPanel.step();
			if(animTimer != null)
			if(animTimer.isRunning()) animTimer.stop();

			recordInfo.setText("PAUSED!  Frame #"+ drawPanel.getCurrentIndex());

		}
		else if(arg0.getSource() == stop){
			animTimer.stop();
			animTimer = null;
			recording = false;

			recordInfo.setText("STOPPED!");
		}
		else if(arg0.getSource()== animTimer){
			if(recording){
				BufferedImage bi = new BufferedImage((int)editorPanel.getSize().getWidth(), (int)editorPanel.getSize().getHeight(), BufferedImage.TYPE_USHORT_555_RGB);
				editorPanel.paintAll(bi.getGraphics());
				System.out.println(drawPanel.getImages().capacity());
				drawPanel.getImages().add(bi);
				recordInfo.setText("RECORDING! Frame #"+ drawPanel.getImages().size());
				drawPanel.step();
				if(drawPanel.getImages().size()==MAX_FRAMES){
					animTimer.stop();
					recording = false;
					drawPanel.stopAnimation();
					recordInfo.setText("STOPPED!");
				}
			}
			else{
				drawPanel.step();
				recordInfo.setText("PLAYING!  Frame #"+ drawPanel.getCurrentIndex());

				if(drawPanel.getFrame() == drawPanel.getImages().size()){

					recordInfo.setText("STOPPED!");
					animTimer.stop();
					drawPanel.stopAnimation();
				}

			}
		}
		playerFrame.repaint();
	}
	@Override
	public void stateChanged(ChangeEvent arg0) {
		framesPerSecond = fps.getValue();
		
	}
	class AnimationPanel extends JPanel{
		private Vector<BufferedImage> images;
		private int currentIndex;
		public int getCurrentIndex() {
			return currentIndex;
		}
		public Vector<BufferedImage> getImages() {
			return images;
		}
		public void setImages(Vector<BufferedImage> images) {
			this.images = images;
		}
		public AnimationPanel(){
			images = new Vector<BufferedImage>();
		}
		public void step(){
			currentIndex++;
			if(currentIndex>=images.size())
				currentIndex = 0;
		}
		public void stepBack(){
			currentIndex--;
			if(currentIndex<0)
				currentIndex = images.size()-1;
		}
		public int getFrame(){
			return currentIndex;
		}
		public void stopAnimation(){
			currentIndex = 0;			
		}
		public void startAnimation(){
			currentIndex = 0;			
		}
		@Override
		public void paintComponent(Graphics g){
			g.fillRect(0, 0, getWidth(), getHeight());
			if(images.size()>currentIndex)
			g.drawImage(images.get(currentIndex), 0,0,null);
		}
		
	}
}