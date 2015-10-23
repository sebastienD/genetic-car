using System;

namespace GeneticAlgorithm.algo
{
    public class CustomRandom
    {
        private const float CHASSIS_MIN_AXIS = 0.1F;
        private const float CHASSIS_MAX_AXIS = 1.1F;

        private const float CHASSIS_MIN_DENSITY = 30F;
        private const float CHASSIS_MAX_DENSITY = 300F;

        private const float WHEEL_MIN_RADIUS = 0.2F;
        private const float WHEEL_MAX_RADIUS = 0.5F;

        private const float WHEEL_MIN_DENSITY = 40F;
        private const float WHEEL_MAX_DENSITY = 100F;

        private const int VERTEX_MAX_VALUE = 7;

        private CustomRandom()
        {
        }

        private static float Next(float minValue, float maxValue)
        {
            return (float) (new Random().NextDouble() * (maxValue - minValue) + minValue);
        }

        public static float NextChassisAxis()
        {
            return Next(CHASSIS_MIN_AXIS, CHASSIS_MAX_AXIS);
        }

        public static float NextChassisDensity()
        {
            return Next(CHASSIS_MIN_DENSITY, CHASSIS_MAX_DENSITY);
        }

        public static float NextWheelRadius()
        {
            return Next(WHEEL_MIN_RADIUS, WHEEL_MAX_RADIUS);
        }

        public static float NextWheelDensity()
        {
            return Next(WHEEL_MIN_DENSITY, WHEEL_MAX_DENSITY);
        }

        public static int NextVertex()
        {
            return new Random().Next(VERTEX_MAX_VALUE + 1);
        }
    }
}
