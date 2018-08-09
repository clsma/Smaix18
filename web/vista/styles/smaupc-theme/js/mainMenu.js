text = "Bienvenidos a nuestra universidad"; // The text to scroll
color1 = "#333333"; // original text color
color2 = "white"; // new character color
fontsize = "2"; // font size of text 
speed = 100; // how fast to rotate to next character 
// time is in milliseconds, (i.e. 1000 = 1 second)
i = 0;

window.onload = trigger;

function showAlertaSesion(show)
{
    if (show)
    {
        var timeRestante = (tiempoCierre - countInteval) / 60;
        if (timeRestante % 2 != 0)
        {
            timeRestante = parseInt("" + timeRestante) + 1;
        }
        get("div_alerta_sesion").innerHTML = "Su sesion terminara en " + timeRestante + " minutos";
        get("div_alerta_sesion").style.display = "block";
    }
    else
        get("div_alerta_sesion").style.display = "none";
}

function redirigir(url, target)
{
    target = Trim(target) != '' ? target : 'display';

    if (Trim(url) == '/smauic/servlet/Valid/Login?logout=Out')
    {
        window.fullscreen = 'no';
    }

    if (eval('top.' + target) != null)
    {
        eval('top.' + target + '.location.href = url;');
    }
    else
        window.location.href = url;
}


//inicializa el efecto
function trigger()
{
    var width = 385;
    //udc.style.left = window.screen.width/2 - width/2;
    width = 240;
    //smp.style.left = window.screen.width/2 - width/2;
    //smp.style.top = 54;
//    usr.style.left = window.screen.width - 400;
//    usr.style.top = 110;
    //udc.style.top = 20;

    //udc.style.display = 'block';
    //smp.style.display = 'block';

    show5();
    setInterval("changeCharColor()", speed);
}

/*
 Upper Left Corner Live Clock Script- © Dynamic Drive (www.dynamicdrive.com)
 For full source code, 100's more DHTML scripts, and TOS,
 visit http://www.dynamicdrive.com
 */

function show5()
{
    liveclock.style.top = 100;
    if (!document.layers && !document.all && !document.getElementById)
        return

    var Digital = new Date()
    var hours = Digital.getHours()
    var minutes = Digital.getMinutes()
    var seconds = Digital.getSeconds()
    var dn = "AM"

    if (hours > 12)
    {
        dn = "PM"
        hours = hours - 12
    }
    if (hours == 0)
        hours = 12
    if (minutes <= 9)
        minutes = "0" + minutes
    if (seconds <= 9)
        seconds = "0" + seconds
    //change font size here to your desire
    myclock = "" + hours + ":" + minutes + ":" + seconds + " " + dn + "";
    if (document.layers)
    {
        document.layers.liveclock.document.write(myclock)
        document.layers.liveclock.document.close()
    }
    else if (document.all)
        liveclock.innerHTML = myclock
    else if (document.getElementById)
        document.getElementById("liveclock").innerHTML = myclock
    setTimeout("show5()", 1000)
}

document.write("<div id='smp' style='position:absolute;left: 300;top: 35;font-style: italic;width: 240;display: none'></div>");

function changeCharColor()
{
    if (navigator.appName == "Microsoft Internet Explorer")
    {
        str = "<center><font size=" + fontsize + " face = 'VERDANA'><font color=" + color1 + ">";
        for (var j = 0; j < text.length; j++)
        {
            if (j == i) {
                str += "<font color=" + color2 + " face = 'VERDANA'>" + text.charAt(i) + "</font>";
            }
            else
            {
                str += text.charAt(j);
            }
        }
        str += "</font></font></center>";
        smp.innerHTML = str;
    }
    (i == text.length) ? i = 0 : i++; // reset after going through all letters
}

function minimiza()
{
    window.blur();
}
