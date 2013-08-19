/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.crisma;

import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;

import java.util.List;

import de.cismet.cismap.commons.gui.MappingComponent;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  $Revision$, $Date$
 */
public final class MapSyncUtil {

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  mcs  DOCUMENT ME!
     */
    public static void sync(final List<MappingComponent> mcs) {
        final SyncL s = new SyncL(mcs);
        for (final MappingComponent mc : mcs) {
            mc.addInputListener("sync", s);
            mc.setInteractionMode("sync");
        }
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static final class SyncL extends PBasicInputEventHandler {

        //~ Instance fields ----------------------------------------------------

        private final List<MappingComponent> mcs;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new SyncL object.
         *
         * @param  mcs  DOCUMENT ME!
         */
        public SyncL(final List<MappingComponent> mcs) {
            this.mcs = mcs;
        }

        //~ Methods ------------------------------------------------------------

//        @Override
//        public void processEvent(final PInputEvent event, final int type) {
//            for (final MappingComponent mc : mcs) {
//                final PBasicInputEventHandler p = (PBasicInputEventHandler)mc.getInputListener("wfsclick");
//                p.processEvent(event, type);
//            }
//        }
//
        @Override
        public void mouseClicked(final PInputEvent event) {
            for (final MappingComponent mc : mcs) {
                final PBasicInputEventHandler p = (PBasicInputEventHandler)mc.getInputListener("wfsclick");
                p.mouseClicked(event);
            }
        }
//
//        @Override
//        public void mouseDragged(final PInputEvent e) {
//            for (final MappingComponent mc : mcs) {
//                final PBasicInputEventHandler p = (PBasicInputEventHandler)mc.getInputListener("wfsclick");
//                p.mouseDragged(e);
//            }
//        }
//
//        @Override
//        public void mouseWheelRotated(final PInputEvent event) {
//            for (final MappingComponent mc : mcs) {
//                final PBasicInputEventHandler p = (PBasicInputEventHandler)mc.getInputListener("wfsclick");
//                p.mouseWheelRotated(event);
//            }
//        }
//
//        @Override
//        public void mousePressed(final PInputEvent event) {
//            for (final MappingComponent mc : mcs) {
//                final PBasicInputEventHandler p = (PBasicInputEventHandler)mc.getInputListener("wfsclick");
//                p.mousePressed(event);
//            }
//        }
//
//        @Override
//        public void mouseEntered(final PInputEvent event) {
//            for (final MappingComponent mc : mcs) {
//                final PBasicInputEventHandler p = (PBasicInputEventHandler)mc.getInputListener("wfsclick");
//                p.mouseEntered(event);
//            }
//        }
//
//        @Override
//        public void mouseExited(final PInputEvent event) {
//            for (final MappingComponent mc : mcs) {
//                final PBasicInputEventHandler p = (PBasicInputEventHandler)mc.getInputListener("wfsclick");
//                p.mouseExited(event);
//            }
//        }
//
//        @Override
//        public void mouseMoved(final PInputEvent event) {
//            for (final MappingComponent mc : mcs) {
//                final PBasicInputEventHandler p = (PBasicInputEventHandler)mc.getInputListener("wfsclick");
//                p.mouseMoved(event);
//            }
//        }
//
//        @Override
//        public void mouseReleased(final PInputEvent event) {
//            for (final MappingComponent mc : mcs) {
//                final PBasicInputEventHandler p = (PBasicInputEventHandler)mc.getInputListener("wfsclick");
//                p.mouseReleased(event);
//            }
//        }
//
//        @Override
//        public void mouseWheelRotatedByBlock(final PInputEvent event) {
//            for (final MappingComponent mc : mcs) {
//                final PBasicInputEventHandler p = (PBasicInputEventHandler)mc.getInputListener("wfsclick");
//                p.mouseWheelRotatedByBlock(event);
//            }
//        }
    }
}
