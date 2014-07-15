/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.crisma.objectrenderer;

import javax.swing.JComponent;
import javax.swing.JLabel;

import de.cismet.tools.gui.jbands.interfaces.Band;
import de.cismet.tools.gui.jbands.interfaces.BandAbsoluteHeightProvider;
import de.cismet.tools.gui.jbands.interfaces.BandMember;
import de.cismet.tools.gui.jbands.interfaces.Section;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  $Revision$, $Date$
 */
public final class VGapBand implements Band, BandAbsoluteHeightProvider {

    //~ Methods ----------------------------------------------------------------

    @Override
    public int getNumberOfMembers() {
        return 1;
    }

    @Override
    public BandMember getMember(final int i) {
        return new Section() {

                @Override
                public double getFrom() {
                    return 0;
                }

                @Override
                public double getTo() {
                    return 100;
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
                public JComponent getBandMemberComponent() {
                    return new JLabel();
                }
            };
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
        return 5;
    }
}
