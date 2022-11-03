package com.example.javabase.java.base;

import com.google.common.collect.Sets;
import com.google.common.hash.BloomFilter;
import java.util.Arrays;
import java.util.BitSet;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @program: myjava
 * @description:
 * @author: soulx
 * @create: 2022-08-15 09:43
 **/
public class Main {

	public static void main(String[] args) throws InterruptedException {
		ConcurrentSkipListSet h = new ConcurrentSkipListSet();
		Iterator iterator = h.iterator();
		Thread.sleep(100);
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
		EnumSet set = EnumSet.allOf(Testenum.class);
		Set ss = Sets.newHashSet();
		BitSet bs = new BitSet();
//		BloomFilter f = BloomFilter.create();
//		f.put()

	}

}
