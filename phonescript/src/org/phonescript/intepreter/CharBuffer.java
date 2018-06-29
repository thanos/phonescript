package org.phonescript.intepreter;

import java.io.IOException;

public class CharBuffer {
	public NewStack stack = new NewStack();
	
	static final char ETX = 3;
	
	public int getCh(File in) throws IOException {
		return (in != null) ? inputReader.getCh(): ETX;
	}
	
	public void feed(Object obj)  {
		StringBuffer s = new StringBuffer();
		s.append(obj);
		int []newbuf= new int[s.length()+buff.length];
		for (int i=0; i < s.length(); i++)
			newbuf[i] = s.charAt(i);
		System.arraycopy(buff, 0, newbuf, s.length(), buff.length);
		buff = newbuf;
	}
	
	CharBuffer(File in, int k) {
		lookAhead = k;
		buff = new int[k];
		inputReader = in;
			try {
				for (int i=0; i<k; i++) 
					buff[i] = getCh(in);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	CharBuffer(String  in, int k) {	
		buff = new int[0];
		feed(in);
	}
	
	public int peek(int i) {
		if ( i >= 1 && i <= buff.length) 
			return buff[i-1];
		return 0;
	}
	
	public void  consume() {
		System.arraycopy(buff, 1, buff, 0, buff.length-1);
        try {
			int ch = getCh(inputReader);
			if (ch == '\r')
				ch = getCh(inputReader);
			buff[buff.length-1] = ch;
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	
	
	int []buff;
	File inputReader;
	int lookAhead;
	
	


}
