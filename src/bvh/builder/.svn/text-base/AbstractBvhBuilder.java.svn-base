package bvh.builder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import triangle.Triangle;
import voxel.util.VoxelUtil;
import bvh.BvhNode;
import bvh.BvhTree;
import bvh.util.BvhNodeUtil;

public abstract class AbstractBvhBuilder implements IBvhBuilder
{
	@Override
	public void buildChildren(BvhNode bvhNode, BvhTree bvhTree) throws Exception
	{
		Set<Triangle> leftTriangleSubset = new HashSet<Triangle>();
		Set<Triangle> rightTriangleSubset = new HashSet<Triangle>();

		if (bvhNode.getTriangles().size() > 1)
		{
			// Sets the left and right triangle subsets
			BvhNodeUtil.getBestSahSplit(bvhNode, leftTriangleSubset, rightTriangleSubset);

			if (leftTriangleSubset.size() == 0 && rightTriangleSubset.size() == 0)
			{
				List<Triangle> trianglesList = new ArrayList<Triangle>(bvhNode.getTriangles());

				int halfSize = trianglesList.size() / 2;
				leftTriangleSubset.addAll(trianglesList.subList(0, halfSize));
				rightTriangleSubset.addAll(trianglesList.subList(halfSize, trianglesList.size()));
			}
		}
		else if (!bvhNode.getTriangles().isEmpty())
		{
			bvhNode.setIsLeaf(true);
		}

		synchronized (bvhTree)
		{
			// Left Child
			if (leftTriangleSubset.size() > 0)
			{
				bvhNode.setLeftBvhNodeIndex(bvhTree.getTreeCollection().size());
				bvhTree.getTreeCollection().add(buildBvhNode(leftTriangleSubset));
			}

			// Right Child
			if (rightTriangleSubset.size() > 0)
			{
				bvhNode.setRightBvhNodeIndex(bvhTree.getTreeCollection().size());
				bvhTree.getTreeCollection().add(buildBvhNode(rightTriangleSubset));
			}
		}

		bvhNode.setHasChildren(true);
	}

	@Override
	public BvhNode buildBvhNode(Set<Triangle> triangles) throws Exception
	{
		return new BvhNode(triangles, VoxelUtil.getVoxel(triangles));
	}

	@Override
	public Boolean nodeRequiresChildren(BvhNode bvhNode)
	{
		return bvhNode.getTriangles().iterator().next() != null
				&& bvhNode.getLeftBvhNodeIndex() == null && bvhNode.getRightBvhNodeIndex() == null;
	}

}
