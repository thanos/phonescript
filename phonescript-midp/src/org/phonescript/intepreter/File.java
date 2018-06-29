package org.phonescript.intepreter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.microedition.io.Connection;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

public class File implements Stream {

	
	InputStream reader;
	
	
	public File(Object path, Object access) throws IOException  {
		try {
			 reader = ((StreamConnection)Connector.open(path.toString(), Connector.READ, true)).openInputStream();	
		} catch (MalformedURLException e) {
			reader  = ((StreamConnection)Connector.open(path.toString())).openInputStream();	
		}
	}
	
	public String read() throws IOException {
		return read(-1);
	}
	
	public String read(int num) throws IOException {
		StringBuffer buff= new StringBuffer();
		byte[]inbuf = new byte[8096];
		int bytesGot = 0;
		while ((bytesGot =reader.read(inbuf, 0, num)) != -1 && num != 0) {
		    buff.append(inbuf);
		    num -= bytesGot;
		}
		return buff.toString(); //.getBytes();
	}
	
	public int getCh() throws IOException {
		return reader.read();
	}
	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
