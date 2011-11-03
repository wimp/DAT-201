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
import sim.editor.EditorListener.Link;
import sim.functions.*;
import sim.gui.elements.*;
import sim.structures.*;

class EditorFileHandling {
	private static final String MAIN_SEPARATOR 	= "#¤%";
	private static final String SUB_SEPARATOR	= "£€";
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
	
	public static void loadFile(File loadFile, EditorListener el){
		int numLines 	 	 = getNumberOfLines(loadFile);
		int[][] links	 	 = new int[numLines][4];
		
		try{
			FileReader fr 	 = new FileReader(loadFile);
			BufferedReader r = new BufferedReader(fr);
			String line		 = r.readLine();
			Vector<String[][]> attributeElements = new Vector<String[][]>();
		
			// Main loop for looping through the lines in the file
			while(line != null){
				String[] loadString = line.split(MAIN_SEPARATOR);
				String[][] attributes = new String[4][2];
				int id = 0;
				Rectangle bounds = null;
				String[] boundsSection = new String[4];
				//Loop to traverse the main sections of every line in the file
				for(int i = 0; i < loadString.length; i++){
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
						}else if(name.equals("Remove")){
							JComponent c = el.getComponentFromEnum(ElementType.REMOVE,bounds);
							el.addElementAtPosition(c);
						}else if(name.equals("Insert")){
							JComponent c = el.getComponentFromEnum(ElementType.INSERT,bounds);
							el.addElementAtPosition(c);
						}else if(name.equals("Push")){
							JComponent c = el.getComponentFromEnum(ElementType.PUSH,bounds);
							el.addElementAtPosition(c);
						}else if(name.equals("Add")){
							JComponent c = el.getComponentFromEnum(ElementType.ADD,bounds);
							el.addElementAtPosition(c);
						}else if(name.equals("Pop")){
							JComponent c = el.getComponentFromEnum(ElementType.POP,bounds);
							el.addElementAtPosition(c);
						}else if(name.equals("Get")){
							JComponent c = el.getComponentFromEnum(ElementType.GET,bounds);
							el.addElementAtPosition(c);
						}else if(name.equals("Set")){
							JComponent c = el.getComponentFromEnum(ElementType.SET,bounds);
							el.addElementAtPosition(c);
						}
						break;
					case 3:
						// Get the id of the links
						String[] linkArray = loadString[i].split(SUB_SEPARATOR);
						for(int j = 0;j < linkArray.length;j++){
							if(linkArray[j].length() > 0)
								links[id][j] = Integer.parseInt(linkArray[j]);
						}
						break;
					case 4:
						// Get the attributes
						String[] attrArray = loadString[i].split(SUB_SEPARATOR);
//						String eName = loadString[2];
						for(int j = 0;j < attrArray.length;j++){
							String[] varAttr = attrArray[j].split(ATTR_SEPARATOR);
							attributes[j][0] = varAttr[0];
							attributes[j][1] = varAttr[1];
							attributeElements.add(id,attributes);
							/*if(eName.equals("Variable")){
								String[] varAttr = attrArray[j].split(ATTR_SEPARATOR);
								attributes[j][0] = varAttr[0];
								attributes[j][1] = varAttr[1];
							}else if(eName.equals("InfoPanel")){
								
							}*/
						}
						break;
					}//End of swtich/case
				}//End of main sections loop
				line = r.readLine();
			}// End of main loop
			
			// Handle the links between the objects //
			for(int i = 0;i < links.length;i++){
				Object element = el.elements.get(i);
				Link li = el.new Link();
				li.from = element;
				li.fromGui = el.guiElements.get(i);
				
				for(int j = 0;j < links[i].length;j++){
					if(links[i][j] == -1) continue;
					// Adding actual links between the objects //
					if(!el.elements.get(links[i][j]).equals(element) && el.checkCompatibility(element,el.elements.get(links[i][j])) && !(element instanceof Variable)){
						li.to = el.elements.get(links[i][j]);
						li.toGui = el.guiElements.get(links[i][j]);
						li.makeLink();
						el.linkys.add(li);
						li = el.new Link();
						li.from = element;
						li.fromGui = el.guiElements.get(i);
					}// End of adding links
					
					// Making the objects fit together by setting their relationships
					if(element instanceof Add){
						switch(j){
						case 0:
							((Add) element).setTarget(el.elements.get(links[i][j]));
							break;
						case 1:
							((Add) element).setSourceVariable((Variable) el.elements.get(links[i][j]));
							break;
						case 2:
							((Add) element).setIndexVariable((Variable) el.elements.get(links[i][j]));
							break;
						}
					}else if(element instanceof Remove){
						switch(j){
						case 0:
							((Remove) element).setSource(el.elements.get(links[i][j]));
							break;
						case 1:
							((Remove) element).setTargetVariable((Variable) el.elements.get(links[i][j]));
							break;
						case 2:
							((Remove) element).setIndexVariable((Variable) el.elements.get(links[i][j]));
							break;
						}
					}else if(element instanceof Insert){
						switch(j){
						case 0:
							((Insert) element).setTarget(el.elements.get(links[i][j]));
							break;
						case 1:
							((Insert) element).setSourceVariable((Variable) el.elements.get(links[i][j]));
							break;
						case 2:
							((Insert) element).setIndexVariable((Variable) el.elements.get(links[i][j]));
							break;
						}
					}else if(element instanceof Push){
						switch(j){
						case 0:
							((Push) element).setTarget(el.elements.get(links[i][j]));
							break;
						case 1:
							((Push) element).setSourceVariable((Variable) el.elements.get(links[i][j]));
							break;
						}
					}else if(element instanceof Pop){
						switch(j){
						case 0:
							((Pop) element).setSource(el.elements.get(links[i][j]));
							break;
						case 1:
							((Pop) element).setTargetVariable((Variable) el.elements.get(links[i][j]));
							break;
						}
					}else if(element instanceof Get){
						switch(j){
						case 0:
							((Get) element).setSource(el.elements.get(links[i][j]));
							break;
						case 1:
							((Get) element).setTarget((Variable) el.elements.get(links[i][j]));
							break;
						case 2:
							((Get) element).setIndexVariable((Variable) el.elements.get(links[i][j]));
							break;
						}
					}else if(element instanceof Set){
						switch(j){
						case 0:
							((Set) element).setTarget(el.elements.get(links[i][j]));
							break;
						case 1:
							((Set) element).setSourceVariable((Variable) el.elements.get(links[i][j]));
							break;
						case 2:
							((Set) element).setIndexVariable((Variable) el.elements.get(links[i][j]));
							break;
						}
					}// End of object relationship routine
				}
			}// End of link handling //
			
			// Handle object attributes //
			for(int i = 0;i < el.elements.size();i++){
				if(attributeElements.get(i) != null){
					Object element = el.elements.get(i); 
					if(element instanceof Variable){
						for(int j = 0;j < attributeElements.get(i).length;j++){
							if(attributeElements.get(j)[j][0] == null)
								continue;
							
							if(attributeElements.get(i)[j][0].equals("value"))
								((Variable) element).setValue(attributeElements.get(i)[j][1]);
							else if(attributeElements.get(i)[j][0].equals("editable"))
								((Variable) element).setEditable(Boolean.parseBoolean(attributeElements.get(i)[j][1]));
						}
					}else if(element instanceof Info){
						for(int j = 0;j < attributeElements.get(i).length;j++){
							if(attributeElements.get(j)[j][0] == null)
								continue;
							
							if(attributeElements.get(j)[j][0].equals("value"))
								((Info) element).setValue(attributeElements.get(i)[j][1]);
							else if(attributeElements.get(i)[j][0].equals("editable"))
								((Info) element).setEditable(Boolean.parseBoolean(attributeElements.get(i)[j][1]));
						}
					}else if(element instanceof Set){
						for(int j = 0;j < attributeElements.get(i).length;j++){
							if(attributeElements.get(j)[j][0] == null)
								continue;
							
							if(attributeElements.get(j)[j][0].equals("singleChar"))
								((Set) element).setSingleChar(Boolean.parseBoolean(attributeElements.get(i)[j][1]));
						}
					}else if(element instanceof Get){
						for(int j = 0;j < attributeElements.get(i).length;j++){
							if(attributeElements.get(j)[j][0] == null)
								continue;
							
							if(attributeElements.get(j)[j][0].equals("singleChar"))
								((Get) element).setSingleChar(Boolean.parseBoolean(attributeElements.get(i)[j][1]));
						}
					}
				}
			}// End of attributes handling
		}catch(Exception e){
			e.printStackTrace();
		}
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
		}else if(element instanceof Info){
			attr += "value"+ATTR_SEPARATOR;
			byte[] b = ((Info) element).getValue().getBytes();
			for(int i = 0;i < b.length;i++){
				attr += b[i] + BYTE_SEPARATOR;
			}
			attr += SUB_SEPARATOR;
			attr += "editable" + ATTR_SEPARATOR;
			attr += Boolean.toString(((Info) element).getEditable());
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
