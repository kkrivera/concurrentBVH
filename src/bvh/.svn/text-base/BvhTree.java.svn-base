package bvh;

import java.util.Collection;
import java.util.Collections;

/**
 * A class object which has a parent {@link BvhNode} and the {@link Collections}
 * of children {@link BvhNode}s
 * 
 * @author kkrivera
 */
public class BvhTree
{
	private BvhNode parentBvhNode;
	private Collection<BvhNode> treeCollection;

	/**
	 * Minimal Constructor for {@link BvhTree}
	 * 
	 * @param tree
	 *        The {@link Collection} containing the children {@link BvhNode}s
	 */
	public BvhTree(Collection<BvhNode> tree)
	{
		this.parentBvhNode = null;
		this.treeCollection = tree;
	}

	/**
	 * Maximal Constructor for {@link BvhTree}
	 * 
	 * @param parentBvhNode
	 *        The parent {@link BvhNode}
	 * @param tree
	 *        The {@link Collection} containing the children {@link BvhNode}s
	 */
	public BvhTree(BvhNode parentBvhNode, Collection<BvhNode> tree)
	{
		this.parentBvhNode = parentBvhNode;
		this.treeCollection = tree;
		this.treeCollection.add(parentBvhNode);
	}

	/**
	 * Gets the parentBvhNode field
	 * 
	 * @return the parentBvhNode
	 */
	public BvhNode getParentBvhNode()
	{
		return parentBvhNode;
	}

	/**
	 * Sets the parentBvhNode field
	 * 
	 * @param parentBvhNode
	 *        the parentBvhNode to set
	 */
	public void setParentBvhNode(BvhNode parentBvhNode)
	{
		this.parentBvhNode = parentBvhNode;
		this.treeCollection.clear();
		this.treeCollection.add(parentBvhNode);
	}

	/**
	 * Gets the treeCollection field
	 * 
	 * @return the treeCollection
	 */
	public Collection<BvhNode> getTreeCollection()
	{
		return treeCollection;
	}

	/**
	 * Sets the treeCollection field
	 * 
	 * @param treeCollection
	 *        the treeCollection to set
	 */
	public void setTreeCollection(Collection<BvhNode> treeCollection)
	{
		this.treeCollection = treeCollection;
	}

}
