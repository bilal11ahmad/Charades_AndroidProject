package fyp.com.charades.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TextPrediction {
    @SerializedName("prediction")
    @Expose
    private String prediction;

    public String getPrediction() {
        return prediction;
    }

    public void setPrediction(String prediction) {
        this.prediction = prediction;
    }
}
