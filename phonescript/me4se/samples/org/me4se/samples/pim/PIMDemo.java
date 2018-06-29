package org.me4se.samples.pim;

import java.util.Vector;
import java.util.Enumeration;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;
import javax.microedition.pim.*;

/**
 * @author Stefan Haustein
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 */
public class PIMDemo extends MIDlet implements CommandListener {

    Vector contactVector;
    Display display;
    List screenList;
    ContactList contacts;

    Command DELETE = new Command("Delete", Command.ITEM, 0);
    Command EXIT = new Command("Exit", Command.EXIT, 0);
    Command NEW = new Command("New", Command.SCREEN, 0);

    public PIMDemo() {
        try {
            contacts =
                (ContactList) PIM.getInstance().openPIMList(
                    PIM.CONTACT_LIST,
                    PIM.READ_WRITE);
        }
        catch (PIMException e) {
            throw new RuntimeException(e.toString());
        }
    }

    void refreshList() {
        contactVector = new Vector();
        screenList = new List("Contacts", List.IMPLICIT);

        screenList.addCommand(EXIT);
        screenList.addCommand(NEW);
        screenList.addCommand(DELETE);
        screenList.setCommandListener(this);

        try {

            for (Enumeration e = contacts.items(); e.hasMoreElements();) {
                Contact contact = (Contact) e.nextElement();
                contactVector.addElement(contact);
                screenList.append(
                    PIMUtil.getString(contact, Contact.FORMATTED_NAME),
                    null);
            }
        }
        catch (PIMException e) {
            throw new RuntimeException(e.toString());
        }
    }

    void show() {
        display.setCurrent(screenList);
    }

    /**
     * @see javax.microedition.midlet.MIDlet#destroyApp(boolean)
     */
    protected void destroyApp(boolean unconditional)
        throws MIDletStateChangeException {
        try {
            contacts.close();
        }
        catch (PIMException e) {
        }
    }
    /**
     * @see javax.microedition.midlet.MIDlet#pauseApp()
     */
    protected void pauseApp() {
    }
    /**
     * @see javax.microedition.midlet.MIDlet#startApp()
     */
    protected void startApp() throws MIDletStateChangeException {
        display = Display.getDisplay(this);
        refreshList();
        show();
    }
    /**
     * @see javax.microedition.lcdui.CommandListener#commandAction(javax.microedition.lcdui.Command, javax.microedition.lcdui.Displayable)
     */
    public void commandAction(Command cmd, Displayable d) {
        if (cmd.equals(List.SELECT_COMMAND)) {
            new ContactDialog(
                this,
                (Contact) contactVector.elementAt(
                    screenList.getSelectedIndex()))
                .show();
        }
        else if (cmd.equals(EXIT)) {
            try {
                contacts.close();
            }
            catch (PIMException e) {
            }
            notifyDestroyed();
        }
        else if (cmd.equals(DELETE)) {
            contacts.removeContact(
                (Contact) contactVector.elementAt(
                    screenList.getSelectedIndex()));
            refreshList();
            show();
        }
        else if (cmd.equals(NEW)) {
            new ContactDialog(this, contacts.createContact()).show();
        }

    }

}
