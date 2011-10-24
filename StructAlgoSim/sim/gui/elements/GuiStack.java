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
import javax.xml.crypto.Data;

/**
 * The graphical element of the {@link sim.structures.Stack} stack class
 */
@SuppressWarnings("serial")
public class GuiStack extends GuiElement implements ActionListener{
// Class variables //
	StackPanel stackPanel;
	boolean removed;
	boolean added;
	String recent;
// Getters and setters //
	
	public GuiStack(Rectangle bounds,Vector<Object> data){
		super();
		
		animation = new Timer(750,this);
		
		setBounds(bounds);
		initGraphics(data);
	}
	public void setAdded(String changed){
		added = true;
		recent = changed;
	}
	public void setRemoved(String changed){
		removed = true;
		recent = changed;
	}
	private void initGraphics(Vector<Object> data){
		stackPanel = new StackPanel(data);
		JScrollPane listScroller = new JScrollPane(stackPanel);
		listScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		stackPanel.setPreferredSize(new Dimension(getWidth(), getHeight()));
		listScroller.setPreferredSize(new Dimension(getWidth(), getHeight()));
		this.add(listScroller);
	}
	private class StackPanel extends JPanel{

	Vector<Object> data;
	
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
			int elementH = GuiSettings.STACKELEMENTHEIGHT;
			int elementW = getWidth();//GuiSettings.STACKELEMENTWIDTH;
			g.clearRect(0, 0, getWidth(), getHeight());
			if(elementH*data.size()>getHeight())
				setPreferredSize(new Dimension(getWidth(), elementH*data.size()));
			for(int i = 0; i<data.size(); i++){
				String s = (String)data.get(i);
				
				Color c = g.getColor();
				
				g.setColor(i==data.size()-1 ?  GuiSettings.STACKTOPCOLOR : GuiSettings.STACKELEMENTCOLOR);
				
				
				int v=g.getFontMetrics(getFont()).charsWidth(s.toCharArray(), 0, s.toCharArray().length)+20;
				
				elementW = v<GuiSettings.STACKELEMENTWIDTH ? GuiSettings.STACKELEMENTWIDTH : v;
				if(added && s == recent){
					g.fillRoundRect(0, (getHeight()/MAXFRAME)*frame-i*elementH-elementH, elementW, elementH, 5, 5);
					g.setColor(c);
					g.drawString(s,10, (getHeight()/MAXFRAME)*frame-i*elementH-elementH/3);
					g.drawRoundRect(0, (getHeight()/MAXFRAME)*frame-i*elementH-elementH, elementW, elementH, 5, 5);
				}
				else if(removed && s == recent){
					g.fillRoundRect(0, (getHeight()/MAXFRAME)*(MAXFRAME-frame)-i*elementH-elementH, elementW, elementH, 5, 5);
					g.setColor(c);
					g.drawString(s,10, (getHeight()/MAXFRAME)*(MAXFRAME-frame)-i*elementH-elementH/3);
					g.drawRoundRect(0, (getHeight()/MAXFRAME)*(MAXFRAME-frame)-i*elementH-elementH, elementW, elementH, 5, 5);
				}
				else{
				g.fillRoundRect(0, getHeight()-i*elementH-elementH, elementW, elementH, 5, 5);
				g.setColor(c);
				g.drawString(s,10, getHeight()-i*elementH-elementH/3);
				g.drawRoundRect(0, getHeight()-i*elementH-elementH, elementW, elementH, 5, 5);
				}
			}
			//revalidate();
		}
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==animation){
			frame++;
			if(frame > MAXFRAME){
				frame = 0;
				animation.stop();
				added = false;
				if(removed){
					stackPanel.getData().remove(recent);
					removed = false;
				}
				repaint();
			}
			repaint();
		}
	}
}
