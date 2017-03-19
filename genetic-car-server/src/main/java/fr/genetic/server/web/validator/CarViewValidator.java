package fr.genetic.server.web.validator;

import fr.genetic.server.simulation.CarDefinition;
import fr.genetic.server.web.view.CarView;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CarViewValidator {

    public List<String> validate(CarView carView) {
        // TODO améliorer le validator
        List<String> errors = Validator.of(carView.chassis)
                .validate(c -> c.vecteurs.size() == 16, "le nombre de coordonnées du chassis est incorrect (!= de 16)")
                .messagesStream()
                .collect(Collectors.toList());

        if (!errors.isEmpty()) {
            return errors;
        }

        return Stream.of(
                validateWheel(carView.wheel1),
                validateWheel(carView.wheel2),
                Validator.of(carView.chassis)
                        .validate(c -> c.vecteurs, 0, validPositiveCoord(), "la coordonnée n'est pas comprise entre 0.1 et 1.1")
                        .validate(c -> c.vecteurs, 1, isZero(), "la coordonnée doit avoir la valeur zéro")
                        .validate(c -> c.vecteurs, 2, validPositiveCoord(), "la coordonnée n'est pas comprise entre 0.1 et 1.1")
                        .validate(c -> c.vecteurs, 3, validPositiveCoord(), "la coordonnée n'est pas comprise entre 0.1 et 1.1")
                        .validate(c -> c.vecteurs, 4, isZero(), "la coordonnée doit avoir la valeur zéro")
                        .validate(c -> c.vecteurs, 5, validPositiveCoord(), "la coordonnée n'est pas comprise entre 0.1 et 1.1")
                        .validate(c -> c.vecteurs, 6, validNegativeCoord(), "la coordonnée n'est pas comprise entre -1.1 et -0.1")
                        .validate(c -> c.vecteurs, 7, validPositiveCoord(), "la coordonnée n'est pas comprise entre 0.1 et 1.1")
                        .validate(c -> c.vecteurs, 8, validNegativeCoord(), "la coordonnée n'est pas comprise entre -1.1 et -0.1")
                        .validate(c -> c.vecteurs, 9, isZero(), "la coordonnée doit avoir la valeur zéro")
                        .validate(c -> c.vecteurs, 10, validNegativeCoord(), "la coordonnée n'est pas comprise entre -1.1 et -0.1")
                        .validate(c -> c.vecteurs, 11, validNegativeCoord(), "la coordonnée n'est pas comprise entre -1.1 et -0.1")
                        .validate(c -> c.vecteurs, 12, isZero(), "la coordonnée doit avoir la valeur zéro")
                        .validate(c -> c.vecteurs, 13, validNegativeCoord(), "la coordonnée n'est pas comprise entre -1.1 et -0.1")
                        .validate(c -> c.vecteurs, 14, validPositiveCoord(), "la coordonnée n'est pas comprise entre 0.1 et 1.1")
                        .validate(c -> c.vecteurs, 15, validNegativeCoord(), "la coordonnée n'est pas comprise entre -1.1 et -0.1")
                        .validate(c -> c.densite, inBetween(30F, 300F), "la densite du chassis n'est pas comprise entre 30 et 300")
                .messagesStream()
        )
                .flatMap(Function.identity())
                .collect(Collectors.toList());
    }

    private Stream<String> validateWheel(CarDefinition.WheelDefinition wheel) {
        return Validator.of(wheel)
                .validate(w -> w.radius, inBetween(0.2F, 0.5F), "le rayon doit être compris entre 0.2 et 0.5")
                .validate(w -> w.density, inBetween(40F, 100F), "la densite doit être comprise entre 40 et 100")
                .validate(w -> w.vertex, inBetween(0, 7), "le sommet doit être compris entre 0 et 7")
                .messagesStream();
    }

    private Predicate<Float> validPositiveCoord() {
        return inBetween(0.1F, 1.1F);
    }

    private Predicate<Float> validNegativeCoord() {
        return inBetween(-1.1F, -0.1F);
    }

    private Predicate<Integer> inBetween(int start, int end) {
        return t -> t >= start && t <= end;
    }

    private Predicate<Float> inBetween(float start, float end) {
        return t -> t >= start && t <= end;
    }

    private Predicate<Float> isZero() {
        return t -> t == 0;
    }

}
