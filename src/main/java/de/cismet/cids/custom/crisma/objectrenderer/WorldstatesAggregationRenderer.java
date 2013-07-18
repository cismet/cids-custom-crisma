/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.crisma.objectrenderer;

import Sirius.navigator.ui.RequestsFullSizeComponent;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.log4j.Logger;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.SpiderWebPlot;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Paint;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import de.cismet.cids.custom.crisma.AbstractCidsBeanAggregationRenderer;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   mscholl
 * @version  $Revision$, $Date$
 */
public class WorldstatesAggregationRenderer extends AbstractCidsBeanAggregationRenderer
        implements RequestsFullSizeComponent {

    //~ Static fields/initializers ---------------------------------------------

    /** LOGGER. */
    private static final transient Logger LOG = Logger.getLogger(WorldstatesAggregationRenderer.class);

    //~ Instance fields --------------------------------------------------------

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables

    private int next = 0;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WorldstatesAggregationRenderer.
     */
    public WorldstatesAggregationRenderer() {
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
        jTabbedPane1 = new javax.swing.JTabbedPane();

        setLayout(new java.awt.BorderLayout());

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    } // </editor-fold>//GEN-END:initComponents

    @Override
    protected void init() {
        initMultipleBarCharts(0);
        initSingleBarChart(1);
        initSpiderWebChart(2);
        initLineChart(3);
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
}
