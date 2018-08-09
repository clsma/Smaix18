var urlbase = document.getElementById("scriptinit").getAttribute("urlbase");
var stylebase = document.getElementById("scriptinit").getAttribute("stylebase");


Rutav = urlbase;
var open = '0';
var codofrx = '';


$(document).ready(function() {
    $('.itemOculto').hide();

    conf_form('90%', 'auto', 'model_menu', 'top');
    $("#model_menu").dialog('option', 'position', 'top');
    conf_form('90%', 'auto', 'model_individual', 'top');
//    $('input[type="text"],textarea').bind("cut copy paste", function(e) {
//        e.preventDefault();
//    });
    checkIndex();
    if (navigator.userAgent.toLowerCase().indexOf('chrome') === -1) {
        show_message("Para una mejor experiencia use google chrome");
    }


    $('#pswprs').keypress(function(e) {
        if (e.keyCode === 13) {
            e.preventDefault();
            isLoging($('#formLogin'));
            return false;
        }
    });
    $('#btnLogin').click(function(e) {
        e.preventDefault();
        isLoging($('#formLogin'));
        return false;
    });
    getSedes();
});

function isLoging($elem) {
    return validThelogin($elem);
}

function validLogin()
{
    var codigo = document.getElementById("codprs").value;
    var clave = document.getElementById("pswprs").value;
//    if (codigo.trim() === '' || clave.trim() === '') {
//        return;
//    }

    var formLogin = $('#formLogin').serialize();
    var w = screen.availWidth - 7;
    var h = screen.availHeight - 45;
    document.formLogin.submit();
}

function capturarDatos() {

    /*funcion que  ejecuta la misma funcion cuando se da click en aceptar del teclado*/
    do_stuff();
    /**/
    validLogin();
}


var lista = new Array(urlbase + '/vista/styles/' + stylebase + '/img/imginit/banner1.jpg',
        urlbase + '/vista/styles/' + stylebase + '/img/imginit/banner2.jpg',
        urlbase + '/vista/styles/' + stylebase + '/img/imginit/banner3.jpg');

var lista2 = new Array(urlbase + '/vista/styles/' + stylebase + '/img/imginit/banner4.jpg',
        urlbase + '/vista/styles/' + stylebase + '/img/imginit/banner5.jpg',
        urlbase + '/vista/styles/' + stylebase + '/img/imginit/banner6.jpg');
var contador = 0;
var tiempo = 0;
var tempor = null;

function changeImg()
{
    setInterval("cambio(1)", 2000);
}

function cambio(sen)
{
    contador += sen;

    if (contador === lista.length)
        contador = -1;
    else
    {
        //$('#imgRandom').hide('slow');
        //document.images.imgRandom.src = lista[contador]
        $('#imgRandom').attr('src', lista[contador]);
        $('#imgJef').attr('src', lista2[contador]);
        //$('#imgRandom').show('slow');
    }

    window.status = "Imagen número " + contador;
}

function getSedes() {
    $.ajax({
        type: 'POST',
        url: Rutac + '/Adm/InitPage?state=GETSEDE',
        data: {ruta: Rutav},
        success: function(data) {
            data = $.parseJSON(data);
            $('#sedes').html(data.Sedes);
        }, error: function(data) {
            html = '<div style="clear:both;heigth:50px;width:100px"></div>'
        }
    });

}


function showPanel() {
    $('#model_menu').html('<fieldset> ' +
            '<table style="width: 99%;"> ' +
            '<tr> ' +
            '<td align="center"> ' +
            '<div align="center" class="demo"> ' +
            '<button id="PGM" type = "button" onclick="showOffers()">Resultados Por Programa</button> ' +
            '</div>' +
            '</td> ' +
            '<td align="center"> ' +
            '<div align="center" class="demo"> ' +
            '<button id="PRS" type = "button" onclick="showIndividual()">Resultado Individual</button> ' +
            '</div>' +
            '</td> ' +
            '</tr> ' +
            '</table> ' +
            '</fieldset> ');
    $("#PGM").button({icons: {primary: "ui-icon-search"}, text: true});
    $("#PRS").button({icons: {primary: "ui-icon-search"}, text: true});
    $('#model_menu').dialog('open');
    $('#model_menu').dialog('option', 'buttons', {});
}

function showOffers() {

    var url = '<%= CONTROLLER %>/Adm/AdmissionTestResults';
    var pars = 'events=OFFERS';

    $.ajax
            ({
                type: 'post',
                url: url,
                async: false,
                data: pars,
                error: function(objeto, detalle, otroobj)
                {
                    show_message("Ocurrio un problema al procesar información de resultados");
                },
                success: function(response)
                {
                    $("#model_menu").html('');
                    $("#model_menu").html(response);


                }
            });
}

function showIndividual() {

    var url = '<%= CONTROLLER %>/Adm/AdmissionTestResults';
    var pars = 'events=OFRIND';

    $.ajax
            ({
                type: 'post',
                url: url,
                async: false,
                data: pars,
                error: function(objeto, detalle, otroobj)
                {
                    alert(error + ", Detalle: " + detalle);
                },
                success: function(response)
                {
                    $("#model_menu").html('');
                    $("#model_menu").html(response);


                }
            });

}

function showForm(codofr) {

    $("#model_menu").html('<fieldset> ' +
            '<table style="width: 99%;"> ' +
            '<tr> ' +
            '<td colspan="2" align="center"> ' +
            '<fieldset> ' +
            '<img src="<%=BASEURL %>/vista/img/LOGO.png"> ' +
            '</fieldset> ' +
            '</td> ' +
            '</tr> ' +
            '<tr> ' +
            '<td width="50%" align="right" id="lblsnpprs" > ' +
            'Documento de Identidad: ' +
            '</td> ' +
            '<td> ' +
            '<input type="text" class = "text" value = "" name = "nriprs" id = "nriprs" size = "16" maxlength = "15" title = "Documento de Identidad"> ' +
            '<input type="hidden" value = "' + codofr + '" name = "codofr" id = "codofr"> ' +
            '</td> ' +
            '</tr> ' +
            '<tr> ' +
            '<td colspan="10"> ' +
            '<div id="div_info_admission"> </div> ' +
            '</td> ' +
            '</tr> ' +
            '</table> ' +
            '</fieldset> ');

    $('#model_menu').dialog('option', 'buttons', {
        "Consultar": function() {
            send();
        },
        "Salir": function() {
            $(this).dialog('close');
        }
    });

    $('#nriprs').keydown(function(tecla) {

        if (tecla.keyCode === 13) {
            send();
        }
    });
}

function send() {

    if ($('#nriprs').val() === "") {
        alert("Debe digitar su numero de identificacion");
        return;
    }
    var url = '<%= CONTROLLER %>/Adm/AdmissionTestResults';
    var pars = 'events=NRIPRS&nriprs=' + $('#nriprs').val() + '&codofr=' + $('#codofr').val();

    $.ajax
            ({
                type: 'post',
                url: url,
                async: false,
                data: pars,
                error: function(objeto, detalle, otroobj)
                {
                    alert(error + ", Detalle: " + detalle);
                },
                success: function(response)
                {

                    $("#div_info_admission").html(response);

                }
            });
}

function showPrograms(codofr) {

    var url = '<%= CONTROLLER %>/Adm/AdmissionTestResults';
    var pars = 'events=PROGRAMS&codofr=' + codofr;

    $.ajax
            ({
                type: 'post',
                url: url,
                async: false,
                data: pars,
                error: function(objeto, detalle, otroobj)
                {
                    alert(error + ", Detalle: " + detalle);
                },
                success: function(response)
                {

                    $("#model_menu").html('');
                    $("#model_menu").html(response);


                    $("#accordion_alter").accordion("destroy");

                    $("#accordion_alter").accordion({
                        active: false,
                        autoHeight: false,
                        navigation: true,
                        fillSpace: false,
                        collapsible: true,
                        disabled: false
                    });

                }
            });
}

function showResults(nropgm, codofr) {

    var url = '<%= CONTROLLER %>/Adm/AdmissionTestResults';
    var pars = 'events=RESULTS&codofr=' + codofr + '&nropgm=' + nropgm;

    $.ajax
            ({
                type: 'post',
                url: url,
                async: false,
                data: pars,
                error: function(objeto, detalle, otroobj)
                {
                    alert(error + ", Detalle: " + detalle);
                },
                success: function(response)
                {
                    $('#div_' + nropgm + '_' + codofr).html(response);
                }
            });

}

function checkIndex()
{
    var isInIFrame = (window.location !== window.parent.location);
    if (isInIFrame === true) {
        $("body").html('');
        $('.container').html('');
        $('#footer').html('');

        $("body").css("background", "none");
        window.top.location.href = urlbase + '/vista/init.js';
//        show_message("La sessión ha expirado, por favor ingrese de nuevo a la aplicación", "window.top.location.href='" + urlbase + "/vista/init.jsp'", 'INFO');
    }


}

function validThelogin($this) {

    var bool = true;

    $($this).find('input[type=text],[type=password].input-control').each(function() {

        if (clsma.$isEmpty($(this).val())) {

            $(this).focus().css({
                border: '1px solid red'
            }).blur(function() {
                $(this).css({
                    border: '1px solid gray'
                });
            });
            bool = false;
            return false;
        } else {
            $(this).blur(function() {
                $(this).css({
                    border: '1px solid gray'
                });

            });
        }
    });

    if (!!bool) {
        makeSender();
    }

    return false;

}

function makeSender() {


    $('#pswprs').attr('disabled', true);
    $('#codprs').attr('disabled', true);
    $('#codcia').attr('disabled', true);

    clsma.$encryptForm('#formLogin', 'test')
            .$send();

}