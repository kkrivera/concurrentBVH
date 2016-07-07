package bvh.builder;

import java.util.Set;

import triangle.Triangle;
import bvh.BvhNode;
import bvh.BvhTree;

/**
 * An interface class for building Bvh Trees
 * 
 * @author kkrivera
 */
public interface IBvhBuilder
{
	public BvhTree buildTree(Set<Triangle> triangles) throws Exception;

	public void buildChildren(BvhNode bvhNode, BvhTree bvhTree) throws Exception;

	public BvhNode buildBvhNode(Set<Triangle> triangles) throws Exception;

	public Boolean nodeRequiresChildren(BvhNode bvhNode);
}
