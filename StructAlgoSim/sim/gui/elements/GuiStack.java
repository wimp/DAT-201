package sim.gui.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

/**
 * The graphical element of the {@link sim.structures.Stack} stack class
 */
@SuppressWarnings("serial")
public class GuiStack extends GuiElement{
	// Class variables //
	private StackPanel stackPanel;
	private boolean removed;
	private boolean added;
	private String recent;
	// Getters and setters //
	public void setAdded(String changed){
		added = true;
		removed = false;
		recent = changed;
	}
	public void setRemoved(String changed){
		removed = true;
		added = false;
		recent = changed;
	}
	//Class constructor//
	public GuiStack(Rectangle bounds,Vector<Object> data){
		setBounds(bounds);
		initGraphics(data);
	}
	//Class methods//
	private void initGraphics(Vector<Object> data){
		stackPanel = new StackPanel(data);
		JScrollPane listScroller = new JScrollPane(stackPanel);
		listScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		stackPanel.setPreferredSize(new Dimension(getWidth(), getHeight()-5));
		listScroller.setPreferredSize(new Dimension(getWidth(), getHeight()));
		this.add(listScroller);
	}
	public void startAnimation() {
		currentFrame = 0;
		animation.start();
	}
	public void stopAnimation(){
		animation.stop();
		added = false;
		if(removed){
			removed = false;
		}
		recent = null;
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==animation){
			currentFrame++;
			if(currentFrame > getMaxFrame()){
				stopAnimation();
			}
			repaint();
		}
	}
	//Nested classes and interfaces//
	@SuppressWarnings("unused")
	private class StackPanel extends JPanel{

		private Vector<Object> data;
		
		public Vector<Object> getData() {
			return data;
		}
		public void setData(Vector<Object> data) {
			this.data = data;
		}
		public StackPanel(Vector<Object> data){
			this.data = data;
		}
		@Override
		public void paintComponent(Graphics g){
			if(!GuiSettings.isAnimated && animation.isRunning())
				stopAnimation();

			int elementH = GuiSettings.STACKELEMENTHEIGHT;
			int elementW = getWidth();
			int preferredWidth = 0;
			int preferredHeight = 0;

			g.clearRect(0, 0, getWidth(), getHeight());

			Color c = g.getColor();
			if(elementH*data.size()+elementH*2>getHeight() || elementH*data.size()+elementH*2<getHeight())
				preferredHeight = elementH*data.size()+elementH*2;
			else
				preferredHeight = getHeight()-10;

			for(int i = 0; i<data.size(); i++){
				String s = (String)data.get(i);

				if(s == recent && added) continue;

				g.setColor(i==data.size()-1 ?  GuiSettings.STACKTOPCOLOR : GuiSettings.STACKELEMENTCOLOR);


				int v=g.getFontMetrics(getFont()).charsWidth(s.toCharArray(), 0, s.toCharArray().length)+20;

				elementW = v<GuiSettings.STACKELEMENTWIDTH ? GuiSettings.STACKELEMENTWIDTH : v;

				if(elementW>preferredWidth)
					preferredWidth = elementW;

				g.fillRoundRect(0, getHeight()-i*elementH-elementH, elementW, elementH, 5, 5);
				g.setColor(c);
				g.drawString(s,10, getHeight()-i*elementH-elementH/3);
				g.drawRoundRect(0, getHeight()-i*elementH-elementH, elementW, elementH, 5, 5);
			}
			if(recent!=null){
				int v=g.getFontMetrics(getFont()).charsWidth(recent.toCharArray(), 0, recent.toCharArray().length)+20;

				elementW = v<GuiSettings.STACKELEMENTWIDTH ? GuiSettings.STACKELEMENTWIDTH : v;
				if(elementW>preferredWidth)
					preferredWidth = elementW;

				if(added){
					g.setColor(GuiSettings.STACKADDEDCOLOR);
					g.fillRoundRect(0, ((getHeight()-data.size()*elementH)/getMaxFrame())*currentFrame, elementW, elementH, 5, 5);
					g.setColor(c);
					g.drawString(recent,10, ((getHeight()-data.size()*elementH)/getMaxFrame())*currentFrame+elementH-elementH/3);
					g.drawRoundRect(0, ((getHeight()-data.size()*elementH)/getMaxFrame())*currentFrame, elementW, elementH, 5, 5);
				}
				else if(removed){
					g.setColor(GuiSettings.STACKADDEDCOLOR);
					g.fillRoundRect(0, ((getHeight()-data.size()*elementH)/getMaxFrame())*(getMaxFrame()-currentFrame)-elementH, elementW, elementH, 5, 5);
					g.setColor(c);
					g.drawString(recent,10, ((getHeight()-data.size()*elementH)/getMaxFrame())*(getMaxFrame()-currentFrame)-elementH/3);
					g.drawRoundRect(0, ((getHeight()-data.size()*elementH)/getMaxFrame())*(getMaxFrame()-currentFrame)-elementH, elementW, elementH, 5, 5);
				}
			}
			if(preferredWidth+10 !=getWidth() || preferredHeight+10 !=getHeight()){
				setPreferredSize(new Dimension(preferredWidth+10, preferredHeight+10));
			}
			revalidate();
		}
	}
}