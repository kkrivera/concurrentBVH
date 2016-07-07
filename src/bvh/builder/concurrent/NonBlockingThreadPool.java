package bvh.builder.concurrent;

import java.util.concurrent.ConcurrentLinkedQueue;

public class NonBlockingThreadPool
{
	private Thread worker;
	private Thread[] pool;
	private ConcurrentLinkedQueue<Runnable> taskQueue;

	private boolean shutdown;

	public NonBlockingThreadPool(int threadCount)
	{
		pool = new Thread[threadCount];
		taskQueue = new ConcurrentLinkedQueue<Runnable>();

		shutdown = false;

		worker = new Thread(new ThreadPoolWorker());
		worker.start();
	}

	public void submitTask(Runnable task)
	{
		taskQueue.add(task);
	}

	public void shutdown()
	{
		shutdown = true;
	}

	private class ThreadPoolWorker implements Runnable
	{
		public ThreadPoolWorker()
		{
		}

		public void run()
		{
			int empty_count = 0;
			Runnable task;

			while (!shutdown)
			{
				if (taskQueue.isEmpty())
				{
					// If we are just spinning, start sleeping
					if (empty_count > 5)
					{
						try
						{
							Thread.sleep(50);
						}
						catch (InterruptedException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else
					{
						empty_count++;
						Thread.yield();
					}
				}
				else
				{
					for (int i = 0; i < pool.length; i++)
					{
						if (pool[i] == null || pool[i].getState() == Thread.State.TERMINATED)
						{
							task = taskQueue.poll();

							if (task == null)
								break;

							pool[i] = new Thread(task);
							pool[i].start();
						}
					}
					empty_count = 0;
				}
			}
		}

	}

}
