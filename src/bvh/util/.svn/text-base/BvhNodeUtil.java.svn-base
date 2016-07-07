package bvh.util;

import static triangle.util.TriangleUtil.getVertex;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.vecmath.Vector3f;

import ray.Ray;
import rayMap.RayMap;
import triangle.Triangle;
import voxel.Voxel;
import voxel.util.VoxelUtil;
import bvh.BvhNode;
import enums.Plane;

public class BvhNodeUtil
{
	/**
	 * A method for getting the Surface Area Heuristic of a {@link Voxel}'s
	 * children {@link Voxel}s and the number of {@link Triangle}s those
	 * children {@link Voxel}s contain
	 * 
	 * @param leftVoxel
	 *        The {@link Voxel} representing the left child
	 * @param leftVoxelTriangles
	 *        The {@link Integer} representing the number of {@link Triangle}s
	 *        within the left {@link Voxel}
	 * @param rightVoxel
	 *        The {@link Voxel} representing the right child
	 * @param rightVoxelTriangles
	 *        The {@link Integer} representing the number of {@link Triangle}s
	 *        within the right {@link Voxel}
	 * @param parentVoxel
	 *        The parent {@link Voxel} containing both the left and right child
	 *        {@link Voxel}s
	 * @return A {@link Float} representing the SAH cost
	 */
	private static Float getSahCost(Voxel leftVoxel, Integer leftVoxelTriangles, Voxel rightVoxel,
			Integer rightVoxelTriangles, Voxel parentVoxel)
	{
		return getVoxelFraction(leftVoxel, parentVoxel) * leftVoxelTriangles
				+ getVoxelFraction(rightVoxel, parentVoxel) * rightVoxelTriangles;
	}

	/**
	 * A method for getting the fraction of the surface area of a {@link Voxel}
	 * compaired to its parent's
	 * 
	 * @param voxel
	 *        The {@link Voxel} being compared
	 * @param parentVoxel
	 *        The parent {@link Voxel} of the one being compared
	 * @return A {@link Float} representing the fraction of surface area of one
	 *         {@link Voxel} to the other
	 */
	private static Float getVoxelFraction(Voxel voxel, Voxel parentVoxel)
	{
		return getSA(voxel) / getSA(parentVoxel);
	}

	/**
	 * A convenience method to get the surface area of a {@link Voxel}
	 * 
	 * @param voxel
	 *        The {@link Voxel} having its surface area calculated
	 * @return A {@link Float} representing the surface area of the
	 *         {@link Voxel}
	 */
	private static Float getSA(Voxel voxel)
	{
		float width = voxel.getMaxX() - voxel.getMinX();
		float height = voxel.getMaxY() - voxel.getMinY();
		float depth = voxel.getMaxZ() - voxel.getMinZ();

		return 2 * (width * height + height * depth + depth * width);
	}

	/**
	 * Method for getting the centroid of a {@link Triangle} as a
	 * {@link Vector3f}
	 * 
	 * @param triangle
	 *        The {@link Triangle} of which the centroid is being calculated
	 * @return A {@link Vector3f} representing the centroid of the
	 *         {@link Triangle}
	 * @throws Exception
	 */
	public static Vector3f getTriangleCentroid(Triangle triangle) throws Exception
	{
		Float minX = null;
		Float maxX = null;
		Float minY = null;
		Float maxY = null;
		Float minZ = null;
		Float maxZ = null;

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

		return new Vector3f((maxX + minX) / 2.0f, (maxY + minY) / 2.0f, (maxZ + minZ) / 2.0f);
	}

	/**
	 * A utility method to get the best point to split the {@link BvhNode}, and
	 * therefore its {@link Triangle}s
	 * 
	 * @param bvhnode
	 *        The {@link BvhNode} being split
	 * @param leftTriangleSubset
	 *        The {@link Set} of {@link Triangle}s for the left side of the
	 *        {@link BvhNode}
	 * @param rightTriangleSubset
	 *        The {@link Set} of {@link Triangle}s for the right side of the
	 *        {@link BvhNode}
	 * @throws Exception
	 */
	public static void getBestSahSplit(BvhNode bvhNode, Set<Triangle> leftTriangleSubset,
			Set<Triangle> rightTriangleSubset) throws Exception
	{
		Plane planeOfSplit = getPlaneOfSplit(bvhNode);

		Float minAlongPlane = bvhNode.getVoxel().getMinZ();
		Float maxAlongPlane = bvhNode.getVoxel().getMaxZ();

		if (planeOfSplit == Plane.XZ)
		{
			minAlongPlane = bvhNode.getVoxel().getMinY();
			maxAlongPlane = bvhNode.getVoxel().getMaxY();
		}
		else if (planeOfSplit == Plane.YZ)
		{
			minAlongPlane = bvhNode.getVoxel().getMinX();
			maxAlongPlane = bvhNode.getVoxel().getMaxX();
		}

		Float bestSahCost = null;

		int loops = 12;
		// Attempt 12 different splits
		for (int i = 0; i < loops; i++)
		{
			Float valueOfSplit = minAlongPlane
					+ ((maxAlongPlane - minAlongPlane) / ((float) loops + 1.0f) * (i + 1));

			Set<Triangle> leftTriangles = new HashSet<Triangle>();
			Set<Triangle> rightTriangles = new HashSet<Triangle>();

			splitTriangles(bvhNode.getTriangles(), valueOfSplit, planeOfSplit, leftTriangles,
					rightTriangles);

			if (leftTriangles.size() > 0 && rightTriangles.size() > 0)
			{
				// Calculate SAH cost and compare with current best
				Float sahCost = getSahCost(VoxelUtil.getVoxel(leftTriangles), leftTriangles.size(),
						VoxelUtil.getVoxel(rightTriangles), rightTriangles.size(),
						bvhNode.getVoxel());

				if (bestSahCost == null || sahCost < bestSahCost)
				{
					bestSahCost = sahCost;

					leftTriangleSubset.clear();
					leftTriangleSubset.addAll(leftTriangles);

					rightTriangleSubset.clear();
					rightTriangleSubset.addAll(rightTriangles);
				}
			}
		}

		// Checks to see if splits occured appropriately and just splits them in
		// half if they are not
		if ((leftTriangleSubset.size() == 0 && rightTriangleSubset.size() > 1)
				|| (rightTriangleSubset.size() == 0 && leftTriangleSubset.size() > 1)
		/*
		 * || (leftTriangleSubset.size() == 0 && rightTriangleSubset.size() ==
		 * 0)
		 */)
		{
			int halfSize = bvhNode.getTriangles().size() / 2;
			int count = 0;
			for (Triangle triangle : bvhNode.getTriangles())
			{
				if (count < halfSize)
				{
					leftTriangleSubset.add(triangle);
				}
				else
				{
					rightTriangleSubset.add(triangle);
				}
				count++;
			}
		}
	}

	/**
	 * Separates a set of {@link Triangle}s into left and right {@link Triangle}
	 * subsets
	 * 
	 * @param trianglesToSplit
	 *        The {@link Triangle}s being split
	 * @param valueOfSplit
	 *        The {@link Float} where the {@link Triangle}s need to split
	 * @param planeOfSplit
	 *        The {@link Plane} where the split occurs
	 * @param leftTriangles
	 *        The {@link Triangle}s to the left of the split value
	 * @param rightTriangles
	 *        The {@link Triangle}s to the right of the split value
	 * @throws Exception
	 */
	private static void splitTriangles(Set<Triangle> trianglesToSplit, Float valueOfSplit,
			Plane planeOfSplit, Set<Triangle> leftTriangles, Set<Triangle> rightTriangles)
			throws Exception
	{
		for (Triangle triangle : trianglesToSplit)
		{
			Vector3f triangleCentroid = getTriangleCentroid(triangle);

			Float centroidValueOnPlane = triangleCentroid.getZ();

			if (planeOfSplit == Plane.XZ)
			{
				centroidValueOnPlane = triangleCentroid.getY();
			}
			else if (planeOfSplit == Plane.YZ)
			{
				centroidValueOnPlane = triangleCentroid.getX();
			}

			// Add triangles to their respective splits
			if (centroidValueOnPlane == valueOfSplit)
			{
				leftTriangles.add(triangle);
				rightTriangles.add(triangle);
			}
			else
			{
				if (centroidValueOnPlane < valueOfSplit)
				{
					leftTriangles.add(triangle);
				}
				else
				{
					rightTriangles.add(triangle);
				}
			}
		}
	}

	/**
	 * Utility method to determine on which {@link Plane} the {@link BvhNode}
	 * needs to be split
	 * 
	 * @param bvhNode
	 *        The {@link BvhNode} being split
	 * @return A {@link Plane}
	 */
	public static Plane getPlaneOfSplit(BvhNode bvhNode)
	{

		float diffX = bvhNode.getVoxel().getMaxX() - bvhNode.getVoxel().getMinX();
		float diffY = bvhNode.getVoxel().getMaxY() - bvhNode.getVoxel().getMinY();
		float diffZ = bvhNode.getVoxel().getMaxZ() - bvhNode.getVoxel().getMinZ();

		Plane plane = Plane.XY;
		if (diffX >= diffY && diffX >= diffZ)
		{
			plane = Plane.YZ;
		}
		else if (diffY >= diffX && diffY >= diffZ)
		{
			plane = Plane.XZ;
		}

		return plane;
	}

	/**
	 * Intersects a {@link RayMap}'s {@link Ray} with a {@link BvhNode} until
	 * all {@link Triangle}s are found and added to the {@link RayMap}
	 * 
	 * @param bvhNode
	 *        The current {@link BvhNode}
	 * @param childBvhNodes
	 *        All of the child {@link BvhNode}s
	 * @param rayMap
	 *        The {@link RayMap} being intersected
	 */
	public static void intersectWithStructure(BvhNode bvhNode, List<BvhNode> childBvhNodes,
			RayMap rayMap)
	{
		if (VoxelUtil.rayIntersectsVoxel(rayMap.getRay(), bvhNode.getVoxel()))
		{
			if (bvhNode.getTriangles() != null && bvhNode.getTriangles().size() == 1)
			{
				rayMap.getTriangles().addAll(bvhNode.getTriangles());
				return;
			}

			// Traverse Left Child
			intersectWithStructure(childBvhNodes.get(bvhNode.getLeftBvhNodeIndex()), childBvhNodes,
					rayMap);

			// Traverse Right Child
			intersectWithStructure(childBvhNodes.get(bvhNode.getRightBvhNodeIndex()),
					childBvhNodes, rayMap);
		}
	}
}
