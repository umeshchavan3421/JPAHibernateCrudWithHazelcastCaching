package com.uxpsystems.assignment.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * Entity class maps to User table in database
 * 
 * @author Umesh .Chavan
 *
 */
@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "userName"))
public class User implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3365835825931898778L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @NotNull
    @Column(name = "userName")
    String userName;

    @NotNull
    @Column(name = "password")
    String password;
    @Column(name = "status")
    String status;

    public User() {
        super();
    }

    public User(String userName, String password, String status) {
        this.userName = userName;
        this.password = password;
        this.status = status;
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

}
