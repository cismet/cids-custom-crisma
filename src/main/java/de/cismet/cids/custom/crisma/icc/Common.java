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
public abstract class Common {

    //~ Instance fields --------------------------------------------------------

    String displayName;
    String iconResource;

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

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getIconResource() {
        return iconResource;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  iconResource  DOCUMENT ME!
     */
    public void setIconResource(final String iconResource) {
        this.iconResource = iconResource;
    }
}
