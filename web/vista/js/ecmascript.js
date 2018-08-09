function init(evt)
{
    if (window.svgDocument == null)
    {
        svgDocument = evt.target.ownerDocument;
    }

    tooltip = svgDocument.getElementById('tooltip');
    tooltip_bg = svgDocument.getElementById('tooltip_bg');
    tooltip_emp = svgDocument.getElementById('tooltip_emp');
    tooltip_bg_emp = svgDocument.getElementById('tooltip_bg_emp');

}

function ShowTooltip(evt, mouseovertext)
{

//    if (window.svgDocument == null)
//    {
//        svgDocument = evt.target.ownerDocument;
//    }
//
//    tooltip = svgDocument.getElementById('tooltip');
//    tooltip_bg = svgDocument.getElementById('tooltip_bg');

    var arrTooltip = mouseovertext.split(',');

    tooltip.setAttributeNS(null, "x", evt.clientX - 10);
    tooltip.setAttributeNS(null, "y", evt.clientY - 60);
    tooltip.firstChild.data = arrTooltip[0]; // mouseovertext;

    tooltip_bg.setAttributeNS(null, "x", evt.clientX - 20);
    tooltip_bg.setAttributeNS(null, "y", evt.clientY - 75);

    var child = tooltip.firstChild;
    //loop over all childs
    i = 0;
    while (child != null) {
        //see if child is a tspan and has child nodes
        if (child.nodeName == "tspan" && child.hasChildNodes()) {
            //see if firstChild is of nodeType "text"
//					if (child.firstChild.nodeType == 2) 
//						child.firstChild.x = evt.clientX + 21;
//						child.firstChild.setAttributeNS(null,"x",evt.clientX+21);
            if (child.firstChild.nodeType == 3) {
                i++;
                child.firstChild.nodeValue = arrTooltip[i];
                child.setAttributeNS(null, "x", evt.clientX - 10);

                //	alert("child's text content="+child.firstChild.nodeValue);
            }
        }
        child = child.nextSibling;
    }

    //tooltip.firstElementChild.childNodes.item(2).data = 'Que mas';
    $(tooltip).css({'z-index': 10000, position: 'fixed'});
    $(tooltip_bg).css({'z-index': 9999, position: 'fixed'});

    tooltip.setAttributeNS(null, "visibility", "visible");
    tooltip_bg.setAttributeNS(null, "visibility", "visibile");

}

function HideTooltip(evt)
{
//    if (window.svgDocument == null)
//    {
//        svgDocument = evt.target.ownerDocument;
//    }
//
//    tooltip = svgDocument.getElementById('tooltip');
//    tooltip_bg = svgDocument.getElementById('tooltip_bg');

    tooltip.setAttributeNS(null, "visibility", "hidden");
    tooltip_bg.setAttributeNS(null, "visibility", "hidden");
}

function ShowTooltip_emp(evt, mouseovertext)
{

//    if (window.svgDocument == null)
//    {
//        svgDocument = evt.target.ownerDocument;
//    }
//
//    tooltip = svgDocument.getElementById('tooltip');
//    tooltip_bg = svgDocument.getElementById('tooltip_bg');

    var arrTooltip = mouseovertext.split(',');

    tooltip_emp.setAttributeNS(null, "x", evt.clientX - 60);
    tooltip_emp.setAttributeNS(null, "y", evt.clientY - 50);
    tooltip_emp.firstChild.data = arrTooltip[0]; // mouseovertext;

    tooltip_bg_emp.setAttributeNS(null, "x", evt.clientX - 70);
    tooltip_bg_emp.setAttributeNS(null, "y", evt.clientY - 70);

    var child = tooltip_emp.firstChild;
    //loop over all childs
    i = 0;
    while (child != null) {
        //see if child is a tspan and has child nodes
        if (child.nodeName == "tspan" && child.hasChildNodes()) {
            //see if firstChild is of nodeType "text"
//					if (child.firstChild.nodeType == 2) 
//						child.firstChild.x = evt.clientX + 21;
//						child.firstChild.setAttributeNS(null,"x",evt.clientX+21);
            if (child.firstChild.nodeType == 3) {
                i++;
                child.firstChild.nodeValue = arrTooltip[i];
                child.setAttributeNS(null, "x", evt.clientX - 60);

                //	alert("child's text content="+child.firstChild.nodeValue);
            }
        }
        child = child.nextSibling;
    }

    //tooltip.firstElementChild.childNodes.item(2).data = 'Que mas';
    $(tooltip_emp).css({'z-index': 10000, position: 'fixed'});
    $(tooltip_bg_emp).css({'z-index': 9999, position: 'fixed'});

    tooltip_emp.setAttributeNS(null, "visibility", "visible");
    tooltip_bg_emp.setAttributeNS(null, "visibility", "visibile");

}

function HideTooltip_emp(evt)
{
//    if (window.svgDocument == null)
//    {
//        svgDocument = evt.target.ownerDocument;
//    }
//
//    tooltip = svgDocument.getElementById('tooltip');
//    tooltip_bg = svgDocument.getElementById('tooltip_bg');

    tooltip_emp.setAttributeNS(null, "visibility", "hidden");
    tooltip_bg_emp.setAttributeNS(null, "visibility", "hidden");
}


function showblank(evt, source)
{
    source.setAttribute('opacity', '1');
    ShowTooltip(evt, source.getAttribute('mouseovertext'));
}

function hideblank(evt, source)
{
    source.setAttribute('opacity', '0.5');
    HideTooltip(evt);
}


function show(evt, objects, source)
{
    // source.setAttribute('opacity', '0.5');
    ShowTooltip(evt, source.getAttribute('mouseovertext'));
    if (objects !== "none") {

        var svgdoc = evt.target.ownerDocument;
        var arrObjects = objects.split(',');

        for (i = 0; i < arrObjects.length; i++)
        {
            nodo = "P" + arrObjects[i] + "S";
//alert(nodo);
            obj = svgdoc.getElementById(nodo);
            obj.setAttribute("visibility", "visible");
        }
    }
}

function hide(evt, objects, source)
{
    source.setAttribute('opacity', '1');
    HideTooltip(evt);
    if (objects !== "none") {

        var svgdoc = evt.target.ownerDocument;
        var arrObjects = objects.split(',');

        for (i = 0; i < arrObjects.length; i++)
        {
            nodo = "P" + arrObjects[i] + "S";
            obj = svgdoc.getElementById(nodo);
            obj.setAttribute("visibility", "hidden");
        }
    }
}


function show_emp(evt, objects, source)
{
    // source.setAttribute('opacity', '0.5');
    ShowTooltip_emp(evt, source.getAttribute('mouseovertext'));
    if (objects !== "none") {

        var svgdoc = evt.target.ownerDocument;
        var arrObjects = objects.split(',');

        for (i = 0; i < arrObjects.length; i++)
        {
            nodo = "P" + arrObjects[i] + "S";
//alert(nodo);
            obj = svgdoc.getElementById(nodo);
            obj.setAttribute("visibility", "visible");
        }
    }
}

function hide_emp(evt, objects, source)
{
    source.setAttribute('opacity', '1');
    HideTooltip_emp(evt);
    if (objects !== "none") {

        var svgdoc = evt.target.ownerDocument;
        var arrObjects = objects.split(',');

        for (i = 0; i < arrObjects.length; i++)
        {
            nodo = "P" + arrObjects[i] + "S";
            obj = svgdoc.getElementById(nodo);
            obj.setAttribute("visibility", "hidden");
        }
    }
}
