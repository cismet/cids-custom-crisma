/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.crisma.icc;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Iterator;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  $Revision$, $Date$
 */
public final class ICCData implements Iterable<ValueIterable> {

    //~ Instance fields --------------------------------------------------------

    Casualties casualties;
    DamagedBuildings damagedBuildings;
    DamagedInfrastructure damagedInfrastructure;
    Cost cost;
    EvacuationCost evacuationCost;

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Casualties getCasualties() {
        return casualties;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  casualties  DOCUMENT ME!
     */
    public void setCasualties(final Casualties casualties) {
        this.casualties = casualties;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Cost getCost() {
        return cost;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cost  DOCUMENT ME!
     */
    public void setCost(final Cost cost) {
        this.cost = cost;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public DamagedBuildings getDamagedBuildings() {
        return damagedBuildings;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  damagedBuildings  DOCUMENT ME!
     */
    public void setDamagedBuildings(final DamagedBuildings damagedBuildings) {
        this.damagedBuildings = damagedBuildings;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public DamagedInfrastructure getDamagedInfrastructure() {
        return damagedInfrastructure;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  damagedInfrastructure  DOCUMENT ME!
     */
    public void setDamagedInfrastructure(final DamagedInfrastructure damagedInfrastructure) {
        this.damagedInfrastructure = damagedInfrastructure;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public EvacuationCost getEvacuationCost() {
        return evacuationCost;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  evacuationCost  DOCUMENT ME!
     */
    public void setEvacuationCost(final EvacuationCost evacuationCost) {
        this.evacuationCost = evacuationCost;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  args  DOCUMENT ME!
     */
    public static void main(final String[] args) {
        final Casualties cas = new Casualties();
        Value v = new Value();
        cas.setDisplayName("cas");
        v.setDisplayName("noofdead");
        v.setValue("val1");
        cas.setNoOfDead(v);
        v = new Value();
        v.setDisplayName("noofhomeless");
        v.setValue("val2");
        cas.setNoOfHomeless(v);
        v = new Value();
        v.setDisplayName("noofinjured");
        v.setValue("val3");
        cas.setNoOfInjured(v);

        final Cost cost = new Cost();
        cost.setDisplayName("cos1");
        v = new Value();
        v.setDisplayName("dir");
        v.setValue("1");
        cost.setDirectDamageCost(v);
        v = new Value();
        v.setDisplayName("ind");
        v.setValue("2");
        cost.setIndirectDamageCost(v);
        v = new Value();
        v.setDisplayName("res");
        v.setValue("3");
        cost.setRestorationCost(v);

        final DamagedBuildings db = new DamagedBuildings();
        db.setDisplayName("db");
        v = new Value();
        v.setDisplayName("lost");
        v.setValue("5");
        db.setLostBuildings(v);
        v = new Value();
        v.setDisplayName("uns");
        v.setValue("6");
        db.setUnsafeBuildings(v);

        final DamagedInfrastructure di = new DamagedInfrastructure();
        db.setDisplayName("dr");
        v = new Value();
        v.setDisplayName("road");
        v.setValue("8");
        di.setDamagedRoadSegments(v);

        final EvacuationCost ec = new EvacuationCost();
        ec.setDisplayName("ec");
        v = new Value();
        v.setDisplayName("evac");
        v.setValue("10");
        ec.setTotalEvacuationCost(v);

        final ICCData icc = new ICCData();

        final ObjectMapper mapper = new ObjectMapper(new JsonFactory());
//        mapper.writeValue(new File(""), icc);
    }

    @Override
    public Iterator<ValueIterable> iterator() {
        return new Iterator<ValueIterable>() {

                int i = 0;

                @Override
                public boolean hasNext() {
                    return i < ICCData.class.getDeclaredFields().length;
                }

                @Override
                public ValueIterable next() {
                    try {
                        return (ValueIterable)ICCData.class.getDeclaredFields()[i++].get(ICCData.this);
                    } catch (final Exception ex) {
                        throw new RuntimeException("cannot iterate", ex);
                    }
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException("Not supported yet."); // To change body of generated
                                                                                   // methods, choose Tools | Templates.
                }
            };
    }
}
