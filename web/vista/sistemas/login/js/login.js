/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function() {
//    clearChildren('#formLogin');
    $('#userTxt').val('').focus();
    Remove_Caracteres('formLogin');
    validTextReason();
    a();
    validUser();
    imageResize();
//    $('#forgrtContent').hide();////Hide
});

function validThisLogin(form) {

    function verify(form) {
        var bool = true;
        $(form).find('input[type=text],input[type=password]').each(function() {
            $(this).removeClass('error');
            if (isEmpty($(this).val())) {
                bool = false;
                $(this).addClass('error');
            }
        });
        return bool;
    }
    if (verify(form)) {
        login(form);
    }
    return false;
}

function login(login) {

    clsma.$encryptForm($(login), 'test').$$serialCrypto();
    var formu = clsma.$$getForm();
    formu.navigator = clsma.$isMobile() ? 'true' : 'false';
    formu.clientKey = getValue('coreid');
    formu.keep = 0;
    if ($('#keep').is(':checked')) {
        formu.keep = 1;
    }

    genericAjax({
        url: Rutac + '/InitLogin/Application',
        data: {
            request: $.toJSON(formu),
            event: 'IN'
        }
    }).then(function(data) {
        data = $.parseJSON(data);
        if (data.exito === 'OK' && isEmpty(data.show)) {
            window.top.location.href = Rutav + data.goto;
        } else if (!isEmpty(data.show)) {
            showTable(data);
        } else {
            setMessage(data.msg, data.type);
        }
    });
}
function showTable(data) {

    conf_form('auto', 'auto', 'showTable');
    $('#showTable').empty().html(data.html);
    $('#showTable').dialog('option', 'title', 'Administración De Rol');
    $('#showTable').dialog('option', 'position', ['center', 40]);
    $('#showTable').dialog('open');


}

function initLogin(id) {
    var data = $('#jqRoles').getRowData(id);
    get('tpoTxt').value = data.TPOSGU;
    get('usrTxt').value = isEmpty(data.NROUSR) ? '' : data.NROUSR;
    login(get('formLogin'));
}

function setMessage(msg, type) {
//    clsma.$createStyle('.class' + type, {color: type === 'INFO' ? '#D7D599 !important' : 'red !important'})

    $('.messages').hide();
    $('.messages span').html(msg);
    $('.messages').slideDown();
}

function validTextReason() {
    var div = $('#reason');
    var val = div.html();

    if (!isEmpty(val)) {
        div.hide().delay(500);
        div.slideDown('slow').delay(4000).slideUp('fast');
    }
}

function a() {

    $('.a-for').click(function() {
        var id = $(this).attr('href').substring(1);
        $('html,body').animate({
            scrollTop: $("#" + id).offset().top},
        {duration: 'slow',
            complete: function() {
                $('#nriprs').focus();
            }
        },
        'slow');
    });


}


function validChgn() {
    clsma.$createStyle('.redborder', {'border': '1px solid red !important'});

    $('#tpoprs').removeClass('redborder');
    var tpo = getValue('tpoprs');
    if (getValue('tpoprs') === 'AAA') {
        $('#tpoprs').addClass('redborder');
        return;
    }

    if (isEmpty(getValue('nriprs'))) {
        $('#nriprs').addClass('redborder');
        return;
    }

    $('#idepgm').removeClass('redborder');
    if (getValue('idepgm') === '000' && ['BAS', 'EGR'].has(tpo)) {
        $('#idepgm').addClass('redborder');
        return;
    }

    genericAjax({
        url: Rutac + '/InitLogin/Application',
        data: {
            b: getValue('nriprs'),
            c: getValue('idepgm'),
            d: getValue('tpoprs'),
            event: 'CHN'
        }
    }).then(function(data) {
        data = $.parseJSON(data);

        if (data.exito === 'OK') {
            $('.messages2 span').css({color: 'green'});
            $('#bRestore').addClass('disabledButton');
        } else {
            $('.messages2 span').css({color: 'red'});
            $('#bRestore').removeClass('disabledButton');

        }
        $('.messages2 span').html(data.msg);

    }).done(function() {
        $('.messages2').hide();

        $('.messages2').slideDown();
    });

}


function validUser() {
    $('#nriprs').hide();
    var tpo = getValue('tpoprs');

    if (tpo === 'AAA') {
        $('#idepgm,#nriprs').hide();
    } else {

        $('#nriprs').show();
        if (['BAS', 'EGR'].has(tpo)) {
            $('#idepgm').show();
        } else {
            $('#idepgm').hide();
        }
    }



    if (tpo === 'AAA') {
        $('#bRestore').addClass('disabledButton');
        $('input , select', '#FORMCHN').not('#tpoprs').attr('disabled', true);
    } else {
        $('#bRestore').removeClass('disabledButton');
        $('input , select', '#FORMCHN').not('#tpoprs').attr('disabled', false);
    }
    clearChildren('#FORMCHN', '#tpoprs');
}

function imageResize() {

    $(window).bind('resize', function() {
        $('#cont-video img').css({width: '100%', height: '100%'})
    })

}

function makeAlerts(){
    
    
    
}