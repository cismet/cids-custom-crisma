/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.crisma.treeicons;

import Sirius.navigator.types.treenode.ClassTreeNode;
import Sirius.navigator.types.treenode.ObjectTreeNode;
import Sirius.navigator.types.treenode.PureTreeNode;
import Sirius.navigator.ui.tree.CidsTreeObjectIconFactory;

import org.openide.util.ImageUtilities;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  $Revision$, $Date$
 */
public final class WorldstatesIconFactory implements CidsTreeObjectIconFactory {

    //~ Instance fields --------------------------------------------------------

    private final transient ImageIcon normalIcon16;
    private final transient ImageIcon leafIcon16;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new WorldstatesIconFactory object.
     */
    public WorldstatesIconFactory() {
        normalIcon16 = ImageUtilities.loadImageIcon(WorldstatesIconFactory.class.getPackage().getName().replaceAll(
                    "\\.",
                    "/")
                        + "/world_16.png",
                false);

        leafIcon16 = ImageUtilities.loadImageIcon(WorldstatesIconFactory.class.getPackage().getName().replaceAll(
                    "\\.",
                    "/")
                        + "/world_leaf_16.png",
                false);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public Icon getClosedPureNodeIcon(final PureTreeNode ptn) {
        return normalIcon16;
    }

    @Override
    public Icon getOpenPureNodeIcon(final PureTreeNode ptn) {
        return normalIcon16;
    }

    @Override
    public Icon getLeafPureNodeIcon(final PureTreeNode ptn) {
        return leafIcon16;
    }

    @Override
    public Icon getOpenObjectNodeIcon(final ObjectTreeNode otn) {
        return otn.getMetaObject().getBean().getBeanCollectionProperty("childworldstates").isEmpty() ? leafIcon16
                                                                                                     : normalIcon16;
    }

    @Override
    public Icon getClosedObjectNodeIcon(final ObjectTreeNode otn) {
        return otn.getMetaObject().getBean().getBeanCollectionProperty("childworldstates").isEmpty() ? leafIcon16
                                                                                                     : normalIcon16;
    }

    @Override
    public Icon getLeafObjectNodeIcon(final ObjectTreeNode otn) {
        return normalIcon16;
    }

    @Override
    public Icon getClassNodeIcon(final ClassTreeNode dmtn) {
        return normalIcon16;
    }
}
