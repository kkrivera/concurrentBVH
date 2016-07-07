package object3d.util;

import javax.vecmath.Matrix3f;
import javax.vecmath.Vector3f;

import matrix.util.Matrix3fUtil;
import object3d.Object3D;
import triangle.Triangle;
import triangle.util.TriangleUtil;
import vector.util.Vector3fUtil;

public class ObjectUtil
{
	// public static Object3D createCube(float x, float y, float z, float L,
	// String color,
	// float reflectivity)
	// {
	// return createBox(x, y, z, L, L, L, color, reflectivity);
	// }

	// public static Object3D createBox(float x, float y, float z, float xL,
	// float yL, float zL,
	// String color, float reflectivity)
	// {
	// Collection<Triangle> triangles = new ArrayList<Triangle>();
	//
	// float minX = x - xL / 2.0f;
	// float maxX = x + xL / 2.0f;
	//
	// float minY = y - yL / 2.0f;
	// float maxY = y + yL / 2.0f;
	//
	// float minZ = z - zL / 2.0f;
	// float maxZ = z + zL / 2.0f;
	//
	// // Positions
	// Vector3f frontLeftTop = new Vector3f(minX, maxY, minZ);
	// Vector3f backLeftTop = new Vector3f(minX, maxY, maxZ);
	// Vector3f frontRightTop = new Vector3f(maxX, maxY, minZ);
	// Vector3f backRightTop = new Vector3f(maxX, maxY, maxZ);
	//
	// Vector3f frontLeftBottom = new Vector3f(minX, minY, minZ);
	// Vector3f backLeftBottom = new Vector3f(minX, minY, maxZ);
	// Vector3f frontRightBottom = new Vector3f(maxX, minY, minZ);
	// Vector3f backRightBottom = new Vector3f(maxX, minY, maxZ);
	//
	// // Back
	// triangles.addAll(createRectangle(backRightTop, backRightBottom,
	// backLeftBottom,
	// backLeftTop, color, color, color, color, reflectivity));
	//
	// // Front
	// triangles.addAll(createRectangle(frontLeftTop, frontLeftBottom,
	// frontRightBottom,
	// frontRightTop, color, color, color, color, reflectivity));
	//
	// // Top
	// triangles.addAll(createRectangle(frontLeftTop, frontRightTop,
	// backRightTop, backLeftTop,
	// color, color, color, color, reflectivity));
	//
	// // Bottom
	// triangles.addAll(createRectangle(frontLeftBottom, backLeftBottom,
	// backRightBottom,
	// frontRightBottom, color, color, color, color, reflectivity));
	//
	// // Left
	// triangles.addAll(createRectangle(backLeftTop, backLeftBottom,
	// frontLeftBottom,
	// frontLeftTop, color, color, color, color, reflectivity));
	//
	// // Right
	// triangles.addAll(createRectangle(frontRightTop, frontRightBottom,
	// backRightBottom,
	// backRightTop, color, color, color, color, reflectivity));
	//
	// return new Object3D(triangles, new Vector3f(x, y, z));
	// }
	//
	// public static Object3D createSphere(float x, float y, float z, float r,
	// float detail,
	// String color, float reflectivity)
	// {
	// Collection<Triangle> triangles = new ArrayList<Triangle>();
	//
	// for (float theta = 0; theta < Math.PI; theta += detail)
	// {
	// for (float phi = 0; phi < Math.PI * 2; phi += detail)
	// {
	// triangles.add(new Triangle(MathUtil.sphericalToCartesian(r, theta +
	// detail, phi, x,
	// y, z), MathUtil.sphericalToCartesian(r, theta, phi, x, y, z), MathUtil
	// .sphericalToCartesian(r, theta + detail, phi + detail, x, y, z), color,
	// color, color, reflectivity));
	//
	// triangles.add(new Triangle(MathUtil.sphericalToCartesian(r, theta +
	// detail, phi
	// + detail, x, y, z), MathUtil.sphericalToCartesian(r, theta, phi, x, y,
	// z),
	// MathUtil.sphericalToCartesian(r, theta, phi + detail, x, y, z), color,
	// color, color, reflectivity));
	// }
	// }
	//
	// return new Object3D(triangles, new Vector3f(x, y, z));
	// }

	// public static Object3D createRoom(float xL, float yL, float zL, String
	// wallColor,
	// String ceilingColor, String floorColor, float wallReflectivity,
	// float ceilingReflectivity, float floorReflectivity)
	// {
	// float PI = (float) Math.PI;
	//
	// float depth = 1;
	// Object3D wallSide = ObjectUtil.createBox(0, 0, 0, zL, yL, depth,
	// wallColor,
	// wallReflectivity);
	// Object3D wallFront = ObjectUtil.createBox(0, 0, 0, xL, yL, depth,
	// wallColor,
	// wallReflectivity);
	//
	// Object3D ceiling = ObjectUtil.createBox(0, 0, 0, xL, zL, depth,
	// ceilingColor,
	// ceilingReflectivity);
	// Object3D floor = ObjectUtil
	// .createBox(0, 0, 0, xL, zL, depth, floorColor, floorReflectivity);
	//
	// Object3D wall_1 = wallSide.clone();
	// wall_1.rotateBy(0, PI / 2, 0);
	// ObjectUtil.translateTo(wall_1, (xL + depth) / 2.0f, 0, 0);
	//
	// Object3D wall_2 = wallSide.clone();
	// wall_2.rotateBy(0, PI / 2, 0);
	// ObjectUtil.translateTo(wall_2, -(xL + depth) / 2.0f, 0, 0);
	//
	// Object3D wall_3 = wallFront.clone();
	// ObjectUtil.translateTo(wall_3, 0, 0, -(zL + depth) / 2.0f);
	//
	// Object3D wall_4 = wallFront.clone();
	// ObjectUtil.translateTo(wall_4, 0, 0, (zL + depth) / 2.0f);
	//
	// ceiling.rotateBy(PI / 2, 0, 0);
	// ObjectUtil.translateTo(ceiling, 0, (yL + depth) / 2.0f, 0);
	//
	// floor.rotateBy(PI / 2, 0, 0);
	// ObjectUtil.translateTo(floor, 0, -(yL + depth) / 2.0f, 0);
	//
	// return ObjectUtil.makeCombinedObject3D(0, 0, 0, wall_1, wall_2, wall_3,
	// wall_4, ceiling,
	// floor);
	// }

	// public static Object3D createTable(float topLength, float topWidth, float
	// topThickness,
	// String topColor, float topReflectivity, float legLength, float
	// legThickness,
	// String legColor, float legReflectivity)
	// {
	// float PI = (float) Math.PI;
	//
	// Object3D tableTop = ObjectUtil.createBox(0, 0, 0, topLength, topWidth,
	// topThickness,
	// topColor, topReflectivity);
	// Object3D tableLeg = ObjectUtil.createBox(0, 0, 0, legThickness,
	// legLength, legThickness,
	// legColor, legReflectivity);
	//
	// tableTop.rotateBy(PI / 2, 0, 0);
	//
	// Object3D tableLeg_1 = tableLeg.clone();
	// ObjectUtil.translateTo(tableLeg_1, -topLength / 2.0f + legThickness,
	// -(legLength + topThickness) / 2.0F, -topWidth / 2.0f + legThickness);
	//
	// Object3D tableLeg_2 = tableLeg.clone();
	// ObjectUtil.translateTo(tableLeg_2, -topLength / 2.0f + legThickness,
	// -(legLength + topThickness) / 2.0F, topWidth / 2.0f - legThickness);
	//
	// Object3D tableLeg_3 = tableLeg.clone();
	// ObjectUtil.translateTo(tableLeg_3, topLength / 2.0f - legThickness,
	// -(legLength + topThickness) / 2.0F, -topWidth / 2.0f + legThickness);
	//
	// Object3D tableLeg_4 = tableLeg.clone();
	// ObjectUtil.translateTo(tableLeg_4, topLength / 2.0f - legThickness,
	// -(legLength + topThickness) / 2.0F, topWidth / 2.0f - legThickness);
	//
	// return ObjectUtil.makeCombinedObject3D(0, 0, 0, tableTop, tableLeg_1,
	// tableLeg_2,
	// tableLeg_3, tableLeg_4);
	// }

	// private static Collection<Triangle> createRectangle(Vector3f pos1,
	// Vector3f pos2,
	// Vector3f pos3, Vector3f pos4, String color1, String color2, String
	// color3,
	// String color4, float reflectivity)
	// {
	// // Positions should be fed in counter-clockwise
	//
	// Collection<Triangle> triangles = new ArrayList<Triangle>();
	//
	// triangles.add(new Triangle(new Vector3f(pos1), new Vector3f(pos2), new
	// Vector3f(pos3),
	// color1, color2, color3, reflectivity));
	// triangles.add(new Triangle(new Vector3f(pos1), new Vector3f(pos3), new
	// Vector3f(pos4),
	// color1, color3, color4, reflectivity));
	//
	// return triangles;
	// }

	public static void translateBy(Object3D object3d, float x, float y, float z) throws Exception
	{
		translateBy(object3d, new Vector3f(x, y, z));
	}

	public static void translateBy(Object3D object3d, Vector3f v) throws Exception
	{
		translateTo(object3d, Vector3fUtil.add(object3d.getCenterPoint(), v));
	}

	public static void translateTo(Object3D object, float x, float y, float z) throws Exception
	{
		translateTo(object, new Vector3f(x, y, z));
	}

	public static void translateTo(Object3D object, Vector3f translateVector) throws Exception
	{
		for (Triangle triangle : object.getTriangles())
		{
			for (int i = 1; i <= 3; i++)
			{
				TriangleUtil.setVertex(triangle, i,
						Vector3fUtil.add(TriangleUtil.getVertex(triangle, i), translateVector));
			}
		}
	}

	public static void rotateBy(Object3D object3d, Vector3f rotation, Vector3f rotationCenter)
			throws Exception
	{
		rotateBy(object3d, rotation.getX(), rotation.getY(), rotation.getZ(), rotationCenter);
	}

	public static void rotateTo(Object3D object3d, Vector3f rotation, Vector3f rotationCenter)
			throws Exception
	{
		rotateBy(object3d, Vector3fUtil.sub(rotation, object3d.getRotation()), rotationCenter);
	}

	/**
	 * Translates the center of the object to the center of rotation, rotates
	 * points, then puts it back
	 * 
	 * @param object3d
	 *        The {@link Object3D}
	 * @param x
	 *        The x rotation
	 * @param y
	 *        The y rotation
	 * @param z
	 *        The z rotation
	 * @param rotationCenter
	 *        The {@link Vector3f} representing the center of rotation
	 * @throws Exception
	 */
	public static void rotateBy(Object3D object3d, float x, float y, float z,
			Vector3f rotationCenter) throws Exception
	{
		Matrix3f xRotMatrix = new Matrix3f();
		xRotMatrix.rotX(x);

		Matrix3f yRotMatrix = new Matrix3f();
		yRotMatrix.rotY(y);

		Matrix3f zRotMatrix = new Matrix3f();
		zRotMatrix.rotZ(z);

		object3d.translateBy(Vector3fUtil.mult(rotationCenter, -1.0f));
		for (Triangle triangle : object3d.getTriangles())
		{
			for (int i = 1; i <= 3; i++)
			{
				TriangleUtil.setVertex(
						triangle,
						i,
						Matrix3fUtil.mult(
								zRotMatrix,
								Matrix3fUtil.mult(
										yRotMatrix,
										Matrix3fUtil.mult(xRotMatrix,
												TriangleUtil.getVertex(triangle, i)))));
			}

			triangle.setNormal(TriangleUtil.getNormal(triangle));
		}
		object3d.translateBy(rotationCenter);
	}

	public static void rotateVertexBy(Vector3f vertex, float x, float y, float z,
			Vector3f rotationCenter)
	{
		Matrix3f xRotMatrix = new Matrix3f();
		xRotMatrix.rotX(x);

		Matrix3f yRotMatrix = new Matrix3f();
		yRotMatrix.rotY(y);

		Matrix3f zRotMatrix = new Matrix3f();
		zRotMatrix.rotZ(z);

		rotateVertexBy(vertex, xRotMatrix, yRotMatrix, zRotMatrix, rotationCenter);
	}

	public static void rotateVertexBy(Vector3f vertex, Matrix3f xRotMatrix, Matrix3f yRotMatrix,
			Matrix3f zRotMatrix, Vector3f rotationCenter)
	{
		Vector3fUtil.addTo(vertex, Vector3fUtil.mult(rotationCenter, -1.0f));

		Vector3fUtil.set(
				vertex,
				Matrix3fUtil.mult(zRotMatrix,
						Matrix3fUtil.mult(yRotMatrix, Matrix3fUtil.mult(xRotMatrix, vertex))));

		Vector3fUtil.addTo(vertex, rotationCenter);
	}

	// public static Object3D makeCombinedObject3D(float x, float y, float z,
	// Object3D... objs)
	// {
	// return makeCombinedObject3D(new Vector3f(x, y, z), objs);
	// }

	// public static Object3D makeCombinedObject3D(Vector3f objCenter,
	// Object3D... objs)
	// {
	// Object3D object3d = new Object3D();
	//
	// for (int i = 0; i < objs.length; i++)
	// {
	// object3d.getTriangles().addAll(new
	// ArrayList<Triangle>(objs[i].getTriangles()));
	// }
	//
	// object3d.setCenterPoint(VoxelUtil.getVoxelCenter(VoxelUtil.getVoxel(object3d.getTriangles())));
	//
	// object3d.translateTo(objCenter);
	//
	// return object3d;
	// }
}
