using System;
using System.Security.Cryptography;

namespace GeneticAlgorithm.Algo
{
    public class CustomRandom
    {
        private static RandomNumberGenerator RNG = RandomNumberGenerator.Create();

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

        private static double NextDouble()
        {
            byte[] b = new byte[4];
            RNG.GetBytes(b);
            return (double)BitConverter.ToUInt32(b, 0) / UInt32.MaxValue;
        }

        private static float Next(float minValue, float maxValue)
        {
            return (float) (NextDouble() * (maxValue - minValue) + minValue);
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
            return (int) Next(0, VERTEX_MAX_VALUE + 1);
        }
    }
}
