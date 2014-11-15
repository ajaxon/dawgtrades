package edu.uga.dawgtrades.persist;

import java.util.Iterator;

import edu.uga.dawgtrades.model.Attribute;
import edu.uga.dawgtrades.model.AttributeType;
import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.Bid;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ExperienceReport;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.Membership;
import edu.uga.dawgtrades.model.RegisteredUser;




public interface Persistence {

	

	  public void                    			saveAttribute( Attribute attribute ) throws DTException;
	  public Iterator<Attribute>          		restoreAttribute( Attribute attribute ) throws DTException;
	  public void		        				deleteAttribute(Attribute attribute ) throws DTException;
	  
	  public void                    			saveAttributeType( AttributeType attributeType ) throws DTException;
	  public Iterator<AttributeType>          		restoreAttributeType( AttributeType attributeType ) throws DTException;
	  public void		        				deleteAttribute(AttributeType attributeType ) throws DTException;
	  
	  public void                    			saveAuction( Auction auction ) throws DTException;
	  public Iterator<Auction>          	restoreAuction( Auction auction ) throws DTException;
	  public void		        			 	deleteAuction(Auction auction ) throws DTException;
	  
	  public void                    			saveBid( Bid bid ) throws DTException;
	  public Iterator<Bid>          	restoreBid( Bid bid ) throws DTException;
	  public void		        			 	deleteBid(Bid bid ) throws DTException;
	  
	  public void                    			saveCategory( Category category ) throws DTException;
	  public Iterator<Category>          	restoreCategory( Category category ) throws DTException;
	  public void		        			 	deleteCategory(Category category ) throws DTException;
	  
	  public void                    			saveExperienceReport( ExperienceReport experienceReport ) throws DTException;
	  public Iterator<ExperienceReport>          	restoreExperienceReport( ExperienceReport experienceReport ) throws DTException;
	  public void		        			 	deleteExperienceReport(ExperienceReport experienceReport ) throws DTException;
	  
	  public void                    			saveItem( Item item ) throws DTException;
	  public Iterator<Item>          	restoreItem( Item item ) throws DTException;
	  public void		        			 	deleteItem(Item item ) throws DTException;
	  
	  public void                    			saveMembership( Membership membership ) throws DTException;
	  public Membership							restoreMembership() throws DTException;
	  public void		        			 	deleteMembership(Membership membership ) throws DTException;
	  
	  public void                    			saveRegisteredUser( RegisteredUser registeredUser ) throws DTException;
	  public Iterator<RegisteredUser>          	restoreRegisteredUser( RegisteredUser registeredUser ) throws DTException;
	  public void		        			 	deleteRegisteredUser(RegisteredUser registeredUser ) throws DTException;
	  public Category							restoreParentCategoryBy(Category category) throws DTException;
	  public Iterator<Category>					restoreChildCategoryBy(Category category)throws DTException;
	  public Category							restoreCategoryBy(AttributeType attributeType)throws DTException;
	  public Iterator<AttributeType>            restoreAttributeTypeBy(Category category)throws DTException;
	  public Category							restoreCategoryBy(Item item) throws DTException;
	  public Iterator<Item>						restoreItemBy(Category category) throws DTException;
	  public Item								restoreItemBy(Attribute attribute) throws DTException;
	  public Iterator<Attribute>				restoreAttributeBy(Item item) throws DTException;
	  public RegisteredUser						restoreRegisteredUserBy(Item item) throws DTException;
	  public Iterator<Item>						restoreItemBy(RegisteredUser registeredUser) throws DTException;
	  public Item								restoreItemBy(Auction auction) throws DTException;
	  public Auction							restoreAuctionBy(Item item) throws DTException;
	  public AttributeType						restoreAttributeTypeBy(Attribute attribute) throws DTException;
	  
	  

}


