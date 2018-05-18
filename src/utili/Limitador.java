/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utili;

import javax.swing.text.*;

public class Limitador extends PlainDocument {

    private int iMaxLength;

    public Limitador(int maxlen) {
        super();
        iMaxLength = maxlen;
    }

    @Override
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str == null) {
            return;
        }
        if (iMaxLength <= 0) { // aceitará qualquer nº de caracteres
            super.insertString(offset, str, attr);
            return;
        }

        int ilen = (getLength() + str.length());

        if (ilen <= iMaxLength) { // se o comprimento final for menor...
            super.insertString(offset, str, attr); // ...aceita str
        }
    }
}
