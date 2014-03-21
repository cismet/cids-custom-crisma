"use strict";

(function(de, undefined) {
    (function(cismet, undefined) {
        (function(Namespace, undefined) {
            var canExecute = function(obj, method) {
                return obj[method] && typeof obj[method] === 'function';
            };

            Namespace.create = function(ns) {
                // NOTE: how can these errors be caught if used in anonymous self-executing functions?
                if (!ns.length || !ns.length > 0) {
                    throw {
                        name: "IllegalArgumentException",
                        message: "non-string or empty string not supported"
                    };
                }

                if (canExecute(ns, 'split')) {
                    var nss = ns.split('.');
                    var curNs = window;

                    for (var i = 0; i < nss.length; ++i) {
                        if (!curNs[nss[i]]) {
                            curNs[nss[i]] = {};
                        }

                        curNs = curNs[nss[i]];
                    }

                    return curNs;
                } else {
                    throw {
                        name: "IllegalArgumentException",
                        message: "The given object is not splittable"
                    };
                }

            };
        })(window.de.cismet.Namespace = window.de.cismet.Namespace || {});
    })(window.de.cismet = window.de.cismet || {});
})(window.de = window.de || {});
