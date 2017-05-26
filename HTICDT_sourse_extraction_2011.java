import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//2011年7月20日～20のフォーマットでの日経平均先物の成行注文(限月調整済み)を抽出するプログラム
public class HTICDT_sourse_extraction_2011{

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new FileReader("filelist2015.txt"));//読み取りたいファイル名の記入
        String txtFileName;

        while((txtFileName = br.readLine()) != null) {

        	String Index;
        	String db;//DBxy
        	String record1;//取引所コード
        	String security;//証券種別
        	String code1;//銘柄コード(a,b)
        	String code2;//銘柄コード(限月)
        	String code3;//銘柄コード(c,d)
        	String record2;//(レコード種別)
        	int day;//日付



        	FileReader fr = new FileReader(txtFileName);
            BufferedReader brtxt = new BufferedReader(fr);
            String line ="";

            String[] filename = txtFileName.split("\\.");

         	File file = new File(filename[1] +
         			"_" + filename[2] + //.が2つある場合
         			"_market_gcheck.txt");
         	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));

            while ((line = brtxt.readLine()) != null) {

            	Index = line;

            	db = Index.substring(0,2);
            	record1 = Index.substring(13,15);
            	security = Index.substring(15,17);
            	code1 = Index.substring(21,23);
            	code2 = Index.substring(23,26);
            	code3 = Index.substring(26,30);
            	record2 = Index.substring(34,36);

            	String d1 = Index.substring(8,12);
            	day = Integer.parseInt(d1);



            	//System.out.println(code3);
            	if((db.equals("31") && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018") && record2.equals(" 0"))
            			&& code2.equals("003") && day < 313 && day >= 0){
            		pw.println(Index);
            	}
            	else if((db.equals("31") && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018") && record2.equals(" 0"))
            			&& code2.equals("006") && day < 612 && day >= 313){
            		pw.println(Index);
            	}
            	else if((db.equals("31") && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018") && record2.equals(" 0"))
            			&& code2.equals("009") && day < 911 && day >= 612){
            		pw.println(Index);
            	}
            	else if((db.equals("31") && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018") && record2.equals(" 0"))
            			&& code2.equals("012") && day < 1211 && day >= 911){
            		pw.println(Index);
            	}
            	else if((db.equals("31") && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018") && record2.equals(" 0"))
            			&& code2.equals("103") && day < 1240 && day >= 1211){
            		pw.println(Index);
            	}
            	else{
            		//System.out.println(record1+" "+security+" "+code1+" "+code2+" "+code3);
            	}


            	}




            brtxt.close();
            fr.close();
            pw.close();

        	// txtファイル名を一行ずつロードする
        }
        br.close();

    }



}


