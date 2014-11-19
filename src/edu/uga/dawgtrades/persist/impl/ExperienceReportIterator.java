package edu.uga.dawgtrades.persist.impl;

import java.sql.ResultSet;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Iterator;
import java.sql.Connection;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.ExperienceReport;
import edu.uga.dawgtrades.model.RegisteredUser;

public class ExperienceReportIterator implements Iterator<ExperienceReport> {

	private ResultSet rs = null;
	private ObjectModel objectModel = null;
	private boolean more = false;

	public ExperienceReportIterator(ResultSet rs, ObjectModel objectModel) throws Exception{
		this.rs = rs;
		this.objectModel = objectModel;
		try{
			more = rs.next();
		}catch(Exception e){
			throw new Exception("ExperienceReportIterator: Cannot create an iterator." + e);
		}
	}

	@Override
	public boolean hasNext() {
		return more;
	}

	@Override
	public ExperienceReport next() {
		long id;
		long reviewId;
		long reviewedId;
		int rating;
		String report;
		Date date;
		ExperienceReport experienceReport = null;

		if(more){
			try {
				id = rs.getLong(1);
				reviewId = rs.getLong(2);
				reviewedId = rs.getLong(3);
				rating = rs.getInt(4);
				report = rs.getString(5);
				date = rs.getDate(6);
				more = rs.next();
			}catch(Exception e){
				throw new NoSuchElementException("ExperienceReportIterator: No next object; root cause: " + e);
			}
			try{
				RegisteredUser reviewer = objectModel.createRegisteredUser(null, null, null, null, false, null, null, false);
				RegisteredUser reviewed = objectModel.createRegisteredUser(null, null, null, null, false, null, null, false);
				reviewer.setId(reviewId);
				reviewed.setId(reviewedId);
				experienceReport = objectModel.createExperienceReport(reviewer, reviewed, rating, report, date);
				experienceReport.setId(id);
				experienceReport.setReviewer(reviewer);
				experienceReport.setReviewed(reviewed);
			}catch(Exception e){}
			return experienceReport;
		}else{
			throw new NoSuchElementException("ExperienceReportIterator: No next object");
		}
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
