
var url;
var codkls_;
var tabs;
$(document).ready(function() {

    url = Rutac + '/Adm/CalendarPublic';
    conf_panel($(window).width() - 100, 'auto', 'panel');
//    conf_panel('90%', 'auto', 'panel');
    $('#panel').dialog('option', 'buttons', {
        Salir: function() {
            $(this).dialog('close');
        }
    });

    genTabs('tabs', 'container');
    clsma.tab = $('#tabs').menu_vertical({
        renderAt: 'container',
        beforeActivate: function(a) {
            var id = a.newPanel.attr('id');

            if (id === 'LST') {
                showList();
                codkls_ = '';
            }

        }
    }).disableTab(1);
    hideButton('Salir');

    showList();
});

function newCalendar() {
    $('#formKls')[0].reset();
    disabe_tabs([2, 3, 4]);
    active_tab(1);
}

function showList() {

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

            $('#agnprs').attr("disabled", true);
            $('#tpokls').attr("disabled", true);
            $('#stdkls_').attr("disabled", true);
            $('#prdprs').attr("disabled", true);

        },
        complete: function() {
            clsma.tab.enableTab(1).activeTab(1);
            clsma.tab.enableTab(2).activeTab(2);
        }
    });
//    window.location.href = Rutac + "/Adm/Calendar?events=SHOWKLS&codkls=" + codkls;
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
            if (arr[i].NROAXK !== '') {
                $(this).jqGrid('setSelection', arr[i].NROELM, true);
            }

            $('#bgnaxk_' + arr[i].NROELM).datepicker({
                dateFormat: 'yy-mm-dd',
                onClose: function(selectedDate) {
                    var id = $(this).attr('id').split('_')[1];
                    $('#endaxk_' + id).datepicker("option", "minDate", selectedDate);
//                    $('#endaxk_' + id).val(selectedDate);
                }
            });

            $('#endaxk_' + arr[i].NROELM).datepicker({
                dateFormat: 'yy-mm-dd',
                onClose: function(selectedDate) {
                    var id = $(this).attr('id').split('_')[1];
                    $('#bgnaxk_' + id).datepicker("option", "maxDate", selectedDate);
//                    $('#bgnaxk_' + id).val(selectedDate);
                }
            });
        }



    } else if (id.indexOf('Kxp') > 0) {

        for (var i in arr) {
            if (arr[i].NROKXP !== '') {
                $(this).jqGrid('setSelection', arr[i].IDEPGM, true);
            }
        }

    } else if (id.indexOf('Kxd') > 0) {

        for (var i in arr) {
            if (arr[i].NROKXD !== '') {
                $(this).jqGrid('setSelection', arr[i].IDEDKS, true);
            }
        }

    }
}


function setInput(c, o, r) {

    var agnprs = $('#agnprs').clone();
    var prdprs = $('#prdprs').clone();
    var tipaxk = $('#tipaxk').clone();
    var div = $('<div/>');
    var input = $('<input/>');

    if (o.pos === 4) {

        input.attr({
            id: 'bgnaxk_' + r.NROELM,
            name: 'bgnaxk_' + r.NROELM,
            readonly: true,
            value: c
        });

        input.appendTo(div);

    } else if (o.pos === 5) {

        input.attr({
            id: 'endaxk_' + r.NROELM,
            name: 'endaxk_' + r.NROELM,
            readonly: true,
            value: c
        });

        input.appendTo(div);

    } else if (o.pos === 6) {

        agnprs.attr({
            id: 'agnprs_' + r.NROELM,
            name: 'agnprs_' + r.NROELM
        }).appendTo(div);

        if (agnprs.find('option[value=' + c + ']')[0])
            agnprs.find('option[value=' + c + ']').attr('selected', true);

    } else if (o.pos === 7) {

        prdprs.attr({
            id: 'prdprs_' + r.NROELM,
            name: 'prdprs_' + r.NROELM
        }).appendTo(div);

        if (prdprs.find('option[value=' + c + ']')[0])
            prdprs.find('option[value=' + c + ']').attr('selected', true);

    } else if (o.pos === 8) {

        tipaxk.attr({
            id: 'tipaxk_' + r.NROELM,
            name: 'tipaxk_' + r.NROELM
        }).appendTo(div);

        if (tipaxk.find('option[value=' + c + ']')[0])
            tipaxk.find('option[value=' + c + ']').attr('selected', true);

    }

    return  div.prop('outerHTML');
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

    var lstAxk = $('#lstAxk').jqGrid('getGridParam', 'selarrrow');
    var arrSel = [];
    for (var i in lstAxk) {

        var codaxk = lstAxk[i];

        var elm = {
            codaxk: codaxk,
            bgnaxk: $('#bgnaxk_' + codaxk).val(),
            endaxk: $('#endaxk_' + codaxk).val(),
            agnprs: $('#agnprs_' + codaxk).val(),
            prdprs: $('#prdprs_' + codaxk).val(),
            tipaxk: $('#tipaxk_' + codaxk).val()
        };
        arrSel.push(elm);
    }

    show_confirmation('¿Esta seguro(a) que desea enviar?', function() {

        genericAjax({
            url: url,
            data: {
                events: 'SAVEAXK',
                codkls: codkls_,
                lstAxk: lstAxk.toString(),
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

                    }
                });
            },
            loading: true
        });
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