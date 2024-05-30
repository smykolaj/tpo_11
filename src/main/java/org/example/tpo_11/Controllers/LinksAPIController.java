package org.example.tpo_11.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.example.tpo_11.Services.*;
import org.example.tpo_11.Models.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.validation.FieldError;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/links",
        produces = {MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_XML_VALUE})
public class LinksAPIController
{
    private final LinkService linkService;
    private final ObjectMapper objectMapper;


    public LinksAPIController(LinkService linkService, ObjectMapper objectMapper)
    {
        this.linkService = linkService;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    public ResponseEntity<?> saveLink(@Valid @RequestBody PostLinkDTO postLinkDTO) {


        LinkDTO savedLink = linkService.saveLink(postLinkDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedLink.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedLink);
    }

    @Tag(name = "GET", description = "Get information about redirect link")
    @GetMapping("/{id}")
    public ResponseEntity<LinkDTO> getLink(@PathVariable String id)
    {
        return linkService.getLinkDTOById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Tag(name = "PATCH", description = "Update link information")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateLink(@PathVariable String id, @RequestBody JsonMergePatch patch)
    {
        try
        {
            LinkDTO linkDTO = linkService.getLinkDTOById(id).orElseThrow();
            if (linkService.checkPassword(id, patch))
            {
                LinkDTO patchedlinkDTO = applyPatch(linkDTO, patch);
                String  pass = patch.apply(objectMapper.valueToTree(new PostLinkDTO())).path("password").toString();
                pass = pass.substring(1, pass.length()-1);
                patchedlinkDTO.setPassword(pass);
                linkService.updateLink(patchedlinkDTO);
                return ResponseEntity.noContent().build();
            } else
                return ResponseEntity.status(403).header("Reason", "wrong password").build();

        } catch (NoSuchElementException ex)
        {
            return ResponseEntity.notFound().build();
        } catch (JsonPatchException | JsonProcessingException e)
        {
            return ResponseEntity.internalServerError().build();
        }


    }

    private LinkDTO applyPatch(LinkDTO bookDTO, JsonMergePatch patch) throws JsonProcessingException, JsonPatchException
    {
        JsonNode bookNode = objectMapper.valueToTree(bookDTO);
        JsonNode patchNode = patch.apply(bookNode);
        return objectMapper.treeToValue(patchNode, LinkDTO.class);
    }


    @Tag(name = "DELETE", description = "Remove book")
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteLink(@PathVariable String id, @RequestParam(required = false) String password)
    {
        try
        {
            linkService.deleteLink(id, password);
        } catch (NoSuchElementException e)
        {
            return ResponseEntity.noContent().build();
        } catch (SecurityException e)
        {
            return ResponseEntity.status(403).header("Reason", "wrong password").build();

        }
        return ResponseEntity.noContent().build();


    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    Map<String, ArrayList<String>> handle(MethodArgumentNotValidException ex){
        return ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(
                                FieldError::getDefaultMessage,
                                Collectors.toCollection(ArrayList::new)
                        )
                ));
    }

    @ExceptionHandler ({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Map<String, List<String>> handleConstraintViolationException(
            ConstraintViolationException e) {
        return e.getConstraintViolations()
                .stream()
                .collect(Collectors.groupingBy(
                        violation -> violation.getPropertyPath().toString(),
                        Collectors.mapping(violation -> violation.getMessage(), Collectors.toList())
                ));
    }


}
