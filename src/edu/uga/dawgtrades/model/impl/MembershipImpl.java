package edu.uga.dawgtrades.model.impl;

import java.util.Date;

import edu.uga.dawgtrades.model.Membership;

public class MembershipImpl extends Persistent implements Membership {

	private float price;
	private Date date;
    private static MembershipImpl instance = null;
	
	public MembershipImpl(float price, Date date)
	{

            super(-1);
            this.price = price;
            this.date = date;


	}
    public static MembershipImpl getInstance()
    {
        if(instance == null){
           // instance = new MembershipImpl();
        }
        return instance;
    }
	
	@Override
	public float getPrice() {
		
		return this.price;
	}

	@Override
	public void setPrice(float price) {

		this.price=price;
	}

	@Override
	public Date getDate() {
		
		return this.date;
	}

}
