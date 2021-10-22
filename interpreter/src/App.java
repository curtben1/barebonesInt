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
    static HashMap<String,Integer>  variables = new HashMap<String,Integer>();
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
        //String fileName = br.readLine();
        String content = new String(Files.readAllBytes(Paths.get("file.txt")));
        content = content.replace("\r", "");
        content = content.replace("\n", "");
        content = content.replace("    ", "");
        
        System.out.println(content);
        String[] myCode = content.split(";");
        String[][] myArr = new String[myCode.length][1];

        for (int i=0;i< myCode.length;i++ ){
            myArr[i] =  myCode[i].split(" ");
            
        }
        return (myArr);
    }


    public static void execute(String[][] codeArr) throws Exception{
        
        int newVal;
        int lineNum = 0;
        for (String[]line:codeArr){
            
            switch (line[0]){
                case "clear":
                    App.variables.put(line[1],0);
                    System.out.println(line[1] + ": 0");
                    break;
                case "incr":
                    newVal = variables.get(line[1]) + 1;
                    variables.put(line[1], newVal);
                    System.out.println(line[1] +": " + Integer.toString(newVal));
                    break;
                case "decr":
                    newVal = variables.get(line[1]) -1;
                    variables.put(line[1], newVal);
                    System.out.println(line[1] +": " + Integer.toString(newVal));
                    break;
                case "while":
                    if (! (line[2].equals("not") || line[3].equals("0") ||line[3].equals("do"))){
                        System.out.println("error bad syntax");
                    }
                    else{
                        //while loop time
                        while (variables.get(line[1]) !=0){

                        
                            int tempLineNum = lineNum;
                            int endCounter = 0;
                            do{
                                if (codeArr[tempLineNum][0].equals("while") ){
                                    endCounter -= 1;
                                    tempLineNum ++;
                                }else if (codeArr[tempLineNum][0].equals("end") ){
                                    endCounter ++;
                                    System.out.println("end counter"+ Integer.toString(endCounter));
                                    
                                }
                                else{
                                    tempLineNum ++;
                                }
                                
                                
                            }while (endCounter !=0);
                            String[][] tempArr = new String[tempLineNum-lineNum][1];
                            System.arraycopy(codeArr, lineNum + 1,tempArr, 0, tempLineNum-lineNum);
                            execute(tempArr);
                            // enters while loop but no real exit condition, currently runs twice for unkown reason, after calling execute should check condition (should also check condition before starting. Maybe use a while loop to implement)
                    
                        }
                        
                    }
                
            } 
            lineNum++;  
        }
    }
}
