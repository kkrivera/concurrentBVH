package rayMap.util;

import java.util.Collection;

import rayMap.RayMap;
import rayMap.enums.RayMapType;

public class RayMapUtil
{
	/**
	 * Gets a child {@link RayMap} based off of its type
	 * 
	 * @param rayMapArray
	 *        The {@link Collection} of {@link RayMap}s which will be searched
	 * @param rayMapType
	 *        The {@link RayMapType} enumeration
	 * @return Null if no map of said type is found, otherwise the matched
	 *         {@link RayMap}
	 */
	public static RayMap getRayMapByType(Collection<RayMap> rayMapArray, RayMapType rayMapType)
	{
		if (rayMapArray == null || rayMapType == null)
			return null;

		for (RayMap rayMap : rayMapArray)
		{
			if (rayMap.getRayMapType() == rayMapType)
			{
				return rayMap;
			}
		}

		return null;
	}

}
