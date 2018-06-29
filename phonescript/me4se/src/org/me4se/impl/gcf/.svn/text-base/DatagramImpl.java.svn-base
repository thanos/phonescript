/**
 * @author Mario A. Negro Ponzi
 */

package org.me4se.impl.gcf;

import javax.microedition.io.*;
import java.io.*;
import java.net.*;

public class DatagramImpl implements Datagram {
	
	public DatagramPacket dp;
	int write_pointer = 0;
	int read_pointer = 0;
	byte[] theData;

	public DatagramImpl(byte[] buf, int size, String addr) throws IOException {
		dp = new DatagramPacket(buf, size);
		setAddress(addr);
	}

	public String getAddress() {
		try {
			return ("datagram://" + dp.getAddress().getHostName() + ":" + Integer.toString(dp.getPort()));
		}
		catch (Exception e) {
			return (null);
		}
	}

	public byte[] getData() {
		return dp.getData();
	}

	public int getLength() {
		return dp.getLength();
	}

	public int getOffset() {
		throw new RuntimeException("Datagram.getOffset() not yet implemented.");
	}

	public void setAddress(String addr) throws IOException {
		if (addr == null)
			throw new IOException();
		else {
			int cut = addr.lastIndexOf(':');
			String host;
			int port = -1;

			if (cut >= 11) {
				host = addr.substring(11, cut);
				port = Integer.parseInt(addr.substring(cut + 1));
			}
			else {
				host = addr.substring(11);
				port = -1;
			}
			dp.setAddress(InetAddress.getByName(host));
			if (port != -1)
				dp.setPort(port);
		}
	}

	public void setAddress(Datagram reference) {
		try {
			setAddress(reference.getAddress());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setLength(int len) {
		dp.setLength(len);
	}

	public void setData(byte[] buffer, int offset, int len) {
		byte[] ib = dp.getData();
		byte[] ob;
		if (ib.length < offset + len)
			ob = new byte[offset + len];
		else
			ob = new byte[ib.length];
		System.arraycopy(ib, 0, ob, 0, ib.length);
		System.arraycopy(buffer, offset, ob, ib.length, len);
		dp.setData(ob);
	}

	private void setData(byte buffer, int offset, int len) {
		byte[] b = new byte[1];
		b[0] = buffer;
		setData(b, offset, len);
	}

	public void reset() {
		dp.setData(new byte[0]);
	}

	public boolean readBoolean() throws IOException {
		theData = dp.getData();
		if (theData[read_pointer++] == (byte) 0)
			return false;
		else
			return true;
	}
	
	public byte readByte() throws IOException {
		theData = dp.getData();
		return theData[read_pointer++];
	}
	
	public char readChar() throws IOException {
		return (char) ((readByte() << 8) | (readByte() & 0xff));
	}
	
	public void readFully(byte[] b) throws IOException {
		b = dp.getData();
		read_pointer = 0;
	}
	
	public void readFully(byte[] b, int off, int len) throws IOException {
		if (b == null)
			throw new NullPointerException();
		if (off < 0)
			throw new IndexOutOfBoundsException();
		if (len < 0)
			throw new IndexOutOfBoundsException();
		byte[] buf = dp.getData();
		if (off + len > buf.length)
			throw new IndexOutOfBoundsException();
		System.arraycopy(buf, off, b, 0, len);
		read_pointer = 0;
	}
	
	public int readInt() throws IOException {
		return (((readByte() & 0xff) << 24) | ((readByte() & 0xff) << 16) | ((readByte() & 0xff) << 8) | (readByte() & 0xff));

	}
	
	public long readLong() throws IOException {
		return (
			((long) (readByte() & 0xff) << 56)
				| ((long) (readByte() & 0xff) << 48)
				| ((long) (readByte() & 0xff) << 40)
				| ((long) (readByte() & 0xff) << 32)
				| ((long) (readByte() & 0xff) << 24)
				| ((long) (readByte() & 0xff) << 16)
				| ((long) (readByte() & 0xff) << 8)
				| ((long) (readByte() & 0xff)));

	}
	
	public short readShort() throws IOException {
		theData = dp.getData();
		return (short) ((readByte() << 8) | (readByte() & 0xff));
	}
	
	public int readUnsignedByte() throws IOException {
		theData = dp.getData();
		return (int) theData[read_pointer++];
	}
	
	public int readUnsignedShort() throws IOException {
		return (((readByte() & 0xff) << 8) | (readByte() & 0xff));
	}
	
	public String readUTF() {
		return ("not yet implemented"); //TBD
	}
	
	public int skipBytes(int n) {
		if (read_pointer + n > getData().length) {
			read_pointer = getData().length;
			return getData().length - read_pointer;
		}
		else {
			read_pointer = read_pointer + n;
			return n;
		}
	}

	public void write(byte[] b) throws IOException {
		setData(b, write_pointer, b.length);
		write_pointer = write_pointer + b.length;
	}
	
	public void write(byte[] b, int off, int len) {
		setData(b, off, len);
		write_pointer = write_pointer + len;
	}
	
	public void write(int b) throws IOException {
		setData((byte) (0xff & (b >> 24)), write_pointer++, 1);
		setData((byte) (0xff & (b >> 16)), write_pointer++, 1);
		setData((byte) (0xff & (b >> 8)), write_pointer++, 1);
		setData((byte) (0xff & b), write_pointer++, 1);
	}
	
	public void writeBoolean(boolean v) throws IOException {
		if (v)
			setData((byte) (1), write_pointer++, 1);
		else
			setData((byte) (0), write_pointer++, 1);
	}
	
	public void writeByte(int v) throws IOException {
		setData((byte) v, write_pointer++, 1);
	}
	
	public void writeChar(int v) throws IOException {
		setData((byte) (0xff & (v >> 8)), write_pointer++, 1);
		setData((byte) (0xff & v), write_pointer++, 1);
	}
	
	public void writeChars(String s) throws IOException {
		if (s == null)
			throw new NullPointerException();
		byte[] b = s.getBytes();
		setData(b[0], write_pointer++, 1);
		setData(b[1], write_pointer++, 1);
	}

	public void writeInt(int v) throws IOException {
		setData((byte) (0xff & (v >> 24)), write_pointer++, 1);
		setData((byte) (0xff & (v >> 16)), write_pointer++, 1);
		setData((byte) (0xff & (v >> 8)), write_pointer++, 1);
		setData((byte) (0xff & v), write_pointer++, 1);
	}
	
	public void writeLong(long v) throws IOException {
		setData((byte) (0xff & (v >> 48)), write_pointer++, 1);
		setData((byte) (0xff & (v >> 40)), write_pointer++, 1);
		setData((byte) (0xff & (v >> 32)), write_pointer++, 1);
		setData((byte) (0xff & (v >> 24)), write_pointer++, 1);
		setData((byte) (0xff & (v >> 16)), write_pointer++, 1);
		setData((byte) (0xff & (v >> 8)), write_pointer++, 1);
		setData((byte) (0xff & v), write_pointer++, 1);
	}
	
	public void writeShort(int v) throws IOException {
		setData((byte) (0xff & (v >> 8)), write_pointer++, 1);
		setData((byte) (0xff & v), write_pointer++, 1);
	}
	
	public void writeUTF(String str) throws IOException {
		throw new RuntimeException("Datagram.writeUTF() not yet implemented.");
	}

	public float readFloat() {
		throw new RuntimeException("Datagram.readFloat() not yet implemented.");
	}
	
	public double readDouble() {
		throw new RuntimeException("Datagram.readDouble() not yet implemented.");
	}
	
	public String readLine() {
		throw new RuntimeException("Datagram.readLine() not yet implemented.");
	}
	
	public void writeFloat(float f) {
		throw new RuntimeException("Datagram.writeFloat() not yet implemented.");	
	}
	
	public void writeDouble(double d) {
		throw new RuntimeException("Datagram.writeDouble() not yet implemented.");	
	}
	
	public void writeBytes(String s) {
		throw new RuntimeException("Datagram.writeBytes() not yet implemented.");
	}
}