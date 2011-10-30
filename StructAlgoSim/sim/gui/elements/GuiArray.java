package sim.gui.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class GuiArray extends GuiElement {
	private Object[][] data;
	ArrayPanel panel;

	public void setData(Object[][] data){
		this.data = data;
	}
	
	public GuiArray(Rectangle bounds,Object[][] data){
		super();

		this.data = data;
		setBounds(bounds);
		
		initGraphics();
	}

	public void initGraphics(){
		panel = new ArrayPanel(data);
		JScrollPane listScroller = new JScrollPane(panel);
		listScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		listScroller.setPreferredSize(new Dimension(getWidth(), getHeight()));
		this.add(listScroller);
	}
	@SuppressWarnings("unused")
	private class ArrayPanel extends JPanel{

		Object[][] data;

		
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
			int elementH = GuiSettings.ARRAYELEMENTHEIGHT;
			int elementW = getWidth()/data[0].length;
			g.clearRect(0, 0, getWidth(), getHeight());
			if(elementH*data.length>getHeight())
				setPreferredSize(new Dimension(getWidth(), elementH*data.length));

			for(int y = 0; y<data.length; y++){
					for(int x = 0; x<data[y].length; x++){
						drawElement(g,data[y][x],x,y,  elementW, elementH, 0);
					}
				}
			}
	}
	
	private void drawElement(Graphics g, Object o,int x,int y,  int elementW, int elementH, int offset){

		String s = (String)o;

		Color c = g.getColor();
		
		if(o ==null) 
			g.setColor( GuiSettings.ARRAYEMPTYCOLOR);
		else 
			g.setColor( GuiSettings.ARRAYELEMENTCOLOR);
		
		g.fillRoundRect(offset+elementW*x,y*elementH, elementW, elementH, 5, 5);
		g.setColor(c);
		
		if(s!=null)
		g.drawString(s,offset+elementW*x+10,y*elementH+elementH-elementH/3);
		
		g.drawRoundRect(offset+elementW*x,y*elementH, elementW, elementH, 5, 5);
		

	}
}
