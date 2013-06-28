/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.crisma.worldstate;

import org.openide.util.NbBundle;

import javax.swing.JComponent;

/**
 * DOCUMENT ME!
 *
 * @author   mscholl
 * @version  $Revision$, $Date$
 */
public class CriticalInfrastructureDetailView extends AbstractDetailView {

    //~ Instance fields --------------------------------------------------------

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form CriticalInfrastructureDetailView.
     */
    public CriticalInfrastructureDetailView() {
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
        jLabel1 = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText(NbBundle.getMessage(
                CriticalInfrastructureDetailView.class,
                "CriticalInfrastructureDetailView.jLabel1.text")); // NOI18N
        add(jLabel1, java.awt.BorderLayout.CENTER);
    }                                                              // </editor-fold>//GEN-END:initComponents

    @Override
    public JComponent getView() {
        return this;
    }

    @Override
    public JComponent getMiniatureView() {
        return this;
    }

    @Override
    public String getId() {
        return getClass().getCanonicalName();
    }

    @Override
    public String getDisplayName() {
        return "Critical infrastructure";
    }
}
