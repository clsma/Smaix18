/* 
 *Author     : Cl-sma(Carlos Pinto)
 */

function configGridTab(setData) {

    var defaults = {
        datatype: "local",
        loadonce: true,
        width: 'auto',
        height: 200,
        rowNum: 100,
        rowList: [100, 200, 300, 1000, 2000, 3000, 100000],
        viewrecords: true,
        rownumbers: true,
        ignoreCase: true,
        hidegrid: false,
        caption: null,
        search: true,
        autosearch: true,
        sortname: '',
        pager: ''
    };

    var ta = $('<textarea />');
    ta[0].value = setData;

    var data = $.parseJSON(ta[0].value.cleanString());
//    var data = $.parseJSON(setData.cleanString());

    var idGrid = $("#" + data.idGrid);
    var filterToolbar = data.tableChasis.filter;

    var options = data.options;

    if (data.pager)
        defaults.pager = data.pager;

    idGrid.jqGrid('GridUnload');

    for (i in options) {
        if (typeof window[options[i]] === 'function') {
            options[i] = window[options[i]];
        }
    }

    var settings = $.extend(true, defaults, options || {});

    var response = data.tableChasis;


    var dataList = response.listData;

    for (i in response.columnModel) {
        if (response.columnModel[i].formatter) {
            response.columnModel[i].formatter = eval(response.columnModel[i].formatter);
        }
        if (response.dateFormat) {
            for (j in response.dateFormat) {
                if (response.dateFormat[j] === parseInt(i)) {
                    response.columnModel[i].formatter = 'date';
                    response.columnModel[i].formatoptions = {newformat: 'Y-m-d'};

                }

            }

        }
        if (response.formatMoney) {

            for (aux in response.formatMoney) {
                if (response.formatMoney[aux] === response.columnModel[i].name) {
                    response.columnModel[i].align = 'right';
                }
            }
        }

    }

    if (response.formatMoney) {
        for (k in  dataList) {
            for (l in response.formatMoney) {
                var cod = response.formatMoney[parseInt(l)];
                dataList[k][cod] = accounting.formatMoney((dataList[k][cod] || '').replace('.', ','));
            }
        }
    }
    var inners = {
        colModel: response.columnModel,
        colNames: response.titleModel
    };

    var group = response.grouping;
    if (group) {
        group = {grouping: true, groupingView: response.groupingView};
    } else {
        group = {};
    }
    settings = $.extend(true, settings, inners, group);


    if (options) {

        if (options.align) {
            var parent = idGrid.parent();

            if (parent[0].tagName === 'DIV') {
                $(parent).parent().css({'text-align': options.align});
            } else if (parent[0].tagName === 'TD')
                parent.attr('align', options.align);
        }

    }
    if (dataList !== null && dataList.length > 0)
        settings = $.extend(true, settings, {data: dataList});

    idGrid.jqGrid(settings);

    if (settings.multiselect) {
        addMultiselectConfig(idGrid);
    }


    if (filterToolbar) {
        idGrid.jqGrid('filterToolbar',
                {defaultSearch: 'cn', searchOnEnter: false});
    }

    if (response.frozen) {
        idGrid.jqGrid('setGroupHeaders', response.frozen);
    }
    if (response.frozenColumn) {
        idGrid.jqGrid('setFrozenColumns');
    }



    if (response.sortable)
        idGrid.sortableRows();

    if (response.generalFilter) {

        $.expr[':'].icontains = function (obj, index, meta, stack) {
            return (obj.textContent || obj.innerText || jQuery(obj).text() || '').toLowerCase().indexOf(meta[3].toLowerCase()) >= 0;
        };
        setFilert(data.idGrid);
    }

    if (response.summaryCols && Object.isArray(response.summaryCols)) {
        setTotals(idGrid, response.summaryCols);
    }

    if (!isEmpty(response.rowsPanModel)) {
        setRowsPan(idGrid, dataList, response.rowsPanModel);
    }





    $('script[id=scr_' + data.idGrid + ']').remove();
    window['datasZXC' + data.idGrid] = '';
    delete window['datasZXC' + data.idGrid];
}

function setTotals(grid, conf) {

    var records = grid.getGridParam('records');
    records++;
    var data = grid.getGridParam('data');
    var colModel = grid.getGridParam('colModel');

    var total;
    var objec;
    objec = {};
    conf.each(function (value) {
        total = 0;
        data.each(function (values) {
            if (O(values).get('key').has(value)) {
                if (Object.isFunction(accounting.formatMoney))
                    total += accounting.unformat(values[value]);
                else
                    total += parseFloat(values[value]);
            }
        });

        objec[value] = Object.isFunction(accounting.formatMoney)
                ? accounting.formatMoney(total)
                : total;
        objec[value] = '<b>' + objec[value] + '</b>';
    });


    colModel.each(function (value) {

        if (!value.hidden) {
            objec[value.name] = '<b><i>Total : </i><b>';
            return false;
        }

    });

    grid.jqGrid('addRowData', records, objec);


}


function setFilert(id) {

    var div = $('<div/>').attr({id: 'div_' + id});
    var input = $('<input/>').attr({id: 'filter_' + id});
    var label = $('<label/>').attr({for : 'filter_' + id}).text('Filtro general: ');
    var p = $('<p/>');
    var parent = $('#theQgrid_' + id);


    label.appendTo(p);
    input.appendTo(p);
    p.appendTo(div);

    div.prependTo(parent);




    input.keyup(function () {
        var buscar = $(this).val();
        var tabla = '#' + id;

        $(tabla + ' tr').removeClass('resaltar');

        if (jQuery.trim(buscar) !== '') {
            if ($(tabla + " tr:icontains('" + buscar + "') td").length > 0) {
                $(tabla + ' tr:icontains(' + buscar + ')').addClass('resaltar');
                $(tabla).parent().parent().animate({
                    scrollTop: $(tabla + " tr:icontains('" + buscar + "') td").offset().top - 200
                }, 200);
            }
        } else {
            $(tabla + ' tr').removeClass('resaltar');
        }


    });


}

function addMultiselectConfig(table) {
    var settings;
    if (table && table[0].grid)
        settings = table[0].p;
    else
        throw new Error('No hay tabla definida');

    if (settings.multiselect) {
        $.jgrid.extend({
            keepRecords: function () {
                var $t = table[0];
                $t.p.selected = [];

                $($t).on('jqGridSelectRow', function (a, b, c) {
                    hasArrayInsert.call(this, this.p.selected, b, this);
                    hasCheckSelected.call(this, this.p.selected, b, this);
                });

                $($t).on('jqGridSelectAll', function (a, b, c) {
                    hasArrayInsert.call(this, this.p.selected, b, this);
                    hasCheckSelected.call(this, this.p.selected, b, this);
                });

                $t.p.onPaging = function (a, b, c) {
                    hasArrayInsert.call(this, this.p.selected,
                            $(this).getGridParam('selarrrow'),
                            this);
                }//End onPaging

                $($t).on('jqGridLoadComplete', function (a, b) {
                    setAllSelection.call(this, this.p.selected,
                            this);
                    if (Object.isFunction(this.triggerToolbar)) {
                        this.triggerToolbar();
                    }
                });

                $($t).on('jqGridToolbarAfterSearch', function (a, b) {
                    setAllSelection.call(this, this.p.selected,
                            this);
                });
                $($t).on('jqGridSortCol', function (a, b) {
                    setAllSelection.call(this, this.p.selected,
                            this);
                });




                function hasArrayInsert(array, datas, table) {
                    datas = $.type(datas) === 'array' ? datas : [datas];

                    if (isEmpty(array)) {
                        for (i in datas) {
                            array.push({row: datas[i], dataRow: $(this).getRow(datas[i])});
                        }
                        return array;
                    }

                    var auxx = [];
                    for (i in array) {
                        auxx.push(array[i].row);
                    }

                    for (i in datas) {
                        if (auxx.indexOf(datas[i]) === -1) {
                            array.push({row: datas[i], dataRow: $(this).getRow(datas[i])});
                        }
                    }

                    return array;
                }



                function hasCheckSelected(array, datas, table) {
                    datas = $.type(datas) === 'array' ? datas : [datas];
                    var id;
                    for (i in datas) {
                        id = '#jqg_' + (table.id) + '_' + datas[i];

                        if (isEmpty(array)) {
                            return array;
                        }

                        for (j in array) {
                            if (datas[i] === array[j].row) {
                                if (!$(id).is(':checked')) {
                                    array.splice(j, 1);
                                }
                            }
                        }
                    }
                    return array;
                }

                function setAllSelection(array, table) {
                    array = $.type(array) === 'array' ? array : [array];
                    var id;
                    for (i in array) {
                        id = '#jqg_' + (table.id) + '_' + array[i].row;
                        if (!$(id).is(':checked')) {
                            $(table).setSelection(array[i].row);
                        }
                    }
                    return array;
                }
            },
            getRecords: function (detail) {
                if (!this[0].grid) {
                    return [];
                }

                if (this[0].p.data.length === 0) {
                    this[0].p.selected = [];
                    return [];
                }

                var arrResult = [];
                for (row in this[0].p.selected) {
//                    this[0].p.selected[i].dataRow = $(this).getRowData(this[0].p.selected[i].row);
                    this[0].p.selected[row].dataRow = $(this).getRow(this[0].p.selected[row].row);
                    arrResult.push(!detail ? this[0].p.selected[row].row : this[0].p.selected[row].dataRow);
                }
                return arrResult;
            },
            getRow: function (object) {

                var arr = this[0].p.data;
                var key = this[0].p.keyName;
                if (isEmpty(arr))
                    return;

                if (key === false) {
                    return this.getRowData(object);
                }
                for (i in arr) {
                    if (key !== false && arr[i][key] && arr[i][key] === object) {
                        return arr[i];
                    }
                }
            }
        });
        table.keepRecords();

    }

}

function setRowsPan(table, data, rowspanOptions) {

    var $this = $(table);
    if (data.empty())
        return;
    var name = $this.attr('id');
    var columns = rowspanOptions.groupColumns;//['CODPSM'];
    var rowspanColumns = rowspanOptions.rowspanColumn;//['CRDMAT'];
    var columnExp = rowspanOptions.colExpr;//'TIPASIG';
    var valueExp = rowspanOptions.valueExpr;//'INT';
    var cols_;
    var arrCols = [], arrObjects = [];
    var itemcount, childrens, tr, iterator;

    var plantilla = 'td[aria-describedby="%s_%s"]';
    $this.find('tr.jqgrow[role=row] ').each(function () {
        tr = this;
        var text = '';
        var textExpr = '';

        columns.each(function () {
            text += '-' + $(tr).find(plantilla.StringFormat(name, this)).text();
        });
        text = text.substring(1);
        textExpr = $(tr).find(plantilla.StringFormat(name, columnExp)).text();
        if (!arrCols.has(text) && textExpr === valueExp) {
            var arrTr = [];
            $this.find('tr.jqgrow[role=row] ').each(function () {
                var valAux = '';
                var tr_ = this;
                columns.each(function () {
                    valAux += '-' + $(tr_).find(plantilla.StringFormat(name, this)).text();
                });
                valAux = valAux.substring(1);
                if (valAux === text) {
                    arrTr.push(tr_);
                }
            });

            arrObjects[text] = arrTr;
            arrCols.push(text);
        }
    });

    arrCols.each(function () {
        cols_ = this;
        itemcount = arrObjects[cols_].size();
        childrens = arrObjects[cols_];
        childrens.each(function (a, b) {
            tr = this;
            iterator = b;
            rowspanColumns.each(function () {
                if (iterator > 0)
                    $(tr).find('td[aria-describedby="' + name + '_' + this + '"]').hide();
                else {
                    $(tr).find('td[aria-describedby="' + name + '_' + this + '"]').attr('rowspan', itemcount);
                }
            });
        });
    });
}