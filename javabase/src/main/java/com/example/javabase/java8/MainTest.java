package com.example.javabase.java8;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;
import org.apache.commons.lang3.ObjectUtils;

/**
 * @program: myjava
 * @description:
 * @author: soulx
 * @create: 2024-04-10 16:04
 **/
public class MainTest {

	public static void main(String[] args) {

		ThrowingFunction<String, Integer> function = s -> {
			if (s == null) {
				throw new IOException("Input cannot be null");
			}
			return Integer.parseInt(s);
		};

	}
}
