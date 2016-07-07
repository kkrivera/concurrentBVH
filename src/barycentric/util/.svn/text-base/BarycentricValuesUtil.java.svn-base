package barycentric.util;

import javax.vecmath.Vector3f;

import triangle.Triangle;
import vector.util.Vector3fUtil;
import barycentric.BarycentricValues;

public class BarycentricValuesUtil
{
	/**
	 * Gets the {@link BarycentricValues} given a point and a {@link Triangle}
	 * 
	 * @param pointVertex
	 *        The {@link Vector3f} representing the point on the plane of the
	 *        {@link Triangle}
	 * @param triangle
	 *        The {@link Triangle} being tested
	 * @return {@link BarycentricValues} for the point of intersection on the
	 *         {@link Triangle}'s plane
	 */
	public static BarycentricValues getBarycentricValues(Vector3f pointVertex, Triangle triangle)
	{
		return getBarycentricValues(pointVertex, triangle.getVertex1(), triangle.getVertex2(),
				triangle.getVertex3());
	}

	/**
	 * Gets the {@link BarycentricValues} given a point and a {@link Triangle}
	 * 
	 * @param pointVertex
	 *        The {@link Vector3f} representing the point on the plane of the
	 *        {@link Triangle}
	 * @param vertex1
	 *        The first vertex of the {@link Triangle}
	 * @param vertex2
	 *        The second vertex of the {@link Triangle}
	 * @param vertex3
	 *        The third vertex of the {@link Triangle}
	 * @return {@link BarycentricValues} for the point of intersection on the
	 *         {@link Triangle}'s plane
	 */
	public static BarycentricValues getBarycentricValues(Vector3f pointVertex, Vector3f vertex1,
			Vector3f vertex2, Vector3f vertex3)
	{
		Vector3f pointVec = Vector3fUtil.sub(pointVertex, vertex1);
		Vector3f vec1 = Vector3fUtil.sub(vertex2, vertex1);
		Vector3f vec2 = Vector3fUtil.sub(vertex3, vertex1);
		Vector3f v1Xv = Vector3fUtil.cross(vec1, pointVec);
		Vector3f v1Xv2 = Vector3fUtil.cross(vec1, vec2);
		Vector3f vXv2 = Vector3fUtil.cross(pointVec, vec2);

		return new BarycentricValues(
				Vector3fUtil.dot(v1Xv2, vXv2) / Vector3fUtil.dot(v1Xv2, v1Xv2), Vector3fUtil.dot(
						v1Xv2, v1Xv) / Vector3fUtil.dot(v1Xv2, v1Xv2));
	}

	/**
	 * Evaluates {@link BarycentricValues} to determine if the point is within a
	 * {@link Triangle}
	 * 
	 * @param baryValues
	 *        The {@link BarycentricValues}
	 * @return A {@link Boolean} determining if the {@link BarycentricValues}
	 *         are within a {@link Triangle}
	 */
	public static boolean evaluateBarcentricCoordinates(BarycentricValues baryValues)
	{
		return (baryValues.getGamma() >= 0 && baryValues.getGamma() <= 1
				&& baryValues.getAlpha() >= 0 && baryValues.getAlpha() <= 1
				&& baryValues.getBeta() >= 0 && baryValues.getBeta() <= 1);
	}

	/**
	 * Interpolates between 3 vectors given the {@link BarycentricValues}
	 * 
	 * @param v1
	 *        The first {@link Vector3f}
	 * @param v2
	 *        The second {@link Vector3f}
	 * @param v3
	 *        The third {@link Vector3f}
	 * @param baryVals
	 *        The {@link BarycentricValues}
	 * @return An interpolated {@link Vector3f}
	 */
	public static Vector3f getBarycentricInterpolation(Vector3f v1, Vector3f v2, Vector3f v3,
			BarycentricValues baryVals)
	{
		return new Vector3f(Vector3fUtil.add(Vector3fUtil.mult(v1, baryVals.getGamma()),
				Vector3fUtil.mult(v2, baryVals.getAlpha()),
				Vector3fUtil.mult(v3, baryVals.getBeta())));
	}
}
