/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.crisma;

import Sirius.navigator.types.treenode.DefaultMetaTreeNode;
import Sirius.navigator.types.treenode.ObjectTreeNode;
import Sirius.navigator.ui.ComponentRegistry;

import Sirius.server.middleware.types.MetaObject;

import org.apache.log4j.Logger;

import org.openide.util.ImageUtilities;
import org.openide.util.lookup.ServiceProvider;

import java.awt.event.ActionEvent;

import java.util.Collection;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import de.cismet.cids.navigator.utils.CidsClientToolbarItem;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  $Revision$, $Date$
 */
@ServiceProvider(service = CidsClientToolbarItem.class)
public final class DeleteAction extends AbstractAction implements CidsClientToolbarItem {

    //~ Static fields/initializers ---------------------------------------------

    /** LOGGER. */
    private static final transient Logger LOG = Logger.getLogger(DeleteAction.class);

    //~ Instance fields --------------------------------------------------------

    private final transient ImageIcon worldDel16;
    private final transient ImageIcon worldDel32;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new EditAction object.
     */
    public DeleteAction() {
        super(" Delete Worldstate ");

        worldDel16 = ImageUtilities.loadImageIcon(DeleteAction.class.getPackage().getName().replaceAll("\\.", "/")
                        + "/world_del_16.png",
                false);
        worldDel32 = ImageUtilities.loadImageIcon(DeleteAction.class.getPackage().getName().replaceAll("\\.", "/")
                        + "/world_del_32.png",
                false);
        putValue(Action.SMALL_ICON, worldDel16);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void actionPerformed(final ActionEvent e) {
        final Collection c = ComponentRegistry.getRegistry().getCatalogueTree().getSelectedNodes();
        if (!c.isEmpty()) {
            final int answer = JOptionPane.showConfirmDialog(ComponentRegistry.getRegistry().getMainWindow(),
                    "Do you really want to delete the selected worldstates?",
                    "Delete worldstates",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    worldDel32);
            if (JOptionPane.YES_OPTION == answer) {
                for (final Object o : ComponentRegistry.getRegistry().getCatalogueTree().getSelectedNodes()) {
                    final DefaultMetaTreeNode n = (DefaultMetaTreeNode)o;
                    if (n instanceof ObjectTreeNode) {
                        final MetaObject mo = ((ObjectTreeNode)n).getMetaObject();
                        if (mo.getMetaClass().getName().equalsIgnoreCase("worldstates")) {
                            try {
                                Tools.deleteWorldstate(mo.getBean());
                            } catch (Exception ex) {
                                LOG.error("cannot delete ws", ex);
                            }
                        }
                    }
                }
            }
        }

        Tools.reloadCatalogTree();
        ScenarioView.getInstance().updateLeafs();
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
