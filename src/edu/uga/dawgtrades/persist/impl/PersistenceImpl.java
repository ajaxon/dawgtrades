package edu.uga.dawgtrades.persist.impl;

import java.sql.Connection;
import java.util.Iterator;


import edu.uga.dawgtrades.model.ObjectModel;
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
import edu.uga.dawgtrades.persist.Persistence;

public class PersistenceImpl implements Persistence {
	
	private AttributeManager attributeManager = null;
    private AttributeTypeManager attributeTypeManager = null;
    private AuctionManager auctionManager = null;
    private BidManager  bidManager= null;
    private CategoryManager categoryManager = null;
    private ExperienceReportManager experienceReportManager = null;
    private ItemManager  itemManager= null;
    private RegisteredUserManager registeredUserManager = null;
    private MembershipManager membershipManager = null;
    
   
	
	 public PersistenceImpl( Connection conn, ObjectModel objectModel )
	    {
		  attributeManager = new AttributeManager(conn, objectModel);
		  attributeTypeManager = new AttributeTypeManager(conn,objectModel);
		  auctionManager = new AuctionManager(conn,objectModel);
		  bidManager = new BidManager(conn,objectModel);
		  categoryManager = new CategoryManager(conn,objectModel);
		  experienceReportManager = new ExperienceReportManager(conn,objectModel);
		  itemManager = new ItemManager(conn,objectModel);
		  registeredUserManager = new RegisteredUserManager(conn,objectModel);
		  membershipManager = new MembershipManager(conn,objectModel);
		 
	    }
	
	
	@Override
	public void saveAttribute(Attribute attribute) throws DTException {
		
		attributeManager.save(attribute);

	}

	@Override
	public Iterator<Attribute> restoreAttribute(Attribute attribute)
			throws DTException {
	
		return attributeManager.restore(attribute);
	}

	@Override
	public void deleteAttribute(Attribute attribute) throws DTException {
		
		attributeManager.delete(attribute);

	}

	@Override
	public void saveAttributeType(AttributeType attributeType)
			throws DTException {
		
		attributeTypeManager.save(attributeType);

	}

	@Override
	public Iterator<AttributeType> restoreAttributeType(
			AttributeType attributeType) throws DTException {
	
		return attributeTypeManager.restore(attributeType);
	}

	@Override
	public void deleteAttribute(AttributeType attributeType) throws DTException {
		
		attributeTypeManager.delete(attributeType);

	}

	@Override
	public void saveAuction(Auction auction) throws DTException {
		
		auctionManager.save(auction);

	}

	@Override
	public Iterator<Auction> restoreAuction(Auction auction) throws DTException {
		
		return auctionManager.restore(auction);
	}

	@Override
	public void deleteAuction(Auction auction) throws DTException {
		
		auctionManager.delete(auction);

	}

	@Override
	public void saveBid(Bid bid) throws DTException {
		
		bidManager.save(bid);

	}

	@Override
	public Iterator<Bid> restoreBid(Bid bid) throws DTException {
		
		return bidManager.restore(bid);
	}

	@Override
	public void deleteBid(Bid bid) throws DTException {
		
		bidManager.delete(bid);

	}

	@Override
	public void saveCategory(Category category) throws DTException {
		
		categoryManager.save(category);

	}

	@Override
	public Iterator<Category> restoreCategory(Category category)
			throws DTException {
		
		return categoryManager.restore(category);
	}

	@Override
	public void deleteCategory(Category category) throws DTException {
		
		categoryManager.delete(category);

	}

	@Override
	public void saveExperienceReport(ExperienceReport experienceReport)
			throws DTException {
		
			experienceReportManager.save(experienceReport);

	}

	@Override
	public Iterator<ExperienceReport> restoreExperienceReport(
			ExperienceReport experienceReport) throws DTException {
		
		return experienceReportManager.restore(experienceReport);
	}

	@Override
	public void deleteExperienceReport(ExperienceReport experienceReport)
			throws DTException {
		
		experienceReportManager.delete(experienceReport);

	}

	@Override
	public void saveItem(Item item) throws DTException {
		
		itemManager.save(item);

	}

	@Override
	public Iterator<Item> restoreItem(Item item) throws DTException {
		
		return itemManager.restore(item);
	}

	@Override
	public void deleteItem(Item item) throws DTException {
		
		itemManager.delete(item);

	}

	@Override
	public void saveMembership(Membership membership) throws DTException {
		
		membershipManager.save(membership);

	}


	@Override
	public void deleteMembership(Membership membership) throws DTException {
		
		membershipManager.delete(membership);

	}

	@Override
	public void saveRegisteredUser(RegisteredUser registeredUser)
			throws DTException {
		
		registeredUserManager.save(registeredUser);

	}

	@Override
	public Iterator<RegisteredUser> restoreRegisteredUser(
			RegisteredUser registeredUser) throws DTException {
	
		return registeredUserManager.restore(registeredUser);
	}

	@Override
	public void deleteRegisteredUser(RegisteredUser registeredUser)
			throws DTException {
		
		registeredUserManager.restore(registeredUser);

	}


	@Override
	public Membership restoreMembership() throws DTException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Category restoreParentCategoryBy(Category category) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Iterator<Category> restoreChildCategoryBy(Category category) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Category restoreCategoryBy(AttributeType attributeType) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Iterator<AttributeType> restoreAttributeTypeBy(Category category) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Category restoreCategoryBy(Item item) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Iterator<Item> restoreItemBy(Category category) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Item restoreItemBy(Attribute attribute) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Iterator<Attribute> restoreAttributeBy(Item item) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public RegisteredUser restoreRegisteredUserBy(Item item) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Iterator<Item> restoreItemBy(RegisteredUser registeredUser) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Item restoreItemBy(Auction auction) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Auction restoreAuctionBy(Item item) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public AttributeType restoreAttributeTypeBy(Attribute attribute) {
		// TODO Auto-generated method stub
		return null;
	}

}
