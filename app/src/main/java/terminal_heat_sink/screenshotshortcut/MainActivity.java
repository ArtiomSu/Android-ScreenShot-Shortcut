package terminal_heat_sink.screenshotshortcut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private final String FIRST_RUN = "terminal_heat_sink.screenshotshortcut.first_run";

    private int write_to_sys(String command){
        Context context = getApplicationContext();
        Process p;
        try {
            p = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(p.getOutputStream());
            os.writeBytes(command);
            os.writeBytes("exit\n");
            os.flush();
            try {
                p.waitFor();
                if (p.exitValue() != 255) {

                    if(p.exitValue() == 0){
                        Log.i("SystemWriter","wrote successfully "+command);

                    }else{
                        Log.i("SystemWriter","failed to write");
                        Toast toast = Toast.makeText(context, "Could not write please allow ScreenShot Shortcut root access in magisk", Toast.LENGTH_LONG);
                        toast.show();
                    }
                    return 1;
                }
                else {
                    Log.i("SystemWriter","not rooted 1");
                    Toast toast = Toast.makeText(context, "root required please root your phone", Toast.LENGTH_SHORT);
                    toast.show();
                }
            } catch (InterruptedException e) {
                Log.i("SystemWriter","not rooted 2");
                Toast toast = Toast.makeText(context, "root required please root your phone", Toast.LENGTH_SHORT);
                toast.show();
            }
        } catch (IOException e) {
            Log.i("SystemWriter","not rooted 3");
            Toast toast = Toast.makeText(context, "root required please root your phone", Toast.LENGTH_SHORT);
            toast.show();
        }
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences prefs = getApplicationContext().getSharedPreferences(
                "terminal_heat_sink.screenshotshortcut", Context.MODE_PRIVATE);
        if(! prefs.getBoolean(FIRST_RUN,false)){
            if(write_to_sys("magisk --sqlite \"UPDATE policies SET notification = 0 WHERE package_name LIKE 'terminal_heat_sink.screenshotshortcut'\" \n") == 1){
                prefs.edit().putBoolean(FIRST_RUN,true).apply();
            }else{
                prefs.edit().putBoolean(FIRST_RUN,false).apply();
            }
        }

        write_to_sys("input keyevent 120\n");
    }
}