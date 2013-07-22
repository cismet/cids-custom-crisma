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

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

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
        final CidsBean transition = (CidsBean)worldstate.getProperty("origintransition");
        final CidsBean newT = ClassCacheMultiple.getMetaClass("CRISMA", "transitions").getEmptyInstance().getBean();
        newT.setProperty("name", transition.getProperty("name"));
        newT.setProperty("description", transition.getProperty("description"));
        newT.setProperty("performedsimulation", transition.getProperty("simulationcontrolparameter"));
        newT.setProperty("transitionstatuscontenttype", transition.getProperty("transitionstatuscontenttype"));
        newT.setProperty("transitionstatus", transition.getProperty("transitionstatus"));
        newT.getBeanCollectionProperty("performedmanipulations")
                .addAll(transition.getBeanCollectionProperty("performedmanipulations"));

        final CidsBean newW = ClassCacheMultiple.getMetaClass("CRISMA", "worldstates").getEmptyInstance().getBean();
        newW.setProperty("name", worldstate.getProperty("name"));
        newW.setProperty("description", worldstate.getProperty("description"));
        newW.getBeanCollectionProperty("categories").addAll(worldstate.getBeanCollectionProperty("categories"));
        newW.setProperty("creator", worldstate.getProperty("creator"));
        newW.setProperty("created", new Timestamp(System.currentTimeMillis()));
        newW.setProperty("origintransition", newT);
        newW.setProperty(
            "parentworldstate",
            SessionManager.getProxy().getMetaObject(
                worldstate.getMetaObject().getID(),
                worldstate.getMetaObject().getClassID(),
                worldstate.getMetaObject().getDomain()).getBean());
        newW.getBeanCollectionProperty("worldstatedata").addAll(worldstate.getBeanCollectionProperty("worldstatedata"));
        newW.setProperty("iccdata", calculateICCData(worldstate));

        return newW.persist();
    }

    /**
     * DOCUMENT ME!
     *
     * @param   worldstate  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    private static CidsBean calculateICCData(final CidsBean worldstate) throws Exception {
        final CidsBean icc = ClassCacheMultiple.getMetaClass("CRISMA", "dataitems").getEmptyInstance().getBean();
        icc.setProperty("name", "icc dummy");
        icc.setProperty("actualaccessinfocontenttype", "application/json");
        icc.setProperty(
            "actualaccessinfo",
            "{\"noOfDead\" : { 	\"displayName\":\"Number of dead\", 	\"value\":\""
                    + getRandom()
                    + "\"}, \"damagedBuildings\":{ 	\"displayName\":\"Damaged buildings\", 	\"value\":\""
                    + getRandom()
                    + "\"}, \"noOfInjured\":{ 	\"displayName\":\"Number of injured\", 	\"value\":\""
                    + getRandom()
                    + "\"}, \"cost\":{ 	\"displayName\":\"Total cost\", 	\"value\":\""
                    + getRandom()
                    + "\"}, \"Infrastucture\":{ 	\"displayName\":\"Infrastructure damage\", 	\"value\":\""
                    + getRandom()
                    + "\"} }");
        icc.setProperty("worldstate", null);
        icc.setProperty("datadescriptor", worldstate.getProperty("iccdata.datadescriptor"));

        return icc;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private static int getRandom() {
        return Double.valueOf(Math.floor(Math.random() * 500)).intValue();
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
