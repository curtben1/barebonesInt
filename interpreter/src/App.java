import java.lang.reflect.Array;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.nio.charset.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class App {
    public static void main(String[] args) throws Exception {
        String[][] codeArr = generateCode();
        /*
        for(String[]row:codeArr){
            for(String word:row){
                System.out.println(word);
            }
        }
        */
        execute(codeArr);
        


    }
    
    public static String[][] generateCode() throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));  
        String fileName = br.readLine();
        String content = new String(Files.readAllBytes(Paths.get(fileName)));
        content = content.replace("\t", "");
        content = content.replace("\n", "");
        content = content.replace("  ", "");
        String[] myCode = content.split(";");
        String[][] myArr = new String[myCode.length][1];

        for (int i=0;i< myCode.length;i++ ){
            myArr[i] =  myCode[i].split(" ");
            
        }
        return (myArr);
    }


    public static void execute(String[][] codeArr) throws Exception{
        HashMap<String,Integer> variables = new HashMap<String,Integer>();
        System.out.println(Arrays.toString(codeArr));
        for (String[]line:codeArr){
            System.out.println(Arrays.toString(line));
            switch (line[0]){
                case "clear":
                    variables.put(line[1],0);
                    System.out.println(line[1] + ": 0");
                    break;
                case "incr":
                    int newVal = variables.get(line[1]) + 1;
                    variables.put(line[1], newVal);
                    System.out.println(newVal);
                    break;
                
            } 
            
        }
    }
}
