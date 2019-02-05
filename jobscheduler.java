import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;


public class jobscheduler{

    public static void main(String[] args) throws IOException{
        RedBlackTree rbt = new RedBlackTree();

        PriorityQueue<Job> minHeap=new PriorityQueue<Job>(new MyComparator());


        //BufferedReader f = new BufferedReader(new FileReader(args[0]));
        //BufferedReader f = new BufferedReader(new FileReader("sample_input3.txt"));
        //Scanner scanner = new Scanner(new File("sample_input3.txt"));
        Scanner scanner = new Scanner(new File(args[0]));
        File of = new File("sample_output.txt");
        FileOutputStream fop = new FileOutputStream(of);
        OutputStreamWriter writer = new OutputStreamWriter(fop, "UTF-8");


        String line = "";
        int time = 0;
        while (scanner.hasNext()){
            line = scanner.nextLine().trim();
            //System.out.println(line);
            int input_time = Integer.valueOf(line.split(":")[0].trim());
            String command = line.split(":")[1].split("\\(")[0].trim();
            //System.out.println("time : " + time);
            //System.out.println("input time : " + input_time);
            //System.out.println(command);

            for (; time <= input_time; time += 1){
                if (!minHeap.isEmpty()){
                    Job todojob = minHeap.poll();
                    todojob.addExe_time(1);
                    if ((time % 5) == 0){
                        if (todojob.getTotal_time() - todojob.getExe_time() < 5){
                            rbt.deleteNode(todojob.Id);
                        }
                        else{ minHeap.offer(todojob);}
                    }
                    else{ minHeap.offer(todojob);}
                }

            }
//            for (; time <= input_time; time += 5){
//                if (!minHeap.isEmpty()){
//                    Job todojob = minHeap.poll();
//                    if (todojob.getTotal_time() - todojob.getExe_time() >= 5){
//                        todojob.addExe_time(5);
//                        //rbt.Increase(todojob.getId(), 5);
//                        minHeap.offer(todojob);
//                    }else //if (todojob.getTotal_time() - todojob.getExe_time() >= 0)
//                        {
//                        rbt.deleteNode(todojob.Id);
//                    }
//                }
//            }

            if (command.equals("Insert")){
                String tmp = line.split(":")[1].split("\\(")[1];
                int input_id = Integer.valueOf(tmp.split(",")[0]);
                int input_total_time = Integer.valueOf(tmp.split(",")[1].split("\\)")[0]);
                //System.out.println(input_id);
                //System.out.println(input_total_time);

                Job job = new Job(input_id, 0, input_total_time);
                rbt.insert(job);
                minHeap.offer(job);

            }else if (command.equals("PrintJob")){
                String tmp = line.split(":")[1].split("\\(")[1].split("\\)")[0];
                //System.out.println(tmp);
                if (tmp.split(",").length == 2){
                    int input_id1 = Integer.valueOf(tmp.split(",")[0]);
                    int input_id2 = Integer.valueOf(tmp.split(",")[1]);
                    //System.out.println(input_id1);
                    //System.out.println(input_id2);
                    ArrayList<Job> jobs = rbt.printRange(input_id1, input_id2);
                    if (jobs == null){
                        //System.out.print("(0,0,0)");
                        writer.append("(0,0,0)");
                    }else {
                        for(int i = 0; i < jobs.size() - 1; i++){
                            writer.append("(" + jobs.get(i).Id + ","
                                    + jobs.get(i).exe_time + "," + jobs.get(i).total_time + "),");
                        }
                        writer.append("(" + jobs.get(jobs.size() - 1).Id + ","
                                + jobs.get(jobs.size() - 1).exe_time + "," + jobs.get(jobs.size() - 1).total_time + ")");
                    }
                    //System.out.print("\n");
                    writer.append("\n");
                }else{
                    int input_id = Integer.valueOf(tmp);
                    //System.out.println(input_id);
                    Job jobtoprint = rbt.printJob(input_id);
                    if (jobtoprint == null){
                        writer.append("(0,0,0)");
                    }else {
                        writer.append("(" + jobtoprint.Id + ","
                                + jobtoprint.exe_time + "," + jobtoprint.total_time + ")");
                    }

                    //System.out.print("\n");
                    writer.append("\n");
                }
            }else if (command.equals("NextJob")){
                int input_id = Integer.valueOf(line.split("\\(")[1].split("\\)")[0]);
                //System.out.println(input_id);
                Job jobtoprint = rbt.nextJob(input_id);
                if (jobtoprint == null){
                    writer.append("(0,0,0)");
                }else {
                    writer.append("(" + jobtoprint.Id + ","
                            + jobtoprint.exe_time + "," + jobtoprint.total_time + ")");
                }
                //System.out.print("\n");
                writer.append("\n");
            }else if (command.equals("PreviousJob")){
                int input_id = Integer.valueOf(line.split("\\(")[1].split("\\)")[0]);
                //System.out.println(input_id);
                Job jobtoprint = rbt.prevJob(input_id);
                if (jobtoprint == null){
                    writer.append("(0,0,0)");
                }else {
                    writer.append("(" + jobtoprint.Id + ","
                            + jobtoprint.exe_time + "," + jobtoprint.total_time + ")");
                }
                //System.out.print("\n");
                writer.append("\n");
            }else{
                System.out.println("Wrong input command.");
                return;
            }
        }

        writer.close();
        scanner.close();
    }
}

class MyComparator implements Comparator<Job>
{
    public int compare( Job job1, Job job2 )
    {
        return job1.exe_time - job2.exe_time;
    }
}


