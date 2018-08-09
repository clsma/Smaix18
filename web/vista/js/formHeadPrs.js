/* 
 *Author     : Cl-sma(Carlos Pinto)
 */


(function ($, window) {
    var arrHeads = [];

    $.fn.destroyHead = function () {
        if (!isEmpty($(this).data('headPrs'))) {
            this.empty().removeData();
        }

        return this;
    };

    $.fn.getHead = function (options) {

        var controller,
                dfd,
                $this,
                element,
                defaults = {
                    codtrc: null,
                    tpotrc: null,
                    controllerPath: null,
                    viewPath: null,
                    styles: {},
                    complete: function () {
                    }
                };
        $this = $(this),
                element = this;


        if (isEmpty($this.data('headPrs'))) {
            defaults = $.extend({}, defaults, options);
        } else {
            return this;
        }


        var nrotrc = $this.data('nrotrc') || '';
        var tpotrc = $this.data('tpotrc') || '';

        if (nrotrc !== '') {
            defaults.codtrc = nrotrc;
        }
        if (tpotrc !== '') {
            defaults.tpotrc = tpotrc;
        }

        if (defaults.codtrc === null || defaults.tpotrc === null) {
            $.error('No ha definido el persona o típo');
            return;
        }

        if (defaults.controllerPath === null || defaults.viewPath === null) {
            $.error('No ha definido las rutas');
            return;
        }


        $this.data('headPrs', defaults);
        controller = defaults.controllerPath + '/Adm/HeadPrs';
        var functions = {
            config: function (options, data) {

                var div,
                        table,
                        tr,
                        td1,
                        td2,
                        td,
                        table2,
                        img,
                        label,
                        a,
                        element = $(this),
                        clases = {};
                clases = {
                    classmetal: {
                        'font-size': '12px',
                        color: '#3581B8',
                        'font-weigth': 'bold'
                    },
                    classBlue: {
                        color: '#1B4965',
                        'font-size': '10px !important',
                        'font-weigth': 'bold',
                        'margin-right': '6px'
                    },
                    classmail: {
                    },
                    classtd: {
                        padding: '3px',
                        'background-color':'#FBFBFB'
                    },
                    classdiv: {
                        background: '#FCFCFC',
                        '-webkit-box-shadow': '0px 0px 9px 0px rgba(0,0,0,0.26)',
                        '-moz-box-shadow': '0px 0px 9px 0px rgba(0,0,0,0.26)',
                        'box-shadow': '0px 0px 9px 0px rgba(0,0,0,0.26)',
                        'border-radius': '5px',
                        width: defaults.styles.width,
                        margin: '0 auto'

                    },
                    classimg: {
                        border: '1px dotted gray',
                        'border-radius': '5px',
                        
                    }
                };

                div = $('<div/>').css(clases.classdiv);
                table = $('<table/>').css({width: '100%'});
                table2 = $('<table/>').css({width: '100%'});
                tr = $('<tr/>');
                td1 = $('<td/>').css({width: '80%'});
                tr.appendTo(table);
                //td datos
                td1.appendTo(tr);
                //tabla foto
                td2 = $('<td/>');
                td2.appendTo(tr);
                img = $('<img class="head_photo"/>').css({width: '98', height: '120'}).css(clases.classimg);

                img[0].onerror = function () {
                    try {
                        this.src = defaults.viewPath + '/vista/img/img-not-foud.png';
                    } catch (e) {
                    }
                }
                try {
                    img[0].src = defaults.viewPath + '/utility/photos/' + data.NROPRS + '.png';
                } catch (e) {
                }
//                functions.setImg.call(this, defaults.viewPath + '/utility/photos/' + data.CODPRS + '.png', data.CODPRS);
                img.appendTo(td2);
                //Tabla datos
                table2.appendTo(td1);

                if (data.TPOPRS === 'BAS' || data.TPOPRS === 'DBI') {

                    tr = $('<tr id="plain"/>');
                    tr.appendTo(table2);
                    label = $('<label/>').text('Plan de Estudio Gráfico del Estudiante').css(clases.classmetal);
                    td = $('<td/>').css({width: '5%', 'text-align': 'right'}).css(clases.classtd).html(label);
                    td.appendTo(tr);
                    td = $('<td/>').css({width: '10%'}).css(clases.classtd);
                    a = $('<a/>').click(function () {
//                    alert(data.IDETABLE);
                        functions.show_syllabus.call(this, data.KEYTABLE);

                    }).css({cursor: 'pointer'});
                    img = $('<img/>').css({width: '18', height: '18'}).css(clases.classimg)
                            .attr({src: defaults.viewPath + '/vista/img/playnstudy.png'});
                    img.appendTo(a);
                    a.appendTo(td);
                    td.appendTo(tr);
                    label = $('<label/>').text(' - ').css({'margin-right': '5px'}).css(clases.classmetal);
                    label.appendTo(td);
                    label = $('<label/>').text('Estado académico del estudiante').css({'margin-right': '5px'}).css(clases.classmetal);
                    label.appendTo(td);
                    label = $('<label/>').text(data.ESTADO + ' - ' + data.STDBAS).css(clases.classBlue);
                    label.appendTo(td);
                    $('<br/>').appendTo(td);
                    label = $('<label/>').text('Estado de Ingreso').css({'margin-right': '5px'}).css(clases.classmetal);
                    label.appendTo(td);
                    label = $('<label/>').text(data.NGRBAS).css(clases.classBlue);
                    label.appendTo(td);
                    //--------------------------->
                }
                tr = $('<tr/>');
                tr.appendTo(table2);
                label = $('<label/>').text('Código').css(clases.classmetal);
                td = $('<td/>').css({width: '5%', 'text-align': 'right'}).css(clases.classtd).html(label);
                td.appendTo(tr);
                td = $('<td/>').css({width: '10%'}).css(clases.classtd);
                label = $('<label/>').text(data.CODIGO).css(clases.classBlue);
                label.appendTo(td);
                label = $('<label/>').text('Identificación').css({'margin-right': '5px'}).css(clases.classmetal);
                label.appendTo(td);
                label = $('<label/>').text(data.NRIPRS).css(clases.classBlue);
                ;
                label.appendTo(td);
                label = $('<label/>').text('Sexo').css({'margin-right': '5px'}).css(clases.classmetal);
                label.appendTo(td);
                label = $('<label/>').text(data.NOMSEX).css(clases.classBlue);
                label.appendTo(td);
                td.appendTo(tr);

                if (data.TPOPRS === 'MPY' || data.TPOPRS === 'EGR') {
                    var a = $(('<a href="#" >' +
                            '<img src="%s/vista/img/acrobat.gif" style="width:20px;height:20px">Hoja de vida</a>').StringFormat(defaults.viewPath));
                    a.click(function () {
                        functions.printVitae.call(this, data.NROPRS, data.TPOPRS);
                    })
                    td.append(a);
//                    td.appendTo(tr);

                }

                //-------------__>
                tr = $('<tr/>');
                tr.appendTo(table2);
                label = $('<label/>').text('Apellidos').css(clases.classmetal);
                td = $('<td/>').css({width: '5%', 'text-align': 'right'}).css(clases.classtd).html(label);
                td.appendTo(tr);
                td = $('<td/>').css({width: '10%'}).css(clases.classtd);
                label = $('<label/>').text(data.APEPRS).css(clases.classBlue);
                ;
                label.appendTo(td);
                td.appendTo(tr);
                //-------------__>
                tr = $('<tr/>');
                tr.appendTo(table2);
                label = $('<label/>').text('Nombres').css(clases.classmetal);
                td = $('<td/>').css({width: '5%', 'text-align': 'right'}).css(clases.classtd).html(label);
                td.appendTo(tr);
                td = $('<td/>').css({width: '10%'}).css(clases.classtd);
                label = $('<label/>').text(data.NOMPRS).css(clases.classBlue);
                ;
                label.appendTo(td);
                td.appendTo(tr);
                //--------------->
                tr = $('<tr/>');
                tr.appendTo(table2);
                label = $('<label/>').text('E-mail').css(clases.classmetal);
                td = $('<td/>').css({width: '5%', 'text-align': 'right'}).css(clases.classtd).html(label);
                td.appendTo(tr);
                td = $('<td/>').css({width: '10%'}).css(clases.classtd);
                label = $('<label/>').text(data.EMLPRS).css(clases.classBlue);
                ;
                label.appendTo(td);
                td.appendTo(tr);
                //-------------__>
                tr = $('<tr/>');
                tr.appendTo(table2);
                label = $('<label/>').text('Telefono').css({'margin-right': '5px'}).css(clases.classmetal);
                td = $('<td/>').css({width: '5%', 'text-align': 'right'}).css(clases.classtd).html(label);
                td.appendTo(tr);
                td = $('<td/>').css({width: '10%'}).css(clases.classtd);
                label = $('<label/>').text(data.TELPRS).css(clases.classBlue);
                label.appendTo(td);
                td.appendTo(tr);


                label = $('<label/>').text('Dirección').css({'margin-right': '5px'}).css(clases.classmetal);
                label.appendTo(td);
                label = $('<label/>').text(data.DIRPRS).css(clases.classBlue);
                label.appendTo(td);

                if (data.TPOPRS === 'BAS' || data.TPOPRS === 'DBI' || data.TPOPRS === 'EGR') {


                    tr = $('<tr/>');
                    tr.appendTo(table2);
                    label = $('<label/>').text('Programa').css(clases.classmetal);
                    td = $('<td/>').css({width: '5%', 'text-align': 'right'}).css(clases.classtd).html(label);
                    td.appendTo(tr);
                    td = $('<td id="program"/>').css({width: '10%'}).css(clases.classtd);
                    label = $('<label/>').text(data.NROPGM).css(clases.classBlue);
                    ;
                    label.appendTo(td);
                    label = $('<label/>').text(' - ').css({'margin-right': '5px'}).css(clases.classmetal);
                    label.appendTo(td);
                    label = $('<label/>').text(data.NOMPGM).css(clases.classBlue);
                    label.appendTo(td);
                    label = $('<label id="semester"/>').text('Sem.').css({'margin-right': '5px'}).css(clases.classmetal);
                    label.appendTo(td);
                    label = $('<label/>').text(data.SMTPSM).css(clases.classBlue);
                    ;
                    label.appendTo(td);
                    td.appendTo(tr);
                    //-------------__>

                    tr = $('<tr/>');
                    tr.appendTo(table2);
                    label = $('<label/>').text('Reforma Pénsum').css(clases.classmetal);
                    td = $('<td id="pensum"/>').css({width: '5%', 'text-align': 'right'}).css(clases.classtd).html(label);
                    td.appendTo(tr);
                    td = $('<td/>').css({width: '10%'}).css(clases.classtd);
                    label = $('<label/>').text(data.NROPSM).css(clases.classBlue);
                    label.appendTo(td);
                    label = $('<label/>').text(' - ').css({'margin-right': '5px'}).css(clases.classmetal);
                    label.appendTo(td);
                    label = $('<label/>').text(data.NOMPSD).css(clases.classBlue);
                    label.appendTo(td);
                    td.appendTo(tr);
                    //-------------__>

                }
                table.appendTo(div);
                div.appendTo(element);


                $($this).data('record', data);
                if (typeof defaults.complete === 'function') {
                    defaults.complete.call($this, data);
                }

            },
            validData: function (data) {

                data = $.parseJSON(data);
                if (data.exito === 'OK') {
                    return data.list;
                } else {
                    show_message(data.msg, '', 'ERROR');
                    return;
                }
            },
            getData: function (options) {

                return genericAjax({
                    url: controller,
                    data: {
                        events: 'SEARCH',
                        codtrc: options.codtrc,
                        tpotrc: options.tpotrc
                    }
                });
            },
            setImg: function (img, url, codprs) {
                $('<img>', {
                    src: url,
                    error: function () {
                        functions.setImg.call(this, defaults.viewPath + "/utility/photos/" + codprs + ".jpg", codprs);
                    },
                    load: function () {
                        $(img).attr("src", url);
                    }
                });
            },
            show_syllabus: function (ideprs) {


                $.getScript(defaults.viewPath + '/vista/js/ecmascript.js', function () {


                    var url = Rutac + '/Adm/SyllabusStudentView?idebas=' + ideprs + '&accion=VIEW';
                    var dfd = genericAjax({
                        url: url,
                        data: {},
                        loading: true
                    });


                    dfd.then(function (response) {

                        try {

                            response = $.parseJSON(response);
                            show_message(response.msg, '', 'ERROR');

                        } catch (e) {

                            var divOut = $('<div/>').attr({
                                id: 'syllabusPanel'
                            });
                            $(window.parent.document).find('body').append(divOut);

                            conf_form($(window.parent).width() - 8, $(window.parent).height() - 8, $(window.parent.document).find('#syllabusPanel'));
                            $(window.parent.document).find('#syllabusPanel').html(response);
                            $(window.parent.document).find('#syllabusPanel').dialog({
                                buttons: {
                                    Salir: function () {
                                        $(this).dialog('close');
                                    }

                                },
                                close: function () {
                                    $(this).hide();
                                    $(this).dialog('destroy');
                                    $(this).remove();
                                }
                            });
                            $(window.parent.document).find('#syllabusPanel').dialog("open");

                        }

                    });

                });




            },
            printVitae: function (nro, tpo) {

                genericAjax({
                    url: defaults.controllerPath + '/Adm/HeadPrs',
                    data: {
                        events: 'GETVIT',
                        a: nro,
                        b: tpo
                    },
                    loading: true
                }).then(function (data) {
                    data = $.parseJSON(data);
                    if (data.exito !== 'OK') {
                        show_message(data.msg, '', 'ERROR');
                        return;
                    }
                    setTimeout(function () {
                        $('#printVitae' + nro).remove();
                        var div = $('<div id="printVitae' + nro + '" />').appendTo($(window.parent.document).find('body'));
                        var iframe = $('<iframe style="width:100%;"/>').appendTo(div);
                        conf_form($(window.top).width() - 10, $(window.top).height() - 10, div);
                        iframe.attr('src', defaults.viewPath + '/utility/reportes/Jasper_Reports_UDS/pdf/' + data.nomrpt + '.pdf')
                                .css({height: $(window.top).height()});

                        div.dialog('option', {zIndex: 9999999});
                        div.dialog('open');



                    }, 1000);
                })

            },
            goToVitaeBas: function (nro, cod) {
                window.parent.location.href = defaults.viewPath + '/vista/academicos/admisiones/formSearchAllPrs.jsp?title=Hoja de vida Estudiante&urlNext=C/Adm/CurriculumVitae?accion=schInfoBas&findPrs=BAS&redirCod=' + cod
            }
        };
        element.each(function () {
            arrHeads.push(this);
        });



        function executeHead() {
            var $this = this;
            var elem = arrHeads.shift();

            if (elem && !isEmpty(elem)) {

                if ($(elem).prop('tagName') !== 'DIV' & $(elem).prop('tagName') !== 'TD') {
                    $.error('Contenedor no permitido');
                    return;
                }
                var data = functions.getData.call($(elem), defaults);
                data.then(function (data) {
                    data = $.parseJSON(data);
                    if (!data.exito === 'OK')
                        return false;

                    var fn = (function (list) {
                        return function () {
                            functions.config.call($(elem), defaults, list);
                            executeHead.call($this);
                        }
                    })(data.list);

                    setTimeout(function () {
                        fn();
                    }, 200);
                });
            }
            $(window).triggerHandler('headComplete');
            return $this;
        }
        executeHead.call(element);

    };

})(jQuery, window);

$(function () {

    $('div.headprs[data-head=true]').each(function () {
        $(this).getHead({
            viewPath: Rutav,
            controllerPath: Rutac
        });
    });

});
