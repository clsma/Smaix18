package mvc.util;
 
public enum ClsmaTypeException {
    ERROR("ERROR"),
    INFO("INFO");
        
    

    private final String description;    
    
    ClsmaTypeException(String description) {
        this.description = description; 
    }

    public String getDescription() {
        return description;
    }
}
