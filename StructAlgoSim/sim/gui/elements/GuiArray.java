package sim.gui.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class GuiArray extends GuiElement implements ActionListener{
	private Object[][] data;
	private Object changed;

	private ArrayPanel panel;
	public Object getChanged() {
		return changed;
	}
	public void setChanged(Object changed) {
		this.changed = changed;
	}
	public void setData(Object[][] data){
		this.data = data;
	}
	public void stopAnimation() {
		frame = 0;
		animation.stop();
		changed = null;
	}
	public GuiArray(Rectangle bounds,Object[][] data){
		super();
		animation = new Timer(200, this);
		changed = null;
		this.data = data;
		setBounds(bounds);

		initGraphics();
	}

	public void initGraphics(){
		panel = new ArrayPanel(data);

		JScrollPane listScroller = new JScrollPane(panel);
		listScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		//int v=g.getFontMetrics(getFont()).charsWidth(s.toCharArray(), 0, s.toCharArray().length)+20;

		panel.setPreferredSize(new Dimension(80 * data[0].length, getHeight()));
		this.add(listScroller);
	}
	private void drawElement(Graphics g, Object o,int x,int y,  int elementW, int elementH, int offset){

		String s = (String)o;

		Color c = g.getColor();

		if(o ==null) 
			g.setColor( GuiSettings.ARRAYEMPTYCOLOR);
		else if(o == changed)
			g.setColor( GuiSettings.ARRAYCHANGEDCOLOR);
		else
			g.setColor( GuiSettings.ARRAYELEMENTCOLOR);
		
		g.fillRoundRect(offset+elementW*x,y*elementH, elementW, elementH, 5, 5);
		g.setColor(c);

		if(s!=null)
			g.drawString(s,offset+elementW*x+10,y*elementH+elementH-elementH/3);

		g.drawRoundRect(offset+elementW*x,y*elementH, elementW, elementH, 5, 5);


	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == animation){
			frame ++;
			if(frame >getMaxFrame()){
				stopAnimation();
				repaint();
			}
		}
		
	}	
	
	@SuppressWarnings("unused")
	private class ArrayPanel extends JPanel{

		private Object[][] data;


		public Object[][] getData() {
			return data;
		}
		public void setData(Object[][] data) {
			this.data = data;
		}
		public ArrayPanel(Object[][] data){
			this.data = data;
		}

		@Override
		public void paintComponent(Graphics g){
			if(!GuiSettings.isAnimated && animation.isRunning()){
				stopAnimation();
			}
			int elementH = GuiSettings.ARRAYELEMENTHEIGHT;
			int elementW =80;
			g.clearRect(0, 0, getWidth(), getHeight());
			if(elementH*data.length>getHeight())
				setPreferredSize(new Dimension(getWidth(), elementH*data.length));

			for(int y = 0; y<data.length; y++){
				for(int x = 0; x<data[y].length; x++){
					drawElement(g,data[y][x],x,y,  elementW, elementH, 0);
				}
			}
			revalidate();
		}

	}

}
