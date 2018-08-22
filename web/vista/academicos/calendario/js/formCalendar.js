
var url;
var codkls_;
var tabs;
var listaAxk = [];
var listaPgm = [];
var listaDks = [];
var listaAxSel = [];
var listaPgmSel = [];
var listaDksSel = [];
$(document).ready(function() {

    url = Rutac + '/Adm/Calendar';
    conf_panel($(window).width() - 100, 'auto', 'panel');
//    conf_panel('90%', 'auto', 'panel');
    $('#panel').dialog('option', 'buttons', {
        "Nuevo Calendario": function() {
            newCalendar();
        },
        Guardar: function() {
            validarGuardado();
        },
        Salir: function() {
            $(this).dialog('close');
        }
    });
    hideButton('Guardar');
    genTabs('tabs');
    conf_tabs({
        disabled: [1, 2, 3, 4],
        beforeActivate: function(event, ui) {
            tabs = ui.newPanel.attr('id');
            if (tabs === 'LST') {
                showList();
                $(this).tabs({disabled: [1, 2, 3, 4]});
                hideButton('Guardar');
                showButton('Nuevo Calendario');
                codkls_ = '';
            } else {
                showButton('Guardar');
                hideButton('Nuevo Calendario');
            }
            if (tabs === 'AXK') {
                hideButton('Guardar');
            }

        }
    });
    showList();
    setIcons();
});
function setIcons() {

    setIcon('Nuevo Calendario', '-document');
    setIcon('Guardar', '-disk');
    setIcon('Salir', '-closethick');
}

function newCalendar() {

    $('#formKls')[0].reset();
    disabe_tabs([2, 3, 4]);
    active_tab(1);
//    window.location.href = Rutav + "/vista/academicos/calendario/formCalendarData.jsp";
}

function showList() {

//    var pars = 'events=LSTKLS&tpokls=' + $('#tpokls').val() + '&stdkls=' + $('#stdkls').val();

    genericAjax({
        url: url,
        data: {
            events: 'LSTKLS',
            tpokls: $('#tpokls_').val(),
            stdkls: $('#stdkls').val()
        },
        done: function(response) {
            $("#div_calendarios").html(response);
        }
    });
}

function showCalendar(codkls) {

    codkls_ = codkls;
    genericAjax({
        url: url,
        data: {
            events: 'SHOWKLS',
            codkls: codkls,
            tpopgm: $('#tpopgm').val(),
            stgpgm: $('#stgpgm').val()
        },
        done: function(response) {

            var resp = $.parseJSON(response);
            var smakls = resp.smakls;
            $('#div_actividades').html(resp.smaaxk);
            $('#div_programas').html(resp.smakxp);
            $('#div_departamentos').html(resp.smakxd);
            $.each(smakls, function(key, value) {
                $('#' + key.toLowerCase()).val(value);
            });
        },
        complete: function() {
            $('#tabs').tabs({
                disabled: [],
                active: 1
            });
            listaAxk = $('#lstAxk').getRowData();
            listaPgm = $('#lstKxp').getRowData();
            listaDks = $('#lstKxd').getRowData();
            listaAxkSel = $('#lstAxk').jqGrid('getGridParam', 'selarrrow');
            listaPgmSel = $('#lstKxp').jqGrid('getGridParam', 'selarrrow');
            listaDksSel = $('#lstKxd').jqGrid('getGridParam', 'selarrrow');

//    window.location.href = Rutac + "/Adm/Calendar?events=SHOWKLS&codkls=" + codkls;
        }
    }).done(function() {
        selectRows();
    });
}

function showPgm() {
    genericAjax({
        url: url,
        data: {
            events: 'GETPGM',
            codkls: codkls_,
            tpopgm: $('#tpopgm').val(),
            stgpgm: $('#stgpgm').val()
        },
        done: function(response) {
            $('#div_programas').html(response);
        }
    });
}

function selectAll() {

    var arr = $(this).getRowData();
    var id = $(this).attr('id');
    if (id.indexOf('Axk') > 0) {

        for (var i in arr) {

            clsma.$date($('#bgnaxk_' + arr[i].NROELM), {
                select: function(selectedDate) {
                    $('#endaxk_' + id).datepicker("option", "minDate", selectedDate);
                    validElement(this);
                }});

            clsma.$date($('#endaxk_' + arr[i].NROELM), {
                select: function(selectedDate) {
                    $('#bgnaxk_' + id).datepicker("option", "maxDate", selectedDate);
                    validElement(this);
                }});

        }

    } else if (id.indexOf('Kxp') > 0) {

        for (var i in arr) {
            if (arr[i].NROKXP !== '') {
                if (!$(this).jqGrid('getGridParam', 'selarrrow').has(arr[i].IDEPGM)) {
                    $(this).jqGrid('setSelection', arr[i].IDEPGM, true);
                }
            }
        }

    } else if (id.indexOf('Kxd') > 0) {

        for (var i in arr) {
            if (arr[i].NROKXD !== '') {
                if (!$(this).jqGrid('getGridParam', 'selarrrow').has(arr[i].IDEDKS)) {
                    $(this).jqGrid('setSelection', arr[i].IDEDKS, true);
                }
            }
        }

    }


}

function selectRows() {
    var data = $('#lstAxk').getRowData();

    data.each(function() {
        if (!isEmpty(this.NROAXK)) {
            $('#lstAxk').jqGrid('setSelection', this.NROELM);
        }
    });

}


function setInput(c, o, r) {

    var agnprs = $('#agnprs').clone();
    var prdprs = $('#prdprs').clone();
    var tipaxk = $('#tipaxk').clone();
    var div = $('<div/>');
    var input = $('<input/>');
    var a = $('<a/>');
    var img = $('<img/>');
    if (o.pos === 5) {

        input.attr({
            id: 'bgnaxk_' + r.NROELM,
            name: 'bgnaxk_' + r.NROELM,
            readonly: true,
            value: c.substring(0, 10),
            'data-nro': r.NROELM,
            'data-tpo': 'bgnaxk',
        });
        input.appendTo(div);
    } else if (o.pos === 6) {

        input.attr({
            id: 'endaxk_' + r.NROELM,
            name: 'endaxk_' + r.NROELM,
            readonly: true,
            value: c.substring(0, 10),
            'data-nro': r.NROELM,
            'data-tpo': 'endaxk',
        });
        input.appendTo(div);
    } else if (o.pos === 7) {

        agnprs.attr({
            id: 'agnprs_' + r.NROELM,
            name: 'agnprs_' + r.NROELM,
            'data-nro': r.NROELM,
            'data-tpo': 'agnprs',
            onchange: 'validElement(this)'
        }).appendTo(div);
        if (agnprs.find('option[value=' + c + ']')[0])
            agnprs.find('option[value=' + c + ']').attr('selected', true);
    } else if (o.pos === 8) {

        prdprs.attr({
            id: 'prdprs_' + r.NROELM,
            name: 'prdprs_' + r.NROELM,
            'data-nro': r.NROELM,
            'data-tpo': 'prdprs',
            onchange: 'validElement(this)'
        }).appendTo(div);
        if (prdprs.find('option[value=' + c + ']')[0])
            prdprs.find('option[value=' + c + ']').attr('selected', true);
    } else if (o.pos === 9) {

        tipaxk.attr({
            id: 'tipaxk_' + r.NROELM,
            name: 'tipaxk_' + r.NROELM,
            'data-tpo': 'tipaxk',
            'data-nro': r.NROELM,
            onchange: 'validElement(this)'
        }).appendTo(div);
        if (tipaxk.find('option[value=' + c + ']')[0])
            tipaxk.find('option[value=' + c + ']').attr('selected', true);
    } else if (o.pos === 11) {
        a.attr({
            onclick: 'javascript:editRow( \'' + r.NROELM + '\' )',
            href: '#'
        });

        img.attr({
            src: Rutav + '/vista/img/icono_lapiz.png'
        }).css({
            width: '20px',
            height: '20px'
        }).appendTo(a);
        a.appendTo(div);

    } else if (o.pos === 12) {
        if (isEmpty(r.NROAXK))
            return '';

        a.attr({
            onclick: 'javascript:delRow( \'' + r.NROAXK + '\' )',
            href: '#'
        });

        img.attr({
            src: Rutav + '/vista/img/delete.png'
        }).css({
            width: '20px',
            height: '20px'
        }).appendTo(a);
        a.appendTo(div);

    }

    return  div.css('text-align', 'center').prop('outerHTML');
}

function editRow(id) {

    var row = selectRow(id);
//    console.log(row);
    clsma.$confirm('¿ Desea guardar los datos ?').Aceptar(function() {
        genericAjax({
            url: url,
            data: {
                events: 'EDITAXK',
                form: $.toJSON(row),
                codkls: codkls_
            },
            loading: true
        }).then(function(data) {
            data = $.parseJSON(data);
            $.softMessage({
                class: data.exito === 'OK' ? 'exito' : 'error',
                float: 'right',
                left: 50,
                delay: 1000,
                message: data.msg,
                done: function() {
                    listAxk();
                }
            });
        });
    });
}

function delRow(id) {

//    var row = selectRow(id);
//    console.log(row);
    clsma.$confirm('¿ Desea eliminar registro ?').Aceptar(function() {
        genericAjax({
            url: url,
            data: {
                events: 'DELAXK',
                form: id,
                codkls: codkls_
            },
            loading: true
        }).then(function(data) {
            data = $.parseJSON(data);
            $.softMessage({
                class: data.exito === 'OK' ? 'exito' : 'error',
                float: 'right',
                left: 50,
                delay: 1000,
                message: data.msg,
                done: function() {
                    listAxk();
                }
            });
        });
    });
}

function validElement($elem) {
    var nro = $($elem).data('nro');
    var tpo = $($elem).data('tpo');

    $('#lstAxk')[0].p.data.each(function() {
        if (this.NROELM === nro) {
            this[tpo.toUpperCase()] = $elem.value;
            return false;
        }
    });
}

function selectRow(id) {
    var object = {};
    $('#lstAxk')[0].p.data.each(function() {
        if (this.NROELM === id) {
            this.BGNAXK = getValue('bgnaxk_' + id);
            this.ENDAXK = getValue('endaxk_' + id);
            this.AGNPRS = getValue('agnprs_' + id);
            this.PRDPRS = getValue('prdprs_' + id);
            this.TIPAXK = getValue('tipaxk_' + id);
            object = Object.clone(this);
            return false;
        }
    });
    return object;
}


function validarGuardado() {
    if (tabs === 'KLS') {
        saveKls();
    } else if (tabs === 'AXK') {
        saveAxk();
    } else if (tabs === 'PGM') {
        savePgm();
    } else if (tabs === 'DKS') {
        saveDKS();
    }

}

function saveKls() {

    if (validarAllform('formKls', '')) {

        show_confirmation('¿Esta seguro(a) de enviar los datos?', function() {

            var pars = 'events=SAVEKLS&codkls=' + codkls_ + '&' + $('#formKls').serialize();
            genericAjax({
                url: url,
                data: pars,
                done: function(response) {
                    var resp = $.parseJSON(response);
//                    show_message(resp.msg, resp.fnc, resp.type);

                    $.softMessage({
                        class: resp.type === 'OK' ? 'exito' : 'error',
                        float: 'right',
                        left: 50,
                        delay: 1000,
                        message: resp.msg,
                        done: function() {
                            if (resp.codkls)
                                showCalendar(resp.codkls);
                        }
                    });
                },
                loading: true
            });
        });
    }
}



function saveAxk() {
    $('#div_actividades .ui-search-input input[type="text"]').each(function() {
        $(this).val('');
        $(this).focus();

    });
    var lstAxk = $('#lstAxk').getRecords(true);
    var lstAxk_ = $('#lstAxk').getRecords();
    var arrSel = [];
    for (var i in lstAxk) {

        var codaxk = lstAxk[i];
        var elm = {
            codaxk: codaxk.NROELM,
            bgnaxk: codaxk.BGNAXK,
            endaxk: codaxk.ENDAXK,
            agnprs: codaxk.AGNPRS,
            prdprs: codaxk.PRDPRS,
            tipaxk: codaxk.TIPAXK
        };
        arrSel.push(elm);
    }
//    console.log( arrSel );
//    return;
    show_confirmation('¿Esta seguro(a) que desea enviar?', function() {

        genericAjax({
            url: url,
            data: {
                events: 'SAVEAXK',
                codkls: codkls_,
                lstAxk: lstAxk_.toString(),
                arrSel: $.toJSON(arrSel)
            },
            done: function(response) {
                var resp = $.parseJSON(response);
                $.softMessage({
                    class: resp.type === 'OK' ? 'exito' : 'error',
                    float: 'right',
                    left: 50,
                    delay: 1000,
                    message: resp.msg,
                    done: function() {
                        listAxk();
                    }
                });
            },
            loading: true
        });
    });
}

function listAxk() {

    genericAjax({
        url: url,
        data: {
            events: 'LISTAXK',
            codkls: codkls_
        },
        done: function(response) {
            response = $.parseJSON(response);
            if (response.exito === 'OK') {
                $('#div_actividades').empty().html(response.html);
            } else {
                clsma.$msg(response.msg, 'clsma.$reload', 'OK');
            }

        },
        loading: true
    }).then(function() {
//        selectAll();
        selectRows();
    });

}

function saveDKS() {

    var lstDks = $('#lstKxd').jqGrid('getGridParam', 'selarrrow');
    show_confirmation('¿Esta seguro(a) que desea enviar?', function() {

        genericAjax({
            url: url,
            data: {
                events: 'SAVEDKS',
                codkls: codkls_,
                lstdks: lstDks.toString()
            },
            done: function(response) {
                var resp = $.parseJSON(response);
                $.softMessage({
                    class: resp.type === 'OK' ? 'exito' : 'error',
                    float: 'right',
                    left: 50,
                    delay: 1000,
                    message: resp.msg,
                    done: function() {

                    }
                });
            },
            loading: true
        });
    });
}

function savePgm() {

    var lstKxp = $('#lstKxp').jqGrid('getGridParam', 'selarrrow');
    show_confirmation('¿Esta seguro(a) que desea enviar?', function() {

        genericAjax({
            url: url,
            data: {
                events: 'SAVEPGM',
                codkls: codkls_,
                lstKxp: lstKxp.toString(),
                tpopgm: $('#tpopgm').val(),
                stgpgm: $('#stgpgm').val()

            },
            done: function(response) {
                var resp = $.parseJSON(response);
                $.softMessage({
                    class: resp.type === 'OK' ? 'exito' : 'error',
                    float: 'right',
                    left: 50,
                    delay: 1000,
                    message: resp.msg,
                    done: function() {

                    }
                });
            },
            loading: true
        });
    });
}