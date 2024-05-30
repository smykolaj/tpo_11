package org.example.tpo_11.Services;

import org.example.tpo_11.Models.Link;
import org.example.tpo_11.Models.LinkDTO;
import org.example.tpo_11.Models.PostLinkDTO;
import org.springframework.stereotype.Service;

@Service
public class LinkDtoMapper
{

    public LinkDTO map(Link link) {
        LinkDTO linkDTO = new LinkDTO();
        linkDTO.setId(link.getId());
        linkDTO.setName(link.getName());
        linkDTO.setTargetUrl(link.getTargetUrl());
        linkDTO.setRedirectUrl(link.getRedirectUrl());
        linkDTO.setVisits(link.getVisits());
        linkDTO.setPassword(link.getPassword());
        return linkDTO;
    }

    public Link map(LinkDTO linkDto) {
        Link link = new Link();
        link.setId(linkDto.getId());
        link.setName(linkDto.getName());
        link.setTargetUrl(linkDto.getTargetUrl());
        link.setRedirectUrl(linkDto.getRedirectUrl());
        link.setVisits(linkDto.getVisits());
        link.setPassword(linkDto.getPassword());
        return link;
    }

    public Link map(PostLinkDTO postLinkDTO){
        Link link = new Link();
        link.setName(postLinkDTO.getName());
        link.setTargetUrl(postLinkDTO.getTargetUrl());
        link.setPassword(postLinkDTO.getPassword());
        return link;
    }

    public PostLinkDTO mapToPost (Link link){
        PostLinkDTO postLinkDTO = new PostLinkDTO();
        postLinkDTO.setName(link.getName());
        postLinkDTO.setTargetUrl(link.getTargetUrl());
        postLinkDTO.setPassword(link.getPassword());
        return postLinkDTO;
    }




}
