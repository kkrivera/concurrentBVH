package bvh.builder.concurrent.rcbvh;

import java.util.Set;

import rayMap.RayMap;
import triangle.Triangle;
import bvh.BvhTree;
import bvh.builder.concurrent.IConcurrentBvhBuilder;

public interface IConcurrentRcbvhBuilder extends IConcurrentBvhBuilder
{
	public BvhTree buildTree(Set<Triangle> triangles, Set<Set<RayMap>> rayMapsSet,
			Integer maxThreads, Integer numReflections, Boolean writeRayMapToRaster)
			throws Exception;
}
