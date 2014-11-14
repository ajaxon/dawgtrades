package edu.uga.dawgtrades.model.impl;

import java.util.Date;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ExperienceReport;
import edu.uga.dawgtrades.model.RegisteredUser;

public class ExperienceReportImpl extends Persistent implements
		ExperienceReport {

		private long reviewerId;
		private long reviewedId;
		private RegisteredUser reviewer;
		private RegisteredUser reviewed;
		private int rating;
		private String report;
		private Date date;
		
	
	public ExperienceReportImpl(RegisteredUser reviewer,
			RegisteredUser reviewed, int rating, String report,
			Date date) throws DTException {
		super(-1);
		if(reviewer==null){
			throw new DTException("The reviewer is null");
		}
		if(!reviewer.isPersistent()){
			throw new DTException("The reviwer is not persistent");
			
		}
		if(reviewed==null){
			throw new DTException("The reviewed is null");
		}
		if(!reviewed.isPersistent()){
			throw new DTException("The reviwed is not persistent");
			
		}
		this.reviewer=reviewer;
		this.setReviewerId(this.reviewer.getId());
		this.reviewed=reviewed;
		this.setReviewedId(this.reviewed.getId());
		this.rating=rating;
		this.report=report;
		this.date=date;
	}

	public ExperienceReportImpl(long reviewerId, long reviewedId, int rating, String report,
			Date date) {
		
			this.setReviewedId(reviewedId);
			this.setReviewerId(reviewerId);
			this.rating=rating;
			this.report=report;
			this.date=date;

	}

	@Override
	public int getRating() {

		return this.rating;
	}

	@Override
	public void setRating(int rating) throws DTException {

		this.rating=rating;
	}

	@Override
	public String getReport() {
		
		return this.report;
	}

	@Override
	public void setReport(String report) {

		this.report=report;
	}

	@Override
	public Date getDate() {

		return this.date;
	}

	@Override
	public void setDate(Date date) {

		this.date=date;
	}

	@Override
	public RegisteredUser getReviewer() {

		return this.reviewer;
	}

	@Override
	public void setReviewer(RegisteredUser reviewer) {

		this.reviewer=reviewer;
	}

	@Override
	public RegisteredUser getReviewed() {

		return this.reviewed;
	}

	@Override
	public void setReviewed(RegisteredUser reviewed) {
			
		this.reviewed=reviewed;
	}

	public long getReviewedId() {
		return reviewedId;
	}

	public void setReviewedId(long reviewedId) {
		this.reviewedId = reviewedId;
	}

	public long getReviewerId() {
		return reviewerId;
	}

	public void setReviewerId(long reviewerId) {
		this.reviewerId = reviewerId;
	}

}
