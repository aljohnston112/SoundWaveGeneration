package function;

import directedGraph.ChanseyCycle;

public class DiscreteProbabilityDensity extends UnivariateFunction {
	
	double[] p;
	
	public DiscreteProbabilityDensity(ChanseyCycle cc) {
		p = cc.getProbabilities().clone();
	}

	@Override
	public double fun(double in) {
		return p[(int) Math.round(in)];
	}

}
