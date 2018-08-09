/*
 * Components Bootstrap
 */



(function (jQuery, window) {

    var _$ = jQuery;
    var _data = 'btpanel',
            _dataelements = 'btpanel_elements';
    var options = {
        init: function () {
            var element = this,
                    _$this = _$(this);
            var options = arguments[0];
            var id = _$this.attr('id');
            var pprimary = _$('<div class="panel" data-panel="' + id + '"/>');
            var pcontainer = _$('<div class="container" data-container="' + id + '"/>');
            var pbody = _$('<div class="panel-body" data-panel-body="' + id + '" />');
            var phead = _$('<div class="panel-heading" data-heading="' + id + '"/>');
            var pbuttons = _$('<div style="max-width:100%;border:1px dotted gray;margin:5px;text-align:right" class="panelbuttons" data-panel-button="' + id + '"/>');
            var pparent = _$this.parent();

            if (!isEmpty(_$this.data(_data))) {
                return this;
            }

            options = _$.extend({}, _$.fn.btpanel.defaults, options);
            options.title = options.title || _$this.attr('title') || '';

            _$this.data(_data, options);

            pprimary.addClass('panel-' + options.class);

            _$this.wrapAll(pprimary); // inserto el panel dentro del panel primario
            pprimary = _$('[data-panel=' + id + ']');
            pprimary.wrapAll(pcontainer); //inserto el panel primario dentro del contenedor
            pcontainer = _$('[data-container=' + id + ']');
            _$this.wrapAll(pbody); //inserto el panel dentro del panel-cuerpo
            pbody = _$('[data-panel-body=' + id + ']');
            phead.html(options.title); //coloco el titulo al panel titulo
            pprimary.prepend(phead); //agrego el panel head al panel primario 
            pcontainer.position(options.position);

            _$this.data(_dataelements, {
                pbuttons: pbuttons,
                pprimary: pprimary,
                pcontainer: pcontainer,
                pbody: pbody,
                phead: phead,
                pparent: pparent
            });
            applyStyles(_$this, options);
            trigger(this, 'buttons');

        },
        methods: {
            buttons: function (buttons) {
                var _$this = _$(this);
                if (!options.methods.isElement(_$this, _data))
                    _$.error('element is not an instance of btpanel Plugin ');

                return options['_buttons'].call(this, buttons);
            },
            destroy: function () {
                var _$this = _$(this);
                if (!options.methods.isElement(_$this, _data))
                    _$.error('element is not an instance of btpanel Plugin ');

                return options['_destroy'].call(this);
            },
            isElement: function (element, plug) {
                return !isEmpty(_$(element).data(plug));
            }
        },
        _destroy: function () {

//            var options = this.data(_data);
            var elements = this.data(_dataelements);
            var id = this[0].id;
            this.unwrap();
            this.prev().unwrap();
            this.unwrap();
            elements.phead.remove();
            if (!isEmpty(_$('[data-panel-button=' + id + ']')))
                elements.pbuttons.remove();

            this.removeData(_data);
            this.removeData(_dataelements);

            return this;
        },
        _buttons: function (buttons) {

            var options = _$(this).data(_data);
            var elements = _$(this).data(_dataelements);
            buttons = !buttons ? options.buttons : options.buttons;

            if (!isEmpty(options.buttons)) {
                elements.pbuttons.empty();
                _$('[data-panel=panel]').append(elements.pbuttons);
                var botonera = _$('<div style="padding:2px"/>');
                var button;
                var but;
                O(buttons).each(function () {
                    button = _$('<button type="button" data-bt-name="' + this.key + '"/>')
                            .addClass('btn btn-default')
                            .text(this.key)
                            .bind('click', [this], function (event) {
                                if (event.data) {
                                    but = event.data[0];
                                    if (!isEmpty(but.value) && Object.isFunction(but.value)) {
                                        but.value.call(this, event);
                                    }
                                }
                            }).appendTo(botonera);
                });
                botonera.appendTo(elements.pbuttons);
            }

        }

    }

    function trigger(element, event, object) {
        if (event === null)
            return options.init.call(element, object || {});
        else {
            if (isEmpty(options.methods[event]))
                _$.error('method ' + event + ' does not exist in btpanel Plugin ');

            return options.methods[event].call(element, object || {});
        }
    }

    function applyStyles(element, options) {
        var elements = element.data(_dataelements);
        elements.pcontainer.css({
            width: options.width
        })

    }


    _$.fn.btpanel = function () {

        var arg = arguments;
        arg = argumentsArray(arg);
        var option = arg[0];

        if (Object.canbehash(option)) {
            return trigger(this, null, arg[0]);
        } else if (Object.isString(option)) {
            return trigger(this, option, arg[0]);
        }
    };

    _$.fn.btpanel.defaults = {
        position: {
            my: "center+0 top+20 ",
            at: "center top",
            of: window,
            collision: "fit"
        },
        class: 'primary'
    }

})(jQuery, window);

(function ($, window) {
    var _$ = jQuery;
    var _data = 'btmodal',
            _dataelements = 'btmodal_elements', uid = 0;
    ;
    _getUId = function () {
        return uid++;
    }
    $.fn.btmodal = function () {


        var options = {
            init: function (  ) {
                var eleme = _$(this);
                var id = eleme.attr('id'),
                        num = _getUId(),
                        settings;
                var options = arguments[0];

                var getAttr = function (name) {
                    return 'data-' + name + num;
                }

                var divContainer = $('<div id="modal_' + id + '" class="modal fade"  tabindex="-1" role="dialog"  aria-labelledby="' + id + 'Label" ' + getAttr('container') + ' />'),
                        divmodalDialog = $('<div class="modal-dialog" ' + getAttr('dialog') + ' />'),
                        divmodalCOntent = $('<div class="modal-content"  ' + getAttr('content') + '/>'),
                        divmodalHeader = $('<div class="modal-header"  ' + getAttr('header') + '/>'),
                        divmodalBody = $('<div class="modal-body"' + getAttr('body') + '/>'),
                        buttonClose = $('<button type="button" class="close" data-dismiss="modal" aria-label="Close" />'),
                        h4 = $('<h4 class="modal-title" id="' + id + 'Label"/>'),
                        divmodalFoot = $('<div class="modal-footer" ' + getAttr('footer') + '/>');

                if (!isEmpty(eleme.data(_data))) {
                    return this;
                } else {
                    settings = _$.extend({}, _$.fn.btmodal.defaults, options);
                }


                if (!isEmpty(settings.title)) {
                    h4.text(settings.title);
                }

                eleme.data(_data, settings);

                eleme.wrapAll(divmodalBody);
                divmodalBody = $('[' + getAttr('body') + ']');
                divmodalBody.wrapAll(divmodalCOntent);
                divmodalCOntent = $('[' + getAttr('content') + ']');
                divmodalCOntent.prepend(divmodalHeader);
                divmodalHeader = $('[' + getAttr('header') + ']');
                h4.appendTo(divmodalHeader);
                buttonClose.prependTo(divmodalHeader);
                $(' <span aria-hidden="true">×</span>').appendTo(buttonClose);

                divmodalCOntent.wrapAll(divmodalDialog);
                divmodalDialog = $('[' + getAttr('dialog') + ']');
                divmodalDialog.wrapAll(divContainer);
                divmodalFoot.appendTo(divmodalCOntent);



                eleme.data(_dataelements, {
                    divcontainer: divContainer,
                    divmodaldialog: divmodalDialog,
                    divmodalcontent: divmodalCOntent,
                    divmodalheader: divmodalHeader,
                    divmodalbody: divmodalBody,
                    buttonclose: buttonClose,
                    divmodalfoot: divmodalFoot,
                    h4: h4
                });

                applyStyles(eleme, options);
                applyEvents(eleme, options);
                trigger(this, 'buttons');
                divContainer.modal('hide');

                return eleme;
            }, methods: {
                buttons: function (buttons) {
                    var options = _$(this).data(_data);
                    var elements = _$(this).data(_dataelements);
                    buttons = !buttons ? options.buttons : options.buttons;

                    if (!isEmpty(options.buttons)) {
                        elements.divmodalfoot.empty();
                        var botonera = _$('<div style="padding:2px"/>');
                        var button;
                        var but;
                        O(buttons).each(function () {
                            button = _$('<button type="button" data-bt-name="' + this.key + '"/>')
                                    .addClass('btn btn-default')
                                    .text(this.key)
                                    .bind('click', [this], function (event) {
                                        if (event.data) {
                                            but = event.data[0];
                                            if (!isEmpty(but.value) && Object.isFunction(but.value)) {
                                                but.value.call(this, event);
                                            }
                                        }
                                    }).appendTo(botonera);
                        });
                        botonera.appendTo(elements.divmodalfoot);
                    }
                },
                isElement: function (element, plug) {
                    return !isEmpty(_$(element).data(plug));
                }
            }
        };

        function applyStyles(element, options) {
            var elements = element.data(_dataelements);

            elements.divmodaldialog.css({
                width: options.width
            });

        }
        function applyEvents(element, options) {
            var elements = element.data(_dataelements);
            $('#modal_' + element.attr('id')).on('hidden.bs.modal', function (e) {
                if (typeof options.close === 'function') {
                    options.close();
                } else if (typeof options.close === 'string') {
                    eval(options.close);
                }
                e.preventDefault();
            });


        }
        function trigger(element, event, object) {
            if (event === null)
                return options.init.call(element, object || {});
            else {
                if (isEmpty(options.methods[event]))
                    _$.error('method ' + event + ' does not exist in btmodal Plugin ');

                return options.methods[event].call(element, object || {});
            }
        }

        var arg = arguments;
        arg = argumentsArray(arg);
        var option = arg[0];

        if (Object.canbehash(option)) {
            return trigger(this, null, arg[0]);
        } else if (Object.isString(option)) {
            return trigger(this, option, arg[0]);
        }

    };

    _$.fn.openModal = function () {
        if (isEmpty($(this).data('btmodal')))
            _$.error('element is not an instance of btmodal Plugin ');

        $('#modal_' + this[0].id).modal('show');
    }
    _$.fn.hideModal = function () {
        if (isEmpty($(this).data('btmodal')))
            _$.error('element is not an instance of btmodal Plugin ');

        $('#modal_' + this[0].id).modal('hide');
    }




    _$.fn.btmodal.defaults = {
        position: {
            my: "center+0 top+20 ",
            at: "center top",
            of: window,
            collision: "fit"
        },
        class: 'fade'
    }
})(jQuery, window);

