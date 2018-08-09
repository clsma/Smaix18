/*
 *   Document   : Components
 *   Created on : 17/06/2016, 06:17:00 PM
 *   Author     : Cl-sma Carlos Pinto
 */


var cUID = 0;
function components() {
    this.name = null;
    this.id = null;
    this.element;
    this.type = 'text';
}
components.prototype.getUID = function() {
    return cUID++;
};

components.prototype.create = function(obj) {

    if (isEmpty(obj)) {
        throw new Error('Debe especificar opciones de creación');
    }

    if (isEmpty(obj.attr.name) && isEmpty(obj.name)) {
        throw new Error('Must specify identifiers[ name ] of the object ');
    }
    if (!isEmpty(obj.name)) {
        obj.attr.name = obj.name;
    }

    this.type = obj.type.toLowerCase();
    this.element = (['radio', 'checkbox', 'text', 'password', 'date', 'money'].has(this.type) ? 'input' :
            this.type);

    if (!['input', 'textarea', 'select'].has(obj.type.toLowerCase())) {
        throw new Error('Element[ ' + obj.type + ' ] not supported.');
    }
    this[0] = document.createElement(this.element);

    if (this.element === 'select') {
        createOptions(this, obj);
    }

    applyStylesAndAttributes(this, obj);
    return this[0];
};

components.prototype.each = function(elements, fn) {
    if (!Object.isArray(elements)) {
        elements = [elements];
    }
    for (var i = 0; i < elements.length; i++) {
        fn.call(elements[i], i, this);
    }
}

components.prototype.append = function(elements) {

    if (this[0]) {
        this.each(elements, function(a, context) {
            context[0].appendChild(this);
        });
    }
}


function createOptions(elem, options) {
    var obj, aux, o;
    var opt = options.options;
    var value = (options.attr || {}).value;

    for (i in opt) {
        obj = opt[i];
        if (Object.isArray(obj)) {
            obj.value = obj[0];
            obj.text = obj.size() > 1 ? obj[1] : obj.value;
        } else if (Object.isPlain(obj)) {
            aux = Object.keys(obj);
            obj.value = obj[aux[0]];
            obj.text = aux.size() > 1 ? obj[aux[1]] : obj[aux[0]];
        } else {
            aux = {};
            aux.value = obj;
            aux.text = obj;
            obj = aux;
        }
        o = document.createElement('option');
        o.value = obj.value;
        o.innerHTML = obj.text;
        if (o.value === value) {
            o.checked = true;
        }
        elem.append(o);
    }
}

function applyStylesAndAttributes(elem, object) {
    var attr;
    //Applying attributes
    if (!isEmpty(object.attr)) {
        attr = object.attr;
        if (Object.isPlain(attr)) {
            for (i in  attr) {
                if (!isEmpty(attr[i])) {
                    elem[0].setAttribute(i, attr[i]);
                }
            }
        }

    }
    if (!isEmpty(object.css)) {
        attr = object.css;
        if (Object.isPlain(attr)) {
            for (i in  attr) {
                if (!isEmpty(attr[i])) {
                    elem[0].style[i] = attr[i];
                }
            }
        }
    }

    if (!isEmpty(object.clases)) {
        if (Object.isString(object.clases)) {
            attr = object.clases.split(' ');
            var iAttr;

            for (i in attr) {
                iAttr = (elem[0].className || '').split(' ');
                if (!isEmpty(attr[i]) && !iAttr.has(attr[i])) {
                    iAttr.push(attr[i]);
                    elem[0].className = iAttr.join(' ');
                }
            }
        }
    }
}
var UI = Object.inherit(components);