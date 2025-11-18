package bowling;

import java.util.ArrayList;
import java.util.List;

public class TourJoueur {
    private int numero;
    private List<LancerJoueur> lancers = new ArrayList<>();

    public TourJoueur(int numero) {
        this.numero = numero;
    }

    public boolean enregistrerLancer(LancerJoueur lancer) {
        lancers.add(lancer);
        if (numero < 10) {
            if (lancers.size() == 1 && lancer.getNbQuille() == 10) {
                return false; // Strike
            } else if (lancers.size() == 2) {
                return false; // Deux lancers effectués
            }
        } else { // 10ème tour
            if (lancers.size() == 2) {
                int total = lancers.get(0).getNbQuille() + lancers.get(1).getNbQuille();
                if (total < 10) {
                    return false; // Pas de lancer supplémentaire
                }
            } else if (lancers.size() == 3) {
                return false; // Trois lancers effectués
            }
        }
        return true;
    }

    public boolean estStrike() {
        return lancers.size() >= 1 && lancers.get(0).getNbQuille() == 10;
    }

    public boolean estSpare() {
        return lancers.size() >= 2 && (lancers.get(0).getNbQuille() + lancers.get(1).getNbQuille() == 10)
               && !estStrike();
    }

    public boolean estTermine() {
        if (numero < 10) {
            return estStrike() || lancers.size() == 2;
        } else {
            if (lancers.size() < 2) {
                return false;
            } else if (lancers.size() == 2) {
                int total = lancers.get(0).getNbQuille() + lancers.get(1).getNbQuille();
                return total < 10;
            } else {
                return true; // Trois lancers effectués
            }
        }
    }

    public List<LancerJoueur> getLancers() {
        return lancers;
    }
}
