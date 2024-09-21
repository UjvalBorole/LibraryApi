package com.libraryapi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name="user")
@NoArgsConstructor
@Getter
@Setter
public class User  implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userid;

    private String fullName;

    private String email;

    private String password;

    private String phonenumber;

    private String userimage;

	// @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<Likes> likes = new ArrayList<>();

	@OneToMany(mappedBy ="user",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Likes>likes = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinTable(name="user_role",joinColumns = @JoinColumn(name="user",referencedColumnName = "userid"),inverseJoinColumns = @JoinColumn(name="role",referencedColumnName = "id"))
    private Set<Role>roles = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Book> books = new ArrayList<>();
	
    @Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority>authorities=this.roles.stream().map((role)->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
		return authorities;
	}

	   @Override
	    public String getPassword() {
	        return this.password;
	    }

	    @Override
	    public String getUsername() {
	        return this.email; // Assuming email is used as the username
	    }

	    @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }

	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isEnabled() {
	        return true; // Assuming you have an 'enabled' flag in your User entity
	    }
}
