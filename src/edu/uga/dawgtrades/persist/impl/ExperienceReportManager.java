package edu.uga.dawgtrades.persist.impl;

import java.sql.Connection;
import java.util.Iterator;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import edu.uga.dawgtrades.model.Attribute;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ExperienceReport;
import edu.uga.dawgtrades.model.ObjectModel;

public class ExperienceReportManager {

	private ObjectModel objectModel = null;
	private Connection  conn = null;

	public ExperienceReportManager(Connection conn, ObjectModel objectModel) {
		this.conn = conn;
		this.objectModel = objectModel;
	}

	public void save(ExperienceReport experienceReport) throws DTException{
		String insertExReSql = "insert into experienceReport (reviewer, reviewed, rating, report, date) values (?, ?, ?, ?)";
		PreparedStatement stmt = null;
		int inscnt;
		long experienceReportId;
		//throw errors here
		try{
			stmt = (PreparedStatement) conn.prepareStatement(insertExReSql);
			stmt.setLong(1, experienceReport.getReviewed().getId());
			stmt.setLong(2, experienceReport.getReviewer().getId());
			stmt.setLong(3, experienceReport.getRating());
			stmt.setString(4, experienceReport.getReport());
			if(experienceReport.getDate() != null){
				java.util.Date javaDate = experienceReport.getDate();
				java.sql.Date sqlDate = new java.sql.Date(javaDate.getTime());
				stmt.setDate(5, sqlDate);
			}
			inscnt = stmt.executeUpdate();
			if(inscnt >= 1){
				String sql = "select last insert id()";
				if(stmt.execute(sql)){
					ResultSet r = stmt.getResultSet();
					while(r.next()){
						experienceReportId = r.getLong(1);
						if(experienceReportId > 0){
							experienceReport.setId(experienceReportId);
						}
					}
				}
			}else{
				throw new DTException("ExperienceReport.save failed");
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new DTException("ExperienceReport.save failed" + e);
		}
	}

	public Iterator<ExperienceReport> restore(ExperienceReport experienceReport) throws DTException{
		String selectExReSql = "select ...";
		Statement stmt = null;
		StringBuffer query = new StringBuffer(100);
		StringBuffer condition = new StringBuffer(100);
//throw errors and checks here
		condition.setLength(0);
		query.append(selectExReSql);
		if(experienceReport != null){
			if(experienceReport.isPersistent()){
				query.append("where id = " + experienceReport.getId());
			}else{
				if(experienceReport.getReviewed() != null){
					condition.append(" and reviewed = " + experienceReport.getReviewed().getId());
				}
				if(experienceReport.getReviewer() != null){
					condition.append(" and reviewer = " + experienceReport.getReviewer().getId());
				}
				if(experienceReport.getRating() != 0){
					condition.append(" and rating = " + experienceReport.getRating());
				}
				if(experienceReport.getReport() != null){
					condition.append(" and report = " + experienceReport.getReport());
				}
				if(experienceReport.getDate() != null){
					condition.append(" and date = " + experienceReport.getDate());
				}
				if(condition.length() > 0){
					query.append(condition);
				}
			}
		}
		try{
			stmt = conn.createStatement();
			if(stmt.execute(query.toString())){
				ResultSet r = stmt.getResultSet();
				return new ExperienceReportIterator(r, objectModel);
			}
		}catch(Exception e){
			throw new DTException("ExperienceReport.restore error " + e);
		}
		throw new DTException("ExperienceReprot.restore error");
	}

	public void delete(ExperienceReport experienceReport) throws DTException{
		String deleteExReSql = "delete from experienceReprot where id = ?";
		PreparedStatement stmt = null;
		int inscnt;
		if(!experienceReport.isPersistent()){
			return;
		}
		try{
			stmt = (PreparedStatement) conn.prepareStatement(deleteExReSql);
			stmt.setLong(1, experienceReport.getId());
			inscnt = stmt.executeUpdate();

			if(inscnt == 1){
				return;
			}else{
				throw new DTException("ExperienceReport failed to delete");
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new DTException("ExperienceReport failed to delete: " + e);
		}
	}
}