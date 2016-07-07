package printstream.util;

import java.io.Console;
import java.io.PrintStream;

public class PrintStreamUtil
{
	/**
	 * Prints a line to both the {@link PrintStream} and {@link Console}
	 * 
	 * @param fileOut
	 *        The {@link PrintStream}
	 * @param label
	 *        The {@link String} label to print
	 */
	public static void print(PrintStream fileOut, String label)
	{
		fileOut.print(label);
		System.out.print(label);
	}

	/**
	 * Prints a line with end line to a {@link PrintStream} and {@link Console}
	 * 
	 * @param fileOut
	 *        The {@link PrintStream}
	 * @param label
	 *        The {@link String} label to print
	 */
	public static void println(PrintStream fileOut, String label)
	{
		fileOut.println(label);
		System.out.println(label);
	}

	/**
	 * Prints a line with end line to a {@link PrintStream} and {@link Console}
	 * with a time difference
	 * 
	 * @param fileOut
	 * @param fileOut
	 *        The {@link PrintStream}
	 * @param label
	 *        The {@link String} label to print
	 * @param start
	 *        The start time {@link Long}
	 * @param end
	 *        The end time {@link Long}
	 * @return The difference between the end and start times
	 */
	public static long println(PrintStream fileOut, String label, long start, long end)
	{
		long diff = end - start;
		String outStr = label + ": " + diff + "ms";
		println(fileOut, outStr);

		return diff;
	}
}
