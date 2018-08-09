/*
 *   Document   : generics
 *   Created on : 3/06/2016, 03:01:10 PM
 *   Author     : Cl-sma Carlos Pinto
 */



function $confirm(msg, okcallback, cancellcallback) {

    var id = 'confirm_' + Math.round(Math.random() * 99);

    var modal = $('<div class="modal fade" role="dialog" id="' + id + '"  tabindex="-1" role="dialog" aria-labelledby="' + id + 'Label" aria-hidden="true" />');
    var modal_dialog = $('<div class="modal-dialog" />');
    var modal_content = $('<div class="modal-content"/>');
    var modal_header = $('<div class="modal-header" />');
    var button_close = $('<button class="close" type="button" data-dismiss="modal" aria-hidden="true"/>').text('x');
    var h4 = $('<h4 class="modal-title" id="' + id + 'Label"/>');
    var modal_body = $('<div class="modal-body" />');
    var modal_footer = $('<div class="modal-footer" />');
    var mod_button_cancel = $('<button class="btn btn-default" />');
    var mod_button_ok = $('<button class="btn btn-primary" />');
    var p = $('<p />').css({
        'font-size': '16px',
        'font-weight': 600,
        padding: '5px',
        'margin-top': '10px'
    });
    modal.appendTo('body');
    modal_dialog.appendTo(modal);
    modal_content.appendTo(modal_dialog);
    modal_header.appendTo(modal_content);
    modal_body.appendTo(modal_content);
    modal_footer.appendTo(modal_content);
    button_close.appendTo(modal_header);
    h4.appendTo(modal_header).text('Confirmación');
    mod_button_cancel.appendTo(modal_footer).text('Cancelar').click(function(e) {
        $('#' + id).modal('hide');
        if (cancellcallback) {
            cancellcallback();
        }
        e.preventDefault();
    });
    mod_button_ok.appendTo(modal_footer).text('Ok').click(function(e) {
        $('#' + id).modal('hide');
        if (okcallback) {
            okcallback();
        }
        e.preventDefault();
    });

    p.text(msg).appendTo(modal_body);

    $('#' + id).modal({keyboard: false, show: 'false'});
    $('#' + id).modal('show');

}

function $alert(msg, okcallback, type) {
    type = !type ? 'info' : type;
    var cssHead = 'background-color: #5cb85c;border-color: #4cae4c;';
    var cssButton = !type ? 'primary' : type;

    switch (type) {
        case 'danger':
            cssHead = 'background-color: firebrick;';
            break;
        case 'info':
            cssHead = 'background-color: #5bc0de;border-color: #46b8da;';
            break;
        case 'warning':
            cssHead = 'background-color: #ec971f;border-color: #d58512;';
            break;
    }

    cssHead += 'border-radius: 5px 5px 0 0;color:white;';
    var id = 'alert_' + Math.round(Math.random() * 99);

    var modal = $('<div class="modal fade" role="dialog" id="' + id + '"  tabindex="-1" role="dialog" aria-labelledby="' + id + 'Label" aria-hidden="true" />');
    var modal_dialog = $('<div class="modal-dialog"  />');
    var modal_content = $('<div class="modal-content"/>');
    var modal_header = $('<div class="modal-header" style="' + cssHead + '" />');
    var button_close = $('<button class="close" type="button" data-dismiss="modal" aria-hidden="true"/>').text('x');
    var h4 = $('<h4 class="modal-title" id="' + id + 'Label"/>');
    var modal_body = $('<div class="modal-body" />');
    var modal_footer = $('<div class="modal-footer" />');
    var mod_button_ok = $('<button class="btn btn-' + cssButton + '" />');
    var p = $('<p />').css({
        'font-size': '16px',
        'font-weight': 600,
        padding: '5px',
        'margin-top': '10px'
    });

    modal.appendTo('body');
    modal_dialog.appendTo(modal);
    modal_content.appendTo(modal_dialog);
    modal_header.appendTo(modal_content);
    modal_body.appendTo(modal_content);
    modal_footer.appendTo(modal_content);
    button_close.appendTo(modal_header);
    h4.appendTo(modal_header).text('Información');
    mod_button_ok.appendTo(modal_footer).text('Ok').click(function(e) {
        $('#'+id).unbind('hidden.bs.modal');
        $('#' + id).modal('hide');
        if (okcallback) {
            okcallback();
        }
        e.preventDefault();
    });
    $('#' + id).bind('hidden.bs.modal', function(e) {
        if (okcallback) {
            okcallback();
        }
        e.preventDefault();
    });
    p.html(msg).appendTo(modal_body);

    $('#' + id).modal({keyboard: false, show: 'false'})
    $('#' + id).modal('show');

}

function $validarForm(form, not) {
    var bool = true;
    var msg = '';
    $(form).find('input , select ,textarea').not(':hidden').not(not).each(function() {


        if ($(this).prop('tagName') === 'INPUT' || $(this).prop('tagName') === 'TEXTAREA' || $(this).prop('tagName') === 'SELECT') {

            $(this).parent().removeClass('has-error');
            if (isEmpty(this.value)) {
                $(this).focus(function() {
                    $(this).parent().removeClass('has-error')
                })
                $(this).parent().addClass('has-error');
                msg += ',' + this.title;
                bool = false;
            }
        }
    });
    return bool;

}
function $disableButton(text, type) {
    var buton;
    if (!type) {
        buton = $('button , input[type=button] , a , div').each(function() {
            if (($.trim((this.textContent || this.innerText || this.value)) === text)
                    && $(this).hasClass('btn')) {
                $(this).addClass('disabled').css('pointer-events', 'none');
            }
        });
    } else
        buton = $(text).addClass('disabled').css('pointer-events', 'none');
    return buton;
}
function $enableButton(text, type) {

    var buton;
    if (!type) {
        buton = $('button , input[type=button] , a , div').each(function() {
            if (($.trim((this.textContent || this.innerText || this.value)) === text)
                    && $(this).hasClass('btn')) {
                $(this).removeClass('disabled').css('pointer-events', 'auto');
            }
        });
    } else
        buton = $(text).removeClass('disabled').css('pointer-events', 'auto');
    return buton;

}
function $hideButton(text, type) {

    var buton;
    if (!type) {
        buton = $('button , input[type=button] , a , div').each(function() {
            if (($.trim((this.textContent || this.innerText || this.value)) === text)
                    && $(this).hasClass('btn')) {
                $(this).hide();
            }
        });
    } else
        buton = $(text).hide();
    return buton;
}
function $showButton(text, type) {

    var buton;
    if (!type) {
        buton = $('button , input[type=button] , a , div').each(function() {
            if (($.trim((this.textContent || this.innerText || this.value)) === text)
                    && $(this).hasClass('btn')) {
                $(this).show();
            }
        });
    } else
        buton = $(text).show();
    return buton;
}
function $configButton(elem, type, icon, fn, data) {

    return $(elem)
            .attr({type: 'button'})
            .addClass('btn btn-' + type)
            .prepend('<span class="glyphicon glyphicon-' + icon + '" aria-hidden="true"></span>&nbsp')
            .click(function(e) {
                if (Object.isFunction(fn)) {
                    fn(data);
                }
            });
}