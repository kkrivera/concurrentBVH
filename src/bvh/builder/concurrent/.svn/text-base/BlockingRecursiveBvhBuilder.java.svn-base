package bvh.builder.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import triangle.Triangle;
import bvh.BvhNode;
import bvh.BvhTree;
import bvh.concurrent.ConcurrentBvhNode;

public class BlockingRecursiveBvhBuilder extends AbstractConcurrentBvhBuilder
{
	@Override
	public BvhTree buildTree(Set<Triangle> triangles, Integer maxThreadPoolSize) throws Exception
	{
		BvhTree bvhTree = new BvhTree(buildBvhNode(triangles), new ArrayList<BvhNode>());

		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(maxThreadPoolSize,
				maxThreadPoolSize, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(
						maxThreadPoolSize));

		Queue<Future<?>> taskQueue = new LinkedBlockingQueue<Future<?>>();

		for (int i = 0; i < maxThreadPoolSize; i++)
		{
			taskQueue.add(threadPool.submit(new BuildTree(bvhTree)));
		}

		// Waits for the tree to finish building
		waitUntilQueueCompleted(taskQueue);

		return bvhTree;
	}

	/**
	 * A class to be run by a threadPool for building the a {@link BvhTree}
	 * 
	 * @author kkrivera
	 */
	private class BuildTree implements Runnable
	{
		private BvhTree bvhTree;

		public BuildTree(BvhTree bvhTree)
		{
			this.bvhTree = bvhTree;
		}

		@Override
		public void run()
		{
			try
			{
				recursiveThreadedTreeBuild((ConcurrentBvhNode) bvhTree.getParentBvhNode(), bvhTree);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * A method to be used to recursively build a {@link BvhTree}
	 * 
	 * @param bvhNode
	 *        The parent {@link BvhNode} to the children nodes
	 * @param bvhTree
	 *        The {@link BvhTree} containing the full list of children
	 * @throws Exception
	 */
	private void recursiveThreadedTreeBuild(ConcurrentBvhNode bvhNode, BvhTree bvhTree)
			throws Exception
	{
		if (bvhNode.getTriangles().size() <= 1)
			return;

		// Locks the node and builds children
		bvhNode.lockAndExecute(new FutureTask<Boolean>(new BuildChildrenCallable(bvhNode, bvhTree)));

		// bvhNode.buildChildren(
		// this.getClass().getMethod("buildChildren", BvhNode.class,
		// BvhTree.class), this,
		// bvhTree);

		List<BvhNode> bvhTreeList = (List<BvhNode>) bvhTree.getTreeCollection();

		// Determines whether the left or right side will be traversed first
		Boolean traverseLeft = null;
		do
		{
			traverseLeft = bvhNode.traverseLeft.get();
		}
		while (!bvhNode.traverseLeft.compareAndSet(traverseLeft, !traverseLeft));

		// Traverses the left side of the tree if traverse left is true
		if (traverseLeft != null && traverseLeft)
		{

			// Traverse Left Side
			if (bvhNode.getLeftBvhNodeIndex() != null)
			{
				recursiveThreadedTreeBuild(
						(ConcurrentBvhNode) bvhTreeList.get(bvhNode.getLeftBvhNodeIndex()), bvhTree);
			}

			// Traverse Right Side
			if (bvhNode.getRightBvhNodeIndex() != null)
			{
				recursiveThreadedTreeBuild(
						(ConcurrentBvhNode) bvhTreeList.get(bvhNode.getRightBvhNodeIndex()),
						bvhTree);
			}
		}
		else
		{
			// Traverse Right Side
			if (bvhNode.getRightBvhNodeIndex() != null)
			{
				recursiveThreadedTreeBuild(
						(ConcurrentBvhNode) bvhTreeList.get(bvhNode.getRightBvhNodeIndex()),
						bvhTree);
			}

			// Traverse Left Side
			if (bvhNode.getLeftBvhNodeIndex() != null)
			{
				recursiveThreadedTreeBuild(
						(ConcurrentBvhNode) bvhTreeList.get(bvhNode.getLeftBvhNodeIndex()), bvhTree);
			}
		}

	}

	/**
	 * A {@link Callable} class for building chilren for a single
	 * {@link BvhNode}
	 * 
	 * @author kkrivera
	 */
	private class BuildChildrenCallable implements Callable<Boolean>
	{
		private BvhNode bvhNode;
		private BvhTree bvhTree;

		public BuildChildrenCallable(BvhNode bvhNode, BvhTree bvhTree)
		{
			this.bvhNode = bvhNode;
			this.bvhTree = bvhTree;
		}

		@Override
		public Boolean call() throws Exception
		{
			Boolean childrenRequired = nodeRequiresChildren(bvhNode);
			if (childrenRequired)
			{
				buildChildren(bvhNode, bvhTree);
			}
			return childrenRequired;
		}

	}

}
