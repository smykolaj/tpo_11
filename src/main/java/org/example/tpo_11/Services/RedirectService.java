package org.example.tpo_11.Services;


import org.example.tpo_11.Models.Link;
import org.example.tpo_11.Repositories.LinkRepository;
import org.springframework.stereotype.Service;


@Service

public class RedirectService
{

    private final LinkRepository linkRepository;

    public RedirectService(LinkRepository linkRepository)
    {
        this.linkRepository = linkRepository;
    }

    public boolean incrementVisits(String id)
    {

        if (linkRepository.findById(id).isPresent()){
            Link link = linkRepository.findById(id).get();
            link.setVisits(link.getVisits() + 1 );
            linkRepository.save(link);
            return true;
        }
        return false;
    }

    public Link getLinkById(String id)
    {
        return linkRepository.findById(id).get();

    }

}
