/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hoaxdetector.Models;

/**
 *
 * @author ghost
 */
public class ResultSet {
    private News news;
    private int distance;
    private String as;

    public ResultSet() {
    }

    public ResultSet(News news, int distance) {
        this.news = news;
        this.distance = distance;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    
    
}
