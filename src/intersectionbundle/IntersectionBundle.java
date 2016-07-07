package intersectionbundle;

import javax.vecmath.Vector3f;

import triangle.Triangle;
import barycentric.BarycentricValues;

public class IntersectionBundle
{
	private Triangle triangle;
	private Vector3f pointInsideTriangle;
	private BarycentricValues barycentricValuesAtPoint;
	private Float t = null;

	public IntersectionBundle()
	{
		this.triangle = null;
		this.pointInsideTriangle = null;
		this.barycentricValuesAtPoint = null;
		this.t = null;
	}

	public IntersectionBundle(Triangle triangle, Vector3f point,
			BarycentricValues barycentricValues, Float t)
	{
		this.triangle = triangle;
		this.pointInsideTriangle = point;
		this.barycentricValuesAtPoint = barycentricValues;
		this.t = t;
	}

	public Triangle getTriangle()
	{
		return triangle;
	}

	public void setTriangle(Triangle triangle)
	{
		this.triangle = triangle;
	}

	public Vector3f getPointInsideTriangle()
	{
		return pointInsideTriangle;
	}

	public void setPointInsideTriangle(Vector3f pointInsideTriangle)
	{
		this.pointInsideTriangle = pointInsideTriangle;
	}

	public BarycentricValues getBarycentricValuesAtPoint()
	{
		return barycentricValuesAtPoint;
	}

	public void setBarycentricValuesAtPoint(BarycentricValues barycentricValuesAtPoint)
	{
		this.barycentricValuesAtPoint = barycentricValuesAtPoint;
	}

	public Float getT()
	{
		return t;
	}

	public void setT(Float t)
	{
		this.t = t;
	}

}
