import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//KS検定の結果をエクセルへ簡単に貼り付けることができるようにするプログラム
public class Kolmo_cat{

    public static void main(String[] args) throws IOException{

    	String Index;
        String Kolmo[][] = new String [25][300];
        int Kolmo_count[] = new int [25];
        int number = 1;
        String Kolmo_natural = "0.0";
        String[] filename = new String [20];
        int nodata_number = 0;


        BufferedReader br = new BufferedReader(new FileReader("filelist.txt"));//読み取りたいファイル名の記入
        String txtFileName;

        while((txtFileName = br.readLine()) != null) {




        FileReader fr = new FileReader(txtFileName);
        BufferedReader brtxt = new BufferedReader(fr);
        String line ="";

        filename = txtFileName.split("\\_");


        while ((line = brtxt.readLine()) != null) {

        	Index = line;
        	if(Index.length()<=30){
            	Kolmo_natural = Index;
        	}
        	else if(Index.length()>30){
        		Kolmo_natural = "-1";
        	}
        	if(filename[0].equals("2008")){//サーキットブレイカー等が原因で取引がとまったところ
        		if((filename[1].equals("01") && number == 192)
        				|| (filename[1].equals("02") && (number ==192 || number == 193 || number == 195))){
        			Kolmo_natural = "-1";
        		}
        	}
        	else if(filename[0].equals("2011")){//サーキットブレイカー等が原因で取引がとまったところ
        		if((filename[1].equals("01") && number == 48)
        				|| (filename[1].equals("09") && number ==22)
        					|| (filename[1].equals("10") && number ==22)
        						|| (filename[1].equals("11") && number ==21)
        							|| (filename[1].equals("13") && number ==22)
        								|| (filename[1].equals("16") && number ==49)){
        										Kolmo_natural = "-1";
        		}
        	}
        	else if(filename[0].equals("2013")){//サーキットブレイカー等が原因で取引がとまったところ
        		if((filename[1].equals("09") && number == 40)
        				|| (filename[1].equals("21") && number == 40)
        					|| (filename[1].equals("22") && number == 94)
           						|| (filename[1].equals("23") && number == 94)){
        							Kolmo_natural = "-1";
        		}
        	}
        	else if(filename[0].equals("2014")){//サーキットブレイカー等が原因で取引がとまったところ
        		if((filename[1].equals("09") && number == 40)){
        							Kolmo_natural = "-1";
        		}
        	}
        	else if(filename[0].equals("2016")){//サーキットブレイカー等が原因で取引がとまったところ
        		if((filename[1].equals("01") && number == 132)){
        							Kolmo_natural = "-1";
        		}
        	}

    		Kolmo[Integer.parseInt(filename[1])][number] = Kolmo_natural;
    		number++;



        }

        number = 1;

        if(Integer.parseInt(filename[1]) % 24 == 0){

			File file = new File(filename[0] + "_" + filename[2] + "_" + filename[3] + "_" + filename[4]  + ".txt");
	     	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));

	     	for(int i = 1;i<=271;i++){
	     		for(int j = 1;j<=24;j++){
	         		if(Kolmo[j][i] != null && Kolmo[j][i].length() <= 30 ){
	         			pw.print(Kolmo[j][i] + ",");
	         			Kolmo[j][i] = null;
	         		}
	         		else if(Kolmo[j][i] != null && Kolmo[j][i].equals("-1")){
	         			pw.print("lackofdata,");
	         			nodata_number++;
	         			Kolmo[j][i] = null;
	         			//System.out.println(nodata_number);
	         		}
	         		else{
	         			pw.print(",");
	         			nodata_number++;
	         			//System.out.println(nodata_number);
	         		}
	     		}
	     		pw.println();
	     		if(nodata_number == 24){
	     			nodata_number = 0;
	     			break;
	     		}
	     		nodata_number = 0;
	     	}
	     	//number = 0;

	        pw.close();

		}






        brtxt.close();
        fr.close();


	}



        br.close();
    }






}

