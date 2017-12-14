
public class Job implements Comparable<Job> {

    private int jobID;
    private int arrivalTime;
    private int burstTime;
    private int startTime;
    private int endTime;
    private int remainingTime;
    
    public Job(int jobID, int arrivalTime, int burstTime) {
        setJobID(jobID);
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        startTime = -1;
    }

    public int getJobID() {
        return jobID;
    }

    public void setJobID(int jobID) {
        this.jobID = jobID;
    }
    
    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }
    
    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }
    
    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }
    
    @Override
    public int compareTo(Job j) {
        if(burstTime > j.burstTime) {
            return 1;
        }
        else if(burstTime < j.burstTime) {
            return -1;
        }
        else if(arrivalTime > j.arrivalTime) {
            return 1;
        }
        else if(arrivalTime < j.arrivalTime) {
            return -1;
        }
        else if(jobID > j.jobID) {
            return 1;
        }
        else if(jobID < j.jobID) {
            return -1;
        }
        return 0;
    }

}
