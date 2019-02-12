package com.alphazer.util;

import java.util.ArrayList;

public class Set<E> extends ArrayList<E> {
	private static final long serialVersionUID = 1L;

	public Set<Set<E>> getSubsets() {
		Set<E> set = new Set<E>();
		set.addAll(this);
		
		return getSubsets(set);		
	}
	
	private Set<Set<E>> getSubsets(Set<E> set) {		
		Set<Set<E>> sub = new Set<Set<E>>();
		
		Set<E> f = new Set<E>();
		f.add(set.popFirst());
		sub.add(f);
				
		if (set.isEmpty())
			return sub;
		
		Set<Set<E>> subb = getSubsets(set);
		sub.addAll(subb);
		
		for (Set<E> z : subb) {
			Set<E> z2 = new Set<E>();
			z2.addAll(z);
			z2.addFirst(f.getFirst());
			sub.add(z2);
		}
		
		return sub;
	}
	
	public E popFirst() {
		E first = this.get(0);
		this.remove(0);
		
		return first;
	}
	
	public void addFirst(E e) {
		add(0, e);
	}
	
	public E getFirst() {
		return get(0);
	}
	
	/* TODO
	public void addSorted(E e) {
		 for (int i = 0; i < size(); i++)
			if (e <= get(i))
				this.add(i, e);
		
	} */
}
