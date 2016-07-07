package bvh.builder.concurrent;

import java.util.Set;

import triangle.Triangle;
import bvh.BvhTree;
import bvh.builder.IBvhBuilder;

public interface IConcurrentBvhBuilder extends IBvhBuilder
{
	public BvhTree buildTree(Set<Triangle> triangles, Integer maxThreads) throws Exception;
}
