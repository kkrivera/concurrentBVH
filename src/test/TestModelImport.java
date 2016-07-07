package test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.vecmath.Vector3f;

import triangle.Triangle;

import com.obj.Face;
import com.obj.Group;
import com.obj.TextureCoordinate;
import com.obj.Vertex;
import com.obj.WavefrontObject;

public class TestModelImport
{
	public static void main(String[] args)
	{
		importModel();
	}

	public static void importModel()
	{
		String fileName = new String();
		String modelPath = new String();

		// LegoMan
		modelPath = "models\\legoMan\\";
		fileName = "LegoMan.obj";

		// Bunny
		// fileName = "models\\bunny\\bunny.obj";

		WavefrontObject obj = new WavefrontObject(modelPath + fileName);

		Set<Triangle> triangles = new HashSet<Triangle>();

		for (Group group : obj.getGroups())
		{
			BufferedImage texImg = null;

			try
			{
				if (group.getMaterial() != null && group.getMaterial().texName != null)
				{
					texImg = ImageIO.read(new File(modelPath + group.getMaterial().texName));
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
								v3.getY(), v3.getZ()));

				if (texImg != null)
				{
					TextureCoordinate t1 = face.getTextures()[0];
					TextureCoordinate t2 = face.getTextures()[1];
					TextureCoordinate t3 = face.getTextures()[2];

					int width = texImg.getWidth() - 1;
					int height = texImg.getHeight() - 1;

					triangle.setColor1(Integer.toHexString(
							texImg.getRGB((int) (width * t1.getU()), (int) (height * t1.getV())))
							.substring(2));
					triangle.setColor2(Integer.toHexString(
							texImg.getRGB((int) (width * t2.getU()), (int) (height * t2.getV())))
							.substring(2));
					triangle.setColor3(Integer.toHexString(
							texImg.getRGB((int) (width * t3.getU()), (int) (height * t3.getV())))
							.substring(2));
				}

				triangles.add(triangle);
			}
		}
	}
}
