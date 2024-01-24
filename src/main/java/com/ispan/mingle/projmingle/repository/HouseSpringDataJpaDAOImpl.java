package com.ispan.mingle.projmingle.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.json.JSONObject;

import com.ispan.mingle.projmingle.domain.HouseBean;
import com.ispan.mingle.projmingle.util.DatetimeConverter;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class HouseSpringDataJpaDAOImpl implements HouseSpringDataJpaDAO {
	@PersistenceContext
	private Session session;

	public Session getSession() {
		return this.session;
	}

	@Override
	public List<HouseBean> find(JSONObject param) {
		// select * from product where ?
		Integer houseid = param.isNull("houseid") ? null : param.getInt("houseid");
		Integer lordid = param.isNull("lordid") ? null : param.getInt("lordid");
		String houseType = param.isNull("houseType") ? null : param.getString("houseType");
		String city = param.isNull("city") ? null : param.getString("city");
		String name = param.isNull("name") ? null : param.getString("name");
		String description = param.isNull("description") ? null : param.getString("description");
		String address = param.isNull("address") ? null : param.getString("address");
		String postCode = param.isNull("postCode") ? null : param.getString("postCode");
		Integer beds = param.isNull("beds") ? null : param.getInt("beds");
		String status = param.isNull("status") ? null : param.getString("status");
		String notes = param.isNull("notes") ? null : param.getString("notes");
		Character hasWifi = param.isNull("hasWifi") ? null : param.getString("hasWifi").charAt(0);
		Character hasTV = param.isNull("hasTV") ? null : param.getString("hasTV").charAt(0);
		Character hasKitchen = param.isNull("hasKitchen") ? null : param.getString("hasKitchen").charAt(0);
		Character hasLaundry = param.isNull("hasLaundry") ? null : param.getString("hasLaundry").charAt(0);
		Character hasParkingLot = param.isNull("hasParkingLot") ? null : param.getString("hasParkingLot").charAt(0);
		Character hasAirconditioner = param.isNull("hasAirconditioner") ? null
				: param.getString("hasAirconditioner").charAt(0);
		Character hasPersonalSpace = param.isNull("hasPersonalSpace") ? null
				: param.getString("hasPersonalSpace").charAt(0);
		Character hasPool = param.isNull("hasPool") ? null : param.getString("hasPool").charAt(0);
		Character hasGym = param.isNull("hasGym") ? null : param.getString("hasGym").charAt(0);
		String tempcreatedAt = param.isNull("tempcreatedAt") ? null : param.getString("tempcreatedAt");
		String tempupdatedAt = param.isNull("tempupdatedAt") ? null : param.getString("tempupdatedAt");
		Character isDeleted = param.isNull("isDeleted") ? null : param.getString("isDeleted").charAt(0);

		int start = param.isNull("start") ? 0 : param.getInt("start");
		int rows = param.isNull("rows") ? 0 : param.getInt("rows");

		CriteriaBuilder builder = this.getSession().getCriteriaBuilder();
		CriteriaQuery<HouseBean> query = builder.createQuery(HouseBean.class);

		// from house
		Root<HouseBean> table = query.from(HouseBean.class);

		List<Predicate> predicates = new ArrayList<>();
		// houseid=?
		if (houseid != null) {
			predicates.add(builder.equal(table.get("houseid"), houseid));
		}
		// lordid=?
		if (lordid != null) {
			predicates.add(builder.equal(table.get("lordid"), lordid));
		}
		// houseType?
		if (houseType != null) {
			predicates.add(builder.equal(table.get("houseType"), houseType));
		}
		// city?
		if (city != null) {
			predicates.add(builder.equal(table.get("city"), city));
		}
		// name?
		if (name != null) {
			predicates.add(builder.equal(table.get("name"), name));
		}
		// description?
		if (description != null) {
			predicates.add(builder.equal(table.get("description"), description));
		}
		// address?
		if (address != null) {
			predicates.add(builder.equal(table.get("address"), address));
		}
		// postCode?
		if (postCode != null) {
			predicates.add(builder.equal(table.get("postCode"), postCode));
		}
		// beds?
		if (beds != null) {
			predicates.add(builder.equal(table.get("beds"), beds));
		}
		// status?
		if (status != null) {
			predicates.add(builder.equal(table.get("status"), status));
		}
		// notes?
		predicates.add(builder.equal(table.get("notes"), notes));
		// hasWifi?
		if (hasWifi != null) {
			predicates.add(builder.equal(table.get("hasWifi"), hasWifi));
		}
		// hasTV?
		if (hasTV != null) {
			predicates.add(builder.equal(table.get("hasTV"), hasTV));
		}
		// hasKitchen?
		if (hasKitchen != null) {
			predicates.add(builder.equal(table.get("hasKitchen"), hasKitchen));
		}
		// hasLaundry?
		if (hasLaundry != null) {
			predicates.add(builder.equal(table.get("hasLaundry"), hasLaundry));
		}
		// hasParkingLot?
		if (hasParkingLot != null) {
			predicates.add(builder.equal(table.get("hasParkingLot"), hasParkingLot));
		}
		// hasAirconditioner?
		if (hasAirconditioner != null) {
			predicates.add(builder.equal(table.get("hasAirconditioner"), hasAirconditioner));
		}
		// hasPersonalSpace?
		if (hasPersonalSpace != null) {
			predicates.add(builder.equal(table.get("hasPersonalSpace"), hasPersonalSpace));
		}
		// hasPool?
		if (hasPool != null) {
			predicates.add(builder.equal(table.get("hasPool"), hasPool));
		}
		// hasGym?
		if (hasGym != null) {
			predicates.add(builder.equal(table.get("hasGym"), hasGym));
		}
		// createdAt?
		if (tempcreatedAt != null && tempcreatedAt.length() != 0) {
			java.util.Date createAt = DatetimeConverter.parse(tempcreatedAt, "yyyy-MM-dd");
			predicates.add(builder.equal(table.get("createAt"), createAt));
		}
		// updatedAt?
		if (tempupdatedAt != null && tempupdatedAt.length() != 0) {
			java.util.Date updatedAt = DatetimeConverter.parse(tempupdatedAt, "yyyy-MM-dd");
			predicates.add(builder.equal(table.get("updatedAt"), updatedAt));
		}
		// isDeleted?
		if (isDeleted != null) {
			predicates.add(builder.equal(table.get("isDeleted"), isDeleted));
		}

		if (predicates != null && !predicates.isEmpty()) {
			query = query.where(predicates.toArray(new Predicate[0]));
		}

		TypedQuery<HouseBean> typedQuery = this.getSession().createQuery(query)
				.setFirstResult(start)
				.setMaxResults(rows);
		List<HouseBean> result = typedQuery.getResultList();
		if (result != null && !result.isEmpty()) {
			return result;
		} else {
			return null;
		}
	}

	@Override
	public long count(JSONObject param) {
		// select * from product where ?
		Integer houseid = param.isNull("houseid") ? null : param.getInt("houseid");
		Integer lordid = param.isNull("lordid") ? null : param.getInt("lordid");
		String houseType = param.isNull("houseType") ? null : param.getString("houseType");
		String city = param.isNull("city") ? null : param.getString("city");
		String name = param.isNull("name") ? null : param.getString("name");
		String description = param.isNull("description") ? null : param.getString("description");
		String address = param.isNull("address") ? null : param.getString("address");
		String postCode = param.isNull("postCode") ? null : param.getString("postCode");
		Integer beds = param.isNull("beds") ? null : param.getInt("beds");
		String status = param.isNull("status") ? null : param.getString("status");
		String notes = param.isNull("notes") ? null : param.getString("notes");
		Character hasWifi = param.isNull("hasWifi") ? null : param.getString("hasWifi").charAt(0);
		Character hasTV = param.isNull("hasTV") ? null : param.getString("hasTV").charAt(0);
		Character hasKitchen = param.isNull("hasKitchen") ? null : param.getString("hasKitchen").charAt(0);
		Character hasLaundry = param.isNull("hasLaundry") ? null : param.getString("hasLaundry").charAt(0);
		Character hasParkingLot = param.isNull("hasParkingLot") ? null : param.getString("hasParkingLot").charAt(0);
		Character hasAirconditioner = param.isNull("hasAirconditioner") ? null
				: param.getString("hasAirconditioner").charAt(0);
		Character hasPersonalSpace = param.isNull("hasPersonalSpace") ? null
				: param.getString("hasPersonalSpace").charAt(0);
		Character hasPool = param.isNull("hasPool") ? null : param.getString("hasPool").charAt(0);
		Character hasGym = param.isNull("hasGym") ? null : param.getString("hasGym").charAt(0);
		String tempcreatedAt = param.isNull("tempcreatedAt") ? null : param.getString("tempcreatedAt");
		String tempupdatedAt = param.isNull("tempupdatedAt") ? null : param.getString("tempupdatedAt");
		Character isDeleted = param.isNull("isDeleted") ? null : param.getString("isDeleted").charAt(0);

		CriteriaBuilder builder = this.getSession().getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);

		// from house
		Root<HouseBean> table = query.from(HouseBean.class);

		// select count(*)
		query = query.select(builder.count(table));
		
		List<Predicate> predicates = new ArrayList<>();
		// houseid=?
		if (houseid != null) {
			predicates.add(builder.equal(table.get("houseid"), houseid));
		}
		// lordid=?
		if (lordid != null) {
			predicates.add(builder.equal(table.get("lordid"), lordid));
		}
		// houseType?
		if (houseType != null) {
			predicates.add(builder.equal(table.get("houseType"), houseType));
		}
		// city?
		if (city != null) {
			predicates.add(builder.equal(table.get("city"), city));
		}
		// name?
		if (name != null) {
			predicates.add(builder.equal(table.get("name"), name));
		}
		// description?
		if (description != null) {
			predicates.add(builder.equal(table.get("description"), description));
		}
		// address?
		if (address != null) {
			predicates.add(builder.equal(table.get("address"), address));
		}
		// postCode?
		if (postCode != null) {
			predicates.add(builder.equal(table.get("postCode"), postCode));
		}
		// beds?
		if (beds != null) {
			predicates.add(builder.equal(table.get("beds"), beds));
		}
		// status?
		if (status != null) {
			predicates.add(builder.equal(table.get("status"), status));
		}
		// notes?
		predicates.add(builder.equal(table.get("notes"), notes));
		// hasWifi?
		if (hasWifi != null) {
			predicates.add(builder.equal(table.get("hasWifi"), hasWifi));
		}
		// hasTV?
		if (hasTV != null) {
			predicates.add(builder.equal(table.get("hasTV"), hasTV));
		}
		// hasKitchen?
		if (hasKitchen != null) {
			predicates.add(builder.equal(table.get("hasKitchen"), hasKitchen));
		}
		// hasLaundry?
		if (hasLaundry != null) {
			predicates.add(builder.equal(table.get("hasLaundry"), hasLaundry));
		}
		// hasParkingLot?
		if (hasParkingLot != null) {
			predicates.add(builder.equal(table.get("hasParkingLot"), hasParkingLot));
		}
		// hasAirconditioner?
		if (hasAirconditioner != null) {
			predicates.add(builder.equal(table.get("hasAirconditioner"), hasAirconditioner));
		}
		// hasPersonalSpace?
		if (hasPersonalSpace != null) {
			predicates.add(builder.equal(table.get("hasPersonalSpace"), hasPersonalSpace));
		}
		// hasPool?
		if (hasPool != null) {
			predicates.add(builder.equal(table.get("hasPool"), hasPool));
		}
		// hasGym?
		if (hasGym != null) {
			predicates.add(builder.equal(table.get("hasGym"), hasGym));
		}
		// createdAt?
		if (tempcreatedAt != null && tempcreatedAt.length() != 0) {
			java.util.Date createAt = DatetimeConverter.parse(tempcreatedAt, "yyyy-MM-dd");
			predicates.add(builder.equal(table.get("createAt"), createAt));
		}
		// updatedAt?
		if (tempupdatedAt != null && tempupdatedAt.length() != 0) {
			java.util.Date updatedAt = DatetimeConverter.parse(tempupdatedAt, "yyyy-MM-dd");
			predicates.add(builder.equal(table.get("updatedAt"), updatedAt));
		}
		// isDeleted?
		if (isDeleted != null) {
			predicates.add(builder.equal(table.get("isDeleted"), isDeleted));
		}

		TypedQuery<Long> typedQuery = this.getSession().createQuery(query);
		Long result = typedQuery.getSingleResult();
		if(result!=null) {
			return result;
		} else {
			return 0;
		}
	}

}