package edu.uga.dawgtrades.model.impl;

import java.util.Date;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ExperienceReport;
import edu.uga.dawgtrades.model.RegisteredUser;

public class ExperienceReportImpl extends Persistent implements
		ExperienceReport {

	public ExperienceReportImpl(RegisteredUser reviewer,
			RegisteredUser reviewed, int rating, String report,
			Date date) {
		// TODO Auto-generated constructor stub
	}

	public ExperienceReportImpl(long reviewerId, long reviewedId, int rating, String report,
			Date date) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getRating() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setRating(int rating) throws DTException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getReport() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setReport(String report) {
		// TODO Auto-generated method stub

	}

	@Override
	public Date getDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDate(Date date) {
		// TODO Auto-generated method stub

	}

	@Override
	public RegisteredUser getReviewer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setReviewer(RegisteredUser reviewer) {
		// TODO Auto-generated method stub

	}

	@Override
	public RegisteredUser getReviewed() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setReviewed(RegisteredUser reviewed) {
		// TODO Auto-generated method stub

	}

}
