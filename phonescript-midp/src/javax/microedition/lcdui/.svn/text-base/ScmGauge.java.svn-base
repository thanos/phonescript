package javax.microedition.lcdui;

import org.me4se.impl.lcdui.*;
import java.awt.event.KeyEvent;

// IMPROVE: Allow mouse draging instead of only links in the corner....

class ScmGauge extends DeviceComponent {

    Gauge gauge;

    ScmGauge(boolean interactive) {
        super(interactive ? "item.gauge.interactive" : "item.gauge", interactive);
    }
    
    public void init(Gauge gauge){
    	this.gauge = gauge;
    }

    public boolean keyPressed(int code, int mask) {
        if (getFocusable()) {

            if (code == KeyEvent.VK_LEFT && gauge.value > 0) {
                gauge.value--;
                repaint();
                return true;
            }
            else if (
                code == KeyEvent.VK_RIGHT && gauge.value < gauge.maximum) {
                gauge.value++;
                repaint();
                return true;
            }
        }
        return super.keyPressed(code, mask);
    }
    
    public boolean mouseClicked(int button, int x, int y, int modifiers, int clicks){
    	if(super.mouseClicked(button, x, y, modifiers, clicks)) return true;
    	if(!gauge.isInteractive()) return false;
    	
    	if(button == 1)
    	if(x < getWidth() / 2){
    		if(gauge.value > 0){
    			gauge.value = Math.max(0, gauge.value - clicks);
    			repaint();
    			return true;		
    		}
    	}
    	else{
    		if(gauge.value < gauge.maximum){
				gauge.value = Math.min(gauge.maximum, gauge.value + clicks);
				repaint();
				return true;		
    		}
    	}
    	
    	return false;
    }

    public void paint(java.awt.Graphics g) {
        super.paint(g);

        if (getFocusable() && hasFocus()) {
            int m = getHeight() / 2;

            for (int i = 0; i < 4; i++) {
                g.drawLine(i, m - i, i, m + i);
                g.drawLine(
                    getWidth() - 1 - i,
                    m - i,
                    getWidth() - 1 - i,
                    m + i);
            }
        }

        g.setColor(getFontInfo().foreground);

        int w = getWidth() - 13;
        int h = getHeight() - 3;

        g.drawRect(6, 1, w, h);

        int mx = (w * gauge.value) / gauge.maximum;
        g.fillRect(6, 2, mx, h);
    }
}
