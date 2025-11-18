package bowling;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe a pour but d'enregistrer le nombre de quilles abattues lors des
 * lancers successifs d'<b>un seul et même</b> joueur, et de calculer le score
 * final de ce joueur
 */
public class PartieMonoJoueur {
	private List<TourJoueur> tours;
    private int tourCourant;

	/**
	 * Constructeur
	 */
	public PartieMonoJoueur() {
		tours = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            tours.add(new TourJoueur(i));
        }
        tourCourant = 0;
	}

	/**
	 * Cette méthode doit être appelée à chaque lancer de boule
	 *
	 * @param nombreDeQuillesAbattues le nombre de quilles abattues lors de ce lancer
	 * @throws IllegalStateException si la partie est terminée
	 * @return vrai si le joueur doit lancer à nouveau pour continuer son tour, faux sinon	
	 */
	public boolean enregistreLancer(int nombreDeQuillesAbattues) {
		if (estTerminee()) {
            throw new IllegalStateException("La partie est terminée");
        }

        LancerJoueur lancer = new LancerJoueur(nombreDeQuillesAbattues);
        boolean tourContinue = tours.get(tourCourant).enregistrerLancer(lancer);

        if (!tourContinue && tours.get(tourCourant).estTermine()) {
            tourCourant++;
        }

        return tourContinue;
	}

	/**
	 * Cette méthode donne le score du joueur.
	 * Si la partie n'est pas terminée, on considère que les lancers restants
	 * abattent 0 quille.
	 * @return Le score du joueur
	 */
	public int score() {
		List<Integer> lancers = getTousLancers();
        int score = 0;
        int indexLancer = 0;

        for (int frame = 0; frame < 10 && indexLancer < lancers.size(); frame++) {
            int premier = lancers.get(indexLancer);

            // STRIKE
            if (premier == 10) {
                score += 10 + getLancerSuivant(lancers, indexLancer + 1) 
                             + getLancerSuivant(lancers, indexLancer + 2);
                indexLancer += 1;
            }
            // SPARE
            else if (premier + getLancerSuivant(lancers, indexLancer + 1) == 10) {
                score += 10 + getLancerSuivant(lancers, indexLancer + 2);
                indexLancer += 2;
            }
            // CAS NORMAL
            else {
                score += premier + getLancerSuivant(lancers, indexLancer + 1);
                indexLancer += 2;
            }
        }

        return score;
	}

	private int getLancerSuivant(List<Integer> lancers, int i) {
        return (i < lancers.size()) ? lancers.get(i) : 0;
    }

    private List<Integer> getTousLancers() {
        List<Integer> lancers = new ArrayList<>();
        for (TourJoueur t : tours) {
            for (LancerJoueur l : t.getLancers()) {
                lancers.add(l.getNbQuille());
            }
        }
        return lancers;
    }


	/**
	 * @return vrai si la partie est terminée pour ce joueur, faux sinon
	 */
	public boolean estTerminee() {
		return tourCourant >= 10;
	}


	/**
	 * @return Le numéro du tour courant [1..10], ou 0 si le jeu est fini
	 */
	public int numeroTourCourant() {
		 return estTerminee() ? 0 : tourCourant + 1;
	}

	/**
	 * @return Le numéro du prochain lancer pour tour courant [1..3], ou 0 si le jeu
	 *         est fini
	 */
	public int numeroProchainLancer() {
		return estTerminee() ? 0 : tours.get(tourCourant).getLancers().size() + 1;
	}
}
