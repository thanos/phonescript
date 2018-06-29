/*
 * Created on 01.11.2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package javax.microedition.lcdui;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.microedition.midlet.ApplicationManager;

import org.kobjects.scm.ScmComponent;
import org.kobjects.util.Csv;

/**
 * @author haustein
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
class ScmIcon extends ScmComponent {

	Hashtable states = new Hashtable();
	String name;
	java.awt.Image currentImage;
	String currentState;

	private ScmIcon(String name, String propStr) {
		this.name = name;
		ApplicationManager manager = ApplicationManager.manager;
		String[] props = Csv.decode(propStr);

		setX(Integer.parseInt(props[0]));
		setY(Integer.parseInt(props[1]));

		String seek = name + ".";
		for (Enumeration e = manager.properties.keys(); e.hasMoreElements();) {
			String key = (String) e.nextElement();
			if (key.toLowerCase().startsWith(seek)) {
				String value = manager.getProperty(key).trim();
				String state = key.substring(seek.length());
			//	System.out.println("item state '" + state + "' for " + name + " is " + value);

				if (!value.equals("")) {
					java.awt.Image image;
					try {
						image = manager.getImage(ApplicationManager.concatPath(manager.getProperty("skin"), value));
						int w = ApplicationManager.manager.getImageWidth(image, value);
						int h = ApplicationManager.manager.getImageHeight(image, value);
						if(w > getWidth()) setWidth(w);
						if(h > getHeight()) setHeight(h);
					}
					catch (IOException e1) {
						throw new RuntimeException(e1.getMessage());
					}

					// adjust size here!!

					states.put(state, image);
				}
			}
		}
	//	System.out.println("states: "+states);
		setState(props[2]);
	}

	public void setState(String state){
		if(state.equals(currentState)) return;
		currentState = state;
		//System.out.println("setting icon "+name+" to '"+state+"'");
		currentImage = (java.awt.Image) states.get(state);
		//if(currentImage == null) System.out.println("no image!");
		repaint();
	}


	public void paint(java.awt.Graphics g){
		if(currentImage == null) return;
		
		ApplicationManager.manager.drawImage(g, currentImage, 0, 0, name+"."+currentState);
	}

	public static ScmIcon create(String name) {
		String propStr = ApplicationManager.manager.getProperty(name);
	//	System.out.println("creating icon '" + name + "'; entry found: " + propStr);
		return (propStr == null) ? null : new ScmIcon(name, propStr);
	}
}
