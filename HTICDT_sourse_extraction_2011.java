import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//2010年7月20日以後のフォーマットでの日経平均先物の成行注文(限月調整済み)を抽出するプログラム
//指値注文を抽出するプログラムもある
public class HTICDT_sourse_extraction_2011{

    public static void main(String[] args) throws IOException{


        BufferedReader br = new BufferedReader(new FileReader("filelist.txt"));//読み取りたいファイル名の記入
        String txtFileName;

        while((txtFileName = br.readLine()) != null) {

        	String Index;
        	String db;//DBxy(レコード種別（約定or気配）)
        	String record1;//取引所コード
        	String security;//証券種別
        	String code1;//銘柄コード(a,b)
        	String code2;//銘柄コード(限月)
        	String code3;//銘柄コード(c,d)
        	String record2;//(レコード種別)
        	String record3;//約定・気配コード
        	int day;//日付
        	int count=0;



        	FileReader fr = new FileReader("D:/日経平均先物/2014/" + txtFileName);
            BufferedReader brtxt = new BufferedReader(fr);
            String line ="";

            String[] filename = txtFileName.split("\\.");

         	File file = new File("D:/日経平均先物/2014hashimoto/" + filename[1] +
         			"_" + filename[2] + //.が2つある場合
         			"_market_limit_gcheck.txt");
         	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
         			//"E:\\201510\\" +
         	file)));

            while ((line = brtxt.readLine()) != null) {
            	count++;

            	Index = line;

            	db = Index.substring(0,2);
            	record1 = Index.substring(13,15);
            	security = Index.substring(15,17);
            	code1 = Index.substring(21,23);
            	code2 = Index.substring(23,26);
            	code3 = Index.substring(26,30);
            	record2 = Index.substring(34,36);
            	record3 = Index.substring(49,52);

            	String d1 = Index.substring(8,12);
            	day = Integer.parseInt(d1);
            	//System.out.println(record3);



            	/*if(((db.equals("31") || db.equals("32")) && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& (record2.equals(" 0") ||(record2.equals("33") && (record3.equals("  0") || record3.equals("128")))))
            			&& code2.equals("503") && day < 300 && day >= 0){
            		pw.println(Index);
            	}
            	else if(((db.equals("31") || db.equals("32")) && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& (record2.equals(" 0") ||(record2.equals("33") && (record3.equals("  0") || record3.equals("128")))))
            			&& code2.equals("506") && day < 600 && day >= 300){
            		pw.println(Index);
            	}
            	else if(((db.equals("31") || db.equals("32")) && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& (record2.equals(" 0") ||(record2.equals("33") && (record3.equals("  0") || record3.equals("128")))))
            			&& code2.equals("509") && day < 900 && day >= 600){
            		pw.println(Index);
            	}
            	else if(((db.equals("31") || db.equals("32")) && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& (record2.equals(" 0") ||(record2.equals("33") && (record3.equals("  0") || record3.equals("128")))))
            			&& code2.equals("512") && day < 1200 && day >= 900){
            		pw.println(Index);
            	}
            	else if(((db.equals("31") || db.equals("32")) && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& (record2.equals(" 0") ||(record2.equals("33") && (record3.equals("  0") || record3.equals("128")))))
            			&& code2.equals("603") && day < 1240 && day >= 1200){
            		pw.println(Index);
            	}
            	else{
            		//System.out.println(record1+" "+security+" "+code1+" "+code2+" "+code3);
            	}//2010年バージョン(2010/7/20から)*/

            	/*if(((db.equals("31") || db.equals("32")) && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& (record2.equals(" 0") ||(record2.equals("33") && (record3.equals("  0") || record3.equals("128")))))
            			&& code2.equals("603") && day < 300 && day >= 0){
            		pw.println(Index);
            	}
            	else if(((db.equals("31") || db.equals("32")) && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& (record2.equals(" 0") ||(record2.equals("33") && (record3.equals("  0") || record3.equals("128")))))
            			&& code2.equals("606") && day < 600 && day >= 300){
            		pw.println(Index);
            	}
            	else if(((db.equals("31") || db.equals("32")) && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& (record2.equals(" 0") ||(record2.equals("33") && (record3.equals("  0") || record3.equals("128")))))
            			&& code2.equals("609") && day < 900 && day >= 600){
            		pw.println(Index);
            	}
            	else if(((db.equals("31") || db.equals("32")) && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& (record2.equals(" 0") ||(record2.equals("33") && (record3.equals("  0") || record3.equals("128")))))
            			&& code2.equals("612") && day < 1200 && day >= 900){
            		pw.println(Index);
            	}
            	else if(((db.equals("31") || db.equals("32")) && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& (record2.equals(" 0") ||(record2.equals("33") && (record3.equals("  0") || record3.equals("128")))))
            			&& code2.equals("703") && day < 1240 && day >= 1200){
            		pw.println(Index);
            	}
            	else{
            		//System.out.println(record1+" "+security+" "+code1+" "+code2+" "+code3);
            	}//2011年バージョン*/


            	/*if(((db.equals("31") || db.equals("32")) && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& (record2.equals(" 0") ||(record2.equals("33") && (record3.equals("  0") || record3.equals("128")))))
            			&& code2.equals("703") && day < 300 && day >= 0){
            		pw.println(Index);
            	}
            	else if(((db.equals("31") || db.equals("32")) && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& (record2.equals(" 0") ||(record2.equals("33") && (record3.equals("  0") || record3.equals("128")))))
            			&& code2.equals("706") && day < 600 && day >= 300){
            		pw.println(Index);
            	}
            	else if(((db.equals("31") || db.equals("32")) && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& (record2.equals(" 0") ||(record2.equals("33") && (record3.equals("  0") || record3.equals("128")))))
            			&& code2.equals("709") && day < 900 && day >= 600){
            		pw.println(Index);
            	}
            	else if(((db.equals("31") || db.equals("32")) && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& (record2.equals(" 0") ||(record2.equals("33") && (record3.equals("  0") || record3.equals("128")))))
            			&& code2.equals("712") && day < 1200 && day >= 900){
            		pw.println(Index);
            	}
            	else if(((db.equals("31") || db.equals("32")) && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& (record2.equals(" 0") ||(record2.equals("33") && (record3.equals("  0") || record3.equals("128")))))
            			&& code2.equals("803") && day < 1240 && day >= 1200){
            		pw.println(Index);
            	}
            	else{
            		//System.out.println(record1+" "+security+" "+code1+" "+code2+" "+code3);
            	}//2012年バージョン*/

            	/*if(((db.equals("31") || db.equals("32")) && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& (record2.equals(" 0") ||(record2.equals("33") && (record3.equals("  0") || record3.equals("128")))))
            			&& code2.equals("803") && day < 300 && day >= 0){
            		pw.println(Index);
            	}
            	else if(((db.equals("31") || db.equals("32")) && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& (record2.equals(" 0") ||(record2.equals("33") && (record3.equals("  0") || record3.equals("128")))))
            			&& code2.equals("806") && day < 600 && day >= 300){
            		pw.println(Index);
            	}
            	else if(((db.equals("31") || db.equals("32")) && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& (record2.equals(" 0") ||(record2.equals("33") && (record3.equals("  0") || record3.equals("128")))))
            			&& code2.equals("809") && day < 900 && day >= 600){
            		pw.println(Index);
            	}
            	else if(((db.equals("31") || db.equals("32")) && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& (record2.equals(" 0") ||(record2.equals("33") && (record3.equals("  0") || record3.equals("128")))))
            			&& code2.equals("812") && day < 1200 && day >= 900){
            		pw.println(Index);
            	}
            	else if(((db.equals("31") || db.equals("32")) && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& (record2.equals(" 0") ||(record2.equals("33") && (record3.equals("  0") || record3.equals("128")))))
            			&& code2.equals("903") && day < 1240 && day >= 1200){
            		pw.println(Index);
            	}
            	else{
            		//System.out.println(record1+" "+security+" "+code1+" "+code2+" "+code3);
            	}//2013年バージョン*/

            	if(((db.equals("31") || db.equals("32")) && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& (record2.equals(" 0") ||(record2.equals("33") && (record3.equals("  0") || record3.equals("128")))))
            			&& code2.equals("903") && day < 300 && day >= 0){
            		pw.println(Index);
            	}
            	else if(((db.equals("31") || db.equals("32")) && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& (record2.equals(" 0") ||(record2.equals("33") && (record3.equals("  0") || record3.equals("128")))))
            			&& code2.equals("906") && day < 600 && day >= 300){
            		pw.println(Index);
            	}
            	else if(((db.equals("31") || db.equals("32")) && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& (record2.equals(" 0") ||(record2.equals("33") && (record3.equals("  0") || record3.equals("128")))))
            			&& code2.equals("909") && day < 900 && day >= 600){
            		pw.println(Index);
            	}
            	else if(((db.equals("31") || db.equals("32")) && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& (record2.equals(" 0") ||(record2.equals("33") && (record3.equals("  0") || record3.equals("128")))))
            			&& code2.equals("912") && day < 1200 && day >= 900){
            		pw.println(Index);
            	}
            	else if(((db.equals("31") || db.equals("32")) && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018")&& (record2.equals(" 0") ||(record2.equals("33") && (record3.equals("  0") || record3.equals("128")))))
            			&& code2.equals("003") && day < 1240 && day >= 1200){
            		pw.println(Index);
            	}
            	else{
            		//System.out.println(record1+" "+security+" "+code1+" "+code2+" "+code3);
            	}//2014年バージョン






            	/*if((db.equals("31") && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018") && record2.equals(" 0"))
            			&& code2.equals("003") && day < 300 && day >= 0){
            		pw.println(Index);
            	}
            	else if((db.equals("31") && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018") && record2.equals(" 0"))
            			&& code2.equals("006") && day < 600 && day >= 300){
            		pw.println(Index);
            	}
            	else if((db.equals("31") && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018") && record2.equals(" 0"))
            			&& code2.equals("009") && day < 900 && day >= 600){
            		pw.println(Index);
            	}
            	else if((db.equals("31") && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018") && record2.equals(" 0"))
            			&& code2.equals("012") && day < 1200 && day >= 900){
            		pw.println(Index);
            		//System.out.println(db + " " + record1+" "+security+" "+code1+" "+code2+" "+code3);
            	}
            	else if((db.equals("31") && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018") && record2.equals(" 0"))
            			&& code2.equals("103") && day < 1240 && day >= 1200){
            		pw.println(Index);

            	}
            	else{
            		//System.out.println(db + " " + record1+" "+security+" "+code1+" "+code2+" "+code3);
            	}//2015年バージョン*/

            	//ここまで成行注文を抽出するプログラム

            	/*if((db.equals("32") && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018") && record2.equals("33"))
            			&& code2.equals("003") && day < 300 && day >= 0 //&& (record3.equals("  0") || record3.equals("128"))
            			){//最良気配の買・売注文を抽出
            		pw.println(Index);
            	}
            	else if((db.equals("32") && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018") && record2.equals("33"))
            			&& code2.equals("006") && day < 600 && day >= 300 && (record3.equals("  0") || record3.equals("128"))){//最良気配の買・売注文を抽出
            		pw.println(Index);
            	}
            	else if((db.equals("32") && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018") && record2.equals("33"))
            			&& code2.equals("009") && day < 900 && day >= 600 && (record3.equals("  0") || record3.equals("128"))){//最良気配の買・売注文を抽出
            		pw.println(Index);
            	}
            	else if((db.equals("32") && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018") && record2.equals("33"))
            			&& code2.equals("012") && day < 1200 && day >= 900 && (record3.equals("  0") || record3.equals("128"))){//最良気配の買・売注文を抽出
            		pw.println(Index);
            		//System.out.println(db + " " + record1+" "+security+" "+code1+" "+code2+" "+code3);
            	}
            	else if((db.equals("32") && record1.equals("21") && security.equals("20") && code1.equals("16") && code3.equals("0018") && record2.equals("33"))
            			&& code2.equals("103") && day < 1240 && day >= 1200 && (record3.equals("  0") || record3.equals("128"))){//最良気配の買・売注文を抽出
            		pw.println(Index);

            	}
            	else{
            		//System.out.println(db + " " + record1+" "+security+" "+code1+" "+code2+" "+code3);
            	}*///指値注文の抽出2015年バージョン




            	}




            brtxt.close();
            fr.close();
            pw.close();

        	// txtファイル名を一行ずつロードする
        }
        br.close();

    }



}


