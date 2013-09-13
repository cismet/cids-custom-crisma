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
public final class EvacuationCost extends Common {

    //~ Instance fields --------------------------------------------------------

    Value totalEvacuationCost;

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Value getTotalEvacuationCost() {
        return totalEvacuationCost;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  totalEvacuationCost  DOCUMENT ME!
     */
    public void setTotalEvacuationCost(final Value totalEvacuationCost) {
        this.totalEvacuationCost = totalEvacuationCost;
    }
}
