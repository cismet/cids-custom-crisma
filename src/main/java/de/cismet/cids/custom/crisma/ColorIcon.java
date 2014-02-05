/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.crisma;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  $Revision$, $Date$
 */
public final class ColorIcon implements Icon {

    //~ Static fields/initializers ---------------------------------------------

    private static final int HEIGHT = 24;
    private static final int WIDTH = 32;

    //~ Instance fields --------------------------------------------------------

    private final Color color;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ColorIcon object.
     *
     * @param  color  DOCUMENT ME!
     */
    public ColorIcon(final Color color) {
        this.color = color;
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void paintIcon(final Component c, final Graphics g, final int x, final int y) {
        final Color old = g.getColor();
        g.setColor(color);
        g.fillRect(x, y, WIDTH - 1, HEIGHT - 1);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, WIDTH - 1, HEIGHT - 1);
        g.setColor(old);
    }

    @Override
    public int getIconWidth() {
        return WIDTH;
    }

    @Override
    public int getIconHeight() {
        return HEIGHT;
    }
}
