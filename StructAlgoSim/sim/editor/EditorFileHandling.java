package sim.editor;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Vector;

import javax.swing.JComponent;

import sim.editor.EditorListener.ElementType;
import sim.functions.*;
import sim.gui.elements.*;
import sim.structures.*;

class EditorFileHandling {
	private static final String MAIN_SEPARATOR 	= "#¤%";
	private static final String SUB_SEPARATOR	= "£$€";
	private static final String ATTR_SEPARATOR	= "µ!@";
	private static final String BYTE_SEPARATOR	= "|";
	
	/**
	 * File format:
	 * elementID MAIN_SEPARATOR 
	 * bounds MAIN_SEPARATOR 
	 * elementName MAIN_SEPARATOR
	 * elementLinks MAIN_SEPARATOR
	 * elementAttributes LINE_SEPARATOR
	 * 
	 * @param filePath
	 * @param elements
	 * @param guiElements
	 */
	public static void saveFile(String filePath, Vector<Object> elements, Vector<GuiElement> guiElements){
	// Converting path if necessary
		filePath = filePath.endsWith(".ags") ? filePath : filePath + ".ags";
		try{
			FileWriter fw 		= new FileWriter(filePath);
			BufferedWriter out 	= new BufferedWriter(fw);
		
		
		// Starting main loop for generating the save file
			for(int i = 0; i < elements.size(); i++){
				Object currentElement 			= elements.get(i);
				GuiElement currentGuiElement 	= guiElements.get(i);
				int currentId 					= i;
				String bounds 					= currentGuiElement.getX() + 
												  SUB_SEPARATOR + 
												  currentGuiElement.getY() + 
												  SUB_SEPARATOR + 
												  currentGuiElement.getWidth() + 
												  SUB_SEPARATOR + 
												  currentGuiElement.getHeight();
				String elementName				= currentElement.getClass().getSimpleName();
				
			// Set up links and attributes
				String links 					= generateLinksString(currentElement, elements);
				String attributes 				= generateAttributesString(currentElement, elements);
				
			// Set up the line representing this element
				String saveLine					= Integer.toString(currentId) 	+ MAIN_SEPARATOR + 
												  bounds 						+ MAIN_SEPARATOR + 
												  elementName 					+ MAIN_SEPARATOR + 
												  links 						+ MAIN_SEPARATOR + 
												  attributes;
				
					out.write(saveLine);
					out.write(System.getProperty("line.separator"));
					out.flush();
					links = "";
					attributes = "";
					saveLine ="";
				
			}
			out.close();
		}catch(Exception e){
			//TODO CHANGE THIS SHIZZLE
			e.printStackTrace();
		}
	}
	
	public static Vector<Object> loadFile(File loadFile, EditorListener el){
		Object[] returnVal	 = new Object[2];
		Vector<Object> ev	 = new Vector<Object>();
		Vector<GuiElement>gv = new Vector<GuiElement>();
		int numLines 	 	 = getNumberOfLines(loadFile);
		int[][] links	 	 = new int[numLines][4];
		
		try{
			FileReader fr 	 = new FileReader(loadFile);
			BufferedReader r = new BufferedReader(fr);
			String line		 = r.readLine();
		
			// Main loop for looping through the lines in the file
			while(line != null){
				String[] loadString = line.split(MAIN_SEPARATOR);
				//Loop to traverse the main sections of every line in the file
				for(int i = 0; i < loadString.length; i++){
					int id;
					Rectangle bounds = null;
					String[] boundsSection;
					switch(i){
					case 0:
						// Get the id of the item
						id = Integer.parseInt(loadString[i]);
						break;
					case 1:
						// Get the bounds
						boundsSection = loadString[i].split(SUB_SEPARATOR);
						bounds = new Rectangle(
								Integer.parseInt(boundsSection[0]),
								Integer.parseInt(boundsSection[1]),
								Integer.parseInt(boundsSection[2]),
								Integer.parseInt(boundsSection[3])
								);
						break;
					case 2:
						// Get class name and set object
						String name = loadString[i];
						if(name.equals("LinkedList")){
							JComponent c = el.getComponentFromEnum(ElementType.LIST,bounds);
							el.addElementAtPosition(c);
						}else if(name.equals("Queue")){
							JComponent c = el.getComponentFromEnum(ElementType.QUEUE,bounds);
							el.addElementAtPosition(c);
						}else if(name.equals("Stack")){
							JComponent c = el.getComponentFromEnum(ElementType.STACK,bounds);
							el.addElementAtPosition(c);
						}else if(name.equals("Array")){
							JComponent c = el.getComponentFromEnum(ElementType.ARRAY,bounds);
							el.addElementAtPosition(c);
						}else if(name.equals("Heap")){
							JComponent c = el.getComponentFromEnum(ElementType.HEAP,bounds);
							el.addElementAtPosition(c);
						}else if(name.equals("Tree")){
							JComponent c = el.getComponentFromEnum(ElementType.TREE,bounds);
							el.addElementAtPosition(c);
						}else if(name.equals("Variable")){
							JComponent c = el.getComponentFromEnum(ElementType.VARIABLE,bounds);
							el.addElementAtPosition(c);
						}else if(name.equals("InfoPanel")){
							JComponent c = el.getComponentFromEnum(ElementType.TEXT,bounds);
							el.addElementAtPosition(c);
						}
						break;
					case 3:
						break;
					}
				}//End of main sections loop
			}// End of main loop
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Generates a string of the indices in elements that element has a link to
	 * @param element
	 * @param elements
	 * @return
	 */
	private static String generateLinksString(Object element, Vector<Object> elements){
		String links = "";
		if(element instanceof Remove){
			links += Integer.toString(elements.indexOf(((Remove) element).getSource()));
			links += SUB_SEPARATOR;
			links += Integer.toString(elements.indexOf(((Remove) element).getTargetVariable()));
			links += SUB_SEPARATOR;
			links += Integer.toString(elements.indexOf(((Remove) element).getIndexVariable()));
		}else if(element instanceof Insert){
			links += Integer.toString(elements.indexOf(((Insert) element).getTarget()));
			links += SUB_SEPARATOR;
			links += Integer.toString(elements.indexOf(((Insert) element).getSourceVariable()));
			links += SUB_SEPARATOR;
			links += Integer.toString(elements.indexOf(((Insert) element).getIndexVariable()));
		}else if(element instanceof Push){
			links += Integer.toString(elements.indexOf(((Push) element).getTarget()));
			links += SUB_SEPARATOR;
			links += Integer.toString(elements.indexOf(((Push) element).getSourceVariable()));
		}else if(element instanceof Add){
			links += Integer.toString(elements.indexOf(((Add) element).getTarget()));
			links += SUB_SEPARATOR;
			links += Integer.toString(elements.indexOf(((Add) element).getSourceVariable()));
			links += SUB_SEPARATOR;
			links += Integer.toString(elements.indexOf(((Add) element).getIndexVariable()));
		}else if(element instanceof Pop){
			links += Integer.toString(elements.indexOf(((Pop) element).getSource()));
			links += SUB_SEPARATOR;
			links += Integer.toString(elements.indexOf(((Pop) element).getTargetVariable()));
		}else if(element instanceof Get){
			links += Integer.toString(elements.indexOf(((Get) element).getSource()));
			links += SUB_SEPARATOR;
			links += Integer.toString(elements.indexOf(((Get) element).getTarget()));
			links += SUB_SEPARATOR;
			links += Integer.toString(elements.indexOf(((Get) element).getIndexVariable()));
		}else if(element instanceof Set){
			links += Integer.toString(elements.indexOf(((Set) element).getTarget()));
			links += SUB_SEPARATOR;
			links += Integer.toString(elements.indexOf(((Set) element).getSourceVariable()));
			links += SUB_SEPARATOR;
			links += Integer.toString(elements.indexOf(((Set) element).getIndexVariable()));
		}
		return links;
	}

	private static String generateAttributesString(Object element, Vector<Object> elements){
		String attr = "";
		if(element instanceof Variable){
			attr += "value"+ATTR_SEPARATOR+((Variable) element).getValue();
			attr += SUB_SEPARATOR;
			attr += "editable"+ATTR_SEPARATOR+((Variable) element).getEditable();
		}else if(element instanceof InfoPanel){
			attr += "value"+ATTR_SEPARATOR;
			byte[] b = ((InfoPanel) element).getValue().getBytes();
			for(int i = 0;i < b.length;i++){
				attr += b[i] + BYTE_SEPARATOR;
			}
			attr += SUB_SEPARATOR;
			attr += "editable" + ATTR_SEPARATOR;
			attr += Boolean.toString(((InfoPanel) element).getEditable());
		}else if(element instanceof Get){
			attr += "singleChar"+ATTR_SEPARATOR;
			attr += Boolean.toString(((Get) element).getSingleChar());
		}else if(element instanceof Set){
			attr += "singleChar"+ATTR_SEPARATOR;
			attr += Boolean.toString(((Set) element).getSingleChar());
		}
		return attr;
	}

	private static int getNumberOfLines(File f){
		int numLines = 0;
		try{
			FileReader fr 	 = new FileReader(f);
			BufferedReader r = new BufferedReader(fr);
			while(r.readLine() != null)
				numLines++;
			r.close();
		}catch(Exception e){
			numLines = 0;
		}
		return numLines;
	}
}
