"""Main file of the python starter, code and send your solution in this module
"""

from car import RandomCar
from carScoreView import CarScoreView

import ujson
import httplib2

# /!\ Change team value below /!\
team = 'BLUE'  # RED, YELLOW, BLUE, GREEN, ORANGE, PURPLE
host = "http://genetic-car.herokuapp.com"


def run():
    doMyAlgo()


def evaluate(cars):
    """This will send your cars to the competition server and give to each of them
    a core. Then the best of those cars will be considered as your champion, the
    one which will be match against other teams. Those scores will be returned.
    """
    url = host + "/simulation/evaluate/" + team
    h = httplib2.Http(".cache")
    resp_headers, content = h.request(
        url,
        "POST",
        ujson.dumps(cars),
        headers={'content-type': 'application/json'}
    )
    response = ujson.decode(content)

    result = []
    try:
        result = [CarScoreView(car_view, res["score"])
                  for car_view, res in zip(cars, response)]
    except TypeError:
        print("Cannot parse response [%s]" % response)

    return result


def doMyAlgo():
    cars = [RandomCar() for i in range(20)]
    carScores = evaluate([car.to_carView() for car in cars])

    # Here comes your algo
    ##########################################################################
    #

    #
    ##########################################################################

    champion = max(carScores, key=lambda carScore: carScore.score)
    print("Mon champion est %s" % format(champion))


if __name__ == '__main__':
    run()
