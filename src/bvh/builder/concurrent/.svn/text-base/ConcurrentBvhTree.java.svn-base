package bvh.builder.concurrent;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import bvh.BvhNode;
import bvh.BvhTree;

public class ConcurrentBvhTree extends BvhTree
{
	AtomicInteger treeSize;

	public ConcurrentBvhTree(BvhNode parentBvhNode, Collection<BvhNode> tree)
	{
		super(parentBvhNode, tree);
		treeSize = new AtomicInteger(0);
	}

	/**
	 * @return the treeSize
	 */
	public AtomicInteger getTreeSize()
	{
		return treeSize;
	}

}
