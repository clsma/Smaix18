/* 
 * Plugin que nos permite subir una foto a nuestro servidor
 
 * Carlos pinto Jimenez
 * 2015-07-23
 */

$(function() {

    $.uploadPhoto = function(options) {

        var nom_file,
                extension,
                $this = {},
                defaults = {
                    path: null,
                    controller: null,
                    control: '/Adm/PhotoUpload',
                    imgName: null,
                    folder: null,
                    imageFormat: [],
                    imageSize: 3000,
                    imageSizeFormat: 'mb',
                    whenCompleted: function(event) {

                    }
                };
        defaults = $.extend({}, defaults, options);
        if (defaults.path === null || defaults.controller === null) {
            $.error('No existe el path del proyecto');
            return;
        }

        if (defaults.folder === null) {
            $.error('Especifique la ruta donde se guardará la imagen atributo (folder) , esta ruta vendra despues de (upload)');
            return;
        }

        if (defaults.imgName === null) {
            defaults.imgName = (Math.random() * 99 + 446846) * 99;
        }



        var functions = {
            _controller: defaults.controller + defaults.control,
            _path: defaults.path,
            init: function() {

                var div = $('<div/>'),
                        body = $('body'),
                        form = $('<form/>'),
                        p,
                        file = $('<input type="file"/>'),
                        img = $('<img/>'),
                        button = $('<button/>'),
                        style = $('<style/>'),
                        panelDiv = $('<div/>');

                try {
                    $('#panelCamera').dialog('destroy');
                } catch (e) {
                    console.log(e);
                }
                $('#panelCamera').remove();

                style.html('.ui-button .ui-icon-smacam {background-image: url(' + defaults.path + '/vista/img/smacam.png);}');
                style.appendTo(body);
                body.find('div#panelImg').remove();
                div.attr({id: 'panelImg', align: 'center'});
                form.attr({id: 'photop', name: 'photop'});
                p = $('<p/>');

                var div_ = $('<div/>').css({position: 'relative'});

                img.attr({src: defaults.path + '/vista/img/init/no_image_icon.gif', id: 'imagendita'});
                img.appendTo(div_);
                div_.appendTo(p);
                div_.attr('id', 'panelCamCam');
                p.appendTo(form);

                panelDiv.css({
                    'min-width': '46%',
                    'font-size': '15px',
                    'text-align': 'left',
                    'min-height': '15%',
                    'font-family': 'helvetica, arial, sans-serif',
                    'border-radius': '5px',
                    'background-color': '#4F4F4F',
                    'background-image': 'url(' + defaults.path + '/vista/img/camera.png)',
                    'background-repeat': 'no-repeat',
                    '-webkit-transition': 'background-color .13s ease-out',
                    '-moz-border-radius': ' 5px',
                    'background-position': 'right center',
                    '-webkit-border-radius': '5px',
                    position: 'absolute',
                    color: 'white',
                    padding: '4px',
                    right: '25%',
                    bottom: '3px',
                    cursor: 'pointer'
                }).hover(function() {
                    $(this).css({'background-color': '#1F1D1D'});
                }, function() {
                    $(this).css({'background-color': '#4F4F4F'});
                }).text('Agregar foto').appendTo(div_);



                p = $('<p/>');

                file.attr({id: 'filUpload', name: 'filUpload'});
                file.appendTo(p);
                p.appendTo(form);

                p = $('<p/>');

                button.attr({id: 'bton'}).text('Enviar');
                button.appendTo(p);
                p.appendTo(form);
                form.appendTo(div);
                div.appendTo(body);


                var panelCamera = $('<div/>');

                $('<video/>').attr({
                    id: "video",
                    width: "640",
                    height: "480",
                    autoplay: ""
                }).appendTo(panelCamera);

                $('<canvas/>').attr({
                    id: "canvas",
                    width: "640",
                    height: "480"
                }).css({
                    display: 'none'
                }).appendTo(panelCamera);
                panelCamera.attr({
                    tittle: 'Captura de foto',
                    id: 'panelCamera'
                }).appendTo(body);

                panelDiv.attr('id', 'panelCam');

                panelDiv.click(function() {
                    functions.generarVideo({
                        panel: div,
                        button: button,
                        file: file,
                        img: img,
                        panelCamera: panelCamera

                    });
                });
                functions.config_All.call(this, {
                    panel: div,
                    button: button,
                    file: file,
                    img: img,
                    panelCamera: panelCamera

                });
            },
            config_All: function(options) {



                conf_form('20%', 'auto', options.panel.attr('id'));

                options.panel.dialog('open');
                functions.button.call(this, options);
                options.file.change(function() {
                    if ($(this).val() === '')
                        return;
                    if (functions.validar_extension_tamaño.call(this, options)) {
                        functions.loadTemp.call(this, options);
                    }
                });
                disableButton(options.button, true);
                return;
            }, validar_extension_tamaño: function(options) {
                var archivo = options.file.val();
                extension = (archivo.substring(archivo.lastIndexOf("."))).toLowerCase();
                var _formats = functions.length(defaults.imageFormat) === 0 ?
                        ['.png', '.jpg'] : defaults.imageFormat;
                var bool = false;
                for (i in _formats) {

                    if (extension === _formats[i]) {
                        bool = true;
                        break;
                    }

                }
                if (!bool) {
                    show_message('Extension de archivo no permitida , solo imágenes ' + $.toJSON(_formats));
                    options.file.val('');
                    return false;
                }

                var _fileSize = defaults.imageSize;
                var size = options.file[0].files[0].size;
                if (_fileSize < functions.validSize(size)) {
                    show_message('Tamaño de archivo muy grande  , solo imágenes tamaño ' + (_fileSize / 1000) + ' mb.');
                    options.file.val('');
                    return false;
                }



                enableButton(options.button, true);
                return true;
            }, loadTemp: function(options) {

                var dfd = genericAjax({
                    url: functions._controller +
                            '?events=TEMP&arrExt=' +
                            $.toJSON(defaults.imageFormat),
                    data: new FormData(get('photop')),
                    isFile: true,
                    loading: true
                });
                dfd.then(function(data) {

                    data = $.parseJSON(data);
                    if (data.exito === 'OK') {
//                        setTimeout(function() {
                        $(options.img).attr({src: functions._path + '/utility/upload/temp/' + data.archivo})
                                .css({width: '180px', height: '200px'});
                        nom_file = data.archivo;
                        options.file.val('');
//                        }, 2000);

                    } else {
                        show_message(data.msg, '', 'ERROR');
                        options.file.val('');
                    }
                });
            }, button: function(options) {
                configButton(options.button, '-', function() {
                    saveFile(options);
                });
                function saveFile(options) {
                    var dfd = genericAjax({
                        url: functions._controller + '?events=SAVETEMP',
                        data: {
                            img: nom_file,
                            identImage: defaults.imgName,
                            folder: defaults.folder,
                            ext: extension
                        },
                        loading: true
                    });
                    dfd.done(function(data) {
                        data = $.parseJSON(data);
                        if (data.exito === 'OK') {
                            show_message(data.msg, function() {

                                options.panel.dialog('close');
                                defaults.whenCompleted.call(this, {
                                    nomfile: defaults.imgName + extension
                                });
                            }, 'OK');
                        } else {
                            show_message(data.msg, '', 'ERROR');
                            return;
                        }

                    });
                }
            }, buttonCamera: function(options) {

                options.panelCamera.dialog('option', 'buttons', {
                    Capturar: function() {
                        capturar();
                    },
                    Guardar: function() {
                        saveCaptura(options);
                    },
                    Refrescar: function() {
                        refrescar();
                    }
                });
                hideButton('Guardar');
                setIcon('Guardar', '-disk');
                setIcon('Refrescar', '-refresh');
                setIcon('Capturar', '-smacam');
                function refrescar() {
                    $('#canvas').css({
                        display: 'none'
                    });
                    $('#video').css({
                        display: 'block'
                    });

                    hideButton('Guardar');
                    showButton('Capturar');
                }

                function capturar() {
                    var canvas = document.getElementById("canvas"),
                            context = canvas.getContext("2d"),
                            video = document.getElementById("video");
                    context.drawImage(video, 0, 0, 640, 480);
                    $('#canvas').css({
                        display: 'block'
                    });
                    $('#video').css({
                        display: 'none'
                    });
                    showButton('Guardar');
                    hideButton('Capturar');
                    showButton('Refrescar');
                }

                function saveCaptura(options) {

                    var canvas = document.getElementById("canvas");
                    var dataURL = canvas.toDataURL('image/png');

                    var dfd = genericAjax({
                        url: functions._controller +
                                '?events=TMPCMR&arrExt=' +
                                $.toJSON(defaults.imageFormat),
                        data: {
                            imfCamera: dataURL},
                        loading: true
                    });
                    dfd.then(function(data) {

                        data = $.parseJSON(data);
                        if (data.exito === 'OK') {
                            $(options.img).attr({src: functions._path + '/utility/upload/temp/' + data.archivo})
                                    .css({width: '180px', height: '200px'});
                            nom_file = data.archivo;
                            enableButton(options.button, true);
                            extension = (data.archivo.substring(data.archivo.lastIndexOf("."))).toLowerCase();
                            options.localMediaStream.stop();
                            $('#panelCamera').dialog('close');
                        } else {
                            show_message(data.msg, '', 'ERROR');
                            options.file.val('');
                        }
                    });
                }
            },
            length: function(arr) {

                return arr.length;
            },
            validSize: function(size) {

                switch (defaults.imageSizeFormat) {

                    case 'mb':

                        return (size / 1000000);
                        break;
                    case 'kb':
                        return (size / 1000);
                        break;
                    default:
                        return 0;
                }


            },
            generarVideo: function(options) {
                // Grab elements, create settings, etc.
                var video = document.getElementById("video"),
                        videoObj = {"video": true},
                errBack = function(error) {
                    console.log("Video capture error: ", error.code);
                };


                // Put video listeners into place
                if (navigator.getUserMedia) { // Standard
                    navigator.getUserMedia(videoObj, function(stream) {
                        video.src = stream;
                        video.play();
                        options.localMediaStream = stream;
                    }, errBack);
                } else if (navigator.webkitGetUserMedia) { // WebKit-prefixed
                    navigator.webkitGetUserMedia(videoObj, function(stream) {
                        video.src = window.webkitURL.createObjectURL(stream);
                        video.play();
                        options.localMediaStream = stream;
                    }, errBack);
                } else if (navigator.mozGetUserMedia) { // WebKit-prefixed
                    navigator.mozGetUserMedia(videoObj, function(stream) {
                        video.src = window.URL.createObjectURL(stream);
                        video.play();
                        options.localMediaStream = stream;
                    }, errBack);
                }

                $('#canvas').css({
                    display: 'none'
                });
                $('#video').css({
                    display: 'block'
                });

                try {
                    conf_form('auto', 'auto', options.panelCamera.attr('id'));
                } catch (e) {
                    console.log(e);
                }
                functions.buttonCamera.call(this, options);


                options.panelCamera.dialog({
                    beforeClose: function() {
                        if (options.localMediaStream) {
                            options.localMediaStream.stop();
                        }
                    }
                });
                options.panelCamera.dialog('open');
            }

        }; //end functions

        functions.init.call();
        return $this;
    };
});

