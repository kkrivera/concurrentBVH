package ray;

import javax.vecmath.Vector3f;

import vector.util.Vector3fUtil;

public class Ray
{
	private Vector3f origin;
	private Vector3f direction;

	/**
	 * Default Constructor for {@link Ray}
	 * 
	 * @param origin
	 *        The {@link Vector3f} that represents the origin of the {@link Ray}
	 * @param direction
	 *        The {@link Vector3f} that represents the direction of the
	 *        {@link Ray}
	 */
	public Ray(Vector3f origin, Vector3f direction)
	{
		this.origin = origin;
		this.direction = Vector3fUtil.norm(direction);
	}

	/**
	 * Gets a {@link Vector3f} representing the origin of the {@link Ray}
	 * 
	 * @return A {@link Vector3f}
	 */
	public Vector3f getOrigin()
	{
		return origin;
	}

	/**
	 * Sets the origin of the {@link Ray}
	 * 
	 * @param origin
	 *        A {@link Vector3f}
	 */
	public void setOrigin(Vector3f origin)
	{
		this.origin = origin;
	}

	/**
	 * Gets a {@link Vector3f} representing the direction of the {@link Ray}
	 * 
	 * @return
	 */
	public Vector3f getDirection()
	{
		return direction;
	}

	/**
	 * Sets the direction of the {@link Ray}
	 * 
	 * @param direction
	 *        A {@link Vector3f}
	 */
	public void setDirection(Vector3f direction)
	{
		this.direction = direction;
	}
}
