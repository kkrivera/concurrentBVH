package test;

import java.util.Set;

import javax.vecmath.Vector3f;

import modelloader.ModelLoaderUtil;
import rayTrace.RayTrace;
import triangle.Triangle;
import vector.util.Vector3fUtil;
import voxel.Voxel;
import voxel.util.VoxelUtil;
import window.Window;
import bvh.BvhTree;
import bvh.builder.DepthFirstBvhBuilder;
import bvh.builder.IBvhBuilder;

public class TestRayTrace
{
	public static void main(String[] args)
	{
		Set<Triangle> triangles = ModelLoaderUtil.loadModel("models\\legoMan\\LegoMan.obj");

		// Set<Triangle> triangles =
		// ModelLoaderUtil.loadModel("models\\parliament\\parliament.obj");

		// Set<Triangle> triangles =
		// ModelLoaderUtil.loadModel("models\\halo3\\halo3Scene.obj");

		// Set<Triangle> triangles =
		// ModelLoaderUtil.loadModel("models\\church\\church.obj");

		// Set<Triangle> triangles =
		// ModelLoaderUtil.loadModel("models\\mainzCathedral\\mainzCathedral.obj");

		// Set<Triangle> triangles =
		// ModelLoaderUtil.loadModel("models\\ussEnterprise\\ussEnterprise.obj");

		// Set<Triangle> triangles =
		// ModelLoaderUtil.loadModel("models\\bunny\\bunny.obj");

		// Set<Triangle> triangles =
		// ModelLoaderUtil.loadModel("models\\bumblebee\\bumblebee.obj");

		Window.initWindow(800, 600);

		System.out.println("Triangles: " + triangles.size());
		System.out.println("Building BVH");
		IBvhBuilder recursiveBuilder = new DepthFirstBvhBuilder();
		BvhTree bvhTree = null;
		try
		{
			bvhTree = recursiveBuilder.buildTree(triangles);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(0);
		}

		System.out.println("BVH Built");
		Voxel parentNodeVoxel = bvhTree.getParentBvhNode().getVoxel();
		Vector3f centerOfVoxel = VoxelUtil.getVoxelCenter(bvhTree.getParentBvhNode().getVoxel());

		float zoom = 1f;

		// Vector3f eye = new Vector3f(parentNodeVoxel.getMaxX() / zoom,
		// parentNodeVoxel.getMaxY() / zoom, parentNodeVoxel.getMaxZ() / zoom);

		Vector3f eye = new Vector3f(0, 0, 5);

		// eye = Vector3fUtil.mult(eye, new Vector3f(1, 1, 1));

		// Vector3f eye = new Vector3f(centerOfVoxel.getX()
		// + (parentNodeVoxel.getMaxX() - parentNodeVoxel.getMinX()),
		// centerOfVoxel.getY(),
		// centerOfVoxel.getZ());

		// Vector3f eye = new Vector3f(
		// (parentNodeVoxel.getMinX() + parentNodeVoxel.getMaxX()) / 2.0f,
		// (parentNodeVoxel.getMinY() + parentNodeVoxel.getMaxY()) / 2.0f,
		// (centerOfVoxel.getZ() + (parentNodeVoxel.getMaxZ() -
		// centerOfVoxel.getZ()) + (parentNodeVoxel
		// .getMaxY() - parentNodeVoxel.getMinY()) / 4.0f));

		// RayTrace.rayTrace(bvhTree, eye, centerOfVoxel, new
		// Vector3f(parentNodeVoxel.getMaxX(),
		// parentNodeVoxel.getMaxY(), parentNodeVoxel.getMaxZ()), 1);

		RayTrace.rayTrace(bvhTree, eye, centerOfVoxel,
				Vector3fUtil.mult(eye, new Vector3f(1, 1, 1)), 0, true);

	}

}
