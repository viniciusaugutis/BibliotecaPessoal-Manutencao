/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utili;

import java.awt.Label;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;

public class Relogio {

    private Date date;
    private int delay;
    private SimpleDateFormat formatter;
    private JLabel label;
    private boolean stop;
    private Thread thread;

    public Relogio(JLabel label, String format, int delay) {
        super();
        try {
            this.label = label;
            this.delay = delay;
            date = new Date();
            formatter = new SimpleDateFormat(format);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            stop = false;
            thread = new Thread(new Runnable() {

                public void run() {
                    try {
                        String text = null;
                        while (!stop) {
                            date.setTime(System.currentTimeMillis());
                            text = formatter.format(date);
                            label.setText(text);
                            Thread.sleep(delay);
                        }
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                }
            });
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        stop = true;
    }
}

