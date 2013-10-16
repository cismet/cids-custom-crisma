/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.crisma.worldstate.viewer;

import org.openide.util.NbBundle;

import de.cismet.cids.custom.crisma.WorldstateContainer;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   mscholl
 * @version  $Revision$, $Date$
 */
public class WorldstateCoreDetailMiniatureView extends MiniaturePanel implements WorldstateContainer {

    //~ Instance fields --------------------------------------------------------

    private transient CidsBean worldstate;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblWstDesc;
    private javax.swing.JLabel lblWstDescValue;
    private javax.swing.JLabel lblWstName;
    private javax.swing.JLabel lblWstNameValue;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WorldstateCoreDetailMiniatureView.
     */
    public WorldstateCoreDetailMiniatureView() {
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

        lblWstName = new javax.swing.JLabel();
        lblWstNameValue = new javax.swing.JLabel();
        lblWstDesc = new javax.swing.JLabel();
        lblWstDescValue = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        lblWstName.setText(NbBundle.getMessage(
                WorldstateCoreDetailMiniatureView.class,
                "WorldstateCoreDetailMiniatureView.lblWstName.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(lblWstName, gridBagConstraints);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${worldstate.name}"),
                lblWstNameValue,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(lblWstNameValue, gridBagConstraints);

        lblWstDesc.setText(NbBundle.getMessage(
                WorldstateCoreDetailMiniatureView.class,
                "WorldstateCoreDetailMiniatureView.lblWstDesc.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 3, 5);
        add(lblWstDesc, gridBagConstraints);

        lblWstDescValue.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${worldstate.description}"),
                lblWstDescValue,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 5, 5);
        add(lblWstDescValue, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    @Override
    public CidsBean getWorldstate() {
        return worldstate;
    }

    @Override
    public void setWorldstate(final CidsBean worldstate) {
        this.worldstate = worldstate;
        bindingGroup.unbind();
        bindingGroup.bind();
    }
}
