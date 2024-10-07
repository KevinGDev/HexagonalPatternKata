package ls.accenture.Bibliotheque.repository;

import ls.accenture.Bibliotheque.entity.Emprunt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpruntRepository extends JpaRepository<Emprunt, Long> {

}
