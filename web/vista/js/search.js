/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/** FUNCIONES PARA LA CREACION DE VENTANAS MODALES**/
var pkVentana = 0;

function nuevaVentana(Id, Titulo, ancho, alto) {

    if (!Id)
        Id = 'windows' + pkVentana++;
    if ($.trim(Id) ===  '')
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

    if ($.trim(ancho) ===  '')
        ancho = 'auto';
    if ($.trim(alto) ===  '')
        alto = 'auto';

    $('#' + Id).dialog({
        autoOpen: false,
        show: {
            effect: "fade",
            duration: 300
        },
        hide: {
            effect: "fade",
            duration: 500
        },
        resizable: false,
        buttons: {
            Cancelar: function() {
                $(this).dialog('close');
                $(this).dialog('destroy');
                $(this).remove();
            }
        },
        position: ['center', 50],
        draggable: false,
        zIndex: 900,
        modal: true,
        height: alto,
        width: ancho,
        minWidth: 400

    });
    return Id;
}

/**FUNCIONES PARA LA NUEVA ESTRATEGIA DEL BUSCAR SEARCH**/
function showWindowSearch(pKey, fieldReturn, searchFlag, filter, callBackSearch) {

    if (!callBackSearch)
        callBackSearch = '';

    //pkey: primary key's table smasch
    //fieldReturn: return field of query
    //searchFlag: true if just send the value of filter and false if depends of another field
    //filter: fields to conform the statement where  001|OOO5 -> FALSE ,  &nrofkp&|&nroklt& -> TRUE $('#'+ARR[i]).val()

    var output = "";
    var url = Rutac + '/Build/SearchMaker';
    try {
        nuevaVentana('windowsSearch', 'VENTANA DE BÚSQUEDAS', 'auto', 'auto');

        $("#windowsSearch").dialog("option", "show", null);

        //$( "#windowsSearch" ).dialog({
        //  close: function( event, ui ) {}
        //	});

    } catch (e) {
        // TODO: handle exception
        show_message("<center class=\"caption tablas \" style=\height: 100%;font-size: medium;\" >" + "�No existe el marco de bsqueda, Consulte a su proovedor!" + "</center>", '', 'ERROR');
        return;
    }

    output += '<body rightmargin="0" leftMargin="0" topMargin="0" marginheight="0" marginwidth="0" >';
    output += '<table width="220" align="center" cellpadding="0" cellspacing="0">';
    output += '  <tr valign="middle" height="100">';
    output += '    <td class="caption" align="center">Cargando, espere por favor...</td>';
    output += '  </tr>';
    output += '</table>';
    output += '</body>';

    $("#windowsSearch").html(output);

    var filterAux = '';

    if (searchFlag ===  true && $.trim(filter) !== '') {

        var array = filter.split('|'); // [0]=&nomroo& [1]=&ttttt&  ->  &nomroo&|&ttttt&

        for (var i = 0; i < array.length; i++) {

            var isHtmlField = false;
            while ($.trim(array[i]).indexOf('&') !== -1) {
                array[i] = array[i].replace('&', ''); // buscar para ver comomokmsa nomroo
                isHtmlField = true;
            }

            if (isHtmlField ===  true) {
                var aux = $('#' + array[i]).val();
                if (!aux) {
                    aux = $('[name=' + array[i] + ']').val();
                }
            } else {
                var aux = array[i];
            }

            filterAux = filterAux + aux + '|';
        }
        filter = filterAux;
    }

    $.ajax({
        type: 'post',
        url: url,
        async: false,
        data: {action: 'INIT', pKey: pKey, fieldReturn: fieldReturn, searchFlag: searchFlag, filter: filter, callBackSearch: callBackSearch},
        success: function(responseText) {
            try {
                $("#windowsSearch").html(responseText);
                $("#layTitle").css('font-weight', 'bold').css('font-family', 'cursive').css('font-size', 'medium');

            } catch (e) {
                show_message("<center class=\"caption tablas \" style=\height: 100%;font-size: medium;\" >" + e + "</center>", '', 'ERROR');
            }


        }
    });

    $("#btnSelectSearch").button();

    $("#windowsSearch").dialog("open");
    $('#containerWindowSearch').find('#inputSearch').focus();
    return;

}

function showWindowSearchSelect(nropk, nombre, toField, callBackSearch)
{
    var aux = $('#' + toField).size();
    if (aux > 0) {
        $('#' + toField).val(nropk);
    } else {
        $('[name="' + toField + '"]').val(nropk);
    }
    if (nombre ===  false) {
        $('[name="' + toField + 'Shw"]').val(nropk);
    } else {
        $('[name="' + toField + 'Shw"]').val(nombre);
    }

    //falta darle focus a cualquier elemento diferente en el form
    $("#windowsSearch").dialog("close");
    $("#windowsSearch").remove();
    if (callBackSearch) {
        if (callBackSearch.indexOf('(') < 0)
            callBackSearch = '' + callBackSearch + '(';
        if (callBackSearch.indexOf(')') < 0)
            callBackSearch = '' + callBackSearch + ')';
        try {
            eval(callBackSearch);
        } catch (e) {

        }
    }
    return;
}
function showWindowFiltersSearch()
{
    var fieldSearch;
    var valueSearch;
    fieldSearch = $('#selectSearch').find('option:selected').attr('id');
    valueSearch = $('#inputSearch').val();
    var url = Rutac + '/Build/SearchMaker';

    $.ajax({
        type: 'post',
        url: url,
        async: false,
        data: {action: 'FLT', fieldSearch: fieldSearch, valueSearch: valueSearch},
        success: function(responseText) {
            try {
                $("#resultGridPanel").html(responseText);
            } catch (e) {
                show_message("<center class=\"caption tablas \" style=\height: 100%;font-size: medium;\" >" + e + "</center>", '', 'ERROR');
            }

        }
    });
}

function showWindowFiltersSearchValid(event) {
    var keycode = (event.keyCode ? event.keyCode : event.which);
    if (keycode ===  13) {
        showWindowFiltersSearch();
    }
}


function conf_tab(id) {
    $("#" + id).tabs({selected: -1});
}

function conf_tabForm(divForm, id) {
    $('#' + divForm).find('#' + id).tabs({selected: -1});
}

/** FIN DE LAS NUEVAS FUNCIONES DE GUARDAR **/

