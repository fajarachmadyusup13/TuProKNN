/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hoaxdetector;

import hoaxdetector.Models.News;
import hoaxdetector.Models.ResultSet;
import hoaxdetector.Operations.DatabaseOperations;
import hoaxdetector.Operations.KNNOperations;
import hoaxdetector.Utils.Limits;
import hoaxdetector.Utils.Operations;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author ghost
 */
public class HoaxDetector {
    private Operations.DatabaseOperations dbOps;
    private Operations.KNNOperations knnOps;
    private ArrayList<News> dataTraining;
    private ArrayList<News> tempTraining;
    private ArrayList<News> dataTesting;
    private ArrayList<News> validated;
    private Connection con;

    public HoaxDetector() {
        this.dbOps = new DatabaseOperations();
        this.knnOps = new KNNOperations();
        this.con = dbOps.establishConnection();
        this.dataTraining = dbOps.getDataTraining(con);
        this.tempTraining = dbOps.getDataTraining(con);
        this.dataTesting = dbOps.getDataTest(con);
        this.validated = new ArrayList<>();
    }

    public void operations() {
        knnOps.knn(knnOps, dataTraining, dataTesting);
        for (News news : dataTesting) {
            System.out.println(news.getNewsID() + " - " + news.getLike() + " - " +
                               news.getProvocation() + " - " + news.getComment()+ " - " +
                               news.getEmotion()+ " - " + news.getHoax()+ " - ");
        }
        for (ArrayList<News> arrayList : knnOps.crossValidation(knnOps, tempTraining)) {
            for (int i = 0; i < arrayList.size(); i++) {
                validated.add(arrayList.get(i));
            }
        }
        knnOps.sortingIDNews(validated);
        double accuracy = ((double)knnOps.comparing(dataTraining, validated)/4000)*100;
        System.out.println("----------------------------------------------------------");
        System.out.println("Accuracy : " + String.format("%.3f", accuracy) + "%");
    }
}
