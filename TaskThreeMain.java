import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TaskThreeMain {

	public static void main(String[] args) {		
		DependencHandler dh = new DependencHandler();
		dh.installDependencies();
	}
}
