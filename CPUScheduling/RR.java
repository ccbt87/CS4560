import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;


public class RR {

    private ArrayList<Job> jobList;
    private int quantumTime;
    private ArrayList<Job> reportJobList;
    int clock;
    int cpuIdleTime;
    
    public RR(ArrayList<Job> jobList, int quantumTime) {
        this.jobList = jobList;
        this.quantumTime = quantumTime;        
        reportJobList = new ArrayList<Job>();
        clock = 0;
        cpuIdleTime = 0;
    }
    
    public void dispatch() {
                
        int runningTime = 0;    // used to count quantum time
        Job cpuJob = null;      // null means CPU is idle
        Queue<Job> readyQueue = new LinkedList<Job>();
        
        // continue run if there's job still being processed
        while(!readyQueue.isEmpty() || !jobList.isEmpty() || cpuJob != null) {
            // when it is the job arrival time, put job into the ready queue
            Iterator<Job> jobIterator = jobList.iterator();
            while(jobIterator.hasNext()){
                Job j = jobIterator.next();
                if(j.getArrivalTime() == clock) {
                    j.setRemainingTime(j.getBurstTime());
                    readyQueue.add(j);
                    jobIterator.remove();
                }
            }   
            // when cpu is idle and there is a job in the ready queue, process that job
            if(cpuJob == null && !readyQueue.isEmpty()) {
                Job job = readyQueue.poll();
                //System.out.println("Run Job ID: " + job.getJobID());
                if(job.getStartTime() == -1) {  // if it's a brand new job, set the start time
                    job.setStartTime(clock);
                }                      
                cpuJob = job;           
            }             
            clock++;    // time elapse
            if(cpuJob == null) {  // if cpu is idle, increase the idle time
                cpuIdleTime++;
            }
            else {   // if cpu is processing a job
                runningTime++;
                int remainingTime = cpuJob.getRemainingTime();                
                remainingTime--;
                cpuJob.setRemainingTime(remainingTime); 
                if(remainingTime == 0) {  // job finished
                    cpuJob.setEndTime(clock);
                    reportJobList.add(cpuJob);
                    runningTime = 0;
                    cpuJob = null;
                }
                else if(runningTime == quantumTime) { // job not finished but quantum time reached
                    readyQueue.add(cpuJob);
                    runningTime = 0;
                    cpuJob = null;
                }
            }                        

        }
    }
    
    public ArrayList<Job> getReportJobList() {
        return reportJobList;
    }
       
    public int getClock() {
        return clock;
    }
    
    public int getCpuIdleTime() {
        return cpuIdleTime;
    }
    
}
