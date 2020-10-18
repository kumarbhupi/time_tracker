import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager implements Element {
  public static final String JSON_SAVED_FILE_NAME = "json_data.json";
  private JSONObject jsonObjectRead;

  public JSONObject getJsonObjectRead() {
    return jsonObjectRead;
  }

  public void saveToJsonFile(JSONObject jsonObject) {
    try (FileWriter file = new FileWriter(JSON_SAVED_FILE_NAME)) {
      file.write(jsonObject.toString());
      file.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void readFromJsonFile(){
    try(FileReader reader = new FileReader(JSON_SAVED_FILE_NAME)){
      jsonObjectRead = (JSONObject) new JSONTokener(reader).nextValue();
      System.out.println(jsonObjectRead.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  @Override
  public TaskManager accept(VisitorRead v) {
    return v.visit(this);
  }

  @Override
  public JSONObject accept(Visitor v) {
    return null;
  }

  @Override
  public void print(VisitorPrint visitorPrint) {

  }
}
