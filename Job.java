/**
 * Created by yc on 3/24/18.
 */
public class Job {
    int Id;
    int exe_time;
    int total_time;
    public Job(int Id, int exe_time, int total_time){
        this.Id = Id;
        this.exe_time = exe_time;
        this.total_time = total_time;
    }
    public int getExe_time(){
        return this.exe_time;
    }
    public void addExe_time(int t){
        this.exe_time = this.exe_time + t;
    }
    public int getTotal_time(){
        return this.total_time;
    }
    public int getId(){
        return this.Id;
    }
}
