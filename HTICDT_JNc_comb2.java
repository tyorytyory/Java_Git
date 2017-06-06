import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//日経ニーズデータとコンマ秒データとの対応を、出来高から判定するプログラム(このプログラムはデータそのものの対応をチェックする)
import java.io.PrintWriter;
import java.util.Arrays;

public class HTICDT_JNc_comb2{

    public static void main(String[] args) throws IOException{
    	String Index;

    	int i1 = 0;


    	int number_JNIc = 0;
    	int number_HTICDT = 0;




    	int count = 1;



    	String marketvolume_JNc [] = new String [9000000];//コンマデータの出来高

    	String marketvolume_HTICDT[] = new String[9000000];//NIKKEI NEEDS(出来高)




    	String marketprice_HTICDT []  = new String [9000000];////NIKKEI NEEDS(価格)
    	String limit_HTICDT []  = new String [9000000];////NIKKEI NEEDS(価格)
    	String marketprice_JNc [] = new String [9000000];////JNc(価格)
    	String limit_JNc [] = new String [9000000];////JNc(価格)

    	String JNIc_data [] = new String[900000];
    	String HTICDT_data [] = new String[900000];
    	Arrays.fill(HTICDT_data, null);


    	String JNIc_line [] = new String[900000];
    	String HTICDT_line [] = new String[900000];



    	boolean open_session = false;//HTICDTで寄付きが起きたかどうか示す変数

        BufferedReader br = new BufferedReader(new FileReader("filelist_comb2.txt"));//読み取りたいファイル名の記入
        String txtFileName;

        while((txtFileName = br.readLine()) != null) {





        	FileReader fr = new FileReader("C:/Users/Hashimoto/Documents/pleiades/workspace/Git/2006/" + txtFileName);
            BufferedReader brtxt = new BufferedReader(fr);
            String line ="";


            while ((line = brtxt.readLine()) != null) {
            	if(1 == count%2){//JNcの読み込み

            		String[] JNIc_split = line.split(",", 0);

            		//System.out.println(line);

            		if((JNIc_split.length == 13 && (JNIc_split[12].equals("Open|High|Low[USER]") || JNIc_split[4].equals(" [TRADE_TONE]")))){
            			open_session = true;
            		}
            		else if(open_session == true){
            			if(JNIc_split[4].equals("Settlement Price")){
            				open_session = false;
            				do{
            					if((JNIc_data[number_JNIc - 1].substring(0,5)).equals("Quote")){
            						number_JNIc--;
            					}
            				}while((JNIc_data[number_JNIc].substring(0,5)).equals("Trade"));
            			}
                    	else{
                    		if(JNIc_split[4].equals("Trade")){
                    			JNIc_data[number_JNIc] = JNIc_split[4] + JNIc_split[5] + JNIc_split[6];
                    		}
                    		else if(JNIc_split[4].equals("Quote")){
                    			JNIc_data[number_JNIc] = JNIc_split[4] + JNIc_split[8] + JNIc_split[9] + JNIc_split[10] + JNIc_split[11];
                    		}
                    		JNIc_line[number_JNIc] = line;

                    		number_JNIc++;
                    	}
            		}
            	}
            	else if(0 == count%2){//HTICDTの読み込み

            		 //System.out.println(line);

            		String[] HTICDT_split = line.split(",", 0);

            		if(HTICDT_split[4].equals("Trade")){
            			HTICDT_data[number_HTICDT] = HTICDT_split[4] + HTICDT_split[5] + HTICDT_split[6];
            		}
            		else if(HTICDT_split[4].equals("Quote")){
            			HTICDT_data[number_HTICDT] = HTICDT_split[4] + HTICDT_split[8] + HTICDT_split[9] + HTICDT_split[10] + HTICDT_split[11];
            		}
            		//System.out.println(HTICDT_data[number_HTICDT]);
            		HTICDT_line[number_HTICDT] = line;

            		number_HTICDT++;

            	}
            }

            if(0 == count%2){
            	String[] filename = txtFileName.split("\\.");
         		File file = new File(filename[0] +	"_comb2222.csv");
              	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
              	File file1 = new File(filename[0] +	"_delete.csv");
              	PrintWriter pw1 = new PrintWriter(new BufferedWriter(new FileWriter(file1)));


                number_JNIc = 0;//初期化
              	number_HTICDT = 0;//初期化

              	int JNIc_delete = 1;


              	do{
              		if(HTICDT_data[number_HTICDT].equals(JNIc_data[number_JNIc])){
              			//System.out.println("OK");
              			pw.println(HTICDT_line[number_HTICDT] + "," + JNIc_line[number_JNIc]);
              			number_JNIc++;
              			number_HTICDT++;
              			//System.out.println(HTICDT_data[number_HTICDT]);
                      	//System.out.println(JNIc_data[number_JNIc]);
              		}
              		else{
              			 while(!(HTICDT_data[number_HTICDT].equals(JNIc_data[number_JNIc + JNIc_delete]))){
              				//System.out.println(HTICDT_line[number_HTICDT] + "," + JNIc_line[number_JNIc]);
              				 JNIc_delete++;
              				 if(JNIc_delete == 10000){
              					JNIc_delete = 1;
              					pw1.println(HTICDT_line[number_HTICDT]);
              					number_HTICDT++;
              					//System.out.println("1231");
              				 }
              			}
              			pw.println(HTICDT_line[number_HTICDT] + "," + JNIc_line[number_JNIc + JNIc_delete]);
              			for(int i = 0;i < JNIc_delete;i++){
              				pw1.println(JNIc_line[number_JNIc + i]);
              			}
              			number_JNIc = number_JNIc + JNIc_delete + 1;
              			number_HTICDT++;
              			//System.out.println("");
              			JNIc_delete = 1;
              		}
              		//System.out.println(HTICDT_line[number_HTICDT]);
              	}while(HTICDT_data[number_HTICDT] != null);
                pw.close();
                pw1.close();
            }

            count++;




            brtxt.close();
            fr.close();





        }








    }
}