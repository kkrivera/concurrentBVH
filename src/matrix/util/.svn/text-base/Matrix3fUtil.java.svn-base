package matrix.util;

import javax.vecmath.Matrix3f;
import javax.vecmath.Vector3f;

public class Matrix3fUtil
{
	public static Matrix3f mult(Matrix3f m1, Matrix3f m2)
	{
		Matrix3f m = new Matrix3f(m1);
		m.mul(m2);

		return m;
	}

	public static Vector3f mult(Matrix3f m1, Vector3f v1)
	{
		Matrix3f m = new Matrix3f(m1);
		Matrix3f m2 = new Matrix3f(v1.getX(), 0, 0, v1.getY(), 0, 0, v1.getZ(), 0, 0);
		m.mul(m2);

		return new Vector3f(m.m00, m.m10, m.m20);
	}
}
