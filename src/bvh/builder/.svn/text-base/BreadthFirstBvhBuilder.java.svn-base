package bvh.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import triangle.Triangle;
import bvh.BvhNode;
import bvh.BvhTree;

public class BreadthFirstBvhBuilder extends AbstractBvhBuilder
{
	@Override
	public BvhTree buildTree(Set<Triangle> triangles) throws Exception
	{
		BvhTree bvhTree = new BvhTree(buildBvhNode(triangles), new ArrayList<BvhNode>());

		List<BvhNode> tree = (List<BvhNode>) bvhTree.getTreeCollection();
		for (int i = 0; i < tree.size(); i++)
		{
			BvhNode currentNode = tree.get(i);

			// Adds children to the List of tree bvh nodes
			if (nodeRequiresChildren(currentNode))
			{
				buildChildren(currentNode, bvhTree);
			}
		}

		return bvhTree;
	}

}
