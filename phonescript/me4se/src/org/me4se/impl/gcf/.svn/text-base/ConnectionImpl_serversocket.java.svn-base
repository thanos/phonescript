package org.me4se.impl.gcf;

import java.io.*;
import java.util.*;
import java.net.*;
import javax.microedition.io.*;

public class ConnectionImpl_serversocket extends ConnectionImpl 
    implements StreamConnectionNotifier {

    ServerSocket serverSocket;

	public void initialise( Properties properties )
	{
	}

    public void open (String url, int mode, 
		      boolean timeouts) throws IOException {

	serverSocket = new ServerSocket 
	    (Integer.parseInt (url.substring (url.lastIndexOf (':')+1)));
    }
    

    public StreamConnection acceptAndOpen () throws IOException {
	
	ConnectionImpl_socket c = new ConnectionImpl_socket ();
	c.socket = serverSocket.accept ();
	
	return c;
    }


    public void close () throws IOException {
	serverSocket.close ();
    }

}
