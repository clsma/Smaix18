;
/*
 * VENTANA DE BÚSQUEDAS - Carlos Pinto 21/03/2015
 * @param {type} $
 * @returns {Search}
 */

(function (jQuery) {



    var methods = {
        init: function (options) {
            var inputShow,
                    buttonShow,
                    buttonClean,
                    inputHidden,
                    $this,
                    label,
                    element,
                    dataElement = {},
                    settings = {},
                    privates = {},
                    temps;


            $this = jQuery(this),
                    element = this;

            element.getSql = function () {
                return this.Consulta;
            };

            element.getConfig = function () {
                return settings;
            };

            element.getValues = function () {
                if (!isEmpty($this.data('search'))) {
                    var elemen = $this.data('elements');
                    return [elemen[2].val(), elemen[0].val()];
                }
                return [];
            };

            element.addOptions = function (object) {
                if (jQuery.isPlainObject(object)) {
                    var def = $this.data('search').defaults;
                    $this.data('search').defaults = jQuery.extend({}, def, object);
                }
            };

            return element.each(function () {

                $this = jQuery(this),
                        element = this;

                dataElement = $this.data('search');

                if (typeof (dataElement) === 'undefined') {
                    settings = jQuery.extend({}, jQuery.fn.search.searchVars.defaults, options);
                    privates = jQuery.fn.search.searchVars.privates;
                    privates.isField = true;
                    dataElement = {defaults: settings, private: privates};

                } else {
                    settings = jQuery.extend({}, dataElement.defaults, options);
                    privates.isField = true;
                    privates = dataElement.private;
                    return this;
                }

                dataElement.owner = $this;
                $this.data('search', dataElement);

                if (settings.nrosch === null || settings.pkey === null) {
                    show_message('Debe definir Key de busqueda', '', 'ERROR');
                    return;
                }

                inputShow = jQuery('<input type="text" onfocus="javascript:return;" class="searchPlugin">');
                buttonClean = jQuery('<input type="button" class="searchPlugin">');
                inputHidden = jQuery('<input type="hidden" class="searchPlugin">');
                buttonShow = jQuery('<input type="button" class="searchPlugin">');


                label = jQuery('<div style="' + (settings.label.style || '')
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
                    sttmnt: settings.nrosch
                };

                inputShow.attr(configs).addClass('input-control');

                configs = {
                    id: pk,
                    name: pk,
                    title: settings.title,
                };

                inputHidden.attr(configs);

                configs = {
                    id: 'btn' + pk + 'Shw',
                    name: 'btn' + pk + 'Shw',
                    onmouseover: 'this.style.cursor=\'pointer\'',
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
//                    float: 'left'
                };

                var attrs = $this.attr('data-hidden');
                temps = validateValue(attrs);

                inputHidden.val(temps);

                attrs = $this.attr('data-show');
                temps = validateValue(attrs);

                inputShow.val(temps);

                $this.html('');

                buttonShow.css(css).attr(configs);

                css = {
                    'background-image': 'url(' + Rutav + '/vista/img/clean_search_icon.png)',
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
                    'margin-left': '-1px'
                };

                inputShow.appendTo($this);
                buttonShow.appendTo($this);

                if (settings.buttonClean) {
                    buttonClean.css(css).appendTo($this);
                    buttonClean.click([settings, $this], function (e) {
                        var data = e.data;
                        (data[1]).trigger('reset');
                        if (Object.isFunction((data[0] || {}).onSearchClean)) {
                            (data[0] || {}).onSearchClean.call(data[1]);
                        }
                    });
                }

                inputHidden.appendTo($this);

                if (settings.label && jQuery.trim(settings.label.name) !== '') {

                    label.html(settings.label.name);
                    label.prependTo($this);

                }

                $this.data('elements', [inputShow, buttonShow, inputHidden, buttonClean]);
                $this.attr('data-search', '');

                var openSearch = function () {
                    this.data('search').private.isField = true;
                    showNewSearch.call(this, this.data('search'));
                };


                buttonShow.on('click', $this, function (e) {
                    openSearch.call(e.data); //e.data = this
                    e.preventDefault();
                    e.stopPropagation();
                });

                $this.bind('reset', function () {
                    resetData.call(this);
                });

                $this.bind('setValues', function (value) {
                    value = arguments[1];
                    setValues.call(this, value);
                });


            });

        }
        , option: function (option, value) {
            if (jQuery(this).length === 0) {
                return this;
            }

//                    attr_ = $this.attr();
            return this.each(function () {
                var buttonShow = jQuery(this).data('elements')[1],
                        buttonClean = jQuery(this).data('elements')[3],
                        inputShow = jQuery(this).data('elements')[0],
                        inputhidden = jQuery(this).data('elements')[2],
                        $this = jQuery(this),
                        id = $this.attr('id');

                switch (option) {
                    case 'disabled':

                        var elements = $this.data('elements');
                        var buttonShow = elements[1];
                        var buttonClean = elements[3];

                        if (value) {
                            buttonShow.css(
                                    {opacity: '0.4',
                                        filter: 'alpha(opacity=40)',
                                        'pointer-events': 'none'});
                            buttonClean.css(
                                    {opacity: '0.4',
                                        filter: 'alpha(opacity=40)',
                                        'pointer-events': 'none'});
                        } else {
                            buttonShow.css(
                                    {opacity: '1',
                                        filter: 'alpha(opacity=100)',
                                        'pointer-events': 'auto'}).attr('readonly', false).attr('disabled', false);
                            buttonClean.css(
                                    {opacity: '1',
                                        filter: 'alpha(opacity=100)',
                                        'pointer-events': 'auto'}).attr('readonly', false).attr('disabled', false);
                        }
                        break;
                    case 'values':
                        if (value || value !== null) {
                            if (jQuery.type(value) === 'array') {

                                if (value.length > 0) {
                                    inputShow.val(validateValue(value[1]));
                                    inputhidden.val(validateValue(value[0]));
                                }

                            } else if (jQuery.type(value) === 'string') {
                                inputShow.val(validateValue(value));
                                inputhidden.val(validateValue(value));
                            }

                        }
                        break;
                    case 'destroy':

                        if (jQuery.fn.search) {

                            $this.removeData();
                            $this.empty();

                            var bef = $this.prev();
                            var parent = $this.parent();
                            var new_ = jQuery('<div />').attr('id', id);

                            if (bef.length > 0) {
                                new_.insertAfter(bef);
                            } else {
                                parent.append(new_);
                            }
                            $this.remove();
                            return new_;


                        }
                        break;

                }//end switch
            });
//            return this;
        }//end options
    };

    var validateValue = function (valor) {

        valor = (valor === null) ? '' :
                (valor === 'null') ? '' :
                (valor === undefined) ? '' : valor;
        return valor.replace(/&#39;/, '\'').replace(/&#34;/, '"');
        ;
    };

    var importScript = function () {
        var script = jQuery('#jqScript');
        if (script.length <= 0) {
            /*
             var script = document.createElement('script');
             script.id='jqScript';
             script.src=Rutav+'/vista/js/jquery.jqGrid-4.5.4/js/i18n/grid.locale-es.js';
             script.charset='UTF-8';
             jQuery(document).find('head').get(0).appendChild(script);
             
             script.onload=function(){
             console.log('creoScript');
             }
             script = document.createElement('script');
             script.id='jqScript';
             script.src=Rutav+'/vista/js/jquery.jqGrid-4.5.4/js/jquery.jqGrid.min.js';
             script.charset='UTF-8';
             jQuery(document).find('head').get(0).appendChild(script);
             
             script = document.createElement('link');
             script.id='jqScript';
             script.href=Rutav+'/vista/js/jquery.jqGrid-4.5.4/css/ui.jqgrid.css';
             script.rel='stylesheet';
             script.type='text/css';
             jQuery(document).find('head').get(0).appendChild(script);
             */
            show_message('Debes importar la libreria del Jqgrid');
            jQuery.error('Debes importar la libreria del Jqgrid');
            return;

        }

    };

    var getData = function (id) {
        var data_ = $(this.searchGrid).getRowData(id);
        var retu = {};
        for (i in data_) {
            retu[i] = jQuery(data_[i]).text();
        }
        return retu;
    };

    var setValues = function (value) {

        var dataElement_ = this.data('search');
        var configs = dataElement_.defaults;
        $this = this;
        var nrosch = configs.nrosch,
                pkey = configs.pkey,
                params = configs.params;

        genericAjax({
            url: Rutac + '/Build/SearchNew?state=VALUES',
            data: {nrosch: nrosch, pk_val: value},
            done: function (data) {
                data = jQuery.parseJSON(data);

                if (data.exito === 'EXITO') {

                    var el = jQuery($this);
                    var elems = el.data('elements');
                    elems[0].val(data.data_show);
                    elems[2].val(data.data_hidden);

                } else {
                    var el = jQuery($this);
                    var elems = el.data('elements');
                    elems[0].val('');
                    elems[2].val('');
                }

            },
            loading: false
        });
        return this;
    };

    var showNewSearch = function (dataElement_) {
        var el = this;


        importScript();

        var configs = dataElement_.defaults;

        var before = configs.beforeSearchOpen.call(el, configs) === false
                ? false : true;

        if (!before) {
            return;
        }

        var nrosch = configs.nrosch,
                pkey = configs.pkey,
                isHtml = configs.isHtml,
                params = configs.params,
                generalFilter = configs.generalFilter,
                rowsNum = configs.rowsNum;


        id = 'win' + nrosch + ($.isPlainObject(el) ? '' : el[0].id);

        var id = newWin(id, 'auto', 'auto', 'VENTANA DE BÚSQUEDAS');
        el.formModal = '#' + id;

//        this.idWin = id;
        el.Consulta = '';
        var datasParams = "";
        var bool = true;

        if (isHtml & params !== null) {

            var spli = jQuery.type(params) === 'string'
                    ? params.split('~') : params;

            for (i in spli) {
                var val = (spli[i].indexOf('~') !== -1) ?
                        spli[i].replace(/~/g, '') :
                        jQuery('#' + spli[i]).val();

                datasParams += ',' + val;
            }

            datasParams = datasParams.substring(1);

        } else if (params !== null) {

            var spli = jQuery.type(params) === 'string'
                    ? params.split('~') : params;

            for (i in spli) {
                var val = (spli[i].indexOf('~') !== -1) ?
                        spli[i].replace(/~/g, '') :
                        spli[i];
                datasParams += ',' + val;
            }

            datasParams = datasParams.substring(1);
        }

        if (!bool) {

        }

        genericAjax({
            url: Rutac + '/Build/SearchNew?state=INIT',
            data: {
                nrosch: nrosch,
                p_key: pkey,
                params: datasParams,
                generalFilter: generalFilter,
                rowsNum: rowsNum
            },
            done: function (data) {
                data = jQuery.parseJSON(data);

                if (data.exito === 'OK') {
                    makeGrid.call(el, data.lista, data.modelCols, data.modelTitl, data.descripcion, dataElement_);
                    $(el.formModal).dialog('open');
                } else {

                    dataElement_.private.executeCallback = false;

                    var errorSearch = {};
                    var spanError = {};

                    var msg = isEmpty(configs.msg) ? data.msg : configs.msg;
                    if (data.exito === 'NODATA') {

                        errorSearch = {
                            'text-align': 'center',
                            diaplay: 'block',
                            height: '100%',
                            position: 'relative',
                            width: '500px',
                            padding: '10px',
                            'background-color': 'background-color: rgba(238,255,223,1);',
                            'border-radius': '5px'
                        };

                        spanError = {
                            'font-size': '14px',
                            'font-family': '"Comic Sans MS", cursive, sans-serif',
                            'font-weight': 'bold',
                            color: '#3d6850'
                        };


                        if (Object.isFunction(configs.onNoDataFound)) {
                            try {
                                var msg = configs.onNoDataFound.call(el, configs);
                            } catch (e) {
                                msg:'La consulta no ha generado resultados.';
                            }
                        } else if (Object.isString(configs.onNoDataFound)) {
                            msg = configs.onNoDataFound;
                        }

                        if (isEmpty(msg)) {
                            msg = data.msg;
                        }
                    } else if (data.exito === 'ERROR') {

                        errorSearch = {
                            'text-align': 'center',
                            diaplay: 'block',
                            height: '100%',
                            width: '500px',
                            padding: '10px',
                            'background-color': 'background-color: rgba(255, 179, 179,.2);'
                        };

                        spanError = {
                            'font-size': '14px',
                            'font-family': '"Comic Sans MS", cursive, sans-serif',
                            'font-weight': 'bold',
                            color: 'red',
                            'text-shadow': '1px 1px 1px solid black'
                        };

                    }

                    jQuery('<div id="errorSearch"></div>').appendTo(el.formModal);
                    jQuery('<center><span id="spanError" >' + msg + '</span></center>').appendTo('#errorSearch');
                    jQuery('#errorSearch').css(errorSearch);
                    jQuery('#spanError').css(spanError);

                    makeForm.call(el, dataElement_);
                }

            },
            loading: configs.loading
        });
    };

    var makeForm = function (dataElement_) {
        var el = this;

        try {
            $(el.formModal).dialog("destroy");
        } catch (ex) {
        }

        $(el.formModal).dialog({
            bgiframe: true,
            autoOpen: true,
            resizable: false,
            position: ['center', 20],
            modal: true,
            width: 'auto',
            heigth: 'auto',
            zIndex: 99999999,
            close: function () {
                if (!jQuery.isPlainObject(el))
                    dataElement_ = jQuery(el).data('search');

                if (dataElement_.private.executeCallback) {
                    dataElement_.defaults.onSearchClose.call(el);
                    dataElement_.private.executeCallback = false;
                }
                jQuery(this).dialog('destroy');
                jQuery(this).remove();
            },
            buttons: {
                "Salir": function () {
                    jQuery(this).dialog('close');

                }
            }
        });
    };

    var makeGrid = function (arrData, arrcolModel, arrTitles, title, dataElement_) {
        var el = this;
        if (!jQuery.isPlainObject(el))
            dataElement_ = jQuery(el).data('search');

        var opt = {};
        var option = [];
        var hidens = dataElement_.defaults.hideColumn;
        for (i in arrcolModel) {
            arrcolModel[i].formatter = function (a, b, c) {
                return '<a href=\"#\" style="text-decoration:none" id="' + b.rowId + "~" + a +
                        '" onclick=\"javascript:jQuery(this).parent().parent().click()">' + a + '</a>';
            };
            if (isEmpty(hidens)) {
                option.push({
                    value: arrcolModel[i].name,
                    text: arrTitles[i]
                });
            }
            for (j in hidens) {
                if (hidens[j] === arrcolModel[i].name) {
                    arrcolModel[i].hidden = true;
                } else {
                    option.push({
                        value: arrcolModel[i].name,
                        text: arrTitles[i]
                    });
                }
            }
        }
        opt.options = option;

        var table = $('<table />').attr({id: 'filterSpace'}).appendTo(el.formModal);
        var tr = $('<tr />').appendTo(table);


        if (dataElement_.defaults.generalFilter === true) {
            dataElement_.filterOptions = opt;
            makeFilter.call(el, dataElement_, tr);
        }
        tr = $('<tr />').appendTo(table);
        var td = $('<td colspan="3"/>').appendTo(tr);
        var searchGrid = jQuery('<table></table>').attr('id', 'gridSearch').appendTo(td);
        var pagerSearch = jQuery('<div></div>').attr('id', 'pagerSearch').appendTo(td);

        el.searchGrid = '#gridSearch';




        var rows = dataElement_.defaults.rowNum;

        searchGrid.jqGrid('GridUnload');
        searchGrid.jqGrid({
            datatype: "local",
            colNames: arrTitles,
            colModel: arrcolModel,
            loadonce: true,
            width: 'auto',
            height: 200,
            rowNum: rows,
            rowList: [10, 20, 30, 50, 500, 1000, 100000],
            pager: pagerSearch,
            viewrecords: true,
            ignoreCase: true,
            hidegrid: false,
            caption: title,
            onSelectRow: function (id) {

                var data = getData.call(el, id);
                setResultSearch.call(el, dataElement_, data);
                var resultFunc = dataElement_.defaults.onSelectRow.call(el, data);
                if (resultFunc === false)
                    setResultSearch.call(el, dataElement_, {});

                dataElement_.private.executeCallback = true;

                $(el.formModal).dialog("close");

            }
        });

        var wid = searchGrid.jqGrid('getGridParam', 'width');

        searchGrid.jqGrid('setGridWidth', wid + 150, true);
        if (!dataElement_.defaults.generalFilter) {
            searchGrid.jqGrid('filterToolbar', {defaultSearch: 'cn', searchOnEnter: false});
        }
        searchGrid.jqGrid('setGridParam', {data: arrData}).trigger('reloadGrid');
        makeForm.call(el, dataElement_);
    };

    var makeFilter = function (data, parent) {
        var el = this;
        parent.addClass('form-control');
        var input = jQuery('<input/>');
        var td = jQuery('<td/>').css({'text-align': 'right'})
                .addClass('lblReq').text('Filtrar por :').appendTo(parent);

        td = jQuery('<td/>')
                .css({'text-align': 'left'})
                .appendTo(parent);

        var select = jQuery('<select/>')
                .attr({id: 'selectFilter' + data.defaults.pkey})
                .appendTo(td);


        createOptions(select, data.filterOptions);

        td.css({width: (select.width() + 5)});

        td = jQuery('<td/>')
                .css({'text-align': 'left'})
                .appendTo(parent);

        input.attr({
            id: 'filter' + data.defaults.pkey,
            name: 'filter' + data.defaults.pkey,
            type: 'text',
            size: '30'
        }).appendTo(td);

        input.bind('keypress', data, function (e) {
            if ((e.keyCode || e.which) === 13) {
                filter.call(this, e, e.data);
            }
        });

        select.bind('change', data, function (e) {
            filter.call(input, e, e.data);
        });
    };

    var filter = function (e, data) {

        var el = this;


        var configs = data.defaults;

        var nrosch = configs.nrosch,
                pkey = configs.pkey,
                isHtml = configs.isHtml,
                params = configs.params,
                rowsNum = configs.rowsNum;
        var datasParams = "";

        var spli = jQuery.type(params) === 'string'
                ? params.split('~') : params;

        for (i in spli) {
            var val = (spli[i].indexOf('~') !== -1) ?
                    spli[i].replace(/~/g, '') :
                    (isHtml ? jQuery('#' + spli[i]).val() : spli[i]);

            datasParams += ',' + val;
        }

        datasParams = datasParams.substring(1);


        genericAjax({
            url: Rutac + '/Build/SearchNew?state=FILTER',
            data: {
                nrosch: nrosch,
                p_key: pkey,
                params: datasParams,
                camsch: $('#selectFilter' + data.defaults.pkey).val(),
                filter: $(el).val(),
                rowsNum: rowsNum
            },
            done: function (dataresult) {
                dataresult = jQuery.parseJSON(dataresult);
                $(data.owner.searchGrid).clearGridData().jqGrid('setGridParam', {data: dataresult}).trigger('reloadGrid');
            }
        });
    }

    var setResultSearch = function (configs, data) {
        var el = this;
        var hide = jQuery('#' + configs.defaults.pkey),
                show = jQuery(el).find('#' + configs.defaults.pkey + 'Shw', el);

        var valor;


        var aux = new Array();

        for (i in data) {
            aux.push(data[i]);
        }

        if (!jQuery.isPlainObject(el)) {
            configs = jQuery(el).data('search');
            hide = jQuery(el).find('#' + configs.defaults.pkey);
        }

        hide.val(aux[0]);

        if (configs.private.isField) {

            if (aux.length > 1)
                show.val(aux[1]);
            else
                show.val(aux[0]);
        }


    };

    v_wins = 0;
    var newWin = function (id, width, heigth, title) {
        var html = '';
        var style = 'style="width:' + width + ';height:' + heigth + '"';
        var id_ = '#' + id + (v_wins - 1);
        var ids = id + v_wins;

        if (jQuery(id_).size() > 0)
            jQuery(id_).remove();

        html = '<div id="' + ids + '" ' + style + ' title="' + title + '"></div>';

        jQuery('body').append(html);
        v_wins++;

        return ids;
    };

    jQuery.fn.extend({
        search: function ()
        {
            var method = arguments[0];

            if (methods[method]) {
                arguments = Array.prototype.slice.call(arguments, 1);
                return methods[method].apply(this, arguments);
            } else if (typeof (method) === 'object' || !method) {
                method = methods.init;
                return method.apply(this, arguments);
            } else {
                jQuery.error('Metodo ' + method + ' no existe en jQuery.search');
                return this;
            }



        }
    });

    var resetData = function () {
        var el = jQuery(this);
        var elems = el.data('elements');
        elems[0].val('');
        elems[2].val('');
    };

    jQuery.search = function (options) {

        var settings = {},
                privates = {},
                dataElement = {},
                search = {},
                inputHidden;

        search.getConfig = function () {
            return settings;
        };

        var init = function () {
            inputHidden = jQuery('<input type="hidden">');

            settings = jQuery.extend({}, jQuery.fn.search.searchVars.defaults, options);
            privates = jQuery.fn.search.searchVars.privates;

            dataElement = {defaults: settings, private: privates};

            if (settings.nrosch === null || settings.pkey === null) {
                show_message('Debe definir Key de busqueda', '', 'ERROR');
                return;
            }

            var pk = settings.pkey;
            var configs = {
                id: pk,
                name: pk
            };

            if (inputHidden.length > 0)
                inputHidden = inputHidden[0];
            else {
                inputHidden.attr(configs);
                inputHidden.appendTo('body');
            }
        };

        search.openSearch = function () {
            privates.isField = false;
            showNewSearch.call(this, dataElement);
        };

        init();
        return search;
    };

    jQuery.fn.search.searchVars = {
        id: 'search',
        version: '2.0',
        defaults: {
            nrosch: null,
            pkey: null,
            isHtml: false,
            params: null,
            title: 'Búsqueda',
            size: 45,
            hideColumn: [],
            generalFilter: false,
            rowsNum: 30,
            label: {
                name: null,
                style: null
            },
            beforeSearchOpen: function () {
            },
            onSearchClose: function () {
            },
            onSelectRow: function () {
            },
            onNoDataFound: function () {
            },
            onSearchClean: function () {
            },
            loading: true,
            rowNum: 20,
            setValues: {
                hidden: null,
                shown: null
            }
        },
        privates: {
            disable: false,
            executeCallback: false,
            isField: true
        }
    };

})(jQuery);
