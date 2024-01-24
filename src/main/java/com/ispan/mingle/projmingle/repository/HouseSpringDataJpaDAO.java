package com.ispan.mingle.projmingle.repository;

import java.util.List;

import org.json.JSONObject;

import com.ispan.mingle.projmingle.domain.HouseBean;

public interface HouseSpringDataJpaDAO {
    public abstract List<HouseBean> find(JSONObject param);
    public abstract long count(JSONObject param);
}
