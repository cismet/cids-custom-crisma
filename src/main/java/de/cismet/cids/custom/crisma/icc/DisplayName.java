/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.crisma.icc;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  $Revision$, $Date$
 */
public abstract class DisplayName {

    //~ Instance fields --------------------------------------------------------

    String displayName;

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  displayName  DOCUMENT ME!
     */
    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }
}
