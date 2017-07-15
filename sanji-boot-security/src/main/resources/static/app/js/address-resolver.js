/**
 *
 * @authors summer_last (sunxzg@gmail.com)
 * @date    2016-11-21 09:07:42
 * @version $Id$
 */

function AR() {
    this.href = location.href;
}
AR.prototype = {
    constructor: AR,
    logger: function (msg) {
        //console.log(msg);
    },
    getObj: function () {
        return this.mapToObj(this.getMap());
    },
    getMap: function () {
        return this.buildMap(this.href);
    },
    buildMap: function (str) {
        var map = new Map();
        var index = str.indexOf("?");
        if (index > 0) {
            var paramsStr = str.substring(index + 1);
            index = paramsStr.indexOf("&");
            if (index > 0) {
                var params = paramsStr.split("&");
                params.forEach(function (param) {
                    index = param.indexOf("=");
                    if (index > 0) {
                        var p = param.split("=");
                        map.set(p[0], p[1]);
                    }
                })
            } else {
                index = paramsStr.indexOf("=");
                if (index > 0) {
                    var p = paramsStr.split("=");
                    map.set(p[0], p[1]);
                }
            }
        }
        return map;
    }
}
