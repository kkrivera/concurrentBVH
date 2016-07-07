package bvh.builder.concurrent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import triangle.Triangle;
import bvh.BvhNode;
import bvh.BvhTree;
import bvh.util.BvhNodeUtil;

public class NonBlockingThreadPoolBvhBuilder extends AbstractConcurrentBvhBuilder
{
	private Queue<BuildTask> buildTaskQueue;
	private AtomicInteger pendingThreadCount;

	@Override
	public BvhTree buildTree(Set<Triangle> triangles, Integer maxThreadPoolSize) throws Exception
	{
		buildTaskQueue = new ConcurrentLinkedQueue<BuildTask>();
		BvhTree bvhTree = new BvhTree(buildBvhNode(triangles), new ConcurrentLinkedQueue<BvhNode>());

		buildTaskQueue.add(new BuildTask(bvhTree.getParentBvhNode(), bvhTree));

		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(maxThreadPoolSize,
				maxThreadPoolSize, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(
						triangles.size()));

		pendingThreadCount = new AtomicInteger(0);
		int submitCount = 0;

		while (pendingThreadCount.get() > 0 || !buildTaskQueue.isEmpty())
		{
			BuildTask task = buildTaskQueue.poll();

			if (task != null)
			{
				threadPool.submit(task);
				pendingThreadCount.incrementAndGet();
				submitCount++;
			}
			else
			{
				Thread.yield();
			}
		}

		// System.out.println("Number of submit calls: " + submitCount);

		return bvhTree;
	}

	@Override
	public void buildChildren(BvhNode bvhNode, BvhTree bvhTree) throws Exception
	{
		if (bvhNode.getTriangles().size() > 1)
		{
			Set<Triangle> leftTriangleSubset = new HashSet<Triangle>();
			Set<Triangle> rightTriangleSubset = new HashSet<Triangle>();

			BvhNodeUtil.getBestSahSplit(bvhNode, leftTriangleSubset, rightTriangleSubset);

			if (leftTriangleSubset.size() == 0 && rightTriangleSubset.size() == 0
					&& bvhNode.getTriangles().size() > 1)
			{
				List<Triangle> trianglesList = new ArrayList<Triangle>(bvhNode.getTriangles());

				int halfSize = trianglesList.size() / 2;
				leftTriangleSubset.addAll(trianglesList.subList(0, halfSize));
				rightTriangleSubset.addAll(trianglesList.subList(halfSize, trianglesList.size()));
			}

			// Left Child
			if (leftTriangleSubset.size() > 0)
			{
				bvhNode.setLeftBvhNodeIndex(bvhTree.getTreeCollection().size());

				BvhNode leftNode = buildBvhNode(leftTriangleSubset);

				bvhTree.getTreeCollection().add(leftNode);
				buildTaskQueue.add(new BuildTask(leftNode, bvhTree));
			}

			// Right Child
			if (rightTriangleSubset.size() > 0)
			{
				bvhNode.setRightBvhNodeIndex(bvhTree.getTreeCollection().size());

				BvhNode rightNode = buildBvhNode(rightTriangleSubset);

				bvhTree.getTreeCollection().add(rightNode);
				buildTaskQueue.add(new BuildTask(rightNode, bvhTree));
			}
		}

		pendingThreadCount.decrementAndGet();
	}

	/**
	 * A class for building a {@link BvhNode}'s children
	 * 
	 * @author kkrivera
	 */
	private class BuildTask implements Runnable
	{
		private BvhNode bvhNode;
		private BvhTree bvhTree;

		public BuildTask(BvhNode bvhNode, BvhTree bvhTree)
		{
			this.bvhNode = bvhNode;
			this.bvhTree = bvhTree;
		}

		@Override
		public void run()
		{
			try
			{
				buildChildren(bvhNode, bvhTree);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

	}
}
