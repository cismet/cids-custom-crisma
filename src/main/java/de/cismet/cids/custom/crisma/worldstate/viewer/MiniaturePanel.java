/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.crisma.worldstate.viewer;

import java.awt.Dimension;

import javax.swing.JPanel;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  $Revision$, $Date$
 */
public abstract class MiniaturePanel extends JPanel {

    //~ Methods ----------------------------------------------------------------

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(120, 80);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(120, 80);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(120, 80);
    }

    @Override
    public void setMaximumSize(final Dimension maximumSize) {
        super.setMaximumSize(new Dimension(120, 80));
    }

    @Override
    public void setPreferredSize(final Dimension preferredSize) {
        super.setPreferredSize(new Dimension(120, 80));
    }

    @Override
    public void setMinimumSize(final Dimension minimumSize) {
        super.setMinimumSize(new Dimension(120, 80));
    }
}
