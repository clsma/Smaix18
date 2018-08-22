/* 
 * Modulo de Manejo de Extension
 */

clsma.$setScope([
    {name: Rutac + '/Adm/AcademicProgramProject', path: 'events', alias: 'main'}
], 'controller', '$request');

clsma.agnprs = '';
clsma.prdprs = '';
clsma.idepgm = '';
var isEdit = false;
var iTabs = 0 ;
var index  = 0;
var state = 'VIEW';
var vcheck = [];
var salario = 0;
var maxTabs = 7;
clsma.range = 0;
clsma.max = 0;
tabCrs = '#jqCrs';


$(function () {
    init_panel();
    configTabs();
    conf_form('60%', 'auto', 'shwDtl');
    init_buttons();
//    setIconos();
    init();
});

function configTabs() {
    genTabs('tabs', 'container');
    clsma.tab = $('#tabs').menu_vertical({
        renderAt: 'container',
        beforeActivate: function (a) {
            hideButton('Proyectar');
            hideButton('Copiar');
            var id = a.newPanel.attr('id');
            if (id === 'PROJECTS') {
                index = 1;
                this.disableTab([ 2, 3, 4, 5 ]);
                showProjects();
            } else if (id === 'SEMESTER') {
                index = 2;
                this.disableTab([ 3, 4, 5 ]);
                showSemesters();
            } else if (id === 'COURSES') {
                index = 3;
                this.disableTab([ 4, 5 ]);
                showCourses();
            } else if (id === 'PROGRAM') {
                index = 4;
            } else if (id === 'DELETE') {
                showProjeted();
                index = 5;
            }
        }
    });

}

function init_panel(id) {
    conf_panel($(window).width() - 100, 'auto', 'panel', {
        Copiar:function () {
            copySelected();
        },
        Proyectar:function () {
            projects();
        },
        Eliminar:function () {
            delProjs();
        },
        Salir: function () {
            $(this).dialog('close');
        }
    });


}

function init_buttons() {
    setIconos();
    configButton('searchPak', '-search', searchPak);
}

function init() {
    clsma.$request({
        data: ['GETCAL', {}],
        loading: true
    }).then(function (data) {
          data = $.parseJSON(data);
            if (data.exito === "OK") {
               $('#agnprs').val(data.agnprs);
               $('#prdprs').val(data.prdprs);
               clsma.agnprs = data.agnprs ;
               clsma.prdprs = data.prdprs ;
           } else {
                show_message(data.msg, 'window.history.back()', 'ERROR');
            }
    });    
}


function searchPak() {
    clsma.tab.activeTab(0);

    if (getValue('agnprs') === '999') {
        $('#divProject').empty().html('<span style="color:green;font-size:15px;font-weight:bold;text-align:center">No se han encontrado datos.</span>');
    } else {
        showProjects();
    }

}


function showProjects() {
    
    clsma.$request({
        data: ['LIST', {}],
        loading: true
    }).then(function (data) {
        data = $.parseJSON(data);
        $('#divProject').empty();
        $('#divProject').html(data.project);
        $('#divProject').show();
    });    
}

function setGenProject(a, b, c) {
   // if (c.NROPKP==='PROJECT') {
        if (c.NROPKP==='NEW') {
        var state = 'PROJ';
        var img = $('<img/>');
        var div = $('<div/>');
        img.attr({
            title: 'Proyectar'
            , style: 'width:24px;height:24px'
            , onclick: 'genProject(\'' + c.IDEPGM + '\',\'' + state + '\')'
            , src: Rutav + '/vista/img/Actualizar.png'
        }).appendTo(div);
        return div.html();
    } else {
        return '';
    }
}

function setCopProject(a, b, c) {
        var img = $('<img/>');
        var div = $('<div/>');
        var state = 'COPY';
        img.attr({
            title: 'Copiar'
            , style: 'width:24px;height:24px'
            , onclick: 'genProject(\'' + c.IDEPGM + '\',\'' + state + '\')'
//            , onclick: 'genCopy(\'' + c.IDEPGM + '\')'
            , src: Rutav + '/vista/img/copy.png'
        }).appendTo(div);
        return div.html();
}

function genProject(a,b) {
  $('#idepgm').val(a);
  state = b;
  clsma.tab.enableTab([1, 2]).activeTab(2);
}


function setVieProject(a, b, c) {
    if (c.NROPKP!=='NEW') {
        var img = $('<img/>');
        var div = $('<div/>');
        img.attr({
            title: 'Consultar'
            , style: 'width:24px;height:24px'
            , onclick: 'viewProject(\'' + c.NROPKP + '\',\'' + c.IDEPGM + '\')'
            , src: Rutav + '/vista/img/Consultar.png'
        }).appendTo(div);
        return div.html();
    } else {
        return '';
    }
}



function setViewProject(a, b, c) {
 //   if (c.STDPKP=='Proyectado') {
        var img = $('<img/>');
        var div = $('<div/>');
        img.attr({
            title: 'Consultar'
            , style: 'width:24px;height:24px'
            , onclick: 'viewCourses(\'' + c.IDESMT + '\')'
            , src: Rutav + '/vista/img/Consultar.png'
        }).appendTo(div);
        return div.html();
/*    } else {
        return '';
    }*/
}

function viewProject(a, b){    

    clsma.$request({
        data: ['SHOW', {nropkp: a, idepgm: b}],
        loading: true
    }).then(function (data) {
        data = $.parseJSON(data);
        $('#divProgrammer').empty();
        $('#divProgrammer').html(data.project);
        $('#divProgrammer').show();
        clsma.tab.enableTab([1, 4]).activeTab(4);        
    });    
 }


function setGen(a, b, c) {
    if (c.STDPKP==='Proyectar-') {
        var img = $('<img/>');
        var div = $('<div/>');
        img.attr({
            title: 'Proyectar'
            , style: 'width:24px;height:24px'
            , onclick: 'genCourses(\'' + c.IDESMT + '\',\'' + c.IDEPGM + '\',\'' + c.SMTPSM+'\')'
            , src: Rutav + '/vista/img/Actualizar.png'
        }).appendTo(div);
        return div.html();
    } /*else if (c.STDPKP=='Proyectado') {
        var img = $('<img/>');
        var div = $('<div/>');
        img.attr({
            title: 'Consultar'
            , style: 'width:24px;height:24px'
            , onclick: 'viewCourses(\'' + c.IDESMT + '\')'
            , src: Rutav + '/vista/img/Consultar.png'
        }).appendTo(div);
        return div.html();
    } */else {
        return '';
    }
}


function setCopyProject(a, b, c) {
    if (c.STDPKP==='Proyectar-') {
        var img = $('<img/>');
        var div = $('<div/>');
        img.attr({
            title: 'Copiar'
            , style: 'width:24px;height:24px'
            , onclick: 'genCopy(\'' + c.IDESMT + '\',\'' + c.SMTPSM+'\')'
            , src: Rutav + '/vista/img/copy.png'
        }).appendTo(div);
        return div.html();
    } else {
        return '';
    }
}


function genCopy(a,b) {
  var nrocrs = $('#nrocrs_'+a).val();  
  var nropsd = $('#idepsd_'+a).val();
  var chkprf = ($('#chkprf').is(':checked')===true)?'1':'0';
  var chkhro = ($('#chkhro').is(':checked')===true)?'1':'0';
//  var nropsm = document.getElementById("sch_"+a).getAttribute( "data-show" )
  clsma.$confirm('¿Desea copiar [ %s ] los curso(s) del semestre anterior ?'
            .StringFormat(nrocrs))
            .Aceptar(function () {
        clsma.$request({
            data: ['COPY', {id: a, qrs: nrocrs, psm: nropsd, sem: b, chkprf: chkprf, chkhro: chkhro}],
            loading: true
        }).then(function (data) {
            var resp = $.parseJSON(data);
            clsma.$msg(resp.msg, resp.fnc, resp.exito);
        }); 
//      clsma.tab.enableTab([1, 2]).activeTab(2);
 });
}

function genCourses(a,b,c) {
  var nrocrs = $('#nrocrs_'+a).val();  
  var nropsd = $('#idepsd_'+a).val();
  var nropsm = document.getElementById("sch_"+a).getAttribute( "data-show" )
  clsma.$confirm('¿Desea proyectar [ %s ] curso(s) en el semestre [ %s ] - Reforma [ %s]?'
            .StringFormat(nrocrs, c, nropsm))
            .Aceptar(function () {
        clsma.$request({
            data: ['PROJECT', {id: a, pgm: b, qrs: nrocrs, psm: nropsd, sem: c}],
            loading: true
        }).then(function (data) {
            var resp = $.parseJSON(data);
            clsma.$msg(resp.msg, resp.fnc, resp.exito);
        }); 
//      clsma.tab.enableTab([1, 2]).activeTab(2);
 });
}


function viewCourses(a) {
  $('#idesmt').val(a);
  clsma.tab.enableTab([1, 3]).activeTab(3);
}

function setDeleteGroup(a, b, c) {
    if (c.IDEGRP) {
        var img = $('<img/>');
        var div = $('<div/>');
        img.attr({
            title: 'Eliminar grupo'
            , style: 'width:24px;height:24px'
            , onclick: 'deleteGroup(\'' + c.IDEGRP + '\')'
            , src: Rutav + '/vista/img/delete.png'
        }).appendTo(div);
        return div.html();
    } else {
        return '';
    }
}
 

function deleteGroup(id) {

    clsma.$confirm('¿Esta seguro de eliminar este grupo?').Aceptar(function () {

        clsma.$request({
            data: ['DELGRP', {id: id}],
            loading: true
        }).then(function (data) {
            var resp = $.parseJSON(data);
            clsma.$msg(resp.msg, resp.fnc, resp.exito);
        });    
    });
}


function setDelproject(a, b, c) {
    if (c.NROFER) {
        var img = $('<img/>');
        var div = $('<div/>');
        img.attr({
            title: 'Eliminar grupo'
            , style: 'width:24px;height:24px'
            , onclick: 'deleteGroup(\'' + c.NROFER + '\')'
            , src: Rutav + '/vista/img/delete.png'
        }).appendTo(div);
        return div.html();
    } else {
        return '';
    }
}

function setDelSemester(a, b, c) {
    if (c.IDESMT!=='NEW') {
        var img = $('<img/>');
        var div = $('<div/>');
        img.attr({
            title: 'Eliminar grupo'
            , style: 'width:24px;height:24px'
            , onclick: 'deleteSemester(\'' + c.IDESMT + '\')'
            , src: Rutav + '/vista/img/delete.png'
        }).appendTo(div);
        return div.html();
    } else {
        return '';
    }
}
 

function deleteSemester(id) {

    clsma.$confirm('¿Esta seguro de eliminar este semestre?').Aceptar(function () {

        clsma.$request({
            data: ['DELSEM', {id: id}],
            loading: true
        }).then(function (data) {
            var resp = $.parseJSON(data);
            clsma.$msg(resp.msg, resp.fnc, resp.exito);
        });    
    });
}

function projects(){

    var datsel = $('#jqSmt').getGridParam('selarrrow');
    var idepgm = $('#idepgm').val();

    if (datsel.length === 0) {
        show_message("Debe seleccionar por lo menos un semestre");
        return;
    }


    show_confirmation('Desea generar la proyección?', function() {

        var datas = $('#jqSmt').getGridParam('data');
        var json = [];
        for (i in datsel) {
           var sele = { id:datas[i].IDESMT, pgm: idepgm, qrs:datas[i].NROCRS, psm:datas[i].NROPSD, sem: datas[i].SMTPSM };

           json.push(sele);
        }    

        clsma.$request({
            data: ['PROJECTS', {select: $.toJSON(json)}],
            loading: true
        }).then(function (data) {
            data = $.parseJSON(data);
                if (data.exito === 'OK') {
                    show_message(data.msg, function() {
                        window.location.reload();
                    }, 'OK');

                } else {
                    show_message(data.msg, '', 'ERROR');
                }
        });    


    });

}


function copySelected(){

    var datsel = $('#jqSmt').getGridParam('selarrrow');
    var idepgm = $('#idepgm').val();
    var chkprf = $('#chkprf').val();
    var chkhro = $('#chkhro').val();

    if (datsel.length === 0) {
        show_message("Debe seleccionar por lo menos un semestre");
        return;
    }


    show_confirmation('Desea generar la copia de todos los seleccionados?', function() {

        var datas = $('#jqSmt').getGridParam('data');
        var json = [];
        for (i in datsel) {
           var sele = { id:datas[i].IDESMT, qrs:datas[i].NROCRS, psm:datas[i].NROPSD, sem: datas[i].SMTPSM };

           json.push(sele);
        }    

        clsma.$request({
            data: ['COPIES', {chkprf: chkprf, chkhro: chkhro, select: $.toJSON(json)}],
            loading: true
        }).then(function (data) {
            data = $.parseJSON(data);
                if (data.exito === 'OK') {
                    show_message(data.msg, function() {
                        window.location.reload();
                    }, 'OK');

                } else {
                    show_message(data.msg, '', 'ERROR');
                }
        });    


    });

}


function delProjs(){
  clsma.tab.enableTab([1, 5]).activeTab(5);
}

function showProjeted() {

    var idepgm = $('#idepgm').val();
 
    genericAjax({
        url: Rutac + '/Adm/AcademicProgramProject',
        data: {events: 'VIEWPPK', idepgm: idepgm},
            loading: true,
        done: function (data) {
            data = $.parseJSON(data);
            $('#idepgm').val(data.idepgm);
            $('#divDelete').empty();
            $('#divDelete').html(data.pjcted);
            $('#divDelete').show();
        }
    });

}

function setDelPpk(a, b, c) {
    if(c.NROCRS === '01'){
        var img = $('<img/>');
        var div = $('<div/>');
        img.attr({
            title: 'Eliminar generado'
            , style: 'width:24px;height:24px'
            , onclick: 'deletePpk(\'' + c.IDECRS + '\')'
            , src: Rutav + '/vista/img/delete.png'
        }).appendTo(div);
        return div.html();
    }else {
        return '';
    }

}
 

function deletePpk(id) {

    clsma.$confirm('¿Esta seguro de eliminar este curso?').Aceptar(function () {

        clsma.$request({
            data: ['DELPPK', {id: id}],
            loading: true
        }).then(function (data) {
            var resp = $.parseJSON(data);
            clsma.$msg(resp.msg, resp.fnc, resp.exito);
        });    
    });
}

function project(){

    var datsel = $('#jqCrs').getGridParam('selarrrow');
//    var agnprs = $('#agnprs').val();
//    var prdprs = $('#prdprs').val();
    var idepgm = $('#idepgm').val();

    if (datsel.length === 0) {
        show_message("Debe seleccionar por lo menos una asignatura");
        return;
    }


    show_confirmation('Desea generar la proyección?', function() {

        var datas = $('#jqCrs').getGridParam('data');
        var json = [];
        for (i in datsel) {
            i = datsel[i];
           var sele = { id:datas[i - 1].IDESMT, pgm: idepgm, qrs:datas[i - 1].NROCRS, psm:datas[i - 1].NROPSD, sem: datas[i - 1].SMTPSM };

           json.push(sele);
        }    

        clsma.$request({
            data: ['SAVE', {agnprs: agnprs, 
                            prdprs: prdprs,
                            idepgm: idepgm,
                            select: $.toJSON(json)
                           }],
            loading: true
        }).then(function (data) {
            data = $.parseJSON(data);
                if (data.exito === 'OK') {
                    show_message(data.msg, function() {
                        window.location.reload();
                    }, 'OK');

                } else {
                    show_message(data.msg, '', 'ERROR');
                }
        });    


    });

}

function deleteRange(id) {

    clsma.$confirm('¿Esta seguro de eliminar este rango?').Aceptar(function () {
        var url = Rutac + '/Adm/AcademicProgramProject';
        genericAjax({
            url: url,
            data: {
                events: 'DELFER',
                id: id
            },
            done: function (response) {
                var resp = $.parseJSON(response);
                clsma.$msg(resp.msg, resp.fnc, resp.type);
            }
        });
    });
}


function showSemesters() {

    var idepgm = $('#idepgm').val();
          
    genericAjax({
        url: Rutac + '/Adm/AcademicProgramProject',
        data: {events: 'SHOWSEM', idepgm: idepgm, state: state},
            loading: true,
        done: function (data) {
            data = $.parseJSON(data);
            $('#idepgm').val(data.idepgm);
            $('#divSemester').empty();
            $('#divSemester').html(data.semesters);
            $('#divSemester').show();
        }
    });
  //  showButton('Proyectar');
    showButton('Copiar');

}


function setGenSemester(a, b, c){
    if (c.IDESMT==='NEW') {
        var img = $('<img/>');
        var div = $('<div/>');
        img.attr({
            title: 'Proyectar Semestre'
            , style: 'width:24px;height:24px'
            , onclick: 'genSemester(\'' + c.SMTPSM + '\')'
            , src: Rutav + '/vista/img/Actualizar.png'
        }).appendTo(div);
        return div.html();
    } else {
        return '';
    }
   
}


function genSemester(a) {
  $('#smtpsm').val(a);
  clsma.tab.enableTab([1, 3]).activeTab(3);
}

function showCourses() {

    var idesmt = $('#idesmt').val();
/*    var idepgm = $('#idepgm').val();
    var smtpsm = $('#smtpsm').val();
    var json = [];
    var smt = { smtpsm: smtpsm };
    json.push(smt);
 
    var arrSmt = $('#jqSem').getGridParam('selarrrow');
        
    $.each(arrSmt, function (a,b) {
       if ($('#jqg_jqSem_' + b).is(':checked') && smtpsm !==b ) {
            smt = { smtpsm: b };
            json.push(smt);
        }
    });
    */
    genericAjax({
        url: Rutac + '/Adm/AcademicProgramProject',
        data: {events: 'SHOWCRS', idesmt: idesmt},
            loading: true,
        done: function (data) {
            data = $.parseJSON(data);
            $('#divCourses').empty();
            $('#divCourses').html(data.cursos);
            $('#divCourses').show();
        }
    });


}


function setNumberCourse(a, b, c) {
    var html = '';

    html += ('<input type="text" id="nrocrs_%s" data-value size="8" style="text-align:right;" maxlength="5" data-valid="numeros" data-valor="' + c.NROCRS + '" value="' + c.NROCRS + '"  />').StringFormat(c.IDESMT);

    return html;
}

function formatPensum(a,b,c){
    return '<div id="sch_'+c.IDESMT+'" data-hidden="' + c.NROPSD + '" data-show= "' + c.NROPSM + '" param="'+c.IDEPGM+'"></div>';
    
}

function gridPensum() {
   
    $(this).getRowData().each(function() {
      $('#sch_'+this.IDESMT).search({
            nrosch: 'SCHPKPPSD'
            , pkey: 'idepsd_'+this.IDESMT
            , size: 10
            , isHtml:false
            , params:[this.IDEPGM]  
            , title: 'Plan de estudios'
            , beforeSearchOpen:function(){
console.debug($(this).attr('params'))
//                $('#help').val($(this).attr('param'));
            }
            , onSelectRow: function (id) {
                $(this).search('option', 'values', [id.NROPSD, id.NROPSM]);
            }
        });
        //$("#idesmt_" + this.IDESMT + "Shw").val(this.NROPSM);
//        $('#sch_'+this.IDESMT).search('option', 'values', [this.NROPSD, this.NROPSM]);
//        $('#nropsd_'+this.NROPSD+'Shw').attr('param',$('#sch_'+this.NROPSD).attr('param'));
//         
    });

}

/*

function selectCourses(data) {
    for (var i in data.rows) {
        var rows = data.rows[i];
//        $('#jqCrs').jqGrid('setSelection', rows.SMTPSM);
        $('#jqg_jqCrs_'+rows.NROMAT).attr('selected', true);
    }
}
*/

function reload() {
    clsma.$reload();
}


function setCompletePgm(data) {

    for (var i in data.rows) {
        var rows = data.rows[i];
        if (rows.NROFEP) {
            $('#lstPgm').jqGrid('setSelection', rows.IDEPGM);
            $('#jqg_lstPgm_' + rows.IDEPGM).prop('disabled', true);
        }
    }
}

function setDelGroup(a,b,c){
    var img = $('<img/>');
    var div = $('<div/>');
    img.attr({
        title: 'Eliminar Grupo'
        , style: 'width:24px;height:24px'
        , onclick: 'delGroup(\'' + c.NROPAK + '\')'
        , src: Rutav + '/vista/img/delete.png'
    }).appendTo(div);
    return div.html();
}        


function delGroup(id) {

    clsma.$confirm('¿Esta seguro de eliminar este grupo?').Aceptar(function () {

        clsma.$request({
            data: ['DELGRP', {id: id}],
            loading: true
        }).then(function (data) {
            var resp = $.parseJSON(data);
            $('#jqCrs').jqGrid('delRowData', id);       
            clsma.$msg(resp.msg, resp.fnc, resp.exito);
        });    
    });
}
