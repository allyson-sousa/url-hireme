package testing.urls.lampit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import testing.urls.lampit.model.URLMapping;

import java.util.List;


public interface URLMappingRepository extends JpaRepository<URLMapping, Long> {
    URLMapping findByAlias(String alias);
    List<URLMapping> findTop10ByOrderByHitsDesc();

}
