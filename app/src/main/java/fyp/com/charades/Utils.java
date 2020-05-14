package fyp.com.charades;

import android.app.AlertDialog;
import android.content.Context;

import fyp.com.charades.Api.CharadesClient;
import retrofit2.Retrofit;

public class Utils {
    public static fyp.com.charades.Api.CharadesApi getCharadesApi() {
        return fyp.com.charades.Api.CharadesClient.getCharadesClient().create(fyp.com.charades.Api.CharadesApi.class);
    }

    public static Retrofit getRetrofitAttribute(){
        return CharadesClient.retrofit;
    }

    public static AlertDialog showDialogMessage(Context context, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).setTitle(title).setMessage(message).show();
        if (alertDialog.isShowing()) {
            alertDialog.cancel();
        }
        return alertDialog;
    }
}
