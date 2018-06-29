package org.me4se.samples.pim;

import javax.microedition.pim.*;
import javax.microedition.lcdui.*;

/**
 * @author Stefan Haustein
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 */
public class ContactDialog implements CommandListener {

    PIMDemo pimDemo;
    Contact contact;
    List fieldList;
    Displayable previous;

    static final Command BACK = new Command("Back", Command.BACK, 0);
    static final Command CANCEL = new Command("Cancel", Command.CANCEL, 0);
	static final Command COMMIT = new Command("Commit", Command.OK, 0);

    /** Builds the forms for a Contact corresponding to the given list */

    public ContactDialog(PIMDemo pimDemo, Contact contact) {
        this.pimDemo = pimDemo;
        this.contact = contact;
    }
    
 

    public void show () {
        fieldList = new List (PIMUtil.getString(contact, Contact.FORMATTED_NAME), List.IMPLICIT);
        ContactList contacts = (ContactList) contact.getPIMList(); 
        int[] fields = contacts.getSupportedFields();
        for (int i = 0; i < fields.length; i++) {
            fieldList.append(contact.getPIMList().getFieldLabel(fields[i])+":"+PIMUtil.getString(contact, fields[i]), null);
        }

    	if (contact.isModified()) {
    		fieldList.removeCommand(BACK);
    		fieldList.addCommand(COMMIT);
    		fieldList.addCommand(CANCEL);
    	}		 
        else 
            fieldList.addCommand(BACK);

        fieldList.setCommandListener(this);
        
        pimDemo.display.setCurrent(fieldList);
    }
    /**
     * @see javax.microedition.lcdui.CommandListener#commandAction(javax.microedition.lcdui.Command, javax.microedition.lcdui.Displayable)
     */
    public void commandAction(Command cmd, Displayable d) {
        if (cmd.equals(List.SELECT_COMMAND)) {
           new FieldDialog(this, contact.getPIMList().getSupportedFields() [fieldList.getSelectedIndex()]).show();            
        }
        else {
        	if (cmd == COMMIT) {
                try {
                    contact.commit();
                }
                catch (PIMException e) {
                    throw new RuntimeException(e.toString());
                }
           		pimDemo.refreshList();
        	}
        	pimDemo.show();
        }
    }
}
