/* 
 * Menu vertical 
 * Con eventos
 * By : Carlos Pinto Jimenez
 * 15/10/2015
 */


(function($) {



    var methods = {
        init: function(options) {

            var $elem = $(this),
                    elem = this,
                    data = {},
                    settings,
                    privates
                    ;
            return elem.each(function() {
                data = $elem.data('menu-vertical');
                if (typeof (data) === 'undefined') {
                    settings = jQuery.extend({}, jQuery.fn.menu_vertical.defaults, options);
                    data = {defaults: settings};
                } else {
                    data = jQuery.extend(true, {}, data, {defaults: options});
                    methods.validStates.call($elem, data);
                    return this;
                }



                $elem.data('menu-vertical', data);
                initConfig.call($elem);
                return elem;
            });
        },
        option: function(key, value) {
            var data = $(this).data('menu-vertical');
            if (key === 'destroy') {
                return methods.destroyElement.call(this);
            } else if (key === 'active') {
                return methods.tabActivated.call(this);
            } else if (key === 'count') {
                return methods.tabCount.call(this);
            } else if (key === 'last') {
                return methods.lasTab.call(this);
            }

        },
        destroyElement: function() {
            if (isEmpty($(this).data('menu-vertical'))) {
                $.error('This element is not an instance of Jquery.menu_vertical Plugin');
            }
            var defaults = $(this).data('menu-vertical').defaults;

//            $(this).removeData('menu-vertical');
            var li;
            $(this).find('li[data-tab-index]').each(function() {
                li = this;
                for (i in  defaults.liClasses) {
                    $(li).removeClass(defaults.liClasses[i]);
                }
                for (i in  defaults.liAttributes) {
                    $(li).removeAttr(defaults.liAttributes[i]);
                }
            });
            var a;
            $(this).find('a[data-clicker]').each(function() {
                a = this;
                for (i in  defaults.aClasses) {
                    $(a).removeClass(defaults.aClasses[i]);
                }
                for (i in  defaults.aAttributes) {
                    $(a).removeAttr(defaults.aAttributes[i]);
                }

                $(this).removeAttr('');
                $(this).removeClass('.tab-parent')
            });

            $('#container').children('div').each(function() {
                for (i in defaults.divAttibutes) {
                    $(this).removeAttr(defaults.divAttibutes[i]);
                }
                $(this).show();
            });

            $(this)[0].style = null;
            ($('#container')[0] || {}).style = null;
            $(this).removeClass('menu-vertical');
            $(this).removeData('menu-vertical');
            return this;
        },
        lasTab: function() {
            var tabLastHtml = $('li[data-tab-index!=-1]', this)
                    .not('.tab-parent').last().attr('data-tab-index');
            var active = methods.tabActivated.call(this);
            return active === parseInt(tabLastHtml);
        },
        tabCount: function() {
            return $('li[data-tab-index!=-1]', this).not('.tab-parent').size();
        },
        tabActivated: function() {
            var arr = $(this).data('menu-vertical').menuIds;
            var index;
            $.each(arr, function(a, b) {

                var a_ = a;
                var b_ = b;
                if (b.state === 'activated') {
                    index = b.index;
                    return false;
                }

            });
            return index;
        },
        validStates: function(data) {
            var datos = data;
            var elem = $(this);
            var tabs = data.menuIds;
            var active = datos.defaults.active;
            var enable = datos.defaults.enable,
                    isEmptyEnable = testEmpty.call(this, enable);
            var disable = datos.defaults.disable,
                    isEmptyDisable = testEmpty.call(this, disable);
            var hide = datos.defaults.hide,
                    isEmptyHide = testEmpty.call(this, hide);
            var show = datos.defaults.show,
                    isEmptyShow = testEmpty.call(this, show);

            var isInit = datos.defaults.init;
            //Validar deshabilitacion del tab
            if (!isEmptyDisable && disable !== null) {
                methods.disableTabs.call(elem, tabs, disable);
            }
            if (isEmptyDisable && disable !== null) {
                methods.disableTabs.call(elem, tabs, []);
            }

            //Validar habilitacion del tab
            if (!isEmptyEnable) {
                methods.enableTabs.call(elem, tabs, enable);
            }

            //Validar tabs a ocultar
            if (!isEmptyHide) {
                methods.hideTabs.call(elem, tabs, hide);
            }

            //Validar tabs a mostrar
            if (!isEmptyShow) {
                methods.showTabs.call(elem, tabs, show);
            }

            //Validar activacion del tab
            if (isInit && active !== null) {
                methods.activeTabs.call(elem, tabs, active);
            }


            return elem;
        }
        , disableTabs: function(tabs, disable) {
            disable = (disable.length ? disable : [disable]);
            if ($.type(disable[0]) === 'array')
                disable = disable[0];

            var elem = $(this);
            if (!testEmpty.call(this, argToAr.call(this, disable))) {
                $.each(disable, function(index, valor) {

                    $.each(tabs, function(index_, valor_) {
                        var li = elem.find('li[data-tab-index=' + valor_.index + ']');


                        if (valor === valor_.index) {

                            if (!li.hasClass('tab-disabled')) {
                                li.addClass('tab-disabled');
                                valor_.element.addClass('link-disabled');
                                valor_.state = 'disabled';

                                if (li.hasClass('has-sub')) {
                                    li.removeClass('open');
                                    li.find('li').removeClass('open');
                                    li.find('ul').slideUp();
                                }

                            }
                        }
                    });
                });
            }

            if (testEmpty.call(this, argToAr.call(this, disable))) {

                $.each(tabs, function(index_, valor_) {
                    var li = elem.find('li[data-tab-index=' + valor_.index + ']');


                    if (li.hasClass('tab-disabled')) {
                        li.removeClass('tab-disabled');
                        valor_.element.removeClass('link-disabled');
                        valor_.state = null;

                        if (li.hasClass('has-sub')) {
                            li.removeClass('open');
                            li.find('li').removeClass('open');
                            li.find('ul').slideUp();
                        }
                    }
                });
            }

        }
        , enableTabs: function(tabs, enable) {
            var elem = $(this);
            enable = (enable.length ? argToAr.call(this, enable) : [enable]);
            $.each(enable, function(index, valor) {

                $.each(tabs, function(index_, valor_) {
                    var li = elem.find('li[data-tab-index=' + valor_.index + ']');

                    if (valor === valor_.index) {
                        if (li.hasClass('tab-disabled')) {
                            li.removeClass('tab-disabled');
                            valor_.element.removeClass('link-disabled');
                            valor_.state = null;
                        }
                    }
                });
            });
        }
        , activeTabs: function(tabs, active) {
            active = (active.length ? active[0] : active);
            var bool = true;
            var elem = $(this);

            $.each(tabs, function(index_, valor_) {
                if (!bool) {
                    return false;
                }
                var li = elem.find('li[data-tab-index=' + valor_.index + ']');

                if (active === valor_.index) {
                    if (!li.hasClass('tab-disabled')) {
                        if (li.data('parent-index') >= 0 && !$('li[data-tab-index=' + li.data('parent-index') + ']').hasClass('open')) {
                            methods.triggerParentActivation.call(elem, tabs, li);
                        }
                        methods.triggerActivation.call(elem, tabs, valor_);
                        bool = false;
                        return false;
                    }
                }
            });
        },
        hideTabs: function(tabs, hide) {
            hide = (hide.length ? argToAr.call(this, hide) : [hide]);
            var elem = $(this);
            $.each(hide, function(index, valor) {

                $.each(tabs, function(index_, valor_) {
                    var li = elem.find('li[data-tab-index=' + valor_.index + ']');

                    if (valor === valor_.index) {
                        if (!li.hasClass('tab-hidden')) {
                            li.addClass('tab-hidden');
                            li.hide();
                            valor_.state = 'hidden';
                            return false;
                        }
                    }
                });
            });
        },
        showTabs: function(tabs, show) {
            show = (show.length ? argToAr.call(this, show) : [show]);
            var elem = $(this);
            $.each(show, function(index, valor) {

                $.each(tabs, function(index_, valor_) {
                    var li = elem.find('li[data-tab-index=' + valor_.index + ']');

                    if (valor === valor_.index) {
                        if (li.hasClass('tab-hidden')) {
                            li.removeClass('tab-hidden');
                            li.show();
                            valor_.state = null;
                        }
                    }
                });
            });
        },
        isEmpty: function(select) {

            if ($.type(select) === 'array') {
                return select.length === 0;
            }

            select = typeof select === 'object' ?
                    $(select).val() :
                    select;
            return $.trim(select) === '' || select === undefined || select === null;
        },
        length: function(elem) {

            if (testEmpty.call(this, elem))
                return 0;
            return elem.length;
        },
        argsToArray: function() {
            var arr = [];
            if (arguments[0] && arguments[0].length === 0)
                return [];
            for (i in arguments[0]) {
                if ($.type(arguments[0][0]) === 'array') {
                    for (j in arguments[0][0]) {
                        arr.push(arguments[0][0][j]);
                    }
                } else {
                    arr.push(arguments[0][i]);
                }
            }
            return arr;
        }, triggerActivation: function(tabs, element) {

            var parent = $(this); // elemento plugin
            var data = parent.data('menu-vertical');
            var id = element.id.replaceAll('href', '');
            var div = $('div[id=' + id + ']').attr('child-class', 'mv');
            var a = $(parent).find('a[id=' + element.id + ']');
            $.each(tabs, function(inde, data) {
                if (data.id === a.attr('id')) {
                    return false;
                }
                data.state = null;
            });

            if (div.length > 0 &&
                    !a.hasClass('link-disabled') &&
                    !a.parent().hasClass('tab-disabled') &&
                    !a.parent().hasClass('tab-parent') &&
                    !a.parent().hasClass('tab-activated') &&
                    $.inArray(element.state, ['disabled', 'hidden']) <= -1) {

                var dataOut = {
                    oldPanel: div,
                    newPanel: div.clone().attr({id: div.attr('id').replaceAll('href', '')})
                };
                $(parent).triggerHandler( 'onBeforeMenuActivate' );
                if (data.defaults.beforeActivate.call(parent, dataOut) === false) {
                    return false;
                }

                parent.find('li[data-tab-index != -1]')
                        .not(a.parent())
                        .not('li.tab-parent');

                parent.find('li.tab-activated').removeClass('tab-activated');
                parent.find('a.link-activated').removeClass('link-activated');

                a.data('index', element.index);

                a.parent().data('index', element.index);
                a.parent().addClass('tab-activated');
                a.addClass('link-activated');



                $('div[child-class=mv]').hide();

                div.fadeIn('fast');
                element.state = 'activated';
                setTimeout(function() {
                    $(parent).triggerHandler( 'onMenuActivate' );
                    parent.data('menu-vertical').defaults.activate.call(parent, dataOut);
                }, 200);
            }

        },
        triggerParentActivation: function(array, element) {
            var elem = this;
            var parent_id = element.data('parent-index');
            if (!parent_id && parent_id !== 0)
                return;
            var obj;
            array.each(function(a, b) {
                obj = a;
                if (a.index === parent_id) {

                    if (!a.parent.hasClass('open')) {
                        methods.enableTabs.call(elem, array, a.index);
                        a.element.click();
                        methods.triggerParentActivation.call(elem, array, a.parent);
                    }
                    return false;
                }
            });
        },
        getTabs: function() {
            var argument = argToAr(arguments[0]);
            var datas = $(this).data('menu-vertical').menuIds;
            var divName;
            var len = argument.length;
            var elements = [];
            for (i in argument) {
                for (j in datas) {
                    if (datas[j].index === argument[i]) {
                        divName = datas[j].id.replace(/^href/g, '');
                        elements.push({element: datas[j].element[0], div: $('div#' + divName)[0], parent: datas[j].parent[0]});
                    }
                }
            }
            return elements.length > 1 ? elements : elements[0];
        }

    };

    var testEmpty = methods.isEmpty;
    var testLength = methods.length;
    var argToAr = methods.argsToArray;
    initSubMenu = function() {
        var $this = this;
        var fnO = $this.data('menu-vertical').defaults.onParentOpened || function() {
        };
        var fnC = $this.data('menu-vertical').defaults.onParentClosed || function() {
        };
        $('li.has-sub>a', $this).on('click', function() {
            $(this).removeAttr('href');
            var element = $(this).parent('li');
            if (element.hasClass('open')) {
                element.removeClass('open');
                element.find('li').removeClass('open');
                element.find('ul').slideUp();
                $this.triggerHandler('parentClosed', [this]);
                fnC.call($this, this);
            }
            else {
                $this.triggerHandler('parentOpened', [this]);
                fnO.call($this, this);
                element.addClass('open');
                element.children('ul').slideDown();
                element.siblings('li').children('ul').slideUp();
                element.siblings('li').removeClass('open');
                element.siblings('li').find('li').removeClass('open');
                element.siblings('li').find('ul').slideUp();
            }
        });
        $('ul>li.has-sub>a', this).append('<span class="holder"></span>');
    };

    includeChildren = function(ul, array, isChildren) {
        ul.parent('li').addClass('tab-parent');
        ul.children('li').each(function() {
            array.push(this);
            $(this).children('a').attr('data-clicker', $(this).children('a').attr('id').substring(4));
            if (isChildren) {
                $(this).attr({'data-children': "true"});
                $(this).attr({
                    'data-parent-clicker': $(this).parent().parent().children('a').attr('data-clicker')
                });
            }
            var ul = $(this).children('ul');
            if (ul.length > 0) {
                includeChildren.call(this, ul, array, true);
            }
        });
    };
    initConfig = function() {

        var elem = this,
                data = $(elem).data('menu-vertical');
        if (data.defaults.renderAt !== null) {

            if ($('[id=' + data.defaults.renderAt + ']').lenght === 0) {
                $.error('No se encontro contenedor ' + '[id=' + data.defaults.renderAt + ']');
                return;
            } else {
                $('[id=' + data.defaults.renderAt + ']').css($.extend(true, {
                    display: 'inline-table',
                    height: 'auto',
                    'vertical-align': 'top',
                    position: 'relative',
                    padding: '10px 10px',
                    border: '1px solid #327E04',
                    background: 'rgb( 251, 251 , 251 )'
                }, data.defaults.renderStyle));
            }

        } else {
            $.error('No se encontr? contenedor ' + '[id=' + data.defaults.renderAt + ']');
            return;
        }


        $(elem).addClass('menu-vertical').css({
            display: 'inline-table',
            'max-width': '16%',
            'vertical-align': 'top',
            position: 'relative',
            width: data.defaults.tabWidth || '15%'
        });
        var arrLi = []; //$(elem).children('ul').children('li');
        var arrUl = $(elem).children('ul');
        $(arrUl).each(function(a, b) {

            includeChildren.call(elem, $(this), arrLi);
        });
        $(arrLi).each(function(a, b) {
            $(this).attr('data-tab-index', a);
            var ul = $(this).children('ul');
            if (ul.length > 0) {
                $(this).addClass('has-sub');
                ul.children('li').attr({'data-parent-index': a});
            }
            if (!$(this).data('children')) {
                $(this).addClass('ui-state-default')
                        .hover(function() {
                            $(this).addClass('ui-state-hover')
                        },
                                function() {
                                    $(this).removeClass('ui-state-hover')
                                });
            }

        });
        initSubMenu.call(elem);
        var arrA = $(arrLi).children('a');
        var ids = [];
        $(arrA).each(function(a, b) {
            ids.push({
                id: $(this).attr('id'),
                index: a,
                element: $(this),
                parent: $(this).parent(),
                super_parent: null,
                state: null
            });
        });
        $.each(ids, function(a, b) {

            var id = b.id.replaceAll('href', '');
            $('div[id=' + id + ']').attr('child-class', 'mv').hide();
            var a = $(elem).find('a[id=' + b.id + ']');
            a.click(function(e) {
                methods.triggerActivation.call(elem, ids, b);
                e.preventDefault();
                e.stopPropagation();
            });
        });
        data = $.extend({}, data, {menuIds: ids});
        data.defaults.init = true; // se ha iniciado el menu
        data.methods = methods;
        $(elem).data('menu-vertical', data);

        var index_ = 0;
        var obj = arrA[index_];
        while (true) {
            if (!$(obj).parent().hasClass('tab-parent')) {
                methods.activeTabs.call(elem, ids, index_);
                break;
            } else {
                $(obj)[0].click();
            }
            index_++;
            obj = arrA[index_];
        }
    };
    jQuery.fn.extend({
        menu_vertical: function() {

            var method = arguments[0];
            if (methods[method]) {
                arguments = Array.prototype.slice.call(arguments, 1);
                return methods[method].apply(this, arguments);
            } else if (typeof (method) === 'object' || !method) {
                method = methods.init;
                return method.apply(this, arguments);
            } else {
                jQuery.error('Metodo ' + method + ' no existe en jQuery.menu_vertical');
                return this;
            }
        }
    });
    $.each('disable enable active hide show'.split(' '), function(a, b) {
        jQuery.fn[ b + 'Tab'] = function() {

            var data;
//            arguments = arguments[0];
            if ((data = jQuery(this).data('menu-vertical')) !== 'undefined') {
                methods[ b + 'Tabs'].call(this, data.menuIds, arguments);
            } else {
                $.error('No es una instancia del menu_vertical ');
            }
            return this;
        };
    });
    jQuery.fn.getTabs = function() {
        if (jQuery(this).data('menu-vertical') !== 'undefined') {
            return methods.getTabs.call(this, arguments);
        } else {
            $.error('No es una instancia del menu_vertical ');
        }
    }

    jQuery.fn.menu_vertical.defaults = {
        liClasses: ['tab-disabled', 'tab-activated', 'tab-hidden', 'tab-parent', 'ui-state-default', 'has-sub', 'open'],
        aClasses: ['link-disabled', 'link-activated'],
        liAttributes: ['data-tab-index', 'data-tab-id', 'data-parent-clicker', 'data-parent-index', 'data-children'],
        aAttributes: ['data-clicker'],
        divAttibutes: ['child-class'],
        styles: {
        },
        renderAt: null,
        updateComponetSize:false,
        renderStyle: {
            'max-width': '80%',
            'max-height': '100%',
            width: '80%'
        },
        beforeActivate: function(a) {

        },
        activate: function(b) {

        },
        menuIds: {
        },
        active: null,
        enable: null,
        disable: null,
        init: false
    };
})(jQuery);
