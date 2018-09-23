clsma.$requestSetup({
    controller: [
        {name: Rutac + '/Adm/AcademicProgramApprove', path: 'event'}
    ],
    returning: 'object'
});

clsma.nomtabList = '#jqPgm';

$(function () {
    
    conf_panel('95%', 'auto', 'panel',
    {
        'Aprobar': save_approve
    });
    setIconos();    
    configTabs();
    //searchPak();
    configPersonalPosition();
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

function configPersonalPosition() {
    clsma.$request({
        data: ['PERSONALPOSITION', {}],
        loading: true
    }).then(function (data) {  
        if (data.exito === "OK") {                        
            clsma.topikp = data.msg ;
         } else {
            show_message(data.msg, 'window.history.back()', 'ERROR');
         }
    });    
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
            $('#PRF').find('input:checkbox[name="codgrp"]').attr('checked', true);           
        }else{
            show_message('Ah ocurrido un error ', '', 'ERROR');

        }        
    });
}

function detailProgram(id){
    var data = $(clsma.nomtabList).getRowData(id);
    clsma.tab.enableTab(0,1,2).activeTab(2);
    clsma.nropkp = data.NROPKP;
    clsma.idepgm = id;
    lstSmt();
   
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

function save_approve(){	
    
  /*  if($('tr#'+get('hddPrograma').value).hasClass('PES')){//si es una aprobacion de la solicitud
        save_re_approve();
        return;
    }
    if($('tr#'+get('hddPrograma').value).hasClass('PE3')){
        alert('Esta programacion académica ya ha sido aprobada');
        return;
    }*/
    if( !confirm('¿Esta seguro(a) de aprobar esta programacion académica?') ){ 
           return;  
      }
      
      var form = getFormData('PRF');
      form.idepgm = clsma.idepgm;
      form.nropkp = clsma.nropkp; 
      form.agnprs = clsma.agnprs; 
      form.prdprs = clsma.prdprs;
      
      clsma.$request({
        data: ['APPROVE', {form: $.toJSON(form)}],
        loading: true
    }).then(function (data) {
        if(data.exito === "OK"){            
            $('#RSM').empty().html(data.html);
        }        
    });

    $.ajax({
     type   :'post',
     url    : url,
     async  : false,
     data   : 'nropgm='+get('hddPrograma').value+'&'+'prdprs='+get('hddperiodo').value+'&'+'agnprs='+get('hddAnio').value+'&'+'accion=APPROVE',
     error  : function(objeto, detalle, otroobj){  
       showHideInactiveArea();
       show_message(detalle);           		                         
     },
     success: function(response){   
      
     }
   });        

}
	  	  	  	  
function save_re_approve(){	  		  
              if($('#lstResum,#lstSEM').size()<1){
                      alert('No ha seleccionado ningun programa');
                      return;
              }
        var url="<%=CONTROLLER %>/Adm/AcademicProgramManagerApprove";       
       // var paiide = $('#nromrfShw').val();
       // document.forms.formUpdate.nromrf.value=paiide;

        /*if($('tr#'+get('hddPrograma').value).hasClass('PES')){
                alert('Esta programacion académica ya ha sido aprobada');
                return;
        }*/
        if( !$('tr#'+get('hddPrograma').value).hasClass('PES' ))
          { 
               return;  
          }
              if(!confirm('¿Esta seguro(a) de aprobar esta modificacion de la programacion académica?')){
                       return;
              }

               $.ajax
    ({

          type   :'post',
          url    : url,
          async  : false,
          data   : 'nropgm='+get('hddPrograma').value+'&'+'prdprs='+get('hddperiodo').value+'&'+'agnprs='+get('hddAnio').value+'&'+'accion=RE_APPROVE',
          error  : function(objeto, detalle, otroobj)
                   {  
                              showHideInactiveArea();
                          show_message(detalle);           		                         
                                                   },
          success: function(response)
                   {   
                      llamarProgramas();
                       alert(response);

                       }
        });        

}

function show_txt(obj){
	
    var codgrp = $(obj).attr('id').split('_')[1];
    
    if($(obj).val() === 'Negada'){
        $('.tbljusti').each(function(){
            if($(this).attr('id') === 'tbl_'+codgrp){
                $(this).css('display','block');
            }
	});		
    }else{
	$('.tbljusti').each(function(){
            if($(this).attr('id') === 'tbl_'+codgrp){
		$(this).css('display','none');
            }
	});
		
    }
}

function validCombo(obj){
//    return;
    if($(obj).is(':checked')){
            $('#stdapp_'+$(obj).val()).attr('disabled',false);
    }else{
            $('#stdapp_'+$(obj).val()).attr('disabled',true);
    }
}
