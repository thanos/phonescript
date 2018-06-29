// ME4SE - A MicroEdition Emulation for J2SE 
//
// Copyright (C) 2001 Stefan Haustein, Oberhausen (Rhld.), Germany
//
// Contributors:
//
// STATUS: API complete, optional image functionality missing
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License as
// published by the Free Software Foundation; either version 2 of the
// License, or (at your option) any later version. This program is
// distributed in the hope that it will be useful, but WITHOUT ANY
// WARRANTY; without even the implied warranty of MERCHANTABILITY or
// FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public
// License for more details. You should have received a copy of the
// GNU General Public License along with this program; if not, write
// to the Free Software Foundation, Inc., 59 Temple Place - Suite 330,
// Boston, MA 02111-1307, USA.

package javax.microedition.lcdui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.microedition.midlet.ApplicationManager;

import org.kobjects.scm.ScmScrollBar;
import org.kobjects.scm.ScmScrollPane;
import org.me4se.impl.lcdui.DeviceLabel;

/**
 * @API MIDP-1.0
 * @API MIDP-2.0 
 * 
 * @TODO: Dont perferform select event if focus not on the same event
 * @TODO: If the selected element is deleted, the focus is lost completly
 * @TODO: Selection index is lost if the button menu is displayed.
 * 
 */
public class List extends Screen implements Choice {

    /**
     * @API MIDP-1.0
     */
    public static final Command SELECT_COMMAND =
        new Command("", Command.SCREEN, 0);

    int type;
    ScmDeviceList list = new ScmDeviceList(this);
    ScmScrollPane scrollPane = new ScmScrollPane();
    Vector group;

    ActionListener listener = new ActionListener() {
        public void actionPerformed(ActionEvent ev) {
            handleCommand(SELECT_COMMAND, null);
        }
    };

    /**
     * @API MIDP-1.0
     */
    public List(String title, int listType) {
        super(title);
        type = listType;
        if (type != MULTIPLE)
            group = new Vector();

        scrollPane.add(list);
        ScmScrollBar sb = new ScmScrollBar();
        sb.setBackground(
            javax.microedition.midlet.ApplicationManager.manager.bgColor);

        if (ApplicationManager.manager.getFlag("scrollbar"))
            scrollPane.setVerticalBar(sb);

        container.setMain(scrollPane);

        selectButtonRequired = true;
        container.updateButtons();
    }

    /**
     * @API MIDP-1.0
     */
    public List(
        String title,
        int listType,
        String[] stringElements,
        Image[] imageElements) {

        this(title, listType);

        for (int i = 0; i < stringElements.length; i++)
            append(
                stringElements[i],
                imageElements == null ? null : imageElements[i]);
    }

    /**
     * @API MIDP-1.0
     */
    public int append(String s, Image i) {
        insert(size(), s, i);
        return size() - 1;
    }

    /**
     * @API MIDP-1.0
     */
    public void delete(int index) {
        if (index >= size() || index < 0)
            throw new IndexOutOfBoundsException();
        if (group != null)
            group.removeElementAt(index);
        list.remove(index);
        
       _showNotify();
    }

    DeviceLabel getLabel(int index) {
        if (index >= size() || index < 0)
            throw new IndexOutOfBoundsException();
        return (DeviceLabel) list.getComponent(index);
    }

    /**
     * @API MIDP-1.0
     */
    public Image getImage(int index) {
        return (Image) getLabel(index).object;
    }

    /**
     * @API MIDP-1.0
     */
    public int getSelectedFlags(boolean[] flags) {
        int cnt = size();

        for (int i = 0; i < cnt; i++)
            flags[i] = isSelected(i);

        for (int i = cnt; i < flags.length; i++)
            flags[i] = false;

        return cnt;
    }

    /**
     * @API MIDP-1.0
     */
    public int getSelectedIndex() {
        if (type != MULTIPLE) {
/*
			if (type == IMPLICIT) {
				for (int i = 0; i < size(); i++)
					if (getLabel(i).hasFocus()){
						setSelectedIndex(i, true);
						return i;
					}
			}*/

            for (int i = 0; i < size(); i++)
                if (isSelected(i))
                    return i;
        }
        return -1;
    }

    /**
     * @API MIDP-1.0
     */
    public String getString(int index) {
        return getLabel(index).getText();
    }

    /**
     * @API MIDP-1.0
     */
    public void insert(int index, String stringItem, Image imageItem) {
        if (index > size() || index < 0)
            throw new IndexOutOfBoundsException();
        DeviceLabel label = new DeviceLabel("item", null, true);
        label.selectButtonRequired = true;
        label.setText(stringItem);
        if (imageItem != null) {
            label.object = imageItem;
            label.image = imageItem._image;
        }
        label.highlight = true;
        label.checkbox = type != IMPLICIT;
        //       label.addKeyListener(eventHandler);

        if (type == IMPLICIT){
			label.selectOnFocus = true;
            label.addActionListener(listener);
		}

        if (group != null) {
            group.addElement(label);
            label.group = group;
        }

        list.add(label, index);
    }

    /**
     * @API MIDP-1.0
     */
    public boolean isSelected(int index) {
        return getLabel(index).selected();
    }

    /**
     * @API MIDP-1.0
     */
    public void set(int index, String str, Image img) {
        boolean sel = isSelected(index);
        delete(index);
        insert(index, str, img);
        setSelectedIndex(index, sel);
    }

    /**
     * @API MIDP-1.0
     */
    public void setSelectedFlags(boolean[] flags) {

        for (int i = 0; i < size(); i++) {
            getLabel(i).select(flags[i]);
            if (group != null && flags[i])
                return;
        }
    }

    /**
     * @API MIDP-1.0
     */
    public void setSelectedIndex(int i, boolean state) {
        getLabel(i).select(state);
    }

    /**
     * @API MIDP-1.0
     */
    public int size() {
        return list.getComponentCount();
    }

    /**
     * @ME4SE INTERNAL
     */
    public String toString() {
        StringBuffer buf = new StringBuffer(getClass() + "(lcduiList)[");
        for (int i = 0; i < size(); i++) {
            if (i > 0)
                buf.append(',');
            buf.append(getString(i));
        }
        buf.append(']');
        return buf.toString();
    }

    /**
     * @ME4SE INTERNAL
     */
    void _showNotify() {
		if(type == IMPLICIT){
			int i = getSelectedIndex();
			if(i == -1) list.validateFocus();
			else
			getLabel(i).requestFocus();
		}
		else    	
	        list.validateFocus();
        
    }

    /**
     * Sets a ticker for use with this Displayable, replacing any previous ticker. 
     * If null, removes the ticker object from this Displayable. The same ticker 
     * may be shared by several Displayable objects within an application. This is 
     * done by calling setTicker() with the same Ticker object on several different 
     * Displayable objects. If the Displayable is actually visible on the display, 
     * the implementation should update the display as soon as it is feasible to do so. 
     * The existence of a ticker may affect the size of the area available for 
     * Displayable's contents. If the application adds, removes, or sets the ticker 
     * text at runtime, this can dynamically change the size of the content area. This 
     * is most important to be aware of when using the Canvas class.
     * 
     * Overrides: setTicker in class Displayable
     * @param ticker the ticker object used on this screen
     * 
     * @API MIDP-2.0
     */
    public void setTicker(Ticker ticker) {
        super.setTicker(ticker);
    }

    /**
     * Sets the title of the Displayable. If null is given, removes the title. 
     * If the Displayable is actually visible on the display, the implementation 
     * should update the display as soon as it is feasible to do so.
     * The existence of a title may affect the size of the area available 
     * for Displayable content. If the application adds, removes, or sets 
     * the title text at runtime, this can dynamically change the size of the 
     * content area. This is most important to be aware of when using the
     * Canvas class.
     * 
     * Overrides: setTitle in class Displayable
     * 
     * @param s - the new title, or null for no title
     * 
     * @API MIDP-2.0
     */
    public void setTitle(String s) {
        super.setTitle(s);
    }

    /**
     * Deletes all elements from this List.
     *
     * @API MIDP-2.0
     */
    public void deleteAll() {
		for(int i = size()-1; i >= 0; i--){
			delete(i);
		}
    }

    /**
     * The same as Displayable.removeCommand but with the following additional 
     * semantics. If the command to be removed happens to be the select command, 
     * the List is set to have no select command, and the command is removed 
     * from the List.
     * The following code:
     * 
     * // Command c is the select command on List list
     * list.removeCommand(c);
     * 
     * is equivalent to the following code:
     * 
     * // Command c is the select command on List list
     * list.setSelectCommand(null);
     * list.removeCommand(c);
     * 
     * Overrides: removeCommand in class Displayable
     * @param cmd  the command to be removed
     * 
     * @API MIDP-2.0
     */

    public void removeCommand(Command cmd) {
    	super.removeCommand(cmd);
    }

    /**
     * Sets the Command to be used for an IMPLICIT List selection action. 
     * By default, an implicit selection of a List will result in the 
     * predefined List.SELECT_COMMAND being used. This behavior may be 
     * overridden by calling the List.setSelectCommand() method with an 
     * appropriate parameter value. If a null reference is passed, this 
     * indicates that no "select" action is appropriate for the contents 
     * of this List.
     * 
     * If a reference to a command object is passed, and it is not the 
     * special command List.SELECT_COMMAND, and it is not currently present 
     * on this List object, the command object is added to this List as if 
     * addCommand(command) had been called prior to the command being made 
     * the select command. This indicates that this command is to be invoked
     * when the user performs the "select" on an element of this List.
     * 
     * The select command should have a command type of ITEM to indicate that 
     * it operates on the currently selected object. It is not an error if the 
     * command is of some other type. (List.SELECT_COMMAND has a type of SCREEN 
     * for historical purposes.) For purposes of presentation and placement within
     * its user interface, the implementation is allowed to treat the select 
     * command as if it were of type ITEM.
     * 
     * If the select command is later removed from the List with removeCommand(), 
     * the List is set to have no select command as if List.setSelectCommand(null) 
     * had been called.
     * 
     * The default behavior can be reestablished explicitly by calling setSelectCommand() 
     * with an argument of List.SELECT_COMMAND.
     * 
     * This method has no effect if the type of the List is not IMPLICIT.
     * 
     * @param command the command to be used for an IMPLICIT list selection action, 
     *                or null if there is none
     * 
     * @API MIDP-2.0
     * @ME4SE UNIMPLEMENTED 
     */

    public void setSelectCommand(Command command) {
        System.out.println("List.selectCommand() called with no effect!");
    }

    /**
     * Sets the application's preferred policy for fitting Choice element 
     * contents to the available screen space. The set policy applies 
     * for all elements of the Choice object. Valid values are TEXT_WRAP_DEFAULT, 
     * TEXT_WRAP_ON, and TEXT_WRAP_OFF. Fit policy is a hint, and the implementation
     * may disregard the application's preferred policy.
     * 
     * @param fitPolicy preferred content fit policy for choice elements
     * @throws IllegalArgumentException - if fitPolicy is invalid
     * 
     * @API MIDP-2.0
     * @ME4SE UNIMPLEMENTED
     */
    public void setFitPolicy(int fitPolicy) {
        System.out.println("List.setFitPolicy() called with no effect!");
    }

    /**
     * Sets the application's preferred policy for fitting Choice element contents 
     * to the available screen space. The set policy applies for all elements of the 
     * Choice object. Valid values are Choice.TEXT_WRAP_DEFAULT, Choice.TEXT_WRAP_ON, 
     * and Choice.TEXT_WRAP_OFF. Fit policy is a hint, and the implementation may 
     * disregard the application's preferred policy.
     * 
     * Specified by: setFitPolicy in interface Choice
     * 
     * @param fitPolicy preferred content fit policy for choice elements
     * @throws IllegalArgumentException if fitPolicy is invalid
     * 
     * @API MIDP-2.0
     * @ME4SE UNIMPLEMENTED
     */
    public int getFitPolicy() {
        System.out.println("List.getFitPolicy() called with no effect!");
        return -4711;
    }

    /**
    	 * Sets the application's preferred font for rendering the specified 
    	 * element of this Choice. An element's font is a hint, and the implementation 
    	 * may disregard the application's preferred font.
    	 * The elementNum parameter must be within the range [0..size()-1], inclusive.
    	 * The font parameter must be a valid Font object or null. If the font parameter 
    	 * is null, the implementation must use its default font to render the element.
    	 * 
    	 * Specified by: setFont in interface Choice
    	 * 
    	 * @param elementNum the index of the element, starting from zero
    	 * @param font the preferred font to use to render the element
    	 * @throws IndexOutOfBoundsException - if elementNum is invalid
    	 * 
     * @API MIDP-2.0
     * @ME4SE UNIMPLEMENTED
    	 */
    public void setFont(int elementNum, Font font) {
        System.out.println("List.setFont() called with no effect!");
    }

    /**
     * Gets the application's preferred font for rendering the specified 
     * element of this Choice. The value returned is the font that had been 
     * set by the application, even if that value had been disregarded by the 
     * implementation. If no font had been set by the application, or if the 
     * application explicitly set the font to null, the value is the default 
     * font chosen by the implementation. 
     * The elementNum parameter must be within the range [0..size()-1], inclusive.
     * 
     * Specified by: getFont in interface Choice
     * 
     * @param elementNum the index of the element, starting from zero
     * @return the preferred font to use to render the element
     * @throws IndexOutOfBoundsException - if elementNum is invalid
     *
     * @API MIDP-2.0
     * @ME4SE UNIMPLEMENTED	  
     */
    public Font getFont(int elementNum) {
        System.out.println("List.getFont() called with no effect!");
        return null;
    }
}
