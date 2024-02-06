package com.ispan.mingle.projmingle.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ispan.mingle.projmingle.domain.HouseBean;
import com.ispan.mingle.projmingle.domain.LandlordBean;
import com.ispan.mingle.projmingle.domain.WorkBean;

public interface WorkRepository
        extends JpaRepository<WorkBean, Integer>, JpaSpecificationExecutor<WorkBean>, WorkSpringDataJpaDAO {
    // 依據查詢條件獲取工作
    Page<WorkBean> findAll(Specification<WorkBean> spec, Pageable pageable);

    // 查詢待審核工作數量
    @Query("SELECT COUNT(w) FROM WorkBean w WHERE w.status = '待審核'")
    public Integer findPendingWorkCount();

    /** 透過workid查詢房屋 */
    @Query("SELECT h FROM WorkBean w LEFT JOIN WorkHouseBean wh ON w.workid = wh.workid LEFT JOIN HouseBean h ON wh.houseid = h.houseid WHERE w.workid = :workId and wh.isDeleted = false")
    public List<HouseBean> findHousesByWorkId(@Param("workId") Integer workId);

    /** 透過workid查詢房主資訊 */
    @Query("SELECT l FROM WorkBean w LEFT JOIN LandlordBean l ON w.landlordid = l.landlordid WHERE w.workid = :workId")
    public LandlordBean findLandlordByWorkId(@Param("workId") Integer workId);


    /** 透過工作id新增已報名人數 */
    @Query("UPDATE WorkBean w SET w.attendance = :attendance WHERE w.workid = :workid")
    public WorkBean updateAttendance(Integer workid, Integer attendance);

}
