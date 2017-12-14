import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class Simulator {
    
    private int jobNumber;
    private int minBurstTime;
    private int maxBurstTime;
    private int maxArrivalInterval;
    private ArrayList<Job> jobList;
    private ArrayList<Job> jobListCopy;
    
    public Simulator(int jobNumber, int minBurstTime, int maxBurstTime, int maxArrivalInterval) {
        this.jobNumber = jobNumber;
        this.minBurstTime = minBurstTime;
        this.maxBurstTime = maxBurstTime;
        this.maxArrivalInterval = maxArrivalInterval;
        jobList = new ArrayList<Job>();
        jobListCopy = new ArrayList<Job>();
    }
    
    public void generateJobList() {
        Random rand = new Random();
        int arrivalTime = 0;
        int burstTime;
        jobList = new ArrayList<Job>();
        System.out.println("Job ID" + "\t| " + "Arrival Time" + "\t| " + "Burst Time");
        for(int i = 0; i < jobNumber; i++) {           
            burstTime = rand.nextInt((maxBurstTime - minBurstTime) + 1) + minBurstTime;
            System.out.println(i + "\t|\t" + arrivalTime + "\t|\t" + burstTime);
            jobList.add(new Job(i, arrivalTime, burstTime));
            jobListCopy.add(new Job(i, arrivalTime, burstTime));
            arrivalTime += rand.nextInt(maxArrivalInterval);
        }
    }
    
    public double getAverageTurnaroundTime(ArrayList<Job> reportJobList) {
        int totalTurnaroundTime = 0;
        Iterator<Job> jobIterator = reportJobList.iterator();
        while(jobIterator.hasNext()){
            Job j = jobIterator.next();
            totalTurnaroundTime += j.getEndTime() - j.getArrivalTime();
        }        
        return  totalTurnaroundTime * 1.0 / reportJobList.size();
    }
    
    public double getAverageWaitingTime(ArrayList<Job> reportJobList) {
        int totalWaitingTime = 0;
        Iterator<Job> jobIterator = reportJobList.iterator();
        while(jobIterator.hasNext()){
            Job j = jobIterator.next();
            totalWaitingTime += j.getEndTime() - j.getArrivalTime() - j.getBurstTime();
        }     
        return  totalWaitingTime * 1.0 / reportJobList.size();
    }
    
    public double getAverageResponseTime(ArrayList<Job> reportJobList) {
        int totalResponseTime = 0;
        Iterator<Job> jobIterator = reportJobList.iterator();
        while(jobIterator.hasNext()){
            Job j = jobIterator.next();
            totalResponseTime += j.getStartTime() - j.getArrivalTime();
        }        
        return  totalResponseTime * 1.0 / reportJobList.size();
    }
    
    public static void main(String[] args) {
        
        int jobNumber = 10;
        int minBurstTime = 2;
        int maxBurstTime = 42;
        int maxArrivalInterval = 10;
        Simulator s = new Simulator(jobNumber, minBurstTime, maxBurstTime, maxArrivalInterval);
        // Randomly generate job list
        s.generateJobList();        
        // Manually generate job list
        /*
        s.jobList.add(new Job(0,0,24));
        s.jobList.add(new Job(1,0,3));
        s.jobList.add(new Job(2,15,3));
        
        s.jobListCopy.add(new Job(0,0,24));
        s.jobListCopy.add(new Job(1,0,3));
        s.jobListCopy.add(new Job(2,15,3));
        */
        
        // Run and output
        System.out.println("\n=============SJF=============");
        SJF sjf = new SJF(s.jobList);
        sjf.dispatch();       
        ArrayList<Job> reportJobList = sjf.getReportJobList();
        int cpuUtilization = (int)((1 - (sjf.getCpuIdleTime() * 1.0 / sjf.getClock())) * 100);
        int averageThroughput = (int)(reportJobList.size() * 1000.0 / sjf.getClock() / cpuUtilization * 100);
        System.out.println("CPU utilization: " + cpuUtilization + "%");
        System.out.println("Average Throughput: " + averageThroughput + " jobs per second");
        System.out.println("Average Turnaround Time: " + s.getAverageTurnaroundTime(reportJobList)  + " ms");
        System.out.println("Average Waiting Time: " + s.getAverageWaitingTime(reportJobList) + " ms");
        System.out.println("Average Response Time: " + s.getAverageResponseTime(reportJobList) + " ms");

        System.out.println("\n=============RR=============");
        int quantumTime = 10;
        RR rr = new RR(s.jobListCopy, quantumTime);
        rr.dispatch();
        ArrayList<Job> reportJobList2 = rr.getReportJobList();
        int cpuUtilization2 = (int)((1 - (rr.getCpuIdleTime() * 1.0 / rr.getClock())) * 100);
        int averageThroughput2 = (int)(reportJobList2.size() * 1000.0 / rr.getClock() / cpuUtilization2 * 100);
        System.out.println("CPU utilization: " + cpuUtilization2 + "%");
        System.out.println("Average Throughput: " + averageThroughput2 + " jobs per second");
        System.out.println("Average Turnaround Time: " + s.getAverageTurnaroundTime(reportJobList2)  + " ms");
        System.out.println("Average Waiting Time: " + s.getAverageWaitingTime(reportJobList2) + " ms");
        System.out.println("Average Response Time: " + s.getAverageResponseTime(reportJobList2) + " ms");
    }

}
