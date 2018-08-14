/*
 * Autor ing J. F. Llanos ( Cl-Sma )
 * Created 13/07/2016 06:14:45 PM
 * File formAcademicPlanningManager
 */

clsma.$requestSetup({
    controller: [
        {name: Rutac + '/Adm/' + 'formAcademicPlanningManager'.substring(4), path: 'event'}
    ],
    returning: 'object'
});
//clsma.nomtabList = '#jqDks';
clsma.nomtabList = '#jqPgm';
clsma.nomtabGroup = '#jqGroup';
clsma.nomtabSmt = '#jqSmt';
clsma.nomtabCrs = '#jqCrs';
clsma.nomtabElc = '#jqElectiva';
clsma.nomtabLbr = '#jqLbr';
clsma.nomtabModules = '#jqMdl';
clsma.nomtabModulesNew = '#jqModules';
clsma.nomtabIntegrated = '#jqMatIntegrated';
clsma.nomtabHrsPrf = '#jqHrsPrf';
$(function () {


    configOthers();
    conf_panel('95%', 'auto', 'panel', {
        'Nuevo Semestre': addSmt,
        'Guardar Semestre': saveSmt,
        'Nuevo Curso': addCrs,
        'Guardar Curso': saveCrs,
        'Nuevo Grupo': detailGrp,
        'Guardar Grupo': saveGrp,
        'Nuevo Grupo Libre': newLbr,
        'Nuevo Grupo Electiva': newElective
    });
    setIconos();
    configTabs();
    configSearch();
    searchPak();
    confOthers();
});
function configTabs() {
    genTabs('tabs', 'container');
    clsma.tab = $('#tabs').menu_vertical({
        renderAt: 'container',
        beforeActivate: function (a) {

            var id = a.newPanel.attr('id');
            $('#MAT , #PRF , #LSTADD , #LSTSMT , #LSTCRS , .schedulecontainer, #LBR').empty();

            hideButton('Nuevo Semestre');
            hideButton('Guardar Semestre');
            hideButton('Nuevo Grupo');
            hideButton('Nuevo Curso');
            hideButton('Guardar Grupo');
            hideButton('Guardar Curso');
            hideButton('Nuevo Grupo Libre');
            hideButton('Nuevo Grupo Electiva');
            clearChildren('#GRP', '[name=knppak],#habmat,#lblmat');
            if (!['GRP'].has(id)) {
                this.disableTab(10);
                delete clsma.pak;
                delete clsma.isElc;
                delete clsma.isLbr;
                $('.tblPrfHra').empty();
            }


            //if (id === 'DKS') {
            if (id === 'PGMS') {
                this.disableTab(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12).hideTab(1, 12);
                $('.rowButtonInt').hide();
                $('.advisegrp').empty();
                $('#MAT,#PRF').empty();
            } else if (id === 'MAT') {
                getGroups('MAT');
                showButton('Nuevo Grupo');
            } else if (id === 'PRF') {
                getGroups('PRF');
                showButton('Nuevo Grupo');
            } else if (id === 'GRP') {
                showButton('Guardar Grupo');
                getDataGrp();
            } else if (id === 'HOR') {
                $('#horby option[value=SEL]').prop('selected', true).trigger('change');
//                validHorario();
            } else if (id === 'RQS') {
            } else if (id === 'CRS') {
                if (clsma.ispgm) {
                    showButton('Nuevo Curso');
                    active_tab('#tabsCrs', 0);
                    lstCrs();
                }
            } else if (id === 'SMT') {
                if (clsma.ispgm) {
                    showButton('Nuevo Semestre');
                    active_tab('#tabsSmt', 0);
                    lstSmt();
                }

            } else if (id === 'LBR') {
                clsma.type = 'LBR';
                showButton('Nuevo Grupo Libre');
                getLbr();
            } else if (id === 'ELC') {
                clsma.type = 'ELC';
                getElc();
            } else if (id === 'INT') {
                active_tab('#tabInt', 0);
                confModules();
//                lstIntegerated();
            }

            $('#msg').html(writeInfoMsg(msgTab(id)));
        }
    });
    genTabs('tabsAd');
    conf_tabs('#tabsAd', {
        beforeActivate: function (a, b) {
            var id = b.newPanel.attr('id');
            hideButton('Guardar Adicional');
            hideButton('Docente Adicional');
            if (id === 'LSTADD') {
                disabe_tabs('#tabsAd', [1]);
                showButton('Docente Adicional');
                clearChildren('#DTLADD');
            } else {
                showButton('Guardar Adicional');
            }
        }
    });
    disabe_tabs('#tabsAd', [1]);
    hideButton('Guardar Adicional');

    genTabs('tabsCrs');
    conf_tabs('#tabsCrs', {
        beforeActivate: function (a, b) {
            var id = b.newPanel.attr('id');
            hideButton('Guardar Curso');
            hideButton('Nuevo Curso');
            if (id === 'LSTCRS') {
                disabe_tabs('#tabsCrs', [1]);
                showButton('Nuevo Curso');
                clearChildren('#CRS');
            } else {
                showButton('Guardar Curso');
            }
        }
    });
    disabe_tabs('#tabsCrs', [1]);
    genTabs('tabsSmt');
    conf_tabs('#tabsSmt', {
        beforeActivate: function (a, b) {
            var id = b.newPanel.attr('id');
            hideButton('Guardar Semestre');
            hideButton('Nuevo Semestre');
            if (id === 'LSTSMT') {
                disabe_tabs('#tabsSmt', [1]);
                showButton('Nuevo Semestre');
                clearChildren('#SMT');
            } else {
                showButton('Guardar Semestre');
            }
        }
    });
    disabe_tabs('#tabsSmt', [1]);
    genTabs('tabInt');
    conf_tabs('#tabInt', {
        beforeActivate: function (a, b) {
            var id = b.newPanel.attr('id');
            hideButton('Guardar Integrada');
            if (id === 'LSTINT') {
                disabe_tabs('#tabInt', [1]);
                showButton('Nueva Integrada');
                $('#integrada').trigger('reset');
                $('.lstModule').empty()
            } else {
            }
        }
    });
    disabe_tabs('#tabInt', [1]);
    configButton('newInteger', '-plus', newInteger);
    configButton('HrasPrf', '-disk', addHraPrf);
}

function configSearch() {

    $('#docente').search({
        nrosch: 'SCH_PRF_PAK',
        pkey: 'nroprf',
        title: 'Docente',
        size: 40,
        hideColumn: ['NROPRF', 'NROPRS', 'STDWRK', 'CODSEX', 'NOMFAC', 'TPOPRF', 'NOMPRS', 'APEPRS', 'IDEFAC'],
        onSelectRow: function (data) {
            $('#nroprf').val(data.NROPRF);
            validHrsPrf();
            enableButton('#desprf', true);
            getTblHrs();

        }
    });
    $('#docadicional').search({
        nrosch: 'SCH_PRF_PAK',
        pkey: 'prfpxg',
        title: 'Docente Adicional',
        size: 50,
        hideColumn: ['NROPRF', 'NROPRS', 'STDWRK', 'CODSEX', 'NOMFAC', 'TPOPRF', 'NOMPRS', 'APEPRS', 'IDEFAC'],
        onSelectRow: function (data) {
            $('#prfpxg').val(data.NROPRF);
        }
    });
    $('#asignatura').search({
        nrosch: 'SCH_MAT_PAK',
        pkey: 'nromat',
        title: 'Asignatura',
        size: 40,
        beforeSearchOpen: function (set) {
            if (isEmpty(getValue('idecrs')) && clsma.ispgm && !clsma.isLbr && !clsma.isElc) {
                clsma.$msg('Debe seleccionar el curso');
                return false;
            }

            set.params = [clsma.dks, 'X'];
            set.hideColumn = ['NROMAT'];
            if (clsma.ispgm) {
                set.params = [clsma.dks, getValue('nropss')];
            } else {
                set.hideColumn.push('SMTPSM');
            }

            if (clsma.isLbr) {
                set.params.push('LBR');
            } else if (clsma.isElc) {
                set.params.push('ELC');
            } else {
                set.params.push('MAT');
            }

        },
        onSelectRow: function (data) {
            if (!isEmpty(data.CODPSM)) {
                $('#codpsm').val(data.CODPSM);
            }
            if (!isEmpty(data.CODMDL)) {
                $('#mdlmat').val(data.CODMDL);
                $('#codmat').val(data.CODMAT);
                $('#NOMMAT').val(data.NOMMAT);
            }
            getDataMat(data.NROMAT);
        }
    }).search('option', 'disabled', false);
    $('#seccional').search({
        nrosch: 'SCH_SCN_PAK',
        pkey: 'idescn',
        title: 'Seccional',
        size: 40
    });
    $('#programa').search({
        nrosch: 'SCH_PSD_PAK',
        pkey: 'nropsd',
        title: 'Plan de estudios de mis programas',
        size: 40,
        hideColumn: ['NROPSD'],
        beforeSearchOpen: function (set) {
            set.params = [('%s').StringFormat(clsma.dks)];
        },
        onSelectRow: function (data) {
            $('#idepgm').val(data.IDEPGM);
        }
    });
    $('#semestre').search({
        nrosch: 'SCH_PSS_PAK',
        pkey: 'nropss',
        title: 'Semestre',
        size: 15,
        isHtml: true,
        hideColumn: ['NROPSS'],
        beforeSearchOpen: function (set) {
            set.params = ['nropsd', ('~%s~'.StringFormat(clsma.pkp))];
            if (isEmpty(getValue('nropsd'))) {
                clsma.$msg('Debe seleccionar pensum');
                return false;
            }
        }
    });
    $('#curso').search({
        nrosch: 'SCH_CRS_PAK',
        pkey: 'idecrs',
        title: 'Curso',
        size: 30,
        hideColumn: ['IDECRS', 'NROPSS', 'KPCCRS', 'BGNCRS', 'ENDCRS'],
        beforeSearchOpen: function (set) {
            set.params = [('~%s~'.StringFormat(clsma.pkp)), 'MAT'];
            if (clsma.isLbr) {
                set.params = [('~%s~'.StringFormat(clsma.pkp)), 'LBR'];
            }

        },
        onSelectRow: function (data) {
            clearChildren('#GRP', '#idecrs,#idecrsShw,[name=knppak],#habmat,#lblmat');
            $('#nropss').val(data.NROPSS);
            $('#kpcpak').val(data.KPCCRS);
            var begin, end;
            begin = Date.toDate(data.BGNCRS);
            end = Date.toDate(data.ENDCRS);

            setDates('#bgnpak,#bgnpxg', 'minDate', begin);
            setDates('#endpak,#endpxg', 'maxDate', end);

        }
    });
    $('#curseohor').search({
        nrosch: 'SCH_CRSHOR_PAK',
        pkey: 'crshor',
        title: 'Curso',
        size: 50,
        beforeSearchOpen: function (set) {
            set.params = [('~%s~'.StringFormat(clsma.pkp))];
        },
        onSelectRow: getGroupsCurse
    });
    $('#asignaturahor').search({
        nrosch: 'SCH_MATHOR_PAK',
        pkey: 'mathor',
        title: 'Asignatura',
        size: 50,
        beforeSearchOpen: function (set) {
            set.params = [('~%s~'.StringFormat(clsma.pkp))];
        },
        onSelectRow: getGroupsCurse
    });
    $('#docentehor').findPrs({
        pkey: 'prfhor',
        title: 'Docente',
        size: 50,
        onSelect: getGroupsCurse,
        tpoprs: ['PRF']
    });


    $('#integrada').search({
        nrosch: 'SCH_INT_PAK',
        pkey: 'nromatInt',
        title: 'Asignatura integrada',
        size: 50,
        hideColumn: ['NROMAT'],
        beforeSearchOpen: function (sett) {
            sett.params = ['~%s~'.StringFormat(clsma.dks)]
        },
        onSelectRow: getModules
    });

    $('#electivahor').search({
        nrosch: 'SCH_PAK_ELC',
        pkey: 'nroelc',
        title: 'Electiva',
        beforeSearchOpen: function (sett) {
            sett.params = ['~%s~'.StringFormat(clsma.pkp)]
        },
        hideColumn: ['CODPSM'],
        onSelectRow: function (data) {
            $('#nroelcShw').val(data.NOMMAT);
            getDataScedElc(data.CODPSM);
        }
    });
}
function configOthers() {

    if (!clsma.hasAccess) {
        $('#container').hide();
        clsma.$msg('Usted no posee privilegios para administrar este módulo. Comuniquese con el administrador del sistema.');
        clsma.$error('Usted no posee privilegios para administrar este módulo. Comuniquese con el administrador del sistema.');
    }
    if (!clsma.hasCalendar) {
        $('#container').hide();
        clsma.$msg(clsma.$calendarMsg('Programación Académica [ PAK ]'));
        clsma.$error(clsma.$calendarMsg('Programación Académica [ PAK ]'));
    }
    $('#habmat,#lblmat').click(function (e) {
        e.preventDefault();
    });
    $('.datapgm,.hide').hide();
    var but = configButton('desprf', '-cancel', function () {
        $('#docente').trigger('reset');
        disableButton(this, true);
        $('.tblPrfHra').empty();
        $('.hraPrf').hide();
        validHrsPrf();
    });
    disableButton(but, true);

    configButton('searchPak', '-search', searchPak);
    configButton('addPrf', '-plus', addPrf);
    configButton('savePrfAdd', '-disk', savePrfAdd);
}

function setMin(date) {
    setDates('#endpak,#endpxg', 'minDate', date);
}
function setMax(date) {
    setDates('#bgnpak,#bgnpxg', 'maxDate', date);
}

function setDates(input, typeDate, date) {
    try {
        $(input).datepicker('destroy');
    } catch (e) {

    }
    clsma.$date($(input));
    $(input).datepicker('option', typeDate, date);
}

function lstDks() {

    delete clsma.dks;
    delete clsma.type;
    delete clsma.pak;
    delete clsma.pkp;
    delete clsma.isLbr;
    delete clsma.isElc;

    clsma.agn = getValue('agnprs');
    clsma.prd = getValue('prdprs');
    clsma.$request({
        data: ['LSTDKS', {a: getValue('agnprs'), p: getValue('prdprs')}],
        loading: true
    }).then(function (data) {
        $('#DKS').empty().html(data.html);
    });
}


function detailDepartment(id) {

    var data = $(clsma.nomtabList).getRowData(id);
    clsma.dks = id;
    clsma.pkp = data.NROPKP;
    clsma.tab.enableTab(1, 2, 3, 4, 5, 6, 7, 8, 9, 11).activeTab(8);
    $('.advisegrp').empty().html(data.NOMDKS);
    $('#bgncrs').datepicker('option', 'minDate', data.FCIPKP.substring(0, 10));
    $('#endcrs').datepicker('option', 'maxDate', data.FCVPKP.substring(0, 10));
    setDates('#bgnpak,#bgnpxg', 'minDate', data.FCIPKP.substring(0, 10));
    setDates('#endpak,#endpxg', 'maxDate', data.FCVPKP.substring(0, 10));
}

function getGroups(type) {


    clsma.type = type;
    clsma.$request({
        data: ['GROUPS', {a: clsma.dks, b: clsma.type, c: clsma.pkp, d: clsma.idepgm}],
        loading: true
    }).done(function (data) {
        $('#' + type).empty().html(data.html);
    }).done(function (data) {
        clsma.ispgm = data.ispgm;
        $('.htmlCombo').empty().html(data.combo);
        if (clsma.ispgm) {
            $('.datapgm').show();
            clsma.tab.showTab(1);
        } else {
            $('.datapgm').hide();
        }
    }).done(function () {
        $('tr[role=row]', clsma.nomtabGroup).removeClass('rowActive rowInActive');
        $('tr[role=row] td', clsma.nomtabGroup).removeClass('tdInActive tdActive');
        $(clsma.nomtabGroup).getGridParam('data').each(function () {
            if (this.CODPAK === '[I]') {
                $('tr[role=row][id=' + this.NROPAK + ']', clsma.nomtabGroup).addClass('rowIntegrated');
                $('tr[role=row][id=' + this.NROPAK + '] td', clsma.nomtabGroup).addClass('tdIntegrated');
                return;
            }
            if (this.CANEDIT === 'true') {
                $('tr[role=row][id=' + this.NROPAK + ']', clsma.nomtabGroup).addClass('rowActive');
                $('tr[role=row][id=' + this.NROPAK + '] td', clsma.nomtabGroup).addClass('tdActive');
            } else {
                $('tr[role=row][id=' + this.NROPAK + ']', clsma.nomtabGroup).addClass('rowInActive');
                $('tr[role=row][id=' + this.NROPAK + '] td', clsma.nomtabGroup).addClass('tdInActive');
            }
        });
    });
}

function loadComplete() {
    var data = $(clsma.nomtabGroup).getGridParam('data');
    $('tr[role=row]', clsma.nomtabGroup).removeClass('rowActive rowInActive');
    $('tr[role=row] td', clsma.nomtabGroup).removeClass('tdInActive tdActive');
    data.each(function () {
        if (isEmpty(this.INTEGRATED)) {
            $('tr[id=' + this.NROPAK + '] td[aria-describedby=jqGroup_subgrid]').empty();
        }
        if (this.CANEDIT === 'true') {
            $('tr[role=row][id=' + this.NROPAK + ']', clsma.nomtabGroup).addClass('rowActive');
            $('tr[role=row][id=' + this.NROPAK + '] td', clsma.nomtabGroup).addClass('tdActive');
        } else {
            $('tr[role=row][id=' + this.NROPAK + ']', clsma.nomtabGroup).addClass('rowInActive');
            $('tr[role=row][id=' + this.NROPAK + '] td', clsma.nomtabGroup).addClass('tdInActive');
        }
    });

}

function getDataMat(a, b) {

    clsma.$request({
        data: ['DATAMAT', {a: (a || ''), b: (b || ''), c: getValue('codpsm')}],
        loading: true
    }).then(function (data) {
        fillTheFormMat(data.smamat);
        if (!isEmpty(data.hrsprf)) {
            $('.tblPrfHra').empty().html(data.hrsprf);
        }
    });
}

function fillTheFormMat(data) {


    $('#crdakd').val(data.CRDMAT);
    $('#pjapak').val(isEmpty(clsma.pak) ? data.PJAMAT : data.PJAPAK);
    $('#pjbpak').val(isEmpty(clsma.pak) ? data.PJBMAT : data.PJBPAK);
    $('#pjcpak').val(isEmpty(clsma.pak) ? data.PJCMAT : data.PJCPAK);
    $('#pjdpak').val(isEmpty(clsma.pak) ? data.PJDMAT : data.PJDPAK);
    $('#teomat').val(data.TEOMAT);

    $('#pramat').val(data.PRAMAT);
    $('#invmat').val(data.INVMAT);
    $('#lblmat').prop('checked', data.LBLMAT === '1');
    $('#habmat').prop('checked', data.HABMAT === '1');
    if ((isEmpty(getValue('mdlmat')) && !clsma.isElc) || (clsma.isElc && !isEmpty(clsma.pak))) {
        $('#asignatura').search('option', 'values', [data.NROMAT, data.NOMMAT]);
        $('#codmat').val(data.CODMAT);
    }


    $('#docente,#seccional').search('option', 'disabled', false);

    if (clsma.isElc) {
        $('#asignatura').search('option', 'disabled', true);

    }
    var minDate = $('#bgnpak').datepicker('option', 'minDate');
    var maxDate = $('#endpak').datepicker('option', 'maxDate');
    if (!isEmpty(minDate) && !isEmpty(maxDate)) {
        minDate = Date.toDate(minDate).string();
        maxDate = Date.toDate(maxDate).string();

        $('#bgnpak,#bgnpxg').val(minDate);
        $('#endpak,#endpxg').val(maxDate);
    }



    $(':input:radio[name=knppak][value=' + (isEmpty(clsma.pak) ? data.KNPMAT : data.KNPPAK) + ']').prop('checked', true);
    if (!isEmpty(clsma.pak)) {
        $('#kpcpak').val(isEmpty(clsma.pak) ? data.KPCMAT : data.KPCPAK);
        $('#curso').search('option', 'disabled', true);
        $('#asignatura').search('option', 'disabled', true);
        $('#docente').search('option', 'values', [data.NROPRF, data.NOMBRE]);
        $('#seccional').search('option', 'values', [data.IDESCN, data.NOMSCN]);
        $('#curso').search('option', 'values', [data.IDECRS, data.NOMCRS]);
        $('#codpak').val(data.CODPAK);
        $('#codpsm').val(data.CODPSM);
        $('#nropak').val(clsma.pak);
        $('#bgnpak').val(data.BGNPAK.substring(0, 10));
        $('#endpak').val(data.ENDPAK.substring(0, 10));
        $('#tpoprf').val(data.TPOPRF);
        $('#hrsprf').val(data.HRSPRF);
        $('#tpohrs').val(data.TPOHRS);
        $('#tpopak').val(data.TPOPAK);
        $('.trPrfAdd').show();
        active_tab('#tabsAd', 0)

        /*Permite editar las cosas*/
        var editDisabled = true;

        $('[name=knppak]').prop('disabled', editDisabled);
        $('#habmat,#lblmat').prop('disabled', editDisabled);
        $('#pjapak,#pjbpak,#pjcpak,#pjdpak').attr('disabled', editDisabled);
        $('#teomat,#pramat,#invmat').attr('disabled', editDisabled);
        $('#codpak,#codmat,#invmat').attr('disabled', editDisabled);


        if (data.CANEDIT === 'false' && !isEmpty(data.NROPAK)) {

            hideButton('Guardar Grupo');
            $('#docente,#seccional').search('option', 'disabled', true);
            hideButton('Docente Adicional');
            disableButton('#desprf', true);
            $('.ButtonSveHrs').hide();

        } else {
            showButton('Guardar Grupo');
            $('#docente,#seccional').search('option', 'disabled', false);
            showButton('Docente Adicional');
            $('.ButtonSveHrs').show();
        }

        listPrfAdd();
        $('.hraPrf').show();

        $('#bgnpak,#bgnpxg').datepicker('option', 'minDate', data.BGNCRS).val(data.BGNCRS);
        $('#bgnpak,#bgnpxg').datepicker('option', 'maxDate', data.ENDCRS);
        $('#endpak,#endpxg').datepicker('option', 'minDate', data.BGNCRS).val(data.ENDCRS);
        $('#endpak,#endpxg').datepicker('option', 'maxDate', data.ENDCRS);


    }

    if (!isEmpty(data.NROPRF) && data.CANEDIT === 'true') {
        enableButton('#desprf', true);
    }

    validHrsPrf();

}

function detailGrp(id) {
    if (id instanceof jQuery === false && !Object.isString(id)) {
        clsma.tab.enableTab(10).activeTab(10);
        return;
    }
    var data = $(this).getGridParam('data').select(function () {
        return this.NROPAK === id;
    })[0];


    if (Object.isString(id)) {
        if (data.CODPAK === '[I]') {
            return;
        }
        clsma.pak = id;
    }
    clsma.tab.enableTab(10).activeTab(10);
}

function getDataGrp() {

    $('.datapgm').show();
    if (!clsma.ispgm || clsma.isLbr || clsma.isElc) {
        $('.datapgm').hide();
    }
    $('#asignatura').search('option', 'disabled', false);
    $('.trPrfAdd,.hraPrf').hide();
    $('#LSTADD').empty();
//    var row = $(clsma.nomtabList).getRowData(clsma.dks);
    var row = $(clsma.nomtabList).getRowData(clsma.idepgm);
    $('#bgnpak').val(row.FCIPKP.substring(0, 10));
    $('#endpak').val(row.FCVPKP.substring(0, 10));
    $('#curso').search('option', 'disabled', false);

    if (isEmpty(clsma.pak)) {
        return;
    }
    getDataMat('', clsma.pak);
}

function saveGrp() {
    var not = '#nroprfShw,#DTLADD input , #DTLADD select , #textarea,#habmat,#lblmat,#codmat,#hrsprf,#tpohrs,#tpoprf';
    if (!clsma.ispgm || clsma.isLbr || clsma.isElc) {
        not += ',#idecrsShw';
    }
    if (!validarAllform('GRP', not)) {
        return;
    }

    var form = getFormData('GRP', 'tabsAd input,tabsAd select');
    if (isEmpty($('[name=knppak]:checked'))) {
        clsma.$msg('Debe elegir si el grupo es Cuantitativo/Cualitativo');
        return;
    }
    form.tpomat = clsma.isLbr ? 'LBR' : clsma.isElc ? 'ELC' : 'MAT';
    form.knppak = $('[name=knppak]:checked').val();
    form.nropkp = clsma.pkp;
    clsma.$confirm('¿ Desea enviar los datos ?').Aceptar(function () {

        clsma.$request({
            data: ['SAVE', {form: $.toJSON(form)}],
            loading: true
        }).then(function (data) {
            clsma.$msg(data.msg, function () {
                if (clsma.isLbr)
                    clsma.tab.activeTab(4);
                else if (clsma.isElc)
                    clsma.tab.activeTab(5);
                else
                    clsma.tab.activeTab(8);
            }
            , 'OK');
        });
    });
}

function addSmt() {
    enable_tab('#tabsSmt', 1);
    active_tab('#tabsSmt', 1);
}
function saveSmt() {

    if (!validarAllform('DTLSMT')) {
        return;
    }

    var form = getFormData('DTLSMT');
    form.nropkp = clsma.pkp;
    clsma.$confirm('¿Desea enviar los datos?').Aceptar(function () {
        clsma.$request({
            data: ['SAVESMT', {form: $.toJSON(form)}],
            loading: true
        }).then(function (data) {
            clsma.$msg(data.msg, function () {
                lstSmt();
                active_tab('#tabsSmt', [0]);
            }, 'OK');
        });
    });
}
function lstSmt() {

    clsma.$request({
        data: ['GETSMT'],
        loading: true
    }).then(function (data) {
        $('#LSTSMT').empty().html(data.html);
        $('.htmlCombo').empty().html(data.combo);
    });
}

function addCrs() {
    enable_tab('#tabsCrs', 1);
    active_tab('#tabsCrs', 1);
    $('#idesmt').attr('disabled', false);
}

function saveCrs() {

    if (!validarAllform('DTLCRS', '#codcrs,#nrocrs')) {
        return;
    }

    var form = getFormData('DTLCRS');
    clsma.$confirm('¿ Desea enviar los datos ?').Aceptar(function () {

        clsma.$request({
            data: ['SAVECRS', {form: $.toJSON(form)}],
            loading: true
        }).then(function (data) {
            clsma.$msg(data.msg, function () {
                lstCrs();
                active_tab('#tabsCrs', [0]);
            }, 'OK');
        });
    });
}

function lstCrs() {
    clsma.$request({
        data: ['GETCRS'],
        loading: true
    }).then(function (data) {
        $('#LSTCRS').empty().html(data.html);
    });
}

function detailCrs(id) {
    enable_tab('#tabsCrs', 1);
    active_tab('#tabsCrs', 1);
    clsma.$request({
        data: ['DATACRS', {a: id}],
        loading: true
    }).then(function (data) {
        fillFormCrs(data.smacrs);
    });
}


function fillFormCrs(data) {
    fillForm('DTLCRS', data);
    $('#idesmt').attr('disabled', true);
}

function delGrp(a, b, c) {
    if (!isEmpty(c.NROPAK) && c.CODPAK !== '[I]' && c.CANEDIT === 'true') {
        return clsma.$deleteTemplate(('deleteGrp(\'%s\')').StringFormat(c.NROPAK));
    } else {
        return '';
    }
}

function deleteGrp(a) {

    clsma.$confirm('¿ Desea eliminar grupo ?').Aceptar(function () {

        clsma.$request({
            data: ['DELGRP', {a: a}],
            loading: true
        }).then(function (data) {
            clsma.$msg(data.msg, function () {
                if (clsma.type === 'LBR')
                    getLbr();
                else if (clsma.type === 'ELC')
                    getElc();
                else {
                    getGroups(clsma.type);
                }

            }, 'OK');
        });
    });
}

function delSmt(a, b, c) {
    return clsma.$deleteTemplate(('deleteSmt(\'%s\')').StringFormat(c.IDESMT));
}

function deleteSmt(a) {

    clsma.$confirm('¿ Desea eliminar semestre ?').Aceptar(function () {

        clsma.$request({
            data: ['DELSMT', {a: a}],
            loading: true
        }).then(function (data) {
            clsma.$msg(data.msg, lstSmt, 'OK');
        });
    });
}

function delCrs(a, b, c) {
    return clsma.$deleteTemplate(('deleteCrs(\'%s\')').StringFormat(c.IDECRS));
}


function deleteCrs(a, b) {

    var msg = ' ¿ Desea eliminar curso ?';
    if (!isEmpty(b) && b) {
        msg += ', Tenga en cuenta que este curso tiene grupos creados y posiblemente ya esten matriculas activas';
    }
    clsma.$confirm(msg).Aceptar(function () {

        clsma.$request({
            data: ['DELCRS', {a: a, b: (b || '0')}],
            loading: true
        }).then(function (data) {
            if (data.exito === 'CONFIRM') {
                deleteCrs(a, 1);
                return;
            } else {
                lstCrs();
            }
        });
    });
}

function validHorario() {
    var val = getValue('horby');
    $('.hidemat ,.hideprf,.hidecrs,.hideelec').hide();
    $('.schedulecontainer').empty();
    if (clsma.ispgm) {
        $('#horby option[value=MAT]').hide();
        $('#horby option[value=CRS], #horby option[value=PRF]').show();
    } else {
        $('#horby option[value=MAT]').show();
        $('#horby option[value=CRS], #horby option[value=PRF]').hide();
    }

    if (val !== 'SEL') {

        $('#curseohor').trigger('reset');
        $('#docentehor').trigger('reset');
        $('#asignaturahor').trigger('reset');
        $('#electivahor').trigger('reset');
        if (val === 'CRS') {
            $('.hidecrs').show();
        } else if (val === 'PRF') {
            $('.hideprf').show();
        } else if (val === 'MAT') {
            $('.hidemat').show();
        } else if (val === 'ELC') {
            $('.hideelec').show();
        }

    }

}

function getGroupsCurse() {

    $('.schedulecontainer').schedule({
        name: 'schedule',
        idecrs: getValue('crshor'),
        agnprs: clsma.agn,
        prdprs: clsma.prd,
        nroprf: getValue('prfhor'),
        idedks: clsma.dks,
        nromat: getValue('mathor'),
        codpsm: getValue('nroelc'),
        nropkp: clsma.pkp,
        view: Rutav,
        controller: Rutac,
        savePda: !clsma.ispgm ? savePda : null,
        delPda: !clsma.ispgm ? delPda : null
    });
}

function savePda() {

    var elem = $(this);
    var day = $('#win_sch').find('#daypgr').val();
    var hour = $('#win_sch').find('#hourpgr').val();
    var prd = $('#win_sch').find('#smnpak').val();
    var bgn = $('#win_sch').find('#bgnpgr').val();
    var end = $('#win_sch').find('#endpgr').val();
    var mat = $('#win_sch').find('#matpda').val();
    if (isEmpty(bgn) || isEmpty(end) || isEmpty(prd)) {
        clsma.$msg('Debe escoger los rangos de fechas y periodicidad');
        return;
    }

    clsma.$request({
        data: ['SAVEPDA', {nromat: mat, dat: day, sch: hour, prd: prd, bgn: bgn, end: end}],
        loading: true
    }).then(function (data) {
        clsma.$msg(data.msg, function () {
            elem.addClass('loaded');
        }, 'OK');
    });
}

function delPda() {
    var elem = $(this);
    var mat = elem.data('mat');
    clsma.$request({
        data: ['DELPDA', {mat: mat}],
        loading: true
    }).then(function (data) {
        clsma.$msg(data.msg, function () {
            elem.remove();
        }, 'OK');
    });
}

function newLbr() {
    clsma.isLbr = true;
    clsma.tab.enableTab(10).activeTab(10);
    $('.datapgm').hide();
}

function getLbr() {
    clsma.$request({
        data: ['GETLBR'],
        loading: true
    }).then(function (data) {
        $('#LBR').empty().html(data.html);
    });
}

function loadCompleteLbr() {
    var data = $(clsma.nomtabLbr).getGridParam('data');
    $('tr[role=row]', clsma.nomtabLbr).removeClass('rowActive rowInActive');
    $('tr[role=row] td', clsma.nomtabLbr).removeClass('tdInActive tdActive');
    data.each(function () {
        if (isEmpty(this.INTEGRATED)) {
            $('tr[id=' + this.NROPAK + '] td[aria-describedby=jqGroup_subgrid]').empty();
        }
        if (this.CANEDIT === 'true') {
            $('tr[role=row][id=' + this.NROPAK + ']', clsma.nomtabLbr).addClass('rowActive');
            $('tr[role=row][id=' + this.NROPAK + '] td', clsma.nomtabLbr).addClass('tdActive');
        } else {
            $('tr[role=row][id=' + this.NROPAK + ']', clsma.nomtabLbr).addClass('rowInActive');
            $('tr[role=row][id=' + this.NROPAK + '] td', clsma.nomtabLbr).addClass('tdInActive');
        }
    });

}

function detailLbr(id) {

    clsma.pak = id;
    clsma.isLbr = true;
    clsma.tab.enableTab(10).activeTab(10);
}

function getElc() {
    clsma.$request({
        data: ['GETELC'],
        loading: true
    }).then(function (data) {
        $('#ELC').empty().html(data.html);
    });
}

function loadCompleteElc() {
    var data = $(clsma.nomtabElc).getGridParam('data');
    $('tr[role=row]', clsma.nomtabElc).removeClass('rowActive rowInActive');
    $('tr[role=row] td', clsma.nomtabElc).removeClass('tdInActive tdActive');

    var row;
    data.each(function () {
        row = this;
        var tds = $('tr[role=row] td', clsma.nomtabElc).filter(function () {
            return $(this).text() === row.CODPSE;
        });
        var sib = tds.siblings('td').filter(function () {
            return $(this).text() === row.NOMMATELC;
        })
        if (!isEmpty(sib)) {
            if (row.CANEDIT === 'true') {
                sib.siblings().addClass('tdActive');
                sib.parent().addClass('rowActive');
            } else {
                sib.siblings().addClass('tdInActive');
                sib.parent().addClass('rowInActive');
            }
        }

    });


    data.each(function () {

        if (this.CANEDIT === 'true') {
            $('tr[role=row][id=' + this.NROPAK + ']', clsma.nomtabElc).addClass('rowActive');
            $('tr[role=row][id=' + this.NROPAK + '] td', clsma.nomtabElc).addClass('tdActive');
        } else {
            $('tr[role=row][id=' + this.NROPAK + ']', clsma.nomtabElc).addClass('rowInActive');
            $('tr[role=row][id=' + this.NROPAK + '] td', clsma.nomtabElc).addClass('tdInActive');
        }
    });

}


function newElec(id) {
    var row = $(clsma.nomtabElc).getRowData(id);
    row = $(clsma.nomtabElc).getGridParam('data').select(function () {
        return this.CODPSE === row.CODPSE && this.NOMMATELC === row.NOMMATELC;
    })[0];
    if (row.CANEDIT !== 'true') {
        hideButton('Nuevo Grupo Electiva');
        return;
    }
    showButton('Nuevo Grupo Electiva');
}

function newElective() {
    clsma.isElc = true;
    clsma.tab.enableTab(10).activeTab(10);
    var data = $(clsma.nomtabElc).getRowData($(clsma.nomtabElc).getGridParam('selrow'));
    $('#codpsm').val(data.CODPSM);
    $('#codmat').val(data.CODPSE);
    $('#asignatura').search('option', 'values', [data.NROMAT, data.NOMPSE]);
    getDataMat(data.NROMAT);

}

function detailElective(a, b, c) {
    if (!isEmpty(c.CODPAK)) {
        return ('<a href="#" style="color:green;font-weight:bolder" onclick="javascript:showElective(\'%s\')">%s</a>').StringFormat(c.NROPAK, c.CODPAK);
    } else {
        return '';
    }

}
function showElective(a) {
    clsma.pak = a;
    clsma.isElc = true;
    clsma.tab.enableTab(10).activeTab(10);
}


function msgTab(id) {

    switch (id) {
        case 'DKS':
            return 'Doble click sobre un registro de departamento para ver su programación.';
            break;
        case 'MAT':
            return ('<br/> -Doble click sobre la asignatura para ver detalle del grupo.'
                    + '<br/> -Click sobre el bote de basura para [ Eliminar ] Grupo.');
            break;
        case 'PRF':
            return ('<br/> -Doble click sobre la asignatura para ver detalle del grupo.'
                    + '<br/> -Click sobre el bote de basura para [ Eliminar ] Grupo.');
            break;
        case 'GRP':
            return ('<br/>- Modifique los datos del grupo y luego presione botón [ Guardar Grupo ] para modificar.'
                    + '<br/>- Agregue y elimine Docentes Adicionales.');
            break;
        case 'HOR':
            return ('<br>- En esta sección Usted podrá graficar y diseñar el Horario de este departamento.');
            break;
        case 'CRS':
            return ('<br>- En esta sección Usted podrá Administrar sus cursos.');
            break;
        case 'SMT':
            return ('<br>- En esta sección Usted podrá Administrar sus semestres.');
            break;
        case 'ELC':
            return ('<br>- En esta sección Usted podrá Administrar sus grupos de asignaturas electivas con base en sus lineas de énfasis.' +
                    '<br>- Click sobre una [ fila de asignatura ] luego click sobre el botón [ Nuevo Grupo Electiva ] para crear un nuevo grupo.' +
                    '<br>- Click sobre el [ link ] con color verde para editar Grupo.');
        case 'LBR':
            return ('<br>- En esta sección Usted podrá Administrar sus grupos de cursos libres.' +
                    '<br>- Doble click sobre la asignatura para ver detalle del grupo.');
            break;
        case 'INT':
            return ('<br>- En esta sección Usted podrá Administrar Los módulos de las asignaturas integradas.' +
                    '<br>- Click sobre el ícono azul para eliminar una asignatura módulo de la integrada.' +
                    '<br>- Click sobre el botón [ Nuevo ] para integrar un nuevo módulo.');
            break;
    }

}



function getModules() {

    clsma.$request({
        data: ['LSTMOD', {a: getValue('nromatInt')}],
        loading: true
    }).then(function (data) {
        $('.lstModule').empty().html(data.html);
        $('.rowButtonInt').show();
    }).done(function () {
        configButton('saveInt', '-plus', saveInt);
    })

}

function formatPtj(a, b, c) {
    return '<input type="text" id="ptj_%s"  data-mat="%s" data-ptj maxlength="5" size="6" value="%s" onblur="setValue(this)" onkeypress="return validSQLINumber(event)"/>'.StringFormat(c.NROMAT, c.NROMAT, c.PTJMDL);
}

function setValue($this) {
    var nro = $($this).data('mat');
    var row = $(clsma.nomtabModulesNew).getGridParam('data').select(function () {
        return this.NROMAT === nro;
    })[0];

    row.PTJMDL = parseFloat($this.value.replace(/\,/g, '.'));

}
function saveInt() {

    var data = $(clsma.nomtabModulesNew).getGridParam('data');
    if (data.empty()) {
        clsma.$msg('Debe seleccionar asignatura a guardar');
        return;
    }

    var arr = [];
    var tot = 0;
    data.each(function () {
        tot += this.PTJMDL;
        arr.push({
            nromat: getValue('nromatInt'),
            mdlmat: this.NROMAT,
            ptjmdl: this.PTJMDL,
            nromdl: this.NROMDL
        });
    });
    if (tot !== 100) {
        clsma.$msg('La suma de los porcentajes no debe ser menor o mayor de el 100%');
        return;
    }

    clsma.$confirm(' ¿ Desea enviar los datos ?').Aceptar(function () {

        clsma.$request({
            data: ['SAVEMDL', {form: $.toJSON(arr)}],
            loading: true
        }).then(function (data) {
            clsma.$msg(data.msg, function () {
                active_tab('#tabInt', 0);
                lstIntegerated();
            }, 'OK');
        });


    });


}

function lstIntegerated() {

    clsma.$request({
        data: ['LSTINT'],
        loading: true
    }).then(function (data) {
        $('.LSTINTE').empty().html(data.html);
    });

}

function newInteger() {

    enable_tab('#tabInt', 1);
    active_tab('#tabInt', 1);

}
function delMdl(a, b, c) {
    if (!isEmpty(c.NROMDL)) {
        return clsma.$deleteTemplate('deleteMdl(\'' + c.NROMDL + '\')');
    } else {
        return '';
    }
}
function deleteMdl(id) {

    clsma.$confirm('¿ Desea eliminar módulo ?').Aceptar(function () {

        clsma.$request({
            data: ['DELMDL', {a: id}],
            loading: true
        }).then(function (data) {
            clsma.$msg(data.msg, lstIntegerated, 'OK');
        });

    });

}

function checkBeforeExpand(id, idd) {
    var data = $(clsma.nomtabGroup).getRowData(idd);
    if (isEmpty(data.INTEGRATED)) {
        return false;
    }

}

function collapseSubGrid(subgrid_id, row_id) {
    var data = $(clsma.nomtabGroup).getRowData(row_id);
    var id = '%s_%s'.StringFormat(subgrid_id, data.CODMAT)
    $('<div id="%s"/>'.StringFormat(id))
            .empty();
}


function expandSubGrid(idTable, row_id) {
    var data = $(clsma.nomtabGroup).getRowData(row_id);
    var id = '%s_%s'.StringFormat(idTable, data.CODMAT)
    $('<div id="%s"/>'.StringFormat(id))
            .css({padding: '10px 0 '})
            .appendTo($('#' + idTable));

    clsma.$request({
        data: ['LSTINTEGRATED', {a: data.INTEGRATED}],
        loading: true
    }).then(function (data) {
        $('#' + id).empty().html(data.html);
    });
}

function loadCompleteIntegrated() {
    var table = this;
    $('tr[role=row]', table).removeClass('rowActive rowInActive');
    $('tr[role=row] td', table).removeClass('tdInActive tdActive');
    $(this).getGridParam('data').each(function () {
        if (this.CANEDIT === 'true') {
            $('tr[role=row][id=' + this.NROPAK + ']', table).addClass('rowActive');
            $('tr[role=row][id=' + this.NROPAK + '] td', table).addClass('tdActive');
        } else {
            $('tr[role=row][id=' + this.NROPAK + ']', table).addClass('rowInActive');
            $('tr[role=row][id=' + this.NROPAK + '] td', table).addClass('tdInActive');
        }
    });
}

function searchPak() {
    clsma.tab.activeTab(0);

    if (getValue('agnprs') === '999') {
        $('#DKS').empty().html('<span style="color:green;font-size:15px;font-weight:bold;text-align:center">No se han encontrado datos.</span>');
    } else {
        lstPgms();
//        lstDks();
    }

}

function addPrf() {
    enable_tab('#tabsAd', 1);
    active_tab('#tabsAd', 1);
}

function savePrfAdd() {

    if (!validarAllform('DTLADD')) {
        return;
    }

    var form = getFormData('DTLADD');
    form.nropak = clsma.pak;
    form.nroprf = form.prfpxg;
    form.tpohrs = getValue('tpohrsAdd');
    form.hrsprf = getValue('hrsprfAdd');

    clsma.$confirm('¿ Desea enviar los datos ?').Aceptar(function () {

        clsma.$request({
            data: ['SAVEADD', {a: $.toJSON(form)}]
        }).then(function (data) {
            clsma.$msg(data.msg, function () {
                active_tab('#tabsAd', 0);
                listPrfAdd();
            }, 'OK');
        });

    });
}

function listPrfAdd() {

    clsma.$request({
        data: ['LSTPXG', {a: clsma.pak}]
    }).then(function (data) {
        $('#LSTADD').empty().html(data.html);
    });

}
function formatDelAdd(a, b, c) {
    return clsma.$deleteTemplate('delPxg(\'' + c.NROPXG + '\')');
}
function delPxg(a) {

    clsma.$confirm(' ¿Desea eliminar Docente adicional ?').Aceptar(function () {

        clsma.$request({
            data: ['DELPXG', {a: a}]
        }).then(function (data) {
            listPrfAdd();
        });
    });

}

function detailPxg(a) {

    clsma.$request({
        data: ['DTLPXG', {a: a, b: clsma.pak}]
    }).then(function (data) {
        fillTheFormPxg(data.smapxg);
    });
}
function fillTheFormPxg(data) {

    fillForm('DTLADD', data);
    $('#docadicional').search('option', 'values', [data.NROPRF, data.NOMBRE]);
    $('#hrsprfAdd').val(data.HRSPRF);
    $('#tpohrsAdd').val(data.TPOHRS);
    enable_tab('#tabsAd', 1);
    active_tab('#tabsAd', 1);

}

function formatHrsPrf(a, b, c) {
    return  '<input type="text" data-cant value="%s" size="5"  data-tipo="%s"  onblur="updateValue(this)" onkeypress="return validSQLINumber(event)" maxlength="4"/>'.StringFormat(c.CNTHRS, c.NROELM);
}

function updateValue($this) {

    var tpo = $($this).attr('data-tipo');
    var row = $(clsma.nomtabHrsPrf).getGridParam('data').select(function () {
        return this.NROELM === tpo;
    })[0];

    row.CNTHRS = $this.value;


}

function addHraPrf() {

    var data = $(clsma.nomtabHrsPrf).getGridParam('data');

    if (data.empty()) {
        clsma.$msg('Debe ingresar las horas que desea Guardar.');
        return;
    }

    var aux = [];
    var agnprs = getValue('agnprs');
    var prdprs = getValue('prdprs');
    data.each(function () {
        if (parseFloat(this.CNTHRS) > 0) {

            aux.push({
                nroelm: this.NROELM,
                cnthrs: this.CNTHRS,
                agnprs: agnprs,
                prdprs: prdprs,
                nrohpr: this.NROHPR
            });

        }

    });

    clsma.$confirm('¿ Desea enviar los datos ?').Aceptar(function () {

        clsma.$request({
            data: ['SAVEHPR', {arrHrs: $.toJSON(aux), nroprf: getValue('nroprf')}]
        }).then(function (data) {
            clsma.$msg(data.msg, '', 'OK');
        });

    });

}

function getTblHrs() {

    var nro = getValue('nroprf');
    clsma.$request({
        data: ['HRSPRF', {a: nro}]
    }).then(function (data) {
        $('.tblPrfHra').empty().html(data.html);
        $('.hraPrf').show();
    });
}

function validHrsPrf() {

    var nroprf = getValue('nroprf');
    if (isEmpty(nroprf)) {
        $('#tpohrs,#tpoprf').attr('disabled', true).val(0);
        $('#hrsprf').val(0).attr('disabled', true);
    } else {
        $('#tpohrs,#tpoprf').attr('disabled', false);
        $('#hrsprf').attr('disabled', false);
    }

}

function confOthers() {

    clsma.$createStyle('.rowActive', {border: '1px solid green'});
    clsma.$createStyle('.rowInActive', {border: '1px solid red'});
    clsma.$createStyle('.rowIntegrated', {border: '1px solid orange'});
    clsma.$createStyle('.tdActive', {'background-color': 'rgba(75, 162, 78, 0.1);', color: 'black'});
    clsma.$createStyle('.tdActive:hover', {color: 'white'});
    clsma.$createStyle('.tdIntegrated:hover', {color: 'white'});
    clsma.$createStyle('.tdInActive', {'background-color': 'rgba(188, 50, 68, 0.1)', color: 'black'});
    clsma.$createStyle('.tdIntegrated', {'background-color': 'rgba(253, 162, 80, 0.1)', color: 'black'});
    validHrsPrf();
}


function  getDataScedElc(codpsm) {
    getGroupsCurse();

}












function lstPgms() {

    delete clsma.pgm;
    delete clsma.dks;
    delete clsma.type;
    delete clsma.pak;
    delete clsma.pkp;
    delete clsma.isLbr;
    delete clsma.isElc;

    clsma.agn = getValue('agnprs');
    clsma.prd = getValue('prdprs');
    clsma.$request({
        data: ['LSTPGMS', {a: getValue('agnprs'), p: getValue('prdprs')}],
        loading: true
    }).then(function (data) {
        $('#PGMS').empty().html(data.html);
    });
}



function detailProgram(id) {

    var data = $(clsma.nomtabList).getRowData(id);
    clsma.idepgm = id;
    clsma.dks = data.IDEDKS;
    clsma.pkp = data.NROPKP;
    clsma.tab.enableTab(1, 2, 3, 4, 5, 6, 7, 8, 9, 11).activeTab(8);
    $('.advisegrp').empty().html(data.NOMPGM);
    $('#bgncrs').datepicker('option', 'minDate', data.FCIPKP);
    $('#endcrs').datepicker('option', 'maxDate', data.FCVPKP);
    setDates('#bgnpak,#bgnpxg', 'minDate', data.FCIPKP);
    setDates('#endpak,#endpxg', 'maxDate', data.FCVPKP);
}

function confModules() {

    clsma.$request({
        data: ['MODCONF', {pgm: clsma.idepgm}],
        loading: true
    }).then(function (data) {
        $('.LSTINTE').empty().html(data.html);
    });

}


function confPtj(a, b, c) {
    return '<input type="text" id="ptjmdl_%s"  data-psm="%s" data-ptj maxlength="5" size="6" value="%s" onblur="setValue(this)" onkeypress="return validSQLINumber(event)"/>'.StringFormat(c.NROMAI, c.CODPSM, c.PTJMAI);
}


function savePtj(a, b, c) {
    var img = $('<img/>');
    var div = $('<div/>');
    img.attr({
        title: 'Guardar Porcentajes'
        , style: 'width:20px;height:20px'
        , onclick: 'savePorcent(\'' + c.NROMAI + '\',\'' + c.CODPSM + '\')'
        , src: Rutav + '/vista/img/Save.png'
    }).appendTo(div);
    return div.html();
}

function savePorcent(a, b) {
   var json = [];
   var s = 0;
   var p = '0';
   $.each($('#jqMai').getGridParam('data'), function (key, value) {
      if($('#ptjmdl_'+value.NROMAI).attr('data-psm')===b)  {
         p = $('#ptjmdl_'+value.NROMAI).val(); 
         s=s+parseFloat(p); 
         var mdl = { mdl: value.NROMAI, ptj: p };
         json.push(mdl);
      }
    });
    if(s!==100){
      show_message('Lo siento la suma ['+p+'] de los porcentajes no es 100%', '', 'ERROR');
      return;
    }
    clsma.$request({
        data: ['SAVEMAI', {json: $.toJSON(json)}],
        loading: true
    }).then(function (data) {
        clsma.$msg(data.msg, '', 'OK');
    });

}


function delMai(a, b, c) {
    return clsma.$deleteTemplate('deleteMai(\'' + c.NROMAI + '\')');
}

function deleteMai(id) {

    clsma.$confirm('¿ Desea eliminar módulo ?').Aceptar(function () {

        clsma.$request({
            data: ['DELMAI', {a: id}],
            loading: true
        }).then(function (data) {
            clsma.$msg(data.msg, lstIntegerated, 'OK');
        });

    });

}


