package com.ispan.mingle.projmingle.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ispan.mingle.projmingle.domain.HouseBean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class WorkModifyHouseDTO {
    // 房東有的房子(沒被刪的)
    private List<HouseBean> houseDetail;

    // 目前綁定的房子，各自houseid
    private List<Integer> bindingHousesID;
}
