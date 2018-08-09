/*
 * author ( Cl-sma ) Carlos Pinto Jimenez
 * Created on 10/05/2016 04:38:52 PM
 */

var
        slice = [].slice,
        splice = [].splice,
        push = [].push,
        toString = Object.prototype.toString,
        hasOwn = Object.hasOwnProperty,
        cp = window.cp || (window.cp = {}),
        uid = 0;


function _each(obj, callback, context) {

    var length = obj.length, i;
    for (i = 0; i < length; i++) {
        callback.call(context, obj[i], i, obj);
    }
}

function Scope() {
    this.$$watchers = [];
    this.$$emptyFunc = function() {
    };
    this.$$lastDirty = null,
            this.$watch = _watch,
            this.$digest = _diggest,
            this.$loop = _each;
}

function _watch(watchExp, watchFn, deep) {
    var watch = {
        exp: watchExp,
        fn: watchFn || function() {
        },
        last: this.$$emptyFunc,
        deep: deep
    };
    if (!this.$$watchers)
        this.$$watchers = [];

    this.$$watchers.unshift(watch);
    this.$$lastDirty = null;
    var $this = this;
    return function() {
        var index = $this.$$watchers.indexOf(watch);
        if (index >= 0) {
            $this.$$watchers.splice(index, 1);
            $this.$$lastDirty = null;
        }
    };
}

function _diggest() {
    var dirty;
    var ttl = 5;
    var $this = this;
    $this.$$lastDirty = null;
    do {
        var $this = this;
        var newV, oldV, dirty, length = this.$$watchers.length, currentW;
        while (length--) {
            try {

                currentW = $this.$$watchers[length];
                if (!currentW)
                    continue;

                if (!isFunction(currentW.exp)) {
                    currentW.exp = $this.$fn(currentW.exp);
                }
                newV = currentW.exp($this);
                oldV = currentW.last;
                if (!$this.$$areEqual(newV, oldV, currentW.deep)) {
                    $this.$$lastDirty = currentW;
                    currentW.last = (currentW.deep ? _clone(newV) : newV); //Usar método clone
                    currentW.fn(newV, (oldV === $this.$$emptyFunc ? newV : oldV), $this);
                    dirty = true;
                } else if ($this.$$lastDirty === currentW) {
                    dirty = false;
                    return dirty;
                }

            } catch (e) {
                console.log(e);
            }
        }
    } while (dirty && !(--ttl));
}
;

Scope.prototype.$fn = function(value) {
    if (!this.$isType(value, 'function')) {
        return function() {
            return value;
        };
    }
};
Scope.prototype.$$areEqual = function(nValue, oValue, deep) {
    if (deep) {
        return JSON.parse(nValue) === JSON.parse(oValue); //Cambiar por otro metodo
    } else {
        return nValue === oValue ||
                (typeof nValue === 'number' && typeof oValue === 'number' &&
                        isNaN(nValue) && isNaN(oValue));
        ;
    }
}

function _clone(source, destination) {
    var key;

    if (destination) {
        //Destination always must be empty
        if (isArray(destination)) {
            destination.length = 0;
        } else {
            _each(destination, function(value, key) {
                if (key !== '$$hashKey') {
                    delete destination[key];
                }
            });
        }

        return _elementCopy(source, destination);
    }

    return _copyElement(source);

    function _copyElement(src) {

        if (!isObject(src)) {
            return src;
        }

        if (isWindow(src)) {
            throw new Error('Elements like window cant be merged');
        }
        var isdeep;


        if (!destination || destination === undefined) {
            destination = (isArray(src) ? [] : Object.create(Object.getPrototypeOf(src)));
            isdeep = true;
        }

        return isdeep ?
                _elementCopy(src, destination) :
                destination;

    }

    function _elementCopy(source, destination) {
        if (isArray(source)) {
            for (var i = 0, ii = source.length; i < ii; i++) {
                destination.push(_copyElement(source[i]));
            }
        } else {
            for (key in source) {
                if (hasOwn.call(source, key)) {
                    destination[key] = _copyElement(source[key]);
                }
            }
        }
        return destination;
    }

}



function isRegExp(value) {
    return toString.call(value) === '[object RegExp]';
}
function isWindow(obj) {
    return obj && obj.window === obj;
}

function isFile(obj) {
    return toString.call(obj) === '[object File]';
}

function isFormData(obj) {
    return toString.call(obj) === '[object FormData]';
}

function isBlob(obj) {
    return toString.call(obj) === '[object Blob]';
}

function isBoolean(value) {
    return typeof value === 'boolean';
}
function isArray(obj) {
    return (Array.isArray) ? Array.isArray(obj) : typeof obj === 'array';
}
function isObject(obj) {
    return obj !== null && typeof obj === 'object';
}
function isFunction(obj) {
    return typeof obj === 'function';
}

function _repeat( element , exp ) {
    
}