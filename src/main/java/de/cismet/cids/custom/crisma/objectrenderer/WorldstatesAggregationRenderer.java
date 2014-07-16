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
import Sirius.navigator.ui.LAFManager;
import Sirius.navigator.ui.RequestsFullSizeComponent;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.SpiderWebPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.TextTitle;
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
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.lang.reflect.Field;

import java.text.NumberFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.prefs.Preferences;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box.Filler;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import de.cismet.cids.custom.crisma.AbstractCidsBeanAggregationRenderer;
import de.cismet.cids.custom.crisma.BorderPanel;
import de.cismet.cids.custom.crisma.ColorIcon;
import de.cismet.cids.custom.crisma.icc.Casualties;
import de.cismet.cids.custom.crisma.icc.Common;
import de.cismet.cids.custom.crisma.icc.Cost;
import de.cismet.cids.custom.crisma.icc.DamagedBuildings;
import de.cismet.cids.custom.crisma.icc.DamagedInfrastructure;
import de.cismet.cids.custom.crisma.icc.EvacuationCost;
import de.cismet.cids.custom.crisma.icc.ICCData;
import de.cismet.cids.custom.crisma.icc.OWA;
import de.cismet.cids.custom.crisma.icc.Value;
import de.cismet.cids.custom.crisma.icc.ValueIterable;
import de.cismet.cids.custom.crisma.tostringconverter.WorldstatesToStringConverter;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.commons.gui.equalizer.DefaultEqualizerModel;
import de.cismet.commons.gui.equalizer.EqualizerCategory;
import de.cismet.commons.gui.equalizer.EqualizerModelEvent;
import de.cismet.commons.gui.equalizer.EqualizerModelListener;
import de.cismet.commons.gui.equalizer.EqualizerPanel;
import de.cismet.commons.gui.equalizer.Range;

import de.cismet.tools.gui.TitleComponentProvider;
import de.cismet.tools.gui.jbands.BandModelEvent;
import de.cismet.tools.gui.jbands.JBand;
import de.cismet.tools.gui.jbands.SimpleBandModel;
import de.cismet.tools.gui.jbands.interfaces.BandModelListener;
import de.cismet.tools.gui.log4jquickconfig.Log4JQuickConfig;
import javax.swing.JComboBox;

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

    private final ImageIcon editIcon16 = ImageUtilities.loadImageIcon(this.getClass().getPackage().getName().replaceAll(
                "\\.",
                "/")
                    + "/edit_16.png",
            false);
    private final ImageIcon saveIcon16 = ImageUtilities.loadImageIcon(this.getClass().getPackage().getName().replaceAll(
                "\\.",
                "/")
                    + "/save_16.png",
            false);

    private SingleColumnModel singleColumnModel = loadStrategyModel();
    private SingleColumnModelCritFunc singleColumnModelCritFunc = loadCritFuncModel();

    private EqualizerPanel eqPanel;

    private DefaultEqualizerModel dem;

    private boolean init = false;

    private List<JSpinner> spinners = new ArrayList<JSpinner>();

    private final OWA owa;

    private final Map<CidsBean, double[]> crit = new HashMap<CidsBean, double[]>();

    private final List<Rank> ranks = new ArrayList<Rank>();

    private final HashMap<String, IndicatorBand> valueBands = new HashMap<String, IndicatorBand>(10);
    
    static boolean _critEditing = false;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddCritFunc;
    private javax.swing.JButton btnDel;
    private javax.swing.JButton btnEditSave;
    private javax.swing.JButton btnEditSaveCritFunc;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnRemCritFunc;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cboCritFuncCrit;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JList jList3;
    private javax.swing.JList jList4;
    private javax.swing.JList jList5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JPanel pnlBands;
    private javax.swing.JPanel pnlEq;
    private javax.swing.JPanel pnlSpin;
    private javax.swing.JRadioButton rdbMax;
    private javax.swing.JRadioButton rdbMin;
    private javax.swing.JRadioButton rdbMinus;
    private javax.swing.JRadioButton rdbNeutral;
    private javax.swing.JRadioButton rdbPlus;
    private javax.swing.JTable tblCrit;
    private javax.swing.JTable tblCritFunc;
    private javax.swing.JTable tblIC;
    private javax.swing.JTable tblInd;
    private javax.swing.JTable tblRankings;
    private javax.swing.JTable tblStrategies;
    private javax.swing.JTabbedPane tbpCrit;
    private javax.swing.JTabbedPane tbpInd;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WorldstatesAggregationRenderer.
     *
     * @throws  IOException  DOCUMENT ME!
     */
    public WorldstatesAggregationRenderer() throws IOException {
        owa = new OWA();
        initComponents();

        jLabel1.setIcon(i);
        jTabbedPane2.addChangeListener(new TabChangedL());
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
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jList4 = new javax.swing.JList();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jList5 = new javax.swing.JList();
        jPanel15 = new javax.swing.JPanel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        pnlBands = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblInd = new javax.swing.JTable();
        tbpInd = new javax.swing.JTabbedPane();
        jPanel23 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblIC = new javax.swing.JTable();
        jPanel24 = new javax.swing.JPanel();
        jToolBar2 = new javax.swing.JToolBar();
        btnEditSaveCritFunc = new javax.swing.JButton();
        btnAddCritFunc = new javax.swing.JButton();
        btnRemCritFunc = new javax.swing.JButton();
        jScrollPane12 = new javax.swing.JScrollPane();
        tblCritFunc = new javax.swing.JTable();
        jPanel25 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblCrit = new javax.swing.JTable();
        tbpCrit = new javax.swing.JTabbedPane();
        cboCritFuncCrit = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0),
                new java.awt.Dimension(0, 0),
                new java.awt.Dimension(32767, 0));
        jPanel11 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        pnlEq = new javax.swing.JPanel();
        pnlSpin = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        rdbPlus = new javax.swing.JRadioButton();
        rdbNeutral = new javax.swing.JRadioButton();
        rdbMinus = new javax.swing.JRadioButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0),
                new java.awt.Dimension(0, 0),
                new java.awt.Dimension(0, 32767));
        rdbMin = new javax.swing.JRadioButton();
        rdbMax = new javax.swing.JRadioButton();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblStrategies = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        btnEditSave = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        btnDel = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblRankings = new javax.swing.JTable();
        jPanel22 = new javax.swing.JPanel();

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

        jPanel12.setLayout(new java.awt.GridBagLayout());

        jPanel13.setLayout(new java.awt.BorderLayout());

        jList4.setModel(new javax.swing.AbstractListModel() {

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
        jList4.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane5.setViewportView(jList4);

        jPanel13.add(jScrollPane5, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel12.add(jPanel13, gridBagConstraints);

        jPanel14.setLayout(new java.awt.BorderLayout());

        jList5.setModel(new javax.swing.AbstractListModel() {

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
        jList5.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane7.setViewportView(jList5);

        jPanel14.add(jScrollPane7, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel12.add(jPanel14, gridBagConstraints);

        jPanel15.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel12.add(jPanel15, gridBagConstraints);

        pnlBands.setLayout(new java.awt.BorderLayout());

        setLayout(new java.awt.GridBagLayout());

        jTabbedPane2.setName("f"); // NOI18N

        jPanel9.setOpaque(false);
        jPanel9.setLayout(new java.awt.GridBagLayout());

        jScrollPane4.setMinimumSize(new java.awt.Dimension(454, 270));
        jScrollPane4.setPreferredSize(new java.awt.Dimension(454, 270));

        tblInd.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                    { null, null, null, null },
                    { null, null, null, null },
                    { null, null, null, null },
                    { null, null, null, null }
                },
                new String[] { "Title 1", "Title 2", "Title 3", "Title 4" }));
        tblInd.setCellSelectionEnabled(true);
        tblInd.setPreferredSize(new java.awt.Dimension(300, 245));
        tblInd.setShowHorizontalLines(false);
        jScrollPane4.setViewportView(tblInd);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel9.add(jScrollPane4, gridBagConstraints);

        tbpInd.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        jPanel9.add(tbpInd, gridBagConstraints);

        jTabbedPane2.addTab(NbBundle.getMessage(
                WorldstatesAggregationRenderer.class,
                "WorldstatesAggregationRenderer.jPanel9.TabConstraints.tabTitle",
                new Object[] {}),
            jPanel9); // NOI18N

        jPanel23.setLayout(new java.awt.GridBagLayout());

        jScrollPane10.setPreferredSize(new java.awt.Dimension(454, 270));

        tblIC.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                    { null, null, null, null },
                    { null, null, null, null },
                    { null, null, null, null },
                    { null, null, null, null }
                },
                new String[] { "Title 1", "Title 2", "Title 3", "Title 4" }));
        tblIC.setPreferredSize(new java.awt.Dimension(300, 245));
        jScrollPane10.setViewportView(tblIC);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel23.add(jScrollPane10, gridBagConstraints);

        jPanel24.setPreferredSize(new java.awt.Dimension(250, 433));
        jPanel24.setLayout(new java.awt.BorderLayout(5, 5));

        jToolBar2.setRollover(true);

        btnEditSaveCritFunc.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/crisma/objectrenderer/edit_16.png"))); // NOI18N
        btnEditSaveCritFunc.setText(NbBundle.getMessage(
                WorldstatesAggregationRenderer.class,
                "WorldstatesAggregationRenderer.btnEditSaveCritFunc.text"));                          // NOI18N
        btnEditSaveCritFunc.setFocusable(false);
        btnEditSaveCritFunc.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEditSaveCritFunc.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(btnEditSaveCritFunc);

        btnAddCritFunc.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/crisma/objectrenderer/new.png"))); // NOI18N
        btnAddCritFunc.setText(NbBundle.getMessage(
                WorldstatesAggregationRenderer.class,
                "WorldstatesAggregationRenderer.btnAddCritFunc.text"));                           // NOI18N
        btnAddCritFunc.setFocusable(false);
        btnAddCritFunc.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAddCritFunc.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAddCritFunc.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnAddCritFuncActionPerformed(evt);
                }
            });
        jToolBar2.add(btnAddCritFunc);

        btnRemCritFunc.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/crisma/objectrenderer/del.png"))); // NOI18N
        btnRemCritFunc.setText(NbBundle.getMessage(
                WorldstatesAggregationRenderer.class,
                "WorldstatesAggregationRenderer.btnRemCritFunc.text"));                           // NOI18N
        btnRemCritFunc.setFocusable(false);
        btnRemCritFunc.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRemCritFunc.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRemCritFunc.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnRemCritFuncActionPerformed(evt);
                }
            });
        jToolBar2.add(btnRemCritFunc);

        jPanel24.add(jToolBar2, java.awt.BorderLayout.PAGE_START);

        tblCritFunc.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                    { null, null, null, null },
                    { null, null, null, null },
                    { null, null, null, null },
                    { null, null, null, null }
                },
                new String[] { "Title 1", "Title 2", "Title 3", "Title 4" }));
        jScrollPane12.setViewportView(tblCritFunc);

        jPanel24.add(jScrollPane12, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel23.add(jPanel24, gridBagConstraints);

        jPanel25.setBorder(javax.swing.BorderFactory.createTitledBorder(
                NbBundle.getMessage(
                    WorldstatesAggregationRenderer.class,
                    "WorldstatesAggregationRenderer.jPanel25.border.title"))); // NOI18N
        jPanel25.setLayout(new java.awt.BorderLayout());

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText(NbBundle.getMessage(
                WorldstatesAggregationRenderer.class,
                "WorldstatesAggregationRenderer.jLabel2.text")); // NOI18N
        jPanel25.add(jLabel2, java.awt.BorderLayout.PAGE_END);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel23.add(jPanel25, gridBagConstraints);

        jTabbedPane2.addTab(NbBundle.getMessage(
                WorldstatesAggregationRenderer.class,
                "WorldstatesAggregationRenderer.jPanel23.TabConstraints.tabTitle"),
            jPanel23); // NOI18N

        jPanel10.setOpaque(false);
        jPanel10.setLayout(new java.awt.GridBagLayout());

        jScrollPane6.setPreferredSize(new java.awt.Dimension(454, 270));

        tblCrit.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                    { null, null, null, null },
                    { null, null, null, null },
                    { null, null, null, null },
                    { null, null, null, null }
                },
                new String[] { "Title 1", "Title 2", "Title 3", "Title 4" }));
        tblCrit.setCellSelectionEnabled(true);
        tblCrit.setShowHorizontalLines(false);
        jScrollPane6.setViewportView(tblCrit);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel10.add(jScrollPane6, gridBagConstraints);

        tbpCrit.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        jPanel10.add(tbpCrit, gridBagConstraints);

        cboCritFuncCrit.setModel(new javax.swing.DefaultComboBoxModel(
                new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel10.add(cboCritFuncCrit, gridBagConstraints);

        jLabel3.setText(NbBundle.getMessage(
                WorldstatesAggregationRenderer.class,
                "WorldstatesAggregationRenderer.jLabel3.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 5);
        jPanel10.add(jLabel3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel10.add(filler2, gridBagConstraints);

        jTabbedPane2.addTab(NbBundle.getMessage(
                WorldstatesAggregationRenderer.class,
                "WorldstatesAggregationRenderer.jPanel10.TabConstraints.tabTitle",
                new Object[] {}),
            jPanel10); // NOI18N

        jPanel11.setOpaque(false);
        jPanel11.setLayout(new java.awt.GridBagLayout());

        jPanel16.setOpaque(false);
        jPanel16.setLayout(new java.awt.GridBagLayout());

        pnlEq.setOpaque(false);
        pnlEq.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel16.add(pnlEq, gridBagConstraints);

        pnlSpin.setOpaque(false);
        pnlSpin.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel16.add(pnlSpin, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanel11.add(jPanel16, gridBagConstraints);

        jPanel17.setOpaque(false);
        jPanel17.setLayout(new java.awt.GridBagLayout());

        buttonGroup1.add(rdbPlus);
        rdbPlus.setText(NbBundle.getMessage(
                WorldstatesAggregationRenderer.class,
                "WorldstatesAggregationRenderer.rdbPlus.text",
                new Object[] {})); // NOI18N
        rdbPlus.setToolTipText(NbBundle.getMessage(
                WorldstatesAggregationRenderer.class,
                "WorldstatesAggregationRenderer.rdbPlus.toolTipText",
                new Object[] {})); // NOI18N
        rdbPlus.setContentAreaFilled(false);
        rdbPlus.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    rdbPlusActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        jPanel17.add(rdbPlus, gridBagConstraints);

        buttonGroup1.add(rdbNeutral);
        rdbNeutral.setSelected(true);
        rdbNeutral.setText(NbBundle.getMessage(
                WorldstatesAggregationRenderer.class,
                "WorldstatesAggregationRenderer.rdbNeutral.text",
                new Object[] {})); // NOI18N
        rdbNeutral.setContentAreaFilled(false);
        rdbNeutral.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    rdbNeutralActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        jPanel17.add(rdbNeutral, gridBagConstraints);

        buttonGroup1.add(rdbMinus);
        rdbMinus.setText(NbBundle.getMessage(
                WorldstatesAggregationRenderer.class,
                "WorldstatesAggregationRenderer.rdbMinus.text",
                new Object[] {})); // NOI18N
        rdbMinus.setContentAreaFilled(false);
        rdbMinus.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    rdbMinusActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        jPanel17.add(rdbMinus, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel17.add(filler1, gridBagConstraints);

        buttonGroup1.add(rdbMin);
        rdbMin.setText(NbBundle.getMessage(
                WorldstatesAggregationRenderer.class,
                "WorldstatesAggregationRenderer.rdbMin.text")); // NOI18N
        rdbMin.setContentAreaFilled(false);
        rdbMin.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    rdbMinActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        jPanel17.add(rdbMin, gridBagConstraints);

        buttonGroup1.add(rdbMax);
        rdbMax.setText(NbBundle.getMessage(
                WorldstatesAggregationRenderer.class,
                "WorldstatesAggregationRenderer.rdbMax.text")); // NOI18N
        rdbMax.setContentAreaFilled(false);
        rdbMax.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    rdbMaxActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        jPanel17.add(rdbMax, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel11.add(jPanel17, gridBagConstraints);

        jPanel18.setOpaque(false);
        jPanel18.setLayout(new java.awt.GridBagLayout());

        jPanel19.setLayout(new java.awt.GridBagLayout());

        tblStrategies.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                    { null, null, null, null },
                    { null, null, null, null },
                    { null, null, null, null },
                    { null, null, null, null }
                },
                new String[] { "Title 1", "Title 2", "Title 3", "Title 4" }));
        jScrollPane8.setViewportView(tblStrategies);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel19.add(jScrollPane8, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel18.add(jPanel19, gridBagConstraints);

        jToolBar1.setRollover(true);

        btnEditSave.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/crisma/objectrenderer/edit_16.png"))); // NOI18N
        btnEditSave.setText(NbBundle.getMessage(
                WorldstatesAggregationRenderer.class,
                "WorldstatesAggregationRenderer.btnEditSave.text",
                new Object[] {}));                                                                    // NOI18N
        btnEditSave.setFocusable(false);
        btnEditSave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEditSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnEditSave);

        btnNew.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/crisma/objectrenderer/new.png"))); // NOI18N
        btnNew.setText(NbBundle.getMessage(
                WorldstatesAggregationRenderer.class,
                "WorldstatesAggregationRenderer.btnNew.text",
                new Object[] {}));                                                                // NOI18N
        btnNew.setFocusable(false);
        btnNew.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNew.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNew.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnNewActionPerformed(evt);
                }
            });
        jToolBar1.add(btnNew);

        btnDel.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/crisma/objectrenderer/del.png"))); // NOI18N
        btnDel.setText(NbBundle.getMessage(
                WorldstatesAggregationRenderer.class,
                "WorldstatesAggregationRenderer.btnDel.text",
                new Object[] {}));                                                                // NOI18N
        btnDel.setFocusable(false);
        btnDel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDel.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnDelActionPerformed(evt);
                }
            });
        jToolBar1.add(btnDel);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel18.add(jToolBar1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.4;
        jPanel11.add(jPanel18, gridBagConstraints);

        jPanel20.setOpaque(false);
        jPanel20.setLayout(new java.awt.GridBagLayout());

        jPanel21.setOpaque(false);
        jPanel21.setLayout(new java.awt.GridBagLayout());

        tblRankings.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                    { "L'Aquila (M=10 +BR -PC) [S1]", null, new Integer(1) },
                    { "L'Aquila (M=7 +BR -PC) [S3]", null, new Integer(2) },
                    { "L'Aquila (M=7 +BR -PC) 2 [S0]", null, new Integer(3) },
                    { "L'Aquila (M=7 +BR) [S2]", null, new Integer(4) },
                    { "L'Aquila (M=7) [S1]", null, new Integer(5) },
                    { null, null, null },
                    { null, null, null },
                    { null, null, null },
                    { null, null, null },
                    { null, null, null },
                    { null, null, null },
                    { null, null, null },
                    { null, null, null },
                    { null, null, null },
                    { null, null, null }
                },
                new String[] { "Worldstate", "Level of Satisfaction", "Ranking" }) {

                Class[] types = new Class[] {
                        java.lang.String.class,
                        java.lang.Integer.class,
                        java.lang.Integer.class
                    };
                boolean[] canEdit = new boolean[] { false, false, false };

                @Override
                public Class getColumnClass(final int columnIndex) {
                    return types[columnIndex];
                }

                @Override
                public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                    return canEdit[columnIndex];
                }
            });
        tblRankings.getTableHeader().setReorderingAllowed(false);
        jScrollPane9.setViewportView(tblRankings);
        if (tblRankings.getColumnModel().getColumnCount() > 0) {
            tblRankings.getColumnModel()
                    .getColumn(0)
                    .setHeaderValue(NbBundle.getMessage(
                            WorldstatesAggregationRenderer.class,
                            "WorldstatesAggregationRenderer.tblRankings.columnModel.title0",
                            new Object[] {})); // NOI18N
            tblRankings.getColumnModel().getColumn(1).setPreferredWidth(50);
            tblRankings.getColumnModel()
                    .getColumn(1)
                    .setHeaderValue(NbBundle.getMessage(
                            WorldstatesAggregationRenderer.class,
                            "WorldstatesAggregationRenderer.tblRankings.columnModel.title1",
                            new Object[] {})); // NOI18N
            tblRankings.getColumnModel().getColumn(2).setPreferredWidth(50);
            tblRankings.getColumnModel()
                    .getColumn(2)
                    .setHeaderValue(NbBundle.getMessage(
                            WorldstatesAggregationRenderer.class,
                            "WorldstatesAggregationRenderer.tblRankings.columnModel.title2",
                            new Object[] {})); // NOI18N
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel21.add(jScrollPane9, gridBagConstraints);

        jPanel22.setOpaque(false);
        jPanel22.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.7;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel21.add(jPanel22, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel20.add(jPanel21, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel11.add(jPanel20, gridBagConstraints);

        jTabbedPane2.addTab(NbBundle.getMessage(
                WorldstatesAggregationRenderer.class,
                "WorldstatesAggregationRenderer.jPanel11.TabConstraints.tabTitle",
                new Object[] {}),
            jPanel11); // NOI18N

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jTabbedPane2, gridBagConstraints);
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnDelActionPerformed(final java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnDelActionPerformed
    {//GEN-HEADEREND:event_btnDelActionPerformed
        singleColumnModel.removeRow(tblStrategies.getSelectedRow());
        if (singleColumnModel.getRowCount() == 0) {
            btnEditSave.setEnabled(false);
            btnDel.setEnabled(false);
        }
        calcOWARanks();
    }//GEN-LAST:event_btnDelActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnNewActionPerformed(final java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnNewActionPerformed
    {//GEN-HEADEREND:event_btnNewActionPerformed
        final Strategy s = new Strategy();
        s.name = "New Strategy";
        s.lse = 0;
        s.data = new ICCData();
        s.data.setCasualties(new Casualties());
        s.data.setCost(new Cost());
        s.data.setDamagedBuildings(new DamagedBuildings());
        s.data.setDamagedInfrastructure(new DamagedInfrastructure());
        s.data.setEvacuationCost(new EvacuationCost());
        s.data.getCasualties().setNoOfDead(new Value());
        s.data.getCasualties().getNoOfDead().setValue(String.valueOf(100));
        s.data.getCasualties().setNoOfInjured(new Value());
        s.data.getCasualties().getNoOfInjured().setValue(String.valueOf(100));
        s.data.getCasualties().setNoOfHomeless(new Value());
        s.data.getCasualties().getNoOfHomeless().setValue(String.valueOf(100));
        s.data.getCost().setDirectDamageCost(new Value());
        s.data.getCost().getDirectDamageCost().setValue(String.valueOf(100));
        s.data.getCost().setIndirectDamageCost(new Value());
        s.data.getCost().getIndirectDamageCost().setValue(String.valueOf(100));
        s.data.getCost().setRestorationCost(new Value());
        s.data.getCost().getRestorationCost().setValue(String.valueOf(100));
        s.data.getDamagedBuildings().setLostBuildings(new Value());
        s.data.getDamagedBuildings().getLostBuildings().setValue(String.valueOf(100));
        s.data.getDamagedBuildings().setUnsafeBuildings(new Value());
        s.data.getDamagedBuildings().getUnsafeBuildings().setValue(String.valueOf(100));
        s.data.getDamagedInfrastructure().setDamagedRoadSegments(new Value());
        s.data.getDamagedInfrastructure().getDamagedRoadSegments().setValue(String.valueOf(100));
        s.data.getEvacuationCost().setTotalEvacuationCost(new Value());
        s.data.getEvacuationCost().getTotalEvacuationCost().setValue(String.valueOf(100));

        singleColumnModel.addRow(s);
        final int row = tblStrategies.getRowCount() - 1;
        tblStrategies.getSelectionModel().setSelectionInterval(row, row);
        setCritEnabled(true);
        singleColumnModel.setEditingRow(row);
        btnEditSave.getAction().putValue(Action.SMALL_ICON, saveIcon16);
        tblStrategies.repaint();
        tblStrategies.setEditingRow(row);
        btnNew.setEnabled(false);
        btnDel.setEnabled(false);
    }//GEN-LAST:event_btnNewActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void rdbPlusActionPerformed(final java.awt.event.ActionEvent evt)//GEN-FIRST:event_rdbPlusActionPerformed
    {//GEN-HEADEREND:event_rdbPlusActionPerformed
        singleColumnModel.strategies.get(singleColumnModel.getEditingRow()).lse = 1;
        calcOWARanks();
    }//GEN-LAST:event_rdbPlusActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void rdbNeutralActionPerformed(final java.awt.event.ActionEvent evt)//GEN-FIRST:event_rdbNeutralActionPerformed
    {//GEN-HEADEREND:event_rdbNeutralActionPerformed
        singleColumnModel.strategies.get(singleColumnModel.getEditingRow()).lse = 0;
        calcOWARanks();
    }//GEN-LAST:event_rdbNeutralActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void rdbMinusActionPerformed(final java.awt.event.ActionEvent evt)//GEN-FIRST:event_rdbMinusActionPerformed
    {//GEN-HEADEREND:event_rdbMinusActionPerformed
        singleColumnModel.strategies.get(singleColumnModel.getEditingRow()).lse = -1;
        calcOWARanks();
    }//GEN-LAST:event_rdbMinusActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void rdbMinActionPerformed(final java.awt.event.ActionEvent evt)//GEN-FIRST:event_rdbMinActionPerformed
    {//GEN-HEADEREND:event_rdbMinActionPerformed
        singleColumnModel.strategies.get(singleColumnModel.getEditingRow()).lse = -2;
        calcOWARanks();
    }//GEN-LAST:event_rdbMinActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void rdbMaxActionPerformed(final java.awt.event.ActionEvent evt)//GEN-FIRST:event_rdbMaxActionPerformed
    {//GEN-HEADEREND:event_rdbMaxActionPerformed
        singleColumnModel.strategies.get(singleColumnModel.getEditingRow()).lse = 2;
        calcOWARanks();
    }//GEN-LAST:event_rdbMaxActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnRemCritFuncActionPerformed(final java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnRemCritFuncActionPerformed
    {//GEN-HEADEREND:event_btnRemCritFuncActionPerformed
        singleColumnModelCritFunc.removeRow(tblCritFunc.getSelectedRow());
        if (singleColumnModelCritFunc.getRowCount() == 0) {
            btnEditSaveCritFunc.setEnabled(false);
            btnRemCritFunc.setEnabled(false);
        }
        // reinit bands?
    }//GEN-LAST:event_btnRemCritFuncActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnAddCritFuncActionPerformed(final java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnAddCritFuncActionPerformed
    {//GEN-HEADEREND:event_btnAddCritFuncActionPerformed
        try {
            final CritFunc s = new CritFunc();
            s.name = "New Criteria Function";
            s.bands = new ArrayList<CritFuncBand>();

            final List<CidsBean> icclist = getCidsBeans().iterator().next().getBeanCollectionProperty("iccdata");
            CidsBean iccbean = null;
            for (final CidsBean icc : icclist) {
                if ("Indicators".equalsIgnoreCase((String)icc.getProperty("name"))) {
                    iccbean = icc;
                    break;
                }
            }

            final ICCData data = m.readValue((String)iccbean.getProperty("actualaccessinfo"), ICCData.class);
            for (final ValueIterable vi : data) {
                for (final Value v : vi) {
                    final CritFuncBand band = new CritFuncBand();
                    band.name = v.getDisplayName();
                    band.groups = new TreeSet<CriteriaGroup>();
                    band.zeroGroup = new CriteriaGroup(0, 0, v.getUnit());
                    band.hundredGroup = new CriteriaGroup(100, 0, v.getUnit());
                    s.bands.add(band);
                }
            }

            singleColumnModelCritFunc.addRow(s);
            final int row = tblCritFunc.getRowCount() - 1;
            tblCritFunc.getSelectionModel().setSelectionInterval(row, row);
            singleColumnModelCritFunc.setEditingRow(row);
            btnEditSaveCritFunc.getAction().putValue(Action.SMALL_ICON, saveIcon16);
            tblCritFunc.repaint();
            tblCritFunc.setEditingRow(row);
            btnAddCritFunc.setEnabled(false);
            btnRemCritFunc.setEnabled(false);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnAddCritFuncActionPerformed

    @Override
    protected void init() {
        jLabel1.setText("Comparing " + getCidsBeans().size() + " Worldstates");
        try {
//            for (final CidsBean c : getCidsBeans()) {
//                final List<CidsBean> icclist = c.getBeanCollectionProperty("iccdata");
//                CidsBean iccbean = null;
//                for (final CidsBean icc : icclist) {
//                    if ("Criteria".equalsIgnoreCase((String)icc.getProperty("name"))) {
//                        iccbean = icc;
//                        break;
//                    }
//                }
//
//                final ICCData data = m.readValue((String)iccbean.getProperty("actualaccessinfo"), ICCData.class);
//                final double[] vector = new double[10];
//                int i = 0;
//                for (final ValueIterable vi : data) {
//                    for (final Value v : vi) {
//                        vector[i++] = Double.parseDouble(v.getValue()) / 100d;
//                    }
//                }
//                crit.put(c, vector);
//            }
            initTable(false);
//            initTable(true);
        } catch (Exception ex) {
            LOG.warn("cannot init", ex);
        }
        try {
            initBar(false);
        } catch (final Exception e) {
            LOG.warn("cannot init", e);
        }
        try {
            initAnalysisGraph(false);
//            initAnalysisGraph(true);
        } catch (final Exception e) {
            LOG.warn("cannot init", e);
        }

        try {
            initBand();
            initICTable();
            jPanel25.add(pnlBands);
        } catch (final Exception e) {
            LOG.warn("cannot init", e);
        }
//        try {
//            final Thread t = new Thread(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            initMultipleSpiderWebChart();
//                        }
//                    });
//            t.start();
//        } catch (final Exception e) {
//            LOG.warn("cannot init", e);
//        }
//        try {
//            initMCA();
//        } catch (final Exception e) {
//            LOG.warn("cannot init", e);
//        }
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
                if ("Indicators".equalsIgnoreCase((String)icc.getProperty("name"))) {
                    iccbean = icc;
                    break;
                }
            }

            ICCData data = m.readValue((String)iccbean.getProperty("actualaccessinfo"), ICCData.class);
            if (criteria) {
                data = calcCritData(data, (CritFunc)cboCritFuncCrit.getSelectedItem());
            }

            if (first) {
                tm.addColumn(criteria ? "Criteria (higher is better)" : "Indicators",
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
                        l.setText(nf.format(Double.parseDouble(v.getValue())) + " " + v.getUnit());
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
     * @param  criteria  DOCUMENT ME!
     */
    private void initAnalysisGraph(final boolean criteria) {
        GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = java.awt.GridBagConstraints.BOTH;
        gbc.weighty = 0.5;
        gbc.insets = new java.awt.Insets(5, 5, 5, 5);
        final BorderPanel p1 = new BorderPanel();
        jPanel12.removeAll();
        jPanel15.removeAll();
        if (criteria) {
            p1.setTitle("X-Criteria");
            jPanel12.remove(jPanel13);
            p1.setContentPane(jPanel13);
            jPanel12.add(p1, gbc);
        } else {
            p1.setTitle("X-Indicator");
            jPanel1.remove(jPanel3);
            p1.setContentPane(jPanel3);
            jPanel1.add(p1, gbc);
        }

        gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = java.awt.GridBagConstraints.BOTH;
        gbc.weightx = 0.2;
        gbc.weighty = 0.5;
        gbc.insets = new java.awt.Insets(5, 5, 5, 5);
        final BorderPanel p2 = new BorderPanel();
        if (criteria) {
            p2.setTitle("Y-Criteria");
            jPanel12.remove(jPanel14);
            p2.setContentPane(jPanel14);
            jPanel12.add(p2, gbc);
        } else {
            p2.setTitle("Y-Indicator");
            jPanel1.remove(jPanel4);
            p2.setContentPane(jPanel4);
            jPanel1.add(p2, gbc);
        }
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
        if (criteria) {
            jPanel12.remove(jPanel15);
            p3.setContentPane(jPanel15);
            jPanel12.add(p3, gbc);
        } else {
            jPanel1.remove(jPanel2);
            p3.setContentPane(jPanel2);
            jPanel1.add(p3, gbc);
        }

        final Collection<CidsBean> wsts = getCidsBeans();
        final CidsBean wst = wsts.iterator().next();
        final List<CidsBean> icclist = wst.getBeanCollectionProperty("iccdata");
        CidsBean iccbean = null;
        for (final CidsBean icc : icclist) {
            if ("Indicators".equalsIgnoreCase((String)icc.getProperty("name"))) {
                iccbean = icc;
                break;
            }
        }
        final String json = (String)iccbean.getProperty("actualaccessinfo");
        try {
            ICCData icc = m.readValue(json, ICCData.class);
            if (criteria) {
                icc = calcCritData(icc, (CritFunc)cboCritFuncCrit.getSelectedItem());
            }
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
            final ListCellRenderer rend = new DefaultListCellRenderer() {

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
                };
            if (criteria) {
                jList4.setModel(xdlm);
                jList5.setModel(ydlm);
                jList4.setCellRenderer(rend);
                jList5.setCellRenderer(rend);
            } else {
                jList1.setModel(xdlm);
                jList2.setModel(ydlm);
                jList1.setCellRenderer(rend);
                jList2.setCellRenderer(rend);
            }
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

                    final Value x = (Value)(criteria ? jList4.getSelectedValue() : jList1.getSelectedValue());
                    final Value y = (Value)(criteria ? jList5.getSelectedValue() : jList2.getSelectedValue());

                    if ((x != null) && (y != null)) {
                        final XYSeriesCollection dataset = new XYSeriesCollection();
                        for (final CidsBean wst : getCidsBeans()) {
                            try {
                                final Double xValue = getValue(criteria, x.getDisplayName(), wst);
                                final Double yValue = getValue(criteria, y.getDisplayName(), wst);
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
                        if (criteria) {
                            jPanel15.removeAll();
                            jPanel15.add(p, BorderLayout.CENTER);
                        } else {
                            jPanel2.removeAll();
                            jPanel2.add(p, BorderLayout.CENTER);
                        }
                        WorldstatesAggregationRenderer.this.revalidate();
                    }
                }
            }

            if (criteria) {
                final ListSelL l = new ListSelL();
                jList4.addListSelectionListener(l);
                jList5.addListSelectionListener(l);
                tbpCrit.insertTab(
                    "Analysis",
                    null,
                    jPanel12,
                    "Analyse two criteria",
                    0);
            } else {
                final ListSelL l = new ListSelL();
                jList1.addListSelectionListener(l);
                jList2.addListSelectionListener(l);
                tbpInd.insertTab(
                    "Analysis",
                    null,
                    jPanel1,
                    "Analyse two indicators",
                    1);
            }
        } catch (final Exception e) {
            LOG.error("cannot init analysis graph", e);
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void initMultipleSpiderWebChart() {
        final GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);

        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    jPanel5.remove(jPanel6);
                    final BorderPanel p1 = new BorderPanel();
                    p1.setTitle("Reference Worldstate");
                    p1.setContentPane(jPanel6);
                    jPanel5.add(p1, gridBagConstraints);
                }
            });

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

        if (Thread.currentThread().isInterrupted()) {
            return;
        }

        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
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
                                final JLabel l = (JLabel)super.getListCellRendererComponent(
                                        arg0,
                                        arg1,
                                        arg2,
                                        arg3,
                                        arg4);
                                l.setText((String)((CidsBean)arg1).getProperty("name"));
                                l.setIcon(
                                    ((CidsBean)arg1).getBeanCollectionProperty("childworldstates").isEmpty() ? i2 : i);

                                return l;
                            }
                        });
                    jList3.addListSelectionListener(new ListSelectionListener() {

                            private final ObjectMapper m = new ObjectMapper(new JsonFactory());

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

                                    final JFreeChart chart = new JFreeChart(null,
                                            TextTitle.DEFAULT_FONT,
                                            plot,
                                            true);
                                    final ChartPanel chartPanel = new ChartPanel(chart, true, false, false, true, true);
                                    final BorderPanel p = new BorderPanel();
                                    p.setContentPane(chartPanel);
                                    p.setTitle(
                                        "Criteria Data of "
                                                + wst.getProperty("name"));
                                    jPanel7.add(p);
                                }
                                jPanel5.revalidate();
                            }

                            private void add(final CidsBean wst, final DefaultCategoryDataset dataset) {
                                final List<CidsBean> icclist = wst.getBeanCollectionProperty("iccdata");
                                CidsBean iccbean = null;
                                for (final CidsBean icc : icclist) {
                                    if ("Indicators".equalsIgnoreCase((String)icc.getProperty("name"))) {
                                        iccbean = icc;
                                        break;
                                    }
                                }

                                try {
                                    ICCData icc = m.readValue(
                                            (String)iccbean.getProperty("actualaccessinfo"),
                                            ICCData.class);
                                    icc = calcCritData(icc, (CritFunc)cboCritFuncCrit.getSelectedItem());

                                    final Field[] fields = icc.getClass().getDeclaredFields();
                                    for (final Field field : fields) {
                                        field.setAccessible(true);
                                        final Object o = field.get(icc);
                                        for (final Field x : o.getClass().getDeclaredFields()) {
                                            x.setAccessible(true);
                                            final Value v = (Value)x.get(o);
                                            dataset.addValue(
                                                (int)Double.parseDouble(v.getValue()),
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
                    tbpCrit.insertTab(
                        "Spider",
                        null,
                        jPanel5,
                        "Criteria data of "
                                + getCidsBeans().size()
                                + " worldstates",
                        1);
                }
            });
    }

    /**
     * DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    private void initMCA() throws Exception {
        final List<CidsBean> icclist = getCidsBeans().iterator().next().getBeanCollectionProperty("iccdata");
        CidsBean iccbean = null;
        for (final CidsBean icc : icclist) {
            if ("Indicators".equalsIgnoreCase((String)icc.getProperty("name"))) {
                iccbean = icc;
                break;
            }
        }

        ICCData data = m.readValue((String)iccbean.getProperty("actualaccessinfo"), ICCData.class);
        data = calcCritData(data, singleColumnModelCritFunc.funcs.get(0));

        int i = 0;
        final List<EqualizerCategory> cats = new ArrayList<EqualizerCategory>();
        for (final ValueIterable vi : data) {
            for (final Value v : vi) {
                cats.add(new EqualizerCategory(String.valueOf(++i)));
            }
        }

        dem = new DefaultEqualizerModel(cats, new Range(0, 100));

        i = 0;
        final GridBagConstraints gbc = new GridBagConstraints(
                0,
                0,
                1,
                1,
                0.6,
                0,
                GridBagConstraints.WEST,
                GridBagConstraints.BOTH,
                new Insets(3, 3, 3, 3),
                0,
                0);
        pnlSpin.removeAll();
        spinners.clear();
        for (final ValueIterable vi : data) {
            for (final Value v : vi) {
                cats.add(new EqualizerCategory(v.getDisplayName()));
                final GridBagConstraints c = (GridBagConstraints)gbc.clone();
                c.gridy = i;
                final GridBagConstraints c1 = (GridBagConstraints)c.clone();
                c1.gridx = 1;
                c1.weightx = 0.4;
                final JLabel l = new JLabel(String.valueOf(i + 1) + " - " + v.getDisplayName() + ":");
                final SpinnerNumberModel snm = new SpinnerNumberModel(0, 0, 100, 1);
                final int index = i;
                snm.addChangeListener(new ChangeListener() {

                        boolean changing;

                        @Override
                        public void stateChanged(final ChangeEvent e) {
                            if (init) {
                                return;
                            }
                            if (!changing) {
                                changing = true;
                                dem.setValueAt(index, snm.getNumber().intValue());
                                changing = false;
                            }
                        }
                    });

                final JSpinner s = new JSpinner(snm);
                pnlSpin.add(l, c);
                pnlSpin.add(s, c1);
                spinners.add(s);

                i++;
            }
        }

        dem.addEqualizerModelListener(new EqualizerModelListener() {

                boolean changing;

                @Override
                public void equalizerChanged(final EqualizerModelEvent event) {
                    if (init) {
                        return;
                    }
                    if (!changing) {
                        changing = true;
                        if (event.getIndex() < 0) {
                            for (int j = 0; j < spinners.size(); j++) {
                                spinners.get(j).setValue(eqPanel.getModel().getValueAt(j));
                            }
                        } else {
                            spinners.get(event.getIndex()).setValue(event.getNewValue());
                        }

                        if (singleColumnModel.editingRow == null) {
                            changing = false;
                            return;
                        }
                        final Strategy s = singleColumnModel.strategies.get(singleColumnModel.editingRow);
                        s.data.getCasualties().getNoOfDead().setValue(String.valueOf(eqPanel.getModel().getValueAt(0)));
                        s.data.getCasualties()
                                .getNoOfInjured()
                                .setValue(String.valueOf(eqPanel.getModel().getValueAt(1)));
                        s.data.getCasualties()
                                .getNoOfHomeless()
                                .setValue(String.valueOf(eqPanel.getModel().getValueAt(2)));
                        s.data.getDamagedBuildings()
                                .getLostBuildings()
                                .setValue(String.valueOf(eqPanel.getModel().getValueAt(3)));
                        s.data.getDamagedBuildings()
                                .getUnsafeBuildings()
                                .setValue(String.valueOf(eqPanel.getModel().getValueAt(4)));
                        s.data.getDamagedInfrastructure()
                                .getDamagedRoadSegments()
                                .setValue(String.valueOf(eqPanel.getModel().getValueAt(5)));
                        s.data.getCost()
                                .getDirectDamageCost()
                                .setValue(String.valueOf(eqPanel.getModel().getValueAt(6)));
                        s.data.getCost()
                                .getIndirectDamageCost()
                                .setValue(String.valueOf(eqPanel.getModel().getValueAt(7)));
                        s.data.getCost()
                                .getRestorationCost()
                                .setValue(String.valueOf(eqPanel.getModel().getValueAt(8)));
                        s.data.getEvacuationCost()
                                .getTotalEvacuationCost()
                                .setValue(String.valueOf(eqPanel.getModel().getValueAt(9)));

                        changing = false;

                        calcOWARanks();
                    }
                }
            });
        try {
            final Preferences p = Preferences.userNodeForPackage(WorldstatesAggregationRenderer.class);
            final String s = p.get("strategies", null);
            final List<Strategy> ss = m.readValue(s, List.class);
//            singleColumnModel.strategies.addAll(ss);
        } catch (final Exception ex) {
            LOG.error("cannot read strategies", ex);
        }
        tblStrategies.setModel(singleColumnModel);
        tblStrategies.setSelectionModel(new DefaultListSelectionModel() {

                @Override
                public void setSelectionInterval(final int index0, final int index1) {
                    if (singleColumnModel.editingRow == null) {
                        super.setSelectionInterval(index0, index1);
                    }
                }
            });
        tblStrategies.setDefaultRenderer(String.class, new DefaultTableCellRenderer() {

                @Override
                public Component getTableCellRendererComponent(final JTable table,
                        final Object value,
                        final boolean isSelected,
                        final boolean hasFocus,
                        final int row,
                        final int column) {
                    final JLabel l = (JLabel)super.getTableCellRendererComponent(
                            table,
                            value,
                            isSelected,
                            hasFocus,
                            row,
                            column);
                    l.setEnabled((singleColumnModel.editingRow == null) || (singleColumnModel.editingRow == row));

                    return l;
                }
            });

        btnEditSave.setAction(new AbstractAction() {

                {
                    putValue(Action.SMALL_ICON, editIcon16);
                }

                @Override
                public void actionPerformed(final ActionEvent e) {
                    final Integer index = singleColumnModel.editingRow;
                    if (index == null) {
                        putValue(Action.SMALL_ICON, saveIcon16);
                        singleColumnModel.setEditingRow(tblStrategies.getSelectedRow());
                        setCritEnabled(true);
                        btnNew.setEnabled(false);
                        btnDel.setEnabled(false);
                        tblStrategies.setEditingRow(singleColumnModel.editingRow);
                    } else {
                        singleColumnModel.setEditingRow(null);
                        if (tblStrategies.getCellEditor() != null) {
                            tblStrategies.getCellEditor().stopCellEditing();
                        }
                        setCritEnabled(false);
                        putValue(Action.SMALL_ICON, editIcon16);
                        btnDel.setEnabled(true);
                        btnNew.setEnabled(true);
                    }
                    tblStrategies.repaint();
                }
            });
        tblStrategies.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        eqPanel = new EqualizerPanel(dem);
        eqPanel.setSplinePainted(false);
        eqPanel.setOpaque(false);
        pnlEq.removeAll();
        pnlEq.add(eqPanel);
        jPanel11.removeAll();
//        jPanel11.remove(jPanel18);
//        jPanel11.remove(jPanel17);
//        jPanel11.remove(jPanel16);
//        jPanel11.remove(jPanel20);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.gridwidth = 2;
        BorderPanel p = new BorderPanel();
        p.setTitle("Decision Strategy");
        p.setContentPane(jPanel18);
        jPanel11.add(p, gridBagConstraints);
        p = new BorderPanel();
        p.setTitle("Criteria Emphasis");
        p.setContentPane(jPanel16);
        gridBagConstraints = (GridBagConstraints)gridBagConstraints.clone();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridwidth = 2;
        jPanel11.add(p, gridBagConstraints);
        p = new BorderPanel();
        p.setTitle("Level of Satisfaction Emphasis");
        p.setContentPane(jPanel17);
        gridBagConstraints = (GridBagConstraints)gbc.clone();
        gridBagConstraints.gridy = i++;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.weighty = 1;
        pnlSpin.add(new Filler(null, null, null), gridBagConstraints);
        gridBagConstraints = (GridBagConstraints)gbc.clone();
        gridBagConstraints.gridy = i;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.weighty = 0;
        pnlSpin.add(p, gridBagConstraints);

        p = new BorderPanel();
        p.setTitle("Rankings");
        p.setContentPane(jPanel20);
        gridBagConstraints = (GridBagConstraints)gridBagConstraints.clone();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.weightx = 1;
        jPanel11.add(p, gridBagConstraints);
        gridBagConstraints = (GridBagConstraints)gridBagConstraints.clone();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.insets = new Insets(0, 10, 0, 5);
        gridBagConstraints.weightx = 0;
        gridBagConstraints.weighty = 0;
        final JLabel l = new JLabel("Criteria function:");
        jPanel11.add(l, gridBagConstraints);
        gridBagConstraints = (GridBagConstraints)gridBagConstraints.clone();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        gridBagConstraints.weightx = 0;
        gridBagConstraints.weighty = 0;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        final JComboBox<CritFunc> cboCritFuncOwa = new JComboBox<CritFunc>();
        final DefaultComboBoxModel<CritFunc> model = new DefaultComboBoxModel<CritFunc>();
        for (final CritFunc func : singleColumnModelCritFunc.funcs) {
            model.addElement(func);
        }
        cboCritFuncOwa.setRenderer(new DefaultListCellRenderer() {

                @Override
                public Component getListCellRendererComponent(final JList<?> list,
                        final Object value,
                        final int index,
                        final boolean isSelected,
                        final boolean cellHasFocus) {
                    final JLabel l = (JLabel)super.getListCellRendererComponent(
                            list,
                            value,
                            index,
                            isSelected,
                            cellHasFocus);
                    l.setText(((CritFunc)value).getName());

                    return l;
                }
            });
        cboCritFuncOwa.addItemListener(new ItemListener() {

                @Override
                public void itemStateChanged(final ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        final CritFunc func = (CritFunc)e.getItem();
                        try {
                            crit.clear();
                            for (final CidsBean c : getCidsBeans()) {
                                final List<CidsBean> icclist = c.getBeanCollectionProperty("iccdata");
                                CidsBean iccbean = null;
                                for (final CidsBean icc : icclist) {
                                    if ("Indicators".equalsIgnoreCase((String)icc.getProperty("name"))) {
                                        iccbean = icc;
                                        break;
                                    }
                                }

                                final ICCData data = calcCritData(m.readValue(
                                            (String)iccbean.getProperty("actualaccessinfo"),
                                            ICCData.class), func);
                                final double[] vector = new double[10];
                                int i = 0;
                                for (final ValueIterable vi : data) {
                                    for (final Value v : vi) {
                                        vector[i++] = Double.parseDouble(v.getValue()) / 100d;
                                    }
                                }
                                crit.put(c, vector);
                            }
                            calcOWARanks();
                        } catch (final Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
        cboCritFuncOwa.setModel(model);
        cboCritFuncOwa.setSelectedIndex(0);
        jPanel11.add(cboCritFuncOwa, gridBagConstraints);

        jPanel11.invalidate();
        jPanel11.validate();
        tblStrategies.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

                @Override
                public void valueChanged(final ListSelectionEvent e) {
                    if (e.getValueIsAdjusting()) {
                        return;
                    }
                    if (e.getFirstIndex() < 0) {
                        btnEditSave.setEnabled(false);
                        btnDel.setEnabled(false);
                    } else {
                        init = true;
                        final int index = tblStrategies.getSelectedRow();
                        if (index == -1) {
                            init = false;
                            return;
                        }
                        final Strategy s = singleColumnModel.strategies.get(index);
                        int i = 0;
                        for (final ValueIterable vi : s.data) {
                            for (final Value v : vi) {
                                final int val = Integer.parseInt(v.getValue());
                                dem.setValueAt(i, val);
                                spinners.get(i++).setValue(val);
                            }
                        }
                        btnEditSave.setEnabled(true);
                        btnDel.setEnabled(true);
                        btnNew.setEnabled(true);

                        switch (s.lse) {
                            case -2: {
                                rdbMin.setSelected(true);
                                break;
                            }
                            case -1: {
                                rdbMinus.setSelected(true);
                                break;
                            }
                            case 0: {
                                rdbNeutral.setSelected(true);
                                break;
                            }
                            case 1: {
                                rdbPlus.setSelected(true);
                                break;
                            }
                            case 2: {
                                rdbMax.setSelected(true);
                                break;
                            }
                        }

                        calcOWARanks();
                    }
                    init = false;
                }
            });
        tblStrategies.getSelectionModel().setSelectionInterval(-1, -1);
        setCritEnabled(false);
        btnEditSave.setEnabled(false);
        btnDel.setEnabled(false);

        final CritFunc func = (CritFunc)cboCritFuncOwa.getSelectedItem();
        crit.clear();
        for (final CidsBean c : getCidsBeans()) {
            final List<CidsBean> icclist1 = c.getBeanCollectionProperty("iccdata");
            CidsBean iccbean1 = null;
            for (final CidsBean icc : icclist1) {
                if ("Indicators".equalsIgnoreCase((String)icc.getProperty("name"))) {
                    iccbean1 = icc;
                    break;
                }
            }

            final ICCData data1 = calcCritData(m.readValue(
                        (String)iccbean1.getProperty("actualaccessinfo"),
                        ICCData.class), func);
            final double[] vector = new double[10];
            int i1 = 0;
            for (final ValueIterable vi : data1) {
                for (final Value v : vi) {
                    vector[i1++] = Double.parseDouble(v.getValue()) / 100d;
                }
            }
            crit.put(c, vector);
        }
        calcOWARanks();
    }

    /**
     * DOCUMENT ME!
     */
    private void calcOWARanks() {
        ranks.clear();
        final int index = tblStrategies.getSelectedRow();
        if (index >= 0) {
            final double[] importance = new double[10];
            final Strategy strategy = singleColumnModel.strategies.get(index);
            int i = 0;
            for (final ValueIterable vi : strategy.data) {
                for (final Value v : vi) {
                    importance[i++] = Double.parseDouble(v.getValue()) / 100;
                }
            }
            double[] weights = null;
            switch (strategy.lse) {
                case -2: {
                    weights = new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 };
                    break;
                }
                case -1: {
                    weights = owa.getLLSWeights(10);
                    break;
                }
                case 0: {
                    weights = owa.getMeanWeights(10);
                    break;
                }
                case 1: {
                    weights = owa.getHLSWeights(10);
                    break;
                }
                case 2: {
                    weights = new double[] { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
                    break;
                }
            }
            final int j = 0;
            for (final CidsBean c : getCidsBeans()) {
                final double[] data = crit.get(c);
                final double rank = owa.getAggregateLS(data, weights, importance);
                final double r = Math.round(rank * 1000) / 10d;
                final Rank rr = new Rank();
                rr.rank = r;
                rr.b = c;
                ranks.add(rr);
            }
            Collections.sort(ranks, new Comparator<Rank>() {

                    @Override
                    public int compare(final Rank o1, final Rank o2) {
                        return o2.rank.compareTo(o1.rank);
                    }
                });
        }

        updateOWATable();
        jPanel22.removeAll();
        jPanel22.add(createRankingPlot());
        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    jPanel22.invalidate();
                    jPanel22.revalidate();
                }
            });
    }

    /**
     * DOCUMENT ME!
     */
    private void updateOWATable() {
        final DefaultTableModel dtm = new DefaultTableModel();
        dtm.setColumnIdentifiers(new Object[] { "Worldstate", "Level of Satisfaction", "Rank" });
        if (!ranks.isEmpty()) {
            int i = 1;
            for (final Rank score : ranks) {
                dtm.addRow(new Object[] { score.b.getProperty("name"), score.rank, i++ });
            }
        }
        tblRankings.setModel(dtm);
        tblRankings.getColumn("Worldstate").setPreferredWidth(150);
        tblRankings.getColumn("Level of Satisfaction").setPreferredWidth(80);
        tblRankings.getColumn("Rank").setPreferredWidth(30);
        tblRankings.getColumn("Rank").setCellRenderer(new DefaultTableCellRenderer() {

                @Override
                public Component getTableCellRendererComponent(final JTable table,
                        final Object value,
                        final boolean isSelected,
                        final boolean hasFocus,
                        final int row,
                        final int column) {
                    final JLabel l = (JLabel)super.getTableCellRendererComponent(
                            table,
                            value,
                            isSelected,
                            hasFocus,
                            row,
                            column); // To change body of generated methods, choose Tools | Templates.
                    l.setHorizontalAlignment(JLabel.CENTER);
                    l.setHorizontalTextPosition(JLabel.CENTER);
                    return l;
                }
            });
        tblRankings.getColumn("Level of Satisfaction").setCellRenderer(new DefaultTableCellRenderer() {

                @Override
                public Component getTableCellRendererComponent(final JTable table,
                        final Object value,
                        final boolean isSelected,
                        final boolean hasFocus,
                        final int row,
                        final int column) {
                    final JLabel l = (JLabel)super.getTableCellRendererComponent(
                            table,
                            value,
                            isSelected,
                            hasFocus,
                            row,
                            column); // To change body of generated methods, choose Tools | Templates.
                    l.setText(l.getText() + " %");
                    l.setHorizontalAlignment(JLabel.RIGHT);
                    l.setHorizontalTextPosition(JLabel.RIGHT);
                    return l;
                }
            });
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private ChartPanel createRankingPlot() {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        if (ranks.isEmpty()) {
            for (final CidsBean wst : getCidsBeans()) {
                dataset.addValue(0, "", (String)wst.getProperty("name"));
            }
        } else {
            final int i = 0;
            for (final Rank rank : ranks) {
                dataset.addValue(rank.rank, "", (String)rank.b.getProperty("name"));
            }
        }

        final JFreeChart c = ChartFactory.createBarChart(
                "Ranking",
                "Worldstates",
                "",
                dataset,
                PlotOrientation.HORIZONTAL,
                false,
                false,
                false);
        final CategoryPlot plot = c.getCategoryPlot();
        plot.getRangeAxis().setRange(0, 100);
        ((BarRenderer)plot.getRenderer()).setBarPainter(new StandardBarPainter());

        return new ChartPanel(c, false, false, false, false, false);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  enabled  DOCUMENT ME!
     */
    private void setCritEnabled(final boolean enabled) {
        eqPanel.setEnabled(enabled);
        for (final Component c : pnlSpin.getComponents()) {
            if (c instanceof JSpinner) {
                c.setEnabled(enabled);
            }
        }
        rdbMinus.setEnabled(enabled);
        rdbNeutral.setEnabled(enabled);
        rdbPlus.setEnabled(enabled);
        rdbMax.setEnabled(enabled);
        rdbMin.setEnabled(enabled);
    }

    /**
     * DOCUMENT ME!
     *
     * @throws  IOException  DOCUMENT ME!
     */
    private void initBand() throws IOException {
        tblCritFunc.setModel(singleColumnModelCritFunc);
        tblCritFunc.setSelectionModel(new DefaultListSelectionModel() {

                @Override
                public void setSelectionInterval(final int index0, final int index1) {
                    if (singleColumnModelCritFunc.editingRow == null) {
                        super.setSelectionInterval(index0, index1);
                    }
                }
            });
        tblCritFunc.setDefaultRenderer(String.class, new DefaultTableCellRenderer() {

                @Override
                public Component getTableCellRendererComponent(final JTable table,
                        final Object value,
                        final boolean isSelected,
                        final boolean hasFocus,
                        final int row,
                        final int column) {
                    final JLabel l = (JLabel)super.getTableCellRendererComponent(
                            table,
                            value,
                            isSelected,
                            hasFocus,
                            row,
                            column);
                    l.setEnabled(
                        (singleColumnModelCritFunc.editingRow == null)
                                || (singleColumnModelCritFunc.editingRow == row));

                    return l;
                }
            });

        btnEditSaveCritFunc.setAction(new AbstractAction() {

                {
                    putValue(Action.SMALL_ICON, editIcon16);
                }

                @Override
                public void actionPerformed(final ActionEvent e) {
                    final Integer index = singleColumnModelCritFunc.editingRow;
                    if (index == null) {
                        putValue(Action.SMALL_ICON, saveIcon16);
                        singleColumnModelCritFunc.setEditingRow(tblCritFunc.getSelectedRow());
                        btnAddCritFunc.setEnabled(false);
                        btnRemCritFunc.setEnabled(false);
                        tblCritFunc.setEditingRow(singleColumnModelCritFunc.editingRow);
                    } else {
                        singleColumnModelCritFunc.setEditingRow(null);
                        if (tblCritFunc.getCellEditor() != null) {
                            tblCritFunc.getCellEditor().stopCellEditing();
                        }
                        putValue(Action.SMALL_ICON, editIcon16);
                        btnRemCritFunc.setEnabled(true);
                        btnAddCritFunc.setEnabled(true);
                    }
                    tblCritFunc.repaint();
                }
            });
        tblCritFunc.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        tblCritFunc.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

                @Override
                public void valueChanged(final ListSelectionEvent e) {
                    if (e.getValueIsAdjusting()) {
                        return;
                    }
                    if (e.getFirstIndex() < 0) {
                        btnEditSaveCritFunc.setEnabled(false);
                        btnRemCritFunc.setEnabled(false);
                    } else {
                        init = true;
                        final int index = tblCritFunc.getSelectedRow();
                        if (index == -1) {
                            init = false;
                            return;
                        }
                        final CritFunc cf = singleColumnModelCritFunc.funcs.get(index);
                        try {
                            setBands(cf);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        btnEditSaveCritFunc.setEnabled(true);
                        btnRemCritFunc.setEnabled(true);
                        btnAddCritFunc.setEnabled(true);
                    }
                    init = false;
                }
            });
        tblCritFunc.getSelectionModel().setSelectionInterval(-1, -1);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   funcs  DOCUMENT ME!
     *
     * @throws  IOException  DOCUMENT ME!
     */
    private void setBands(final CritFunc funcs) throws IOException {
//        final List<CidsBean> icclist = getCidsBeans().iterator().next().getBeanCollectionProperty("iccdata");
//        CidsBean iccbean = null;
//        for (final CidsBean icc : icclist) {
//            if ("Indicators".equalsIgnoreCase((String)icc.getProperty("name"))) {
//                iccbean = icc;
//                break;
//            }
//        }

        valueBands.clear();
        final SimpleBandModel bm = new SimpleBandModel();
        for (final CritFuncBand cfb : funcs.bands) {
            final IndicatorBand b = new IndicatorBand(
                    cfb.name,
                    cfb.zeroGroup,
                    cfb.hundredGroup,
                    cfb.groups,
                    bm);
            bm.addBand(b);
            bm.addBand(b.getSpotBand());
            valueBands.put(cfb.name, b);
            bm.addBand(new VGapBand());
        }
        bm.removeBand(bm.getBand(bm.getNumberOfBands() - 1));

//        final ICCData data = m.readValue((String)iccbean.getProperty("actualaccessinfo"), ICCData.class);
//        final Iterator<ValueIterable> it1 = data.iterator();
//        while (it1.hasNext()) {
//            final Iterator<Value> it2 = it1.next().iterator();
//            while (it2.hasNext()) {
//                final Value v = it2.next();
//                final IndicatorBand b = new IndicatorBand(
//                        v.getDisplayName(),
//                        new CriteriaGroup(0, 0, v.getUnit()),
//                        new CriteriaGroup(100, 0, v.getUnit()),
//                        new ArrayList<CriteriaGroup>(),
//                        bm);
//                bm.addBand(b);
//                bm.addBand(b.getSpotBand());
//                valueBands.put(v.getDisplayName(), b);
//                if (it1.hasNext() || it2.hasNext()) {
//                    bm.addBand(new VGapBand());
//                }
//            }
//        }

        bm.addBandModelListener(new BandModelListener() {

                @Override
                public void bandModelChanged(final BandModelEvent e) {
                    bandModelValuesChanged(e);
                }

                @Override
                public void bandModelSelectionChanged(final BandModelEvent e) {
                    // noop
                }

                @Override
                public void bandModelValuesChanged(final BandModelEvent e) {
                    EventQueue.invokeLater(new Runnable() {

                            @Override
                            public void run() {
                                tblIC.repaint();
                            }
                        });
                }
            });

        final JBand band = new JBand(bm);
        band.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pnlBands.removeAll();
        pnlBands.add(band);
        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    jPanel23.invalidate();
                    jPanel23.validate();
                    jPanel23.repaint();
                    ((SimpleBandModel)band.getModel()).fireBandModelChanged();
                    jPanel23.invalidate();
                    jPanel23.validate();
                    jPanel23.repaint();
                }
            });
    }

    /**
     * DOCUMENT ME!
     *
     * @param   indData  DOCUMENT ME!
     * @param   func     DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private ICCData calcCritData(final ICCData indData, final CritFunc func) {
        final ICCData critData = new ICCData();

        // casualties
        final Casualties cas = indData.getCasualties();
        final Casualties nc = new Casualties();
        nc.setDisplayName(cas.getDisplayName());
        nc.setIconResource(cas.getIconResource());

        // noofdead
        Value n = new Value();
        Value o = cas.getNoOfDead();
        n.setDisplayName(o.getDisplayName());
        n.setIconResource(o.getIconResource());
        n.setUnit("%");
        n.setValue(String.valueOf(calcCriteria(o, func)));
        nc.setNoOfDead(n);

        // noofinjured
        n = new Value();
        o = cas.getNoOfInjured();
        n.setDisplayName(o.getDisplayName());
        n.setIconResource(o.getIconResource());
        n.setUnit("%");
        n.setValue(String.valueOf(calcCriteria(o, func)));
        nc.setNoOfInjured(n);

        // noofhomeless
        n = new Value();
        o = cas.getNoOfHomeless();
        n.setDisplayName(o.getDisplayName());
        n.setIconResource(o.getIconResource());
        n.setUnit("%");
        n.setValue(String.valueOf(calcCriteria(o, func)));
        nc.setNoOfHomeless(n);

        critData.setCasualties(nc);

        // buildings
        final DamagedBuildings dam = indData.getDamagedBuildings();
        final DamagedBuildings ndb = new DamagedBuildings();
        ndb.setDisplayName(dam.getDisplayName());
        ndb.setIconResource(dam.getIconResource());

        // lostbuildings
        n = new Value();
        o = dam.getLostBuildings();
        n.setDisplayName(o.getDisplayName());
        n.setIconResource(o.getIconResource());
        n.setUnit("%");
        n.setValue(String.valueOf(calcCriteria(o, func)));
        ndb.setLostBuildings(n);

        // unsafe buildings
        n = new Value();
        o = dam.getUnsafeBuildings();
        n.setDisplayName(o.getDisplayName());
        n.setIconResource(o.getIconResource());
        n.setUnit("%");
        n.setValue(String.valueOf(calcCriteria(o, func)));
        ndb.setUnsafeBuildings(n);

        critData.setDamagedBuildings(ndb);

        // infra
        final DamagedInfrastructure di = indData.getDamagedInfrastructure();
        final DamagedInfrastructure ndi = new DamagedInfrastructure();
        ndi.setDisplayName(di.getDisplayName());
        ndi.setIconResource(di.getIconResource());

        // roads
        n = new Value();
        o = di.getDamagedRoadSegments();
        n.setDisplayName(o.getDisplayName());
        n.setIconResource(o.getIconResource());
        n.setUnit("%");
        n.setValue(String.valueOf(calcCriteria(o, func)));
        ndi.setDamagedRoadSegments(n);

        critData.setDamagedInfrastructure(ndi);

        // cost
        final Cost c = indData.getCost();
        final Cost co = new Cost();
        co.setDisplayName(c.getDisplayName());
        co.setIconResource(c.getIconResource());

        // direct
        n = new Value();
        o = c.getDirectDamageCost();
        n.setDisplayName(o.getDisplayName());
        n.setIconResource(o.getIconResource());
        n.setUnit("%");
        n.setValue(String.valueOf(calcCriteria(o, func)));
        co.setDirectDamageCost(n);

        // indirect
        n = new Value();
        o = c.getIndirectDamageCost();
        n.setDisplayName(o.getDisplayName());
        n.setIconResource(o.getIconResource());
        n.setUnit("%");
        n.setValue(String.valueOf(calcCriteria(o, func)));
        co.setIndirectDamageCost(n);

        // restoration
        n = new Value();
        o = c.getRestorationCost();
        n.setDisplayName(o.getDisplayName());
        n.setIconResource(o.getIconResource());
        n.setUnit("%");
        n.setValue(String.valueOf(calcCriteria(o, func)));
        co.setRestorationCost(n);

        critData.setCost(co);

        // evac
        final EvacuationCost ec = indData.getEvacuationCost();
        final EvacuationCost ne = new EvacuationCost();
        ne.setDisplayName(ec.getDisplayName());
        ne.setIconResource(ec.getIconResource());

        // total evac cost
        n = new Value();
        o = ec.getTotalEvacuationCost();
        n.setDisplayName(o.getDisplayName());
        n.setIconResource(o.getIconResource());
        n.setUnit("%");
        n.setValue(String.valueOf(calcCriteria(o, func)));
        ne.setTotalEvacuationCost(n);

        critData.setEvacuationCost(ne);

        return critData;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   value  DOCUMENT ME!
     * @param   func   DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private double calcCriteria(final Value value, final CritFunc func) {
        if (func == null) {
            return 0;
        }

        CritFuncBand b = null;
        for (final CritFuncBand band : func.bands) {
            if (band.name.equals(value.getDisplayName())) {
                b = band;
                break;
            }
        }

        if (b == null) {
            return 0;
        }
        final ArrayList<CriteriaGroup> l = new ArrayList<CriteriaGroup>();
        l.add(b.getZeroGroup());
        l.addAll(b.getGroups());
        l.add(b.getHundredGroup());
        if (b.getZeroGroup().getValue() > b.getHundredGroup().getValue()) {
            Collections.reverse(l);
        }

        assert l.size() >= 2 : "too few criteria groups";

        final double los;
        final double val = Double.parseDouble(value.getValue());
        if (l.get(0).getValue() >= val) {
            los = l.get(0).getLevelOfSatisfaction();
        } else if (l.get(l.size() - 1).getValue() <= val) {
            los = l.get(l.size() - 1).getLevelOfSatisfaction();
        } else {
            CriteriaGroup pre = null;
            CriteriaGroup suc = null;
            for (int i = 0; i < l.size(); ++i) {
                final CriteriaGroup g = l.get(i);
                if (g.getValue() < val) {
                    pre = g;
                    if (l.get(i + 1).getValue() > val) {
                        suc = l.get(i + 1);
                        break;
                    }
                }
            }

            assert (pre != null) && (suc != null) : "pre and suc not found";

            final double max = Math.max(pre.getValue(), suc.getValue());
            final double min = Math.min(pre.getValue(), suc.getValue());
            final double preLos = pre.getLevelOfSatisfaction();
            final double sucLos = suc.getLevelOfSatisfaction();
            final double rate = (max - val) / (max - min);

            los = sucLos + ((preLos - sucLos) * rate);
        }

        return Math.round(los * 100) / 100d;
    }

    /**
     * DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    private void initICTable() throws Exception {
        boolean first = true;
        final DefaultTableModel tm = new DefaultTableModel();
        for (final CidsBean wst : getCidsBeans()) {
            final List<CidsBean> icclist = wst.getBeanCollectionProperty("iccdata");
            CidsBean ibean = null;
            for (final CidsBean icc : icclist) {
                if ("Indicators".equalsIgnoreCase((String)icc.getProperty("name"))) {
                    ibean = icc;
                }
            }

            final ICCData idata = m.readValue((String)ibean.getProperty("actualaccessinfo"), ICCData.class);

            if (first) {
                tm.addColumn(
                    "Indicators / Criteria",
                    new Object[] {
                        idata.getCasualties(),
                        idata.getCasualties().getNoOfDead(),
                        idata.getCasualties().getNoOfInjured(),
                        idata.getCasualties().getNoOfHomeless(),
                        idata.getDamagedBuildings(),
                        idata.getDamagedBuildings().getLostBuildings(),
                        idata.getDamagedBuildings().getUnsafeBuildings(),
                        idata.getDamagedInfrastructure(),
                        idata.getDamagedInfrastructure().getDamagedRoadSegments(),
                        idata.getCost(),
                        idata.getCost().getDirectDamageCost(),
                        idata.getCost().getIndirectDamageCost(),
                        idata.getCost().getRestorationCost(),
                        idata.getEvacuationCost(),
                        idata.getEvacuationCost().getTotalEvacuationCost()
                    });
                first = false;
            }

            tm.addColumn(wst.getProperty("name"),
                new Object[] {
                    "",
                    idata.getCasualties().getNoOfDead(),
                    idata.getCasualties().getNoOfInjured(),
                    idata.getCasualties().getNoOfHomeless(),
                    "",
                    idata.getDamagedBuildings().getLostBuildings(),
                    idata.getDamagedBuildings().getUnsafeBuildings(),
                    "",
                    idata.getDamagedInfrastructure().getDamagedRoadSegments(),
                    "",
                    idata.getCost().getDirectDamageCost(),
                    idata.getCost().getIndirectDamageCost(),
                    idata.getCost().getRestorationCost(),
                    "",
                    idata.getEvacuationCost().getTotalEvacuationCost()
                });

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
                            final Value ic = (Value)value;
                            CritFunc func = null;
                            try {
                                func = singleColumnModelCritFunc.funcs.get(tblCritFunc.getSelectedRow());
                            } catch (final Exception e) {
                                // noop
                            }
                            l.setText(nf.format(Double.parseDouble(ic.getValue())) + " " + ic.getUnit() + " / "
                                        + calcCriteria(
                                            ic,
                                            func) + " %");
                            l.setHorizontalTextPosition(SwingConstants.RIGHT);
                            l.setHorizontalAlignment(SwingConstants.RIGHT);
                        }

                        return l;
                    }
                };

            final JTable t = tblIC;
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
    }

    /**
     * DOCUMENT ME!
     *
     * @param   args  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static void main(final String[] args) throws Exception {
        LAFManager.getManager().changeLookAndFeel("Plastic 3D");
        Log4JQuickConfig.configure4LumbermillOnLocalhost();
        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    try {
                        final WorldstatesAggregationRenderer r = new WorldstatesAggregationRenderer();
                        r.cidsBeans = new ArrayList<CidsBean>(4);

                        CidsBean iccbean = new ICCClass();
                        iccbean.setProperty(
                            "actualaccessinfo",
                            "{   \"casualties\": {     \"displayName\": \"Casualties\",      \"iconResource\": \"flower_16.png\",      \"noOfDead\": {       \"displayName\": \"Number of dead\",        \"iconResource\": \"flower_dead_16.png\",        \"value\": \"138\",     \"unit\":\"People\"     },      \"noOfInjured\": {       \"displayName\": \"Number of injured\",        \"iconResource\": \"flower_injured_16.png\",        \"value\": \"1515\",     \"unit\":\"People\"     },      \"noOfHomeless\": {       \"displayName\": \"Number of homeless\",        \"iconResource\": \"flower_homeless_16.png\",        \"value\": \"13953\",     \"unit\":\"People\"     }   },    \"cost\": {     \"directDamageCost\": {       \"displayName\": \"Direct damage cost\",        \"iconResource\": \"dollar_direct_16.png\",        \"value\": \"33547532\",     \"unit\":\"Dollar\"     },      \"displayName\": \"Economic cost\",      \"iconResource\": \"dollar_16.png\",      \"indirectDamageCost\": {       \"displayName\": \"Indirect damage cost\",        \"iconResource\": \"dollar_indirect_16.png\",        \"value\": \"65753689\",     \"unit\":\"Dollar\"     },      \"restorationCost\": {       \"displayName\": \"Direct restoration cost\",        \"iconResource\": \"dollar_restoration_16.png\",        \"value\": \"123657772\",     \"unit\":\"Dollar\"     }   },    \"damagedBuildings\": {     \"displayName\": \"Damaged buildings\",      \"iconResource\": \"home_16.png\",      \"lostBuildings\": {       \"displayName\": \"Lost buildings\",        \"iconResource\": \"home_lost_16.png\",        \"value\": \"241\",     \"unit\":\"Buildings\"     },      \"unsafeBuildings\": {       \"displayName\": \"Unsafe buildings\",        \"iconResource\": \"home_unsafe_16.png\",        \"value\": \"7350\",     \"unit\":\"Buildings\"     }   },    \"damagedInfrastructure\": {     \"damagedRoadSegments\": {       \"displayName\": \"Number of damaged road segments\",        \"iconResource\": \"road_damaged_16.png\",        \"value\": \"1567\",     \"unit\":\"Road seqments\"     },      \"displayName\": \"Damaged Infrastructure\",      \"iconResource\": \"road_16.png\"   },    \"evacuationCost\": {     \"displayName\": \"Evacuation cost\",      \"iconResource\": \"money_evac_16.png\",      \"totalEvacuationCost\": {       \"displayName\": \"Total evacuation cost\",        \"iconResource\": \"money_total_evac_16.png\",        \"value\": \"18067094\",     \"unit\":\"Dollar\"     }   } } ");
                        iccbean.setProperty("name", "Indicators");
                        CidsBean b = new WSClass();
                        b.addCollectionElement("iccdata", iccbean);
                        r.cidsBeans.add(b);

                        iccbean = new ICCClass();
                        iccbean.setProperty(
                            "actualaccessinfo",
                            "{   \"casualties\": {     \"displayName\": \"Casualties\",      \"iconResource\": \"flower_16.png\",      \"noOfDead\": {       \"displayName\": \"Number of dead\",        \"iconResource\": \"flower_dead_16.png\",        \"value\": \"1\",     \"unit\":\"People\"     },      \"noOfInjured\": {       \"displayName\": \"Number of injured\",        \"iconResource\": \"flower_injured_16.png\",        \"value\": \"54\",     \"unit\":\"People\"     },      \"noOfHomeless\": {       \"displayName\": \"Number of homeless\",        \"iconResource\": \"flower_homeless_16.png\",        \"value\": \"8434\",     \"unit\":\"People\"     }   },    \"cost\": {     \"directDamageCost\": {       \"displayName\": \"Direct damage cost\",        \"iconResource\": \"dollar_direct_16.png\",        \"value\": \"22547532\",     \"unit\":\"Dollar\"     },      \"displayName\": \"Economic cost\",      \"iconResource\": \"dollar_16.png\",      \"indirectDamageCost\": {       \"displayName\": \"Indirect damage cost\",        \"iconResource\": \"dollar_indirect_16.png\",        \"value\": \"43753689\",     \"unit\":\"Dollar\"     },      \"restorationCost\": {       \"displayName\": \"Direct restoration cost\",        \"iconResource\": \"dollar_restoration_16.png\",        \"value\": \"83657772\",     \"unit\":\"Dollar\"     }   },    \"damagedBuildings\": {     \"displayName\": \"Damaged buildings\",      \"iconResource\": \"home_16.png\",      \"lostBuildings\": {       \"displayName\": \"Lost buildings\",        \"iconResource\": \"home_lost_16.png\",        \"value\": \"178\",     \"unit\":\"Buildings\"     },      \"unsafeBuildings\": {       \"displayName\": \"Unsafe buildings\",        \"iconResource\": \"home_unsafe_16.png\",        \"value\": \"449\",     \"unit\":\"Buildings\"     }   },    \"damagedInfrastructure\": {     \"damagedRoadSegments\": {       \"displayName\": \"Number of damaged road segments\",        \"iconResource\": \"road_damaged_16.png\",        \"value\": \"1287\",     \"unit\":\"Road seqments\"     },      \"displayName\": \"Damaged Infrastructure\",      \"iconResource\": \"road_16.png\"   },    \"evacuationCost\": {     \"displayName\": \"Evacuation cost\",      \"iconResource\": \"money_evac_16.png\",      \"totalEvacuationCost\": {       \"displayName\": \"Total evacuation cost\",        \"iconResource\": \"money_total_evac_16.png\",        \"value\": \"25067094\",     \"unit\":\"Dollar\"     }   } } ");
                        iccbean.setProperty("name", "Indicators");
                        b = new WSClass();
                        b.addCollectionElement("iccdata", iccbean);
                        r.cidsBeans.add(b);

                        iccbean = new ICCClass();
                        iccbean.setProperty(
                            "actualaccessinfo",
                            "{   \"casualties\": {     \"displayName\": \"Casualties\",      \"iconResource\": \"flower_16.png\",      \"noOfDead\": {       \"displayName\": \"Number of dead\",        \"iconResource\": \"flower_dead_16.png\",        \"value\": \"189\",     \"unit\":\"People\"     },      \"noOfInjured\": {       \"displayName\": \"Number of injured\",        \"iconResource\": \"flower_injured_16.png\",        \"value\": \"2840\",     \"unit\":\"People\"     },      \"noOfHomeless\": {       \"displayName\": \"Number of homeless\",        \"iconResource\": \"flower_homeless_16.png\",        \"value\": \"10416\",     \"unit\":\"People\"     }   },    \"cost\": {     \"directDamageCost\": {       \"displayName\": \"Direct damage cost\",        \"iconResource\": \"dollar_direct_16.png\",        \"value\": \"32547532\",     \"unit\":\"Dollar\"     },      \"displayName\": \"Economic cost\",      \"iconResource\": \"dollar_16.png\",      \"indirectDamageCost\": {       \"displayName\": \"Indirect damage cost\",        \"iconResource\": \"dollar_indirect_16.png\",        \"value\": \"78453689\",     \"unit\":\"Dollar\"     },      \"restorationCost\": {       \"displayName\": \"Direct restoration cost\",        \"iconResource\": \"dollar_restoration_16.png\",        \"value\": \"113657772\",     \"unit\":\"Dollar\"     }   },    \"damagedBuildings\": {     \"displayName\": \"Damaged buildings\",      \"iconResource\": \"home_16.png\",      \"lostBuildings\": {       \"displayName\": \"Lost buildings\",        \"iconResource\": \"home_lost_16.png\",        \"value\": \"251\",     \"unit\":\"Buildings\"     },      \"unsafeBuildings\": {       \"displayName\": \"Unsafe buildings\",        \"iconResource\": \"home_unsafe_16.png\",        \"value\": \"637\",     \"unit\":\"Buildings\"     }   },    \"damagedInfrastructure\": {     \"damagedRoadSegments\": {       \"displayName\": \"Number of damaged road segments\",        \"iconResource\": \"road_damaged_16.png\",        \"value\": \"1416\",     \"unit\":\"Road seqments\"     },      \"displayName\": \"Damaged Infrastructure\",      \"iconResource\": \"road_16.png\"   },    \"evacuationCost\": {     \"displayName\": \"Evacuation cost\",      \"iconResource\": \"money_evac_16.png\",      \"totalEvacuationCost\": {       \"displayName\": \"Total evacuation cost\",        \"iconResource\": \"money_total_evac_16.png\",        \"value\": \"13067094\",     \"unit\":\"Dollar\"     }   } } ");
                        iccbean.setProperty("name", "Indicators");
                        b = new WSClass();
                        b.addCollectionElement("iccdata", iccbean);
                        r.cidsBeans.add(b);

                        iccbean = new ICCClass();
                        iccbean.setProperty(
                            "actualaccessinfo",
                            "{   \"casualties\": {     \"displayName\": \"Casualties\",      \"iconResource\": \"flower_16.png\",      \"noOfDead\": {       \"displayName\": \"Number of dead\",        \"iconResource\": \"flower_dead_16.png\",        \"value\": \"15\",     \"unit\":\"People\"     },      \"noOfInjured\": {       \"displayName\": \"Number of injured\",        \"iconResource\": \"flower_injured_16.png\",        \"value\": \"164\",     \"unit\":\"People\"     },      \"noOfHomeless\": {       \"displayName\": \"Number of homeless\",        \"iconResource\": \"flower_homeless_16.png\",        \"value\": \"8434\",     \"unit\":\"People\"     }   },    \"cost\": {     \"directDamageCost\": {       \"displayName\": \"Direct damage cost\",        \"iconResource\": \"dollar_direct_16.png\",        \"value\": \"22547532\",     \"unit\":\"Dollar\"     },      \"displayName\": \"Economic cost\",      \"iconResource\": \"dollar_16.png\",      \"indirectDamageCost\": {       \"displayName\": \"Indirect damage cost\",        \"iconResource\": \"dollar_indirect_16.png\",        \"value\": \"58453689\",     \"unit\":\"Dollar\"     },      \"restorationCost\": {       \"displayName\": \"Direct restoration cost\",        \"iconResource\": \"dollar_restoration_16.png\",        \"value\": \"83657772\",     \"unit\":\"Dollar\"     }   },    \"damagedBuildings\": {     \"displayName\": \"Damaged buildings\",      \"iconResource\": \"home_16.png\",      \"lostBuildings\": {       \"displayName\": \"Lost buildings\",        \"iconResource\": \"home_lost_16.png\",        \"value\": \"178\",     \"unit\":\"Buildings\"     },      \"unsafeBuildings\": {       \"displayName\": \"Unsafe buildings\",        \"iconResource\": \"home_unsafe_16.png\",        \"value\": \"449\",     \"unit\":\"Buildings\"     }   },    \"damagedInfrastructure\": {     \"damagedRoadSegments\": {       \"displayName\": \"Number of damaged road segments\",        \"iconResource\": \"road_damaged_16.png\",        \"value\": \"1287\",     \"unit\":\"Road seqments\"     },      \"displayName\": \"Damaged Infrastructure\",      \"iconResource\": \"road_16.png\"   },    \"evacuationCost\": {     \"displayName\": \"Evacuation cost\",      \"iconResource\": \"money_evac_16.png\",      \"totalEvacuationCost\": {       \"displayName\": \"Total evacuation cost\",        \"iconResource\": \"money_total_evac_16.png\",        \"value\": \"18067094\",     \"unit\":\"Dollar\"     }   } } ");
                        iccbean.setProperty("name", "Indicators");
                        b = new WSClass();
                        b.addCollectionElement("iccdata", iccbean);
                        r.cidsBeans.add(b);

                        r.init();
                        final JFrame f = new JFrame("test");
                        f.addWindowListener(new WindowAdapter() {

                                @Override
                                public void windowClosing(final WindowEvent e) {
                                    r.dispose();
                                    System.exit(0);
                                }
                            });
                        f.setLayout(new BorderLayout(5, 5));
                        f.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                        f.add(r);
                        f.pack();
                        f.setSize(1200, 1024);
                        f.setVisible(true);
                        f.toFront();
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                }
            });
    }

    @Override
    public void dispose() {
        System.out.println("dispose");
        final Preferences p = Preferences.userNodeForPackage(WorldstatesAggregationRenderer.class);
        try {
            p.clear();
            p.put("strategies", m.writeValueAsString(singleColumnModel.strategies));
            final String s = m.writeValueAsString(singleColumnModelCritFunc.getCritFuncs());
            System.out.println(s);
            p.put("critfuncs", compressString(s));
        } catch (final Exception ex) {
            LOG.error("cannot save prefs", ex);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   s  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    private String compressString(final String s) throws Exception {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final DeflaterOutputStream dos = new DeflaterOutputStream(baos);
        dos.write(s.getBytes("ISO-8859-1"));
        dos.close();

        return baos.toString("ISO-8859-1");
    }

    /**
     * DOCUMENT ME!
     *
     * @param   s  b DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    private String decompressString(final String s) throws Exception {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final InflaterOutputStream ios = new InflaterOutputStream(baos);
        ios.write(s.getBytes("ISO-8859-1"));
        ios.close();

        return baos.toString("ISO-8859-1");
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  RuntimeException  DOCUMENT ME!
     */
    private SingleColumnModel loadStrategyModel() {
        try {
            final Preferences p = Preferences.userNodeForPackage(WorldstatesAggregationRenderer.class);
            final List<Strategy> strategies = m.readValue(p.get("strategies", "[]"),
                    new TypeReference<List<Strategy>>() {
                    });

            return new SingleColumnModel(strategies);
        } catch (IOException iOException) {
            throw new RuntimeException("cannot load strategies", iOException);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  RuntimeException  DOCUMENT ME!
     */
    private SingleColumnModelCritFunc loadCritFuncModel() {
        try {
            final Preferences p = Preferences.userNodeForPackage(WorldstatesAggregationRenderer.class);
            String s = "[]";
            try {
                s = decompressString(p.get("critfuncs", ""));
                if (s.isEmpty()) {
                    s = "[]";
                }
            } catch (Exception e) {
            }
            final List<CritFunc> strategies = m.readValue(s,
                    new TypeReference<List<CritFunc>>() {
                    });

            return new SingleColumnModelCritFunc(strategies);
        } catch (Exception iOException) {
            throw new RuntimeException("cannot load critfuncs", iOException);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   criteria     DOCUMENT ME!
     * @param   displayName  DOCUMENT ME!
     * @param   worldstate   DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private double getValue(final boolean criteria, final String displayName, final CidsBean worldstate) {
        try {
            final List<CidsBean> icclist = worldstate.getBeanCollectionProperty("iccdata");
            CidsBean iccbean = null;
            for (final CidsBean icc : icclist) {
                if ("Indicators".equalsIgnoreCase((String)icc.getProperty("name"))) {
                    iccbean = icc;
                    break;
                }
            }
            final String json = (String)iccbean.getProperty("actualaccessinfo");
            ICCData icc = m.readValue(json, ICCData.class);
            if (criteria) {
                icc = calcCritData(icc, (CritFunc)cboCritFuncCrit.getSelectedItem());
            }
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

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private final class TabChangedL implements ChangeListener {

        //~ Instance fields ----------------------------------------------------

        Thread t;

        //~ Methods ------------------------------------------------------------

        @Override
        public void stateChanged(final ChangeEvent e) {
            try {
                final int selTab = jTabbedPane2.getSelectedIndex();
                if (selTab == 2) {
                    final DefaultComboBoxModel<CritFunc> model = new DefaultComboBoxModel<CritFunc>();
                    for (final CritFunc func : singleColumnModelCritFunc.funcs) {
                        model.addElement(func);
                    }
                    cboCritFuncCrit.setRenderer(new DefaultListCellRenderer() {

                            @Override
                            public Component getListCellRendererComponent(final JList<?> list,
                                    final Object value,
                                    final int index,
                                    final boolean isSelected,
                                    final boolean cellHasFocus) {
                                final JLabel l = (JLabel)super.getListCellRendererComponent(
                                        list,
                                        value,
                                        index,
                                        isSelected,
                                        cellHasFocus);
                                l.setText(((CritFunc)value).getName());

                                return l;
                            }
                        });
                    cboCritFuncCrit.addItemListener(new ItemListener() {

                            @Override
                            public void itemStateChanged(final ItemEvent e) {
                                if (e.getStateChange() == ItemEvent.SELECTED) {
                                    e.getItem();
                                    try {
                                        tbpCrit.removeAll();
                                        initTable(true);
                                        initAnalysisGraph(true);
                                        if (t != null) {
                                            t.interrupt();
                                        }
                                        t = new Thread(new Runnable() {

                                                    @Override
                                                    public void run() {
                                                        initMultipleSpiderWebChart();
                                                    }
                                                });
                                        t.start();
                                    } catch (final Exception ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            }
                        });
                    cboCritFuncCrit.setModel(model);
                    cboCritFuncCrit.setSelectedIndex(0);
                    tbpCrit.removeAll();
                    initTable(true);
                    initAnalysisGraph(true);
                    if (t != null) {
                        t.interrupt();
                    }
                    t = new Thread(new Runnable() {

                                @Override
                                public void run() {
                                    initMultipleSpiderWebChart();
                                }
                            });
                    t.start();
                } else if (selTab == 3) {
                    initMCA();
                }
            } catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public static class ICCClass extends CidsBean {

        //~ Instance fields ----------------------------------------------------

        String actualaccessinfo;
        String name;

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param  name  DOCUMENT ME!
         */
        public void setName(final String name) {
            this.name = name;
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public String getName() {
            return name;
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public String getActualaccessinfo() {
            return actualaccessinfo;
        }

        /**
         * DOCUMENT ME!
         *
         * @param  actualaccessinfo  DOCUMENT ME!
         */
        public void setActualaccessinfo(final String actualaccessinfo) {
            this.actualaccessinfo = actualaccessinfo;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public static class WSClass extends CidsBean {

        //~ Static fields/initializers -----------------------------------------

        static AtomicInteger ai = new AtomicInteger(1);

        //~ Instance fields ----------------------------------------------------

        Collection<CidsBean> iccdata = new ArrayList<CidsBean>(1);
        final int id;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new WSClass object.
         */
        public WSClass() {
            id = ai.getAndIncrement();
        }

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public String getName() {
            return "WS" + id;
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public Collection<CidsBean> getIccdata() {
            return iccdata;
        }

        /**
         * DOCUMENT ME!
         *
         * @param  iccdata  DOCUMENT ME!
         */
        public void setIccdata(final Collection<CidsBean> iccdata) {
            this.iccdata = iccdata;
        }

        @Override
        public int hashCode() {
            return id;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static final class Rank {

        //~ Instance fields ----------------------------------------------------

        Double rank;
        CidsBean b;
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public static final class Strategy {

        //~ Instance fields ----------------------------------------------------

        String name;
        int lse;
        ICCData data;

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public String getName() {
            return name;
        }

        /**
         * DOCUMENT ME!
         *
         * @param  name  DOCUMENT ME!
         */
        public void setName(final String name) {
            this.name = name;
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public int getLse() {
            return lse;
        }

        /**
         * DOCUMENT ME!
         *
         * @param  lse  DOCUMENT ME!
         */
        public void setLse(final int lse) {
            this.lse = lse;
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public ICCData getData() {
            return data;
        }

        /**
         * DOCUMENT ME!
         *
         * @param  data  DOCUMENT ME!
         */
        public void setData(final ICCData data) {
            this.data = data;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public static final class CritFunc {

        //~ Instance fields ----------------------------------------------------

        String name;
        List<CritFuncBand> bands;

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public String getName() {
            return name;
        }

        /**
         * DOCUMENT ME!
         *
         * @param  name  DOCUMENT ME!
         */
        public void setName(final String name) {
            this.name = name;
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public List<CritFuncBand> getBands() {
            return bands;
        }

        /**
         * DOCUMENT ME!
         *
         * @param  bands  DOCUMENT ME!
         */
        public void setBands(final List<CritFuncBand> bands) {
            this.bands = bands;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public static final class CritFuncBand {

        //~ Instance fields ----------------------------------------------------

        String name;
        CriteriaGroup zeroGroup;
        CriteriaGroup hundredGroup;
        TreeSet<CriteriaGroup> groups;

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public String getName() {
            return name;
        }

        /**
         * DOCUMENT ME!
         *
         * @param  name  DOCUMENT ME!
         */
        public void setName(final String name) {
            this.name = name;
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public CriteriaGroup getZeroGroup() {
            return zeroGroup;
        }

        /**
         * DOCUMENT ME!
         *
         * @param  zeroGroup  DOCUMENT ME!
         */
        public void setZeroGroup(final CriteriaGroup zeroGroup) {
            this.zeroGroup = zeroGroup;
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public CriteriaGroup getHundredGroup() {
            return hundredGroup;
        }

        /**
         * DOCUMENT ME!
         *
         * @param  hundredGroup  DOCUMENT ME!
         */
        public void setHundredGroup(final CriteriaGroup hundredGroup) {
            this.hundredGroup = hundredGroup;
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public TreeSet<CriteriaGroup> getGroups() {
            return groups;
        }

        /**
         * DOCUMENT ME!
         *
         * @param  groups  DOCUMENT ME!
         */
        public void setGroups(final TreeSet<CriteriaGroup> groups) {
            this.groups = groups;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private final class SingleColumnModelCritFunc implements TableModel {

        //~ Instance fields ----------------------------------------------------

        Integer editingRow;
        List<CritFunc> funcs;
        private final EventListenerList listeners = new EventListenerList();

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new SingleColumnModel object.
         *
         * @param  funcs  strategies DOCUMENT ME!
         */
        public SingleColumnModelCritFunc(final List<CritFunc> funcs) {
            this.funcs = funcs;
            _critEditing = (editingRow != null);
        }

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        List<CritFunc> getCritFuncs() {
            for (final CritFunc cf : funcs) {
                for (final CritFuncBand b : cf.getBands()) {
                    final IndicatorBand ib = valueBands.get(b.name);
                    b.setGroups((TreeSet)ib.getGroups());
                }
            }

            return funcs;
        }

        /**
         * DOCUMENT ME!
         *
         * @param  row  DOCUMENT ME!
         */
        public void removeRow(final int row) {
            try {
                funcs.remove(row);
                fire(new TableModelEvent(this));
            } catch (Exception e) {
                // noop
            }
        }

        /**
         * DOCUMENT ME!
         *
         * @param  s  DOCUMENT ME!
         */
        public void addRow(final CritFunc s) {
            funcs.add(s);
            fire(new TableModelEvent(this));
        }

        @Override
        public int getRowCount() {
            return funcs.size();
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public Integer getEditingRow() {
            return editingRow;
        }

        /**
         * DOCUMENT ME!
         *
         * @param  rowIndex  DOCUMENT ME!
         */
        public void setEditingRow(final Integer rowIndex) {
            this.editingRow = rowIndex;
            _critEditing = (editingRow != null);
        }

        @Override
        public int getColumnCount() {
            return 1;
        }

        @Override
        public String getColumnName(final int columnIndex) {
            return "";
        }

        @Override
        public Class<?> getColumnClass(final int columnIndex) {
            return String.class;
        }

        @Override
        public boolean isCellEditable(final int rowIndex, final int columnIndex) {
            return (editingRow != null) && (editingRow == rowIndex);
        }

        @Override
        public Object getValueAt(final int rowIndex, final int columnIndex) {
            return funcs.get(rowIndex).name;
        }

        @Override
        public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
            funcs.get(rowIndex).name = (String)aValue;

            fire(new TableModelEvent(this, rowIndex));
        }

        /**
         * DOCUMENT ME!
         *
         * @param  e  DOCUMENT ME!
         */
        private void fire(final TableModelEvent e) {
            final TableModelListener[] l = listeners.getListeners(TableModelListener.class);
            for (final TableModelListener tl : l) {
                tl.tableChanged(e);
            }
        }

        @Override
        public void addTableModelListener(final TableModelListener l) {
            listeners.add(TableModelListener.class, l);
        }

        @Override
        public void removeTableModelListener(final TableModelListener l) {
            listeners.remove(TableModelListener.class, l);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static final class SingleColumnModel implements TableModel {

        //~ Instance fields ----------------------------------------------------

        Integer editingRow;
        List<Strategy> strategies;
        private final EventListenerList listeners = new EventListenerList();

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new SingleColumnModelCritFunc object.
         *
         * @param  strategies  DOCUMENT ME!
         */
        public SingleColumnModel(final List<Strategy> strategies) {
            this.strategies = strategies;
        }

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param  row  DOCUMENT ME!
         */
        public void removeRow(final int row) {
            try {
                strategies.remove(row);
                fire(new TableModelEvent(this));
            } catch (Exception e) {
                // noop
            }
        }

        /**
         * DOCUMENT ME!
         *
         * @param  s  DOCUMENT ME!
         */
        public void addRow(final Strategy s) {
            strategies.add(s);
            fire(new TableModelEvent(this));
        }

        @Override
        public int getRowCount() {
            return strategies.size();
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public Integer getEditingRow() {
            return editingRow;
        }

        /**
         * DOCUMENT ME!
         *
         * @param  rowIndex  DOCUMENT ME!
         */
        public void setEditingRow(final Integer rowIndex) {
            this.editingRow = rowIndex;
        }

        @Override
        public int getColumnCount() {
            return 1;
        }

        @Override
        public String getColumnName(final int columnIndex) {
            return "";
        }

        @Override
        public Class<?> getColumnClass(final int columnIndex) {
            return String.class;
        }

        @Override
        public boolean isCellEditable(final int rowIndex, final int columnIndex) {
            return (editingRow != null) && (editingRow == rowIndex);
        }

        @Override
        public Object getValueAt(final int rowIndex, final int columnIndex) {
            return strategies.get(rowIndex).name;
        }

        @Override
        public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
            strategies.get(rowIndex).name = (String)aValue;

            fire(new TableModelEvent(this, rowIndex));
        }

        /**
         * DOCUMENT ME!
         *
         * @param  e  DOCUMENT ME!
         */
        private void fire(final TableModelEvent e) {
            final TableModelListener[] l = listeners.getListeners(TableModelListener.class);
            for (final TableModelListener tl : l) {
                tl.tableChanged(e);
            }
        }

        @Override
        public void addTableModelListener(final TableModelListener l) {
            listeners.add(TableModelListener.class, l);
        }

        @Override
        public void removeTableModelListener(final TableModelListener l) {
            listeners.remove(TableModelListener.class, l);
        }
    }
}
