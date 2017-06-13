import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

//日経NEEDSデータ(HTICDT)をロイター通信社(JNIc)のデータフォーマットに変換するプログラム
//寄り付きから引けまでの取引を抽出　昼休みの有無は関係なく、9時から取引終了まですべてのデータを抽出している。

public class HTICDT_change_JNIc{

    public static void main(String[] args) throws IOException{



    	String HTICDT_day = null;//日付
    	String HTICDT_time_hour = null;//時間(時)
    	String HTICDT_time_minute = null;//時間(分)
    	String HTICDT_time_second = null;//時間(秒)
    	String HTICDT_record = null;//Trade or Quote
    	String HTICDT_sell_information = null;//最良売気配の情報
    	String HTICDT_quotes_information = null;//最良売気配と最良買気配の書き込む情報
    	String HTICDT_before_price_volume_info = null;//一つ前の最良売気配と最良買気配の価格と厚みのデータ。JNIcと同様のデータに書き込むif文で必要となる。
    	String HTICDT_price_volume_info = null;//最良売気配と最良買気配の価格と厚みのデータ。JNIcと同様のデータに書き込むif文で必要となる。

    	int HTICDT_time_sum = 0;//秒換算のある時点での時刻
    	int HTICDT_before_time_sum = 0;//ひとつ前の取引の秒換算のある時点での時刻
    	String HTICDT_before_day = "";//そのデータの一つ前のデータの日付
    	boolean open_session = false;//true→ザラバ、false→not ザラバ


        BufferedReader br = new BufferedReader(new FileReader("filelist4.txt"));//読み取りたいファイル名の記入
        String txtFileName;

        while((txtFileName = br.readLine()) != null) {





        	FileReader fr = new FileReader("C:/Users/Hashimoto/Documents/pleiades/workspace/Git/2011/" + txtFileName);
            BufferedReader brtxt = new BufferedReader(fr);
            String line ="";

            String[] filename = txtFileName.split("\\.");
    		File file = new File(filename[0] +	"_change.csv");
         	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));



            while ((line = brtxt.readLine()) != null) {

            	HTICDT_day = line.substring(4,12);//日付
            	HTICDT_time_hour = line.substring(30,32);//時間
            	HTICDT_time_minute = line.substring(32,34);//分
            	HTICDT_time_second = line.substring(36,38);//秒
            	HTICDT_record = line.substring(34,36);//Trade or Quote

            	HTICDT_time_sum = (Integer.parseInt(HTICDT_time_hour)*3600) + (Integer.parseInt(HTICDT_time_minute)*60) + (Integer.parseInt(HTICDT_time_second));//ある時点の時間を秒換算

            	if(line.substring(49,52).equals("  1") && Integer.parseInt(HTICDT_time_hour) <= 15){//寄り付き(HTICDT_time_hourの制約があるのは、読み込んだデータにナイトセッションのデータがありから
        			open_session = true;
        			HTICDT_before_day = HTICDT_day;
        			if(HTICDT_time_hour.equals("12")){
        				pw.println("HTICDT,NIKKEI," + HTICDT_day + "," + HTICDT_time_hour  + ":" + HTICDT_time_minute + ":" + HTICDT_time_second
        						+ ".000000,Trade," + Integer.parseInt(line.substring(42,47)) + "," + Integer.parseInt(line.substring(56,66)) + ",,,,,,");
        			}
        		}
            	else if(!(HTICDT_before_day.equals(HTICDT_day)) && !(HTICDT_before_day.equals(""))){
            		open_session = false;
            		HTICDT_before_day = HTICDT_day;
            	}
        		else if(open_session == true){
        			HTICDT_before_day = HTICDT_day;
        			if(HTICDT_record.equals(" 0")){//約定
        				pw.println("HTICDT,NIKKEI," + HTICDT_day + "," + HTICDT_time_hour  + ":" + HTICDT_time_minute + ":" + HTICDT_time_second
        						+ ".000000,Trade," + Integer.parseInt(line.substring(42,47)) + "," + Integer.parseInt(line.substring(56,66)) + ",,,,,,");
            		}
            		else if(line.substring(34,36).equals("33") && line.substring(49,52).equals("  0")){//最良売気配
            			HTICDT_sell_information =Integer.parseInt(line.substring(42,47)) + "," + Integer.parseInt(line.substring(56,66)) + ",";
            		}
            		else if(line.substring(34,36).equals("33") && line.substring(49,52).equals("128")){//最良買気配

            			HTICDT_quotes_information = "HTICDT,NIKKEI," + HTICDT_day + "," + HTICDT_time_hour  + ":" + HTICDT_time_minute + ":" + HTICDT_time_second
        						+ ".000000,Quote,,,," + Integer.parseInt(line.substring(42,47)) + "," + Integer.parseInt(line.substring(56,66)) + "," + HTICDT_sell_information;

            			HTICDT_price_volume_info = Integer.parseInt(line.substring(42,47)) + "," + Integer.parseInt(line.substring(56,66)) + "," + HTICDT_sell_information;



            			if(HTICDT_before_price_volume_info == null){//JNIcと同様のデータにするために必要なif文（JNIcは板の厚みが同じときはデータに反映させないが、HTICDTは板の厚みに変化がないデータも反映させているため）。つまりHTICDTのほうがデータは多い。
            				HTICDT_before_price_volume_info = HTICDT_price_volume_info;
            				HTICDT_before_time_sum = HTICDT_time_sum;

            				pw.println(HTICDT_quotes_information);
            			}
            			else{
            				if(!(HTICDT_before_price_volume_info.equals(HTICDT_price_volume_info))//情報が異なるとき
            						|| (HTICDT_before_price_volume_info.equals(HTICDT_price_volume_info)//情報自身が同じでかつ
            						&& ((HTICDT_time_sum - HTICDT_before_time_sum) > 900))//時間が15分以上はなれるとき（15分は適当。ここを操作することで出力されるデータが異なる）
            						){

            					HTICDT_before_price_volume_info = HTICDT_price_volume_info;
            					HTICDT_before_time_sum = HTICDT_time_sum;

                				pw.println(HTICDT_quotes_information);
            				}
            			}


            		}
        			if((line.substring(52,55).equals("128") || line.substring(52,55).equals("160")) && Integer.parseInt(HTICDT_time_hour) >= 15){//終了約定コード(128,160)かつ日中のザラバ取引が終了する時間（15時代)
                		open_session = false;
                	}

        		}




            }

            pw.close();
            brtxt.close();
            fr.close();

        }

    }
}