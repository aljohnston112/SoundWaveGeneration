package function;

public class Dirac extends UnivariateFunction {

	double[] oneLocations;

	public Dirac(double[] oneLocations) {
		this.oneLocations = oneLocations.clone();
	}

	@Override
	public double fun(double in) {
		for(int i = 0; i < oneLocations.length; i++) {
			if(in == oneLocations[i]) {
				return 1;
			}
		}
		return 0;
	}

}
