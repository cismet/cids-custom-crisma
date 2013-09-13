/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.crisma.objectrenderer;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.exception.ConnectionException;
import Sirius.navigator.ui.RequestsFullSizeComponent;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.SpiderWebPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.cismet.cids.custom.crisma.AbstractCidsBeanAggregationRenderer;
import de.cismet.cids.custom.crisma.BorderPanel;
import de.cismet.cids.custom.crisma.MapSync;
import de.cismet.cids.custom.crisma.MapSyncUtil;
import de.cismet.cids.custom.crisma.icc.ICCData;
import de.cismet.cids.custom.crisma.icc.Value;
import de.cismet.cids.custom.crisma.worldstate.viewer.DetailView;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cismap.commons.gui.MappingComponent;

import de.cismet.tools.gui.TitleComponentProvider;

/**
 * DOCUMENT ME!
 *
 * @author   mscholl
 * @version  $Revision$, $Date$
 */
public class WorldstatesAggregationRenderer extends AbstractCidsBeanAggregationRenderer
        implements RequestsFullSizeComponent,
            TitleComponentProvider {

    //~ Static fields/initializers ---------------------------------------------

    /** LOGGER. */
    private static final transient Logger LOG = LoggerFactory.getLogger(WorldstatesAggregationRenderer.class);

    //~ Instance fields --------------------------------------------------------

    private final transient ImageIcon i = ImageUtilities.loadImageIcon(WorldstatesAggregationRenderer.class.getPackage()
                    .getName().replaceAll("\\.", "/") + "/worlds_32.png",
            false);

    private int next = 0;

    private final ObjectMapper m = new ObjectMapper(new JsonFactory());

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JList jList3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WorldstatesAggregationRenderer.
     */
    public WorldstatesAggregationRenderer() {
        initComponents();

        jLabel1.setIcon(i);
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

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList3 = new javax.swing.JList();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel3.setLayout(new java.awt.BorderLayout());

        jList1.setModel(new javax.swing.AbstractListModel() {

                String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };

                @Override
                public int getSize() {
                    return strings.length;
                }
                @Override
                public Object getElementAt(final int i) {
                    return strings[i];
                }
            });
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jList1);

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jPanel3, gridBagConstraints);

        jPanel4.setLayout(new java.awt.BorderLayout());

        jList2.setModel(new javax.swing.AbstractListModel() {

                String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };

                @Override
                public int getSize() {
                    return strings.length;
                }
                @Override
                public Object getElementAt(final int i) {
                    return strings[i];
                }
            });
        jList2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(jList2);

        jPanel4.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jPanel4, gridBagConstraints);

        jPanel2.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jPanel2, gridBagConstraints);

        jPanel5.setLayout(new java.awt.GridBagLayout());

        jPanel6.setLayout(new java.awt.BorderLayout());

        jList3.setModel(new javax.swing.AbstractListModel() {

                String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };

                @Override
                public int getSize() {
                    return strings.length;
                }
                @Override
                public Object getElementAt(final int i) {
                    return strings[i];
                }
            });
        jList3.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList3.setToolTipText(NbBundle.getMessage(
                WorldstatesAggregationRenderer.class,
                "WorldstatesAggregationRenderer.jList3.toolTipText")); // NOI18N
        jScrollPane3.setViewportView(jList3);

        jPanel6.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jPanel6, gridBagConstraints);

        jPanel7.setLayout(new java.awt.BorderLayout(5, 5));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.8;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jPanel7, gridBagConstraints);

        jPanel8.setOpaque(false);
        jPanel8.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18));     // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText(NbBundle.getMessage(
                WorldstatesAggregationRenderer.class,
                "WorldstatesAggregationRenderer.jLabel1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel8.add(jLabel1, gridBagConstraints);

        setLayout(new java.awt.BorderLayout());

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    } // </editor-fold>//GEN-END:initComponents

    @Override
    protected void init() {
        jLabel1.setText("Comparing " + getCidsBeans().size() + " Worldstates");
        int tab = 0;
        try {
            tab = initComp(tab);
        } catch (final Exception e) {
            LOG.warn("cannot init", e);
        }
//        try {
//            initMultipleBarCharts(tab++);
//        } catch (final Exception e) {
//            LOG.warn("cannot init", e);
//        }
//        try {
//            initSingleBarChart(tab++);
//        } catch (final Exception e) {
//            LOG.warn("cannot init", e);
//        }
//        try {
//            initSpiderWebChart(tab++);
//        } catch (final Exception e) {
//            LOG.warn("cannot init", e);
//        }
//        try {
//            initMultipleSpiderWebChart(tab++);
//        } catch (final Exception e) {
//            LOG.warn("cannot init", e);
//        }
//        try {
//            initLineChart(tab++);
//        } catch (final Exception e) {
//            LOG.warn("cannot init", e);
//        }
        try {
            initAnalysisGraph(tab++);
        } catch (final Exception e) {
            LOG.warn("cannot init", e);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   tab  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private int initComp(final int tab) {
        int i = tab;
        final Collection<CidsBean> worldstates = getCidsBeans();
        final Map<String, List<DetailView>> views = new HashMap();
        try {
            final MetaClass mcr = ClassCacheMultiple.getMetaClass("CRISMA", "renderingdescriptors");
            final String sqlr = "SELECT " + mcr.getID() + ", r."
                        + mcr.getPrimaryKey()
                        + " FROM renderingdescriptors r, renderingdescriptorscategories rc, categories ca, classifications cl where r.categories = rc.renderingdescriptors_reference and rc.categories = ca.id and ca.classification = cl.id and cl.key like 'worldstate_detail_component'";
            final MetaObject[] mosr = SessionManager.getProxy()
                        .getMetaObjectByQuery(SessionManager.getSession().getUser(), sqlr);
            final ObjectMapper m = new ObjectMapper(new JsonFactory());
            final TypeReference<Map<String, String>> ref = new TypeReference<Map<String, String>>() {
                };

            for (final MetaObject mo : mosr) {
                final String jsonString = (String)mo.getBean().getProperty("uiintegrationinfo");
                final Map<String, String> json = m.readValue(jsonString, ref);
                final String viewName = json.get("canonicalName");
                final List<DetailView> l = new ArrayList();
                for (final CidsBean worldstate : worldstates) {
                    final DetailView view = (DetailView)Class.forName(viewName).newInstance();
                    view.setDescriptor(mo.getBean());
                    view.setWorldstate(worldstate);
                    l.add(view);
                }

                if (l.get(0) instanceof MapSync) {
                    final List<MappingComponent> mcs = new ArrayList<MappingComponent>(l.size());
                    for (final DetailView v : l) {
                        mcs.add(((MapSync)v).getMap());
                    }
                    MapSyncUtil.sync(mcs);
                }

                views.put(l.get(0).getDisplayName(), l);
            }

            for (final String s : views.keySet()) {
                final JPanel p = new JPanel();
                final List<DetailView> v = views.get(s);
                final GridLayout g = new GridLayout(2, v.size(), 15, 15);
                p.setLayout(g);
                for (final DetailView dv : v) {
                    final BorderPanel bp = new BorderPanel();
                    bp.setTitle(dv.getWorldstate().getProperty("name").toString());
                    bp.setContentPane((JPanel)dv.getView());
                    p.add(bp);
                }
                jTabbedPane1.insertTab(s, null, p, null, i++);

                if ("ICC Data".equals(s)) {
                    try {
                        initMultipleSpiderWebChart(i++);
                    } catch (final Exception e) {
                        LOG.warn("cannot init", e);
                    }
                }
            }
        } catch (final Exception e) {
            LOG.error("cannot init aggregation renderer", e);
        }

        return i;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  tab  DOCUMENT ME!
     */
    private void initMultipleBarCharts(final int tab) {
        final Collection<CidsBean> worldstates = getCidsBeans();
        final Map<String, DefaultCategoryDataset> sets = new HashMap<String, DefaultCategoryDataset>(4);

        for (final CidsBean wst : worldstates) {
            final String json = (String)wst.getProperty("iccdata.actualaccessinfo");
            final ObjectMapper m = new ObjectMapper(new JsonFactory());
            final TypeReference<Map<String, Map<String, String>>> ref =
                new TypeReference<Map<String, Map<String, String>>>() {
                };
            try {
                final Map<String, Map<String, String>> props = m.readValue(json, ref);

                for (final String s : props.keySet()) {
                    final Map<String, String> kv = props.get(s);
                    final String catName = kv.get("displayName");
                    final Integer value = Integer.parseInt(kv.get("value"));

                    DefaultCategoryDataset dataset = sets.get(catName);
                    if (dataset == null) {
                        dataset = new DefaultCategoryDataset();
                    }
                    dataset.addValue(value, (String)wst.getProperty("name"), catName);

                    sets.put(catName, dataset);
                }
            } catch (Exception ex) {
                LOG.error("cannot init icc data view", ex);
            }
        }

        final JPanel p = new JPanel(new GridLayout(1, sets.size(), 5, 5));
        final Map<Integer, Paint> paints = new HashMap<Integer, Paint>(worldstates.size() + 1);
        for (int i = 0; i < worldstates.size(); ++i) {
            paints.put(i, getPaint());
        }
        for (final String cat : sets.keySet()) {
            final JFreeChart chart = ChartFactory.createBarChart3D(
                    cat,
                    cat,
                    "Value",
                    sets.get(cat),
                    PlotOrientation.VERTICAL,
                    true,
                    false,
                    false);

            final BarRenderer3D r = (BarRenderer3D)chart.getCategoryPlot().getRenderer();
            for (final Integer i : paints.keySet()) {
                r.setSeriesPaint(i, paints.get(i));
            }
            r.setBaseItemLabelsVisible(true);
            r.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
            chart.getCategoryPlot().getRangeAxis().setAutoRange(false);
            chart.getCategoryPlot().getRangeAxis().setUpperBound(500);
            p.add(new ChartPanel(chart, true, false, false, false, true));
        }

        jTabbedPane1.insertTab(
            "ICC Data (Multiple Barcharts)",
            null,
            p,
            "ICC Data of "
                    + getCidsBeans().size()
                    + " worldstates",
            tab);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  tab  DOCUMENT ME!
     */
    private void initSingleBarChart(final int tab) {
        final Collection<CidsBean> worldstates = getCidsBeans();
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (final CidsBean wst : worldstates) {
            final String json = (String)wst.getProperty("iccdata.actualaccessinfo");
            final ObjectMapper m = new ObjectMapper(new JsonFactory());
            final TypeReference<Map<String, Map<String, String>>> ref =
                new TypeReference<Map<String, Map<String, String>>>() {
                };
            try {
                final Map<String, Map<String, String>> props = m.readValue(json, ref);

                for (final String s : props.keySet()) {
                    final Map<String, String> kv = props.get(s);
                    final String catName = kv.get("displayName");
                    final Integer value = Integer.parseInt(kv.get("value"));

                    dataset.addValue(value, (String)wst.getProperty("name"), catName);
                }
            } catch (Exception ex) {
                LOG.error("cannot init icc data view", ex);
            }
        }

        final JPanel p = new JPanel(new BorderLayout());
        final Map<Integer, Paint> paints = new HashMap<Integer, Paint>(worldstates.size() + 1);
        for (int i = 0; i < worldstates.size(); ++i) {
            paints.put(i, getPaint());
        }
        final JFreeChart chart = ChartFactory.createBarChart3D(
                "ICC Data (Single Barchart)",
                "ICC Data",
                "Value",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                false,
                false);

        final BarRenderer3D r = (BarRenderer3D)chart.getCategoryPlot().getRenderer();
        for (final Integer i : paints.keySet()) {
            r.setSeriesPaint(i, paints.get(i));
        }
        r.setBaseItemLabelsVisible(true);
        r.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        chart.getCategoryPlot().getRangeAxis().setAutoRange(false);
        chart.getCategoryPlot().getRangeAxis().setUpperBound(500);
        p.add(new ChartPanel(chart, true, false, false, false, true), BorderLayout.CENTER);

        jTabbedPane1.insertTab(
            "ICC Data (Single Barchart)",
            null,
            p,
            "ICC Data of "
                    + getCidsBeans().size()
                    + " worldstates",
            tab);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  tab  DOCUMENT ME!
     */
    private void initSpiderWebChart(final int tab) {
        final Collection<CidsBean> wsts = getCidsBeans();
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (final CidsBean wst : wsts) {
            final String json = (String)wst.getProperty("iccdata.actualaccessinfo");
            final ObjectMapper m = new ObjectMapper(new JsonFactory());
            final TypeReference<Map<String, Map<String, String>>> ref =
                new TypeReference<Map<String, Map<String, String>>>() {
                };
            try {
                final Map<String, Map<String, String>> props = m.readValue(json, ref);

                for (final String s : props.keySet()) {
                    final Map<String, String> kv = props.get(s);
                    final String catName = kv.get("displayName");
                    final Integer value = Integer.parseInt(kv.get("value"));

                    dataset.addValue(value, (String)wst.getProperty("name"), catName);
                }
            } catch (Exception ex) {
                LOG.error("cannot init icc data view", ex);
            }
        }

        final SpiderWebPlot plot = new SpiderWebPlot(dataset);
        final JFreeChart chart = new JFreeChart("ICC Data (SpiderWeb)", TextTitle.DEFAULT_FONT, plot, true);
        final ChartPanel chartPanel = new ChartPanel(chart, true, false, false, true, true);
        final JPanel p = new JPanel(new BorderLayout());
        p.add(chartPanel, BorderLayout.CENTER);
        jTabbedPane1.insertTab(
            "ICC Data (SpiderWeb)",
            null,
            p,
            "ICC Data of "
                    + getCidsBeans().size()
                    + " worldstates",
            tab);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   tab  DOCUMENT ME!
     *
     * @throws  IllegalStateException  DOCUMENT ME!
     */
    private void initMultipleSpiderWebChart(final int tab) {
        final GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.remove(jPanel6);
        final BorderPanel p1 = new BorderPanel();
        p1.setTitle("Reference Worldstate");
        p1.setContentPane(jPanel6);
        jPanel5.add(p1, gridBagConstraints);

        final MetaClass mc = ClassCacheMultiple.getMetaClass("CRISMA", "WORLDSTATES");

        final String sql = "SELECT " + mc.getID() + ", " + mc.getPrimaryKey() + " FROM WORLDSTATES";

        final MetaObject[] mos;
        try {
            mos = SessionManager.getProxy().getMetaObjectByQuery(sql, 0);
        } catch (final ConnectionException ex) {
            LOG.error("cannot update leafs", ex);
            return;
        }

        final DefaultListModel dlm = new DefaultListModel();
        for (final MetaObject mo : mos) {
            dlm.addElement(mo.getBean());
        }
        jList3.setModel(dlm);

        final int count = getCidsBeans().size();
        final int rows = Double.valueOf(Math.ceil(count / 3d)).intValue();
        final int columns = Double.valueOf(Math.ceil(count / 2d)).intValue();

        if (count == 2) {
            jPanel7.setLayout(new GridLayout(2, 1, 5, 5));
        } else if (count == 3) {
            jPanel7.setLayout(new GridLayout(3, 1, 5, 5));
        } else {
            jPanel7.setLayout(new GridLayout(rows, columns, 5, 5));
        }

        jList3.setCellRenderer(new DefaultListCellRenderer() {

                private final ImageIcon i = ImageUtilities.loadImageIcon(
                        WorldstatesAggregationRenderer.class.getPackage().getName().replaceAll("\\.", "/")
                                + "/world_16.png",
                        false);
                private final ImageIcon i2 = ImageUtilities.loadImageIcon(
                        WorldstatesAggregationRenderer.class.getPackage().getName().replaceAll("\\.", "/")
                                + "/world_leaf_16.png",
                        false);

                @Override
                public Component getListCellRendererComponent(final JList arg0,
                        final Object arg1,
                        final int arg2,
                        final boolean arg3,
                        final boolean arg4) {
                    final JLabel l = (JLabel)super.getListCellRendererComponent(arg0, arg1, arg2, arg3, arg4);
                    l.setText((String)((CidsBean)arg1).getProperty("name"));
                    l.setIcon(((CidsBean)arg1).getBeanCollectionProperty("childworldstates").isEmpty() ? i2 : i);

                    return l;
                }
            });
        jList3.addListSelectionListener(new ListSelectionListener() {

                // nv = v / 10^scale
                private final Map<String, Integer> scale;
                private final ObjectMapper m;

                {
                    try {
                        scale = new HashMap<String, Integer>();
                        m = new ObjectMapper(new JsonFactory());
                        for (final CidsBean wst : getCidsBeans()) {
                            final String json = (String)wst.getProperty("iccdata.actualaccessinfo");
                            final ICCData icc = m.readValue(json, ICCData.class);
                            final Field[] fields = icc.getClass().getDeclaredFields();
                            for (final Field field : fields) {
                                field.setAccessible(true);
                                final Object o = field.get(icc);
                                for (final Field x : o.getClass().getDeclaredFields()) {
                                    x.setAccessible(true);
                                    final Value v = (Value)x.get(o);
                                    final Integer cs = scale.get(v);
                                    final double rv = Double.parseDouble(v.getValue());
                                    final Integer ns = (rv == 0)
                                        ? 0 : (-1
                                                    * ((Double.valueOf(Math.floor(Math.log10(rv))).intValue()) - 1));
                                    if ((cs == null) || (ns < cs)) {
                                        scale.put(v.getDisplayName(), ns);
                                    }
                                }
                            }
                        }
                    } catch (final Exception e) {
                        LOG.error("cannot calculate scale value", e);

                        throw new IllegalStateException("cannot calculate scale value", e);
                    }
                }

                @Override
                public void valueChanged(final ListSelectionEvent e) {
                    if (e.getValueIsAdjusting()) {
                        return;
                    }

                    jPanel7.removeAll();
                    final CidsBean refB = (CidsBean)jList3.getSelectedValue();
                    final Collection<CidsBean> wsts = getCidsBeans();
                    for (final CidsBean wst : wsts) {
                        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                        add(wst, dataset);
                        add(refB, dataset);
                        final SpiderWebPlot plot = new SpiderWebPlot(dataset);
                        plot.setToolTipGenerator(new CategoryToolTipGenerator() {

                                @Override
                                public String generateToolTip(final CategoryDataset cd, final int i, final int i1) {
                                    return "<html>" + (Math.round(cd.getValue(i, i1).floatValue() * 10) / 10f)
                                                + " , Scale = 10 <sup>" + scale.get(cd.getColumnKey(i1))
                                                + "</sup>, original value = "
                                                + (cd.getValue(i, i1).doubleValue()
                                                    / Math.pow(10, scale.get(cd.getColumnKey(i1)))) + "</html>";
                                }
                            });

                        final JFreeChart chart = new JFreeChart(null,
                                TextTitle.DEFAULT_FONT,
                                plot,
                                true);
                        final ChartPanel chartPanel = new ChartPanel(chart, true, false, false, true, true);
                        final BorderPanel p = new BorderPanel();
                        p.setContentPane(chartPanel);
                        p.setTitle(
                            "ICC Data of "
                                    + wst.getProperty("name"));
                        jPanel7.add(p);
                    }
                    jPanel5.revalidate();
                }

                private void add(final CidsBean wst, final DefaultCategoryDataset dataset) {
                    final String json = (String)wst.getProperty("iccdata.actualaccessinfo");

                    try {
                        final ICCData icc = m.readValue(json, ICCData.class);

                        final Field[] fields = icc.getClass().getDeclaredFields();
                        for (final Field field : fields) {
                            field.setAccessible(true);
                            final Object o = field.get(icc);
                            for (final Field x : o.getClass().getDeclaredFields()) {
                                x.setAccessible(true);
                                final Value v = (Value)x.get(o);

                                final Double sv = Double.parseDouble(v.getValue())
                                            * Math.pow(10, scale.get(v.getDisplayName()));

                                dataset.addValue(
                                    sv,
                                    (String)wst.getProperty("name"),
                                    v.getDisplayName());
                            }
                        }
                    } catch (Exception ex) {
                        LOG.error("cannot init icc data view", ex);
                    }
                }
            });

        jList3.setSelectedIndex(dlm.size() - 1);
        jTabbedPane1.insertTab(
            "ICC Data (Multiple SpiderWeb)",
            null,
            jPanel5,
            "ICC Data of "
                    + getCidsBeans().size()
                    + " worldstates",
            tab);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  tab  DOCUMENT ME!
     */
    private void initLineChart(final int tab) {
        final Collection<CidsBean> wsts = getCidsBeans();
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (final CidsBean wst : wsts) {
            final String json = (String)wst.getProperty("iccdata.actualaccessinfo");
            final ObjectMapper m = new ObjectMapper(new JsonFactory());
            final TypeReference<Map<String, Map<String, String>>> ref =
                new TypeReference<Map<String, Map<String, String>>>() {
                };
            try {
                final Map<String, Map<String, String>> props = m.readValue(json, ref);

                for (final String s : props.keySet()) {
                    final Map<String, String> kv = props.get(s);
                    final String catName = kv.get("displayName");
                    final Integer value = Integer.parseInt(kv.get("value"));

                    dataset.addValue(value, (String)wst.getProperty("name"), catName);
                }
            } catch (Exception ex) {
                LOG.error("cannot init icc data view", ex);
            }
        }

        final JFreeChart chart = new JFreeChart(
                "ICC Data (Line)",
                JFreeChart.DEFAULT_TITLE_FONT,
                new CategoryPlot(
                    dataset,
                    new CategoryAxis("ICC Data"),
                    new NumberAxis("Value"),
                    new LineAndShapeRenderer(true, true)),
                true);
        ChartFactory.createLineChart(
            "ICC Data",
            "ICC Data",
            "Values",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false);

        final ChartPanel chartPanel = new ChartPanel(chart, true, false, false, true, true);
        final JPanel p = new JPanel(new BorderLayout());
        p.add(chartPanel, BorderLayout.CENTER);
        jTabbedPane1.insertTab(
            "ICC Data (Line)",
            null,
            p,
            "ICC Data of "
                    + getCidsBeans().size()
                    + " worldstates",
            tab);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  tab  DOCUMENT ME!
     */
    private void initAnalysisGraph(final int tab) {
        GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = java.awt.GridBagConstraints.BOTH;
        gbc.weighty = 0.5;
        gbc.insets = new java.awt.Insets(5, 5, 5, 5);
        final BorderPanel p1 = new BorderPanel();
        p1.setTitle("X-Criteria");
        jPanel1.remove(jPanel3);
        p1.setContentPane(jPanel3);
        jPanel1.add(p1, gbc);

        gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = java.awt.GridBagConstraints.BOTH;
        gbc.weightx = 0.2;
        gbc.weighty = 0.5;
        gbc.insets = new java.awt.Insets(5, 5, 5, 5);
        final BorderPanel p2 = new BorderPanel();
        p2.setTitle("Y-Criteria");
        jPanel1.remove(jPanel4);
        p2.setContentPane(jPanel4);
        jPanel1.add(p2, gbc);

        gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.fill = java.awt.GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new java.awt.Insets(5, 5, 5, 5);
        final BorderPanel p3 = new BorderPanel();
        p3.setTitle("Graph");
        jPanel1.remove(jPanel2);
        p3.setContentPane(jPanel2);
        jPanel1.add(p3, gbc);

        final Collection<CidsBean> wsts = getCidsBeans();
        final CidsBean wst = wsts.iterator().next();
        final String json = (String)wst.getProperty("iccdata.actualaccessinfo");
        try {
            final ICCData icc = m.readValue(json, ICCData.class);
            final Field[] fields = icc.getClass().getDeclaredFields();
            final List<Value> values = new ArrayList<Value>();
            for (final Field field : fields) {
                field.setAccessible(true);
                final Object o = field.get(icc);
                for (final Field x : o.getClass().getDeclaredFields()) {
                    x.setAccessible(true);
                    values.add((Value)x.get(o));
                }
            }

            final DefaultListModel xdlm = new DefaultListModel();
            final DefaultListModel ydlm = new DefaultListModel();
            final Map<Value, ImageIcon> icons = new HashMap<Value, ImageIcon>();
            for (final Value v : values) {
                xdlm.addElement(v);
                ydlm.addElement(v);
                icons.put(
                    v,
                    ImageUtilities.loadImageIcon(
                        WorldstatesAggregationRenderer.class.getPackage().getName().replaceAll("\\.", "/")
                                + "/"
                                + v.getIconResource(),
                        false));
            }

            jList1.setModel(xdlm);
            jList2.setModel(ydlm);
            jList1.setCellRenderer(new DefaultListCellRenderer() {

                    @Override
                    public Component getListCellRendererComponent(final JList arg0,
                            final Object arg1,
                            final int arg2,
                            final boolean arg3,
                            final boolean arg4) {
                        final JLabel l = (JLabel)super.getListCellRendererComponent(arg0, arg1, arg2, arg3, arg4);
                        l.setText(((Value)arg1).getDisplayName());
                        l.setIcon(icons.get(arg1));
                        return l;
                    }
                });
            jList2.setCellRenderer(new DefaultListCellRenderer() {

                    @Override
                    public Component getListCellRendererComponent(final JList arg0,
                            final Object arg1,
                            final int arg2,
                            final boolean arg3,
                            final boolean arg4) {
                        final JLabel l = (JLabel)super.getListCellRendererComponent(arg0, arg1, arg2, arg3, arg4);
                        l.setText(((Value)arg1).getDisplayName());
                        l.setIcon(icons.get(arg1));
                        return l;
                    }
                });
            /**
             * DOCUMENT ME!
             *
             * @version  $Revision$, $Date$
             */
            class ListSelL implements ListSelectionListener {

                /**
                 * DOCUMENT ME!
                 *
                 * @param  e  DOCUMENT ME!
                 */
                @Override
                public void valueChanged(final ListSelectionEvent e) {
                    if (e.getValueIsAdjusting()) {
                        return;
                    }

                    final Value x = (Value)jList1.getSelectedValue();
                    final Value y = (Value)jList2.getSelectedValue();

                    if ((x != null) && (y != null)) {
                        final XYSeriesCollection dataset = new XYSeriesCollection();
                        for (final CidsBean wst : getCidsBeans()) {
                            try {
                                final Double xValue = getValue(x.getDisplayName(), wst);
                                final Double yValue = getValue(y.getDisplayName(), wst);
                                final XYSeries series = new XYSeries((String)wst.getProperty("name"), false, false);
                                series.add(xValue, yValue);
                                dataset.addSeries(series);
                            } catch (final Exception ex) {
                                LOG.error("cannot create comp chart", ex);
                            }
                        }

                        final String xName = x.getDisplayName();
                        final String yName = y.getDisplayName();
                        final JFreeChart chart = ChartFactory.createScatterPlot("Relation " + xName + " to " + yName,
                                xName,
                                yName,
                                dataset,
                                PlotOrientation.VERTICAL,
                                true,
                                true,
                                false);

                        final Shape[] shapes = new Shape[DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE.length];
                        for (int i = 0; i < DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE.length; ++i) {
                            shapes[i] = AffineTransform.getScaleInstance(2, 2)
                                        .createTransformedShape(DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE[i]);
                        }

                        ((XYPlot)chart.getPlot()).setDrawingSupplier(
                            new DefaultDrawingSupplier(
                                DefaultDrawingSupplier.DEFAULT_PAINT_SEQUENCE,
                                DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE,
                                DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE,
                                DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE,
                                shapes));
                        ((XYPlot)chart.getPlot()).setRangeCrosshairVisible(true);
                        ((XYPlot)chart.getPlot()).setDomainCrosshairVisible(true);

                        final ChartPanel p = new ChartPanel(chart, true, true, true, true, true);
                        jPanel2.removeAll();
                        jPanel2.add(p, BorderLayout.CENTER);
                        WorldstatesAggregationRenderer.this.revalidate();
                    }
                }
            }

            final ListSelL l = new ListSelL();
            jList1.addListSelectionListener(l);
            jList2.addListSelectionListener(l);
            jTabbedPane1.insertTab(
                "Analysis",
                null,
                jPanel1,
                "Analyse two criteria ",
                tab);
        } catch (final Exception e) {
            LOG.error("cannot init analysis graph", e);
        }
    }
    /**
     * DOCUMENT ME!
     *
     * @param   displayName  DOCUMENT ME!
     * @param   worldstate   DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private double getValue(final String displayName, final CidsBean worldstate) {
        try {
            final String json = (String)worldstate.getProperty("iccdata.actualaccessinfo");
            final ICCData icc = m.readValue(json, ICCData.class);
            final Field[] fields = icc.getClass().getDeclaredFields();
            for (final Field field : fields) {
                field.setAccessible(true);
                final Object o = field.get(icc);
                for (final Field x : o.getClass().getDeclaredFields()) {
                    x.setAccessible(true);
                    final Value v = (Value)x.get(o);
                    if (displayName.equals(v.getDisplayName())) {
                        return Double.parseDouble(v.getValue());
                    }
                }
            }
        } catch (final Exception e) {
            LOG.warn("cannot getvalue: " + displayName + " | " + worldstate, e);
        }

        return 0d;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private Paint getPaint() {
        switch (next++ % 6) {
            case 0: {
                return Color.CYAN;
            }
            case 1: {
                return Color.MAGENTA;
            }
            case 2: {
                return Color.YELLOW;
            }
            case 3: {
                return Color.BLUE;
            }
            case 4: {
                return Color.GRAY;
            }
            case 5: {
                return Color.ORANGE;
            }
        }

        return Color.BLACK;
    }

    @Override
    public JComponent getTitleComponent() {
        return jPanel8;
    }
}
