package vector.util;

import javax.vecmath.Vector3f;

public class Vector3fUtil
{
	public static void set(Vector3f v1, Vector3f v2)
	{
		v1.setX(v2.getX());
		v1.setY(v2.getY());
		v1.setZ(v2.getZ());
	}

	public static Vector3f add(Vector3f v1, Vector3f... v2)
	{
		Vector3f v = new Vector3f(v1);
		return addTo(v, v2);
	}

	public static Vector3f addTo(Vector3f v1, Vector3f... v2)
	{
		for (int i = 0; i < v2.length; i++)
			v1.add(v2[i]);

		return v1;
	}

	public static Vector3f sub(Vector3f v1, Vector3f... v2)
	{
		Vector3f v = new Vector3f(v1);
		return subFrom(v, v2);
	}

	public static Vector3f subFrom(Vector3f v1, Vector3f... v2)
	{
		for (int i = 0; i < v2.length; i++)
			v1.sub(v2[i]);

		return v1;
	}

	public static Vector3f mult(Vector3f v1, float val)
	{
		Vector3f v = new Vector3f(v1);
		v.scale(val);
		return v;
	}

	public static Vector3f mult(Vector3f v1, Vector3f v2)
	{
		Vector3f v = new Vector3f(v1);
		v.setX(v.getX() * v2.getX());
		v.setY(v.getY() * v2.getY());
		v.setZ(v.getZ() * v2.getZ());
		return v;
	}

	public static Vector3f norm(Vector3f v)
	{
		v.normalize();
		return v;
	}

	public static Vector3f cross(Vector3f v1, Vector3f v2)
	{
		Vector3f v = new Vector3f();
		v.cross(v1, v2);
		// v.scale(-1);
		return v;
	}

	public static float dot(Vector3f v1, Vector3f v2)
	{
		return v1.dot(v2);
	}

	public static float dist(Vector3f v1, Vector3f v2)
	{
		return mag(sub(v2, v1));
	}

	public static float mag(Vector3f v)
	{
		return (float) Math.sqrt(Math.pow(v.getX(), 2) + Math.pow(v.getY(), 2)
				+ Math.pow(v.getZ(), 2));
	}

}
