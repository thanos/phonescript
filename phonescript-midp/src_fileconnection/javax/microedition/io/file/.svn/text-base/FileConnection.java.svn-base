/*
 * $Id: FileConnection.java,v 1.2 2003/02/14 14:12:15 mkroll Exp $
 */

/**
 * @author Michael Kroll, michael.kroll@trantor.de
 */

package javax.microedition.io.file;

import java.io.*;
import java.util.Enumeration;

import javax.microedition.io.StreamConnection;

public interface FileConnection extends StreamConnection {

	public long availableSize();

	public boolean canRead();

	public boolean canWrite();

	public boolean create() throws IOException;

	public boolean delete() throws IOException;

	public long directorySize(boolean includeSubDirs) throws IOException;

	public boolean exists();

	public long fileSize() throws IOException;

	public String getName();

	public String getPath();

	public String getURL();

	public boolean isDirectory();

	public boolean isHidden();

	public long lastModified();

	public Enumeration list() throws IOException;
	
	public Enumeration list(String filter, boolean includeHidden) throws IOException;

	public boolean mkdir() throws IOException;

	public boolean rename(java.lang.String newName) throws IOException;

	public void setFileConnection(String fileName) throws IOException;

	public void setHidden(boolean hidden) throws IOException;

	public void setReadable(boolean readable) throws IOException;

	public void setWriteable(boolean writable) throws IOException;

	public long totalSize();

	public void truncate(int byteOffset) throws IOException;

	public long usedSize();
}

/*
 * $Log: FileConnection.java,v $
 * Revision 1.2  2003/02/14 14:12:15  mkroll
 * Changed String[] to Enumeration.
 *
 * Revision 1.1  2003/02/14 12:10:46  mkroll
 * New directory for FileConnection.
 *
 */