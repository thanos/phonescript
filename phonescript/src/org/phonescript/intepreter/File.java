package org.phonescript.intepreter;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

final public class File {

	
	final InputStream reader;
	
	
	public File(Object path, Object access) throws IOException  {
				reader  = ((StreamConnection)Connector.open(path.toString())).openInputStream();	
	}
	
	final public String read() throws IOException {
		return read(-1);
	}
	
	final public String read(int num) throws IOException {
		StringBuffer buff= new StringBuffer();
		byte[]inbuf = new byte[2046];
		int bytesGot = 0;
		while ((bytesGot =reader.read(inbuf, 0, num)) != -1 && num != 0) {
		    buff.append(inbuf);
		    num -= bytesGot;
		}
		return buff.toString(); //.getBytes();
	}
	
	final public int getCh() throws IOException {
		return reader.read();
	}
	

}
