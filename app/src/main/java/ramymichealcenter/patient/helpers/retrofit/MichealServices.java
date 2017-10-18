package ramymichealcenter.patient.helpers.retrofit;

import java.util.List;

import okhttp3.MultipartBody;
import ramymichealcenter.patient.viewmodel.BookReservationViewModel;
import ramymichealcenter.patient.viewmodel.GetAllPostsViewModel;
import ramymichealcenter.patient.viewmodel.GetAllReservationViewModel;
import ramymichealcenter.patient.viewmodel.GetAllSlidersViewModel;
import ramymichealcenter.patient.viewmodel.GetCommunicationViewModel;
import ramymichealcenter.patient.viewmodel.GetPostViewModel;
import ramymichealcenter.patient.viewmodel.GetServicesViewModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by mhasan on 5/21/2017.
 */

public interface MichealServices {

    @GET("services")
    Call<GetServicesViewModel> getDoctorServices();

    /*--------------------------------------------*/
    /*--------------------------------------------*/

    @GET("getCommunication")
    Call<GetCommunicationViewModel> getCommunication();

    @GET("_getAllReservation/{service_id}")
    Call<GetAllReservationViewModel> getAllReservation(@Path("service_id") int service_id);

    @Multipart
    @POST("bookReservation")
    Call<BookReservationViewModel> bookReservation(@Part List<MultipartBody.Part> test);

    /*--------------------------------------------*/
    /*--------------------------------------------*/

    @GET("getAllPosts")
    Call<GetAllPostsViewModel> getAllPosts();

    @GET("getPost/{postId}")
    Call<GetPostViewModel> getPost(@Path("postId") int postId);

    /*--------------------------------------------*/
    /*--------------------------------------------*/

    @GET("getSlider")
    Call<GetAllSlidersViewModel> getAllSliders();

}
