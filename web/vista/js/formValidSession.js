function init_page()
{
    conf_panel(375, false, 'panel', 'top');

    $('#panel').dialog('option', 'buttons', {
        "Entrar": function() {
            if (validarsesion()){
                $('#formUpdate').submit();
            };
        },
        "Salir": function() {
            $('#panel').dialog('close');
        }
    });

    init_buttons();
    setIconos();
    setIcon('Entrar', '-unlocked');
    get('panel').style.display = 'block';
    $('#pswprs').focus();
    $('#botonA').button();
    if ($('#msg').val() !== '') {
        show_message($('#msg').val(), '', 'ERROR');
        return;
    }
    clsma.$createStyle('.bordered', {'border': '1px solid red !important'});
}
var urlNxt = '';
function setUrl(urlNxt_) {
    urlNxt = urlNxt_;
}

function validateKeyPress(e)
{

    var key;
    var keychar;
    if (window.event || !e.which) // IE
        key = e.keyCode; // para IE
    else if (e) // netscape	
        key = e.which;
    else
        return true;
    if (key === 13) { //Enter
        validarsesion();
    }
}

function validSubmittion() {
    return false;
}

function validarsesion() {
    $('#pswprs').removeClass('bordered');


    if (isEmpty(getValue('pswprs'))) {
        $('#pswprs').addClass('bordered');
        return false;
    } else {

    }
    return true;

    /*
     var pars = {
     urlnxt: $("#urlnxt").val(),
     pswprs: encodeURI($('#pswprs').val()),
     state: 'VALID'
     };
     $.ajax({
     type: 'post',
     url: Rutac + '/Adm/ValidSession',
     data: pars,
     success: function(response)
     {
     var res = response.split("#");
     if (res[0] === '1') {
     console.log(res[1]);
     window.location.href = Rutav + res[1];
     } else {
     show_message(res[1], '', 'ERROR');
     }
     }
     }
     );*/
}
