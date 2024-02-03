package com.cash_manager.api;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name="users")
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    @Column(unique=true)
    private String email;
    private String password;
    private Boolean admin;
    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<Transaction> transactions = new HashSet<>();;
    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<Panier> panier = new HashSet<>();
    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<Payment> payment = new HashSet<>();
    
    /**
    * Returns the id of this message. This is used to distinguish messages that have been created by the client and are no longer valid after the client has returned a response from the server.
    * 
    * 
    * @return the id of this message or null if the client has not returned a response from the server ( or is invalid
    */
    public Long getId() {
        return id;
    }
    
    /**
    * Sets the id of the entity. This is used to determine the entity's id when it is added to the list of entities for a user or the user has deleted it
    * 
    * @param id - the id of the
    */
    public void setId(Long id) {
        this.id = id;
    }
    /**
    * Returns the email associated with this user. Note that this is a copy of the value returned by #getEmail ().
    * 
    * 
    * @return the email associated with this user or null if there is no email associated with this user or if the user is
    */
    public String getEmail() {
        return email;
    }
    /**
    * Sets the email to be sent to the user. This is a convenience method for setting the email to be sent to the user
    * 
    * param email - the e - mail
    */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
    * Returns the nom of the object. This is used to generate error messages when there is no nom in the XML file.
    * 
    * 
    * @return the nom of the object or null if it is not a nom in the XML file or if the nom does not
    */
    public String getNom() {
        return nom;
    }
    /**
    * Set the Nom. This is used to identify the person who sent the message. If you are interested in the name of the person use #setPerson ( String ) instead.
    * 
    * param nom - the Nom to set in the message as
    */
    public void setNom(String nom) {
        this.nom = nom;
    }
    /**
    * Returns the password to use for this authentication. If this method is called before #authenticate ( java. lang. String ) it will return null.
    * 
    * 
    * return the password to use for this authentication null if not set or if there is no password to use for
    */
    public String getPassword() {
        return password;
    }
    /**
    * Sets the password to use for authenticating. By default this is null. You can override this with a password that is stored in the database or by passing it to #setPassword ( String ).
    * 
    * param password - the password to use for authenticating. If null the password will be reset
    */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
    * Returns true if this user is an admin. Admins are used to create and edit users and have a chance to do their own work when the user is logged in.
    * 
    * 
    * return true if this user is an admin false otherwise ( never returns true ). Note that this is the default
    */
    public boolean getAdmin() {
        return admin;
    }
    /**
    * Sets whether or not this user is an admin. This is used to prevent accidental access to the data source from being blocked by another user.
    * 
    * param admin - true if this user is an admin false otherwise
    */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
    // Getters and setters (or use Lombok annotations)
    /**
    * Returns the authorities that this user has. This method is called by #getAuthority () and can be overridden to provide additional authorities.
    * 
    * 
    * return a list of GrantedAuthority objects that this user has in the context of the authority ( s )
    */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        // Returns a list of roles that are allowed to access the user.
        if(this.admin){
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),new SimpleGrantedAuthority("ROLE_USER"));
        }else{
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }
    /**
    * Returns the username to use for this user. This is the email address that is associated with this user.
    * 
    * 
    * return the username to use for this user or null if there is no username associated with this user or if the username is
    */
    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return email;
    }
    /**
    * Returns true if the account is non expired. This is used to determine if there is a change in the account's expiration date.
    * 
    * 
    * return true if the account is non expired false otherwise. Note : The implementation of this method does not guarantee that the account is valid
    */
    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }
    /**
    * Returns true if the account is locked. This is used to determine if there is a lock on the account or not.
    * 
    * 
    * return true if the account is locked false otherwise. Note : The implementation does not check if the account is locked
    */
    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }
    /**
    * Returns true if the credentials are expired. This is used to determine if we should try to authenticate to the server for a non expiring user.
    * 
    * 
    * return whether or not the credentials are expired or not ( true or false ) based on the credentials type
    */
    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }
    /**
    * Returns true if this plugin is enabled. A plugin is enabled if it has been configured to do so by the user or if it has been configured to disable it in the configuration file.
    * 
    * 
    * return true if this plugin is enabled false otherwise ( default is true ). Note : This method always returns true
    */
    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }
    
}