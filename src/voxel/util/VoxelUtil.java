package voxel.util;

import static triangle.util.TriangleUtil.getVertex;

import java.util.Set;

import javax.vecmath.Vector3f;

import ray.Ray;
import triangle.Triangle;
import tvalue.TValue;
import voxel.Voxel;

public class VoxelUtil
{
	/**
	 * A method for getting a {@link Voxel} by looping through a {@link Set} of
	 * {@link Triangle}s and getting the smallest and largest bounds
	 * 
	 * @param triangles
	 *        The {@link Set} of {@link Triangle}s over which are being looped
	 * @return A {@link Voxel} representing the largest bounds which the
	 *         {@link Triangle}s consume
	 * @throws Exception
	 *         When an there is an issue getting a vertex from {@link Triangle}
	 */
	public static Voxel getVoxel(Set<Triangle> triangles) throws Exception
	{
		Float minX = null;
		Float maxX = null;
		Float minY = null;
		Float maxY = null;
		Float minZ = null;
		Float maxZ = null;

		for (Triangle triangle : triangles)
		{
			for (int i = 1; i <= 3; i++)
			{
				if (minX == null || getVertex(triangle, i).getX() < minX)
					minX = getVertex(triangle, i).getX();
				if (maxX == null || getVertex(triangle, i).getX() > maxX)
					maxX = getVertex(triangle, i).getX();

				if (minY == null || getVertex(triangle, i).getY() < minY)
					minY = getVertex(triangle, i).getY();
				if (maxY == null || getVertex(triangle, i).getY() > maxY)
					maxY = getVertex(triangle, i).getY();

				if (minZ == null || getVertex(triangle, i).getZ() < minZ)
					minZ = getVertex(triangle, i).getZ();
				if (maxZ == null || getVertex(triangle, i).getZ() > maxZ)
					maxZ = getVertex(triangle, i).getZ();
			}
		}
		return new Voxel(minX, maxX, minY, maxY, minZ, maxZ);
	}

	/**
	 * Determines if a {@link Ray} intersects a {@link Voxel}
	 * 
	 * @param ray
	 *        The {@link Ray}
	 * @param voxel
	 *        The {@link Voxel}
	 * @return A {@link Boolean} of whether the {@link Ray} intersects the
	 *         {@link Voxel}
	 */
	public static boolean rayIntersectsVoxel(Ray ray, Voxel voxel)
	{
		TValue tXVals = getTxValues(voxel, ray);
		if (!validateTValues(tXVals))
		{
			return false;
		}

		float tMin = tXVals.getMin();
		float tMax = tXVals.getMax();

		TValue tYVals = getTyValues(voxel, ray);
		if (!validateTValues(tYVals))
		{
			return false;
		}

		float tYMin = tYVals.getMin();
		float tYMax = tYVals.getMax();

		if (tMin > tYMax || tYMin > tMax)
		{
			return false;
		}
		if (tYMin > tMin)
		{
			tMin = tYMin;
		}
		if (tYMax < tMax)
			tMax = tYMax;

		TValue tZVals = getTzValues(voxel, ray);
		if (!validateTValues(tZVals))
		{
			return false;
		}
		float tZMin = tZVals.getMin();
		float tZMax = tZVals.getMax();

		if (tMin > tZMax || tZMin > tMax)
		{
			return false;
		}
		if (tZMin > tMin)
		{
			tMin = tZMin;
		}
		if (tZMax < tMax)
			tMax = tZMax;

		return true;
	}

	/**
	 * Validates to make sure that the ray is intersecting the object in front
	 * of it
	 * 
	 * @param tVals
	 *        The {@link TValue} to verify
	 * 
	 * @return Whether one of the values is positive, or in front of the camera
	 */
	private static Boolean validateTValues(TValue tVals)
	{
		return tVals.getMin() > 0 || tVals.getMax() > 0;
	}

	/**
	 * Gets the {@link TValue} for x
	 * 
	 * @param voxel
	 *        The {@link Voxel} to get the {@link TValue} from
	 * @param ray
	 *        The {@link Ray}
	 * @return {@link TValue}
	 */
	private static TValue getTxValues(Voxel voxel, Ray ray)
	{
		return getTValues(voxel.getMinX(), voxel.getMaxX(), ray.getOrigin().getX(), ray
				.getDirection().getX());
	}

	/**
	 * Gets the {@link TValue} for y
	 * 
	 * @param voxel
	 *        The {@link Voxel} to get the {@link TValue} from
	 * @param ray
	 *        The {@link Ray}
	 * @return {@link TValue}
	 */
	private static TValue getTyValues(Voxel voxel, Ray ray)
	{
		return getTValues(voxel.getMinY(), voxel.getMaxY(), ray.getOrigin().getY(), ray
				.getDirection().getY());
	}

	/**
	 * Gets the {@link TValue} for z
	 * 
	 * @param voxel
	 *        The {@link Voxel} to get the {@link TValue} from
	 * @param ray
	 *        The {@link Ray}
	 * @return {@link TValue}
	 */
	private static TValue getTzValues(Voxel voxel, Ray ray)
	{
		return getTValues(voxel.getMinZ(), voxel.getMaxZ(), ray.getOrigin().getZ(), ray
				.getDirection().getZ());
	}

	/**
	 * Gets {@link TValue} along a given axis
	 * 
	 * @param min
	 *        The min of the {@link Voxel} along that axis
	 * @param max
	 *        The max of the {@link Voxel} along that axis
	 * @param rayOrigin
	 *        The {@link Ray}'s origin along an axis
	 * @param rayDirection
	 *        The {@link Ray}'s direction along an axis
	 * @return {@link TValue} along an axis
	 */
	private static TValue getTValues(float min, float max, float rayOrigin, float rayDirection)
	{
		TValue tVals = new TValue((min - rayOrigin) / rayDirection, (max - rayOrigin)
				/ rayDirection);
		if (tVals.getMin() > tVals.getMax())
		{
			float tmp = tVals.getMin();
			tVals.setMin(tVals.getMax());
			tVals.setMax(tmp);
		}

		return tVals;
	}

	/**
	 * Gets a the center of the {@link Voxel} as a {@link Vector3f}
	 * 
	 * @param voxel
	 *        The {@link Voxel}
	 * @return A {@link Vector3f} representing the center of the {@link Voxel}
	 */
	public static Vector3f getVoxelCenter(Voxel voxel)
	{
		return new Vector3f((voxel.getMaxX() + voxel.getMinX()) / 2.0f,
				(voxel.getMaxY() + voxel.getMinY()) / 2.0f,
				(voxel.getMaxZ() + voxel.getMinZ()) / 2.0f);
	}
}
