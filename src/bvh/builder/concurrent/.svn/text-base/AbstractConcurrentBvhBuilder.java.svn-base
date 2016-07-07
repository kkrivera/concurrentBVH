package bvh.builder.concurrent;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import triangle.Triangle;
import voxel.util.VoxelUtil;
import bvh.BvhNode;
import bvh.BvhTree;
import bvh.builder.AbstractBvhBuilder;
import bvh.concurrent.ConcurrentBvhNode;

public abstract class AbstractConcurrentBvhBuilder extends AbstractBvhBuilder implements
		IConcurrentBvhBuilder
{
	@Override
	public BvhNode buildBvhNode(Set<Triangle> triangles) throws Exception
	{
		return new ConcurrentBvhNode(triangles, VoxelUtil.getVoxel(triangles));
	}

	@Override
	public BvhTree buildTree(Set<Triangle> triangles) throws Exception
	{
		return buildTree(triangles, 1);
	}

	/**
	 * Waits until all {@link Future} tasks are completed
	 * 
	 * @param taskQueue
	 *        The {@link Queue} of tasks
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	protected void waitUntilQueueCompleted(Queue<Future<?>> taskQueue) throws InterruptedException,
			ExecutionException
	{
		Future<?> task = taskQueue.poll();
		while (task != null)
		{
			task.get();
			task = taskQueue.poll();
		}
	}
}
