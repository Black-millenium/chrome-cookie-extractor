package me.desu.chromecookieextractor.repository;

import me.desu.chromecookieextractor.entity.CookiesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CookiesRepository extends CrudRepository<CookiesEntity, Long> {

    List<CookiesEntity> findByHostKeyContains(String hostKey);
}
