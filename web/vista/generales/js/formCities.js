/*
 * Autor Ing. Jacobo Llanos
 * Created 2/09/2016 10:32:38 AM
 * File formCities
 */

clsma.$requestSetup({
    controller: [
        {name: Rutac + '/Adm/' + 'formCities'.substring(4), path: 'event'}
    ],
    returning: 'object'
});

$(function() {

    conf_panel('90%', 'auto', 'panel', {
        Guardar: saveCity,
        Nuevo: newCity
    });
    setIconos();
    configTabs();
    
    
    $('#dpto').search({
        nrosch : 'DEPTOS',
        pkey:'idedpt'
    });
    
});

function configTabs() {
    genTabs('tabs', 'container');
    clsma.tab = $('#tabs').menu_vertical({
        renderAt: 'container',
        beforeActivate: function(a) {
            var id = a.newPanel.attr('id');
            if (id === 'List') {
                this.disableTab(1, 2);
                city();
                showButton("Nuevo");
                hideButton("Guardar");
            } else if (id === 'Basic') {
                showButton("Guardar");
                hideButton("Nuevo");
                $('#msg').html(writeInfoMsg('<br/>En esta Opción puede hacer:<br/>1.Registro de Ciudades.<br/\n\
                                                >-Debe llenar los campos solicitados.<br/>-Agregar Ciudades o modificar datos en Ciudades ya registradas.'));
            }
        }
    });

}


function city() {
    clsma.$request({
        data: ['LIST'],
        loading: true}).done(function(data) {
        $('.lstCiu').empty().html(data.html);
    });

}

function formatterDelete(valorColumna, opciones, rowFila) {
    return clsma.$deleteTemplate('delCity(\'' + rowFila.IDECIU + '\')');
}


function formatterEdit(valorColumna, opciones, rowFila) {
    return clsma.$genericTemplate('editCity(\'' + rowFila.IDECIU + '\')', 'Editar Ciudad', Rutav + '/vista/img/Edit.png' );
}


function editCity(id) {
    clsma.$request({
        data: ['EDIT', {id: id}],
        loading: true}).done(function(data) {
              fillForm('container', data.smaciu);
              $("#idedpt").val(data.smaciu.IDEDPT);
              $("#idedptShw").val(data.smaciu.NOMDPT);
              clsma.tab.enableTab(1, 2);
              clsma.tab.activeTab(1);
    });
}


function delCity(id) {
    var row = $("#jqCiu").getRowData(id);

    clsma.$confirm('¿Desea Eliminar la Ciudad ['+row.NOMCIU+']?').Aceptar(function() {
        if (row.IDECIU === "") {
            $("#jqCiu").jqGrid('delRowData', id);
        }
        else {
            deleteCity(id);
        }
    });
}


function deleteCity(id) {

    clsma.$request({
        data: ['DELETE', {id: id}],
        loading: true
    }).then(function(data) {
         if(data.exito==="OK"){ 
          $("#jqCiu").jqGrid('delRowData', id);
         } else {
             clsma.$msg(data.msg, "", data.exito);
         }
    });
}


function newCity() {
    $('#ideciu').val('');
    $('#codciu').val('');
    $('#nomciu').val('');
    $('#npqciu').val('');
    $("#idedpt").val('');
    $("#idedptShw").val('');
    clsma.tab.enableTab(1, 2);
    clsma.tab.activeTab(1);
}

function saveCity() {


    if ($("#codciu").val().trim().toString() === "") {
        clsma.$msg("No ha indicado el código", function() {
        }, 'ERROR');
        return;
    }
    if ($("#npqciu").val().trim().toString() === "") {
        clsma.$msg("No ha indicado el identificador", function() {
        }, 'ERROR');
        return;
    }
    if ($("#nomciu").val().trim().toString() === "") {
        clsma.$msg("No ha indicado el nombre", function() {
        }, 'ERROR');
        return;
    }

    var formulario = getFormData('Basic');

    clsma.$confirm('¿ Desea enviar los datos ?').Aceptar(function() {
        clsma.$request({
            data: ['SAVE', {formulario: $.toJSON(formulario)}],
            loading: true
        }).then(function(data) {
            clsma.$msg(data.msg, function() {
                clsma.tab.activeTab(0);
            }, 'OK');
        });

    });

}
