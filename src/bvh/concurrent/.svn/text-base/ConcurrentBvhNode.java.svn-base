package bvh.concurrent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;

import triangle.Triangle;
import voxel.Voxel;
import bvh.BvhNode;
import bvh.BvhTree;
import bvh.builder.concurrent.IConcurrentBvhBuilder;

public class ConcurrentBvhNode extends BvhNode
{
	public AtomicBoolean traverseLeft;

	public ConcurrentBvhNode(Set<Triangle> triangles, Voxel voxel)
	{
		super(triangles, voxel);
		this.traverseLeft = new AtomicBoolean();
	}

	/**
	 * A synchronized and templated method to lock a {@link BvhNode} and return
	 * a the templated value
	 * 
	 * @param <T>
	 *        The template which gets returned after executing the
	 *        {@link FutureTask}
	 * @param task
	 *        The {@link FutureTask} which gets executed
	 * @return The result of executing the {@link FutureTask}
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public synchronized <T> T lockAndExecute(FutureTask<T> task) throws InterruptedException,
			ExecutionException
	{
		task.run();
		return task.get();
	}

	/**
	 * Builds children in a synchronized method
	 * 
	 * @param buildChrildrenMethod
	 *        The {@link Method} which builds the children {@link BvhNode}
	 * @param builder
	 *        The {@link IConcurrentBvhBuilder} which builds children
	 * @param bvhTree
	 *        The {@link BvhTree} which will be modified
	 */
	public synchronized void buildChildren(Method buildChrildrenMethod,
			IConcurrentBvhBuilder builder, BvhTree bvhTree)
	{
		try
		{
			buildChrildrenMethod.invoke(builder, this, bvhTree);
		}
		catch (IllegalArgumentException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}