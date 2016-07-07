package bvh.builder.concurrent.rcbvh;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import rayMap.RayMap;
import triangle.Triangle;
import bvh.BvhNode;
import bvh.BvhTree;

public class BlockingRecursiveRcbvhBuilder extends AbstractBlockingRcbvhBuilder
{
	protected Integer numThreads;

	@Override
	public BvhTree buildTree(Set<Triangle> triangles, Set<Set<RayMap>> rayMapsSet,
			Integer maxThreads, Integer numReflections, Boolean writeRayMapToRaster)
			throws Exception
	{
		BvhTree bvhTree = new BvhTree(buildBvhNode(triangles), new ArrayList<BvhNode>());
		childBvhNodes = (List<BvhNode>) bvhTree.getTreeCollection();

		this.numThreads = maxThreads;
		this.numReflections = numReflections;

		if (rayMapsSet.size() != maxThreads)
		{
			throw new Exception("RayMaps need to equal number of threads:" + maxThreads);
		}

		for (Set<RayMap> rayMaps : rayMapsSet)
		{
			new Thread(new BuildTree(bvhTree, rayMaps, writeRayMapToRaster)).start();
			this.runningThreads.incrementAndGet();
		}

		// Wait for threads to finish executing
		while (this.runningThreads.get() > 0)
			Thread.yield();

		return bvhTree;
	}
}
