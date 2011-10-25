package sim.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import sim.gui.elements.GuiElement;
import sim.gui.elements.GuiSettings;
import sim.structures.Array;
import sim.structures.LinkedList;
import sim.structures.Queue;
import sim.structures.Stack;
import sim.structures.Tree;
import sim.structures.Variable;

@SuppressWarnings("serial")
public class EditorInfo extends JPanel{
	InfoPanel panel;
	InfoType infotype;

	public EditorInfo(){
		panel = new InfoPanel();
		panel.changeInfo(InfoType.TREE);
		setLayout(new GridLayout(1,1));
		add(panel);
	}
	public void setInfoType(InfoType infotype){
		this.infotype = infotype;
		this.panel.changeInfo(infotype);
		validate();
	}
	public static enum InfoType{
		//Structures
		STACK,
		ARRAY,
		HEAP,
		LINKEDLIST,
		QUEUE,
		TREE,
		VARIABLE,
		//Functions
		ADD,
		INSERT,
		MOVECHAR,
		POP,
		PUSH,
		REMOVE,
		//Default
		NONE	
	}
	public class InfoPanel extends JPanel{
		GuiElement type;
		InfoType info;
		
		public InfoPanel(){
			super(new GridLayout(1,2));
			info = InfoType.NONE;
		}
		public void changeInfo(InfoType infotype){
			removeAll();
			drawComp(infotype);
		}
		private void drawComp(InfoType info){
			JTextArea text = new JTextArea();
			text.setEditable(false);
			text.setBorder(BorderFactory.createLineBorder(Color.BLACK));

			JScrollPane textPane = new JScrollPane(text);
			add(textPane);
			text.setLineWrap(true);
			text.setWrapStyleWord(true);
			textPane.setPreferredSize(new Dimension(getWidth()-150,getHeight()));
			
			String t;
			
			switch(info){
			case STACK:
				Stack s = new Stack(new Rectangle(0,0,(int)(GuiSettings.STACKELEMENTWIDTH*(3/2.0)), 150));
				s.push("value 1");
				s.push("value 2");
				s.push("value 3");
				s.push("value 4");
				add(s.getGuiElement());

				t = 
						"Om strukturen:\n" +
						"En stack kan kun ta imot og fjerne elementer fra toppen.\n" +
						"Metodene som brukes for å gjøre dette er henholdsvis Push og Pop\n" +
						"\n" +
						"Om bruk i editor:\n" +
						"Legges til på ønsket posisjon og linkes med Push og/eller Pop"
					;
				text.setText(t);
				break;
			case ARRAY:
				Array a = new Array(new Rectangle(0,0, 100, 100), 5);
				a.insertAt("value 1", 1);
				a.insertAt("value 2", 0);
				a.insertAt("value 3", 4);
				add(a.getGuiElement(), BorderLayout.WEST);
				t = 
						"Et array består av en liste med objekter som ofte har en gitt størrelsesorden før den tas i bruk." +
						" Objektene sorteres med en tallindeks og kan leses og skrives fra/til alle indekser."
					;
				text.setText(t);
				break;
			case HEAP:
				break;
			case LINKEDLIST:
				LinkedList ll = new LinkedList(new Rectangle(0,0,100, 75));
				ll.addFirst("value 1");
				ll.addFirst("value 2");
				ll.addFirst("value 3");
				
				add(ll.getGuiElement(), BorderLayout.WEST);
				t = 
						"En linket liste er en liste hvor et objekt peker til det neste objektet i listen." +
						"En dobbeltlenket liste peker og til objektet før." +
						"Dersom listen er sirkulær peker det siste objektet til det første." +
						"Dersom listen og er dobbeltlenket, peker det første objektet og til det siste."
					;
				text.setText(t);
				
				break;
			case QUEUE:
				Queue q = new Queue(new Rectangle(0,0,100, 75));
				q.add("value 1");
				q.add("value 2");
				q.add("value 3");
				
				add(q.getGuiElement(), BorderLayout.WEST);
				t = 
						"Om Strukturen:\n" +
						"En kø er et spesialtilfelle av Stack hvor man kun kan sette inn elementer i slutten av strukturen" +
						"og kun kan ta ut elementer fra begynnelsen av strukturen.\n" +
						"\n" +
						"Om bruk i editoren:\n" +
						"Køen kan lenkes med Push og Pop for å , henholdsvis, legge til og fjerne elementer."
					;
				text.setText(t);
				
				break;
			case TREE:
				Tree tr = new Tree(new Rectangle(0,0,100, 75), true);
				tr.addBreadthFirst("value 1");
				tr.addBreadthFirst("value 2");
				tr.addBreadthFirst("value 3");
				
				add(tr.getGuiElement(), BorderLayout.WEST);
				t = 
						"The queue is wicked cool, yo!"
					;
				text.setText(t);
				
				break;
			case VARIABLE:
				Variable v = new Variable(new Rectangle(0,0,50,100), "Variable value", false);
				
				add(v.getGuiElement());
				t= 
						"Om strukturen:\n" +
						"En variabel kan skrives og leses verdier fra og brukes til å ta vare på" +
						"verdier mellom metodekall og beregninger.\n" +
						"\n" +
						"Om bruk i editoren:\n" +
						"En variabel kan lenkes med de fleste funksjonene i editoren. Noen av disse er retningsbestemt, noe som vil si" +
						"at dersom du lager en lenke fra variabelen til funksjonen så betyr det noe annet enn om du lager en lenke fra" +
						"funksjonen til variabelen. Det står informasjon om hva som gjelder på de forskjellige funksjonene."
						;
				text.setText(t);
				break;
			}
		}
	}
}
