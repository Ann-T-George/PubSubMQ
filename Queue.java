package pubsubmq;

import java.util.PriorityQueue;

public class Queue {
    private PriorityQueue<Messages> messageQueue;

    public Queue() {
        messageQueue = new PriorityQueue<>();
    }

    public void enqueue(Messages message) {
        messageQueue.offer(message);
    }

    public Messages dequeue() {
        return messageQueue.poll();
    }

    public int size() {
        return messageQueue.size();
    }

	public boolean isEmpty() {
		return messageQueue.isEmpty();
	}

}

