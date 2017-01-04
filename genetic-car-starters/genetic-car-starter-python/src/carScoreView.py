class CarScoreView(object):

    def __init__(self, car, score):
        self.car = car
        self.score = score

    def __str__(self):
        return "{{car:{car}, score:{score}}}".format(
            car=str(self.car),
            score=str(self.score)
        )

    def __repr__(self):
        return str(self)
