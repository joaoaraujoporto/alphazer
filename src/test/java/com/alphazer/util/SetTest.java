package com.alphazer.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SetTest {
	Set<Integer> set;

	@Before
	public void init() {
		set = new Set<Integer>();
		
		for (int i = 0; i < 4; i++)
			set.add(i);
	}
	
	@Test
	public void testSubsets() {
		Set<Set<Integer>> sub = set.getSubsets();
		
		assertEquals((Math.pow(2, set.size())) - 1, sub.size(), 0);
		System.out.println(sub.toString());
	}
}
