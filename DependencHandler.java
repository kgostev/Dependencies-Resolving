import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DependencHandler {
	public void installDependencies() {
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(new FileReader("./TaskThree/dependencies.json"));
			JSONObject dependencies = (JSONObject) obj;
			JSONArray missingDep = checkForMissingDependencies(dependencies, "dependencies");

			if (missingDep.size() == 0) {
				System.out.println("All dependencies are installed");
			} else {
				obj = parser.parse(new FileReader("./TaskThree/all_packages.json"));
				JSONObject allPacks = (JSONObject) obj;
				installPackages(missingDep, allPacks);
				System.out.println("All done");
			}

		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private JSONArray checkForMissingDependencies(JSONObject dependencies, String name) {
		JSONArray missingDep = (JSONArray) dependencies.get(name);

		for (int i = 0; i < missingDep.size(); i++) {
			String packageName = (String) missingDep.get(i);

			File pack = new File("./TaskThree/installed_modules/" + packageName);
			if (pack.exists()) {
				System.out.println(packageName + " exists");
				missingDep.remove(i);
			} else {
				System.out.println("Package \"" + packageName + "\" will be added for instalation");
			}

		}

		return missingDep;
	}

	private void installPackages(JSONArray missingDep, JSONObject allPacks) {
		// System.out.println("Installing modules");
		for (int i = 0; i < missingDep.size(); i++) {
			System.out.println("Installing \"" + missingDep.get(i) + "\"...");

			File mod = new File("./TaskThree/installed_modules/" + missingDep.get(i));
			try {
				mod.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			JSONArray ap = (JSONArray) allPacks.get(missingDep.get(i));
			if (ap.size() > 0) {
				System.out.println("In order to install " + missingDep.get(i) + "we need: " + ap);

				for (int j = 0; j < ap.size(); j++) {
					File f = new File("./TaskThree/installed_modules/" + (String) ap.get(j));
					if (f.exists()) {
						System.out.println("::" + ap.get(j) + " exists");
						ap.remove(j);
					}
				}
				installPackages(ap, allPacks);
			} else {
				System.out.println(missingDep.get(i) + " installed");
				missingDep.remove(i);
				return;
			}
		  }
	}
}
