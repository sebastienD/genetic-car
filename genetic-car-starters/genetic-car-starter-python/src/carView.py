class Chassi(object):

    def __init__(self):
        self.vecteurs = [0.0 for i in range(16)]
        self.densite = 0.0

    def __str__(self):
        return "{{vecteurs:{vecteurs}, densite:{densite}}}".format(
            vecteurs=str(self.vecteurs),
            densite=str(self.densite)
        )


class Wheel(object):

    def __init__(self):
        self.radius = 0.0
        self.density = 0.0
        self.vertex = 0

    def __str__(self):
        return "{{radius:{radius}, density:{density}, vertex:{vertex}}}".format(
            radius=str(self.radius),
            density=str(self.density),
            vertex=str(self.vertex)
        )


class CarView(object):

    def __init__(self):
        self.chassi = Chassi()
        self.wheel1 = Wheel()
        self.wheel2 = Wheel()

    def __str__(self):
        return "{{chassi:{chassi}, wheel1:{wheel1},wheel2:{wheel2}}}".format(
            chassi=str(self.chassi),
            wheel1=str(self.wheel1),
            wheel2=str(self. wheel2)
        )

    def __repr__(self):
        return str(self)
