package bvh.builder.concurrent.rcbvh;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.vecmath.Vector3f;

import rayMap.RayMap;
import triangle.Triangle;
import voxel.util.VoxelUtil;
import bvh.BvhNode;
import bvh.BvhTree;
import bvh.concurrent.ConcurrentBvhNode;
import bvh.util.BvhNodeUtil;
import enums.Plane;

/**
 * A class for building an N Children RCBVH implementation
 * 
 * @author kkrivera
 */
public class BlockingNChildrenRcbvhBuilder extends BlockingRecursiveRcbvhBuilder
{
	AtomicBoolean buildNChildren = new AtomicBoolean(true);

	@Override
	public BvhNode buildBvhNode(Set<Triangle> triangles) throws Exception
	{
		return new ConcurrentNChildBvhNode(triangles, VoxelUtil.getVoxel(triangles));
	}

	@Override
	protected void recursiveThreadedTreeBuild(ConcurrentBvhNode bvhNode, BvhTree bvhTree,
			Set<RayMap> rayMaps) throws Exception
	{
		ConcurrentNChildBvhNode nChildBvhNode = (ConcurrentNChildBvhNode) bvhNode;

		if (rayMaps.isEmpty())
		{
			return;
		}

		Set<RayMap> intersectedRayMaps = new HashSet<RayMap>();

		for (RayMap rayMap : rayMaps)
		{
			if (VoxelUtil.rayIntersectsVoxel(rayMap.getRay(), bvhNode.getVoxel()))
			{
				intersectedRayMaps.add(rayMap);
			}
		}

		if (nChildBvhNode.getTriangles().size() == 1)
		{
			for (RayMap rayMap : intersectedRayMaps)
			{
				rayMap.getTriangles().addAll(nChildBvhNode.getTriangles());
			}
			return;
		}

		if (!intersectedRayMaps.isEmpty())
		{
			FutureTask<Boolean> buildChildrenTask = null;

			if (buildNChildren.get())
			{
				buildChildrenTask = new FutureTask<Boolean>(new BuildNChildrenCallable(
						nChildBvhNode, bvhTree, numThreads));
			}
			else
			{
				buildChildrenTask = new FutureTask<Boolean>(new BuildChildrenCallable(
						nChildBvhNode, bvhTree));
			}

			// Locks the node and builds children
			nChildBvhNode.lockAndExecute(buildChildrenTask);

			List<BvhNode> bvhTreeList = (List<BvhNode>) bvhTree.getTreeCollection();

			int sizeOfChildren = nChildBvhNode.getChildrenIndeces().size();
			int childrenProecessed = 0;

			// Checks to make sure start index is not out of bounds
			int currentIndex = nChildBvhNode.startIndex.getAndIncrement();
			while (currentIndex >= sizeOfChildren)
			{
				nChildBvhNode.startIndex.set(0);
				currentIndex = nChildBvhNode.startIndex.getAndIncrement();
			}

			while (childrenProecessed < sizeOfChildren)
			{
				recursiveThreadedTreeBuild(
						(ConcurrentBvhNode) bvhTreeList.get(nChildBvhNode.getChildrenIndeces().get(
								currentIndex)), bvhTree, intersectedRayMaps);

				currentIndex++;

				currentIndex %= sizeOfChildren;
				childrenProecessed++;
			}
		}
	}

	protected class BuildNChildrenCallable implements Callable<Boolean>
	{
		private ConcurrentNChildBvhNode bvhNode;
		private BvhTree bvhTree;
		private Integer n;

		public BuildNChildrenCallable(ConcurrentNChildBvhNode bvhNode, BvhTree bvhTree, Integer n)
		{
			this.bvhNode = bvhNode;
			this.bvhTree = bvhTree;
			this.n = n;
		}

		@Override
		public Boolean call() throws Exception
		{
			if (nodeRequiresChildren(bvhNode))
			{
				buildNChildren(bvhNode, bvhTree, n);
			}
			return true;
		}

	}

	/**
	 * Builds N {@link ConcurrentNChildBvhNode}s for N {@link Thread}s
	 * 
	 * @param bvhNode
	 *        The {@link ConcurrentNChildBvhNode} having children built for
	 * @param bvhTree
	 *        The {@link BvhTree} storing the structure
	 * @param n
	 *        The number of childre to build
	 * @throws Exception
	 */
	protected void buildNChildren(ConcurrentNChildBvhNode bvhNode, BvhTree bvhTree, int n)
			throws Exception
	{
		final Plane planeOfSplit = BvhNodeUtil.getPlaneOfSplit(bvhNode);

		/**
		 * Comparator class
		 * 
		 * @author kkrivera
		 */
		class compareTriangles implements Comparator<Triangle>
		{
			@Override
			public int compare(Triangle o1, Triangle o2)
			{
				try
				{
					Vector3f centroid1 = BvhNodeUtil.getTriangleCentroid(o1);
					Vector3f centroid2 = BvhNodeUtil.getTriangleCentroid(o1);

					float val1 = centroid1.getZ();
					float val2 = centroid2.getZ();

					if (planeOfSplit == Plane.YZ)
					{
						val1 = centroid1.getX();
						val2 = centroid2.getX();
					}
					else if (planeOfSplit == Plane.XZ)
					{
						val1 = centroid1.getY();
						val2 = centroid2.getY();
					}

					if (val1 < val2)
						return 1;
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				return 0;
			}

		}
		List<Triangle> triangles = new ArrayList<Triangle>(bvhNode.getTriangles());
		Collections.sort(triangles, new compareTriangles());

		bvhNode.setTriangles(new HashSet<Triangle>(triangles));

		int size = triangles.size();
		int div = size / n;

		int start = 0;
		int end = 0;
		for (int i = 0; i < n; i++)
		{
			start = div * i;
			if (i != n - 1)
			{
				end = div * (i + 1);
			}
			else
			{
				end = size;
			}
			ConcurrentNChildBvhNode concurrentNChildBvhNode = (ConcurrentNChildBvhNode) buildBvhNode(new HashSet<Triangle>(
					triangles.subList(start, end)));

			bvhNode.getChildrenIndeces().add(bvhTree.getTreeCollection().size());
			bvhTree.getTreeCollection().add(concurrentNChildBvhNode);
		}

		buildNChildren.set(false);
	}

	@Override
	public void buildChildren(BvhNode bvhNode, BvhTree bvhTree) throws Exception
	{
		ConcurrentNChildBvhNode nChildBvhNode = (ConcurrentNChildBvhNode) bvhNode;

		Set<Triangle> leftTriangleSubset = new HashSet<Triangle>();
		Set<Triangle> rightTriangleSubset = new HashSet<Triangle>();

		if (nChildBvhNode.getTriangles().size() > 1)
		{
			// Sets the left and right triangle subsets
			BvhNodeUtil.getBestSahSplit(nChildBvhNode, leftTriangleSubset, rightTriangleSubset);

			if (leftTriangleSubset.size() == 0 && rightTriangleSubset.size() == 0)
			{
				List<Triangle> trianglesList = new ArrayList<Triangle>(nChildBvhNode.getTriangles());

				int halfSize = trianglesList.size() / 2;
				leftTriangleSubset.addAll(trianglesList.subList(0, halfSize));
				rightTriangleSubset.addAll(trianglesList.subList(halfSize, trianglesList.size()));
			}
		}

		synchronized (bvhTree)
		{
			// Left Child
			if (leftTriangleSubset.size() > 0)
			{
				nChildBvhNode.getChildrenIndeces().add(bvhTree.getTreeCollection().size());
				bvhTree.getTreeCollection().add(buildBvhNode(leftTriangleSubset));
			}

			// Right Child
			if (rightTriangleSubset.size() > 0)
			{
				nChildBvhNode.getChildrenIndeces().add(bvhTree.getTreeCollection().size());
				bvhTree.getTreeCollection().add(buildBvhNode(rightTriangleSubset));
			}
		}
	}

	@Override
	public Boolean nodeRequiresChildren(BvhNode bvhNode)
	{
		ConcurrentNChildBvhNode nChildBvhNode = (ConcurrentNChildBvhNode) bvhNode;
		return nChildBvhNode.getTriangles().iterator().next() != null
				&& nChildBvhNode.getChildrenIndeces().isEmpty();
	}
}
