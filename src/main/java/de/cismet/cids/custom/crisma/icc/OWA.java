/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.crisma.icc;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.ScriptableObject;

import java.io.IOException;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  $Revision$, $Date$
 */
public final class OWA {

    //~ Instance fields --------------------------------------------------------

    private final ScriptableObject globalScope;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new OWA object.
     *
     * @throws  IOException  DOCUMENT ME!
     */
    public OWA() throws IOException {
        final String nsScript = StringUtils.join(
                IOUtils.readLines(OWA.class.getResourceAsStream("Namespace.js"), "UTF-8"),
                "\n");
        final String owaScript = StringUtils.join(
                IOUtils.readLines(OWA.class.getResourceAsStream("owa.js"), "UTF-8"),
                "\n");
        final Context ctx = Context.enter();

        globalScope = ctx.initStandardObjects();
        ctx.evaluateString(globalScope, "var window = {};", "window", 1, null);
        ctx.evaluateString(globalScope, nsScript, "Namespace", 1, null);
        ctx.evaluateString(globalScope, "var de = window.de;", "de", 1, null);
        ctx.evaluateString(globalScope, owaScript, "owa", 1, null);
        ctx.evaluateString(globalScope, "owa = de.cismet.crisma.OWA;", "owavar", 1, null);
        Context.exit();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param   criteria    DOCUMENT ME!
     * @param   weights     DOCUMENT ME!
     * @param   importance  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  RuntimeException  DOCUMENT ME!
     */
    public double getAggregateLS(final double[] criteria, final double[] weights, final double[] importance) {
        try {
            final Context ctx = Context.enter();
            globalScope.put("x", globalScope, criteria);
            globalScope.put("y", globalScope, weights);

            if (importance == null) {
                ctx.evaluateString(globalScope, "result = owa.aggregateLS(x, y)", "getAggregateLS", 1, null);
            } else {
                // this has to be done (Context.toObject()) because importance is compared to another value
                globalScope.put("z", globalScope, Context.toObject(importance, globalScope));
                ctx.evaluateString(globalScope, "result = owa.aggregateLS(x, y, z)", "getAggregateLS", 1, null);
            }

            return (double)globalScope.get("result", globalScope);
        } catch (final Exception e) {
            throw new RuntimeException("could not calculate orness", e);
        } finally {
            globalScope.delete("result");
            globalScope.delete("x");
            globalScope.delete("y");
            if (importance != null) {
                globalScope.delete("z");
            }
            Context.exit();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   weights  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  RuntimeException  DOCUMENT ME!
     */
    public double getOrness(final double[] weights) {
        try {
            final Context ctx = Context.enter();
            globalScope.put("x", globalScope, weights);
            ctx.evaluateString(globalScope, "result = owa.orness(x)", "getOrness", 1, null);

            return (double)globalScope.get("result", globalScope);
        } catch (final Exception e) {
            throw new RuntimeException("could not calculate orness", e);
        } finally {
            globalScope.delete("result");
            globalScope.delete("x");
            Context.exit();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   orness  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public double getAndness(final double orness) {
        return 1 - orness;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   weights  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public double getAndness(final double[] weights) {
        return getAndness(getOrness(weights));
    }

    /**
     * DOCUMENT ME!
     *
     * @param   weights  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  RuntimeException  DOCUMENT ME!
     */
    public double getDispersion(final double[] weights) {
        try {
            final Context ctx = Context.enter();
            globalScope.put("x", globalScope, weights);
            ctx.evaluateString(globalScope, "result = owa.dispersion(x)", "getDispersion", 1, null);

            return (double)globalScope.get("result", globalScope);
        } catch (final Exception e) {
            throw new RuntimeException("could not calculate dispersion", e);
        } finally {
            globalScope.delete("result");
            globalScope.delete("x");
            Context.exit();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   criteriaCount  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  RuntimeException  DOCUMENT ME!
     */
    public double[] getHLSWeights(final int criteriaCount) {
        try {
            final Context ctx = Context.enter();
            globalScope.put("x", globalScope, criteriaCount);
            ctx.evaluateString(globalScope, "result = owa.hLSWeights(x)", "getHLSWeights", 1, null);

            final NativeArray na = (NativeArray)globalScope.get("result", globalScope);

            return getDoubleArray(na);
        } catch (final Exception e) {
            throw new RuntimeException("could not calculate hls weights", e);
        } finally {
            globalScope.delete("result");
            globalScope.delete("x");
            Context.exit();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   criteriaCount  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  RuntimeException  DOCUMENT ME!
     */
    public double[] getLLSWeights(final int criteriaCount) {
        try {
            final Context ctx = Context.enter();
            globalScope.put("x", globalScope, criteriaCount);
            ctx.evaluateString(globalScope, "result = owa.lLSWeights(x)", "getLLSWeights", 1, null);

            final NativeArray na = (NativeArray)globalScope.get("result", globalScope);

            return getDoubleArray(na);
        } catch (final Exception e) {
            throw new RuntimeException("could not calculate lls weights", e);
        } finally {
            globalScope.delete("result");
            globalScope.delete("x");
            Context.exit();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   criteriaCount  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  RuntimeException  DOCUMENT ME!
     */
    public double[] getMeanWeights(final int criteriaCount) {
        try {
            final Context ctx = Context.enter();
            globalScope.put("x", globalScope, criteriaCount);
            ctx.evaluateString(globalScope, "result = owa.meanWeights(x)", "getMeanWeights", 1, null);

            final NativeArray na = (NativeArray)globalScope.get("result", globalScope);

            return getDoubleArray(na);
        } catch (final Exception e) {
            throw new RuntimeException("could not calculate mean weights", e);
        } finally {
            globalScope.delete("result");
            globalScope.delete("x");
            Context.exit();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   criteria  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  RuntimeException  DOCUMENT ME!
     */
    public double[] getOrderedArgs(final double[] criteria) {
        try {
            final Context ctx = Context.enter();
            globalScope.put("x", globalScope, criteria);
            ctx.evaluateString(globalScope, "result = owa.orderedArgs(x)", "getOrderedArgs", 1, null);

            final NativeArray na = (NativeArray)globalScope.get("result", globalScope);

            return getDoubleArray(na);
        } catch (final Exception e) {
            throw new RuntimeException("could not calculate ordered args vector", e);
        } finally {
            globalScope.delete("result");
            globalScope.delete("x");
            Context.exit();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   na  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private double[] getDoubleArray(final NativeArray na) {
        final double[] result = new double[na.size()];
        for (int i = 0; i < result.length; ++i) {
            result[i] = (double)na.get(i);
        }

        return result;
    }
}
