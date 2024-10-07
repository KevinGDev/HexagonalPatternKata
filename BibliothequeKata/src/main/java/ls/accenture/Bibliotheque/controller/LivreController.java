package ls.accenture.Bibliotheque.controller;

import ls.accenture.Bibliotheque.entity.Livre;
import ls.accenture.Bibliotheque.entity.Utilisateur;
import ls.accenture.Bibliotheque.repository.LivreRepository;
import ls.accenture.Bibliotheque.repository.UtilisateurRepository;
import ls.accenture.Bibliotheque.service.LivreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/livre")
public class LivreController {

    @Autowired
    LivreService livreService;
    @Autowired
    UtilisateurRepository utilisateurRepository;
    @Autowired
    LivreRepository livreRepository;


    @GetMapping("/id")
    public Optional<Livre> getLivreById(@RequestParam Long id) {
        return livreService.getLivreById(id);
    }

    @GetMapping()
    public List<Livre> getLivres() {
        return livreService.getLivres();
    }

    @PostMapping()
    public ResponseEntity<Livre> insertLivre(@RequestBody Livre livre, @RequestParam Long utilisateurId) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(utilisateurId);
        if (utilisateur.isPresent()) {
            if (utilisateur.get().getRole().equals("administrateur")) {
                Livre livreInsere = livreService.insertLivre(livre);
                return new ResponseEntity<>(livreInsere, HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/rent")
    public ResponseEntity<HttpStatus> rentLivre(@RequestParam Long livreId, @RequestParam Long utilisateurId) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(utilisateurId);
        if (utilisateur.isPresent()) {
            Optional<Livre> livre = livreRepository.findById(livreId);
            if (livre.isPresent()) {
                if (livre.get().getDisponible().equals("Oui")) {
                    livre.get().setDisponible("Non");
                    livreService.rentLivre(livre.get(), utilisateur.get());
                    return new ResponseEntity<>(HttpStatus.ACCEPTED);
                }
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


}
