/*
 * Autor Ing. Jacobo Llanos
 * Created 2/09/2016 10:32:38 AM
 * File formDepartments
 */

clsma.$requestSetup({
    controller: [
        {name: Rutac + '/Adm/' + 'formDepartments'.substring(4), path: 'event'}
    ],
    returning: 'object'
});

$(function() {

    conf_panel('90%', 'auto', 'panel', {
        Guardar: saveDepartament,
        Nuevo: newDepartament
    });
    setIconos();
    configTabs();
    
    
    $('#pais').search({
        nrosch : 'PAISES',
        pkey:'idepai'
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
                departament();
                showButton("Nuevo");
                hideButton("Guardar");
            } else if (id === 'Basic') {
                showButton("Guardar");
                hideButton("Nuevo");
                $('#msg').html(writeInfoMsg('<br/>En esta Opción puede hacer:<br/>1.Registro de Departamentos.<br/\n\
                                                >-Debe llenar los campos solicitados.<br/>-Agregar Departamentos o modificar datos en Departamentos ya registradas.'));
            }
        }
    });

}


function departament() {
    clsma.$request({
        data: ['LIST'],
        loading: true}).done(function(data) {
        $('.lstDpt').empty().html(data.html);
    });

}

function formatterDelete(valorColumna, opciones, rowFila) {
    return clsma.$deleteTemplate('delDepartament(\'' + rowFila.IDEDPT + '\')');
}


function formatterEdit(valorColumna, opciones, rowFila) {
    return clsma.$genericTemplate('editDepartament(\'' + rowFila.IDEDPT + '\')', 'Editar Departamento', Rutav + '/vista/img/Edit.png' );
}


function editDepartament(id) {
    clsma.$request({
        data: ['EDIT', {id: id}],
        loading: true}).done(function(data) {
              fillForm('container', data.smadpt);
              $("#idepai").val(data.smadpt.IDEPAI);
              $("#idepaiShw").val(data.smadpt.NOMPAI);
              clsma.tab.enableTab(1, 2);
              clsma.tab.activeTab(1);
    });
}


function delDepartament(id) {
    var row = $("#jqDpt").getRowData(id);

    clsma.$confirm('¿Desea Eliminar el Departamento ['+row.NOMDPT+']?').Aceptar(function() {
        if (row.IDEDPT === "") {
            $("#jqDpt").jqGrid('delRowData', id);
        }
        else {
            deleteDepartament(id);
        }
    });
}


function deleteDepartament(id) {

    clsma.$request({
        data: ['DELETE', {id: id}],
        loading: true
    }).then(function(data) {
         if(data.exito==="OK"){ 
          $("#jqDpt").jqGrid('delRowData', id);
         } else {
             clsma.$msg(data.msg, "", data.exito);
         }
    });
}


function newDepartament() {
    $('#idedpt').val('');
    $('#coddpt').val('');
    $('#nomdpt').val('');
    $('#npqdpt').val('');
    $("#idepai").val('');
    $("#idepaiShw").val('');
    clsma.tab.enableTab(1, 2);
    clsma.tab.activeTab(1);
}

function saveDepartament() {


    if ($("#coddpt").val().trim().toString() === "") {
        clsma.$msg("No ha indicado el código", function() {
        }, 'ERROR');
        return;
    }
    if ($("#npqdpt").val().trim().toString() === "") {
        clsma.$msg("No ha indicado el identificador", function() {
        }, 'ERROR');
        return;
    }
    if ($("#nomdpt").val().trim().toString() === "") {
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
