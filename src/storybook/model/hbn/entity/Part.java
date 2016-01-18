/*
Storybook: Open Source software for novelists and authors.
Copyright (C) 2008 - 2012 Martin Mustun

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package storybook.model.hbn.entity;

import java.sql.Timestamp;
import java.util.Date;


/**
 * Part generated by hbm2java
 *
 * @hibernate.class
 *   table="PART"
 */
@SuppressWarnings("serial")
public class Part extends AbstractEntity {

	private Integer number;
	private String name;
	private String notes;
	private Part superpart;
    private Timestamp creationTime;
    private Timestamp objectiveTime;
    private Timestamp doneTime;
    private Integer objectiveChars;

	public Part() {
		this.creationTime = new Timestamp(new Date().getTime());
	}

	public Part(Integer number, String name, String notes, Part superpart,
			Timestamp creationTime, Timestamp objectiveTime,
			Timestamp doneTime) {
		this.number = number;
		this.name = name;
		this.notes = notes;
		this.superpart = superpart;
		this.creationTime = creationTime;
		this.objectiveTime = objectiveTime;
		this.doneTime = doneTime;
	}

	/**
	 * @hibernate.id
	 *   column="ID"
	 *   generator-class="increment"
	 *   unsaved-value="null"
	 */
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @hibernate.property
	 */
	public Integer getNumber() {
		return this.number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	/**
	 * @hibernate.property
	 */
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @hibernate.property
	 */
	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getNumberName() {
		return this.number + ": " + this.name;
	}
	/**
	 * @hibernate.property
	 */
    public Part getSuperpart() {
        return this.superpart;
    }

    public boolean hasSuperpart() {
        return superpart != null;
    }

    public void setSuperpart(Part superpart) {
    	this.superpart = superpart;
    }
    
    public boolean isPartOfPart(Part ancestor) {
        if (this.getId().equals(ancestor.getId())) {
    		return true;
    	}
    	if (this.hasSuperpart()) {
    		return this.getSuperpart().isPartOfPart(ancestor);
    	}
    	return false;
    }

	/**
	 * @hibernate.property
	 */
    public boolean hasCreationTime() {
        return creationTime != null;
    }

    public void setCreationTime(Timestamp ts) {
    	creationTime = ts;
    }

    public Timestamp getCreationTime() {
    	return creationTime;
    }

	/**
	 * @hibernate.property
	 */
    public boolean hasObjectiveTime() {
        return objectiveTime != null;
    }

    public void setObjectiveTime(Timestamp ts) {
    	objectiveTime = ts;
    }

    public Timestamp getObjectiveTime() {
    	return objectiveTime;
    }

	/**
	 * @hibernate.property
	 */
    public boolean hasObjectiveChars() {
        return objectiveChars != null;
    }
	public Integer getObjectiveChars() {
		return (hasObjectiveChars()) ? this.objectiveChars : 0;
	}

	public void setObjectiveChars(Integer objectiveChars) {
		this.objectiveChars = objectiveChars;
	}

	/**
	 * @hibernate.property
	 */
    public boolean isDone() {
        return hasDoneTime();
    }
    public boolean hasDoneTime() {
        return doneTime != null;
    }

    public void setDoneTime(Timestamp ts) {
    	doneTime = ts;
    }

    public Timestamp getDoneTime() {
    	return doneTime;
    }

	@Override
	public String toString() {
		return getNumber() + ": " + getName();
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		Part test = (Part) obj;
		boolean ret = true;
		ret = ret && equalsIntegerNullValue(number, test.getNumber());
		ret = ret && equalsStringNullValue(name, test.getName());
		ret = ret && equalsStringNullValue(notes, test.getNotes());
		ret = ret && equalsObjectNullValue(superpart, test.getSuperpart());
		return ret;
	}

	@Override
	public int hashCode() {
		int hash = super.hashCode();
		hash = hash * 31 + (number != null ? number.hashCode() : 0);
		hash = hash * 31 + (name != null ? name.hashCode() : 0);
		hash = hash * 31 + (notes != null ? notes.hashCode() : 0);
		hash = hash * 31 + (superpart != null ? superpart.hashCode() : 0);
		return hash;
	}
}