package org.phonescript.intepreter;

import java.io.IOException;

public class CharBuffer {
	public Stack stack = new Stack();
	
	
	public void feed(Object obj)  {
		StringBuffer s = new StringBuffer();
		s.append(obj);
		int []newbuf= new int[s.length()+buff.length];
		for (int i=0; i < s.length(); i++)
			newbuf[i] = s.charAt(i);
		System.arraycopy(buff, 0, newbuf, s.length(), buff.length);
		buff = newbuf;
	}
	
	CharBuffer(Stream in, int k) {
		lookAhead = k;
		buff = new int[k];
		inputReader = in;
			try {
				for (int i=0; i<k; i++) 
					buff[i] = inputReader.getCh();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		
	public int peek(int i) {
		if ( i >= 1 && i <= lookAhead) 
			return buff[i-1];
		return 0;
	}
	
	public void  consume() {
		System.arraycopy(buff, 1, buff, 0, buff.length-1);
		try {
			int ch = inputReader.getCh();
			if (ch == '\r')
				ch = inputReader.getCh();
			buff[buff.length-1] = ch;
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	
	
	int []buff;
	Stream inputReader;
	int lookAhead;
	
	


}
