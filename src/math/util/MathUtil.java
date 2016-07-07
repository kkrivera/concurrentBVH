package math.util;

import javax.vecmath.Vector3f;

public class MathUtil
{

	/**
	 * Converts a decimal integer to a hexadecimal {@link String} and filled
	 * with a number of 0's
	 * 
	 * @param d
	 *        The decimal number
	 * @param fill
	 *        The length to fill the string with 0's
	 * @return A hexadecimal {@link String}
	 */
	public static String d2h(int d, int fill)
	{
		String hex = Integer.toHexString((int) Math.floor(d));

		while (hex.length() < fill)
			hex = "0" + hex;

		return hex;
	}

	/**
	 * Converts a hexadecimal string to a decimal
	 * 
	 * @param h
	 *        The hexadecimal {@link String}
	 * @return An {@link Integer}
	 */
	public static Integer h2d(String h)
	{
		return Integer.parseInt(h, 16);
	}

	/**
	 * Converts spherical coordinates to cartesian
	 * 
	 * @param r
	 *        The radius
	 * @param theta
	 *        The theta angle
	 * @param phi
	 *        The phi angle
	 * @param xOff
	 *        The offset for x
	 * @param yOff
	 *        The offset for y
	 * @param zOff
	 *        The offset for z
	 * @return A {@link Vector3f} of cartestian coordinates
	 */
	public static Vector3f sphericalToCartesian(float r, float theta, float phi, float xOff,
			float yOff, float zOff)
	{
		return new Vector3f((float) (r * Math.sin(theta) * Math.cos(phi) + xOff), (float) (r
				* Math.sin(theta) * Math.sin(phi) + yOff), (float) (r * Math.cos(theta) + zOff));
	}

	/**
	 * Gets a {@link Vector3f} of colors RGB
	 * 
	 * @param color
	 *        The hex color {@link String}
	 * @return A {@link Vector3f} of colors RGB
	 */
	public static Vector3f getColorVec(String color)
	{
		return new Vector3f((float) Math.floor(MathUtil.h2d(color.substring(0, 2))),
				(float) Math.floor(MathUtil.h2d(color.substring(2, 4))),
				(float) Math.floor(MathUtil.h2d(color.substring(4))));
	}

	/**
	 * Gets a {@link Vector3f} of colors RGB with a modification
	 * 
	 * @param color
	 *        The hex color {@link String}
	 * @param mod
	 *        The modification of the color {@link Vector3f}
	 * @return A {@link Vector3f} of colors RGB
	 */
	public static Vector3f getColorVecWithMod(String color, float mod)
	{
		return new Vector3f((float) Math.floor(MathUtil.h2d(color.substring(0, 2)) * mod),
				(float) Math.floor(MathUtil.h2d(color.substring(2, 4)) * mod),
				(float) Math.floor(MathUtil.h2d(color.substring(4)) * mod));
	}
}
