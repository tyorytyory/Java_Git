import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//日経NEEDSをロイター通信社のデータフォーマットに変換するプログラム
//寄り付きから引けまでの取引を抽出　昼休みの有無は関係ない
import java.io.PrintWriter;

public class HTICDT_change_JNIc{

    public static void main(String[] args) throws IOException{



    	String HTICDT_day = null;//日付
    	String HTICDT_time_hour = null;//時間(時)
    	String HTICDT_time_minute = null;//時間(分)
    	String HTICDT_time_second = null;//時間(秒)
    	String HTICDT_record = null;//Trade or Quote
    	String HTICDT_sell_information = null;
    	String HTICDT_information = null;
    	String HTICDT_before_price_volume_info = null;
    	String HTICDT_price_volume_info = null;

    	String HTICDT_time_second_before = null;
    	String HTICDT_time_minute_before = null;
    	String HTICDT_time_hour_before = null;

    	boolean open_session = false;//true→ザラバ、false→not ザラバ


        BufferedReader br = new BufferedReader(new FileReader("filelist4.txt"));//読み取りたいファイル名の記入
        String txtFileName;

        while((txtFileName = br.readLine()) != null) {





        	FileReader fr = new FileReader("C:/Users/Hashimoto/Documents/pleiades/workspace/Git/2006/" + txtFileName);
            BufferedReader brtxt = new BufferedReader(fr);
            String line ="";

            String[] filename = txtFileName.split("\\.");
    		File file = new File(filename[0] +	"_change.csv");
         	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));



            while ((line = brtxt.readLine()) != null) {

            	HTICDT_day = line.substring(4,12);
            	HTICDT_time_hour = line.substring(30,32);
            	HTICDT_time_minute = line.substring(32,34);
            	HTICDT_time_second = line.substring(36,38);
            	HTICDT_record = line.substring(34,36);




            	if(line.substring(49,52).equals("  1")){
        			open_session = true;
        			if(HTICDT_time_hour.equals("12")){
        				pw.println("HTICDT,NIKKEI," + HTICDT_day + "," + HTICDT_time_hour  + ":" + HTICDT_time_minute + ":" + HTICDT_time_second
        						+ ".000000,Trade," + line.substring(42,47) + "," + Integer.parseInt(line.substring(56,66)) + ",,,,,,");
        			}
        		}
        		else if(open_session == true){
        			if(HTICDT_record.equals(" 0")){//最良売り気配(  0)or最良買い気配(128)or寄り付き（  1)){
        				pw.println("HTICDT,NIKKEI," + HTICDT_day + "," + HTICDT_time_hour  + ":" + HTICDT_time_minute + ":" + HTICDT_time_second
        						+ ".000000,Trade," + line.substring(42,47) + "," + Integer.parseInt(line.substring(56,66)) + ",,,,,,");
            		}
            		else if(line.substring(34,36).equals("33") && line.substring(49,52).equals("  0")){
            			HTICDT_sell_information = line.substring(42,47) + "," + Integer.parseInt(line.substring(56,66)) + ",";
            		}
            		else if(line.substring(34,36).equals("33") && line.substring(49,52).equals("128")){

            			HTICDT_information = "HTICDT,NIKKEI," + HTICDT_day + "," + HTICDT_time_hour  + ":" + HTICDT_time_minute + ":" + HTICDT_time_second
        						+ ".000000,Quote,,,," + line.substring(42,47) + "," + Integer.parseInt(line.substring(56,66)) + "," + HTICDT_sell_information;

            			HTICDT_price_volume_info = line.substring(42,47) + "," + Integer.parseInt(line.substring(56,66)) + "," + HTICDT_sell_information;



            			if(HTICDT_before_price_volume_info == null){
            				HTICDT_before_price_volume_info = HTICDT_price_volume_info;
            				HTICDT_time_second_before = HTICDT_time_second;
            				HTICDT_time_minute_before = HTICDT_time_minute;
            				HTICDT_time_hour_before = HTICDT_time_hour;

            				pw.println(HTICDT_information);
            			}
            			else{
            				if(!(HTICDT_before_price_volume_info.equals(HTICDT_price_volume_info))
            						//|| (HTICDT_before_price_volume_info.equals(HTICDT_price_volume_info) )
            						//&& !(HTICDT_time_second_before.equals(HTICDT_time_second))
            						//&& !(HTICDT_time_minute.equals(HTICDT_time_minute_before))
            						){

            					HTICDT_before_price_volume_info = HTICDT_price_volume_info;
            					HTICDT_time_second_before = HTICDT_time_second;
            					HTICDT_time_minute_before = HTICDT_time_minute;
            					HTICDT_time_hour_before = HTICDT_time_hour;

                				pw.println(HTICDT_information);
            				}
            			}


            		}
        			if((line.substring(52,55).equals("128") || line.substring(52,55).equals("160")) && Integer.parseInt(HTICDT_time_hour) >= 15){
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