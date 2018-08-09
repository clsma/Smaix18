/*
 * Autor Carlos Pinto Jimenez ( Cl-Sma )
 * Created 18/07/2016 11:13:01 AM
 * File scheduleDraw
 */
(function ($) {

    var methods = {
        init: function () {

            var options = arguments[0],
                    elem = $(this);

            if (isEmpty(options.view) || isEmpty(options.controller)) {
                $.error('Must define view path and controller path');
            }

            if (isEmpty(options.name)) {
                $.error('Must define name for the schedule');
            }

            var settings = elem.data('schedule');

            settings = options = Object.extends({}, $.fn.schedule.defaults, options);
            elem.data('schedule', options);
            var auxOpt = Object.extends(true, {}, options);
            delete auxOpt.complete;

            clsma.$createStyle('.schemedetail', {width: '110px', padding: '2px', border: '1px solid white', background: '#BEBEBE', 'text-align': 'center', 'font-weight': 'bolder'});
            clsma.$createStyle('.schemerow', {width: '82px', 'height': '25px', background: '#F3F6FF', border: '1px solid #ADD8E6'});
            clsma.$createStyle('.schemegroup', {width: '90px', border: '1px solid gray', 'font-size': '8px', 'cursor': 'pointer', 'color': 'black', 'height': '25px', 'text-align': 'center', 'text-shadow': '1px 1px 5px white'});
            clsma.$createStyle('.droppe', {padding: '10px 2px', height: '50px', border: '1px solid black', 'background-image': 'url(' + options.view + '/vista/img/delete.png)', 'background-repeat': 'no-repeat', 'background-position': 'top'});
            clsma.$createStyle('.loaded', {'background-image': 'url(' + options.view + '/vista/img/oksmall.png)', 'background-repeat': 'no-repeat', 'background-position': 'right top'});

            clsma.$get(
                    options.controller + '/Adm/ScheduleDraw?event=GET',
                    auxOpt, {loading: true, returning: 'object'})
                    .done(function (data) {
                        if (data.exito === 'OK') {
                            drawSchdeule.call(elem, data, options.name);
                        } else {
                            clsma.$msg(data.msg, '', 'ERROR');
                        }
                    }).done(function (data) {
                if (Object.isFunction(settings.complete)) {
                    settings.complete.call(elem, data);
                }
            });
        }
    };


    $.fn.schedule = function () {

        var args = argumentsArray(arguments);
        var method = args[0];
        if (Object.isString(method) && isFunction(methods[method])) {
            return methods[method].call(this, args.slice(0));
        } else if (Object.isPlain(method)) {
            return methods.init.call(this, method);
        } else {
            $.error('Not such usage');
        }
    }

    $.fn.schedule.defaults = {
        name: 'schedule' + clsma.$UID(),
        complete: clsma.$noop
    };

    function drawSchdeule(data, id) {
        var schedule = $(this);
        var options = $(schedule).data('schedule');
        var arrData = isEmpty(data.groups) ? (options.groups || []) : data.groups;
        var arrSchedule = isEmpty(data.schedule) ? (options.schedule || []) : data.schedule;
        var arrDay = isEmpty(options.days) ? (data.days || []) : options.days;
        var arrHour = isEmpty(options.hours) ? (data.hours || []) : (options.hours);



        var hasGroups = !arrData.empty();

        schedule.css({width: '100%', background: 'white'}).empty();

        var table, tr, td, div;

        //Table parent
        table = $('<table style="width:100%"/>').appendTo(schedule);
        tr = $('<tr />').appendTo(table);

        td = $('<td style="width:10%;vertical-align:top" class="groups"/>');
        if (!hasGroups) {
            td.hide();
        }
        td.appendTo(tr);

        td = $('<td style="width:90%;vertical-align:top" class="ranges"/>').appendTo(tr);

        //Table Grupos
        if (hasGroups) {
            table = $('<table style="width:100%"/>').appendTo('.groups');
            tr = $('<tr />').appendTo(table);
            td = $('<td/>')
                    .addClass('schemedetail')
                    .text('Asignaturas')
                    .appendTo(tr);

            //Creamos Grupos
            var dat, nommat, codmat, color;
            arrData.each(function () {
                tr = $('<tr />').appendTo(table);
                td = $('<td/>').appendTo(tr);

                nommat = nomMat(this);
                codmat = codPak(this);
                color = colorMat(this.CLRMAT)

                div = $('<div />')
                        .attr({id: this.NROPAK,
                            'data-mat': this.NROMAT,
                            'data-bgn': this.BGNPAK,
                            'data-end': this.ENDPAK,
                            title: this.CODMAT + ' - ' + this.NOMMAT + ' - ' + this.CODPAK
                        })
                        .attr({'data-color': color})
                        .css({'text-align': 'center', height: '25px', 'background-color': '#' + color})
                        .addClass('schemegroup')
                        .html(codmat + nommat)
                        .appendTo(td);
            });

            tr = $('<tr />').appendTo(table);
            td = $('<td/>')
                    .html('<div class="droppe"/>')
                    .appendTo(tr);
        }

        //Table Horario
        table = $('<table id="' + id + '" style="width:100%"/>').appendTo('.ranges');
        var thead = $('<thead />').appendTo(table);

        tr = $('<tr />').appendTo(thead);
        td = $('<td colspan="9"/>')
                .css('text-align', 'center')
                .addClass('schemedetail')
                .text('Programación de horarios').appendTo(tr);

        tr = $('<tr />').appendTo(thead);
        td = $('<td />')
                .text('Intervalos')
                .addClass('schemedetail')
                .css({'min-width': '100px'})
                .appendTo(tr);

        arrDay.each(function () {
            td = $('<td />')
                    .text(this.NOMDAY)
                    .addClass('schemedetail')
                    .appendTo(tr);
        });
        var hour;
        var tbody = $('<tbody/>').appendTo(table);
        var index;
        arrHour.each(function () {
            index = 1;
            tr = $('<tr />').appendTo(tbody);
            td = $('<td />')
                    .text(this.HOUTIM)
                    .addClass('schemedetail')
                    .appendTo(tr);
            hour = this.HOUTIM;

            arrDay.each(function () {
                td = $('<td />')
                        .attr({id: 'scm_' + hour + '_' + this.NOMDAY, 'nomday': this.NOMDAY, 'nomhor': hour, 'dayindex': index})
                        .addClass('schemerow')
                        .appendTo(tr);
                index++;
            });
        });

//        if (hasGroups) {
        var lastMat, nommat, codmat;
        arrSchedule.each(function () {

            nommat = nomMat(this);
            codmat = codPak(this);
            color = colorMat(this.CLRMAT);
            if (lastMat !== this.NROMAT) {
                lastMat = this.NROMAT;
            }
            color = !isEmpty($('.schemegroup[data-mat="' + this.NROMAT + '"]')) ?
                    $('.schemegroup[data-mat="' + this.NROMAT + '"]').attr('data-color') :
                    color;

            div = $('<div />')
                    .attr({id: this.NROPAK, 'data-mat': this.NROMAT, 'data-bgn': this.BGNPAK || this.BGNPDA, 'data-end': this.ENDPAK || this.ENDPDA, 'data-pda': this.NROPDA, 'data-day': this.DATPDA, 'data-hour': this.SCHPDA})
                    .attr({'data-color': color, title: this.NOMMAT})
                    .css({'text-align': 'center', height: '25px', 'background-color': '#' + color})
                    .addClass('schemegroup')
                    .html(codmat + nommat);

            if (!isEmpty(this.NROPDA) || !isEmpty(this.NROMAT)) {
                dat = 'scm_' + this.SCHPDA + '_' + this.DATPDA;
                if (!isEmpty(dat)) {
                    $('td[ id="' + dat + '"]').append(div.addClass('dropped loaded'));
                    div.bind('click', function (e) {
                        showFormSave.call(this, schedule);
                    });
                }
            }
        });
//        }

        if (hasGroups) {
            clsma.$drag($('.schemegroup'), {
                drag: options.drag
            });

            clsma.$drop($('.schemerow'), {
                accept: '.schemegroup:not(.loaded)',
                drop: function (a, b) {
                    onDropElement.call(this, a, b, schedule);
                },
                hoverClass: "ui-state-default"
            });

            clsma.$drop($('.droppe'), {
                accept: '.dropped',
                drop: function (a, b) {
                    onDropRemove.call(this, a, b, schedule);
                },
                hoverClass: "ui-state-default"
            });
        }

    }

    function colorMat(color) {
        return color.length <= 2 ? (parseInt(clsma.$rand(9999999)).toString(16)) : (parseInt(color).toString(16));
    }

    function nomMat(row) {
        return row.CODMAT.length > 13 ?
                (row.CODPAK + ' - ' + (row.NOMMAT.substring(0, 15))) :
                (row.NOMMAT.substring(0, 15));

    }
    function codPak(row) {
        return  (row.CODMAT.length <= 13 ?
                (row.CODMAT + ' - ' + row.CODPAK) :
                (row.CODMAT)) + '<br/>';

    }

    function onDropElement(a, b, sched) {

        var drag = b.draggable.clone();
        var target = a.target;

        var datatarget = target.id;
        datatarget = datatarget.split('_');

        drag.attr({'data-hour': datatarget[1], 'data-day': datatarget[2]})

        $(target).append(drag);

        drag.removeClass('loaded').addClass('dropped');

        clsma.$drag(drag, {});

        drag.bind('click', function (e) {
            showFormSave.call(this, sched);
        });
    }

    function onDropRemove(a, b, sched) {

        var pda = b.draggable.data('pda');
        if (isEmpty(pda) && !b.draggable.hasClass('loaded')) {
            b.draggable.remove();
        } else {
            clsma.$confirm('¿Esta seguro de eliminar la programación de horario para este grupo?').Aceptar(function () {
                executeDelete.call(b.draggable, sched, pda);
            });
        }
    }

    function showFormSave(sched) {
        var elem = this;
        var id = elem.id;
        var bgn = $(elem).data('bgn');
        var end = $(elem).data('end');
        var hour = $(elem).data('hour');
        var day = $(elem).data('day');
        var pda = $(elem).attr('data-pda') || '';
        var mat = $(elem).data('mat');
        var option = sched.data('schedule');
        var isLoaded = $(elem).hasClass('loaded');

        var button = ['Guardar', 'Cancelar'];

        if (isLoaded) {
            button = ['Cancelar'];
        }

        var html = html_form_save(id, hour, day, pda, mat);

        $('#win_sch').remove();

        var div = clsma.$new_window('win_sch', {
            buttons: button,
            width: '300',
            title: 'Confirmación',
            position: ['top', 20]
        });

        $(div.$this).empty().html(html).dialog('open');

        div.Cancelar(function () {
            $(this.$this).remove();
        });

        if (!isLoaded) {
            div.Guardar(function () {
                executeSave.call(elem, this.$this, sched);
            });
        }
        clsma.$date($('#bgnpgr ', '#win_sch'), {
            mindate: bgn,
            maxdate: end
        }).val((bgn || '').substring(0, 10));
        clsma.$date($('#endpgr ', '#win_sch'), {
            mindate: bgn,
            maxdate: end
        }).val((end || '').substring(0, 10));

    }

    function html_form_save(nropak, hour, day, pda, mat) {

        var table = $('<table />');
        var tr = $('<tr />').appendTo(table);
        var td = $('<td />')
                .html('<b>Periodicidad<b/>').appendTo(tr);
        td = $('<td colspan="5"/>')
                .html('<label>Cada</label><input size="5" type="text" id="smnpak" value="1"/><label>Semena(s)</label>')
                .appendTo(tr);
        tr = $('<tr />').appendTo(table);
        td = $('<td />')
                .html('<b>Inicia</b>').appendTo(tr);
        td = $('<td />')
                .html('<input type="text" id="bgnpgr" size="10" readonly/>')
                .appendTo(tr);
        tr = $('<tr />').appendTo(table);
        td = $('<td />')
                .html('<b>Finaliza</b>').appendTo(tr);
        td = $('<td />')
                .html('<input type="text" id="endpgr" size="10" readonly />')
                .appendTo(tr);
        tr = $('<tr />').appendTo(table);
        td = $('<td />')
                .html('<input type="hidden" id="pakpgr" value="' + nropak + '"/>')
                .appendTo(tr);
        td = $('<td />')
                .html('<input type="hidden" id="hourpgr" value="' + hour + '"/>')
                .appendTo(tr);
        td = $('<td />')
                .html('<input type="hidden" id="daypgr" value="' + day + '"/>')
                .appendTo(tr);
        td = $('<td />')
                .html('<input type="hidden" id="nropda" value="' + pda + '" />')
                .appendTo(tr);
        td = $('<td />')
                .html('<input type="hidden" id="matpda" value="' + mat + '" />')
                .appendTo(tr);
        return table;
    }

    function executeSave(panel, sched) {

        var elem = this;
        var option = sched.data('schedule');
        if (Object.isFunction(option.beforeSave)) {
            if (option.beforeSave.call(this, panel, option) === false) {
                return;
            }
        }

        //Sobreescribir el guardado 
        if (Object.isFunction(option.savePda)) {
            option.savePda.call(this, panel, option);
            return;
        } else {
            savePda.call(this, panel, sched);
        }

    }

    function savePda(panel, sched) {
        var elem = $(this);
        var option = sched.data('schedule');

        var day = $(panel).find('#daypgr').val();
        var hour = $(panel).find('#hourpgr').val();
        var pak = $(panel).find('#pakpgr').val();
        var prd = $(panel).find('#smnpak').val();
        var bgn = $(panel).find('#bgnpgr').val();
        var end = $(panel).find('#endpgr').val();
        var mat = $(panel).find('#matpda').val();

        if (isEmpty(bgn) || isEmpty(end) || isEmpty(prd)) {
            clsma.$msg('Debe escoger los rangos de fechas y periodicidad');
            return;
        }

        clsma.$get(option.controller + '/Adm/ScheduleDraw?event=SAVEPDA',
                {day: day, hour: hour, pak: pak, prd: prd, bgn: bgn, end: end, mat: mat},
                {loading: true, returning: 'object'}).then(function (data) {
            if (data.exito === 'OK') {
                clsma.$msg(data.msg, function () {
                    afterSave.call(elem, panel, data.nropda, sched);
                }, 'OK');
            } else {
                clsma.$msg(data.msg, '', 'ERROR');
            }
        });
    }

    function afterSave(panel, nropda, sched) {
        $(this).attr({'data-pda': nropda}).addClass('loaded');
        var option = sched.data('schedule');

        if (Object.isFunction(option.afterSave)) {
            if (option.afterSave.call(this, panel, option) === false) {
                return;
            }
        }
    }

    function executeDelete(sched, nropda) {
        var elem = this;
        var option = sched.data('schedule');

        if (Object.isFunction(option.beforeDelete)) {
            if (option.beforeDelete.call(this, option) === false) {
                return;
            }
        }

        //Sobreescribir el Eliminar 
        if (Object.isFunction(option.delPda)) {
            option.delPda.call(this, option);
            return;
        } else {

            delPda.call(this, sched, nropda);
        }

    }

    function delPda(sched, nropda) {
        var elem = this;
        var option = sched.data('schedule');

        clsma.$get(option.controller + '/Adm/ScheduleDraw?event=DELPDA',
                {pda: nropda}, {loading: true, returning: 'object'}).then(function (data) {
            if (data.exito === 'OK') {
                clsma.$msg(data.msg, function () {
                    afterDelete.call(elem, data.nropda, sched);
                }, 'OK');
            } else {
                clsma.$msg(data.msg, '', 'ERROR');
            }
        });

    }

    function afterDelete(nropda, sched) {
        $(this).remove();
        var option = sched.data('schedule');

        if (Object.isFunction(option.afterDelete)) {
            if (option.afterDelete.call(this, option) === false) {
                return;
            }
        }

    }

})(jQuery);