package APITest.API;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadPropertiesFile {

	protected Properties Prop = null;
	protected FileInputStream LoadFile = null;

	public void ReadPropFile() throws IOException 
	 {
			Prop = new Properties();
			LoadFile = new FileInputStream("./src/test/resources/Properties/expectedValues.properties");
			Prop.load(LoadFile);
	}
	public String getUserId() {
		return Prop.getProperty("userId");
	}
	
	public String getId() {
		return Prop.getProperty("id");
	}
	
	public String getTitle() {
		return Prop.getProperty("title");
	}
	
	public String getBody() {
		return Prop.getProperty("body");
	}
	
}
