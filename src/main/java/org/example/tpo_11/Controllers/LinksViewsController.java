package org.example.tpo_11.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.example.tpo_11.Models.LinkDTO;
import org.example.tpo_11.Models.PostLinkDTO;
import org.example.tpo_11.Services.LinkService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;
import java.util.Optional;

@Controller
public class LinksViewsController {

    private final LinkService linkService;
    private final ObjectMapper objectMapper;

    public LinksViewsController(LinkService linkService, ObjectMapper objectMapper) {
        this.linkService = linkService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }


    @GetMapping("/createLink")
    public String createLink(Model model) {
        model.addAttribute("link", new PostLinkDTO());
        return "create";
    }
    @GetMapping("/change-language")
    public String showChangeLanguage(Model model) {
        String lang = "";
        model.addAttribute(lang);
        return "changeLanguage";
    }

    @PostMapping("/change-language")
    public String changeLanguage(Model model, @ModelAttribute("lang") String lang){
        Locale.setDefault(Locale.of(lang));
        return "index";
    }


    @GetMapping("/createLinkWoPassword")
    public String createLinkWoPassword(Model model) {
        model.addAttribute("link", new PostLinkDTO());
        return "createNoPass";
    }

    @PostMapping("/createLink")
    public String createLink(Model model, @Valid @ModelAttribute("link") PostLinkDTO postLinkDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "create";
        LinkDTO dto = linkService.saveLink(postLinkDTO);
        model.addAttribute("link", dto);
        return "linkInfo";
    }

    @PostMapping("/createLinkWoPassword")
    public String createLinkWoPassword(Model model, @Valid @ModelAttribute("link") PostLinkDTO postLinkDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "createNoPass";

        LinkDTO dto = linkService.saveLink(postLinkDTO);
        model.addAttribute("link", dto);
        return "linkInfo";
    }

    @GetMapping("/findLinkById")
    public String getLinkInfo(@RequestParam("id") String id, Model model) {
        Optional<LinkDTO> link = linkService.getLinkDTOById(id);
        if (link.isPresent()) {
            model.addAttribute("link", link.get());
            return "linkInfo";
        } else {
            return "notFound";
        }
    }
    @GetMapping("/lookUp")
    public String lookUp(Model model) {
        return "lookUp";
    }

    @GetMapping("/links/delete")
    public String deleteLink(){
        return "deleteLink";
    }

    @PostMapping("/links/delete")
    public String deleteLink(@RequestParam("id") String id, @RequestParam("password") String password, RedirectAttributes redirectAttributes) {
        try{linkService.deleteLink(id, password);
            redirectAttributes.addFlashAttribute("message", "Link successfully deleted.");
            return "redirect:/";
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("error", "Invalid ID or password.");
            return "redirect:/links/delete";
        }
    }
}
