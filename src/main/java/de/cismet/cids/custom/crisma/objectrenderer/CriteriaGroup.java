/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.crisma.objectrenderer;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  $Revision$, $Date$
 */
public final class CriteriaGroup implements Comparable<CriteriaGroup> {

    //~ Instance fields --------------------------------------------------------

    private int levelOfSatisfaction;
    private double value;
    private String unit;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new CriteriaGroup object.
     */
    public CriteriaGroup() {
    }

    /**
     * Creates a new CriteriaGroup object.
     *
     * @param  levelOfSatisfaction  DOCUMENT ME!
     * @param  value                DOCUMENT ME!
     * @param  unit                 DOCUMENT ME!
     */
    public CriteriaGroup(final int levelOfSatisfaction, final double value, final String unit) {
        this.levelOfSatisfaction = levelOfSatisfaction;
        this.value = value;
        this.unit = unit;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public int getLevelOfSatisfaction() {
        return levelOfSatisfaction;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  levelOfSatisfaction  DOCUMENT ME!
     */
    public void setLevelOfSatisfaction(final int levelOfSatisfaction) {
        this.levelOfSatisfaction = levelOfSatisfaction;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public double getValue() {
        return value;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  value  DOCUMENT ME!
     */
    public void setValue(final double value) {
        this.value = value;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  unit  DOCUMENT ME!
     */
    public void setUnit(final String unit) {
        this.unit = unit;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getUnit() {
        return unit;
    }

    @Override
    public int compareTo(final CriteriaGroup o) {
        return ((Integer)this.getLevelOfSatisfaction()).compareTo(o.getLevelOfSatisfaction());
    }
}
