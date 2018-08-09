package mvc.activities;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Hashtable;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mvc.model.ModelSma;

public class ValidateActiveRegistration extends Activity {

	@Override
	public boolean isValidated(ModelSma model, HttpServletRequest request, HttpSession session, ServletContext application) throws SQLException, ParseException  {
		// TODO Auto-generated method stub
	
		String sqlCmd = "Select sma_active_registration('"+model.getCodCia()+"','"+model.getCodPrs()+"') as sw";	
		model.listGenericHash(sqlCmd);
		List<Hashtable> list = model.getList();
		if(list.get(0).get("sw").equals("t"))
                    return false;
                else
                    return true;
	}

 
}
