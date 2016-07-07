package bvh.builder.concurrent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import triangle.Triangle;
import bvh.BvhNode;
import bvh.BvhTree;
import bvh.util.BvhNodeUtil;

public class NonBlockingBvhBuilder extends AbstractConcurrentBvhBuilder
{
	ConcurrentLinkedQueue<BvhNode> nodesToBuild;
	AtomicInteger threadsRunning;
	ConcurrentBvhTree bvhTree;
	Integer expectedTreeSize;
	AtomicInteger nodesBuilt;

	@Override
	public BvhTree buildTree(Set<Triangle> triangles, Integer maxThreads) throws Exception
	{
		nodesToBuild = new ConcurrentLinkedQueue<BvhNode>();
		nodesBuilt = new AtomicInteger(0);
		threadsRunning = new AtomicInteger(0);
		expectedTreeSize = new Integer(triangles.size() * 2 - 1);

		bvhTree = new ConcurrentBvhTree(buildBvhNode(triangles),
				new ConcurrentLinkedQueue<BvhNode>());

		// LockFreeVector<BvhNode> tree = new LockFreeVector<BvhNode>();
		// WTF. Reserving space is HILARIOUSLY slow.
		// tree.reserve(expectedTreeSize);
		// bvhTree = new ConcurrentBvhTree(buildBvhNode(triangles), tree);

		System.out.printf("Starting main thread gen loop\n");

		int spawnCount = 0;
		while (threadsRunning.get() < maxThreads && nodesBuilt.get() < expectedTreeSize)
		{
			if (!nodesToBuild.isEmpty())
			{
				Thread thread = new Thread(new BuildNodesOnQueue());
				threadsRunning.incrementAndGet();

				thread.start();

				spawnCount++;
			}
		}

		System.out.printf("Hit maxThreads, spawnCount = %d\n", spawnCount);

		// Wait for threads to finish executing
		while (threadsRunning.get() > 0)
			Thread.yield();

		System.out.printf("All threads completed\n");

		return bvhTree;
	}

	private class BuildNodesOnQueue implements Runnable
	{
		@Override
		public void run()
		{
			BvhNode nodeToBuild = nodesToBuild.poll();

			while (nodeToBuild != null)
			{
				try
				{
					if (nodeRequiresChildren(nodeToBuild))
					{
						buildChildren(nodeToBuild, bvhTree);
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

				nodeToBuild = nodesToBuild.poll();

				while (nodeToBuild == null && nodesBuilt.get() < expectedTreeSize)
				{
					nodeToBuild = nodesToBuild.poll();
				}
			}

			threadsRunning.decrementAndGet();
		}
	}

	@Override
	public BvhNode buildBvhNode(Set<Triangle> triangles) throws Exception
	{
		BvhNode node = super.buildBvhNode(triangles);
		nodesBuilt.incrementAndGet();
		nodesToBuild.add(node);

		return node;
	}

	@Override
	public void buildChildren(BvhNode bvhNode, BvhTree bvhTree) throws Exception
	{
		Set<Triangle> leftTriangleSubset = new HashSet<Triangle>();
		Set<Triangle> rightTriangleSubset = new HashSet<Triangle>();

		if (bvhNode.getTriangles().size() > 1)
		{
			// Sets the left and right triangle subsets
			BvhNodeUtil.getBestSahSplit(bvhNode, leftTriangleSubset, rightTriangleSubset);

			if (leftTriangleSubset.size() == 0 && rightTriangleSubset.size() == 0)
			{
				List<Triangle> trianglesList = new ArrayList<Triangle>(bvhNode.getTriangles());

				int halfSize = trianglesList.size() / 2;
				leftTriangleSubset.addAll(trianglesList.subList(0, halfSize));
				rightTriangleSubset.addAll(trianglesList.subList(halfSize, trianglesList.size()));
			}
		}

		ConcurrentBvhTree concurrentBvhTree = (ConcurrentBvhTree) bvhTree;
		// Left Child
		if (leftTriangleSubset.size() > 0)
		{
			bvhNode.setLeftBvhNodeIndex(concurrentBvhTree.getTreeSize().getAndIncrement());
			bvhTree.getTreeCollection().add(buildBvhNode(leftTriangleSubset));
		}

		// Right Child
		if (rightTriangleSubset.size() > 0)
		{
			bvhNode.setRightBvhNodeIndex(concurrentBvhTree.getTreeSize().getAndIncrement());
			bvhTree.getTreeCollection().add(buildBvhNode(rightTriangleSubset));
		}
	}
}
