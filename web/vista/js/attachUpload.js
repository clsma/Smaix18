/* 
* Autor: Carlos Pinto Jimenez
 */


(function() {

    var defaults = {
        width: 500,
        height: 100,
        name: null,
        rVista: null,
        formats: ['.png', '.jpg', '.pdf'],
        filesize: 3,
        numFiles: null
    };


    var method = {
        destroy: function() {

            var $this = $(this);

            var container = $(this).data('container');
            var datas = container.data('attach');
            if (isEmpty(datas)) {
                $.error('Element is not a instance of jQuery.attach plugin');
            }
            container.remove();
            $this.removeData('container');

            $this.unbind('fileThrow');
            $this.unbind('fileRemove');
            return $this;
        },
        option: function( ) {
            var $this = $(this);
            var args = $a(arguments);
            var metho = args[0] || '';
            args = args.slice(1);
            metho = metho.toLowerCase();
            switch (metho) {
                case 'list':
                    method._initList.call($this, args);
                    return $this;
                    break;
                case 'upload':
                    method._upload.call($this, args);
                    return $this;
                    break;
                default :
                    return $this;
            }
        },
        _initList: function(options) {
            options = options[0];
            options.idPlug = this[0].id;
            if (isEmpty(options.servletPath)) {
                $.error('Contexto del controlador de la aplicación no debe estar nulo param [ servletContext ]');
            }
            var container = $(this).data('container');
            container.data('listOptions', options);
            var datas = {
                url: options.servletPath + '/Attach/UploadEvents',
                data: {
                    event: 'LISTFILES',
                    path: options.path,
                    idPlug: options.idPlug
                }
            }
            genericAjax(datas)
                    .then(function(data) {
                        if (!isEmpty(data)) {
                            $('.divList').empty().html(data);
                        } else {
                            $('.divList').empty().html('<b><i>No se han encontrado archivos </i></b>');
                        }
                    });
        },
        _upload: function(options) {
            options = options[0];
            options.idPlug = this[0].id;
            if (isEmpty(options.servletPath)) {
                $.error('Contexto del controlador de la aplicación no debe estar nulo param [ servletContext ]');
            }
            var container = $(this).data('container');
            container.data('uploadOptions', options);

            var formats = '';
            container.data('attach').formats.each(function() {
                formats += ',' + this.slice(0).replace(/\./g, '')
            });
            formats = formats.substring(1);
            var datas = {
                url: options.servletPath + '/Attach/UploadEvents?event=UPLOADFILES&path='
                        + options.path + '&idPlug='
                        + options.idPlug + '&formats='
                        + formats,
                data: $(this).attach().getFormData(),
                isFile: true
            }
            genericAjax(datas)
                    .then(function(data) {
                        container.data('uploadResult', data);
                    });
            return this;
        }
    };

    function init(options) {
        var element = this,
                $this = $(this),
                iframe_ = null,
                file_,
                plus_,
                remove_;

        element.getFormData = function() {
            var form = new FormData();
            var container = $(this).data('container');
            var name = encodeURI(container.data('attach').name);
            var files = container.data('element');

            for (i in files) {
                if (isEmpty((files[i].object)[0].files[0]))
                    continue;
                form.append(name, (files[i].object)[0].files[0], _accentsTidy.call((files[i].object)[0].files[0].name));

            }
            return form;

        };
        element.hasFiles = function() {
            var container = $(this).data('container');
            var files = container.data('element') || [];
            var file = files.first();
            if (!isEmpty(file.object[0].files))
                return file.object[0].files.length > 0;
        };


        $(this).bind('fileThrow', fileThrow);
        $(this).bind('fileRemove', fileRemove);

        return element.each(function() {
            var id = this.id;
            var settings;

            var container = $(this).find('div[ id=containerAttacher' + this.id + ']');
            if (container.length === 0) {
                container = $('<div/>').attr({id: 'containerAttacher' + this.id});
                $(this).data('container', container);
            } else {
                container = $(this).data('container');
            }
            settings = container.data('attach');
            if (!settings) {
                settings = $.extend({}, true, defaults, options);
            } else {
                return false;
            }
            if (settings.name === null || settings.name === '' || settings.name === undefined) {
                $.error('Debe asignar nombre al elemento');
                return;
            }

            if (settings.rVista === null || settings.rVista === '' || settings.rVista === undefined) {
                $.error('Debe asignar la ruta de contexto de la aplicacion para llegar a las imágenes');
                return;
            }

            container.data('attach', settings);

            var table = $('<table id="attacher_' + id + '"/>').css({width: '100%'});

            var tr = $('<tr/>');
            var td = $('<td colspan="6"/>');

            td.html('<b><i>Formato de archivos permitidos : ' + $.toJSON(settings.formats) + '</i></b>').css({padding: '5px 0 20px 10px'});
            td.appendTo(tr);
            tr.appendTo(table);

            tr = $('<tr id="tr_file_' + id + '"/>');
            td = $('<td/>');
            var fileId = _UId(settings.name);
            var file = $('<input type="file" name="' + settings.name + '" id="' + fileId + '"/>');
            file.change(function() {
                _validchange(this, id);
            });
            var a, img;
            var div;
            div = $('<div/>').css({width: '100%', height: '100%', padding: '10px', margin: '0 auto'});
            var files = [];
            files.push({id: file.attr('id'), object: file});
            container.data('element', files);

            td.text(' Archivo:  ');
            td.css({'text-align': 'right', width: '10%'});
            td.appendTo(tr);

            td = $('<td/>');
            td.css({'text-align': 'right', width: '10%'});
            file.appendTo(td);
            td.appendTo(tr);

            td = $('<td/>');
            td.css('text-align', 'left');
            a = $('<a id="add_' + fileId + '" fileId="' + fileId + '" href="#" onclick="_addFile_(\'' + settings.name + '\',\'' + id + '\')"/>');
            img = $('<img/>').css({width: '20px', height: '20px'}).attr({src: settings.rVista + '/vista/img/plus.png'});
            img.appendTo(a);
            if (isEmpty(settings.numFiles))
                td.append(a);
            a = $('<a id="del_' + fileId + '" fileId="' + fileId + '" href="#"  onclick="_delFile_(this, \'' + id + '\')"/>');

            var del = img.clone().attr({src: settings.rVista + '/vista/img/cross.png'});
            del.appendTo(a);
            td.append(a);
            tr.append(td);

            tr.appendTo(table);
            div.append(table);

            table = $('<table id="lstFiles_' + id + '"/>').css({width: '100%'});
            tr = $('<tr/>');
            td = $('<td/>').attr({colspan: 6});
            td.append('<div id="lstFiles' + _UId(settings.name) + '" class="divList" div_container="' + id + '" />').css({padding: '10px 0 20px 10px'});
            td.appendTo(tr);
            tr.appendTo(table);


            div.append(table);
            container.prepend(div);
            container.appendTo($this);


        });

    }
    _UId = function(name) {
        return  name + '' + parseInt(Math.random() * 99);
    }
    _addFile_ = function(name, id) {

        var container = $('#' + id).data('container');
        var files = container.data('element') || [];

        var parent = $('#tr_file_' + id);
        var tr = parent.clone();
        var file = tr.find('input[type=file]');
        var idFile = _UId(name);
        file.attr({name: name, id: idFile});
        file.change(function() {
            _validchange(this, id);
        });

        var a = tr.find('a[id*=add]');
        a.attr({id: 'add_' + idFile, fileId: idFile});
        a = tr.find('a[id*=del]');
        a.attr({id: 'del_' + idFile, fileId: idFile});

        files.push({id: file.attr('id'), object: file});
        parent.parent().append(tr);

        container.data('element', files);

    }

    _delFile_ = function($this, id) {
        var container = $('#' + id).data('container');
        var files = container.data('element') || [];

        var count = $('#attacher_' + id).find('tr#tr_file_' + id);
        if (count.length > 1) {
            var parent = $($this).parent().parent();
            var id_ = $($this).attr('fileId');
            var file = id_;

            for (i in files) {
                if (files[i].id === file) {
                    files.splice(i, 1);
                    parent.remove();
                    break;
                }
            }
            container.data('element', files);

        }
    }
    _validchange = function($this, id) {

        var container = $('#' + id).data('container');
        var options = container.data('attach');

        var archivo = $($this).val();
        var extension = (archivo.substring(archivo.lastIndexOf("."))).toLowerCase();
        var _formats = options.formats.length === 0 ?
                ['.png', '.jpg', '.pdf'] : options.formats;
        var bool = false;

        if (archivo === '' || archivo === null || archivo === undefined) {
            return true;
        }

        for (i in _formats) {

            if (extension.replace(/\./, '') === _formats[i].replace(/\./, '')) {
                bool = true;
                break;
            }

        }
        if (!bool) {
            show_message('Extension de archivo no permitida , solo archivos ' + $.toJSON(_formats));
            $($this).val('');
            return false;
        }

        var _fileSize = options.filesize;
        var size = $this.files[0].size;

        if (_fileSize < (size / 1000000)) {
            show_message('Tamaño de archivo muy grande  , solo imágenes tamaño ' + (_fileSize) + ' mb.');
            $($this).val('');
            return false;
        }


    }

    _accentsTidy = function() {
        var r = this.toLowerCase();
        r = r.replace(new RegExp("\\s", 'g'), "_");
        r = r.replace(new RegExp("[äã]", 'g'), "a");
        r = r.replace(new RegExp("æ", 'g'), "ae");
        r = r.replace(new RegExp("ç", 'g'), "c");
        r = r.replace(new RegExp("[ĕë]", 'g'), "e");
        r = r.replace(new RegExp("[ïĭ]", 'g'), "i");
        r = r.replace(new RegExp("ň", 'g'), "n");
        r = r.replace(new RegExp("[öő]", 'g'), "o");
        r = r.replace(new RegExp("óé", 'g'), "oe");
        r = r.replace(new RegExp("[ü]", 'g'), "u");
        r = r.replace(new RegExp("[űü]", 'g'), "y");
        return r;
    };


    jQuery.fn.attach = function() {
        var args = $a(arguments);
        var metho = args[0];
        args = args.slice(1);
        if (!isEmpty(metho)) {
            if (Object.isString(metho)) {
                if (isEmpty(method[metho])) {
                    $.error('Method [ ' + metho + ' ] is not defined as an attach Plugin Method');
                }

                return method[metho].apply(this, args);
            } else {
                if (!Object.canbehash(metho)) {
                    $.error('Arguments must be a Javascript object ');
                }
                return init.call(this, metho);
            }
        } else {
            return init.call(this, metho);
        }


    }

    jQuery.fn.uploadResult = function(func) {

        if (!$(this).data('container')) {
            $.error(' %s & is not a attach Element'.StringFormat(this.id));
        }

        if (Object.isFunction(func)) {

            var container = $(this).data('container');
            var result = container.data('uploadResult') || '';

            func.call(this, result);

        }

    };

})(jQuery);

function viewFileAttach(a, b, object) {
    var parent = $(this).data('parent');
    var attacher = $('#' + $('#' + parent).parent().attr('div_container'));
    var container = attacher.data('container');
    var sett = $(container).data('attach');
    var arr_viewrs = $(container).data('attach_viewers') || [];
    var uid = _UId('attach_view_' + b.rowId);
    arr_viewrs.push({id: uid, fileName: object.fileName, path: object.fileLocation, viewpath: sett.rVista});
    $(container).data('attach_viewers', arr_viewrs);
    var id = $(attacher).attr('id');
    var templ = '<div style="text-align:center"><a href="#" id="%s" onclick="javascript:$(\'#%s\').trigger(\'fileThrow\',\'%s\')"><img src="%s/vista/img/htm.gif" style="width:20px;height:20px"></a></div>';
    return  templ.StringFormat(uid, id, uid, sett.rVista);
}
function deleteFileAttach(a, b, object) {
    var parent = $(this).data('parent');
    var attacher = $('#' + $('#' + parent).parent().attr('div_container'));
    var container = attacher.data('container');
    var sett = $(container).data('attach');
    var arr_viewrs = $(container).data('attach_removers') || [];
    var uid = _UId('attach_remove_' + b.rowId);
    arr_viewrs.push({id: uid, fileName: object.fileName, path: object.fileLocation, viewpath: sett.rVista});
    $(container).data('attach_removers', arr_viewrs);
    var id = $(attacher).attr('id');
    var templ = '<div style="text-align:center"><a href="#" id="%s" onclick="javascript:$(\'#%s\').trigger(\'fileRemove\',\'%s\')"><img src="%s/vista/img/delete.png" style="width:20px;height:20px"></a></div>';
    return  templ.StringFormat(uid, id, uid, sett.rVista);
}
;

function fileRemove(event, idElem) {
    var elem = $(this);
    var container = $(this).data('container');
    var ids = container.data('attach_removers') || [];
    var obj = ids.select(function() {
        return this.id === idElem;
    })[0];

    genericAjax({
        url: Rutac + '/Attach/UploadEvents',
        data: {
            event: 'DELETEFILE',
            path: obj.path,
            file: obj.fileName
        }
    }).then(function(data) {
        elem.attach('option', 'list', container.data('listOptions'));
    });
}

function fileThrow(event, idElem) {
    var elem = $(this);
    var container = elem.data('container');
    var ids = container.data('attach_viewers') || [];
    var obj = ids.select(function() {
        return this.id === idElem;
    })[0];
    var templ = '<a href="%s" target="_blank"></a>';
    var a = $(templ.StringFormat((obj.viewpath + obj.path + obj.fileName)));
    $('body').append(a);
    a[0].click();
    a.remove();
}

