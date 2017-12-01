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
public class News {
    String newsID;
    int like;
    int provocation;
    int comment;
    int emotion;
    int hoax;

    public News() {
    }

    public News(String newsID, int like, int provocation, int comment, int emotion, int hoax) {
        this.newsID = newsID;
        this.like = like;
        this.provocation = provocation;
        this.comment = comment;
        this.emotion = emotion;
        this.hoax = hoax;
    }

    public String getNewsID() {
        return newsID;
    }

    public void setNewsID(String newsID) {
        this.newsID = newsID;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getProvocation() {
        return provocation;
    }

    public void setProvocation(int provocation) {
        this.provocation = provocation;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getEmotion() {
        return emotion;
    }

    public void setEmotion(int emotion) {
        this.emotion = emotion;
    }

    public int getHoax() {
        return hoax;
    }

    public void setHoax(int hoax) {
        this.hoax = hoax;
    }

    
    
    
}
