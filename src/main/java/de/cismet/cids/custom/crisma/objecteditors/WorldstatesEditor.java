/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.crisma.objecteditors;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.types.treenode.ObjectTreeNode;
import Sirius.navigator.ui.ComponentRegistry;
import Sirius.navigator.ui.RequestsFullSizeComponent;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;
import Sirius.server.middleware.types.MetaObjectNode;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.log4j.Logger;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.swing.Box.Filler;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.LayerUI;

import de.cismet.cids.custom.crisma.AbstractCidsBeanRenderer;
import de.cismet.cids.custom.crisma.ScenarioView;
import de.cismet.cids.custom.crisma.Tools;
import de.cismet.cids.custom.crisma.worldstate.editor.DetailEditor;
import de.cismet.cids.custom.crisma.worldstate.viewer.DetailView;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.tools.gui.StaticSwingTools;

/**
 * DOCUMENT ME!
 *
 * @author   mscholl
 * @version  $Revision$, $Date$
 */
public class WorldstatesEditor extends AbstractCidsBeanRenderer implements RequestsFullSizeComponent {

    //~ Static fields/initializers ---------------------------------------------

    /** LOGGER. */
    private static final transient Logger LOG = Logger.getLogger(WorldstatesEditor.class);

    //~ Instance fields --------------------------------------------------------

    private transient boolean editing;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel pnlDetails;
    private javax.swing.JPanel pnlSwapper;
    private javax.swing.JPanel pnlTreepath;
    private javax.swing.JPanel pnlWorldstate;
    private javax.swing.JScrollPane scrSwapper;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WorldstateRenderer.
     */
    public WorldstatesEditor() {
        this(true);
    }

    /**
     * Creates a new WorldstatesEditor object.
     *
     * @param  editing  DOCUMENT ME!
     */
    public WorldstatesEditor(final boolean editing) {
        this.editing = editing;

        initComponents();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        pnlTreepath = new javax.swing.JPanel();
        pnlWorldstate = new javax.swing.JPanel();
        pnlDetails = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        scrSwapper = new javax.swing.JScrollPane();
        pnlSwapper = new javax.swing.JPanel();

        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());

        pnlTreepath.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlTreepath.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        add(pnlTreepath, gridBagConstraints);

        pnlWorldstate.setLayout(new java.awt.GridBagLayout());

        pnlDetails.setOpaque(false);
        pnlDetails.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.7;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        pnlWorldstate.add(pnlDetails, gridBagConstraints);

        jPanel1.setLayout(new java.awt.BorderLayout());

        pnlSwapper.setOpaque(false);
        pnlSwapper.setLayout(new java.awt.GridLayout(1, 0));
        scrSwapper.setViewportView(pnlSwapper);

        jPanel1.add(scrSwapper, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 0.3;
        pnlWorldstate.add(jPanel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        add(pnlWorldstate, gridBagConstraints);
    } // </editor-fold>//GEN-END:initComponents

    @Override
    protected void init() {
        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    initTreepath();
                    initViewer();
                }
            });

        setTitle((String)cidsBean.getProperty("name"));
    }

    /**
     * DOCUMENT ME!
     */
    private void initTreepath() {
        assert EventQueue.isDispatchThread() : "EDT only";

//        final LinkedList<CidsBean> beans = new LinkedList<>();
        final LinkedList<CidsBean> beans = new LinkedList<CidsBean>();
        CidsBean current = cidsBean;

        while (current != null) {
            beans.addFirst(current);
            current = (CidsBean)current.getProperty("parentworldstate");
        }

        for (int i = 0; i < beans.size(); ++i) {
            final JButton b = new JButton((String)beans.get(i).getProperty("name"));
            final MetaObject mo = beans.get(i).getMetaObject();
            b.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        if (!mo.equals(WorldstatesEditor.this.cidsBean.getMetaObject())) {
                            ComponentRegistry.getRegistry().getDescriptionPane().gotoMetaObject(mo, null);
                        }
                    }
                });

            final GridBagConstraints gc1 = new GridBagConstraints(i * 2,
                    0,
                    1,
                    1,
                    0,
                    0,
                    GridBagConstraints.WEST,
                    GridBagConstraints.HORIZONTAL,
                    new Insets(5, 5, 5, 5),
                    0,
                    0);
            final GridBagConstraints gc2 = new GridBagConstraints((i * 2) + 1,
                    0,
                    1,
                    1,
                    0,
                    0,
                    GridBagConstraints.WEST,
                    GridBagConstraints.HORIZONTAL,
                    new Insets(5, 5, 5, 5),
                    0,
                    0);

            pnlTreepath.add(b, gc1);
            pnlTreepath.add(new JLabel("-->"), gc2);
        }

        pnlTreepath.remove(pnlTreepath.getComponentCount() - 1);
        pnlTreepath.add(new Filler(new Dimension(), new Dimension(), new Dimension()),
            new GridBagConstraints(
                pnlTreepath.getComponentCount()
                        + 2,
                0,
                1,
                1,
                1,
                0,
                GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0),
                0,
                0));
    }

    /**
     * DOCUMENT ME!
     *
     * @throws  IllegalStateException  DOCUMENT ME!
     */
    private void initViewer() {
        assert EventQueue.isDispatchThread() : "EDT only";
        assert !editing : "init while not editing only";

//        final Map<DetailView, DetailEditor> views = new HashMap<>();
        final Map<DetailView, DetailEditor> views = new TreeMap<DetailView, DetailEditor>(new Comparator<DetailView>() {

                    @Override
                    public int compare(final DetailView o1, final DetailView o2) {
                        return o1.getDisplayName().compareTo(o2.getDisplayName());
                    }
                });
        try {
            final MetaClass mcr = ClassCacheMultiple.getMetaClass("CRISMA", "renderingdescriptors");
            final MetaClass mce = ClassCacheMultiple.getMetaClass("CRISMA", "MANIPULATIONDESCRIPTORS");
            final String sqlr = "SELECT " + mcr.getID() + ", r."
                        + mcr.getPrimaryKey()
                        + " FROM renderingdescriptors r, renderingdescriptorscategories rc, categories ca, classifications cl where r.categories = rc.renderingdescriptors_reference and rc.categories = ca.id and ca.classification = cl.id and cl.key like 'worldstate_detail_component'";
            final String sqle = "SELECT " + mce.getID() + ", m." + mce.getPrimaryKey()
                        + " FROM MANIPULATIONDESCRIPTORS m, manipulationdescriptorscategories mc, categories ca, classifications cl where m.categories = mc.manipulationdescriptors_reference and mc.category = ca.id and ca.classification = cl.id and cl.key like 'worldstate_detail_component'";
            final MetaObject[] mosr = SessionManager.getProxy()
                        .getMetaObjectByQuery(SessionManager.getSession().getUser(), sqlr);
            final MetaObject[] mose = SessionManager.getProxy()
                        .getMetaObjectByQuery(SessionManager.getSession().getUser(), sqle);
            final ObjectMapper m = new ObjectMapper(new JsonFactory());
            final TypeReference<Map<String, String>> ref = new TypeReference<Map<String, String>>() {
                };

            for (final MetaObject mo : mosr) {
                final String jsonString = (String)mo.getBean().getProperty("uiintegrationinfo");
                final Map<String, String> json = m.readValue(jsonString, ref);
                final String viewName = json.get("canonicalName");

                final DetailView view = (DetailView)Class.forName(viewName).newInstance();
                view.setDescriptor(mo.getBean());

                DetailEditor editor = null;
                for (final MetaObject moe : mose) {
                    final Object o = moe.getBean().getProperty("categories");
                    if (mo.getBean().getProperty("categories").equals(o)) {
                        final String jsonStringM = (String)moe.getBean().getProperty("uiintegrationinfo");
                        final Map<String, String> jsonM = m.readValue(jsonStringM, ref);
                        final String editorName = jsonM.get("canonicalName");

                        editor = (DetailEditor)Class.forName(editorName).newInstance();
                        editor.setDescriptor(moe.getBean());

                        break;
                    }
                }
                views.put(view, editor);
            }
        } catch (final Exception e) {
//        } catch (ConnectionException | ClassNotFoundException | IOException | InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("illegal renderingdescriptors configuration", e);
        }

        pnlSwapper.setLayout(new GridLayout(1, views.size() - 1, 10, 0));

        int i = 0;
        for (final DetailView view : views.keySet()) {
            final JPanel contentPane = new JPanel(new BorderLayout());
            view.setWorldstate(cidsBean);
            contentPane.putClientProperty("detailView", view);
            contentPane.putClientProperty("detailEditor", views.get(view));
            contentPane.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED), view.getDisplayName()));

            if (++i == views.size()) {
                contentPane.add(view.getView(), BorderLayout.CENTER);
                pnlDetails.add(contentPane);
            } else {
                final LayerUI<JComponent> layerUI = new LayerUI<JComponent>() {

                        @Override
                        public void eventDispatched(final AWTEvent awte, final JLayer jlayer) {
                            assert EventQueue.isDispatchThread() : "EDT only";

                            if (MouseEvent.MOUSE_CLICKED == awte.getID()) {
                                final JComponent oldContentPane = (JComponent)pnlDetails.getComponent(0);
                                final JComponent newContentPane = (JComponent)jlayer.getView();

                                final DetailView oldDetailV = (DetailView)oldContentPane.getClientProperty(
                                        "detailView");
                                final DetailEditor oldDetailE = (DetailEditor)oldContentPane.getClientProperty(
                                        "detailEditor");
                                final DetailView newDetailV = (DetailView)newContentPane.getClientProperty(
                                        "detailView");
                                final DetailEditor newDetailE = (DetailEditor)newContentPane.getClientProperty(
                                        "detailEditor");

                                oldContentPane.removeAll();
                                oldContentPane.add(editing ? oldDetailE.getMiniatureEditor()
                                                           : oldDetailV.getMiniatureView(),
                                    BorderLayout.CENTER);

                                newContentPane.removeAll();
                                newContentPane.add(editing ? newDetailE.getEditor() : newDetailV.getView(),
                                    BorderLayout.CENTER);

                                jlayer.setView(oldContentPane);

                                newContentPane.setVisible(true);
                                pnlDetails.removeAll();
                                pnlDetails.add(newContentPane, BorderLayout.CENTER);
                                pnlDetails.invalidate();
                                pnlDetails.revalidate();
                                pnlDetails.repaint();
                            }
                        }
                    };

                final JLayer<JComponent> layer = new JLayer<JComponent>(contentPane, layerUI);
                layer.setLayerEventMask(Long.MAX_VALUE);
                contentPane.add(view.getMiniatureView(), BorderLayout.CENTER);
                pnlSwapper.add(layer);
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean isEditing() {
        return editing;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  editing  DOCUMENT ME!
     */
    public void setEditing(final boolean editing) {
        if ((this.editing && editing) || !(this.editing || editing)) {
            return;
        }

        try {
            if (!editing) {
                final int answer = JOptionPane.showConfirmDialog(
                        this,
                        "Do you want to save the changes",
                        "Save changes",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (JOptionPane.CANCEL_OPTION == answer) {
                    return;
                } else if (JOptionPane.YES_OPTION == answer) {
                    cidsBean = Tools.saveWorldstate(
                            ((DetailEditor)((JPanel)pnlDetails.getComponent(0)).getClientProperty("detailEditor"))
                                        .getWorldstate());

                    StaticSwingTools.getParentFrame(this).invalidate();
                    StaticSwingTools.getParentFrame(this).validate();
                    StaticSwingTools.getParentFrame(this).repaint();

                    this.editing = editing;

                    Tools.reloadCatalogTree();
                    ScenarioView.getInstance().updateLeafs();
                    final List mos = new ArrayList(1);
                    mos.add(new ObjectTreeNode(new MetaObjectNode(cidsBean)));

                    ComponentRegistry.getRegistry().getDescriptionPane().setNodesDescriptions(mos);

                    return;
                }
            }
            final CidsBean copy = SessionManager.getProxy()
                        .getMetaObject(cidsBean.getMetaObject().getID() + "@" + cidsBean.getMetaObject().getClassKey())
                        .getBean();

            for (final Component c : pnlSwapper.getComponents()) {
                final JPanel miniature = (JPanel)((JLayer)c).getView();
                miniature.removeAll();
                final DetailView v = (DetailView)miniature.getClientProperty("detailView");
                final DetailEditor e = (DetailEditor)miniature.getClientProperty("detailEditor");
                if (editing) {
                    e.setWorldstate(copy);
                    miniature.add(e.getMiniatureEditor(), BorderLayout.CENTER);
                } else {
                    v.setWorldstate(cidsBean);
                    miniature.add(v.getMiniatureView(), BorderLayout.CENTER);
                }
            }

            final JPanel miniature = (JPanel)pnlDetails.getComponent(0);
            miniature.removeAll();
            final DetailEditor e = (DetailEditor)miniature.getClientProperty("detailEditor");
            final DetailView v = (DetailView)miniature.getClientProperty("detailView");
            if (editing) {
                e.setWorldstate(copy);
                miniature.add(e.getEditor(), BorderLayout.CENTER);
            } else {
                v.setWorldstate(cidsBean);
                miniature.add(v.getView(), BorderLayout.CENTER);
            }

            for (final Component c : pnlTreepath.getComponents()) {
                if (c instanceof JButton) {
                    ((JButton)c).setEnabled(!editing);
                }
            }

            StaticSwingTools.getParentFrame(this).invalidate();
            StaticSwingTools.getParentFrame(this).validate();
            StaticSwingTools.getParentFrame(this).repaint();

            this.editing = editing;
        } catch (final Exception exception) {
            LOG.error("cannot change edit status", exception);
        }
    }
}
