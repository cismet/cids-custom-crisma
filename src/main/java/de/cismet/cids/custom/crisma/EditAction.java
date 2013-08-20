/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.crisma;

import Sirius.navigator.ui.ComponentRegistry;

import org.openide.util.ImageUtilities;
import org.openide.util.lookup.ServiceProvider;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

import de.cismet.cids.custom.crisma.objectrenderer.WorldstatesRenderer;

import de.cismet.cids.navigator.utils.CidsClientToolbarItem;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  $Revision$, $Date$
 */
@ServiceProvider(service = CidsClientToolbarItem.class)
public final class EditAction extends AbstractAction implements CidsClientToolbarItem {

    //~ Instance fields --------------------------------------------------------

    private final transient ImageIcon saveIcon16;
    private final transient ImageIcon editIcon16;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new EditAction object.
     */
    public EditAction() {
        super(" Edit Worldstate ");

        editIcon16 = ImageUtilities.loadImageIcon(DeleteAction.class.getPackage().getName().replaceAll("\\.", "/")
                        + "/world_edit_16.png",
                false);
        saveIcon16 = ImageUtilities.loadImageIcon(DeleteAction.class.getPackage().getName().replaceAll("\\.", "/")
                        + "/world_save_16.png",
                false);

        putValue(Action.SMALL_ICON, editIcon16);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void actionPerformed(final ActionEvent e) {
        final CidsBeanRenderer r = ComponentRegistry.getRegistry().getDescriptionPane().currentRenderer();
        if (r instanceof WorldstatesRenderer) {
            final WorldstatesRenderer wr = (WorldstatesRenderer)r;
            wr.setEditing(!wr.isEditing());
            putValue(Action.NAME, wr.isEditing() ? " Save Worldstate " : " Edit Worldstate ");
            putValue(Action.SMALL_ICON, wr.isEditing() ? saveIcon16 : editIcon16);
        }
    }

    @Override
    public String getSorterString() {
        return "-1";
    }

    @Override
    public boolean isVisible() {
        return true;
    }
}
