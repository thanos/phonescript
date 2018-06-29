package org.me4se.samples.pim;

import javax.microedition.pim.*;

/**
 * @author Stefan Haustein
 */
public class PIMUtil {

    static String getString(Contact contact, int field, int index) {
        
        StringBuffer buf = new StringBuffer();
        
        switch (contact.getPIMList().getFieldDataType(field)) {
            case PIMItem.STRING: 
                buf.append(contact.getString(field, index));
                break;
                
            case PIMItem.STRING_ARRAY: 
            {
                String[] val = contact.getStringArray(field, index);
                for (int i = 0; i < val.length; i++) {
                    if (val[i] != null && val[i].length() != 0) {
                        if (buf.length() >0) buf.append(',');
                        buf.append(val[i]);
                    }
                }
                break;
            }
        }
        
        int attr = contact.getAttributes(field, index);
        if (attr != 0) {
            buf.append(" (");        
            boolean first = true;            
            int supp[] = contact.getPIMList().getSupportedAttributes(field);
            for (int i = 0; i < supp.length; i++) {
                if ((attr & supp[i]) != 0) {
                    if (first) first = false;
                    else buf.append(',');
                    buf.append(contact.getPIMList().getAttributeLabel(supp[i]));
                }
            }
            buf.append(')');
        }
        
        return buf.toString();
    }

    static String getString(Contact contact, int field) {

        if (contact.countValues(field) == 0) return "";
        
        int pref = contact.getPreferredIndex(field);
        if (pref == -1) pref = 0;
        String result = getString(contact, field, pref);
        return result != null ? result : "";
    }

}
