package sim.gui.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

@SuppressWarnings("serial")
public class GuiArray extends GuiElement {
	private Vector<Object> data;
	JList list;
	ArrayPanel panel;
	
	public void setData(Vector<Object> data){
		list.setListData(data);
	}
	
	public int getListElementIndex(){
		return list.getSelectedIndex();
	}
	
	public GuiArray(Rectangle bounds,Vector<Object> data){
		super();
		
		this.data = data;
		setBounds(bounds);
		
		initGraphics();
	}
	
	public void initGraphics(){
		panel = new ArrayPanel(data);
		JScrollPane listScroller = new JScrollPane(panel);
		listScroller.setPreferredSize(new Dimension(getWidth(), getHeight()));
		this.add(listScroller);
	}
	private class ArrayPanel extends JPanel{

		Vector<Object> data;
		
		public Vector<Object> getData() {
				return data;
			}
			public void setData(Vector<Object> data) {
				this.data = data;
			}
			public ArrayPanel(Vector<Object> data){
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
					if(data.get(i) instanceof Vector<?>){
						Vector<Object> v = (Vector<Object>)data.get(i);
						for(int j = 0; j<v.size(); j++){
							drawElement(g, v.get(j),j,  elementW, elementH, elementW);
						}
					}
					else
					{
						drawElement(g, data.get(i),i,  elementW, elementH, 0);
					}
				}
				//revalidate();
			}
		}
		private void drawElement(Graphics g, Object o,int index,  int elementW, int elementH, int offset){

				String s = (String)o;
				
				Color c = g.getColor();
				
				g.setColor(o==data.get(data.size()-1) ?  GuiSettings.STACKTOPCOLOR : GuiSettings.STACKELEMENTCOLOR);
				
				
				int v=g.getFontMetrics(getFont()).charsWidth(s.toCharArray(), 0, s.toCharArray().length)+20;

				elementW = v<GuiSettings.STACKELEMENTWIDTH ? GuiSettings.STACKELEMENTWIDTH : v;

				g.fillRoundRect(offset, getHeight()-index*elementH-elementH, elementW, elementH, 5, 5);
				g.setColor(c);
				g.drawString(s,offset+10, getHeight()-index*elementH-elementH/3);
				g.drawRoundRect(offset, getHeight()-index*elementH-elementH, elementW, elementH, 5, 5);
				
			}
}
