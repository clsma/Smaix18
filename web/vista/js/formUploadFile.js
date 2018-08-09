/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function refreshImg(){	  
    var oForm = document.forms.formUpdate;
	  //document.getElementById('Img').src = oForm.File.value;	  
  }
  function valid(){
		if (getValue('File') == '' ){
		    show_message('Ingrese archivo!');
			return false;
		}
		if (getValue('codpho') == '' ){
		    show_message('No existe ningun codigo para actualizar el archivo!');
			return false;
		}
		
		show_message("La imagen fue actualizada exitosamente!!");			
		eval("'"+$("#exec").val()+"'");		
		
		return true;
  }
  function revise(command){	  
	  if(command == "close"){
		  window.close();
	  }
  }