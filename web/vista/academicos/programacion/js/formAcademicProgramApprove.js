clsma.$requestSetup({
    controller: [
        {name: Rutac + '/Adm/AcademicProgramApprove', path: 'event'}
    ],
    returning: 'object'
});

clsma.nomtabList = '#jqPgm';

$(function () {
    
    conf_panel('95%', 'auto', 'panel');
    setIconos();    
    configTabs();
    //searchPak();
    configDateCalendar();
});

function searchPak() {
    //clsma.tab.enableTab(0,1).activeTab(0).disableTab(1);

   // clsma.tab.activeTab(1);
    if (getValue('agnprs').length === 0) {
        $('#PGM').empty().html('<span style="color:green;font-size:15px;font-weight:bold;text-align:center">No se han encontrado datos.</span>');
    } else {
        lstPgm();
//        lstDks();
    }

}

function configTabs() {
    genTabs('tabs', 'container');
    
        clsma.tab = $('#tabs').menu_vertical({
        renderAt: 'container',
        beforeActivate: function (a) {
            var id = a.newPanel.attr('id');
            $('#PGM , #SMT , #PRF , #RSM , #DTL').empty();                        
            if (id === 'PGM') {
                //this.disableTab(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12).hideTab(1, 12);                                
                //lstPgm();
                searchPak();
                this.disableTab(1);
                //$('.advisegrp').empty();                
                
            }else if(id === 'DTL'){
                clsma.tab.enableTab(0,1);                
            }else if(id === 'SMT'){
                lstSmt();
            }else if(id === 'PRF'){
                lstPrf();
            }else if(id === 'RSM'){
                lstSummary();
            }
        }
    });   
    
    configButton('searchPak', '-search', searchPak);
}

function configDateCalendar() {
    clsma.$request({
        data: ['GETCAL', {}],
        loading: true
    }).then(function (data) {  
        if (data.exito === "OK") {
            $('#agnprs').val(data.agnprs);
            $('#prdprs').val(data.prdprs);
            
            clsma.agnprs = data.agnprs ;
            clsma.prdprs = data.prdprs ;
            lstPgm();
         } else {
            show_message(data.msg, 'window.history.back()', 'ERROR');
         }
    });    
}

function lstPgm() {                    
    clsma.$request({
        data: ['GET_LIST_PROGRAM_APPROVE', {agnprs: clsma.agnprs, prdprs: clsma.prdprs}],
        loading: true
    }).then(function (data) {
        
        $('#PGM').empty().html(data.html);
    });
}

function lstSmt() {                    
    clsma.$request({
        data: ['GET_TABLE_SEMESTER', {idepgm: clsma.idepgm, nropkp: clsma.nropkp}],
        loading: true
    }).then(function (data) {        
        $('#SMT').empty().html(data.html);
        //lstPrf();
    });
}

function lstPrf() {                    
    clsma.$request({
        data: ['GET_TABLE_TEACHER', {idepgm: clsma.idepgm, nropkp: clsma.nropkp, agnprs:clsma.agnprs, prdprs: clsma.prdprs}],
        loading: true
    }).then(function (data) {
        $('#PRF').empty().html(data.html);        
        if(data.exito === "OK"){            
            //$(document).ready(function(){
                $( "#acordeon" ).accordion({
                      heightStyle: "content",
                      collapsible: true
                });
                $('.ui-accordion-content').css("height","auto");    
             //});
        }        
    });
}

function detailProgram(id){
    var data = $(clsma.nomtabList).getRowData(id);
    clsma.tab.enableTab(0,1,2).activeTab(2);
    alert(id+' | '+data.IDEPGM+' # '+data.NROPKP);
    clsma.nropkp = data.NROPKP;
    clsma.idepgm = id;
    lstSmt();
   
    /*clsma.idepgm = id;
    clsma.dks = data.IDEDKS;
    clsma.pkp = data.NROPKP;
    clsma.agnprs = $('#agnprs').val();
    clsma.prdprs = $('#prdprs').val();
    clsma.tab.enableTab(1, 2, 3, 4, 5, 6, 7, 8, 9, 11).activeTab(8);
    clsma.tab.enableTab(1, 2, 3, 4, 5, 6, 7, 8, 9, 11).activeTab(8).hideTab(11);
    $('.advisegrp').empty().html(data.NOMPGM);
    $('#bgncrs').datepicker('option', 'minDate', data.FCIPKP);
    $('#endcrs').datepicker('option', 'maxDate', data.FCVPKP);
    setDates('#bgnpak,#bgnpxg', 'minDate', data.FCIPKP);
    setDates('#endpak,#endpxg', 'maxDate', data.FCVPKP);*/
}

function lstSummary(){
        clsma.$request({
        data: ['GET_TABLE_SUMMARY', {idepgm: clsma.idepgm, 
                                     nropkp: clsma.nropkp, 
                                     agnprs:clsma.agnprs, 
                                     prdprs: clsma.prdprs}],
        loading: true
    }).then(function (data) {
        if(data.exito === "OK"){            
            $('#RSM').empty().html(data.html);
        }        
    });
}
function processDetailProgram(){
    $('#SEM > div').load(url,{ 'anio':anio,
                               'periodo':periodo, 
                               'programa':programa,
                               'codkls':codkls,
                               'accion':'GET_TABLE_SEMESTER'}, function (){
        $('#DOC > div').load(url,{ 'anio':get('hddAnio').value,
                                   'periodo':get('hddperiodo').value, 
                                   'programa':get('hddPrograma').value,
                                   'codkls':codkls,
                                   'accion':'GET_TABLE_TEACHER'},function(){
                url="<%=CONTROLLER %>/Adm/AcademicProgramManagerApprove?";
                $('#RES > div').load(url,{ 'anio':get('hddAnio').value,'periodo':get('hddperiodo').value, 'programa':get('hddPrograma').value,'accion':'GET_TABLE_SUMMARY'});
                get('hddajax').value='0';
                $(document).ready(function(){
                    $( "#acordeon" ).accordion({
                          heightStyle: "content",
                          collapsible: true
                    });
                    $('.ui-accordion-content').css("height","auto");    
                });
            });				    
    });
}
	