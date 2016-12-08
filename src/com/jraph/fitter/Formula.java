package com.jraph.fitter;

public abstract class Formula {

	public static Formula gaus = new Formula() {
		@Override
		public double eval(double x, double[] p, int o) {
			return p[0+o] * Math.exp(-0.5 * Math.pow((x - p[1+o]) / p[2+o], 2));
		}
	};

	public static Formula pol1 = new Formula() {
		@Override
		public double eval(double x, double[] p, int o) {
			return p[0+o] + p[1+o] * x;
		}
	};
	
	public static Formula pol2 = new Formula() {
		@Override
		public double eval(double x, double[] p, int o) {
			return p[0+o] + p[1+o] * (x - p[3+o]) + p[2+o] * Math.pow(x - p[3+o], 2);
		}
	};

	public static Formula sin = new Formula() {
		@Override
		public double eval(double x, double[] p, int o) {
			return p[0+o] * Math.sin(x * p[1+o] - p[2+o]);
		}
	};

	public static Formula cos = new Formula() {
		@Override
		public double eval(double x, double[] p, int o) {
			return p[0+o] * Math.cos(x * p[1+o] - p[2+o]);
		}
	};

	public double eval(double x, double[] p) {
		return eval(x, p, 0);
	}

	public abstract double eval(double x, double[] p, int paroffset);
}
