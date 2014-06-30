package edu.vuum.mocca;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.concurrent.CountDownLatch;

/**
 * @class AndroidPlatformStrategy
 * @brief Provides methods that define a platform-independent API for
 * output data to Android UI thread and synchronizing on thread
 * completion in the ping/pong game.  It plays the role of the
 * "Concrete Strategy" in the Strategy pattern.
 */
public class AndroidPlatformStrategy extends PlatformStrategy {
    /**
     * TextViewVariable.
     */
    private TextView mTextViewOutput;

    private static final String TAG = "AndroidPlatformStrategy";

    /**
     * Activity variable finds gui widgets by view.
     */
    private WeakReference<Activity> mActivity;

    public AndroidPlatformStrategy(Object output,
                                   final Object activityParam) {
        /**
         * A textview output which displays calculations and expression trees.
         */
        mTextViewOutput = (TextView) output;

        /** The current activity window (succinct or verbose). */
        mActivity = new WeakReference<Activity>((Activity) activityParam);
    }

    /**
     * Latch to decrement each time a thread exits to control when the
     * play() method returns.
     */
    private static CountDownLatch mLatch = null;

    /**
     * Do any initialization needed to start a new game.
     */
    public void begin() {

        /** (Re)initialize the CountDownLatch. */
        // TODO - You fill in here.
        mLatch = new CountDownLatch(NUMBER_OF_THREADS);
        Log.i(TAG, "begin()");
    }

    /**
     * Print the outputString to the display.
     */
    public void print(final String outputString) {

        /**
         * Create a Runnable that's posted to the UI looper thread
         * and appends the outputString to a TextView. 
         */
        // TODO - You fill in here.
        mActivity.get().runOnUiThread(new Runnable() {
            public void run() {
                if(mTextViewOutput != null){
                    mTextViewOutput.append(outputString + "\n");
                }
            }
        });


        Log.i(TAG, "print(final String outputString = " + outputString + ")");
    }

    /**
     * Indicate that a game thread has finished running.
     */
    public void done() {

        // TODO - You fill in here.
        mLatch.countDown();
        Log.i(TAG, "done()");
    }

    /**
     * Barrier that waits for all the game threads to finish.
     */
    public void awaitDone() {

        // TODO - You fill in here.
        try {
            mLatch.await();
        } catch (java.lang.InterruptedException e) {
        }

        Log.i(TAG, "awaitDone()");
    }

    /**
     * Error log formats the message and displays it for the
     * debugging purposes.
     */
    public void errorLog(String javaFile, String errorMessage) {
        Log.e(javaFile, errorMessage);
    }
}
