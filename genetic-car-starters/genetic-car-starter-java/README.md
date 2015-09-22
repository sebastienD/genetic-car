# Starter Java - genetic car [![Build Status](https://travis-ci.org/sebastienD/genetic-car.svg?branch=master)](https://travis-ci.org/sebastienD/genetic-car)

Ce projet est est un exercice permettant de se falimiariser avec les algorithmes génétiques de manière ludique.

Il a été fortement inspiré des projets:
* http://boxcar2d.com/
* fork de http://rednuht.org/genetic_cars_2/ ([github](https://github.com/red42/HTML5_Genetic_Cars))

## La modélisation

Une voiture est composée de:
* 1 chassi: 8 vecteurs et une densité
* 2 roues : 1 rayon, 1 densité et un somet permettant de rattacher la roue au chassi (pour chaque roue)

### Les contraintes

* Les 8 vecteurs composants le chassi sont définis come suit:
(X1,0) (X2,Y1) (0,Y2) (-X3,Y3) (-X4,0) (-X5,-Y4) (0,-Y5) (X6, -Y6)
où les Xn et Yn sont des float conpris entre 0.1 et 1.1 (bornes incluses).
La position des 0 et des signes est à respecter.

Exemple :
```
   (0.1,0) (0.8,0.3) (0,0.5) (-1,1.05) (-0.4,0) (-0.1,-0.7) (O,-0.6) (1.09,-1)
```

* La densité du chassi est un float compris entre 30 et 300 (bornes incluses).

* Le rayon d'une roue est un float compris entre 0.2 et 0.5 (bornes incluses).

* La densité d'une roue est un float compris entre 40 et 100 (bornes incluses).

* Le sommet reliant la roue au chassi est un entier compris entre 0 et 7 (bornes incluses).

* Pas plus de voitures soumises par appel de la fonction d'évaluation.

* Le champion de chaque équipe est enregistré à chaque appel de la fonction d'évaluation. 
Si le champion de la dernière évaluation est moins bon que le champion de la soumission précédente, c'est tout de même le dernier champion qui sera enregistré.

## L'api 

Le plus simple est d'aller voir sur [swagger](http://genetic-car.herokuapp.com/swagger-ui.html).


Happy coding !