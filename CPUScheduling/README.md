This Java program simulates and compares two CPU Scheduling algorithms, SJF (Shortest Job First) and RR (Round Robin). 

Test inputs were randomly generated.

![Test run 1:](/markdown/test1.png)

![Test run 2:](/markdown/test2.png)

CPU utilization and Throughput don’t have difference between SJF and RR when running the same job list. In this simulation, CPU utilization decreases only when the current job finished and the next job hasn’t arrived yet. Throughput on a same set of jobs are the same due to this simulation doesn’t consider the algorithm efficiency, context-switch time, etc.

SJF wins on the turnaround time and the waiting time. When a job comes in, it’s more likely to be finished early when using SJF against RR. SJF will finish one job and go to the next, while RR won’t finish the job if the burst time longer than the quantum time. RR goes to other jobs in the queue and then go back.

RR wins on the response time. Because RR only spends a small amount of time on one job and goes to the next, it has a better response time than SJF. The response time of SJF is equal to its waiting time.

Which one if better? It’s a tradeoff. If the system requires fast response time, RR is better. If the system requires less turnaround time and waiting time, SJF is better.
