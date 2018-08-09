
/*Funcion que evita la edicion de campos de entrada (input,select,textArea)
cuando lo que se quiere es que se serialize*/
function evitarEdicionDeCampos(campos){
	//console.log("Funcion Evitar");
	$(campos).focus(function(){
		$(this).blur();
	});
}

/*Para comprobar que no se repitan valores en una columna en todas las filas*/
/*ADECUAR*/
function existeNC(campo,tbody){
	campo = $(campo);
	tbody = $(tbody);
	var tds = tbody.find(".inp_codpqk");
	//console.log("inputs "+tds.length);
	var existe = false;
	
	tds.each(function(i,td){
		td = $(td);
		if($.trim(td.val())==$.trim(campo.val())){
			existe = true;
			return false;
		}
	});
	
	return existe;
}

function setConfiguration(con_inp,file_json){
	//console.log("Se llamo setConfiguration()");
	con_inp = $(con_inp);
	
	$.ajax({
		url : "/smaix12/config_form/"+file_json+".json",
		type : "POST",
		dataType : "json",
		success : function(json){
			//console.log("Status: "+textStatus);
			//console.log("Respuesta del servidor");
			//console.log("tama�o del json: "+json.length);
			//console.log("Estructura del json");
			//console.log(json);
			
			json.each(function(i,at){
				//console.log("i="+i+"; at="+at);
			});
		}
	});
	
	/*$.getJSON("/smaix12/config_form/"+file_json+".json",function(json,textStatus,jqxhr){
		console.log("Status: "+textStatus);
		console.log("Respuesta del servidor");
		console.log("tama�o del json: "+json.length);
		console.log("Estructura del json");
		console.log(json);
		
		json.each(function(i,at){
			console.log("i="+i+"; at="+at);
		});
	});*/
}

/******************* ******************************************** ******************************************** *************************/
function replaceMask(string_mask){
	var aux = "";
	string_mask = $.trim(string_mask);
	
	for(var i=0; i<string_mask.length; i++){
		aux = aux + string_mask[i].replace(",","");
	}
	
	aux = aux!="" ? parseInt(aux):0;
	
	return aux;
}

function removeMaskToElements(elements,class_mask){
	elements.each(function(i,e){
		e = $(e);
		if( e.hasClass(class_mask) ){
			var valor = e.val()!="" ? e.val():e.text();
			valor = replaceMask(valor);
			e.val( valor );
		}
	});
}

function removeMaskToElementsSimple(elements){
	elements.each(function(i,e){
		e = $(e);
		var valor = e.val()!="" ? e.val():e.text();
		valor = replaceMask(valor);
		e.val( valor );
	});
}


function calcTotalRubro(inputs,input_total){
	var total = 0;
	inputs.each(function(i,input){
		var cant = replaceMask(input.value);
		total = total + cant;
	});
	
	input_total.val(total);
	/*jq1102(input_total).mask("000,000,000,000,000,000,000,000,000,000", {reverse: true, maxlength: true});
	jq1102(input_total).keyup();*/
	setValuesMaskToInputs(input_total,"000,000,000,000,000,000,000,000,000,000",{reverse: true, maxlength: true});
	
	
	var name = input_total.attr("name");
	calcSubTotales({
			col : {
				campos : $("input[name='"+name+"']"),
				destino_suma : $("#subTotal_"+name)
			}
		},true);
	
	calcFaltaSobra({
		col : {
			destino_suma : $("#subTotal_"+name),
			total_esperado: $("#totalEsperado_"+name),
			aviso : $("#falta_sobra_"+name),
			mask_aviso : true
		}
	});
}

function calcTotalText(inputs,input_total){
	var total = 0;
	inputs.each(function(i,input){
		input = $(input);
		var cant = replaceMask(input.text());
		total = total + cant;
	});
	
	input_total.text(total);
	jq1102(input_total).mask("000,000,000,000,000,000,000,000,000,000", {reverse: true, maxlength: true});
	jq1102(input_total).keyup();
	
	var name = input_total.attr("name");
	calcSubTotales({
			col : {
				campos : $("input[name='"+name+"']"),
				destino_suma : $("#subTotal_"+name)
			}
		},true);
	
	calcFaltaSobra({
		col : {
			destino_suma : $("#subTotal_"+name),
			total_esperado: $("#totalEsperado_"+name),
			aviso : $("#falta_sobra_"+name),
			mask_aviso : true
		}
	});
}

function calcFaltaSobra(json){
	$.each(json,function(key, value){
		var destino_suma = value.destino_suma;
		var total_esperado = value.total_esperado;
		var aviso = value.aviso;
		var mask_aviso = value.mask_aviso;
		
		var val_destino_suma = destino_suma.text();
		var val_total_esperado = total_esperado.text();
		
		var subTotal = replaceMask( val_destino_suma );
		var totalEsperado = replaceMask( val_total_esperado );
		
		var fs = subTotal - totalEsperado;
		var abs_fs = Math.abs(fs);
		
		if( fs < 0 ){
			aviso.html("(+<span>"+abs_fs+"</span>)");
		}else if ( fs > 0){
			aviso.html("(-<span>"+abs_fs+"</span>)");
			
		}else if( fs == 0 ){
			aviso.empty();
		}
		
		aviso.css("color","red");
		
		if(mask_aviso){
			jq1102(aviso).find("span").mask("000,000,000,000,000,000,000,000,000,000", {reverse: true, maxlength: true});
			jq1102(aviso).find("span").keyup();
		}
		
	});
}

function calcSubTotales(json,with_mask,ope){
	
	$.each(json,function(key2,val2){
		var campos = val2.campos;
		var destino_suma = val2.destino_suma;
		
		var total;
		
		if(ope=="x")
			total = 1;
		else
			total = 0;
		
		campos.each(function(i,campo){
			campo = $(campo);
			var val_campo = campo.val();
			//console.log("valor campo: "+val_campo);
			val_campo = replaceMask(val_campo);

			if(ope=="x")
				total = total*val_campo;
			else
				total = total+val_campo;
			
		});
		
		//console.log("Total: "+total);
		destino_suma.text(total).val(total);
		if(with_mask){
			/*jq1102(destino_suma).mask("000,000,000,000,000,000,000,000,000,000", {reverse: true, maxlength: true});
			jq1102(destino_suma).keyup();
			*/
			setValuesMaskToInputs(destino_suma,"000,000,000,000,000,000,000,000,000,000",{reverse: true, maxlength: true});
		}
	});
	
}

function calcSubTotalesWarning(json,val_camp,camp_war,ope){
	val_camp = replaceMask(val_camp);
	$.each(json,function(key2,val2){
		var campos = val2.campos;
		//var destino_suma = val2.destino_suma;
		
		var total;
		
		if(ope=="x")
			total = 1;
		else
			total = 0;
		
		campos.each(function(i,campo){
			campo = $(campo);
			var val_campo = campo.val();
			
			val_campo = replaceMask(val_campo);

			if(ope=="x")
				total = total*val_campo;
			else
				total = total+val_campo;
			
		});
		
		if(total!=val_camp)
			camp_war.css("color","red");
		else
			camp_war.css("color","#A2A2A2");
	});
}

function setValuesMaskToInputs(inps,mask_string,sett_mask){
	//inps param
	inps.each(function(i,inp){
		inp = $(inp);
		clone_inp = inp.clone();
		clone_inp.removeAttr("id").removeAttr("class").removeAttr("name");
		clone_inp.off().unbind();
		clone_inp.mask(mask_string, sett_mask);
		clone_inp.keyup();
		inp.val( clone_inp.val() ).text( clone_inp.text() );
	});
	
}

function serializeRows(elems_row_srz,filter){
	var elements = elems_row_srz;
	var div_aux = $("<div></div>");
	
	elements.each(function(i,e){
		var tr = $(e).parents("tr:first").clone();
		div_aux.append(tr);
	});
	
	var srz = div_aux.find(filter).serialize();
	return srz;
}
/*
function srzEncode(form,filter){
	var srz = form.serialize();
	var srzEncode;
	
	var equals = srz.split("&");
	
	for( var i = 0 ; i < equals.length ; i++){
		var equal = equals[i];
		var name = equal.split("=")[0];
		var 
	}
	
	return srzEncode;
}*/

function serializeElementsByCols(elems_col_srz,filter){
	var elems = $(elems_col_srz);
	
	var div_aux = $("<div></div>");
	
	elems.each(function(i,elem){
		elem = $(elem);
		var td = elem.parents("td:first");
		//console.log("elemento: "+td.find(filter));
		div_aux.append(td.clone());
	});
	//console.log(div_aux);
	return div_aux.find(filter).serialize();
}

function change_datepicker_es(){
	 $.datepicker.regional['es'] = {
       closeText: 'Cerrar',
       prevText: '<Ant',
       nextText: 'Sig>',
       currentText: 'Hoy',
       monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
       monthNamesShort: ['Ene','Feb','Mar','Abr', 'May','Jun','Jul','Ago','Sep', 'Oct','Nov','Dic'],
       dayNames: ['Domingo', 'Lunes', 'Martes', 'Mi�rcoles', 'Jueves', 'Viernes', 'S�bado'],
       dayNamesShort: ['Dom','Lun','Mar','Mi�','Juv','Vie','S�b'],
       dayNamesMin: ['Do','Lu','Ma','Mi','Ju','Vi','S�'],
       weekHeader: 'Sm',
       dateFormat: 'dd/mm/yy',
       firstDay: 1,
       isRTL: false,
       showMonthAfterYear: false,
       yearSuffix: ''
   };
   $.datepicker.setDefaults($.datepicker.regional['es']);
}

function serializeForm( form ){
	form = $(form);
	var srzEncode;
	
	var srz = form.serialize();
	var igualdades = srz.split("&");
	
	for( var i = 0; i < igualdades.length; i++ ){
		 var igualdad = igualdades[i];
		 var key = igualdad.split("=")[0];
		 var value = igualdad.split("=")[1];
		 var valueEncode = encodeURIComponent( value );
		 
		 srzEncode = srzEncode + "&" + key + "=" + valueEncode;
	}
	
	return srzEncode.substring(1);
}


function validOnlyNumber(e){	
	if( e.which != 37 && e.which != 38 && e.which != 39 && e.which != 40 ){
		var el = e.target;
		  var normalize = (function() {
			  var from = "1234567890QWERTYUIOPÑLKJHGFDSAZXCVBNM,qwertyuiopasdfghjklñzxcvbnmÃÀÄÂÈÉËÊÌÍÏÎÒÓÖÔÙÚÜÛãàäâèëêìïîòöôùúüûÇç-\\¨º~#@|!\·$%&/()?'¡¿[^`]+}{¨´><;:.°*_=¬\"",
			      to   = "1234567890",
			      mapping = {};
			 
			  for(var i = 0, j = from.length; i < j; i++ )
			      mapping[ from.charAt( i ) ] = to.charAt( i );
			 
			  return function( str ) {
			      var ret = [];
			      for( var i = 0, j = str.length; i < j; i++ ) {
			          var c = str.charAt( i );
			          if( mapping.hasOwnProperty( str.charAt( i ) ) )
			              ret.push( mapping[ c ] );
			          else
			              ret.push( c );
			      }
			      return ret.join( '' );
			  }
			 
			})();    	  
		  var getCod = $(el).val();
		  var cod = normalize(getCod);
		  $(el).val(cod);
	}
	
}

function stateButtonUI(visible,id_dialog,text_button){
	var panel = $(id_dialog);
	var dialog = panel.parents(".ui-dialog:first");
	var panelButton = dialog.find(".ui-dialog-buttonpane");
	var button = panelButton.find("button:contains('"+text_button+"')");
	//console.log("button");
	//console.log(button.text());
	if( !visible )
		button.attr("disabled","disabled").addClass("ui-state-disabled");
	else
		button.removeAttr("disabled").removeClass("ui-state-disabled");
	
}

/******************* ******************************************** ******************************************** *************************/