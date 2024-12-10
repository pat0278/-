package com.event.cia103g1springboot.hibernate.util.CompositeQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.event.cia103g1springboot.product.pdtimg.model.PdtImgVO;
import com.event.cia103g1springboot.product.product.model.PdtVO;

public class HibernateUtil_CompositeQuery_PdtImg {
	public static Predicate get_aPredicate_For_AnyDB(CriteriaBuilder builder,Root<PdtImgVO> root, String columnName, String value) {
		Predicate predicate = null;
		
		if("pdtImgId".equals(columnName)) {
			predicate = builder.equal(root.get(columnName),Integer.valueOf(value));
		}else if("pdtImgName".equals(columnName)) {
			predicate = builder.like(root.get(columnName), "%" + value+"%");
		}else if("pdtId".equals(columnName)) {
			PdtVO pdtVO = new PdtVO();
			pdtVO.setPdtId(Integer.valueOf(value));
			predicate = builder.equal(root.get("pdtVO"), pdtVO);
			
		}
		return predicate;
	}
	
	@SuppressWarnings("unchecked")
	public static List<PdtImgVO> getAllC(Map<String,String[]> map, Session session){
		Transaction tx = session.beginTransaction();
		List<PdtImgVO> list = null;
		try {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<PdtImgVO> criteriaQuery = builder.createQuery(PdtImgVO.class);
			Root<PdtImgVO> root = criteriaQuery.from(PdtImgVO.class);
			List<Predicate> predicateList = new ArrayList<Predicate>();
			Set<String> keys = map.keySet();
			int count = 0;
			for(String key : keys) {
				String value = map.get(key)[0];
				if (value != null && !value.trim().isEmpty() && !"action".equals(key)) {
			        count++;
					predicateList.add(get_aPredicate_For_AnyDB(builder, root, key, value.trim()));			        
			        System.out.println("送出資料的欄位共:"+count+"欄");
					System.out.println("處理的欄位: " + key + ", 值: " + value);
				}
			}
			System.out.println("predicateList.size()="+predicateList.size());
			System.out.println("Generated Predicates: " + predicateList);
			criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
			criteriaQuery.orderBy(builder.asc(root.get("pdtImgId")));
			Query query = session.createQuery(criteriaQuery);
			list = query.getResultList();
			
			tx.commit();
		}catch (RuntimeException ex) {
	        if (tx != null) {
	            tx.rollback(); // 回滾交易
	        }
	        System.err.println("查詢商品列表時出現錯誤，已回滾交易。");
	        ex.printStackTrace();

	    } finally {
	        if (session != null) {
	            try {
	                session.clear(); // 清除 session
	            } catch (Exception e) {
	                System.err.println("清除 session 時發生錯誤。");
	                e.printStackTrace();
	            }
	        }
	    }
		if (list == null) {
	        list = new ArrayList<>();
	    }
		return list;
	}
}
