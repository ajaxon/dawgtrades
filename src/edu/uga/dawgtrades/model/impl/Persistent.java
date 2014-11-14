package edu.uga.dawgtrades.model.impl;

import edu.uga.dawgtrades.model.Persistable;

public class Persistent implements Persistable {

	@Override
	public long getId() {
		return this.id;
	}

	@Override
	public void setId(long id) {
		this.id = id;

	}

	@Override
	public boolean isPersistent() {
		return id >= 0;
	}

}
