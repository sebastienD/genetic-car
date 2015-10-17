from src.carView import CarView
from src.random_utils import Random


class Car(object):

    def _createFrom(self, carView):
        for i in range(16):
            self.coords[i] = carView.chassi.vecteurs[i]

        self.coords[16] = carView.chassi.densite

        self.coords[17] = carView.wheel1.density
        self.coords[18] = carView.wheel1.radius
        self.coords[19] = carView.wheel1.vertex

        self.coords[20] = carView.wheel2.density
        self.coords[21] = carView.wheel2.radius
        self.coords[22] = carView.wheel2.vertex


    def __init__(self, create_from=None):
        self.coords = [0.0 for i in range(23)]
        if create_from is not None:
            self._createFrom(create_from)



    def to_carView(self):
        carView = CarView()

        for i in range(16):
            carView.chassi.vecteurs[i] = self.coords[i]

        carView.chassi.densite = self.coords[16]

        carView.wheel1.density = self.coords[17]
        carView.wheel1.radius = self.coords[18]
        carView.wheel1.vertex = self.coords[19]

        carView.wheel2.density = self.coords[20]
        carView.wheel2.radius = self.coords[21]
        carView.wheel2.vertex = self.coords[22]

        return carView


class RandomCar(Car):

    def __init__(self):
        self.coords = [0.0 for i in range(23)]

        self.coords[0] = Random.nextChassisAxis()
        self.coords[1] = 0.0
        self.coords[2] = Random.nextChassisAxis()
        self.coords[3] = Random.nextChassisAxis()
        self.coords[4] = 0.0
        self.coords[5] = Random.nextChassisAxis()
        self.coords[6] = -Random.nextChassisAxis()
        self.coords[7] = Random.nextChassisAxis()
        self.coords[8] = -Random.nextChassisAxis()
        self.coords[9] = 0.0
        self.coords[10] = -Random.nextChassisAxis()
        self.coords[11] = -Random.nextChassisAxis()
        self.coords[12] = 0.0
        self.coords[13] = -Random.nextChassisAxis()
        self.coords[14] = Random.nextChassisAxis()
        self.coords[15] = -Random.nextChassisAxis()
        self.coords[16] = Random.nextChassisDensity()
        self.coords[17] = Random.nextWheelDensity()
        self.coords[18] = Random.nextWheelRadius()
        self.coords[19] = Random.nextVertex()
        self.coords[20] = Random.nextWheelDensity()
        self.coords[21] = Random.nextWheelRadius()
        self.coords[22] = Random.nextVertex()
