// ME4SE - A MicroEdition Emulation for J2SE
//
// Copyright (C) 2001 Stefan Haustein, Oberhausen (Rhld.), Germany
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

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Properties;

import org.kobjects.scm.ScmContainer;
import org.kobjects.scm.ScmWrapper;
import org.me4se.impl.JadFile;
import org.me4se.impl.MIDletChooser;
import org.me4se.impl.gcf.ConnectionImpl;

import com.sixlegs.image.png.PngImage;

/** 
 * This class is needed *here* in order to be able to call the
 * protected MIDlet startApp() method (etc.). It should perhaps be
 * hidden from the documentation.
 * 
 * @ME4SE INTERNAL 
 */
public class ApplicationManager {
	
	// IMPROVE:  Should we try to get rid of this static?
	// e.g. by caching and a method getManager(Midlet midlet)? 
	// Problem: Canvas wants to know its size even before assignment to MIDlet.... :(
	// Assignment to thread(s) is not feasible (no method to figure out parent
	// 
    // Possible "heuristics": getManager(null) returns "last known" manager
     
    public static ApplicationManager manager;

    public MIDlet active;

    public int colorCount = 256*256*256;
    public boolean isColor = true;
    public Properties properties;
    public java.awt.Color bgColor = java.awt.Color.white;
    public ScmContainer skin;

    public JadFile jadFile = new JadFile();

    public java.awt.Image offscreen;
    public java.awt.Dimension offscreenSize;
    public ScmContainer displayContainer;
    public ScmWrapper wrapper;
    public java.awt.Frame frame;
    public java.awt.Container awtContainer;

    public int screenWidth;
    public int screenHeight;

    public String documentBase;

    public boolean isApplet;

    public ClassLoader classLoader;

    private java.awt.MediaTracker tracker = null;
    private int trackerID = 0;
    private Hashtable imageCache = new Hashtable();

    public java.awt.Image createImage(byte[] data, int start, int len) {

        if ((data[start] == -119)
            && (data[start + 1] == 80)
            && (data[start + 2] == 78)
            && (data[start + 3] == 71)
            && (data[start + 4] == 13)
            && (data[start + 5] == 10)
            && (data[start + 6] == 26)
            && (data[start + 7] == 10)) {

            InputStream is = new ByteArrayInputStream(data, start, len);
            PngImage pi = new com.sixlegs.image.png.PngImage(is);

            pi.getEverything();
            try {
                is.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return java.awt.Toolkit.getDefaultToolkit().createImage(pi);
        }
        else
            return java.awt.Toolkit.getDefaultToolkit().createImage(
                data,
                start,
                len);
    }

    public java.awt.Image createImage(InputStream is) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        while (true) {
            int count = is.read(buffer);
            if (count <= 0)
                break;
            os.write(buffer, 0, count);

        }
        is.close();
        os.close();

        if (os.size() == 0)
            throw new RuntimeException("empty image stream!");

        return createImage(os.toByteArray(), 0, os.size());
    }

    /** 
     * loads an image from the given file name, using the 
     * openInputStream() method. If the file extension is PNG or png,
     * the image is loaded using the sixlegs png library. */

    public java.awt.Image getImage(String fileName) throws IOException {

        java.awt.Image img = (java.awt.Image) imageCache.get(fileName);
        if (img != null)
            return img;

        InputStream is = openInputStream(fileName);
        if (is == null)
            throw new RuntimeException("null stream opening: " + fileName);

        img = createImage(is);
        imageCache.put(fileName, img);
        return img;
    }

    /** 
     * opens an input stream on the given resource; if the path is
     * not absolute and not an URL, the documentBase is taken into
     * account. */

    public InputStream openInputStream(String fileName) throws IOException {
        //            System.out.println ("open stream: "+fileName);

        fileName = concatPath(documentBase, fileName);

        //     System.out.println ("concatenated: "+fileName);

        if (isUrl(fileName)) {
            //System.out.println("opening url: " + fileName);
            return new URL(fileName).openStream();
        }
        // try resource

        try { /*
        		       			fileName = fileName.replace('\\', '/');
        		       			 if (!fileName.startsWith("/"))
        		       				 fileName = "/" + fileName;
        		               	*/
            InputStream result = classLoader != null ?
            	classLoader.getResourceAsStream(fileName)
                : ApplicationManager.class.getResourceAsStream(fileName);
            if (result != null) {
                //     System.out.println("resource opended: " + fileName);
                return result;
            }
        }
        catch (Exception e) {
        }

        // file otherwise
        //System.out.println("opening file: " + fileName);

        return new FileInputStream(fileName);
    }

    public String getProperty(String name) {
        return properties.getProperty(name.toLowerCase());
    }

    /** determines whether path starts with http:// or file:// */

    public static boolean isUrl(String path) {
        String test = path.toLowerCase();
        return test.startsWith("http://") || test.startsWith("file:");
    }

    /** if file is absolute (starts with /, http:// or file://, 
     * or the base path is null, file is returned; otherwise the 
     * last index of / or \ is searched in base and 
     * the strings are concatenated. Please note that this is not equivalent to
     * kobjects.buildUrl; buildUrl always adds "file://" for 
     * urls with no location type given. 
     * 
     * @IMPROVE: It may make sense to use Util.buildUrl here and 
     * map file:/// secretly to "resource:" in openStream??  */

    public static String concatPath(String base, String file) {
        if (base == null
            || file.startsWith("/")
            || file.startsWith("\\")
            || (file.length() >= 2 && file.charAt(1) == ':')
            || isUrl(file))
            return file;

        int cut = Math.max(base.lastIndexOf('/'), base.lastIndexOf('\\'));

        return base.substring(0, cut + 1) + file;
    }

    java.awt.Image lastWait;

    public int getImageWidth(java.awt.Image image, String info) {
        while (true) {
            int w = image.getWidth(null);
            if (w != -1)
                return w;
            waitForImage(image, info);
        }
    }

    public int getImageHeight(java.awt.Image image, String info) {
        while (true) {
            int h = image.getHeight(null);
            if (h != -1)
                return h;
            waitForImage(image, info);
        }
    }

    public void drawImage(
        java.awt.Graphics g,
        java.awt.Image image,
        int x,
        int y,
        String info) {
        int cnt = 0;
        while (!g.drawImage(image, x, y, null)) {
            waitForImage(image, info);
        }
    }

    public void waitForImage(java.awt.Image image, String info) {
        //System.out.println ("enter waitforimage" +manager.displayContainer);

        if (image == null)
            throw new RuntimeException("Image " + info + " is null");

        //System.out.println("wait for img: " + info);
        if (manager.tracker == null)
            manager.tracker =
                new java.awt.MediaTracker(
                    ApplicationManager.manager.awtContainer);

        int id = manager.trackerID;
        try {

            if (lastWait != image) {
                id = ++manager.trackerID;
                manager.tracker.addImage(image, id);
                lastWait = image;
            }

            //   System.out.println("status of ID "+id+" is:" +manager.tracker.statusID(id, true));

            // manager.awtContainer.getGraphics().drawImage(image, 0, 0, null);

            manager.tracker.waitForID(id);
            //            System.out.println("ok for image "+info);

            if (manager.tracker.isErrorID(id)) {
                throw new RuntimeException(
                    "error loading image '"
                        + info
                        + "'; tracker: "
                        + manager.tracker);
                //Thread.sleep(100);
            }

            manager.tracker.removeImage(image);

        }
        catch (InterruptedException ie) {
        }

        //System.out.println("image ok: " + info);
        //System.out.println ("leave waitforimage");
    }

    public ApplicationManager(
        java.applet.Applet applet,
        Properties properties) {

        this.properties = properties;

        wrapper =
            new ScmWrapper(
                new Float(properties.getProperty("scale", "1")).floatValue());
        //FontInfo.cache = new Hashtable ();

        if (manager != null)
            manager.destroy(true, true);  // notify, may not exit, killall

        manager = this;

        if (applet != null) {
            isApplet = true;
            applet.setLayout(new java.awt.BorderLayout());

            documentBase = applet.getDocumentBase().toString();
            awtContainer = applet;

            screenWidth = applet.getSize().width;
            screenHeight = applet.getSize().height;
        }
        else {
            frame = new java.awt.Frame("ME4SE MIDlet");

            //screenWidth = getIntProperty("screen.width", 150);
            //screenHeight = getIntProperty ("screen.height", 200);        

            // Hi Stefan. Ist das Okay so? Die Kommandozeilen - Parameter f?r
            // H?he und Breite des Fensters werden nun noch vor denen vom Properties
            // File ausgewertet. Alles im !applet Mode.

            screenWidth =
                getIntProperty("width", getIntProperty("screen.width", 150));
            screenHeight =
                getIntProperty("height", getIntProperty("screen.height", 200));

            // *********************************
            // * Set microedition.* properties *
            // *********************************

            String os = System.getProperty("os.name");
            String osArch = System.getProperty("os.arch");
            String platform = "ME4SE_J2SE";

            if (os != null) {
                if (os.equals("EPOC")) {
                    platform = "ME4SE_EPOC";
                }
                else if (os.equals("Windows CE")) {
                    platform = "ME4SE_WINCE";
                }
                else if (os.equals("Linux") && osArch.equals("arm")) {
                    platform = "ME4SE_ZAURUS";
                }
            }

            Properties sysProps = System.getProperties();

            sysProps.put("microedition.platform", platform);
            sysProps.put("microedition.encoding", "ISO8859_1");
            sysProps.put(
                "microedition.locale",
                Locale.getDefault().getCountry());
            sysProps.put("microedition.configuration", "CLDC-1.0");
            sysProps.put("microedition.profiles", "MIDP-1.0");

            System.setProperties(sysProps);

            if (platform.equals("ME4SE_WINCE")) {
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                screenWidth = dim.width;
                screenHeight = dim.height - 25;
            }
            else if (platform.equals("ME4SE_ZAURUS")) {
                screenWidth = 235;
                screenHeight = 280;
            }
            else if (platform.equals("ME4SE_EPOC")) {
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                screenWidth = dim.width;
                screenHeight = dim.height;
            }

            frame.addWindowListener(new java.awt.event.WindowAdapter() {
                public void windowClosing(java.awt.event.WindowEvent ev) {
                    destroy(true, true); // notify, mayExit, killAll
                }
            });

            awtContainer = frame;
            //frame.show ();
        }

        if (getProperty("skin") != null) {
            try {
                skin =
                    (ScmContainer) Class
                        .forName("org.me4se.impl.skins.Skin")
                        .newInstance();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            displayContainer = skin;

            // access to skin properties available now

            screenWidth = getIntProperty("screen.width", screenWidth);
            screenHeight = getIntProperty("screen.height", screenHeight);
        }
        else {
            displayContainer = new ScmContainer() {
                public Dimension getMinimumSize() {
                    return new Dimension(screenWidth, screenHeight);
                }
                public void paint(java.awt.Graphics g) {
                    if (getComponentCount() == 0) {
                        g.drawString("Loading; please wait...", 20, 20);
                    }
                    else {
                        super.paint(g);
                    }
                }
            };
            displayContainer.setBackground(new java.awt.Color(0x0ffffff));
        }

        wrapper.setComponent(displayContainer);
        //	wrapper.setFocusable (true);

        awtContainer.add(wrapper, java.awt.BorderLayout.CENTER);

        if (skin != null && frame != null) {
            frame.pack();
            frame.show();
            frame.setResizable(false);
        }
        else if (isApplet) {
            wrapper.invalidate();
            awtContainer.validate();
        }

        // the MIDP API does not permit runtime size changes
        // runtime size changes may result in unpredictable
        // behaviour
    }

    /** not called if MIDlet provides a main method */

    public void launch() {

        try {
            if (getProperty("socketProxyHost") != null) {
                ConnectionImpl connectionImpl =
                    (ConnectionImpl) Class
                        .forName("org.me4se.impl.ConnectionImpl_socket")
                        .newInstance();

                connectionImpl.initialise(properties);
            }

            if (getProperty("httpProxyHost") != null) {
                ConnectionImpl connectionImpl =
                    (ConnectionImpl) Class
                        .forName("org.me4se.impl.ConnectionImpl_http")
                        .newInstance();
                connectionImpl.initialise(properties);
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Error initializing proxy:" + e);
        }

        /*
         * Initialization code necessary for the CommConnection.
         */

        if (!isApplet && System.getProperty("comm.enable") != null) {
            try {
                ConnectionImpl connectionImpl =
                    (ConnectionImpl) Class
                        .forName("org.me4se.impl.gcf.ConnectionImpl_comm")
                        .newInstance();
                connectionImpl.initialise(null);
            }
            catch (Exception e) {
                throw new RuntimeException(
                    "Error initializing CommConnection:" + e);
            }

        }

        /*
         * Initialization code necessary for the WMA implementation.
         */

        if (!isApplet && System.getProperty("wma.enable") != null) {
            try {
                ConnectionImpl connectionImpl =
                    (ConnectionImpl) Class
                        .forName("org.me4se.impl.gcf.ConnectionImpl_sms")
                        .newInstance();

                Properties props = new Properties();
                props.put(
                    "wma.enable",
                    System.getProperty("wma.enable", "true"));
                props.put(
                    "wma.d211",
                    System.getProperty("wma.enable", "false"));
                props.put(
                    "wma.commport",
                    System.getProperty("wma.commport", "COM1"));
                props.put(
                    "wma.baudrate",
                    System.getProperty("wma.baudrate", "19200"));
                props.put(
                    "wma.debug",
                    System.getProperty("wma.debug", "false"));
                connectionImpl.initialise(props);
            }
            catch (Exception e) {
                throw new RuntimeException(
                    "Error initializing WMA implememtation:" + e);
            }
        }

        String jadUrl = getProperty("jad");
        if (jadUrl == null)
            jadUrl = "/META-INF/MANIFEST.MF";

		try {
			System.out.println("trying to load JAD data from: " + jadUrl);
			jadFile.load(openInputStream(jadUrl));
		}
		catch (Exception e) {
			System.err.println("JAD access error: "+e+"; trying midlet/jam property");
		}
		
		if(jadFile.getValue("MIDlet-1")==null){
			System.err.println("No MIDlet specified, trying property");
            String midlet = getProperty("MIDlet");

            /**
             * If no MIDlet to start has been specified, but we have a
             * Java Application Manager set, start this one.
             */
            if (midlet == null)
                midlet = getProperty("jam");

            if (midlet == null)
                throw new RuntimeException("Invalid MIDlet, jam, or jad specified");

            jadFile.add("MIDlet-1: MIDlet,," + midlet);
        }

        // care about rms location

        if (isApplet) {
            org.me4se.impl.rms.RecordStoreImpl_file.rmsDir = null;
        }
        else {
            //			The directory is now named .rms/ by default.
            // and created only if RecrodStores are used
            // in the MIDlet.
            File rmsDir = new File(System.getProperty("rms.home", ".rms"));
            org.me4se.impl.rms.RecordStoreImpl_file.rmsDir = rmsDir;
        }

        try {
            if (jadFile.getMIDletCount() == 1)
                active =
                    ((MIDlet) Class
                        .forName(jadFile.getMIDlet(1).getClassName())
                        .newInstance());
            else
                active = new MIDletChooser();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.toString());
        }
    }

    public int getIntProperty(String name, int dflt) {
        try {
            return Integer
                .decode((String) ApplicationManager.manager.getProperty(name))
                .intValue();
        }
        catch (Exception e) {
            return dflt;
        }
    }
    
    int discretize(int value, int steps){
    	double v = value / 255.0;                // 0..1
		v = (int) ((steps - 1) * v + 0.5);   // 0..step
		v *= 255.0 / (steps - 1);            // 0..255

	//	System.out.println("discretize ("+value+","+steps+")="+(int)v);

		return (int) v;		    	
    }

    public int getDeviceColor(int color) {

		if(isColor && colorCount > 65536 && java.awt.Color.white.equals(ApplicationManager.manager.bgColor))
			return color;

		int r = (color >> 16) & 255;
		int g = (color >> 8) & 255;
		int b = (color & 255);

		if (isColor){
			int scnt = 2;
			int cnt = 8;
			while(cnt < colorCount){
				scnt *= 2;
				cnt *= 8;
			}

			r = discretize(r, scnt);
			g = discretize(g, scnt);
			b = discretize(b, scnt);
		}
		else {
	        double grayscale = (r+g+b) / (3.0 * 255.0); 

   	        grayscale = (int) ((colorCount - 1) * grayscale + 0.5);
       	    grayscale /= (colorCount - 1);

	        java.awt.Color bg = ApplicationManager.manager.bgColor;

	        r = (int) grayscale * bg.getRed();
        	g = (int) grayscale * bg.getGreen();
        	b = (int) grayscale * bg.getBlue();
		}
        return (color & 0x0ff000000) | (r << 16) | (g << 8) | b;
    }

    //void setRmsDir(String dflt) {

    //}

    public void start() {
        try {
            if (active != null)
                active.startApp();
        }
        catch (MIDletStateChangeException e) {
        }
    }

    public void pause() {

        if (active != null)
            active.pauseApp();
    }

	/** Destruction requested "externally" or by midlet */

    public void destroy(boolean notifyMIDlet, boolean killAll) {

		imageCache = new Hashtable();

		String jamName = getProperty("jam");
		String activeName = active == null ? "" : manager.active.getClass().getName();

		if(notifyMIDlet && active != null) {
	        try {
	        	active.inDestruction = true;
                active.destroyApp(true);
                active = null;
			}
			catch(Exception e){
				e.printStackTrace();				
			}
		}

		// If the current MIDlet is JAM, good bye!
		
		if(activeName.equals(jamName)){
			if(!isApplet)
				System.exit(0);
			else
				return;
		}

		// Try to fall back to something 

		int midletCount = manager.jadFile.getMIDletCount();

		try {

			if ((midletCount > 1 || manager.isApplet || jamName != null) && !killAll) {

				if (activeName.equals("org.me4se.impl.MIDletChooser")
					|| (midletCount == 1 && !manager.isApplet)) {
					if (jamName != null)
						startMIDlet(jamName);
				}
				else {
					manager.startMIDlet("org.me4se.impl.MIDletChooser");
				}
			}
			else {  
				
				// Give the JAM a final opportunity for cleanup....
				
				if(jamName != null){
					startMIDlet(jamName);
					active.destroyApp(true);
				}

				active = null;
				if(!isApplet)
					System.exit(0);
			}
		}

		catch (Exception e) {
			e.printStackTrace();
			active = null;
			if(!isApplet)
				System.exit(0);
		}

    }

    /**
     * Instantiates and launches the given MIDlet. DestroyApp is called for any running MIDlet.
     */
    public void startMIDlet(String name)
        throws
            InstantiationException,
            ClassNotFoundException,
            IllegalAccessException {
            	
        if(active != null && !active.inDestruction){
        	try{
        		active.inDestruction = true;
        		active.destroyApp(true);
        	}
        	catch(Exception e){
        		e.printStackTrace();
        	}
        }
            	
        MIDlet midlet = null;

        if (classLoader != null) {
            midlet = ((MIDlet) classLoader.loadClass(name).newInstance());
        }
        else {
            midlet = ((MIDlet) Class.forName(name).newInstance());
        }

        // Throw away everything in the current DisplayContainer. Tell
        // the ApplicationManager that the newly created MIDlet is the
        // active one. Then run it.
        //        displayContainer.removeAll();
        //displayContainer.setLayout(new java.awt.BorderLayout());
        //displayContainer.setBackground(bgColor);

        active = midlet;
        start();
    }

    public Object getComponent(String name) {
        String custom = getProperty(name + ".component");
        if (custom == null)
            return null;
        try {
            return Class
                .forName("javax.microedition.lcdui." + custom)
                .newInstance();
        }
        catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    public boolean getFlag(String flag) {
        String f = getProperty("me4se.flags");
        return f == null
            ? false
            : f.toLowerCase().indexOf(flag.toLowerCase()) != -1;
    }
}
