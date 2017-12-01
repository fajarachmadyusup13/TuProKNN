/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hoaxdetector.Operations;

import hoaxdetector.Models.News;
import hoaxdetector.Models.ResultSet;
import hoaxdetector.Utils.Limits;
import hoaxdetector.Utils.Operations;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author ghost
 */
public class KNNOperations implements Operations.KNNOperations{

    @Override
    public ArrayList<ResultSet> calculateDistance(ArrayList<News> dataTraining, News dataTesting) {
        int sum = 0;
        ArrayList<ResultSet> sets = new ArrayList<>();
        
        for (News newsTraining : dataTraining) {
            sum = Math.abs(dataTesting.getLike() - newsTraining.getLike()) + 
                  Math.abs(dataTesting.getProvocation()- newsTraining.getProvocation()) +
                  Math.abs(dataTesting.getComment()- newsTraining.getComment()) +
                  Math.abs(dataTesting.getEmotion()- newsTraining.getEmotion());
            sets.add(new ResultSet(newsTraining, sum));
//            sum = (int) Math.sqrt(Math.pow((dataTesting.getLike() - newsTraining.getLike()), 2) +
//                            Math.pow((dataTesting.getProvocation()- newsTraining.getProvocation()), 2) + 
//                            Math.pow((dataTesting.getComment()- newsTraining.getComment()), 2) + 
//                            Math.pow((dataTesting.getEmotion()- newsTraining.getEmotion()), 2));
//            sets.add(new ResultSet(newsTraining, sum));
        }        
        return sets;
    }

    @Override
    public void sortingDistance(ArrayList<ResultSet> sets) {
        Collections.sort(sets, new Comparator<ResultSet> () {
            @Override
            public int compare(ResultSet o1, ResultSet o2) {
                return Integer.valueOf(o1.getDistance()).compareTo(o2.getDistance());
            }
        });
    }
    
    
    @Override
    public ArrayList<ResultSet> reduceDuplicate(ArrayList<ResultSet> sets) {
        ArrayList<ResultSet> rses = new ArrayList<>();
        ResultSet temp = sets.get(0);
        rses.add(temp);
        for (ResultSet set : sets) {
            if (set.getDistance() != temp.getDistance()) {
                temp = set;
                rses.add(temp);
            }
        }
        return rses;
    }
    
    @Override
    public ArrayList<ResultSet> getTopK(ArrayList<ResultSet> sets) {
        ArrayList<ResultSet> topK = new ArrayList<>();
        for (int i = 0; i < Limits.K; i++) {
            topK.add(sets.get(i));
        }
        return topK;
    }
    
    @Override
    public void setResult(ArrayList<ResultSet> sets, News n) {
        int hoax    = 0;
        int notHoax = 0;
//        System.out.println("before - "+n.getHoax());
        for (ResultSet set : sets) {
            if (set.getNews().getHoax() == 1) {
                hoax++;
            }else {
                notHoax++;
            }
            if (hoax > notHoax) {
                n.setHoax(1);
            }else {
                n.setHoax(0);
            }
        }
//        System.out.println("after - "+n.getHoax());
    }

    @Override
    public ArrayList<ArrayList<News>> clustering(ArrayList<News> newsTraining) {
        int rangeCluster  = newsTraining.size()/Limits.K;
        int startPoint    = 0;
        int endPoint      = rangeCluster;
        int mod           = newsTraining.size()%Limits.K;
        ArrayList<ArrayList<News>> clusters = new ArrayList<>();
        ArrayList<News> cluster = new ArrayList<>();
        
        if (mod != 0) {
            for (int j = 0; j < mod; j++) {
                cluster.add(newsTraining.get(j));
            }
            startPoint  = mod;
            endPoint    = mod+rangeCluster;
            mod         = (newsTraining.size()-mod)%Limits.K;
            clusters.add(cluster);
        }
              
        for (int i = 0; i < Limits.K; i++) {
            cluster = new ArrayList<>();
            for (int j = startPoint; j < endPoint; j++) {
                cluster.add(newsTraining.get(j));
            }
            startPoint = endPoint;
            endPoint += rangeCluster;
            clusters.add(cluster);
        }
        
        return clusters;
    }

    @Override
    public ArrayList<ArrayList<News>> crossValidation(Operations.KNNOperations knno, ArrayList<News> newsTraining) {
        ArrayList<News> tempTraining = newsTraining;
        int size = knno.clustering(tempTraining).size();
        ArrayList<ArrayList<News>> restData = knno.clustering(tempTraining);
        ArrayList<News> training = new ArrayList<>();
        ArrayList<News> testing = new ArrayList<>();
        
        for (int i = 0; i < Limits.K; i++) {
            testing = restData.get(i);
            restData.remove(i);
            for (ArrayList<News> arrayList : restData) {
                for (int j = 0; j < arrayList.size(); j++) {
                    training.add(arrayList.get(j));
                }
            }
            
            
            knn(knno, training, testing);
            restData.add(testing);
            training = new ArrayList<>();
        }
        
        return restData;
    }

    @Override
    public void knn(Operations.KNNOperations knno, ArrayList<News> newsTraining, ArrayList<News> newsTesting) {
        for (News news : newsTesting) {
            ArrayList<ResultSet> tes = knno.calculateDistance(newsTraining, news);
            knno.sortingDistance(tes);
            tes = knno.getTopK(tes);
            knno.setResult(tes, news);
        }
    }

    @Override
    public void sortingIDNews(ArrayList<News> ns) {
        Collections.sort(ns, new Comparator<News> () {
            @Override
            public int compare(News o1, News o2) {
                return o1.getNewsID().compareTo(o2.getNewsID());
            }
        });
    }

    @Override
    public int comparing(ArrayList<News> training, ArrayList<News> validated) {
        int same = 0;
        for (int i = 0; i < training.size(); i++) {
            if (training.get(i).getHoax() == validated.get(i).getHoax()) {
                same++;
            }
        }
        return same;
    }
 
}
