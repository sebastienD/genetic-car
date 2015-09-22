# Serveur - genetic car [![Build Status](https://travis-ci.org/sebastienD/genetic-car.svg?branch=master)](https://travis-ci.org/sebastienD/genetic-car)

Ce projet est est un exercice permettant de se falimiariser avec les algorithmes génétiques de manière ludique.

Il a été fortement inspiré des projets:
* http://boxcar2d.com/
* fork de http://rednuht.org/genetic_cars_2/ ([github](https://github.com/red42/HTML5_Genetic_Cars))

## L'api

Elle est composée de deux controllers ([swagger](http://genetic-car.herokuapp.com/swagger-ui.html)):
* GameController: permet d'initialiser et de supprimer l'environnement de simulation pour chacune des équipes.
* SimulationController: contient la fonction d'évaluation et les méthodes pour retourner le champion de chacune des équipes.

Une IHM est prévue pour :
* suivre en temps réel le dernier champion éavlué de chaque équipe
* confronter les champions de toutes les équipes


Happy Coding !