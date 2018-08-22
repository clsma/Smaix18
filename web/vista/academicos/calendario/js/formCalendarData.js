var tpokls = document.getElementById("thescript").getAttribute("tpokls");

$(document).ready(function() {
    conf_panel('70%', 'auto', 'panel');
    $('#panel').dialog('option', 'buttons', {
        "Regresar": function() {
            redirec();
        },
        "Guardar": function() {
            saveCalendar();
        },
        "Salir": function() {
            $(this).dialog('close');
        }
    });

    conf_form('60%', '500', 'modal_pgm');
    $('#modal_pgm').dialog('option', 'buttons', {
        "Guardar": function() {
            addPgm();
        },
        "Salir": function() {
            $(this).dialog('close');
        }
    });

    conf_form('50%', '500', 'modal_axk');
    $('#modal_axk').dialog('option', 'buttons', {
        "Guardar": function() {
            addAxk();
        },
        "Salir": function() {
            $(this).dialog('close');
        }
    });


    $('#tabs').tabs();
    if ($('#codkls').val() === "") {
        $("#tabs").tabs("option", "disabled", [1, 2]);
    }else{
        listActivities();
        listPrograms();
    }

    $("#btnPgm").button({icons: {primary: "ui-icon-disk"}, text: true});
    $("#btnAxk").button({icons: {primary: "ui-icon-disk"}, text: true});
    
    if(tpokls === "Template"){
        $('#tpokls option[value="Normal.."]').attr("disabled",true);        
    }else if(tpokls === "Normal.."){
        $('#tpokls option[value="Template"]').attr("disabled",true);
        $( "#btnPgm" ).button({disabled:true});
        $("#tabs").tabs("option", "disabled", [2]);
    }
    $('input[type="text"],textarea').bind("cut copy paste", function(e) {
        e.preventDefault();
    });
    setIcons();
    
    
});


function setIcons() {
    
    setIcon('Guardar', '-disk');
    setIcon('Salir', '-closethick');
    setIcon('Regresar', '-arrowreturnthick-1-w');    
}


function saveCalendar() {
    
    if($('#nomkls').val() === ""){
        $('#nomkls').css('border', 'solid 2px red');
        $('#nomkls').focus(function() {
            $('#nomkls').css('border', '');
        });
        show_message("Debe digitar nombre del calendario");
        return;
    }
    var url = Rutac + '/Adm/Calendar';
    var pars = $('#formUpdate').serialize()+'&events=SAVEKLS';

    $.ajax
            ({
                type: 'post',
                url: url,
                async: false,
                data: pars,
                error: function(objeto, detalle, otroobj)
                {
                    show_message(detalle,'','ERROR');
                },
                success: function(response)
                {
                    var respuesta = $.parseJSON(response);
                    show_message(respuesta.mensaje,'redirec()',respuesta.icon);
                    

                }
            });
}

function redirec(){
    window.location.href = Rutav + "/vista/academicos/calendario/formCalendar.jsp";
}

function showActivities() {

    var url = Rutac + '/Adm/Calendar';
    var pars = 'events=GETAXK&codkls=' + $('#codkls').val();

    $.ajax
            ({
                type: 'post',
                url: url,
                async: false,
                data: pars,
                error: function(objeto, detalle, otroobj)
                {
                    show_message(detalle, '', 'ERROR');
                },
                beforeSend: function() {
                    $("#modal_axk").html('<table style="width: 99%;"> ' +
                            '<tr>' +
                            '<td align="center">' +
                            '<img alt="#" src="' + Rutav + '/vista/img/ajax-loader2.gif">' +
                            '</td>' +
                            '</tr>' +
                            '<tr>' +
                            '<td align="center">' +
                            'Cargando Espere por favor...' +
                            '</td>' +
                            '</tr>' +
                            '</table>');
                },
                success: function(response)
                {
                    $("#modal_axk").html(response);
                    $("#modal_axk").dialog('open');
                },
                complete: function() {                    
                    $('.calendario').each(function() {
                        
                        conf_datepicker($(this).attr('id'), 1, '', '', '');
                        
                    });
                    
                     change_datepicker_es();
                    
                    conf_table("lstActivities");
                    
                    $("#modal_axk input[name='codaxk']").each(function(){
                        var id = $(this).val();
                        
                        $('#bgnaxk_'+id).datepicker({
                             onClose: function( selectedDate ) {
                                $( '#endaxk_'+id).datepicker( "option", "minDate", selectedDate );
                              }
                        });
                        $('#endaxk_'+id).datepicker({
                             onClose: function( selectedDate ) {
                                $( '#bgnaxk_'+id).datepicker( "option", "maxDate", selectedDate );
                              }
                        });
                    });
                }
            });
}

function listActivities() {

    var url = Rutac + '/Adm/Calendar';
    var pars = 'events=LSTAXK&codkls=' + $('#codkls').val();

    $.ajax
            ({
                type: 'post',
                url: url,
                async: false,
                data: pars,
                error: function(objeto, detalle, otroobj)
                {
                    show_message(detalle, '', 'ERROR');
                },
                beforeSend: function() {
                    $("#div_actividades").html('<table style="width: 99%;"> ' +
                            '<tr>' +
                            '<td align="center">' +
                            '<img alt="#" src="' + Rutav + '/vista/img/ajax-loader2.gif">' +
                            '</td>' +
                            '</tr>' +
                            '<tr>' +
                            '<td align="center">' +
                            'Cargando Espere por favor...' +
                            '</td>' +
                            '</tr>' +
                            '</table>');
                },
                success: function(response)
                {
                    $("#div_actividades").html(response);
                }
            });
}

function showPrograms() {

    var url = Rutac + '/Adm/Calendar';
    var pars = 'events=GETPGM&codkls=' + $('#codkls').val();

    $.ajax
            ({
                type: 'post',
                url: url,
                async: false,
                data: pars,
                error: function(objeto, detalle, otroobj)
                {
                    show_message(detalle, '', 'ERROR');
                },
                beforeSend: function() {
                    $("#div_prog_modal").html('<table style="width: 99%;"> ' +
                            '<tr>' +
                            '<td align="center">' +
                            '<img alt="#" src="' + Rutav + '/vista/img/ajax-loader2.gif">' +
                            '</td>' +
                            '</tr>' +
                            '<tr>' +
                            '<td align="center">' +
                            'Cargando Espere por favor...' +
                            '</td>' +
                            '</tr>' +
                            '</table>');
                },
                success: function(response)
                {
                    $("#div_prog_modal").html(response);
                    $("#modal_pgm").dialog('open');
                },
                complete: function() {
                    conf_table("lstPrograms");
                }
            });
}

function listPrograms() {

    var url = Rutac + '/Adm/Calendar';
    var pars = 'events=LSTPGM&codkls=' + $('#codkls').val();

    $.ajax
            ({
                type: 'post',
                url: url,
                async: false,
                data: pars,
                error: function(objeto, detalle, otroobj)
                {
                    show_message(detalle, '', 'ERROR');
                },
                beforeSend: function() {
                    $("#div_programas").html('<table style="width: 99%;"> ' +
                            '<tr>' +
                            '<td align="center">' +
                            '<img alt="#" src="' + Rutav + '/vista/img/ajax-loader2.gif">' +
                            '</td>' +
                            '</tr>' +
                            '<tr>' +
                            '<td align="center">' +
                            'Cargando Espere por favor...' +
                            '</td>' +
                            '</tr>' +
                            '</table>');
                },
                success: function(response)
                {
                    $("#div_programas").html(response);
                }
            });
}


function showCalendar(codkls) {

}

function addAxk(){    
    var flag = true;
    $("#modal_axk input[name='codaxk']").each(function(){
        if($(this).is(':checked')){
            var id = $(this).val();
            if($('#bgnaxk_'+id).val() === ""){
                $('#bgnaxk_'+id).css('border', 'solid 2px red');
                $('#bgnaxk_'+id).focus(function() {
                    $('#bgnaxk_'+id).css('border', '');
                });
                flag = false;
            }
            if($('#endaxk_'+id).val() === ""){
                $('#endaxk_'+id).css('border', 'solid 2px red');
                $('#endaxk_'+id).focus(function() {
                    $('#endaxk_'+id).css('border', '');
                });
                flag = false;
            }
        }
    });
    
    if (!flag){
        show_message("Existen fechas en blanco , debe diligenciar todas las fechas de las actividades escogídas que estan marcadas en rojo");
        return;
    }
    
    var url = Rutac + '/Adm/Calendar';
    var pars = $('#formAxk').serialize() + '&events=SAVEAXK&codkls='+$('#codkls').val();

    $.ajax
            ({
                type: 'post',
                url: url,
                async: false,
                data: pars,
                error: function(objeto, detalle, otroobj)
                {
                    show_message(detalle,'','ERROR');
                },
                success: function(response)
                {
                    var respuesta = $.parseJSON(response);
                    show_message(respuesta.mensaje,'',respuesta.icon);
                    

                },
                complete: function() {
                    listActivities();
                }
            });
}

function addPgm(){
    
    var url = Rutac + '/Adm/Calendar';
    var pars = $('#formPgm').serialize() + '&events=SAVEPGM&codkls='+$('#codkls').val();

    $.ajax
            ({
                type: 'post',
                url: url,
                async: false,
                data: pars,
                error: function(objeto, detalle, otroobj)
                {
                    show_message(detalle,'','ERROR');
                },
                success: function(response)
                {
                    var respuesta = $.parseJSON(response);
                    show_message(respuesta.mensaje,'',respuesta.icon);
                    

                },
                complete: function() {
                    listPrograms();
                }
            });
}

function marcartodos(){
    $('.check_1').each(function(){
       this.checked = true;
    });
}

function desmarcartodos(){
    $('.check_1').each(function(){
       this.checked = false;
    });
}