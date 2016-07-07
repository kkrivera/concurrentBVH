package triangle;

import javax.vecmath.Vector3f;

import vector.util.Vector3fUtil;

public class Triangle
{
	private Vector3f vertex1;
	private Vector3f vertex2;
	private Vector3f vertex3;

	private Vector3f normal1;
	private Vector3f normal2;
	private Vector3f normal3;

	private Vector3f normal;

	private Vector3f kA;
	private Vector3f kD;
	private Vector3f kS;

	private Vector3f texPos1;
	private Vector3f texPos2;
	private Vector3f texPos3;

	private String texColor1;
	private String texColor2;
	private String texColor3;

	private Float shininess;

	private String textureName;

	private void setNormal()
	{
		this.normal = Vector3fUtil.norm(Vector3fUtil.cross(
				Vector3fUtil.sub(this.vertex2, this.vertex1),
				Vector3fUtil.sub(this.vertex3, this.vertex1)));
	}

	/**
	 * Minimal constructor for {@link Triangle} object
	 * 
	 * @param v1
	 *        A {@link Vector3f} representing the first vertex of the
	 *        {@link Triangle}
	 * @param v2
	 *        A {@link Vector3f} representing the second vertex of the
	 *        {@link Triangle}
	 * @param v3
	 *        A {@link Vector3f} representing the third vertex of the
	 *        {@link Triangle}
	 */
	public Triangle(Vector3f v1, Vector3f v2, Vector3f v3)
	{
		this.vertex1 = v1;
		this.vertex2 = v2;
		this.vertex3 = v3;

		setNormal();

		this.normal1 = this.normal;
		this.normal2 = this.normal;
		this.normal3 = this.normal;
	}

	/**
	 * A minimal constructor containing the normals to the triangle
	 * 
	 * @param v1
	 *        A {@link Vector3f} representing the first vertex of the
	 *        {@link Triangle}
	 * @param v2
	 *        A {@link Vector3f} representing the second vertex of the
	 *        {@link Triangle}
	 * @param v3
	 *        A {@link Vector3f} representing the third vertex of the
	 *        {@link Triangle}
	 * @param n1
	 *        A {@link Vector3f} representing the first normal of the
	 *        {@link Triangle}
	 * @param n2
	 *        A {@link Vector3f} representing the first normal of the
	 *        {@link Triangle}
	 * @param n3
	 *        A {@link Vector3f} representing the first normal of the
	 *        {@link Triangle}
	 */
	public Triangle(Vector3f v1, Vector3f v2, Vector3f v3, Vector3f n1, Vector3f n2, Vector3f n3)
	{
		this.vertex1 = v1;
		this.vertex2 = v2;
		this.vertex3 = v3;

		this.normal1 = n1;
		this.normal2 = n2;
		this.normal3 = n3;

		setNormal();
	}

	/**
	 * A default constructor for a {@link Triangle}
	 * 
	 * @param v1
	 *        A {@link Vector3f} representing the first vertex of the
	 *        {@link Triangle}
	 * @param v2
	 *        A {@link Vector3f} representing the second vertex of the
	 *        {@link Triangle}
	 * @param v3
	 *        A {@link Vector3f} representing the third vertex of the
	 *        {@link Triangle}
	 * @param n1
	 *        A {@link Vector3f} representing the first normal of the
	 *        {@link Triangle}
	 * @param n2
	 *        A {@link Vector3f} representing the first normal of the
	 *        {@link Triangle}
	 * @param n3
	 *        A {@link Vector3f} representing the first normal of the
	 *        {@link Triangle}
	 * @param kA
	 *        The kA of the {@link Triangle}
	 * @param kD
	 *        The diffuse color of the {@link Triangle}
	 * @param kS
	 *        The specular color of the {@link Triangle}
	 * @param shinniness
	 *        The shininess of a given {@link Triangle}
	 */
	public Triangle(Vector3f v1, Vector3f v2, Vector3f v3, Vector3f n1, Vector3f n2, Vector3f n3,
			Vector3f kA, Vector3f kD, Vector3f kS, Float shininess)
	{
		this.vertex1 = v1;
		this.vertex2 = v2;
		this.vertex3 = v3;

		this.normal1 = n1;
		this.normal2 = n2;
		this.normal3 = n3;

		this.kA = kA;
		this.kD = kD;
		this.kS = kS;

		this.shininess = shininess;

		setNormal();

	}

	/**
	 * The maximal constructor for a {@link Triangle}
	 * 
	 * @param v1
	 *        A {@link Vector3f} representing the first vertex of the
	 *        {@link Triangle}
	 * @param v2
	 *        A {@link Vector3f} representing the second vertex of the
	 *        {@link Triangle}
	 * @param v3
	 *        A {@link Vector3f} representing the third vertex of the
	 *        {@link Triangle}
	 * @param n1
	 *        A {@link Vector3f} representing the first normal of the
	 *        {@link Triangle}
	 * @param n2
	 *        A {@link Vector3f} representing the second normal of the
	 *        {@link Triangle}
	 * @param n3
	 *        A {@link Vector3f} representing the third normal of the
	 *        {@link Triangle}
	 * @param kA
	 *        The kA of the {@link Triangle}
	 * @param kD
	 *        The diffuse color of the {@link Triangle}
	 * @param kS
	 *        The specular color of the {@link Triangle}
	 * @param shinniness
	 *        The shininess of a given {@link Triangle}
	 * @param t1
	 *        A {@link Vector3f} representing the first texture position of the
	 *        {@link Triangle}
	 * @param t2
	 *        A {@link Vector3f} representing the second texture position of the
	 *        {@link Triangle}
	 * @param t3
	 *        A {@link Vector3f} representing the third texture position of the
	 *        {@link Triangle}
	 * @param texColor1
	 *        A {@link String} representing the hex color of texture position 1
	 * @param texColor2
	 *        A {@link String} representing the hex color of texture position 2
	 * @param texColor3
	 *        A {@link String} representing the hex color of texture position 3
	 */
	public Triangle(Vector3f v1, Vector3f v2, Vector3f v3, Vector3f n1, Vector3f n2, Vector3f n3,
			Vector3f kA, Vector3f kD, Vector3f kS, Float shininess, Vector3f t1, Vector3f t2,
			Vector3f t3, String texColor1, String texColor2, String texColor3)
	{
		this.vertex1 = v1;
		this.vertex2 = v2;
		this.vertex3 = v3;

		this.normal1 = n1;
		this.normal2 = n2;
		this.normal3 = n3;

		this.kA = kA;
		this.kD = kD;
		this.kS = kS;

		this.shininess = shininess;

		this.texPos1 = t1;
		this.texPos2 = t2;
		this.texPos3 = t3;

		this.texColor1 = texColor1;
		this.texColor2 = texColor2;
		this.texColor3 = texColor3;

		setNormal();
	}

	/**
	 * Gets a {@link Vector3f} representing the first vertex
	 * 
	 * @return A {@link Vector3f}
	 */
	public Vector3f getVertex1()
	{
		return vertex1;
	}

	/**
	 * Sets the first vertex with a {@link Vector3f}
	 * 
	 * @param vertex1
	 *        A {@link Vector3f} representing the first vertex
	 */
	public void setVertex1(Vector3f vertex1)
	{
		this.vertex1 = vertex1;
	}

	/**
	 * Gets a {@link Vector3f} representing the second vertex
	 * 
	 * @return A {@link Vector3f}
	 */
	public Vector3f getVertex2()
	{
		return vertex2;
	}

	/**
	 * Sets the second vertex with a {@link Vector3f}
	 * 
	 * @param vertex2
	 *        A {@link Vector3f} representing the second vertex
	 */
	public void setVertex2(Vector3f vertex2)
	{
		this.vertex2 = vertex2;
	}

	/**
	 * Gets a {@link Vector3f} representing the third vertex
	 * 
	 * @return A {@link Vector3f}
	 */
	public Vector3f getVertex3()
	{
		return vertex3;
	}

	/**
	 * Sets the third vertex with a {@link Vector3f}
	 * 
	 * @param vertex3
	 *        A {@link Vector3f} representing the third vertex
	 */
	public void setVertex3(Vector3f vertex3)
	{
		this.vertex3 = vertex3;
	}

	/**
	 * Gets a {@link Vector3f} representing the the normal
	 * 
	 * @return A {@link Vector3f}
	 */
	public Vector3f getNormal()
	{
		return normal;
	}

	/**
	 * Sets the normal with a {@link Vector3f}
	 * 
	 * @param normal
	 *        A {@link Vector3f} representing the normal
	 */
	public void setNormal(Vector3f normal)
	{
		this.normal = normal;
	}

	/**
	 * Gets a {@link String} representing the color of the first vertex
	 * 
	 * @return A {@link String}
	 */
	public String getTexColor1()
	{
		return texColor1;
	}

	/**
	 * Sets the color of first vertex with a {@link Vector3f}
	 * 
	 * @param texColor1
	 *        A {@link Vector3f} representing the hexadecimal color of the first
	 *        vertex
	 */
	public void setTexColor1(String texColor1)
	{
		this.texColor1 = texColor1;
	}

	/**
	 * Gets a {@link String} representing the color of the second vertex
	 * 
	 * @return A {@link String}
	 */
	public String getTexColor2()
	{
		return texColor2;
	}

	/**
	 * Sets the color of second vertex with a {@link Vector3f}
	 * 
	 * @param texColor2
	 *        A {@link Vector3f} representing the hexadecimal color of the
	 *        second vertex
	 */
	public void setTexColor2(String texColor2)
	{
		this.texColor2 = texColor2;
	}

	/**
	 * Gets a {@link String} representing the color of the third vertex
	 * 
	 * @return A {@link String}
	 */
	public String getTexColor3()
	{
		return texColor3;
	}

	/**
	 * Sets the color of third vertex with a {@link Vector3f}
	 * 
	 * @param texColor3
	 *        A {@link Vector3f} representing the hexadecimal color of the third
	 *        vertex
	 */
	public void setTexColor3(String texColor3)
	{
		this.texColor3 = texColor3;
	}

	/**
	 * Gets the normal for the first vertex
	 * 
	 * @return the normal1
	 */
	public Vector3f getNormal1()
	{
		return normal1;
	}

	/**
	 * Sets the normal for the first vertex
	 * 
	 * @param normal1
	 *        the normal1 to set
	 */
	public void setNormal1(Vector3f normal1)
	{
		this.normal1 = normal1;
	}

	/**
	 * Gets the normal for the second vertex
	 * 
	 * @return the normal2
	 */
	public Vector3f getNormal2()
	{
		return normal2;
	}

	/**
	 * Sets the normal for the second vertex
	 * 
	 * @param normal2
	 *        the normal2 to set
	 */
	public void setNormal2(Vector3f normal2)
	{
		this.normal2 = normal2;
	}

	/**
	 * Gets the normal for the third vertex
	 * 
	 * @return the normal3
	 */
	public Vector3f getNormal3()
	{
		return normal3;
	}

	/**
	 * Sets the normal for the first vertex
	 * 
	 * @param normal3
	 *        the normal3 to set
	 */
	public void setNormal3(Vector3f normal3)
	{
		this.normal3 = normal3;
	}

	/**
	 * Gets kA
	 * 
	 * @return the kA
	 */
	public Vector3f getkA()
	{
		return kA;
	}

	/**
	 * Sets kA
	 * 
	 * @param kA
	 *        the kA to set
	 */
	public void setkA(Vector3f kA)
	{
		this.kA = kA;
	}

	/**
	 * Gets kD, the diffuse color
	 * 
	 * @return the kD
	 */
	public Vector3f getkD()
	{
		return kD;
	}

	/**
	 * Sets kD, the diffuse color
	 * 
	 * @param kD
	 *        the kD to set
	 */
	public void setkD(Vector3f kD)
	{
		this.kD = kD;
	}

	/**
	 * Gets kS, the specular color
	 * 
	 * @return the kS
	 */
	public Vector3f getkS()
	{
		return kS;
	}

	/**
	 * Sets kS, the specular color
	 * 
	 * @param kS
	 *        the kS to set
	 */
	public void setkS(Vector3f kS)
	{
		this.kS = kS;
	}

	/**
	 * Gets the shininess
	 * 
	 * @return the shininess
	 */
	public Float getShininess()
	{
		return shininess;
	}

	/**
	 * Sets the shininess
	 * 
	 * @param shinniness
	 *        the shininess to set
	 */
	public void setShininess(Float shinniness)
	{
		this.shininess = shinniness;
	}

	/**
	 * @return the texPos1
	 */
	public Vector3f getTexPos1()
	{
		return texPos1;
	}

	/**
	 * @param texPos1
	 *        the texPos1 to set
	 */
	public void setTexPos1(Vector3f texPos1)
	{
		this.texPos1 = texPos1;
	}

	/**
	 * @return the texPos2
	 */
	public Vector3f getTexPos2()
	{
		return texPos2;
	}

	/**
	 * @param texPos2
	 *        the texPos2 to set
	 */
	public void setTexPos2(Vector3f texPos2)
	{
		this.texPos2 = texPos2;
	}

	/**
	 * @return the texPos3
	 */
	public Vector3f getTexPos3()
	{
		return texPos3;
	}

	/**
	 * @param texPos3
	 *        the texPos3 to set
	 */
	public void setTexPos3(Vector3f texPos3)
	{
		this.texPos3 = texPos3;
	}

	/**
	 * @return the textureName
	 */
	public String getTextureName()
	{
		return textureName;
	}

	/**
	 * @param textureName
	 *        the textureName to set
	 */
	public void setTextureName(String textureName)
	{
		this.textureName = textureName;
	}

}
