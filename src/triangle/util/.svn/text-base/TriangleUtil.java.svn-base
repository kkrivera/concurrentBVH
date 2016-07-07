package triangle.util;

import java.awt.image.BufferedImage;

import javax.vecmath.Vector3f;

import math.util.MathUtil;
import modelloader.ModelLoaderUtil;
import triangle.Triangle;
import vector.util.Vector3fUtil;
import barycentric.BarycentricValues;

public class TriangleUtil
{
	/**
	 * Method to get a vertex given a vertex number
	 * 
	 * @param triangle
	 *        The {@link Triangle} from which the vertex is being retrieved
	 * @param i
	 *        The {@link Integer} representing the vertex number
	 * @return A {@link Vector3f} representing the vertex
	 * @throws Exception
	 *         When the user attempts to retrieve a vertex outside of the
	 *         {@link Triangle}'s bounds
	 */
	public static Vector3f getVertex(Triangle triangle, Integer i) throws Exception
	{
		if (i > 3 || i < 1)
		{
			throw new Exception("Index out of bounds");
		}

		Vector3f vertex = triangle.getVertex3();
		if (i == 1)
		{
			vertex = triangle.getVertex1();
		}
		else if (i == 2)
		{
			vertex = triangle.getVertex2();
		}

		return vertex;
	}

	/**
	 * Sets a vertex on a {@link Triangle}
	 * 
	 * @param triangle
	 *        The {@link Triangle} having a vertex set
	 * @param i
	 *        The index of the vertex
	 * @param vertex
	 *        The {@link Vector3f} being set on the {@link Triangle}
	 * @throws Exception
	 *         When the index is out of range
	 */
	public static void setVertex(Triangle triangle, Integer i, Vector3f vertex) throws Exception
	{
		if (i > 3 || i < 1)
		{
			throw new Exception("Index out of bounds");
		}

		if (i == 1)
		{
			triangle.setVertex1(vertex);
		}
		else if (i == 2)
		{
			triangle.setVertex2(vertex);
		}
		else if (i == 3)
		{
			triangle.setVertex3(vertex);
		}
	}

	/**
	 * Produces a normal for a {@link Triangle}
	 * 
	 * @param triangle
	 *        The {@link Triangle} for which the normal {@link Vector3f} is
	 *        being generated
	 * 
	 * @return A {@link Vector3f} representing the normal to the
	 *         {@link Triangle}
	 */
	public static Vector3f getNormal(Triangle triangle)
	{
		return Vector3fUtil.norm(Vector3fUtil.cross(
				Vector3fUtil.sub(triangle.getVertex3(), triangle.getVertex1()),
				Vector3fUtil.sub(triangle.getVertex2(), triangle.getVertex1())));
	}

	/**
	 * Gets an interpolated color based off of the {@link BarycentricValues}
	 * 
	 * @param triangle
	 *        The {@link Triangle} within which we are interpolating the color
	 * @param baryValues
	 *        The {@link BarycentricValues} of the intersection point
	 * @return The decimal colors RGB as {@link Vector3f}
	 */
	public static Vector3f getColorInDec(Triangle triangle, BarycentricValues baryValues)
	{
		// return new Vector3f(Integer.parseInt(triangle.getTexColor1(), 16) *
		// baryValues.getGamma(),
		// Integer.parseInt(triangle.getTexColor2(), 16) *
		// baryValues.getAlpha(),
		// Integer.parseInt(triangle.getTexColor3(), 16) *
		// baryValues.getBeta());

		String vertexColor1R = triangle.getTexColor1().substring(0, 2);
		String vertexColor1G = triangle.getTexColor1().substring(2, 4);
		String vertexColor1B = triangle.getTexColor1().substring(4);

		String vertexColor2R = triangle.getTexColor2().substring(0, 2);
		String vertexColor2G = triangle.getTexColor2().substring(2, 4);
		String vertexColor2B = triangle.getTexColor2().substring(4);

		String vertexColor3R = triangle.getTexColor3().substring(0, 2);
		String vertexColor3G = triangle.getTexColor3().substring(2, 4);
		String vertexColor3B = triangle.getTexColor3().substring(4);

		return new Vector3f((float) (Math.floor(MathUtil.h2d(vertexColor1R) * baryValues.getGamma()
				+ MathUtil.h2d(vertexColor2R) * baryValues.getAlpha() + MathUtil.h2d(vertexColor3R)
				* baryValues.getBeta())), (float) (Math.floor(MathUtil.h2d(vertexColor1G)
				* baryValues.getGamma() + MathUtil.h2d(vertexColor2G) * baryValues.getAlpha()
				+ MathUtil.h2d(vertexColor3G) * baryValues.getBeta())),
				(float) (Math.floor(MathUtil.h2d(vertexColor1B) * baryValues.getGamma()
						+ MathUtil.h2d(vertexColor2B) * baryValues.getAlpha()
						+ MathUtil.h2d(vertexColor3B) * baryValues.getBeta())));

	}

	public static Vector3f getAverageTextureColor(Triangle triangle, Vector3f minTexPos,
			Vector3f maxTexPos)
	{
		BufferedImage texture = ModelLoaderUtil.textures.get(triangle.getTextureName());

		// int bytesPerPixel = this->materialPointer->tex->bytesPerPixel;
		//
		// //texPosMax->x = 1.0-texPosMin->x;
		// //texPosMin->x = 1.0-texPosMax->x;
		//
		// texPosMax->y = 1.0-texPosMax->y;
		// texPosMin->y = 1.0-texPosMin->y;
		//
		// int minPosX = (int)(texPosMin->x *
		// this->materialPointer->tex->width);
		// int minPosY = (int)(texPosMin->y *
		// this->materialPointer->tex->height);
		//
		// int maxPosX = (int)(texPosMax->x *
		// this->materialPointer->tex->width);
		// int maxPosY = (int)(texPosMax->y *
		// this->materialPointer->tex->height);
		//
		// if (maxPosY < minPosY)
		// {
		// float temp = maxPosY;
		// maxPosY = minPosY;
		// minPosY = temp;
		// }
		//
		// if (maxPosX < minPosX)
		// {
		// float temp = maxPosX;
		// maxPosX = minPosX;
		// minPosX = temp;
		// }
		//
		// if (minPosX < 0)
		// minPosX = 0;
		//
		// if (minPosY < 0)
		// minPosY = 0;
		//
		// if (maxPosX > this->materialPointer->tex->width - 1)
		// maxPosX = this->materialPointer->tex->width - 1;
		//
		// if (maxPosY > this->materialPointer->tex->height - 1)
		// maxPosY = this->materialPointer->tex->height - 1;
		//
		Vector3f averageColorVec = new Vector3f(0, 0, 0);
		// int count = 0;
		//
		// for (int i = minPosX; i <= maxPosX; i++)
		// {
		// for (int j = minPosY; j <= maxPosY; j++)
		// {
		// int index = (j * this->materialPointer->tex->height +
		// i)*bytesPerPixel;
		// averageColorVec->x += (float)this->materialPointer->tex->data[index];
		// averageColorVec->y += (float)this->materialPointer->tex->data[index +
		// 1];
		// averageColorVec->z += (float)this->materialPointer->tex->data[index +
		// 2];
		// count++;
		// }
		// }
		//
		// *averageColorVec /= ((float)count * 255.0);

		return averageColorVec;
	}
}
