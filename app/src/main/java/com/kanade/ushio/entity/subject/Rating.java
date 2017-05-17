package com.kanade.ushio.entity.subject;

import com.google.gson.annotations.JsonAdapter;
import com.kanade.ushio.entity.typeadapter.RatingTypeAdapter;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
@JsonAdapter(RatingTypeAdapter.class)
public class Rating implements RealmModel {
    private int total;
    private Count count;
    private double score;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Count getCount() {
        return count;
    }

    public void setCount(Count count) {
        this.count = count;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rating)) return false;

        Rating rating = (Rating) o;

        if (total != rating.total) return false;
        if (Double.compare(rating.score, score) != 0) return false;
        return count != null ? count.equals(rating.count) : rating.count == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = total;
        result = 31 * result + (count != null ? count.hashCode() : 0);
        temp = Double.doubleToLongBits(score);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
