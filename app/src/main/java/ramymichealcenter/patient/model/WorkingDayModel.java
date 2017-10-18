package ramymichealcenter.patient.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mostafa on 9/9/2017.
 */

public class WorkingDayModel {

    @SerializedName("working_days_id")
    private Integer workingDaysId;

    @SerializedName("working_day")
    private String workingDay;

    /*--------------------------------------------*/
    public Integer getWorkingDaysId() {
        return workingDaysId;
    }

    public void setWorkingDaysId(Integer workingDaysId) {
        this.workingDaysId = workingDaysId;
    }

    public String getWorkingDay() {
        return workingDay;
    }

    public void setWorkingDay(String workingDay) {
        this.workingDay = workingDay;
    }

    /*--------------------------------------------*/
    @Override
    public String toString() {
        return "{" +
                "workingDaysId=" + workingDaysId +
                ", workingDay='" + workingDay + '\'' +
                '}';
    }
}
