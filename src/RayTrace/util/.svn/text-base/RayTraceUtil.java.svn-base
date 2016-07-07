package rayTrace.util;

import intersectionbundle.IntersectionBundle;
import intersectionbundle.util.IntersectionBundleUtil;

import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.vecmath.Vector3f;

import math.util.MathUtil;
import modelloader.ModelLoaderUtil;
import ray.Ray;
import ray.util.RayUtil;
import rayMap.RayMap;
import rayMap.enums.RayMapType;
import rayMap.util.RayMapUtil;
import triangle.Triangle;
import vector.util.Vector3fUtil;
import window.Window;
import barycentric.util.BarycentricValuesUtil;

/**
 * A utility method for aiding in the process of ray tracing
 * 
 * @author kkrivera
 */
public class RayTraceUtil
{
	private static float PI = (float) Math.PI;

	private static int rows;
	private static int cols;
	public static Vector3f eye;
	public static Vector3f verticalIncrement;
	public static Vector3f horizontalIncrement;
	public static Vector3f dir00;
	public static Vector3f lightPoint;
	public static float lightIntensity = 3.5f;

	/**
	 * Initializes the Ray Tracer variables
	 * 
	 * @param eyeVertex
	 *        The {@link Vector3f} of the eye
	 * @param at
	 *        The {@link Vector3f} of the at position
	 * @param lightVertex
	 *        The {@link Vector3f} position of the light
	 */
	public static void initRayTracer(Vector3f eyeVertex, Vector3f at, Vector3f lightVertex)
	{
		eye = eyeVertex;
		lightPoint = lightVertex;
		Vector3f up = new Vector3f(0, 1, 0);
		int n = 1;
		float fov = PI / 2;

		Vector3f w = Vector3fUtil.norm(Vector3fUtil.sub(eye, at));
		Vector3f u = Vector3fUtil.norm(Vector3fUtil.cross(up, w));
		Vector3f v = Vector3fUtil.cross(w, u);

		int wW = Window.raster.getWidth();
		int wH = Window.raster.getHeight();

		float aspect = (float) wW / (float) wH;
		float resolution = 1.0f;

		float height = 2 * n * (float) (Math.tan(fov / 2));
		float width = aspect * height;

		rows = (int) (wH * resolution);
		cols = (int) (wW * resolution);

		float pixelW = width / cols;
		float pixelH = height / rows;

		verticalIncrement = Vector3fUtil.mult(v, pixelH);
		horizontalIncrement = Vector3fUtil.mult(u, pixelW);

		dir00 = Vector3fUtil.sub(Vector3fUtil.mult(w, -n), Vector3fUtil.mult(v, height / 2),
				Vector3fUtil.mult(u, width / 2));
		Vector3fUtil.addTo(dir00, Vector3fUtil.mult(u, pixelW / 2),
				Vector3fUtil.mult(v, pixelH / 2));
	}

	/**
	 * Creates a collection of {@link RayMap} objects
	 * 
	 * @param eyeVertex
	 *        The {@link Vector3f} of the eye
	 * @param at
	 *        The {@link Vector3f} of the at position
	 * @param lightVertex
	 *        The {@link Vector3f} position of the light
	 * @return A {@link HashSet} of {@link RayMap}s
	 */
	public static Set<RayMap> getSceneRayMaps(Vector3f eyeVertex, Vector3f at, Vector3f lightVertex)
	{
		// Initialize the ray tracer
		initRayTracer(eyeVertex, at, lightVertex);

		Set<RayMap> mainRayMapArray = new HashSet<RayMap>();
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				Ray ray = new Ray(eye, Vector3fUtil.norm(Vector3fUtil.add(dir00,
						Vector3fUtil.mult(verticalIncrement, i),
						Vector3fUtil.mult(horizontalIncrement, j))));

				RayMap rayMap = new RayMap(ray, RayMapType.MAIN);
				rayMap.setPixelX(cols - 1 - j);
				rayMap.setPixelY(rows - 1 - i);

				mainRayMapArray.add(rayMap);
			}
		}

		return mainRayMapArray;
	}

	/**
	 * Writes the {@link Collection} of {@link RayMap}s to the raster
	 * 
	 * @param rayMapArray
	 *        The collection of {@link RayMap}
	 */
	public static void writeRayMapToRaster(Collection<RayMap> rayMapArray)
	{

		for (RayMap rayMap : rayMapArray)
		{
			if (rayMap.getRayMapType() == RayMapType.MAIN)
			{
				Window.setPixelColor(rayMap.getPixelX(), rayMap.getPixelY(), getPixelColor(rayMap));
			}
		}
	}

	/**
	 * Gets the color of a given pixel for a {@link Ray} within a {@link RayMap}
	 * 
	 * @param rayMap
	 *        The {@link RayMap} containing the necessary {@link Ray} and
	 *        {@link IntersectionBundle}
	 * @return A {@link String} representing the hexadecimal color of the
	 *         {@link Ray}'s intersection with a {@link Triangle}
	 */
	public static String getPixelColor(RayMap rayMap)
	{
		if (rayMap.getIntersectionBundle() == null)
			return "333333";

		Vector3f colorVec = new Vector3f(0, 0, 0);

		RayMap shadowRayMap = RayMapUtil.getRayMapByType(rayMap.getIndirectRayMaps(),
				RayMapType.SHADOW);
		if (!underShadow(shadowRayMap, lightPoint))
		{
			colorVec = getColorVector(rayMap.getRay(), rayMap.getIntersectionBundle());

			RayMap reflectiveRayMap = RayMapUtil.getRayMapByType(rayMap.getIndirectRayMaps(),
					RayMapType.REFLECTIVE);

			if (reflectiveRayMap != null && reflectiveRayMap.getIntersectionBundle() != null)
			{
				colorVec.add(MathUtil.getColorVecWithMod(getPixelColor(reflectiveRayMap), 1));
				// reflectiveRayMap.getIntersectionBundle().getTriangle().getShininess()
			}
		}

		// Clamps once more so 255 is the max color
		if (colorVec.getX() > 255)
			colorVec.setX(255);
		if (colorVec.getY() > 255)
			colorVec.setY(255);
		if (colorVec.getZ() > 255)
			colorVec.setZ(255);

		return MathUtil.d2h((int) Math.floor(colorVec.getX()), 2)
				+ MathUtil.d2h((int) Math.floor(colorVec.getY()), 2)
				+ MathUtil.d2h((int) Math.floor(colorVec.getZ()), 2);
	}

	/**
	 * Gets the color of an intersection as RGB
	 * 
	 * @param ray
	 *        The {@link Ray} which intersected a {@link Triangle}
	 * @param intersectionBundle
	 *        The {@link IntersectionBundle} describing the intesection
	 * @return A {@link Vector3f} color RGB
	 */
	public static Vector3f getColorVector(Ray ray, IntersectionBundle intersectionBundle)
	{
		// Calculate Normal
		Vector3f normal = BarycentricValuesUtil.getBarycentricInterpolation(intersectionBundle
				.getTriangle().getNormal1(), intersectionBundle.getTriangle().getNormal2(),
				intersectionBundle.getTriangle().getNormal3(), intersectionBundle
						.getBarycentricValuesAtPoint());

		Vector3f n = Vector3fUtil.norm(normal);
		Vector3f L = Vector3fUtil.norm(Vector3fUtil.sub(
				intersectionBundle.getPointInsideTriangle(), lightPoint));
		Vector3f R = RayUtil.getReflectionDirection(L, n);
		Vector3f V = Vector3fUtil.norm(Vector3fUtil.mult(ray.getDirection(), -1));

		// Clamp
		float nDotL = n.dot(Vector3fUtil.mult(L, -1));
		if (nDotL < 0)
			nDotL = 0.0f;

		Vector3f kD = new Vector3f(1, 1, 1);
		if (intersectionBundle.getTriangle().getkD() != null)
		{
			kD = intersectionBundle.getTriangle().getkD();
		}

		Vector3f colorDiffuse = Vector3fUtil.mult(kD, nDotL);

		// Adds the texture color
		Vector3f textureVec = new Vector3f(255, 255, 255);
		if (intersectionBundle.getTriangle().getTexColor1() != null)
		{
			Vector3f texturePos = BarycentricValuesUtil.getBarycentricInterpolation(
					intersectionBundle.getTriangle().getTexPos1(), intersectionBundle.getTriangle()
							.getTexPos2(), intersectionBundle.getTriangle().getTexPos3(),
					intersectionBundle.getBarycentricValuesAtPoint());

			// IntersectionBundle dxIntersection = IntersectionBundleUtil
			// .rayIntersects(RayUtil.getDeltaRay(ray, horizontalIncrement),
			// intersectionBundle.getTriangle());
			//
			// IntersectionBundle dyIntersection =
			// IntersectionBundleUtil.rayIntersects(
			// RayUtil.getDeltaRay(ray, verticalIncrement),
			// intersectionBundle.getTriangle());
			//
			// Vector3f dxTexPos =
			// BarycentricValuesUtil.getBarycentricInterpolation(
			// intersectionBundle.getTriangle().getTexPos1(),
			// intersectionBundle.getTriangle()
			// .getTexPos2(), intersectionBundle.getTriangle().getTexPos3(),
			// dxIntersection.getBarycentricValuesAtPoint());
			//
			// Vector3f dyTexPos =
			// BarycentricValuesUtil.getBarycentricInterpolation(
			// intersectionBundle.getTriangle().getTexPos1(),
			// intersectionBundle.getTriangle()
			// .getTexPos2(), intersectionBundle.getTriangle().getTexPos3(),
			// dyIntersection.getBarycentricValuesAtPoint());
			//
			// colorDiffuse = Vector3fUtil.mult(colorDiffuse,
			// TriangleUtil.getAverageTextureColor(
			// intersectionBundle.getTriangle(), texturePos, new
			// Vector3f(dxTexPos.getX(),
			// dyTexPos.getY(), 0)));

			BufferedImage textureImage = ModelLoaderUtil.textures.get(intersectionBundle
					.getTriangle().getTextureName());

			ModelLoaderUtil.TEXTURE_TYPE textureType = ModelLoaderUtil.TEXTURE_TYPE.WRAP;
			Boolean mirrorTextureU = false;
			Boolean mirrorTextureV = false;

			String texColor = ModelLoaderUtil.getHexColor(textureImage,
					textureImage.getWidth() - 1, textureImage.getHeight() - 1, ModelLoaderUtil
							.getConvertedTextCoordinate(texturePos.getX(), textureType,
									mirrorTextureU), ModelLoaderUtil.getConvertedTextCoordinate(
							texturePos.getY(), textureType, mirrorTextureV));

			textureVec = MathUtil.getColorVec(texColor);
		}
		colorDiffuse = Vector3fUtil.mult(colorDiffuse, textureVec);

		float power = 20f;
		if (intersectionBundle.getTriangle().getShininess() != null)
		{
			power = intersectionBundle.getTriangle().getShininess();
		}

		float rDotV = R.dot(V);

		// Clamp
		if (rDotV < 0)
			rDotV = 0.0f;

		Vector3f kS = new Vector3f(1, 1, 1);
		if (intersectionBundle.getTriangle().getkS() != null)
		{
			kS = intersectionBundle.getTriangle().getkS();
		}

		Vector3f colorSpecular = new Vector3f(255, 255, 255);
		colorSpecular = Vector3fUtil.mult(colorSpecular, kS);
		colorSpecular = Vector3fUtil.mult(colorSpecular, (float) Math.pow(rDotV, power));

		Vector3f colorVec = Vector3fUtil.add(colorDiffuse, colorSpecular);

		if (intersectionBundle.getTriangle().getkA() != null)
		{
			Vector3fUtil.addTo(colorVec, intersectionBundle.getTriangle().getkA());
		}

		// float distFromLight =
		// Vector3fUtil.dist(intersectionBundle.getPointInsideTriangle(),
		// lightPoint);
		//
		// float mod = lightIntensity / (float) Math.sqrt((double)
		// distFromLight);

		if (colorVec.getX() == 0 && colorVec.getY() == 0 && colorVec.getZ() == 0)
		{
			// System.out.print("N?");
		}

		return colorVec;
	}

	/**
	 * Determines if a {@link RayMap}'s {@link Ray} is under shadow
	 * 
	 * @param rayMap
	 *        The {@link RayMap} being tested
	 * @param lightPt
	 *        The position of the light point as a {@link Vector3f}
	 * @return A {@link Boolean} indicating if the {@link RayMap} is under
	 *         shadow
	 */
	public static boolean underShadow(RayMap rayMap, Vector3f lightPt)
	{
		if (rayMap == null)
			return false;

		return underShadow(rayMap.getRay(), rayMap.getIntersectionBundle(), lightPt);
	}

	/**
	 * Determines if a {@link Ray} is under shadow
	 * 
	 * @param ray
	 *        The {@link Ray} in question
	 * @param intersectionBundle
	 *        The {@link IntersectionBundle} detailing the intersection
	 * @param lightPt
	 *        The position of the light point as a {@link Vector3f}
	 * @return A {@link Boolean} indicating if the {@link RayMap} is under
	 *         shadow
	 */
	public static boolean underShadow(Ray ray, IntersectionBundle intersectionBundle,
			Vector3f lightPt)
	{
		if (intersectionBundle == null)
			return false;

		return intersectionBundle.getT() > 0.0f
				&& intersectionBundle.getT() <= Vector3fUtil.mag(Vector3fUtil.sub(ray.getOrigin(),
						lightPt));
		// intersectionBundle.getT() <= 1.0f;
	}

	/**
	 * Generates the indirect {@link RayMap} for a given intersection
	 * 
	 * @param rayMapArray
	 *        The collection of {@link RayMap}s
	 * @param generateShadowRayMap
	 *        A {@link Boolean} of whether to generate a shadow {@link RayMap}
	 * @param generateReflectiveRayMap
	 *        A {@link Boolean} of whether to generate a reflective
	 *        {@link RayMap}
	 * @return A {@link Set} of indirect {@link RayMap}s
	 */
	public static Set<RayMap> generateInderectRayMaps(RayMap rayMap, boolean generateShadowRayMap,
			boolean generateReflectiveRayMap)
	{
		Set<RayMap> indirectRayMapArray = new HashSet<RayMap>();

		rayMap.setIntersectionBundle(IntersectionBundleUtil.getClosestTriangleIntersection(
				rayMap.getRay(), rayMap.getTriangles()));

		if (rayMap.getIntersectionBundle() != null && rayMap.getRayMapType() != RayMapType.SHADOW)
		{
			if (generateShadowRayMap)
			{
				rayMap.getIndirectRayMaps().add(
						new RayMap(RayUtil.getShadowRay(rayMap.getRay(),
								rayMap.getIntersectionBundle(), lightPoint), RayMapType.SHADOW));
			}

			if (generateReflectiveRayMap)
			{
				rayMap.getIndirectRayMaps().add(
						new RayMap(RayUtil.getReflectiveRay(rayMap.getRay(),
								rayMap.getIntersectionBundle()), RayMapType.REFLECTIVE));
			}

			indirectRayMapArray.addAll(rayMap.getIndirectRayMaps());
		}

		return indirectRayMapArray;
	}

	/**
	 * Generates a {@link Set} of indirect {@link RayMap}s
	 * 
	 * @param rayMaps
	 *        The {@link RayMap}s having their indirect ones generated
	 * @param generateShadowRayMap
	 *        The {@link Boolean} determining if the shadow {@link RayMap}
	 *        should be generated
	 * @param generateReflectiveRayMap
	 *        The {@link Boolean} determining if the reflective {@link RayMap}
	 *        should be generated
	 * @return A {@link Set} of indirect {@link RayMap}s
	 */
	public static Set<RayMap> generateInderectRayMaps(Set<RayMap> rayMaps,
			boolean generateShadowRayMap, boolean generateReflectiveRayMap)
	{
		Set<RayMap> indirectRayMaps = new HashSet<RayMap>();
		for (RayMap rayMap : rayMaps)
		{
			indirectRayMaps.addAll(RayTraceUtil.generateInderectRayMaps(rayMap,
					generateShadowRayMap, generateReflectiveRayMap));
		}

		return indirectRayMaps;
	}
	// public static PixelArea createSubDivididedPixelArea(int width, int
	// height, int subdivisions)
	// {
	// PixelArea parentPixelArea = new PixelArea(0, width - 1, 0, height - 1);
	//
	// List<PixelArea> pixelAreaArray = new ArrayList<PixelArea>();
	// pixelAreaArray.add(parentPixelArea);
	//
	// while (pixelAreaArray.size() < subdivisions)
	// {
	// int arraySize = pixelAreaArray.size();
	// for (int i = arraySize - 1; i >= 0; i--)
	// {
	// PixelArea pixelArea = pixelAreaArray.get(i);
	// PixelAreaUtil.splitArea(pixelArea);
	//
	// pixelAreaArray.remove(i);
	// pixelAreaArray.addAll(pixelArea.getChildAreas());
	//
	// if (pixelAreaArray.size() == subdivisions)
	// break;
	// }
	// }
	//
	// return parentPixelArea;
	// }
	//
	// public static Collection<Collection<RayMap>>
	// getPixelAreaDividedRayMaps(int numSections)
	// {
	// PixelArea windowPixelArea = RayTraceUtil.createSubDivididedPixelArea(
	// Window.raster.getWidth(), Window.raster.getHeight(), numSections);
	//
	// Collection<RayMap> mainRayMapArray = createMainRayMapArray();
	//
	// Collection<PixelArea> pixelAreaArray =
	// PixelAreaUtil.getPixelAreaChildren(windowPixelArea);
	// Map<PixelArea, Collection<RayMap>> pixelAreaRaysMap = new
	// HashMap<PixelArea, Collection<RayMap>>();
	//
	// for (PixelArea pixelArea : pixelAreaArray)
	// pixelAreaRaysMap.put(pixelArea, new ArrayList<RayMap>());
	//
	// for (RayMap mainRayMap : mainRayMapArray)
	// {
	// for (PixelArea pixelArea : pixelAreaArray)
	// {
	// if (pixelArea.getxMin() <= mainRayMap.getPixelX()
	// && mainRayMap.getPixelX() <= pixelArea.getxMax()
	// && pixelArea.getyMin() <= mainRayMap.getPixelY()
	// && mainRayMap.getPixelY() <= pixelArea.getyMax())
	// {
	// pixelAreaRaysMap.get(pixelArea).add(mainRayMap);
	// break;
	// }
	// }
	// }
	//
	// return pixelAreaRaysMap.values();
	// }
}
