package com.ispan.mingle.projmingle.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.mingle.projmingle.dto.AnalyzeDTO;
import com.ispan.mingle.projmingle.repository.LandlordRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AnalyzeService {

    @Autowired
    private LandlordRepository landlordRepository;

    
     /**透過房東id取得資料 */
    public AnalyzeDTO getAnalyzeByLandlordId(Integer landlordId) {
      
        AnalyzeDTO analyzeDTO = new AnalyzeDTO();
      
        analyzeDTO.setLandlordid(landlordId);

       analyzeDTO.setOrder(landlordRepository.findOrderByLandlordId(landlordId));

       analyzeDTO.setWork(landlordRepository.findWorkByLandlordId(landlordId));

       analyzeDTO.setReview(landlordRepository.findReviewByLandlordId(landlordId));

       analyzeDTO.setTotalorder(landlordRepository.findTotalNumberByLandlordId(landlordId));
    

       analyzeDTO.setOrdernumbers(landlordRepository.findOrderNumberByLandlordId(landlordId));


        analyzeDTO.setTotalview(landlordRepository.findTotalViewsByLandlordId(landlordId));


        analyzeDTO.setAvgstar(landlordRepository.findAverageStarsByLandlordId(landlordId));

        analyzeDTO.setTotalreview(landlordRepository.findCountReviewByLandlordId(landlordId));

       return analyzeDTO;



    }
    

   /** 用房東id找每年每月訂單數 */

 public List<Object[]> findMonthlyOrderCountByLandlordId(Integer landlordId) {
     
     return landlordRepository.findMonthlyOrderCountByLandlordId(landlordId);
 }

    /** 用房東id找工作個別訂單數 */

    public List<Object[]> findWorkOrderCountByLandlordId (Integer landlordId) {
     
        return landlordRepository.findWorkOrderCountByLandlordId(landlordId);
    }

     /** 透過房東id查詢每個工作的瀏覽量 */

     public List<Object[]> findWorkViewsByLandlordId(Integer landlordId) {
         
         return landlordRepository.findWorkViewsByLandlordId(landlordId);
     }
      /** 房東每個工作分別評價數及評分 */

      public List<Object[]> findWorkReviewByLandlordId(Integer landlordId) {
          
          return landlordRepository.findWorkReviewByLandlordId(landlordId);
      }



}
