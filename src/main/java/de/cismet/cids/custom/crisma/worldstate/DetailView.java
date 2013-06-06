/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.crisma.worldstate;

import javax.swing.JComponent;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  $Revision$, $Date$
 */
public interface DetailView {

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    JComponent getView();

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    JComponent getMiniatureView();

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    String getId();
    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    String getDisplayName();
}
