var url = Rutac + "/Adm/ConditionsBuilder"; // Controlador
var checkedsTag = ""; // Checks seleccionados
var selectedTag = false;
var tabCounter = 0;
var tabPointer = 0; // Apuntador de Tab (Tab actual dentro de las condiciones)
var tabNumbers = 0;
var $tabs = "";
var selected = 0;
var listaTmpkeys = new Array();
var listaPermanentkeys = "";
var listaPermanentValues = "";
var codrpt;
var nrorpt;
var funcionFinal;
var codSvk = ""; // Tipo de ejecuciÃ³n del generador de condiciones
var txtXml = ""; // Tipo de ejecuciÃ³n del generador de condiciones
var accion = ""; // acciÃ³n a seguir
var nroSvk = ""; // Tipo de ejecuciÃ³n del generador de condiciones
var keySvkTab = ""; //*Usada para almacenar los keys de los componentes por defectos del tab
var keySvk = ""; //*Usada para almacenar los keys de los objetos agregados al tab por medio de
//registros asociados en la tabala smasak, que son objetos html que se agregan
//sin envargo los valores de los key entran por el metodo checkNextTabs(listadeKeys)
// como una string separado por # que serÃ¡ almcenado en un campo oculto 
// llamado listaTmpkeys
var valsvk = ""; // Respuestas o datos que se enviarÃ¡n al siguiente tab
var valsvkTab = "";
var tabs;
function addHeadTab() {
    var id = "tabs-0" + tabCounter;
    $("#tabs").find(".ui-tabs-nav").append("<li class='ui-state-default ui-corner-top ui-tabs-selected ui-state-active' ><a href='#" + id + "'>  <span >Selector</span></a></li>");
    $("#tabs").append('<div class="ui-tabs-panel ui-widget-content ui-corner-bottom" id="' + id + '"> </div>');
    return id;
}
var tposxp = '', idesxp = '';
function setvars(tposxp_, idesxp_, codsvk_, txtXml_, keySvkTab_, accion_,nrosvk_) {
    tposxp = tposxp_;
    idesxp = idesxp_;
    codSvk = codsvk_;
    txtXml = txtXml_;
    keySvkTab = keySvkTab_;
    accion = accion_;
    nroSvk=nrosvk_;
}


function initTabs() {
    var url = Rutav + "/vista/tabs/formSelectorBuilder.jsp";
    //  var id     = "#tabs-0" + tabCounter;
    //$("#tabs").tabs({ }).tabs("add", id, "Selector");

    var id = addHeadTab();
    $('#' + id).append("<i>Espere un momento...</i>");
//	$("#tabs").tabs({
//		            fx : { opacity : "toggle" },
//		   ajaxOptions : {
//                   data: {accion: "SELECT" , tposxp: "<%= tposxp %>", idesxp: "<%= idesxp %>" }
//                         }
//                         }).tabs("url",tabCounter, url ).tabs("load", 0);
//    
    var tposxp_ = tposxp;
    var idesxp_ = idesxp;

    $.ajax({
        type: 'post',
        url: url,
        async: false,
        data: {accion: 'SELECT', tposxp: tposxp_, idesxp: idesxp_},
        success: function(responseText) {
            try {

                $('#' + id).html(responseText);
            } catch (e) {
                show_message("<center class=\"caption tablas  tableMetal\" style=\height: 100%;font-size: medium;\" >" + e + "</center>", '', 'ERROR');
            }

        }
    });
    tabCounter++;
    enable_button('.the_panel', 'Siguiente');
}

$(function() {

    if (document.documentMode <= 7) {
        alert('Para una mejor experiencia desactive en Internet Explorer la vista de                                          compatibilidad\n            Luego intentelo nuevamente, Gracias!');
        self.history.back();
    }

//tabs = $( "#tabs" ).tabs();


});
function initpage()
{
    config_panel(950, 'auto', 'panel'); // Configurar panel y form modal 
    $("#panel").dialog('option', 'position', ['center', 10]); // Configurar tabs

    init_buttons(); // Inicializar tab y botones
    setIcons(); // Adicionar incono a botones 

    $tabs = $("#tabs").tabs();
    initTabs();
    $("#tabs-00").click();
}

function init_buttons() {																								// Deshabilita botones Anterior y Siguiente

    disable_button('.the_panel', 'Anterior');
    disable_button('.the_panel', 'Siguiente');
}

function config_panel(width_, height_, id)
{
    try {
        $("#" + id).dialog("destroy");
    } catch (e) {

    }


    $("#" + id).dialog
            ({
                dialogClass: 'the_panel',
                bgiframe: true,
                autoOpen: true,
                resizable: false,
                height: height_,
                position: 'top',
                draggable: false,
                width: width_,
                zIndex: 0,
                stack: false,
                modal: false,
                buttons:
                        {
                            //Anterior: function() 
                            //{
                            //priorTabs(); 
                            //},
                            Salir: function()
                            {
                                //addTabApi();
                                //return;
                                $(this).dialog('close');
                            },
                            Siguiente: function()
                            {
                                addTabs();
                            }
                        }
            });
}

function setIcons() {	   																								// Establece Iconos para los botones  	    
    setIcon('Anterior', '-circle-triangle-w');
    setIcon('Salir', '-closethick');
    setIcon('Siguiente', '-circle-triangle-e');
}





function removeTabs() {


    var tabExist = $("#tabs").tabs("length");
    var $tabs = $('#tabs').tabs();
    var selected = $tabs.tabs('option', 'selected');
}

function addTabs() {

    nextTabs();
    disableTabs();
}

function nextTabs() {
    var finTab = 0;
    getDataForNextTabs();
    saveTrasability();
    tabNumbers = $('#maxtab').val();

    if (tabCounter === 1) {
        alert(tabNumbers + "-> " + tabCounter + "->" + tabPointer + " ->" + tposxp + " ->" + get('nrosvk').value);
        var params = "&accion=NEXT&iTab=" + tabPointer + "&codSvk=" + tposxp + "&nroSvk=" + get('nrosvk').value;
    }
    else if (tabCounter <= tabNumbers) {
        var params = "&accion=NEXT&iTab=" + tabPointer + "&codSvk=" + codSvk + "&nroSvk=" + get('nrosvk').value + "&keysvk=" + listaPermanentkeys + "&valsvk=" + listaPermanentValues;
    }
    else if (tabCounter > tabNumbers && accion === "NEXT") {
        var params = "&accion=CONFIG&iTab=" + tabPointer + "&codSvk=" + codSvk + "&nroSvk=" + get('nrosvk').value + "&keysvk=" + listaPermanentkeys + "&valsvk=" + listaPermanentValues;
    }
    else if (tabCounter > tabNumbers && accion === "CONFIG") {
        finTab = 1;
    }
//       if (tabCounter === 1)
//        var params = "&accion=NEXT&iTab=" + tabPointer + "&codSvk=<%= tposxp %>&nroSvk=" + get('nrosvk').value;
//        else if (tabCounter <= tabNumbers)
//        var params = "&accion=NEXT&iTab=" + tabPointer + "&codSvk=" + codSvk + "&nroSvk=" + get('nrosvk').value + "&keysvk=" + keySvk + "&valsvk=" + valsvk;
//        else if (tabCounter > tabNumbers && accion === "NEXT")
//        var params = "&accion=CONFIG&iTab=" + tabPointer + "&codSvk=" + codSvk + "&nroSvk=" + get('nrosvk').value + "&keysvk=" + keySvk + "&valsvk=" + valsvk;
//        else if (tabCounter > tabNumbers && accion === "CONFIG")
//        return;

    var servlet = Rutac + "/Adm/ConditionsBuilder";
    keySvk = ''; // Reinicio los keys identificadores para recibir los posibles nuevos objetos sak del nuevo tab
    valsvk = ''; // encaso de que nuevo tab no tuviese objetos no se duplican los los keys y los values, por eso se hace necesario limpiarlos
    // alert("para--> "+params );
    var id = "#tabs-0" + tabCounter;
    if (finTab === 1) {
        disable_button('.the_panel', 'Siguiente');
        disable_button('.the_panel', 'Salir');
        if (codSvk === 'RPT')
            eval(funcionFinal + '(' + '\'' + codrpt + '\'' + ',' + '\'' + nrorpt + '\'' + ',' + '\'' + codSvk + '\'' + ',' + '\'' + nroSvk + '\'' + ',' + '\'' + keyXml + '\'' + ')');
        else
            eval(funcionFinal + '()');
        return;
    } else if (tabCounter > tabNumbers && accion === "NEXT" && codSvk === "SCR") {
        disable_button('.the_panel', 'Siguiente');
        disable_button('.the_panel', 'Salir');
        document.formulario.action = servlet + '?' + params;
        document.formulario.submit();
        return;
    } else {
        alert(accion + " -<" + params); 
        try {
            // $("#tabs").tabs("add", id, "Condición " + tabCounter);
            var ul = $("#tabs").find("ul");
            $("<li><a href='" + id + "'>Condición " + tabCounter + "</a></li>").appendTo(ul);
            $('<div id=\'' + id + '\'><i style=\'margin:20px;\'>Espere un momento...</i></div>').appendTo('#tabs');
            $.ajax({
                url: servlet,
                type: 'POST',
                async: false,
                data: params,
                success: function(data) {

                    $(id).html("");
                    $(id).fadeIn(700, function() {
                        $(id).html(data);
                    });
                }

            });

            $('#tabs').tabs("refresh");
        } catch (e) {
            alert(e);
        }
        if (tabCounter > tabNumbers)
            enable_button('.the_panel', 'Siguiente');
        else
            disable_button('.the_panel', 'Siguiente');
        selected = $tabs.tabs('option', 'selected');
        $("#tabs").tabs("option", "selected", selected + 1);
        $(id).click(function() {
            disableTabs();
        });
        tabPointer++;
        tabCounter++;
    }

}


function priorTabs() {

// Click en botÃ³n Anterior: Si es mayor que cero se disminuye el apuntador

    var $tabs = $('#tabs').tabs();
    var selected = $tabs.tabs('option', 'selected');
    if (iTab > 0)
        iTab--;
    showTab();
}


function selectConditionsAll(elemento) {
    var checkos = $("#listSra tr td div input[type='checkbox']");
    var state = false;
    if ($(checkos).size() > 0) {
        state = checkos[0].checked;
        if (!state)
            state = $(checkos[0]).attr("checked");
    }
    if (state === true)
        state = false;
    else
        state = true;
    $(checkos).each(function() {
        $(this).attr("checked", state);
        checkedTag(this, elemento);
    });
    return;
}

function nextCheck() {																								// Click en botÃ³n Siguiente    	

    if (checkedsTag.substring(1).length === 0 && tabPointer <= tabNumbers)
        disable_button('.the_panel', 'Siguiente');
    else
        enable_button('.the_panel', 'Siguiente');
    disableTabs();
}


function optionTag(radiobutton, parameters)
{

    var prms = parameters;
    if (radiobutton.checked === true)
        checkedsTag = ",!" + radiobutton.value + "!";
    nextCheck();
}

function checkedTag(checkbox, idInput) {

// Al checkear o no el programa

    if ($("#" + idInput) === undefined || ($("#" + idInput) === null))
        return;
    if (checkbox.checked === true)
        $("#" + idInput).val('' + $("#" + idInput).val() + ",!" + checkbox.value + "!");
    else
        $("#" + idInput).val($("#" + idInput).val().replace(",!" + checkbox.value + "!", ""))

    //alert('$("#"+idInput).val()-> '+$("#"+idInput).val());		


    readyForNextTabs();
}
function RadiocheckedTag(radio, idInput) {
// Al checkear o no el programa
    if ($("#" + idInput) === undefined || ($("#" + idInput) === null))
        return;
    if (radio.checked === true)
        $("#" + idInput).val(",!" + radio.value + "!");
    //alert('$("#"+idInput).val()-> '+$("#"+idInput).val())	;

    readyForNextTabs();
}


function disableTabs() {
//alert( 'disableTabs'+disableTabs);
    var tabExist = 0;
    try {
        tabExist = $('#tabs ul li').length;
        //alert('disableTabs' + $('#tabs ul li').length);    
    } catch (e) {

    }



    for (var i = 0; i < tabExist - 1; i++) {
        $("#tabs").tabs("disable", i);
    }
}

function readyForNextTabs() {
    if (keySvkTab != '') {

        listaTmpKeys = keySvkTab.split('#');
        if (listaTmpKeys[0] != '')
            for (i = 0; i < listaTmpKeys.length; i++) {
//keySvk+=listaTmpKeys[i];
                var tmp = listaTmpKeys[i];
                if ($("#" + listaTmpKeys[i]) === undefined || $("#" + listaTmpKeys[i]) === null)
                    continue;
                if ($('#' + tmp).val() === '') {
                    disable_button('.the_panel', 'Siguiente');
                    return false;
                }

            }
    }
    enable_button('.the_panel', 'Siguiente');
    return true;
}






// cargar los datos y los key de objetos del tab, para llevar al siguiente formulario
// listaTmpkeys contiene la lista de keys que serÃ¡n llevadas al siguiente tab
function getDataForNextTabs() {

    listaTmpKeys = $("#listaTmpkeys").val();
    alert(listaTmpKeys + " -> ");
    if (listaTmpKeys !== '') {

        listaTmpKeys = listaTmpKeys.split(',');
        for (i = 0; i < listaTmpKeys.length; i++) {
            keySvk += listaTmpKeys[i];
            var tmp = listaTmpKeys[i];
            if ($("#" + listaTmpKeys[i]) === undefined || $("#" + listaTmpKeys[i]) === null)
                continue;
            valsvk += $('#' + tmp).val();
            if (i !== (listaTmpKeys.length - 1)) {
                keySvk += '|';
                valsvk += '|';
            }

        }

    }
    alert(keySvkTab + " <- ");
    if (keySvkTab !== '') {
        listaTmpKeys = keySvkTab.split('#');
        if (listaTmpKeys[0] === '')
            return;
        if (valsvkTab !== '' && listaTmpKeys[0] !== '')
            valsvkTab += '|';
        keySvkTab = '';
        for (i = 0; i < listaTmpKeys.length; i++) {
            keySvkTab += listaTmpKeys[i];
            var tmp = listaTmpKeys[i];
            if ($("#" + listaTmpKeys[i]) === undefined || $("#" + listaTmpKeys[i]) === null)
                continue;
            valsvkTab += $('#' + tmp).val().substring(1);
            if (i !== (listaTmpKeys.length - 1)) {
                keySvkTab += '|';
                valsvkTab += '|';
            }

        }

    }

    alert(listaTmpKeys + " -> ");
    $("#listaTmpkeys").val('');
}


function checkNextTabs(listKeysElements) {
    var listKeys = new Array();
    var listKeys = listKeysElements.split('#');
    var listaTmpkeys = listKeys;
    $("#listaTmpkeys").val(listaTmpkeys);
    for (i = 0; i < listKeys.length; i++) {

        if ($("#" + listKeys[i]) === undefined || $("#" + listKeys[i]) === null)
            continue;
        var tmp = $("#" + listKeys[i]).val();
        tmp = tmp.replace(/^(\s|\&nbsp;)*|(\s|\&nbsp;)*$/g, "");
        if (tmp === '') {
            disable_button('.the_panel', 'Siguiente');
            return false;
        }




// tomar los value
//si los vvalue todos son diferentes de vacio avctivar boton siguiente
// si todo estan diferentes de vacio concadenarle los valores -> al valsvk los lisKeys _> keyxml 
    }


    readyForNextTabs();
}

function saveTrasability() {


    if (listaPermanentkeys != "")
        listaPermanentkeys += "|";
    if (listaPermanentValues != "")
        listaPermanentValues += "|";
    listaPermanentkeys += keySvkTab;
    listaPermanentValues += valsvkTab;
    listaPermanentkeys += keySvk;
    listaPermanentValues += valsvk;
    // alert('listaPermanentkeys-> '+listaPermanentkeys);
    // alert('listaPermanentValues-> '+listaPermanentValues);

    keySvk = ''; // Reinicio los keys identificadores para recibir los posibles nuevos objetos sak del nuevo tab
    valsvk = ''; // encaso de que nuevo tab no tuviese objetos no se duplican los los keys y los values, por eso se hace necesario limpiarlos

    keySvkTab = ''; // Reinicio los keys identificadores para recibir los posibles nuevos objetos sak del nuevo tab
    valsvkTab = ''; // encaso de que nuevo tab no tuviese objetos no se duplican los los keys y los values, por eso se hace necesario limpiarlos


}


function checkSeach(event) {
    var key;
    if (window.event)
        key = window.event.keyCode;
    else if (event)
        key = event.which;
    else
        return true;
    if (key === 13) {
        searchStudents();
    }
}

function searchStudents() {

    var params = $('#formUpdate').serialize();
    $.ajax({
        type: 'post',
        url: Rutac + '/Adm/ConditionsBuilderSearchAllPrs',
        async: false,
        data: {params: params, accion: 'GET'},
        success: function(responseText) {
            try {
                $('#tbodyPanelListAulas').html(responseText);
                $('#panelListAulas').dialog('open');
                return;
            } catch (e) {
                show_message("<center class=\"caption tablas  tableMetal\" style=\height: 100%;font-size: medium;\" >" + e + "</center>", '', 'ERROR');
            }
        }
    });
}

function initPageSearchStudents() {
    alert('Cl para el mundo...');
    conf_dialog('panelListStudents', 'auto', 'auto', 'center');
    $('#panelListStudents').dialog('open');
}
;
function setFieldSearch(nomfld) {
    document.getElementById(nomfld).disabled = 0;
    document.getElementById(nomfld).value = '';
    document.getElementById(nomfld).focus();
    $('#nomfld').val(nomfld);
    $('#checkSearch').val('0');
    //alert(nomfld+' ' +$('#nomfld').val());
}

function get_students(event) {



    if (window.event)
        key = window.event.keyCode;
    else if (event)
        key = event.which;
    else
        return true;
    if (key === 13) {



        var pars = $('#formUpdateSerieStudents').serialize();
        // alert(pars);
        $.ajax({
            type: 'post',
            url: Rutac + '/Adm/ConditionsBuilderSearchAllPrs?' + pars,
            async: false,
            data: {},
            success: function(responseText) {
                try {


                    if (responseText.indexOf("{") === 0) {
                        responseText = $.parseJSON(responseText);
                        var smaprs = responseText.data[0];
                        select_student(smaprs.nriprs, smaprs.codprs, smaprs.apeprs, smaprs.nomprs);
                    } else {
                        $('#panelListStudents').html(responseText);
                        $('#panelListStudents').dialog('open');
                    }
                    //show_message(responseText.msn,"limpiarHorario('"+responseText.estado+"')",responseText.estado);

                } catch (e) {
                    show_message("<center class=\"caption tablas \" style=\height: 100%;font-size: medium;\" >" + e + "</center>", '', 'ERROR');
                }

            }
        });
    } else {
        $('#checkSearch').val('0');
    }
}




function select_student(nriprs, codprs, apeprs, nomprs) {
//alert(nriprs+' '+codprs+' '+apeprs+' '+nomprs);
    $('#codprs').val(codprs);
    $('#nriprs').val(nriprs);
    $('#apeprs').val(apeprs);
    $('#nomprs').val(nomprs);
    $('#panelListStudents').dialog('close');
    var url = $('#phoprs').attr('src');
    url = url.substring(0, url.lastIndexOf('/') + 1) + codprs + '.jpg';
    //alert(url);
    $('#phoprs').attr('src', url)
    $('#checkSearch').val('1');
    //alert($('#checkSearch').val());
}

function add_students(idInput) {
    if ($('#checkSearch').val() === '1') {

        if ($("#" + idInput) === undefined || ($("#" + idInput) === null))
            return;
        $("#" + idInput).val('' + $("#" + idInput).val() + ",!" + $('#codprs').val() + "!");
        //alert($("#"+idInput).val());
        var template = $('#tdTemplate').clone();
        $(template).find('#tmpCodprs').text($('#codprs').val());
        $(template).find('#tmpNriprs').text($('#nriprs').val());
        $(template).find('#tmpApeprs').text($('#apeprs').val());
        $(template).find('#tmpNomprs').text($('#nomprs').val());
        // FALTA VERIFICAR QUE NO SE AGREGUE DOS VECES EL MISMO ESTUDIANTE
        -$('#listStudentsSelected').append(template);
//       $('#listStudentsSelected').append('<tr>' +
//        + '<td>' + $('#codprs').val().toString() + '</td>' +
//        + '<td>' + $('#nriprs').val().toString() + '</td>' +
//        + '<td>' + $('#apeprs').val().toString() + '</td>' +
//        + '<td>' + $('#nomprs').val().toString() + '</td>' +
//        + '</tr>');
        readyForNextTabs();
    } else {
        show_message("<center class=\"caption tablas \" style=\height: 100%;font-size: medium;\" >" + 'Realice la b\FAsqueda del estudiante' + "</center>", '', 'ERROR');
    }
}

function remove_student_of_list(elemento, idInput) {

    if ($("#" + idInput) === undefined || ($("#" + idInput) === null))
        return;
    alert($("#" + idInput).val());
    var aux = $(elemento).parent().find('#tmpCodprs').text();
    alert(aux);
    alert($("#" + idInput).val());
    $("#" + idInput).val($("#" + idInput).val().replace(",!" + aux + "!", ""))
    $('elemento').parent().parent().remove();
}



	  