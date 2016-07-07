package bvh.builder.concurrent.rcbvh;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import triangle.Triangle;
import voxel.Voxel;
import bvh.concurrent.ConcurrentBvhNode;

public class ConcurrentNChildBvhNode extends ConcurrentBvhNode
{
	public AtomicInteger startIndex;
	private List<Integer> childrenIndeces;

	public ConcurrentNChildBvhNode(Set<Triangle> triangles, Voxel voxel)
	{
		super(triangles, voxel);
		childrenIndeces = new ArrayList<Integer>();
		startIndex = new AtomicInteger(0);
	}

	/**
	 * @return the children
	 */
	public List<Integer> getChildrenIndeces()
	{
		return childrenIndeces;
	}

	/**
	 * @param children
	 *        the children to set
	 */
	public void setChildrenIndeces(List<Integer> children)
	{
		this.childrenIndeces = children;
	}

}
