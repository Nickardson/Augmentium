/*
    Rhino-Require is Public Domain
    <http://en.wikipedia.org/wiki/Public_Domain>

    The author or authors of this code dedicate any and all copyright interest
    in this code to the public domain. We make this dedication for the benefit
    of the public at large and to the detriment of our heirs and successors. We
    intend this dedication to be an overt act of relinquishment in perpetuity of
    all present and future rights to this code under copyright law.
 */

(function(global) {

    var require = global.require = function(id) { /*debug*///console.log('require('+id+')');
        if (typeof arguments[0] !== 'string') throw 'USAGE: require(moduleId)';

        var moduleContent = '',
            moduleUri;

        moduleUri = require.resolve(id);
        moduleContent = '';

        if (require.cache[moduleUri]) {
          return require.cache[moduleUri];
        }

        var file = _modclass.getResourceAsStream(moduleUri);
        try {
            var scanner = new java.util.Scanner(file).useDelimiter("\\Z");
            moduleContent = String( scanner.next() );
            scanner.close();
        }
        catch(e) {
            throw 'Unable to read file at: '+moduleUri+', '+e;
        }

        if (moduleContent) {
                try {
                    var f = new Function('require', 'exports', 'module', moduleContent),
                    exports = require.cache[moduleUri] || {},
                    module = { id: id, uri: moduleUri, exports: exports, global: global };

                    require._root.unshift(moduleUri);
                    f.call({}, require, exports, module);
                    require._root.shift();
                }
                catch(e) {
                    throw 'Unable to require source code from "' + moduleUri + '": ' + e.toSource();
                }

                exports = module.exports || exports;
                require.cache[moduleUri] = exports;
        }
        else {
            throw 'The requested module cannot be returned: no content for id: "' + id + '" in paths: ' + require.paths.join(', ');
        }

        return exports;
    };
    require._root = [''];
    require.paths = [];
    require.cache = {}; // cache module exports. Like: {id: exported}

    var SLASH = Packages.java.io.File.separator;

    /** Given a module id, try to find the path to the associated module.
     */
    require.resolve = function(id) {
        //return loadAsResource(id);
        return '/lib/' + id + '.js';
    };

/*
    function isResource(id) {
        java.lang.System.out.println("with: " + ModClass + " : " + ModClassLoader);
        java.lang.System.out.println("in: " + id + " " + String(ModClassLoader.getResourceAsStream(id)) + " : " + (ModClassLoader.getResourceAsStream(id)!=null));
        java.lang.System.out.println("in: " + id + " " + String(ModClassLoader.getResource(id)) + " : " + (ModClassLoader.getResource(id)!=null));
        //return ModClass.getResourceAsStream(id) != null;
        return ModClassLoader.getResource(id) != null;
    }

    function loadAsResource(id, isLib) {
        if (id[0] != '/') {
            id = '/' + id;
        }

        if ( isResource(id) ) { return id; }
        if ( isResource(id+'.js') ) { return id+'.js'; }

        // Try, prefixed with lib
        if (!isLib) {
            return loadAsResource('/lib' + id, true);
        }

    }*/
})(this);
