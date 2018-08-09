/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var Id = 'dialog-modal_forgpass';

function forgetPassword() {


    var Titulo = 'Asistente para recuperar contraseña';
    var ancho = '600';
    var alto = 'auto';
    var div;
    var url = Rutav + '/vista/formForgetPassword.jsp';

    $("#" + Id).remove();

    var body = document.getElementsByTagName('body');
    div = document.createElement('div');
    div.setAttribute('id', Id);
    body[0].appendChild(div);
    div.setAttribute('title', Titulo);


    $('#' + Id).dialog({
        autoOpen: false,
        hide: {
            effect: "clip",
            duration: 500
        },
        resizable: false,
        position: 'center',
        draggable: false,
        zIndex: 900,
        modal: true,
        height: alto,
        width: ancho,
        minWidth: 600,
        minHeight: 200

    });

    $('#' + Id).dialog('option', 'buttons', {
        Recuperar: function() {
            recuperar();
        },
        Salir: function() {
            $(this).remove();
        }
    });

    $(":button:contains('Recuperar')").button(
            {icons:
                        {
                            primary: "ui-icon-disk",
                            secondary: 'ui-icon ui-icon-grip-dotted-vertical'
                        }
            }
    );
    $(":button:contains('Salir')").button(
            {icons:
                        {
                            primary: "ui-icon-closethick",
                            secondary: 'ui-icon ui-icon-grip-dotted-vertical'
                        }
            }
    );
    $('#' + Id).load(url, function() {
        $("#btnSch").button(
                {icons:
                            {
                                primary: "ui-icon-search",
                                secondary: 'ui-icon ui-icon-grip-dotted-vertical'
                            }
                });
        $("#btnSch").button().click(
                function() {
                    authentication();
                }
        );
        $('#panelInformativo').hide();
        $('#panelSeguridad').hide();
        $(":button:contains('Recuperar')").hide();
        $('input').bind("cut copy paste", function(e) {
            e.preventDefault();
        });
        $('textarea').bind("cut copy paste", function(e) {
            e.preventDefault();
        });


        $('#' + Id).dialog('open');
    }
    );

}

function cambioTpo(this_) {
    if ($(this_).val() === 'nriprs') {
        $('#tpo').html('Nro.Identificacion:');
        $('#formUpdateForgetPass').find('#codprs').attr('title', 'Nro.Identificacion');

    } else {
        $('#tpo').html('Código:');
        $('#formUpdateForgetPass').find('#codprs').attr('title', 'Código');
    }

}



function authentication() {

    var urlController = Rutac + '/Adm/ForgetPasswordPublic';
    var codcia = $('#formUpdateForgetPass').find('#codcia').val();
    var codprs = $('#formUpdateForgetPass').find('#codprs').val();
    var tpodoc = $('#formUpdateForgetPass').find('#tpodoc').val();

    if ($.trim(codcia) === '') {
        show_message('la institución es obligatoria', '', 'ERROR');
        return;
    }
    if ($.trim(codprs) === '') {
        var title = $('#formUpdateForgetPass').find('#codprs').attr('title');
        show_message('El campo ' + title + ' es obligatorio', '', 'ERROR');
        return;
    }
    var pars = 'accion=AUTH_USER&codcia=' + codcia
            + '&codprs=' + codprs
            + '&tpodoc= ' + tpodoc;
    $.ajax({
        type: 'post',
        url: urlController,
        async: true,
        data: pars,
        success: function(responseText) {
            try {
                responseText = $.parseJSON(responseText);

                if (responseText.state === "Exito") {

                    $("#emlprs").val(responseText.emlprs);
                    $("#fldprs").val(responseText.field);

                    $("#contenSeguridad").html(responseText.html);

                    if (responseText.tpo === 'date')
                        conf_datepicker('answer', 1, '', '');

                    $(":button:contains('Recuperar')").show();

                    $('#panelInformativo').show();
                    $('#panelSeguridad').show();

                } else {
                    $('#panelInformativo').hide();
                    $('#panelSeguridad').hide();
                    show_message(responseText.msg, '', responseText.state);
                }
            } catch (e) {
                show_message(e, '', 'ERROR');
            }
        }
    });
}



function recuperar() {

    var urlController = Rutac + '/Adm/ForgetPasswordPublic';
    var codcia = $('#formUpdateForgetPass').find('#codcia').val();
    var codprs = $('#formUpdateForgetPass').find('#codprs').val();
    var fldprs = $('#formUpdateForgetPass').find('#fldprs').val();
    var answer = $('#formUpdateForgetPass').find('#answer').val();
    var emlprs = $('#formUpdateForgetPass').find('#emlprs').val();
    var tpodoc = $('#formUpdateForgetPass').find('#tpodoc').val();

    if ($.trim(emlprs) === '' || $.trim(emlprs) === 'no tiene') {
        show_message('Lo sentimos el correo no es valido!, por favor acerquese a la institución para actualizar sus datos', 'reloadForget()', 'ERROR');
        return;
    }

    if ($.trim(answer) === '' || $.trim(answer) === 'no tiene') {
        show_message('Favor suministre la información que se le solicita!', 'reloadForget()', 'ERROR');
        return;
    }

    $.ajax({
        type: 'post',
        url: urlController,
        async: true,
        data: {
            accion: 'VERI_ANSWER',
            codcia: codcia,
            codprs: codprs,
            fldprs: fldprs,
            answer: answer,
            tpodoc: tpodoc
        },
        success: function(responseText) {
            responseText = $.parseJSON(responseText);

            show_message(responseText.mensaje, 'reloadForget()', responseText.state);

        },
        error: function(objeto, detalle, otroobj) {
            show_message(detalle, '', 'ERROR');
        }
    });
}

function reloadForget() {

    $('#' + Id).remove();
    $('#panelInformativo').hide();
    $('#panelSeguridad').hide();

}