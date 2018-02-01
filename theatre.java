import java.io.*;
import static java.lang.System.exit;


public class theatre {
    String[][] theatreSeats= new String[10][20];
    public theatre(String filename){
        initializeArray();
        try {
            readFile(filename);
        }
        catch (IOException e){
            System.out.println("The file specified was not found, exiting.");
            exit(1);
        }
    }
    public  void readFile(String filename) throws IOException{

        FileInputStream fstream = new FileInputStream(filename);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;

        while ((strLine = br.readLine()) != null)   {
            String[] row=strLine.split("\\s+");
            processRequest(row[0],Integer.parseInt(row[1]));

        }

        br.close();


    }
    public void processRequest(String rowNum, int reservNum){
        boolean check=false;
        for(int i=0;i<theatreSeats.length;i++){
            for(int j=0;j<theatreSeats[i].length;j++){
                if(j+reservNum<theatreSeats[i].length){
                    int freeSeats=calculatefree(theatreSeats[i],reservNum);
                    if(freeSeats>-1){
                        markIndices(i,freeSeats,reservNum);
                        outputResults(rowNum,i,freeSeats,reservNum);
                        check=true;
                    }
                }
                if(check){
                    break;
                }
            }
            if(check){
                break;
            }
        }
        if(!check) {
            System.out.println("Counldn't find a place to sit all of you together, sorry.");
            exit(1);
        }
    }
    public  int calculatefree(String[] row,int reservNum){
        StringBuilder builder = new StringBuilder();
        for(String s : row) {
            builder.append(s);
        }
        String str = builder.toString();
        String freeSpace="";
        for(int i=0;i<reservNum;i++){
            freeSpace+="s";
        }

        boolean isfree=str.contains(freeSpace);
        if(isfree){
            return str.indexOf(freeSpace);
        }
        else{
            return -1;
        }
    }
    public void initializeArray(){
        for(int i=0;i<theatreSeats.length;i++){
            for(int j=0;j<theatreSeats[i].length;j++){
                theatreSeats[i][j]="s";
            }
        }

    }
    public void markIndices(int i,int j,int reserveNum){
        for(int m=j;m<j+reserveNum;m++){
            theatreSeats[i][m]="r";
        }

    }
    public void outputResults(String order,int i,int j, int index){
        String outline=order+" ";
        String alphabet = "abcdefghijklmnopqrstuvwxyz".toUpperCase();

        for(int m=j;m<j+index;m++){
            outline+=alphabet.charAt(i)+String.valueOf(m);
            if(m<(j+index-1)){
                outline+=',';
            }
        }
        try(FileWriter fw = new FileWriter("output.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(outline);
        } catch (IOException e) {
        }
    }
}
