package function;

public class UnitTriangle {

	public static double Cotrine(double theta) {
		double t = theta;
		if(theta > 2.0*Math.PI) {
			while(t > 2.0*Math.PI) {
				t-=2.0*Math.PI;
			}
		}
		if(t < 2.0*Math.PI/3.0) {
			return 3.0*Math.sqrt(3.0)/(4.0*Math.PI)*t;
		} else if(t < 4.0*Math.PI/3.0) {
			return -3.0*Math.sqrt(3.0)/(2.0*Math.PI)*(t-(2.0*Math.PI/3.0))+Math.sqrt(3.0)/(2.0);
		} else {
			return 3.0*Math.sqrt(3.0)/(4.0*Math.PI)*(t-(4.0*Math.PI/3.0))-Math.sqrt(3.0)/(2.0);
		}
	}
	
	public static double Trine(double theta) {
		double t = theta;
		if(theta > 2.0*Math.PI) {
			while(t > 2.0*Math.PI) {
				t-=2.0*Math.PI;
			}
		}
		if(t < 2.0*Math.PI/3.0) {
			return -Math.sqrt(3.0)*Cotrine(t)+1.0;
		} else if(t < 4.0*Math.PI/3.0) {
			return -1.0/2.0;
		} else {
			return Math.sqrt(3.0)*Cotrine(t)+1.0;
		}
	}

	
}