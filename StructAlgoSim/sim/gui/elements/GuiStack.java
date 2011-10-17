package sim.gui.elements;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * The graphical element of the {@link sim.structures.Stack} stack class
 */
@SuppressWarnings("serial")
public class GuiStack extends GuiElement {
// Class variables //
	StackPanel stackPanel;
// Getters and setters //
	
	public GuiStack(Rectangle bounds,Vector<Object> data){
		super();
		setBounds(bounds);
		initGraphics(data);
	}
	private void initGraphics(Vector<Object> data){
		stackPanel = new StackPanel(data);
		JScrollPane listScroller = new JScrollPane(stackPanel);
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
			int elementH = getHeight()/data.size();
			int elementW = getWidth();
			
			for(int i = 0; i<data.size(); i++){
				String s = (String)data.get(i);
				
				g.drawRoundRect(i*elementH, 0, elementW, elementH, 5, 5);
				g.drawString(s, i*elementH, 0);
			}
		}
	}
}
