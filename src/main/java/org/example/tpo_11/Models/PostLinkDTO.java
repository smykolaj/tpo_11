package org.example.tpo_11.Models;

import jakarta.validation.constraints.NotNull;
import org.example.tpo_11.constraint.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

public class PostLinkDTO
{
    @NotNull
    @Length(min=5, max=20)
    private String name;

    @NotNull
    @URL(protocol = "https")
    @UniqueTargetUrl
    private String targetUrl;

    @Length(min=10)
    @HasFourSpecials
    @HasOneLowercase
    @HasThreeDigits
    @HasTwoUppercase
    private String password;

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

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
