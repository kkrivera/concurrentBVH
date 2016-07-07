package test;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.vecmath.Vector3f;

import modelloader.ModelLoaderUtil;
import triangle.Triangle;
import voxel.Voxel;
import bvh.BvhTree;
import bvh.builder.BreadthFirstBvhBuilder;
import bvh.builder.DepthFirstBvhBuilder;
import bvh.builder.IBvhBuilder;
import bvh.builder.concurrent.BlockingRecursiveBvhBuilder;
import bvh.builder.concurrent.IConcurrentBvhBuilder;
import bvh.builder.concurrent.NonBlockingBvhBuilder;
import bvh.builder.concurrent.NonBlockingThreadPoolBvhBuilder;

public class TestBuildBvhTree
{
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// Setup our output file
		PrintStream fileOut = null;
		String filename = "test_out.txt";

		if (args.length > 0)
			filename = args[0];

		try
		// to open the file outputFileName
		{
			fileOut = new PrintStream(filename);
		}
		catch (FileNotFoundException e)
		{
			System.err.printf("\nFailure to load \"%s\". Closing program...", filename);
			System.exit(0);
		}

		String modelFullPath = "models\\legoman\\LegoMan.obj";

		int maxThreads = 32;
		int numLoops = 10;

		System.out.println("Starting Test");
		System.out.println("MaxThreads: " + maxThreads + ", Num Test Loops: " + numLoops);

		if (fileOut != null)
		{
			fileOut.println("Starting Test");
			System.out.println("MaxThreads: " + maxThreads + ", Num Test Loops: " + numLoops);
		}

		runTests(modelFullPath, maxThreads, numLoops, fileOut);
	}

	/**
	 * Tests each of our BVH builders on a randomly generated scene using an
	 * incremental number of threads per test
	 * 
	 * @param numTriangles
	 *        Number of random triangles generated
	 * @param maxThreads
	 *        Maximum number of threads to test, must be an even number
	 * @param numTestLoops
	 *        Number of times to repeat a given test set
	 * @param outFile
	 *        Output file to print test text too, or null for no file out
	 */
	private static void runTests(String modelFullPath, int maxThreads, int numTestLoops,
			PrintStream outFile)
	{
		long start = System.currentTimeMillis();
		Set<Triangle> triangles = ModelLoaderUtil.loadModel(modelFullPath);

		outputTime(System.out, "Build " + triangles.size() + " triangles", start,
				System.currentTimeMillis());
		if (outFile != null)
			outputTime(outFile, "Build " + triangles.size() + " triangles", start,
					System.currentTimeMillis());

		IBvhBuilder depthFirstBuilder = new DepthFirstBvhBuilder();
		IBvhBuilder breadthFirstBuilder = new BreadthFirstBvhBuilder();
		IConcurrentBvhBuilder blockingBuilder = new BlockingRecursiveBvhBuilder();
		IConcurrentBvhBuilder nonBlockingThreadPoolBuilder = new NonBlockingThreadPoolBvhBuilder();
		IConcurrentBvhBuilder nonBlockingBuilder = new NonBlockingBvhBuilder();

		String outerTestSep = "-----------------------------------";
		String innerTestSep = "***********************************";

		// Run a bias test. The first build that runs takes significantly longer
		// to run than all the others
		// 4-5 times longer. This is most likely due to behind the scenes memory
		// management going on with
		// the JVM, so we run a single build as a bias build to get this initial
		// setup out of the way to
		// stop from biasing the tests
		// try
		// {
		// BvhTree tree = nonBlockingBuilder.buildTree(triangles, 2);
		// System.out.println("Tree size = " + tree.getTreeCollection().size());
		// }
		// catch (Exception e1)
		// {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }

		Integer expectedTreeSize = triangles.size() * 2 - 1;

		for (int i = 2; i <= maxThreads; i *= 2)
		{
			printTestTitle(System.out, String.valueOf(i), innerTestSep, outerTestSep);

			if (outFile != null)
				printTestTitle(outFile, String.valueOf(i), innerTestSep, outerTestSep);

			long blockingTotal = 0;
			long nonBlockingTotal = 0;
			for (int j = 0; j < numTestLoops; j++)
			{
				try
				{
					System.out.println("Test " + (j + 1));
					if (outFile != null)
						outFile.println("Test " + (j + 1));

					// Non-Blocking
					System.out.printf("Start Non-Blocking Builder\n");
					start = System.currentTimeMillis();
					BvhTree tree = nonBlockingBuilder.buildTree(triangles, i);
					long curTime = System.currentTimeMillis();
					nonBlockingTotal += outputTime(System.out, "Non-Blocking Builder(" + i + ")",
							start, curTime);

					if (outFile != null)
						outputTime(outFile, "Non-Blocking Builder(" + i + ")", start, curTime);

					if (tree.getTreeCollection().size() != expectedTreeSize)
					{
						throw new Exception("Incorrect Tree Size");
					}

					// // Non-Blocking Thread Pool
					// start = System.currentTimeMillis();
					// tree = nonBlockingThreadPoolBuilder.buildTree(triangles,
					// i);
					// curTime = System.currentTimeMillis();
					// nonBlockingTotal += outputTime(System.out,
					// "Non-Blocking Thread Pool Builder("
					// + i + ")", start, curTime);
					//
					// if (outFile != null)
					// outputTime(outFile, "Non-Blocking Thread Pool Builder(" +
					// i + ")", start,
					// curTime);
					//
					// if (tree.getTreeCollection().size() != expectedTreeSize)
					// {
					// throw new Exception("Incorrect Tree Size");
					// }

					System.out.printf("Start Blocking Builder\n");
					start = System.currentTimeMillis();
					tree = blockingBuilder.buildTree(triangles, i);
					curTime = System.currentTimeMillis();
					blockingTotal += outputTime(System.out, "Blocking Builder(" + i + ")", start,
							curTime);

					if (tree.getTreeCollection().size() != expectedTreeSize)
					{
						throw new Exception("Incorrect Tree Size");
					}

					if (outFile != null)
						outputTime(outFile, "Blocking Builder(" + i + ")", start, curTime);

				}
				catch (Exception e)
				{
					System.out.println("***Exception. Re-running test***");

					if (outFile != null)
						outFile.println("***Exception. Re-running test***");

					j--;
				}
			}

			System.out.println(innerTestSep);
			System.out.println("Results");
			System.out.println("Non-Blocking Average(" + i + "): " + (float) nonBlockingTotal
					/ (float) numTestLoops + "ms");
			System.out.println("Blocking Average(" + i + "): " + (float) blockingTotal
					/ (float) numTestLoops + "ms");
			System.out.println(outerTestSep);

			if (outFile != null)
			{
				outFile.println(innerTestSep);
				outFile.println("Results");
				outFile.println("Non-Blocking Average(" + i + "): " + (float) nonBlockingTotal
						/ (float) numTestLoops + "ms");
				outFile.println("Blocking Average(" + i + "): " + (float) blockingTotal
						/ (float) numTestLoops + "ms");
				outFile.println(outerTestSep);
			}
		}

		long depthFirstTotal = 0;
		long breadthFirstTotal = 0;

		printTestTitle(System.out, "Single", innerTestSep, outerTestSep);

		if (outFile != null)
			printTestTitle(outFile, "Single", innerTestSep, outerTestSep);

		// Single Threaded Tests
		for (int i = 0; i < numTestLoops; i++)
		{
			System.out.println("Test " + (i + 1));

			if (outFile != null)
				outFile.println("Test " + (i + 1));

			try
			{
				// Depth First
				start = System.currentTimeMillis();
				depthFirstBuilder.buildTree(triangles);
				long curTime = System.currentTimeMillis();
				depthFirstTotal += outputTime(System.out, "DepthFirst", start, curTime);

				if (outFile != null)
					outputTime(outFile, "DepthFirst", start, curTime);

				// Breadth First
				start = System.currentTimeMillis();
				breadthFirstBuilder.buildTree(triangles);
				curTime = System.currentTimeMillis();
				breadthFirstTotal += outputTime(System.out, "BreadthFirst", start, curTime);

				if (outFile != null)
					outputTime(outFile, "BreadthFirst", start, curTime);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		System.out.println(innerTestSep);
		System.out.println("Results");
		System.out.println("Depth First Average: " + (float) depthFirstTotal / (float) numTestLoops
				+ "ms");
		System.out.println("Breadth First Average: " + (float) breadthFirstTotal
				/ (float) numTestLoops + "ms");
		System.out.println(outerTestSep);

		if (outFile != null)
		{
			outFile.println(innerTestSep);
			outFile.println("Results");
			outFile.println("Depth First Average: " + (float) depthFirstTotal
					/ (float) numTestLoops + "ms");
			outFile.println("Breadth First Average: " + (float) breadthFirstTotal
					/ (float) numTestLoops + "ms");
			outFile.println(outerTestSep);

			outFile.close();
		}

		System.exit(0);
	}

	private static void printTestTitle(PrintStream out, String threads, String innerTestSep,
			String outerTestSep)
	{
		out.println();
		out.println(outerTestSep);
		out.println(threads + " Threaded Tests");
		out.println(innerTestSep);
	}

	private static long outputTime(PrintStream out, String label, long start, long end)
	{
		long timeDiff = (end - start);
		out.println(label + ": " + timeDiff + "ms");

		return timeDiff;
	}

	private static Set<Triangle> buildRandomScene(Integer numTriangles, Voxel sceneBounds)
	{
		Set<Triangle> triangles = new HashSet<Triangle>();

		Float sceneXDiff = sceneBounds.getMaxX() - sceneBounds.getMinX();
		Float sceneYDiff = sceneBounds.getMaxY() - sceneBounds.getMinY();
		Float sceneZDiff = sceneBounds.getMaxZ() - sceneBounds.getMinZ();

		Random rand = new Random(System.currentTimeMillis());
		for (int i = 0; i < numTriangles; i++)
		{
			triangles.add(new Triangle(new Vector3f(sceneBounds.getMinX() + sceneXDiff
					* rand.nextFloat(), sceneBounds.getMinX() + sceneXDiff * rand.nextFloat(),
					sceneBounds.getMinX() + sceneXDiff * rand.nextFloat()), new Vector3f(
					sceneBounds.getMinX() + sceneXDiff * rand.nextFloat(), sceneBounds.getMinX()
							+ sceneXDiff * rand.nextFloat(), sceneBounds.getMinX() + sceneXDiff
							* rand.nextFloat()), new Vector3f(sceneBounds.getMinX() + sceneXDiff
					* rand.nextFloat(), sceneBounds.getMinX() + sceneXDiff * rand.nextFloat(),
					sceneBounds.getMinX() + sceneXDiff * rand.nextFloat())));
		}

		return triangles;
	}
}
