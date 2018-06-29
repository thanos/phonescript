package org.phonescript.intepreter;

public class Stack {
	StackItem mTop; int mLen;
	public void push(Object object) {
		StackItem item = new StackItem();
		item.data = object;
		if (mTop == null) {
			mTop = item;
		}
		else 
		{
			item.next = mTop;
			mTop = item;
		}
		mLen++;
	}
	
	public Object top() {
		
		return mTop != null ? mTop.data: null;
	}
	public Object pop() {
		if (mTop != null) {
			Object object = mTop.data;
			mTop = mTop.next;
			mLen--;
			return object;
		}
		return null;
	}
	
	public Object cut(int c) {
		int stop = c > 0 ? c: mLen - c;
		StackItem ptr = mTop;
		for (int i = 0; i < stop-1; i ++) {
			ptr = ptr.next;
		}
		Object data = ptr.next.data;
		ptr.next= ptr.next.next;
		mLen--;
		return data;
	}
	
	public void insert(int n, Object object) {
		int stop = n > 0 ? n: mLen - n;
		StackItem ptr = mTop;
		for (int i = 0; i < stop-1; i ++) {
			ptr = ptr.next;
		}
		StackItem item = new StackItem();
		item.data = object;
		item.next = ptr.next.next;
		ptr.next= item;
		mLen++;
	}
	
	Object []toArray() {
		Object []array = new Object[mLen];
		StackItem ptr = mTop;
		for (int i = 0; i < mLen; i++) {
			array[i] = ptr.data;
			ptr = ptr.next;
		}
		return array;
	}
	

	
	Object popTo(Object item) {
		StackItem ptr = mTop;
		int i=0;
		for (i=0; ptr != null && ptr.data != item; i++)
			ptr = ptr.next;
		if (ptr != null) {
			Object []array = new Object[i];
			i--;
			while (i >= 0) {
				array[i--] = pop();
			}
			pop();
			return array;
		}
		return new Object[0];
	}
	
	public String toString() {
		StringBuffer buf = new StringBuffer();
		StackItem ptr = mTop;
		int stop = Math.min(8, mLen);
		for (int i = 0; ptr != null; i++) {
			buf.append(i).append(" :").append(ptr.data).append("\n");
			ptr = ptr.next;
		}	
		return buf.toString();
	}
	


	
	

	

	
	
	
	

	public void dup() {
		push(mTop.data);

	}

	public void copy() {
		Object o = mTop.data;
		// TODO
		push(o);
		
	}

	public void roll() {
		int direction = ((Double)pop()).intValue();
		int size = ((Double)pop()).intValue();
		int shifts = Math.abs(direction);
		Object o;
		for (int j=0; j < shifts; j++) {
			if (direction > 0) {
				o = pop();
				insert(size-1, o);
			}
			else {
				o = cut(size-1);
				push(o);
			}
		}
		
	}
	public void exch() {
		Object data = mTop.data;
		mTop.data = mTop.next.data;
		mTop.next.data = data;		
	}
	
	public void index() {
		// TODO Auto-generated method stub
		
	}

	public void mark() {
		// TODO Auto-generated method stub
		
	}

	public void clear() {
		// TODO Auto-generated method stub
		
	}

	public void count() {
		// TODO Auto-generated method stub
		
	}

	public void countToMark() {
		// TODO Auto-generated method stub
		
	}

	public void clearToMark() {
		// TODO Auto-generated method stub
		
	}
	
	
}
