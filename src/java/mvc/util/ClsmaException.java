package mvc.util;

import org.json.simple.JSONObject;

public class ClsmaException extends Exception {

    private final String code;
    private final String type;
    private final String message;
    private Throwable cause;

    
    public ClsmaException(String code, String type, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.type = type;
        this.message = message;
    }
    
    public ClsmaException(String type, String message, Throwable cause) {
        super(message, cause);
        this.code = UtilConstantes.STR_VACIO;
        this.type = type;
        this.message = message;
    }
    
    public ClsmaException(String type, String message) {
        super(message);
        this.code = UtilConstantes.STR_VACIO;
        this.type = type;
        this.message = message;
    }
    
     public ClsmaException(ClsmaTypeException type, String message) {
        super(message);
        this.code = UtilConstantes.STR_VACIO;
        this.type = type.toString();
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }
    
    public void writeJsonError(JSONObject json){
        json.put("exito", this.getType());
        json.put("msg", this.getMessage());
    }

}
