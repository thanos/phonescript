// ME4SE - A MicroEdition Emulation for J2SE
//
// Copyright (C) 2001 Stefan Haustein, Oberhausen (Rhld.), Germany
//
// Contributors: Sebastian Vastag
//
// STATUS: API complete
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

package javax.microedition.midlet;

import javax.microedition.io.ConnectionNotFoundException;

/**
 * @API MIDP-1.0
 * @API MIDP-2.0 
 */
public abstract class MIDlet {

	boolean inDestruction;

    /**
     * @API MIDP-1.0
     */
    protected MIDlet() {

        if (ApplicationManager.manager == null)
            new ApplicationManager(null, new java.util.Properties());

        ApplicationManager.manager.active = this;
    }

    /**
     * @API MIDP-1.0
     */
    protected abstract void destroyApp(boolean unconditional)
        throws MIDletStateChangeException;

    /** 
     * First the property is read from the JAD-File,
     * if the key is not available in the JAD File,
     * the property is read as system property.
     * Example: -DMIDlet-Version=1.0. Unfortunately, manifest entries cannot be read 
     * with Applet safe code.
     *  
     * @API MIDP-1.0 
     */
    public String getAppProperty(String key) {

        String result = ApplicationManager.manager.jadFile.getValue(key);
        if (result != null)
            return result;

        result = ApplicationManager.manager.properties.getProperty(key);
        if (result != null)
            return result;

        if (!ApplicationManager.manager.isApplet) {
            result = System.getProperty(key);
        }

        return result;
    }

    /**
     * @API MIDP-1.0
     */
    public void notifyDestroyed() {
        /**
         * If the MIDlet currently being destroyed is one of several MIDlets
         * in a MIDlet suite, start the MIDletChooser again to give the user
         * a chance to run another MIDlet of the suite.
         */

		if(!inDestruction && this == ApplicationManager.manager.active)
			ApplicationManager.manager.destroy(false, false);

    }

    /**
     * @API MIDP-1.0
     */
    public void notifyPaused() {
    }

    /**
     * @API MIDP-1.0
     */
    protected abstract void pauseApp();

    /**
     * @API MIDP-1.0
     * @ME4SE UNSUPPORTED
     */
    public void resumeRequest() {
    }

    /**
     * @API MIDP-1.0
     */
    protected abstract void startApp() throws MIDletStateChangeException;

    /**
     * @API MIDP-2.0
     * @ME4SE UNSUPPORTED 
     */
    public final boolean platformRequest(String URL)
        throws ConnectionNotFoundException {
        System.out.println("MIDlet.platformRequest() called with no effect!");
        return false;
    }

    /**
     * @API MIDP-2.0
     * @ME4SE UNSUPPORTED 
     */
    public final int checkPermission(String permission) {
        System.out.println("MIDlet.checkPermission() called with no effect!");
        return -4711;
    }

}