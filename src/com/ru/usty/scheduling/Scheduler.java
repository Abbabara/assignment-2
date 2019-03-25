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
	int processRunning = -1;
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
			
			fcfsQueue.add(processID);
			if(!fcfsQueue.isEmpty()) {
				processExecution.switchToProcess(fcfsQueue.peek());	
			}
			
			break;
		case RR:	//Round robin

			break;
		case SPN:	//Shortest process next
			spnArray.add(processID);
			if(processRunning == -1) {
				processRunning = findShortest();
				processExecution.switchToProcess(processRunning);	
			} else {
				processExecution.switchToProcess(processRunning);	
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
	


	/**
	 * DO NOT CHANGE DEFINITION OF OPERATION
	 */
	public void processFinished(int processID) {
		
		
		switch(policy) {
		case FCFS:	//First-come-first-served
			fcfsQueue.remove();
			System.out.println(fcfsQueue.size());
			
			if(!fcfsQueue.isEmpty()) {
				processExecution.switchToProcess(fcfsQueue.peek());	
			}
			break;
		case RR:	//Round robin

			break;
		case SPN:	//Shortest process next
			

			if(!spnArray.isEmpty()) {
				spnArray.remove(processRunning);
				processRunning = -1;
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
	
	private int getId(int processID) {
		
		for(int i = 0; i < spnArray.size(); i++) {
			if(processID == spnArray.get(i)) {
				return i;
			}
		}
		return 0;
	}
	
	private int findShortest() {

		int shortestId = 0;
		
		for(int i = 0; i < spnArray.size(); i++) {
			
			long shortest = processExecution.getProcessInfo(spnArray.get(shortestId)).totalServiceTime;
			long time = processExecution.getProcessInfo(spnArray.get(i)).totalServiceTime;
			
			if( time <= shortest) {
				shortestId = i;
			}
		}
		return shortestId;
	}
}
