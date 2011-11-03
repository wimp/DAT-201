package sim.gui.elements;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("serial")
public abstract class GuiElement extends JPanel{
// Class variables //
	protected Timer animation;
	public Timer getAnimation() {
		return animation;
	}
	public void startAnimation() {
		if(animation != null){
		animation.start();
		frame = 0;
		}
	}
	protected int frame;
	private int maxFrame = 7;
	
public int getMaxFrame() {
		return maxFrame;
	}
	public void setMaxFrame(int maxFrame) {
		this.maxFrame = maxFrame;
	}
	// Class constructor //
	/**
	 * Class constructor - sets the instance of the class to visible.
	 */
	public GuiElement(){
		setLayout(new GridLayout(1,1));
		setVisible(true);
	}
	public interface GraphicalStructure{
		public GuiElement getGuiElement();
	}
}
