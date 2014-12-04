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
		String insertExReSql = "insert into experience_report (rating, report, reviewer_id, reviewed_id, date) values (?, ?, ?, ?, ?)";
		String updateExReSql ="update experience_report set rating = ?, report = ?, reviewer_id = ?, reviewed_id = ?, date = ?  where id = ?";
		PreparedStatement stmt = null;
		int inscnt;
		long experienceReportId;
		//throw errors here
		try{

			if( !experienceReport.isPersistent() )
				stmt = (PreparedStatement) conn.prepareStatement( insertExReSql );
			else
				stmt = (PreparedStatement) conn.prepareStatement( updateExReSql );

			if( experienceReport.getRating() >= 0 && experienceReport.getRating() <= 5 )
				stmt.setInt(1, experienceReport.getRating());
			else
				throw new DTException( "ExperienceReport.save: can't save an Experience Report: rating is not between 0 and 5" );

			if( experienceReport.getReport() != null )
				stmt.setString(2, experienceReport.getReport());
			else
				throw new DTException( "ExperienceReport.save: can't save a Experience Report: report undefined" );

			if( experienceReport.getReviewer() != null && experienceReport.getReviewer().getId() >= 0 )
				stmt.setLong(3, experienceReport.getReviewer().getId());
			else
				throw new DTException( "ExperienceReport.save: can't save a Experience Report: reviewer is undefined" );

			if( experienceReport.getReviewed() != null && experienceReport.getReviewed().getId() >= 0 )
				stmt.setLong(4, experienceReport.getReviewed().getId());
			else
				throw new DTException( "ExperienceReport.save: can't save a Experience Report: reviewed is undefined" );

			if(experienceReport.getDate() != null){
                java.sql.Date date = new java.sql.Date(experienceReport.getDate().getTime());
                stmt.setDate(5,date);

			}
			else
				throw new DTException( "ExperienceReport.save: can't save an Experience Report: date is undefined");

			if( experienceReport.isPersistent() )
				stmt.setLong( 6, experienceReport.getId() );


			/*stmt = (PreparedStatement) conn.prepareStatement(insertExReSql);
			stmt.setLong(1, experienceReport.getReviewed().getId());
			stmt.setLong(2, experienceReport.getReviewer().getId());
			stmt.setLong(3, experienceReport.getRating());
			stmt.setString(4, experienceReport.getReport());*/

			inscnt = stmt.executeUpdate();
			if(inscnt >= 1){
				String sql = "select last_insert_id()";
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
		String selectExReSql = "select id, reviewer_id, reviewer_id, rating, report,  date from experience_report";
		Statement stmt = null;
		StringBuffer query = new StringBuffer(100);
		StringBuffer condition = new StringBuffer(100);
//throw errors and checks here
		condition.setLength(0);
		query.append(selectExReSql);
		if(experienceReport != null){
			if(experienceReport.isPersistent()){
				query.append("where id = '" + experienceReport.getId() + "'");
			}else{
				if(experienceReport.getReviewed() != null){
                    if (condition.length() > 0) {
                        condition.append(" and");
                    }
					condition.append(" reviewed_id = '" + experienceReport.getReviewed().getId() + "'");
				}
				if(experienceReport.getReviewer() != null){
                    if (condition.length() > 0) {
                        condition.append(" and");
                    }
					condition.append(" reviewer_id = '" + experienceReport.getReviewer().getId() + "'");
				}
				if(experienceReport.getRating() != 0){
                    if (condition.length() > 0) {
                        condition.append(" and");
                    }
					condition.append(" rating = '" + experienceReport.getRating() + "'");
				}
				if(experienceReport.getReport() != null){
                    if (condition.length() > 0) {
                        condition.append(" and");
                    }
					condition.append(" report = '" + experienceReport.getReport() + "'");
				}
				if(experienceReport.getDate() != null){
                    if (condition.length() > 0) {
                        condition.append(" and");
                    }
					condition.append(" date = '" + experienceReport.getDate() + "'");
				}
				if(condition.length() > 0){
					query.append(  " where " );
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
		String deleteExReSql = "delete from experience_report where id = ?";
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