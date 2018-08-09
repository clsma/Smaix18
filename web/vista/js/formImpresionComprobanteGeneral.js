/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function init(){
    conf_panel('900','auto','panel');
  	   $( "#panel" ).dialog( "option", "dialogClass", 'the_panel' );

       $( "#panel" ).dialog( "option", "buttons", { 
    	   "Buscar": function() {search_ord();},
		   "Salir": function()   { $('#oculto').toggle(200);										
					 $(this).dialog('close'); } 
		});
       $( "#panel" ).dialog( "option", "align","top");
}


function search_ord(){
    
    var url = Rutac + '/Adm/ImpresionComprobante';
      
   $.ajax
            ({
                type: 'post',
                url: url,
                async: false,
                data: "events=GRIDPRINTBAS"+"&codbas="+$("#codbas").val(),
                error: function(objeto, detalle, otroobj){                    
                    show_message(detalle);                    
                },
                success: function(response){
                    $('#comprobantesTucomandita').html(response);
                }
            });
    
}