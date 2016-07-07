package object3d;

import java.util.ArrayList;
import java.util.Collection;

import javax.vecmath.Vector3f;

import object3d.util.ObjectUtil;
import triangle.Triangle;
import vector.util.Vector3fUtil;

public class Object3D
{
	private Collection<Triangle> triangles;
	private Vector3f centerPoint;
	private Vector3f rotation;

	/**
	 * Default Constructor
	 */
	public Object3D()
	{
		this.triangles = new ArrayList<Triangle>();
		this.centerPoint = new Vector3f(0, 0, 0);
		this.rotation = new Vector3f(0, 0, 0);
	}

	/**
	 * Maximal Constructor
	 * 
	 * @param triangles
	 *        The {@link Triangle}s in an {@link Object3D}
	 * @param centerPoint
	 *        The {@link Vector3f} which represents the center point of the
	 *        object
	 */
	public Object3D(Collection<Triangle> triangles, Vector3f centerPoint)
	{
		this.triangles = triangles;
		this.centerPoint = centerPoint;
		this.rotation = new Vector3f(0, 0, 0);
	}

	/**
	 * Gets all {@link Triangle}s
	 * 
	 * @return A Collection of {@link Triangle}s
	 */
	public Collection<Triangle> getTriangles()
	{
		return triangles;
	}

	/**
	 * Sets all of the {@link Triangle}s on a {@link Object3D}
	 * 
	 * @param triangles
	 *        The {@link Triangle}s being set on the object
	 */
	public void setTriangles(Collection<Triangle> triangles)
	{
		this.triangles = triangles;
	}

	/**
	 * Gets the {@link Vector3f} which is the center point of the
	 * {@link Object3D}
	 * 
	 * @return A {@link Vector3f}
	 */
	public Vector3f getCenterPoint()
	{
		return centerPoint;
	}

	/**
	 * Sets the center point of the {@link Object3D}
	 * 
	 * @param centerPoint
	 *        The center point, a {@link Vector3f}
	 */
	public void setCenterPoint(Vector3f centerPoint)
	{
		this.centerPoint = centerPoint;
	}

	/**
	 * Gets a {@link Vector3f} representing the current rotation of the
	 * {@link Object3D}
	 * 
	 * @return A {@link Vector3f}
	 */
	public Vector3f getRotation()
	{
		return rotation;
	}

	/**
	 * Sets the current rotation of the object
	 * 
	 * @param rotation
	 *        A {@link Vector3f}
	 */
	public void setRotation(Vector3f rotation)
	{
		this.rotation = rotation;
	}

	// /**
	// * Clones an {@link Object3D}
	// */
	// public Object3D clone()
	// {
	// Object3D obj = new Object3D();
	//
	// for (Triangle triangle : this.triangles)
	// obj.triangles.add(triangle.clone());
	//
	// obj.centerPoint = new Vector3f(this.centerPoint);
	//
	// return obj;
	// }

	/**
	 * Translates the {@link Object3D} to the given coordinates
	 * 
	 * @param x
	 *        A float
	 * @param y
	 *        A float
	 * @param z
	 *        A float
	 * @throws Exception
	 */
	public void translateTo(float x, float y, float z) throws Exception
	{
		this.translateTo(new Vector3f(x, y, z));
	}

	/**
	 * Translates the {@link Object3D} to the Vector position
	 * 
	 * @param v
	 *        The {@link Vector3f}
	 * @throws Exception
	 */
	public void translateTo(Vector3f v) throws Exception
	{
		ObjectUtil.translateTo(this, Vector3fUtil.sub(v, this.centerPoint));
		this.centerPoint = v;
	}

	/**
	 * Translates the {@link Object3D} by a vector
	 * 
	 * @param x
	 *        The {@link Float} representing the x portion of the vector
	 * @param y
	 *        The {@link Float} representing the y portion of the vector
	 * @param z
	 *        The {@link Float} representing the y portion of the vector
	 * @throws Exception
	 */
	public void translateBy(float x, float y, float z) throws Exception
	{
		this.translateBy(new Vector3f(x, y, z));
	}

	/**
	 * 
	 * @param v
	 * @throws Exception
	 */
	public void translateBy(Vector3f v) throws Exception
	{
		ObjectUtil.translateBy(this, v);
		this.centerPoint = Vector3fUtil.add(this.centerPoint, v);
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @throws Exception
	 */
	public void rotateBy(float x, float y, float z) throws Exception
	{
		this.rotateBy(new Vector3f(x, y, z));
	}

	/**
	 * 
	 * @param rotation
	 * @throws Exception
	 */
	public void rotateBy(Vector3f rotation) throws Exception
	{
		this.rotateByAbout(rotation, this.centerPoint);
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @throws Exception
	 */
	public void rotateTo(float x, float y, float z) throws Exception
	{
		this.rotateTo(new Vector3f(x, y, z));
	}

	/**
	 * 
	 * @param rotation
	 * @throws Exception
	 */
	public void rotateTo(Vector3f rotation) throws Exception
	{
		this.rotateToAbout(rotation, this.centerPoint);
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param rotationCenter
	 * @throws Exception
	 */
	public void rotateByAbout(float x, float y, float z, Vector3f rotationCenter) throws Exception
	{
		this.rotateByAbout(new Vector3f(x, y, z), rotationCenter);
	}

	/**
	 * 
	 * @param rotation
	 * @param rotationCenter
	 * @throws Exception
	 */
	public void rotateByAbout(Vector3f rotation, Vector3f rotationCenter) throws Exception
	{
		ObjectUtil
				.rotateBy(this, rotation.getX(), rotation.getY(), rotation.getZ(), rotationCenter);
		Vector3fUtil.addTo(this.rotation, rotation);
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param rotationCenter
	 * @throws Exception
	 */
	public void rotateToAbout(float x, float y, float z, Vector3f rotationCenter) throws Exception
	{
		this.rotateToAbout(new Vector3f(x, y, z), rotationCenter);
	}

	/**
	 * 
	 * @param rotation
	 * @param rotationCenter
	 * @throws Exception
	 */
	public void rotateToAbout(Vector3f rotation, Vector3f rotationCenter) throws Exception
	{
		ObjectUtil.rotateTo(this, rotation, rotationCenter);
		this.rotation = rotation;
	}
}
