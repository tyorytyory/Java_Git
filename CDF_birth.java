import java.math.BigDecimal;
import org.apache.commons.math3.special.*;

class CDF_birth{
	double pareto3(double randam, double alpha, double beta){//pareto分布３の累積分布関数から実際の値をだすやつ．
		double x1 = randam/(1-randam);//チェック済み
		double x2 = Math.pow(x1,beta);
		double x3 = x2*alpha;
		BigDecimal x4 = new BigDecimal(x3);
		double x = x4.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
		return x;
	}
	double expo(double random, double lambda){//指数分布
		double x5 =Math.log(1-random);//チェック済み
		double x6 = -1 * lambda;
		double x7 = x5/x6;
		BigDecimal x8 = new BigDecimal(x7);
		double x = x8.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
		return x;
	}
	double negabio(double random, double n, double p){//負の二項分布
		double x = 0;//チェック済み
		double k = 0;
		for(k = 0;k<=100;k++){
			x = Beta.regularizedBeta(p, n, 1+k);
			//System.out.println(x);
			if(random < x){
				k--;
				break;
			}
		}		
		return k+1;//定義域に0を含むので
	}
	double zeta(double random, double rho){//ゼータ分布
		double x = 0;//チェック済み
		double k = 1;
		double bunsi = 0;
		double bunbo = 0;
		for(k=1;k<=100;k++){
			bunsi = 0;
			for(int i=1;i<=k;i++){
				bunsi += 1/(Math.pow(i, (1+rho)));
			}
			bunbo = 0;
			for(int n=1;n<=100000;n++){
				bunbo += 1/(Math.pow(n, (1+rho)));
			}
			x = bunsi/bunbo;
			//System.out.println(x);
			if(random < x){
				k--;
				break;
			}
		}
		return k;
	}
	double poisson(double random, double mu){//ポアソン分布
		double k = 0;//チェック済み
		double x = 0;
		for(k=0;k<=100;k++){
			x = Gamma.regularizedGammaQ(1+k,mu);
			//System.out.println(x);
			if(random < x){
				k--;
				break;
			}
		}
		
		return k+1;//定義域に0を含むので
	}
	double Test(double random){
		System.out.println(random);
		double x = random *2;
		return x;
	}
}