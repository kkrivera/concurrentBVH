package intersectionbundle.util;

import intersectionbundle.IntersectionBundle;

import java.util.Collection;

import javax.vecmath.Vector3f;

import ray.Ray;
import ray.util.RayUtil;
import triangle.Triangle;
import vector.util.Vector3fUtil;
import barycentric.BarycentricValues;
import barycentric.util.BarycentricValuesUtil;

public class IntersectionBundleUtil
{
	/**
	 * Determines whether a ray has intersected a {@link Triangle}
	 * 
	 * @param ray
	 *        The {@link Ray} intersecting the {@link Triangle}
	 * @param triangle
	 *        The {@link Triangle} being intersected
	 * @return An {@link IntersectionBundle} or null if no intersection
	 */
	public static IntersectionBundle rayIntersects(Ray ray, Triangle triangle)
	{
		float nDotD = Vector3fUtil.dot(triangle.getNormal(), ray.getDirection());
		if (nDotD != 0.0f)
		{
			float t = Vector3fUtil.dot(Vector3fUtil.sub(triangle.getVertex1(), ray.getOrigin()),
					triangle.getNormal()) / nDotD;
			Vector3f p = RayUtil.getPoint(ray, t);

			BarycentricValues baryValues = BarycentricValuesUtil.getBarycentricValues(p, triangle);

			if (BarycentricValuesUtil.evaluateBarcentricCoordinates(baryValues))
			{
				return new IntersectionBundle(triangle, p, baryValues, t);
			}
		}

		return null;
	}

	/**
	 * Gets the closest intersection
	 * 
	 * @param ray
	 *        The {@link Ray} in question
	 * @param triangles
	 *        The {@link Collection} of {@link Triangle}s
	 * @return An {@link IntersectionBundle} if there is an intersection
	 */
	public static IntersectionBundle getClosestTriangleIntersection(Ray ray,
			Collection<Triangle> triangles)
	{
		IntersectionBundle intersectionBundle = null;
		Float closestT = null;

		for (Triangle triangle : triangles)
		{
			IntersectionBundle tempIntersectionBundle = IntersectionBundleUtil.rayIntersects(ray,
					triangle);

			if (tempIntersectionBundle != null)
			{
				if (tempIntersectionBundle.getT() > 0.0f
						&& (closestT == null || tempIntersectionBundle.getT() < closestT))
				{
					closestT = tempIntersectionBundle.getT();
					intersectionBundle = tempIntersectionBundle;
				}
			}
		}

		return intersectionBundle;
	}
}
