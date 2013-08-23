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
public final class DamagedInfrastructure extends Common {

    //~ Instance fields --------------------------------------------------------

    Value damagedRoadSegments;

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Value getDamagedRoadSegments() {
        return damagedRoadSegments;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  damagedRoadSegments  DOCUMENT ME!
     */
    public void setDamagedRoadSegments(final Value damagedRoadSegments) {
        this.damagedRoadSegments = damagedRoadSegments;
    }
}
