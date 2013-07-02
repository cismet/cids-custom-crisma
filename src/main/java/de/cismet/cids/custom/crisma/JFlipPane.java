/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.crisma;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import de.cismet.cids.custom.crisma.worldstate.viewer.CriticalInfrastructureDetailView;
import de.cismet.cids.custom.crisma.worldstate.viewer.EmergencyServicesDetailView;

import de.cismet.tools.gui.StaticSwingTools;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  $Revision$, $Date$
 */
public final class JFlipPane extends JPanel {

    //~ Instance fields --------------------------------------------------------

    private JComponent frontPanel;
    private JComponent backPanel;
    private int animationDuration;
    private boolean frontShowing;
    private boolean flipping;

    private transient BufferedImage combinedImage;
    private transient int currentStep;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new JFlipPane object.
     */
    public JFlipPane() {
        setLayout(new BorderLayout());
        frontShowing = true;
        flipping = false;
        animationDuration = 300;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public JComponent getFrontPanel() {
        return frontPanel;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  frontPanel  DOCUMENT ME!
     */
    public void setFrontPanel(final JComponent frontPanel) {
        this.frontPanel = frontPanel;

        if (frontShowing) {
            removeAll();
            add(frontPanel, BorderLayout.CENTER);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public JComponent getBackPanel() {
        return backPanel;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  backPanel  DOCUMENT ME!
     */
    public void setBackPanel(final JComponent backPanel) {
        this.backPanel = backPanel;

        if (!frontShowing) {
            removeAll();
            add(backPanel, BorderLayout.CENTER);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean isFrontShowing() {
        return frontShowing;
    }

    /**
     * DOCUMENT ME!
     */
    public void flip() {
        if (backPanel == null) {
            return;
        }

        final Dimension size = getSize();
        if (!isVisible() || (size.height == 0) || (size.width == 0)) {
            JFlipPane.this.removeAll();
            if (frontShowing) {
                JFlipPane.this.add(backPanel, BorderLayout.CENTER);
            } else {
                JFlipPane.this.add(frontPanel, BorderLayout.CENTER);
            }
            JFlipPane.this.invalidate();
            JFlipPane.this.validate();
            JFlipPane.this.repaint();

            frontShowing = !frontShowing;
            return;
        }

        flipping = true;

        if (frontShowing) {
            backPanel.setSize(size);
            backPanel.setPreferredSize(size);
            backPanel.setMaximumSize(size);
            backPanel.setMinimumSize(size);
            currentStep = 0;
        } else {
            frontPanel.setSize(size);
            frontPanel.setPreferredSize(size);
            frontPanel.setMaximumSize(size);
            frontPanel.setMinimumSize(size);
            currentStep = size.width;
        }

        final BufferedImage frontImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        final BufferedImage backImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        combinedImage = new BufferedImage(2 * size.width, size.height, BufferedImage.TYPE_INT_ARGB);

        doLayout(frontPanel);
        frontPanel.invalidate();
        frontPanel.validate();
        frontPanel.paint(frontImage.createGraphics());

        doLayout(backPanel);
        backPanel.invalidate();
        backPanel.validate();
        backPanel.paint(backImage.createGraphics());

        final Graphics2D g2d = combinedImage.createGraphics();
        g2d.drawImage(frontImage, 0, 0, null);
        g2d.drawImage(backImage, size.width, 0, null);
        // 30 fps
        final int frames = animationDuration / 33;
        final int stepWidth = size.width / frames;

        final Thread t = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        if (frontShowing) {
                            while (currentStep < size.width) {
                                try {
                                    EventQueue.invokeAndWait(new Runnable() {

                                            @Override
                                            public void run() {
                                                JFlipPane.this.repaint();
                                                StaticSwingTools.getParentFrame(JFlipPane.this).repaint();
                                                currentStep += stepWidth;
                                                if (currentStep > size.width) {
                                                    currentStep = size.width;
                                                }
                                                System.out.println(currentStep);
                                            }
                                        });
                                    Thread.sleep(33);
                                } catch (Exception ex) {
                                    System.out.println(ex);
                                }
                            }

                            flipping = false;
                            frontShowing = !frontShowing;
                            EventQueue.invokeLater(new Runnable() {

                                    @Override
                                    public void run() {
                                        JFlipPane.this.removeAll();
                                        JFlipPane.this.add(backPanel, BorderLayout.CENTER);
                                        JFlipPane.this.invalidate();
                                        JFlipPane.this.validate();
                                        JFlipPane.this.repaint();
                                    }
                                });
                        } else {
                            while (currentStep > 0) {
                                try {
                                    EventQueue.invokeAndWait(new Runnable() {

                                            @Override
                                            public void run() {
                                                JFlipPane.this.repaint();
                                                currentStep -= stepWidth;
                                                if (currentStep < 0) {
                                                    currentStep = 0;
                                                }
                                                System.out.println(currentStep);
                                            }
                                        });
                                    Thread.sleep(33);
                                } catch (Exception ex) {
                                    System.out.println(ex);
                                }
                            }

                            flipping = false;
                            frontShowing = !frontShowing;
                            EventQueue.invokeLater(new Runnable() {

                                    @Override
                                    public void run() {
                                        JFlipPane.this.removeAll();
                                        JFlipPane.this.add(frontPanel, BorderLayout.CENTER);
                                        JFlipPane.this.invalidate();
                                        JFlipPane.this.validate();
                                        JFlipPane.this.repaint();
                                    }
                                });
                        }
                    }
                });
        t.start();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  c  DOCUMENT ME!
     */
    private void doLayout(final Container c) {
        for (final Component comp : c.getComponents()) {
            if (comp instanceof Container) {
                doLayout((Container)comp);
            }
        }

        c.doLayout();
    }

    @Override
    public void paint(final Graphics g) {
        super.paint(g);

        if (flipping && isVisible()) {
            final int width = getSize().width;
            final int height = getSize().height;

            g.drawImage(combinedImage.getSubimage(currentStep, 0, width, height), 0, 0, this);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public int getAnimationDuration() {
        return animationDuration;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  animationDuration  DOCUMENT ME!
     */
    public void setAnimationDuration(final int animationDuration) {
        this.animationDuration = animationDuration;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  args  DOCUMENT ME!
     */
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    final JFrame frame = new JFrame();
                    frame.setLayout(new BorderLayout(5, 5));
                    final JPanel p1 = new CriticalInfrastructureDetailView();
                    p1.setBorder(new LineBorder(Color.BLACK, 5));
                    final JPanel p2 = new EmergencyServicesDetailView();
                    p2.setBorder(new LineBorder(Color.ORANGE, 5));
                    final JFlipPane flip = new JFlipPane();
                    flip.setFrontPanel(p1);
                    flip.setBackPanel(p2);
                    final JButton b = new JButton("flip");
                    b.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(final ActionEvent e) {
                                flip.flip();
                            }
                        });

                    frame.add(b, BorderLayout.NORTH);
                    frame.add(flip, BorderLayout.CENTER);
                    frame.pack();
                    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    frame.setVisible(true);
                }
            });
    }
}
