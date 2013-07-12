/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.crisma;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.types.treenode.RootTreeNode;
import Sirius.navigator.ui.ComponentRegistry;
import Sirius.navigator.ui.tree.MetaCatalogueTree;

import Sirius.server.middleware.types.MetaObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  $Revision$, $Date$
 */
public final class Tools {

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new Tools object.
     */
    private Tools() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param   worldstate  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static CidsBean saveWorldstate(final CidsBean worldstate) throws Exception {
        worldstate.setProperty(
            "parentworldstate",
            SessionManager.getProxy().getMetaObject(
                worldstate.getMetaObject().getID(),
                worldstate.getMetaObject().getClassID(),
                worldstate.getMetaObject().getDomain()).getBean());
        worldstate.setProperty("id", -1);
        worldstate.getMetaObject().setStatus(MetaObject.NEW);
        final CidsBean transition = (CidsBean)worldstate.getProperty("origintransition");
        transition.setProperty("id", -1);
        transition.getMetaObject().setStatus(MetaObject.NEW);

        return worldstate.persist();
    }

    /**
     * DOCUMENT ME!
     *
     * @param   worldstate  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static void deleteWorldstate(final CidsBean worldstate) throws Exception {
        final List<CidsBean> wsts = new ArrayList<CidsBean>();
        getToDelete(worldstate, wsts);
        for (final CidsBean c : wsts) {
            c.delete();
            c.persist();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  worldstate  DOCUMENT ME!
     * @param  toDelete    DOCUMENT ME!
     */
    private static void getToDelete(final CidsBean worldstate, final List<CidsBean> toDelete) {
        final Collection<CidsBean> children = worldstate.getBeanCollectionProperty("childworldstates");
        for (final CidsBean c : children) {
            getToDelete(c, toDelete);
        }
        toDelete.add(worldstate);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static Future reloadCatalogTree() {
        final MetaCatalogueTree tree = ComponentRegistry.getRegistry().getCatalogueTree();
        final TreePath path = tree.getSelectionPath();
        final DefaultTreeModel model = (DefaultTreeModel)tree.getModel();

        try {
            final RootTreeNode root = new RootTreeNode(SessionManager.getProxy().getRoots());
            model.setRoot(root);
            model.reload();

            return tree.exploreSubtree(path);
        } catch (final Exception ex) {
        }

        return null;
    }
}
