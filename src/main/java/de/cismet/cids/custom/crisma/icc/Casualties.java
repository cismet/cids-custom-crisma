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
public final class Casualties extends DisplayName {

    //~ Instance fields --------------------------------------------------------

    Value noOfDead;
    Value noOfHomeless;
    Value noOfInjured;

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Value getNoOfDead() {
        return noOfDead;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  noOfDead  DOCUMENT ME!
     */
    public void setNoOfDead(final Value noOfDead) {
        this.noOfDead = noOfDead;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Value getNoOfHomeless() {
        return noOfHomeless;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  noOfHomeless  DOCUMENT ME!
     */
    public void setNoOfHomeless(final Value noOfHomeless) {
        this.noOfHomeless = noOfHomeless;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Value getNoOfInjured() {
        return noOfInjured;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  noOfInjured  DOCUMENT ME!
     */
    public void setNoOfInjured(final Value noOfInjured) {
        this.noOfInjured = noOfInjured;
    }
}
