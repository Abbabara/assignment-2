package com.ru.usty.scheduling;

import com.ru.usty.scheduling.process.ProcessExecution;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Scheduler {

	ProcessExecution processExecution;
	Policy policy;
	int quantum;
	Queue <Integer> queue = new LinkedList<Integer>(); 
	List<Integer> array = new ArrayList<Integer>();
	int processArrayPlace;
	boolean processIsRunning = false;
	/**
	 * Add any objects and variables here (if needed)
	 */


	/**
	 * DO NOT CHANGE DEFINITION OF OPERATION
	 */
	public Scheduler(ProcessExecution processExecution) {
		this.processExecution = processExecution;

		/**
		 * Add general initialization code here (if needed)
		 */
	}

	/**
	 * DO NOT CHANGE DEFINITION OF OPERATION
	 */
	public void startScheduling(Policy policy, int quantum) {

		this.policy = policy;
		this.quantum = quantum;

		/**
		 * Add general initialization code here (if needed)
		 */

		switch(policy) {
		case FCFS:	//First-come-first-served
			System.out.println("Starting new scheduling task: First-come-first-served");
			/**
			 * Add your policy specific initialization code here (if needed)
			 */
			break;
		case RR:	//Round robin
			System.out.println("Starting new scheduling task: Round robin, quantum = " + quantum);
			/**
			 * Add your policy specific initialization code here (if needed)
			 */
			break;
		case SPN:	//Shortest process next
			System.out.println("Starting new scheduling task: Shortest process next");
			/**
			 * Add your policy specific initialization code here (if needed)
			 */
			break;
		case SRT:	//Shortest remaining time
			System.out.println("Starting new scheduling task: Shortest remaining time");
			/**
			 * Add your policy specific initialization code here (if needed)
			 */
			break;
		case HRRN:	//Highest response ratio next
			System.out.println("Starting new scheduling task: Highest response ratio next");
			/**
			 * Add your policy specific initialization code here (if needed)
			 */
			break;
		case FB:	//Feedback
			System.out.println("Starting new scheduling task: Feedback, quantum = " + quantum);
			/**
			 * Add your policy specific initialization code here (if needed)
			 */
			break;
		}

		/**
		 * Add general scheduling or initialization code here (if needed)
		 */

	}

	

	/**
	 * DO NOT CHANGE DEFINITION OF OPERATION
	 */
	public void processAdded(int processID) {
		switch(policy) {
		case FCFS:	//First-come-first-served
			//adding the process to queue
			queue.add(processID);
			//switching to head of queue
			processExecution.switchToProcess(queue.peek());	
		
			break;
		case RR:	//Round robin
			/*queue.add(processID);
			
			System.out.println(queue);
			if(!queue.isEmpty()) {
				processExecution.switchToProcess(queue.peek());
				
				if(processExecution.getProcessInfo(queue.peek()).elapsedExecutionTime 
						< processExecution.getProcessInfo(queue.peek()).totalServiceTime) {
					queue.add(queue.peek());
					System.out.println(queue);
				}
				queue.remove();
				System.out.println(queue);
			} */
			break;
		case SPN:	//Shortest process next
			//adding process to Array
			array.add(processID);
			
			//If no process is running
			if(processIsRunning == false) {
				processIsRunning = true;
				//finding the place of the shortest process in the array
				processArrayPlace = findShortestInArray();	
			}
			//switching to shortest process
			processExecution.switchToProcess(array.get(processArrayPlace));
			break;
		case SRT:	//Shortest remaining time
			//adding process to Array
			array.add(processID);
			
	
			//finding the place of the shortest process in the array
			processArrayPlace = findShortestElapsedExecutionTimeInArray();	
			//switching to shortest process
			processExecution.switchToProcess(array.get(processArrayPlace));
			break;
		case HRRN:	//Highest response ratio next
			array.add(processID);
			if(processIsRunning == false) {
				processIsRunning = true;
				processArrayPlace = findHighestResponseRatioNext();
			}
			processExecution.switchToProcess(array.get(processArrayPlace));
			break;
		case FB:	//Feedback
			break;
		} 
	}
	


	/**
	 * DO NOT CHANGE DEFINITION OF OPERATION
	 */
	public void processFinished(int processID) {
		
		
		switch(policy) {
		case FCFS:	//First-come-first-served
			//remove head
			queue.remove();
			
			if(!queue.isEmpty()) {
				//switching to head
				processExecution.switchToProcess(queue.peek());	
			}
			break;
		case RR:	//Round robin
	
			break;
		case SPN:	//Shortest process next
			//removing the finished process
			array.remove(processArrayPlace);
			
			if(!array.isEmpty()) {
				//finding the shortest process in array
				processArrayPlace = findShortestInArray();
				//switching to the shortest array
				processExecution.switchToProcess(array.get(processArrayPlace));
			} else {
				processArrayPlace = -0;
			}
			break;
		case SRT:	//Shortest remaining time
			//removing the finished process
			array.remove(processArrayPlace);
			
			if(!array.isEmpty()) {
				//finding the shortest process in array
				processArrayPlace = findShortestElapsedExecutionTimeInArray();
				//switching to the shortest array
				processExecution.switchToProcess(array.get(processArrayPlace));
			} else {
				processArrayPlace = -0;
			}
			break;
		case HRRN:	//Highest response ratio next
			array.remove(processArrayPlace);
			
			if(!array.isEmpty()) {
				//finding the shortest process in array
				processArrayPlace = findHighestResponseRatioNext();
				//switching to the shortest array
				processExecution.switchToProcess(array.get(processArrayPlace));
			} else {
				processArrayPlace = -0;
			}
			break;
		case FB:	//Feedback
			break;
		} 
	}
	
	//Finding the shortest process
	private int findShortestInArray() {

		int shortestArrayPlace = 0;
		//time of the shortest known process
		long shortest = processExecution.getProcessInfo(array.get(shortestArrayPlace)).totalServiceTime;
		
		for(int i = 0; i < array.size(); i++) {

			//time of the process we are comparing
			long time = processExecution.getProcessInfo(array.get(i)).totalServiceTime;
			
			if( time < shortest) {
				//changing the shortest process
				shortestArrayPlace = i;
				shortest = time;
			}
		}
		return shortestArrayPlace;
	}
	
	private int findShortestElapsedExecutionTimeInArray() {

		int shortestArrayPlace = 0;
		
		long shortest = processExecution.getProcessInfo(array.get(shortestArrayPlace)).totalServiceTime -
				processExecution.getProcessInfo(array.get(shortestArrayPlace)).elapsedExecutionTime;
		
		for(int i = 0; i < array.size(); i++) {

			//time of the process we are comparing
			long time = processExecution.getProcessInfo(array.get(i)).totalServiceTime -
					processExecution.getProcessInfo(array.get(i)).elapsedExecutionTime;
			
			if( time < shortest) {
				//changing the shortest process
				shortestArrayPlace = i;
				shortest = time;
			}
		}
		return shortestArrayPlace;
	}

	
	private int findHighestResponseRatioNext() {
		int highestResponseRatio = 0;
		
		long highest = r(processExecution.getProcessInfo(array.get(highestResponseRatio)).elapsedWaitingTime, 
				processExecution.getProcessInfo(array.get(highestResponseRatio)).totalServiceTime);
		
		for(int i = 0; i < array.size(); i++) {

			
			long current =r(processExecution.getProcessInfo(array.get(i)).elapsedWaitingTime, 
					processExecution.getProcessInfo(array.get(i)).totalServiceTime);
			
			if (current > highest) {
				highestResponseRatio = i;
				highest = current;
			}
		}
		
		return highestResponseRatio;	
	}
	private long r(long x, long y) {
		return (x + y)/y;
	}
}
