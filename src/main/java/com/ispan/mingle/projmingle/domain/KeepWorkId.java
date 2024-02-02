package com.ispan.mingle.projmingle.domain;

import java.io.Serializable;
import java.util.Objects;

public class KeepWorkId implements Serializable {
    private String volunteer; // 對應到 VolunteerBean 的 userid
    private Integer work; // 對應到 WorkBean 的 workid

    public KeepWorkId() {}

    public KeepWorkId(String volunteer, Integer work) {
        this.volunteer = volunteer;
        this.work = work;
    }

    // getters and setters

    public String getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(String volunteer) {
        this.volunteer = volunteer;
    }

    public Integer getWork() {
        return work;
    }

    public void setWork(Integer work) {
        this.work = work;
    }

    // hashCode and equals methods

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeepWorkId that = (KeepWorkId) o;
        return Objects.equals(volunteer, that.volunteer) &&
                Objects.equals(work, that.work);
    }

    @Override
    public int hashCode() {
        return Objects.hash(volunteer, work);
    }
}