/*
 * $Id: FileSystemRegistry.java,v 1.3 2004/05/30 17:57:04 haustein Exp $
 */

/**
 * @author Michael Kroll, michael.kroll@trantor.de
 */

package javax.microedition.io.file;

import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

public class FileSystemRegistry {

	static class FileRootEnumeration implements Enumeration {
		
		String[] list = null;
		int idx = 0;
	
		public FileRootEnumeration(String[] list) {
			this.list = list;
		}
	
		public int size() {
			if (list != null) {
				return list.length;
			}
			return 0;
		}
	
		public boolean hasMoreElements() {
			if (list == null) {
				return false;
			}
			else if (idx < list.length){
				return true;
			}
			else {
				return false;
			}
		}
	
		public Object nextElement() { 
			return list[idx++];
		}
		
	}

	static String[] listenRoots = null;

	static FileSystemListener fslistener;

	static class ListenThread extends Thread {

		Hashtable roots = new Hashtable();

		public void run() {

			FileRootEnumeration list = (FileRootEnumeration)FileSystemRegistry.listRoots();

			while(list.hasMoreElements()){
				String element = (String)list.nextElement();
				roots.put(element, element);
			}
			
			while (FileSystemRegistry.fslistener != null) {
				list = (FileRootEnumeration)FileSystemRegistry.listRoots();

				// new root has beed added
				if (list.size() > roots.size()) {
					while (list.hasMoreElements()) {
						String element = (String)list.nextElement();
						if (roots.get(element) == null) {
							roots.put(element, element);

							fslistener.rootChanged(FileSystemListener.ROOT_ADDED, element);
							break;
						}
					}

				}
				// root has been removed
				else if (list.size() < roots.size()) {
					while (list.hasMoreElements()) {
						roots.remove((String)list.nextElement());
					}
					String removedRoot = (String) roots.elements().nextElement();
					fslistener.rootChanged(FileSystemListener.ROOT_REMOVED, removedRoot);
					
					roots = new Hashtable();
					
					list = (FileRootEnumeration)FileSystemRegistry.listRoots();					
					
					while(list.hasMoreElements()) {
						String element = (String)list.nextElement();
						roots.put(element, element);
					}
				}
				
				try {
					sleep(2500);
				}
				catch (InterruptedException e) {
				}
			}
		}
	}

	private FileSystemRegistry() {
	}

	private static void initRoots() {
		String roots = System.getProperty("fconn.listenroots");

		if(roots == null){
			System.err.println("Please specify the file system roots in VM variable fconn.listenroots. Example -Dfconn.listenroots=C:\\;D:\\");

			roots= File.separatorChar == '\\' ? "c:" : "/";
		}
		
		StringTokenizer tokens = new StringTokenizer(roots, ";");
		listenRoots = new String[tokens.countTokens()];
		int i = 0;
		while (tokens.hasMoreTokens()) {
			listenRoots[i++] = tokens.nextToken();
		}
	}

	/**
	 * This method is used to register a FileSystemListener that is notified in
	 * case of adding and removing a new file system root. Multiple file system
	 * listeners can be added. If file systems are not supported on a device,
	 * false will be returned from the method (this check is performed prior to
	 * security checks).
	 * 
	 * 
	 * @param listener - The new FileSystemListener to be registered in
	 * 					  order to handle adding/removing file system roots.
	 * @return	indicating if file system listener was successfully added or not
	 * @throws java.lang.SecurityException - if application is not given
		 * 										  permission to	read files.
		 * @throws java.lang. NullPointerException - if listener is null.
	 */
	public static boolean addFileSystemListener(FileSystemListener listener) {
		if (fslistener != null)
			return false;

		if (listenRoots == null)
			initRoots();

		fslistener = listener;
		new ListenThread().start();

		return true;
	}
	
	/**
	 * This method returns the currently mounted root file systems on a device.
	 * If there are no roots available on the device, a zero length Enumeration
	 * is returned. If file systems are not supported on a device, a zero length
	 * Enumeration is also returned (this check is performed prior to security
	 * checks).
     * @return an Eumeration of mounted file systems.
     * @throws java.lang.SecurityException - if application is not given 
     *                                        permission to read files.
	 */
	public static Enumeration listRoots() {

		if (listenRoots == null)
			initRoots();

		if (listenRoots != null) {

			Vector v = new Vector();

			for (int i = 0; i < listenRoots.length; i++) {
				File rootDir = new File(listenRoots[i]);
				if ((rootDir != null) && (rootDir.exists())) {
					v.addElement(listenRoots[i]);
				}
			}

			String[] roots = new String[v.size()];

			for (int i = 0; i < roots.length; i++) {
				String root = (String) v.elementAt(i);
				root = root.substring(0, root.length());
				roots[i] = root;
			}
			
			FileRootEnumeration enum = new FileRootEnumeration(roots);
			return enum;
		}
		else {
			return new FileRootEnumeration(null);
		}
	}

	/**
	 * This method is used to remove a registered FileSystemListener. 
	 * If file systems are not supported on a device, false will be 
	 * returned from the method. 
	 * @param listener - The FileSystemListener to be removed.
	 * @return indicating if file system listener was successfully 
	 *          removed  or not
	 * @throws java.lang.NullPointerException - if listener is null.
	 */
	public static boolean removeFileSystemListener(FileSystemListener listener) {
		listener = null;
		return true;
	}
}

/*
 * $Log: FileSystemRegistry.java,v $
 * Revision 1.3  2004/05/30 17:57:04  haustein
 * file connection
 *
 * Revision 1.2  2003/02/14 14:12:15  mkroll
 * Changed String[] to Enumeration.
 *
 * Revision 1.1  2003/02/14 12:10:46  mkroll
 * New directory for FileConnection.
 *
 */