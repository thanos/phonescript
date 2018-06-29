/*
 * Created on 08.11.2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package org.me4se.samples.ui;

import java.io.IOException;

import javax.microedition.lcdui.*;

/**
 * @author haustein
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Demo_Image extends Canvas{

	Image image;

	public Demo_Image() {
		/*
		image = Image.createImage (10,10);
		image.getGraphics ().fillArc (0,0,10,10,0, 360);
		*/

		try {
			image = Image.createImage("/me4se-logo-small.png");
		}
		catch (IOException e) {
			throw new RuntimeException("Unable to load Image: " + e);
		}
	}

	public void paint(Graphics g) {
		g.setGrayScale(255);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.drawImage(image, 0, 0, Graphics.TOP | Graphics.LEFT);
		g.drawImage(image, getWidth() / 2, getHeight() / 2, Graphics.HCENTER | Graphics.VCENTER);
		g.drawImage(image, getWidth(), getHeight(), Graphics.BOTTOM | Graphics.RIGHT);
	}
}
