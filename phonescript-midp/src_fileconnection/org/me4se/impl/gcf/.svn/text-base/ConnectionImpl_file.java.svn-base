/*
 * $Id: ConnectionImpl_file.java,v 1.4 2004/05/30 17:57:18 haustein Exp $
 */

/**
 * @author Michael Kroll, michael.kroll@trantor.de
 */

package org.me4se.impl.gcf;

import javax.microedition.io.file.*;

import org.me4se.impl.gcf.ConnectionImpl;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.DataOutputStream;
import java.io.DataInputStream;

import java.util.Properties;
import java.util.Enumeration;

/*
 * Currently not implemented:
 * 
 * availabelSize()
 * directorySize(boolean) 
 * getName() 
 * getPath() 
 * getURL() 
 * isHidden() 
 * list (String, boolean)
 * setHidden(boolean)
 * setReadable(boolean)
 * setWritable(boolean)
 * totalSize()
 * truncate(int)
 * usedSize()
 */

public class ConnectionImpl_file extends ConnectionImpl implements FileConnection {

	class ListEnumeration implements Enumeration {

		String[] elements = null;
		int index = 0;

		public ListEnumeration(String[] list) {
			elements = list;
		}

		public boolean hasMoreElements() {
			return index < elements.length;
		}

		public Object nextElement() {
			return elements[index++];
		}
	}

	File file = null;
	int mode = -1;
//	String path = null;

	public void initialise(Properties properties) {

	}

	public void open(String url, int mode, boolean timeouts) throws IOException {

		this.mode = mode;
		//url = url.substring(5); 
		
		int cut = 5; // file://
		while(cut < url.length() && url.charAt(cut)=='/')
			cut++;
		
		if(cut+1 >= url.length() || url.charAt(cut+1) != ':')
			cut--;
			
		String path = url.substring(cut);
		System.out.println("opening file: "+path);
		file = new File(path);
	}

	/*
	 * Will be left out !
	 */
	public long availableSize() {
		throw new RuntimeException("FileConnection.avilableSize() not implemented.");

	}

	public boolean canRead() {
		return file.canRead();

	}

	public boolean canWrite() {
		return file.canWrite();
	}

	public boolean create() throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		fos.close();
		return file.exists();
	}

	public boolean delete() {
		return file.delete();
	}

	public long directorySize(boolean includeSubDirs) throws IOException {
		if (!file.isDirectory()) {
			throw new IOException("FileConnection.directorySize() cannot be invoked on a file: " + file);
		}
		else {
			if (includeSubDirs) {

				throw new RuntimeException("FileConnection.directorySize(includingSubDirs) not implemented yet.");

			}
			else {
				String[] list = file.list();
				if (list != null) {
					long size = 0L;
					for (int i = 0; i < list.length; i++) {
						File f = new File(file, list[i]);
						if (f.isFile())
							size += f.length();
					}
					return size;
				}
			}
			return 0L;
		}
	}

	public boolean exists() {
		return file.exists();
	}

	public long fileSize() throws IOException {
		if (file.isDirectory()) {
			throw new IOException("FileConnection.fileSize() cannot be invoked on a directory: " + file);
		}
		else {
			return file.length();
		}
	}

	public String getName() {
		return file.isDirectory() ? file.getName()+ "/" : file.getName();
	}

	public String getPath() {

			return file.isDirectory() 
				? file.getAbsolutePath() + "/"
				: file.getAbsolutePath();
	}

	public String getURL() {
		return "file:///"+getPath();
	}

	public boolean isDirectory() {
		return file.isDirectory();
	}

	/*
	 * Will be left out. false is returned by default sind jdk1.1.8 does not support isHidden().
	 */
	public boolean isHidden() {
		throw new RuntimeException("FileConnection.isHidden() not yet implemented!");
	}

	public long lastModified() {
		if (file.exists()) {
			return file.lastModified();
		}
		else {
			return 0L;
		}
	}

	public Enumeration list() {
		if (file.exists() && file.isDirectory()) {

			File[] files = file.listFiles();
			String[] names = new String[files == null ? 0 : files.length];
			for(int i = 0; i < names.length; i++) {
				names[i] = files[i].isDirectory()
					? files[i].getName()+'/' 
					: files[i].getName();
			}
			
			return new ListEnumeration(names);
		}
		else {
			return new ListEnumeration(new String[0]);
		}
	}

	public Enumeration list(String filter, boolean includeHidden) throws IOException {
		throw new RuntimeException("FileConnection.list(String filter, boolean includeHidden) not yet implemented.");
	}

	/* This method is used to create a directory that is specified in the 
	 * Connector.open() method. The URL needs to end with a trailing "/" 
	 * in order for a directory to be created.
	 */
	public boolean mkdir() {
		return file.mkdirs();
	}

	public boolean rename(String newName) {
		if (file.exists()) {
			return file.renameTo(new File(newName));
		}
		else {
			return false;
		}
	}

	/*
	 * Throws a RuntimeExeption currently
	 */
	public void setFileConnection(String fileName) {
		throw new RuntimeException("FileConnection.setFileConnection() is not yet implemented !");
	}

	/*
	 * We be left out ! Currently  RuntimeException is thrown. Perhaps we should simply do nothing.
	 */
	public void setHidden(boolean tf) throws IOException {
		
			throw new IOException("FileConnection.setHidden() not implemented.");
	}

	/*
	 * We be left out ! Currently  RuntimeException is thrown. Perhaps we should simply do nothing.
	 */
	public void setReadable(boolean tf) throws IOException {
		
			throw new IOException("FileConnection.setReadable() not implemented.");
	}

	/*
	 * We be left out ! Currently  RuntimeException is thrown. Perhaps we should simply do nothing.
	 */
	public void setWriteable(boolean tf) throws IOException {
			throw new IOException("FileConnection.setWritable() not implemented.");
	}

	/*
	 * We be left out ! Currently  RuntimeException is thrown. Perhaps we should 
	 * return Integer.MAX_VALUE.
	 */
	public long totalSize() {
		throw new RuntimeException("FileConnection.totalSize() not implemented.");
	}

	public void truncate(int byteOffset) throws IOException {
		throw new RuntimeException("FileConnection.truncate(int byteOffset) not yet implemented!");

	}

	/*
	 * We be left out ! Currently  RuntimeException is thrown. Perhaps we should 
	 * return Integer.MAX_VALUE or call directorySize(true) on the file system root,
	 */
	public long usedSize() {
		throw new RuntimeException("FileConnection.usedSize() not implemented.");
	}

	public InputStream openInputStream() throws IOException {
		if (file.isDirectory()) {
			throw new IOException("FileConnection.openInputStream() cannot be invoked on a directory: " + file);
		}
		else {
			return new FileInputStream(file);
		}
	}

	public DataInputStream openDataInputStream() throws IOException {
		
			return new DataInputStream(openInputStream());
	}

	public OutputStream openOutputStream() throws IOException {
		if (file.isDirectory()) {
			throw new IOException("FileConnection.openOutputStream() cannot be invoked on a directory: " + file);
		}
		else {
			return new FileOutputStream(file);
		}
	}

	public DataOutputStream openDataOutputStream() throws IOException {
		if (file.isDirectory()) {
			throw new IOException("FileConnection.openDataOutputStream() cannot be invoked on a directory: " + file);
		}
		else {
			return new DataOutputStream(openOutputStream());
		}
	}

	public void close() throws IOException {
		file = null;
		mode = -1;
	}
}

/*
 * $Log: ConnectionImpl_file.java,v $
 * Revision 1.4  2004/05/30 17:57:18  haustein
 * file connection
 *
 * Revision 1.3  2003/10/08 07:41:37  mkroll
 * *** empty log message ***
 *
 * Revision 1.2  2003/02/14 14:13:04  mkroll
 * Changed String[] to Enumeration.
 *
 * Revision 1.1  2003/02/14 12:10:46  mkroll
 * New directory for FileConnection.
 *
 */