package sim.gui.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class GuiQueue extends GuiElement {
	// Class variables //
		QueuePanel queuePanel;
	// Getters and setters //
		
		public GuiQueue(Rectangle bounds,Vector<Object> data){
			super();
			setBounds(bounds);
			initGraphics(data);
		}
		private void initGraphics(Vector<Object> data){
			queuePanel = new QueuePanel(data);
			JScrollPane listScroller = new JScrollPane(queuePanel);
			listScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

			queuePanel.setPreferredSize(new Dimension(getWidth(), getHeight()));
			listScroller.setPreferredSize(new Dimension(getWidth(), getHeight()));
			this.add(listScroller);
		}
		
		private class QueuePanel extends JPanel{
		Vector<Object> data;	
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
				int elementH = GuiSettings.QUEUEELEMENTHEIGHT;
				int elementW = GuiSettings.QUEUEELEMENTWIDTH;
				
				g.clearRect(0, 0, getWidth(), getHeight());
				
				if(elementH*data.size()>getHeight())
					setPreferredSize(new Dimension(elementW*data.size(),getHeight()));
				
				for(int i = 0; i<data.size(); i++){
					String s = (String)data.get(i);
					
					Color c = g.getColor();
					
					if(i ==data.size()-1) 
						g.setColor(GuiSettings.QUEUETOPCOLOR);
					else if(i==0)
						g.setColor(GuiSettings.QUEUEENDCOLOR);
					else 
						g.setColor(GuiSettings.QUEUEELEMENTCOLOR);
					 
					int v=g.getFontMetrics(getFont()).getHeight()-g.getFontMetrics(getFont()).getHeight()/4;
					elementH = v*s.length()<GuiSettings.QUEUEELEMENTHEIGHT ? GuiSettings.QUEUEELEMENTHEIGHT : v*s.length();
					g.fillRoundRect(elementW*i,0, elementW, elementH, 5, 5);
					g.setColor(c);
					g.drawRoundRect(elementW*i,0, elementW, elementH, 5, 5);
					  v=g.getFontMetrics(getFont()).getHeight()-g.getFontMetrics(getFont()).getHeight()/4;
					  int j = 0;
					  int k = s.length();
					  while(j < k+1) {
					    if (j == k) 
					       g.drawString(s.substring(j),i*elementW+5, 10+(j*v));
					    else
					       g.drawString(s.substring(j,j+1),i*elementW+5, 10+(j*v));
					    j++;
					  }
					
				}
				revalidate();
			}
		}
}
