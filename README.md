# genetic-car [![Build Status](https://travis-ci.org/sebastienD/genetic-car.svg?branch=master)](https://travis-ci.org/sebastienD/genetic-car)

Ce projet est est un exercice permettant de se falimiariser avec les algorithmes génétiques de manière ludique.

C'est un fork de ...

Il est composé de deux parties :
* le server: qui contient l'api
* les starters: qui contienent les clients permettant d'accéder à l'api.

Le déroulement
--------------

1. Initier une partie
2. mettre à jour les propriétés contenues dans le starter
3. développer l'algorithme génétique
4. confronter les résultats

L'api
-----

l'url de la fonction d'évaluation.
Pas plus de 20 voitures soumises par tour.
Le champion de chaque équipe est enregistré à chaque tour. Si le champion d'uné équipe du tour courant est moins que le champion du tour précédent, c'est quand même le dernier soumis qui est enregsitré.
URL de rendu temps réél.
URL finale.

La modélisation
--------------

Une voiture est composée de:
* 1 chassi: 8 polygones + une densité
* 2 roues : 1 rayon + 1 densité
* 2 points permettant de relier les roues au chassis


