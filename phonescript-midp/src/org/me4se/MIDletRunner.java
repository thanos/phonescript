// ME4SE - A MicroEdition Emulation for J2SE
//
// Copyright (C) 2001 Stefan Haustein, Oberhausen (Rhld.), Germany
//
// Contributors: Sebastian Vastag
//
// STATUS:
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License as
// published by the Free Software Foundation; either version 2 of the
// License, or (at your option) any later version. This program is
// distributed in the hope that it will be useful, but WITHOUT ANY
// WARRANTY; without even the implied warranty of MERCHANTABILITY or
// FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public
// License for more details. You should have received a copy of the
// GNU General Public License along with this program; if not, write
// to the Free Software Foundation, Inc., 59 Temple Place - Suite 330,
// Boston, MA 02111-1307, USA.

package org.me4se;

//import java.net.*;
import java.applet.*;
//import java.awt.*;
import javax.microedition.midlet.*;
import java.util.*;

public class MIDletRunner extends Applet {

    private boolean running = false;
  //  public static boolean isApplet;

    public void start() {
        //        System.out.println("test");

        if (!running) {
            running = true;

            // the rmsdir is set by the application manager!!!!
            // org.me4se.impl.RecordStoreImpl_file.rmsDir = null;

            if (ApplicationManager.manager != null)
                ApplicationManager.manager.destroy(true, true); // notify, killall
            //String[] param = {getParameter ("MIDlet"), getParameter("JAD-File")};

            Properties param = new Properties();

            if (getParameter("jad") != null)
                param.put("jad", getParameter("jad"));

            if (getParameter("skin") != null)
                param.put("skin", getParameter("skin"));

            if (getParameter("jad-file") != null)
                param.put("jad", getParameter("jad-file"));

            if (getParameter("MIDlet") != null)
                param.put("midlet", getParameter("MIDlet"));

            if (getParameter("socketProxyHost") != null)
                param.put("socketproxyhost", getParameter("socketProxyHost"));

            if (getParameter("socketProxyPort") != null)
                param.put("socketproxyport", getParameter("socketProxyPort"));

            if (getParameter("httpProxyHost") != null)
                param.put("httpproxyhost", getParameter("httpProxyHost"));

            if (getParameter("httpProxyPort") != null)
                param.put("httpproxyport", getParameter("httpProxyPort"));

			// JAD File Properties for Applet use...

			if (getParameter("MIDlet-Name") != null) 
				param.put("MIDlet-Name", getParameter("MIDlet-Name"));

			if (getParameter("MIDlet-Vendor") != null) 
				param.put("MIDlet-Vendor", getParameter("MIDlet-Vendor"));
                
			if (getParameter("MIDlet-Description") != null) 
				param.put("MIDlet-Description", getParameter("MIDlet-Description"));

			if (getParameter("MIDlet-Version") != null) 
				param.put("MIDlet-Version", getParameter("MIDlet-Version"));

			               
            Enumeration enum = param.keys();
                        
            System.out.println("Registered parameters in MIDletRunner:");
            while(enum.hasMoreElements()) {
            	String key = (String)enum.nextElement();
            	System.out.println("	" + key +  " = " + param.getProperty(key));
            }
              
            new ApplicationManager(this, param).launch();
        }

        ApplicationManager.manager.start();
    }

    public void stop() {
        ApplicationManager.manager.pause();
    }

    public void destroy() {
        ApplicationManager.manager.destroy(true, true); // notify,  killall
    }

    public boolean isFocusTraversable() {
        return false;
    }

    public static void main(String[] argv) {

        Properties param = new Properties();
        //String midlet = null;
        //String jadfile = null;

        for (int i = 0; i < argv.length; i++) {

            if (argv[i].startsWith("-")) {
                param.put(argv[i].substring(1), argv[i + 1]);
                i++;
            }
            else{
            int p = argv[i].indexOf(".jad");
            if (p!=-1 && p == argv[i].length() - 4)
                param.put("jad", argv[i]);
            else
                param.put("midlet", argv[i]);
            }
        }

        if (param.get("midlet") == null && param.get("jad") == null) {
            param.put("jam", "org.me4se.impl.jam.MIDletSuiteManager");
            param.put("midlet", "org.me4se.impl.jam.MIDletSuiteManager");
        }

        new ApplicationManager(null, param).launch();
        ApplicationManager.manager.start();
    }
}
