package modelloader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.vecmath.Vector3f;

import math.util.MathUtil;
import triangle.Triangle;

import com.obj.Face;
import com.obj.Group;
import com.obj.TextureCoordinate;
import com.obj.Vertex;
import com.obj.WavefrontObject;

public class ModelLoaderUtil
{
	public static Map<String, BufferedImage> textures;

	/**
	 * A model loader which uses the {@link WavefrontObject} to be able to
	 * perform this task
	 * 
	 * @param modelPath
	 *        The directory of where the model is located
	 * @param modelFileName
	 *        The name of the model file
	 * @return Set of {@link Triangle} objects
	 */
	public static Set<Triangle> loadModel(String modelFullPath)
	{
		String modelPath = modelFullPath.substring(0, modelFullPath.lastIndexOf('\\') + 1);
		String modelFileName = modelFullPath.substring(modelFullPath.lastIndexOf('\\') + 1);

		System.out.println(modelPath + "--" + modelFileName);

		WavefrontObject obj = new WavefrontObject(modelPath + modelFileName);

		Set<Triangle> triangles = new HashSet<Triangle>();

		textures = new HashMap<String, BufferedImage>();
		for (Group group : obj.getGroups())
		{
			BufferedImage texImg = null;
			Integer width = null;
			Integer height = null;

			try
			{
				if (group.getMaterial() != null && group.getMaterial().texName != null)
				{
					texImg = ImageIO.read(new File(modelPath + group.getMaterial().texName));

					textures.put(group.getMaterial().texName, texImg);

					width = group.getMaterial().getTexture().getWidth() - 1;
					height = group.getMaterial().getTexture().getHeight() - 1;
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			for (Face face : group.getFaces())
			{
				Vertex v1 = face.getVertices()[0];
				Vertex v2 = face.getVertices()[1];
				Vertex v3 = face.getVertices()[2];

				Vertex n1 = face.getNormals()[0];
				Vertex n2 = face.getNormals()[1];
				Vertex n3 = face.getNormals()[2];

				Triangle triangle = new Triangle(new Vector3f(v1.getX(), v1.getY(), v1.getZ()),
						new Vector3f(v2.getX(), v2.getY(), v2.getZ()), new Vector3f(v3.getX(),
								v3.getY(), v3.getZ()),
						new Vector3f(n1.getX(), n1.getY(), n1.getZ()), new Vector3f(n2.getX(),
								n2.getY(), n2.getZ()),
						new Vector3f(n3.getX(), n3.getY(), n3.getZ()));

				if (group.getMaterial() != null)
				{
					Vertex kA = group.getMaterial().getKa();
					Vertex kD = group.getMaterial().getKd();
					Vertex kS = group.getMaterial().getKs();

					triangle.setkA(new Vector3f(kA.getX(), kA.getY(), kA.getZ()));
					triangle.setkD(new Vector3f(kD.getX(), kD.getY(), kD.getZ()));
					triangle.setkS(new Vector3f(kS.getX(), kS.getY(), kS.getZ()));

					triangle.setShininess(group.getMaterial().getShininess());
				}

				if (texImg != null)
				{
					triangle.setTextureName(group.getMaterial().texName);

					TextureCoordinate t1 = face.getTextures()[0];
					TextureCoordinate t2 = face.getTextures()[1];
					TextureCoordinate t3 = face.getTextures()[2];

					triangle.setTexPos1(new Vector3f(t1.getU(), t1.getV(), t1.getW()));
					triangle.setTexPos2(new Vector3f(t2.getU(), t2.getV(), t2.getW()));
					triangle.setTexPos3(new Vector3f(t3.getU(), t3.getV(), t3.getW()));

					TEXTURE_TYPE texType = TEXTURE_TYPE.WRAP;
					Boolean mirrorU = false;
					Boolean mirrorV = false;
					triangle.setTexColor1(getHexColor(texImg, width, height,
							getConvertedTextCoordinate(t1.getU(), texType, mirrorU),
							getConvertedTextCoordinate(t1.getV(), texType, mirrorV)));
					triangle.setTexColor2(getHexColor(texImg, width, height,
							getConvertedTextCoordinate(t2.getU(), texType, mirrorU),
							getConvertedTextCoordinate(t2.getV(), texType, mirrorV)));
					triangle.setTexColor3(getHexColor(texImg, width, height,
							getConvertedTextCoordinate(t3.getU(), texType, mirrorU),
							getConvertedTextCoordinate(t3.getV(), texType, mirrorV)));
				}

				triangles.add(triangle);
			}
		}

		return triangles;
	}

	/**
	 * Gets the color of a texture at a given location
	 * 
	 * @param texImg
	 *        A {@link BufferedImage}
	 * @param width
	 *        The width of the {@link BufferedImage}
	 * @param height
	 *        The height of the {@link BufferedImage}
	 * @param tU
	 *        The u texture coordinate
	 * @param tV
	 *        The v texture coordinate
	 * @return A hex color of the texture
	 */
	public static String getHexColor(BufferedImage texImg, int width, int height, float tU, float tV)
	{
		String texColor = MathUtil.d2h(texImg.getRGB((int) (width * tU), (int) (height * tV)), 6);

		if (texColor.length() > 6)
		{
			texColor = texColor.substring(texColor.length() - 6);
		}

		return texColor.toUpperCase();
	}

	/**
	 * Unwraps texture coordinates if they are negative or greater than 1
	 * 
	 * @param coord
	 *        The u or v texture coordinate
	 * @return The unwrapped texture
	 */
	public static float getConvertedTextCoordinate(float coord, TEXTURE_TYPE texType,
			Boolean texMirror)
	{
		while (coord > 1.0f)
		{
			if (texType == TEXTURE_TYPE.WRAP)
			{
				coord -= 1.0f;
			}
			else if (texType == TEXTURE_TYPE.CLAMP)
			{
				coord = 1.0f;
			}

		}

		while (coord < 0.0f)
		{
			if (texType == TEXTURE_TYPE.WRAP)
			{
				coord += 1.0f;
			}
			else if (texType == TEXTURE_TYPE.CLAMP)
			{
				coord = 0.0f;
			}
		}

		if (texMirror)
		{
			coord = 1 - coord;
		}

		return coord;
	}

	public enum TEXTURE_TYPE
	{
		WRAP, CLAMP
	};
}
