/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.crisma.worldstate.editor;

import javax.swing.JPanel;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  $Revision$, $Date$
 */
public abstract class AbstractDetailEditor extends JPanel implements DetailEditor {

    //~ Instance fields --------------------------------------------------------

    private transient CidsBean worldstate;
    private transient CidsBean descriptor;

    //~ Methods ----------------------------------------------------------------

    @Override
    public CidsBean getWorldstate() {
        return worldstate;
    }

    @Override
    public void setWorldstate(final CidsBean worldstate) {
        this.worldstate = worldstate;
    }

    @Override
    public CidsBean getDescriptor() {
        return descriptor;
    }

    @Override
    public void setDescriptor(final CidsBean descriptor) {
        this.descriptor = descriptor;
    }
}
