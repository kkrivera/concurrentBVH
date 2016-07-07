package bvh.builder.concurrent;

import java.util.Set;

import triangle.Triangle;
import bvh.BvhNode;

public class BuilderTask
{
	private BvhNode parent;
	private Set<Triangle> triangles;
	private Boolean leftChild; // False for right child

	public BuilderTask(BvhNode parent, Set<Triangle> triangles, Boolean leftChildFlag)
	{
		this.parent = parent;
		this.triangles = triangles;
		this.leftChild = leftChildFlag;
	}

	public BvhNode getParent()
	{
		return parent;
	}

	public void setParent(BvhNode parent)
	{
		this.parent = parent;
	}

	public Set<Triangle> getTriangles()
	{
		return triangles;
	}

	public void setTriangles(Set<Triangle> triangles)
	{
		this.triangles = triangles;
	}

	public Boolean getLeftChild()
	{
		return leftChild;
	}

	public void setLeftChild(Boolean leftChild)
	{
		this.leftChild = leftChild;
	}
}
