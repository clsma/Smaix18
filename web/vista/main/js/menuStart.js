/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function () {
//return;
    $.initMenu({
        use: 'route',
        keyDisplayed: true,
        controllerPath: Rutac,
        viewPath: Rutav,
        completed: function () {
            $.when(catchSession()).then(function () {
                validateActivity();
            });
        }

    });

});


$.initMenu = function (options) {

    var defaults = {
        use: 'route',
        keyDisplayed: true,
        controllerPath: null,
        viewPath: null,
        completed: function () {
        }

    };

    defaults = $.extend({}, defaults, options);

    if (defaults.controllerPath === null) {

        $.error('Debe asignar path del controlador');
        return;


    } else if (defaults.viewPath === null) {
        $.error('Debe asignar path de la vista');
        return;

    }




    var controller = defaults.controllerPath + '/Adm/InitPage',
            functions = {
                configElements: function (lista) {


                    var parent = $('#cssmenu');


                    var primaryMenu = [];
                    var secondMenu = [];
                    var thirdMenu = [];

                    primaryMenu = $.grep(lista, function (elemento, i) {
                        return (elemento.TIPMNU === "P");
                    });

                    secondMenu = $.grep(lista, function (elemento, i) {
                        return (elemento.TIPMNU === "S");
                    });

                    thirdMenu = $.grep(lista, function (elemento, i) {
                        return (elemento.TIPMNU === "T");
                    });

                    var menu,
                            codmnu,
                            submnu,
                            dpnmnu,
                            nommnu,
                            dirmnu,
                            prmmnu,
                            keymnu,
                            idemnx,
                            href = '#',
                            classLi = '';

                    var ul = $('<ul/>');
                    $.each(primaryMenu, function (i, b) {
                        i = parseInt(i);

                        var li;
                        var span;
                        var a;


                        codmnu = b.CODMNU;
                        submnu = b.SUBMNU;
                        dpnmnu = b.DPNMNU;
                        nommnu = b.PRMMNU;
                        dirmnu = b.PRCMNU;
                        prmmnu = b.PMTMNU;
                        keymnu = b.KEYMNU;
                        idemnx = b.IDEMNU;

                        if (i === (functions.length.call(this, primaryMenu) - 1)) {
                            classLi = "last";
                        } else {
                            classLi = "";
                        }


                        li = $('<li />').addClass(classLi).attr({id: 'LI_' + codmnu}).addClass('accor opened');

                        a = functions.validateRute.call(this, b);

                        span = $('<span/>').text(nommnu);

                        span.appendTo(a);
                        a.appendTo(li);
                        li.appendTo(ul);

                    });
                    ul.appendTo(parent);

                    $.each(secondMenu, function (i, b) {
                        i = parseInt(i);
                        var ul2 = $('<ul/>');
                        var li2;
                        var span;
                        var a,
                                padreUl;



                        codmnu = b.CODMNU;
                        submnu = b.SUBMNU;
                        dpnmnu = b.DPNMNU;
                        nommnu = b.PRMMNU;
                        dirmnu = b.PRCMNU;
                        prmmnu = b.PMTMNU;
                        keymnu = b.KEYMNU;

                        padreUl = $('#LI_' + codmnu, parent).find('ul');

                        if (functions.length.call(this, padreUl) === 0) {
                            $('#LI_' + codmnu, parent).append(ul2);
                            padreUl = ul2;
                        }

                        $('#LI_' + codmnu, parent).addClass('has-sub');


                        if (i === (functions.length.call(this, secondMenu) - 1)) {
                            classLi = "last";
                        } else {
                            classLi = "";
                        }


                        li2 = $('<li />').addClass(classLi).attr({id: 'LIB_' + codmnu + '_' + submnu});


                        a = functions.validateRute.call(this, b);

                        span = $('<span/>').text(nommnu);

                        span.appendTo(a);
                        a.appendTo(li2);
                        li2.appendTo(padreUl);


                    });

                    $.each(thirdMenu, function (i, b) {
                        i = parseInt(i);
                        var ul3 = $('<ul/>');
                        var li3;
                        var span;
                        var a,
                                padreUl;

                        codmnu = b.CODMNU;
                        submnu = b.SUBMNU;
                        dpnmnu = b.DPNMNU;
                        nommnu = b.PRMMNU;
                        dirmnu = b.PRCMNU;
                        prmmnu = b.PMTMNU;
                        keymnu = b.KEYMNU;

                        padreUl = $('#LIB_' + codmnu + '_' + submnu, parent).find('ul');

                        if (functions.length.call(this, padreUl) === 0) {
                            $('#LIB_' + codmnu + '_' + submnu, parent).append(ul3);
                            padreUl = ul3;
                        }

                        $('#LIB_' + codmnu + '_' + submnu, parent).addClass('has-sub');

                        if (i === (functions.length.call(this, secondMenu) - 1)) {
                            classLi = "last";
                        } else {
                            classLi = "";
                        }


                        li3 = $('<li />').addClass(classLi).attr({id: 'LIC_' + codmnu + '_' + submnu});

                        a = functions.validateRute.call(this, b);
                        span = $('<span/>').text(nommnu);

                        span.appendTo(a);
                        a.appendTo(li3);
                        li3.appendTo(padreUl);


                    });

                },
                validateRute: function (b) {

                    var ruta, params, idecon,
                            finalRoute, a,
                            keyMnu,
                            parent = $('#cssmenu'),
                            clicktrigger = function () {
                            },
                            routeShown;

                    ruta = b.PRCMNU,
                            params = b.PMTMNU,
                            idecon = b.IDEMNU,
                            keyMnu = b.KEYMNU,
                            a = $('<a/>');

                    if (ruta !== "NULL") {

                        if (ruta.startsWith('V/')) {
                            finalRoute = defaults.viewPath + ruta.substring(1) + params;
                        } else {
                            finalRoute = defaults.controllerPath + ruta.substring(1) + params;
                        }

                        if (defaults.use.toLowerCase() === 'route') {
                            routeShown = finalRoute;
                        } else {
                            routeShown = idecon;
                        }

                        if (defaults.keyDisplayed) {


                            a.attr({
                                href: routeShown,
                                keymnu: keyMnu
                            });

                        } else {
                            a.attr({
                                href: '000000000',
                                keymnu: keyMnu
                            });

                        }

                        clicktrigger = function (e) {

                            parent.find('ul.open').removeClass('open');
                            var data = $(this).data('mine');
                            var see = (data.url.indexOf('?') === -1) ? '?' : '&';

                            var link = $('<a/>').attr({
                                id: 'clicktrigger',
                                href: data.url + see + 'seek=' + data.seek + '&seekExecute=TRUE',
                                target: data.keymnu,
                                location: 'no'
                            });
                            if (['_parent', '_top'].has(data.keymnu.toLowerCase())) {
                                window.parent.href = link[0].href;
                                return false;
                            }


                            link.appendTo('body');
                            var targ = $('#' + link[0].target);
                            if (isEmpty(targ)) {
                                targ = $('#display');
                            }
                            targ.attr('src', link[0].href);
                            link.remove();
                            e.preventDefault();

                            return false;

                        };

                    } else {

                        a.attr({
                            href: '#',
                            keymnu: null
                        }).click(function (e) {
                            e.preventDefault();
                        });
                    }
                    a.data('mine', {
                        url: finalRoute,
                        keymnu: keyMnu,
                        functio: clicktrigger,
                        seek: idecon
                    });

                    a.click(function (e) {
                        $(this).data('mine').functio.call(this, e);
                    });
                    return a;
                },
                length: function (arr) {
                    return arr.length;
                }
            };




    var dfd = genericAjax({
        url: controller,
        data: {state: 'GETMNU'},
        loading: true,
        loadingText: 'Iniciando Menú.. Espere por favor'
    });



    dfd.done(function (data) {
        data = $.parseJSON(data);

        if (data.exito === 'OK') {
            if (functions.length.call(this, data.lista) > 0)
                functions.configElements.call(this, data.lista);
            defaults.completed.call(this);
        } else {

        }
    });


};

String.prototype.startsWith = function (str) {
    return this.indexOf(str) === 0;
};


function catchSession() {

    (function session() {
        genericAjax({
            url: Rutac + '/Adm/InitPage',
            data: {
                state: 'KEYSESSION'
            }
        }).then(function (data) {
            data = $.parseJSON(data).session;
            if (data !== 'TRUE')
                show_message("La sesion ha terminado, volverá a la pagina de inicio", function () {
                    window.top.location.href = Rutav + '/vista/sistemas/login/login.jsp';
                });
            setTimeout(session, 300000);
        }).fail(function () {
            show_message("La sesion ha terminado, volverá a la pagina de inicio", function () {
                window.top.location.href = Rutav + '/vista/sistemas/login/login.jsp';
            });
        });

    })();

}


function validateActivity() {

    genericAjax({
        url: Rutac + '/Adm/InitPage',
        data: {
            state: 'GETACTIVITY'
        }
    }).then(function (data) {

        var resp = $.parseJSON(data);

        if (resp.performing) {

            var activity = resp.activity || {};

            if ($.trim(activity.SRCHLQ) === '') {
                return;
            }


            if (activity.SRCHLQ.substring(0, 1) === 'C')
                activity.SRCHLQ = Rutac + activity.SRCHLQ.substring(1, activity.SRCHLQ.length);
            else
            if (activity.SRCHLQ.substring(0, 1) === 'V')
                activity.SRCHLQ = Rutav + activity.SRCHLQ.substring(1, activity.SRCHLQ.length);

            activity.FRMHLQ = 'TOP';
            printActivity(activity);
        }


    }, function (error) {
        console.log(error);
    }).done(function () {
        validateAlerts();
    });
}

function printActivity(activity, frame) {

    if (activity.FRMHLQ === 'TOP') {
        $('#activityFrm').attr('src', activity.SRCHLQ);
        $('#activity').show();
        var a = $('#activity .close_');
        var h3 = $('#activity .h3');
        h3.text('Bienvenido, Ayúdenos a mejorar los procesos, por favor diligenciar la siguiente información');
        if (activity.REQHLQ === '1') {

            a.click(function () {
                clsma.$endSession();
            });

//            h3.text('Bienvenido, Tiene la siguiente actividad requerida pendiente. Favor realizarla');

            $('#activityFrm').load(function () {
                $(this).contents().find('button[type=button].ui-dialog-titlebar-close').hide();
                var buttons = $(this).contents().find('div.ui-dialog-buttonset').find('button[role=button]');
                buttons.each(function () {
                    if ($(this)[0].textContent === 'Salir') {
                        $(this).hide()
                    }
                });
            });
        } else {
//            h3.text('Bienvenido, Tiene la siguiente actividad pendiente. Favor realizarla');
            a.show().click(function () {
                $('#activity').find('#activityFrm').attr('src', '');
                $('#activity').hide();

            });
        }
    } else {
        frame = $((frame || '#display'));


        if (activity.REQHLQ === '1') {
            frame.load(function () {
                show_message('Esta actividad es obligatoria , por favor complete.');
            });
        }
        frame.attr('src', activity.SRCHLQ);
    }

}
function makeMessage(msg, windo) {
    if (windo === window) {
        window.showMessage(msg);
        window.onbeforeunload = function () {
            alert(123)
        }
    } else {
        $(windo).load(function () {
            show_message(msg);
        });

        $(windo).contents().get(0).defaultView.unload = function () {
            alert(123)
        }
    }


}

function validMenu() {
    genericAjax({
        url: Rutac + '/Adm/InitPage',
        data: {
            state: 'DELMNX'
        }
    });
}

function validateAlerts() {

    var func = function () {

        getAlertsData().then(function (data) {
            data = $.parseJSON(data);
            if (data.exito === 'ERROR' || data.lstData.empty()) {
                return;
            }
            makeAlerts(data.lstData);
        });

    };

    if (window.stateApplication === 'loading') {
        clearInterval(window.intervalId);
        setInterval(validateAlerts, 300);
        return;
    } else if (isEmpty(window.stateApplication) || window.stateApplication === 'finished') {
        clearInterval(window.intervalId);

//        window.intervalId = setInterval(func, 50000);
        func();

    }
}
function makeAlerts(data) {
    styles();

    var parent = $('#alerty', window.top.document.body);
    if (isEmpty(parent)) {
        parent = $('<div id="alerty" class="alert_container"/>').appendTo(window.top.document.body);
        parent.css({
            float: 'right',
            position: 'fixed',
            'z-index': 10000,
            top: 3,
            height: 'auto',
            width: 'auto',
            transition: 'width .5s, height .5s ease-in-out',
            '-webkit-transition': 'width .5s, height .5s ease-in-out',
            right: 5,
//            'background-color': '#f3f1f3'
        });
    }
    parent.empty();

    var modal = $('<div class="modale hide"/>')
            .appendTo(parent);
    var alerty = $('<div id="circle" class="circle shakeinit init_alert"/>')
            .appendTo(parent);
    var alerts_container = $('<div class="alerts_container hide"/>')
            .appendTo(parent);


    alerty.click(function () {
        if (alerts_container.hasClass('hide')) {
            alerts_container.fadeIn('fast').removeClass('hide')
//            modal.fadeIn('fast').removeClass('hide')
        } else {
            alerts_container.fadeOut('fast').addClass('hide')
//            modal.fadeOut('fast').addClass('hide')
        }
        $('#circle').removeClass('init_alert shakeinit');
    });

    var alert_container, alert_panel, alert_title, alert_body, a, p;
    data.each(function () {
        alert_container = $('<div class="alert_div"/>')
                .appendTo(alerts_container);
        alert_panel = $('<div class="alert_panel"/>')
                .appendTo(alert_container);
        alert_title = $('<div class="alert_title"/>')
                .appendTo(alert_panel);
        a = $('<a class="a_title" />')
                .appendTo(alert_title)
                .text(this.TITALT);
        alert_body = $('<div class="alert_body"/>')
                .appendTo(alert_panel);
        p = $('<p class="p_body" />')
                .appendTo(alert_body)
                .html(this.DSPALT);

    });

}

function styles() {

    clsma.$createStyle('.init_alert', {
        '-webkit-box-shadow': '0px 0px 13px 0px rgba(60,198,76, 1)',
        '-moz-box-shadow': '0px 0px 13px 0px rgba(60,198,76, 1)',
        'box-shadow': '0px 0px 13px 0px rgba(60,198,76, 1)'
    });
    clsma.$createStyle('.alert_style', {
        padding: '5px',
    });
    clsma.$createStyle('.information_style', {
        padding: '5px',
    });
    clsma.$createStyle('.alert_panel', {
        padding: '5px',
    });
    clsma.$createStyle('.a_title', {
        color: 'black',
        'font-size': '13px',
        'text-decoration': 'none',
        'font-weight': 'bolder'
    });
    clsma.$createStyle('.a_title:hover', {
        color: 'black',
        cursor: 'pointer'
    });
    clsma.$createStyle('.alert_title', {
        'margin-top': '2px ',
    });
    clsma.$createStyle('.alert_body', {
        margin: '2px 0',
    });
    clsma.$createStyle('.p_body', {
        color: 'gray',
        'font-size': '14px',
        margin: '5px 0 0 10px'
    });

    clsma.$createStyle('.circle', {
        height: '40px',
        'border': '1px solid gray',
        'background-color': 'white',
        width: '40px',
        'border-radius': '50%',
        'background-size': '70%',
        'z-index': 1000,
        'cursor': 'pointer',
        'background-repeat': 'no-repeat',
        'background-position': 'center',
        'background-image': 'url( "' + Rutav + '/vista/img/alert.png' + '")',
    });
    clsma.$createStyle('.alerts_container', {
        position: 'fixed',
        top: '50px',
        float: 'right',
        'z-index': 1000,
        'max-height': '70%',
        height: 'auto',
        width: '300px',
        'max-width': '300px',
        right: '10px',
        'overflow-y': 'scroll',
        'background-color': 'rgb(179,175,179)',
        '-webkit-box-shadow': '0px 0px 5px 0px rgba(50, 50, 50, 0.75)',
        '-moz-box-shadow': '0px 0px 5px 0px rgba(50, 50, 50, 0.75)',
        'box-shadow': '0px 0px 5px 0px rgba(50, 50, 50, 0.75)',
        'border-color': '#ccc'
    });
    clsma.$createStyle('.hide', {
        display: 'none'
    });
    clsma.$createStyle('.alert_div', {
        display: 'block',
        height: 'auto',
        margin: '10px 5px',
        position: 'relative',
        'z-index': 1000,
        'background-color': 'white',
        'border-radius': '4px',
        '-webkit-box-shadow': '0px 0px 5px 0px rgba(50, 50, 50, 0.75)',
        '-moz-box-shadow': '0px 0px 5px 0px rgba(50, 50, 50, 0.75)',
        'box-shadow': '0px 0px 5px 0px rgba(50, 50, 50, 0.75)',
        'border-color': '#ccc'
    });
    clsma.$createStyle('.modale', {
        position: 'fixed',
        top: 0,
        left: 0,
        margin: '5px',
        width: '100%',
        height: '100%',
        'z-index': 999,
        'background-color': 'black',
        opacity: 0.4,
        filter: 'alpha(opacity = 40)',
    });


}

function getAlertsData() {

    return genericAjax({
        url: Rutac + '/Adm/InitPage',
        data: {
            state: 'ALERT'
        }
    });

}