package com.alphazer;

import com.alphazer.util.Set;

public class Result {
	private Set<Integer> z;
	private double alpha;
	
	
	public Result(Set<Integer> z, double alpha) {
		this.z = z;
		this.alpha = alpha;
	}

	public Set<Integer> getZ() {
		return z;
	}

	public double getAlpha() {
		return alpha;
	}
	
	public String toString() {
		return "Alpha = " + alpha + "\nItems = " + z.toString();
	}
}
