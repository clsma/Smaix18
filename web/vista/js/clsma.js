/* 
 * Author     : Cl-sma(Carlos Pinto)  -  19/10/15
 */

function Clsma() {

    this.$scope = {
        $request: {},
        $partials: {},
        $responses: {},
        $gen: {
        },
        $application: {
            $nameSpaces: [],
            $scope: {},
            $cript: []
        }
    },
    this.$navigatores = {
        Android: function () {
            return navigator.userAgent.match(/Android/i);
        },
        BlackBerry: function () {
            return navigator.userAgent.match(/BlackBerry/i);
        },
        iOS: function () {
            return navigator.userAgent.match(/iPhone|iPad|iPod/i);
        },
        Opera: function () {
            return navigator.userAgent.match(/Opera Mini/i);
        },
        Windows: function () {
            return navigator.userAgent.match(/IEMobile/i);
        },
        Mozilla: function () {
            return navigator.userAgent.match(/Mozilla/i) && navigator.appName.match(/Netscape/i);
        },
        Chrome: function () {
            return navigator.userAgent.match(/Chrome/i);
        },
        any: function () {
            return (this.Android() || this.BlackBerry() || this.iOS() || this.Opera() || this.Windows() || this.Mozilla());
        }
    };
    this.$$criptName = '';
    this.$$instance;
    this.$noop = function () {
    };
    this.$uid = 0;

}
;
var clsma = new Clsma();
Clsma.prototype.$app = {};
class_type = clsma.$scope.$partials.class2type = {};
core_string = clsma.$scope.$partials.core_toString = class_type.toString;
Clsma.prototype.$window = window;
Clsma.prototype.$document = window.document;



Clsma.prototype.$$addToCryptElement = function (element) {
    var $this = this;
    if (element) {

        var id = $(element).attr('id') || $(element).attr('name');
        var aux1 = id.substring(3);
        var aux2 = id.replace(aux1, '');
        var final_id = aux1 + $this.$$crypto(id) + aux2;
        this.$scope.$application.$cript.push({
            name: $this.$$criptName,
            data: {
                element: element,
                name: $this.$$crypto(final_id),
                value: $this.$$in32($(element).val())
            }
        });
        $this.$$criptName = null;
    }
};

Clsma.prototype.$$serialCrypto = function () {

    var div;
    var parent = $(this[0]);
    var myChild = this.$grep(this.$scope.$application.$cript,
            (parent.attr('id') || parent.attr('name'))
            );
    var object = {};
    $('#cont_parent').remove();
    parent.append('<div id="cont_parent" style="display:none"/>');
    $.each(myChild, function (a, b) {
        object[b.data.name] = b.data.value;
        div = $('<input type="hidden"/>');
        div.attr({
            id: b.data.name,
            name: b.data.name,
            get: "true"
        }).val(b.data.value);
        div.appendTo('#cont_parent');
    });
    this.$setScope(object, '$final', '$application.$cript');
}

Clsma.prototype.$$getForm = function () {
    return this.$getScope('$final', '$application.$cript');
}

Clsma.prototype.$$crypto = function (value) {
    return clsma.$window['md5'].call(this, value);
};
Clsma.prototype.$$in32 = function (value) {

    var str2 = '';
    for (var i = 0; i < value.length; i++) {
        str2 += String.fromCharCode(value.charCodeAt(i) + 32);
    }
    return str2;
}

Clsma.prototype.$$out32 = function (value) {

    var string = '';
    for (var i = 0; i < value.length; i++) {
        string += String.fromCharCode(value.charCodeAt(i) - 32);
    }
    return string;
}



clsma.$UID = function () {
    return this.$uid++;
}

clsma.$now = function () {
    return new Date().getTime();
}

clsma.$reload = function () {
    clsma.$window.location.reload();
};
Clsma.prototype.$getScope = function (elem, context) {

    return (!context) ? clsma.$scope[elem] : clsma.$scope[context][elem];
};
Clsma.prototype.$setScope = function (valor, elem, context) {

    if (!elem || (!elem && !context)) {
        $.error('No ha definido contexto o el nombre de la variable al cual incluira el data ');
        return;
    }

    if (clsma.$scope[elem]) {
        clsma.$scope[elem] = $.extend(true, {}, clsma.$scope[elem], valor);
    }

    if ((clsma.$scope[context]) && (clsma.$scope[context][elem])) {
        clsma.$scope[context][elem] = $.extend(true, {}, clsma.$scope[context][elem], valor);
    }

    if (!context) {
        (clsma.$scope)[elem] = valor;
    } else {
        clsma.$scope[context] = $.extend(true, {}, clsma.$scope[context], clsma.$scope[context] || {}[elem]);
        clsma.$scope[context][elem] = valor;
    }

    return clsma;
};
Clsma.prototype.$options = function (conf) {
    var options, finalConfig;
    options = this.$getScope('$request');
    finalConfig = options.$send || {};
    if (this.$isEmpty(options.controller) && !conf.url) {
        this.$error('Debe definir controlador(es) o destinos de la petici?n');
        return {};
    }

    var control = options.controller; // Controladores 
    var auxControl = {};
    var alias_ = conf.controller; //Alias a tomar

    // Si no hay configuracion aun ..
    if (this.$isEmpty(finalConfig)) {
        // Tomamos por defecto el primer controlador

        if (alias_) {
            auxControl = this.$grep(control, 'alias', alias_)[0];
        } else {
            auxControl = control[0];
        }
        //Agrego a las configuraciones iniciales
        finalConfig.$controllers = control;
        finalConfig.$mainController = {
            $alias: auxControl.alias,
            $path: auxControl.path,
            $name: auxControl.name
        }
        alias_ = alias_ || finalConfig.$mainController.$alias;
        // Si ya existe configuracion
    } else {

        if (!alias_) {
            auxControl = finalConfig.$controllers[0];
            finalConfig.$mainController = {
                $alias: auxControl.alias,
                $path: auxControl.path,
                $name: auxControl.name
            }
        } else {
            auxControl = this.$grep(finalConfig.$controllers, 'alias', alias_)[0];
        }


        if (!auxControl.path) {
            this.$error('Debe definir evento receptor del controlador');
            return {};
        }
    }

    conf = Object.extends(true, {}, options, conf);

    if (!conf.returning && !options.returning) {
        conf.returning = conf.dataType = 'html';
    } else if (conf.returning) {
        conf.dataType = conf.returning;
    } else {
        conf.dataType = conf.returning = options.returning;
    }
    conf.errors = finalConfig.errors = conf.error;
    if (!conf.error)
        conf.errors = finalConfig.errors = 'exito';
    // evento del controlador
    if (!this.$isAray(conf.data) && this.$isEmpty(conf.data)) {
        this.$error('Debe definir evento para dirigir la peticion');
        return;
    } else {
        if (this.$isAray(conf.data)) {
            var aux = conf.data;
            conf.data = {};
            if (aux.size() > 1) {
                conf.data = aux[1];
                if (conf.isFile) {
                    conf.url = auxControl.name + (auxControl.name.has('?') ? '&' : '?') + auxControl.path + '=' + aux[0];
                } else if (Object.isString(aux[1])) {
                    conf.url = auxControl.name + (auxControl.name.has('?') ? '&' : '?') + auxControl.path + '=' + aux[0];
                }

                if (conf.isFile && aux.size() > 2) {
                    conf.url += '&' + clsma.$serialize(aux[2]);
                } else if (!conf.isFile) {
                    conf.data[ auxControl.path ] = aux[0];
                    conf.url = !conf.url ? auxControl.name : conf.url;
                }
            } else {
                conf.data[ auxControl.path ] = aux[0];
                conf.url = auxControl.name;
            }
        }
    }
    conf = $.extend(true, {}, auxControl, conf);
//    finalConfig = $.extend(true, {}, finalConfig, conf);
    this.$setScope(finalConfig, '$send', '$request');
    return conf;
};
Clsma.prototype.$requestSetup = function (options) {
    var scope = this.$getScope('$request');
    if (!isEmpty(options.controller)) {
        scope.controller = (scope.controller || []).concat(options.controller);
    }
    options = $.extend(true, {}, scope, options);
    options.controller = scope.controller;

    this.$setScope(options, '$request');
};
Clsma.prototype.$get = function (url, options, request) {
    return new Request()
            .init(url, options, request)
            .initTransport()
            .$get();
}

clsma.$serialize = function (object) {

    var query = null;
    if (this.$inArray('string'.split(' '), this.$type(object))) {
        query = [encodeURIComponent(object) + ''];
    } else if (Object.canbehash(object)) {
        query = [];
        for (var key in object) {
            query.push(encodeURIComponent(key) + '=' + encodeURIComponent(object[key]));
        }
    } else if (this.$type(object) === 'array') {
        var obj;
        query = [];
        for (i in object) {
            obj = object[i];
            if (Object.canbehash(obj)) {
                for (key in obj) {
                    query.push(encodeURIComponent(key) + '=' + encodeURIComponent(obj[key]));
                }
            }
        }
    } else {
        return null;
    }

    return query.join('&');
}

clsma.$request = function (conf) {
//Obtenemos las configuraciones finales
    conf = this.$options(conf);
    return clsma.$get(conf).done(function (data) {

// Si se devolvio un objeto , validamos que no haya sido error para lanzar la exepci?n
        if (Object.isUndefined(data)) {
            if (this.loading)
                this._loading.hide();
            return;
        }

        if (conf.returning !== 'html') {

            if (conf.loading)
                this._loading.hide();
            if (data.sessionEnd === true) {
                if (!this.isBootstrap) {
                    clsma.$msg(data.msg, function () {
                        clsma.$window.top.location.href = Rutav + '/vista/sistemas/login/login.jsp';
                    }, 'ERROR');
                } else {
                    $alert(data.msg, function () {
                        clsma.$window.top.location.href = Rutav + '/vista/sistemas/login/login.jsp';
                    }, 'danger');
                }
                clsma.$error(data.msg);
            }
            if (conf.novalid)
                return;
            if (data[clsma.$getScope('$send', '$request').errors] === 'ERROR') {
                if (!this.isBootstrap)
                    clsma.$msg(data.msg, '', 'ERROR');
                else
                    $alert(data.msg, null, 'danger');
                clsma.$error(data.msg);
            }
        } else {

            try {
                data = $.parseJSON(data);
                if (conf.loading)
                    this._loading.hide();
                if (data.sessionEnd === true) {
                    if (!this.isBootstrap) {
                        clsma.$msg(data.msg, function () {
                            clsma.$window.top.location.href = Rutav + '/vista/sistemas/login/login.jsp';
                        }, 'ERROR');
                    } else {
                        $alert(data.msg, function () {
                            clsma.$window.top.location.href = Rutav + '/vista/sistemas/login/login.jsp';
                        }, 'danger');
                    }
                    clsma.$error(data.msg);
                }
                if (conf.novalid)
                    return;
                if (data[clsma.$getScope('$send', '$request').errors] === 'ERROR') {
                    if (!this.isBootstrap)
                        clsma.$msg(data.msg, '', 'ERROR');
                    else
                        $alert(data.msg, null, 'danger');
                    clsma.$error(data.msg);
                }
            } catch (e) {
                if (conf.loading)
                    this._loading.hide();
                if (clsma.$isEmpty(data)) {
                    clsma.$msg('Se ha generado un resultado inesperado, no se pudo obtener respuesta del servidor', '', 'ERROR');
                    clsma.$error('Se ha generado un resultado inesperado, no se pudo obtener respuesta del servidor');
                }
            }
        }
    });
};
clsma.$loading = function (msg, options) {


    var options = $.extend(true, {}, options, clsma.$scope.$gen.$loading || {}),
            msg_ = msg || options.msg,
            $loading = {
                show: function () {

                    var div = $('<div/>')
                            , parent = $('body'),
                            tabla = $('<table/>'),
                            container = $('<div/>');
                    div.attr({id: 'loading' + clsma.$UID()})
                            .css(options.styles);
                    parent.append(div);
                    container.css({
                        width: '40%',
                        height: '200px',
                        'z-index': 1600,
                        margin: '0 auto',
                        position: 'relative',
                        top: '50%',
                        transform: 'translateY(-50%)',
                        'border-radius': '6px',
                        'vertical-align': 'middle',
                        'overflow': 'hidden'
                    });
                    container.appendTo(div);
                    var tr = $('<tr/>')
                            .appendTo(tabla);
                    var td = $('<td/>')
                            .css({'text-align': 'center'})
                            .appendTo(tr);
                    var img = $('<img/>')
                            .attr({src: Rutav + '/vista/img/ajax-loader3.gif'})
                            .css({width: '85px', height: '85px'})
                            .appendTo(td);
                    tr = $('<tr/>').appendTo(tabla);
                    td = $('<td/>')
                            .css({'text-align': 'center'})
                            .appendTo(tr);
                    var div_ = $('<div/>')
                            .css({border: '1px solid black',
                                'text-align': 'center',
                                width: '100%',
                                color: 'white',
                                'font-size': '14px'
                            })
                            .html(msg_)
                            .appendTo(td);
                    tabla.css({width: '100%', 'margin-top': '50px'})
                            .appendTo(container);
                    this._loading.$self = div;
                    return this;
                },
                hide: function () {
                    this._loading.$self.remove();
                    return this;
                },
                _loading: {}
            };
    return $loading;
}

clsma.$imgView = function (element, options) {
    if (Object.canbehash(element)) {
        options = element;
        element = $('body');
    }

    var _id = this.$UID();

    if (!options || (Object.canbehash(options) && !options.img)) {
        this.$error(
                'Debe especificar la ruta de la imagen');
    }

    options = $.extend(true, this.$scope.$gen.$imgView, options);
    if (clsma.$isEmpty(options.noimg)) {
        options.noimg = Rutav + '/vista/img/imagenOfertaAcademica.jpg';
    }

    $('#backImg').remove();
    $('#modalImg').remove();
    var div = $('<div/>').attr({id: 'backImg' + _id}),
            img, modal, cont, aClose;
    modal = $('<div/>').attr({id: 'modalImg' + _id}).css({
        position: 'fixed',
        width: '100%',
        height: '100%',
        top: '0',
        left: '0',
        'z-index': 9999,
        'background-color': 'black',
        opacity: 0.4,
        filter: 'alpha(opacity = 40)',
    });
    div.css({
        position: 'fixed',
        width: '100%',
        height: '100%',
        top: '0',
        left: '0',
        'z-index': 100000,
        display: 'none'
    }).click(function () {
        $('#backImg' + _id).remove();
        $('#modalImg' + _id).remove();
    });
    cont = $('<div/>').css({
        width: options.styles.width,
        height: options.styles.height,
        position: 'relative',
        margin: '0 auto',
        top: '50%',
        transform: 'translateY(-50%)',
        'border-shadow': '2px 2px white',
        '-webkit-box-shadow': ' 0px 3px 21px 0px rgba(255,255,255,1)',
        '-moz-box-shadow': ' 0px 3px 21px 0px rgba(255,255,255,1)',
        'box-shadow': '0px 3px 21px 0px rgba(255,255,255,1)',
        'z-index': 9999
    }).appendTo(div);
    aClose = $('<a/>').css({
        width: '15px',
        height: '15px',
        'border-radius': '5px',
        'background-color': 'white',
        position: 'absolute',
        top: '5px',
        right: '10px',
        'z-index': 9999,
        'cursor': 'pointer',
        padding: '6px',
        'text-align': 'center',
        'font-weight': '900'
    }).text('X').click(function () {
        $('#backImg' + _id).remove();
        $('#modalImg' + _id).remove();
    });
    img = $('<img/>').css({width: '100%', height: '100%', margin: '0 auto'});
    img[0].onerror = function () {
        this.src = options.noimg;
    };
    img[0].src = options.img;
    img.appendTo(cont);
    aClose.appendTo(cont);
    modal.appendTo(element);
    div.appendTo(element).fadeIn('slow');
};
clsma.$confirm = function (msg) {
    var id = 'jQueryconfirm' + this.$UID();
    $('#' + id).remove();
    var width_ = msg.length * 9;
    if (width_ > 600) {
        width_ = 600;
    }

    var div = clsma.$new_window(id, {
        buttons: ['Aceptar', 'Cancelar'],
        width: width_,
        title: 'Confirmación',
        position: ['top', 20]
    });
    var iconHTML = '<td><img src="' + Rutav + '/vista/img/confirmation.png" width="40" height="40" ></td>',
            msg = '<td>' + msg + '</td>';
    $(div.$this).html('<table style="margin:0 auto"><tr>' + iconHTML + msg + '</tr></table>').dialog('open');
    return div;
}

clsma.$new_window = function (id, options) {
    var div
    if (options.location === 'top') {
        div = $('<div/>', window.top.document.body);
    } else {
        div = $('<div/>', window.document.body);
    }
    div.attr({id: id}).get(0);
    div.options = {};
    options.close = function () {
        this.options.executeCallbacks.call(this);
        $(this).dialog('destroy');
        $(this).remove();
    };
    options.buttons = this.$arrayToObject(options.buttons, this.$scope.$gen.$dialog.buttons);
    for (i in options.buttons) {
        options.buttons[ i ] = [options.buttons[ i ], clsma.$Callbacks()];
    }
    if (!options.buttons) {
        options.buttons = {
            Salir: function () {
                $(this).dialog('close');
            }
        };
    }
    ;
    div.options.booleans = {};
    div.options.buttons = {};
    for (name in options.buttons) {

        div.options.booleans[ 'boolean' + name ] = false;
        div.options.buttons[  name ] = options.buttons[ name ];
        options.buttons[ name ] = (function (name) {
            return function (event) {
                var panel = this;
                panel.options.booleans[ 'boolean' + name ] = true;
                $(panel).dialog('close');
            }
        })(name);
        var list = div.options.buttons[  name ][1];
        div.options[name] = list.add(name);
        div.options['fire' + name] = function (name, options) {
            div.options['fire' + name + 'Exe'](name, options, arguments);
            return div.options;
        };
        div.options['fire' + name + 'Exe'] = list.fireContext;
    }
    ;
    div.options.executeCallbacks = function () {

        for (button in this.options.buttons) {
            if (!!this.options.booleans[ 'boolean' + button ]) {
                this.options['fire' + button](button, this.options);
                return true;
            }
        }
    };
    options = $.extend(true, {}, this.$scope.$gen.$dialog, options);
    if ((options.height + '').indexOf('%') !== -1) {
        var div_ = $('<div />');
        if (options.location === 'top') {
            div_.appendTo(window.top.document.body);
        } else {
            div_.appendTo(window.document.body);
        }
        div_.css({width: options.width, height: options.height, position: 'absolute'});
        options.height = div_.height();
        options.width = div_.width();
    }

    try {

        div.options.$this = div;
        div[0].options = div.options;
        $(div.options.$this).dialog('destroy');
    } catch (e) {
    }

    $(div.options.$this).dialog(options);
    return div.options;
}


clsma.$Callbacks = function (options) {

    options = options || {};
    var firing,
            fired,
            list = [],
            firingIndex = -1,
            firingLength,
            arrNames = [],
            fire = function (index, data) {

                firingLength = list.length;
                firingIndex = -1;
                fired = firing = true;
                if (clsma.$type(index) !== 'string') {
                    data = index;
                    index = undefined;
                }

                while (++firingIndex < list.length) {

                    if (list[ firingIndex ] && (list[ firingIndex ][ 'name' ] === index)) {
                        var result = (list[ firingIndex ][ 'function']).apply(data[0], data[1]);
                        if (result === false)
                            firingIndex = list.length;
                    } else {
                        if ((list[ firingIndex ]).apply(data[0], data[1]) !== false && options.stopFalse) {
                            firingIndex = list.length;
                        }
                    }
                }

                firing = list = undefined;
                return this;
            },
            $self = {
                add: function (name) {
                    return  (function (name) {
                        return function (func) {
                            if (list) {
                                var start = list.length;
                                var arg;
                                if (options.once && arrNames.indexOf(name) !== -1) {
                                    return this;
                                }
                                if (name) {
                                    arrNames.push(name);
                                    arg = [{name: name, 'function': func}];
                                } else {
                                    arg = [func];
                                }
                                (function add(args) {
                                    $.each(args, function (_, arg) {
                                        if (clsma.$type(arg) === 'function') {
                                            if (!options.unique) {
                                                list.push(arg);
                                            }
                                        } else if (arg && clsma.$type(arg) === 'object') {
                                            list.push(arg);
                                        }
                                    });
                                })(arg);
                            }
                            return this;
                        };
                        return $self;
                    })(name);
                },
                fireContext: function (name, context, data) {

                    if (clsma.$inArray(arrNames, name)) {
                        data = data || [];
                        var args = [context, data ? data : data];
                        fire(name, args);
                    }
                    return this;
                },
                fireAll: function (data) {
                    data = data || [];
                    fire(this, data);
                    return this;
                },
                fired: function () {
                    return !!fired;
                }
            };
    return $self;
}

clsma.$arrayToObject = function (array, object, deep) {

    var aux = {};
    for (i in array) {
        aux[array[i]] = array[i];
    }

    if (object) {
        if (deep) {
            aux = $.extend(true, {}, aux, object);
        } else {
            aux = $.extend({}, aux, object);
        }
    }
    return aux;
}

clsma.$calendarMsg = function (activity) {
    return ('La actividad de calendario de %s no está activa, Dirigase a Actualizar -> Académicos -> Calendarios y Habilítela.').StringFormat(activity);
}
clsma.$deleteTemplate = function (functio, title) {
    return ('<div style="text-align:center"><a href="#" onclick="javascript:%s"><img src="%s/vista/img/delete.png" title="%s" style="width:20px;height:20px"></a>').StringFormat(functio, Rutav, title);
}

clsma.$genericTemplate = function (functio, title, ruta) {
    return ('<img onclick="javascript:%s" src="%s" style="text-align:center;width:20px;height:20px;cursor:pointer" title="%s"></img>').StringFormat(functio, ruta, title);
}
clsma.$error = function (msg) {
    throw new Error(msg);
};
clsma.$isEmpty = function (element) {
    return clsma.$undefined(element) || isEmpty.call(window, element);
};
clsma.$msg = function (data, event, type) {
    show_message.call(window, data, event, type);
};
clsma.$message = function (data) {
    show_message.call(window, data.msg, data.fnc, data.type);
};
clsma.$money = function (elem) {

    if (clsma.$inArray(['string', 'number'], clsma.$type(elem)))
        return accounting.formatMoney(parseFloat(elem), {decimal: ',', precision: 2, thousand: ".", });
    else
        setToMoney.call(window, elem);
    return elem;
};
clsma.$nomoney = function (elem) {
    if (['string', 'number'].has(clsma.$type(elem)))
        return accounting.unformat(elem);
    else
        return accounting.unformat($(elem).val());
};
clsma.$destroyMoney = function (elem) {
    elem = $(elem);
    if (clsma.$isEmpty(elem.data('money')))
        return this;
    var data_press = elem.data('keypress_handler');
    var data_up = elem.data('keyup_handler');
    elem.attr('onkeypress', '');
    elem.unbind('keypress', data_press);
    elem.unbind('keyup', data_up);
    ['size', 'valor', 'keyup_handler', 'keypress_handler'].each(function () {
        elem.removeData(this.slice(0));
    });
    elem.removeAttr('data-money');
    elem.removeData('money');
    elem.val(clsma.$nomoney(elem.val()));
};
clsma.$isAray = Array.isArray || function (obj) {
    return clsma.$type(obj) === "array";
};
clsma.$type = function (obj) {

    if (obj === null) {
        return String(obj);
    }

    return typeof obj === "object" || typeof obj === "function" ?
            class_type[ core_string.call(obj) ] || "object" :
            typeof obj;
};
clsma.$length = function (element) {

    if (element === null || clsma.$undefined(element)) {
        return 0;
    }

    if (clsma.$type(element) === 'array' || clsma.$type(element) === 'string') {
        return element.length;
    }

    if (clsma.$type(element) === 'object') {
        var i = 0;
        for (name in element) {
            if (name === 'length')
                return element.length;
            else
                i++;
        }
        return i;
    }
};
clsma.$isObject = function (obj) {
    return $.isPlainObject(obj);
}

clsma.$grep = function ($array, $find, $value) {
    return $.grep($array, function (a, b) {
        return a[$find] === $value;
    }) || [];
};
clsma.$undefined = function (element) {
    return element === undefined;
};
clsma.$isFunction = function (element) {
    return clsma.$type(element) === 'function';
};
clsma.$isMobile = function () {
    return this.$navigatores.any();
}

clsma.$inArray = function (array, obj) {
    return Array.prototype.indexOf.call(array, obj) !== -1;
};
clsma.$arrayRemove = function (array, value) {
    var index = array.indexOf(value);
    if (index >= 0)
        array.splice(index, 1);
    return value;
};
clsma.$eval = function (data, options) {
    if (data && $.trim(data)) {
        (clsma.$window.execScript || function (argument) {
            var args = argument;
            if (args.length === 0)
                return;
            var func = args[0];
            var datas = $a(args).splice(1, $a(args).size());
            clsma.$window[ data ].apply(clsma.$window, datas);
        })(arguments);
    }
};
clsma.$cookies = function (name, value, obj) {

    if (clsma.$isEmpty(value) && clsma.$isEmpty(obj)) {
        return getCookie(name);
    }
    if (!clsma.$isEmpty(name) && value === null) {
        return deleteCookie(name);
    }

    if (!clsma.$isEmpty(value) && !clsma.$isEmpty(name)) {
        return createCookie(name, value, obj);
    }


    function createCookie(name, value, obj) {
        var str = '';
        for (a in obj) {
            if (a.toLowerCase() === 'expires') {
                str += '; ' + a + '=' + (new Date().addMin(parseInt(obj[a]))).toGMTString();
            } else {
                str += '; ' + a + '=' + obj[a];
            }

        }
        str = str.substring(1) || '';
        str = name + '=' + value + ';' + str;
        return clsma.$document.cookie = str;
    }

    function deleteCookie(name) {
        createCookie(name, '', {expires: -1});
    }

    function getCookie(name) {
        var cookies = {};

        if (!clsma.$isEmpty(clsma.$document.cookie)) {
            var split = clsma.$document.cookie.split('; ');
            for (var i = 0; i < split.length; i++) {
                var name_value = split[i].split("=");
                if (name_value[0] === name) {
                    name_value[0] = name_value[0].replace(/^ /, '');
                    cookies[decodeURIComponent(name_value[0])] = decodeURIComponent(name_value[1]);
                }
            }
        }

        return cookies;
    }

}

clsma.$rand = function (to, from) {
    from = !from ? 1 : from;
    return Math.round(Math.random() * (to - from) + parseInt(from));
};
clsma.$evalarguments = function (funct, data) {
    if (data && $.trim(data)) {
        (clsma.$window.execScript || function (funct, data) {
            clsma.$window[ funct ].apply(window, data);
        })(funct, data);
    }
};
clsma.$drag = function (element, options) {

    options = Object.extends({}, this.$scope.$gen.$drag, (options || {}));
    $(element).draggable(options);
}

clsma.$drop = function (element, options) {

    options = Object.extends({}, this.$scope.$gen.$drop, (options || {}));
    if (this.$isEmpty(options.accept)) {
        this.$error('Debe especificar la clase accept [ los draggables que aceptará ]');
    }
    $(element).droppable(options);
}
clsma.$context = function (elems, options) {
    options = Object.extends(this.$scope.$gen.$context, (options || {}));
    if (clsma.$isEmpty(options.bindings) && clsma.$isEmpty(options.options)) {
        clsma.$error('No hay opciones de manú agregadas');
    }
    var realOptions = {};
    var id = 'context_' + this.$UID();
    var divC = $('<div />')
            .attr({id: id})
            .appendTo('body')
            .css({display: 'none'});
    var ul = $('<ul />').appendTo(divC);
    var li, img, has_error;
    O(options.options).each(function () {
        has_error = false;
        li = $('<li />')
                .attr({id: this.key})
                .html('<span>' + this.value.text + '</span>');
        realOptions[ this.key ] = this.value.fn;
        if (!clsma.$isEmpty(this.value.icon)) {
            img = $('<img />').css({width: '18px', height: '18px'});
            try {
                img[0].onerror = function () {
                    has_error = true;
                }
            } catch (e) {

            }
            img[0].src = Rutav + this.value.icon;
            if (!has_error) {
                img.prependTo(li);
            }

        }

        li.appendTo(ul);
    });
    options.bindings = realOptions;
    return elems.css('cursor', 'pointer').contextMenu(id, options);
};
clsma.$sort = function (element, options) {

    options = Object.extends(this.$scope.$gen.$sortable, (options || {}));
    $(element).each(function () {
        $(this).sortable(options);
    });
}
clsma.$load = function (element, url, callback) {

    if (clsma.$length($(element)) === 0) {
        return;
    }
    ;
    if (clsma.$type(url) !== 'array') {
        clsma.$error('Ruta de petici?n no valida');
        return;
    }
    ;
    clsma.$request({
        data: url,
        returning: 'html'
    }).then(function (data) {
        if (clsma.$type(data) === 'string') {
            $(element).html(data);
        }

    }).done(function (data) {
        if (clsma.$type(data) === 'string') {
            if (clsma.$isFunction(callback)) {
                callback.call($(element));
            } else if (clsma.$type(callback) === 'string') {
                clsma.$eval(callback);
            }
        }
    });
};
clsma.$date = function ($element, object)
{

    object = object || {};
    $element = clsma.$type($element) === 'string' ? '#' + $element : $element;
    try {
        $($element).datepicker("destroy");
    } catch (e) {
    }
    var value = $($element).val();
    value = clsma.$isEmpty(value) ? "" : value;
    $($element).datepicker
            ($.extend(true, {}, clsma.$scope.$gen.$datepicker, {
                numberOfMonths: object.months || 1,
                minDate: object.mindate || null,
                maxDate: object.maxdate || null,
                onSelect: function (date, event) {

                    if (Object.isFunction(object.select)) {
                        object.select.call(this, date, event);
                    } else if (Object.isString(object.select)) {
                        var funcs = object.select.split(/\,/g);
                        for (i in funcs) {
                            clsma.$eval(funcs[i], date, event);
                        }

                    }
                }
            }));
    var val_ = object.init &&
            (clsma.$inArray(['date', 'string'], clsma.$type(object.init))) ?
            (object.init === 'today' ? new Date() : object.init) :
            value;
    val_ = clsma.$type(val_) === 'date'
            ? val_.string()
            : (val_ || '').substring(0, 10);
    return   $($element).attr('readonly', true).val(val_);

};
clsma.$time = function ($element, object)
{

    object = object || {};
    if (clsma.$isEmpty(object.ampm)) {
        object.ampm = 'true';
    }
    try {
        $($element).timepicker("destroy");
    } catch (e) {
    }


    var value = $($element).val();
    value = clsma.$isEmpty(value) ? "" : value;
    var init = object.init || value || null;
    $element = clsma.$type($element) === 'string' ? '#' + $element : $($element);
    var timeFormat = ((object.format || '12').toLowerCase() === '12' ?
            ('hh:mm ' + (object.ampm === 'true' ? 'tt' : '')) :
            ('HH:mm ' + (object.ampm === 'true' ? 'tt' : '')));
    return  $($element).timepicker({
        hourGrid: object.hour || 4,
        minuteGrid: object.minutes || 10,
        timeFormat: timeFormat,
        minTime: object.mintime,
        stepMinute: parseInt(object.step || 1),
        maxTime: object.maxtime,
        onSelect: function (selected, event) {
            if (Object.isFunction(object.select)) {
                object.select.call(this, selected, event);
            } else if (Object.isString(object.select)) {
                clsma.$eval(object.select, selected, event);
            }
        },
        onClose: function (selected, event) {
            if (Object.isFunction(object.close)) {
                object.close.call(this, selected, event);
            } else if (Object.isString(object.close)) {
                clsma.$eval(object.close, selected, event);
            }
        }
    }).timepicker('setTime', init).attr('readonly', true);
};
clsma.$set = function ($element, data) {
    var $this = $($element);
    if (clsma.$isEmpty(data)) {
        return  $this;
    }

    if (!clsma.$isEmpty($this)) {
        var tag = $this.get(0).tagName;
        if (!tag) {
            return $this;
        }
        tag = tag.toLowerCase();
        if (clsma.$inArray('td,span,label,a,b,button'.split(','), tag)) {
            $this.text(data);
        } else if (clsma.$inArray('input,select,textarea'.split(','), tag)) {
            $this.val(data);
        }
    }
    return $this;
};
Clsma.prototype.$encryptForm = function (elem, name) {
    if (!name || this.$isEmpty(name)) {
        this.$error('Debe especificar nombre de almacenamiento de los valores');
    }

    elem = this.$type(elem) === 'string' ? $(elem) : elem;
    if (this.$inArray('form,div,table'.split(','), elem.prop('tagName').toLowerCase())) {

        var $this = this;
        elem.find('input , select , textarea ').each(function () {
            $this.$$criptName = elem.attr('id') || elem.attr('name');
            $this.$$addToCryptElement.call($this, this);
        });
    }
    this.$$instance = elem;
    this[0] = elem.get(0);
    return this;
};
Clsma.prototype.$endSession = function () {
    window.top.location.href = Rutac + '/Init/LoginApplication?event=OUT';
}

Clsma.prototype.$initApplicationPage = function () {
    window.top.location.href = Rutav;
}

clsma.$createStyle = function (className, attr) {
    var stylesMade = this.$scope.$gen.stylesAdded;
    var style = stylesMade.select(function () {
        return this.styleName === className;
    })[0] || {};
    addStyles = function (className, attr) {
        var style = '/*%s*/ %s { '.StringFormat(className, className);
        for (i in attr) {
            style += (i + ':' + attr[i] + ' ;');
        }
        style += ' } /*%s*/'.StringFormat(className);
        return style;
    }

    var style_ = $('head').find('style#clsmaStyle');
    if (clsma.$isEmpty(style_)) {
        $('head').append('<style id="clsmaStyle"  />');
        style_ = $('head').find('style#clsmaStyle');
    }

    var lastAttr;
    if (!clsma.$isEmpty(style)) {
        lastAttr = Object.clone(style.attributes);
        style.attributes = $.extend(style.attributes, attr);
    } else {
        style = {styleName: className, attributes: attr, lastAttributte: {}};
        stylesMade.push(style);
    }

    style.lastAttributte = Object.clone(style.attributes);
    var result = addStyles(className, style.attributes);
    var lastAttr_ = addStyles(className, lastAttr);
    lastAttr_ = lastAttr_.replace(/\//g, '~');
    var text = style_.text();
    text = text.replace(/\//g, '~');
    text = text.replace(lastAttr_, '');
    text += result;
    text = text.replace(/\~/g, '/');
    style_.text(text);
    return this;
}



Clsma.prototype.$send = function (option) {

    if (this.$$instance) {
        this.$$serialCrypto();
        this[0].submit();
    }
};
Clsma.prototype.$repeat = function ($msg) {

    var matcher = /^\s*([\s\S]+?)\s+in\s+([\s\S]+?)(?:\s+as\s+([\s\S]+?))?(?:\s+track\s+by\s+([\s\S]+?))?\s*$/;
    console.log($msg.match(matcher));
}

function $makeDefaultConfigs(scope) {


    $()



}

(function (window, document, undefined) {


    jQuery.each("Boolean Number String Function Array Date RegExp Object Error Arguments".split(" "), function (i, name) {
        class_type[ "[object " + name + "]" ] = name.toLowerCase();
    });
    Clsma.prototype.$application = function (name, functions) {

        var app = $('[cl-app=' + name + ']');
        if (clsma.$length(app) > 1) {
            clsma.$error('Solo debe haber una aplicacion por espacio de trabajo (Nombre)');
            return;
        }

        if (clsma.$length(app) === 0) {
            clsma.$error('No eiste elemento al cual referenciar este espacio de trabajo');
            return;
        }
    };
})(window, document);
clsma.$scope.$gen = {
    $drag: {
        helper: "clone",
        revert: "invalid",
        cursor: 'move'
    },
    $drop: {
//        activeClass: "ui-state-hover",
        hoverClass: "ui-state-active"
    },
    $sortable: {
        placeholder: "ui-state-highlight"
    },
    $context: {
        menuStyle: {
            'border-radius': '5px',
            overflow: 'hidden',
            width: '200px',
            padding: 0,
            'list-style-type': 'none',
            '-webkit-box-shadow': '0px 0px 10px -1px rgba(0,0,0,0.50)',
            '-moz-box-shadow': '0px 0px 10px -1px rgba(0,0,0,0.50)',
            'box-shadow': '0px 0px 10px -1px rgba(0,0,0,0.50)',
            backgroundColor: '#f8f9f8',
        },
        itemStyle: {
            fontFamily: 'verdana',
            fontSize: 'small',
            color: 'black',
            border: 'none',
            padding: '5px 10px',
            width: '100%',
            'font-weight': 400
        },
        itemHoverStyle: {
            border: 'none',
            color: '#1d5987',
            backgroundColor: '#d0e5f5'

        }
    },
    $months: ['Enero', 'Febrero'],
    $datepicker: {
        closeText: 'Cerrar',
        prevText: '<Ant',
        nextText: 'Sig>',
        currentText: 'Hoy',
        monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
        monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
        dayNames: ['Domingo', 'Lunes', 'Martes', 'Mi?rcoles', 'Jueves', 'Viernes', 'S?bado'],
        dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mi?', 'Juv', 'Vie', 'S?b'],
        dayNamesMin: ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'S?'],
        changeMonth: true,
        showButtonPanel: true,
        buttonImageOnly: true,
        showAnim: '',
        dateFormat: 'yy-mm-dd',
        yearRange: 'c-25:c+25',
        changeYear: true
    },
    $dialog: {
        autoOpen: false,
        show: {
            effect: "fadeIn",
            duration: 300
        },
        hide: {
            effect: "fadeOut",
            duration: 500
        },
        resizable: false,
        position: ['top', 30],
        draggable: false,
        zIndex: 900,
        modal: true,
        height: 'auto',
        width: 'auto',
//        minWidth: 400
    },
    $loading: {
        msg: 'Cargando, espere por favor...!',
        styles: {
            width: '100%',
            height: '100%',
            'z-index': 1500,
            position: 'fixed',
            border: '1px solid white',
            'background-color': 'black',
            opacity: 0.4,
            filter: 'alpha(opacity = 40)',
            display: 'block',
            top: '0',
            left: '0'
        }
    },
    $imgView: {
        styles: {
            width: '80%',
            height: '80%'
        }
    },
    $MSG: {
        'delete': '¿ Desea eliminar los datos ? ',
        'save': ' ¿ Desea guardar los datos ?'

    },
    stylesAdded: []
}
