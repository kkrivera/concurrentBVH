package bvh.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import triangle.Triangle;
import bvh.BvhNode;
import bvh.BvhTree;

public class DepthFirstBvhBuilder extends AbstractBvhBuilder
{
	@Override
	public BvhTree buildTree(Set<Triangle> triangles) throws Exception
	{
		BvhTree bvhTree = new BvhTree(buildBvhNode(triangles), new ArrayList<BvhNode>());

		recursiveTreeBuild(bvhTree.getParentBvhNode(), bvhTree);

		return bvhTree;
	}

	/**
	 * A recursive method to build the {@link BvhNode} children through
	 * recursion, going down the left side of the {@link BvhTree} first
	 * 
	 * @param bvhNode
	 *        The {@link BvhNode} for which children may be created
	 * @param bvhTree
	 *        The {@link BvhTree} containing all of the {@link BvhNode} children
	 * @throws Exception
	 */
	private void recursiveTreeBuild(BvhNode bvhNode, BvhTree bvhTree) throws Exception
	{
		if (nodeRequiresChildren(bvhNode))
		{
			buildChildren(bvhNode, bvhTree);

			// Traverse Left Side
			if (bvhNode.getLeftBvhNodeIndex() != null)
			{
				recursiveTreeBuild(((List<BvhNode>) bvhTree.getTreeCollection()).get(bvhNode
						.getLeftBvhNodeIndex()), bvhTree);
			}

			// Traverse Right Side
			if (bvhNode.getRightBvhNodeIndex() != null)
			{
				recursiveTreeBuild(((List<BvhNode>) bvhTree.getTreeCollection()).get(bvhNode
						.getRightBvhNodeIndex()), bvhTree);
			}
		}
	}
}
