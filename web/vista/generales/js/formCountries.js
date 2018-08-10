/*
 * Autor harrison blanco
 * Created 2/09/2016 10:32:38 AM
 * File formInternationalization
 */

clsma.$requestSetup({
    controller: [
        {name: Rutac + '/Adm/' + 'formCountries'.substring(4), path: 'event'}
    ],
    returning: 'object'
});

$(function() {

    conf_panel('90%', 'auto', 'panel', {
        Guardar: saveCountries,
        Nuevo: newCountries
    });
    setIconos();
    configTabs();

});

function configTabs() {
    genTabs('tabs', 'container');
    clsma.tab = $('#tabs').menu_vertical({
        renderAt: 'container',
        beforeActivate: function(a) {
            var id = a.newPanel.attr('id');
            if (id === 'List') {
                this.disableTab(1, 2);
                Countries();
                showButton("Nuevo");
                hideButton("Guardar");
            } else if (id === 'Basic') {
                showButton("Guardar");
                hideButton("Nuevo");
//                $('#msg').html(writeInfoMsg('<br/>En esta Opción debe:<br/>1.Diligenciar los campos.<br/\n\
//                                                >-Debe llenar los campos solicitados.<br/>-Agregar contactos externos e internos para realizar este paso debe presionar en el boton CONTACTOS del menú.'));
            }
        }
    });

}


function Countries() {
    clsma.$request({
        data: ['LIST'],
        loading: true}).done(function(data) {
        $('.lstPai').empty().html(data.html);
    });

}

function formatterDelete(valorColumna, opciones, rowFila) {
    return clsma.$deleteTemplate('delCountries(\'' + opciones.rowId + '\')');
}


function formatterEdit(valorColumna, opciones, rowFila) {
    return clsma.$genericTemplate('editCountries(\'' + opciones.rowId + '\')', 'Editar País', Rutav + '/vista/img/Edit.png' );
}


function editCountries(id) {
    clsma.$request({
        data: ['EDIT', {id: id}],
        loading: true}).done(function(data) {
              fillForm('container', data.smapai);
              clsma.tab.enableTab(1, 2);
              clsma.tab.activeTab(1);
    });
}


function delCountries(id) {
    var row = $("#jqPai").getRowData(id);

    clsma.$confirm('¿ Desea Eliminar el País ['+row.NOMPAI+']?').Aceptar(function() {
        if (row.NROLNG === "") {
            $("#jqPai").jqGrid('delRowData', id);
        }
        else {
            deleteCountries(id);
        }
    });
}


function deleteCountries(id) {

    clsma.$request({
        data: ['DELETE', {id: id}],
        loading: true
    }).then(function(data) {
         if(data.exito==="OK"){ 
          $("#jqPai").jqGrid('delRowData', id);
         } else {
             clsma.$msg(data.msg, "", data.exito);
         }
    });
}


function newCountries() {
    $('#idepai').val('');
    $('#codpai').val('');
    $('#isapai').val('');
    $('#isbpai').val('');
    $('#nompai').val('');
    clsma.tab.enableTab(1, 2);
    clsma.tab.activeTab(1);
}

function saveCountries() {


    if ($("#codpai").val().trim().toString() === "") {
        clsma.$msg("No ha indicado el código", function() {
        }, 'ERROR');
        return;
    }
    if ($("#isapai").val().trim().toString() === "") {
        clsma.$msg("No ha indicado el código ISA", function() {
        }, 'ERROR');
        return;
    }
    if ($("#nompai").val().trim().toString() === "") {
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
