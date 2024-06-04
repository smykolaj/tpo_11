package org.example.tpo_11.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.example.tpo_11.Models.*;
import org.example.tpo_11.Repositories.LinkRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

@Service
public class LinkService
{

    private final Validator validator;
    private final LinkRepository linkRepository;
    private final LinkDtoMapper linkDtoMapper;
    private final ObjectMapper objectMapper;

    private final Random random = new Random();

    public LinkService(Validator validator, LinkRepository linkRepository, LinkDtoMapper linkDtoMapper, ObjectMapper objectMapper)
    {
        this.validator = validator;
        this.linkRepository = linkRepository;
        this.linkDtoMapper = linkDtoMapper;
        this.objectMapper = objectMapper;
    }

    public LinkDTO saveLink(PostLinkDTO postLinkDTO)
    {

        Link savedlink = linkDtoMapper.map(postLinkDTO);
        savedlink.setVisits(0L);
        String id = this.createRandomString();
        while (linkRepository.findById(id).isPresent())
            id = this.createRandomString();
        savedlink.setId(id);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .replacePath("red/{id}")
                .buildAndExpand(savedlink.getId())
                .toUri();
        savedlink.setRedirectUrl(uri.toString());
            try {
                linkRepository.save(savedlink);
            } catch (ConstraintViolationException cve) {
                Set<ConstraintViolation<?>> violations = cve.getConstraintViolations();
                System.err.println("Constraint violations for object " + savedlink);
                violations.forEach(System.err::println);
                return new LinkDTO();
            }

        return linkDtoMapper.map(savedlink);

    }

    private String createRandomString()
    {
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++)
        {
            int base = (random.nextBoolean()) ? 65 : 97;
            char randomChar = (char) (base + random.nextInt(26));
            sb.append(randomChar);
        }
        return sb.toString();
    }

    public Optional<LinkDTO> getLinkDTOById(String id)
    {
        return linkRepository.findById(id).map(linkDtoMapper::map);
    }

    public Optional<PostLinkDTO> getPostLinkDTOById(String id)
    {
        return linkRepository.findById(id).map(linkDtoMapper::mapToPost);
    }




    public void deleteLink(String id, String password)
    {
        Link existingLink = linkRepository.findById(id).orElse(null);

        if (existingLink == null)
        {
            throw new NoSuchElementException();
        }
        if (existingLink.getPassword() == null || password == null)
        {
            throw new SecurityException();

        }
        if (!password.equals(existingLink.getPassword()))
            throw new SecurityException();

        linkRepository.delete(existingLink);
    }


    public Optional<Link> updateLink(@Valid LinkDTO patchedBookDTO)
    {
        Set<ConstraintViolation<LinkDTO>> errors = validator.validate(patchedBookDTO);
        if (errors.isEmpty()){
            return Optional.of(linkRepository.save(linkDtoMapper.map(patchedBookDTO)));
        }else{
            throw new ConstraintViolationException(errors);
        }

//        try {
//            return Optional.of(linkRepository.save(linkDtoMapper.map(patchedBookDTO)));
//        } catch (ConstraintViolationException cve) {
//            Set<ConstraintViolation<?>> violations = cve.getConstraintViolations();
//            System.err.println("Constraint violations for object " + patchedBookDTO);
//            violations.forEach(System.err::println);
//            return Optional.of(new Link());
//        }


    }

    public boolean checkPassword(String id, JsonMergePatch patch) throws JsonPatchException
    {
        String  pass = patch.apply(objectMapper.valueToTree(new PostLinkDTO())).path("password").toString();
        pass = pass.substring(1, pass.length()-1);
        return pass.equals(linkRepository.findById(id).get().getPassword());
    }
    public boolean checkPassword(String id, PostLinkDTO patch)
    {

        return patch.getPassword().equals(linkRepository.findById(id).get().getPassword());
    }
}
