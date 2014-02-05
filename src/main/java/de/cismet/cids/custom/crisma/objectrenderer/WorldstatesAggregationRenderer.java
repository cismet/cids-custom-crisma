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
import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.SpiderWebPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import java.lang.reflect.Field;

import java.text.NumberFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.Box.Filler;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import de.cismet.cids.custom.crisma.AbstractCidsBeanAggregationRenderer;
import de.cismet.cids.custom.crisma.BorderPanel;
import de.cismet.cids.custom.crisma.ColorIcon;
import de.cismet.cids.custom.crisma.MapSync;
import de.cismet.cids.custom.crisma.MapSyncUtil;
import de.cismet.cids.custom.crisma.icc.Common;
import de.cismet.cids.custom.crisma.icc.ICCData;
import de.cismet.cids.custom.crisma.icc.Value;
import de.cismet.cids.custom.crisma.icc.ValueIterable;
import de.cismet.cids.custom.crisma.tostringconverter.WorldstatesToStringConverter;
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

    private final transient WorldstatesToStringConverter conv = new WorldstatesToStringConverter();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JList jList3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable tblCrit;
    private javax.swing.JTable tblInd;
    private javax.swing.JTabbedPane tbpCrit;
    private javax.swing.JTabbedPane tbpInd;
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
    private void initComponents()
    {
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
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblInd = new javax.swing.JTable();
        tbpInd = new javax.swing.JTabbedPane();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblCrit = new javax.swing.JTable();
        tbpCrit = new javax.swing.JTabbedPane();

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel3.setLayout(new java.awt.BorderLayout());

        jList1.setModel(new javax.swing.AbstractListModel()
        {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
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

        jList2.setModel(new javax.swing.AbstractListModel()
        {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
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

        jList3.setModel(new javax.swing.AbstractListModel()
        {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList3.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList3.setToolTipText(NbBundle.getMessage(WorldstatesAggregationRenderer.class, "WorldstatesAggregationRenderer.jList3.toolTipText")); // NOI18N
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

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText(NbBundle.getMessage(WorldstatesAggregationRenderer.class, "WorldstatesAggregationRenderer.jLabel1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel8.add(jLabel1, gridBagConstraints);

        setLayout(new java.awt.GridBagLayout());

        jTabbedPane2.setName("f"); // NOI18N

        jPanel9.setOpaque(false);
        jPanel9.setLayout(new java.awt.GridBagLayout());

        tblInd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String []
            {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblInd.setCellSelectionEnabled(true);
        tblInd.setShowHorizontalLines(false);
        jScrollPane4.setViewportView(tblInd);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.42;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel9.add(jScrollPane4, gridBagConstraints);

        tbpInd.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.58;
        jPanel9.add(tbpInd, gridBagConstraints);

        jTabbedPane2.addTab(NbBundle.getMessage(WorldstatesAggregationRenderer.class, "WorldstatesAggregationRenderer.jPanel9.TabConstraints.tabTitle", new Object[] {}), jPanel9); // NOI18N

        jPanel10.setOpaque(false);
        jPanel10.setLayout(new java.awt.GridBagLayout());

        tblCrit.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String []
            {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(tblCrit);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.4;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel10.add(jScrollPane5, gridBagConstraints);

        tbpCrit.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.6;
        jPanel10.add(tbpCrit, gridBagConstraints);

        jTabbedPane2.addTab(NbBundle.getMessage(WorldstatesAggregationRenderer.class, "WorldstatesAggregationRenderer.jPanel10.TabConstraints.tabTitle", new Object[] {}), jPanel10); // NOI18N

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jTabbedPane2, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void init() {
        jLabel1.setText("Comparing " + getCidsBeans().size() + " Worldstates");
        try {
            initTable(false);
        } catch (Exception ex) {
            LOG.warn("cannot init", ex);
        }
        try {
            initBar(false);
        } catch (final Exception e) {
            LOG.warn("cannot init", e);
        }
        // int tab = 0;
        // try {
        // tab = initComp(tab);
        // } catch (final Exception e) {
        // LOG.warn("cannot init", e);
        // }
        // try {
        // initMultipleBarCharts(tab++);
        // } catch (final Exception e) {
        // LOG.warn("cannot init", e);
        // }
        // try {
        // initSingleBarChart(tab++);
        // } catch (final Exception e) {
        // LOG.warn("cannot init", e);
        // }
        // try {
        // initSpiderWebChart(tab++);
        // } catch (final Exception e) {
        // LOG.warn("cannot init", e);
        // }
        // try {
        // initMultipleSpiderWebChart(tab++);
        // } catch (final Exception e) {
        // LOG.warn("cannot init", e);
        // }
        // try {
        // initLineChart(tab++);
        // } catch (final Exception e) {
        // LOG.warn("cannot init", e);
        // }
        // try {
        // initAnalysisGraph(tab++);
        // }
        // LOG.warn("cannot init", e);
        // }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   criteria  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    private void initTable(final boolean criteria) throws Exception {
        boolean first = true;
        final DefaultTableModel tm = new DefaultTableModel();
        for (final CidsBean wst : getCidsBeans()) {
            final List<CidsBean> icclist = wst.getBeanCollectionProperty("iccdata");
            CidsBean iccbean = null;
            for (final CidsBean icc : icclist) {
                if (criteria && "Criteria".equalsIgnoreCase((String)icc.getProperty("name"))) {
                    iccbean = icc;
                    break;
                }
                if (!criteria && "Indicators".equalsIgnoreCase((String)icc.getProperty("name"))) {
                    iccbean = icc;
                    break;
                }
            }

            final ICCData data = m.readValue((String)iccbean.getProperty("actualaccessinfo"), ICCData.class);

            if (first) {
                tm.addColumn(criteria ? "Criteria" : "Indicators",
                    new Object[] {
                        data.getCasualties(),
                        data.getCasualties().getNoOfDead(),
                        data.getCasualties().getNoOfInjured(),
                        data.getCasualties().getNoOfHomeless(),
                        data.getDamagedBuildings(),
                        data.getDamagedBuildings().getLostBuildings(),
                        data.getDamagedBuildings().getUnsafeBuildings(),
                        data.getDamagedInfrastructure(),
                        data.getDamagedInfrastructure().getDamagedRoadSegments(),
                        data.getCost(),
                        data.getCost().getDirectDamageCost(),
                        data.getCost().getIndirectDamageCost(),
                        data.getCost().getRestorationCost(),
                        data.getEvacuationCost(),
                        data.getEvacuationCost().getTotalEvacuationCost()
                    });
                first = false;
            }

            tm.addColumn(wst.getProperty("name"),
                new Object[] {
                    "",
                    data.getCasualties().getNoOfDead(),
                    data.getCasualties().getNoOfInjured(),
                    data.getCasualties().getNoOfHomeless(),
                    "",
                    data.getDamagedBuildings().getLostBuildings(),
                    data.getDamagedBuildings().getUnsafeBuildings(),
                    "",
                    data.getDamagedInfrastructure().getDamagedRoadSegments(),
                    "",
                    data.getCost().getDirectDamageCost(),
                    data.getCost().getIndirectDamageCost(),
                    data.getCost().getRestorationCost(),
                    "",
                    data.getEvacuationCost().getTotalEvacuationCost()
                });
        }

        final TableCellRenderer rend = new DefaultTableCellRenderer() {

                private final NumberFormat nf = NumberFormat.getInstance();

                @Override
                public Component getTableCellRendererComponent(final JTable table,
                        final Object value,
                        final boolean isSelected,
                        final boolean hasFocus,
                        final int row,
                        final int column) {
                    final JLabel l = new JLabel();

                    if ((column % 2) == 0) {
                        l.setBackground(Color.red);
                    }

                    if ((column == 0) && (value instanceof Common)) {
                        if (!(value instanceof Value)) {
                            l.setFont(l.getFont().deriveFont(Font.BOLD));
                        }
                        final Common common = (Common)value;
                        l.setText(common.getDisplayName());
                        l.setIcon(ImageUtilities.loadImageIcon(
                                WorldstatesAggregationRenderer.class.getPackage().getName().replaceAll("\\.", "/")
                                        + "/"
                                        + common.getIconResource(),
                                false));
                    } else if (value instanceof Value) {
                        final Value v = (Value)value;
                        l.setText(nf.format(Long.parseLong(v.getValue())) + " " + v.getUnit());
                        l.setHorizontalTextPosition(SwingConstants.RIGHT);
                        l.setHorizontalAlignment(SwingConstants.RIGHT);
                    }

                    return l;
                }
            };

        final JTable t = criteria ? tblCrit : tblInd;
        t.setModel(tm);
        final Enumeration<TableColumn> e = t.getColumnModel().getColumns();
        while (e.hasMoreElements()) {
            e.nextElement().setCellRenderer(rend);
        }

        final TableColumnModel columnModel = t.getColumnModel();
        int width = 0;
        for (int column = 0; column < t.getColumnCount(); column++) {
            for (int row = 0; row < t.getRowCount(); row++) {
                final TableCellRenderer renderer = t.getCellRenderer(row, column);
                final Component comp = t.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width, width);
            }
        }
        for (int column = 1; column < t.getColumnCount(); column++) {
            columnModel.getColumn(column).setWidth(width);
            columnModel.getColumn(column).setMinWidth(width);
            columnModel.getColumn(column).setMaxWidth(width);
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   criteria  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    private void initBar(final boolean criteria) throws Exception {
        final Map<String, DefaultCategoryDataset> sets = new TreeMap<String, DefaultCategoryDataset>(
                new Comparator<String>() {

                    private final ArrayList<String> vals = new ArrayList<String>(
                            Arrays.asList(
                                new String[] {
                                    "Number of dead",
                                    "Number of injured",
                                    "Number of homeless",
                                    "Lost buildings",
                                    "Unsafe buildings",
                                    "Number of damaged road segments",
                                    "Direct damage cost",
                                    "Indirect damage cost",
                                    "Direct restoration cost",
                                    "Total evacuation cost"
                                }));

                    @Override
                    public int compare(final String o1, final String o2) {
                        return Integer.valueOf(vals.indexOf(o1)).compareTo(Integer.valueOf(vals.indexOf(o2)));
                    }
                });
        for (final CidsBean wst : getCidsBeans()) {
            final List<CidsBean> icclist = wst.getBeanCollectionProperty("iccdata");
            CidsBean iccbean = null;
            for (final CidsBean icc : icclist) {
                if (criteria && "Criteria".equalsIgnoreCase((String)icc.getProperty("name"))) {
                    iccbean = icc;
                    break;
                }
                if (!criteria && "Indicators".equalsIgnoreCase((String)icc.getProperty("name"))) {
                    iccbean = icc;
                    break;
                }
            }

            final ICCData data = m.readValue((String)iccbean.getProperty("actualaccessinfo"), ICCData.class);

            for (final ValueIterable it : data) {
                for (final Value v : it) {
                    DefaultCategoryDataset dataset = sets.get(v.getDisplayName());
                    if (dataset == null) {
                        dataset = new DefaultCategoryDataset();
                        sets.put(v.getDisplayName(), dataset);
                    }
                    dataset.addValue(Integer.parseInt(v.getValue()),
                        (String)wst.getProperty("name"),
                        v.getDisplayName());
                }
            }
        }

        final JPanel all = new JPanel(new GridLayout(2, (sets.size() + 1) / 2));
        final Map<Integer, Color> paints = new HashMap<Integer, Color>(getCidsBeans().size() + 1);
        for (int i = 0; i < getCidsBeans().size(); ++i) {
            paints.put(i, getColor());
        }

        final JPanel legend = new JPanel(new GridBagLayout());
        final GridBagConstraints constraints = new GridBagConstraints(
                0,
                0,
                1,
                1,
                0.5,
                0,
                GridBagConstraints.CENTER,
                GridBagConstraints.NONE,
                new Insets(10, 10, 5, 10),
                0,
                0);
        legend.add(new Filler(new Dimension(), new Dimension(), new Dimension()), constraints);
        constraints.weightx = 0;
        final Iterator<CidsBean> it = getCidsBeans().iterator();
        while (it.hasNext()) {
            final ColorIcon ci = new ColorIcon(paints.get(constraints.gridx++));
            final JLabel l = new JLabel((String)it.next().getProperty("name"), ci, SwingConstants.CENTER);
            legend.add(l, constraints);
        }
        constraints.weightx = 0.5;
        constraints.gridx++;
        legend.add(new Filler(new Dimension(), new Dimension(), new Dimension()), constraints);

        for (final Entry<String, DefaultCategoryDataset> entry : sets.entrySet()) {
            final JPanel p = new JPanel(new BorderLayout());
            final JFreeChart chart = ChartFactory.createBarChart(
                    entry.getKey(),
                    null,
                    null,
                    entry.getValue(),
                    PlotOrientation.VERTICAL,
                    false,
                    false,
                    false);
            chart.getCategoryPlot().getRangeAxis().setAutoRange(false);
            chart.getCategoryPlot()
                    .getRangeAxis()
                    .setUpperBound(chart.getCategoryPlot().getRangeAxis().getUpperBound() * 1.15);
            chart.setBackgroundPaint(new Color(Integer.decode("0xE2E2E2")));
            chart.getCategoryPlot().setBackgroundAlpha(0.5f);

            final BarRenderer r = (BarRenderer)chart.getCategoryPlot().getRenderer();
            r.setBarPainter(new StandardBarPainter());
            for (final Integer i : paints.keySet()) {
                r.setSeriesPaint(i, paints.get(i));
            }
            r.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
            r.setBaseItemLabelsVisible(true);
            p.add(new ChartPanel(chart, true, false, false, false, true), BorderLayout.CENTER);
            all.add(p);
        }

        final JTabbedPane pane = criteria ? tbpCrit : tbpInd;

        final JPanel container = new JPanel(new BorderLayout());
        container.add(all, BorderLayout.CENTER);
        container.add(legend, BorderLayout.SOUTH);

        pane.insertTab(
            "Bars",
            null,
            container,
            (criteria ? "Criteria" : "Indicators")
                    + " of "
                    + getCidsBeans().size()
                    + " worldstates",
            0);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   criteria  DOCUMENT ME!
     *
     * @throws  Exception              DOCUMENT ME!
     * @throws  IllegalStateException  DOCUMENT ME!
     */
    private void initSpider(final boolean criteria) throws Exception {
        for (final CidsBean wst : getCidsBeans()) {
            final List<CidsBean> icclist = wst.getBeanCollectionProperty("iccdata");
            CidsBean iccbean = null;
            for (final CidsBean icc : icclist) {
                if (criteria && "Criteria".equalsIgnoreCase((String)icc.getProperty("name"))) {
                    iccbean = icc;
                    break;
                }
                if (!criteria && "Indicators".equalsIgnoreCase((String)icc.getProperty("name"))) {
                    iccbean = icc;
                    break;
                }
            }

            final ICCData data = m.readValue((String)iccbean.getProperty("actualaccessinfo"), ICCData.class);
        }

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
        tbpInd.insertTab(
            "ICC Data (Multiple SpiderWeb)",
            null,
            jPanel5,
            "ICC Data of "
                    + getCidsBeans().size()
                    + " worldstates",
            0);
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
                    bp.setTitle(conv.convert(dv.getWorldstate().getMetaObject()));
                    bp.setContentPane((JPanel)dv.getView());
                    p.add(bp);
                }
                tbpInd.insertTab(s, null, p, null, i++);

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
            paints.put(i, getColor());
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

        tbpInd.insertTab(
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
            paints.put(i, getColor());
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

        tbpInd.insertTab(
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
        tbpInd.insertTab(
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
        tbpInd.insertTab(
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
        tbpInd.insertTab(
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
            tbpInd.insertTab(
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
    private Color getColor() {
        switch (next++ % 5) {
            case 0: {
                // pastel red
                return new Color(Integer.decode("0xFFA0B3"));
            }
            case 1: {
                // pastel blue
                return new Color(Integer.decode("0x9DC7FF"));
            }
            case 2: {
                // pastel light green
                return new Color(Integer.decode("0x92FF92"));
            }
            case 3: {
                // pastel orange
                return new Color(Integer.decode("0xFFBF00"));
            }
            case 4: {
                // pastel khaki
                return new Color(Integer.decode("0xB0AD62"));
            }
        }

        return Color.BLACK;
    }

    @Override
    public JComponent getTitleComponent() {
        return jPanel8;
    }
}
