/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.crisma.worldstate.viewer;

import javax.swing.JComponent;

import de.cismet.cids.custom.crisma.DescriptorContainer;
import de.cismet.cids.custom.crisma.WorldstateContainer;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  1.0
 */
public interface DetailView extends WorldstateContainer, DescriptorContainer {

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
