import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//JNIcから約定（Trade)のデータを抽出するプログラム
public class Trade_extraction{

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new FileReader("filelist.txt"));//読み取りたいファイル名の記入
        String txtFileName;

        //String JNIc_split[] = new String[9000000];

        while((txtFileName = br.readLine()) != null) {


        FileReader fr = new FileReader(txtFileName);
        BufferedReader brtxt = new BufferedReader(fr);
        String line ="";

        String[] filename = txtFileName.split("_");
        //String[] filename = txtFileName.split("\\.");




        System.out.println(txtFileName);
        System.out.println(filename[0]);


        File file1 = new File( filename[0] + "_"+ filename[1] + "_" + filename[3] + ".txt");
        //File file1 = new File("../data/" + filename[0] + "_Trade.txt");

     	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file1)));

        while ((line = brtxt.readLine()) != null) {

        	String JNIc_split[] = line.split(",", 0);//JNIc用

        	if(JNIc_split[4].equals("Trade")){//JNIc用
        		pw.println(line);
        	}

        	/*if((line.substring(0,2)).equals("31")){
        		pw.println(line);
        	}*/






        }


        brtxt.close();
        fr.close();
        pw.close();

        }
    }
}
