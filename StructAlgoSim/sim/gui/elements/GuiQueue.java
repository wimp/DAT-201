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

@SuppressWarnings("serial")
public class GuiQueue extends GuiElement implements ActionListener{
	// Class variables //
	private QueuePanel queuePanel;
	private boolean removed;
	private boolean added;
	private String recent;
	// Getters and setters //
	public void setAdded(String changed){
		added = true;
		recent = changed;
	}
	public void setRemoved(String changed){
		removed = true;
		recent = changed;
	}
	public GuiQueue(Rectangle bounds,Vector<Object> data){
		super();

		animation = new Timer(300,this);
		setBounds(bounds);
		initGraphics(data);
	}
	private void initGraphics(Vector<Object> data){
		queuePanel = new QueuePanel(data);
		JScrollPane listScroller = new JScrollPane(queuePanel);
		listScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

		queuePanel.setPreferredSize(new Dimension(getWidth()-10, getHeight()));
		listScroller.setPreferredSize(new Dimension(getWidth(), getHeight()));
		this.add(listScroller);
	}
	public void stopAnimation(){
		frame = 0;
		animation.stop();
		added = false;
		if(removed){
			removed = false;
		}
		repaint();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==animation){
			frame++;
			if(frame > getMaxFrame()){
				stopAnimation();
			}
			repaint();
		}
	}
	@SuppressWarnings("unused")
	private class QueuePanel extends JPanel{
		Vector<Object> data;
		int xoffset = GuiSettings.QUEUEELEMENTWIDTH*2;
		int yoffset = GuiSettings.QUEUEELEMENTHEIGHT/4;
		
		public Vector<Object> getData() {
			return data;
		}
		
		public void setData(Vector<Object> data) {
			this.data = data;
		}
		public QueuePanel(Vector<Object> data){
			this.data = data;
		}
		@Override
		public void paintComponent(Graphics g){
			if(!GuiSettings.isAnimated && animation.isRunning())
				stopAnimation();
			
			int elementH = GuiSettings.QUEUEELEMENTHEIGHT;
			int elementW = GuiSettings.QUEUEELEMENTWIDTH;

			g.clearRect(0, 0, getWidth(), getHeight());

			if(elementH*data.size()>getHeight())
				setPreferredSize(new Dimension(elementW*data.size()+xoffset*2,getHeight()));

			
			String f = "FRONT";
			String b = "BACK";

			Color c = g.getColor();
			
			int[] fx = new int[3];
			int[] fy = new int[3];
			fx[0] = xoffset-20;
			fx[1] = xoffset-elementW-20;
			fx[2] = xoffset-20;
			fy[0] = yoffset;
			fy[1] = yoffset+elementH/2;
			fy[2] = yoffset+elementH;
			
			int[] bx = new int[3];
			int[] by = new int[3];
			bx[0] = data.size()*elementW+2*elementW+40;
			bx[1] = data.size()*elementW+elementW+40;
			bx[2] = data.size()*elementW+2*elementW+40;
			by[0] = yoffset;
			by[1] = yoffset+elementH/2;
			by[2] = yoffset+elementH;
			
			g.setColor(GuiSettings.QUEUETOPCOLOR);
			g.fillPolygon(fx, fy, fx.length);
			g.setColor(GuiSettings.QUEUEENDCOLOR);
			g.fillPolygon(bx, by, fx.length);
			g.setColor(c);
			g.drawPolygon(fx, fy, fx.length);
			g.drawPolygon(bx, by, bx.length);

			int j = 0;
			int k = f.length();
			

			int v=g.getFontMetrics(getFont()).getHeight()-g.getFontMetrics(getFont()).getHeight()/4;

			v=g.getFontMetrics(getFont()).getHeight()-g.getFontMetrics(getFont()).getHeight()/4;
			while(j < k+1) {
				if (j == k) 
					g.drawString(f.substring(j),xoffset-elementW, yoffset+10+(j*v));
				else
					g.drawString(f.substring(j,j+1),xoffset-elementW, yoffset+10+(j*v));
				j++;
			}
			j = 0;
			k = b.length();
			while(j < k+1) {
				if (j == k) 
					g.drawString(b.substring(j),data.size()*elementW+2*elementW+30, yoffset+10+(j*v));
				else
					g.drawString(b.substring(j,j+1),data.size()*elementW+2*elementW+30, yoffset+10+(j*v));
				j++;
			}
			
			if(removed){
				g.setColor(GuiSettings.QUEUEELEMENTCOLOR);

				g.fillRoundRect(xoffset-(xoffset/getMaxFrame())*frame-elementW,yoffset, elementW, elementH, 5, 5);
				g.setColor(c);
				g.drawRoundRect(xoffset-(xoffset/getMaxFrame())*frame-elementW,yoffset, elementW, elementH, 5, 5);
				v=g.getFontMetrics(getFont()).getHeight()-g.getFontMetrics(getFont()).getHeight()/4;
				j = 0;
				k = recent.length();
				while(j < k+1) {
					if (j == k) 
						g.drawString(recent.substring(j),xoffset-(xoffset/getMaxFrame())*frame+5-elementW, yoffset+10+(j*v));
					else
						g.drawString(recent.substring(j,j+1),xoffset-(xoffset/getMaxFrame())*frame+5-elementW, yoffset+10+(j*v));
					j++;
				}
			}
			for(int i = 0; i<data.size(); i++){
				String s = (String)data.get(i);
				elementH = v*s.length()<GuiSettings.QUEUEELEMENTHEIGHT ? GuiSettings.QUEUEELEMENTHEIGHT : v*s.length();


				if(i ==data.size()-1) 
					g.setColor(GuiSettings.QUEUEENDCOLOR);
				else if(i==0)
					g.setColor(GuiSettings.QUEUETOPCOLOR);
				else 
					g.setColor(GuiSettings.QUEUEELEMENTCOLOR);

				if(added && s == recent){
					g.fillRoundRect(((getWidth()-elementW*i)/getMaxFrame())*(getMaxFrame()-frame)+elementW*i+xoffset,yoffset, elementW, elementH, 5, 5);
					g.setColor(c);
					g.drawRoundRect(((getWidth()-elementW*i)/getMaxFrame())*(getMaxFrame()-frame)+elementW*i+xoffset,yoffset, elementW, elementH, 5, 5);
					v=g.getFontMetrics(getFont()).getHeight()-g.getFontMetrics(getFont()).getHeight()/4;
					j = 0;
					k = s.length();
					while(j < k+1) {
						if (j == k) 
							g.drawString(s.substring(j),((getWidth()-elementW*i)/getMaxFrame())*(getMaxFrame()-frame)+i*elementW+5+xoffset, yoffset+10+(j*v));
						else
							g.drawString(s.substring(j,j+1),((getWidth()-elementW*i)/getMaxFrame())*(getMaxFrame()-frame)+i*elementW+5+xoffset, yoffset+10+(j*v));
						j++;
					}
				}
				else{
					g.fillRoundRect(elementW*i+xoffset,yoffset, elementW, elementH, 5, 5);
					g.setColor(c);
					g.drawRoundRect(elementW*i+xoffset,yoffset, elementW, elementH, 5, 5);
					v=g.getFontMetrics(getFont()).getHeight()-g.getFontMetrics(getFont()).getHeight()/4;
					j = 0;
					k = s.length();
					while(j < k+1) {
						if (j == k) 
							g.drawString(s.substring(j),i*elementW+5+xoffset, yoffset+10+(j*v));
						else
							g.drawString(s.substring(j,j+1),i*elementW+5+xoffset, yoffset+10+(j*v));
						j++;
					}
				}

			}
			revalidate();
		}
	}
}
