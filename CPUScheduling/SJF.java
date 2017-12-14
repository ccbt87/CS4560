import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;


public class SJF {

    private ArrayList<Job> jobList;
    private ArrayList<Job> reportJobList;
    int clock;
    int cpuIdleTime;
    
    public SJF(ArrayList<Job> jobList) {
        this.jobList = jobList;
        reportJobList = new ArrayList<Job>();
        clock = 0;
        cpuIdleTime = 0;
    }
    
    public void dispatch() {
        
        Job cpuJob = null;  // null means CPU is idle
        PriorityQueue<Job> readyQueue = new PriorityQueue<Job>(); // use priority queue to sort the ready queue by burst time
        
        // continue run if there's job still being processed
        while(!readyQueue.isEmpty() || !jobList.isEmpty() || cpuJob != null) {
            // when it is the job arrival time, put job into the ready queue
            Iterator<Job> jobIterator = jobList.iterator();
            while(jobIterator.hasNext()){
                Job j = jobIterator.next();
                if(j.getArrivalTime() == clock) {
                    readyQueue.add(j);
                    jobIterator.remove();
                }
            }
            // when cpu is idle and there is a job in the ready queue, process that job
            if(cpuJob == null && !readyQueue.isEmpty()) {
                Job job = readyQueue.poll();
                job.setStartTime(clock);
                //System.out.println("Run Job ID: " + job.getJobID());
                cpuJob = job;
                reportJobList.add(job);                
            } 
            clock++;    // time elapse
            if(cpuJob == null) {   // if cpu is idle, increase the idle time
                cpuIdleTime++;
            }   // if cpu is processing a job, check if job is finished or not
            else if(cpuJob.getStartTime() + cpuJob.getBurstTime() == clock) {
                cpuJob.setEndTime(clock);            
                cpuJob = null;
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
