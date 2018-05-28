package br.com.processboss.core.util;

public class FormatUtil {

	/**
	 * 
	 * Metodo para fazer a conversao de bytes
	 * 
	 * @param bytes
	 * @return bytes convertidos 
	 */
	public static String formatBytes(long l) {

		if (l == 1L)
			return String.format("%d byte", new Object[] { Long.valueOf(l) });
		
		if (l < 1024L)
			return String.format("%d bytes", new Object[] { Long.valueOf(l) });
		
		if (l < 0x100000L && l % 1024L == 0L)
			return String.format("%.0f KB", new Object[] { Double.valueOf((double) l / 1024D) });
		
		if (l < 0x100000L)
			return String.format("%.1f KB", new Object[] { Double.valueOf((double) l / 1024D) });
		
		if (l % 0x100000L == 0L && l < 0x40000000L)
			return String.format("%.0f MB", new Object[] { Double.valueOf((double) l / 1048576D) });
		
		if (l < 0x40000000L)
			return String.format("%.1f MB", new Object[] { Double.valueOf((double) l / 1048576D) });
		
		if (l % 0x40000000L == 0L && l < 0x10000000000L)
			return String.format("%.0f GB", new Object[] { Double.valueOf((double) l / 1073741824D) });
		
		if (l < 0x10000000000L)
			return String.format("%.1f GB", new Object[] { Double.valueOf((double) l / 1073741824D) });
		
		if (l % 0x10000000000L == 0L && l < 0x4000000000000L)
			return String.format("%.0f TB", new Object[] { Double.valueOf((double) l / 1099511627776D) });
		
		if (l < 0x4000000000000L)
			return String.format("%.1f TB", new Object[] { Double.valueOf((double) l / 1099511627776D) });
		
		else
			return String.format("%d bytes", new Object[] { Long.valueOf(l) });
		
	}
	
}
