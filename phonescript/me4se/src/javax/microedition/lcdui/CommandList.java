package javax.microedition.lcdui;

import java.util.Vector;

//import javax.microedition.midlet.*;
//import java.awt.*;
//import java.awt.event.*;
//import org.me4se.impl.lcdui.*;

class CommandList extends List implements CommandListener {

    private Displayable owner;
    private Command cancel = new Command("Cancel", Command.CANCEL, 0);
	Vector commandInfo;

    protected CommandList(Displayable d) {

        super("Select", Choice.IMPLICIT);
        owner = d;

        addCommand(cancel);
        commandInfo = d.getCommandInfoList();

        for (int i = 0; i < commandInfo.size(); i++) {
            CmdInfo c = (CmdInfo) commandInfo.elementAt(i);
            append(c.command.getLabel(), null);
        }

        setCommandListener(this);
    }

    public void commandAction(Command cmd, Displayable d) {
        //System.out.println ("commandAction received!!!");
        display.setCurrent(owner);
        if (cmd != cancel){
        	CmdInfo ci = (CmdInfo) commandInfo.elementAt(getSelectedIndex());
            owner.container.displayable.handleCommand(ci.command, ci.item);
		}
    }

}
