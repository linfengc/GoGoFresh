package itp341.lin.feng_cheng.fresh.Model;

import java.io.Serializable;
import java.sql.Timestamp;

import itp341.lin.feng_cheng.fresh.Model.Client.User;

/**
 * Created by fredlin on 4/2/17.
 */

public class Review implements Serializable {
    private Timestamp time;
    private String content;
    private User author;
    private int rating;

    @Override
    public String toString() {
        return "Review{" +
                "time=" + time +
                ", content='" + content + '\'' +
                ", author=" + author +
                ", rating=" + rating +
                '}';
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Review(Timestamp time, String content, User author, int rating) {

        this.time = time;
        this.content = content;
        this.author = author;
        this.rating = rating;
    }
}
