package org.example.tpo_11.constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.tpo_11.Repositories.LinkRepository;
import org.example.tpo_11.Tpo11Application;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


public class UniqueTargetUrlValidator implements ConstraintValidator<UniqueTargetUrl, String> {

    private final LinkRepository linkRepository;

    public UniqueTargetUrlValidator(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }


    @Override
    public void initialize(UniqueTargetUrl constraint) {
    }

    @Override
    public boolean isValid(String url, ConstraintValidatorContext context) {
        return !linkRepository.existsByTargetUrl(url);
    }
}
