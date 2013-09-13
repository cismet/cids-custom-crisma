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
public final class DamagedBuildings extends Common {

    //~ Instance fields --------------------------------------------------------

    Value lostBuildings;
    Value unsafeBuildings;

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Value getLostBuildings() {
        return lostBuildings;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  lostBuildings  DOCUMENT ME!
     */
    public void setLostBuildings(final Value lostBuildings) {
        this.lostBuildings = lostBuildings;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Value getUnsafeBuildings() {
        return unsafeBuildings;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  unsafeBuildings  DOCUMENT ME!
     */
    public void setUnsafeBuildings(final Value unsafeBuildings) {
        this.unsafeBuildings = unsafeBuildings;
    }
}
