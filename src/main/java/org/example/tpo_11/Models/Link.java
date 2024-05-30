package org.example.tpo_11.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.example.tpo_11.constraint.TargetUrlIsHttps;
import org.example.tpo_11.constraint.UniqueTargetUrl;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

@Entity
public class Link
{

    @Id
    @Column(name = "Id")
    private String id;
    @Column(name = "Name")
    private String name;
    @Column(name = "TargetUrl")
    private String targetUrl;
    @Column(name = "RedirectUrl")
    private String redirectUrl;
    @Column(name = "Visits")
    private Long visits = 0L;
    @Column(name = "Password")
    private String password;

    public Link(String id, String name, String targetUrl, String redirectUrl, String password)
    {
        this.id = id;
        this.name = name;
        this.targetUrl = targetUrl;
        this.redirectUrl = redirectUrl;
        this.password = password;
    }


    public Link(String id, String name, String targetUrl, String redirectUrl)
    {
        this.id = id;
        this.name = name;
        this.targetUrl = targetUrl;
        this.redirectUrl = redirectUrl;
    }

    public Link()
    {

    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getTargetUrl()
    {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl)
    {
        this.targetUrl = targetUrl;
    }

    public String getRedirectUrl()
    {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl)
    {
        this.redirectUrl = redirectUrl;
    }

    public Long getVisits()
    {
        return visits;
    }

    public void setVisits(Long visits)
    {
        this.visits = visits;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
