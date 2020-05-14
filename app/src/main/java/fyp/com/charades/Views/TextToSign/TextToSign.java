package fyp.com.charades.Views.TextToSign;

import androidx.appcompat.app.AppCompatActivity;
import fyp.com.charades.Models.TextPrediction;
import fyp.com.charades.R;
import fyp.com.charades.Variables;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.arthenica.mobileffmpeg.Config;
import com.arthenica.mobileffmpeg.FFmpeg;
import com.opencsv.CSVReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_CANCEL;
import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_SUCCESS;

public class TextToSign extends AppCompatActivity implements TextPredictionView {

    TextPredictionPresenter presenter;

    EditText inputText;
    Button translate_btn,repeat_btn;
    VideoView sign_videoView;
    ProgressBar progressBar;
    private int count = 0;

    private String textstr = "no change";
    private String file = "my file";
    private String[] tokens = new String[30];
    private String[] poseids = new String[30];

    private String[] files = new String[30];
    //    int countVideo = Variables.getCount();
//    private String[]  fileline = new String[countVideo];
    private ArrayList<String> videoPath = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_sign);
        inputText=(EditText)findViewById(R.id.Input_EditText);
        translate_btn=(Button)findViewById(R.id.Convert_Button);
        repeat_btn=(Button)findViewById(R.id.Repeat_Button);
        repeat_btn.setEnabled(false);
        sign_videoView=(VideoView)findViewById(R.id.Sign_VideoView);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        presenter = new TextPredictionPresenter(this);
        repeat_btn.setEnabled(false);
        ButtonEvents();
    }

    private void ButtonEvents() {
        translate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = inputText.getText().toString();
                presenter.getPrediction(input);

            }
        });
        repeat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignVideo();

            }
        });
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setPrediction(TextPrediction prediction) {
        //Toast.makeText(TextToSign.this,prediction.getPrediction().toString(),Toast.LENGTH_LONG).show();
        TranslateToSign(prediction.getPrediction());
    }

    @Override
    public void onErrorLoading(String message) {
        Toast.makeText(TextToSign.this,"Error: "+message,Toast.LENGTH_SHORT).show();
    }

    private void TranslateToSign(String text)
    {
        //String text = readFromFile();
        StringTokenizer st1 = new StringTokenizer(text);
        String[] filenames = new String[30];
        int id = 0;
        //Intent intent = new Intent(this, Main2Activity.class);

        for (int i = 0; st1.hasMoreTokens(); i++)
        {
            tokens[i] = st1.nextToken();
            poseids[i] = readCsv(tokens[i]);
//            Log.v(Config.TAG,"Pose ids="+poseids[i]);
        }

        while(poseids[id] != null)
        {
            int i = 0;
            if(poseids[id].equals("not found"))
            {
                id++;
            }else {
                filenames[i] = "pose_" + poseids[id];
                Variables.appendFile(filenames[i]);
                i++;
                id++;
                count++;
                Variables.setCount(count);
            }
        }

//        Log.v(Config.TAG,"count="+Variables.getCount());
        //startActivity(intent);
        showSignVideo();
        repeat_btn.setEnabled(true);
    }

    public String readCsv(String token)
    {
        String record = "";
        String id = "nothing";

        try
        {   InputStreamReader is = new InputStreamReader(getAssets().open("video_metadata.csv"));

            CSVReader reader = new CSVReader(is);
            reader.readNext();
            String[] nextRecord = reader.readNext();

            record = nextRecord[0].replace("+","");
            while (!token.equalsIgnoreCase(record))
            {
                if((nextRecord = reader.readNext()) != null)
                {
                    record = nextRecord[0].replace("+","");
                }else
                {
                    return "not found";
                }

            }
            id = nextRecord[6];
            return id;
        }catch(IOException ex)
        {
            ex.printStackTrace();
        }


        return "not found";
    }

    public int getCount(){
        return count;
    }

    private void writeToFile(String[] data) {

        try {
            FileWriter fw = new FileWriter("/storage/emulated/0/DCIM/join.txt",false);
            for(int i = 0; i< data.length; i++)
            {
                fw.write(data[i]);
                fw.write("\n");
            }
            fw.flush();
            fw.close();
        } catch (Exception e) {
            Toast.makeText(getBaseContext(),"file not found" , Toast.LENGTH_LONG).show();
        }
    }

    private void showSignVideo(){
        ArrayList<String> filenames = Variables.getFilename();
        int countVideo = Variables.getCount();
        String[]  fileline = new String[countVideo];

        for(int i = 0; i < filenames.size(); i++) {
            files[i] = filenames.get(i);
        }

        for(int id = 0 ; id < count; id++) {
            videoPath.add("/storage/emulated/0/DCIM/" + files[id] + ".mp4");
            fileline[id] = "file "+videoPath.get(id);
//            Log.v(Config.TAG,"filelines="+fileline[id]);
        }
        videoPath.clear();

        writeToFile(fileline);
        String mergepath = "/storage/emulated/0/DCIM/merged.mp4";

        int rc = FFmpeg.execute("-f concat -safe 0 -y -i /storage/emulated/0/DCIM/join.txt -c copy "+mergepath);
        if (rc == RETURN_CODE_SUCCESS) {
            Log.i(Config.TAG, "Command execution completed successfully.");
        } else if (rc == RETURN_CODE_CANCEL) {
            Log.i(Config.TAG, "Command execution cancelled by user.");
        } else {
            Log.i(Config.TAG, String.format("Command execution failed with rc=%d and the output below.", rc));
            Config.printLastCommandOutput(Log.INFO);
        }

        sign_videoView.setVideoPath(mergepath);
        sign_videoView.start();

    }
}