package sim.editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class EditorPlayer implements ActionListener, ChangeListener, WindowListener {

	static final int FPS_MIN = 1;
	static final int FPS_MAX = 4;
	static final int FPS_INIT = 2;    //initial frames per second
	static final int MAX_FRAMES = 2000;
	static final int MAX_TEMP_FRAMES = 5;
	static final int MAX_WIDTH = 1000;
	static final int MAX_HEIGHT = 1000;

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
	JMenuBar menu;
	JMenu file;
	JMenuItem open;
	
	Timer animTimer;
	private void initMenu(){
		file = new JMenu("File");
		open = new JMenuItem("Open");
		open.addActionListener(this);
		file.add(open);

		menu = new JMenuBar();
		menu.add(file);

		playerFrame.setJMenuBar(menu);
	}
	public EditorPlayer(JPanel editorPanel){
		this.editorPanel = editorPanel;

		framesPerSecond = FPS_INIT;
		playerFrame = new JFrame("Animation");
		playerFrame.setLayout(new BorderLayout());
		if(editorPanel != null){
			if(editorPanel.getWidth()>0 && editorPanel.getHeight()>0){
				playerFrame.setSize(700, 600);
			}	
			else
				playerFrame.setSize(500, 700);
		}
		else
			playerFrame.setSize(500, 500);

		initMenu();
		JPanel buttonPanel = new JPanel(new GridLayout(1,6));
		buttonPanel.setPreferredSize(new Dimension(playerFrame.getWidth(), playerFrame.getHeight()/10));

		record			= new JButton("Record");
		play			= new JButton("Play");
		stop 			= new JButton("Stop");
		pause			= new JButton("Pause");
		stepback		= new JButton("<-Step");
		stepforward		= new JButton("Step->");

		recordInfo = new JLabel("Not recording");

		record.			addActionListener(this);
		play.			addActionListener(this);
		stop.			addActionListener(this);
		pause.			addActionListener(this);
		stepback.		addActionListener(this);
		stepforward.	addActionListener(this);

		buttonPanel.add(record);
		buttonPanel.add(play);
		buttonPanel.add(stepback);
		buttonPanel.add(pause);
		buttonPanel.add(stepforward);
		buttonPanel.add(stop);

		JPanel fpsPanel = new JPanel(new GridLayout(1,3));

		fpsPanel.setPreferredSize(new Dimension(playerFrame.getWidth(), playerFrame.getHeight()/10));

		fps = new JSlider(JSlider.HORIZONTAL,
				FPS_MIN, FPS_MAX, FPS_INIT);
		fps.addChangeListener(this);
		fps.setMajorTickSpacing(1);
		fps.setMinorTickSpacing(1);
		fps.setPaintTicks(true);
		fps.setPaintLabels(true);
		fpsPanel.add(new JLabel("Frames per second:"));		
		fpsPanel.add(fps);
		fpsPanel.add(recordInfo);
		
		JPanel controlPanel = new JPanel(new BorderLayout());
		
		controlPanel.add(fpsPanel, BorderLayout.NORTH);
		controlPanel.add(buttonPanel, BorderLayout.SOUTH);

		drawPanel = new AnimationPanel();
		
		playerFrame.add(controlPanel, BorderLayout.SOUTH);
		
		JScrollPane drawScrollPane = new JScrollPane(drawPanel);
		playerFrame.add(drawScrollPane, BorderLayout.CENTER);

		playerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		playerFrame.setVisible(true);
	}
	private File animationFile = null;
	ObjectOutputStream oos;
	FileOutputStream fos;
	int framesInFile = 0;

	private void saveToTempFile(BufferedImage img){
		byte[] raw = imageToByteArray(img);
		if(raw != null) { 
			try {

				oos.writeObject(raw);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	private BufferedImage getImageFromFile(int currentIndex){
		int count = 0; ;
		try{
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(animationFile));

			Object obj = null;

			while ((obj = ois.readObject()) != null) {
				count ++;
				if (obj instanceof byte[]  && count == currentIndex) {
					InputStream in = new ByteArrayInputStream((byte[])obj);
					ois.close();
					return ImageIO.read(in);
				}

			}
		}catch(Exception e){
			framesInFile = count;	
		}
		return null;
	}
	private byte[] imageToByteArray(BufferedImage img){
		ByteArrayOutputStream baos = new ByteArrayOutputStream( 1000 );

		try{
			ImageIO.write(img, "jpeg",baos );
			baos.flush();
			byte[] array = baos.toByteArray();

			baos.close();

			return array;

		}catch(Exception e){
			return null;
		}
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == open) {
			JFileChooser load = new JFileChooser();
			FileNameExtensionFilter openFilter = new FileNameExtensionFilter("AlgoSim animation files", "agsa");
			load.setMultiSelectionEnabled(false);
			load.setFileFilter(openFilter);
			
			int rval = load.showOpenDialog(null);
			
			if(rval == JFileChooser.APPROVE_OPTION && load.getSelectedFile().exists()){
				animationFile = load.getSelectedFile();
			}
			else if(rval == JFileChooser.CANCEL_OPTION) return;	
			
			
		}
		else if(arg0.getSource() == record){

				JFileChooser jfc = new JFileChooser();
				FileNameExtensionFilter tempFilter = new FileNameExtensionFilter("AlgoSim animation files", "agsa");
				jfc.setMultiSelectionEnabled(false);
				jfc.setFileFilter(tempFilter);
		
				int rval = jfc.showSaveDialog(null);
				
				if(rval == JFileChooser.APPROVE_OPTION && jfc.getSelectedFile().exists()){
					if(JOptionPane.showConfirmDialog(null, "Existing file will be overwritted. Continue?", "Overwrite",JOptionPane.WARNING_MESSAGE)
							== JFileChooser.CANCEL_OPTION) return;
					if(!jfc.getSelectedFile().delete()){
						JOptionPane.showMessageDialog(null, "Could not overwrite file.", "Stopped",JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				else if(rval == JFileChooser.CANCEL_OPTION) return;	
				if(!jfc.getSelectedFile().getAbsolutePath().endsWith(".agsa"))
					animationFile = new File(jfc.getSelectedFile().getAbsolutePath()+".agsa");
				else
					animationFile = jfc.getSelectedFile();
				
				
				try{
					fos = new FileOutputStream(animationFile, true);
					oos = new ObjectOutputStream(fos);
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, "Could not open file.", "Stopped",JOptionPane.ERROR_MESSAGE);

					return;
				}
				
			if(animationFile != null){

				record.setEnabled(false);
				play.setEnabled(false);
				pause.setEnabled(false);
				stepback.setEnabled(false);
				stepforward.setEnabled(false);
				
				animTimer = new Timer(1000/framesPerSecond, this);
				animTimer.start();
				recording = true;
			}
		}
		else if(arg0.getSource() == play){
			
			record.setEnabled(false);
			play.setEnabled(false);
			pause.setEnabled(true);
			stepback.setEnabled(true);
			stepforward.setEnabled(true);
			
			if(animTimer == null){

				drawPanel.startAnimation();
				animTimer = new Timer(1000/framesPerSecond, this);
			}
			animTimer.start();
		}
		else if(arg0.getSource() == pause){

			record.setEnabled(false);
			play.setEnabled(true);
			pause.setEnabled(false);
			stepback.setEnabled(true);
			stepforward.setEnabled(true);
			
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

			record.setEnabled(true);
			play.setEnabled(true);
			pause.setEnabled(false);
			stepback.setEnabled(true);
			stepforward.setEnabled(true);
			
			if(animTimer!=null){
				animTimer.stop();
				animTimer = null;
				recording = false;
				try{
					if (oos != null) oos.close ();
				}catch (Exception e){
					e.printStackTrace();
				}
				recordInfo.setText("STOPPED!");
			}
		}
		else if(arg0.getSource()== animTimer){
			if(recording){
//				imageWidth = (int)editorPanel.getSize().getWidth()>MAX_WIDTH ? MAX_WIDTH :(int)editorPanel.getSize().getWidth();
//				imageHeight = (int)editorPanel.getSize().getHeight()>MAX_HEIGHT ? MAX_HEIGHT :(int)editorPanel.getSize().getHeight();

				
				if(drawPanel.getCurrentIndex()==MAX_FRAMES){

					animTimer.stop();
					recording = false;
					drawPanel.stopAnimation();
					recordInfo.setText("STOPPED!");
					try{
						if (oos != null) oos.close ();
					}catch (Exception e){
						e.printStackTrace();
					}
					drawPanel.revalidate();
					
						JOptionPane.showMessageDialog(null, "The total number of frames allowed has been exceeded.", "Stopped",JOptionPane.ERROR_MESSAGE);
					return;
				}
				BufferedImage bi = new BufferedImage(
						(int)editorPanel.getSize().getWidth(), 
						(int)editorPanel.getSize().getHeight(), 
						BufferedImage.TYPE_USHORT_555_RGB);

				
				editorPanel.paintAll(bi.getGraphics());

//				int scaleWidth = (int)(imageWidth*(4/4f));
//				int scaleHeight = (int)(imageHeight*(4/4f));
//				Image img = bi.getScaledInstance(scaleWidth, scaleHeight, BufferedImage.SCALE_SMOOTH);
//
//				BufferedImage scaled = new BufferedImage(img.getWidth(null), img.getHeight(null),BufferedImage.TYPE_USHORT_555_RGB);
//
//				scaled.getGraphics().drawImage(img,  0,0,null);

				saveToTempFile(bi);
				framesInFile++;
				recordInfo.setText("RECORDING! Frame #"+ drawPanel.getCurrentIndex()+ " of " + MAX_FRAMES);
				drawPanel.step();
			}
			else{

				recordInfo.setText("PLAYING!  Frame #"+ drawPanel.getCurrentIndex());
				
				drawPanel.step();

			}
		}
		playerFrame.repaint();
		playerFrame.validate();
	}
	@Override
	public void stateChanged(ChangeEvent arg0) {
		framesPerSecond = fps.getValue();
		if(animTimer != null)
			animTimer.setDelay(1000/framesPerSecond);

	}
	@SuppressWarnings("serial")
	class AnimationPanel extends JPanel{

		private int currentIndex;
		public int getCurrentIndex() {
			return currentIndex;
		}
		public AnimationPanel(){
		}
		public void step(){
			currentIndex++;
			if(currentIndex >= framesInFile)
				currentIndex = 0;
		}
		public void stepBack(){
			currentIndex--;
			if(currentIndex<0)
				currentIndex = framesInFile;
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
			
			BufferedImage bi = null;

			if(currentIndex>=0 && animationFile != null)
				bi = getImageFromFile(currentIndex);

			if(bi != null){
				//Image img = bi.getScaledInstance(getWidth(), (int)((bi.getHeight())*((getWidth()/2)/(float)bi.getWidth())), BufferedImage.SCALE_SMOOTH);

				//BufferedImage scaled = new BufferedImage(img.getWidth(null), img.getHeight(null),BufferedImage.TYPE_USHORT_555_RGB);

				//scaled.getGraphics().drawImage(img,  0,0,null);

				if(bi != null)
					g.drawImage(bi, 0,0,null);

				setPreferredSize(new Dimension(getWidth(), (int)(bi.getHeight()*(getWidth()/(float)bi.getWidth()))));
			}
			revalidate();
		}

	}
	@Override
	public void windowActivated(WindowEvent arg0) {
	}
	@Override
	public void windowClosed(WindowEvent arg0) {
	}
	@Override
	public void windowClosing(WindowEvent arg0) {
		try{
			if (oos != null) oos.close ();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	@Override
	public void windowDeactivated(WindowEvent arg0) {
	}
	@Override
	public void windowDeiconified(WindowEvent arg0) {
	}
	@Override
	public void windowIconified(WindowEvent arg0) {
	}
	@Override
	public void windowOpened(WindowEvent arg0) {
		
	}
}
