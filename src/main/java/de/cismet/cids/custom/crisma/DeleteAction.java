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

import org.openide.util.lookup.ServiceProvider;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

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

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new EditAction object.
     */
    public DeleteAction() {
        super(" Delete Worldstate ");
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void actionPerformed(final ActionEvent e) {
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
