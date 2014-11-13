package edu.uga.dawgtrades.model.impl;

import java.util.Date;
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
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.RegisteredUser;

public class ObjectModelImpl implements ObjectModel {

	@Override
	public Category createCategory(Category parent, String name)
			throws DTException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category createCategory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<Category> findCategory(Category modelCategory)
			throws DTException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void storeCategory(Category category) throws DTException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteCategory(Category category) throws DTException {
		// TODO Auto-generated method stub

	}

	@Override
	public AttributeType createAttributeType(Category category, String name)
			throws DTException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AttributeType createAttributeType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void storeAttributeType(AttributeType attributeType)
			throws DTException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAttributeType(AttributeType attributeType)
			throws DTException {
		// TODO Auto-generated method stub

	}

	@Override
	public Item createItem(Category category, RegisteredUser user,
			String identifier, String name, String description)
			throws DTException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item createItem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<Item> findItem(Item modelItem) throws DTException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void storeItem(Item item) throws DTException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteItem(Item item) throws DTException {
		// TODO Auto-generated method stub

	}

	@Override
	public Attribute createAttribute(AttributeType attributeType, Item item,
			String value) throws DTException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Attribute createAttribute() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void storeAttribute(Attribute attribute) throws DTException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAttribute(Attribute attribute) throws DTException {
		// TODO Auto-generated method stub

	}

	@Override
	public Auction createAuction(Item item, float minPrice, Date expiration)
			throws DTException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Auction createAuction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<Auction> findAuction(Auction modelAuction)
			throws DTException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void storeAuction(Auction auction) throws DTException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAuction(Auction auction) throws DTException {
		// TODO Auto-generated method stub

	}

	@Override
	public RegisteredUser createRegisteredUser(String name, String firstName,
			String lastName, String password, boolean isAdmin, String email,
			String phone, boolean canText) throws DTException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RegisteredUser createRegisteredUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<RegisteredUser> findRegisteredUser(
			RegisteredUser modelRegisteredUser) throws DTException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void storeRegisteredUser(RegisteredUser registeredUser)
			throws DTException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteRegisteredUser(RegisteredUser registeredUser)
			throws DTException {
		// TODO Auto-generated method stub

	}

	@Override
	public Bid createBid(Auction auction, RegisteredUser user, float price)
			throws DTException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bid createBid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<Bid> findBid(Bid modelBid) throws DTException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void storeBid(Bid bid) throws DTException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteBid(Bid bid) throws DTException {
		// TODO Auto-generated method stub

	}

	@Override
	public ExperienceReport createExperienceReport(RegisteredUser reviewer,
			RegisteredUser reviewed, int rating, String report, Date date)
			throws DTException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExperienceReport createExperienceReport() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<ExperienceReport> findExperienceReport(
			ExperienceReport modelExperienceReport) throws DTException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void storeExperienceReport(ExperienceReport experienceReport)
			throws DTException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteExperienceReport(ExperienceReport experienceReport)
			throws DTException {
		// TODO Auto-generated method stub

	}

	@Override
	public Membership createMembership(float price, Date date)
			throws DTException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Membership createMembership() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Membership findMembership() throws DTException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void storeMembership(Membership membership) throws DTException {
		// TODO Auto-generated method stub

	}

	@Override
	public Category getParent(Category category) throws DTException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<Category> getChild(Category category) throws DTException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category getCategory(AttributeType attributeType) throws DTException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<AttributeType> getAttributeType(Category category)
			throws DTException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category getCategory(Item item) throws DTException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<Item> getItem(Category category) throws DTException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item getItem(Attribute attribute) throws DTException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<Attribute> getAttribute(Item item) throws DTException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RegisteredUser getRegisteredUser(Item item) throws DTException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<Item> getItem(RegisteredUser registeredUser)
			throws DTException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item getItem(Auction auction) throws DTException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Auction getAuction(Item item) throws DTException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AttributeType getAttributeType(Attribute attribute)
			throws DTException {
		// TODO Auto-generated method stub
		return null;
	}

}
