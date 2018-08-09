package taglib.form;

/**
 * Title:        Proyecto Help
 * Description:  Proyecto para la administracion y manejo de cualquier
 *               Entidad (Proyecto orientado a la Web)
 * Copyright:    Copyright (c) 2005
 * Company:      JDVH Co.
 * @author Jose David Valeta
 * @version 1.0
 */
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import taglib.form.ControlForm;
import javax.servlet.jsp.*;
import java.util.StringTokenizer;
import javax.servlet.jsp.tagext.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import javax.servlet.http.HttpServletResponse;

import mvc.model.ModelSma;

public class GridControlPaged{

  //==========================================================================
  //                Atributos de la Etiqueta
  //==========================================================================
	private ModelSma model = null;
  private Hashtable configGrid = new Hashtable();
  private String color = "#FFFFFF";
  private String height = "200";
  private String align = "center";
  private boolean filter = false;
  private String hg = "30px";
  private String cols = "20";
  private String sqlCmd = "";
  private String javascript = "";
  private int limit = 50;
  private int offset = 0;
  private int offset_old = -1;
  private HttpSession session = null; 
  private HttpServletRequest request = null;
  private HttpServletResponse response = null;
  private StringBuffer strInicio = new StringBuffer();
  private StringBuffer strFin = new StringBuffer();
  private StringBuffer strData = new StringBuffer();


  /**
   * @param model
   */   
  public void setModel(ModelSma model) {
	  this.model = model;
  }

  /**
   * @param configGrid
   */
  public void setConfigGrid(Hashtable configGrid) {
	  this.configGrid = configGrid;
  }

  /**
   * @param color
   */
  public void setColor(String color) {
	  this.color = color;
  }

  /**
   * @param height
   */
  public void setHeight(String height) {
	  this.height = height;
  }
  
  /**
   * @param filter
   */
  public void setFilter(boolean filter) {
	  this.filter = filter;
  }

  /**
   * @param align
   */
  public void setAlign(String align) {
	  this.align = align;
  }
  
  /**
   * @param sqlCmd
   */
  public void setSqlCmd( String sqlCmd ) {
	  this.sqlCmd = sqlCmd;
  }
  
  /**
   * @param setLimit
   */
  public void setLimit( int limit ) {
	  this.limit  = limit ;
  }
  
  /**
   * @param setOffset
   */
  public void setOffset( int offset ) {
	  this.offset = offset;
  }
  
  /**
   * @param setOffset
   */
  public void setOffset_old( int offset_old ) {
	  this.offset_old = offset_old;
  }
  
  /**
   * @param getLimit
   */
  public int getLimit() {
	  return this.limit;
  }
  
  /**
   * @param getOffset
   */
  public int getOffset( ) {
	  return this.offset;
  }
  
  /**
   * @param setSession
   */
  public void setSession( HttpSession session ) {
	  this.session = session;
  }
  
  /**
   * @param setRequest
   */
  public void setRequest( HttpServletRequest request ) {
	  this.request = request;
  }
  
  /**
   * @param setJavscript
   */
  public void setJavascript( String javascript ) {
	  this.javascript = javascript;
  }
  
  /**
   * @param setResponse
   */
  public void setResponse( HttpServletResponse response ) {
	  this.response = response;
  }

  //==================================================
  //             Metodos de Ciclo de Vida
  //==================================================
  
  public String getCode()throws JspException {

	  int w = 0, wg = 0, i = 0, j = 0,y=0, length = 0;
	  String rowTitle = "", title = "", value = "", format = "", field = "";
	  String strControl = "", option = "", strScriptCalendar = "", tooltips = "";
	  String checked = "", javascript = "";
	  String arrColumn[][];
	  String[] arrTrg = {}; 
	  String arrBgColor[] = new String[2];
	  Iterator it;
	  List listData = new LinkedList();
	  List listCmb_ = new LinkedList();
	  List sisDat = new LinkedList();
	  Hashtable dataRow = new Hashtable();
	  StringBuffer str = new StringBuffer(); 
	  String qryCmb = "";
	  String disabled = "";
	  String strScriptPag = ""; // cadena para script de paginacion
	  int count = 0;
	  int countPage = 1;
	  int limitString = 100;
	  int limitPagination = 100;

    try{
    	PrintWriter out = response.getWriter();
	    arrBgColor[0] = this.color;
	    arrBgColor[1] = "#E9E7E2";
	    // Obtener configuraci�n de Columnas
	    arrColumn = (String[][]) configGrid.get("ConfigCol");
	    
	    	    
	    rowTitle += "<tr style='display:none' id='trFilter' height='22px'><th colspan = '" + arrColumn.length + "' bgcolor = '#CCCCCC' align = 'left'>" +
      "<font style='color:#333333' face='Verdana' size = '2'>Filtrar&nbsp;</font><input type ='text' name = 'quickfind' id = 'quickfind' class='text'>" +
      "</th></tr>" ;
	    
	    rowTitle += "        <tr class='boton' height='22px'>\n";
	   
		  // Limpiar matriz
		  for (i = 0; i < arrColumn.length; i++)
			  for (j = 0; j < 8; j++)
			    if (arrColumn[i][j] == null)
			      arrColumn[i][j] = "";
 
	    for (i = 0; i < arrColumn.length; i++){
	      // Titulo de la Columna
	      title = arrColumn[i][1];
	      if (title.equals("")) title = "&nbsp;";
		    // Ancho de la Columna
		    if (!arrColumn[i][2].equals("")){
				  length = Integer.parseInt(arrColumn[i][2]);
				  length = (length / 10) + 3;
				  if (title.length() > length)
				    rowTitle += "          <th width='" + arrColumn[i][2] + "' align='center' ><label title='" + title + "' class = 'label'>" + title.substring(0, length - 3) + "..." + "</label></th>\n";
				  else
				    rowTitle += "          <th width='" + arrColumn[i][2] + "' align='center' class = 'label'>" + title + "</th>\n";
		      w += Integer.parseInt(arrColumn[i][2]);
		    }
	    }
	   
	    rowTitle += "        </tr>\n";
	  	    
	    wg = w + 20;
	    
	    if(filter) hg = "50px";
	    
	    //strInicio.append("<div id='DivGridTable' align='center'>");
	    strInicio.append("<table id='tableData' width='"+wg+"' align='"+this.align+"' cellpadding='0' cellspacing='0' class='simple'>\n  <tr>\n    <td>\n");
	    strInicio.append("      <div id='layTitle' style='position:relative; left:0px; top:0px; width:"+wg+"px; height:"+hg+"; z-index:1; background-color:#ffffff; layer-background-color:#ffffff; border:1px none #000000; visibility:visible; '>\n");
	    strInicio.append("      <table width='"+w+"' border='0' cellspacing='1' cellpadding='1' align='left'>\n");
	    strInicio.append(rowTitle + "      </table>\n");
	    strInicio.append("      </div>\n");
       
	    this.model.listGenericHash( this.sqlCmd );
	    listData = this.model.getList();

	    strInicio.append("      <div id='layPublic' style='position:relative; left:0px; top:0px; width:"+wg+"px; height:"+this.height+"px; z-index:1; background-color:#ffffff; layer-background-color:#ffffff; border:1px none #000000; visibility:visible; overflow:auto;'>\n");
	    if (!listData.isEmpty()){
	    	strInicio.append("      <table id = 'GridTable' width='"+w+"' border='1' cellspacing='1' cellpadding='1' align='left' class='simple'>\n");
	      
	      /*ARMO EL THEAD*/
	      if(this.filter)
	      {
	      	strInicio.append("       <thead>");
	      	strInicio.append("       <tr style='display:none' id='trIcons'>");
		      for (i = 0; i < arrColumn.length; i++)
		      {   
		      	  if(!arrColumn[i][1].equals(""))
		      	  {
			           title = arrColumn[i][3].equals("") ? "<label style ='display:none'>|</label><img name='imgFilter' border = '0'/><label style ='display:none'>|</label>" : "";			   
						     strInicio.append("          <th  align='center' class = 'label'>" + title + "</th>\n");
		      	  }
			    }
		      strInicio.append("       </tr>");
		      strInicio.append("       </thead>");
	      } 
	      /*FIN THEAD*/
	      
	      strInicio.append("       <tbody>");
	      i = -1;
	      
	      out.print( strInicio );
	      
	      //while (it.hasNext()){
	      for( int index = 0; index < listData.size(); index++ )
	      {
		      i++;
		      count++;
		      
		      dataRow = (Hashtable)listData.get(index);
		      
		      strData.append("        <tr bgcolor='"+arrBgColor[(i%2)]+"' id='TrGrid_"+ index +"' name='page_"+ countPage +"' "+ ( countPage > 1 ? " style='display:none' " : " style='display:block'" ) 
 		         +" onmouseover='cargarImagen(this,\"#FFFFAA\")' onmouseout='quitarImagen(this,\""+arrBgColor[(i%2)]+"\")'>\n");
		      
		      for (j = 0; j < arrColumn.length; j++){
            // Obtener Valor
			      value = "";
			      if (dataRow.containsKey(arrColumn[j][0])) value = dataRow.get(arrColumn[j][0]).toString();
			      if (arrColumn[j][0].equals("#") || arrColumn[j][0].equals("seq")){ 
			      	  value = "" + (i + 1);
			      	  dataRow.put("seq",value);			      	  
			     	}
            // Ancho de Columnas
            if (!arrColumn[j][2].equals("")){
				      // Verificar tipo de Control
					    if (arrColumn[j][3].equals("")){
					    	if (value.equals("")) value = "&nbsp;";
							  length = Integer.parseInt(arrColumn[j][2]);
							  length = (length / 10) + 3;
							  if (value.length() > length)
							    strControl = "<label title='" + value + "' id='label_"+(arrColumn[j][0] + i)+"' class = 'label'>" + value.substring(0, length - 3) + "..." + "</label>";
							  else
							    strControl = "<label title='" + value + "' id='label_"+(arrColumn[j][0] + i)+"' class = 'label'>" + value + "</label>";
					    }else if (arrColumn[j][3].equals("Check")){
							  checked = "";
							  if (value.equals("1")){
					      	checked = "checked";
					      	disabled = "";
					      }else if (value.equals("3")){
					      	checked = "checked";
					      	disabled = "disabled";
					      }
						    javascript = "onClick=\"updateValueCheckBox(this)\"";
						    if (arrColumn[j][6].startsWith("javascript"))
							    javascript = arrColumn[j][6].substring(arrColumn[j][6].indexOf("#") + 1);  
							  strControl = "<input class='check' name='"+(arrColumn[j][0] + i)+"' id='"+(arrColumn[j][0] + i)+"' type='checkbox' "+checked+" value='"+value+"' "+javascript+">";                					    
					    }
					    else if (arrColumn[j][3].equals("Edit"))
					    {  
					    	 try
					    	 { 
					    		 cols = arrColumn[j][8]; 
					    	 }
					    	 catch (Exception e) 
					    	 {	
					    		 cols = "20";
								 }
							   strControl = "<textarea class='textarea' cols = '"+cols+"' rows = '2' name='"+(arrColumn[j][0] + i)+"' id='"+(arrColumn[j][0] + i)+"' >"+value+"</textarea>";                					    
					    }
					    else if (arrColumn[j][3].equals("Combo")){

					    	javascript = "";
					    	if (!arrColumn[j][4].equals("") && !arrColumn[j][4].equals("1")){
					    		javascript = "onChange=\"javascript:"+arrColumn[j][4]+"\"";
					    	}
					    	
					    	title = arrColumn[j][1];
					    	
				       	// Generar contenidos del combos
				    	  qryCmb = arrColumn[j][6];
				    	  listCmb_.clear();

				    	  if (!qryCmb.equals("")){
						    	
						    	if (!qryCmb.substring(0,6).toLowerCase().equals("select") && !qryCmb.startsWith("DATA:"))
						    	  qryCmb = "select nomelm,dspelm from smaelm where " + qryCmb + "order by nroelm";

						    	// Se ejecuta la consulta y se obtiene las lista con los resultados
						      if (!qryCmb.startsWith("DATA:")){
					          model.listGeneric(qryCmb);
					          listCmb_ = model.getList();
						      }else{
						        // Se carga la lista con datos fijos
						      	qryCmb = qryCmb.substring(5);
							      arrTrg = qryCmb.split("#");
							      for (int k = 0; k < arrTrg.length; k++){
					            sisDat = new LinkedList();
				              if (arrTrg[k].indexOf(",") != -1){
								        sisDat.add(arrTrg[k].substring(0,arrTrg[k].indexOf(",")));
								        sisDat.add(arrTrg[k].substring(arrTrg[k].indexOf(",") + 1));
				              }else{
						            sisDat.add(arrTrg[k]);
							          sisDat.add(arrTrg[k]);
				              }
								      listCmb_.add(sisDat);
							      }
						      }
				    	  }
				    	  
				    	  strControl = "<select name='"+(arrColumn[j][0] + i)+"' "+option+" class='combo' id='"+(arrColumn[j][0] + i)+"' title='"+title+"' "+javascript+">\n";
						    for (int k = 0; k < listCmb_.size(); k++){
							    option = "";
							    sisDat = (List) listCmb_.get(k);
								  if (sisDat.size() == 1) sisDat.add(sisDat.get(0));
							    if (sisDat.get(0).equals(value) || sisDat.get(1).equals(value)) option = "selected";
								  strControl += "  <option value='"+sisDat.get(0)+"' "+option+">"+sisDat.get(1)+"</option>\n";
							  }
						    strControl += "</select>";

					    }else if (arrColumn[j][3].equals("Radio")){
							  checked = "";
						    if (value.equals("1")) checked = "checked";
							  strControl = "<input class='radio' name='"+(arrColumn[j][0])+"' id='"+(arrColumn[j][0] + i)+"' type='radio' "+checked+" value='"+value+"'>";                					    
					    }else if (arrColumn[j][3].equals("Date")){
							  strControl  = "<input readonly='true' name='"+(arrColumn[j][0] + i)+"' type='text' id='"+(arrColumn[j][0] + i)+"' class='text' title='"+arrColumn[j][1]+"' size='10' value='"+value+"'>";
							  strControl += "<input type='button' name='btonDate"+j+"' value='...' class='botonsmall' onClick=\"javascript:showCal('Cal_"+(arrColumn[j][0] + i)+"')\">";
							  strScriptCalendar += "  addCalendar('Cal_"+(arrColumn[j][0] + i)+"', 'Elegir Fecha', '"+(arrColumn[j][0] + i)+"');\n";
						  }else if (arrColumn[j][3].startsWith("Text")){
							  format = arrColumn[j][3].substring(arrColumn[j][3].indexOf("Text") + 4);
							  if (!format.equals(""))
							    format = "onKeyPress=\"return validFormat(this,event,'"+format+"')\"";
							  
							    StringTokenizer tokens = new StringTokenizer(arrColumn[j][3],":");
							    String actions = "";
							    String strAux = "";
							    if ( tokens.countTokens()>0 ){ 
							    	strAux = tokens.nextToken();
							    	format = strAux.substring(strAux.indexOf("Text")+4);
							    	format = "onKeyPress=\"return validFormat(this,event,'"+format+"')\"";
							    	while(tokens.hasMoreTokens()){
							    		StringTokenizer tknAct = new StringTokenizer(tokens.nextToken(),"=");
							    		actions+=tknAct.nextToken()+"='"+tknAct.nextToken()+"' ";
							    	}
						      } 
							    strControl =  "<input class='text' name='"+(arrColumn[j][0] + i)+"' id='"+(arrColumn[j][0] + i)+"' type='text' size='8' maxlength='12' title='"+arrColumn[j][1]+"' "+format+" value='"+value+"'>";
						  }else if (arrColumn[j][3].equals("Link")){
							  // Url's
							  option = arrColumn[j][6];
							  if (!option.equals("")){
								  while(option.indexOf("#") != -1)
								  {
									  field = option.substring(option.indexOf("#") + 1);
									  field = field.substring(0,field.indexOf("#"));
									
									  if (dataRow.containsKey(field)) 
									    option = option.replaceAll("#"+field+"#",dataRow.get(field).toString());
									  else
									    option = option.replaceAll("#"+field+"#","");				
									  
									  if( option.indexOf("\"") != -1 )
									  {	
								      option = option.replaceAll("\""," Pgdas");								      
									  }  
									}
							  }
							  // Target
							  format = arrColumn[j][7];
							  if (!format.equals("")) format = "target='" + format + "'";
                // Tool Tips - Status Bar
							  tooltips = arrColumn[j][1];
							  // Texto
							  if (value.equals(""))
							    value = "[...]";
							  else{
								  tooltips += " [" + value + "]";
								  length = Integer.parseInt(arrColumn[j][2]);
								  length = (length / 10) + 3;
								  if (value.length() > length)
								    value = value.substring(0, length - 3) + "...";
							  }
							  	
							  strControl  = "<a class='hypgrid' href=\""+option+"\" "+format+" onMouseOver='window.status=\""+tooltips+"\";return true' title='"+tooltips+"'>"+value+"</a>";						  	
					    }else if (arrColumn[j][3].equals("LinkTable")) {
								
					    	option = arrColumn[j][6];
							  if (!option.equals("")){
								  while(option.indexOf("#") != -1){
									  field = option.substring(option.indexOf("#") + 1);
									  
									  field = field.substring(0,field.indexOf("#"));
									  
									  if (dataRow.containsKey(field)) {
									    option = option.replaceAll("#"+field+"#",dataRow.get(field).toString());
									  }else{
									    option = option.replaceAll("#"+field+"#",value);									    
									  }
									  //System.out.println(option+" --");
								  }
							  }
//							 Target
							  format = arrColumn[j][7];
							  if (!format.equals("")) format = "target='" + format + "'";
                // Tool Tips - Status Bar
							  tooltips = arrColumn[j][1];
							  // Texto
							  if (value.equals(""))
							    value = "[...]";
							  else{
								  tooltips += " [" + value + "]";
								  length = Integer.parseInt(arrColumn[j][2]);
								  length = (length / 10) + 3;
								  if (value.length() > length)
								    value = value.substring(0, length - 3) + "...";
							  }
					    						  							  
					    	strControl  = "<a class='hypgrid' href=\""+option+"\" "+format+" onMouseOver='window.status=\""+tooltips+"\";return true' title='"+tooltips+"'>"+value+"</a>";
					    	strControl += "<br/><div id = 'div"+arrColumn[j][0]+(y+1)+"' style = 'display: none'>" ;						    	
					    	strControl += arrColumn[j][8];  				    	
					     	strControl += "</div>";   	
					     	y++; 
							}
					    else if (arrColumn[j][3].equals("Search")) 
					    {					    	    
		    	       String filter = arrColumn[j][8].split("~")[0];//filtro
		    	       					   
		    	       ControlForm tagControl = new ControlForm();
		    	       String [] fields  = arrColumn[j][8].split("~")[1].split("#");//fields		    	    
		    	       String [] columns = arrColumn[j][8].split("~")[3].split("#");//columns		    	
		    	       String namesFld = "", namesCol = "";
		    	       
		    	       tagControl.setId(arrColumn[j][0] + i);
		    	       tagControl.setName(arrColumn[j][0] + i);		   
		    	       tagControl.setFiltro(filter);
		    	       tagControl.setModel(this.model);
		    	       tagControl.setTitle(arrColumn[j][8].split("~")[2]);//title				    	      
		    	       tagControl.setTypctl("Search");		
		    	       tagControl.setTable(arrColumn[j][8].split("~")[4]);//table		    	  
		    	       tagControl.setLength(arrColumn[j][8].split("~")[5]);//length
		    	    		    	       					    	       
		    	       for (int p = 0; p < fields.length; p++ )
		    	            namesFld += fields[p] + ",";
		    	    
		    	       namesFld = namesFld.substring(0,namesFld.length()-1);//quito la ultima coma
		    	       tagControl.setFields(namesFld);
		    	       
		    	       for (int p = 0; p < columns.length; p++ )
		    	      	 namesCol += columns[p] + ",";
	    	         	    	       
		    	       namesCol = namesCol.substring(0,namesCol.length()-1);//quito la ultima coma
	    	         tagControl.setTitlecolumns(namesCol);	
		    	       tagControl.setTitlecolumns(namesCol);					    	       
		    	       strControl = tagControl.getSourceCode();		    	    
					    }
					    
					    // Verificar si lleva campo oculto � no
					    if (arrColumn[j][4].equals("1")){
							  strControl += "<input name='" + (arrColumn[j][0] + "H") + "' id='" + (arrColumn[j][0] + "H" + i) + "' type='hidden' value='" + value + "'>";
					    }
              // Verificar alineamiento
				      if (arrColumn[j][5].equals(""))
					      arrColumn[j][5] = "left";

				      strData.append("          <td width='" + arrColumn[j][2] + "' align='" + arrColumn[j][5] + "'>" + strControl + "</td>\n");
            }else{
		          if (!arrColumn[j][0].equals("")){
						    strControl = "          <input name='" + (arrColumn[j][0]) + "' id='" + (arrColumn[j][0] + i) + "' type='hidden' value='" + value + "'>\n";
						    strData.append(strControl); 
		          }
            }
		      }
		      strData.append("        </tr>\n");
		      
		      if( (index+1) % limitPagination == 0 && index != listData.size() - 1 )
		      {
		    	  countPage++;
		      }
		      
		      out.print( strData );
		      strData = new StringBuffer();
	      }
	      strFin.append("       </tbody>");
	      strFin.append("      </table>\n");
	    }
	    strFin.append("      </div>\n");
	    strFin.append("    </td>\n  </tr>\n</table>");
	    
	    if( listData.size() > limitPagination )
	    {
		    strFin.append("<div align='center'>");
		    // FIRST
		    strFin.append(  "<img src='"+ this.request.getContextPath() +"/vista/img/first.png' " +
                            " onclick=\"paginationGrid('first')\" " +
                            " title='Primero' "+
                            " border='0' "+
                            " id='href_first' "+
                            " disabled='disabled' "+
													  " style='cursor:pointer' "+
                        	  " border='0' />&nbsp;&nbsp;"
										 );
		    // PREVIOUS
		    strFin.append(  "<img src='"+ this.request.getContextPath() +"/vista/img/prev.png' " +
								            " onclick=\"paginationGrid('previous')\" " +
								            " title='Anterior' "+
								            " border='0' "+
								            " id='href_previous' "+
								            " disabled='disabled' "+
													  " style='cursor:pointer' "+
								        	  " border='0' />&nbsp;&nbsp;"
										 );
		    
		    // NEXT
		    strFin.append(  "<img src='"+ this.request.getContextPath() +"/vista/img/next.png' " +
								            " onclick=\"paginationGrid('next')\" " +
								            " title='Siguiente' "+
								            " border='0' "+
								            " id='href_next' "+
													  " style='cursor:pointer' "+
								        	  " border='0' />&nbsp;&nbsp;"
										 );
		    
		    // LAST
		    strFin.append(  "<img src='"+ this.request.getContextPath() +"/vista/img/last.png' " +
								            " onclick=\"paginationGrid('last')\" " +
								            " title='Ultimo' "+
								            " border='0' "+
								            " id='href_last' "+
													  " style='cursor:pointer' "+
								        	  " border='0' />&nbsp;&nbsp;"
										 );
		    strFin.append("</div>");
	    }
	    
	   
	    strFin.append("<script language='JavaScript' type='text/JavaScript'>"+
	        " lastPageGrid = "+ countPage +";"+
	        ( !this.javascript.equals("") ? " eval('"+ this.javascript +"');" : "" )+
      "</script>");

	    if (!strScriptCalendar.equals("")){
			  strScriptCalendar = "<script language='JavaScript' type='text/JavaScript'>\n" + strScriptCalendar + "</script>\n";
	    }
	    
	    if( count == 0 )
	    	out.print(strInicio.toString());
	    out.print(strFin.toString());
	    out.print(strScriptCalendar.toString());
	    out.close();
	    
	    return strFin.toString();

    }catch (Exception ex) {   
    	throw new JspException("Class GridControlPaged Metodo [getSourceCode] " + ex);
    }
  }
  
  public void getSourceCode()throws JspException {

	  int w = 0, wg = 0, i = 0, j = 0,y=0, length = 0;
	  String rowTitle = "", title = "", value = "", format = "", field = "";
	  String strControl = "", option = "", strScriptCalendar = "", tooltips = "";
	  String checked = "", javascript = "";
	  String arrColumn[][];
	  String[] arrTrg = {}; 
	  String arrBgColor[] = new String[2];
	  Iterator it;
	  List listData = new LinkedList();
	  List listCmb_ = new LinkedList();
	  List sisDat = new LinkedList();
	  Hashtable dataRow = new Hashtable();
	  StringBuffer str = new StringBuffer(); 
	  String qryCmb = "";
	  String disabled = "";
	  String strScriptPag = ""; // cadena para script de paginacion
	  int count = 0;
	  int countPage = 1;
	  int limitString = 100;
	  int limitPagination = 100;

    try{
    	PrintWriter out = response.getWriter();
	    arrBgColor[0] = this.color;
	    arrBgColor[1] = "#E9E7E2";
	    // Obtener configuraci�n de Columnas
	    arrColumn = (String[][]) configGrid.get("ConfigCol");
	    
	    	    
	    rowTitle += "<tr style='display:none' id='trFilter' height='22px'><th colspan = '" + arrColumn.length + "' bgcolor = '#CCCCCC' align = 'left'>" +
      "<font style='color:#333333' face='Verdana' size = '2'>Filtrar&nbsp;</font><input type ='text' name = 'quickfind' id = 'quickfind' class='text'>" +
      "</th></tr>" ;
	    
	    rowTitle += "        <tr class='boton' height='22px'>\n";
	   
		  // Limpiar matriz
		  for (i = 0; i < arrColumn.length; i++)
			  for (j = 0; j < 8; j++)
			    if (arrColumn[i][j] == null)
			      arrColumn[i][j] = "";
 
	    for (i = 0; i < arrColumn.length; i++){
	      // Titulo de la Columna
	      title = arrColumn[i][1];
	      if (title.equals("")) title = "&nbsp;";
		    // Ancho de la Columna
		    if (!arrColumn[i][2].equals("")){
				  length = Integer.parseInt(arrColumn[i][2]);
				  length = (length / 10) + 3;
				  if (title.length() > length)
				    rowTitle += "          <th width='" + arrColumn[i][2] + "' align='center' ><label title='" + title + "' class = 'label'>" + title.substring(0, length - 3) + "..." + "</label></th>\n";
				  else
				    rowTitle += "          <th width='" + arrColumn[i][2] + "' align='center' class = 'label'>" + title + "</th>\n";
		      w += Integer.parseInt(arrColumn[i][2]);
		    }
	    }
	   
	    rowTitle += "        </tr>\n";
	  	    
	    wg = w + 20;
	    
	    if(filter) hg = "50px";
	    
	    //strInicio.append("<div id='DivGridTable' align='center'>");
	    strInicio.append("<table id='tableData' width='"+wg+"' align='"+this.align+"' cellpadding='0' cellspacing='0' class='simple'>\n  <tr>\n    <td>\n");
	    strInicio.append("      <div id='layTitle' style='position:relative; left:0px; top:0px; width:"+wg+"px; height:"+hg+"; z-index:1; background-color:#ffffff; layer-background-color:#ffffff; border:1px none #000000; visibility:visible; '>\n");
	    strInicio.append("      <table width='"+w+"' border='0' cellspacing='1' cellpadding='1' align='left'>\n");
	    strInicio.append(rowTitle + "      </table>\n");
	    strInicio.append("      </div>\n");
       
	    this.model.listGenericHash( this.sqlCmd );
	    listData = this.model.getList();

	    strInicio.append("      <div id='layPublic' style='position:relative; left:0px; top:0px; width:"+wg+"px; height:"+this.height+"px; z-index:1; background-color:#ffffff; layer-background-color:#ffffff; border:1px none #000000; visibility:visible; overflow:auto;'>\n");
	    if (!listData.isEmpty()){
	    	strInicio.append("      <table id = 'GridTable' width='"+w+"' border='1' cellspacing='1' cellpadding='1' align='left' class='simple'>\n");
	      
	      /*ARMO EL THEAD*/
	      if(this.filter)
	      {
	      	strInicio.append("       <thead>");
	      	strInicio.append("       <tr style='display:none' id='trIcons'>");
		      for (i = 0; i < arrColumn.length; i++)
		      {   
		      	  if(!arrColumn[i][1].equals(""))
		      	  {
			           title = arrColumn[i][3].equals("") ? "<label style ='display:none'>|</label><img name='imgFilter' border = '0'/><label style ='display:none'>|</label>" : "";			   
						     strInicio.append("          <th  align='center' class = 'label'>" + title + "</th>\n");
		      	  }
			    }
		      strInicio.append("       </tr>");
		      strInicio.append("       </thead>");
	      } 
	      /*FIN THEAD*/
	      
	      strInicio.append("       <tbody>");
	      i = -1;
	      
	      out.print( strInicio );
	      
	      //while (it.hasNext()){
	      for( int index = 0; index < listData.size(); index++ )
	      {
		      i++;
		      count++;
		      
		      dataRow = (Hashtable)listData.get(index);
		      
		      strData.append("        <tr bgcolor='"+arrBgColor[(i%2)]+"' id='TrGrid_"+ index +"' name='page_"+ countPage +"' "+ ( countPage > 1 ? " style='display:none' " : " style='display:block'" ) 
 		         +" onmouseover='cargarImagen(this,\"#FFFFAA\")' onmouseout='quitarImagen(this,\""+arrBgColor[(i%2)]+"\")'>\n");
		      
		      for (j = 0; j < arrColumn.length; j++){
            // Obtener Valor
			      value = "";
			      if (dataRow.containsKey(arrColumn[j][0])) value = dataRow.get(arrColumn[j][0]).toString();
			      if (arrColumn[j][0].equals("#") || arrColumn[j][0].equals("seq")){ 
			      	  value = "" + (i + 1);
			      	  dataRow.put("seq",value);			      	  
			     	}
            // Ancho de Columnas
            if (!arrColumn[j][2].equals("")){
				      // Verificar tipo de Control
					    if (arrColumn[j][3].equals("")){
					    	if (value.equals("")) value = "&nbsp;";
							  length = Integer.parseInt(arrColumn[j][2]);
							  length = (length / 10) + 3;
							  if (value.length() > length)
							    strControl = "<label title='" + value + "' id='label_"+(arrColumn[j][0] + i)+"' class = 'label'>" + value.substring(0, length - 3) + "..." + "</label>";
							  else
							    strControl = "<label title='" + value + "' id='label_"+(arrColumn[j][0] + i)+"' class = 'label'>" + value + "</label>";
					    }else if (arrColumn[j][3].equals("Check")){
							  checked = "";
							  if (value.equals("1")){
					      	checked = "checked";
					      	disabled = "";
					      }else if (value.equals("3")){
					      	checked = "checked";
					      	disabled = "disabled";
					      }
						    javascript = "onClick=\"updateValueCheckBox(this)\"";
						    if (arrColumn[j][6].startsWith("javascript"))
							    javascript = arrColumn[j][6].substring(arrColumn[j][6].indexOf("#") + 1);  
							  strControl = "<input class='check' name='"+(arrColumn[j][0] + i)+"' id='"+(arrColumn[j][0] + i)+"' type='checkbox' "+checked+" value='"+value+"' "+javascript+">";                					    
					    }
					    else if (arrColumn[j][3].equals("Edit"))
					    {  
					    	 try
					    	 { 
					    		 cols = arrColumn[j][8]; 
					    	 }
					    	 catch (Exception e) 
					    	 {	
					    		 cols = "20";
								 }
							   strControl = "<textarea class='textarea' cols = '"+cols+"' rows = '2' name='"+(arrColumn[j][0] + i)+"' id='"+(arrColumn[j][0] + i)+"' >"+value+"</textarea>";                					    
					    }
					    else if (arrColumn[j][3].equals("Combo")){

					    	javascript = "";
					    	if (!arrColumn[j][4].equals("") && !arrColumn[j][4].equals("1")){
					    		javascript = "onChange=\"javascript:"+arrColumn[j][4]+"\"";
					    	}
					    	
					    	title = arrColumn[j][1];
					    	
				       	// Generar contenidos del combos
				    	  qryCmb = arrColumn[j][6];
				    	  listCmb_.clear();

				    	  if (!qryCmb.equals("")){
						    	
						    	if (!qryCmb.substring(0,6).toLowerCase().equals("select") && !qryCmb.startsWith("DATA:"))
						    	  qryCmb = "select nomelm,dspelm from smaelm where " + qryCmb + "order by nroelm";

						    	// Se ejecuta la consulta y se obtiene las lista con los resultados
						      if (!qryCmb.startsWith("DATA:")){
					          model.listGeneric(qryCmb);
					          listCmb_ = model.getList();
						      }else{
						        // Se carga la lista con datos fijos
						      	qryCmb = qryCmb.substring(5);
							      arrTrg = qryCmb.split("#");
							      for (int k = 0; k < arrTrg.length; k++){
					            sisDat = new LinkedList();
				              if (arrTrg[k].indexOf(",") != -1){
								        sisDat.add(arrTrg[k].substring(0,arrTrg[k].indexOf(",")));
								        sisDat.add(arrTrg[k].substring(arrTrg[k].indexOf(",") + 1));
				              }else{
						            sisDat.add(arrTrg[k]);
							          sisDat.add(arrTrg[k]);
				              }
								      listCmb_.add(sisDat);
							      }
						      }
				    	  }
				    	  
				    	  strControl = "<select name='"+(arrColumn[j][0] + i)+"' "+option+" class='combo' id='"+(arrColumn[j][0] + i)+"' title='"+title+"' "+javascript+">\n";
						    for (int k = 0; k < listCmb_.size(); k++){
							    option = "";
							    sisDat = (List) listCmb_.get(k);
								  if (sisDat.size() == 1) sisDat.add(sisDat.get(0));
							    if (sisDat.get(0).equals(value) || sisDat.get(1).equals(value)) option = "selected";
								  strControl += "  <option value='"+sisDat.get(0)+"' "+option+">"+sisDat.get(1)+"</option>\n";
							  }
						    strControl += "</select>";

					    }else if (arrColumn[j][3].equals("Radio")){
							  checked = "";
						    if (value.equals("1")) checked = "checked";
							  strControl = "<input class='radio' name='"+(arrColumn[j][0])+"' id='"+(arrColumn[j][0] + i)+"' type='radio' "+checked+" value='"+value+"'>";                					    
					    }else if (arrColumn[j][3].equals("Date")){
							  strControl  = "<input readonly='true' name='"+(arrColumn[j][0] + i)+"' type='text' id='"+(arrColumn[j][0] + i)+"' class='text' title='"+arrColumn[j][1]+"' size='10' value='"+value+"'>";
							  strControl += "<input type='button' name='btonDate"+j+"' value='...' class='botonsmall' onClick=\"javascript:showCal('Cal_"+(arrColumn[j][0] + i)+"')\">";
							  strScriptCalendar += "  addCalendar('Cal_"+(arrColumn[j][0] + i)+"', 'Elegir Fecha', '"+(arrColumn[j][0] + i)+"');\n";
						  }else if (arrColumn[j][3].startsWith("Text")){
							  format = arrColumn[j][3].substring(arrColumn[j][3].indexOf("Text") + 4);
							  if (!format.equals(""))
							    format = "onKeyPress=\"return validFormat(this,event,'"+format+"')\"";
							  
							    StringTokenizer tokens = new StringTokenizer(arrColumn[j][3],":");
							    String actions = "";
							    String strAux = "";
							    if ( tokens.countTokens()>0 ){ 
							    	strAux = tokens.nextToken();
							    	format = strAux.substring(strAux.indexOf("Text")+4);
							    	format = "onKeyPress=\"return validFormat(this,event,'"+format+"')\"";
							    	while(tokens.hasMoreTokens()){
							    		StringTokenizer tknAct = new StringTokenizer(tokens.nextToken(),"=");
							    		actions+=tknAct.nextToken()+"='"+tknAct.nextToken()+"' ";
							    	}
						      } 
							    strControl =  "<input class='text' name='"+(arrColumn[j][0] + i)+"' id='"+(arrColumn[j][0] + i)+"' type='text' size='8' maxlength='12' title='"+arrColumn[j][1]+"' "+format+" value='"+value+"'>";
						  }else if (arrColumn[j][3].equals("Link")){
							  // Url's
							  option = arrColumn[j][6];
							  if (!option.equals("")){
								  while(option.indexOf("#") != -1)
								  {
									  field = option.substring(option.indexOf("#") + 1);
									  field = field.substring(0,field.indexOf("#"));
									
									  if (dataRow.containsKey(field)) 
									    option = option.replaceAll("#"+field+"#",dataRow.get(field).toString());
									  else
									    option = option.replaceAll("#"+field+"#","");				
									  
									  if( option.indexOf("\"") != -1 )
									  {	
								      option = option.replaceAll("\""," Pgdas");								      
									  }  
									}
							  }
							  // Target
							  format = arrColumn[j][7];
							  if (!format.equals("")) format = "target='" + format + "'";
                // Tool Tips - Status Bar
							  tooltips = arrColumn[j][1];
							  // Texto
							  if (value.equals(""))
							    value = "[...]";
							  else{
								  tooltips += " [" + value + "]";
								  length = Integer.parseInt(arrColumn[j][2]);
								  length = (length / 10) + 3;
								  if (value.length() > length)
								    value = value.substring(0, length - 3) + "...";
							  }
							  	
							  strControl  = "<a class='hypgrid' href=\""+option+"\" "+format+" onMouseOver='window.status=\""+tooltips+"\";return true' title='"+tooltips+"'>"+value+"</a>";						  	
					    }else if (arrColumn[j][3].equals("LinkTable")) {
								
					    	option = arrColumn[j][6];
							  if (!option.equals("")){
								  while(option.indexOf("#") != -1){
									  field = option.substring(option.indexOf("#") + 1);
									  
									  field = field.substring(0,field.indexOf("#"));
									  
									  if (dataRow.containsKey(field)) {
									    option = option.replaceAll("#"+field+"#",dataRow.get(field).toString());
									  }else{
									    option = option.replaceAll("#"+field+"#",value);									    
									  }
									  //System.out.println(option+" --");
								  }
							  }
//							 Target
							  format = arrColumn[j][7];
							  if (!format.equals("")) format = "target='" + format + "'";
                // Tool Tips - Status Bar
							  tooltips = arrColumn[j][1];
							  // Texto
							  if (value.equals(""))
							    value = "[...]";
							  else{
								  tooltips += " [" + value + "]";
								  length = Integer.parseInt(arrColumn[j][2]);
								  length = (length / 10) + 3;
								  if (value.length() > length)
								    value = value.substring(0, length - 3) + "...";
							  }
					    						  							  
					    	strControl  = "<a class='hypgrid' href=\""+option+"\" "+format+" onMouseOver='window.status=\""+tooltips+"\";return true' title='"+tooltips+"'>"+value+"</a>";
					    	strControl += "<br/><div id = 'div"+arrColumn[j][0]+(y+1)+"' style = 'display: none'>" ;						    	
					    	strControl += arrColumn[j][8];  				    	
					     	strControl += "</div>";   	
					     	y++; 
							}
					    else if (arrColumn[j][3].equals("Search")) 
					    {					    	    
		    	       String filter = arrColumn[j][8].split("~")[0];//filtro
		    	       					   
		    	       ControlForm tagControl = new ControlForm();
		    	       String [] fields  = arrColumn[j][8].split("~")[1].split("#");//fields		    	    
		    	       String [] columns = arrColumn[j][8].split("~")[3].split("#");//columns		    	
		    	       String namesFld = "", namesCol = "";
		    	       
		    	       tagControl.setId(arrColumn[j][0] + i);
		    	       tagControl.setName(arrColumn[j][0] + i);		   
		    	       tagControl.setFiltro(filter);
		    	       tagControl.setModel(this.model);
		    	       tagControl.setTitle(arrColumn[j][8].split("~")[2]);//title				    	      
		    	       tagControl.setTypctl("Search");		
		    	       tagControl.setTable(arrColumn[j][8].split("~")[4]);//table		    	  
		    	       tagControl.setLength(arrColumn[j][8].split("~")[5]);//length
		    	    		    	       					    	       
		    	       for (int p = 0; p < fields.length; p++ )
		    	            namesFld += fields[p] + ",";
		    	    
		    	       namesFld = namesFld.substring(0,namesFld.length()-1);//quito la ultima coma
		    	       tagControl.setFields(namesFld);
		    	       
		    	       for (int p = 0; p < columns.length; p++ )
		    	      	 namesCol += columns[p] + ",";
	    	         	    	       
		    	       namesCol = namesCol.substring(0,namesCol.length()-1);//quito la ultima coma
	    	         tagControl.setTitlecolumns(namesCol);	
		    	       tagControl.setTitlecolumns(namesCol);					    	       
		    	       strControl = tagControl.getSourceCode();		    	    
					    }
					    
					    // Verificar si lleva campo oculto � no
					    if (arrColumn[j][4].equals("1")){
							  strControl += "<input name='" + (arrColumn[j][0] + "H") + "' id='" + (arrColumn[j][0] + "H" + i) + "' type='hidden' value='" + value + "'>";
					    }
              // Verificar alineamiento
				      if (arrColumn[j][5].equals(""))
					      arrColumn[j][5] = "left";

				      strData.append("          <td width='" + arrColumn[j][2] + "' align='" + arrColumn[j][5] + "'>" + strControl + "</td>\n");
            }else{
		          if (!arrColumn[j][0].equals("")){
						    strControl = "          <input name='" + (arrColumn[j][0]) + "' id='" + (arrColumn[j][0] + i) + "' type='hidden' value='" + value + "'>\n";
						    strData.append(strControl); 
		          }
            }
		      }
		      strData.append("        </tr>\n");
		      
		      if( (index+1) % limitPagination == 0 && index != listData.size() - 1 )
		      {
		    	  countPage++;
		      }
		      
		      out.print( strData );
		      strData = new StringBuffer();
	      }
	      strFin.append("       </tbody>");
	      strFin.append("      </table>\n");
	    }
	    strFin.append("      </div>\n");
	    strFin.append("    </td>\n  </tr>\n</table>");
	    
	    if( listData.size() > limitPagination )
	    {
		    strFin.append("<div align='center'>");
		    // FIRST
		    strFin.append(  "<img src='"+ this.request.getContextPath() +"/vista/img/first.png' " +
                            " onclick=\"paginationGrid('first')\" " +
                            " title='Primero' "+
                            " border='0' "+
                            " id='href_first' "+
                            " disabled='disabled' "+
													  " style='cursor:pointer' "+
                        	  " border='0' />&nbsp;&nbsp;"
										 );
		    // PREVIOUS
		    strFin.append(  "<img src='"+ this.request.getContextPath() +"/vista/img/prev.png' " +
								            " onclick=\"paginationGrid('previous')\" " +
								            " title='Anterior' "+
								            " border='0' "+
								            " id='href_previous' "+
								            " disabled='disabled' "+
													  " style='cursor:pointer' "+
								        	  " border='0' />&nbsp;&nbsp;"
										 );
		    
		    // NEXT
		    strFin.append(  "<img src='"+ this.request.getContextPath() +"/vista/img/next.png' " +
								            " onclick=\"paginationGrid('next')\" " +
								            " title='Siguiente' "+
								            " border='0' "+
								            " id='href_next' "+
													  " style='cursor:pointer' "+
								        	  " border='0' />&nbsp;&nbsp;"
										 );
		    
		    // LAST
		    strFin.append(  "<img src='"+ this.request.getContextPath() +"/vista/img/last.png' " +
								            " onclick=\"paginationGrid('last')\" " +
								            " title='Ultimo' "+
								            " border='0' "+
								            " id='href_last' "+
													  " style='cursor:pointer' "+
								        	  " border='0' />&nbsp;&nbsp;"
										 );
		    strFin.append("</div>");
	    }
	    
	   
	    strFin.append("<script language='JavaScript' type='text/JavaScript'>"+
	        " lastPageGrid = "+ countPage +";"+
	        ( !this.javascript.equals("") ? " eval('"+ this.javascript +"');" : "" )+
      "</script>");

	    if (!strScriptCalendar.equals("")){
			  strScriptCalendar = "<script language='JavaScript' type='text/JavaScript'>\n" + strScriptCalendar + "</script>\n";
	    }
	    
	    if( count == 0 )
	    	out.print(strInicio.toString());
	    out.print(strFin.toString());
	    out.print(strScriptCalendar.toString());
	    out.close();

    }catch (Exception ex) {   
    	throw new JspException("Class GridControlPaged Metodo [getSourceCode] " + ex);
    }
  }

  public void printCode()throws JspException {
  	try{
  		this.getSourceCode();
  	}catch (Exception ex) {
  	  throw new JspException("Class GriControlPaged Metodo [printCode] " + ex.getMessage());
  	}
  }
  
}// Fina Class