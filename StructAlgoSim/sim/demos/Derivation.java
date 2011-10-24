package sim.demos;

import java.awt.Rectangle;
import java.util.Vector;

import sim.structures.Stack;
import sim.structures.Tree;
import sim.structures.Tree.Traversal;


public class Derivation extends DemoFrame {
	private String postfixS;
	public Derivation(){
		super("Derivation demo");
		
		Vector<String> operators = new Vector<String>();
		Vector<Integer> operatorPrio = new Vector<Integer>();
		operators.add("^"); operatorPrio.add(3);
		operators.add("*"); operatorPrio.add(2);
		operators.add("/"); operatorPrio.add(2);
		operators.add("+"); operatorPrio.add(1);
		operators.add("-"); operatorPrio.add(1);
		
		postfixS = "4 x * 2 x ^ *";
		
		Tree tree = new Tree(new Rectangle(0,0,1000,500),true);
		tree.setTraversal(Traversal.INORDER);
		Stack opStack = new Stack(new Rectangle(200,200,300,300));
		Stack valStack = new Stack(new Rectangle(400,400,300,300));		
		
		//Generating tree
		
		while(postfixS.length()>0){
			String active = getFirstInPostFix(postfixS);
			if(operators.contains(active)){//is operator
				opStack.push(active);
			}
			else{//is operand
				valStack.push(active);
			}
		}
		
		int i = 1; 
		
//		for(int j = 1;j<11;j++){
//		//tree.insertAt(j, "insert "+j);
//			tree.addChildAt(j, "prime "+j);
//			tree.addChildAt(j, "second "+j);
//		}
		while(!opStack.isEmpty()){
			tree.insertAt(i, i + " " +opStack.pop());
			i++;
			if(!valStack.isEmpty())
				tree.addChildAt(i, i + " " +valStack.pop());
			if(!valStack.isEmpty())
				tree.addChildAt(i, i + " " +valStack.pop());
			i++;
		}	
		
		add(tree.getGuiElement());
//		add(opStack.getGuiElement());
//		add(valStack.getGuiElement());
		validate();
	}
	private String getFirstInPostFix(String postfix){
		String[] arr = postfix.split(" ");
		System.out.println(postfixS.indexOf(" "));
		if(postfixS.contains(" "))
			postfixS = postfixS.substring(postfixS.indexOf(" ")+1);
		else
			postfixS = "";
		System.out.println(postfixS);
		return arr[0];
	}
}
