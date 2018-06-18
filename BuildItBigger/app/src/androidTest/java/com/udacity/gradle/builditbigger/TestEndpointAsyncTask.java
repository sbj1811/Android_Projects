package com.udacity.gradle.builditbigger;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;

@RunWith(AndroidJUnit4.class)
public class TestEndpointAsyncTask {

    @Rule
    public final ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testJokeAvailability (){

        String joke;

        EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask(mainActivityActivityTestRule.getActivity());
        endpointsAsyncTask.execute();
        try {
            joke = endpointsAsyncTask.get();
            Log.e("TEST", "testJokeAvailability: "+joke);
            assertNotNull(joke);
            assertFalse(joke.isEmpty());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }



    }


}
