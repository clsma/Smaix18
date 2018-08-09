/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.ge
 */

var cache = {};
(function () {

    $.ajaxSetup({headers: {'Ajax-Request': true}});

})();
function showLoader(mensaje, image) {
    if (!mensaje)
        mensaje = "Cargando espere por favor...";
    if (!image)
        image = Rutav + "/vista/img/ajax-loader2.gif";
    $.loader();
    $("#jquery-loader").html('<table style="width: 99%;"> ' +
            '<tr>' +
            '<td align="center">' +
            '<img alt="#" src="' + image + '">' +
            '</td>' +
            '</tr>' +
            '<tr>' +
            '<td align="center">' +
            '<b style="font-size:12px">' + mensaje + '</b>' +
            '</td>' +
            '</tr>' +
            '</table>');
    $("#jquery-loader").css('height', '90px');
    $("#jquery-loader").css('width', '200px');
    $("#jquery-loader").css('background-color', 'rgb(239, 239, 239)');
    $("#jquery-loader").css('border-style', 'groove');
    $("#jquery-loader").css('border-radius', '10px');
    $("#jquery-loader").css('border-width', '1px');
    $("#jquery-loader-background").css('background-color', 'black');
    $("#jquery-loader-background").css('filter', 'alpha(opacity=25)');
}

function hideLoader() {
    $.loader('close');
}

function validSQLI(e) {
    var caracter = String.fromCharCode(e.which);
    if (!caracter.match(/^[\s,ñÑáéíóúÁÉÍÓÚ,-.()]*$/)) {
        if (!caracter.match(/^[a-zA-Z0-9]*$/)) {
            return false;
        }
    }
}

function fixFormatoFecha($id) {
    var fecha = $('#' + $id).val();
    fecha = fecha.split("00:00:00.0")[0];
    $('#' + $id).val(fecha);
}

function validSQLILetters(e) {

    if (clsma.$navigatores.Mozilla()) {
        if (e.which === 8) {
            return true;
        }
    }
    var caracter = String.fromCharCode(e.which);
    if (!!(caracter.match(new TESTER().LETRA))) {
        return true;
    }
    return false;
}

function validSQLILettersAndNumber(e) {
    if (clsma.$navigatores.Mozilla()) {
        if (e.which === 8) {
            return true;
        }
    }

    var caracter = String.fromCharCode(e.which);
    if (!!(caracter.match(new TESTER().LETRANUMERO))) {
        return true;
    }
    return false;
}

function validSQLIAddress(e) {
    if (clsma.$navigatores.Mozilla()) {
        if (e.which === 8) {
            return true;
        }
    }
    var caracter = String.fromCharCode(e.which);
    if (!!(caracter.match(new TESTER().ADDRESS))) {
        return true;
    }
    return false;
}

function validSQLIEmail(e) {
    if (clsma.$navigatores.Mozilla()) {
        if (e.which === 8) {
            return true;
        }
    }
    var caracter = String.fromCharCode(e.which);
    if (!!(caracter.match(new TESTER().MAIL))) {
        return true;
    }
    return false;
}

function validSQLIWeb(e) {
    if (clsma.$navigatores.Mozilla()) {
        if (e.which === 8) {
            return true;
        }
    }
    var caracter = String.fromCharCode(e.which);
    if (!!(caracter.match(new TESTER().WEB))) {
        return true;
    }
    return false;
}

function validSQLIPassword(e) {
    if (clsma.$navigatores.Mozilla()) {
        if (e.which === 8) {
            return true;
        }
    }
    var caracter = String.fromCharCode(e.which);
    return !!(caracter.match(new TESTER().PASSWORD));
}

function validSQLINumber(e, callback) {
    if (clsma.$navigatores.Mozilla()) {
        if (e.which === 8) {
            return true;
        }
    }

    var caracter = String.fromCharCode(e.which);
    var value = $(e.target).val();
    if (caracter === '.' || caracter === ',') {
        if (value.indexOf(',') !== -1 || value.indexOf('.') !== -1) {
            return false;
        }
    } else {

        if (!caracter.match(new TESTER().NUMBER)) {
            if ((e.keyCode || e.which) === 13)
                executeKeyCallback(e, callback);
            return false;
        } else
        if ((e.keyCode || e.which) === 13)
            executeKeyCallback(e, callback);
    }
}
function validSQLIDni(e) {
    if (clsma.$navigatores.Mozilla()) {
        if (e.which === 8) {
            return true;
        }
    }
    var caracter = String.fromCharCode(e.which);
    return !!caracter.match(new TESTER().DNI);
}

function validSQLIDecimal(e, callback) {
    if (clsma.$navigatores.Mozilla()) {
        if (e.which === 8) {
            return true;
        }
    }
    var caracter = String.fromCharCode(e.which);
    var value = $(e.target).val();
    var last = value.substring(value.length - 1, value.length);
    if (caracter === '.' || caracter === ',') {
        if (value.has(caracter) || ['.', ','].has(last))
            return false;
        if (caracter === '.' && value.indexOf(',') !== -1) {
            return false;
        } else if (caracter === ',' && value.has(caracter) && value.indexOf(',') !== -1) {
            return false;
        }
    } else {

        if (!caracter.match(new TESTER().DECIMAL)) {
            return false;
        } else {

            var oth = value.toArray('.')[1] || '';
            if (oth.length === 3) {
                return false;
            }
        }
        var retu;
        $(e.target).bind('blur', function () {
            var value = this.value || '';
            if (value.toArray('.').size() < 3 && !value.has(',')) {
                this.value = value.replace('.', ',');
                retu = false;
                return retu;
            }
        });
        // $(e.target).trigger('blurTHis');
        return retu;
    }


}

function validSQLIFloat(e, callback) {
    if (clsma.$navigatores.Mozilla()) {
        if (e.which === 8) {
            return true;
        }
    }
    var caracter = String.fromCharCode(e.which);
    var value = $(e.target).val();
    var last = value.substring(value.length - 1, value.length);
    if (caracter === '.' || caracter === ',') {
        if (value.has(caracter) || ['.', ','].has(last))
            return false;
        if (caracter === '.' && value.indexOf(',') !== -1) {
            return false;
        } else if (caracter === ',' && value.has(caracter) && value.indexOf(',') !== -1) {
            return false;
        }
    } else {

        if (!caracter.match(new TESTER().DECIMAL)) {
            return false;
        } else {

            var oth = value.toArray('.')[1] || '';
            if (oth.length === 3) {
                return false;
            }
        }
        var retu;
        $(e.target).bind('blur', function () {
            var value = this.value || '';
            if (value.toArray(',').size() < 3 && !value.has('.')) {
                this.value = value.replace(',', '.');
                retu = false;
                return retu;
            }
        });
        // $(e.target).trigger('blurTHis');
        return retu;
    }


}


function validSQLIMayus(e) {
    var caracter = String.fromCharCode(e.which);
    if (!caracter.match(/^[\sñÑáéíóúÁÉÍÓÚ]*$/)) {
        if (!caracter.match(/^[A-ZñÑ\s]*$/)) {
            if ((e.which >= 97) && (e.which <= 122)) {
                return true;
            } else {
                return false;
            }
        }
    }
}
function validSQLICharacters(e, chars) {
    if (clsma.$navigatores.Mozilla()) {
        if (e.which === 8) {
            return true;
        }
    }

    var caracter = String.fromCharCode(e.which);
    if (!isEmpty(chars)) {
        return !!(caracter.match(new TESTER().CHARACTERS)) && !!(caracter.match(new RegExp(chars, 'gi')));
    }
    return !!(caracter.match(new TESTER().CHARACTERS));
}

function executeKeyCallback(e, callback) {
    var key;
    if (window.event || !e.which) // IE
        key = e.keyCode; // para IE
    else if (e) // netscape	
        key = e.which;
    else
        return true;
    if (key === 13) //Enter
        if (typeof callback === 'function')
            callback.call();
        else
            eval(callback);
}

function toUpper(input) {
    var texto = input.value;
    input.value = texto.toUpperCase();
}

function toUpperCase(input) {
    var texto = $(input).val();
    $(input).val(texto.toUpperCase());
}



$.fn.extend({
    toUpperCase: function (event) {

        var events = ['blur', 'keypress', 'keydown', 'focus', 'keyup'];
        return this.each(function () {

            if (events.indexOf(event || 'blur') !== -1) {

                $(this).on(event || 'blur', function () {
                    $(this).val($(this).val().toUpperCase());
                });
            }

        });
    },
    enterPressed: function (callBack) {
        $(this).keypress(callBack, function (event) {
            if (event.keyCode === 13 || event.wich === 13) {
                if (Object.isFunction(event.data)) {
                    event.data.call(this);
                } else if (Object.isString(event.data) && Object.isFunction(window[event.data])) {
                    window[event.data].call(this);
                }
            }
        })

    }
});
function validarAllform(id, notfld) {
    var flag = true;
    var mensaje = "";
    var id = typeof id === 'string' ? ("#" + id) : id;
    var accounting;
    try {
        accounting = accounting.formatMoney || true;
    } catch (e) {
        accounting = false;
    }

    if (!notfld) {

        $('input[type!="hidden"],  select,  textarea', id)
                .not('input:button,.ui-search-input > input[type="text"],.ui-jqgrid input,.ui-jqgrid select')
                .not('input[data-tovalidate=false]')
                .each(function () {

                    $(this).css('border', '');
                    if ($(this).data('editor')) {
                        var valor = tinyMCE.get($(this).attr('id')).getContent();
                        var id = $(this).attr('id');
                        var tbl = $('#' + id + '_tbl');
                        var frm = $('#' + id + '_ifr').contents().find('#tinymce');
                        if (isEmpty(valor)) {
                            flag = false;
                            if ($(this).attr('title'))
                                mensaje += "," + $(this).attr('title');
                            $(tbl).css('border', 'solid 2px red');
                            $(frm).focus(function () {
                                $(tbl).css('border', '');
                            });
                        }
                    } else if (isEmpty($(this).val()) || (accounting && ($(this).val() === (accounting(0))))) {
                        flag = false;
                        if ($(this).attr('title'))
                            mensaje += "," + $(this).attr('title');
                        $(this).css('border', 'solid 2px red');
                        $(this).focus(function () {
                            $(this).css('border', '');
                        });
                        $(this).change(function () {
                            if ($(this).val() !== '')
                                $(this).css('border', '');
                        });
                    }
                });
    } else {

        $('  input[type!="hidden"],  select,  textarea', id)
                .not('input:button , .ui-search-input > input[type="text"],.ui-jqgrid input,.ui-jqgrid select ')
                .not('input[data-tovalidate=false]').each(function () {
            $(this).css('border', '');
        });
        $('  input[type!="hidden"],  select,  textarea', id)
                .not('input:button , .ui-search-input > input[type="text"],.ui-jqgrid input,.ui-jqgrid select , ' + notfld)
                .not('input[data-tovalidate=false]')
                .each(function () {

                    $(this).css('border', '');
                    if ($(this).data('editor')) {
                        var valor = tinyMCE.get($(this).attr('id')).getContent();
                        var id = $(this).attr('id');
                        var tbl = $('#' + id + '_tbl');
                        var frm = $('#' + id + '_ifr').contents().find('#tinymce');
                        if (isEmpty(valor)) {
                            flag = false;
                            if ($(this).attr('title'))
                                mensaje += "," + $(this).attr('title');
                            $(tbl).css('border', 'solid 2px red');
                            $(frm).focus(function () {
                                $(tbl).css('border', '');
                            });
                        }
                    } else if (isEmpty($(this).val()) || (accounting && ($(this).val() === (accounting(0))))) {
                        flag = false;
                        if ($(this).attr('title'))
                            mensaje += "," + $(this).attr('title');
                        $(this).css('border', 'solid 2px red');
                        $(this).focus(function () {
                            $(this).css('border', '');
                        });
                        $(this).change(function () {
                            if ($(this).val() !== '')
                                $(this).css('border', '');
                        });
                    }
                });
    }

    if (!flag) {
        show_message("debe diligenciar los campos en rojo [" + mensaje.substring(1) + "]");
    }
    return flag;
}

function escribirMensajeRequeridos() {
    document.write('<table><tr><td><img src="' + Rutav + '/vista/img/info.png" width="40" height="40" ></td><td>IMPORTANTE: Los campos subrayados y en color rojo indican que son obligatorios, favor diligenciar todos los campos requeridos.</td></tr></table>');
}

function selectAll(idDiv, input) {
    var flag = $(input).prop('checked');
    $("#" + idDiv).find('input[type=checkbox]').each(function () {
        $(this).prop('checked', flag);
    });
}

// function starsWith de java (en js no esta)
// ejemplo de uso : 'Hola Mundo'.startsWith('H');
if (typeof String.prototype.startsWith !== 'function') {
    String.prototype.startsWith = function (str) {
        return this.substring(0, str.length) === str;
    }
}
;
// ejemplo de uso : 'Hola Mundo'.endsWith('o');
if (typeof String.prototype.endsWith !== 'function') {
    String.prototype.endsWith = function (str) {
        return this.substring(this.length - str.length, this.length) === str;
    }
}
;
// ejemplo: getQGridRowData($(grid), row).NOMPRS devuelve el nomprs de la fila especificada
function getQGridRowData(grid, row) {
    var ret = $(grid).jqGrid('getRowData', row);
    return ret;
}

// ejemplo: geRowsSelectedQgrid($(grid)); devuelve todas las filas seleccionadas (multiselect:true)
//en una cadena asi : 1,2,3,4,5,6,7...
function geRowsSelectedQgrid(tabla) {
    var out = '';
    var rows = jQuery(tabla).jqGrid('getGridParam', 'selarrrow');
    for (var i = 0; i < rows.length; i++) {
        out += rows[i] + (i === rows.length - 1 ? "" : ",");
    }
    return out;
}

// ejemplo: getValsRowsSelectedQgrid($(grid),NROPRS); devuelve todas las filas seleccionadas (multiselect:true)
//en una cadena asi : 0000000001,0000000002,0000000003... 
function getValsRowsSelectedQgrid(tabla, field) {
    var out = '';
    var rows = jQuery(tabla).jqGrid('getGridParam', 'selarrrow');
    for (var i = 0; i < rows.length; i++) {
        out += $(tabla).jqGrid('getCell', rows[i], field) + (i === rows.length - 1 ? "" : ",");
    }
    return out;
}

function gridPrint() {

    var url = Rutac + '/Adm/ImpresionComprobante';
    $.ajax
            ({
                type: 'post',
                url: url,
                async: false,
                data: "events=GRIDPRINT",
                error: function (objeto, detalle, otroobj) {
                    show_message(detalle);
                },
                success: function (response) {
                    $('#comprobantesTucomandita').html(response);
                }
            });
}


function printthis(nrokmp) {

    var url_ = Rutav + '/utility/kmp/' + nrokmp + '.pdf';
    var x = (screen.Width - 200) / 2;
    var props = 'scrollbars=no,resizable=no,toolbar=no,menubar=no,status=yes,location=no,directories=no,width=900,height=800,top=200,left=' + x;
    window.open(url_, 'COMPROBANTE', props);
}


function setIcons()
{
    setIcon('Guardar', '-disk');
    setIcon('Salir', '-closethick');
    setIcon('Nuevo', '-document');
    setIcon('Buscar', '-circle-zoomin');
    setIcon('Eliminar', '-trash');
}

//<!-- ***************************************************************************** -->
//<!-- ******************  CAMBIAR EL CONTENIDO DE UN JQGRID			 *** -->
//<!-- ***************************************************************************** -->

function set_ContenJqgri(selector, url) {
    jQuery(selector).jqGrid('setGridParam', {url: url, datatype: "json"}).trigger("reloadGrid");
    return;
}

//<!-- ***************************************************************************** -->
//<!-- ******************  DIBUJAR EL SEARCH EN EL FORMULARIO	-Carlos		 *** -->
//<!--           <script>printSch('consulta tabla sch','campo formulario',
//     ******************false-true,'parametros a la bd',
//    ******************'function a ejecutar','titulo','taba?o'); </script>-->   ***->
//<!-- ***************************************************************************** -->
function printSch(nrosch, field, hasHtml, prmSch, callback, title, size) {
    var html = "";
    if (!size) {
        size = 30;
    }

    if (!title)
        title = 'Titulo';
    html += ' <input readonly="readonly" name="' + field + 'Shw" type="text" id="' + field + 'Shw"  title="' + title + '" class="text" size="' + size + '" value="" onfocus="javascript:return;"> ';
    html += '<input type = "button" id = "btn' + field + 'Show" name = "btn' + field + 'Show" value ="" class = "botonsmall" onmouseover = "changeCursor(this)"';
    html += ' onclick = "javascript:showWindowSearch(\'' + nrosch + '\', \'' + field + '\', ' + eval(hasHtml) + ', \'' + prmSch + '\', \'' + callback + '\');" style="cursor: pointer;" >';
    html += '<input name = "' + field + '" type = "hidden" id ="' + field + '" title="' + title + '"value = "" > ';
    return(html);
}


//<!-- ***************************************************************************** -->
//<!-- ******************  ELIMINAR LOS CARACTERES ESPECIALES EN LOS ELEMENTOS DE **-->
//<!-- ******************  UN FORMULARIO ESECIFICO AL PERDER FOCO - Carlos        **--> 
//<!-- ******************  how to use: en el onLoad() or document.ready colocar   **-->
//<!-- ******************  Remove_Caracteres('id del formulario');                 **-->
//<!-- ***************************************************************************** -->
function  Remove_Caracteres(formulario) {
    $('#' + formulario + ' input ,  textarea').each(function (a, b) {
        if ($(b).attr('data-valid')) {
            var attr = $(b).attr('data-valid');
            var def = $(b).attr('data-default');
            $(b).blur(function () {
                if (attr.toLowerCase() === 'web') {
                    ValidaURL(this);
                } else if (attr.toLowerCase() === 'mail') {
                    validarEmail(this);
                }
                if (attr.toLowerCase() !== 'mail')
                    $(this).val(blur_Remove(this, attr, def));
            });
        }
    });
}

//<!-- ***************************************************************************** -->
//<!-- ******************  VALIDA UN CAMPO   AL PERDER FOCO  (URL)                    -->
//<!-- ***************************************************************************** -->
function ValidaURL(selector) {
    var url = $(selector).val();
    var regex = /^((http|https|ftp):\/\/)?[-a-zA-Z0-9@:%_\+.~#?&//=]{2,256}(\.[a-z]{2,4})?(\/[-a-zA-Z0-9@:%_\+.~#?&//=]*)?/gi;
    if ($('#spanurl').length > 0)
        $('#spanurl').parent().remove();
    $(selector).parent().append('<td><div id="spanurl" style="display: none;float: right;\n\
                    position: relative;z-index:1000;right: -20px;vertical-align: bottom;alignment-adjust: baseline">\n\
                    <span style="color: red;">Direccion Web incorrecta</span></div></td>');
    if (!regex.test(url) && !isEmpty(url)) {
        $(selector).css({'border': '1px solid red'});
        if (!$('#spanurl').is(":visible")) {
            $('#spanurl').toggle(200);
            $(selector).focus();
            return false;
        }
    } else {
        $(selector).css({'border': '1px solid gray'});
        if ($('#spanurl').length > 0)
            $('#spanurl').parent().remove();
        return true;
    }
}

function validarEmail(selector) {

    var email = $(selector).val();
    $(selector).attr({'main-validated': false});
    expr = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    clsma.$createStyle('.redborder', {'border': '1px solid red !important'});
    if ($('#spanurl').length > 0)
        $('#spanurl').parent().remove();
    $(selector).parent().append('<td><div id="spanurl" style="display: none;float: right;\n\
                    position: relative;z-index:1000;right: -20px;vertical-align: bottom;alignment-adjust: baseline">\n\
                    <span style="color: red;">Cuenta de correo no válida</span></div></td>');
    if (!expr.test(email) && !isEmpty(email)) {
        $(selector).addClass('redborder');
        if (!$('#spanurl').is(":visible")) {
            $('#spanurl').toggle(200);
            $(selector).focus();
            return false;
        }
    } else {
        $(selector).removeClass('redborder');
        $(selector).attr({'main-validated': true});
        if ($('#spanurl').length > 0)
            $('#spanurl').parent().remove();
        return true;
    }
}

//<!-- ***************************************************************************** -->
//<!-- ******************  ELIMINAR LOS CARACTERES ESPECIALES AL PERDER FOCO - Carlos *** -->
//<!-- ***************************************************************************** -->
function blur_Remove(object, opcion, defaul) {

    if (!defaul)
        defaul = '';
    var len = $(object).val().length;
    var aux = '';
    for (var i = 0; i < len; i++) {
        var car = $(object).val().charAt(i);
        if (opcion === 'caracteres') {
            if (!compara(car, 'caracteres')) {
                aux += car;
            }
        } else if (opcion === 'numeros') {
            if (compara(car, 'numeros')) {
                aux += car;
            }
        } else if (opcion === 'numeronit') {
            if (compara(car, 'numeronit')) {
                aux += car;
            }
        } else if (opcion === 'letras') {
            if (!compara(car, 'letras')) {
                aux += car;
            }
        } else if (opcion === 'letranumero') {
            if (!compara(car, 'letranumero')) {
                aux += car;
            }
        } else if (opcion === 'mail') {
            if (!compara(car, 'mail')) {
                aux += car;
            }
        } else if (opcion === 'direccion') {
            if (!compara(car, 'direccion')) {
                aux += car;
            }
        } else if (opcion === 'web') {
            if (compara(car, 'web')) {
                aux += car;
            }
        } else if (opcion === 'password') {
            if (compara(car, 'password')) {
                aux += car;
            }
        }
    }
    return $.trim(aux) === '' ? defaul : $.trim(aux);
}

function compara(valor, let_num) {
    var not = '';
//    var carac = '\}{/*+!#$%&/:()=?_����|[]~\';\"<>';
    var carac = '´{}+\'+¿!"$%&~/=?¡*¨°¬<>¨\\/:|';
    if (let_num === 'caracteres') {
        not = "\'";
    } else if (let_num === 'numeros') {
        not = "0123456789,$.";
    } else if (let_num === 'numeronit') {
        not = "0123456789-.";
    } else if (let_num === 'letras') {
        not = carac + '1234567890#,()[]-@._';
    } else if (let_num === 'letranumero') {
        not = carac + "#@";
    } else if (let_num === 'mail') {
        not = carac + "#ñÑ()[],";
    } else if (let_num === 'direccion') {
        not = carac + "@.()[]ñÑ";
    } else if (let_num === 'web') {
        not = "@./:?-1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_=&,% ";
    } else if (let_num === 'password') {
        not = '-+1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_@#';
    }
    for (var j = 0; j < not.length; j++) {
        var car2 = not.charAt(j);
        if (valor === car2) {
            return true;
        }
    }
    return false;
}


//*****************************************************************************************//

function validarField(selector) {
    var flag = true;
    $(selector).each
            (function ()
            {
                if ($(this).val() === '' || $(this).val() === '-9' || $(this).val() === '-0')
                {
                    show_message("<center class=\"caption tablas \" style=\height: 100%;font-size: medium;\" >" + "El campo " + $(this).attr('title') + " es obligatorio" + "</center>", '', '');
                    flag = false;
                    return flag;
                }
            }
            );
    return flag;
}

function disableField(selector, valor) {

    $(selector).each
            (function ()
            {
                //$(this).attr('disabled','disabled');
                $(this).prop('disabled', valor);
                // $(this).get(0).disabled=valor;
            }
            );
    return true;
}



//<!-- ***************************************************************************** -->
//<!-- ****************** FUNCION QUE LLENA UN FORMULARIO ESPECIFICO             *** -->
//<!-- ****************** DESDE UN JSON o ARRAY - Carlos                         *** -->
//<!-- ***************************************************************************** -->

function fillForm(form, data, not) {
    if (isEmpty(data)) {
        return;
    }
    form = typeof form === 'string' ? $('#' + form) : form;
    var J_sondata = data;
    if (!not && not !== '') {
        $(form).find('input , textarea , select')
                .not('.ui-jqgrid input , .ui-jqgrid select , .ui-jqgrid textarea').each(function (a, b) {
            var attr = $(b).attr('id');
            var name = $(b).attr('name');
            var type = $(b).prop('type').toLowerCase();
            var isRadiocheck = (type === 'radio' || type === 'checkbox');
            if (isRadiocheck) {
                attr = name;
            } else {
                attr = ((attr === '' || !attr) ? name : attr);
            }

            if (isEmpty(attr)) {
                return;
            }

            var datos = J_sondata[attr.toUpperCase()] ? J_sondata[attr.toUpperCase()] : J_sondata[attr.toLowerCase()];
            datos = datos || '';

            if (Object.isString(datos)) {
                datos = datos.replace(/&#39;/g, '\'');
                datos = datos.replace(/&#34;/g, '"');
            }
            if ($(this).data('money')) {
                $(this).val(clsma.$money(datos));
            } else if ($(this).data('date')) {
                $(this).val(datos.substring(0, 10));
            } else if (isRadiocheck) {
                $('[name=' + name + '][value="' + datos + '"]').prop('checked', true);
            } else {
                $(this).val(datos);
            }

        });
    } else {

        var vct = not.split(',');
        var aux = '';
        for (i in vct) {
            if (vct[i] !== '') {
                aux += '#' + vct[i];
                if (i !== vct.lenght)
                    aux += ',';
            }
        }
        if (aux.endsWith(','))
            aux = aux.substring(0, aux.length - 1);
        $(form).find('input , textarea ,select').not(aux)
                .not('.ui-jqgrid input , .ui-jqgrid select , .ui-jqgrid textarea').each(function (a, b) {


            var attr = $(b).attr('id');
            var name = $(b).attr('name');
            var type = $(b).prop('type').toLowerCase();
            var isRadiocheck = (type === 'radio' || type === 'checkbox');
            if (isRadiocheck) {
                attr = name;
            } else {
                attr = ((attr === '' || !attr) ? name : attr);
            }

            if (isEmpty(attr)) {
                return;
            }

            var datos = J_sondata[attr.toUpperCase()] ? J_sondata[attr.toUpperCase()] : J_sondata[attr.toLowerCase()];
            datos = datos || '';
            if (Object.isString(datos)) {
                datos = datos.replace(/&#39;/, '\'');
                datos = datos.replace(/&#34;/, '"');
            }
            if ($(this).data('money')) {
                $(this).val(clsma.$money(datos));
            } else if ($(this).data('date')) {
                $(this).val(datos.substring(0, 10));
            } else if (isRadiocheck) {
                $('[name=' + name + '][value=' + datos + ']').prop('checked', true);
            } else {
                $(this).val(datos);
            }


        });
    }
    validateDecimals();
}

//<!-- ***************************************************************************** -->
//<!-- ****************** FUNCION QUE DEVUELVE UN OBJECT JSON                    *** -->
//<!-- ****************** DESDE UN FORMULARIO - Carlos                           *** -->
//<!-- ***************************************************************************** -->

function getFormData(form, not) {
    form = typeof form === 'string' ? $('#' + form) : $(form);
    var json = new Object();
    if (!not && not !== '') {
        $(form).find('input , textarea , select ,checkbox , radio').not('.ui-jqgrid input , .ui-jqgrid select , .ui-jqgrid textarea').each(function (a, b) {
            var attrJF = $(b).attr('id');
            var name = $(b).attr('name');
            var datosJF = '';
            var ckeck = $(b).prop('type').toLowerCase();
            if (ckeck === 'checkbox' || ckeck === 'radio') {
                datosJF = $('[name=' + name + ']').length > 1 ?
                        $('[name=' + name + ']:checked').val() :
                        $('[name=' + name + ']').is(':checked') ? 1 : 0;
//                datosJF = $('[name=' + name + ']:checked').val() || 0;
                json[name] = datosJF;
//                datosJF = 0;
//                if ($(b).is(':checked')) {
//                    datosJF = 1;
//                    json[name] = datosJF;
//                } else {
//                    json[name] = datosJF;
//                }
            } else {
                if (!isEmpty($(b).attr('data-money')) || !isEmpty($(b).attr('data-decimal'))) {
                    datosJF = clsma.$nomoney($(b).val());
                } else {
                    datosJF = ($(b).val() || '').escapeSpecialChars();
                }
            }
            json[attrJF] = datosJF;
        });
    } else {

        var vct = not.split(',');
        var aux = '';
        for (i in vct) {
            if (vct[i] !== '') {
                aux += '#' + vct[i];
                if (i !== vct.lenght)
                    aux += ',';
            }
        }
        if (aux.endsWith(','))
            aux = aux.substring(0, aux.length - 1);
        $(form).find('input , textarea , checkbox ,select , radio').not(aux)
                .not('.ui-jqgrid input , .ui-jqgrid select , .ui-jqgrid textarea').each(function (a, b) {
            var attrJF = $(b).attr('id');
            var name = $(b).attr('name');
            var datosJF = '';
            var ckeck = $(b).prop('type').toLowerCase();
            if (ckeck === 'checkbox' || ckeck === 'radio') {

                datosJF = $('[name=' + name + ']').length > 1 ?
                        $('[name=' + name + ']:checked').val() :
                        $('[name=' + name + ']').is(':checked') ? 1 : 0;
//                datosJF = $('[name=' + name + ']:checked').val() || 0;
                json[name] = datosJF;
//                if ($(b).is(':checked')) {
//                    datosJF = 1;
//                    json[name] = datosJF;
//                } else {
//                    json[name] = datosJF;
//                }
            } else {
                if (!isEmpty($(b).attr('data-money')) || !isEmpty($(b).attr('data-decimal'))) {
                    datosJF = clsma.$nomoney($(b).val());
                } else {
                    datosJF = ($(b).val() || '').escapeSpecialChars();
                }
            }
            json[attrJF] = datosJF;
        });
    }
    return json;
}

String.prototype.escapeSpecialChars = function () {
    return this.replace(/\\n/g, "\\\\n")
            .replace(/\\'/g, "\\\\'")
            .replace(/\\"/g, '\\\\"')
            .replace(/\\&/g, "\\\\&")
            .replace(/\\r/g, "\\\\r")
            .replace(/\\t/g, "\\\\t")
            .replace(/\\b/g, "\\\\b")
            .replace(/\\f/g, "\\\\f");
};
function disabe_tabs(tab, array) {

    if (!tab & !array) {
        $(function () {
            $('#tabs').tabs({disabled: []});
        });
    } else {

        var tabs;
        var object = {};
        tabs = '#tabs';
        if (jQuery.type(tab) === 'numeric') {
            tabs = '#tabs';
            object.disabled = [tab];
        }

        if (jQuery.type(tab) === 'string') {
            tabs = tab;
            if (!array) {
                object.disabled = [];
            } else {
                object.disabled = array;
            }
        }

        if ($.isArray(tab)) {
            object.disabled = tab;
        }
        $(tabs).tabs(object);
    }



}

function enable_tab(tab, arr) {

    if (!tab & !arr) {
        $(function () {
            $('#tabs').tabs('enable', 0);
        });
    } else {

        var tabs = '#tabs';
        var object = {};
        if (jQuery.type(tab) === 'number') {
            tabs = '#tabs';
            object.enable = tab;
        }

        if (jQuery.type(tab) === 'string') {
            tabs = tab;
            if (!arr) {
                object.enable = 0;
            } else {
                object.enable = arr;
            }
        }
        $(tabs).tabs('enable', object.enable);
    }
}


function hide_tabs(tab, arr) {
    var arrTab = [];
    var tabs;
    if (!tab & !arr) {

        $(function () {
            tabs = '#tabs';
            $(tabs).children('div').each(function () {
                var id = $(this).attr('id');
                arrTab.push(id);
            });
        });
    } else {



        if (jQuery.type(tab) === 'array') {
            tabs = '#tabs';
            arr = tab;
        }

        if (jQuery.type(tab) === 'string') {
            tabs = tab;
        }

        $(tabs).children('div').each(function () {
            var id = $(this).attr('id');
            arrTab.push(id);
        });
    }

    for (i in arr) {
        i = parseInt(i);
        var sel = $(tabs).find('a[id=href' + arrTab[arr[i]] + ']')
                || $(tabs).find('a[href=#' + arrTab[arr[i]] + ']');
        sel.parent().hide();
        $(tabs).children('div[id=' + arrTab[arr[i]] + ']').hide();
    }


}


function show_tabs(tab, arr) {
    var arrTab = [];
    var tabs;
    if (!tab & !arr) {

        $(function () {
            tabs = '#tabs';
            $(tabs).children('div').each(function () {
                var id = $(this).attr('id');
                arrTab.push(id);
            });
        });
    } else {



        if (jQuery.type(tab) === 'array') {
            tabs = '#tabs';
            arr = tab;
        }

        if (jQuery.type(tab) === 'string') {
            tabs = tab;
        }

        $(tabs).children('div').each(function () {
            var id = $(this).attr('id');
            arrTab.push(id);
        });
    }

    for (i in arr) {
        i = parseInt(i);
        var sel = $(tabs).find('a[id=href' + arrTab[arr[i]] + ']')
                || $(tabs).find('a[href=#' + arrTab[arr[i]] + ']');
        sel.parent().show();
//        $(tabs).children('div[id=' + arrTab[arr[i]] + ']').show();
    }


}
function active_tab(tab, arr) {

    if (!tab & !arr) {
        $(function () {
            $('#tabs').tabs({active: []});
        });
    } else {

        var tabs;
        var object = {};
        if (jQuery.type(tab) === 'number') {
            tabs = '#tabs';
            object.active = [tab];
        }

        if (jQuery.type(tab) === 'string') {
            tabs = tab;
            if (isEmpty(arr)) {
                object.active = [];
            } else {
                object.active = [arr];
            }
        }
        $(tabs).tabs(object);
    }

}

// funcion para setear el tama?o del iframe dependiendo de su contenido
function ResizeIframe(id) {
//    var id_ = id.contentWindow.document || id.contentDocument;
    var hei = $($(id).contents().context.contentDocument).height();
    var wid = $($(id).contents().context.contentDocument).width();
    $(id).height(hei);
}

function genericAjax(url, data_, succes, before, complet, isMultipart) {
    var data;
    var configAjax;
    var error_ = function (error) {
        if (data.loading)
            data._loading.hide();
        var error = ''
        try {
            if (errorCustom)
                error = errorCustom;
        } catch (e) {
            error = 'Ha ocurrido un error en la aplicaci?n , Detalle : ' + error;
        }

        show_message(error, '', 'ERROR');
    };
    var before_ = function () {
        if (data.loading)
            data._loading.show();
        if (data.before && data.before !== null)
            return data.before();
    };
    var sucess_ = function (data_) {

        if (data.done && data.done !== null)
            if (data.returning === 'object')
                data.done($.parseJSON(data_));
            else
                data.done(data_);
        if (data.loading)
            data._loading.hide();
    };
    var complete_ = function () {
        if (data.complete && data.complete !== null)
            data.complete();
//        if (data.loading)
//            hideLoader();

    };
    if ($.type(url) === 'string')
        data = {
            type: 'POST',
            url: url,
            async: true,
            loading: true,
            before: before,
            data: data_,
            done: succes,
            complete: complet,
            isFile: isMultipart ? true : false
        };
    else if ($.isPlainObject(url)) {

        data = {
            type: url.type || 'POST',
            url: url.url,
            async: url.loading ? true : url.async ? true : false,
            loading: url.loading ? true : false,
            dataType: (url.dataType) ? url.dataType :
                    (url.returning === 'html') ? 'html' : null,
            returning: url.returning || null,
            before: url.before || null,
            data: url.data,
            done: url.done,
            complete: url.complete || null,
            isFile: url.isFile ? true : false
        };
    }

    data._loading = clsma.$loading(url.loadingText);
    var isMuli = {
//        mimeType: "multipart/form-data",
        enctype: 'multipart/form-data',
        contentType: false,
        cache: false,
        processData: false
    };
    configAjax = {
        type: data.type,
        url: data.url,
        async: data.async,
        error: error_,
        beforeSend: before_,
        data: data.data,
        success: sucess_,
        complete: complete_,
        cache: false,
    };
    if (data.isFile)
        configAjax = $.extend({}, configAjax, isMuli, {cache: new Date().getTime()});
    try {

        return $.ajax(configAjax).promise().done(function (data) {
            try {

                data = $.parseJSON(data);
                if (data.sessionEnd === true) {
                    show_message(data.msg, function () {
                        window.top.location.href = Rutav + '/vista/sistemas/login/login.jsp';
                    }, 'ERROR');
                    $.error(data.msg);
                }

            } catch (e) {

            }
        });
    } catch (e) {
        show_message(e, '', 'ERROR');
    }

}
function catchTheSession() {
    return $.ajax({
        url: Rutac + '/Adm/InitPage',
        data: {
            state: 'KEYSESSION'
        }
    }).then(function (data) {

        if (data !== 'TRUE') {
            show_message('La sesion ha finalizado , inicie de nuevo', function () {
                window.top.location.href = Rutav + '/vista/sistemas/login/login.jsp';
            }, 'ERROR');
        }
    }).fail(function () {
        show_message('La sesion ha finalizado , inicie de nuevo', function () {
            window.top.location.href = Rutav + '/vista/sistemas/login/login.jsp';
        }, 'ERROR');
    });
}


function setIconos()
{
    setIcon('Guardar', '-disk');
    setIcon('Generar', '-disk');
    setIcon('Solicitar', '-disk');
    setIcon('Calcular', '-calculator');
    setIcon('Actualizar', '-disk');
    setIcon('Activar', '-check');
    setIcon('Programar', '-check');
    setIcon('Salir', '-closethick');
    setIcon('Cerrar', '-power');
    setIcon('Nuevo', '-document');
    setIcon('Nueva', '-document');
    setIcon('Agregar', '-plusthick');
    setIcon('Asignar', '-plusthick');
    setIcon('Refrescar', '-refresh');
    setIcon('Buscar', '-circle-zoomin');
    setIcon('Eliminar', '-trash');
    setIcon('Anular', '-cancel');
    setIcon('Exportar', '-circle-arrow-s');
    setIcon('Descargar', '-circle-arrow-s');
    setIcon('Quitar', '-trash');
    setIcon('Seleccionar', '-check');
    setIcon('Siguiente', '-seek-next');
    setIcon('Anterior', '-seek-prev');
    setIcon('Enviar', '-mail-closed');
    setIcon('Imprimir', '-print');
    setIcon('Editar', '-pencil');
    setIcon('Configurar', '-gear');
    setIcon('Detalle', '-info');
    setIcon('Atras', '-arrowreturnthick-1-w');
    setIcon('Regresar', '-seek-prev');
    setIcon('Subir Archivo', '-circle-arrow-n');
    setIcon('Recargar', '-refresh');
    setIcon('Aprobar', '-check');
    setIcon('Denegar', '-cancel');
    setIcon('Autorizar', '-check');
    setIcon('Rechazar', '-closethick');
}

function setToMoney(select, moneda) {
    $(select).each(function () {
        select = this;
        $(select).val(setValByMoney($(select).val(), moneda));
        ['size', 'valor', 'keyup_handler', 'keypress_handler'].each(function () {
            $(select).removeData(this.slice(0));
        });
        var handlerpress = function (event) {
            if ($(this).attr('readonly')) {
                return false;
            }


            var bol = typeof validSQLINumber(event) === 'undefined' ? true : false;
            if (bol) {
                var size = this.maxLength === -1 ? 10 : this.maxLength;
                var oldSize = $(this).data('size');
                var data = accounting.unformat($(this).val());
                var len = (data + '').length;
                if (len >= oldSize) {
                    return false;
                }
                $(this).data('valor', '');
                $(this).data('size', size);
                var a = String.fromCharCode((event.keyCode || event.which));
                $(this).data('valor', parseFloat(data + a));
            } else
                return false;
        };
        $(select).bind('keypress', handlerpress);
        $(select).data('keypress_handler', handlerpress);
        var handlerup = function (event) {

            if ($(this).attr('readonly'))
                return false;
            if (!((event.keyCode >= 48 && event.keyCode <= 57) ||
                    (event.which >= 48 && event.which <= 57))) {
                event.keyCode = event.keyCode - 48;
                event.which = event.which - 48;
            }
            var bol = typeof validSQLINumber(event) === 'undefined' ? true : false;
            if (bol) {
                $(this).val(setValByMoney($(this).data('valor'), moneda));
            }
        };
        $(select).bind('keyup', handlerup);
        $(select).data('keyup_handler', handlerup);
        $(select).attr('data-money', true);
    });
}

function writeInfoMsg(msg) {
    var html = '';
    html += '<div class="ui-state-error" style="margin-top:20px;border-radius:5px;padding:0 3px"><table><tr><td><img src="' +
            Rutav + '/vista/img/info.png" width="35" height="35" ></td><td style="font-size:11px;font-weight:bolder;color:black;padding-left:20px"> IMPORTANTE: ' + msg + '</td></tr></table></div>';
    return html;
}

function clearForm(selector, isform) {

    if (isform === true) {
        var form = $(selector);
        form.get(0).reset();
        $(selector).find('input[type=hidden]').each(function () {
            $(this).val('');
        });
    } else {
        $(selector).each(function () {
            $(this).val('');
        });
    }

}

function disableButton(texto, type) {
    if (!type)
        $(":button:contains('" + texto + "')").button("disable");
    else
        $(texto).button("disable");
    //$('#'+form).find(":button:contains('"+id+"')").button( "disable" );
    return $(texto);
}

function enableButton(texto, type) {
    if (!type)
        $(":button:contains('" + texto + "')").button("enable");
    else
        $(texto).button("enable");
    return $(texto);
}

function hideButton(texto, type) {
    if (!type) {
        $('button , input[type=button] , a , div').each(function () {
            if ((
                    (this.textContent || this.innerText || this.value) === texto
                    ) &&
                    $(this).hasClass('ui-button')) {
                $(this).hide();
            }
        });
    } else
        $(texto).hide();
    return $(texto);
}

function showButton(texto, type) {
    if (!type) {
        $('button , input[type=button] , a , div').each(function () {
            if ((
                    (this.textContent || this.innerText || this.value) === texto
                    ) &&
                    $(this).hasClass('ui-button')) {
                $(this).show();
            }
        });
    } else
        $(texto).show();
    return $(texto);
}


// Configurar elementos <a> , <button> , <input type="button"> a botones jquery ,
// colocar iconos y ejecutar llamados callback - Carlos Pinto 12/05/2015
function configButton(idButton, icon, execute) {
    var sele = $(idButton);
    if (typeof idButton === 'string') {
        sele = $('#' + idButton);
    }

    $(sele).unbind('click');
    sele.button({
        icons:
                {
                    primary: 'ui-icon ui-icon' + icon,
                    secondary: 'ui-icon ui-icon-grip-dotted-vertical',
                    text: true
                }
    }).click(function (event) {
        event.preventDefault();
        if (execute)
            if (typeof execute === 'string') {
                eval(execute);
            } else {
                execute.call(this, event);
            }
    });
    return sele;
}

function setMinDate(pickTO, pickFROM, onclose) {
    $("#" + pickTO).datepicker("option", "minDate", $('#' + pickFROM).val());
}

function setMaxDate(pickTO, pickFROM) {
    $("#" + pickTO).datepicker("option", "maxDate", $('#' + pickFROM).val());
}


function clearChildren(element, not) {
    element = $.type(element) === 'string' ?
            $(element) : element;
    not = (!not) ? '' : not;
    element.find('input , select , textarea').not('input:button').not(not).each(function () {

        if ($(this).get(0).type.toLowerCase() === 'radio'
                || $(this).get(0).type.toLowerCase() === 'checkbox') {

            $(this).attr('checked', false);
        } else if ($(this).prop("tagName").toLowerCase() === 'select') {
            $(this).prop('selectedIndex', 0);
        } else {

            $(this).val('');
        }

    });
}


/*Export_Grid_TO_CSV Carlos Pinto 11/05/2015
 * Parametros:
 * 1) Selector de la tabla
 * 2) Array de columnas a mostrar ej: ['CODPRS','TPOPRS',''....]
 * 3) Array de titulos a mostrar el : ['Codigo','Tipo',''...]
 * 4) Titulo del archivo 
 * 5) true: mostrara las cabeceras , false : no las mostrara
 * */


function Export_Grid_TO_CSV(Grid, colsToShow, colsTitles, ReportTitle, showLabels) {
    var cm = colsToShow; // Columnas
    var cols = colsToShow; // datos
    var select = typeof Grid === 'string' ? ('#' + Grid) : Grid;
    var has_splice = false;
//    var records = $('#' + Grid).getGridParam('records'); // Total de records 
    var all = jQuery(select).getGridParam('data'); // Datos de la Grilla

    if (colsToShow.length <= 0) {
        cm = jQuery(select).getGridParam("colModel");
        if (['cb', 'rn'].has(cm[0].name)) {
            cm = cm.splice(1, cm.length);
            has_splice = true;
        }
        for (index in cm) {
            var cL = cm[index];
            cols.push(cL['index']);
        }
    }

// Creamos el array de columnas a mostrar
    var titles = colsTitles;
    if (colsTitles.length <= 0) {
        titles = jQuery(select).getGridParam('colNames');
        if (has_splice) {
            titles = titles.splice(1, titles.length);
        }
    }


    var CSV = '';
    //Nombre de archivo en primera linea

    CSV += ReportTitle + '\r\n\n';
    //Aqui generamos las columnas cabeceras
    if (showLabels) {
        var row = "";
        //Extraigo los titulos
        for (var index in titles) {
            row += titles[index] + ',';
        }

        row = row.slice(0, -1);
        CSV += row + '\r\n';
    }

//Extraigo cada dato a exportar
    for (var i = 0; i < all.length; i++) {
        var row = "";
        for (j in cols) {
            row += '"' + all[i][cols[parseInt(j)]] + '",';
        }

        row.slice(0, row.length - 1);
        CSV += row + '\r\n';
    }

    if (CSV === '') {
        show_message("Datos Inv?lidos", '', 'ERROR');
        return;
    }

//Nombre de archivo
    var fileName = "";
    fileName += ReportTitle.replace(/ /g, "_");
    //Se inicializa a csv
    var uri = 'data:text/csv;charset=UTF-8,' + escape(CSV);
    var link = document.createElement("a");
    link.href = uri;
    link.style = "visibility:hidden";
    link.download = fileName + ".csv";
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
}

/**
 * @method configura loa atributos para un elemento del formulario
 * @param {type}  formulario o contenedor
 * @param {type} configuraci?n 
 * @returns {undefined}
 */
function config_form_fields(formulario, datas) {

    if (datas.length === 0)
        return;
    var makeProccess = function (formulario, lista) {

        var validAttr = function (atributo) {
            var attr_ = ['readonly', 'disabled'];
            for (i in attr_) {
                if (attr_[i] === atributo)
                    return true;
            }

            return false;
        };
        $.each(lista, function () {
            var $this = this;
            var campo = $this.FLDOPT;
            var maxleng = $this.MAXLGN;
            maxleng = parseInt(maxleng);
            var tpofld = $this.TPOFLD;
            var valid = $this.VLDFLD;
            var stdfld = $this.STDFLD;
            var vlrstd = $this.VLRSTD;
            vlrstd = parseInt(vlrstd);
            var fld = $(formulario).find('#' + campo);
            if (fld.length > 0) {

                if (parseInt(maxleng) > 0)
                    fld.attr('maxlength', maxleng);
                if (!isEmpty(valid))
                    fld.attr('data-valid', valid);
                if (tpofld !== 'Search') {
                    if (!isEmpty(stdfld)) {
                        if (validAttr(stdfld))
                            fld.attr(stdfld, (vlrstd === 1 ? true : false));
                    }
                } else {
                    if (validAttr(stdfld)) {
                        fld.search('option', stdfld, (vlrstd === 1 ? true : false));
                    }
                }
            }
        });
    };
    genericAjax({
        url: Rutac + '/Configurate/Form?event=GETCONF',
        data: datas,
        done: function (data) {
            data = $.parseJSON(data);
            if (data.exito === 'OK') {
                if (!isEmpty(data.lista))
                    makeProccess(formulario, data.lista);
                else
                    return;
            } else {
                show_message(data.msg, '', 'ERRoR');
            }
        }
    });
}

function genTabs(selector, context) {

    if (!context) {

        selector = typeof selector === 'string' ? $('#' + selector) : $(selector);
        var ul = $('<ul/>');
        if (selector.children('ul').length > 0)
            return;
        selector.children('div').each(function () {
            var tab = $(this),
                    id = tab.attr('id'),
                    title = tab.attr('title'),
                    icon = tab.attr('icon'),
                    li = $('<li/>'),
                    a = $('<a/>'),
                    span = $('<span/>');
            if (tab.attr('not-tab') === '') {
                return;
            }

            a.attr({href: '#' + id, id: 'href' + id}).
                    addClass('ui-tabs-anchor').text(title);
            if (icon !== '' && icon !== undefined) {
                span.addClass('ui-icon ');
                span.addClass('ui-icon ' + icon);
                span.appendTo(li);
            }


            a.prependTo(li);
            li.appendTo(ul);
        });
        selector.prepend(ul);
    } else {

        context = typeof context === 'string' ? $('#' + context) : $(context);
        selector = typeof selector === 'string' ? $('#' + selector) : $(selector);
        var ul = $('<ul/>');
        if (selector.children('ul').length > 0)
            return;
        context.children('div').each(function () {

            if ($(this).attr('data-parent')) {
                return false;
            }

            var tab = $(this),
                    id = tab.attr('id'),
                    title = tab.attr('title'),
                    icon = tab.attr('icon'),
                    li = $('<li/>'),
                    a = $('<a/>'),
                    span = $('<span/>');
            if (tab.attr('not-tab') === '') {
                return false;
            }

            a.attr({href: '#' + id, id: 'href' + id}).text(title);
            if (icon !== '' && icon !== undefined) {
                span.addClass('ui-icon');
                span.addClass('ui-icon' + icon);
                span.appendTo(a);
            }


            a.appendTo(li);
            li.attr('data-tab-id', id).appendTo(ul);
        });
        selector.prepend(ul);
        context.children('div').each(function () {
            var tab = $(this),
                    id = tab.attr('id'),
                    title = tab.attr('title'),
                    icon = tab.attr('icon'),
                    li = $('<li/>'),
                    a = $('<a/>'),
                    span = $('<span/>');
            if (tab.attr('not-tab') === '') {
                return false;
            }

            if (tab.attr('data-parent')) {

                var att = $(tab).attr('data-parent');
                var a_ = $('a[id=href' + att + ']');
                var a_p = a_.parent('li');
                var a_p_ul = a_p.children('ul');
                var newUl = $('<ul/>');
                var newLi = $('<li/>').attr('data-tab-id', id);
                var newA = $('<a/>');
                var newId = tab.attr('id');
                var newTitle = tab.attr('title');
                if (!isEmpty(a_)) {


                    if (isEmpty(a_p_ul)) {
                        a_p.append(newUl);
                    }

                    a_p_ul = a_p.children('ul');
                    newA.attr({href: '#' + newId, id: 'href' + newId}).text(newTitle);
                    newA.appendTo(newLi);
                    newLi.appendTo(a_p_ul);
                }
            }

        });
    }
}

function voucherDetail(nrokmp, div) {

    return genericAjax({
        url: Rutac + '/Adm/VoucherDetail',
        data: {
            event: 'DETAIL',
            nrokmp: nrokmp

        }
    }).then(function (data) {

        data = $.parseJSON(data);
        if (data.exito === 'OK') {
            $(div).html(data.html);
            makeTotal(nrokmp);
        } else {
            show_message(data.msg, '', 'ERROR');
        }

    });

}

function creditDetail(nrocxc, div) {

    if (typeof nrocxc !== 'array') {
        nrocxc = [nrocxc];
    }

    return genericAjax({
        url: Rutac + '/Adm/VoucherDetail',
        data: {
            event: 'DTLCRD',
            nrocxc: $.toJSON(nrocxc)

        }
    }).then(function (data) {

        data = $.parseJSON(data);
        if (data.exito === 'OK') {
            $(div).html(data.html);
        } else {
            show_message(data.msg, '', 'ERROR');
        }

    });
}

function makeTotal(nrokmp) {

    var datos = $('#jqKMP' + nrokmp).getGridParam('data');
    var arr = ['VLRDUD'];
    var totls = [];
    var aux = 0;
    var new_data_ = {};
    for (j in arr) {

        j = parseInt(j);
        aux = 0;
        for (i in datos) {
            i = parseInt(i);
            if (accounting) {

                aux += parseFloat(accounting.unformat(datos[i][arr[j]]));
            } else {
                aux += parseFloat(datos[i][arr[j]]);
            }
            totls[j] = aux;
        }
        new_data_[arr[j]] = accounting ? accounting.formatMoney(totls[j]) : totls[j];
    }
    new_data_['DSPDUD'] = '<b>Total :</b>';
    $('#jqKMP' + nrokmp).jqGrid('addRowData', (datos.length) + 1, new_data_);
}


function isEmpty(select) {
    if (clsma.$undefined(select)) {
        return true;
    }

    if ($.type(select) === 'array' || select instanceof jQuery) {
        return select.length === 0;
    }

    if (typeof select === 'object') {
        var name;
        for (name in select) {
            return false;
        }
        return true;
    }

    if (typeof select === 'string') {
        return $.trim(select) === '' || select === undefined || select === null;
    }



}

(function () {


    var defaults,
            functions = {
                _softMessage: function () {

                    var elem = $('<div/>')
                            .attr({id: 'softMessage'})
                            ,
                            img,
                            p;
                    if ($('[id=softMessage]').length > 0) {
                        $('[id=softMessage]').stop().remove();
                    }

                    if (window.document) {

                        img = $('<img/>');
                        p = $('<div/>');
                        var conf = functions.getConfig.call(this);
                        var cssElement = {
                            'z-index': 100000,
                            position: 'relative',
                            top: '20px',
                            width: 'auto',
                            align: 'middle',
                            'max-width': '60%',
                            '-webkit-box-shadow': '0px 0px 9px 0px rgba(0,0,0,0.75)',
                            '-moz-box-shadow': '0px 0px 9px 0px rgba(0,0,0,0.75)',
                            'box-shadow': ' 0px 0px 9px 0px rgba(0,0,0,0.75)',
                            display: 'none',
                            'border-radius': '10px',
                            'background': 'rgba(255,255,255,1)',
                            'background': '-moz-radial-gradient(center, ellipse cover, rgba(255,255,255,1) 0%, rgba(246,246,246,1) 68%, rgba(237,237,237,1) 100%)',
                                    'background': '-webkit-gradient(radial, center center, 0px, center center, 100%, color-stop(0%, rgba(255,255,255,1)), color-stop(68%, rgba(246,246,246,1)), color-stop(100%, rgba(237,237,237,1)))',
                                    'background': '-webkit-radial-gradient(center, ellipse cover, rgba(255,255,255,1) 0%, rgba(246,246,246,1) 68%, rgba(237,237,237,1) 100%)',
                                    'background': '-o-radial-gradient(center, ellipse cover, rgba(255,255,255,1) 0%, rgba(246,246,246,1) 68%, rgba(237,237,237,1) 100%)',
                                    'background': '-ms-radial-gradient(center, ellipse cover, rgba(255,255,255,1) 0%, rgba(246,246,246,1) 68%, rgba(237,237,237,1) 100%)',
                                    'background': 'radial-gradient(ellipse at center, rgba(255,255,255,1) 0%, rgba(246,246,246,1) 68%, rgba(237,237,237,1) 100%)',
                                    'filter': ' progid:DXImageTransform.Microsoft.gradient( startColorstr=\'#ffffff\', endColorstr=\'#ededed\', GradientType=1 )'
                        };
                        cssElement['float'] = conf.float;
                        cssElement[conf.float] = conf.floatVal + 'px';
                        elem.css(cssElement);
                        img.attr({
                            src: Rutav + '/vista/img/' + conf.icon
                        }).css({
                            width: '25px',
                            heigth: '25px',
                            'float': 'left',
                            'margin-left': '10px',
                            display: 'inline-block',
                            'vertical-align': 'middle',
                            position: 'relative',
                            'padding': '5px',
                            'max-width': '10%'

                        });
                        img.appendTo(elem);
                        p.css({
                            padding: '15px 15px',
//                            margin: '5px',
                            display: 'block',
                            'vertical-align': 'middle',
                            'float': 'right',
                            position: 'relative',
                            'font-size': '12px',
                            'word-wrap': 'break-word'

                        }).html(conf.text);
                        p.appendTo(elem);
                        $(document).find('body').append(elem);
                        $(elem).fadeIn('slow').delay(conf.delay).fadeOut('slow', function () {
                            $(elem).remove();
                            if (typeof defaults.done === 'function') {
                                defaults.done.call(this);
                            } else {
                                $.error('[ done ] attribute must be a function ');
                            }
                        });
                    } // end id focument
                },
                getConfig: function () {

                    var object = {};
                    object['icon'] = defaults.icons[defaults.tipeClasses.indexOf(defaults.class)];
                    object['class'] = defaults.class;
                    object['float'] = defaults.float;
//                    object['left'] = defaults.left;
//                    object['right'] = defaults.right;
                    object['text'] = defaults.message;
                    object['delay'] = defaults.delay;
                    var float = object['float'] === 'left' ?
                            defaults.left :
                            defaults.right;
                    object['floatVal'] = float;
                    return object;
                }

            };
    $.softMessage = function (options) {

        defaults = $.extend({}, $.softMessage.defaults, options);
        functions._softMessage.call(this);
    };
    $.softMessage.defaults = {
        class: 'error',
        tipeClasses: ['error', 'info', 'exito'],
        icons: ['error.png', 'info.png', 'ok.png'],
        float: 'right',
        left: 0,
        right: 50,
        delay: 2500,
        done: function () {

        }
    };
})(jQuery);
String.prototype.replaceAll = function (repl, replWth) {
    var exp = new RegExp('' + repl, 'g');
    return this.replace(exp, replWth);
};
/**
 * funcion que recarga la pagina actual = F5
 */
function reload_page() {
    window.location.reload();
}

function argumentsArray() {
    var args = arguments[0];
    var aux = [];
    for (i in args) {
        if (clsma.$type(args[i]) !== 'Arguments')
            aux.push(args[i]);
        else
            for (j in args[i]) {
                aux.push(args[i][j]);
            }

    }
    return aux;
}



function setValByMoney(valor, moneda) {
    moneda = moneda ? moneda : 'peso';
    if (moneda.toLowerCase() === 'peso') {
        return accounting.formatMoney(valor, {symbol: "$", format: "%s%v"});
    } else if (moneda.toLowerCase() === 'euro') {
        return accounting.formatMoney(valor, "€", 2, ".", ",");
    } else if (moneda.toLowerCase() === 'dollar') {
        return accounting.formatMoney(valor, {symbol: "U$", format: "%v %s"});
    }
}

function validSQLIPorcentaje(event) {

    if (validSQLIDecimal(event) !== undefined)
        return false;
    var caracter = String.fromCharCode(event.which);
    var value = $(event.target).val();
    var valor = parseFloat(value + caracter);
    if (valor > 100)
        return false;
}

function printReport(datas, location, call) {
    var ifr = $('<iframe id="ifrmPrint"/>');

    var locate = Object.isFunction(location) ? '' : location;

    var win = clsma.$new_window('wind', {
        location: locate,
        width: '99%',
        height: '90%',
        buttons: ['Salir'],
        close: function () {
            $(this).remove();
        }
    });
//
    win.Salir(function () {
        $(this.$this).remove();
    });


    genericAjax({
        url: Rutac + '/Adm/ReportPrint',
        data: {
            event: 'PRINT',
            data: $.toJSON(datas)
        },
        loading: true
    }).done(function (data) {
        data = $.parseJSON(data);
        if (data.exito === 'OK') {

            setTimeout(function () {

                ifr.attr({src: Rutav + '/utility/reportes/Jasper_Reports_UDS/pdf/' + data.nomrpt + '.pdf'});
                if (location === 'new') {
                    var rut = Rutav + '/utility/reportes/Jasper_Reports_UDS/pdf/' + data.nomrpt + '.pdf';
                    window.open(rut, "Impresión de comprobantes.",
                            "location=no,width=700,height=750,scrollbars=yes,top=100,resizable = no");

                } else if (location === 'top') {
                    ifr.css({width: $(window.top).width() - 50, height: $(window.top).height()});
                    ifr.appendTo(win.$this);
                    win.$this.dialog('open');
                } else {
                    ifr.css({width: $(window).width() - 50, height: $(window).height()});
                    ifr.appendTo(win.$this);
                    win.$this.dialog('open');
                }

            }, 300);

        } else {
            show_message(data.msg, '', 'ERROR');
        }
    }).done(function (data) {
        if (Object.isFunction(location)) {
            location.call(window, data);
        } else if (Object.isFunction(call)) {
            call.call(window, data);
        }
    });

}
function ReportPrintService(data) {

    if (isEmpty(data)) {
        return;
    }
    if (isEmpty(data.codcia)) {
        data.codcia = clsma.dataPrs.codcia;
    }

    var url = clsma.dataPrs.reportProvider + 'download/pdf/' + JSON.stringify(data);
    if (window.document) {
        var a = window.document.createElement("a");
        a.download = true;
        a.href = url;
        a.click();

    }
}
