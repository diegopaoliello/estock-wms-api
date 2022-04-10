package io.github.diegopaoliello.estockappapi.util;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class Util {

	public static BigDecimal bigDecimalConverter(String value) {
		if (value == null) {
			return null;
		}
		value = value.replace(".", "").replace(",", ".");
		return new BigDecimal(value);
	}
}
