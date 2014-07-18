/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.crisma.objectrenderer;

import org.openide.util.ImageUtilities;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;

import de.cismet.tools.gui.jbands.SimpleBandModel;
import de.cismet.tools.gui.jbands.interfaces.Band;
import de.cismet.tools.gui.jbands.interfaces.BandAbsoluteHeightProvider;
import de.cismet.tools.gui.jbands.interfaces.BandMember;
import de.cismet.tools.gui.jbands.interfaces.BandPostfixProvider;
import de.cismet.tools.gui.jbands.interfaces.BandPrefixProvider;
import de.cismet.tools.gui.jbands.interfaces.Spot;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  $Revision$, $Date$
 */
public final class IndicatorBand implements Band, BandPrefixProvider, BandPostfixProvider, BandAbsoluteHeightProvider {

    //~ Static fields/initializers ---------------------------------------------

    public static final Color A_FIERY = new Color(255, 101, 67);
//    private static final Color B_WATERMILLION = new Color(245, 105, 145);
    public static final Color C_FEELING_ORANGE = new Color(255, 159, 128);
    public static final Color D_AFFINITY = new Color(255, 186, 107);
    public static final Color E_ORANGE_SHERBERT = new Color(255, 196, 140);
    public static final Color F_PEACE_BABY_YELLOW = new Color(255, 220, 138);
    public static final Color G_JAYANTHI = new Color(255, 241, 158);
    public static final Color H_HONEY_DO = new Color(239, 250, 180);
    public static final Color I_SPLASH_OF_LIME = new Color(209, 242, 165);
    public static final Color J_CHARMING_FROGUETTE = new Color(181, 244, 188);

    //~ Instance fields --------------------------------------------------------

    private final LinkedList<IndicatorBandMember> members;
    private final IndicatorBandPrefix prefix;
    private final IndicatorBandPostfix postfix;
    private final CriteriaGroup zeroGroup;
    private final CriteriaGroup hundredGroup;
    private final SortedSet<CriteriaGroup> groups;
    private final SpotBand spotBand;
    private final SimpleBandModel model;

    private List<BandMember> spots;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new IndicatorBand object.
     *
     * @param  name          DOCUMENT ME!
     * @param  zeroGroup     DOCUMENT ME!
     * @param  hundredGroup  DOCUMENT ME!
     * @param  groups        DOCUMENT ME!
     * @param  model         DOCUMENT ME!
     */
    public IndicatorBand(final String name,
            final CriteriaGroup zeroGroup,
            final CriteriaGroup hundredGroup,
            final Collection<CriteriaGroup> groups,
            final SimpleBandModel model) {
        this.model = model;
        this.zeroGroup = zeroGroup;
        this.hundredGroup = hundredGroup;
        this.groups = new TreeSet<CriteriaGroup>();
        this.members = new LinkedList<IndicatorBandMember>();
        this.prefix = new IndicatorBandPrefix(this, name);
        this.postfix = new IndicatorBandPostfix(this);
        this.members.add(new IndicatorBandMember(0, 100, this));
        this.spotBand = new SpotBand();
        this.spots = new ArrayList<BandMember>();

        for (final CriteriaGroup group : groups) {
            addCriteriaGroup(group);
        }
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param   total  DOCUMENT ME!
     * @param   index  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  IllegalArgumentException  DOCUMENT ME!
     */
    public static Color getBandColor(final int total, final int index) {
        if (total > 9) {
            throw new IllegalArgumentException("don't have more than 9 colors available");
        }

        final Color c;
        if (index == 0) {
            c = A_FIERY;
        } else if ((total - 1) == index) {
            c = J_CHARMING_FROGUETTE;
        } else {
            if (total == 3) {
                c = E_ORANGE_SHERBERT;
            } else if (total == 4) {
                if (index == 1) {
                    c = D_AFFINITY;
                } else {
                    c = G_JAYANTHI;
                }
            } else if (total == 5) {
                if (index == 1) {
                    c = C_FEELING_ORANGE;
                } else if (index == 2) {
                    c = F_PEACE_BABY_YELLOW;
                } else {
                    c = H_HONEY_DO;
                }
            } else if (total == 6) {
                if (index == 1) {
                    c = C_FEELING_ORANGE;
                } else if (index == 2) {
                    c = E_ORANGE_SHERBERT;
                } else if (index == 3) {
                    c = F_PEACE_BABY_YELLOW;
                } else {
                    c = H_HONEY_DO;
                }
            } else if (total == 7) {
                if (index == 1) {
                    c = C_FEELING_ORANGE;
                } else if (index == 2) {
                    c = D_AFFINITY;
                } else if (index == 3) {
                    c = F_PEACE_BABY_YELLOW;
                } else if (index == 4) {
                    c = G_JAYANTHI;
                } else {
                    c = H_HONEY_DO;
                }
            } else if (total == 8) {
                if (index == 1) {
                    c = C_FEELING_ORANGE;
                } else if (index == 2) {
                    c = D_AFFINITY;
                } else if (index == 3) {
                    c = F_PEACE_BABY_YELLOW;
                } else if (index == 4) {
                    c = G_JAYANTHI;
                } else if (index == 5) {
                    c = H_HONEY_DO;
                } else {
                    c = I_SPLASH_OF_LIME;
                }
            } else {
                if (index == 1) {
                    c = C_FEELING_ORANGE;
                } else if (index == 2) {
                    c = D_AFFINITY;
                } else if (index == 3) {
                    c = E_ORANGE_SHERBERT;
                } else if (index == 4) {
                    c = F_PEACE_BABY_YELLOW;
                } else if (index == 5) {
                    c = G_JAYANTHI;
                } else if (index == 6) {
                    c = H_HONEY_DO;
                } else {
                    c = I_SPLASH_OF_LIME;
                }
            }
        }

        return c;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public SimpleBandModel getModel() {
        return model;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public CriteriaGroup getZeroGroup() {
        return zeroGroup;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public CriteriaGroup getHundredGroup() {
        return hundredGroup;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public SortedSet<CriteriaGroup> getGroups() {
        return groups;
    }

    @Override
    public int getNumberOfMembers() {
        return members.size();
    }

    @Override
    public IndicatorBandMember getMember(final int i) {
        return members.get(i);
    }

    @Override
    public double getMin() {
        return 0d;
    }

    @Override
    public double getMax() {
        return 100d;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   bandMember  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public int indexOf(final BandMember bandMember) {
        return members.indexOf(bandMember);
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public JComponent getPrefixComponent() {
        return prefix;
    }

    @Override
    public JComponent getPostfixComponent() {
        return postfix;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public SpotBand getSpotBand() {
        return spotBand;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  group  DOCUMENT ME!
     */
    public void addCriteriaGroup(final CriteriaGroup group) {
        groups.add(group);
        final IndicatorBandMember newMem = new IndicatorBandMember(this);
        final ListIterator<IndicatorBandMember> mem = members.listIterator();
        while (mem.hasNext()) {
            final IndicatorBandMember ibm = mem.next();
            if ((ibm.getMin() < group.getLevelOfSatisfaction()) && (ibm.getMax() > group.getLevelOfSatisfaction())) {
                newMem.setMin(group.getLevelOfSatisfaction());
                newMem.setMax(ibm.getMax());
                ibm.setMax(group.getLevelOfSatisfaction());
                mem.add(newMem);
            }
        }
        spots.add(new SpotBandMember(group));
        model.fireBandModelChanged();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  group  DOCUMENT ME!
     */
    public void deleteCriteriaGroup(final CriteriaGroup group) {
        final ListIterator<IndicatorBandMember> li = members.listIterator();
        while (li.hasNext()) {
            final IndicatorBandMember ibm = li.next();
            if (ibm.getMax() == group.getLevelOfSatisfaction()) {
                final IndicatorBandMember m2 = li.next();
                ibm.setMax(m2.getMax());
                li.remove();
            }
        }
        groups.remove(group);
        model.fireBandModelChanged();
    }

    @Override
    public int getAbsoluteHeight() {
        return 24;
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private final class SpotBand implements Band, BandAbsoluteHeightProvider {

        //~ Methods ------------------------------------------------------------

        @Override
        public int getNumberOfMembers() {
            return spots.size();
        }

        @Override
        public BandMember getMember(final int i) {
            return spots.get(i);
        }

        @Override
        public double getMin() {
            return 0;
        }

        @Override
        public double getMax() {
            return 100;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }

        @Override
        public int getAbsoluteHeight() {
            return 6;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private final class SpotBandMember implements Spot {

        //~ Instance fields ----------------------------------------------------

        private final CriteriaGroup group;
        private final JComponent c;
        private final JPopupMenu popupMenu;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new SpotBandMember object.
         *
         * @param  group  position DOCUMENT ME!
         */
        public SpotBandMember(final CriteriaGroup group) {
            this.group = group;
            c = new JLabel(ImageUtilities.loadImageIcon(
                        SpotBandMember.class.getPackage().getName().replaceAll("\\.", "/")
                                + "/up_arrow.png",
                        false));
            c.setToolTipText("<html>Level of satisfaction: " + group.getLevelOfSatisfaction() + " %<br/>"
                        + "Indicator Value: " + group.getValue() + " " + group.getUnit() + "</html>");
            popupMenu = new JPopupMenu();
            popupMenu.add(new AbstractAction("Delete") {

                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        spots.remove(SpotBandMember.this);
                        deleteCriteriaGroup(group);
                    }
                });
            c.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mousePressed(final MouseEvent e) {
                        mouseClicked(e);
                    }

                    @Override
                    public void mouseReleased(final MouseEvent e) {
                        mouseClicked(e);
                    }

                    @Override
                    public void mouseClicked(final MouseEvent e) {
                        if (e.isPopupTrigger() && WorldstatesAggregationRenderer._critEditing) {
                            popupMenu.show(c, e.getX(), e.getY());
                        }
                    }
                });
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public double getPosition() {
            return group.getLevelOfSatisfaction();
        }

        @Override
        public double getMin() {
            return group.getLevelOfSatisfaction();
        }

        @Override
        public double getMax() {
            return group.getLevelOfSatisfaction();
        }

        @Override
        public JComponent getBandMemberComponent() {
            return c;
        }
    }
}
