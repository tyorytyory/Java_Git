import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//2006_market_order2_donation.csvからRV算出で必要な約定値を抽出するプログラム

public class RV_source_extraction{

	public static void main(String[] args) throws IOException{




		BufferedReader br = new BufferedReader(new FileReader("filelist_rv.txt"));//読み取りたいファイル名の記入
		String txtFileName;

		while((txtFileName = br.readLine()) != null) {

			FileReader fr = new FileReader(txtFileName);
			BufferedReader brtxt = new BufferedReader(fr);
			String line ="";


			double time_intervals = 600;//計算したいRVの時間間隔
			double time_for = 32400 + time_intervals;//for文の初期値
			int day = 0;
			boolean am = false;//前場完了の有無(2011/2/14以前)
			boolean pm = false;//後場完了の有無(2011/2/14以前)
			boolean first = false;//2011/2/14以降
			boolean end_trade = false;//time_totalが15:15:00を過ぎていることを示すプログラム
			String line_before = null;//一つ前のline
			String JNIc_split[] = null;
			double time_total = 0;
			String[] filename = txtFileName.split("\\_");

			File file_rv = new File(filename[0].substring(0,4) + "_RV_10min_source2.csv");
			PrintWriter pw_rv = new PrintWriter(new BufferedWriter(new FileWriter(file_rv)));





			while ((line = brtxt.readLine()) != null) {


				JNIc_split = line.split(",", 0);




				double hour = Double.parseDouble(JNIc_split[1].substring(0, 2));
				double minute = Double.parseDouble(JNIc_split[1].substring(3, 5));
				double second = Double.parseDouble(JNIc_split[1].substring(6));
				time_total = hour*3600 + minute*60 + second;

				if(day == 0){
					day = Integer.parseInt(JNIc_split[0]);
				}
				else if(day != Integer.parseInt(JNIc_split[0])){
					if(time_for < 54900 && !(day == 20060104 || day == 20061229 || day == 20070104 || day == 20071228 || day == 20080104 || day == 20081230 || day == 20090105)){
						while(time_for <= 54600){

							int hour_null = (int)(time_for)/3600;
							int minute_null = ((int)(time_for)%3600)/60;
							String hour_null_output = String.valueOf(hour_null);
							String minute_null_output = String.valueOf(minute_null);
							if(hour_null_output.length() == 1){
								hour_null_output = 0 + hour_null_output;
							}
							if(minute_null_output.length() == 1){
								minute_null_output = 0 + minute_null_output;
							}


							pw_rv.println(day  + "," + hour_null_output + ":" + minute_null_output + ":00.000000,NaN,NaN,NaN");
							if(!(day == 20060104 || day == 20061229 || day == 20070104 || day == 20071228 || day == 20080104 || day == 20081230 || day == 20090105) && Integer.parseInt(JNIc_split[0]) < 20110214 && time_for == 39600){
								pw_rv.println(day  + ",12:30:00.000000,NaN,NaN,NaN");
								time_for += 5400 + time_intervals;//昼休み
							}
							else{
								time_for += time_intervals;
							}

						}
					}


					if(Integer.parseInt(JNIc_split[0]) < 20160719){
						time_for = 32400 + time_intervals;
					}
					else{
						time_for = 31500 + time_intervals;
					}
					day = Integer.parseInt(JNIc_split[0]);
					//line_before = null;
					am = false;
					pm = false;
					first = false;
					end_trade = false;
				}

				if(first == false && time_total < (32400 + time_intervals) && 20110214 <= day && day < 20160719 //&& !(JNIc_split[4].equals("error1")) && !(JNIc_split[4].equals("error2"))
						){//2011/2/14 - 2016/07/15(?)
					System.out.println(line_before);

					if(line_before != null){
						JNIc_split = line_before.split(",", 0);
						pw_rv.println(JNIc_split[0] + "," + JNIc_split[1] + "," + JNIc_split[2] + "," + JNIc_split[3] + "," + JNIc_split[4] + "," + JNIc_split[5]);
					}



					JNIc_split = line.split(",", 0);

					pw_rv.println(JNIc_split[0] + "," + JNIc_split[1] + "," + JNIc_split[2] + "," + JNIc_split[3] + "," + JNIc_split[4] + ",NaN");
					first = true;

				}
				else if(first == false && (32400 + time_intervals) < time_total && 20110214 <= day && day < 20170719 //&& !(JNIc_split[4].equals("error1")) && !(JNIc_split[4].equals("error2"))
						){
					if(line_before != null){
						JNIc_split = line_before.split(",", 0);
					}

					pw_rv.println(JNIc_split[0] + "," + JNIc_split[1] + "," + JNIc_split[2] + "," + JNIc_split[3] + "," + JNIc_split[4] + "," + JNIc_split[5]);

					JNIc_split = line.split(",", 0);

					first = true;

					pw_rv.println(day  + ",09:00:00.000000,NaN,NaN,NaN");
					do{

						int hour_null = (int)(time_for)/3600;
						int minute_null = ((int)(time_for)%3600)/60;
						String hour_null_output = String.valueOf(hour_null);
						String minute_null_output = String.valueOf(minute_null);
						if(hour_null_output.length() == 1){
							hour_null_output = 0 + hour_null_output;
						}
						if(minute_null_output.length() == 1){
							minute_null_output = 0 + minute_null_output;
						}


						pw_rv.println(day  + "," + hour_null_output + ":" + minute_null_output + ":00.000000," + "0,0,0");

						time_for += time_intervals;

					}while(time_for < time_total);

				}

				if(first == false && time_total < (31500 + time_intervals) && 20160719 <= day //&& !(JNIc_split[4].equals("error1")) && !(JNIc_split[4].equals("error2"))
						){//2016/07/19以降
					if(line_before != null){
						JNIc_split = line_before.split(",", 0);
						pw_rv.println(JNIc_split[0] + "," + JNIc_split[1] + "," + JNIc_split[2] + "," + JNIc_split[3] + "," + JNIc_split[4] + "," + JNIc_split[5]);
					}



					JNIc_split = line.split(",", 0);



					pw_rv.println(JNIc_split[0] + "," + JNIc_split[1] + "," + JNIc_split[2] + "," + JNIc_split[3] + "," + JNIc_split[4] + ",NaN");
					first = true;
				}
				else if(first == false && (31500 + time_intervals) < time_total && 20110214 <= day && day < 20170719 //&& !(JNIc_split[4].equals("error1")) && !(JNIc_split[4].equals("error2"))
						){
					if(line_before != null){
						JNIc_split = line_before.split(",", 0);
					}

					pw_rv.println(JNIc_split[0] + "," + JNIc_split[1] + "," + JNIc_split[2] + "," + JNIc_split[3] + "," + JNIc_split[4] + "," + JNIc_split[5]);

					JNIc_split = line.split(",", 0);


					first = true;

					pw_rv.println(day  + ",08:45:00.000000,NaN,NaN,NaN");
					do{

						int hour_null = (int)(time_for)/3600;
						int minute_null = ((int)(time_for)%3600)/60;
						String hour_null_output = String.valueOf(hour_null);
						String minute_null_output = String.valueOf(minute_null);
						if(hour_null_output.length() == 1){
							hour_null_output = 0 + hour_null_output;
						}
						if(minute_null_output.length() == 1){
							minute_null_output = 0 + minute_null_output;
						}


						pw_rv.println(day  + "," + hour_null_output + ":" + minute_null_output + ":00.000000," + "0,0,0");

						time_for += time_intervals;

					}while(time_for < time_total);

				}


				if(am == false && time_total < (32400 + time_intervals) && day < 20110214 //&& !(JNIc_split[4].equals("error1")) && !(JNIc_split[4].equals("error2"))
						){//2011/2/14以前

					if(line_before != null){
						JNIc_split = line_before.split(",", 0);
						pw_rv.println(JNIc_split[0] + "," + JNIc_split[1] + "," + JNIc_split[2] + "," + JNIc_split[3] + "," + JNIc_split[4] + "," + JNIc_split[5]);
					}



					JNIc_split = line.split(",", 0);


					pw_rv.println(JNIc_split[0] + "," + JNIc_split[1] + "," + JNIc_split[2] + "," + JNIc_split[3] + "," + JNIc_split[4] + ",NaN");
					am = true;
				}
				else if(am == false && (32400 + time_intervals) < time_total && day < 20110214 //&& !(JNIc_split[4].equals("error1")) && !(JNIc_split[4].equals("error2"))
						){



					if(line_before != null){
						JNIc_split = line_before.split(",", 0);
						pw_rv.println(JNIc_split[0] + "," + JNIc_split[1] + "," + JNIc_split[2] + "," + JNIc_split[3] + "," + JNIc_split[4] + "," + JNIc_split[5]);

					}


					JNIc_split = line.split(",", 0);


					am = true;
					pw_rv.println(day  + ",09:00:00.000000,NaN,NaN,NaN");
					do{

						int hour_null = (int)(time_for)/3600;
						int minute_null = ((int)(time_for)%3600)/60;
						String hour_null_output = String.valueOf(hour_null);
						String minute_null_output = String.valueOf(minute_null);
						if(hour_null_output.length() == 1){
							hour_null_output = 0 + hour_null_output;
						}
						if(minute_null_output.length() == 1){
							minute_null_output = 0 + minute_null_output;
						}


						if(time_for < time_total && time_total < (time_for + time_intervals)){

							JNIc_split = line.split(",", 0);

							pw_rv.println(JNIc_split[0] + "," + JNIc_split[1] + "," + JNIc_split[2] + "," + JNIc_split[3] + "," + JNIc_split[4] + ",NaN");



						}
						else{

							//System.out.println(line);

							pw_rv.println(day  + "," + hour_null_output + ":" + minute_null_output + ":00.000000,NaN,NaN,NaN");

						}

						if(!(day == 20060104 || day == 20061229 || day == 20070104 || day == 20071228 || day == 20080104 || day == 20081230 || day == 20090105) && Integer.parseInt(JNIc_split[0]) < 20110214 && time_for == 39600){
							time_for += 5400 + time_intervals;//昼休み
						}
						else{
							time_for += time_intervals;
						}

					}while(time_for < time_total);

				}



				/*if(time_intervals <= (time_total - time_for) && time_total < 54900 && am == false && !(JNIc_split[4].equals("donation")) && !(JNIc_split[4].equals("error1")) && !(JNIc_split[4].equals("error2"))){//区間内にデータがないとき
            		do{
                		int hour_null = (int)(time_for)/3600;
                		int minute_null = ((int)(time_for)%3600)/60;
                		String hour_null_output = String.valueOf(hour_null);
                		String minute_null_output = String.valueOf(minute_null);
                		if(hour_null_output.length() == 1){
                			hour_null_output = 0 + hour_null_output;
                		}
                		if(minute_null_output.length() == 1){
                			minute_null_output = 0 + minute_null_output;
                		}


                		pw_rv.println(day  + "," + hour_null_output + ":" + minute_null_output + ":00.000000," + "0,0,0");
                		time_for += time_intervals;


            			System.out.println(line);
                		System.out.println(day  + "," + hour_null_output + ":" + minute_null_output + ":00.000000," + "0,0,0");

            		}while(time_intervals <= (time_total - time_for));
            		//System.out.println(line);
            	}*/





				if(31500 < time_total && time_for <= time_total && end_trade == false //&& !(JNIc_split[4].equals("error1")) && !(JNIc_split[4].equals("error2"))
						&& (((day != 20060104 && day != 20061229 && day != 20070104 && day != 20071228 && day != 20080104 && day != 20081230 && day != 20090105) && time_total < 54960)
								|| ((day == 20060104 || day == 20061229 || day == 20070104 || day == 20071228 || day == 20080104 || day == 20081230 || day == 20090105) && time_total < 40500))){

					if((day == 20060104 || day == 20061229 || day == 20070104 || day == 20071228 || day == 20080104 || day == 20081230 || day == 20090105) && time_total > 40200){

					}





					//System.out.println(line + "***** " + time_total);


					if(line_before != null){
						JNIc_split = line_before.split(",", 0);
					}


					if(((day != 20060104 && day != 20061229 && day != 20070104 && day != 20071228 && day != 20080104 && day != 20081230 && day != 20090105 && day < 20110214) && (time_for == 39600 ||  (45000 == time_for && 39600 < time_total) ||  (time_for == 54600 && 54600 < time_total))) || 
							//((day != 20060104 && day != 20061229 && day != 20070104 && day != 20071228 && day != 20080104 && day != 20081230 && day != 20090105) && time_for == 54600) || 	
							((day == 20060104 || day == 20061229 || day == 20070104 || day == 20071228 || day == 20080104 || day == 20081230 || day == 20090105) && time_for == 40200)){

					}
					else{
						if(JNIc_split.length == 5){
							pw_rv.println(JNIc_split[0] + "," + JNIc_split[1] + "," + JNIc_split[2] + "," + JNIc_split[3] + "," + JNIc_split[4] + ",NaN");

						}
						if(JNIc_split.length == 6 && !(JNIc_split[5].equals("final trade"))){
							pw_rv.println(JNIc_split[0] + "," + JNIc_split[1] + "," + JNIc_split[2] + "," + JNIc_split[3] + "," + JNIc_split[4] + ",NaN");
						}
						else if(JNIc_split.length == 6 && JNIc_split[5].equals("final trade")){

							pw_rv.println(JNIc_split[0] + "," + JNIc_split[1] + "," + JNIc_split[2] + "," + JNIc_split[3] + "," + JNIc_split[4] + "," + JNIc_split[5]);
						}

					}






					if(54600 < time_total){
						end_trade = true;
						time_for += time_intervals;
						//System.out.println(line);
						//何もしない
					}
					else if(time_for + time_intervals < time_total){
						while(time_for < time_total && time_total < 54900){

							if((day < 20110214 && 45000 <= time_for && time_for < (45000 + time_intervals) && (45000) <= time_total)){
								//if(day == 20071127){
								System.out.println(time_for + "%%%" + line + "--" + time_total);
								//}     			
								break;
							}

							if(!(day == 20060104 || day == 20061229 || day == 20070104 || day == 20071228 || day == 20080104 || day == 20081230 || day == 20090105) && Integer.parseInt(JNIc_split[0]) < 20110214 && time_for == 39600){
								time_for += 5400;//昼休み
							}
							else{
								time_for += time_intervals;
							}

							int hour_null = (int)(time_for)/3600;
							int minute_null = ((int)(time_for)%3600)/60;
							String hour_null_output = String.valueOf(hour_null);
							String minute_null_output = String.valueOf(minute_null);
							if(hour_null_output.length() == 1){
								hour_null_output = 0 + hour_null_output;
							}
							if(minute_null_output.length() == 1){
								minute_null_output = 0 + minute_null_output;
							}






							if(time_total < time_for){

							}
							else if(time_for < time_total && ((day < 20110214 && time_for != 39600 && time_for != 45000) || (day < 20110214 && time_for == 39600 && 45000 < time_total)
									|| (20110214 < day))
									){

								pw_rv.println(day  + "," + hour_null_output + ":" + minute_null_output + ":00.000000,NaN,NaN,NaN");

							}


						}





					}
					else if(!(day == 20060104 || day == 20061229 || day == 20070104 || day == 20071228 || day == 20080104 || day == 20081230 || day == 20090105) && Integer.parseInt(JNIc_split[0]) < 20110214 && time_for == 39600){
						//System.out.println(time_for);

						time_for += 5400;//昼休み

					}
					else{
						time_for += time_intervals;

					}



				}






				JNIc_split = line.split(",", 0);

				if(pm == false && time_total < (45000 + time_intervals) && 45000 < time_total && day < 20110214// && !(JNIc_split[4].equals("error1")) && !(JNIc_split[4].equals("error2"))
						){

					//System.out.println(line);

					JNIc_split = line_before.split(",", 0);

					hour = Double.parseDouble(JNIc_split[1].substring(0, 2));
					minute = Double.parseDouble(JNIc_split[1].substring(3, 5));
					second = Double.parseDouble(JNIc_split[1].substring(6));
					time_total = hour*3600 + minute*60 + second;

					if(39600 < time_total || (time_total < 39600 && (39600 - time_total) < time_intervals)){
						pw_rv.println(JNIc_split[0] + "," + JNIc_split[1] + "," + JNIc_split[2] + "," + JNIc_split[3] + "," + JNIc_split[4] + "," + JNIc_split[5]);
					}




					JNIc_split = line.split(",", 0);

					pw_rv.println(JNIc_split[0] + "," + JNIc_split[1] + "," + JNIc_split[2] + "," + JNIc_split[3] + "," + JNIc_split[4] + ",NaN");


					//System.out.println(time_for + "+++" + line);

					pm = true;

				}
				else if(pm == false && (45000 + time_intervals) < time_total && day < 20110214 //&& !(JNIc_split[4].equals("error1")) && !(JNIc_split[4].equals("error2"))
						){

					/*if(line_before != null){
            			JNIc_split = line_before.split(",", 0);
            		}*/


					System.out.println(line);

					JNIc_split = line_before.split(",", 0);

					pw_rv.println(JNIc_split[0] + "," + JNIc_split[1] + "," + JNIc_split[2] + "," + JNIc_split[3] + "," + JNIc_split[4] + "," + JNIc_split[5]);

					JNIc_split = line.split(",", 0);


					pm = true;
					pw_rv.println(day  + ",12:30:00.000000,NaN,NaN,NaN");
					while(time_for < time_total && time_total < 54900){

						System.out.println(line + "dsad");

						if(!(day == 20060104 || day == 20061229 || day == 20070104 || day == 20071228 || day == 20080104 || day == 20081230 || day == 20090105) && Integer.parseInt(JNIc_split[0]) < 20110214 && time_for == 39600){
							time_for += 5400 + time_intervals;//昼休み
						}
						else{
							time_for += time_intervals;
						}


						int hour_null = (int)(time_for)/3600;
						int minute_null = ((int)(time_for)%3600)/60;
						String hour_null_output = String.valueOf(hour_null);
						String minute_null_output = String.valueOf(minute_null);
						if(hour_null_output.length() == 1){
							hour_null_output = 0 + hour_null_output;
						}
						if(minute_null_output.length() == 1){
							minute_null_output = 0 + minute_null_output;
						}


						if(time_for < time_total && time_total < (time_for + time_intervals)){

							JNIc_split = line.split(",", 0);

							pw_rv.println(JNIc_split[0] + "," + JNIc_split[1] + "," + JNIc_split[2] + "," + JNIc_split[3] + "," + JNIc_split[4] + ",NaN");

						}
						else if(time_for < time_total){
							pw_rv.println(day  + "," + hour_null_output + ":" + minute_null_output + ":00.000000,NaN,NaN,NaN");
						}



					}
				}

				if(//!(JNIc_split[4].equals("error1")) && !(JNIc_split[4].equals("error2")) && 
						time_total < 54900){
					line_before = line;
				}





			}

			pw_rv.println(JNIc_split[0] + "," + JNIc_split[1] + "," + JNIc_split[2] + "," + JNIc_split[3] + "," + JNIc_split[4] + "," + JNIc_split[5]);

			if(time_for < 54900){
				while(time_for < time_total && time_total < 54900){


					if(!(day == 20060104 || day == 20061229 || day == 20070104 || day == 20071228 || day == 20080104 || day == 20081230 || day == 20090105) && Integer.parseInt(JNIc_split[0]) < 20110214 && time_for == 39600){
						time_for += 5400 + time_intervals;//昼休み
					}
					else{
						time_for += time_intervals;
					}

					int hour_null = (int)(time_for)/3600;
					int minute_null = ((int)(time_for)%3600)/60;
					String hour_null_output = String.valueOf(hour_null);
					String minute_null_output = String.valueOf(minute_null);
					if(hour_null_output.length() == 1){
						hour_null_output = 0 + hour_null_output;
					}
					if(minute_null_output.length() == 1){
						minute_null_output = 0 + minute_null_output;
					}


					pw_rv.println(day  + "," + hour_null_output + ":" + minute_null_output + ":00.000000,NaN,NaN,NaN");


				}
			}




			brtxt.close();
			fr.close();
			pw_rv.close();



		}


		br.close();
	}
}

