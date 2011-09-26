package sim.structures;

import java.util.Vector;

public class Stack {
	private Vector<Object> s = new Vector<Object>();
	public void push(Object obj){
		s.add(obj);
	}
	
	public Object pop(){
		if(s.size()==0) return null;
		Object obj = s.get(s.size()-1);
		s.remove(obj);
		return obj;
	}
	
	public boolean isEmpty(){
		if(s.size() == 0)
			return true;
		else
			return false;
	}
	
	public Object[] toArray(){
		Object[] obj = new Object[s.size()];
		for(int i =0; i<s.size();i++){
			obj[s.size()-i-1] = s.get(i);
		}
		return obj;
	}
}
