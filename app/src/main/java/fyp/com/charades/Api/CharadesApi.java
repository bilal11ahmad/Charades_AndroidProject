package fyp.com.charades.Api;

import fyp.com.charades.Models.TextPrediction;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CharadesApi {
    @GET("predicttext/{sentence}")
    Call<TextPrediction> getTextPrediction(@Path("sentence") String inputSentence);
}
