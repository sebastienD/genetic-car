# Genetic car [![Build Status](https://travis-ci.org/sebastienD/genetic-car.svg?branch=master)](https://travis-ci.org/sebastienD/genetic-car)

Ce projet est est un exercice permettant de se familiariser avec les algorithmes génétiques de manière ludique.

Il a été fortement inspiré des projets:
* http://boxcar2d.com/
* fork de http://rednuht.org/genetic_cars_2/ ([github](https://github.com/red42/HTML5_Genetic_Cars))

## Pré-requis

Une présentation des algorithmes génétiques est disponible [ici](https://github.com/sebastienD/presentation-algorithme-genetique) afin de connaître les concepts de bases.

## Le déroulement

1. Initier une partie via l'api serveur
2. mettre à jour les propriétés contenues dans le starter
3. développer l'algorithme génétique
4. confronter les résultats

## En local

Aller dans le répertoire ```genetic-car-server``` 

### Build Gui

```
    grunt build
```

### Run Gui

```
    grunt serve
```

### Run server

```
    mvn
```

## Heroku

Un procfile est disponible à la racine du projet.

## Deploy

Un web hook est présent sur Heroku.

```
    git push
```

### Les logs

```
    heroku logs --app genetic-car
```


Happy coding !



