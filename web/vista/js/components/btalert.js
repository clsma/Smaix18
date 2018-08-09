/*
 * author ( Cl-sma ) Carlos Pinto Jimenez
 * Created on 26/04/2016 04:50:12 PM
 */


(function($, window) {
  
    var methods = {
        init: function() {
            var args = arguments;
            args = argumentsArray(args)[0];
            var msg, theme;
            msg = args[0];
            theme = args[1];
            var callback = args[2];

            theme = methods.defaults.themes[methods.defaults.themes.indexOf(theme)];
            if (isEmpty(theme)) {
                theme = 'info';
            } 
            var id = $(this).attr('id');
            $('#btalert_' + id).remove();
            var div = $('<div class="alert alert-' + theme + '" id="btalert_' + id + '"/>');
            div.empty().appendTo(this);
            div.html(' <strong>Info!</strong> ' + msg + '.');

            if (Object.isFunction(callback)) {
                setTimeout(callback, 1000);
            }
            return this;
        },
        configurate: function() {

        }
        , defaults: {
            themes: ['info', 'success', 'warning', 'danger'],
            close: null,
            open: null
        }
    };


    $.fn.btalert = function() {
        var args = arguments;
        args = argumentsArray(args);
        if (Object.isString(args[0])) {
            return methods.init.call(this, args);
        }
        else if (Object.canbehash(args[0])) {

        }

    }



})(jQuery, window)