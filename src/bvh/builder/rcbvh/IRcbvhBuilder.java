package bvh.builder.rcbvh;

import java.util.Set;

import rayMap.RayMap;
import triangle.Triangle;
import bvh.BvhTree;
import bvh.builder.IBvhBuilder;

public interface IRcbvhBuilder extends IBvhBuilder
{
	/**
	 * Builds a {@link BvhTree} from a set of triangles and a {@link Set} of
	 * {@link RayMap}s
	 * 
	 * @param triangles
	 *        The {@link Triangle}s in the {@link BvhTree}
	 * @param rayMaps
	 *        The {@link RayMap}s interesecting the {@link BvhTree}
	 * @param numReflections
	 *        The number of reflections for the current model
	 * @return A partially built {@link BvhTree}
	 * @throws Exception
	 */
	public BvhTree buildTree(Set<Triangle> triangles, Set<RayMap> rayMaps, Integer numReflections)
			throws Exception;
}
