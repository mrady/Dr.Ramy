package ramymichealcenter.patient.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mostafa on 9/23/2017.
 */

public class ReservationModel {

    @SerializedName("working_shift_types")
    private String workingShiftTypes;
    @SerializedName("reservationTime")
    private String reservationTime;
    @SerializedName("isReserved")
    private Boolean isReserved;

    /*---------------------------------*/

    public String getWorkingShiftTypes() {
        return workingShiftTypes;
    }

    public void setWorkingShiftTypes(String workingShiftTypes) {
        this.workingShiftTypes = workingShiftTypes;
    }

    public String getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(String reservationTime) {
        this.reservationTime = reservationTime;
    }

    public Boolean getReserved() {
        return isReserved;
    }

    public void setReserved(Boolean reserved) {
        isReserved = reserved;
    }

    /*---------------------------------*/
    @Override
    public String toString() {
        return "ReservationModel{" +
                "workingShiftTypes='" + workingShiftTypes + '\'' +
                ", reservationTime='" + reservationTime + '\'' +
                ", isReserved=" + isReserved +
                '}';
    }
}
