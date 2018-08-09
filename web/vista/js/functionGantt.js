var from = new Date().getTime();
var to = from*1.04;
var conTask = 0;
var ganttModelSource = [
		{
			name: " ",
			desc: " ",
			values: [{
				from: "/Date("+from+")/",
				to: "/Date("+to+")/",
				customClass:"ganttTransparent"
				}
			]
		},
		{
			name: " ",
			desc: " ",
			values: [{
				from: "/Date("+from+")/",
				to: "/Date("+to+")/",
				customClass:"ganttTransparent"
				}
			]
		},
		{
			name: " ",
			desc: " ",
			values: [{
				from: "/Date("+from+")/",
				to: "/Date("+to+")/",
				customClass:"ganttTransparent"
				}
			]
		},
		{
			name: " ",
			desc: " ",
			values: [{
				from: "/Date("+from+")/",
				to: "/Date("+to+")/",
				customClass:"ganttTransparent"
				}
			]
		},
		{
			name: " ",
			desc: " ",
			values: [{
				from: "/Date("+from+")/",
				to: "/Date("+to+")/",
				customClass:"ganttTransparent"
				}
			]
		},
		{
			name: " ",
			desc: " ",
			values: [{
				from: "/Date("+from+")/",
				to: "/Date("+to+")/",
				customClass:"ganttTransparent"
				}
			]
		},
		{
			name: " ",
			desc: " ",
			values: [{
				from: "/Date("+from+")/",
				to: "/Date("+to+")/",
				customClass:"ganttTransparent"
				}
			]
		},
		{
			name: " ",
			desc: " ",
			values: [{
				from: "/Date("+from+")/",
				to: "/Date("+to+")/",
				customClass:"ganttTransparent"
				}
			]
		},
		{
			name: " ",
			desc: " ",
			values: [{
				from: "/Date("+from+")/",
				to: "/Date("+to+")/",
				customClass:"ganttTransparent"
				}
			]
		}
];

var ganttSettings = {
	source : ganttModelSource,
	months : ["Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"],
	dow : ["D", "L", "M", "M", "J", "V", "S"],
	navigate: "scroll",
	scale: "months",
	maxScale: "months",
	minScale: "days",
	itemsPerPage: 50/*,
	onItemClick: function(data) {
		alert("Item clicked - show some details, data: "+data);
	},
	onAddClick: function(dt, rowId) {
		alert("Empty space clicked - add an item!");
	},
	onRender: function() {
		if (window.console && typeof console.log === "function") {
			console.log("chart rendered");
		}
	}*/
};

$(document).on('click','.depLine',function(e){
	var lineas = $(".depLine");
	lineas.hide();
	
	var x = e.pageX;
	var y = e.pageY;
	
	var bar = $( document.elementFromPoint(x, y) );
	$(bar).click();

	lineas.show();
});

function createGantt(sel,displayGantt,callBackOnItemClick){
	ganttSettings.onItemClick = callBackOnItemClick;
	var sel = $(sel);
		
	if(displayGantt)
		sel.show();
	else
		sel.hide();
	
	sel.gantt(ganttSettings);
        conTask = 0;
}

function deps(id,arraDep){
	for( var j = 0; j < arraDep.length; j++){
		for(var i = 0; i < ganttSettings.source.length; i++){
			var task = ganttSettings.source[i];
			var id_task = task.values[0].id;
			
			if( id_task == arraDep[j] ){
				//console.log("La Actividad Cuyo id es: "+id+", va a depender de: "+task.values[0].dataObj.actcxa);
				var values = task.values;
				//console.log("tipo de values: "+typeof(values)+", tipo original: "+typeof( task.values ));
				//console.log("objetos en values:");
				//console.log(values);
				//console.log("cantidad de values: "+values.length);
				//console.log("cantidad de values original: "+task.values.length);
				for( var z = 0 ; z < values.length; z++ ){
					var dep = values[z].dep;
					if( dep == id ){
						//console.log("Ya Existe");
						break;
					}else{
						//console.log("No existe pero puede existir mas adelante");
						if( z == ( values.length - 1) ){
							//console.log("Definitivamente no existe vamos a agregar un value");
							var value = $.extend(true,{},values[z]);
							value.dep = id;
							//console.log("value antes de meter en la actividad que dependera de aquella con id");
							task.values[task.values.length] = value;
							//console.log("valores totales de task");
							//console.log(task);
							break;
						}
					}
				}
			}
		}
		//console.log("---------------------------------------");
	}
}


/* Funcion que no Agrega el id que recibe como parametro al input 
de la vista que que se utiliza para recuperar ese mismo valor para actualizar*/
function addTask(contGantt,refresh,fase,nameTask,id,fromTask,toTask,color,arrayDeps,dataObj){
	contGantt = $(contGantt);
	
	id = id != "" ? id : getNewIdTask();
		
	
	var arraFromTask = fromTask.split("-");
	var arraToTask = toTask.split("-");
	
	fromTask = new Date( parseInt(arraFromTask[0]), parseInt(arraFromTask[1])-1, parseInt(arraFromTask[2]) );
	toTask = new Date( parseInt(arraToTask[0]), parseInt(arraToTask[1])-1, parseInt(arraToTask[2]) );
	
	var newTask = {
		name : fase,
		desc : nameTask,
		values : [{
				id : id,
				label : nameTask,
				from : "/Date("+fromTask.getTime()+")/",
				to : "/Date("+toTask.getTime()+")/",
				customClass : color,
				dataObj : dataObj,
				dep : ""
		}]
	}
	console.log("task");
        console.log(conTask);
	ganttSettings.source[conTask] = newTask;
	conTask++;
	
	deps(id,arrayDeps);
	
	if(refresh)
		refreshGantt(contGantt);
}

function addTask2(contGantt,refresh,fase,nameTask,id,input_update,fromTask,toTask,color,arrayDeps,dataObj){
	contGantt = $(contGantt);
	input_update = $(input_update);
	
	id = id != "" ? id : getNewIdTask();
		
	
	var arraFromTask = fromTask.split("-");
	var arraToTask = toTask.split("-");
	
	fromTask = new Date( parseInt(arraFromTask[0]), parseInt(arraFromTask[1])-1, parseInt(arraFromTask[2]) );
	toTask = new Date( parseInt(arraToTask[0]), parseInt(arraToTask[1])-1, parseInt(arraToTask[2]) );
	
	var newTask = {
		name : fase,
		desc : nameTask,
		values : [{
				id : id,
				label : nameTask,
				from : "/Date("+fromTask.getTime()+")/",
				to : "/Date("+toTask.getTime()+")/",
				customClass : color,
				dataObj : dataObj,
				dep : ""
		}]
	}
	
	ganttSettings.source[conTask] = newTask;
	conTask++;
	
	input_update.val(id);
	
	deps(id,arrayDeps);
	
	if(refresh)
		refreshGantt(contGantt);
}

function deleteTask(contGantt,id){
	clearDeps(id);
	
	for( var i = 0; i < ganttSettings.source.length; i++ ){
		var task = ganttSettings.source[i];
		var id_task = task.values[0].id;
		
		if( id_task == id ){
			ganttSettings.source.splice(i,1);
			break;
		}
	}
	
	refreshGantt(contGantt);
	
}

function clearGantt(){
    //ganttSettings.source ="";
    console.log(ganttSettings.source);
    for( var i = 0; i < ganttSettings.source.length; i++ ){
		var task = ganttSettings.source[i];
                ganttSettings.source.splice(i,1);
	}
}

function clearDeps(id){
	for( var i = 0; i < ganttSettings.source.length ; i++){
		var task = ganttSettings.source[i];
		var values = task.values;

		for( var j = 0; j < values.length; j++){
			var value = values[j];
			var dep_value = value.dep;
			if( dep_value == id ){
				value.dep = "";
			}
		}
		
	}
}

function updateTask(contGantt,idecxa_up,refresh,fase,nameTask,fromTask,toTask,arrayDeps,dataObj){
	contGantt = $(contGantt);
	//console.log("update task");
	var arraFromTask = fromTask.split("-");
	var arraToTask = toTask.split("-");
	
	fromTask = new Date( parseInt(arraFromTask[0]), parseInt(arraFromTask[1])-1, parseInt(arraFromTask[2]) );
	toTask = new Date( parseInt(arraToTask[0]), parseInt(arraToTask[1])-1, parseInt(arraToTask[2]) );
	
	var task = getTaskById(idecxa_up);
	//console.log("task");
	//console.log(task);
	
	setValuesToTask(task,fase,nameTask,fromTask,toTask,dataObj);
	
	clearDeps(idecxa_up);
	
	deps(idecxa_up,arrayDeps);
	
	if(refresh)
		refreshGantt(contGantt);
}

function setValuesToTask(task,fase,nameTask,fromTask,toTask,dataObj){
	task.name = fase;
	task.desc = nameTask;
	
	for(var i = 0 ; i < task.values.length ; i++){
		task.values[i].label = nameTask;
		task.values[i].from = "/Date("+fromTask.getTime()+")/";
		task.values[i].to = "/Date("+toTask.getTime()+")/";
		task.values[i].dataObj = dataObj;
	}

}



function refreshGantt(contGantt){
	contGantt = typeof(contGantt)== "string" ? $(contGantt) : contGantt;

	contGantt.find("div:first").fadeOut("slow",function(){
			contGantt.empty();
			var new_gantt = $("<div style='display:none' class='gantt'></div>");
			contGantt.append(new_gantt);
			new_gantt.gantt(ganttSettings);
			new_gantt.fadeIn("slow");
	});
}

function getTaskById(id){
	var task;
	
	for( var i = 0; i < ganttSettings.source.length; i++){
		var task_aux = ganttSettings.source[i];
		var id_aux = task_aux.values[0].id;
		if( id_aux == id ){
			task = task_aux;
			break;
		}
	}	
	
	return task;
}


function existTask(nrocxa){
	for(var i = 0 ; i < ganttSettings.source.length; i++){
		var task = ganttSettings.source[i];
		var nrocxa_task = typeof(task.values[0].dataObj)!="undefined" ? task.values[0].dataObj.NROCXA : "";
		//console.log('jujuju'+task.values[0].dataObj);
		if( nrocxa_task == nrocxa )
			return true;
	}
	
	return false;
}


function loadTasksWithDep(contGantt,semi_source){
	
	for( var i = 0; i < semi_source.length; i++ ){
		var nrocxa = semi_source[i].NROCXA;
		var idecxa = semi_source[i].IDECXA;
		var dep = semi_source[i].IDEPRE;
                
		if( (!existTask(nrocxa)) ){
			var nrocro = semi_source[i].NROCRO;
			var actcxa = semi_source[i].ACTCXA;
			var dspcxa = semi_source[i].DSPCXA;
			var bgncxa = semi_source[i].BGNCXA;
			var endcxa = semi_source[i].ENDCXA;
			var codprs = semi_source[i].CODPRS;
			addTask(contGantt,false,"",actcxa,idecxa,bgncxa,endcxa,"ganttOrange",[],semi_source[i]);
		}
		
		deps(idecxa,[dep]);
                
		
		
	}
        if(semi_source.length>0){
            ganttSettings.source.length = semi_source.length;
        }
        
	refreshGantt(contGantt);
}

function loadTasksWithDepAjax(contGantt,url,data){
	$.ajax({
		url : url,
		type : "POST",
		data : data,
		dataType : "json",
		success : function(json,ts,jqxhr){
			console.log("tareas a cargar revisidas del servidor");
			console.log(json);
			loadTasksWithDep(contGantt,json.semi_source);
		},
		error : function(jqxhr){
		
		},
		beforeSend : function(jqxhr,s){
		
		}
	});
}

function loadTasks(contGantt,semi_source){
	for( var i = 0; i < semi_source.length; i++ ){
		var nrocxa = semi_source[i].nrocxa;
		var nrocro = semi_source[i].nrocro;
		var actcxa = semi_source[i].actcxa;
		var idecxa = semi_source[i].idecxa;
		var dspcxa = semi_source[i].dspcxa;
		var bgncxa = semi_source[i].bgncxa;
		var endcxa = semi_source[i].endcxa;
		var codprs = semi_source[i].codprs;
		//mandar dependencias en array, mejorar la siguiente sentencia
		addTask(contGantt,false,"",actcxa,idecxa,bgncxa,endcxa,"ganttOrange","",semi_source[i]);
	}
	
	refreshGantt(contGantt);
}

function loadTasksAjax(contGantt,url,data){
	$.ajax({
		url : url,
		type : "POST",
		data : data,
		dataType : "json",
		success : function(json,ts,jqxhr){
			loadTasks(contGantt,json.semi_source);
		},
		error : function(jqxhr){
		
		},
		beforeSend : function(jqxhr,s){
		
		}
	});
}

function getNewIdTask(){
	var source = ganttSettings.source;
	
	var max_id = 0;
	
	for(var i = 0; i < source.length; i++){
		var desc = $.trim( source[i].desc );
		
		if( desc != "" ){
			var id = source[i].values[0].id;
			var num_id = parseInt(id.split("_")[1]);
			
			if( num_id > max_id )
				max_id = num_id;
		}
	}
	
	return "act_"+(max_id + 1);
}

function getNamesTasks(){
	var source = ganttSettings.source;
	var names = [];
	
	for( var i = 0; i < source.length; i++ ){
		var task = source[i];
		names[names.length] = task.desc;
	}
	
	return names;
}

function getIdsTasks(){
	var source = ganttSettings.source;
	var ids = [];
	
	for( var i = 0; i < source.length; i++ ){
		var task = source[i];
		ids[ids.length] = task.values[0].id;
	}
	
	return ids;
}

function getNamesAndIdsTasks(){
	var source = ganttSettings.source;
	var ni = [];
	
	for( var i = 0; i < source.length; i++ ){
		var task = source[i];
		var name = task.desc;
		var id = task.values[0].id;
		
		var json_task = {
			name : name,
			id : id
		};
		
		ni[ni.length] = json_task;
	}
	
	return ni;
}

function getDataObjsPredecessors(from){
	from = new Date( from.replace("-","/") );
	var arraDataObjs = [];
	
	for( var i = 0; i < ganttSettings.source.length; i++ ){
		var task = $.extend(true,{},ganttSettings.source[i]);
		var descTask = $.trim(task.desc);
		
		if( descTask != "" ){
			var timeToTask = getDateMili(task.values[0].to);
			if( timeToTask < from.getTime() )
				arraDataObjs[arraDataObjs.length] = task.values[0].dataObj;
			
		}
	}
	return arraDataObjs;
}

function getTasksPredecessors(from){
	from = new Date( from.replace("-","/") );
	var arraTask = [];
	
	for( var i = 0; i < ganttSettings.source.length; i++ ){
		var task = $.extend(true,{},ganttSettings.source[i]);
		var descTask = $.trim(task.desc);
		
		if( descTask != "" ){
			var timeToTask = getDateMili(task.values[0].to);
			if( timeToTask < from.getTime() )
				arraTask[arraTask.length] = task;
		}
	}
	return arraTask;
}

function getDateMili(date_gantt){
	var mili;
		
	var indexPl = date_gantt.indexOf("(");
	var indexPr = date_gantt.indexOf(")");
	
	mili = parseInt( date_gantt.substring(indexPl+1,indexPr) );
		
	return mili;
}

//Funcion que obtiene los valores de un parametro de query string en un array
function getArrayValuesQS(qs){
	//console.log("Dentro de la Funcion, qs: "+qs);
	var array = [];
	
	var igualdades = qs.split("&");
	//console.log("igualdades");
	//console.log(igualdades);
	for(var i = 0 ; i < igualdades.length ; i++){
		var igualdad = igualdades[i];
		var split1 = igualdad.split("=");
		var val = split1[1];
		//console.log("primer id a guardar: "+val);
		array[array.length] = val;
	}
	//console.log("Array final");
	//console.log(array);
	return array;
}