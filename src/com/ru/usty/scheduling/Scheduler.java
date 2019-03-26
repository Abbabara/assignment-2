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
	Queue <Integer> fcfsQueue = new LinkedList<Integer>(); 
	List<Integer> spnArray = new ArrayList();
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
			fcfsQueue.add(processID);
			//switching to head of queue
			processExecution.switchToProcess(fcfsQueue.peek());	
		
			break;
		case RR:	//Round robin

			break;
		case SPN:	//Shortest process next
			//adding process to Array
			spnArray.add(processID);
			
			//If no process is running
			if(processIsRunning == false) {
				processIsRunning = true;
				//finding the place of the shortest process in the array
				processArrayPlace = findShortestInArray();	
			}
			//switching to shortest process
			processExecution.switchToProcess(spnArray.get(processArrayPlace));
			break;
		case SRT:	//Shortest remaining time
			
			break;
		case HRRN:	//Highest response ratio next
		
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
			fcfsQueue.remove();
			
			if(!fcfsQueue.isEmpty()) {
				//switching to head
				processExecution.switchToProcess(fcfsQueue.peek());	
			}
			break;
		case RR:	//Round robin

			break;
		case SPN:	//Shortest process next
			//removing the finished process
			spnArray.remove(processArrayPlace);
			
			if(!spnArray.isEmpty()) {
				//finding the shortest process in array
				processArrayPlace = findShortestInArray();
				//switching to the shortest array
				processExecution.switchToProcess(spnArray.get(processArrayPlace));
			}
			break;
		case SRT:	//Shortest remaining time
			
			break;
		case HRRN:	//Highest response ratio next
		
			break;
		case FB:	//Feedback
			break;
		} 
	}
	
	//Finding the shortest process
	private int findShortestInArray() {

		int shortestArrayPlace = 0;
		
		for(int i = 0; i < spnArray.size(); i++) {
			//time of the shortest known process
			long shortest = processExecution.getProcessInfo(spnArray.get(shortestArrayPlace)).totalServiceTime;
			//time of the process we are comparing
			long time = processExecution.getProcessInfo(spnArray.get(i)).totalServiceTime;
			
			if( time < shortest) {
				//changing the shortest process
				shortestArrayPlace = i;
			}
		}
		return shortestArrayPlace;
	}
}
