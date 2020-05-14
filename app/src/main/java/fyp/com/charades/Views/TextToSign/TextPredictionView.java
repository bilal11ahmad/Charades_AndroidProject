package fyp.com.charades.Views.TextToSign;

import fyp.com.charades.Models.TextPrediction;

public interface TextPredictionView {
    void showLoading();
    void hideLoading();
    void setPrediction(TextPrediction predictionList);
    void onErrorLoading(String message);
}
