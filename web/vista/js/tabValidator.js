/* 
 *Author     : Cl-sma(Carlos Pinto)
 */


function validAllAtributtes(idscript, name) {
    var element = document.getElementsByName(name);
    element = $(element);
    var tagname = (element.prop('tagName') || '').toLowerCase();
    if (['textarea', 'input'].has(tagname)) {

        var data = element.data('valid');
        var editor = element.data('editor');
        var isDecimal = element.data('decimal');
        var isDate = element.data('date');
        var isTime = element.data('time');
        var isFile = element.data('file');
        var hasEnter = element.data('enter-pressed');
        if (!isEmpty(hasEnter)) {
            element.bind('keypress', hasEnter, function (e) {
                if (e.KeyCode === 13 || e.which === 13) {
                    if (Object.isFunction(window[e.data])) {
                        window[e.data](e);
                    } else {
                        (function () {
                            e.data();
                        })(e);
                    }
                }
            });
        }

        if (element.data('money')) {
            clsma.$money(element[0]);
        }

        if (isDate) {
            var opt = element.data('opt') || {};
            if (opt) {
                try {
                    opt = opt.replace(/'/g, '"');
                    opt = $.parseJSON(opt);
                } catch (e) {
                }
            }
            clsma.$date(element, opt);
        }
        if (isTime) {
            var opt = element.data('opt') || {};
            if (opt) {
                try {
                    opt = opt.replace(/'/g, '"');
                    opt = $.parseJSON(opt);
                } catch (e) {
                }
            }
            clsma.$time(element, opt);
        }

        if (isFile) {

            element.bind('change', function () {
                var value = this.value;
                if (isEmpty(value)) {
                    return false;
                }

                value = value.lastIndexOf('.') === -1 ? 'Archivo' : value.substring(value.lastIndexOf('.') + 1, value.length);
                var format = $(this).data('formats');
                format = format.replace(/\'/g, '\"');
                format = $.parseJSON(format);
                if (!Object.isArray(format)) {
                    this.value = '';
                    clsma.$msg('Formato de archivo no válido, formatos válidos [ ' + $.toJSON(format) + ' ]');
                    return false;
                } else if (!format.has(value)) {
                    clsma.$msg('Formato de archivo no válido, formatos válidos [ ' + $.toJSON(format) + ' ]');
                    this.value = '';
                    return false;
                }

            });
        }
        if (editor) {

            var opt = element.data('opt') || {};
            if (opt) {
                try {
                    opt = opt.replace(/'/g, '"');
                    opt = $.parseJSON(opt);
                } catch (e) {
                }
            }

            var maxlength = parseInt($(element).attr('maxlength') || 0);
            var id = $(element).attr('id');
            /*opt.setup = function (ed) {
             ed.onChange.add(function (ed, l) {
             console.log('Change => ');
             console.log(arguments);
             });
             ed.onKeyUp.add(function (ed, e) {
             var length = $(e.target).html().length + 1;
             if(length > maxlength)
             console.log(maxlength);
             });
             ed.onPaste.add(function (ed, e) {
             console.log('Paste => ');
             console.log(arguments);
             var length = $(e.currentTarget).html().length + 1;
             return maxlength >= length;
             });
             };
             */
            opt.setup = function (ed) {
                ed.onBeforeExecCommand.add(function (ed, cmd, ui, val) {
                    if (val) {
                        var length = tinyMCE.get(id).getContent().length + 1;
                        var length = val.length + length;
                        console.log('Command is to be executed: ' + cmd);
                        return length <= maxlength;
                    }
                });
            };
            openEditor(element, opt);
            if (maxlength > 0) {
                setTimeout(function () {
                    var editor = $('iframe#' + id + '_ifr').contents().find('body#tinymce');
                    editor.keypress(function (e) {
                        var length = tinyMCE.get(id).getContent().length + 1;
                        return length <= maxlength;
                    });
                    editor.bind("paste", function (e) {

                        setTimeout(function () {
                            var length = $(e.currentTarget).html().length;
                            if (length > maxlength) {
                                $.softMessage({
                                    class: 'error',
                                    float: 'right',
                                    left: 0,
                                    right: 50,
                                    delay: 2500,
                                    message: 'Ha excedido el maximo de caracteres permitidos maximo permitidos [' + maxlength + ' ] actuales [' + length + ']',
                                });
                                $(e.currentTarget).html('');
                            }
                        }, 1);
                    });
                }, 1000);
            }
        }

        if (isEmpty(data)) {
            document.getElementById(idscript).remove();
            return;
        }


        var def = element.data('default');
        element.bind('blur', function () {
            if (data.toLowerCase() === 'web') {
                ValidaURL(this);
            } else if (data.toLowerCase() === 'mail') {
                validarEmail(this);
            } else if (data.toLowerCase() === 'dni')
                $(this).val(blur_Remove(this, 'numeronit', def));
            if (data.toLowerCase() !== 'mail' && data.toLowerCase() !== 'dni')
                $(this).val(blur_Remove(this, data, def));
            if (isDecimal) {
                var value = this.value || '';
                if (value.toArray('.').size() < 3 && !value.has(',')) {
                    this.value = value.replace('.', ',');
                }
            }

        });
        if (isDecimal) {
            var value = element[0].value || '';
            if (value.toArray('.').size() < 3 && !value.has(',')) {
                element[0].value = value.replace('.', ',');
            }
        }



    }
    document.getElementById(idscript).remove();
}

function validateDecimals() {
    $('input[data-decimal=true]').each(function () {
        var value = this.value || '';
        if (value.toArray('.').size() < 3 && !value.has(',')) {
            this.value = value.replace('.', ',');
        }
    });
}

function validateDecimal(elem) {

    if (!isEmpty($(elem).attr('data-decimal'))) {
        return;
    }

    $(elem).attr('data-decimal', true);
    $(elem).each(function () {
        var value = this.value || '';
        if (value.toArray('.').size() < 3 && !value.has(',')) {
            this.value = value.replace('.', ',');
        }
    });
    $(elem).bind('blur', function () {
        var value = this.value || '';
        if (value.toArray('.').size() < 3 && !value.has(',')) {
            this.value = value.replace('.', ',');
        }
    });
}