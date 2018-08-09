$(document).ready(function() {
    $('#cssmenu').prepend('<div id="menu-button">Menu</div>');
    $('#cssmenu #menu-button').on('click', function() {
        var menu = $(this).next('ul');
        if (menu.hasClass('open')) {
            menu.removeClass('open');
        }
        else {
            menu.addClass('open');
        }

        var ul = $('#cssmenu').find('ul:first');
        if (ul.length > 0) {

            if ($('#menu-button').is(':visible')) {
                ul.find('li.accor').each(function() {
                    $(this).click(function() {
                        if ($(this).find('ul').hasClass('opened')) {
                            $(this).find('ul').removeClass('opened');
                            $(this).find('ul').slideDown('fast');
                        } else {
                            $(this).find('ul').addClass('opened');
                            $(this).find('ul').slideUp('fast');
                        }
                    });
                    $(this).find('ul').addClass('opened');
                    $(this).find('ul').slideUp('fast');
                });
            }
        }
    });
});