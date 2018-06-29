package org.me4se.impl;

import java.io.IOException;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.ApplicationManager;
import javax.microedition.midlet.MIDlet;

/**
 * Simple class to choose a MIDlet from a JAR file. This is a helper class
 * for the ApplicationManager that allows the user to select one of several
 * MIDlets from a MIDlet suite. Once a MIDlet is chosen, the new MIDlet is
 * instantiated an passed to the ApplicationManager as the active MIDlet.
 */
public class MIDletChooser extends MIDlet implements CommandListener {

	// set this to 0 in order to remove the Evaluation message in the 
	// info Alert screen
	private static final int EVAL_ON = 0;

	/**
	 * The list screen where the MIDlet names are displayed.
	 */
	private List list = new List("Choose MIDlet", Choice.IMPLICIT);

	/**
	 * The launch command.
	 */
	private Command LAUNCH = new Command("Launch", Command.SCREEN, 0);

	/**
	 * The info command.
	 */
	private Command INFO = new Command("Info", Command.SCREEN, 0);

	/**
	 * The back command used for ME4SE-Jam
	 */
	private Command BACK = new Command("Back", Command.BACK, 0);

	/**
	 * The parent MIDlet (Suite selector)
	 */
	private MIDlet parent;

	/**
	 * Creates the MIDlet.
	 */
	public MIDletChooser() {
		JadFile jadFile = ApplicationManager.manager.jadFile;

		for (int i = 1; i <= jadFile.getMIDletCount(); i++) {
			list.append(jadFile.getMIDlet(i).getName(), null);
		}

		list.addCommand(LAUNCH);
		list.addCommand(INFO);
		list.setCommandListener(this);

		if (ApplicationManager.manager.getProperty("jam") != null)
			list.addCommand(BACK);
	}

	/**
	 * Is called when the MIDletChooser is started, activates the list screen.
	 */
	protected void startApp() {
		Display.getDisplay(this).setCurrent(list);
	}

	/**
	 * Does nothing.
	 */
	protected void pauseApp() {
	}

	/**
	 * Does nothing.
	 */
	protected void destroyApp(boolean unconditional) {
	}

	/**
	 * Handles the LAUNCH command.
	 */
	public void commandAction(Command cmd, Displayable dsp) {
		if (cmd == LAUNCH) {
			try {
				// Get the name of the selected MIDlet and create a MIDlet instance
				// for it.
				String s = ApplicationManager.manager.jadFile.getMIDlet(list.getSelectedIndex() + 1).getClassName();
				ApplicationManager.manager.startMIDlet(s);
			} catch (Exception error) {
				error.printStackTrace();
			}
		} else if (cmd == BACK) {
			notifyDestroyed();
		} else if (cmd == INFO) {
			if (EVAL_ON == 0) {
				try {
					Image img = Image.createImage("/me4se-logo-small.png");
					Alert infoAlert = new Alert("ME4SE Info", "Please visit http://www.me4se.org for further information about the device emulation.", img, AlertType.INFO);
					Display.getDisplay(this).setCurrent(infoAlert);
				} catch (IOException ex) {
					Alert infoAlert = new Alert("ME4SE Info");
					infoAlert.setString("Please visit http://www.me4se.org for further information about the device emulation.");
					Display.getDisplay(this).setCurrent(infoAlert);
				}
			} else if (EVAL_ON == 1) {
				try {
					Image img = Image.createImage("/me4se-logo-small.png");
					Alert infoAlert = new Alert("ME4SE Evaluation", "This is an evaluation version of ME4SE not for commercial or public use. If you can read this on a website, where ME4SE is used as emulator, please send an email to contact@kroll-haustein.com to inform the developers of ME4SE about the not permitted use !", img, AlertType.INFO);
					Display.getDisplay(this).setCurrent(infoAlert);
				} catch (IOException ex) {
					Alert infoAlert = new Alert("ME4SE Evaluation");
					infoAlert.setString("This is an evaluation version of ME4SE not for commercial or public use. If you can read this on a website, where ME4SE is used as emulator, please send an email to contact@kroll-haustein.com to inform the developers of ME4SE about the not permitted use !");
					Display.getDisplay(this).setCurrent(infoAlert);
				}
			}
		}
	}
}
