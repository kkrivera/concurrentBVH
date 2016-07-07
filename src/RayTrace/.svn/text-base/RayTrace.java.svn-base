package rayTrace;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.vecmath.Vector3f;

import printstream.util.PrintStreamUtil;
import rayMap.RayMap;
import rayTrace.util.RayTraceUtil;
import triangle.Triangle;
import window.Window;
import bvh.BvhNode;
import bvh.BvhTree;
import bvh.builder.IBvhBuilder;
import bvh.builder.concurrent.rcbvh.IConcurrentRcbvhBuilder;
import bvh.builder.rcbvh.IRcbvhBuilder;
import bvh.util.BvhNodeUtil;

public class RayTrace
{
	/**
	 * Ray traces the completely built {@link BvhTree}
	 * 
	 * @param bvhTree
	 *        The {@link BvhTree} to ray trace
	 * @param eye
	 *        The {@link Vector3f} representing the eye of the observer
	 * @param at
	 *        The {@link Vector3f} representing where the eye is looking
	 * @param lightPoint
	 *        The {@link Vector3f} representing the position of the light point
	 * @param numReflections
	 *        The number of reflections
	 * @param showRaster
	 *        The {@link Boolean} to either show the raster after ray tracing
	 */
	public static void rayTrace(BvhTree bvhTree, Vector3f eye, Vector3f at, Vector3f lightPoint,
			int numReflections, boolean showRaster)
	{
		Collection<RayMap> rayMaps = RayTraceUtil.getSceneRayMaps(eye, at, lightPoint);

		List<BvhNode> bvhNodeChildren = new ArrayList<BvhNode>(bvhTree.getTreeCollection());

		Collection<RayMap> subRayMapArray = new ArrayList<RayMap>(rayMaps);
		Collection<RayMap> indirectRayMaps = new HashSet<RayMap>();
		for (int i = 0; i <= numReflections + 1; i++)
		{
			indirectRayMaps = new HashSet<RayMap>();
			for (RayMap rayMap : subRayMapArray)
			{
				BvhNodeUtil.intersectWithStructure(bvhTree.getParentBvhNode(), bvhNodeChildren,
						rayMap);

				indirectRayMaps.addAll(RayTraceUtil.generateInderectRayMaps(rayMap,
						i < (numReflections + 1), i < numReflections));
			}

			subRayMapArray = indirectRayMaps;
		}

		RayTraceUtil.writeRayMapToRaster(rayMaps);

		if (showRaster)
		{
			Window.showRaster();
		}
	}

	/**
	 * Renders a single threaded {@link IRcbvhBuilder} implementation
	 * 
	 * @param rcbvhBuilder
	 *        The {@link IRcbvhBuilder} to build the rcbvh structure
	 * @param triangles
	 *        The {@link Set} of {@link Triangle}s to render
	 * @param eye
	 *        The {@link Vector3f} representing the eye of the camera
	 * @param at
	 *        The {@link Vector3f} of where the eye is looking
	 * @param light
	 *        The {@link Vector3f} representing the position of the light
	 * @param numReflections
	 *        The {@link Integer} number of reflections
	 * @param showRaster
	 *        A {@link Boolean} of whether to show the raster after rendering
	 * @param fileOut
	 *        The {@link PrintStream}
	 * @return The total time to perform process
	 * @throws Exception
	 */
	public static Long renderSingleThreadedRcbvh(IRcbvhBuilder rcbvhBuilder,
			Set<Triangle> triangles, Vector3f eye, Vector3f at, Vector3f light, int numReflections,
			Boolean showRaster, PrintStream fileOut) throws Exception
	{
		long start;
		long end;
		long total = 0;

		Window.clearRaster();

		PrintStreamUtil.print(fileOut, "Generating Rays - ");
		start = System.currentTimeMillis();
		Set<RayMap> rayMaps = RayTraceUtil.getSceneRayMaps(eye, at, light);
		end = System.currentTimeMillis();
		total += PrintStreamUtil.println(fileOut, "done", start, end);

		PrintStreamUtil.print(fileOut, "Building RCBVH Tree - ");
		start = System.currentTimeMillis();
		BvhTree rcbvhTree = rcbvhBuilder.buildTree(triangles, rayMaps, numReflections);
		end = System.currentTimeMillis();
		total += PrintStreamUtil.println(fileOut, "done", start, end);

		PrintStreamUtil.println(fileOut, "Bvh Nodes: " + rcbvhTree.getTreeCollection().size());

		PrintStreamUtil.print(fileOut, "Ray Tracing - ");
		start = System.currentTimeMillis();
		RayTraceUtil.writeRayMapToRaster(rayMaps);
		end = System.currentTimeMillis();
		total += PrintStreamUtil.println(fileOut, "done", start, end);

		PrintStreamUtil.println(fileOut, "Total time: " + total + "ms");

		if (showRaster)
		{
			Window.showRaster();
		}

		return total;
	}

	/**
	 * Renders a single threaded {@link IBvhBuilder} implementation
	 * 
	 * @param bvhBuilder
	 *        The implementation of the {@link IBvhBuilder}
	 * @param triangles
	 *        The {@link Set} of {@link Triangle}s to render
	 * @param eye
	 *        The {@link Vector3f} representing the eye of the camera
	 * @param at
	 *        The {@link Vector3f} of where the eye is looking
	 * @param light
	 *        The {@link Vector3f} representing the position of the light
	 * @param numReflections
	 *        The {@link Integer} number of reflections
	 * @param showRaster
	 *        A {@link Boolean} of whether to show the raster after rendering
	 * @param fileOut
	 *        The {@link PrintStream}
	 * @return The total time of execution
	 * @throws Exception
	 */
	public static Long renderSingleThreadedBvh(IBvhBuilder bvhBuilder, Set<Triangle> triangles,
			Vector3f eye, Vector3f at, Vector3f light, int numReflections, Boolean showRaster,
			PrintStream fileOut) throws Exception
	{
		long start;
		long end;
		long total = 0;

		Window.clearRaster();

		PrintStreamUtil.print(fileOut, "Build BVH - ");
		start = System.currentTimeMillis();
		BvhTree bvhTree = bvhBuilder.buildTree(triangles);
		end = System.currentTimeMillis();
		total += PrintStreamUtil.println(fileOut, "done", start, end);
		PrintStreamUtil.println(fileOut, "Bvh Nodes: " + bvhTree.getTreeCollection().size());

		PrintStreamUtil.print(fileOut, "Ray Trace - ");
		start = System.currentTimeMillis();
		RayTrace.rayTrace(bvhTree, eye, at, light, numReflections, showRaster);
		end = System.currentTimeMillis();
		total += PrintStreamUtil.println(fileOut, "done", start, end);

		PrintStreamUtil.println(fileOut, "Total time: " + total + "ms");

		return total;
	}

	/**
	 * Builds and renders an {@link IConcurrentRcbvhBuilder}
	 * 
	 * @param concurrentRcbvhBuilder
	 *        The {@link IConcurrentRcbvhBuilder}
	 * @param numThreads
	 *        The number of threads
	 * @param triangles
	 *        The {@link Set} of {@link Triangle}s
	 * @param eye
	 *        The {@link Vector3f} representing the eye of the camera
	 * @param at
	 *        The {@link Vector3f} of where the eye is looking
	 * @param light
	 *        The {@link Vector3f} representing the position of the light
	 * @param numReflections
	 *        The {@link Integer} number of reflections
	 * @param showRaster
	 *        A {@link Boolean} of whether to show the raster after rendering
	 * @param fileOut
	 *        The {@link PrintStream}
	 * @return The total time of execution
	 * @throws Exception
	 */
	public static Long renderConcurrentRcbvh(IConcurrentRcbvhBuilder concurrentRcbvhBuilder,
			Integer numThreads, Set<Triangle> triangles, Vector3f eye, Vector3f at, Vector3f light,
			int numReflections, Boolean showRaster, PrintStream fileOut) throws Exception
	{
		long start;
		long end;
		long total = 0;

		Window.clearRaster();

		// Writes the thread's separate Set of RayMaps after it is done building
		// instead of waiting for all of the threads to build, then RayTrace
		Boolean writeRayMapToRasterPostThreadBuild = true;

		PrintStreamUtil.print(fileOut, "Generating Rays - ");
		start = System.currentTimeMillis();
		List<RayMap> rayMaps = new ArrayList<RayMap>(RayTraceUtil.getSceneRayMaps(eye, at, light));
		end = System.currentTimeMillis();
		total += PrintStreamUtil.println(fileOut, "done", start, end);

		PrintStreamUtil.print(fileOut, "Splitting Rays - ");
		start = System.currentTimeMillis();
		Integer div = rayMaps.size() / numThreads;
		Set<Set<RayMap>> rayMapsSet = new HashSet<Set<RayMap>>();
		for (int i = 0; i < numThreads; i++)
		{
			if (i != numThreads - 1)
			{
				rayMapsSet.add(new HashSet<RayMap>(rayMaps.subList(div * i, div * (i + 1))));
			}
			else
			{
				rayMapsSet.add(new HashSet<RayMap>(rayMaps.subList(div * i, rayMaps.size())));
			}
		}
		end = System.currentTimeMillis();
		total += PrintStreamUtil.println(fileOut, "done", start, end);

		PrintStreamUtil.print(fileOut, "Building RCBVH Tree "
				+ (writeRayMapToRasterPostThreadBuild ? "and RayTracing " : "") + "- ");
		start = System.currentTimeMillis();
		BvhTree bvhTree = concurrentRcbvhBuilder.buildTree(triangles, rayMapsSet, numThreads,
				numReflections, writeRayMapToRasterPostThreadBuild);
		end = System.currentTimeMillis();
		total += PrintStreamUtil.println(fileOut, "done", start, end);

		PrintStreamUtil.println(fileOut, "Bvh Nodes: " + bvhTree.getTreeCollection().size());

		if (!writeRayMapToRasterPostThreadBuild)
		{
			PrintStreamUtil.print(fileOut, "Ray Tracing - ");
			start = System.currentTimeMillis();
			RayTraceUtil.writeRayMapToRaster(rayMaps);
			end = System.currentTimeMillis();
			total += PrintStreamUtil.println(fileOut, "done", start, end);
		}

		PrintStreamUtil.println(fileOut, "Total time: " + total + "ms");

		if (showRaster)
		{
			Window.showRaster();
		}

		return total;
	}
}
