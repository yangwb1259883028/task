package com.fanwe.task;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.fanwe.lib.task.FTask;

public class MainActivity extends AppCompatActivity
{
    public static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickAddTask(View view)
    {
        new FTask(TAG)
        {
            @Override
            protected void onRun() throws Exception
            {
                Log.i(TAG, "----------onRun:" + Thread.currentThread().getName() + " count:" + FTask.getTask(TAG).size());
                long i = 0;
                while (i < 5)
                {
                    i++;
                    Log.i(TAG, "looper:" + i);

                    if (isCancelled())
                    {
                        break;
                    } else
                    {
                        Thread.sleep(1000);
                    }
                }
            }

            @Override
            protected void onError(Exception e)
            {
                super.onError(e);
                //任务异常回调
                Log.e(TAG, "onError:" + e);
                if (isCancelled())
                {
                    //任务被取消
                    Log.e(TAG, "task cancelled");
                }
            }

            @Override
            protected void onFinally()
            {
                super.onFinally();
                Log.i(TAG, "onFinally");
            }
        }.submit();
    }

    public void onClickCancelTask(View view)
    {
        FTask.cancelTag(TAG, true);
    }
}
