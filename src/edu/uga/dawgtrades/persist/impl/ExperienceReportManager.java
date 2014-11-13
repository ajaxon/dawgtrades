package edu.uga.dawgtrades.persist.impl;

import java.sql.Connection;
import java.util.Iterator;

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
		
		
	}
	
	public Iterator<ExperienceReport> restore(ExperienceReport experienceReport) throws DTException{
		
		
		return null;
		
		
	}
	
	public void delete(ExperienceReport experienceReport) throws DTException{
		
		
	}
	
}
