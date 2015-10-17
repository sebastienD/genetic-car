"""Random utilitaire used to build random cars
"""
import random


CHASSIS_MIN_AXIS = 0.1
CHASSIS_MAX_AXIS = 1.1

CHASSIS_MIN_DENSITY = 30
CHASSIS_MAX_DENSITY = 300

WHEEL_MIN_RADIUS = 0.2
WHEEL_MAX_RADIUS = 0.5

WHEEL_MIN_DENSITY = 40
WHEEL_MAX_DENSITY = 100

VERTEX_MAX_VALUE = 7


class Random(object):

    """All methods are static
    """


    @staticmethod
    def nextChassisAxis():
        return random.uniform(CHASSIS_MIN_AXIS, CHASSIS_MAX_AXIS)

    @staticmethod
    def nextChassisDensity():
        return random.uniform(CHASSIS_MIN_DENSITY, CHASSIS_MAX_DENSITY)

    @staticmethod
    def nextWheelRadius():
        return random.uniform(WHEEL_MIN_RADIUS, WHEEL_MAX_RADIUS)

    @staticmethod
    def nextWheelDensity():
        return random.uniform(WHEEL_MIN_DENSITY, WHEEL_MAX_DENSITY)

    @staticmethod
    def nextVertex():
        return random.randint(0, 7)
