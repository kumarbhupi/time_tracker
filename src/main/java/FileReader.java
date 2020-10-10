import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;


public class FileReader  {
  private String fileLocation;
  public final String filename="TasksAndProjects.txt";
  public static File file;


    public FileReader() throws IOException {
    file=new File(filename);//creo instancia de fitxer amb el nom per defecte
    Scanner Reader = new Scanner(file);
    if(!file.exists()){
      file.createNewFile();
    }//CREAR FITXER SI NO EXISTEIX
    while(Reader.hasNextLine())
    {

    }


  }







  /*public static void main(String[] args) {
    Path currentRelativePath = Paths.get("TasksAndProjects");
    String fileLocation = currentRelativePath.toAbsolutePath().toString();
    file=new File(fileLocation);

    if(!file.exists())
      System.out.println("File doesn't exists");
    System.out.println("File Name: "+file.getName());
  }*/

}
