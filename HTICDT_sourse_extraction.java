import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//2010年7月16日以前のフォーマットでの日経平均先物（ちなみにHTICDT-の元データ）の成行注文(限月調整済み)や指値注文(限月調整済み)などを抽出するプログラム
public class HTICDT_sourse_extraction{

    public static void main(String[] args) throws IOException{
    	int count=0;

        BufferedReader br = new BufferedReader(new FileReader("filelist.txt"));//読み取りたいファイル名の記入
        String txtFileName;

        while((txtFileName = br.readLine()) != null) {

        	String Index;
        	String db;//DBxy(レコード種別)
        	String record1;//取引所コード
        	String security;//証券種別
        	String code1;//銘柄コード(a,b)
        	String code2;//銘柄コード(限月)
        	String code3;//銘柄コード(c,d)
        	String record2;//(レコード種別 約定/気配)
        	int day;//日付



        	FileReader fr = new FileReader("E:/日経平均先物/2006/" + txtFileName);
            BufferedReader brtxt = new BufferedReader(fr);
            String line ="";

            String[] filename = txtFileName.split("\\.");

         	File file = new File(filename[1] +
         			"_"+filename[2] + //.が2つある場合
         			"_market_limit_gcheck.txt");
         	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));

            while ((line = brtxt.readLine()) != null) {

            	Index = line;

            	db = Index.substring(2,4);
            	record1 = Index.substring(13,15);
            	security = Index.substring(15,17);
            	code1 = Index.substring(21,23);
            	code2 = Index.substring(23,26);
            	code3 = Index.substring(26,30);
            	record2 = Index.substring(34,36);
            	count++;



            	String d1 = Index.substring(8,12);
            	day = Integer.parseInt(d1);


            	//System.out.println(day);



            	//System.out.println(code3);
            	if((db.equals("31") && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& record2.equals(" 0"))
            			&& code2.equals("103") && day < 300 && day >= 0)
            	{
            		pw.println(Index);
            	}
            	else if((db.equals("31") && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& record2.equals(" 0"))
            			&& code2.equals("106") && day < 600 && day >= 300)
            	{
            		pw.println(Index);
            	}
            	else if((db.equals("31") && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& record2.equals(" 0"))
            			&& code2.equals("109") && day < 900 && day >= 600)
            	{
            		pw.println(Index);
            	}
            	else if((db.equals("31") && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& record2.equals(" 0"))
            			&& code2.equals("112") && day < 1200 && day >= 900)
            	{
            		pw.println(Index);
            	}
            	else if((db.equals("31") && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& record2.equals(" 0"))
            			&& code2.equals("203") && day < 1300 && day >= 1200)
            	{
            		pw.println(Index);
            	}
            	else{
            		//System.out.println(record1+" "+security+" "+code1+" "+code2+" "+code3);
            	}//2006年バージョン


            	/*if((db.equals("31") && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& record2.equals(" 0"))
            			&& code2.equals("203") && day < 309 && day >= 0)
            	{
            		pw.println(Index);
            	}
            	else if((db.equals("31") && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& record2.equals(" 0"))
            			&& code2.equals("206") && day < 608 && day >= 309)
            	{
            		pw.println(Index);
            	}
            	else if((db.equals("31") && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& record2.equals(" 0"))
            			&& code2.equals("209") && day < 914 && day >= 608)
            	{
            		pw.println(Index);
            	}
            	else if((db.equals("31") && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& record2.equals(" 0"))
            			&& code2.equals("212") && day < 1214 && day >= 914)
            	{
            		pw.println(Index);
            	}
            	else if((db.equals("31") && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& record2.equals(" 0"))
            			&& code2.equals("303") && day < 1300 && day >= 1214)
            	{
            		pw.println(Index);
            	}
            	else{
            		//System.out.println(record1+" "+security+" "+code1+" "+code2+" "+code3);
            	}*///2007年バージョン

            	/*if((db.equals("31") && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& record2.equals(" 0"))
            			&& code2.equals("303") && day < 314 && day >= 0)
            	{
            		pw.println(Index);
            	}
            	else if((db.equals("31") && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& record2.equals(" 0"))
            			&& code2.equals("306") && day < 613 && day >= 314)
            	{
            		pw.println(Index);
            	}
            	else if((db.equals("31") && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& record2.equals(" 0"))
            			&& code2.equals("309") && day < 912 && day >= 613)
            	{
            		pw.println(Index);
            	}
            	else if((db.equals("31") && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& record2.equals(" 0"))
            			&& code2.equals("312") && day < 1212 && day >= 912)
            	{
            		pw.println(Index);
            	}
            	else if((db.equals("31") && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& record2.equals(" 0"))
            			&& code2.equals("403") && day < 1300 && day >= 1212)
            	{
            		pw.println(Index);
            	}
            	else{
            		//System.out.println(record1+" "+security+" "+code1+" "+code2+" "+code3);
            	}*///2008年バージョン

            	/*if((db.equals("31") && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& record2.equals(" 0"))
            			&& code2.equals("403") && day < 313 && day >= 0)
            	{
            		pw.println(Index);
            	}
            	else if((db.equals("31") && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& record2.equals(" 0"))
            			&& code2.equals("406") && day < 612 && day >= 313)
            	{
            		pw.println(Index);
            	}
            	else if((db.equals("31") && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& record2.equals(" 0"))
            			&& code2.equals("409") && day < 911 && day >= 612)
            	{
            		pw.println(Index);
            	}
            	else if((db.equals("31") && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& record2.equals(" 0"))
            			&& code2.equals("412") && day < 1211 && day >= 911)
            	{
            		pw.println(Index);
            	}
            	else if((db.equals("31") && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& record2.equals(" 0"))
            			&& code2.equals("503") && day < 1300 && day >= 1211)
            	{
            		pw.println(Index);
            	}
            	else{
            		//System.out.println(record1+" "+security+" "+code1+" "+code2+" "+code3);
            	}//2009年バージョン
            	*/

            	}






            brtxt.close();
            fr.close();
            pw.close();


        	// txtファイル名を一行ずつロードする
        }

        br.close();

    }



}


