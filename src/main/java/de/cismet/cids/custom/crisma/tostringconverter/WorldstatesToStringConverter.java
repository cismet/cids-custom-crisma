/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.crisma.tostringconverter;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.tools.CustomToStringConverter;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  $Revision$, $Date$
 */
public final class WorldstatesToStringConverter extends CustomToStringConverter {

    //~ Methods ----------------------------------------------------------------

    @Override
    public String createString() {
        int step = 0;
        CidsBean parent = (CidsBean)cidsBean.getProperty("parentworldstate");
        while (parent != null) {
            ++step;
            parent = (CidsBean)parent.getProperty("parentworldstate");
        }

        return "<html>" + cidsBean.getProperty("name") + " [T<sub>" + step + "</sub>]</html>";
    }
}
