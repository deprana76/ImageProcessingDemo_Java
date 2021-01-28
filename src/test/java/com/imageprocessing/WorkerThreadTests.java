package com.imageprocessing;

import com.imageprocessing.model.Result;
import com.imageprocessing.worker.WorkerThread;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class WorkerThreadTests {

    @Test
    public void successTest(){
        WorkerThread worker=new WorkerThread();
        List<Result> results = worker.processImages(Arrays.stream(new String[]{"https://i.redd.it/s5viyluv421z.jpg"}));
        assert results!=null;
        assert results.get(0).getThirdColor()!=null;
    }

    @Test
    public void failureTest(){
        WorkerThread worker=new WorkerThread();
        List<Result> results = worker.processImages(Arrays.stream(new String[]{"http://i.imgur.com/FApqk3D.jpg"}));
        assert results!=null;
        assert results.get(0).getThirdColor()==null;
    }
}
