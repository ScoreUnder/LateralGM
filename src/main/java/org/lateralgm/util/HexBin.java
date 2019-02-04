package org.lateralgm.util;

import java.nio.ByteBuffer;

public class HexBin
	{
	private static String HEX_CHARS = "0123456789abcdef";

	public static byte[] decode(String hex)
		{
		final int len = hex.length() - 1;
		final ByteBuffer result = ByteBuffer.allocate(len / 2);
		for (int i = 0; i < len; i += 2)
			{
			result.put(Byte.parseByte(hex.substring(i, i + 2), 16));
			}
		return result.array();
		}

	public static String encode(byte[] vals)
		{
		final StringBuilder result = new StringBuilder(vals.length * 2);
		for (byte b : vals)
			{
			result.append(HEX_CHARS.charAt(b >>> 4));
			result.append(HEX_CHARS.charAt(b & 0xF));
			}
		return result.toString();
		}
	}
