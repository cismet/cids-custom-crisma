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
public final class Cost extends ValueIterable {

    //~ Instance fields --------------------------------------------------------

    Value directDamageCost;
    Value indirectDamageCost;
    Value restorationCost;

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Value getDirectDamageCost() {
        return directDamageCost;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  directDamageCost  DOCUMENT ME!
     */
    public void setDirectDamageCost(final Value directDamageCost) {
        this.directDamageCost = directDamageCost;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Value getIndirectDamageCost() {
        return indirectDamageCost;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  indirectDamageCost  DOCUMENT ME!
     */
    public void setIndirectDamageCost(final Value indirectDamageCost) {
        this.indirectDamageCost = indirectDamageCost;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Value getRestorationCost() {
        return restorationCost;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  restorationCost  DOCUMENT ME!
     */
    public void setRestorationCost(final Value restorationCost) {
        this.restorationCost = restorationCost;
    }
}
