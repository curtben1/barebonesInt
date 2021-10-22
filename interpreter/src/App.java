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
        execute(codeArr);
        


    }
    
    public static String[][] generateCode() throws Exception{
        /**
        Function that effectively performs lexical analysis by generating a token stream and removing 
        unnecesary characters and generating a token stream/array of instructions. It does not check for
        errors at this stage as it is interpreting not compiling so can at runtime with no issues.
        **/
        System.out.println("Enter file name/path to file:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));  
        String fileName = br.readLine();
        String content = new String(Files.readAllBytes(Paths.get(fileName)));
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
        for (int i=0;i< codeArr.length;i++){
            String[] line = codeArr[i];
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
                        int tempLineNum = lineNum;
                        while (variables.get(line[1]) !=0){

                            //System.out.println("the loop condition is: " + line[1] + Integer.toString(variables.get(line[1])));
                            tempLineNum = lineNum;
                            int endCounter = 0;
                            do{
                                if (codeArr[tempLineNum][0].equals("while") ){
                                    endCounter -= 1;
                                    tempLineNum ++;
                                }else if (codeArr[tempLineNum][0].equals("end") ){
                                    endCounter ++;
                                    if (endCounter != 0){
                                        tempLineNum ++;
                                    }
                                    //System.out.println("end counter"+ Integer.toString(endCounter));
                                    
                                }
                                else{
                                    tempLineNum ++;
                                }
                                
                                
                            }while (endCounter !=0);
                            String[][] tempArr = new String[tempLineNum-lineNum][1];
                            System.arraycopy(codeArr, lineNum + 1,tempArr, 0, tempLineNum-lineNum);
                            execute(tempArr);
                        }
                        i=tempLineNum;
                        lineNum=tempLineNum;
                        //System.out.println("jump to "+ Arrays.toString(codeArr[lineNum]));
                    }
                    break;
                case "end":
                    break;
                default:
                    System.out.println("unrecognised instruction");
                    
            } 
            lineNum++;  
        }
    }
}
