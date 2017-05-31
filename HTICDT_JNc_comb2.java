import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
//日経ニーズデータとコンマ秒データとの対応を、出来高から判定するプログラム
//指値注文にも対応させる
public class HTICDT_JNc_comb2{

    public static void main(String[] args) throws IOException{
    	String Index;

    	int i1 = 0;


    	int number1 = 0;
    	int number2 = 0;
    	int number3 = 0;
    	int number4 = 0;



    	int count = 1;
    	int i2 = 0;
    	int i3 = 0;


    	String nikkei_HTICDT[] = new String[9000000];//NIKKEI NEEDS

    	String nikkei_JNc[] = new String[9000000];//コンマ秒(JNc)

    	int volume = 0;


    	String marketvolume_JNc [] = new String [9000000];//コンマデータの出来高
    	String limitvolume_JNc [] = new String [9000000];//コンマデータの指値
    	String marketvolume_HTICDT[] = new String[9000000];//NIKKEI NEEDS(出来高)
    	String limitvolume_HTICDT[] = new String[9000000];//NIKKEI NEEDS(指値)
    	int sum_volume = 0;//出来高の総計(コンマデータ)

    	int count_konma = 0;//コンマデータの行の確認。
    	int length_konma = 0;//コンマデータの長さの確認

    	String time_HTICDT;//取引時間
    	String time_JNc;//取引時間

    	String marketprice_HTICDT []  = new String [9000000];////NIKKEI NEEDS(価格)
    	String marketprice_JNc [] = new String [9000000];////JNc(価格)
    	String limitprice_JNc [] = new String [9000000];////JNc(価格)

    	String bid_or_ask_HTICDT [] =  new String[9000000];//NIKKEI NEEDS(bid or ask)
    	String bid_or_ask_JNc [] =  new String[9000000];//JNc(bid or ask)


    	String a = null;
    	String db1 = null;
    	String db2 = null;
    	String db3 = null;
    	String db4 = null;




        BufferedReader br = new BufferedReader(new FileReader("filelist4.txt"));//読み取りたいファイル名の記入
        String txtFileName;

        while((txtFileName = br.readLine()) != null) {





        	FileReader fr = new FileReader("C:/Users/Hashimoto/Documents/pleiades/workspace/Git/2006/" + txtFileName);
            BufferedReader brtxt = new BufferedReader(fr);
            String line ="";





            while ((line = brtxt.readLine()) != null) {
            	if(1 == count%2){
            		//Index = line;
            		//System.out.println(line);//JNcの読み込み
            		String[] run = line.split(",", 0);

                	if(run[4].equals("Trade")){
                		marketvolume_JNc[number1] = run[6];
                		marketprice_JNc[number1] = run[5];
                		number1++;
                	}
                	else if(run[4].equals("Quote")){
                		limitvolume_JNc[number2] = run[6];
                		limitprice_JNc[number2] = run[8] + run[9] + run[10] + run[11];
                		System.out.println(limitprice_JNc[number1]);
                		number2++;
                	}
            	}
            	else if(0 == count%2){//寄り付き反映
            		if(line.substring(34,36).equals(" 0")){//最良売り気配(  0)or最良買い気配(128)or寄り付き（  1)){
            			marketvolume_HTICDT[number3] = line.substring(56,66);
            			marketprice_HTICDT[number3]  = line.substring(41,47);
            		}
            		else if(line.substring(34,36).equals("33") && line.substring(49,52).equals("  0")){

            		}
            		else if(line.substring(34,36).equals("33") && line.substring(49,52).equals("128")){

            		}
            		//HTICDTの読み込み

            	}
            }
            if(0 == count%2){

            }
        }
    }
}