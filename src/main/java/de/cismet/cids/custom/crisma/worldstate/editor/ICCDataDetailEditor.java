/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.crisma.worldstate.editor;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  $Revision$, $Date$
 */
public final class ICCDataDetailEditor extends NotEditableEditor {

    //~ Methods ----------------------------------------------------------------

    @Override
    public String getId() {
        return "icc_data_editor";
    }

    @Override
    public String getDisplayName() {
        return "ICC Data (autoupdate on save)";
    }
}
