/* 
 *Author     : Cl-sma(Carlos Pinto)
 */


var
        slice = [].slice,
        splice = [].splice,
        _window = window,
        classType = (function (classType) {
            var arr = "Boolean Number String Function Array Date RegExp Object Error".split(" "), classType = {};
            for (i in arr) {
                classType[ "[object " + arr[i] + "]" ] = arr[i].toLowerCase();
            }
            return classType;
        })(),
        coretostring = classType.toString,
        accentsTidy = function () {
            var r = this.toLowerCase();
            return new CHARACTER().rep.call(r);
        };
;


/*
 * Metodo para extender un objeto sobre otro , 
 * haciendo que estas nuevas propiedades no sean enumerables. dentro de ( for i in object )
 */

Object._ = function (destination, source) {

    for (var i in source) {
        Object.defineProperty(destination, i, {value: source[i], configurable: true});
    }
    return destination;
};
/*
 * Metodo para extender las propiedades un objeto sobre otro 
 */
Object.extend = function (des, src) {
    for (name in src) {
        des[name] = src[name];
    }
    return des;
}

Object.extends = function () {

    var object, length = arguments.length,
            target = arguments[0] || {}, deep, i = 0, isArray, org, act, toCopy;
    if (Object.isBoolean(target)) {
        deep = true;
        target = arguments[1] || {};
    }


    for (; i < length; i++) {
        if (deep) {
            if (i < 2) {
                continue;
            }

        }
        if ((object = arguments[i]) !== null) {

            for (name in object) {

                org = target[name];
                act = object[name];

                if (target === act) {
                    continue;
                }

                if (deep && act && (isArray = Object.isArray(act)) || Object.isPlain(act)) {
                    if (isArray) {
                        isArray = false;
                        toCopy = act && Object.isArray(act) ? act : [];
                    } else {
                        toCopy = act && Object.isPlain(act) ? act : {};
                    }
                    // Recursive copy
                    target[ name ] = Object.extends(deep, toCopy, act);

                } else if (act !== undefined) {
                    target[ name ] = act;
                }

            }
        }
    }

    return target;
}
;

Object.cloneArray = function (deep, dest, src) {

    if (dest.size() > 0) {
        dest.length = 0;
    }
    var aux;
    if (!Object.isBoolean(deep)) {
        aux = dest;
        dest = deep;
        src = aux;
        aux = null;
    }

    var obj;
    for (var i = 0; i < src.size(); i++) {
        obj = src[i];
        if (Object.isArray(obj)) {
            dest[ i ] = deep ? Object.cloneArray(true, [], obj) : Object.cloneArray([], obj);
        } else if (Object.canbehash(obj)) {
            dest[ i ] = deep ? Object.extends(true, {}, obj) : Object.extend({}, obj);
        } else {
            dest[ i ] = src[i];
        }

    }

    return dest;

}

Object.merge = function (first, second) {

    var l = first.length,
            s = second.length, i = 0;

    for (; i < s; i++) {
        first[ l++ ] = second[ i ];
    }
    return first;
}

Object.inherit = function (clase) {
    if (!clase.prototype instanceof Object && !clase.prototype instanceof Function) {
        throw new Error('Object [ ' + clase + ' ] is not inheritable');
    }
    function obj() {
    }
    obj.prototype = Object.create(new clase);
    obj.prototype.constructor = obj;
    obj.super = clase.prototype;
    return new obj;
}


String.space = function (num) {
    var t = [], i;
    for (i = 0; i < num; i++) {
        t.push(' ');
    }
    return t.join('');
}
Object.extend(String.prototype, (function () {

    function _cap() {
        if (!this.empty())
            return this.substring(0, 1).toUpperCase() + this.substring(1);
    }

    function _empty() {
        return (this.length === 0);
    }

    function _trim() {
        return this.replace(CHAR.matches.trim, '');
    }
    function _has(value) {
        return this.indexOf(value) > -1;
    }

    function _toarray(index) {
        return (this.has(index) ? this.split(index) : this.split(''));
    }

    function _latin( ) {
        return accentsTidy.call(this);
    }

    function _clean() {
        return this.replace(/\n/g, ' ').replace(/\r/g, ' ');
        var t = [];
        var tab = 0;
        var inString = false;
        for (var i = 0, len = text.length; i < len; i++) {
            var c = text.charAt(i);
            if (inString && c === inString) {
                // TODO: \\"
                if (text.charAt(i - 1) !== '\\') {
                    inString = false;
                }
            } else if (!inString && (c === '"' || c === "'")) {
                inString = c;
            } else if (!inString && (c === ' ' || c === "\t")) {
                c = '';
            } else if (!inString && c === ':') {
                c += ' ';
            } else if (!inString && c === ',') {
                c += "\n" + String.space(tab * 2);
            } else if (!inString && (c === '[' || c === '{')) {
                tab++;
                c += "\n" + String.space(tab * 2);
            } else if (!inString && (c === ']' || c === '}')) {
                tab--;
                c = "\n" + String.space(tab * 2) + c;
            }
            t.push(c);
        }
        return t.join('');
    }
    ;



    function _format(  ) {
        if (this.empty()) {
            return;
        }
        var $this = this.slice(0);
        var args = $a(arguments);

        if (isEmpty(args))
            return;

        var counts = this.slice(0).match(/\%s/g);
        if (!isEmpty(counts)) {
            if (Object.isArray(counts)) {

                counts.each(function (a, b) {
                    if (!isEmpty(args[b])) {
                        $this = $this.replace('%s', args[b]);
                    }
                });
            }
        }
        return $this;
    }

    function _lpad(num, char) {
        var len = this.length;

        if (num <= len) {
            return this;
        }
        char = new String(char);

        var text = (char + this);
        if (text.length > num) {
            char = char.substr(0, (num - len));
            return char + this;
        }

        var tlen = text.length;
        while (tlen < num) {
            text = char.substr(0, (num - tlen)) + text;
            tlen = text.length;
        }

        return text;
    }

    return {
        capitalize: _cap,
        empty: _empty,
        trim: _trim,
        has: _has,
        toArray: _toarray,
        latinize: _latin,
        StringFormat: _format,
        cleanString: _clean,
        lpad: _lpad
    }

})());

var editorFormatter = function () {
    this.actions = {
        format: 1, rollback: 2,
        1: function () {
            return this.replacedText;
        },
        2: function () {
            return this.originalText;
        }
    };
    this.originalText;
    this.replacedText;
    this.replaces = [
        {char: /\'/g, roll: /\s\-\#\-\s/g, value: ' -#- ', valueroll: '\''},
        {char: /\"/g, roll: /\s\.\#\.\s/g, value: ' .#. ', valueroll: '"'},
        {char: /\</g, roll: /\~\#/g, value: '~#', valueroll: '<'},
        {char: /\>/g, roll: /\#\~/g, value: '#~', valueroll: '>'},
        {char: /\&\aacute;/g, roll: /\s\#a#\s/g, value: ' #a# ', valueroll: '&aacute;'},
        {char: /\&\Aacute;/g, roll: /\s\#A#\s/g, value: ' #A# ', valueroll: '&Aacute;'},
        {char: /\&\eacute;/g, roll: /\s\#e#\s/g, value: ' #e# ', valueroll: '&eacute;'},
        {char: /\&\Eacute;/g, roll: /\s\#E#\s/g, value: ' #E# ', valueroll: '&Eacute;'},
        {char: /\&\iacute;/g, roll: /\s\#i#\s/g, value: ' #i# ', valueroll: '&iacute;'},
        {char: /\&\Iacute;/g, roll: /\s\#I#\s/g, value: ' #I# ', valueroll: '&Iacute;'},
        {char: /\&\oacute;/g, roll: /\s\#o#\s/g, value: ' #o# ', valueroll: '&oacute;'},
        {char: /\&\Oacute;/g, roll: /\s\#O#\s/g, value: ' #O# ', valueroll: '&Oacute;'},
        {char: /\&\uacute;/g, roll: /\s\#u#\s/g, value: ' #u# ', valueroll: '&uacute;'},
        {char: /\&\Uacute;/g, roll: /\s\#U#\s/g, value: ' #U# ', valueroll: '&Uacute;'},
        {char: /\&ntilde;/g, roll: /\s\#n#\s/g, value: ' #n# ', valueroll: '&ntilde;'},
        {char: /\&iquest;/g, roll: /\s\#Qi#\s/g, value: ' #Qi# ', valueroll: '&iquest;'}
    ];
    this.option = 0;

    this.init = function (text) {
        this.originalText = text;
        return this;
    };

    this.format = function () {
        this.option = this.actions.format;
        var aux = this.originalText;

        this.replaces.each(function () {
            aux = aux.replace(this.char, this.value);
        });
        this.replacedText = aux;
        return this;
    };

    this.rollback = function () {
        this.option = this.actions.rollback;
        var aux = this.replacedText;

        if (isEmpty(aux)) {
            return this;
        }

        this.replaces.each(function () {
            aux = aux.replace(this.roll, this.valueroll);
        });
        this.replacedText = null;
        this.originalText = aux;
        return this;
    }

    this.text = function () {
        if (this.actions[this.option])
            return this.actions[this.option].call(this);
        else
            return '';
    }
};

var EditorFormat = (function () {
    return {
        init: function (text) {
            return new editorFormatter().init(text);
        },
        rollback: function (text) {
            return new editorFormatter().init(text).format().rollback();
        }
    }
})();


var CHAR = (function () {
    return {
        matches: {
            trim: /^\s+|\s+$/g
        }
    };
})();

Array.argsToArray = function () {
    return this;
}


Object._(Array.prototype, (function () {


    function _new(len, valor) {
        var arr = [];
        len = typeof len === 'number' ? len : 1;
        for (var i = 0; i < len; i++) {
            arr[i] = !valor ? true : valor;
        }
        return arr;
    }
    ;
    function _each(iterator) {
        var value;
        for (var i = 0; i < this.length; i++) {
            value = (iterator.call(this[i], this[i], i, this));
            if (value === false) {
                break;
            } else if (value === -1) {
                i--;
            }
        }
        return this;
    }
    ;
    function _skip(iterator) {
        var results = [];
        this.each(function (a, b) {

            if (Object.isFunction(iterator)) {
                if (!iterator.call(a, a, b, this)) {
                    results.push(a);
                }
            } else {
                if (a !== iterator) {
                    results.push(a);
                }
            }
        });
        return results;
    }
    ;
    function _quit(value) {

        if (!Object.isFunction(value) && !Object.isString(value))
            return this;

        var $this = this;
        if (Object.isString(value)) {
            this.each(function (a, b) {
                if (this.slice(0) === value) {
                    splice.call($this, b, 1);
                    return -1;
                }
            });
            return this;
        }


        if (Object.isFunction(value)) {

            this.each(function (a, b) {
                var value_;
                try {
                    value_ = !!value.call(a, a, b);
                } catch (e) {
                }

                if (value_) {
                    splice.call($this, b, 1);
                    return -1;
                }
            });
            return this;
        }

        this.each(function (a, b) {
            if (a === value) {
                splice.call($this, b, 1);
                return -1;
            }
        });
        return this;
    }
    ;
    function _filter(iterador) {

        if (!iterador)
            return this;

        if (this === null || !Object.isFunction(iterador))
            return;
        var $this = Object(this);
        var results = [], context = arguments[1], value, i = 0;
        for (; i < $this.length; i++) {
            value = $this[i];
            if (iterador.call(value, value, i, $this)) {
                results.push(value);
            }
        }
        return results;
    }

    function _concat() {

        if ($a(arguments).empty())
            return this;
        var values = slice.call($a(arguments), 0);
        for (var i = 0; i < values.size(); i++) {
            if (Object.isArray(values[i])) {
                for (var j = 0; j < values[i].size(); j ++)
                    this.push(values[i][j]);
            } else {
                this.push(values[i]);
            }

        }
        return this;
    }



    function includes(data) {

        if (this.empty())
            return false;
        if (Object.canbehash(data)) {
            var found = false;
            data = O(data);
            this.each(function (a) {
                found = !!data.equals(a);
                if (found)
                    return false;
            });
            return found;
        }

        if (Object.isFunction(data)) {
            try {

                var value = data.call(this);
                return this.has(value);
            } catch (e) {
                return false;
            }

        }

        return this.indexOf(data) > -1;
    }

    function _empty() {
        return !this.length > 0;
    }

    function _last() {
        return this[this.length - 1];
    }

    function _first() {
        return this[0];
    }
    function _clear() {
        this.length = 0;
        return this;
    }

    function _equals(data) {
        if (!data)
            return false;
        if (Object.isArray(data)) {
            if (data.size() !== this.size())
                return false;
            var ret = false, i = 0;
            this.each(function (a) {
                if ((Object.isArray(a) && Object.isArray(data[i])) ||
                        (Object.canbehash(a) && Object.canbehash(data[i]))) {
                    ret = JSON.stringify(a) === JSON.stringify(data[i]);
                } else if (Object.isFunction(a) && Object.isFunction(data[i])) {
                    ret = (JSON.stringify(a.call(a)) === JSON.stringify(data[i].call(data[i])));
                } else if (Object.isDate(a) && Object.isDate(data[i])) {
                    ret = a.equals(data[i]);
                } else {
                    ret = a === data[i];
                }
                i++;
                return ret;
            });
            return ret;
        }

        if (Object.isFunction(data)) {
            var value = data.call(data);
            if (Object.isArray(value)) {
                return _equals.call(this, value);
            }
            return false;
        }

        return false;
    }

    function _size() {
        return this.length;
    }

    function _toquerystring(chr) {
        var str = "";
        chr = chr || "'";
        this.each(function (a) {
            str += "," + chr + a + chr;
        });
        return (str.substring(1));
    }

    return {
        new : _new,
        each: _each,
        empty: _empty,
        has: includes,
        skip: _skip,
        select: _filter,
        all: _filter,
        without: _quit,
        last: _last,
        first: _first,
        clear: _clear,
        concat: (Array.prototype.concat || _concat),
        addAll: _concat,
        equals: _equals,
        size: _size,
        querystr: _toquerystring
    };
})());

['string', 'date', 'array', 'function', 'number', 'regExp', 'object', 'error', 'boolean', 'undefined', 'jquery'].each(function (a, b) {
    Object[ 'is' + a.capitalize()] = function (object) {

        if (a === 'undefined' && typeof object === a) {
            return true;
        }
        if (a === 'jquery' && a instanceof jQuery) {
            return true;
        }

        if (object === null) {
            return false;
        }
        return (typeof object === "object" || typeof object === "function" ?
                classType[ coretostring.call(object) ] || "object" :
                typeof object) === a;
    };
});

Number.roundTo = function (num, to) {
    to = !to ? 1 : to;
    if (!Object.isNumber(+num)) {
        return 0;
    }
    return +(Math.round(+num + ("e+" + to)) + ("e-" + to));
}

Object.extend(Function.prototype, (function () {


    function _bind() {
        if (arguments.length < 2 && Object.isUndefined(arguments[0]))
            return this;
        var $this = this,
                args = $a(arguments),
                object = args.shift();
        return function () {
            return $this.apply(object, args.concat($a(arguments)));
        }
    }

    return {
        bind: _bind
    };
})());
function $a(iterable) {
    if (!iterable)
        return [];
    var length = iterable.length || 0, results = new Array(length);
    while (length--)
        results[length] = iterable[length];
    return results;
}



Object.extend(Object, (function () {

    function _ishash(object) {
        return object instanceof HASH;
    }

    function _clone(object) {

        return Object.isArray(object) ?
                Object.cloneArray(true, [], object) :
                Object.extends(true, {}, object);
    }

    function _canbehash(object) {
        if (Object.isNumber(object) || Object.isDate(object) ||
                Object.isString(object) || Object.isArray(object) ||
                Object.isBoolean(object) || Object.isRegExp(object))
            return false;
        if (Object.isFunction(object)) {
            try {
                object = object.call(object);
            } catch (e) {
                return false;
            }
        }

        for (property in object) {
            return true;
        }

        if (JSON.stringify(object) === '{}') {
            return true;
        }

        return false;
    }

    function _plain(obj) {

        //Miramos que basicamente sea un objeto
        if (typeof obj === 'object' && obj !== null) {

            // Si Object.getPrototypeOf esta soportado , lo usamos
            if (typeof Object.getPrototypeOf === 'function') {
                var proto = Object.getPrototypeOf(obj);
                return proto === Object.prototype || proto === null;
            }

            // Si no soporta Object.getPrototypeOf , entonces usemos la antigua forma
            return Object.prototype.toString.call(obj) === '[object Object]';
        }

        // No es un objeto
        return false;
    }

    return {
        isHash: _ishash,
        clone: _clone,
        canbehash: _canbehash,
        isPlain: _plain
    }
})());
function O(object) {
    return new HASH(object);
}

var Enum = (function () {


    function each(iterator, context) {

        if (Object.isFunction(iterator)) {
            this._each(iterator, context || this);
        }
        return this;
    }

    function _get(prop) {
        var results = [];
        this.each(function (value) {
            results.push(value[prop]);
        });
        return results;
    }

    function _size() {
        return this.length;
    }

    function _equals(object) {

        if (Object.canbehash(object))
            return window.JSON.stringify(object) === window.JSON.stringify(this._object);
        if (Object.isNumber(object) || Object.isDate(object) ||
                Object.isString(object) || Object.isArray(object) ||
                Object.isBoolean(object))
            return false;
        if (Object.isFunction(object)) {
            try {
                var value = object.call(object);
                return window.JSON.stringify(value) === window.JSON.stringify(this._object);
            } catch (e) {
                return false;
            }
        }

        return false;
    }

    return {
        each: each,
        get: _get,
        size: _size,
        equals: _equals
    }

})();
function HASH(object) {
    this.$$initialize = function (object) {
        this._object = Object.isHash(object) ? object.toObject() : Object.clone(object);
    }

    this.$$initialize(object);
}

HASH.prototype = (function () {

    function _each(iterator, context) {
        var i = 0;
        if (Object.isFunction(iterator)) {

            for (var prop in this._object) {
                var value = this._object[prop], key = prop;
                var par = [key, value];
                par.key = key;
                par.value = value;
                if (iterator.call(par, par, i, context._object) === false) {
                    break;
                }
                i++;
            }
        }
        return this._object;
    }

    function _toOb(object) {
        return Object.clone(object);
    }

    function _has() {

    }

    function _serial() {
        var query = null;
        var object = this._object;
        if (Object.isString(object)) {
            query = [encodeURIComponent(object) + ''];
        } else {
            query = [];
            O(object).each(function (a, key) {
                query.push(encodeURIComponent(a.key) + '=' + encodeURIComponent(a.value));
            });
        }

        return query.join('&');

    }


    return {
        _each: _each,
        toObject: _toOb,
        serial: _serial
    }
})();
Object.extend(HASH.prototype, Enum);
Date.test = function (str) {
    return str.match(new Date().matcher(  ));
}
Date.toDate = function (str) {
    if (Object.isDate(str)) {
        return str;
    }
    if ((str || '').trim() === '') {
        return new Date();
    }
    var date, time;
    date = str.split(' ')[0];
    time = str.split(' ')[1];

    var test = Date.test(date);
    if (!isEmpty(test)) {
        var year = parseInt(test[1].length >= 4 ? test[1] : test[3]);
        var month = parseInt(test[2]) - 1;
        var day = parseInt(test[1].length >= 4 ? test[3] : test[1]);
        var hour = time;
        if (!isEmpty(hour)) {
            hour = hour.toArray(':');
        } else {
            var hour = new Date();
            hour = hour.getHours() + ':' + hour.getMinutes() + ':' + hour.getSeconds();
            hour = hour.toArray(':');
        }

        return new Date(year, month, day, hour[0] || '00', hour[1] || '00', hour[2] || '00')
    }

    return null;
};

Date.difference = function (dateIni, dateEnd) {
    if (!dateIni) {
        return null;
    }

    if (!dateEnd) {
        dateEnd = new Date();
    }

    return (((dateIni - dateEnd) / 86400) / 1000);

};

Object.extend(Date.prototype, (function () {



    function _add(days, date) {
        var milisegundos = parseInt(35 * 24 * 60 * 60 * 1000);
        date = date || this;
        var fecha = date;
        var day = fecha.getDate();
        // el mes es devuelto entre 0 y 11
        var month = fecha.getMonth() + 1;
        var year = fecha.getFullYear();
        var hour, minute, second;
        //        document.write("Fecha actual: " + day + "/" + month + "/" + year);

        //Obtenemos los milisegundos desde media noche del 1/1/1970
        var tiempo = fecha.getTime();
        //Calculamos los milisegundos sobre la fecha que hay que sumar o restar...
        milisegundos = parseInt(days * 24 * 60 * 60 * 1000);
        //Modificamos la fecha actual
        fecha.setTime(tiempo + milisegundos);
        day = fecha.getDate();
        month = fecha.getMonth() + 1;
        year = fecha.getFullYear();
        hour = fecha.getHours();
        minute = fecha.getMinutes();
        second = fecha.getSeconds();
        var str = this.format.replace(('yyyy'), year);
        str = str.replace('mm', month);
        str = str.replace('dd', day);
        str += ' ' + hour + ':' + minute + ':' + second;
        return Date.toDate(str);
//        document.write("Fecha modificada: " + day + "/" + month + "/" + year);
    }


    function _string() {
        var date = this;
        var str = this.format.replace(('yyyy'), date.year());
        str = str.replace('mm', date.month());
        str = str.replace('dd', date.day());
        return str;
    }

    function _getdays(date) {
        date = date || this;
        return '' + (date.getDate() < 10 ? '0' + date.getDate() : date.getDate());
    }
    function _getyears(date) {
        date = date || this;
        return '' + date.getFullYear();
    }
    function _getmonths(date) {
        date = date || this;
        return '' + ((date.getMonth() + 1) < 10 ? '0' + (date.getMonth() + 1) : (date.getMonth() + 1));
    }
    function _equals(date) {
        if (!Object.isDate(date)) {
            if (!date.match(Date.matcher)) {
                return false;
            }

            return this.string() === date;
        }
        return this.string() === date.string();
    }

    function _setMin(minute) {
        return new Date(this.getTime() + (60000 * minute));
    }

    function _setHou(hours) {
        return new Date(this.getTime() + (3600000 * hours));
    }

    function _getmatcher(pattern) {
        var aux;
        aux = "(\\d{1,4})[-/.](\\d{1,2})[-/.](\\d{1,4})$";
        return new RegExp(aux);

    }

    function _setMonth(num) {
        if (!num)
            num = 1;

        var month = this.getMonth();
        return new Date(this.setMonth(month + num));
    }

    function _lastDate() {
        return new Date(this.year(), this.month(), 0);
    }

    function _firstDate() {
        return new Date(this.year(), this.month(), 1);
    }

    function _diff(dateEnd) {

        if (!dateEnd) {
            dateEnd = new Date();
        }

        return Math.round((((this - dateEnd) / 86400) / 1000));
    }

    return{
        add: _add,
        string: _string,
        format: 'yyyy-mm-dd',
        matcher: _getmatcher,
        day: _getdays,
        month: _getmonths,
        year: _getyears,
        equals: _equals,
        addMin: _setMin,
        addHour: _setHou,
        difference: _diff,
        addMonth: _setMonth,
        lastDate: _lastDate,
        firstDate: _firstDate
    }

})());


function Time() {
    this._time = null;
    this.MATCH = {
        matcher: /(\d{2})(\:)(\d{2})?(:\d{2})?\s([AMPM]+)/i,
        hour: function () {

        },
        minute: function () {

        },
        second: function () {

        },
        valid: function (hour) {

        }
    }
}
;

var Time = new Time();

Object.extend(Time, (function () {

    function bigger() {

    }

})());

function Request() {
    this._options = null;
    this.location = null;
    this._context = null;
    this._requestHeaders = {};
    this._ajaxDefaults = {
        type: 'POST',
        url: null,
        async: true,
        loading: false,
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType: 'object',
        isFile: false,
        accepts: {
            "*": '*/*',
            text: "text/plain",
            html: "text/html",
            xml: "application/xml, text/xml",
            json: "application/json, text/javascript"
        }
    };
    this.state = 0;
    this.p = {};
    this.x = null;
    this._xmlHtml = {};
    this._transport = {};
    this.loading = null
}

Request.prototype.init = function (url, options, request) {

    var before_, complete_, error_,
            $this = this;

    if (!url && !options) {
        throw new Error('No especificó los parametros de configuracion');
    }

    if (!Object.isString(url)) {
        this._options = options = Object.extends(true, {}, url);
        url = undefined;
    } else {
        this._options = options || {};
    }

    var $this = this;

    this._location = locate();
    this.p = finalParameters.call(this, options || {});
    this.loading = this.p._loading = clsma.$loading();

    if (url) {
        this.p.url = url;
        this.p.data = options;
    }

    if (this.p.url === '/' || this.p.url === this._location) {
        this.p.dataType = 'html';
    }

    if (request) {
        this.p = finalParameters.call(this, request || {});
    }

    before_ = function () {
        if (this.loading)
            this._loading.show();
        if (typeof this._before === 'function')
            this._before();
    }

    complete_ = function () {
        if (this.loading)
            this._loading.hide();
        if (typeof this._complete === 'function')
            this._complete();
    }

    error_ = function () {
        if (this.loading)
            this._loading.hide();
        if (typeof this._error === 'function')
            this._error();
    }

    if (Object.isUndefined(this.p.async)) {
        this.p.async = this._options.loading || true;
    }
    //Default callback events
    this.p.error = error_;
    this.p.complete = complete_;
    this.p.before = before_;
    //Deferred events
    this.p._error = (options || {}).error;
    this.p._before = (options || {}).before;
    this.p._complete = (options || {}).complete;

    //XMLHttpRequest var
    this.x = x();
    //Context to call the done/fail/then events
    this._context = this.p.context || this.p;
    var $this = this;
    this._requestHeaders = {'Ajax-Request': 'true'};
    this._xmlHtml = (function () {
        return  {
            readyState: 0,
            setRequestHeader: function (name, value) {
                if (!$this.state) {
                    $this._requestHeaders[ name ] = value;
                }
                return this;
            },
            abort: function () {
                var text = 'canceled';
                done(0, text);
                return $this;
            }
        }
    })();
    function locate() {
        var location;
        try {
            location = _window.location.href;
        } catch (e) {
            location = _window.document.createElement("a");
            location.href = "";
            location = location.href;
        }

        return location;
    }
    ;
    function  finalParameters(options) {
        if (options.isFile) {
            Object.extend(options, {
                enctype: 'multipart/form-data',
                contentType: false,
                cache: false,
                processData: false
            }
            );
        }
        return Object.extend(this._ajaxDefaults, options);
    }
    ;
    function x() {
        if (typeof XMLHttpRequest !== 'undefined') {
            return new XMLHttpRequest();
        }
        var versions = [
            "MSXML2.XmlHttp.6.0",
            "MSXML2.XmlHttp.5.0",
            "MSXML2.XmlHttp.4.0",
            "MSXML2.XmlHttp.3.0",
            "MSXML2.XmlHttp.2.0",
            "Microsoft.XmlHttp"
        ];
        var xhr;
        for (var i = 0; i < versions.length; i++) {
            try {
                xhr = new ActiveXObject(versions[i]);
                break;
            } catch (e) {
            }
        }
        return xhr;
    }
    ;
    return this;
}

Request.prototype.initTransport = function () {

    var $this = this;
    $this._transport = {
        callback: undefined,
        send: function (xmlhtml, callBacks) {
            var xht = $this.x, status;
            xht.open(xmlhtml.type, xmlhtml.url, xmlhtml.async, xmlhtml.username, xmlhtml.passrowd);
            if (xmlhtml.mimeType && xht.overrideMimeType) {
                xht.overrideMimeType(xmlhtml.mimeType);
            }

            if (!xmlhtml.crossDomain && !$this._requestHeaders["X-Requested-With"]) {
                $this._requestHeaders["X-Requested-With"] = "XMLHttpRequest";
            }

            try {
                for (i in $this._requestHeaders) {
                    xht.setRequestHeader(i, $this._requestHeaders[i]);
                }
            } catch (e) {
            }

            $this._transport.callback = function (abort) {

                return function () {

                    if ($this._transport.callback && (abort || xht.readyState === 4)) {
                        if (abort === 'abort') {
                            xht.abort();
                        } else if (abort === 'error') {
                            callBacks(
                                    xht.status,
                                    xht.statusText
                                    );
                        } else {
                            $this.state = 2;
                            callBacks(
                                    status === 1223 ? 204 : xht.status,
                                    xht.statusText,
                                    typeof xht.responseText === "string" ?
                                    xht.responseText :
                                    undefined,
                                    xht.getAllResponseHeaders()
                                    );
                        }

                    }
                }
            }

            xht.onload = $this._transport.callback();
            xht.onerror = $this._transport.callback('error');

            $this._transport.callback('abort');
            xht.send((xmlhtml.type === 'POST' && xmlhtml.data) ? xmlhtml.data : null);
        },
        abort: function () {
            if ($this._transport.callback) {
                $this._transport.callback(undefined, true);
            }
        }
    };
    return this;
};
Request.prototype.$get = function () {

    var // opciones
            deferred = $.Deferred(),
            $this = this,
            callComplete = $.Callbacks('once memory');
    deferred.promise(this._xmlHtml);

    this._xmlHtml.error = this._xmlHtml.fail;
    this._xmlHtml.success = this._xmlHtml.done;
    this._xmlHtml.complete = callComplete.add;


    this.p.data = this.p.isFile ? this.p.data : Object.isString(this.p.data) ? this.p.data : O(this.p.data).serial();
    this.p.type = this.p.type.toUpperCase();

    if (this.p.data && this.p.contentType !== null && this.p.contentType !== false) {
        this._xmlHtml.setRequestHeader("Content-Type", this.p.contentType);
    }

    this.p.dataTypes = [];
    this.p.dataTypes.push(this.p.dataType || '*');

//    this._xmlHtml.setRequestHeader(
//            "Accept",
//            this.p.dataTypes[ 0 ] && this.p.accepts[ this.p.dataTypes[ 0 ] ] ?
//            this.p.accepts[ this.p.dataTypes[0] ] + (this.p.dataTypes[ 0 ] !== "*" ? ", */*; q=0.01" : "") :
//            this.p.accepts[ "*" ]
//            );

    for (i in this.p.headers) {
        this._xmlHtml.setRequestHeader(i, this.p.headers[ i ]);
    }



    if (this.p.before && (this.p.before.call($this._context, this._xmlHtml, this.p) === false || $this.state === 2)) {
        // Abort if not done already and return
        return done(0, 'cancelled');
    }

    for (i in {error: 1, success: 1, complete: 1}) {
        this._xmlHtml[ i ](this.p[i]);
    }

//    this._xmlHtml.readyState = 1;
//    try {
//        this.state = 1;
    this._transport.send(this.p, done);
//    } catch (e) {
//        if ($this.state < 2) {
//            done(-1, e);
//        } else {
//            throw e;
//        }
//    }

    function done(status, statusText, responses, requestHeaders) {
        var isSuccess, success, error, finalResponse;

        requestHeaders = !requestHeaders ? '' : requestHeaders;
//        $this._xmlHtml.readyState = status > 0 ? 4 : 0;
        isSuccess = status >= 200 && status < 300 || status === 304;

        finalResponse = ajaxConvert($this.p, responses, $this._xmlHtml, isSuccess);

        if (isSuccess && finalResponse.state !== 'fail') {
            statusText = finalResponse.state;
            success = finalResponse.data;
            error = finalResponse.error;
            isSuccess = !error;

        } else {
            error = statusText;
            if (status || !statusText) {
                statusText = "error";
            }

        }

        $this._xmlHtml.status = status;
        $this._xmlHtml.statusText = (statusText) + "";

        if (isSuccess) {
            deferred.resolveWith($this._context, [success, statusText, $this._xmlHtml]);
        } else {
            if ($this._xmlHtml.statusText === 'cancelled')
                $this.p.error.call($this._context, [$this._xmlHtml, statusText]);
            deferred.rejectWith($this._context, [error, statusText, $this._xmlHtml]);
        }


        //Complete
        callComplete.fireWith($this._context, [$this._xmlHtml, statusText]);
        return $this._xmlHtml;
    }

    function ajaxConvert(data, response, xth, isSuccess) {

        if (data.dataType === 'html') {
            return {state: 'success', data: response}
        } else if (data.dataType === 'object') {
            try {
                $.parseJSON(response);
            } catch (e) {
                isSuccess = false;
                return {state: 'fail', data: e}
            }
            return {state: 'success', data: $.parseJSON(response)};
        }
    }

    return this._xmlHtml;
}

  