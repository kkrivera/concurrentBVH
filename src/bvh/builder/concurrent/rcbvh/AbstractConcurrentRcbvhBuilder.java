package bvh.builder.concurrent.rcbvh;

import java.util.Set;

import triangle.Triangle;
import bvh.BvhTree;
import bvh.builder.concurrent.AbstractConcurrentBvhBuilder;

public abstract class AbstractConcurrentRcbvhBuilder extends AbstractConcurrentBvhBuilder implements
		IConcurrentRcbvhBuilder
{
	@Override
	public BvhTree buildTree(Set<Triangle> triangles, Integer maxThreads) throws Exception
	{
		throw new Exception("Collection of RayMaps required");
	}

	@Override
	public BvhTree buildTree(Set<Triangle> triangles) throws Exception
	{
		throw new Exception("Collection of RayMaps and max threads required");
	}
}
