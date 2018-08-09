/* 
 * Plugin : findPrs
 * Author: Carlos Pinto Jimenez
 * Date :16/03/2016
 * Version : 1.0
 */

(function ($) {


    var methods = {
        option: function () {
            var args = argumentsArray(arguments);
            var method = args[0];
            if (!Object.isString(method)) {
                $.error('Argument[0] must be String type');
            }
            args = args.slice(1);
            if (!methods[method]) {
                $.error('Method %s doesnt exist in jQuery.findPrs plugin'.StringFormat(method));
            }
            return  $(this).each(function () {
                return methods[method].apply(this, args.slice(0));
            });

        },
        values: function () {
            var args = argumentsArray(arguments)[0];
            var inputs = $(this).data('elements');
            inputs[1].val(args[0] || ''); // Hidden
            inputs[0].val(args[1] || ''); // Shown}
            return this;

        },
        disabled: function (value) {
            var $this = $(this);

            var buttonShow = $this.data('elements')[2];

            if (value) {
                buttonShow.css(
                        {opacity: '0.4',
                            filter: 'alpha(opacity=40)',
                            'pointer-events': 'none'});
            } else {
                buttonShow.css(
                        {opacity: '1',
                            filter: 'alpha(opacity=100)',
                            'pointer-events': 'auto'}).attr('readonly', false).attr('disabled', false);
            }
            return this;
        },
        destroy: function () {
            if (!isEmpty($(this).data('findPrs'))) {
                var $this = $(this);
                $this.removeData();
                $this.removeAttr('data-finPrs');
                $this.empty();
                return this;
            }
            return this;
        }
    }

    function makeSearch(options) {

        var $this = $(this),
                element = this, datas, settings,
                id = element.id,
                identifier = getId();

        if (['div'].indexOf($this.prop('tagName').toLowerCase()) === -1) {
            $.error('Contenedor no válido, Debe ser un DIV');
        }

        datas = $this.data('findPrs');

        if (isEmpty(datas)) {
            settings = $.extend({}, true, $.fn.findPrs.defaults, $.fn.findPrs.defaults2, options);
        } else {
            return this;
        }

        settings.identifier = identifier;
        var input, hidden, button, label;

        if (isEmpty(settings.pkey)) {
            $.error('No se ha definido key del elemento ');
        }

        input = jQuery('<input type="text" onfocus="javascript:return;">');
        button = jQuery('<input type="button">');
        hidden = jQuery('<input type="hidden">');
        label = $('<div style="' + (settings.label.style || '')
                + ' display:inline-table;margin:0 5px 0 0 ; text-align:right; min-width:60px;font-weight:600" />');
        if (settings.label && jQuery.trim(settings.label.class) !== '') {
            label.addClass(settings.label.class);
        }

        var pk = settings.pkey;
        var configs = {
            id: pk + 'Shw',
            name: pk + 'Shw',
            size: settings.size,
            readonly: 'readonly',
            title: settings.title,
            class: 'input-control'
        };

        input.attr(configs);

        configs = {
            id: pk,
            name: pk,
            title: settings.title
        };

        hidden.attr(configs);

        configs = {
            id: 'btn' + pk + 'Shw',
            name: 'btn' + pk + 'Shw',
            onmouseover: 'this.style.cursor=\'pointer\''
        };

        var css = {
            'background-image': 'url(' + Rutav + '/vista/img/search_plugin.png)',
            'background-color': '#fff',
            'background-repeat': 'no-repeat',
            'background-position': '2px 1px',
            'border': '1px solid #ccc',
            cursor: 'pointer',
            height: '18px',
            'padding-left': '12px',
            'padding-top': '10px',
            'vertical-align': 'middle',
            'border-radius': ' 0 3px 3px 0',
            'margin-left': '-1px',
//            float: 'left'
        };

        var attrs = $this.attr('data-hidden');
        var temps = validateValue(attrs);

        hidden.val(temps);

        attrs = $this.attr('data-show');
        temps = validateValue(attrs);

        input.val(temps);

        $this.html('');

        button.css(css).attr(configs);

        input.appendTo($this);
        button.appendTo($this);
        hidden.appendTo($this);

        if (settings.label && jQuery.trim(settings.label.name) !== '') {

            label.html(settings.label.name);
            label.prependTo($this);

        }

        $this.data('findPrs', settings);
        $this.attr('data-finPrs', '');
        $this.data('elements', [input, hidden, button]);

//        findPrs.call(element, settings);

        button.click(function () {
            $.fn.findPrs.open.call(element);
        });

        $(element).bind('reset', clearInput);

        return this;
    }
    function clearInput() {

        var inputs = $(this).data('elements');
        if (!inputs) {
            return;
        }

        inputs[0].val('');
        inputs[1].val('');

    }

    function findPrs(options) {
        var $this = $(this),
                element = this, datas, settings;


        var container, divContent, divContentTable, table, tr, td, a, input, identifier, div_message;

        identifier = options.identifier;

        $('#container' + identifier).remove();
        container = $('<div/>').attr({id: 'container' + identifier}).appendTo('body').css({display: 'none'}).addClass('form-control');
        divContent = $('<div/>').attr({id: 'divContent' + identifier}).addClass('contentInput')
                .css({padding: '10px'})
                .appendTo(container);
        divContentTable = $('<div/>').attr({id: 'divContentTable' + identifier}).addClass('containerTable').appendTo(container);
        div_message = $('<div/>').attr({id: 'divMessage' + identifier}).addClass('containerMessage').appendTo(container);
        div_message.html(writeInfoMsg(' - Presione  Enter sobre algún campo para Búscar.'))

        table = $('<table id="tblPrs' + identifier + '" style="width:100%"/>').appendTo(divContent);

        tr = $('<tr/>').appendTo(table);
        td = $('<td/>').addClass('trcode');
        td.html('<b>Código</b>').css({'text-align': 'right'}).appendTo(tr);
        td = $('<td/>').addClass('trcode').css({'text-align': 'left'});
        input = $('<input type="text" data-valid="numeros" onkeypress="return validSQLINumber(event)"/>').attr({id: 'codprs' + identifier, name: 'codprs' + identifier, size: 20, maxlength: 13}).appendTo(td);
        td.appendTo(tr);

        td = $('<td/>');
        td.html('<b>Identificación</b>').css({'text-align': 'right'}).appendTo(tr);
        td = $('<td/>').css({'text-align': 'left'});
        input = $('<input type="text" data-valid="numeros" onkeypress="return validSQLINumber(event)"/>').attr({id: 'nriprs' + identifier, name: 'nriprs' + identifier, size: 20, maxlength: 13}).appendTo(td);
        td.appendTo(tr);
        // ------------------------------------------------------------->

        tr = $('<tr/>').appendTo(table);
        td = $('<td/>');
        td.html('<b>Nombres </b>').css({'text-align': 'right'}).appendTo(tr);
        td = $('<td/>').css({'text-align': 'left'});
        input = $('<input type="text" data-valid="letras" onkeypress="return validSQLILetters(event)" />').attr({id: 'nomprs' + identifier, name: 'nomprs' + identifier, size: 50, maxlength: 50}).appendTo(td);
        td.appendTo(tr);

        // ------------------------------------------------------------->    

        tr = $('<tr/>').appendTo(table);
        td = $('<td/>');
        td.html('<b>Apellidos </b>').css({'text-align': 'right'}).appendTo(tr);
        td = $('<td/>').css({'text-align': 'left'});
        input = $('<input type="text" data-valid="letras" onkeypress="return validSQLILetters(event)" />').attr({id: 'apeprs' + identifier, name: 'apeprs' + identifier, size: 50, maxlength: 50}).appendTo(td);
        td.appendTo(tr);

        // ------------------------------------------------------------->    


        tr = $('<tr class="basinfo"/>').appendTo(table);
        td = $('<td/>');
        td.html('<b>Nro. Programa </b>').css({'text-align': 'right'}).appendTo(tr);
        td = $('<td/>').css({'text-align': 'left'});
        input = $('<input type="text" data-valid="numeros" onkeypress="return validSQLINumber(event)" />').attr({id: 'nropgm' + identifier, name: 'nropgm' + identifier, size: 10, maxlength: 5}).appendTo(td);
        input = $('<input type="hidden"/>').attr({id: 'idepgm' + identifier, name: 'idepgm' + identifier}).appendTo(td);
        td.appendTo(tr);

        // ------------------------------------------------------------->   

        tr = $('<tr class="basinfo"/>').appendTo(table);
        td = $('<td/>');
        td.html('<b>Programa </b>').css({'text-align': 'right'}).appendTo(tr);
        td = $('<td/>').css({'text-align': 'left'});
        input = $('<input type="text" data-valid="letranumero" onkeypress="return validSQLILettersAndNumber(event)"/>').attr({id: 'nompgm' + identifier, name: 'nompgm' + identifier, size: 45, maxlength: 50}).appendTo(td);
        td.appendTo(tr);

        td = $('<td class="basinfo"/>');
        td.html('<b>Seccional </b>').css({'text-align': 'right'}).appendTo(tr);
        td = $('<td/>').css({'text-align': 'left'});
        input = $('<input type="text" data-valid="letranumero" />').attr({id: 'nomscn' + identifier, name: 'nomscn' + identifier, size: 30, maxlength: 50}).appendTo(td);
        td.appendTo(tr);

        // ------------------------------------------------------------->   


        tr = $('<tr/>').appendTo(table);
        td = $('<td/>');
        td.html('<b>Tipo de persona </b>').css({'text-align': 'right'}).appendTo(tr);
        td = $('<td/>').css({'text-align': 'left'});
        getCombo(options, identifier).appendTo(td);
        td.appendTo(tr);

        // ------------------------------------------------------------->   

        tr = $('<tr/>').appendTo(table);
        td = $('<td/>');
        td.html('<b>Estado de persona </b>').css({'text-align': 'right'}).appendTo(tr);
        td = $('<td/>').css({'text-align': 'left'});
        getCombo2(options, identifier).appendTo(td);
        td.appendTo(tr);
        
        // ------------------------------------------------------------->

        tr = $('<tr/>').appendTo(table);
        td = $('<td colspan="6"/>').appendTo(tr);
        td.append('<hr/>');

        // ------------------------------------------------------------->   
        table = $('<table id="tblListPrs' + identifier + '"/>').appendTo(divContentTable);
        tr = $('<tr/>').appendTo(table);
        td = $('<td colspan="6"/>').appendTo(tr);
        td.append('<div style="width:100%;" id="listPrs' + identifier + '" data-parent="' + $this[0].id + '" class="tablePrsList"/>');
        // ------------------------------------------------------------->   
        $($this).data('container', container);
        return $this;
    }

    function getCombo(settings, id) {
        var arrTrc = settings.arrTrc;
        var tpoprs = settings.tpoprs;
        if ($.type(tpoprs) !== 'array') {
            tpoprs = [tpoprs];
        }

        if (!isEmpty(tpoprs)) {
            arrTrc = arrTrc.select(function () {
                return tpoprs.has(this.tpotrc);
            });
        }

        var combo = $('<select/>').attr({id: 'tpoprs' + id});
        var option;
        arrTrc.each(function () {
            option = $('<option value="' + this.tpotrc + '" />').text(this.value).appendTo(combo);
        });
        return combo;
    }
    ;
    
    function getCombo2(settings, id) {
        var arrTrc2 = settings.arrTrc2;
        var stdprs = settings.stdprs;
        if ($.type(stdprs) !== 'array') {
            stdprs = [stdprs];
        }

        if (!isEmpty(stdprs)) {
            arrTrc2 = arrTrc2.select(function () {
                return stdprs.has(this.stdtrc);
            });
        }

        var combo = $('<select/>').attr({id: 'stdprs' + id});
        var option;
        arrTrc2.each(function () {
            option = $('<option value="' + this.stdtrc + '" />').text(this.value).appendTo(combo);
        });
        return combo;
    }
    ;

    function makeInputIdentifiers() {
        var $this = $(this);
        var identifier = $this.data('findPrs').identifier;
        var inputs = {
            codprs: ['codprs', document.getElementById('codprs' + identifier), 'codprs' + identifier],
            nriprs: ['nriprs', document.getElementById('nriprs' + identifier), 'nriprs' + identifier],
            nomprs: ['nomprs', document.getElementById('nomprs' + identifier), 'nomprs' + identifier],
            apeprs: ['apeprs', document.getElementById('apeprs' + identifier), 'apeprs' + identifier],
            nropgm: ['nropgm', document.getElementById('nropgm' + identifier), 'nropgm' + identifier],
            idepgm: ['idepgm', document.getElementById('idepgm' + identifier), 'idepgm' + identifier],
            nomscn: ['nomscn', document.getElementById('nomscn' + identifier), 'nomscn' + identifier],
            tpoprs: ['tpoprs', document.getElementById('tpoprs' + identifier), 'tpoprs' + identifier],
            stdprs: ['stdprs', document.getElementById('stdprs' + identifier), 'stdprs' + identifier]
        };
        var input;
        O(inputs).each(function () {
            input = this[1];
            $(input).bind('keypress', [input[0], input[2]], function (event) {

                if (event.which === 13 || event.keyCode === 13)
//                    if (!isEmpty(this.value))
                    $.fn.findPrs.search.call(this, event.data, $this);
            });
        });

        $(this).data('inputsource', inputs);
    }

    var validateValue = function (valor) {
        valor = (valor === null) ? '' :
                (valor === 'null') ? '' :
                (valor === undefined) ? '' : valor;
        return valor;
    };

    function getId() {
        return parseInt(new Date().getTime() + '' + Math.random() * 1000);
    }

    function getInfoInput(element) {

        var inputs = $(this).data('inputsource');
        var result;
        O(inputs).each(function () {
            if (this.value[2] === element) {
                result = this.value;
                return false;
            }
        });
        return result || [];
    }


    function makeResult(data) {

        if (!isEmpty(data)) {
            if (data.exito === 'FOUND') {
                fillComponents.call(this, data);
            } else {
                printTable.call(this, data);
            }

        }
    }
    function fillComponents(data) {

        var inputs = $(this).data('inputsource');
        var settings = $(this).data('findPrs');
        var element = this;
        var auxData = {};
        $(this).removeData('record');

        var elemen;
        var inName;
        var datos;
        O(data).each(function (a, b) {
            inName = this.key === 'codprs' ? 'codigo' : this.key;
            auxData[ inName.toUpperCase() ] = this.value;

        });

        O(inputs).each(function () {
            elemen = this.value[1];
            inName = this.value[0] === 'codprs' ? 'codigo' : this.value[0];
            datos = data[inName];
            if (!isEmpty(datos)) {
                elemen.value = datos;
            }
        });
        var findInput = $(this).data('elements');
        findInput[1].val(auxData.CODIGO);
        findInput[0].val(auxData.APEPRS + ' ' + auxData.NOMPRS);
        $(this).data('record', auxData);

        if (typeof settings.onSelect === 'function') {
            settings.onSelect.call(element, auxData);
        }
        $.fn.findPrs.close.call(this);


    }
    function printTable(data) {
        var settings = $(this).data('findPrs');
        $('#listPrs' + settings.identifier).empty().html(data.tabla);
    }

    function validCombo($parent) {

        var value = this.value;
        if (['BAS', 'DBI'].has(value)) {
            $('.basinfo').show();
            $('.trcode').show();
        } else {
            $('.trcode').hide();
            $('.basinfo').hide();
        }
        clearForm.call($parent);
        $($parent).data('container').dialog('option', 'position', ['center', 50]);
    }

    function clearForm() {
        var inputs = $(this).data('inputsource');
        O(inputs).each(function () {
            if (this.value[0] !== 'tpoprs')
                this.value[1].value = '';
        });
        $(this).data('container').find('.tablePrsList').empty();

    }



    $.fn.findPrs = function ( ) {
        var args = $a(arguments);
        var meto = args[0];
        args = args.slice(1);
        if (!isEmpty(meto)) {
            if (Object.isString(meto)) {
                if (isEmpty(methods[meto])) {
                    $.error('Método [ ' + meto + ' ] no esta definido en este plugin');
                }
                return  this.each(function () {
                    return methods[meto].apply(this, args);
                });
            } else if (Object.canbehash(meto)) {
                return  this.each(function () {
                    return makeSearch.call(this, meto);
                });
            }
        } else {
            return  this.each(function () {
                return makeSearch.call(this);
            });
        }


    };

    $.fn.findPrs.search = function (infoInput, parent) {

        var valfld = this.value;
        var nomfld = infoInput[0];
        var aux = '';
        var datas = parent.data('findPrs');
        var container = parent.data('container');



        $('.contentInput', container).find('input[type=text]').not(this).each(function () {
            if (!isEmpty(this.value)) {
                aux += ' and lower( ' + getInfoInput.call(parent, this.id)[0] + ' ) like lower( \'%' + this.value + '%\' )';
            }
        });

        genericAjax({
            url: Rutac + '/Adm/SearchAllPrs',
            data: {
                events: 'SCHPRS',
                nomfld: nomfld,
                valfld: valfld,
                tpoprs: getValue('tpoprs' + datas.identifier),
                stdprs: getValue('stdprs' + datas.identifier),
                filtros: "!" + aux + "!",
                'function': 'onSelectPerson'
            },
            loading: true
        }).then(function (data) {
            data = $.parseJSON(data);
            makeResult.call(parent, data);
        }).done(function () {
//            parent.dialog('option', 'position', ['center', 50]);
        });
        return this;
    };
    $.fn.findPrs.open = function () {
        var datas = $(this).data('findPrs');
        if (!datas) {
            return this;
        }

        if (typeof datas.beforeSearch === 'function') {
            if (datas.beforeSearch.call(this, datas) === false) {
                return false;
            }
        }

        findPrs.call(this, datas);
        makeInputIdentifiers.call($(this));

        var container = $(this).data('container');
        container.attr('title', 'Búsqueda de personas/usuarios');

        container.dialog(
                {
                    width: 'auto',
                    height: 'auto',
                    close: function () {
                        $(this).dialog('destroy');
                        $(this).remove();
                    },
                    'position': ['center', 50],
                    modal: true,
                    draggable: true
                });
        container.dialog('option', 'position', ['center', 50]);
        Remove_Caracteres(container[0].id);
        var $this = this;
        $('#tpoprs' + datas.identifier).change(function () {
            validCombo.call(this, $this);
        }).trigger('change');

        container.dialog('open');
        return this;
    };

    $.fn.findPrs.close = function () {

        var container = $(this).data('container');
        var settings = $(this).data('findPrs');
        var element = this;
        var data = $(this).data('record');
        if (isEmpty(container)) {
            return;
        }
        container.dialog('close');

        window.setTimeout(function () {
            if (typeof settings.onClose === 'function') {
                settings.onClose.call(element, data);
            }
        }, 200);

    };

    $.fn.findPrs.defaults = {
        pkey: null,
        size: 30,
        title: 'Búsqueda de personas', arrTrc: [
            {tpotrc: 'MPY', value: 'Empleado'},
            {tpotrc: 'BAS', value: 'Estudiante'},
            {tpotrc: 'EGR', value: 'Egresado'},
            {tpotrc: 'PRF', value: 'Docente'},
            {tpotrc: 'PRS', value: 'Persona'},
            {tpotrc: 'TRC', value: 'Tercero'},
            {tpotrc: 'SPR', value: 'Proveedor'},
            {tpotrc: 'USR', value: 'Usuario'}
        ],
        label: {
            name: null,
            style: null
        },
        onSelect: function () {
        },
        onClose: function () {
        },
        beforeSearch: function () {
        }
    };
    
    $.fn.findPrs.defaults2 = {
        pkey: null,
        size: 30,
        title: 'Búsqueda de personas', arrTrc2: [
            {stdtrc: 'ACT', value: 'Activo'},
            {stdtrc: 'INA', value: 'Inactivo'}
        ],
        label: {
            name: null,
            style: null
        },
        onSelect: function () {
        },
        onClose: function () {
        },
        beforeSearch: function () {
        }
    };

})(jQuery);

function  onSelectPerson(id) {

    var data = $('#jqSelPrs').getRowData(id);


    var parent = $('#' + $(this).data('parent'));

    parent = parent.parent();
    parent = $('#' + parent.data('parent'));

    var settings = parent.data('findPrs');
    $(parent).removeData('record');
    $(parent).data('record', data);

    var elemnets = $(parent).data('elements');
    var inputShw = elemnets[0];
    var hidden = elemnets[1];

    hidden.val(data.CODIGO);
    inputShw.val(data.APEPRS + ' ' + data.NOMPRS);

    if (typeof settings.onSelect === 'function') {
        settings.onSelect.call(parent, data);
    }


    $.fn.findPrs.close.call(parent);

}
