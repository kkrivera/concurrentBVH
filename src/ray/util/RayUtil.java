package ray.util;

import intersectionbundle.IntersectionBundle;

import javax.vecmath.Vector3f;

import ray.Ray;
import vector.util.Vector3fUtil;

public class RayUtil
{
	/**
	 * Gets a point from a {@link Ray}
	 * 
	 * @param ray
	 *        The {@link Ray} from which we are calculating the point
	 * @param t
	 *        The distance along the {@link Ray}
	 * @return
	 */
	public static Vector3f getPoint(Ray ray, float t)
	{
		return Vector3fUtil.add(ray.getOrigin(), Vector3fUtil.mult(ray.getDirection(), t));
	}

	/**
	 * Gets a shadow {@link Ray} given a light point
	 * 
	 * @param ray
	 *        The {@link Ray} from which the shadow {@link Ray} is being
	 *        generated
	 * @param intersectionBundle
	 *        The {@link IntersectionBundle} of the point of intersection
	 * @param lightPt
	 *        The {@link Vector3f} light point
	 * @return
	 */
	public static Ray getShadowRay(Ray ray, IntersectionBundle intersectionBundle, Vector3f lightPt)
	{
		return new Ray(Vector3fUtil.add(intersectionBundle.getPointInsideTriangle(),
				Vector3fUtil.mult(intersectionBundle.getTriangle().getNormal(), 0.3f)),
				Vector3fUtil.sub(lightPt, intersectionBundle.getPointInsideTriangle()));
	}

	/**
	 * Generates the direction from the reflective {@link Ray}
	 * 
	 * @param inboundDirection
	 *        The {@link Vector3f} representing the inbound direction
	 * @param normal
	 *        The {@link Vector3f} representing the normal
	 * @return The direction of of a reflection {@link Ray}
	 */
	public static Vector3f getReflectionDirection(Vector3f inboundDirection, Vector3f normal)
	{
		return Vector3fUtil.norm(Vector3fUtil.sub(inboundDirection,
				Vector3fUtil.mult(normal, 2.0f * Vector3fUtil.dot(normal, inboundDirection))));
	}

	/**
	 * Generates a reflection {@link Ray}
	 * 
	 * @param ray
	 *        The ray from which the reflective {@link Ray} will be generated
	 * @param intersectionBundle
	 *        The {@link IntersectionBundle} describing the point of
	 *        intersection
	 * @return A reflective {@link Ray}
	 */
	public static Ray getReflectiveRay(Ray ray, IntersectionBundle intersectionBundle)
	{
		Vector3f reflectionDirection = getReflectionDirection(ray.getDirection(),
				intersectionBundle.getTriangle().getNormal());
		return new Ray(Vector3fUtil.add(intersectionBundle.getPointInsideTriangle(),
				Vector3fUtil.mult(intersectionBundle.getTriangle().getNormal(), 0.1f)),
				reflectionDirection);
	}

	/**
	 * A method to get a delta {@link Ray} for texturig
	 * 
	 * @param ray
	 *        The {@link Ray} being based off of
	 * @param delta
	 *        The delta {@link Vector3f}
	 * @return A delta {@link Ray}
	 */
	public static Ray getDeltaRay(Ray ray, Vector3f delta)
	{
		return new Ray(ray.getOrigin(), Vector3fUtil.add(ray.getDirection(), delta));
	}
}
