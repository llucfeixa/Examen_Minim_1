package edu.upc.dsa.models;

import java.util.List;

public class ListActivity {
    List<Activity> activities;

    public ListActivity() {
    }

    public ListActivity(List<Activity> activities) {
        this.activities = activities;
    }

    public List<Activity> getActivities() {
        return this.activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
}
