package rayMap;

import intersectionbundle.IntersectionBundle;

import java.util.ArrayList;
import java.util.Collection;

import ray.Ray;
import rayMap.enums.RayMapType;
import triangle.Triangle;

public class RayMap
{
	private Ray ray;
	private RayMapType rayMapType;
	private Collection<Triangle> triangles;
	private Collection<RayMap> indirectRayMaps;
	private IntersectionBundle intersectionBundle;
	private int pixelX;
	private int pixelY;

	public RayMap(Ray ray, RayMapType rayMapType)
	{
		this.ray = ray;
		this.rayMapType = rayMapType;
		this.triangles = new ArrayList<Triangle>();
		this.indirectRayMaps = new ArrayList<RayMap>();
		this.intersectionBundle = null;
		this.pixelX = -1;
		this.pixelY = -1;
	}

	public IntersectionBundle getIntersectionBundle()
	{
		return intersectionBundle;
	}

	public void setIntersectionBundle(IntersectionBundle intersectionBundle)
	{
		this.intersectionBundle = intersectionBundle;
	}

	public RayMapType getRayMapType()
	{
		return rayMapType;
	}

	public void setRayMapType(RayMapType rayMapType)
	{
		this.rayMapType = rayMapType;
	}

	public Ray getRay()
	{
		return ray;
	}

	public void setRay(Ray ray)
	{
		this.ray = ray;
	}

	public Collection<Triangle> getTriangles()
	{
		return triangles;
	}

	public synchronized void setTriangles(Collection<Triangle> triangles)
	{
		this.triangles = triangles;
	}

	public synchronized void addTrianglesSynchronous(Collection<Triangle> triangles)
	{
		this.triangles.addAll(triangles);
	}

	public synchronized Collection<RayMap> getIndirectRayMaps()
	{
		return indirectRayMaps;
	}

	public synchronized void setIndirectRayMaps(Collection<RayMap> indirectRayMaps)
	{
		this.indirectRayMaps = indirectRayMaps;
	}

	public int getPixelX()
	{
		return pixelX;
	}

	public void setPixelX(int pixelX)
	{
		this.pixelX = pixelX;
	}

	public int getPixelY()
	{
		return pixelY;
	}

	public void setPixelY(int pixelY)
	{
		this.pixelY = pixelY;
	}

}
