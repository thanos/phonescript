/**
 * @author Mario A. Negro Ponzi
 */

package org.me4se.impl.gcf;

import javax.microedition.io.*;
import java.io.*;
import java.net.*;
import java.util.*;


public class ConnectionImpl_datagram extends ConnectionImpl implements DatagramConnection {
	DatagramSocket datagramsocket;
	String host;
	int port;
	
    public void open (String url, int mode, boolean timeouts) throws IOException {
		int cut = url.lastIndexOf(':');

		if (cut >= 11) {
			host = url.substring(11, cut);
			port = Integer.parseInt(url.substring(cut + 1));
		} else {
			host = url.substring(11);
			port = 1000;
		}
		datagramsocket=new DatagramSocket();
	}

    public void close () throws IOException {
    	datagramsocket.close();
    }

	public void initialise( Properties properties ) {
	}
	
	public int getMaximumLength() throws IOException {
		return 1024;
	}
	
	public int getNominalLength() throws IOException {
		return 1024;
	}
	
	public void send(Datagram dgram) throws IOException {
		if (((DatagramImpl)dgram).dp!=null) datagramsocket.send(((DatagramImpl)dgram).dp);
		else throw new IOException();
	}

	public void receive(Datagram dgram) throws IOException {
		if (((DatagramImpl)dgram).dp!=null) datagramsocket.receive(((DatagramImpl)dgram).dp);
		else throw new IOException();
	}

	public Datagram newDatagram(int size) throws IOException {
		return newDatagram(new byte[size], size, "datagram://"+host+":"+Integer.toString(port));
	}

	public Datagram newDatagram(int size, String addr) throws IOException {
		return newDatagram(new byte[size], size, addr);
	}

	public Datagram newDatagram(byte[] buf, int size) throws IOException{
		return newDatagram(buf, size, "datagram://"+host+":"+Integer.toString(port));
		}

	public Datagram newDatagram(byte[] buf, int size, String addr) throws IOException {
		return new DatagramImpl(buf, size, addr);
	}
}
	