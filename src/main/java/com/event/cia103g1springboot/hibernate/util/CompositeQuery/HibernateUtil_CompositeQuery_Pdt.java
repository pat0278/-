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

import com.event.cia103g1springboot.product.product.model.PdtVO;
import com.event.cia103g1springboot.product.producttype.model.PdtTypeVO;

public class HibernateUtil_CompositeQuery_Pdt {

	public static Predicate get_aPredicate_For_AnyDB(CriteriaBuilder builder,Root<PdtVO> root, String columnName, String value) {
		Predicate predicate = null;
		
		if("pdtId".equals(columnName) || "startPdtPrice".equals(columnName) || "endPdtPrice".equals(columnName)) {
			if ("startPdtPrice".equals(columnName)) {
	            predicate = builder.greaterThanOrEqualTo(root.get("pdtPrice"), Integer.valueOf(value));
	        } else if ("endPdtPrice".equals(columnName)) {
	            predicate = builder.lessThanOrEqualTo(root.get("pdtPrice"), Integer.valueOf(value));
	        }else {
			predicate = builder.equal(root.get(columnName),Integer.valueOf(value));}
		}else if("pdtName".equals(columnName) || "pdtDesc".equals(columnName)) {
			predicate = builder.like(root.get(columnName), "%" + value+"%");
		}else if("pdtStat".equals(columnName)) {
			predicate = builder.equal(root.get(columnName), Byte.valueOf(value));
		}else if("pdtTypeId".equals(columnName)) {
			PdtTypeVO pdtTypeVO = new PdtTypeVO();
			pdtTypeVO.setPdtTypeId(Integer.valueOf(value));	
			predicate = builder.equal(root.get("pdtTypeVO"), pdtTypeVO);
		}
		return predicate;
	}
	
	@SuppressWarnings("unchecked")
	public static List<PdtVO> getAllC(Map<String,String[]> map, Session session){
		Transaction tx = session.beginTransaction();
		List<PdtVO> list = new ArrayList<>();
		try {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<PdtVO> criteriaQuery = builder.createQuery(PdtVO.class);
			Root<PdtVO> root = criteriaQuery.from(PdtVO.class);
			List<Predicate> predicateList = new ArrayList<Predicate>();
			Set<String> keys = map.keySet();
			int count = 0;
			for(String key : keys) {
				String value = map.get(key)[0];
				if (value != null && value.trim().length() != 0 && !"action".equals(key)) {
			        if ("startPdtPrice".equals(key) || "endPdtPrice".equals(key)) {
			            predicateList.add(get_aPredicate_For_AnyDB(builder, root, key, value));
			        } else {
			            predicateList.add(get_aPredicate_For_AnyDB(builder, root, key, value.trim()));
			        }
			        count++;
			        System.out.println("送出資料的欄位共:"+count+"欄");
					 System.out.println("處理的欄位: " + key + ", 值: " + value);
				}
				Predicate predicate = builder.lessThan(root.get("pdtStat"),2);
		        predicateList.add(predicate);
			}
			System.out.println("predicateList.size()="+predicateList.size());			
			criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
			criteriaQuery.orderBy(builder.asc(root.get("pdtId")));
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
