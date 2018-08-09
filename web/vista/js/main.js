

function includeJs(archivo)
{

    var oHead = document.getElementsByTagName('head')[0];
    var oScript = document.createElement('script');
    oScript.type = 'text/javascript';
    oScript.charset = 'utf-8';
    oScript.src = archivo;
    oHead.appendChild(oScript);

}
;

function includeCss(archivo)
{

    var oHead = document.getElementsByTagName('head')[0];
    var oLink = document.createElement('link');
    oLink.rel = 'stylesheet';
    oLink.href = archivo;
    oHead.appendChild(oLink);

}
;

$(document).ready(function() {


    $('#display').height($(document).height() - $('#smaFooter').height() - 150);

    var browser = navigator;
    browser = navigator.appName;

    $.ajax({
        type: 'post',
        url: Rutac + '/Adm/Introduction',
        async: false,
        data: {browser: browser},
        success: function(responseText) {
            try {

                responseText = $.parseJSON(responseText);

                if (responseText.state === 'Error')
                    return;

                var menuPrimario = $.grep(responseText.menu, function(elemento, i) {
                    return (elemento.TIPMNU === "P");
                });

                var menuSecundario = $.grep(responseText.menu, function(elemento, i) {
                    return (elemento.TIPMNU === "S");
                });

                var menuTerciario = $.grep(responseText.menu, function(elemento, i) {
                    return (elemento.TIPMNU === "T");
                });

                //*** Manejo del menú en el chrome *******//
                if (browser === 'Netscape') {
                    $("#menuOld").remove();
                    $("#cssmenu").html('<ul id="ulMenu" > </ul>')

                    $.each(menuPrimario, function(i, elemento) {
                        $("#cssmenu").find('#ulMenu').append('<li class="has-sub accor"  > <a target="" href="#"> <span> ' + elemento.PRMMNU + '</span> </a> <ul name="MN' + elemento.CODMNU + '"  ></ul>  </li>');
                    });

                    $.each(menuSecundario, function(i, elemento) {
                        //var si =$( "#cssmenu" ).find('#ulMenu').find('[name="MN'+elemento.CODMNU+'"]');
                        var isSub = '';
                        var isClass = '';
                        if (elemento.KEYMNU === 'NULL' || $.trim(elemento.KEYMNU) === '')
                            elemento.KEYMNU = 'display';
                        if (elemento.PRCMNU === 'NULL' || $.trim(elemento.PRCMNU) === '') {
                            elemento.PRCMNU = '#';
                            isSub = '<ul name="MN' + elemento.CODMNU + elemento.SUBMNU + '"  ></ul>';
                            isClass = 'has-sub';

                            $("#cssmenu").find('#ulMenu').find('[name="MN' + elemento.CODMNU + '"]').append('<li class="' + isClass + '"  > <a   href="' + elemento.PRCMNU + '">  ' + elemento.PRMMNU + ' </a>  ' + isSub + ' </li>');
                        }
                        else {
                            var a = elemento.PRCMNU.substring(0, 1);
                            var a = elemento.PRCMNU.substring(1, 1);

                            if (elemento.PRCMNU.substring(0, 1) === 'C') {
                                elemento.PRCMNU = Rutac + elemento.PRCMNU.substring(1, elemento.PRCMNU.length);
                                elemento.PRCMNU += elemento.PMTMNU;
                            } else {
                                elemento.PRCMNU = Rutav + elemento.PRCMNU.substring(1, elemento.PRCMNU.length);
                                elemento.PRCMNU += elemento.PMTMNU;
                            }

                            $("#cssmenu").find('#ulMenu').find('[name="MN' + elemento.CODMNU + '"]').append('<li class="' + isClass + '"  > <a target="' + elemento.KEYMNU + '" href="' + elemento.PRCMNU + '">  ' + elemento.PRMMNU + ' </a>  ' + isSub + ' </li>');

                        }


                    });


                    $.each(menuTerciario, function(i, elemento) {

                        if (elemento.KEYMNU === 'NULL' || $.trim(elemento.KEYMNU) === '')
                            elemento.KEYMNU = 'display';
                        if (elemento.PRCMNU === 'NULL' || $.trim(elemento.PRCMNU) === '')
                            elemento.PRCMNU = '#';
                        else {
                            var a = elemento.PRCMNU.substring(0, 1);
                            var a = elemento.PRCMNU.substring(1, 1);

                            // Anotación para el redireccionamiento de Smaix15                                                                                             // Anotación para el redireccionamiento a Smaix15
                            if (elemento.PMTMNU.indexOf('Smaix15') === 0) {
                                elemento.PRCMNU = Rutac + "/Adm/RedirectSmaix15?idemnx=" + elemento.PRCMNU;
                            } else {

                                if (elemento.PRCMNU.substring(0, 1) === 'C') {
                                    elemento.PRCMNU = Rutac + elemento.PRCMNU.substring(1, elemento.PRCMNU.length);
                                    elemento.PRCMNU += elemento.PMTMNU;
                                } else {
                                    elemento.PRCMNU = Rutav + elemento.PRCMNU.substring(1, elemento.PRCMNU.length);
                                    elemento.PRCMNU += elemento.PMTMNU;
                                }

                            }

                        }

                        $("#cssmenu").find('#ulMenu').find('[name="MN' + elemento.CODMNU + elemento.SUBMNU + '"]').append('<li  > <a target="' + elemento.KEYMNU + '" href="' + elemento.PRCMNU + '">  ' + elemento.PRMMNU + ' </a> </li>');
                    });

                } else {
                    $("#menuNew").remove();
                    $("#cssmenu").css('height', '10px');

                    $("#cssmenu").html('<ul id="css3menu1" class="topmenu"> </ul>');
                    var tam = $(menuPrimario).size();
                    $.each(menuPrimario, function(i, elemento) {
                        var clase = 'topmenu';
                        if (i === 0)
                            clase = 'topfirst';
                        if (i === tam - 1)
                            clase = 'toplast';

                        $("#cssmenu").find('#css3menu1').append('<li class="' + clase + ' accor"  > <a target="" href="#"> <span> <img height="24px" width="24px" src="./img/' + elemento.PRMMNU + '.png" alt="' + elemento.PRMMNU + '"> ' + elemento.PRMMNU + '</span> </a> <ul name="MN' + elemento.CODMNU + '"  ></ul>  </li>');
                    });


                    $.each(menuSecundario, function(i, elemento) {
                        //var si =$( "#cssmenu" ).find('#ulMenu').find('[name="MN'+elemento.CODMNU+'"]');
                        var isSub = '';
                        var isClass = '';

                        if (elemento.KEYMNU === 'NULL' || $.trim(elemento.KEYMNU) === '') {
                            elemento.KEYMNU = 'display';
                        }

                        if (elemento.PRCMNU === 'NULL' || $.trim(elemento.PRCMNU) === '') {
                            elemento.PRCMNU = '#';
                            isSub = '<ul name="MN' + elemento.CODMNU + elemento.SUBMNU + '"  ></ul>';
                            isClass = '<img src="./img/favour.png" border="0">';

                            $("#cssmenu").find('#css3menu1').find('[name="MN' + elemento.CODMNU + '"]').append('<li   > <a  href="' + elemento.PRCMNU + '">  ' + isClass + elemento.PRMMNU + ' </a>  ' + isSub + ' </li>');
                        }
                        else {
                            var a = elemento.PRCMNU.substring(0, 1);
                            var a = elemento.PRCMNU.substring(1, 1);

                            if (elemento.PRCMNU.substring(0, 1) === 'C') {
                                elemento.PRCMNU = Rutac + elemento.PRCMNU.substring(1, elemento.PRCMNU.length);
                                elemento.PRCMNU += elemento.PMTMNU;
                            } else {
                                elemento.PRCMNU = Rutav + elemento.PRCMNU.substring(1, elemento.PRCMNU.length);
                                elemento.PRCMNU += elemento.PMTMNU;
                            }

                            $("#cssmenu").find('#css3menu1').find('[name="MN' + elemento.CODMNU + '"]').append('<li   > <a target="' + elemento.KEYMNU + '" href="' + elemento.PRCMNU + '">  ' + isClass + elemento.PRMMNU + ' </a>  ' + isSub + ' </li>');

                        }

                    });


                    $.each(menuTerciario, function(i, elemento) {

                        if (elemento.KEYMNU === 'NULL' || $.trim(elemento.KEYMNU) === '')
                            elemento.KEYMNU = 'display';
                        if (elemento.PRCMNU === 'NULL' || $.trim(elemento.PRCMNU) === '')
                            elemento.PRCMNU = '#';
                        else {
                            var a = elemento.PRCMNU.substring(0, 1);
                            var a = elemento.PRCMNU.substring(1, 1);

                            // Anotación para el redireccionamiento a Smaix15
                            if (elemento.PMTMNU.indexOf('Smaix15') === 0) {
                                elemento.PRCMNU = Rutac + "/Adm/RedirectSmaix15?idemnx=" + elemento.PRCMNU;
                            } else {

                                if (elemento.PRCMNU.substring(0, 1) === 'C') {
                                    elemento.PRCMNU = Rutac + elemento.PRCMNU.substring(1, elemento.PRCMNU.length);
                                    elemento.PRCMNU += elemento.PMTMNU;
                                } else {
                                    elemento.PRCMNU = Rutav + elemento.PRCMNU.substring(1, elemento.PRCMNU.length);
                                    elemento.PRCMNU += elemento.PMTMNU;
                                }

                            }


                        }

                        $("#cssmenu").find('#css3menu1').find('[name="MN' + elemento.CODMNU + elemento.SUBMNU + '"]').append('<li  > <a target="' + elemento.KEYMNU + '" href="' + elemento.PRCMNU + '">  ' + elemento.PRMMNU + ' </a> </li>');
                    });

                }



                //*** Manejo de la actividad **/


                if (responseText.performing === false) {

                    //*** Manejo de la ventana intro (Test falta aplicar modal y todo eso) *******//

                    if (responseText.formIntro.substring(0, 1) === 'C')
                        responseText.formIntro = Rutac + responseText.formIntro.substring(1, responseText.formIntro.length);
                    else
                    if (responseText.formIntro.substring(0, 1) === 'V')
                        responseText.formIntro = Rutav + responseText.formIntro.substring(1, responseText.formIntro.length);

                    $('#display').attr('src', responseText.formIntro);

                } else {

                    var activity = responseText.activity;

                    if ($.trim(activity.srchlq) === '') {
                        return;
                    }


                    if (activity.srchlq.substring(0, 1) === 'C')
                        activity.srchlq = Rutac + activity.srchlq.substring(1, activity.srchlq.length);
                    else
                    if (activity.srchlq.substring(0, 1) === 'V')
                        activity.srchlq = Rutav + activity.srchlq.substring(1, activity.srchlq.length);

                    //srchlq= $.trim(srchlq).replace('C/Adm/',''+Rutac+'/Adm/');
                    //srchlq= $.trim(srchlq).replace('V/Vista/',''+Rutav+'/Vista/');

                    var frmhlq = activity.frmhlq;

                    if (activity.frmhlq) {

                        if (frmhlq === 'TOP')
                            self.location.href = activity.srchlq; //top.location.href = srchlq;
                        else if (frmhlq === 'WIN')
                            showWindowIntro(activity.tithlq, activity.srchlq);
                        else if (frmhlq === 'DLG')
                            showDialogInfo(activity.tithlq, activity.srchlq);
                        else
                            $('#display').attr('src', activity.srchlq);
                        /* 
                         if (frmhlq == 'TOP'){
                         self.location.href = activity.srchlq; //top.location.href = srchlq;
                         }
                         
                         else if (frmhlq == '_parent')
                         parent.location.href = activity.srchlq;
                         
                         else if (frmhlq == '_self') 
                         self.location.href = activity.srchlq;
                         
                         else
                         location.href = activity.srchlq;*/
                    } else {
                        $('#display').attr('src', activity.srchlq);
                    }
                }



            } catch (e) {
                show_message("<center class=\"caption tablas \" style=\height: 100%;font-size: medium;\" >" + e + "</center>", '', 'ERROR');

            }

        }
    });
});




function showWindowIntro(titles, url) {

    var x = (screen.Width - 200) / 2;
    var props = 'scrollbars=no,resizable=no,toolbar=no,menubar=no,status=yes,location=0,directories=no,top=200,left=' + x;
    var param = '', output = '', field = '', macro = '', newWhere = '', i = 0;


    output += '<html>';
    output += '<head>';
    output += '<title>' + titles + '</title>';
    output += '<meta http-equiv="Content-Type" content="text/html; charset=utf-8">';
    output += '</head>';
    output += '<body rightmargin="0" leftMargin="0" topMargin="0" marginheight="0" marginwidth="0" onLoad="location.href = \'' + url + '\' ">';
    output += '<table width="220" align="center" cellpadding="0" cellspacing="0">';
    output += '  <tr valign="middle" height="100">';
    output += '    <td class="caption" align="center">Cargando, espere for favor...</td>';
    output += '  </tr>';
    output += '</table>';
    output += '</body>';
    output += '</html>';

    window.open(url, titles, props);

}


function showDialogInfo(Titulo, url) {

    var Id = 'dialogModalInfoIntro';
    //var Titulo = 'Asistente para recuperar contrase&ntilde;a';
    var ancho = 'auto';
    var alto = 'auto';
    var div;
    //var url = Rutav+ '/vista/Sma/formForgetPassword.jsp';

    $("#" + Id).remove();

    var body = document.getElementsByTagName('body');
    div = document.createElement('div');
    div.setAttribute('id', Id);
    body[0].appendChild(div);
    div.setAttribute('title', Titulo);


    $('#' + Id).dialog({
        autoOpen: true,
        hide: {
            effect: "clip",
            duration: 500
        },
        resizable: false,
        buttons: {
            Cancelar: function() {
                $(this).remove();
                //$("#"+Id).remove();             	        	    	//dialog-modal_infoIntro
            }
        },
        position: 'center',
        draggable: false,
        zIndex: 900,
        modal: true,
        height: alto,
        width: ancho,
        minWidth: 600,
        minHeight: 200

    });

    //$('#'+Id).html('<iframe name="if" id="if" width="100%"  src="" frameborder="0" ></iframe>');
    //$('#dialogModalInfoIntro').find('#if').attr('src',url);
    $('#' + Id).load(url, function( ) {
        //$('#'+Id).dialog('open');
        //	$('#'+Id).dialog("option", "position", "center");
    }
    );
    $('#' + Id).dialog("option", "position", "center");
}
