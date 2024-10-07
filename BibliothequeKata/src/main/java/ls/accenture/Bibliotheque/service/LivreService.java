package ls.accenture.Bibliotheque.service;

import ls.accenture.Bibliotheque.entity.Emprunt;
import ls.accenture.Bibliotheque.entity.Livre;
import ls.accenture.Bibliotheque.entity.Utilisateur;
import ls.accenture.Bibliotheque.repository.EmpruntRepository;
import ls.accenture.Bibliotheque.repository.LivreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivreService {

    @Autowired
    private LivreRepository livreRepository;
    @Autowired
    EmpruntRepository empruntRepository;

    public Optional<Livre> getLivreById(Long id) {
        return livreRepository.findById(id);
    }

    public List<Livre> getLivres() {
        return livreRepository.findAll();
    }

    public Livre insertLivre(Livre livre) {
        return livreRepository.save(livre);
    }

    public void rentLivre(Livre livre, Utilisateur utilisateur) {
        Emprunt emprunt = new Emprunt();
        emprunt.setLivreId(livre.getId());
        emprunt.setUtilisateurId(utilisateur.getId());
        empruntRepository.save(emprunt);
    }
}
