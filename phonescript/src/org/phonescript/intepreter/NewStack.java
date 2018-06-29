package org.phonescript.intepreter;

import java.util.Stack;
import java.util.Vector;

final public class NewStack extends Stack {

	
	
	final public Object top() {
		return size() > 0 ? peek(): null;
	}
	
	
	final public Object cut(int c) {
		Object obj = elementAt(c); 
		removeElementAt(c);
		return obj;
	}
	
	
	
	
	

	
	final Object popTo(Object item) {
		Object obj = null;
		Vector buff = new Vector(size());
		while ((obj = pop()) != item) {
			buff.add(obj);
		}
		return buff.toArray(new Object[buff.size()]);
	}
	
	final public String toString() {
		StringBuffer buf = new StringBuffer();
		int size = size();
		int stop = Math.min(8, size);
		if (stop > 0)
			for (int i = stop-1; i >=  0; i--) {
				buf.append(i).append(" :").append(elementAt(i)).append("\n");
		}	
		return buf.toString();
	}
	


	final public void dup() {
		push(top());

	}

	final public void copy() {
		Object o = top();
		// TODO
		push(o);
		
	}

	final public void roll() {
		int direction = ((Double)pop()).intValue();
		int size = ((Double)pop()).intValue();
		int shifts = Math.abs(direction);
		Object o;
		for (int j=0; j < shifts; j++) {
			if (direction > 0) {
				o = pop();
				insertElementAt(o, size-1);
			}
			else {
				o = cut(size-1);
				push(o);
			}
		}
		
	}
	final public void exch() {
		int second= size()-2;
		Object object = elementAt(second);
		setElementAt(top(), second);
		setElementAt(object, second);
	}
	
	final public void index() {
		// TODO Auto-generated method stub
		
	}

	final public void mark() {
		// TODO Auto-generated method stub
		
	}


	final public void count() {
		// TODO Auto-generated method stub
		
	}

	final public void countToMark() {
		// TODO Auto-generated method stub
		
	}

	final public void clearToMark() {
		// TODO Auto-generated method stub
		
	}
	
	

}
