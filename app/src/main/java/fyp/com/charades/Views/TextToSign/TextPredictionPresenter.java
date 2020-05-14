package fyp.com.charades.Views.TextToSign;

import androidx.annotation.NonNull;
import fyp.com.charades.Models.TextPrediction;
import fyp.com.charades.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TextPredictionPresenter {

    TextPredictionView view;

    public TextPredictionPresenter(TextPredictionView view) {
        this.view = view;
    }

    void getPrediction(String input){
        view.showLoading();
        Call<TextPrediction> predictionCall = Utils.getCharadesApi().getTextPrediction(input);
        predictionCall.enqueue(new Callback<TextPrediction>() {
            @Override
            public void onResponse(@NonNull Call<TextPrediction> call, Response<TextPrediction> response) {
                view.hideLoading();
                if(response.isSuccessful() && response.body() != null){
                    view.setPrediction(response.body());
                } else {
                    view.onErrorLoading(response.message());
                }

            }

            @Override
            public void onFailure(@NonNull Call<TextPrediction> call, Throwable t) {
                view.hideLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });

    }
}
