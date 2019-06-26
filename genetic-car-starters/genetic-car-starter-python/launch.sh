#!/bin/bash

virtualenv -p python3 genetic-car
source genetic-car/bin/activate
python setup.py install
python setup.py test
python src/launch.py

