import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//HTICDT_change_JNIc.javaで作成したchangeファイル(HTICDT)とJNIcデータを合体させ、どのデータが対応しているか否かをチェックする。出力されるファイルは２つ。
import java.io.PrintWriter;
import java.util.Arrays;

public class HTICDT_JNc_comb2{

    public static void main(String[] args) throws IOException{



    	int number_JNIc = 0;
    	int number_HTICDT = 0;

    	int count = 1;

    	String JNIc_data [] = new String[900000];//比較するデータ
    	String HTICDT_data [] = new String[900000];//比較するデータ
    	Arrays.fill(HTICDT_data, null);//初期化
    	String JNIc_line [] = new String[900000];//読み込んだデータの挿入(JNIc)
    	String HTICDT_line [] = new String[900000];//読み込んだデータの挿入(HTICDT)

    	String JNIc_before_day = null;//そのデータの一つ前のデータの日付



    	boolean open_session = false;//HTICDTで寄付きが起きたかどうか示す変数

        BufferedReader br = new BufferedReader(new FileReader("filelist_comb2.txt"));//読み取りたいファイル名の記入
        String txtFileName;

        while((txtFileName = br.readLine()) != null) {

        	FileReader fr = new FileReader("C:/Users/Hashimoto/Documents/pleiades/workspace/Git/" + txtFileName);
            BufferedReader brtxt = new BufferedReader(fr);
            String line ="";


            while ((line = brtxt.readLine()) != null) {
            	if(1 == count%2){//JNIcの読み込み


            		String[] JNIc_split = line.split(",", 0);

            		if(!(JNIc_split[2].equals(JNIc_before_day))){//Settlementが無いデータに対する対応。これによりopne_sessionを初期化
            			open_session = false;
            			//System.out.println(JNIc_split[2]);
            			JNIc_before_day = JNIc_split[2];
            		}

            		if(JNIc_split.length == 13 && (JNIc_split[12].equals("Open|High|Low[USER]") || JNIc_split[12].equals("Open|High|Low|Open 1st[USER]"))){//寄り付き
            			open_session = true;
            		}
            		else if(open_session == true){
            			if(JNIc_split[4].equals("Settlement Price")){//引け
            				open_session = false;
            				do{
            					if((JNIc_data[number_JNIc - 1].substring(0,5)).equals("Quote")){
            						number_JNIc--;
            					}
            				}while((JNIc_data[number_JNIc].substring(0,5)).equals("Trade"));//引けからその前の約定の間のQuoteは削除
            			}
                    	else{
                    		if(JNIc_split[4].equals("Trade")){//約定
                    			JNIc_data[number_JNIc] = JNIc_split[4] + JNIc_split[5] + JNIc_split[6];
                    		}
                    		else if(JNIc_split[4].equals("Quote")){//最良気配
                    			JNIc_data[number_JNIc] = JNIc_split[4] + JNIc_split[8] + JNIc_split[9] + JNIc_split[10] + JNIc_split[11];
                    		}
                    		JNIc_line[number_JNIc] = line;
                    		number_JNIc++;
                    		JNIc_before_day = JNIc_split[2];
                    	}
            		}
            	}
            	else if(0 == count%2){//HTICDTの読み込み

            		String[] HTICDT_split = line.split(",", 0);

            		if(HTICDT_split[4].equals("Trade")){//約定
            			HTICDT_data[number_HTICDT] = HTICDT_split[4] + HTICDT_split[5] + HTICDT_split[6];
            		}
            		else if(HTICDT_split[4].equals("Quote")){//最良気配
            			HTICDT_data[number_HTICDT] = HTICDT_split[4] + HTICDT_split[8] + HTICDT_split[9] + HTICDT_split[10] + HTICDT_split[11];
            		}
            		HTICDT_line[number_HTICDT] = line;
            		number_HTICDT++;
            	}
            }

            //System.out.println(number_JNIc);

            if(0 == count%2){//データの書き込み
            	String[] filename = txtFileName.split("_");
         		File file = new File(filename[0] +	"_quote_comb.csv");//結語データ
              	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
              	File file1 = new File(filename[0] +	"_delete.csv");//削除したデータ
              	PrintWriter pw1 = new PrintWriter(new BufferedWriter(new FileWriter(file1)));


                number_JNIc = 0;//初期化
              	number_HTICDT = 0;//初期化

              	int JNIc_delete = 0;//JNIcのデータの削除を行うための変数


              	do{
              		if(HTICDT_data[number_HTICDT].equals(JNIc_data[number_JNIc])){//データが同じとき書き込み
              			pw.println(HTICDT_line[number_HTICDT] + "," + JNIc_line[number_JNIc]);
              			number_JNIc++;
              			number_HTICDT++;
              		}
              		else{
              			 while(!(HTICDT_data[number_HTICDT].equals(JNIc_data[number_JNIc + JNIc_delete]))){//データの捜索・削除
              				//System.out.println(HTICDT_line[number_HTICDT]);
              				String[] JNIc_split = JNIc_line[number_JNIc + JNIc_delete].split(",", 0);
              				String[] HTICDT_split = HTICDT_line[number_HTICDT].split(",", 0);
              				//System.out.println(JNIc_split[2]);
              					JNIc_delete++;
              					//System.out.println(JNIc_line[number_JNIc]);
                 				 if(JNIc_delete == 10000){
                 					JNIc_delete = 0;
                 					JNIc_split = JNIc_line[number_JNIc].split(",", 0);
             						HTICDT_split = HTICDT_line[number_HTICDT].split(",", 0);
                 					if(JNIc_split[2].equals(HTICDT_split[2])){//日付が同じとき

                     					pw1.println(HTICDT_line[number_HTICDT]);//削除したデータの書き込み(HTICDT)
                     					number_HTICDT++;
                 					}
                 					else if(!(JNIc_split[2].equals(HTICDT_split[2]))){//日付が異なるとき
                 						if(Integer.parseInt(HTICDT_split[2]) < Integer.parseInt(JNIc_split[2])){
                 							while(!(HTICDT_split[2].equals(JNIc_split[2]))){
                 								pw1.println(HTICDT_line[number_HTICDT]);//削除したデータの書き込み(HTICDT)
                     							number_HTICDT++;
                 								HTICDT_split = HTICDT_line[number_HTICDT].split(",", 0);
                     							System.out.println(HTICDT_line[number_HTICDT] + "," + JNIc_line[number_JNIc]);
                     						}
                 						}
                 						else if(Integer.parseInt(HTICDT_split[2]) > Integer.parseInt(JNIc_split[2])){
                 							while(!(HTICDT_split[2].equals(JNIc_split[2]))){
                 								pw1.println(JNIc_line[number_JNIc]);//削除したデータの書き込み(JNIc)
                 								number_JNIc++;
                 								JNIc_split = JNIc_line[number_JNIc].split(",", 0);
                     						}
                 						}
                 					}
                 				 }
              			}
              			pw.println(HTICDT_line[number_HTICDT] + "," + JNIc_line[number_JNIc + JNIc_delete]);
              			//System.out.println(HTICDT_line[number_HTICDT] + "," + JNIc_line[number_JNIc + JNIc_delete]);
              			for(int i = 0;i < JNIc_delete;i++){
              				pw1.println(JNIc_line[number_JNIc + i]);//削除したデータの書き込み(JNIc)
              			}
              			number_JNIc = number_JNIc + JNIc_delete + 1;
              			number_HTICDT++;
              			JNIc_delete = 1;//初期化
              		}

              	}while(HTICDT_data[number_HTICDT] != null);
                pw.close();
                pw1.close();

                number_HTICDT = 0;//初期化
                number_JNIc = 0;//初期化
                open_session = false;//初期化
                Arrays.fill(JNIc_data, null);//初期化
                Arrays.fill(HTICDT_data, null);//初期化
                Arrays.fill(JNIc_line, null);//初期化
                Arrays.fill(HTICDT_line, null);//初期化
            }

            count++;

            brtxt.close();
            fr.close();

        }

    }
}