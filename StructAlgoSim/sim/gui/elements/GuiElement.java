package sim.gui.elements;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * GuiElement is an abstract class used by every other GuiStructure to define a few common methods and the JPanel
 * extension.
 */
@SuppressWarnings("serial")
public abstract class GuiElement extends JPanel implements ActionListener{
	// Class variables //
	protected Timer animation;
	protected int currentFrame;
	private int maxFrame = GuiSettings.ANIMATIONFRAMES;

	//Getters and setters//
	public int getMaxFrame() {
		return maxFrame;
	}
	public void setMaxFrame(int maxFrame) {
		this.maxFrame = maxFrame;
	}
	public Timer getAnimation() {
		return animation;
	}

	//Class constructor//
	/**
	 * Class constructor - sets the instance of the class to visible.
	 */
	public GuiElement(){
		animation = new Timer(500, this);
		setLayout(new GridLayout(1,1));
		setVisible(true);
	}

	//Class methods//
	abstract public void startAnimation();
	abstract public void stopAnimation();

	//Nested classes and interfaces//
	public interface GraphicalStructure{
		public GuiElement getGuiElement();
	}
}
