package mvc.activities;

import java.sql.SQLException;
import java.text.ParseException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import mvc.model.ModelSma;

public abstract class Activity {
	public abstract  boolean isValidated(ModelSma model, HttpServletRequest request, HttpSession session, ServletContext application) throws SQLException, ParseException;
}
