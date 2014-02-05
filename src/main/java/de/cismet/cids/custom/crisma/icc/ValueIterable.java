/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.crisma.icc;

import java.util.Iterator;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  $Revision$, $Date$
 */
public abstract class ValueIterable extends Common implements Iterable<Value> {

    //~ Methods ----------------------------------------------------------------

    @Override
    public Iterator<Value> iterator() {
        return new Iterator<Value>() {

                int i = 0;

                @Override
                public boolean hasNext() {
                    return i < ValueIterable.this.getClass().getDeclaredFields().length;
                }

                @Override
                public Value next() {
                    try {
                        return (Value)ValueIterable.this.getClass().getDeclaredFields()[i++].get(ValueIterable.this);
                    } catch (Exception ex) {
                        throw new RuntimeException("cannot iterate", ex);
                    }
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException("Not supported yet."); // To change body of generated
                                                                                   // methods, choose Tools | Templates.
                }
            };
    }
}
