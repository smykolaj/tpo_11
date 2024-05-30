package org.example.tpo_11.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.example.tpo_11.constraint.TargetUrlIsHttps;
import org.example.tpo_11.constraint.UniqueTargetUrl;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

public class LinkDTO
{
    private String id;

    @Length(min=5, max=20)
    private String name;

    @URL
    @TargetUrlIsHttps
    private String targetUrl;
    private String redirectUrl;
    private Long visits;
    @JsonIgnore
    private String password;

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
