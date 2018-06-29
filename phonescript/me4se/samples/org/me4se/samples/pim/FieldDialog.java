package org.me4se.samples.pim;

import java.util.Date;
import javax.microedition.lcdui.*;
import javax.microedition.pim.*;

/**
 * @author Stefan Haustein
 */
public class FieldDialog implements CommandListener {

    ContactDialog contactDialog;
    Contact contact;
    PIMDemo pimDemo;
    int field;
    int type;
    int index;

    Form form;
    List list;
    PIMList pimList;
    TextField[] textFields;
    DateField dateField;
    ChoiceGroup attributeChoice;

    static Command COMMIT = new Command("OK", Command.OK, 0);
    static Command NEW = new Command("New", Command.SCREEN, 0);
    static Command CANCEL = new Command("Cancel", Command.CANCEL, 0);
    static Command BACK = new Command("Back", Command.BACK, 0);
    static Command EDIT = new Command("Edit", Command.ITEM, 0);
    static Command DELETE = new Command("Delete", Command.ITEM, 0);

    FieldDialog(ContactDialog contactDialog, int field) {
        this.contactDialog = contactDialog;
        this.contact = contactDialog.contact;
        this.pimDemo = contactDialog.pimDemo;
        this.field = field;
        this.pimList = contact.getPIMList();
        this.type = pimList.getFieldDataType(field);
    }

    void showForm(int index) {
        this.index = index;
        form = new Form(PIMUtil.getString(contact, Contact.FORMATTED_NAME));
        String label = pimList.getFieldLabel(field);

        if (type == PIMItem.STRING_ARRAY) {
            String values[] =
                index == -1 ? null : contact.getStringArray(field, index);

            int[] aes = contact.getPIMList().getSupportedArrayElements(field);
            textFields = new TextField[aes.length];
            for (int i = 0; i < aes.length; i++) {
                textFields[i] =
                    new TextField(
                        pimList.getArrayElementLabel(field, aes[i]),
                        index == -1 ? "" : values[aes[i]],
                        64,
                        TextField.ANY);
                form.append(textFields[i]);
            }
        }
        else if (type == PIMItem.DATE) {
            dateField = new DateField(label, DateField.DATE);
            if (index != -1) {
                dateField.setDate(new Date(contact.getDate(field, index)));
            }
            form.append(dateField);
        }
        else if (type == PIMItem.STRING) {
            String value = (index == -1) ? "" : contact.getString(field, index);

            textFields =
                new TextField[] {
                     new TextField(label, value, 64, TextField.ANY)};
            form.append(textFields[0]);

        }
        else
            return;

        int[] attrs = pimList.getSupportedAttributes(field);

        attributeChoice = new ChoiceGroup("Attributes", Choice.MULTIPLE);
        if (attrs != null && attrs.length > 0) {
            for (int i = 0; i < attrs.length; i++) {
                attributeChoice.append(
                    pimList.getAttributeLabel(attrs[i]),
                    null);
                if (index != -1)
                    attributeChoice.setSelectedIndex(
                        i,
                        (contact.getAttributes(field, index) & attrs[i]) != 0);
            }
            form.append(attributeChoice);
        }

        form.addCommand(COMMIT);
        form.addCommand(CANCEL);

        if (contact.countValues(field) == 1 && pimList.maxValues(field) > 1) {
            form.addCommand(NEW);
            form.addCommand(DELETE);
        }

        form.setCommandListener(this);
        pimDemo.display.setCurrent(form);
    }

    public void show() {
        if (field == Contact.UID)
            return;
        if (type != PIMItem.STRING
            && type != PIMItem.STRING_ARRAY
            && type != PIMItem.DATE)
            return;

        // decide whether to show a single item or a selection list

        if (contact.countValues(field) < 2)
            showForm(contact.countValues(field) - 1);
        else
            showList();
    }

    public void showList() {
        list =
            new List(
                pimList.getFieldLabel(field)
                    + ","
                    + PIMUtil.getString(contact, Contact.FORMATTED_NAME),
                List.IMPLICIT);
        for (int i = 0; i < contact.countValues(field); i++) {
            list.append(PIMUtil.getString(contact, field, i), null);
        }

        list.addCommand(BACK);

        if (contact.countValues(field) < pimList.maxValues(field))
            list.addCommand(NEW);

        list.setCommandListener(this);
        pimDemo.display.setCurrent(list);
    }

    void commit() {
        int attr = 0;
        int[] supp = pimList.getSupportedAttributes(field);
        for (int i = 0; i < supp.length; i++) {
            if (attributeChoice.isSelected(i))
                attr |= supp[i];
        }

        int type = pimList.getFieldDataType(field);
        if (type == PIMItem.STRING_ARRAY) {
            String[] values = new String[field == Contact.NAME ? 5 : 6];
            supp = pimList.getSupportedArrayElements(field);
            for (int i = 0; i < supp.length; i++)
                values[supp[i]] = textFields[i].getString();

            if (index == -1)
                contact.addStringArray(field, attr, values);
            else
                contact.setStringArray(field, index, attr, values);
        }
        else if (type == PIMItem.DATE) {
            long value = dateField.getDate().getTime();
            if (index == -1)
                contact.addDate(field, attr, value);
            else
                contact.setDate(field, index, attr, value);
        }
        else if (type == PIMItem.STRING) {
            String value = textFields[0].getString();
            if (index == -1)
                contact.addString(field, attr, value);
            else
                contact.setString(field, index, attr, value);
        }
    }

    /**
     * @see javax.microedition.lcdui.CommandListener#commandAction(javax.microedition.lcdui.Command, javax.microedition.lcdui.Displayable)
     */
    public void commandAction(Command cmd, Displayable d) {
        if (d == form && (cmd.equals(COMMIT) || cmd.equals(NEW))) {
            commit();
        }

        if (cmd.equals(NEW)) {
            showForm(-1);
        }
        else if (cmd.equals(List.SELECT_COMMAND)) {
            showForm(list.getSelectedIndex());
        }
        else {
            if (cmd.equals(DELETE))
                contact.removeValue(
                    field,
                    d == list ? list.getSelectedIndex() : index);

            if (cmd.equals(BACK) || contact.countValues(field) <= 1)
                contactDialog.show();
            else
                showList();
        }
    }
}
