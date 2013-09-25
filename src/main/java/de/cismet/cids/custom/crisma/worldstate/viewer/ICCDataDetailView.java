/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.crisma.worldstate.viewer;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import org.openide.util.NbBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.GridBagLayout;

import java.lang.reflect.Field;

import java.text.NumberFormat;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;

import de.cismet.cids.custom.crisma.BorderPanel;
import de.cismet.cids.custom.crisma.icc.Common;
import de.cismet.cids.custom.crisma.icc.ICCData;
import de.cismet.cids.custom.crisma.icc.Value;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   mscholl
 * @version  $Revision$, $Date$
 */
public class ICCDataDetailView extends AbstractDetailView {

    //~ Static fields/initializers ---------------------------------------------

    /** LOGGER. */
    private static final transient Logger LOG = LoggerFactory.getLogger(ICCDataDetailView.class);

    //~ Instance fields --------------------------------------------------------

    private final transient ICCDataDetailMiniatureView mView = new ICCDataDetailMiniatureView();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form ICCDataDetailView.
     */
    public ICCDataDetailView() {
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
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();

        setLayout(new java.awt.GridBagLayout());

        jLabel1.setText(NbBundle.getMessage(ICCDataDetailView.class, "ICCDataDetailView.jLabel1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(jLabel1, gridBagConstraints);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${worldstate.iccdata.name}"),
                jLabel2,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(jLabel2, gridBagConstraints);

        jLabel3.setText(NbBundle.getMessage(ICCDataDetailView.class, "ICCDataDetailView.jLabel3.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(jLabel3, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${worldstate.iccdata.description}"),
                jLabel4,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(jLabel4, gridBagConstraints);

        jPanel1.setLayout(new java.awt.BorderLayout());
        jPanel1.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(jPanel1, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents
    @Override
    public JComponent getView() {
        return this;
    }

    @Override
    public JComponent getMiniatureView() {
        return mView;
    }

    @Override
    public void setWorldstate(final CidsBean worldstate) {
        super.setWorldstate(worldstate);
        mView.setWorldstate(worldstate);
        init();
        bindingGroup.unbind();
        bindingGroup.bind();
    }

    @Override
    public String getId() {
        return "iccdata_view";
    }

    @Override
    public String getDisplayName() {
        return "ICC Data";
    }

    /**
     * DOCUMENT ME!
     */
    private void init() {
        final BorderPanel bp = new BorderPanel();
        bp.setTitle("Data");
        this.remove(jPanel1);
        bp.setContentPane(jPanel1);
        java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(bp, gridBagConstraints);
        final String json = (String)getWorldstate().getProperty("iccdata.actualaccessinfo");
        final ObjectMapper m = new ObjectMapper(new JsonFactory());
        try {
            final ICCData icc = m.readValue(json, ICCData.class);

            final Field[] fields = icc.getClass().getDeclaredFields();
            for (final Field field : fields) {
                final JPanel p = new JPanel();
                field.setAccessible(true);
                final Object o = field.get(icc);
                final Field[] fields2 = o.getClass().getDeclaredFields();
                final GridBagLayout g = new GridBagLayout();
                p.setLayout(g);
                final String displayName = ((Common)o).getDisplayName();
                jTabbedPane1.add(displayName, p);
                final DefaultPieDataset dataset = new DefaultPieDataset();
                final DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();
                int i = 0;
                final NumberFormat nf = NumberFormat.getInstance();

                for (; i < fields2.length; ++i) {
                    fields2[i].setAccessible(true);
                    final Value val = (Value)fields2[i].get(o);
                    final String catName = val.getDisplayName();
                    final Double value = Double.parseDouble(val.getValue());

                    dataset.setValue(catName, value);
                    dataset2.addValue(value, displayName, catName);

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = i;
                    gridBagConstraints.gridwidth = 1;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
                    gridBagConstraints.weightx = 0.0;
                    gridBagConstraints.weighty = 0.0;
                    gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);

                    final JLabel l = new JLabel(catName + ":");
                    p.add(l, gridBagConstraints);

                    gridBagConstraints.gridx = 1;
                    gridBagConstraints.gridy = i;
                    gridBagConstraints.gridwidth = 1;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
                    gridBagConstraints.weightx = 0.0;
                    gridBagConstraints.weighty = 0.0;
                    gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);

                    final JLabel l1 = new JLabel(nf.format(value) + " " + val.getUnit());
                    l1.setHorizontalAlignment(JLabel.RIGHT);
                    p.add(l1, gridBagConstraints);
                }
                gridBagConstraints.gridx = 2;
                gridBagConstraints.gridy = 0;
                gridBagConstraints.gridwidth = 1;
                gridBagConstraints.gridheight = i + 1;
                gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
                gridBagConstraints.weightx = 0.0;
                gridBagConstraints.weighty = 1.0;
                gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
                final JSeparator sep = new JSeparator(JSeparator.VERTICAL);
                p.add(sep, gridBagConstraints);

                gridBagConstraints.gridx = 3;
                gridBagConstraints.gridy = 0;
                gridBagConstraints.gridwidth = 1;
                gridBagConstraints.gridheight = i + 1;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                gridBagConstraints.weightx = 1.0;
                gridBagConstraints.weighty = 1.0;
                gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);

                final JFreeChart chart = ChartFactory.createPieChart3D(null, dataset, true, true, false);
                final JFreeChart chart2 = ChartFactory.createBarChart3D(
                        null,
                        null,
                        null,
                        dataset2,
                        PlotOrientation.HORIZONTAL,
                        true,
                        true,
                        false);
                final JTabbedPane tp = new JTabbedPane();
                tp.add("Pie chart", new ChartPanel(chart, true, false, false, false, true));
                tp.add("Bar chart", new ChartPanel(chart2, true, false, false, false, true));
                tp.setSelectedIndex(0);
                p.add(tp, gridBagConstraints);
            }
        } catch (Exception ex) {
            LOG.error("cannot init icc data view", ex);
        }
    }
}
