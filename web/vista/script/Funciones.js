var arrayCofigBton = new Array();
var ArrMessageTips = new Array();
var imgOut = new Image;
var imgOver = new Image;
var imgDown = new Image;
var Img1 = new Image;
var Img2 = new Image;
var idxColumn = 0;
var Rutav = '';
var Rutac = '';
var clsNme = 'tdInactive';
var swOnMouseMove = false;
var trgCurrent = '';
var newWindowFind = null;
var swTime = false;
var cntmin = 0, cntsec = 0;
var newWindowReport = null;
var newWindow = null;
var arrTab, arr_Id, arrUrl;
var error = 'Ha ocurrido un error en la aplicacion consulte con el administrador del sistema ';
var vecclr = new Array("aqua", "fuchsia", "olive", "green", "yellow", "blue", "red", "lime", "orange", "purple");
function makeLayerShowTime() {
    var str = '';
    try {
        str += '<div id="layTime" style="position:relative; left:0px; top:0px; width:230px; height:23px; z-index:1; background-color: #ffffff; layer-background-color: #ffffff; border:1px solid #000000; visibility: visible; overflow: auto;">';
        str += '</div>';
        document.write(str);
    } catch (theException) {
        alert('Error [makeLayerShowTime] ' + theException);
    }
}

function confClearSearch(id_search)
{
    document.write("<a href='#' onclick=\"return clearSearch('" + id_search + "')\" class='hypgrl' >Borrar</a>");
}

function clearSearch(id_search)
{
    set(id_search, "");
    set(id_search + "Shw", "");
    return false;
}

function sendView(url, id_contenedor)
{
    var pagina_requerida = false
    if (window.XMLHttpRequest)
    {// Si es Mozilla, Safari etc
        pagina_requerida = new XMLHttpRequest()
    } else if (window.ActiveXObject) { // pero si es IE
        try {
            pagina_requerida = new ActiveXObject("Msxml2.XMLHTTP")
        } catch (e) { // en caso que sea una versi�n antigua
            try {
                pagina_requerida = new ActiveXObject("Microsoft.XMLHTTP")
            } catch (e) {
            }
        }
    } else
        return false
    pagina_requerida.onreadystatechange = function () { // funci�n de respuesta
        cargarpagina(pagina_requerida, id_contenedor)
    }
    pagina_requerida.open('GET', url, true) // asignamos los m�todos open y send
    pagina_requerida.send(null)
}
// todo es correcto y ha llegado el momento de poner la informaci�n requerida
// en su sitio en la pagina xhtml
function cargarpagina(pagina_requerida, id_contenedor)
{
    if (pagina_requerida.readyState == 4 && (pagina_requerida.status == 200 || window.location.href.indexOf("http") == -1))
    {
        document.getElementById(id_contenedor).innerHTML = pagina_requerida.responseText
    }
}

function getScrollTop() {
    if (window.pageYOffset) {
        return window.pageYOffset
    } else {
        return Math.max(document.body.scrollTop, document.documentElement.scrollTop);
    }
}

function hide_tabs(div_active, parent_)
{
    var arrDiv = get(parent_).getElementsByTagName("DIV");
    for (var i = 0; i < arrDiv.length; i++)
    {
        if (arrDiv[i].id != div_active)
        {
            arrDiv[i].innerHTML = "";
            arrDiv[i].style.display = "none";
        }
    }
    get(div_active).style.display = "block";
}

var nextFieldAux = null;
var keyNextField = null;
function avanzar(event, campo)
{
    if (window.event)
        keyNextField = window.event.keyCode;
    else if (event)
        keyNextField = event.which;
    if (keyNextField == "38" && $(campo.tagName + "[posicion='" + (parseInt(campo.posicion) - 1) + "']")) // Flecha  Arriba
        nextFieldAux = $(campo.tagName + "[posicion='" + (parseInt(campo.posicion) - 1) + "']");
    else if (keyNextField == "40" && $(campo.tagName + "[posicion='" + (parseInt(campo.posicion) + 1) + "']")) // Flecha  Abajo
        nextFieldAux = $(campo.tagName + "[posicion='" + (parseInt(campo.posicion) + 1) + "']");
    else
        return;
    if (nextFieldAux.length > 0)
    {
        nextFieldAux[0].focus();
    }

}


function eventEnterKey(event, metodo)
{
    var key = "";
    if (window.event)
        key = window.event.keyCode;
    else if (event)
        key = event.which;
    if (key == 13)
    {
        eval(metodo);
    }
}

function changeColor(objeto)
{
    if (objeto.bgColor == '#ffffc6')
        objeto.bgColor = color;
    else
    {
        color = objeto.bgColor;
        objeto.bgColor = '#ffffc6';
    }
}

function setLastPageGrid(countPage)
{
    if (!get("GridTable"))
        return;
    nextPageGrid = 1;
    previuosPageGrid = 0;
    lastPageGrid = !countPage ? getValue("gridCountPage") : countPage;
}



/*
 * Returns a new XMLHttpRequest object, or false if this browser
 * doesn't support it
 */
function newXMLHttpRequest() {
    var xmlreq = false;
    if (window.XMLHttpRequest) {
        // Create XMLHttpRequest object in non-Microsoft browsers
        xmlreq = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        // Create XMLHttpRequest via MS ActiveX
        try {
            // Try to create XMLHttpRequest in later versions
            // of Internet Explorer
            xmlreq = new ActiveXObject("Msxml2.XMLHTTP");
        } catch (e1) {
            // Failed to create required ActiveXObject
            try {
                // Try version supported by older versions
                // of Internet Explorer
                xmlreq = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (e2) {
                // Unable to create an XMLHttpRequest with ActiveX
            }
        }
    }
    return xmlreq;
}


// ============================================================================== -->
// ============	 Metodo llenado de Combos Dependientes	     ============== -->
// ============================================================================== -->
function llenarCboDependiente(arrayData, codigo, objSelect) {
    var i = 0;
    var j = 0;
    objSelect.length = 0;
    for (i = 0; i < arrayData.length; i++) {
        if (codigo == arrayData[i][0]) {
            objSelect.options[j] = new Option(arrayData[i][2], arrayData[i][1]);
            j++;
        }// End If
    }// End For
}
// ============================================================================== -->
// ============		          Elimina Espacios	        	     ============== -->
// ============================================================================== -->
function TrimRight(str) {
    var resultStr = '';
    var i = 0;
    // Return immediately if an invalid value was passed in
    if (str + '' == 'undefined' || str == null)
        return null;
    // Make sure the argument is a string
    str += '';
    if (str.length == 0)
        resultStr = '';
    else {
        // Loop through string starting at the end as long as there
        // are spaces.
        i = str.length - 1;
        while ((i >= 0) && (str.charAt(i) == " "))
            i--;
        // When the loop is done, we're sitting at the last non-space char,
        // so return that char plus all previous chars of the string.
        resultStr = str.substring(0, i + 1);
    }

    return resultStr;
}

function TrimLeft(str) {
    var resultStr = '';
    var i = len = 0;
    // Return immediately if an invalid value was passed in
    if (str + '' == 'undefined' || str == null)
        return null;
    // Make sure the argument is a string
    str += '';
    if (str.length == 0)
        resultStr = '';
    else {
        // Loop through string starting at the beginning as long as there
        // are spaces. 
        len = str.length;
        while ((i <= len) && (str.charAt(i) == ' '))
            i++;
        // When the loop is done, we're sitting at the first non-space char,
        // so return that char plus the remaining chars of the string.
        resultStr = str.substring(i, len);
    }

    return resultStr;
}

function Trim(str) {
    var resultStr = '';
    resultStr = TrimLeft(str);
    resultStr = TrimRight(resultStr);
    return resultStr;
}

// ***************************************************************************** -->
// ******  Funciones de Validacion de campos requridos en un formularios ******* -->
// ***************************************************************************** -->
function validRequiredFields(form, fields) {
    var i = 0, j = 0, sw = false, valor = '';
    var ArrayCamposReq = fields.split('|');
    var idInput = '';
    var isDate = false;
    for (i = 0; i < form.elements.length; i++) {
        if (form.elements[i].type === 'select-one' ||
                form.elements[i].type === 'text' ||
                form.elements[i].type === 'password' ||
                form.elements[i].type === 'textarea' ||
                form.elements[i].type === 'file' ||
                form.elements[i].type === 'radio') {

            sw = false;
            for (j = 0; j < ArrayCamposReq.length; j++) {
                if (form.elements[i].name === ArrayCamposReq[j] || form.elements[i].name === (ArrayCamposReq[j] + 'Shw')) {
                    sw = true;
                    break;
                }
            }// end for

            if (!sw && form.elements[i].type === 'radio')
            {
                var eleChk = document.getElementsByName(form.elements[i].name);
                for (k = 0; k < eleChk.length; k++)
                {
                    if (eleChk[k].checked)
                    {
                        sw = true;
                        break;
                    }
                }

                if (!sw)
                {
                    show_message('Por favor complete información.' + '\n' + 'Ingrese ' + form.elements[i].title);
                    try
                    {
                        form.elements[i].focus();
                    } catch (theException) {
                    }
                    return false;
                }
            }

            valor = Trim(form.elements[i].value);
            idInput = form.elements[i].id;
            isDate = $('#' + idInput).attr('isDate');
            if ((!sw) && ((valor === '') || (valor.length === 0))) {
                show_message('Por favor complete información.' + '\n' + 'Ingrese ' + form.elements[i].title);
                try {
                    if (!isDate) {
                        form.elements[i].focus();
                    }
                } catch (theException) {
                }
                return false;
            }// end if

            // Valido E mail - Fechas - Horas
            if ((!sw) && (form.elements[i].type === 'text')) {
                sw = true;
                if (form.elements[i].alt === 'E.mail')
                    sw = valEmail(form.elements[i].value);
                if (form.elements[i].alt === 'Date')
                    sw = valFecha(form.elements[i].value);
                if (form.elements[i].alt === 'Hour')
                    sw = valHour(form.elements[i].value);
                if (!sw) {
                    try {
                        form.elements[i].focus();
                    } catch (theException) {
                    }
                    return sw;
                }
            }
        }// end if
    }// end for
    return true;
}

function requiredFieldsLabel(form, fields, idLabel) {
    var i = 0, j = 0, sw = false, valor = '';
    var ArrayCamposReq = fields.split('|');
    for (i = 0; i < form.elements.length; i++) {
        if (form.elements[i].type == 'select-one' ||
                form.elements[i].type == 'text' ||
                form.elements[i].type == 'password' ||
                form.elements[i].type == 'textarea' ||
                form.elements[i].type == 'file' ||
                form.elements[i].type == 'radio') {

            sw = false;
            for (j = 0; j < ArrayCamposReq.length; j++)
            {
                if (form.elements[i].name == ArrayCamposReq[j] || form.elements[i].name == (ArrayCamposReq[j] + 'Shw'))
                {
                    sw = true;
                    break;
                }
            }// end for

            if (!sw && form.elements[i].type == 'radio')
            {
                var eleChk = document.getElementsByName(form.elements[i].name);
                for (k = 0; k < eleChk.length; k++)
                {
                    if (eleChk[k].checked)
                    {
                        sw = true;
                        break;
                    }
                }

                if (!sw)
                {

                    showLabelError('tdError', 'Por favor complete información.' + '\n' + 'Ingrese ' + form.elements[i].title);
                    try
                    {
                        //form.elements[i].focus();
                    } catch (theException) {
                    }
                    return false;
                }
            }

            valor = Trim(form.elements[i].value);
            if ((!sw) && ((valor == '') || (valor.length == 0)))
            {

                showLabelError('tdError', 'Por favor complete información.' + '\n' + 'Ingrese ' + form.elements[i].title);
                try {
                    //form.elements[i].focus();
                } catch (theException) {
                }
                return false;
            }// end if

            // Valido E mail - Fechas - Horas
            if ((!sw) && (form.elements[i].type == 'text')) {
                sw = true;
                if (form.elements[i].alt == 'E.mail')
                    sw = valEmail(form.elements[i].value);
                if (form.elements[i].alt == 'Date')
                    sw = valFecha(form.elements[i].value);
                if (form.elements[i].alt == 'Hour')
                    sw = valHour(form.elements[i].value);
                if (!sw) {
                    try {
                        //form.elements[i].focus();
                    } catch (theException) {
                    }
                    return sw;
                }
            }
        }// end if
    }// end for
    return true;
}


function requiredFields(form, fields)
{
    var requireds = fields.split('|');
    var inputs = form.getElementsByTagName('INPUT');
    var flag = false;
    var sw = true;
    var cont = 0;
    var element;
    for (var i = 0; i < inputs.length; i++)
    {
        cont = 0;
        flag = false;
        for (var j = 0; j < requireds.length; j++)
        {
            element = document.getElementsByName(requireds[j])[0];
            if (element.name == inputs[i].name)
            {
                flag = true;
                break;
            }
        }

        if (flag && (inputs[i].type == 'text' || inputs[i].type == 'hidden'))
        {
            if (Trim(inputs[i].value) == '')
            {
                show_message('Por favor complete información.' + '\n' + 'Ingrese ' + inputs[i].title);
                sw = false;
                break;
            }
        } else if (flag && (inputs[i].type == 'radio' || inputs[i].type == 'checkbox'))
        {
            var radios_checks = document.getElementsByName(inputs[i].name);
            for (var k = 0; k < radios_checks.length; k++)
            {
                if (radios_checks[k].checked == false)
                    cont++;
            }
            if (cont == radios_checks.length)
            {
                show_message('Por favor complete información.' + '\n' + 'Seleccione ' + radios_checks[0].title);
                sw = false;
                break;
            } else
            {
                sw = true;
                break;
            }
        }
    }
    var textareas = form.getElementsByTagName('textarea');
    for (var i = 0; i < textareas.length; i++)
    {
        cont = 0;
        flag = false;
        for (var j = 0; j < requireds.length; j++)
        {
            element = document.getElementsByName(requireds[j])[0];
            if (element.name == textareas[i].name)
            {
                flag = true;
                break;
            }
        }
        if (flag)
        {
            if (Trim(textareas[i].value) == '')
            {
                show_message('Por favor complete información.' + '\n' + 'Ingrese ' + textareas[i].title);
                sw = false;
                break;
            }
        }
    }
    return sw;
}


// ***************************************************************************** -->
// ********  Funcion que retorna el ancho en pixeles de la ventana actual ****** -->
// ***************************************************************************** -->
function getWidthWin() {
    var xMax = 0;
    if (document.all)
        xMax = document.body.clientWidth;
    else if (document.layers)
        xMax = window.innerWidth;
    return xMax;
}
// ***************************************************************************** -->
// ********  Funcion que retorna el alto en pixeles de la ventana actual ****** -->
// ***************************************************************************** -->
function getHeightWin() {
    var yMax = 0;
    if (document.all)
        yMax = document.body.clientHeight;
    else if (document.layers)
        yMax = window.innerHeight;
    return yMax;
}
// ***************************************************************************** -->
// ***************  Funciones utilizada para la carga previa de imagenes ******* -->
// ***************************************************************************** -->
function loadImages() {
    Img1.src = Rutav + '/vista/img/Img1.gif';
    Img2.src = Rutav + '/vista/img/Img2.gif';
    imgOut.src = Rutav + '/vista/img/botones/btonOut.gif';
    imgOver.src = Rutav + '/vista/img/botones/btonOver.gif';
    imgDown.src = Rutav + '/vista/img/botones/btonDown.gif';
}
// ***************************************************************************************** -->
// **  Funciones utilizada para el efecto cambio de imagenes el las columnas de una tabla ** -->
// ***************************************************************************************** -->
function ChangeImage(state, nameImg)
{
    if (state == 'On')
        document.getElementById(nameImg).background = Img2.src;
    else if (state == 'Off')
        document.getElementById(nameImg).background = Img1.src;
}
// ============================================================================== -->
// ============	    Modifica la url de un Form en una pagina     ============== -->
// ============================================================================== -->
function sendForm(nameForm, url, validFields) {
    var sw = true;
    var newUrl = Trim(url);
    if (validFields) {
        sw = validRequiredFields(document.forms[nameForm], validFields);
    }
    if (sw)
    {
        if (newUrl != '') {
            if (newUrl.substring(0, 1) == 'C')
                newUrl = Rutac + newUrl.substring(1);
            else
                newUrl = Rutav + newUrl.substring(1);
            document.forms[nameForm].action = newUrl;
        }
        document.forms[nameForm].submit();
    }
}
// ============================================================================== -->
// ============	      Actualizar un valor del objeto Select      ============== -->
// ============================================================================== -->
function updateDataSelect(name, valor)
{
    if (document.getElementById(name))
        document.getElementById(name).value = valor;
}
// ============================================================================== -->
// ============	       Funcion para redireccionar paginas        ============== -->
// ============================================================================== -->
function reDirect(urlnxt, target)
{
    var newUrl = Trim(urlnxt);
    if (newUrl.substring(0, 1) == 'C')
        newUrl = Rutac + newUrl.substring(1);
    else if (newUrl.substring(0, 1) == 'V')
        newUrl = Rutav + newUrl.substring(1);
    if (target) {
        if (target = 'top')
            top.location.href = newUrl;
        else if (target = '_parent')
            parent.location.href = newUrl;
        else if (target = '_self')
            self.location.href = newUrl;
    } else {
        location.href = newUrl;
    }
}
// ============================================================================== -->
// ============	       Funcion para efecto de las imagenes       ============== -->
// ============================================================================== -->
function makeVisible(name, which) {
    strength = (which == 0) ? 1 : 0.5;
    var cur = document.getElementById(name);
    /*if (cur.style.MozOpacity)
     cur.style.MozOpacity = strength;
     else if (cur.filters)
     cur.filters.alpha.opacity = strength * 100;*/
}
// ***************************************************************************** -->
// ********* Cambia el formato de una fecha dd-mm-yyyy to mm-dd-yyyy      ****** -->
// ***************************************************************************** -->
function getFechaStringDMA(fecha) {
    var anno = fecha.substring(0, 4);
    var mes_ = fecha.substring(6, 8);
    var dia_ = fecha.substring(9);
    return (dia_ + '/' + mes_ + '/' + anno);
}
// ***************************************************************************** -->
// *************     Dibuja el boton para cerrar las ventanas      ************* -->
// ***************************************************************************** -->
function makeBarInformation(wth, ptitle) {
    var output = '', title = '';
    var wTable = getWidthWin();
    var wResto = wTable - (wth - 24);
    if (!ptitle)
        title = '&nbsp;';
    else
        title = ptitle;
    try {
        //output += '<br>';
        output += '<table width="' + wTable + '" border="0" cellpadding="0"cellspacing="0">';
        output += '  <tr>';
        output += '    <td width="2"><img src="' + Rutav + '/vista/img/br.gif" width="2" height="26" ></td>';
        output += '    <td width="10" background="' + Rutav + '/vista/img/brclr.gif">&nbsp;</td>';
        output += '    <td width="' + wth + '" align="center" background="' + Rutav + '/vista/img/brclr.gif" class="title">' + title + '</td>';
        output += '    <td width="10" background="' + Rutav + '/vista/img/brclr.gif">&nbsp;</td>';
        output += '    <td width="2"><img src="' + Rutav + '/vista/img/br.gif" width="2" height="26" ></td>';
        output += '    <td width="' + wResto + '" background="' + Rutav + '/vista/img/brclr.gif">&nbsp;</td>';
        output += '  </tr>';
        output += '</table>';
        document.write(output);
    } catch (theException) {
        alert('Error [makeBarInformation] ' + theException);
    }
}
// ***************************************************************************** -->
// ************************** Path para las vista y controladores*************** -->
// ***************************************************************************** -->
function setPath(url) {
    Rutav = url;
    Rutac = Rutav + '/servlet';
}
// ***************************************************************************** -->
// ************************** Funcion para eliminar registros******************* -->
// ***************************************************************************** -->
function deleteRecord(url, nameForm) {
    show_confirmation('Seguro(a) que desea eliminar este registro?',
            'executeDelete("' + url + '","' + nameForm + '");');
}

function executeDelete(url, nameForm) {
    if (nameForm !== "undefined") {
        var form = document.forms[nameForm];
        if (form.state) {
            form.state.value = 'Delete';
            form.submit();
        }
    } else {
        reDirect(url);
    }
}
// ***************************************************************************** -->
// ****************** Dibuja la barra de titulos de las ventanas *************** -->
// ***************************************************************************** -->
function makeTitleBar(wh, title) {
    var str = '';
    var widthCenter = wh - 180;
    try {
        str += '<table width="' + wh + '" align="center" border="0" cellspacing="0" cellpadding="0">';
        str += '  <tr>';
        str += '    <td width="90" height="27" background="' + Rutav + '/vista/img/esqizq.gif">&nbsp;</td>';
        str += '    <td width="' + widthCenter + '" height="27" background="' + Rutav + '/vista/img/censup.gif" align="center">';
        str += '    <table width="' + widthCenter + '" border="0" cellspacing="0" cellpadding="3"><tr>';
        str += '      <td width="' + widthCenter + '" height="27" background="' + Rutav + '/vista/img/censup.gif" align="center" valign="bottom" style="color:white;font-weight: bold">' + title + '</td>';
        str += '    </tr></table>';
        str += '    </td>';
        str += '    <td width="90" height="27" background="' + Rutav + '/vista/img/esqder.gif">&nbsp;</td>';
        str += '  </tr>';
        str += '</table>';
        document.write(str);
    } catch (theException) {
        alert('Error [makeTitleBar] ' + theException);
    }
}
// ***************************************************************************** -->
// ****************** Dibuja la barra de inferior de las ventanas *************** -->
// ***************************************************************************** -->
function makeBarBottom(wh) {
    var str = '';
    var widthCenter = wh - 89;
    try {
        str += '<table width="' + wh + '" align="center" border="0" cellspacing="0" cellpadding="0">';
        str += '  <tr>';
        str += '    <td><img src="' + Rutav + '/vista/img/esqizqinf.gif" width="44" height="13"></td>';
        str += '    <td><img src="' + Rutav + '/vista/img/ceninf.gif" width="' + widthCenter + '" height="13"></td>';
        str += '    <td><img src="' + Rutav + '/vista/img/esqderinf.gif" width="45" height="13"></td>';
        str += '  </tr>';
        str += '</table>';
        document.write(str);
    } catch (theException) {
        alert('Error [makeBarBottom] ' + theException);
    }
}
// ***************************************************************************** -->
// ********* Dibuja la barra de titulos de las ventanas (Simple) *************** -->
// ***************************************************************************** -->
function makeTitleBarII(wh, title) {
    var str = '';
    var widthCenter = 0;
    if (wh != 'NaN')
        widthCenter = wh - 180;
    else
        widthCenter = 0;
    title = title == '' ? '&nbsp;' : title;
    try {
        str += '<table width="' + wh + '" align="center" border="0" cellspacing="0" cellpadding="0">';
        str += '  <tr class="rowtitle">';
        /*str += '    <td width="10" height="23" background="'+Rutav+'/vista/img/barcenter.jpg">&nbsp;</td>';
         str += '    <td width="71" height="23" background="'+Rutav+'/vista/img/barcenter.jpg">&nbsp;</td>';
         str += '    <td width="10" height="23" background="'+Rutav+'/vista/img/barcenter.jpg">&nbsp;</td>';
         str += '    <td width="'+widthCenter+'" height="23" background="'+Rutav+'/vista/img/barcenter.jpg" align="center" valign="middle" class="titlebar">'+title+'</td>';
         str += '    <td width="9" height="23" background="'+Rutav+'/vista/img/barcenter.jpg">&nbsp;</td>'; 
         str += '    <td width="51" height="23" background="'+Rutav+'/vista/img/barcenter.jpg">&nbsp;</td>';
         str += '    <td width="30" height="23" background="'+Rutav+'/vista/img/barlineder.gif">&nbsp;</td>';*/
        str += '    <td height="23" align="center">' + title + '</td>';
        str += '    <td width="30" height="23">&nbsp;</td>';
        str += '  </tr>';
        str += '</table>';
        document.write(str);
    } catch (theException) {
        alert('Error [makeTitleBarII] ' + theException);
    }
}
// ***************************************************************************** -->
// ****************** Dibuja la barra de inferior de las ventanas *************** -->
// ***************************************************************************** -->
function makeBarBottomII(wh) {
    var str = '';
    var widthCenter = wh - 22;
    try {
        str += '<table width="' + wh + '" align="center" border="0" cellspacing="0" cellpadding="0">';
        str += '  <tr class="rowtitle">';
        /*
         str+='    <td width="11" height="22"><img src="'+Rutav+'/vista/img/barcenter.jpg" width="11" height="22"></td>';
         str+='    <td width="'+widthCenter+'" height="22" background="'+Rutav+'/vista/img/barcenter.jpg" align="center" valign="middle">&nbsp;</td>';
         str+='    <td width="23" height="22"><img src="'+Rutav+'/vista/img/barcenter.jpg" width="23" height="22"></td>';
         */
        str += '  <td height="23" align="center">&nbsp;</td>';
        str += '  </tr>';
        str += '</table>';
        document.write(str);
    } catch (theException) {
        alert('Error [makeBarBottom] ' + theException);
    }
}

function makeBarBottomIII(wh, foot) {
    var str = '';
    var widthCenter = wh - 22;
    try {
        str += '<table width="' + wh + '" align="center" border="0" cellspacing="0" cellpadding="0">';
        str += '  <tr>';
        str += '    <td width="11" height="22"><img src="' + Rutav + '/vista/img/headerth.png" width="11" height="22"></td>';
        str += '    <td width="' + widthCenter + '" height="22" background="' + Rutav + '/vista/img/headerth.png" align="center" valign="middle" class="titlebar"><a  class="titlebar" href="javascript:self.history.back()">' + foot + '</a></td>';
        str += '    <td width="11" height="22"><img src="' + Rutav + '/vista/img/headerth.png" width="11" height="22"></td>';
        str += '  </tr>';
        str += '</table>';
        document.write(str);
    } catch (theException) {
        alert('Error [makeBarBottom] ' + theException);
    }
}

// ***************************************************************************** -->
// ****************** Anula el boton derecho del Mouse             ************* -->
// ***************************************************************************** -->
function nullMouse() {
    if (document.layers) {
        document.captureEvents(Event.MOUSEDOWN);
        document.onmousedown = clickNS;
    } else {
        document.onmouseup = clickNS;
        document.oncontextmenu = clickIE;
    }
    document.oncontextmenu = new Function("return false");
}

function clickIE() {
    var message = '';
    if (document.all) {
        (message);
        return false;
    }
}

function clickNS(e) {
    var message = '';
    if (document.layers || (document.getElementById && !document.all)) {
        if (e.which == 2 || e.which == 3) {
            (message);
            return false;
        }
    }
}
// ***************************************************************************** -->
// ******************          Anula el teclado                    ************* -->
// ***************************************************************************** -->
function nullKeyBoard() {
    return false;
}
// ***************************************************************************** -->
// **********              Dibuja los Botones de Comandos             ********** -->
// ***************************************************************************** -->
function drawCommandBotton() {
    var str = '';
    var i = 0;
    var w = 0;
    try {

        if (arrayCofigBton.length > 0) {
            loadImages();
            w = 93 * arrayCofigBton.length;
            str += '<table align="center" width="' + w + '" border="0" cellpadding="0" cellspacing="2">';
            str += '  <tr>';
            for (i = 0; i < arrayCofigBton.length; i++) {
                //str += '<td width="93" height="20" background = "'+ imgOut.src +'" onMouseOver="javascript:this.background=\'' + imgOver.src + '\'" onMouseOut="javascript:this.background=\'' + imgOut.src + '\'" onMouseDown="javascript:this.background=\'' + imgDown.src + '\'" onMouseUp="javascript:this.background=\'' + imgOver.src + '\'" class="drag" align="center"><a class="hypbtn" href="javascript:' + arrayCofigBton[i][1] + '" onMouseOver="window.status=\'' + arrayCofigBton[i][0] + '\';return true">' + arrayCofigBton[i][0] + '</a></td>\n';
                str += '<td width="93" height="20" align="center"><input name="' + arrayCofigBton[i][0] + '" type="button" class="boton" value="' + arrayCofigBton[i][0] + '" title="' + arrayCofigBton[i][0] + '" onClick="javascript:' + arrayCofigBton[i][1] + '" onMouseOver="window.status=\'' + arrayCofigBton[i][0] + '\';return true"></td>\n';
            }
            str += '  </tr>';
            str += '</table>';
        } else {
            str += '&nbsp;';
        }
        document.write(str);
    } catch (theException) {
        alert('Error [drawCommandBotton] ' + theException);
    }
}
// ***************************************************************************** -->
// **********  Funcion q crea el efecto en la barra de herramientas   ********** -->
// ***************************************************************************** -->
function efect(id, op) {
    if (op == 'On') {
        document.getElementById(id).borderColorDark = '#FFFFFF';
        document.getElementById(id).borderColorLight = '#000000';
    }
    if (op == 'Off')
        document.getElementById(id).borderColorDark = '#000000';
    if (op == 'Down') {
        document.getElementById(id).borderColorDark = '#000000';
        document.getElementById(id).borderColorLight = '#FFFFFF';
    }
}
// ***************************************************************************** -->
// **********              Dibuja los Botones de Comandos             ********** -->
// ***************************************************************************** -->
function drawCommandBottonII(arrCmd, caption) {
    var str = '';
    var i = 0;
    var w = 0;
    var nameImg = '';
    var strCaption = '';
    var idxLocal = idxColumn++;
    try {

        if (arrCmd.length > 0) {
            if (caption)
                w = 100 * arrCmd.length;
            else
                w = 35 * arrCmd.length;
            str += '<table width="' + w + '" cellpadding="0" cellspacing="0" class="tablas">';
            str += '  <tr>\n<td>';
            str += '<table width="' + w + '" bordercolor="#000000" border="1" cellpadding="1" cellspacing="1" class="tablas">';
            str += '  <tr>';
            for (i = 0; i < arrCmd.length; i++) {
                if (arrCmd[i][0] == 'Nuevo')
                    nameImg = 'newII.gif';
                else if (arrCmd[i][0] == 'Guardar')
                    nameImg = 'save.gif';
                else if (arrCmd[i][0] == 'Eliminar')
                    nameImg = 'deleteIII.gif';
                else if (arrCmd[i][0] == 'Buscar')
                    nameImg = 'search.gif';
                else if (arrCmd[i][0] == 'Imprimir')
                    nameImg = 'print.gif';
                else if (arrCmd[i][0] == 'Salir')
                    nameImg = 'close.gif';
                else if (arrCmd[i][0] == 'Regresar')
                    nameImg = 'back.gif';
                else if (arrCmd[i][0] == 'Aceptar')
                    nameImg = 'okII.gif';
                if (caption)
                    strCaption = '&nbsp;' + arrCmd[i][0];
                str += '<td id="Column' + idxLocal + '' + i + '" width="100" bordercolor="#000000" align="center" onMouseOver="efect(this.id,\'On\')" onMouseOut="efect(this.id,\'Off\')" onMouseDown="efect(this.id,\'Down\')" onMouseUp="efect(this.id,\'On\')"><a class="hypbtn" href="javascript:' + arrCmd[i][1] + '" onMouseOver="window.status=\'' + arrCmd[i][0] + '\';return true"><img src="' + Rutav + '/vista/img/' + nameImg + '" border="0">' + strCaption + '</a></td>\n';
            }
            str += '  </tr>';
            str += '</table>';
            str += '</td>\n</tr>\n</table>';
        } else {
            str += '&nbsp;';
        }
        document.write(str);
    } catch (theException) {
        alert('Error [drawCommandBottonII] ' + theException);
    }
}
// ***************************************************************************** -->
// ******        Funcion para limpiar los campos de un formularios       ******* -->
// ***************************************************************************** -->
function clearFields(args, op) {
    var i = 0, j = 0, varAccess = '', sw = false;
    var arrAgrs = args.split(',');
    var form = document.forms[arrAgrs[0]];
    if (op) {
        if (form.state)
            form.state.value = 'New';
        form.submit();
        return;
    }

    if (form.varAccess)
        varAccess = form.varAccess.value;
    if (varAccess != '') {
        if (form.btnSearch) {
            if (varAccess.charAt(0) == '1')
                form.btnSearch.disabled = 0;
            else
                form.btnSearch.disabled = 1;
        }

        if (form.btnSave) {
            if (varAccess.charAt(1) == '1')
                form.btnSave.disabled = 0;
            else
                form.btnSave.disabled = 1;
            if (varAccess.charAt(2) == '1')
                if (form.btnSearch)
                    form.btnSearch.disabled = 0;
        }

        if (form.btnDelete)
            form.btnDelete.disabled = 1;
    }

    for (i = 0; i < form.elements.length; i++) {
        if (form.elements[i].type == 'text' ||
                form.elements[i].type == 'password' ||
                form.elements[i].type == 'textarea' ||
                form.elements[i].type == 'file' ||
                form.elements[i].type == 'hidden') {
            sw = false;
            for (j = 1; j < arrAgrs.length; j++) {
                if (form.elements[i].name == arrAgrs[j]) {
                    sw = true;
                    break;
                }
            }
            if (!sw)
                form.elements[i].value = '';
        }
    }// end for
    if (form.state)
        form.state.value = 'Insert';
}
// ***************************************************************************** -->
// ******           Funcion actualizar la seleccion de un checkbox       ******* -->
// ***************************************************************************** -->
function updateCheckBox(name, op) {
    document.getElementById(name).checked = op;
}
// ***************************************************************************** -->
// ******           Funcion actualizar el valor de un checkbox           ******* -->
// ***************************************************************************** -->
function updateValueCheckBox(obj) {
    if (obj.type == 'checkbox') {
        if (obj.checked) {
            obj.value = '1';
            //obj.style.backgroundColor = "#FFFF80";
        } else {
            obj.value = '0';
            //obj.style.backgroundColor = "";
        }
    }
}
// ============================================================================== -->
// ============		            Validar Fecha		     	     ============== -->
// ============================================================================== -->
function esDigito(sChr) {
    var sCod = sChr.charCodeAt(0);
    return ((sCod > 47) && (sCod < 58));
}

function valSep(strValor) {
    var bOk = false;
    bOk = bOk || ((strValor.charAt(2) == "-") && (strValor.charAt(5) == "-"));
    bOk = bOk || ((strValor.charAt(2) == "/") && (strValor.charAt(5) == "/"));
    return bOk;
}

function finMes(strValor) {
    var nMes = parseInt(strValor.substr(3, 2), 10);
    var nAno = parseInt(strValor.substr(6));
    var nRes = 0;
    switch (nMes) {
        case 1:
            nRes = 31;
            break;
        case 2:
        {
            if ((nAno % 4) == 0)
                nRes = 29;
            else
                nRes = 28;
            break;
        }
        case 3:
            nRes = 31;
            break;
        case 4:
            nRes = 30;
            break;
        case 5:
            nRes = 31;
            break;
        case 6:
            nRes = 30;
            break;
        case 7:
            nRes = 31;
            break;
        case 8:
            nRes = 31;
            break;
        case 9:
            nRes = 30;
            break;
        case 10:
            nRes = 31;
            break;
        case 11:
            nRes = 30;
            break;
        case 12:
            nRes = 31;
            break;
    }
    return nRes;
}

function valDia(strValor) {
    var bOk = false;
    var nDia = parseInt(strValor.substr(0, 2), 10);
    bOk = bOk || ((nDia >= 1) && (nDia <= finMes(strValor)));
    return bOk;
}

function valMes(strValor) {
    var bOk = false;
    var nMes = parseInt(strValor.substr(3, 2), 10);
    bOk = bOk || ((nMes >= 1) && (nMes <= 12));
    return bOk;
}

function valAno(strValor) {
    var bOk = true;
    var nAno = strValor.substr(6);
    // (nAno.length == 2) || 
    bOk = bOk && ((nAno.length == 4));
    if (bOk) {
        for (var i = 0; i < nAno.length; i++) {
            bOk = bOk && esDigito(nAno.charAt(i));
        }
    }
    return bOk;
}

function valFecha(strValor) {
    var bOk = true;
    var sw = true
    if (Trim(strValor) != '') {
        bOk = bOk && (valAno(strValor));
        bOk = bOk && (valMes(strValor));
        bOk = bOk && (valDia(strValor));
        bOk = bOk && (valSep(strValor));
        if (!bOk) {
            sw = false;
        }
    } else {
        sw = false;
    }
    if (!sw) {
        alert('Fecha invalida, verifique!\nFormato [dd/mm/aaaa]');
    }
    return sw;
}
// ============================================================================== -->
// ============		            Validar Hora		     	     ============== -->
// ============================================================================== -->
function valHour(strValor) {
    var bOk = true;
    var valor = Trim(strValor);
    var nHour = '', nMin = '';
    if (valor.length != 5)
        bOk = false;
    else {
        nHour = valor.substring(0, 2);
        nMin = valor.substring(3);
        if (nHour.substring(0, 1) == '0')
            nHour = nHour.substring(1, 2);
        if (nMin.substring(3, 4) == '0')
            nMin = nMin.substring(4);
        if (parseInt(nHour) > 23 || parseInt(nMin) > 59)
            bOk = false;
    }
    if (!bOk) {
        alert('Hora invalida, verifique!');
    }
    return bOk;
}
// ============================================================================== -->
// ============		            Validar E.mail		     	     ============== -->
// ============================================================================== -->
function valEmail_(strValor) {
    var bOk = true;
    var valor = Trim(strValor);
    if (valor.indexOf('@', 0) == -1 || valor.indexOf('.', 0) == -1) {
        alert('Direccion de e-mail inv?lida');
        bOk = false;
    }
    return bOk;
}
// ============================================================================== -->
// ============		       Valida cualquier tipo de formato	     ============== -->
// ============================================================================== -->
function validFormat(field, event, format, exeCommad) {
    var key, keychar, cYear;
    var strValid = '';
    var valor = Trim(field.value);
    if (window.event)
        key = window.event.keyCode;
    else if (event)
        key = event.which;
    else
        return true;
    keychar = String.fromCharCode(key);
    if (format) {
        if (format === 'Date')
            strValid = '0123456789/';
        if (format === 'Int')
            strValid = '0123456789';
        if (format === 'Decimal')
            strValid = '0123456789.';
        if (format === 'DecimalPos')
            strValid = '0123456789.';
        if (format === 'Hour')
            strValid = '0123456789:';
        if (format === 'All' || format === 'Mayus')
            strValid = '';
        if (format === 'del')
            strValid = '   ';
        if (format === 'none')
            return false;
    }


    // Comprobar caracteres especiales como espacio atras
    // Luego comprobar numeros
    if ((key === null) ||
            (key === 26) ||
            (key === 27) ||
            (key === 0) ||
            (key === 8) ||
            (key === 9)) {
        return true;
    } else if (
            (key === 34) ||
            (key === 38) ||
            (key === 40) ||
            (key === 37) ||
            (key === 41) ||
            (key === 60) ||
            (key === 61) ||
            (key === 62) ||
            (key === 63) ||
            (key === 39)) {
        return false;
    } else if (key === 13) {
        if (exeCommad) {
            if (format) {
                if (format === 'Date') { // Formato de fecha
                    if (valor.length >= 8) {
                        cYear = valor.substring(6);
                        if (cYear.length === 2) {
                            if (parseInt(cYear.substring(0, 1)) >= 6)
                                cYear = '19' + cYear;
                            else
                                cYear = '20' + cYear;
                            field.value = valor.substring(0, 6) + cYear
                        }
                    }
                    if (!valFecha(field.value)) {
                        return true;
                    }
                }
            }
            eval(exeCommad);
        } else {
            return true;
        }
    } else if (strValid === '') {
        if ((format) && (format === 'Mayus')) {
            if ((key >= 97) && (key <= 122)) {
                key = key - 32;
                window.event.keyCode = key;
            }
        }
        return true;
    } else if (((strValid).indexOf(keychar) > -1)) {
        if ((format) && (format === 'Date')) {
            if (keychar === '/') {
                if (valor.length === 1)
                    valor = '0' + valor;
                if (valor.length === 2 || valor.length === 3)
                    window.event.keyCode = null;
                if (valor.length === 4)
                    valor = valor.substring(0, 3) + '0' + valor.substring(3);
                if (valor.length === 5)
                    window.event.keyCode = null;
                if (valor.lastIndexOf('/') >= 5)
                    return false;
            }

            if (valor.length === 2 || valor.length === 5)
                valor = valor + '/';
            if (valor.length >= 10)
                return false;
            field.value = valor;
        }

        if ((format) && (format === 'Hour')) {
            if (keychar === ':') {
                if (valor.length === 0)
                    return false;
                if (valor.length === 1)
                    valor = '0' + valor;
                if (valor.lastIndexOf(':') >= 2)
                    return false;
            } else {
                if (valor.length === 2)
                    valor = valor + ':';
            }
            if (valor.length >= 5)
                return false;
            field.value = valor;
        }

        if ((format) && (format === 'Decimal'))
        {
            if ((key === 46)) {
                if (valor.indexOf('.') !== -1)
                    return false;
            }

            if (keychar === '.')
            {

                if (valor.indexOf('.') !== -1)
                    return false;
            }
        }

        if (format === 'Int' && (key === 46)) {
            return false;
        }


        return true;
    } else
        return false;
}

function validINT(field, event, exeCommad) {

    var format = 'Int';
    var key, keychar, cYear;
    var strValid = '0123456789';
    var valor = Trim(field.value);
    if (window.event)
        key = window.event.keyCode;
    else if (event)
        key = event.which;
    else
        return true;
    keychar = String.fromCharCode(key);
    // Comprobar caracteres especiales como espacio atras
    // Luego comprobar numeros
    if ((key === null) ||
            (key === 0) ||
            (key === 8) ||
            (key === 9) ||
            (key === 27)) {
        return true;
    } else if ((key === 34) ||
            (key === 37) ||
            (key === 38) ||
            (key === 39) ||
            (key === 40) ||
            (key === 41) ||
            (key === 60) ||
            (key === 61) ||
            (key === 62) ||
            (key === 63)) {
        return false;
    } else if (key === 13) {
        if (exeCommad) {
            if (format) {
                if (format === 'Date') { // Formato de fecha
                    if (valor.length >= 8) {
                        cYear = valor.substring(6);
                        if (cYear.length === 2) {
                            if (parseInt(cYear.substring(0, 1)) >= 6)
                                cYear = '19' + cYear;
                            else
                                cYear = '20' + cYear;
                            field.value = valor.substring(0, 6) + cYear
                        }
                    }
                    if (!valFecha(field.value)) {
                        return true;
                    }
                }
            }
            eval(exeCommad);
        } else {
            return true;
        }
    } else if (strValid === '') {
        if ((format) && (format === 'Mayus')) {
            if ((key >= 97) && (key <= 122)) {
                key = key - 32;
                window.event.keyCode = key;
            }
        }
        return true;
    } else if (((strValid).indexOf(keychar) > -1)) {
        if ((format) && (format === 'Date')) {
            if (keychar === '/') {
                if (valor.length === 1)
                    valor = '0' + valor;
                if (valor.length === 2 || valor.length === 3)
                    window.event.keyCode = null;
                if (valor.length === 4)
                    valor = valor.substring(0, 3) + '0' + valor.substring(3);
                if (valor.length === 5)
                    window.event.keyCode = null;
                if (valor.lastIndexOf('/') >= 5)
                    return false;
            }

            if (valor.length === 2 || valor.length === 5)
                valor = valor + '/';
            if (valor.length >= 10)
                return false;
            field.value = valor;
        }

        if ((format) && (format === 'Hour')) {
            if (keychar === ':') {
                if (valor.length === 0)
                    return false;
                if (valor.length === 1)
                    valor = '0' + valor;
                if (valor.lastIndexOf(':') >= 2)
                    return false;
            } else {
                if (valor.length === 2)
                    valor = valor + ':';
            }
            if (valor.length >= 5)
                return false;
            field.value = valor;
        }

        if ((format) && (format === 'Decimal')) {
            if (keychar === '.') {
                if (valor.indexOf('.') != -1)
                    return false;
            }
        }
        return true;
    } else
        return false;
}
// =============================================================================== -->
// ============		       Coloca el focus() de un Objeto	     ============== -->
// ============================================================================== -->
function nextFocus(name, valor) {
    document.getElementById(name).disabled = false;
    if (valor)
        document.getElementById(name).value = valor;
    document.getElementById(name).focus();
}
// ============================================================================== -->
// ============		       Muetra la ventana de Busqueda	     ============== -->
// ============================================================================== -->
function showWindowFind(fields, table, where, titles, trg, titwin) {
    var x = (screen.Width - 200) / 2;
    var props = 'scrollbars=no,resizable=no,toolbar=no,menubar=no,status=yes,location=no,directories=no,width=200,height=100,top=200,left=' + x;
    var url = Rutac + '/Build/Search';
    var param = '', output = '', field = '', macro = '', newWhere = '', i = 0;
    if (newWindowFind != null) {
        newWindowFind.close();
        newWindowFind = null;
    }

    if (Trim(table) != '') {
        while (table.indexOf('&') != -1) {
            field = table.substring(table.indexOf('&') + 1);
            field = field.substring(0, field.indexOf('&'));
            table = table.replace('&' + field + '&', getValue(field));
        }
    }

    if (Trim(where) != '') {
        while (where.indexOf('&') != -1) {
            field = where.substring(where.indexOf('&') + 1);
            field = field.substring(0, field.indexOf('&'));
            where = where.replace('&' + field + '&', getValue(field));
        }
    }

    trgCurrent = trg;
    if (fields != '')
        param = '?fields=' + fields;
    if (table != '')
        param += '&table=' + table;
    if (where != '')
        param += '&where=' + where;
    if (titles != '')
        param += '&titles=' + titles;
    if (titwin != '')
        param += '&titwin=' + titwin;
    url += param;
    output += '<html>';
    output += '<head>';
    output += '<title>Generando Datos...</title>';
    output += '<link rel="stylesheet" href="' + Rutav + '/vista/css/estilos.css">';
    output += '<script language="JavaScript" type="text/JavaScript" src="' + Rutav + '/vista/script/Funciones.js">';
    output += '</script>';
    output += '<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">';
    output += '</head>';
    output += '<body rightmargin="0" leftMargin="0" topMargin="0" marginheight="0" marginwidth="0" onLoad="reDirect(\'' + url + '\')">';
    output += '<table width="220" align="center" cellpadding="0" cellspacing="0">';
    output += '  <tr valign="middle" height="100">';
    output += '    <td class="caption" align="center">Cargando, espere por favor...</td>';
    output += '  </tr>';
    output += '</table>';
    output += '</body>';
    output += '</html>';
    newWindowFind = window.open('', 'newWindowFind', props);
    newWindowFind.document.open();
    newWindowFind.document.write(output);
    newWindowFind.document.close();
    newWindowFind.focus();
}
// ============================================================================== -->
// Actualiza variables globales con datos selecionados en la ventana de busqueda  -->
// ============================================================================== -->
function updateDataControl(values)
{
    if (values) {
        var i = 0;
        var arrVls = values.split('#');
        var arrTrg = trgCurrent.split('#');
        for (i = 0; i < arrTrg.length; i++)
        {
            if (document.getElementById(arrTrg[i]))
            {
                if (arrVls.length > 1)
                {
                    document.getElementById(arrTrg[i]).value = arrVls[i];
                } else
                {
                    document.getElementById(arrTrg[i]).value = arrVls[0];
                }

                if (i == 0 || i == 1)
                {
                    if (document.getElementById(arrTrg[i]).type != 'hidden')
                        document.getElementById(arrTrg[i]).focus();
                }
            }
        }
    }

    if (newWindowFind != null) {
        newWindowFind.close();
        newWindowFind = null;
        window.focus();
    }
}
// ============================================================================== -->
// ==========	  Muetra la ventana de espera (Con cualquier destino)	 ============ -->
// ============================================================================== -->
function showWindowWait(w, h, r, texto, url, prm, s, name, full) {
    var output = '', pscroll = 'no';
    var x = (screen.Width - w) / 2;
    var y = (screen.Height - h) / 2;
    if (full) {
        x = 0;
        y = 0;
    }

    if (s)
        pscroll = 'yes';
    var props = 'scrollbars=' + pscroll + ',resizable=' + r + ',toolbar=no,menubar=no,status=yes,location=no,directories=no,width=' + w + ',height=' + h + ',top=' + y + ',left=' + x;
    if (url.substring(0, 1) == 'C')
        url = Rutac + url.substring(1);
    else if (url.substring(0, 1) == 'V')
        url = Rutav + url.substring(1);
    if (prm != '')
        url += prm;
    output += '<html>';
    output += '<head>';
    output += '<title>' + texto + '</title>';
    output += '<link rel="stylesheet" href="' + Rutav + '/vista/css/estilos.css">';
    output += '<script language="JavaScript" type="text/JavaScript" src="' + Rutav + '/vista/script/Funciones.js"></script>';
    output += '<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">';
    output += '</head>';
    output += '<body rightmargin="0" leftMargin="0" topMargin="0" marginheight="0" marginwidth="0" onBlur="window.focus()" >';
    output += '<iframe src="' + url + '" name="SubHtml" width="100%" height="100%" scrolling="auto" frameborder="1">';
    output += '<table width="100%" height="100%" align="center" cellpadding="0" cellspacing="0" class="tablas">';
    output += '  <tr valign="middle">';
    output += '    <td class="caption" align="center">' + texto + '</td>';
    output += '  </tr>';
    output += '</table>';
    output += '</iframe>';
    output += '</body>';
    output += '</html>';
    /*  
     output += '<body rightmargin="0" leftMargin="0" topMargin="0" marginheight="0" marginwidth="0" onBlur="window.focus()" onLoad="reDirect(\''+url+'\')">';
     output += '<table width="100%" height="100%" align="center" cellpadding="0" cellspacing="0" class="tablas">';
     output += '  <tr valign="middle">';
     output += '    <td class="caption" align="center">'+texto+'</td>';
     output += '  </tr>';
     output += '</table>';
     output += '</body>';
     output += '</html>';
     */
    if (!name)
        newWindowReport = window.open('', 'newWindowWait', props);
    else
        newWindowReport = window.open('', name, props);
    newWindowReport.document.open();
    newWindowReport.document.write(output);
    newWindowReport.document.close();
    newWindowReport.focus();
}
// ============================================================================== -->
//               Actualiza los parametros que se envian por una url               -->
// ============================================================================== -->
function getParametersValues(prm) {
    var i = 0, count = 0;
    var prmValues = '', value = '';
    var arrNamePrm = prm.split('#');
    for (i = 0; i < arrNamePrm.length; i++) {
        value = getValue(arrNamePrm[i]);
        if (value != '') {
            if (count == 0)
                prmValues += '?' + arrNamePrm[i] + '=' + value;
            else
                prmValues += '&' + arrNamePrm[i] + '=' + value;
            count++;
        }
    }
    return prmValues;
}
// ============================================================================== -->
//               Obtener valores de elementos del documento                       -->
// ============================================================================== -->
function getValue(name) {
    var value = '';
    if (document.getElementById(name))
        value = Trim(document.getElementById(name).value);
    return value;
}
// ***************************************************************************** -->
// *********** Dibuja el layer para los Tool Tips en cualquier pagina ********** -->
// ***************************************************************************** -->
function makeLayerToolTips(id, left, top, width, height, message) {
    var str = '';
    try {
        str += '<div id="' + id + '" style="position:absolute; left:' + left + 'px; top:' + top + 'px; width:' + width + 'px; height:' + height + 'px; z-index:100; background-color: #FFFFCC; layer-background-color: #FFFFCC; border: 1px inset #000000; visibility: hidden;">';
        str += '<div align="center"><label class="caption">&nbsp;' + message + '&nbsp;</div></label>';
        str += '</div>';
        document.write(str);
    } catch (theException) {
        alert('Error [makeLayerToolTips] ' + theException);
    }
}

// ***************************************************************************** -->
// **********     Dibuja un layer determinado                         ********** -->
// ***************************************************************************** -->
function makeLayerHtml(HTML)
{
    try
    {
        var str = '', valor = '';
        var bgnidx = 0, endidx = 12;
        str += '<div id="capaHtml" style="position:absolute; left:0px; top:0px; width:220px; z-index:100; background-color: #DDDDDD; layer-background-color: #CCCCCC; border: 1px none #000000; height: 59px;visibility: hidden;">';
        str += '<table width="220" border="0" align="center" cellpadding="1" cellspacing="1" class="simple">';
        str += '  <tr class="rowtitle">';
        str += '    <td class="title"><div align="center">' + HTML + '</div></td>';
        str += '  </tr>';
        str += '</table></div>';
        document.write(str);
    } catch (ex) {
        alert(ex);
    }
}


// ***************************************************************************** -->
// **********     Dibuja el layer de la hora en cualquier pagina      ********** -->
// ***************************************************************************** -->
function makeLayerHour(hormil) {
    var str = '', valor = '';
    var bgnidx = 0, endidx = 12;
    try {
        Init();
        str += '<div id="capaHora" style="position:absolute; left:0px; top:0px; width:220px; z-index:100; background-color: #FFFFFF; layer-background-color: #FFFFFF; border: 1px none #000000; height: 59px; visibility: hidden;">';
        str += '<table width="220" border="0" align="center" cellpadding="1" cellspacing="1" class="tablas">';
        str += '  <tr class="rowtitle">';
        str += '    <td class="title"><div align="center">Escoger Hora</div></td>';
        str += '  </tr>';
        str += '  <tr>';
        str += '    <td>';
        str += '	  <div align="center">';
        if (hormil) {
            bgnidx = -1;
            endidx = 23;
        }
        str += '        <select name="cmbHora" id="cmbHora" class="combo">';
        for (i = bgnidx; i < endidx; i++) {
            valor = '' + (i + 1);
            if (valor.length == 1)
                valor = '0' + valor;
            str += '          <option value="' + valor + '">' + valor + '</option>';
        }
        str += '        </select> : ';
        str += '        <select name="cmbMin" id="cmbMin" class="combo">';
        for (i = 0; i < 60; i++) {
            if ((i % 5) == 0) {
                valor = '' + i;
                if (valor.length == 1)
                    valor = '0' + valor;
                str += '          <option value="' + valor + '">' + valor + '</option>';
            }
        }
        str += '        </select>';
        if (!hormil) {
            str += '        <select name="cmbMer" id="cmbMer" class="combo">';
            str += '          <option value="AM">AM</option>';
            str += '          <option value="PM">PM</option>';
            str += '        </select>';
        }

        str += '      </div>';
        str += '	</td>';
        str += '  </tr>';
        str += '  <tr> ';
        str += '    <td>';
        str += '	    <div align="center">';
        str += '        <input name="BotAceptar" type="button" id="BotAceptar" value="Aceptar" onClick="updateHour(1,\'\')" class="boton">';
        str += '        <input name="BotCancelar" type="button" id="BotCancelar" value="Cancelar" onClick="showHideLayers(\'capaHora\',\'hidden\')" class="boton">';
        str += '      </div>';
        str += '	  </td>';
        str += '  </tr>';
        str += '</table>';
        str += '</div>';
        document.write(str);
    } catch (theException) {
        alert('Error [makeLayerHour] ' + theException);
    }
}
// ***************************************************************************** -->
// **********  Dibuja el layer de escoger color en cualquier pagina    ********* -->
// ***************************************************************************** -->
function makeLayerColor() {
    var str = '';
    d = new Array();
    d[1] = "00";
    d[2] = "33";
    d[3] = "66";
    d[4] = "99";
    d[5] = "CC";
    d[6] = "FF";
    try {
        Init();
        str += '<div id="capaColor" style="position:absolute; left:0px; top:0px; width:220px; z-index:100; background-color: #FFFFFF; layer-background-color: #FFFFFF; border: 1px none #000000; height: 59px; visibility: hidden;">';
        str += '<table width="220" border="0" align="center" cellpadding="1" cellspacing="1" class="tablas" onMouseOver="highligthColor(true)" onMouseOut="highligthColor(false)" onClick="updateColor(1,\'\')">';
        str += '  <tr class="rowtitle">';
        str += '    <td class="title" align="center" colspan="18">Colores</td>';
        str += '  </tr>';
        str += '  <tr><td colspan="12" class="caption">&nbsp;</td><td id="showColor" colspan="6" rowspan="3">&nbsp;</td></tr>';
        str += '  <tr class="drag">';
        str += '    <td bgcolor="#000000" title="#000000" width="12" height="12">&nbsp;</td>';
        str += '    <td bgcolor="#333333" title="#333333" width="12" height="12">&nbsp;</td>';
        str += '    <td bgcolor="#666666" title="#666666" width="12" height="12">&nbsp;</td>';
        str += '    <td bgcolor="#999999" title="#999999" width="12" height="12">&nbsp;</td>';
        str += '    <td bgcolor="#CCCCCC" title="#CCCCCC" width="12" height="12">&nbsp;</td>';
        str += '    <td bgcolor="#FFFFFF" title="#FFFFFF" width="12" height="12">&nbsp;</td>';
        str += '    <td bgcolor="#FF0000" title="#FF0000" width="12" height="12">&nbsp;</td>';
        str += '    <td bgcolor="#00FF00" title="#00FF00" width="12" height="12">&nbsp;</td>';
        str += '    <td bgcolor="#0000FF" title="#0000FF" width="12" height="12">&nbsp;</td>';
        str += '    <td bgcolor="#FFFF00" title="#FFFF00" width="12" height="12">&nbsp;</td>';
        str += '    <td bgcolor="#00FFFF" title="#00FFFF" width="12" height="12">&nbsp;</td>';
        str += '    <td bgcolor="#FF00FF" title="#FF00FF" width="12" height="12">&nbsp;</td>';
        str += '  </tr>';
        str += '  <tr><td colspan="12">&nbsp;</td></tr>';
        str += '  <tr class="drag">';
        for (c = 1; c <= 6; c++) {
            for (a = 1; a <= 3; a++) {
                for (b = 1; b <= 6; b++) {
                    colour = d[a] + d[b] + d[c];
                    str += '    <td bgcolor="#' + colour + '" title="#' + colour + '" width="12" height="12">&nbsp;</td>';
                    if (a == 3 && b == 6)
                        str += '</tr>\n<tr class="drag">';
                }
            }
        }
        for (c = 1; c <= 6; c++) {
            for (a = 4; a <= 6; a++) {
                for (b = 1; b <= 6; b++) {
                    colour = d[a] + d[b] + d[c];
                    str += '    <td bgcolor="#' + colour + '" title="#' + colour + '" width="12" height="12">&nbsp;</td>';
                    if (a == 6 && b == 6)
                        str += '</tr>\n<tr class="drag">';
                }
            }
        }
        str += '  </tr>';
        str += '  <tr> ';
        str += '    <td align="center" colspan="18">';
        str += '      <input name="BotCerrar" type="button" id="BotCerrar" value="Cerrar" onClick="showHideLayers(\'capaColor\',\'hidden\')" class="boton">';
        str += '	  </td>';
        str += '  </tr>';
        str += '</table>';
        str += '</div>';
        document.write(str);
    } catch (theException) {
        alert('Error [makeLayerColor] ' + theException);
    }
}
function highligthColor(on) {
    var el = window.event.srcElement;
    var disp = document.getElementById('showColor');
    if (el && el.nodeName == 'TD')
        if (on)
            disp.bgColor = el.title;
}
// ***************************************************************************** -->
// ************************ Se obtienen posiciones del mouse ******************* -->
// ***************************************************************************** -->
function mouseMove(e) {
    xMouse = (document.layers) ? e.pageX : event.x + document.body.scrollLeft
    yMouse = (document.layers) ? e.pageY : event.y + document.body.scrollTop
    return true
}
// ***************************************************************************** -->
// ******* Inicializa en evento onmousemove con la funcion [mouseMove] ********* -->
// ***************************************************************************** -->
function Init() {
    if (!swOnMouseMove) {
        document.onmousemove = mouseMove;
        if (document.layers) {
            document.captureEvents(Event.MOUSEMOVE);
        }
        swOnMouseMove = true;
    }
}
// ***************************************************************************** -->
// *****************       Actualiza un campo de tipo Hora      ***************** -->
// ***************************************************************************** -->
function updateHour(op, nameField) {
    var Hour = '';
    if (op == 0) {
        currendField = nameField;
        showHideLayers('capaHora', 1);
    } else if (op == 1) {
        Hour = getValue('cmbHora') + ':' + getValue('cmbMin');
        if (document.getElementById('cmbMer'))
            Hour += ' ' + getValue('cmbMer');
        document.getElementById(currendField).value = Hour;
        showHideLayers('capaHora', 0);
    }
}
// ***************************************************************************** -->
// *****************      Actualiza un campo de tipo Color     ***************** -->
// ***************************************************************************** -->
function updateColor(op, nameField) {
    var colorHex = '';
    if (op == 0) {
        currendField = nameField;
        showHideLayers('capaColor', 1);
    } else if (op == 1) {
        var el = window.event.srcElement;
        if (el && el.nodeName == 'TD') {
            colorHex = el.title.substring(1);
            colorHex = colorHex.substring(4, 6) + colorHex.substring(2, 4) + colorHex.substring(0, 2);
            document.getElementById(currendField).value = parseInt(colorHex, 16);
            document.getElementById(currendField).style.backgroundColor = el.title;
            document.getElementById(currendField).style.color = el.title;
        }
        showHideLayers('capaColor', 0);
    }
}
// ***************************************************************************** -->
// *****************   Muestra u Oculta un layer segun su id   ***************** -->
// ***************************************************************************** -->
function showHideLayers(name, op, message) {
    var opVisible = 'hidden';
    var output = '';
    var urlImg = '';
    var xWidth = 0;
    if (op == 1) {
        xMouse += 10;
        xWidth = xMouse + parseInt(document.getElementById(name).style.width);
        if (getWidthWin() < xWidth) {
            xMouse = getWidthWin() - (xWidth - getWidthWin()) - parseInt(document.getElementById(name).style.width);
        }
        document.getElementById(name).style.top = yMouse;
        document.getElementById(name).style.left = xMouse;
        if (message) {
            urlImg = Rutav + '/vista/img/security.gif';
            output += '<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1">';
            output += '  <tr style="font-family:Verdana, Arial, Helvetica, sans-serif; font-size:10px;">';
            output += '    <td valign="top"><div align="left"><img src="' + urlImg + '"></div></td>';
            output += '    <td><div align="justify">' + message + '</div></td>';
            output += '  </tr>';
            output += '</table>';
            document.getElementById(name).innerHTML = output;
        }
        opVisible = 'visible';
    } else if (op == 'visible') {
        opVisible = op;
    } else if (op == 'hidden') {
        opVisible = op;
    }

    document.getElementById(name).style.visibility = opVisible;
}
// ***************************************************************************** -->
// *****************       Marcar ? Desamarcar CheckBox        ***************** -->
// ***************************************************************************** -->
function SelectCheck(sw, name) {
    var i = 0;
    element = document.getElementsByTagName('INPUT');
    for (i = 0; i < element.length; i++) {
        if (element[i].type == 'checkbox') {
            if (name) {
                if (element[i].name == name)
                    element[i].checked = sw;
            } else {
                if (sw != -1)
                    element[i].checked = sw;
                else
                    element[i].checked = !element[i].checked;
            }
        }
    }
}
// ***************************************************************************** -->
// *******       Muestra una nueva ventana con cualquier destino        ******** -->
// ***************************************************************************** -->
function showWindow(name, t, l, w, h, r, url) {
    if (t == -1)
        t = (screen.height - h) / 2;
    if (l == -1)
        l = (screen.width - w) / 2;
    if (url.substring(0, 1) == 'C')
        url = Rutac + url.substring(1);
    else if (url.substring(0, 1) == 'V')
        url = Rutav + url.substring(1);
    if (newWindow != null) {
        newWindow.close();
        newWindow = null;
    }

    if (name == 'winPhoto') {
        if (url.substring(url.length - 1, url.length) == '=') {
            url = Rutav + '/vista/formUploadFile.jsp' + getParametersValues('codpho#codprs#codbas#codprf#codtrc#codntd');
        }
    }

    var props = 'scrollbars=yes,resizable=' + r + ',toolbar=no,menubar=no,status=yes,location=no,directories=no,width=' + w + ',height=' + h + ',top=' + t + ',left=' + l;
    newWindow = window.open(url, name, props);
    newWindow.focus();
}
// ***************************************************************************** -->
// ********* Funcion que valida si la fecha final es mayor que la inicial ****** -->
// ***************************************************************************** -->
function validDateTime(dateBgn, dateEnd, condition, op) {
    var nRes = 0, ndateBgn = 0, ndateEnd = 0;
    ndateBgn = stringToDateTime(dateBgn, op);
    ndateEnd = stringToDateTime(dateEnd, op);
    if (eval(ndateBgn + condition + ndateEnd)) {
        return true;
    } else {
        return false;
    }
}
// ***************************************************************************** -->
// **************    Funcion que convierte dato String -  Date        ********** -->
// ***************************************************************************** -->
function stringToDateTime(mydate, op) {
    var Fecha = new Date();
    var cHour = '', cMin = '', cMer = '', cRes = '';
    var cDay = '', cMonth = '', cYear = '', cFecha = '';
    var nRes = 0, nHour = 0;
    var arrFch = new Array();
    cFecha = mydate;
    if (op == 0) {
        arrFch = cFecha.split('/');
        cDay = arrFch[0];
        cMonth = arrFch[1];
        cYear = arrFch[2];
        if (cMonth.substring(0, 1) == '0')
            cMonth = cMonth.substring(1, cMonth.length);
        if (cDay.substring(0, 1) == '0')
            cDay = cDay.substring(1, cDay.length);
        if (cYear.length == 2) {
            if (cYear.substring(0, 1) != '9')
                cYear = '20' + cYear;
            else
                cYear = '19' + cYear;
        }
        Fecha.setYear(parseInt(cYear));
        Fecha.setMonth((parseInt(cMonth) - 1));
        Fecha.setDate(parseInt(cDay));
    } else {
        arrFch = cFecha.split(':');
        cHour = arrFch[0];
        cMin = arrFch[1];
        arrFch = cFecha.split(' ');
        if (arrFch.length == 2)
            cMer = arrFch[1];
        if (cHour.substring(0, 1) == '0')
            cHour = cHour.substring(1, cHour.length);
        if (cMin.substring(0, 1) == '0')
            cMin = cMin.substring(1, cMin.length);
        if (cHour == '12' && cMer != '')
            cHour = '00';
        if (cMer == 'PM')
            nHour = parseInt(cHour) + 12;
        else
            nHour = parseInt(cHour);
        Fecha.setHours(nHour);
        Fecha.setMinutes(parseInt(cMin));
        Fecha.setSeconds(0);
    }

    nRes = Fecha.getTime() / 1000;
    cRes = '' + nRes;
    return (parseInt(cRes))
}
// ***************************************************************************** -->
// **********     Dibuja el layer del A?o/Periodo en cualquier pagina ********** -->
// ***************************************************************************** -->
function makeLayerLoop(strjvs) {
    var str = '', valor = '', strspt = '';
    var bgnidx = 2006, endidx = 2012;
    try {
        //Init();
        str += '<div id="layerLoop" style="position:absolute; left:10px; top:10px; width:160px; z-index:100; background-color: #FFFFFF; layer-background-color: #FFFFFF; border: 1px none #000000; height: 50px; visibility: visible;">';
        str += '<table width="160" border="0" align="center" cellpadding="1" cellspacing="1" class="tablas">';
        str += '  <tr class="rowtitle">';
        str += '    <td class="title"><div align="center">Periodo</div></td>';
        str += '  </tr>';
        str += '  <tr>';
        str += '    <td>';
        str += '	  <div align="center">';
        if (strjvs) {
            strspt = 'onChange="javascript:' + strjvs + '"';
        }

        str += '        <select name="agnlop" id="agnlop" class="combo" ' + strspt + '>';
        for (i = bgnidx; i < endidx; i++) {
            valor = '' + i;
            str += '          <option value="' + valor + '">' + valor + '</option>';
        }
        str += '        </select> - ';
        str += '        <select name="prdlop" id="prdlop" class="combo" ' + strspt + '>';
        for (i = 1; i < 3; i++) {
            valor = '' + i;
            str += '          <option value="' + valor + '">' + valor + '</option>';
        }
        str += '        </select>';
        str += '      </div>';
        str += '	</td>';
        str += '  </tr>';
        str += '</table>';
        str += '</div>';
        document.write(str);
    } catch (theException) {
        alert('Error [makeLayerLoop] ' + theException);
    }
}


/********* SCRIPTS PARA Funciones.js  *************/
function get(elemento) {
    return document.getElementById(elemento);
}

function chnColor(elemento, color) {
    get(elemento).style.backgroundColor = color;
}

function schStr(cadena, charac) {
    var contador = 0;
    for (i = 0; i < cadena.length; i++) {
        if (cadena.charAt(i) == charac)
            contador++;
    }

    return contador;
}

function validDouble(cadena) {
    for (i = 0; i < cadena.length; i++) {
        if (!isDouble(cadena.charAt(i)))
            return false;
    }
    return true;
}

function validInt(cadena) {
    for (i = 0; i < cadena.length; i++) {
        if (!isNum(cadena.charAt(i)))
            return false;
    }
    return true;
}

function isNum(charac) {
    if (charac == "1" || charac == "2" || charac == "3" || charac == "4" ||
            charac == "5" || charac == "6" || charac == "7" || charac == "8" ||
            charac == "9" || charac == "0")
        return true;
    return false;
}

function isDouble(charac) {
    if (isNum(charac) || charac == ".")
        return true;
    return false;
}
/*
 window.onerror=
 function(msg, url, linenumber){
 var logerror='Error message: ' + msg +'\n'+
 'Line Number: ' + linenumber
 alert(logerror)
 return true
 }*/


// ***************************************************************************** -->
// ******  Funcionen para formatear un numero                            ******* -->
// ***************************************************************************** -->
function formatAsMoney(num) {

    num = num.toString().replace(/$,/g, "");
    if (isNaN(num))
        num = "0";
    sign = (num == (num = Math.abs(num)));
    num = Math.floor(num * 100 + 0.50000000001);
    cents = num % 100;
    num = Math.floor(num / 100).toString();
    if (cents < 10)
        cents = "0" + cents;
    for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
        num = num.substring(0, num.length - (4 * i + 3)) + "," + num.substring(num.length - (4 * i + 3));
    return (((sign) ? "" : "-") + "" + num + "." + cents);
}

function unformatNumber(num) {
    return num.replace(/([^0-9\.\-])/g, '') * 1;
}

/************  MODIFICACIONES 10 SEPTIEMBRE 2009 ****************/
function crearBotonBuscar(id_search) {
    get(id_search + "Shw").style.border = "0px";
    get(id_search + "Shw").style.width = "0";
    get(id_search + "Shw").style.backgroundImage = "url('<%= BASEURL %>/vista/img/Background.jpg')";
    get("bton" + id_search + "Show").className = "boton";
    get("bton" + id_search + "Show").value = "Buscar";
}
/**********************************/


//Obtiene un array de los elementos cuyo nombre es igual al pasado por el parametro  nom_elements
function getByName(nom_elements) {
    return document.getElementsByName(nom_elements);
}

/************  MODIFICACIONES 29 AGOSTO 2009 ****************/
var arrayMeses = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"];
function crearComboMeses(name_combo, selected_item, title) {
    var strComboMeses = "";
    var mes_selected = parseInt(selected_item, 10);
    strComboMeses += "<select name='" + name_combo + "' id='" + name_combo + "' title='" + title + "' class='Combo'>";
    for (i = 0; i < arrayMeses.length; i++) {
        var mesAux = i + 1;
        var mes = mesAux < 10 ? "0" + (mesAux) : "" + mesAux;
        strComboMeses += "<option value='" + mes + "'" + (mes_selected == mesAux ? "selected" : "") + ">" + arrayMeses[i] + "</option>";
    }
    strComboMeses += "</select>";
    document.write(strComboMeses);
}


/************  MODIFICACIONES 27 AGOSTO 2009 ****************/

//Obtener el texto de botones u objetos select
function getText(id_elemento) {
    switch (get(id_elemento).type) {
        case "button":
        case "reset":
        case "submit":
            return getValue(id_elemento);
            break;
        case "select-one":
            var obj_select = get(id_elemento);
            var option = obj_select.options[obj_select.selectedIndex];
            return option.text;
            break;
        case "select-multiple":
            var obj_select = get(id_elemento);
            var text = "";
            for (i = 0; i < obj_select.options.length; i++) {
                if (obj_select.options[i].selected == true) {
                    var option = obj_select.options[i];
                    text += option.text + ",";
                }
            }
            if (text != "")
                text = text.substring(0, text.length - 1);
            return text;
            break;
    }
}

function cargarImagen(tr, color) {
//tr.style.background = 'url('+ Rutav + '/vista/img/trbg.jpg)';
    tr.style.backgroundColor = color;
}

function quitarImagen(tr, color) {
//tr.style.background = '';
    tr.style.backgroundColor = color;
}

function cargar_Imagen(tr, img) {
    tr.style.background = 'url(' + Rutav + '/vista/img/' + img + ')';
}

function quitar_Imagen(tr) {
    tr.style.background = '';
}

function changeCursor(control) {
    control.style.cursor = 'pointer';
}

function clearForm()
{
    var i = 0;
    element = document.getElementsByTagName('INPUT');
    for (i = 0; i < element.length; i++)
    {
        try
        {
            if (element[i].type == 'hidden' || element[i].type == 'text')
            {
                element[i].value = '';
            } else
            if (element[i].type == 'radio' || element[i].type == 'checkbox')
            {
                element[i].value = '';
                element[i].ckecked = false;
            }
        } catch (ex)
        {
            alert(ex + ' : funcion clearForm');
        }
    }
}

function Seleccionar(objeto) {
    objeto.select();
}

function seleccionar() {
    this.select();
}

function formatNumber(num, objTxt, prefix) {
    prefix = prefix || '';
    num += '';
    var splitStr = num.split('.');
    var splitLeft = splitStr[0];
    var splitRight = splitStr.length > 1 ? '.' + splitStr[1] : '';
    var regx = /(\d+)(\d{3})/;
    while (regx.test(splitLeft)) {
        splitLeft = splitLeft.replace(regx, '$1' + ',' + '$2');
    }
    if (objTxt)
        objTxt.value = prefix + splitLeft + splitRight;
    else
        return prefix + splitLeft + splitRight;
}

function toolTip(evento, tagHtml) {
    var elEvento = evento || window.event;
    if (elEvento.target.text == undefined) {
        tagHtml.title = tagHtml.options[tagHtml.options.selectedIndex].text;
    } else {
        tagHtml.title = elEvento.target.text;
        titulo = elEvento.target.text;
    }
}

//Obtiene un objeto Date a partir de una cadena de la forma dd/MM/yyyy
function getFecha(str_fecha) {
    var arrAux = str_fecha.split("/");
    return new Date(parseInt(arrAux[2], 10), parseInt(arrAux[1], 10) - 1, parseInt(arrAux[0], 10));
}

//Verifica si todos los checkbox relacionados con un name estan seleccionados o deseleccionados 
function verifyCheckBox(checked, name) {
    var arrChk = getByName(name);
    for (i = 0; i < arrChk.length; i++) {
        if (arrChk[i].checked != checked)
            return false;
    }

    return true;
}

/************  MODIFICACIONES 15 OCTUBRE 2009 ****************/
function getByTypeInput(type, id_element) {
    var elemtn = id_element || '';
    var arrType = new Array();
    var i = 0;
    var arrInput = null;
    if (elemtn != '')
        arrInput = get(id_element).getElementsByTagName("INPUT");
    else
        arrInput = document.getElementsByTagName("INPUT");
    for (j = 0; j < arrInput.length; j++) {
        var inputElement = arrInput[j];
        if (inputElement.type == type) {
            arrType[i] = inputElement;
            i++;
        }
    }

    return arrType;
}
/**********************************/

function quitarFormato(element_padre)
{
    var padre = element_padre || '';
    var regExp = /^num_/;
    if (padre != '')
    {
        var arrElements = getByTypeInput("text", padre);
    } else
    {
        var arrElements = getByTypeInput("text");
    }

    for (i = 0; i < arrElements.length; i++)
    {
        if (regExp.test(arrElements[i].id))
            arrElements[i].value = unformatNumber(arrElements[i].value);
    }
}

function seleccionarAllCheckbox(name, checked)
{
    var arrChk = getByName(name);
    if (arrChk.length > 0)
    {
        for (i = 0; i < arrChk.length; i++)
            arrChk[i].checked = checked;
    }
}

/*TABS*/


function cargar_class(tr, clsNme) {
    tr.className = clsNme;
}

function update_class(tr, id_content) {
    clsNme = tr.className;
    if (clsNme != 'tabs')
        cargar_class(tr, 'tdOver')
}

function emptyTabs()
{
    var tabs = get('tabs').cells;
    for (i = 0; i < tabs.length; i++)
    {
        tabs[i].className = 'tdInactive';
    }
}

function load_Content(tab, url, id_content, funcion)
{
    var tabs = get('tabs').cells;
    for (i = 0; i < tabs.length; i++)
    {
        if (tabs[i].id != tab.id)
            tabs[i].className = 'tdInactive';
        else
        {
            tabs[i].className = 'tabs';
            clsNme = 'tabs';
            var function_ = funcion + '("' + tab.id + '","' + url + '","' + id_content + '")';
            eval(function_);
        }
    }
}

function makeTabs(id_content, heigth_content, funcion, ret)
{
    var HTML_ = '';
    var onclick = '';
    var url_ = '';
    HTML_ += '<table width="100%" cellspacing="0" cellpading="0" align="center" border="0" >';
    HTML_ += '<tr>';
    HTML_ += '<td >';
    HTML_ += '<table width="100%" cellpadding="0" cellspacing="0" border="0"  >';
    HTML_ += '<tr bgcolor = "#EFEFEF">';
    HTML_ += '<td >';
    HTML_ += '<table  align="left" cellspacing="1" cellpading="0" class="tableMetal">';
    HTML_ += '<tr height="23" id="tabs">';
    for (var i = 0; i < arrTab.length; i++)
    {
        if (arrUrl[i].substring(0, 1) == 'C')
            url_ = Rutac + arrUrl[i].substring(1);
        else if (arrUrl[i].substring(0, 1) == 'V')
            url_ = Rutav + arrUrl[i].substring(1);
        onclick = 'onClick = \'load_Content(this,"' + url_ + '","' + id_content + '","' + funcion + '")\'';
        HTML_ += '<td class="tdInactive" id = "' + arr_Id[i] + '" align="center" ' + onclick + ' onmouseover="update_class(this)" onmouseout="cargar_class(this,clsNme)">';
        HTML_ += '&nbsp;' + arrTab[i] + '&nbsp;';
        HTML_ += '</td>';
    }


    HTML_ += '</tr>';
    HTML_ += '</table>';
    HTML_ += '</td>';
    HTML_ += '</tr>';
    HTML_ += '<tr>';
    HTML_ += '<td>';
    HTML_ += '<table width="100%" align="left" class = "tableMetal">';
    HTML_ += '<tr>';
    HTML_ += '<td  height="' + heigth_content + '" valign="top" >';
    HTML_ += '<div id = "' + id_content + '" style="overflow: auto;height: 100%">';
    HTML_ += '</div>';
    HTML_ += '</td>';
    HTML_ += '</tr>';
    HTML_ += '</table>';
    HTML_ += '</td>';
    HTML_ += '</tr>';
    HTML_ += '</table>';
    HTML_ += '</td>';
    HTML_ += '</tr>';
    HTML_ += '</table>';
    if (ret)
        return(HTML_);
    else
        document.write(HTML_);
}

/*MESSAGE MODAL*/
/*
 function show_message(msg,callback) {    
 // a workaround for a flaw in the demo system (http://dev.jqueryui.com/ticket/4375), ignore!
 
 var width_ = msg.length * 9;		
 if(width_ > 600) width_ = 600;
 
 document.getElementById('dialog-message').innerHTML = msg;
 $("#dialog").dialog("destroy");
 
 $("#dialog-message").dialog({
 modal: true,
 buttons: {
 Ok: function() {
 $(this).dialog('close');
 if(callback)
 eval(callback);
 }
 },
 close: function(event, ui) 
 {
 if(callback)
 eval(callback);
 },   
 width: width_,
 zIndex: 0                         
 });
 }
 */

function printMessage()
{
    document.write('<div class="demo" style="display: none" id="div_dialog"><div id="dialog-message" title="INFORMACION" ></div></div>');
}
/*FIN MESSAGE MODAL*/

/*FORM MODAL*/

function conf_form(width_, height_, id, callbackO, callbackC, noBoton, posicion)
{
    if (typeof id === 'string')
        id = $("#" + id);
    else
        id = $(id);
    if (!id)
        id = 'dialog';
    try {
        id.dialog("destroy");
    } catch (ex) {
    }

    if (height_ == '100%')
        height_ = window.offsetHeight - 40;
    if (!width_)
        width_ = 'auto';
    if (!height_)
        height_ = 'auto';

    if (width_ !== 'auto') {
        var a = $('<div id="aux"/>').css({width: width_}).appendTo(window.top.document.body);
        width_ = $(a).width();
        a.remove();
    }

    if (noBoton)
    {
        id.dialog
                ({
                    bgiframe: true,
                    autoOpen: false,
                    resizable: false,
                    height: height_,
                    closeOnEscape: false,
                    draggable: true,
                    position: posicion,
                    width: width_,
                    modal: true,
                    close: function () {

                        if (callbackC)
                            eval(callbackC);
                    },
                    open: function () {
                        if (callbackO)
                            eval(callbackO);
                    },
                    zIndex: 0
                });
    } else
    {
        id.dialog({
            bgiframe: true,
            autoOpen: false,
            resizable: false,
            draggable: true,
            closeOnEscape: false,
            height: height_,
            width: width_,
            buttons: {
                Salir: function () {
                    $(this).dialog('close');
                }
            },
            modal: true,
            close: function () {
                if (callbackC)
                    eval(callbackC);
            },
            open: function () {
                if (callbackO)
                    eval(callbackO);
            },
            zIndex: 0
        });
    }
}


/*PANEL*/

function conf_panel(width_, height_, id, position_, noBoton)
{
    if (!id)
        id = 'dialog';
    try {
        $("#" + id).dialog("destroy");
    } catch (ex) {
    }
    if (width_ !== 'auto' || height_ !== 'auto') {
        var a = $('<div id="aux"/>').css({width: width_, height: height_}).appendTo(window.top.document.body);
        width_ = $(a).width();
        height_ = $(a).height();
        a.remove();

    }

//    if (height_ == '100%')
//        //height_ = window.top.get('display').offsetHeight - 25;
//    if (width_ == '100%')
//        //width_ = window.top.get('display').offsetWidth - 12;
    if (!width_)
        width_ = 'auto';
    if (!height_)
        height_ = 'auto';
    if (!position_ && $.type(position_) !== 'object')
        position_ = 'top';
    var buttons;
    if (position_) {
        if ($.type(position_) === 'object') {
            buttons = $.extend({}, position_);
            if (!buttons.Salir) {
                buttons.Salir = function () {
                    $(this).dialog('close');
                }
            }
        }
    }


    if (noBoton)
    {
        $("#" + id).dialog({
            bgiframe: true,
            autoOpen: true,
            resizable: false,
            height: height_,
            position: ['center', 20],
            draggable: false,
            closeOnEscape: false,
            width: width_,
            zIndex: 0,
            modal: false,
            close: function () {
                /*if(callbackC)
                 eval(callbackC);*/
            }
        });
    } else
    {
        $("#" + id).dialog({
            bgiframe: true,
            autoOpen: true,
            resizable: false,
            height: height_,
            position: ['top', 20],
            draggable: false,
            width: width_,
            closeOnEscape: false,
            zIndex: 0,
            stack: false,
            modal: false,
            buttons: buttons,
            close: function () {
                /*if(callbackC)
                 eval(callbackC);*/
            }
        });
    }


}

/*CONFIRM*/

function show_confirm(msg, callbackA, callbackC)
{
    var width_ = msg.length * 9;
    if (width_ > 600)
        width_ = 600;
    var cerrarA = true;
    var cerrarC = true;
    document.getElementById('dialog-confirm').innerHTML = msg;
    $("#dialog-confirm").dialog("destroy");
    $("#dialog-confirm").dialog({
        resizable: false,
        modal: true,
        buttons: {
            'Cancelar': function () {
                $(this).dialog('close');
                cerrarC = false;
            },
            'Aceptar': function (event, ui) {
                cerrarA = false;
                $(this).dialog('close');
            }
        },
        close: function (event, ui)
        {
            if (callbackA && !cerrarA)
                eval(callbackA);
            else if (callbackC && !cerrarC)
                eval(callbackC);
        },
        width: width_,
        zIndex: 0
    });
}

function printConfirm()
{
    document.write('<div class="demo" style="display: none" id="div_confirm"><div id="dialog-confirm" title="CONFIRMAR"></div></div>');
}

/*FIN CONFIRM MODAL*/

/*DATEPICKER*/
function conf_datepicker(id, months, funcion, minDate_, maxDate_)
{
    if (!id)
        id = 'datepicker';
    if (!months)
        months = 3;
    else
        months = parseInt(months);
    if (!funcion)
        funcion = '';
    if (typeof minDate_ == "string" && minDate_ == "")
        minDate_ = null;
    else if (!minDate_)
        minDate_ = 'today';
    if (!maxDate_)
        maxDate_ = null;
    $('#' + id).datepicker
            ({
                //showOn          : 'both',  
                closeText: 'Cerrar',
                prevText: '<Ant',
                nextText: 'Sig>',
                currentText: 'Hoy',
                monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
                monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
                dayNames: ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'],
                dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mié', 'Juv', 'Vie', 'Sáb'],
                dayNamesMin: ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sá'],
                numberOfMonths: months,
                showButtonPanel: true,
                buttonImageOnly: true,
                showAnim: '',
                dateFormat: 'yy-mm-dd',
                minDate: minDate_,
                maxDate: maxDate_,
                changeMonth: true,
                yearRange: 'c-25:c+25',
                changeYear: true,
                onSelect: function (date) {
                    if (typeof funcion === 'string')
                        eval(funcion);
                    else
                        funcion.call(this, date);
                }
            });
    //$('#' + id).datepicker($.datepicker.regional['es']);
}

function printDatePicker(id, value)
{
    if (!id)
        id = 'datepicker';
    if (!value)
        value = '';
    document.write('<div class="demo"><input type="text" title = "Fecha" id="' + id + '" value="' + value + '" name="' + id + '" class = "text" size = "10"></div>');
}

function returnDatePicker(id, value)
{
    if (!id)
        id = 'datepicker';
    if (!value)
        value = '';
    return '<div class="demo"><input type="text" title = "Fecha" id="' + id + '" name="' + id + '" class = "text" size = "10" value="' + value + '"></div>';
}
/*FIN DATEPICKER*/

/*AUTOCOMPLETE*/

function init_autocomplete(id)
{
    $("#" + id).combobox();
    var vec = document.getElementsByTagName("INPUT");
    for (var i = 0; i < vec.length; i++)
    {
        if (vec[i].type == 'text')
        {
            if (vec[i].className == 'ui-autocomplete ui-corner-left') {
                vec[i].style.width = '80%';
                vec[i].className = 'autocomplete';
            }
        }
    }
}

function init_autocompleteAll()
{
    $("select").combobox();
    var vec = document.getElementsByTagName("INPUT");
    for (var i = 0; i < vec.length; i++)
    {
        if (vec[i].type == 'text')
        {
            if (vec[i].className == 'ui-autocomplete ui-corner-left') {
                vec[i].style.width = '80%';
                vec[i].className = 'autocomplete';
            }
        }
    }
}

function conf_autocomplete()
{
    (function ($) {
        $.widget("ui.combobox", {
            _create: function () {
                var self = this;
                var select = this.element.hide();
                var input = $("<input>")
                        .insertAfter(select)
                        .autocomplete({
                            source: function (request, response) {
                                var matcher = new RegExp(request.term, "i");
                                response(select.children("option").map(function () {
                                    var text = $(this).text();
                                    if (!request.term || matcher.test(text))
                                        return {
                                            id: $(this).val(),
                                            label: text.replace(new RegExp("(?![^&;]+;)(?!<[^<>]*)(" + request.term.replace(/([\^\$\(\)\[\]\{\}\*\.\+\?\|\\])/gi, "\\$1") + ")(?![^<>]*>)(?![^&;]+;)", "gi"), "<strong>$1</strong>"),
                                            value: text
                                        };
                                }));
                            },
                            delay: 0,
                            select: function (e, ui) {
                                if (!ui.item) {
                                    // remove invalid value, as it didn't match anything
                                    $(this).val("");
                                    return false;
                                }
                                $(this).focus();
                                select.val(ui.item.id);
                                self._trigger("selected", null, {
                                    item: select.find("[value='" + ui.item.id + "']")
                                });
                            },
                            minLength: 0
                        })
                        .removeClass("ui-corner-all")
                        .addClass("ui-corner-left");
                $("<button>&nbsp;</button>")
                        .insertAfter(input)
                        .button({
                            icons: {
                                primary: "ui-icon-triangle-1-s"
                            },
                            text: false
                        }).removeClass("ui-corner-all")
                        .addClass("ui-corner-right ui-button-icon")
                        .position({
                            my: "left center",
                            at: "right center",
                            of: input,
                            offset: "-1 0"
                        }).css("top", "")
                        .click(function () {
                            // close if already visible
                            if (input.autocomplete("widget").is(":visible")) {
                                input.autocomplete("close");
                                return;
                            }
                            // pass empty string as value to search for, displaying all results
                            input.autocomplete("search", "");
                            input.focus();
                        });
            }
        });
    })(jQuery);
}

/*FIN AUTOCOMPLETE*/

/*TABS UI*/
function conf_tabs(tab, options)
{
    if (!tab & !options) {
        $(function () {
            $('#tabs').tabs({
                show: function (event, ui) {
                }});
        });
    } else {
        var tabs;
        var object = {};
        if ($.isPlainObject(tab) & !options) {
            tabs = '#tabs';
            tab.show = function (event, ui) {
            };
            object = tab;
        } else if (options) {
            options.show = function (event, ui) {
            };
            object = options;
        }

        if (jQuery.type(tab) === 'string') {
            tabs = tab;
            object.show = function (event, ui) {
            };
        }
        $(tabs).tabs(object);
    }
}


function init_tabs(titTabs, idsTabs, urlTabs, ret)
{
    var onclck = '';
    var urlTab = '';
    var htmltb = '<div id = "tabs" >';
    htmltb += '<ul id="ul_tabs">';
    for (var i = 0; i < titTabs.length; i++)
    {
        if (urlTabs[i].substring(0, 1) == 'C')
            urlTab = Rutac + urlTabs[i].substring(1);
        else if (urlTabs[i].substring(0, 1) == 'V')
            urlTab = Rutav + urlTabs[i].substring(1);
        onclck = 'onclick = \'htmlContent("' + idsTabs[i] + '","' + urlTab + '");\'';
        htmltb += '<li><a ' + 'id = "href' + idsTabs[i] + '" href="#' + idsTabs[i] + '" ' + onclck + ' >' + titTabs[i] + '</a></li>';
    }
    htmltb += '</ul>';
    for (var i = 0; i < titTabs.length; i++)
    {
        htmltb += '<div id = "' + idsTabs[i] + '">';
        htmltb += '</div>';
    }
    htmltb += '</div>';
    if (ret)
        return htmltb;
    else
        document.write(htmltb);
}


function init_tabsII(titTabs, idsTabs, urlTabs, ret, delSpan)
{
    var onclck = '';
    var urlTab = '';
    var htmltb = '<div id = "tabs" >';
    htmltb += '<ul id="ul_tabs">';
    var htmlSpan = '';
    for (var i = 0; i < titTabs.length; i++)
    {
        if (urlTabs[i].substring(0, 1) == 'C')
            urlTab = Rutac + urlTabs[i].substring(1);
        else if (urlTabs[i].substring(0, 1) == 'V')
            urlTab = Rutav + urlTabs[i].substring(1);
        onclck = 'onclick = \'htmlContent("' + idsTabs[i] + '","' + urlTab + '");\'';
        htmlSpan = '';
        if (delSpan[i])
        {
            htmlSpan = '<span class="ui-icon ui-icon-close" onclick=\'deleteTab("' + idsTabs[i] + '")\'>Remove Tab</span>'
        }
        htmltb += '<li><a ' + 'id = "href' + idsTabs[i] + '" href="#' + idsTabs[i] + '" ' + onclck + '  >' + titTabs[i] + '</a>' + htmlSpan + '</li>';
    }
    htmltb += '</ul>';
    for (var i = 0; i < titTabs.length; i++)
    {
        htmltb += '<div id = "' + idsTabs[i] + '">';
        htmltb += '</div>';
    }
    htmltb += '</div>';
    if (ret)
        return htmltb;
    else
        document.write(htmltb);
}


/*FIN TABS UI*/


/*TOOLTIP*/

var ajaxConection;
var urlTtt = '';
function inicializarEventos(vec)
{
    for (f = 0; f < vec.length; f++)
    {
        addEvent(vec[f], 'mouseover', mostrarToolTip, false);
        addEvent(vec[f], 'mouseout', ocultarToolTip, false);
        addEvent(vec[f], 'mousemove', actualizarToolTip, false);
    }
    var ele = document.createElement('div');
    ele.setAttribute('id', 'divmensaje');
    vec = document.getElementsByTagName('body');
    vec[0].appendChild(ele);
}

function mostrarToolTip(e)
{
    var ref;
    var d = document.getElementById("divmensaje");
    $('#divmensaje').show('slow');
    if (window.event)
        e = window.event;
    d.style.left = e.clientX + document.body.scrollLeft - 150;
    d.style.top = e.clientY + document.body.scrollTop + 22;
    if (window.event)
        ref = window.event.srcElement;
    else if (e)
        ref = e.target;
    recuperarServidorTooltip(ref.getAttribute('id'), e.srcElement.id);
}

function actualizarToolTip(e)
{
    if (window.event)
        e = window.event;
    var d = document.getElementById("divmensaje");
    d.style.left = e.clientX + document.body.scrollLeft - 150;
    d.style.top = e.clientY + document.body.scrollTop + 22;
}

function ocultarToolTip(e)
{
    var d = document.getElementById("divmensaje");
    d.style.visibility = "hidden";
}

function recuperarServidorTooltip(codigo, id)
{
    ajaxConection = crearXMLHttpRequest();
    ajaxConection.onreadystatechange = procesarEventos;
    ajaxConection.open('GET', Rutav + urlTtt + id, true);
    ajaxConection.send(null);
}

function procesarEventos()
{
    var d = document.getElementById("divmensaje");
    d.style.visibility = "visible";
    if (ajaxConection.readyState == 4)
        d.innerHTML = ajaxConection.responseText;
    else
        d.innerHTML = '<img src = ' + Rutav + '/vista/img/cargando5.gif';
}

function addEvent(elemento, nomevento, funcion, captura)
{
    if (elemento.attachEvent)
    {
        elemento.attachEvent('on' + nomevento, funcion);
        return true;
    } else if (elemento.addEventListener)
    {
        elemento.addEventListener(nomevento, funcion, captura);
        return true;
    } else
        return false;
}

function crearXMLHttpRequest()
{
    var xmlHttp = null;
    if (window.ActiveXObject)
        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
    else if (window.XMLHttpRequest)
        xmlHttp = new XMLHttpRequest();
    return xmlHttp;
}

/*FIN TOOLTIP*/

/*AREA INACTIVA*/

function printInactiveArea()
{
    document.write('<div id="inactive_area" style="z-index: 1000;position: absolute;display: none;background-color: #EFEFEF; filter: alpha (opacity=50);width:98%;height:98%" align="center"></div>');
}

function showHideInactiveArea(relative, top)
{
    if (get('inactive_area').style.display == 'block')
    {
        get('inactive_area').style.display = 'none';
    } else
    {
        get('inactive_area').style.display = 'block';
    }
}

/*FIN AREA INACTIVA*/

/*LOADING*/

function printLoading(position)
{
    document.write('<div id="loading" style="z-index: 1000;position: absolute;display: none;background-color: #333333; filter: alpha (opacity=50);width:100%" align="center"><img src = "' + Rutav + '/vista/img/loading_big.gif"><font style="font-weight:bold;" face="Verdana" color="#FFFFFF" size="3">Espere por favor</font></div>');
    if (position == 'top')
    {
        loading.style.left = 0;
        loading.style.top = 0;
    } else if (position == 'bottom')
    {
        loading.style.left = window.screen.width - 109;
        loading.style.top = window.screen.height - 109;
    } else
    {
        loading.style.left = window.screen.width / 2 - 100 / 2;
        loading.style.top = window.screen.height / 2 - position;
    }
}

function showHideLoading(relative, top)
{
    if (relative)
    {
        var e = event;
        if (top)
        {
            get('loading').style.top = e.clientY - top;
        } else
        {
            get('loading').style.top = e.clientY - 50;
        }
    }

    if (get('loading').style.display == 'block')
    {
        get('loading').style.display = 'none';
    } else
    {
        get('loading').style.display = 'block';
    }
}

/*FIN LOADING*/


/*FILTER TABLE*/

function conf_table(id)
{
// Initialise Plugin
    var options1 = {
        additionalFilterTriggers: [$('#quickfind')],
        clearFiltersControls: [$('#cleanfilters')],
        matchingRow:
                function (state, tr, textTokens) {
                    if (!state || !state.id) {
                        return true;
                    }
                    var val = tr.children('td:eq(2)').text();
                    switch (state.id) {
                        default:
                            return true;
                    }
                }
    };
    $('#' + id).tableFilter(options1);
    styleText();
}

function styleText()
{
    var inputs = document.getElementsByTagName('INPUT');
    for (var i = 0; i < inputs.length; i++)
    {
        if (inputs[i].type == 'text')
        {
            inputs[i].className = 'text';
        }
    }
}

function showTrFilters()
{
    get('trFilter').style.display = 'block';
    var imgs = getByName('imgFilter');
    for (var i = 0; i < imgs.length; i++)
        imgs[i].src = Rutav + '/vista/img/filter.gif';
    get('trIcons').style.display = 'block';
}

/*FIN FILTER TABLE*/


/*SORTER TABLE*/
var color = '';
function conf_sorter(id, config, pager, noSorts)
{
    var table = get(id);
    if (!config)
        table.className = 'tablesorter';
    conf_heads(id, noSorts);
    $("#" + id).tablesorter({widthFixed: true}); //LA CONFIGURO SORTABLE

    if (pager)
    {
        $("#" + id).tablesorterPager({container: $("#pager")});
        var top = get('pager').style.top.replace('px', '');
        get('pager').style.top = (parseInt(top) + 3) + 'px';
    }

    if (!config)
    {
        table.border = '1px';
        table.style.borderStyle = 'solid';
        table.style.borderColor = '#CCCCCC';
        table.cellspacing = '1px';
        var filas = table.rows;
        for (var i = 0; i < filas.length; i++)
        {
            filas[i].onmouseover = cambiarColor;
            filas[i].onmouseout = cambiarColor;
        }
    }
}

function cambiarColor()
{
    if (this.bgColor == '#ffffc6')
        this.bgColor = color;
    else
    {
        color = this.bgColor;
        this.bgColor = '#ffffc6';
    }
}

function printPager()
{
    var pager = '<div id="pager" class="pager" align="left">';
    pager += '<form>';
    pager += '<img src="' + Rutav + '/vista/img/first.png" class="first" style="vertical-align:middle"/>';
    pager += '<img src="' + Rutav + '/vista/img/prev.png" class="prev" style="vertical-align:middle"/>';
    pager += '<input type="text" class="pagedisplay" />';
    pager += '<img src="' + Rutav + '/vista/img/next.png" class="next" style="vertical-align:middle"/>';
    pager += '<img src="' + Rutav + '/vista/img/last.png" class="last" style="vertical-align:middle"/>';
    pager += '&nbsp;<select class="pagesize" style="vertical-align:middle;">';
    pager += '<option selected="selected"  value="3">10</option>';
    pager += '<option value="20">20</option>';
    pager += '<option value="30">30</option>';
    pager += '<option  value="40">40</option>';
    pager += '</select>';
    pager += '</form>';
    pager += '</div>';
    document.write(pager);
}

function conf_heads(id, noSorts)
{
    var ths = get(id).getElementsByTagName('TH');
    var ind = noSorts.split('-'); //todos los indices de los th q no sera sortables     

    for (var i = 0; i < ind.length; i++)
    {
        ths[ ind[i] ].className = '{sorter: false}';
    }
}

/*FIN SORTER TABLE*/

/*MINIMIZAR*/
function minimize()
{
    self.blur();
    self.moveTo(10000, 10000);
    self.resizeTo(1, 1);
    self.blur();
}

/*FIN MINIMIZAR*/

function show_label(msg, container)
{
    get(container).innerHTML = "<label class='label_'>" + msg + "</label>";
}

function delSpaces(id__)
{
    get(id__).value = TrimLeft(get(id__).value);
    get(id__).value = TrimRight(get(id__).value);
}

function getLoading(tipe)
{
    if (!tipe)
        return '<img src = "' + Rutav + '/vista/img/ajax-loader.gif"/>&nbsp;&nbsp;<strong></strong>';
    else
        return '&nbsp;<img src = "' + Rutav + '/vista/img/spinner.gif"/>';
}

/*BUTTONS*/
function init_buttons()
{
    $("input:button,input:submit,input:reset,button", ".demo").button();
}
/**/

/*TREE*/

function conf_tree(id, draggable)
{
    var drag = false;
    if (draggable)
        drag = true;
    $("#" + id).tree({
        callback:
                {
                    onsearch:
                            function (n, t)
                            {
                                t.container.find('.search').removeClass('search');
                                n.addClass('search');
                            }
                },
        types: {
            // all node types inherit the "default" node type
            "default": {
                draggable: drag,
                deletable: false,
                renameable: false
            },
            "file": {
                icon: {
                    image: Rutav + "/vista/img/tree/edit-folder.png"
                }
            },
            "folder": {
                icon: {
                    image: Rutav + "/vista/img/tree/folderblue.png"
                }
            },
            "new": {
                icon: {
                    image: Rutav + "/vista/img/new.png"
                }
            }
        }
    });
}

function print_tree_search()
{
    var html = '';
    html += '<input type="text" id="txtsrh" onkeypress = "return validFormat(this,event,\'Mayus\')" class="text"/>';
    html += '<input type="button" onclick="$.tree.focused().search($(this).prev().val())" value="Filtrar" style="height: 18px;font-size: 9px;"/>';
    document.write(html);
}

/*FIN TREE*/

/*AJAX SETUP LANZA EL ERROR*/
function ajax_setup()
{
    $.ajaxSetup({
        error: function (event, request, settings) {
            alert("Ha ocurrido un error, Detalle: AJAX SETUP");
        }
    });
}
/**/

function selectValueCombo(id_combo, value__)
{
    var comboAux = get(id_combo);
    for (i = 0; i < comboAux.options.length; i++)
    {
        if (startWith(comboAux.options[i].value, value__))
        {
            comboAux.selectedIndex = i;
            return;
        }
    }
}

function startWith(cadena, subcadena)
{
    var codigo = "var patern = /^" + subcadena + "/; patern.test('" + cadena + "')";
    return (eval(codigo));
}

function selectValueCombo(comboAux, value__)
{
    for (i = 0; i < comboAux.options.length; i++)
    {
        if (startWith(comboAux.options[i].value, value__))
        {
            comboAux.selectedIndex = i;
            return;
        }
    }
}

/*FUNCIONES MAURICIO 12/03/10*/

// ***************************************************************************** -->
// ******  Funciones de Validacion de campos requeridos en un formularios ******* -->
// ***************************************************************************** -->
function validRequiredAllFields(form)
{
    var i = 0, j = 0, exito = true, valor = '';
    var k = 0;
    var arraChk = null;
    var verify = false;
    var strValid = ""; // cadena que guardara los resultados de la validacion
    var countElements = 0; // para saber si hay elementos que no sean casillas que se necesiten digitar

    for (i = 0; i < form.elements.length; i++)
    {
        if ((form.elements[i].type == 'select-one' ||
                form.elements[i].type == 'text' ||
                form.elements[i].type == 'password' ||
                form.elements[i].type == 'textarea' ||
                form.elements[i].type == 'file' ||
                form.elements[i].type == 'checkbox' ||
                form.elements[i].type == 'radio'
                )
                &&
                form.elements[i].obligatorio
                )
        {

            if (form.elements[i].type == 'radio' || form.elements[i].type == 'checkbox')
            {
                arraChk = getByName(form.elements[i].name);
                verify = false;
                for (k = 0; k < arraChk.length; k++)
                {
                    if (arraChk[k].checked)
                    {
                        verify = true;
                        break;
                    }
                }

                if (!verify)
                {
                    strValid += "<br>&nbsp;-Seleccionar una opcion de la(s) casilla(s) de" + (form.elements[i].type == 'radio' ? " verificacion" : " chequeo") + "'" + form.elements[i].title + "'";
                    /*try
                     {
                     //form.elements[i].focus();
                     }
                     catch(theException){}*/
                    exito = false;
                }
            } else
            {
                valor = Trim(form.elements[i].value);
                if (form.elements[i].type == 'select-one')
                {
                    if (valor == '' || valor.length == 0 || valor == '-1' ||
                            valor.toLowerCase() == 'SELECCIONE' || valor.toLowerCase() == 'SELECCIONE..' ||
                            valor.toLowerCase() == 'SELECCIONA' || valor.toLowerCase() == 'SELECCIONAR..'
                            )
                    {
                        strValid += "<br>&nbsp;-Escoja un opcion de la lista de despliegue '" + form.elements[i].title + "'";
                    }
                    exito = false;
                } else
                {
                    if ((valor == '') || (valor.length == 0))
                    {
                        //alert('Por favor complete información.'+ '\n' + 'Ingrese ' + form.elements[i].title);
                        form.elements[i].style.backgroundColor = "yellow";
                        exito = false;
                        countElements++;
                        /*try{
                         form.elements[i].focus();
                         }catch(theException){}*/
                        //return false;
                    }
                }
            }
            // Valido E mail - Fechas - Horas
            /*if ( ( !sw ) && ( form.elements[i].type == 'text' ) ){
             sw = true;
             if(form.elements[i].alt == 'E.mail') sw = valEmail(form.elements[i].value);
             if(form.elements[i].alt == 'Date'  ) sw = valFecha(form.elements[i].value);
             if(form.elements[i].alt == 'Hour'  ) sw = valHour (form.elements[i].value);
             if ( !sw ){
             try{
             form.elements[i].focus();
             }catch(theException){}
             return sw;
             }
             }*/
        }// end if
    }// end for
    if (!exito)
    {
        strValid = "Por favor debe completar la siguiente información:" + strValid;
        if (countElements > 0)
        {
            strValid += "<br>- Los campos sobreados con color Amarillo";
        }
        get("div_results_valid").style.display = "block";
        show_label(strValid, "div_results_valid");
        document.location.href = "#ancla_div_results";
    }
    return exito;
}

function setRequired(campos)
{
    var arrCampos = campos.split(",");
    for (i = 0; i < arrCampos.length; i++)
    {
        get(arrCampos[i]).obligatorio = "si";
    }
}

function printAlert()
{
    document.write('<a name="ancla_div_results"></a><div id="div_results_valid" style="width:550; height:100px; overflow:auto; display: none; background-color:white; border:solid 1px"></div>');
}


function set(id_campo, valor)
{
    get(id_campo).value = valor;
}

function setDataFrame(iframe_name, form_name, campo_name, value__)
{
    window.frames[iframe_name].document.forms[form_name].elements[campo_name].value = value__;
}

function selectValueCombo(id_combo, value__)
{
    var comboAux = get(id_combo);
    for (i = 0; i < comboAux.options.length; i++)
    {
        if (startWith(comboAux.options[i].value, value__))
        {
            comboAux.selectedIndex = i;
            return;
        }
    }
}

function startWith(cadena, subcadena)
{
    var codigo = "var patern = /^" + subcadena + "/; patern.test('" + cadena + "')";
    return (eval(codigo));
}

function selectValue_Combo(comboAux, value__)
{
    for (i = 0; i < comboAux.options.length; i++)
    {
        if (startWith(comboAux.options[i].value, value__))
        {
            comboAux.selectedIndex = i;
            return;
        }
    }
}

function changeTildeBd(cadena)
{
    var strAux = cadena;
    strAux = strAux.replace("&aacute", "�");
    strAux = strAux.replace("&Aacute", "�");
    strAux = strAux.replace("&eacute", "�");
    strAux = strAux.replace("&Eacute", "�");
    strAux = strAux.replace("&iacute", "�");
    strAux = strAux.replace("&Iacute", "�");
    strAux = strAux.replace("&oacute", "�");
    strAux = strAux.replace("&Oacute", "�");
    strAux = strAux.replace("&uacute", "�");
    strAux = strAux.replace("&Uacute", "�");
    strAux = strAux.replace("&ntilde", "�");
    strAux = strAux.replace("&Ntilde", "�");
    return strAux;
}

function changeFormat(format_, cadena)
{
    if (format_ == 'Mayus')
    {
        return cadena.toUpperCase();
    } else if (format_ == 'Minus')
    {
        return cadena.toLowerCase();
    } else if (format_ == 'FMayus')
    {
        var partes = Trim(cadena).replace("  ", " ").split(" ");
        var strAux = "";
        if (partes == cadena)
        {
            strAux += cadena.substring(0, 1).toUpperCase();
            strAux += cadena.substring(1).toLowerCase();
        } else
        {
            for (i = 0; i < partes.length; i++)
            {
                strAux += partes[i].substring(0, 1).toUpperCase();
                if (partes[i].length > 1)
                {
                    strAux += partes[i].substring(1).toLowerCase();
                }
                strAux += " ";
            }
        }
        return Trim(strAux);
    }
}


function getSelectedText()
{
    var txt = "";
    var selectedText = document.selection;
    if (selectedText.type == 'Text')
    {
        var newRange = selectedText.createRange();
        txt = newRange.text;
    }

    return txt;
}

var nextPageGrid = 1;
var previuosPageGrid = 0;
var lastPageGrid = 0;
function paginationGrid(option)
{
    if (option == "previous")
    {
        $("tr[name='page_" + nextPageGrid + "']").hide();
        $("tr[name='page_" + previuosPageGrid + "']").show();
        nextPageGrid = previuosPageGrid;
        previuosPageGrid--;
        get("href_next").disabled = false;
        get("href_last").disabled = false;
        if (previuosPageGrid == 0)
        {
            get("href_previous").disabled = true;
            get("href_first").disabled = true;
        }
    }

    if (option == "next")
    {
        previuosPageGrid = nextPageGrid;
        nextPageGrid++;
        $("tr[name='page_" + previuosPageGrid + "']").hide();
        $("tr[name='page_" + nextPageGrid + "']").show();
        get("href_previous").disabled = false;
        get("href_first").disabled = false;
        if (nextPageGrid == lastPageGrid)
        {
            get("href_next").disabled = true;
            get("href_last").disabled = true;
        }
    }

    if (option == "last")
    {
        previuosPageGrid = nextPageGrid;
        nextPageGrid = lastPageGrid;
        $("tr[name='page_" + previuosPageGrid + "']").hide();
        $("tr[name='page_" + nextPageGrid + "']").show();
        get("href_previous").disabled = false;
        get("href_first").disabled = false;
        get("href_next").disabled = true;
        get("href_last").disabled = true;
        previuosPageGrid = nextPageGrid - 1;
    }

    if (option == "first")
    {
        previuosPageGrid = nextPageGrid;
        nextPageGrid = 1;
        $("tr[name='page_" + previuosPageGrid + "']").hide();
        $("tr[name='page_" + nextPageGrid + "']").show();
        get("href_previous").disabled = true;
        get("href_first").disabled = true;
        get("href_next").disabled = false;
        get("href_last").disabled = false;
        previuosPageGrid = 0;
    }

    return false;
}

function queryAjax(url__, params__, success__, type__, async__)
{
    var arrAux = new Array();
    $.ajax({
        type: (type__ ? type__ : 'post'),
        url: url__,
        async: (async__ ? async__ : true),
        data: params__,
        success: function (responseText)
        {
            /* arrAux[0] = responseText;
             alert(arrAux[0])
             if( success__ )
             eval(success__+"("+ arrAux +");");
             */
            $("#div_result").html(responseText);
        }
    });
}

/*HABILITAR DESHABILITAR BOTONES DE UN PANEL*/

function getDialogButton(dialog_selector, button_name)
{
    var buttons = $(dialog_selector + ' .ui-dialog-buttonpane button');
    for (var i = 0; i < buttons.length; ++i)
    {
        var jButton = $(buttons[i]);
        if (jButton.text() == button_name)
        {
            return jButton;
        }
    }
    return null;
}

function disable_button(dialogClass, nameButton)
{
    var button = getDialogButton(dialogClass, nameButton);
    if (button)
    {
        button.attr('disabled', true).addClass('ui-state-disabled');
        //button.unbind(); 	     
    }
}

function enable_button(dialogClass, nameButton)
{
    var button = getDialogButton(dialogClass, nameButton);
    if (button)
    {
        button.attr('disabled', false).removeClass('ui-state-disabled');
    }
}

// =========================================================================================   -->
//          Pintar el  borde de un campo cuando obtiene el foco - no combobox   - 27 mayo 2010 -->
// =========================================================================================   -->

function paintBorderFields(color, tpofld)
{
    var vectTypes = tpofld.split(',');
    for (i = 0; i < vectTypes.length; i++)
    {
        $(document).find(':' + vectTypes[i]).each(function ()
        {
            $(this).focus(function ()
            {
                $(this).css('border', 'solid 1px ' + color);
                if ($(this).focus)
                    eval('' + $(this).focus)
            })

            $(this).blur(function ()
            {

                if ($(this).attr('tagName') == 'INPUT' && $(this).attr('type') == 'radio' || $(this).attr('type') == 'checkbox')
                {
                    $(this).css('border', 'none');
                } else
                    $(this).css('border', 'solid 1px #000000');
                if ($(this).blur)
                    eval('' + $(this).focus)
            })
        })
    }
}

/*FIN HABILITAR DESHABILITAR BOTONES DE UN PANEL*/

// ============================================================================== -->
// ============ Serializar y codificar formularios 20 junio 2010	 ============== -->
// ============================================================================== -->

function serializar(id_form, form)
{
    var formAux = id_form != "" ? $("#" + id_form) : form;
    var arrRst = formAux.serialize().split("&");
    var arrAux;
    var strAux = "";
    for (var i = 0; i < arrRst.length; i++)
    {
        arrAux = arrRst[i].split("=");
        strAux += "&" + arrAux[0] + "=" + encodeURIComponent(arrAux[1]);
    }
    strAux = strAux.substring(1);
    return strAux;
}



// =========================================================================================  -->
//   10 JUNIO 2010                                                                            -->
//   Quitar o Aplicar formato de numeros a campos de texto que tengan el                      -->
//   atributo format_num con el valor  fmtnum  -                                              -->
//   option = 1 -> Aplicar formato                                                            -->
//   option = 2 -> Quitar formato                                                             -->
// =========================================================================================  -->
function applyFormatNumber(element_padre, option)
{
    var padre = element_padre || '';
    if (padre != '')
    {
        var arrElements = getByTypeInput("text", padre);
    } else
    {
        var arrElements = getByTypeInput("text");
    }


    for (var i = 0; i < arrElements.length; i++)
    {
        if (arrElements[i].format_num && arrElements[i].format_num == 'fmtnum')
        {
            if (option == 1)
                arrElements[i].value = formatNumber(arrElements[i].value);
            else if (option == 2)
                arrElements[i].value = unformatNumber(arrElements[i].value);
        }
    }
}


// =========================================================================================  -->
//   10 JUNIO 2010                                                                            -->
//    Aplicar formato de numeros a campos de texto que tengan el                              -->
//    atributo format_num con el valor  fmtnum                                                -->
//     cuando tengan el evento  onblur                                                        -->
// =========================================================================================  -->

function  onblurFormatNumber()
{
    var arrElements = getByTypeInput("text");
    for (var i = 0; i < arrElements.length; i++)
    {
        if (arrElements[i].format_num && arrElements[i].format_num == 'fmtnum')
        {
            arrElements[i].onblur = function ()
            {
                formatNumber(unformatNumber(this.value), this);
                if ($(this).blur)
                    eval('' + $(this).focus)
            }
        }
    }

}

// ============================================================================== -->
// ============		            Validar E.mail		     	     ============== -->
// ============================================================================== -->

function valEmail(strValor) {
    var email = Trim(strValor);
    var expReg = /^([a-zA-Z]){1}([a-zA-Z0-9\_\.-])*([a-zA-Z0-9\_])+(\@){1}(([a-zA-Z])+(\.){1}([a-zA-Z\.])*([a-zA-Z])+)$/;
    if (!expReg.test(email)) {
        show_message('Direccion de e-mail invalida', '', 'ERROR');
        return false;
    }

    return true;
}

// ============================================================================== -->
// ============ Maximizar ventana 19  junio 2010            	      ============== -->
// ============================================================================== -->

function maxWindow() {
    top.window.moveTo(0, 0);
    if (document.all) {
        top.window.resizeTo(screen.availWidth, screen.availHeight);
    } else if (document.layers || document.getElementById) {
        if (top.window.outerHeight < screen.availHeight || top.window.outerWidth < screen.availWidth) {
            top.window.outerHeight = screen.availHeight;
            top.window.outerWidth = screen.availWidth;
        }
    }
}


// ============================================================================== -->
// ============              Control de acceso a botones           ============== -->
// ============================================================================== -->
function access_control(control, nomacc, nomdbf, jqueryClass, tipeControl)
{
    var url = Rutac + '/Adm/VerifyAccess';
    var events = 'VERIFY';
    var pars = 'nomacc=' + nomacc + '&nomdbf=' + nomdbf + '&events=' + events;
    $.ajax({
        type: 'post',
        url: url,
        async: false,
        data: pars,
        success: function (response)
        {
            if (response == 'disabled')
            {
                if (jqueryClass)
                    disable_button(jqueryClass, control);
                else
                if (tipeControl == 'Link')
                {
                    get(control).style.display = 'none';
                } else
                    get(control).disabled = true;
            }
        }
    });
}

//Dar formato a multiples cajas de texto dependiendo de la cadena jquery enviada
function formatTexts(id_obj, strFind)
{
    $('#' + id_obj).find(strFind).each
            (
                    function ()
                    {
                        formatNumber(unformatNumber($(this).attr('value')), get($(this).attr('id')), '$');
                    }
            );
}
//Quitar formato a multiples cajas de texto dependiendo de la cadena jquery enviada
function unformatTexts(id_obj, strFind)
{
    $('#' + id_obj).find(strFind).each
            (
                    function ()
                    {
                        $(this).attr('value', unformatNumber($(this).attr('value')));
                    }
            );
}

/*METODOS DE CONFIGURACION DE PREGUNTAS DE ACIERTO*/

function conf_qst(cadscv, nroqst)
{
    var vecscv = cadscv.split('  |-|  ');
    var nroscv = '';
    var dragId = '';
    var dropId = '';
    for (var i = 0; i < vecscv.length; i++)
    {
        dragId = 'draggable_' + vecscv[i];
        dropId = 'droppable_' + vecscv[i];
        $("#" + dragId).draggable
                ({
                    helper: 'clone',
                    opacity: 0.7,
                    drag: function (event, ui)
                    {
                        get('objdrg').value = $(this).html();
                        get('objdrg').alt = $(this).attr('color');
                        get('objdrg').codigo = $(this).attr('id').split('_')[1];
                    }
                });
        $("#" + dropId).droppable
                ({
                    activeClass: 'ui-state-hover',
                    hoverClass: 'ui-state-active',
                    drop: function (event, ui)
                    {
                        //$(this).addClass('ui-state-highlight').find('p').html('Dropped!');
                        if (!isUsed(get('objdrg').value, nroqst))
                        {
                            $(this).html(get('objdrg').value);
                            nroscv = $(this).attr('id').split('_')[1];
                            $('#arransQAC' + nroscv).attr('value', get('objdrg').codigo)
                            $(this).css('background', get('objdrg').alt);
                        }
                    }
                });
    }
}

function isUsed(valor, nroqst)
{
    var flag = false;
    $('#qstDiv_' + nroqst).find('div').each
            (function ()
            {
                if ($(this).attr('isDroppable'))
                {
                    if (Trim($(this).html()) == Trim(valor))
                    {
                        flag = true;
                    }
                }
            }
            );
    return flag;
}

function dropAnswer(idDiv)
{
    $('#' + idDiv).html("");
    get(idDiv).style.backgroundColor = "";
}

function printQuestion(nomqst, cadqst, cadans, cadscv, nroqst)
{
    var vecqst = cadqst.split(' |-|  ');
    var vecans = cadans.split('  |-|  ');
    var vecscv = cadscv.split('  |-|  ');
    var html = '<div id="qstDiv_' + nroqst + '">';
    html += '<table width="100%" border="1" class="simple" align="center">';
    //desordeno vector
    desorden(vecans);
    html += '<tr>';
    html += '<td colspan="4" class="caption">';
    html += nomqst;
    html += '</td>';
    html += '</tr>';
    for (var i = 0; i < vecqst.length; i++)
    {
        html += '<tr>' +
                '<td>' +
                vecqst[i] +
                '</td>' +
                '<td align="center">' +
                '<div id="draggable_' + vecscv[i] + '" align="center" class="dragdrop" style="background-color: ' + vecclr[i] + '" color="' + vecclr[i] + '">' +
                (i + 1) +
                '</div>' +
                '</td>' +
                '<td align="center">' +
                '<div id="droppable_' + vecscv[i] + '"  align="center" class="dragdrop" isDroppable="true">' +
                '</div>' +
                '</td>' +
                '<td align="right">' +
                '<a href="javascript:dropAnswer(\'droppable_' + vecscv[i] + '\')">' + vecans[i] + '</a>' +
                '<input name="arransQAC' + vecscv[i] + '" id="arransQAC' + vecscv[i] + '" type="hidden" value="">'
        '</td>' +
                '</tr>';
    }
    html += '</table>';
    html += '</div>';
    html += '<input type="hidden" name="objdrg" id="objdrg" alt="" codigo="">';
    document.write(html);
}

//Desordenar vector
function desorden(arr)
{
    for (a = 1; a <= arr.length - 1; a++)
    {
        quickDeSort(arr, 0, a);
    }
    for (a = 0; a < arr.length - 1; a++)
    {
        quickDeSort(arr, a, arr.length - 1);
    }
}
function quickDeSort(objArray, ini, fin)
{
    var i = ini;
    var j = fin;
    var tmp;
    do
    {
        tmp = objArray[i];
        objArray[i] = objArray[j];
        objArray[j] = tmp;
        if (i <= j)
        {
            i++;
            j--;
        }
    } while (i <= j);
    if (ini < j)
    {
        quickDeSort(objArray, ini, j);
    }
    if (i < fin)
    {
        quickDeSort(objArray, i, fin);
    }
}

//CONFIGURAR TABLA CON SCROLL
function conf_scrollTable(id, width_, height_, clsNme)
{
    $('#' + id).tableScroll
            ({
                //width         : width_,
                height: height_,
                containerClass: clsNme
            });
}

function getSeparator()
{
    return '<img src="' + Rutav + '/vista/img/line.png" border="0" height="1px" width="100%">'
}


// ***************************************************************************** -->
// ****************** Abre una ventana en pantalla completa        ************* -->
// ***************************************************************************** -->
function fullScreen(urlnxt)
{
    window.open(urlnxt, '', 'fullscreen=yes, scrollbars=yes');
}

// ***************************************************************************** -->
// ****************** Setea la variable [dateServer]             *************** -->
// ***************************************************************************** -->
function setDateServer(pdateServer) {
    dateServer = pdateServer;
}
// ***************************************************************************** -->
// ****************** Muestra la Fecha y Hora actual del Servidor  ************* -->
// ***************************************************************************** -->
function getTimeServer(fchcls, maxmin, nameForm, url) {
    var Fecha = new Date();
    var timeTmp = 0;
    var cHour = '', cMin = '', cSeg = '', cNewFec = '';
    var cDay = '', cMonth = '', cYear = '', cFecha = '';
    if (dateServer != '') {
        cFecha = dateServer;
        cMonth = cFecha.substring(0, cFecha.indexOf('/'));
        cFecha = cFecha.substring(cFecha.indexOf('/') + 1);
        cDay = cFecha.substring(0, cFecha.indexOf('/'));
        cFecha = cFecha.substring(cFecha.indexOf('/') + 1);
        cYear = cFecha.substring(0, cFecha.indexOf(' '));
        if (!swTime) {
            cFecha = cFecha.substring(cFecha.indexOf(' ') + 1);
            cHour = cFecha.substring(0, cFecha.indexOf(':'));
            cFecha = cFecha.substring(cFecha.indexOf(':') + 1);
            cMin = cFecha.substring(0, cFecha.indexOf(':'));
            cFecha = cFecha.substring(cFecha.indexOf(':') + 1);
            cSeg = cFecha.substring(0, cFecha.length);
        }

        if (cMonth.substring(0, 1) == '0')
            cMonth = cMonth.substring(1, cMonth.length);
        if (cDay.substring(0, 1) == '0')
            cDay = cDay.substring(1, cDay.length);
        if (!swTime) {
            if (cHour.substring(0, 1) == '0')
                cHour = cHour.substring(1, cHour.length);
            if (cMin.substring(0, 1) == '0')
                cMin = cMin.substring(1, cMin.length);
            if (cSeg.substring(0, 1) == '0')
                cSeg = cSeg.substring(1, cSeg.length);
        }

        Fecha.setYear(parseInt(cYear));
        Fecha.setMonth((parseInt(cMonth) - 1));
        Fecha.setDate(parseInt(cDay));
        if (!swTime) {
            Fecha.setHours(parseInt(cHour));
            Fecha.setMinutes(parseInt(cMin));
            Fecha.setSeconds(parseInt(cSeg));
            nHorCmtr = 0;
            nMinCmtr = 0;
            nSegCmtr = 0;
            swTime = true;
        } else {
            Fecha.setHours(nHor);
            Fecha.setMinutes(nMin);
            Fecha.setSeconds(nSeg);
        }

        nAnn = Fecha.getFullYear();
        nMes = Fecha.getMonth() + 1;
        nDia = Fecha.getDate();
        nHor = Fecha.getHours();
        nMin = Fecha.getMinutes();
        nSeg = Fecha.getSeconds();
        if (nMes < 10)
            nMes = '0' + nMes;
        if (nDia < 10)
            nDia = '0' + nDia;
        if (nHor < 10)
            nHor = '0' + nHor;
        if (nMin < 10)
            nMin = '0' + nMin;
        if (nSeg < 10)
            nSeg = '0' + nSeg;
        if (nHorCmtr < 10)
            nHorCmtr = '0' + nHorCmtr;
        if (nMinCmtr < 10)
            nMinCmtr = '0' + nMinCmtr;
        if (nSegCmtr < 10)
            nSegCmtr = '0' + nSegCmtr;
        window.status = 'FECHA Y HORA DEL SERVIDOR [' + nDia + '/' + nMes + '/' + nAnn + '  ' + nHor + ':' + nMin + ':' + nSeg +
                ']      TIEMPO TRANSCURRIDO [' + nHorCmtr + ':' + nMinCmtr + ':' + nSegCmtr + ']';
        if ((fchcls) && (nameForm) && (url)) {
            if (nHor > 12) {
                timeTmp = nHor - 12;
                if (timeTmp < 10)
                    timeTmp = '0' + timeTmp;
                cNewFec = timeTmp + ':' + nMin + ' ' + 'PM';
            } else {
                cNewFec = timeTmp + ':' + nMin + ' ' + 'AM';
            }

            if (maxmin == cntmin) {
                sendForm(nameForm, url);
                return;
            }

            if (fchcls == cNewFec) {
                sendForm(nameForm, url);
                return;
            }

            if (document.getElementById('layTime') != null)
                document.getElementById('layTime').innerHTML = '<div align="center"><label class="letraDisplay">TIEMPO TRANSCURRIDO</label> <label class="letra">[' + nHorCmtr + ':' + nMinCmtr + ':' + nSegCmtr + ']</label></div>';
        } else {
            if (document.getElementById('layTime') != null)
                document.getElementById('layTime').innerHTML = '<div align="center"><label class="letraDisplay">TIEMPO TRANSCURRIDO</label> <label class="letra">[' + nHorCmtr + ':' + nMinCmtr + ':' + nSegCmtr + ']</label></div>';
        }

        nSeg++;
        if (nSeg > 59) {
            nSeg = 0;
            nMin++;
            if (nMin > 59) {
                nMin = 0;
                nHor++;
                if (nHor > 23) {
                    nHor = 0;
                }
            }
        }

        nSegCmtr++;
        if (nSegCmtr > 59) {
            nSegCmtr = 0;
            nMinCmtr++;
            cntmin++;
            if (nMinCmtr > 59) {
                nMinCmtr = 0;
                nHorCmtr++;
                if (nHorCmtr > 23) {
                    nHorCmtr = 0;
                }
            } else {
                nHorCmtr = '' + nHorCmtr;
                if (nHorCmtr.substring(0, 1) == '0')
                    nHorCmtr = nHorCmtr.substring(1, nHorCmtr.length);
                nHorCmtr = parseInt(nHorCmtr);
            }
        } else {
            nMinCmtr = '' + nMinCmtr;
            nHorCmtr = '' + nHorCmtr;
            if (nMinCmtr.substring(0, 1) == '0')
                nMinCmtr = nMinCmtr.substring(1, nMinCmtr.length);
            if (nHorCmtr.substring(0, 1) == '0')
                nHorCmtr = nHorCmtr.substring(1, nHorCmtr.length);
            nMinCmtr = parseInt(nMinCmtr);
            nHorCmtr = parseInt(nHorCmtr);
        }

        if ((fchcls) && (nameForm) && (url))
            setTimeout("getTimeServer('" + fchcls + "'," + maxmin + ",'" + nameForm + "','" + url + "')", 1001);
        else
            setTimeout('getTimeServer()', 1001);
    }
}

//Agregar y quitar class jquery UI
function addClassError(id)
{
    var imgErr = '<img src="' + Rutav + '/vista/img/Warning.png" border="0" >&nbsp;';
    $('#' + id).addClass('ui-state-error');
    $('#' + id).html(imgErr + $('#' + id).html());
}

function quitClassError(id)
{
    $('#' + id).removeClass('ui-state-error');
}

/*limpiar espacio html que contenga className ui-state-error*/
function clearError(obj)
{
    quitClassError(obj.id);
    $(obj).html('');
}

/*Mostrar label de error con style jQuery y asignar foco*/
function showLabelError(id, message)
{
    $('#' + id).html(message);
    $('#' + id).focus();
    addClassError(id);
}

//Agregar icono a un boton jquery
function setIcon(nmeBtn, classIcon)
{
    $('.ui-dialog-buttonpane').find('button:contains("' + nmeBtn + '")').button
            ({
                icons:
                        {
                            primary: 'ui-icon' + classIcon,
                            secondary: 'ui-icon ui-icon-grip-dotted-vertical'
                        }
            });
}

function serializar(id_form, form)
{
    var formAux = id_form != "" ? $("#" + id_form) : form;
    var arrRst = formAux.serialize().split("&");
    var arrAux;
    var strAux = "";
    for (var i = 0; i < arrRst.length; i++)
    {
        arrAux = arrRst[i].split("=");
        strAux += "&" + arrAux[0] + "=" + encodeURIComponent(arrAux[1]);
    }
    strAux = strAux.substring(1);
    return strAux;
}

























///////////////////////////////////////////////////////////////  FUNCIONES SMA_FUNCIONES   :JP ///////////////////////////////////////////////////////////////


function show_confirmDos(msg, callbackA, callbackC)
{
    var width_ = msg.length * 9;
    if (width_ > 600)
        width_ = 600;
    var cerrarA = true;
    var cerrarC = true;
    $("#dialog-confirm").remove();
    nuevaVentana('dialog-confirm', 'Información', '', '');
    document.getElementById('dialog-confirm').innerHTML = msg;
    $("#dialog-confirm").dialog("option", "buttons", {
        'Aceptar': function (event, ui) {
            cerrarA = false;
            $(this).dialog('close');
        },
        'Cancelar': function () {
            cerrarC = false;
            $(this).dialog('close');
        }
    });
    $("#dialog-confirm").on("dialogclose", function (event, ui) {
        if (callbackA && !cerrarA)
            eval(callbackA);
        else if (callbackC && !cerrarC)
            eval(callbackC);
    });
    $("#dialog-confirm").dialog("open");
    /*$("#dialog-confirm").dialog({
     resizable: false,
     modal: true,
     title: '¿Seguro de aplicar la operación?',
     buttons: {
     'Cancelar': function() {
     cerrarC = false;	
     $(this).dialog('close');
     
     },
     'Aceptar': function(event, ui) {
     cerrarA = false;
     $(this).dialog('close');				
     }            
     },
     close: function(event, ui) 
     {
     if(callbackA && !cerrarA)
     eval(callbackA);
     else if(callbackC && !cerrarC)
     eval(callbackC);
     },  
     width: width_,
     zIndex: 0  
     }); */
}

function show_confirmation(msg, callbackA, callbackC)
{
    var width_ = msg.length * 9;
    if (width_ > 600) {
        width_ = 600;
    }
    var cerrarA = true;
    var cerrarC = true;
    $("#dialog-confirm").remove();
    nuevaVentana('dialog-confirm', 'Confirmación', '', '');
    var iconHTML = '<td><img src="' + Rutav + '/vista/img/confirmation.png" width="40" height="40" ></td>';
    msg = '<td>' + msg + '</td>';
    document.getElementById('dialog-confirm').innerHTML = '<table><tr>' + iconHTML + msg + '</tr></table>';
    $("#dialog-confirm").dialog("option", "buttons", {
        'Aceptar': function (event, ui) {
            cerrarA = false;
            $(this).dialog('close');
        },
        'Cancelar': function () {
            cerrarC = false;
            $(this).dialog('close');
        }
    });
    $("#dialog-confirm").on("dialogclose", function (event, ui) {
        if (callbackA && !cerrarA) {
            if (jQuery.type(callbackA) === "function") {
                var funcion = callbackA;
                funcion();
//                eval(callbackA);
            } else {
                eval(callbackA);
            }

        } else if (callbackC && !cerrarC) {
            if (jQuery.type(callbackC) === "function") {
                var funcion = callbackC;
                funcion();
//                eval(callbackA);
            } else {
                eval(callbackC);
            }
        }
    });
    $("#dialog-confirm").dialog("open");
}



/*DATEPICKER*/
function conf_only_datepicker(id, months, minDate_, maxDate_, funcionClick, funcionCallback, img, textButton)
{

    if (!id)
        id = 'datepicker';
    if (!months || $.trim(img) === '')
        months = 1;
    if (!funcionClick)
        funcionClick = '';
    if (!funcionCallback)
        funcionCallback = '';
    if (!textButton || $.trim(textButton) === '')
        textButton = 'Seleccionar fecha';
    if (typeof minDate_ === "string" && minDate_ === "")
        minDate_ = null;
    else if (!minDate_)
        minDate_ = 'today';
    if (!maxDate_)
        maxDate_ = null;
    if (!img || $.trim(img) === '') {
        try {
            img = Rutav + "/vista/img/Date.png";
        } catch (e) {
            alert(e);
        }
    }

    $('#' + id).datepicker({
        showAnim: '',
        buttonImageOnly: true,
        showButtonPanel: true,
        buttonImageOnly : true,
                buttonText: textButton,
        showOn: "both",
        buttonImage: img,
        dateFormat: 'yy-mm-dd',
        minDate: minDate_,
        maxDate: maxDate_,
        changeMonth: true,
        yearRange: 'c-25:c+25',
        numberOfMonths: months,
        changeYear: true,
        onSelect: function () {
            eval(funcionClick);
        },
        onClose: function () {
            eval(funcionCallback);
        }
    }
    );
    return;
}

function conf_range_datepicker(idDesde, idHasta, img) {

    if (!img || $.trim(img) == '') {

        $("#" + idDesde).datepicker({
            changeMonth: true,
            changeYear: true,
            numberOfMonths: 1,
            dateFormat: 'yy-mm-dd',
            buttonText: 'Seleccionar fecha',
            onClose: function (selectedDate) {
                $("#" + idHasta).datepicker("option", "minDate", selectedDate);
            }
        });
        $("#" + idHasta).datepicker({
            changeMonth: false,
            changeYear: false,
            numberOfMonths: 1,
            dateFormat: 'yy-mm-dd',
            buttonText: 'Seleccionar fecha',
            onClose: function (selectedDate) {
                $("#" + idDesde).datepicker("option", "maxDate", selectedDate);
            }
        });
    } else {

        $("#" + idDesde).datepicker({
            changeMonth: false,
            changeYear: false,
            numberOfMonths: 1,
            showOn: "button",
            buttonImage: img,
            buttonImageOnly: true,
            dateFormat: 'yy-mm-dd',
            buttonText: 'Seleccionar fecha',
            onClose: function (selectedDate) {
                $("#" + idHasta).datepicker("option", "minDate", selectedDate);
            }
        });
        $("#" + idHasta).datepicker({
            changeMonth: false,
            changeYear: false,
            numberOfMonths: 1,
            showOn: "button",
            buttonImage: img,
            buttonImageOnly: true,
            dateFormat: 'yy-mm-dd',
            buttonText: 'Seleccionar fecha',
            onClose: function (selectedDate) {
                $("#" + idDesde).datepicker("option", "maxDate", selectedDate);
            }
        });
    }
}


function show_message(msg, callback, tipo) {
    // a workaround for a flaw in the demo system (http://dev.jqueryui.com/ticket/4375), ignore!

    var width_ = msg.length * 9;
    if (width_ > 600) {
        width_ = 600;
    }

    var iconHTML;
    var headerMsg;
    if (tipo === undefined || tipo === '') {
        headerMsg = 'Información !';
        iconHTML = '<td><img src="' + Rutav + '/vista/img/info.png" width="40" height="40" ></td>';
    } else if (tipo.toUpperCase() === 'ERROR') {
        headerMsg = 'Alerta !';
        iconHTML = '<td><img src="' + Rutav + '/vista/img/error.png" width="40" height="40" ></td>';
    } else if (tipo.toUpperCase() === 'INFO') {
        headerMsg = 'Información !';
        iconHTML = '<td><img src="' + Rutav + '/vista/img/info.png" width="40" height="40" ></td>';
    } else if (tipo.toUpperCase() === 'OK') {
        headerMsg = 'Información !';
        iconHTML = '<td><img src="' + Rutav + '/vista/img/ok.png" width="40" height="40" ></td>';
    }

    msg = '<td>' + msg + '</td>';
    $("#dialog-message").remove();
    nuevaVentana('dialog-message', headerMsg, '', '');
    document.getElementById('dialog-message').innerHTML = '<table><tr>' + iconHTML + msg + '</tr></table>';
    $("#dialog-message").dialog({
        buttons: {
            Ok: function () {
//                if (callback) {
//                    eval(callback);
//                }
                $("#dialog-message").dialog('close');
            }
        },
        close: function () {
            if (callback) {
                if ($.type(callback) === 'function')
                    callback();
                else
                    eval(callback);
            }
        },
        resizable: false,
        modal: true
    });
    $("#dialog-message").dialog("open");
}

var pkVentana = 0;
function nuevaVentana(Id, Titulo, ancho, alto) {

    if (!Id)
        Id = 'windows' + pkVentana++;
    if ($.trim(Id) === '')
        Id = 'windows' + pkVentana++;
    var div;
    if ($('#' + Id).size() < 1) {
        var body = document.getElementsByTagName('body');
        div = document.createElement('div');
        div.setAttribute('id', Id);
        body[0].appendChild(div);
    }

    try {
        if (Titulo)
            div.setAttribute('title', Titulo);
    } catch (e) {
        $('#' + Id).dialog("destroy");
    }

    if ($.trim(ancho) === '')
        ancho = 'auto';
    if ($.trim(alto) === '')
        alto = 'auto';
    $('#' + Id).dialog({
        autoOpen: false,
        show: {
            effect: "fadeIn",
            duration: 300
        },
        hide: {
            effect: "fadeOut",
            duration: 500
        },
        resizable: false,
        buttons: {
            Cancelar: function () {
                $(this).dialog('close');
                $(this).dialog('destroy');
                $(this).remove();
            }
        },
        position: 'center',
        draggable: false,
        zIndex: 900,
        modal: true,
        height: alto,
        width: ancho,
        minWidth: 400

    });
//    $('button[title="close"]').each(function() {
//        $(this).remove();
//    });
    return Id;
}

function conf_dialog(id, width_, height_, position_) {
    if (!id)
        id = 'dialog';
    try {
        $("#" + id).dialog("destroy");
    } catch (e) {
    }

    if (!width_)
        width_ = 'auto';
    if (!height_)
        height_ = 'auto';
    if (height_ === '100%' || $.trim(height_) === '')
        height_ = document.body.clientHeight - 25;
    if (width_ === '100%' || $.trim(width_) === '')
        width_ = document.body.clientWidth - 12;
    if (!position_ || $.trim(position_) === '')
        position_ = 'top';
    $("#" + id).dialog({
        autoOpen: false,
        show: {
            effect: "fade"
        },
        hide: {
            effect: "fade",
        },
        buttons: {
            Cancelar: function () {
                $(this).dialog('close');
            }
        },
        position: ['center', 50],
        draggable: false,
        resizable: false,
        closeOnEscape: false,
        modal: true,
        height: height_,
        width: width_
    });
}


function conf_tooltip() {
    $('#smaTooltip').remove();
    $("body").append('<div id="smaTooltip" class="tooltip    ui-widget-content  ui-corner-all" style="margin: 0px;height: auto;width:auto;position: absolute; width: auto;  padding: 10px; margin: 0 0 0 0;z-index: 1000;bottom: 100%;color: black;text-align: center; font-weight: bold;font-size: 11px; border-radius: 5px; box-shadow: 4px 4px 6px #999; -webkit-box-shadow: 4px 4px 6px #999;" >' +
            //'<table> <tr>'+
            //'<td valign=\"top\">'+
            '<div  class=" ui-widget-content    ui-dialog-titlebar ui-widget-header  " id="smaTooltipTitle"   style="clear: both;top:0px" >TITULO</div>' +
            //'</td>'+
            //'</tr><tr>'+
            //'<td valign=\"top\">'+
            '<div  id="smaTooltipContent" style="clear: both;top:0px" >CONTENIDO</div>' +
            //'</td>'+
            //'</tr><tr>'+
            //'<td valign=\"bottom\">'+
            //   '<div  id="smaTooltipFooter"class=\" ui-dialog-buttonpane ui-widget-content ui-helper-clearfix ui-dialog-titlebar ui-widget-header \" style="clear: both;height: auto;margin-bottom: 0px;" >Pie de tooltip</div>'+
            //'</td>'+
            //'</tr>'+
            //'</table>'+
            '</div>');
    $(".tooltip_elemento").hover(function (e) {

        $('#smaTooltipTitle').html(get_title_tooltip(this)); // SE DEBE SOBREESCRIBIR LA FUNCION  get_title_tooltip PARA COLOCARLE EL TITULO AL TOOLTIP	        
        $('#smaTooltipContent').html(get_content_tooltip(this)); // SE DEBE SOBREESCRIBIR LA FUNCION  get_content_tooltip PARA COLOCARLE CUERPO O CONTENIDO AL TOOLTIP
        var posicion = $(this).position();
        $('#smaTooltip').css("left", e.pageX);
        $('#smaTooltip').css("top", e.pageY);
        $('#smaTooltip').show();
    },
            function () {
                $('#smaTooltip').hide();
            })
    $(".tooltip_elemento").mousemove(function (e) {

        $('#smaTooltip').css("left", e.pageX);
        $('#smaTooltip').css("top", e.pageY);
    })

    $('#smaTooltip').tooltip();
}


// CONFIGURAR ACCORDION
function config_accordion(id, disabled_)
{

    try {
        $("#" + id).accordion("destroy");
    } catch (e) {
        // TODO: handle exception
    }

    $("#" + id).accordion({
        autoHeight: false,
        navigation: true,
        fillSpace: false,
        collapsible: true,
        disabled: disabled_
    });
}


// Cambiar idioma del calendario
function change_datepicker_es() {
    $.datepicker.regional['es'] = {
        closeText: 'Cerrar',
        prevText: '&#x3c;Ant',
        nextText: 'Sig&#x3e;',
        currentText: 'Hoy',
        monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
            'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
        monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun',
            'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
        dayNames: ['Domingo', 'Lunes', 'Martes', 'Mi&eacute;rcoles', 'Jueves', 'Viernes', 'S&aacute;bado'],
        dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mi&eacute;', 'Juv', 'Vie', 'S&aacute;b'],
        dayNamesMin: ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'S&aacute;'],
        weekHeader: 'Sm',
        dateFormat: 'yy-mm-dd',
        firstDay: 1,
        isRTL: false,
        showMonthAfterYear: false,
        yearSuffix: ''};
    $.datepicker.setDefaults($.datepicker.regional['es']);
}


/** FIN DE LAS NUEVAS FUNCIONES DE GUARDAR **/

/** FUNCION PARA SELEC DEL NUEVO CONFIG GRID **/
function gridControlSelectRow(tr, color, idTabla) {

    //alert($('#'+idTabla+ ' tr').size());
    $('#' + idTabla + ' tr').each(function () {
        //if($.trim($(this).css('backgroundColor'))=='rgb(250,250,170)'){
        $(this).css('backgroundColor', $(this).attr('title'));
        $(this).removeClass(idTabla + 'RowSelect');
        //}
    });
    //$(tr).attr('title',$(tr).css('backgroundColor'));
    $(tr).css('backgroundColor', 'rgb(250,250,170)');
    $(tr).addClass(idTabla + 'RowSelect');
}

function rgbToHex(R, G, B) {
    return toHex(R) + toHex(G) + toHex(B);
}
function toHex(n) {
    n = parseInt(n, 10);
    if (isNaN(n))
        return "00";
    n = Math.max(0, Math.min(n, 255));
    return "0123456789ABCDEF".charAt((n - n % 16) / 16)
            + "0123456789ABCDEF".charAt(n % 16);
}
/** FIN FUNCIONES  PARA SELEC DEL NUEVO CONFIG GRID **/



/** INICIO FUCIONES PARA CONTABILIDAD **/

function getAccountingMovimientsDetails(nrotrn, id, tipe) {

    var url = Rutac + '/Adm/AccountingMovements';
    $.ajax
            ({
                type: 'post',
                url: url,
                async: true,
                data: {accion: 'DETAIL', nrotrn: nrotrn, tipe: tipe},
                success: function (response)
                {
                    $(get(id)).html('');
                    $(get(id)).html(response);
                    makeTotalTrx(nrotrn);
                }
            });
}

function makeTotalTrx(nrotrn) {

    var datos = $('#jqGata' + nrotrn).getGridParam('data');
    var arr = ['DBTTEX', 'CRDTEX'];
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
//                aux += parseFloat(accounting.unformat(eval('datos[i].' + arr[j])));

            } else {

                aux += parseFloat(datos[i][arr[j]]);
//                aux += parseFloat(eval('datos[i].' + arr[j]));
            }
            totls[j] = aux;
        }
        new_data_[arr[j]] = accounting ? accounting.formatMoney(totls[j]) : totls[j];
    }
    $('#jqGata' + nrotrn).jqGrid('addRowData', (datos.lenght) + 1, new_data_);
}

function putToolTip()
{
    if (!get("GridTable"))
        return;
    var filas = get("GridTable").rows;
    var arrDsp = getByName("DSPMVX");
    for (var i = 0; i < filas.length; i++)
    {
        filas[i].cells[2].childNodes[0].onmouseover = function () {
        };
        eval("filas[" + i + "].cells[" + 2 + "].childNodes[0].title = changeTildeBd('" + arrDsp[i].value + "')");
    }
}

function putTotal(id)
{
    var totalDbt = 0;
    var totalCrd = 0;
    var tr = "";
    if ($("#" + id).find('table #GridTable'))
    {
        var table = $("#" + id).find('table #GridTable');
        var filas = $("#" + id).find('table #GridTable').find('tr').length;
        var arrDbt = $('#' + id + ' input[name="DBTMVX"]');
        var arrCrd = $('#' + id + ' input[name="CRDMVX"]');
        for (var i = 0; i < filas; i++)
        {
            totalDbt += parseFloat(arrDbt[i].value);
            totalCrd += parseFloat(arrCrd[i].value);
        }

        tr = '<tr>' +
                '<td style="border: none; width: 40px;"></td>' +
                '<td style="border: none; width: 102px;"></td>' +
                '<td style="border: none; width: 300px;">TOTAL</td>' +
                '<td align="right" style="border: none; width: 100px;"><label class="caption">' + formatNumber("" + totalDbt) + '</label></td>' +
                '<td align="right" style="diaplay: block; border: none; width: 100px;"><label class="caption">' + formatNumber("" + totalCrd) + '</label></td>' +
                '</tr>';
        table.append(tr);
        tr = '<tr>' +
                '<td style="border: none; width: 40px;"></td>' +
                '<td style="border: none; width: 102px;"></td>' +
                '<td style="border: none; width: 300px;">DIFERENCIA</td>' +
                '<td align="right" style="border: none; width: 100px;"><label class="caption">' + formatNumber("" + (parseFloat(totalDbt) - parseFloat(totalCrd))) + '</label></td>' +
                '<td align="right" style="diaplay: block; border: none; width: 100px;"></td>' +
                '</tr>';
        table.append(tr);
        /*
         //Total
         var fila = table.insertRow(-1);
         var celda = fila.insertCell(0);
         celda.innerHTML = "";
         celda.width = "40";
         celda.style.border = "none";
         
         celda = fila.insertCell(1);
         celda.width = "102";
         celda.style.border = "none";
         celda.innerHTML = "";
         
         celda = fila.insertCell(2);
         celda.width = "300";
         celda.innerHTML = "TOTAL";
         
         celda = fila.insertCell(3);
         celda.width = "100";
         celda.align = "right";
         celda.innerHTML = "<label class='caption'>" + formatNumber("" + totalDbt) + "</label>";
         
         celda = fila.insertCell(4);
         celda.width = "100";
         celda.align = "right";
         celda.innerHTML = "<label class='caption'>" + formatNumber("" + totalCrd) + "</label>";
         fila.style.display = 'block';
         // Diferencia
         
         
         fila = table.insertRow(-1);
         celda = fila.insertCell(0);
         celda.style.border = "none";
         celda.width = "40";
         celda.innerHTML = "";
         celda = fila.insertCell(1);
         celda.width = "100";
         celda.style.border = "none";
         celda.innerHTML = "";
         celda = fila.insertCell(2);
         celda.width = "300";
         celda.innerHTML = "DIFERENCIA";
         celda = fila.insertCell(3);
         celda.width = "100";
         celda.align = "right";
         celda.innerHTML = "<label class='label_'>" + formatNumber("" + (totalDbt - totalCrd)) + "</label>";
         celda = fila.insertCell(4);
         celda.width = "100";
         celda.style.border = "none";
         fila.style.display = 'block';
         */
    }
}
function getInfoPersona(codprs) {
    var url = Rutac + '/Adm/AccountsPayableGeneration';
    var pars = 'events=INFPRS&codprs=' + codprs;
    $.ajax
            ({
                type: 'post',
                url: url,
                async: false,
                dataType: 'json',
                data: pars,
                success: function (jsonvuelta)
                {
                    if (jsonvuelta.toString().indexOf('nomprs') != -1) {

                        $('#_nomprs').val(jsonvuelta.nombre)
                        $('#_dirprs').val(jsonvuelta.dirprs)
                        $('#_emlprs').val(jsonvuelta.emlprs)
                        $('#_telprs').val(jsonvuelta.telprs)
                        $('#_tdFoto').html(jsonvuelta.foto);
                        $('#_tableInfo').show();
                    }
                }
            });
}

/** FIN FUCIONES PARA CONTABILIDAD **/

/** FUNCION PARA VALIDAR LOS CAMPOS ENVIADOS EN EL SELECTOR**/
//Validar campos obligatorios
function validarForm(selector) {
    var flag = true;
    $(selector).each
            (function ()
            {

                if ($(this).val() == '' || $(this).val() == '-9')
                {
                    show_message("<center class=\"caption tablas \" style=\height: 100%;font-size: medium;\" >" + "El campo " + $(this).attr('title') + " es obligatorio" + "</center>", '', 'ERROR');
                    $(this).removeAttr('disabled');
                    flag = false;
                    return flag;
                }
            }
            )

    return flag;
}


function serializeRows(elems_row_srz, filter) {
    var elements = elems_row_srz;
    var div_aux = $("<div></div>");
    elements.each(function (i, e) {
        var tr = $(e).parents("tr:first").clone();
        div_aux.append(tr);
    });
    var srz = div_aux.find(filter).serialize();
    return srz;
}

/**  FIN FUNCION PARA VALIDAR LOS CAMPOS ENVIADOS EN EL SELECTOR **/


/**
 * FUNCION PARA CREAR UN EDITOR DE TEXTOS 
 * @argument {String} selector selector del textarea para aplicar editor
 * @argument {String} width Ancho del editor editor (default 500px)
 * @argument {String} height Alto del editor editor (default 500px)
 * @
 * */

function openEditor(selector, options) {
    selector = typeof selector === 'string' ? selector : '#' + $(selector).attr('id');

    if (!options) {
        options = {width: 500, height: 300};
    }

    var obj = $.extend({}, options, {
        selector: "textarea" + selector,
        language: "es",
        width: 500,
        height: 300,
        theme: "advanced",
        relative_urls: false,
        plugins: "safari,pagebreak,style,layer,table,save,advhr,advimage,jbimages,advlink,emotions,iespell,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template,inlinepopups",
        // Theme options
        theme_advanced_buttons1: "bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,styleselect,formatselect,fontselect,fontsizeselect",
        theme_advanced_buttons2: "cut,copy,paste,pastetext,pasteword,|,search,replace,|,bullist,numlist,|,outdent,indent,blockquote,|,undo,redo,|,link,unlink,anchor,jbimages,image,cleanup,code,|,insertdate,inserttime,preview,|,forecolor,backcolor",
        theme_advanced_buttons3: "tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|,charmap,emotions,iespell,advhr,|,print,|,ltr,rtl,|",
        theme_advanced_buttons4: "insertlayer,moveforward,movebackward,absolute,|,styleprops,|,cite,abbr,acronym,del,ins,attribs,|,visualchars,nonbreaking",
        theme_advanced_toolbar_location: "top",
        theme_advanced_language: "es",
        theme_advanced_toolbar_align: "left",
        theme_advanced_statusbar_location: "bottom",
        theme_advanced_resizing: false,
        // Example word content CSS (should be your site CSS) this one removes paragraph margins
        content_css: Rutav + "/vista/script/tinymce_spanish/tinymce/examples/css/word.css",
        // Drop lists for link/image/media/template dialogs
        template_external_list_url: Rutav + "/vista/script/tinymce_spanish/tinymce/examples/lists/template_list.js",
        external_link_list_url: Rutav + "/vista/script/tinymce_spanish/tinymce/examples/lists/link_list.js",
        external_image_list_url: Rutav + "/vista/script/tinymce_spanish/tinymce/examples/lists/image_list.js",
        media_external_list_url: Rutav + "/vista/script/tinymce_spanish/tinymce/examples/lists/media_list.js",
        // Replace values for the template plugin
        template_replace_values: {
            username: "Some User",
            staffid: "991234"
        }
    });
    tinyMCE.init(obj);
}

function reemplaceCharacter(text) {
    text = text.replace("&aacute;", "á");
    text = text.replace("&eacute;", "é");
    text = text.replace("&iacute;", "í");
    text = text.replace("&oacute;", "ó");
    text = text.replace("&uacute;", "ú");
    return text;
}

/************************************************************************/
/**
 * 
 * @param {type} url :Ruta hacia la consulta
 * @param {type} event : EVento en el controlador
 * @param {type} id : Div
 * @param {type} key : llave principal
 * @param {type} nomkey : Nombre Llave principal
 * @param {type} callback : Funcion desppues
 * @returns {undefined}
 */
/***********************************************************************/
function callAutoComplete(url, id, event, key, nomkey, callback) {

    $('<input>').attr({
        type: 'hidden',
        id: key,
        name: key
    }).appendTo('#' + id);

    $('<input>').attr({
        id: key + "Auto",
        name: key + "Auto"
    }).appendTo('#' + id);

    var options = {
        url: function (phrase) {
            return url;
        },
        getValue: function (element) {
            $('#' + key).val(element[key.toUpperCase()]);
            return element[nomkey.toUpperCase()];
        },
        ajaxSettings: {
            dataType: "json",
            method: "POST",
            data: {
                dataType: "json",
                events: event
            }
        },
        preparePostData: function (data) {
            data.phrase = $("#" + key + "Auto").val();
            return data;
        },
        list: {
            maxNumberOfElements: 20,
            match: {
                enabled: true
            },
            sort: {
                enabled: true
            }
        },
        requestDelay: 400,
        theme: "plate-dark",
        ajaxCallback: function () {
            setTimeout(callback, 1);
        }
    };

    $("#" + key + "Auto").easyAutocomplete(options);
    $("#" + key + "Auto").css("width", "350px");
}

function validarCorreo(evaluar) {

    var filter = /^([a-zA-Z]){1}([a-zA-Z0-9\_\.-])*([a-zA-Z0-9\_])+(\@){1}(([a-zA-Z])+(\.){1}([a-zA-Z\.])*([a-zA-Z])+)$/;
    if (evaluar.length === 0)
        return true;
    if (filter.test(evaluar))
        return true;
    else
        return false;

}