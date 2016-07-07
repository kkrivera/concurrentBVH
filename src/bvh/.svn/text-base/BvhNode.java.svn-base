package bvh;

import java.util.Set;

import triangle.Triangle;
import voxel.Voxel;

public class BvhNode
{
	private Set<Triangle> triangles;
	private Voxel voxel;
	private Integer leftBvhNodeIndex;
	private Integer rightBvhNodeIndex;
	private Boolean hasChildren;
	private Boolean isLeaf;

	/**
	 * Default Constructor for creating a {@link BvhNode}
	 * 
	 * @param triangles
	 *        The {@link Triangle}s that are within the {@link BvhNode}
	 * @param voxel
	 *        The {@link Voxel} containing the triangles
	 */
	public BvhNode(Set<Triangle> triangles, Voxel voxel)
	{
		this.triangles = triangles;
		this.voxel = voxel;
		this.leftBvhNodeIndex = null;
		this.rightBvhNodeIndex = null;
		hasChildren = false;
		isLeaf = false;
	}

	/**
	 * Gets the triangles field
	 * 
	 * @return A {@link Set} of {@link Triangle}s
	 */
	public Set<Triangle> getTriangles()
	{
		return triangles;
	}

	/**
	 * Sets the triangles field
	 * 
	 * @param triangles
	 *        A {@link Set} of {@link Triangle}s
	 */
	public void setTriangles(Set<Triangle> triangles)
	{
		this.triangles = triangles;
	}

	/**
	 * Gets the voxel field
	 * 
	 * @return A {@link Voxel}
	 */
	public Voxel getVoxel()
	{
		return voxel;
	}

	/**
	 * Sets the voxel field
	 * 
	 * @param voxel
	 *        A {@link Voxel}
	 */
	public void setVoxel(Voxel voxel)
	{
		this.voxel = voxel;
	}

	/**
	 * Gets the leftBvgNodeIndex field
	 * 
	 * @return An {@link Integer}
	 */
	public Integer getLeftBvhNodeIndex()
	{
		return leftBvhNodeIndex;
	}

	/**
	 * Sets the leftBvhNodeIndex field
	 * 
	 * @param leftBvhNodeIndex
	 *        An {@link Integer}
	 */
	public void setLeftBvhNodeIndex(Integer leftBvhNodeIndex)
	{
		this.leftBvhNodeIndex = leftBvhNodeIndex;
	}

	/**
	 * Gets the rightBvhNodeIndex field
	 * 
	 * @return An {@link Integer}
	 */
	public Integer getRightBvhNodeIndex()
	{
		return rightBvhNodeIndex;
	}

	/**
	 * Sets the rightBvhNodeIndex field
	 * 
	 * @param rightBvhNodeIndex
	 *        An {@link Integer}
	 */
	public void setRightBvhNodeIndex(Integer rightBvhNodeIndex)
	{
		this.rightBvhNodeIndex = rightBvhNodeIndex;
	}

	/**
	 * @return the hasChildren
	 */
	public Boolean getHasChildren()
	{
		return hasChildren;
	}

	/**
	 * @param hasChildren
	 *        the hasChildren to set
	 */
	public void setHasChildren(Boolean hasChildren)
	{
		this.hasChildren = hasChildren;
	}

	/**
	 * @return the isLeaf
	 */
	public Boolean getIsLeaf()
	{
		return isLeaf;
	}

	/**
	 * @param isLeaf
	 *        the isLeaf to set
	 */
	public void setIsLeaf(Boolean isLeaf)
	{
		this.isLeaf = isLeaf;
	}

}
