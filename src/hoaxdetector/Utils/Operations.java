/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hoaxdetector.Utils;

import hoaxdetector.Models.News;
import hoaxdetector.Models.ResultSet;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

/**
 *
 * @author ghost
 */
public interface Operations {
    
    
    interface DatabaseOperations {
        public Connection establishConnection ();
        public void updateData (Connection con, News news);
        public ArrayList<News> getDataTraining ( Connection con);
        public ArrayList<News> getRangedDataTraining (Connection con, String start, String end);
        public ArrayList<News> getDataTest ( Connection con);
    }
    
    interface KNNOperations {
        public ArrayList<ResultSet> calculateDistance(ArrayList<News> newsTraining, News newsTest);
        public ArrayList<ResultSet> reduceDuplicate(ArrayList<ResultSet> sets);
        public ArrayList<ResultSet> getTopK(ArrayList<ResultSet> sets);
        public ArrayList<ArrayList<News>> clustering(ArrayList<News> newsTraining);
        public void knn(Operations.KNNOperations knno, ArrayList<News> newsTraining, ArrayList<News> newsTesting);
        public ArrayList<ArrayList<News>> crossValidation(Operations.KNNOperations knno, ArrayList<News> newsTraining);
        public int comparing(ArrayList<News> training, ArrayList<News> validated);
        public void sortingDistance(ArrayList<ResultSet> sets);
        public void sortingIDNews(ArrayList<News> ns);
        public void setResult(ArrayList<ResultSet> sets, News n);
    }
}
