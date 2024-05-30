package org.example.tpo_11.Repositories;

import org.example.tpo_11.Models.Link;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LinkRepository extends CrudRepository<Link, String>
{
    boolean existsByTargetUrl(String target);
}
