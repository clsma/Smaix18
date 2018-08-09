/*
 *   Document   : components
 *   Created on : 23/05/2016, 05:56:45 PM
 *   Author     : Cl-sma Carlos Pinto
 */

(function($, window) {



    $.fn.btcollapse = function() {

        var
                _data = 'btcollapse',
                _dataelement = 'btelementcollapse',
                _args = arguments;

        var methods = {
            init: function() {

                var elem = this,
                        $this = $(this),
                        id = $this.attr('id');

                $this.children('div').each(function() {
                    var _id = this.id;
                    var _this = $(this);
                    var title = this.title || 'Collapse';

                    if (isEmpty(_id)) {
                        return;
                    }

                    var panelContainer = $('<div class="panel panel-default" data-collapse-id="' + _id + '" />');
                    var panelHeading = $('<div class="panel-heading" />');
                    var h4 = $('<h4 class="panel-title" />'); 
                    var aCollapse = $('<a data-toggle="collapse" href="#' + _id + '"/>').text(title);

                    _this.wrapAll(panelContainer);
                    _this.addClass('panel-collapse collapse');
                    panelContainer = $('[data-collapse-id=' + _id + ']');

                    aCollapse.appendTo(h4);
                    h4.appendTo(panelHeading);
                    panelHeading.prependTo(panelContainer);

                });
                $this.addClass('panel-group');

                return $this;

            }
        }


        return this.each(function() {

            var args = _args;
            args = args[0] || {};

            if (Object.canbehash(args)) {
                return methods.init.call(this, args);
            } else if (Object.isString(args)) {
                if (!methods[args]) {
                    $.error('%s is not a instance of btcollapse plugin'.StringFormar(args))
                }
                return methods[args].call(this, argumentsArray(_args).slice(1));
            }

        });





    }
    $.fn.collapse.defaults = {
    }


})(jQuery, window);