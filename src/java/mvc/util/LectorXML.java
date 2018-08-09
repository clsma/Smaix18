package mvc.util;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author efrain
 */
public class LectorXML {

    public static String getSqlXML(String url, String queryName) {

        String sqlCmd = "";

        try {
            DocumentBuilderFactory fábricaCreadorDocumento = DocumentBuilderFactory.newInstance();
            DocumentBuilder creadorDocumento = fábricaCreadorDocumento.newDocumentBuilder();
            Document documento = creadorDocumento.parse(url);
            //Obtener el elemento raíz del documento
            Element raiz = documento.getDocumentElement();
            //Obtener la lista de nodos que tienen etiqueta "EMPLEADO"
            NodeList listaQuerys = raiz.getElementsByTagName("statement");

            //Recorrer la lista de empleados
            for (int i = 0; i < listaQuerys.getLength(); i++) {
                //Obtener de la lista un empleado tras otro
                Node query = listaQuerys.item(i);
                String nameQuery = query.getAttributes().getNamedItem("name").getNodeValue();
                String name = query.getNodeName();

//                System.out.println("Empleado " + i);
//                System.out.println("==========");
                //Obtener la lista de los datos que contiene ese empleado
                if (!queryName.trim().equals(nameQuery.trim())) {
                    continue;
                }

                NodeList datos = query.getChildNodes();

                //Recorrer la lista de los datos que contiene el empleado
                for (int j = 0; j < datos.getLength(); j++) {
                    //Obtener de la lista de datos un dato tras otro
                    Node dato = datos.item(j);

                    String nameTag = dato.getNodeName();

                    if ("sql".equals(nameTag)) {
                        sqlCmd = dato.getFirstChild().getTextContent();
                        break;
                    }
                }
            }
            return sqlCmd;    
        } catch (Exception ex) {
            System.out.println(" LectorXML method getSqlXML[url xmlFile = " + url +"]");
           ex.printStackTrace();
        }
        
        return  null;
        
    }
}
